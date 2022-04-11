/**
 * 
 */
package com.visiongc.app.strategos.web.struts.transacciones.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm.ImportarStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.app.strategos.web.struts.transacciones.forms.TransaccionForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Columna.ColumnaTipo;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.transaccion.TransaccionService;
import com.visiongc.framework.util.FrameworkConnection;

/**
 * @author Kerwin
 *
 */
public class ImportarTransaccionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		  
		Long transaccionId = (request.getParameter("transaccionId") != null ? Long.parseLong(request.getParameter("transaccionId")) : 0L);
		TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
		Transaccion transaccion = (Transaccion) transaccionService.load(Transaccion.class, transaccionId);
		transaccionService.close();

		TransaccionForm transaccionForm = (TransaccionForm)form;
		ActionMessages messages = getMessages(request);
		
		if (request.getParameter("funcion") != null)
		{
			transaccionForm.setTransaccionId(transaccionId);
			if (transaccion != null)
			{
				if (transaccion.getTabla() != null)
					transaccionForm.setTransaccion(transaccion);
			}
	    	
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("verificar")) 
	    	{
	    		Verificar(request, transaccionForm);
	    		if (transaccionForm.getRespuesta().indexOf("false") != -1)
	    		{
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar.archivo.invalido"));
				    saveMessages(request, messages);
	    		}
	    		
	    		return mapping.findForward(forward);
	    	}
	    	else if (funcion.equals("importar"))
	    	{
	    		Importar(request, transaccionForm);
	    		
	    		return mapping.findForward(forward);
	    	}
		}

		transaccionForm.clear();
		
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
		frameworkService.close();
		
		if (configuracion == null)
		{
			transaccionForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.status.configuracion.noexiste"));
			saveMessages(request, messages);
		}
		else
		{
			//XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
	        DocumentBuilder db = dbf.newDocumentBuilder(); 
	        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8"))); 
	        doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("properties");
			Element eElement = (Element) nList.item(0);
			/** Se obtiene el FormBean haciendo el casting respectivo */
			String url = VgcAbstractService.getTagValue("url", eElement);;
			String driver = VgcAbstractService.getTagValue("driver", eElement);
			String user = VgcAbstractService.getTagValue("user", eElement);
			String password = VgcAbstractService.getTagValue("password", eElement);

			if (!new FrameworkConnection().testConnection(url, driver, user, new com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm().getPasswordConexionDecriptado(password)))
			{
				transaccionForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.status.configuracion.noexiste"));
				saveMessages(request, messages);
			}
			else
				transaccionForm.setStatus(ImportarStatus.getImportarStatusSuccess());
		}
		
		transaccionForm.setTipoFuente(ImportacionType.getImportacionTypeExcel());
		transaccionForm.setTransaccionId(transaccionId);
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		Indicador indicador;
		if (transaccion != null)
		{
			transaccionForm.setFrecuencia(transaccion.getFrecuencia());
			
			if (transaccion.getTabla() != null)
			{
				transaccionForm.setTransaccion(transaccion);
				for (Iterator<Columna> i = transaccion.getTabla().getColumnas().iterator(); i.hasNext(); )
				{
					Columna columna = (Columna)i.next();
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoDate().byteValue())
					{
						transaccionForm.setHayColumnaFecha(true);
						if (transaccion.getIndicadorId() != null)
						{
							indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, transaccion.getIndicadorId());
							if (indicador != null)
							{
								transaccionForm.setIndicadorTransaccionesId(indicador.getIndicadorId());
								transaccionForm.setIndicadorTransaccionesNombre(indicador.getNombre());
							}
						}
					}
					
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
					{
						transaccionForm.setHayColumnaMonto(true);
						if (columna.getIndicadorId() != null)
						{
							indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, columna.getIndicadorId());
							if (indicador != null)
							{
								transaccionForm.setIndicadorMontoId(indicador.getIndicadorId());
								transaccionForm.setIndicadorMontoNombre(indicador.getNombre());
							}
						}
					}
				}
			}
		}
		strategosIndicadoresService.close();
		  
		return mapping.findForward(forward);
	}
	
	private void Verificar(HttpServletRequest request, TransaccionForm transaccionForm)
	{
	    if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePlano().byteValue())
	    	VerificarTxt(request, transaccionForm);
	    else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && transaccionForm.getExcelTipo().byteValue() == 0)
	    	VerificarExcel2007(request, transaccionForm);
	    else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && transaccionForm.getExcelTipo().byteValue() == 1)
	    	VerificarExcel2010(request, transaccionForm);
	    else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue()
	    		|| transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue()
	    		|| transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
	    	ObtenerTablasBd(request, transaccionForm);
	}
	
	private void VerificarTxt(HttpServletRequest request, TransaccionForm transaccionForm)
	{
	    String separador = transaccionForm.getSeparador();
	    int indice;
	    String campo = "";
	    BufferedReader entrada;

	    FormFile archivo = (FormFile) transaccionForm.getMultipartRequestHandler().getFileElements().get("fileName");
	    transaccionForm.setFileForm(archivo);
	    
	    String res = "";
	    try 
	    {
	    	entrada = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
	    	String linea;
	    	boolean hayCampo = false;
	    	while(entrada.ready()) 	    
	    	{
	    		linea = entrada.readLine();
	    		indice = 0;
				while (indice > -1)
				{
					indice = linea.indexOf(separador);
	    			hayCampo = false;
	    			String nombreCampo = campo;
					if (indice != -1)
					{
						campo = linea.substring(0, indice);
						linea = linea.substring(indice + 1, linea.length());
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							if (campo.equalsIgnoreCase(columna.getNombre()))
							{
								hayCampo = true;
								break;
							}
						}
						
						if (hayCampo)
							res = nombreCampo + (hayCampo ? "true" : "false") + ",";
					}
					else if (linea.length() > 0)
					{
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							if (campo.equalsIgnoreCase(columna.getNombre()))
							{
								hayCampo = true;
								break;
							}
						}
						
						if (hayCampo)
							res = res + nombreCampo + "=" + (hayCampo ? "true" : "false") + ",";
					}
					
					if (hayCampo)
						break;
				}
				
				if (hayCampo)
					break;
	    	}
	    	
	    	entrada.close();
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }

	    transaccionForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    transaccionForm.setRespuesta(res);
	    transaccionForm.setBdStatusTabla(res);
	}
	
	private void VerificarExcel2007(HttpServletRequest request, TransaccionForm transaccionForm)
	{
	    String res = "";
	    String campo;
	    
	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = (FormFile) transaccionForm.getMultipartRequestHandler().getFileElements().get("fileName");
	    	transaccionForm.setFileForm(archivo);
	    	Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	Sheet sheet = workbook.getSheet(0);
	    	Cell celda = null;
	    	boolean hayCampo = false;
	    	for ( int i=0, z=sheet.getRows(); i<z; i++ )
	    	{
	    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
	    		{
	    			celda = sheet.getCell(j, i);
	    			
	    			// Obtenemos el contenido de la celda
	    			campo = celda.getContents();
	    			hayCampo = false;
	    			String nombreCampo = campo;
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (campo.equalsIgnoreCase(columna.getNombre()))
						{
							hayCampo = true;
							break;
						}
					}
					
					if (!nombreCampo.equals(""))
						res = res + nombreCampo + "=" + (hayCampo ? "true" : "false") + ",";
	    		}
	    		
	    		if (!res.equals(""))
	    			break;
	    	}
	    	
	    	workbook.close();
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }	        

	    transaccionForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    transaccionForm.setRespuesta(res);
	    transaccionForm.setBdStatusTabla(res);
	}
	
	private void VerificarExcel2010(HttpServletRequest request, TransaccionForm transaccionForm)
	{
	    String res = "";
	    String campo;
	    
	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = (FormFile) transaccionForm.getMultipartRequestHandler().getFileElements().get("fileName");
	    	transaccionForm.setFileForm(archivo);
	    	XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	XSSFSheet hssfSheet = workBook.getSheetAt(0);
	    	
	    	Iterator<Row> rowIterator = hssfSheet.rowIterator();
	    	boolean hayCampo = false;
	    	while (rowIterator.hasNext())
	    	{
	    		XSSFRow hssfRow = (XSSFRow) rowIterator.next();
		    	Iterator<org.apache.poi.ss.usermodel.Cell> iterator = hssfRow.cellIterator();
		    	while (iterator.hasNext())
		    	{
		    		XSSFCell hssfCell = (XSSFCell) iterator.next();

	    			// Obtenemos el contenido de la celda
	    			campo = hssfCell.toString();
	    			hayCampo = false;
	    			String nombreCampo = campo;
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (campo.equalsIgnoreCase(columna.getNombre()))
						{
							hayCampo = true;
							break;
						}
					}
					
					if (!nombreCampo.equals(""))
						res = res + nombreCampo + '=' + (hayCampo ? "true" : "false") + ",";
		    	}
		    	if (!res.equals(""))
					break;
	    	}
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }	        

	    transaccionForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    transaccionForm.setRespuesta(res);
	    transaccionForm.setBdStatusTabla(res);
	}
	
	private void ObtenerTablasBd(HttpServletRequest request, TransaccionForm transaccionForm)
	{
		String tablas = null;
		ActionMessages messages = getMessages(request);

	    Connection cn;
		String driver = null;
		String url = null;
		String sql = null;
		Boolean verificarTabla = Boolean.parseBoolean(request.getParameter("verificarTabla"));
		if (transaccionForm.getBdPassword() == null || transaccionForm.getBdPassword().equals("") || (transaccionForm.getBdPassword() != null && !transaccionForm.getBdPassword().equals(request.getParameter("password"))))
			transaccionForm.setBdPasswrod(request.getParameter("password"));
		
		if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue())
		{
			driver = "org.postgresql.Driver";
			url = "jdbc:postgresql://" + transaccionForm.getBdServidor() + ":" + transaccionForm.getBdPuerto() + "/" + transaccionForm.getBdNombre() + "?compatible=7.1";
			sql = "SELECT table_name AS TBNAME FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name";
		}
		else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue())
		{
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + transaccionForm.getBdServidor() + ":" + transaccionForm.getBdPuerto() + ":" + transaccionForm.getBdNombre();
			sql = "SELECT TABLE_NAME AS TBNAME FROM DBA_TABLES UNION SELECT VIEW_NAME AS TBNAME FROM USER_VIEWS ORDER BY TBNAME";
		}
		else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
		{
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			url ="jdbc:sqlserver://" + transaccionForm.getBdServidor() + ":" + transaccionForm.getBdPuerto() + ";databaseName=" + transaccionForm.getBdNombre() + ";user=" + transaccionForm.getBdUsuario() + ";password=" + transaccionForm.getBdPassword() + ";";
			sql = "SELECT name AS TBNAME FROM sysobjects WHERE (type = 'U' OR type = 'V') ORDER BY name";
		}
	    
		Boolean conexionExitosa = false;
		boolean hayCampo = false;
	    try 
	    {
	    	Class.forName(driver);
	    	cn = DriverManager.getConnection(url, transaccionForm.getBdUsuario(), transaccionForm.getBdPassword());
	    	conexionExitosa = true;
	    	Statement stm = cn.createStatement();

	    	ResultSet rs = stm.executeQuery(sql);
			
	    	tablas = "";
			while (rs.next()) 
			{
				if (tablas != null && !tablas.equals(""))
					tablas = tablas + "|";
				tablas = tablas + rs.getString("TBNAME");
			}	    	
			rs.close();

			if (verificarTabla)
			{
				if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue())
					sql = "SELECT column_name AS columnName FROM information_schema.columns WHERE UPPER(table_name) = upper('" + transaccionForm.getBdTabla().toUpperCase() + "') ORDER BY column_name";
				else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue())
					sql = "SELECT column_name AS columnName FROM all_tab_columns WHERE table_name = '" + transaccionForm.getBdTabla().toUpperCase() + "'";
				else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
					sql = "SELECT COLUMN_NAME AS columnName FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + transaccionForm.getBdTabla().toUpperCase() + "'";

				rs = stm.executeQuery(sql);
				
				while (rs.next()) 
				{
					String campo = rs.getString("columnName");
	    			hayCampo = false;
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (campo.equalsIgnoreCase(columna.getNombre()))
						{
							hayCampo = true;
							break;
						}
					}
				}	    	
				rs.close();
			}
			
			stm.close();
	    	cn.close();
	    	cn = null;
	    }
    	catch (Exception e2) 
    	{
    	}
	    
	    transaccionForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    if (!conexionExitosa)
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.archivo.seleccion.bd.servidor.noconected"));
		    saveMessages(request, messages);
	    }
	    else
	    {
	    	String respuesta = tablas;
	    	transaccionForm.setBdListaTablas(respuesta);
	    	transaccionForm.setStatus(ImportarStatus.getImportarStatusValidado());
		    if (verificarTabla)
		    {
			    if (hayCampo)
			    	transaccionForm.setBdStatusTabla("10000");
			    else
			    {
			    	transaccionForm.setBdStatusTabla("10001");
				    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.medicion.seleccion.seleccionar.verificar.tabla.invalido"));
				    saveMessages(request, messages);
			    }
		    	respuesta = transaccionForm.getBdListaTablas() + "," + transaccionForm.getBdStatusTabla();
		    }
		    transaccionForm.setRespuesta(respuesta);
	    }
	}
	
	private int Importar(HttpServletRequest request, TransaccionForm transaccionForm) throws Exception
	{
	    StringBuffer log = new StringBuffer();
	    int respuesta = 10000;
	    
	    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	    log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.titulocalculo") + "\n");

	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.fechainiciocalculo", argsReemplazo) + "\n\n");
	    
	    if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePlano().byteValue())
	    	respuesta = BuscarDatosTxt(request, log, messageResources, transaccionForm);
	    else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && transaccionForm.getExcelTipo().byteValue() == 0)
	    	respuesta = BuscarDatosExcel2003(request, log, messageResources, transaccionForm);
	    else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && transaccionForm.getExcelTipo().byteValue() == 1)
	    	respuesta = BuscarDatosExcel2010(request, log, messageResources, transaccionForm);
	    else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue()
	    		|| transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue()
	    		|| transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
	    	respuesta = BuscarDatosBd(request, log, messageResources, transaccionForm);

	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.fechafin.programada", argsReemplazo) + "\n\n");
	    log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.success.programada") + "\n\n");
	    
	    request.getSession().setAttribute("verArchivoLog", log);
	    
	    return respuesta;
	}
	
	private int BuscarDatosTxt(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, TransaccionForm transaccionForm) throws Exception
	{
	    int indice;
	    String campo = "";
	    Integer posicion = 0;
	    Integer numeroCampos = 0;
	    int respuesta = 10000;
		
	    FormFile archivo = transaccionForm.getFileForm();
    	ActionMessages messages = getMessages(request);
    	
	    BufferedReader entrada;
	    String res;
	    try 
	    {
	    	entrada = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
	    	String linea;
	    
	    	while(entrada.ready())
	    	{
	    		linea = entrada.readLine();
	    		indice = 0;
				posicion = 0;
				while (indice > -1)
				{
					indice = linea.indexOf(transaccionForm.getSeparador());
					if (indice != -1)
					{
						posicion++;
						campo = linea.substring(0, indice);
						linea = linea.substring(indice + 1, linea.length());
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							if (campo.equalsIgnoreCase(columna.getNombre()))
							{
								numeroCampos++;
								columna.setPosicionArchivo(posicion);
								break;
							}
						}
					}
					else if (linea.length() > 0)
					{
						posicion++;
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							if (campo.equalsIgnoreCase(columna.getNombre()))
							{
								numeroCampos++;
								columna.setPosicionArchivo(posicion);
								break;
							}
						}
					}
					
					if (numeroCampos.intValue() == transaccionForm.getTransaccion().getTabla().getColumnas().size())
						break;
				}
	    	}
	    	
	    	entrada.close();
	    	
	    	if (numeroCampos.intValue() == transaccionForm.getTransaccion().getTabla().getColumnas().size())
	    	{
	    		List<List<ObjetoClaveValor>> datos = new ArrayList<List<ObjetoClaveValor>>();
	    		List<ObjetoClaveValor> campos = new ArrayList<ObjetoClaveValor>();
		    
		    	entrada = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
		    	while(entrada.ready())
		    	{
		    		linea = entrada.readLine();
					posicion = 0;
		    		indice = 0;
		    		numeroCampos = 0;
					while (indice > -1)
					{
						indice = linea.indexOf(transaccionForm.getSeparador());
						numeroCampos = 0;
						if (indice != -1)
						{
							posicion++;
							campo = linea.substring(0, indice);
							linea = linea.substring(indice + 1, linea.length());

							for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
							{
								Columna columna = (Columna)col.next();
								if (posicion == columna.getPosicionArchivo() && !columna.getNombre().equalsIgnoreCase(campo))
								{
									numeroCampos++;
									columna.setValorArchivo(campo);
									break;
								}
							}
						}
						else if (linea.length() > 0)
						{
							posicion++;
							for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
							{
								Columna columna = (Columna)col.next();
								if (posicion == columna.getPosicionArchivo() && !columna.getNombre().equalsIgnoreCase(linea))
								{
									numeroCampos++;
									columna.setValorArchivo(linea);
									break;
								}
							}
						}
						
						if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
							break;
					}
					
					if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
					{
						campos = new ArrayList<ObjetoClaveValor>();
						ObjetoClaveValor c = null; 
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							c = new ObjetoClaveValor();
							c.setClave(columna.getNombre());
							c.setValor(columna.getValorArchivo());
							campos.add(c);
						}
						datos.add(campos);
					}
		    	}
		    	
		    	entrada.close();
		    	
				if (datos.size() > 0)
					respuesta = Importar(request, log, messageResources, datos, transaccionForm);
				else
				{
					res = "jsp.asistente.importacion.transaccion.fin.importar.archivo.empthy";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
				    saveMessages(request, messages);
				}
	    	}
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.error", argsReemplazo) + "\n\n");

	    	transaccionForm.setStatus(ImportarStatus.getImportarStatusImportado());
	    	transaccionForm.setRespuesta(res);
	    }
	    
	    if (transaccionForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	    
	    return respuesta;
	}

	private int BuscarDatosExcel2003(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, TransaccionForm transaccionForm) throws Exception
	{
	    String campo = "";
	    Integer posicion = 0;
	    Integer numeroCampos = 0;
	    int respuesta = 10000;
		
	    String res = "";
    	ActionMessages messages = getMessages(request);
    	
	    try 
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = transaccionForm.getFileForm();
	    	Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	Sheet sheet = workbook.getSheet(0);
	    	Cell celda = null;
	    	for ( int i=0, z=sheet.getRows(); i<z; i++ )
	    	{
	    		numeroCampos = 0;
	    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
	    		{
	    			celda = sheet.getCell(j, i);
	    			
	    			// Obtenemos el contenido de la celda
	    			campo = celda.getContents();
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (campo.equalsIgnoreCase(columna.getNombre()))
						{
							numeroCampos++;
							posicion = j + 1;
							columna.setPosicionArchivo(posicion);
							break;
						}
					}

					if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
						break;
	    		}

	    		if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
					break;
	    	}
	    	
	    	workbook.close();

	    	if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
	    	{
	    		List<List<ObjetoClaveValor>> datos = new ArrayList<List<ObjetoClaveValor>>();
	    		List<ObjetoClaveValor> campos = new ArrayList<ObjetoClaveValor>();

		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workbook = Workbook.getWorkbook(archivo.getInputStream());
		    	
		    	//Elegimos la primera hoja
		    	sheet = workbook.getSheet(0);
		    	celda = null;

		    	for ( int r=0, z=sheet.getRows(); r<z; r++ )
		    	{
		    		numeroCampos = 0;
		    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
		    		{
		    			celda = sheet.getCell(j, r);

		    			campo = celda.getContents();
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							if ((j + 1) == columna.getPosicionArchivo() && !columna.getNombre().equalsIgnoreCase(campo))
							{
								numeroCampos++;
								columna.setValorArchivo(getValue(celda));
								break;
							}
						}
		    			
						if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
							break;
					}
		    	
		    		if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
					{
						campos = new ArrayList<ObjetoClaveValor>();
						ObjetoClaveValor c = null; 
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							c = new ObjetoClaveValor();
							c.setClave(columna.getNombre());
							c.setValor(columna.getValorArchivo());
							campos.add(c);
						}
						datos.add(campos);
					}
		    	}
		    	
		    	workbook.close();
		    	
				if (datos.size() > 0)
					respuesta = Importar(request, log, messageResources, datos, transaccionForm);
				else
				{
					res = "jsp.asistente.importacion.transaccion.fin.importar.archivo.empthy";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
				    saveMessages(request, messages);
				}
	    	}
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.error", argsReemplazo) + "\n\n");

	    	transaccionForm.setStatus(ImportarStatus.getImportarStatusImportado());
	    	transaccionForm.setRespuesta(res);
	    }
	    
	    if (transaccionForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	    
	    return respuesta;
	}
	
	private int BuscarDatosExcel2010(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, TransaccionForm transaccionForm) throws Exception
	{
	    String campo = "";
	    int respuesta = 10000;
	    Integer numeroCampos = 0;
		
	    String res = "";
    	ActionMessages messages = getMessages(request);
    	
	    try 
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = transaccionForm.getFileForm();
	    	XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	XSSFSheet hssfSheet = workBook.getSheetAt(0);
	    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); ) 
            {
            	XSSFRow hssfRow = (XSSFRow)i.next();
            	numeroCampos = 0;
            	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
            	{
            		XSSFCell hssfCell = (XSSFCell) j.next();
            		campo = hssfCell.toString();
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (campo.equalsIgnoreCase(columna.getNombre()))
						{
							numeroCampos++;
							columna.setPosicionArchivo(hssfCell.getColumnIndex());
							break;
						}
					}

					if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
						break;
            		
            	}
            	
            	if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
					break;
            }
	    			
	    	if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
	    	{
	    		List<List<ObjetoClaveValor>> datos = new ArrayList<List<ObjetoClaveValor>>();
	    		List<ObjetoClaveValor> campos = new ArrayList<ObjetoClaveValor>();
		    
		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workBook = new XSSFWorkbook(archivo.getInputStream());
		    	
		    	//Elegimos la primera hoja
		    	hssfSheet = workBook.getSheetAt(0);
		    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); )
		    	{
		    		XSSFRow hssfRow = (XSSFRow)i.next();
		    		numeroCampos = 0;
			    	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
		    		{
			    		XSSFCell hssfCell = (XSSFCell) j.next();
			    		
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							if (hssfCell.getColumnIndex() == columna.getPosicionArchivo() && !columna.getNombre().equalsIgnoreCase(hssfCell.toString()))
							{
								numeroCampos++;
								columna.setValorArchivo(getValue(hssfCell));
								break;
							}
						}
		    			
						if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
							break;
					}
		    	
			    	if (numeroCampos == transaccionForm.getTransaccion().getTabla().getColumnas().size())
					{
						campos = new ArrayList<ObjetoClaveValor>();
						ObjetoClaveValor c = null; 
						for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
						{
							Columna columna = (Columna)col.next();
							c = new ObjetoClaveValor();
							c.setClave(columna.getNombre());
							c.setValor(columna.getValorArchivo());
							campos.add(c);
						}
						datos.add(campos);
					}
		    	}
		    	
				if (datos.size() > 0)
					respuesta = Importar(request, log, messageResources, datos, transaccionForm);
				else
				{
					res = "jsp.asistente.importacion.transaccion.fin.importar.archivo.empthy";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
				    saveMessages(request, messages);
				}
	    	}
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.error", argsReemplazo) + "\n\n");

	    	transaccionForm.setStatus(ImportarStatus.getImportarStatusImportado());
	    	transaccionForm.setRespuesta(res);
	    }
	    
	    if (transaccionForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	    
	    return respuesta;
	}
	
	private int BuscarDatosBd(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, TransaccionForm transaccionForm) throws Exception
	{
		ActionMessages messages = getMessages(request);

	    Connection cn;
		String driver = null;
		String url = null;
		String sql = null;
		int respuesta = 10000;
		
		if (transaccionForm.getBdPassword() == null || transaccionForm.getBdPassword().equals(""))
			transaccionForm.setBdPasswrod(request.getParameter("password"));
		
		if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue())
		{
			driver = "org.postgresql.Driver";
			url = "jdbc:postgresql://" + transaccionForm.getBdServidor() + ":" + transaccionForm.getBdPuerto() + "/" + transaccionForm.getBdNombre() + "?compatible=7.1";
		}
		else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue())
		{
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + transaccionForm.getBdServidor() + ":" + transaccionForm.getBdPuerto() + ":" + transaccionForm.getBdNombre();
		}
		else if (transaccionForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
		{
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			url ="jdbc:sqlserver://" + transaccionForm.getBdServidor() + ":" + transaccionForm.getBdPuerto() + ";databaseName=" + transaccionForm.getBdNombre() + ";user=" + transaccionForm.getBdUsuario() + ";password=" + transaccionForm.getBdPassword() + ";";
		}
	    
		Boolean conexionExitosa = false;
	    try 
	    {
	    	Class.forName(driver);
	    	cn = DriverManager.getConnection(url, transaccionForm.getBdUsuario(), transaccionForm.getBdPassword());
	    	conexionExitosa = true;
	    	Statement stm = cn.createStatement();

	    	sql = "SELECT COUNT(*) AS Rec_Count FROM " + transaccionForm.getBdTabla();
	    	
	    	ResultSet rs = stm.executeQuery(sql);
			
	    	int filas = 0;
			while (rs.next()) 
				filas = rs.getInt("Rec_Count");
			rs.close();

    		List<List<ObjetoClaveValor>> datos = null;
    		List<ObjetoClaveValor> campos = null;
			if (filas > 0)
			{
	    		datos = new ArrayList<List<ObjetoClaveValor>>();
	    		campos = new ArrayList<ObjetoClaveValor>();
	    	    
		    	sql = "SELECT codigo, ano, periodo, medicion FROM " + transaccionForm.getBdTabla();
		    	
		    	rs = stm.executeQuery(sql);

		    	filas = 0;
				while (rs.next()) 
				{
					campos = new ArrayList<ObjetoClaveValor>();
					ObjetoClaveValor c = null; 
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						c = new ObjetoClaveValor();
						c.setClave(columna.getNombre());
						c.setValor(rs.getString(columna.getNombre()));
						campos.add(c);
					}
					datos.add(campos);
				}	    	
				rs.close();
			}

			stm.close();
	    	cn.close();
	    	cn = null;
	    	
	    	if (datos != null)
	    		respuesta = Importar(request, log, messageResources, datos, transaccionForm);
	    	else
	    	{
				String res = "jsp.asistente.importacion.transaccion.fin.importar.archivo.empthy";
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
			    saveMessages(request, messages);
	    	}
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.transaccion.log.error", argsReemplazo) + "\n\n");

	    	transaccionForm.setStatus(ImportarStatus.getImportarStatusImportado());
	    	transaccionForm.setRespuesta("Error");
	    }

	    if (!conexionExitosa)
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.archivo.seleccion.bd.servidor.noconected"));
		    saveMessages(request, messages);
	    }
	    else if (transaccionForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	    
	    return respuesta;
	}

	private String getValue(XSSFCell hssfCell)
	{
		String value = "";
		
		if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING) 
			value = hssfCell.toString(); 
		else if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC || hssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) 
		{ 
			Double valor = hssfCell.getNumericCellValue();
			value = valor.toString(); 
		}
		
		return value;
	}
	
	private String getValue(Cell celda)
	{
		String value = "";
		
		if (celda.getType() == CellType.LABEL) 
		{ 
			LabelCell lc = (LabelCell) celda; 
			value = lc.getString(); 
		}
		else if (celda.getType() == CellType.NUMBER || celda.getType() == CellType.NUMBER_FORMULA) 
		{ 
			NumberCell nc = (NumberCell) celda;
			Double valor = nc.getValue();
			value = valor.toString(); 
		}
		else if (celda.getType() == CellType.DATE) 
		{ 
			DateCell dc = (DateCell) celda;
			Date valor = dc.getDate();
			value = valor.toString(); 
		} 		
		
		return value;
	}
	
	private int Importar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, List<List<ObjetoClaveValor>> datos, TransaccionForm transaccionForm) throws Exception
	{
    	ActionMessages messages = getMessages(request);
    	int respuesta = 10000;
		if (datos.size() == 0)
		{
			String res = "jsp.asistente.importacion.transaccion.fin.importar.archivo.empthy";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
		    saveMessages(request, messages);
		}
		else
		{
			Transaccion transaccion = transaccionForm.getTransaccion();
			if (transaccionForm.getIndicadorTransaccionesId() != null && transaccionForm.getIndicadorTransaccionesId() != 0L)
				transaccion.setIndicadorId(transaccionForm.getIndicadorTransaccionesId());
			if (transaccionForm.getIndicadorMontoId() != null && transaccionForm.getIndicadorMontoId() != 0L)
			{
				for (Iterator<Columna> col = transaccion.getTabla().getColumnas().iterator(); col.hasNext(); )
				{
					Columna columna = (Columna)col.next();
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
					{
						columna.setIndicadorId(transaccionForm.getIndicadorMontoId());
						break;
					}
				}
			}
			transaccion.setConfiguracion(transaccion.getXml());
			Usuario usuario = getUsuarioConectado(request);
			TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
			respuesta = transaccionService.saveTransaccion(transaccion, usuario);
			transaccionService.close();

			if (respuesta == 10000)
			{
				ServicioForm servicio = new ServicioForm();
				
				FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
				Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
				frameworkService.close();
				
				if (configuracion == null)
				{
					transaccionForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
					saveMessages(request, messages);
				}
				else
				{
					//XML
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			        DocumentBuilder db = dbf.newDocumentBuilder(); 
			        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8"))); 
			        doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("properties");
					Element eElement = (Element) nList.item(0);
					/** Se obtiene el FormBean haciendo el casting respectivo */
					String url = VgcAbstractService.getTagValue("url", eElement);;
					String driver = VgcAbstractService.getTagValue("driver", eElement);
					String user = VgcAbstractService.getTagValue("user", eElement);
					String password = VgcAbstractService.getTagValue("password", eElement);
					password = new com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm().getPasswordConexionDecriptado(password);

					if (!new FrameworkConnection().testConnection(url, driver, user, password))
					{
						transaccionForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
						saveMessages(request, messages);
					}
					else
					{
						servicio.setProperty("url", url);
						servicio.setProperty("driver", driver);
						servicio.setProperty("user", user);
						servicio.setProperty("password", password);
						servicio.setProperty("tipoFuente", ((Integer)(!request.getParameter("tipoFuente").toString().equals("") ? Integer.parseInt(request.getParameter("tipoFuente").toString()) : 0)).toString());
						servicio.setProperty("separador", request.getParameter("separador").toString());
						servicio.setProperty("logMediciones", ((Boolean)(request.getParameter("logMediciones") != null ? (request.getParameter("logMediciones").toString().equals("1") ? true : false) : false)).toString());
						servicio.setProperty("logErrores", ((Boolean)(request.getParameter("logErrores") != null ? (request.getParameter("logErrores").toString().equals("1") ? true : false) : false)).toString());
						servicio.setProperty("logConsolaMetodos", ((Boolean)(false)).toString());
						servicio.setProperty("logConsolaDetallado", ((Boolean)(false)).toString());
						servicio.setProperty("numeroEjecucion", ((Integer)(1)).toString());
						servicio.setProperty("usuarioId", usuario.getUsuarioId().toString());
						servicio.setProperty("calcular", ((Boolean)(true)).toString());
						
						StringBuffer logBefore = log;
						respuesta = new com.visiongc.servicio.strategos.importar.ImportarManager(servicio.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(datos, transaccion);

						if (respuesta == 10000)
						{
							log = logBefore;
						    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.archivo.configuracion.alerta.fin"));
						    saveMessages(request, messages);

							transaccionForm.setStatus(ImportarStatus.getImportarStatusImportado());
							transaccionForm.setRespuesta("Successful");
						}
						else
						{
						    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.transaccion.archivo.configuracion.alerta.nosuccess"));
						    saveMessages(request, messages);

						    transaccionForm.setStatus(ImportarStatus.getImportarStatusNoSuccess());
						}
					}
				}
			}
		}
		
		return respuesta;
	}
}