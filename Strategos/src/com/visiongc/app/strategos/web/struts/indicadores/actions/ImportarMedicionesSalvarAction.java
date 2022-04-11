/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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

import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm.CalcularStatus;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm.ImportarStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.importacion.ImportacionService;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Importacion;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.actions.LogonAction;
import com.visiongc.servicio.strategos.servicio.model.Servicio;

/**
 * @author Kerwin
 *
 */
public class ImportarMedicionesSalvarAction extends VgcAction
{
    private boolean hayCodigo = false;
    private boolean hayAno = false;
    private boolean hayPeriodo = false;
    private boolean hayMedicion = false;

    public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		/** Se obtiene el FormBean haciendo el casting respectivo */
		ImportarMedicionesForm importarMedicionesForm = (ImportarMedicionesForm) form;

		if (request.getParameter("funcion") != null)
		{
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("verificar")) 
	    	{
	    	    hayCodigo = false;
	    	    hayAno = false;
	    	    hayPeriodo = false;
	    	    hayMedicion = false;
	    	    
	    		Verificar(request, importarMedicionesForm);
	    		
	    		return mapping.findForward(forward);
	    	}
	    	else if (funcion.equals("salvar"))
	    	{
	    		int respuesta = Salvar(request, importarMedicionesForm);
	    		if (respuesta == 10000)
	    			importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusSalvado());
	    		else
	    			importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusNoSalvado());
	    		
	    		return mapping.findForward(forward);
	    	}
	    	else if (funcion.equals("eliminar"))
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		int respuesta = Eliminar(id, request);

	    		request.setAttribute("ajaxResponse", respuesta);
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	else if (funcion.equals("read"))
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
	    	    Importacion importacion = (Importacion)importarService.load(Importacion.class, new Long(id));
	    	    importarService.close();
	    	    
	    	    if (importacion != null)
	    	    	request.setAttribute("ajaxResponse", importacion.getId().toString() + "|" + importacion.getNombre());
	    	    else
	    	    	request.setAttribute("ajaxResponse", "0");
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	else if (funcion.equals("readFull"))
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
	    	    Importacion importacion = (Importacion)importarService.load(Importacion.class, new Long(id));
	    	    importarService.close();
	    	    
	    	    importarMedicionesForm.setId(importacion.getId());
	    	    importarMedicionesForm.setNombre(importacion.getNombre());
	    	    importarMedicionesForm.setPlanSeleccion(importacion.getNombre());
	    	    importarMedicionesForm.setTipoFuente(importacion.getTipo());
	    	    importarMedicionesForm.setXml(importacion.getConfiguracion());
	    	    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusReadSuccess());
	    	    
	    	    return mapping.findForward(forward);
	    	}
	    	else if (funcion.equals("importar")) 
	    	{
	    		String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
	    		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	    		ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
				ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
				pk.setConfiguracionBase("Strategos.Wizar.Importar.ShowPresentacion");
				pk.setObjeto("ShowPresentacion");
				pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
				presentacion.setPk(pk);
				presentacion.setData(showPresentacion);
				frameworkService.saveConfiguracionUsuario(presentacion, this.getUsuarioConectado(request));
				frameworkService.close();
	    		
	    		Importar(request, importarMedicionesForm);
	    		
	    		return mapping.findForward(forward);
	    	}
		}

		return mapping.findForward("ajaxResponse");
	}

	private void Importar(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
	    StringBuffer log = new StringBuffer();
	    
	    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	    log.append(messageResources.getResource("jsp.asistente.importacion.log.titulocalculo") + "\n");

	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.importacion.log.fechainiciocalculo", argsReemplazo) + "\n\n");
	    
	    if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePlano().byteValue())
	    	BuscarDatosTxt(request, log, messageResources, importarMedicionesForm);
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarMedicionesForm.getExcelTipo().byteValue() == 0)
	    	BuscarDatosExcel2003(request, log, messageResources, importarMedicionesForm);
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarMedicionesForm.getExcelTipo().byteValue() == 1)
	    	BuscarDatosExcel2010(request, log, messageResources, importarMedicionesForm);
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue()
	    		|| importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue()
	    		|| importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
	    	BuscarDatosBd(request, log, messageResources, importarMedicionesForm);

	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.importacion.log.fechafin.programada", argsReemplazo) + "\n\n");
	    
	    request.getSession().setAttribute("verArchivoLog", log);
	}
	
	
	private void BuscarDatosTxt(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
	    int indice;
	    String campo = "";
	    int posicionCodigo = 0;
	    int posicionAno = 0;
	    int posicionPeriodo = 0;
	    int posicionMedicion = 0;
	    int posicion = 0;
	    int filas = 0;
		
	    FormFile archivo = importarMedicionesForm.getFileForm();
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
	    		filas++;
	    		indice = 0;
				posicion = 0;
				while (indice > -1)
				{
					indice = linea.indexOf(importarMedicionesForm.getSeparador());
					if (indice != -1)
					{
						posicion++;
						campo = linea.substring(0, indice);
						linea = linea.substring(indice + 1, linea.length());
						if (posicionCodigo == 0 && campo.equalsIgnoreCase("Codigo"))
							posicionCodigo = posicion; 
						else if (posicionAno == 0 && campo.equalsIgnoreCase("Ano"))
							posicionAno = posicion;
						else if (posicionPeriodo == 0 && campo.equalsIgnoreCase("Periodo"))
							posicionPeriodo = posicion;
						else if (posicionMedicion == 0 && campo.equalsIgnoreCase("Medicion"))
							posicionMedicion = posicion;
					}
					else if (linea.length() > 0)
					{
						posicion++;
						if (posicionCodigo == 0 && linea.equalsIgnoreCase("Codigo"))
							posicionCodigo = posicion; 
						else if (posicionAno == 0 && linea.equalsIgnoreCase("Ano"))
							posicionAno = posicion;
						else if (posicionPeriodo == 0 && linea.equalsIgnoreCase("Periodo"))
							posicionPeriodo = posicion;
						else if (posicionMedicion == 0 && linea.equalsIgnoreCase("Medicion"))
							posicionMedicion = posicion;
					}
					
					if (posicionCodigo != 0 && posicionAno != 0 && posicionPeriodo != 0 && posicionMedicion != 0)
						break;
				}
	    	}
	    	
	    	entrada.close();
	    	
	    	if (posicionCodigo != 0 && posicionAno != 0 && posicionPeriodo != 0 && posicionMedicion != 0)
	    	{
	    		String[][] datos = new String[filas][4];
		    
		    	entrada = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
		    	String codigoArchivo = "";
		    	String anoArchivo = "";
		    	String periodoArchivo = "";
		    	String medicionArchivo = "";
		    	filas = 0;
		    	while(entrada.ready())
		    	{
			    	codigoArchivo = "";
			    	anoArchivo = "";
			    	periodoArchivo = "";
			    	medicionArchivo = ""; 
		    		linea = entrada.readLine();
					posicion = 0;
		    		indice = 0;
					while (indice > -1)
					{
						indice = linea.indexOf(importarMedicionesForm.getSeparador());
						if (indice != -1)
						{
							posicion++;
							campo = linea.substring(0, indice);
							linea = linea.substring(indice + 1, linea.length());
							if (posicion == posicionCodigo)
								codigoArchivo = campo; 
							else if (posicion == posicionAno)
								anoArchivo = campo;
							else if (posicion == posicionPeriodo)
								periodoArchivo = campo;
							else if (posicion == posicionMedicion)
								medicionArchivo = campo;
						}
						else if (linea.length() > 0)
						{
							posicion++;
							if (posicion == posicionCodigo)
								codigoArchivo = linea; 
							else if (posicion == posicionAno)
								anoArchivo = linea;
							else if (posicion == posicionPeriodo)
								periodoArchivo = linea;
							else if (posicion == posicionMedicion)
								medicionArchivo = linea;
						}
						
						if (!codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
							break;
					}
					
					if (!codigoArchivo.equalsIgnoreCase("Codigo") && !codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
					{
						datos[filas][0] = codigoArchivo;
						datos[filas][1] = anoArchivo;
						datos[filas][2] = periodoArchivo;
						datos[filas][3] = medicionArchivo;
			    		filas++;
					}
		    	}
		    	
		    	entrada.close();
		    	
				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarMedicionesForm);
				else
				{
					res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
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

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusImportado());
		    importarMedicionesForm.setRespuesta(res);
	    }
	    
	    if (importarMedicionesForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}

	private void BuscarDatosExcel2003(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
	    String campo = "";
	    int posicionCodigo = 0;
	    int posicionAno = 0;
	    int posicionPeriodo = 0;
	    int posicionMedicion = 0;
	    int filas = 0;
		
	    String res = "";
    	ActionMessages messages = getMessages(request);
    	
	    try 
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = importarMedicionesForm.getFileForm();
	    	Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	Sheet sheet = workbook.getSheet(0);
	    	Cell celda = null;
	    	filas = sheet.getRows();
	    	for ( int i=0, z=sheet.getRows(); i<z; i++ )
	    	{
	    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
	    		{
	    			celda = sheet.getCell(j, i);
	    			
	    			// Obtenemos el contenido de la celda
	    			campo = celda.getContents();
					if (campo.equalsIgnoreCase("Codigo"))
						posicionCodigo = j + 1; 
					else if (campo.equalsIgnoreCase("Ano"))
						posicionAno = j + 1;
					else if (campo.equalsIgnoreCase("Periodo"))
						posicionPeriodo = j + 1;
					else if (campo.equalsIgnoreCase("Medicion"))
						posicionMedicion = j + 1;

					if (posicionCodigo != 0 && posicionAno != 0 && posicionPeriodo != 0 && posicionMedicion != 0)
						break;
	    		}

	    		if (posicionCodigo != 0 && posicionAno != 0 && posicionPeriodo != 0 && posicionMedicion != 0)
					break;
	    	}
	    	
	    	workbook.close();

	    	if (posicionCodigo != 0 && posicionAno != 0 && posicionPeriodo != 0 && posicionMedicion != 0)
	    	{
	    		String[][] datos = new String[filas][4];
		    
		    	String codigoArchivo = "";
		    	String anoArchivo = "";
		    	String periodoArchivo = "";
		    	String medicionArchivo = "";

		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workbook = Workbook.getWorkbook(archivo.getInputStream());
		    	
		    	//Elegimos la primera hoja
		    	sheet = workbook.getSheet(0);
		    	celda = null;

		    	filas = 0;
		    	for ( int r=0, z=sheet.getRows(); r<z; r++ )
		    	{
			    	codigoArchivo = "";
			    	anoArchivo = "";
			    	periodoArchivo = "";
			    	medicionArchivo = ""; 
		    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
		    		{
		    			celda = sheet.getCell(j, r);

		    			campo = celda.getContents();
						if ((j + 1) == posicionCodigo)
							codigoArchivo = campo; 
						else if ((j + 1) == posicionAno)
							anoArchivo = campo;
						else if ((j + 1) == posicionPeriodo)
							periodoArchivo = campo;
						else if ((j + 1) == posicionMedicion)
							medicionArchivo = getValue(celda);
		    			
						if (!codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
							break;
					}
		    	
					if (!codigoArchivo.equalsIgnoreCase("Codigo") && !codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
					{
						datos[filas][0] = codigoArchivo;
						datos[filas][1] = anoArchivo;
						datos[filas][2] = periodoArchivo;
						datos[filas][3] = medicionArchivo;
						filas++;
					}
		    	}
		    	
		    	workbook.close();
		    	
				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarMedicionesForm);
				else
				{
					res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
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

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusImportado());
		    importarMedicionesForm.setRespuesta(res);
	    }
	    
	    if (importarMedicionesForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}
	
	private void BuscarDatosExcel2010(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
	    String campo = "";
	    Integer posicionCodigo = null;
	    Integer posicionAno = null;
	    Integer posicionPeriodo = null;
	    Integer posicionMedicion = null;
	    Integer filas = 0;
	    Integer filaError = null;
	    Integer columnaError = null;
		
	    String res = "";
    	ActionMessages messages = getMessages(request);
    	
	    try 
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = importarMedicionesForm.getFileForm();
	    	XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	XSSFSheet hssfSheet = workBook.getSheetAt(0);
	    	filas = hssfSheet.getPhysicalNumberOfRows();
	    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); ) 
            {
            	XSSFRow hssfRow = (XSSFRow)i.next();
            	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
            	{
            		XSSFCell hssfCell = (XSSFCell) j.next();
            		campo = hssfCell.toString();
					if (campo.equalsIgnoreCase("Codigo"))
						posicionCodigo = hssfCell.getColumnIndex(); 
					else if (campo.equalsIgnoreCase("Ano"))
						posicionAno = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("Periodo"))
						posicionPeriodo = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("Medicion"))
						posicionMedicion = hssfCell.getColumnIndex();

					if (posicionCodigo != null && posicionAno != null && posicionPeriodo != null && posicionMedicion != null)
						break;
            		
            	}
            	if (posicionCodigo != null && posicionAno != null && posicionPeriodo != null && posicionMedicion != null)
					break;
            }
	    			
	    	if (posicionCodigo != null && posicionAno != null && posicionPeriodo != null && posicionMedicion != null)
	    	{
	    		String[][] datos = new String[filas][4];
		    
		    	String codigoArchivo = "";
		    	String anoArchivo = "";
		    	String periodoArchivo = "";
		    	String medicionArchivo = "";

		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workBook = new XSSFWorkbook(archivo.getInputStream());
		    	
		    	//Elegimos la primera hoja
		    	hssfSheet = workBook.getSheetAt(0);
		    	int fila = 0;
		    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); )
		    	{
		    		XSSFRow hssfRow = (XSSFRow)i.next();
			    	codigoArchivo = "";
			    	anoArchivo = "";
			    	periodoArchivo = "";
			    	medicionArchivo = "";
			    	filaError = hssfRow.getRowNum();
			    	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
		    		{
			    		XSSFCell hssfCell = (XSSFCell) j.next();
			    		columnaError = hssfCell.getColumnIndex();
						if (hssfCell.getColumnIndex() == posicionCodigo)
							codigoArchivo = hssfCell.toString(); 
						else if (hssfCell.getColumnIndex() == posicionAno)
						{
							if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING)
								anoArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
								anoArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
						else if (hssfCell.getColumnIndex() == posicionPeriodo)
						{
							if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING)
								periodoArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
								periodoArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
						else if (hssfCell.getColumnIndex() == posicionMedicion)
							medicionArchivo = getValue(hssfCell);
		    			
						if (!codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
							break;
					}
		    	
					if (!codigoArchivo.equalsIgnoreCase("Codigo") && !codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
					{
						datos[fila][0] = codigoArchivo;
						datos[fila][1] = anoArchivo;
						datos[fila][2] = periodoArchivo;
						datos[fila][3] = medicionArchivo;
						fila++;
					}
		    	}
		    	
				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarMedicionesForm);
				else
				{
					res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
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

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusImportado());
		    importarMedicionesForm.setRespuesta(res);
	    }
	    catch (Throwable e)
	    {
	    	e.printStackTrace();
	    	filaError++;
	    	columnaError++;
	    	res = "FileError|" + filaError.toString() + "|" + columnaError.toString();

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusFileError());
		    importarMedicionesForm.setRespuesta(res);
	    }
	    
	    if (importarMedicionesForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}
	
	private void BuscarDatosBd(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
		ActionMessages messages = getMessages(request);

	    Connection cn;
		String driver = null;
		String url = null;
		String sql = null;
		if (importarMedicionesForm.getBdPassword() == null || importarMedicionesForm.getBdPassword().equals(""))
			importarMedicionesForm.setBdPasswrod(request.getParameter("password"));
		
		if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue())
		{
			driver = "org.postgresql.Driver";
			url = "jdbc:postgresql://" + importarMedicionesForm.getBdServidor() + ":" + importarMedicionesForm.getBdPuerto() + "/" + importarMedicionesForm.getBdNombre() + "?compatible=7.1";
		}
		else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue())
		{
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + importarMedicionesForm.getBdServidor() + ":" + importarMedicionesForm.getBdPuerto() + ":" + importarMedicionesForm.getBdNombre();
		}
		else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
		{
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			url ="jdbc:sqlserver://" + importarMedicionesForm.getBdServidor() + ":" + importarMedicionesForm.getBdPuerto() + ";databaseName=" + importarMedicionesForm.getBdNombre() + ";user=" + importarMedicionesForm.getBdUsuario() + ";password=" + importarMedicionesForm.getBdPassword() + ";";
		}
	    
		Boolean conexionExitosa = false;
	    try 
	    {
	    	Class.forName(driver);
	    	cn = DriverManager.getConnection(url, importarMedicionesForm.getBdUsuario(), importarMedicionesForm.getBdPassword());
	    	conexionExitosa = true;
	    	Statement stm = cn.createStatement();

	    	sql = "SELECT COUNT(*) AS Rec_Count FROM " + importarMedicionesForm.getBdTabla();
	    	
	    	ResultSet rs = stm.executeQuery(sql);
			
	    	int filas = 0;
			while (rs.next()) 
				filas = rs.getInt("Rec_Count");
			rs.close();

			String[][] datos = null;
			if (filas > 0)
			{
	    		datos = new String[filas][4];
	    	    
		    	String codigoArchivo = "";
		    	String anoArchivo = "";
		    	String periodoArchivo = "";
		    	String medicionArchivo = "";
		    	
		    	sql = "SELECT codigo, ano, periodo, medicion FROM " + importarMedicionesForm.getBdTabla();
		    	
		    	rs = stm.executeQuery(sql);

		    	filas = 0;
				while (rs.next()) 
				{
					codigoArchivo = rs.getString("codigo");
					anoArchivo = rs.getString("ano");
					periodoArchivo = rs.getString("periodo");
					medicionArchivo = rs.getString("medicion");
					
					if (!codigoArchivo.equalsIgnoreCase("Codigo") && !codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("") && !medicionArchivo.equals(""))
					{
						datos[filas][0] = codigoArchivo;
						datos[filas][1] = anoArchivo;
						datos[filas][2] = periodoArchivo;
						datos[filas][3] = medicionArchivo;
						filas++;
					}
				}	    	
				rs.close();
			}

			stm.close();
	    	cn.close();
	    	cn = null;
	    	
	    	if (datos != null)
	    		Importar(request, log, messageResources, datos, importarMedicionesForm);
	    	else
	    	{
				String res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
			    saveMessages(request, messages);
	    	}
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusImportado());
		    importarMedicionesForm.setRespuesta("Error");
	    }

	    if (!conexionExitosa)
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.archivo.seleccion.bd.servidor.noconected"));
		    saveMessages(request, messages);
	    }
	    else if (importarMedicionesForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}

	private String getValue(XSSFCell hssfCell)
	{
		String value = "";
		
		if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING) 
			value = hssfCell.toString(); 
		else if (hssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC || hssfCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) 
		{ 
			Double valor = null;
			//try
			//{
				valor = hssfCell.getNumericCellValue();
			//}
			//catch (Throwable t)
			//{
				//return null;
			//}
			if (valor != null)
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

	private void Importar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, String[][] datos, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
    	ActionMessages messages = getMessages(request);
		if (datos.length == 0)
		{
			String res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
		    saveMessages(request, messages);
		}
		else
		{
			ServicioForm servicioForm = new ServicioForm();
			
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
			
			if (configuracion == null)
			{
				importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
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
					importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
					saveMessages(request, messages);
				}
				else
				{
					byte tipoMedicion = request.getParameter("tipoMedicion") != null ? Byte.parseByte(request.getParameter("tipoMedicion").toString()) : 0;
					Usuario usuario = getUsuarioConectado(request);
					Boolean todosOrganizacion = request.getParameter("todosOrganizacion") != null ? (request.getParameter("todosOrganizacion").toString().equals("1") ? true : false) : false; 
					servicioForm.setProperty("url", url);
					servicioForm.setProperty("driver", driver);
					servicioForm.setProperty("user", user);
					servicioForm.setProperty("password", password);
					servicioForm.setProperty("tipoFuente", ((Integer)(!request.getParameter("tipoFuente").toString().equals("") ? Integer.parseInt(request.getParameter("tipoFuente").toString()) : 0)).toString());
					servicioForm.setProperty("separador", request.getParameter("separador").toString());
					servicioForm.setProperty("logMediciones", ((Boolean)(request.getParameter("logMediciones") != null ? (request.getParameter("logMediciones").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("logErrores", ((Boolean)(request.getParameter("logErrores") != null ? (request.getParameter("logErrores").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("todosPlanes", ((Boolean)(request.getParameter("todosPlanes") != null ? (request.getParameter("todosPlanes").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("todosOrganizacion", todosOrganizacion.toString());
					servicioForm.setProperty("tipoMedicion", ((Byte)(tipoMedicion)).toString());
					servicioForm.setProperty("tipoImportacion", ((Byte)(request.getParameter("tipoImportacion") != null ? Byte.parseByte(request.getParameter("tipoImportacion").toString()) : 0)).toString());
					servicioForm.setProperty("calcular", ((Boolean)(request.getParameter("calcularMediciones") != null ? (request.getParameter("calcularMediciones").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("logConsolaMetodos", ((Boolean)(false)).toString());
					servicioForm.setProperty("logConsolaDetallado", ((Boolean)(false)).toString());
					servicioForm.setProperty("tomarPeriodosSinMedicionConValorCero", ((Boolean)(false)).toString());
					servicioForm.setProperty("activarSheduler", ((Boolean)(true)).toString());
					servicioForm.setProperty("unidadTiempo", ((Integer)(3)).toString());
					servicioForm.setProperty("numeroEjecucion", ((Integer)(1)).toString());
					servicioForm.setProperty("planId", (request.getParameter("funcion") != null ? request.getParameter("planId").toString() : ""));
					servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
					if (!todosOrganizacion)
						servicioForm.setProperty("organizacionId", (String)request.getSession().getAttribute("organizacionId"));
					
					StringBuffer logBefore = log;
					boolean respuesta = new com.visiongc.servicio.strategos.importar.ImportarManager(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(datos);
					log = logBefore;
					String res = "";
					if (tipoMedicion == 0 && !respuesta)
					{
						res = "action.guardarmediciones.mensaje.guardarmediciones.related";
					    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
					    saveMessages(request, messages);
					}
					else if (tipoMedicion == 1 && !respuesta)
					{
						res = "action.guardarmetas.mensaje.guardarmetas.relacionados";
					    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
					    saveMessages(request, messages);
					}
					else
					{
						res = "Successful";
					    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.archivo.configuracion.alerta.fin"));
					    saveMessages(request, messages);
					    
						if (usuario != null)
						{
							Calendar ahora = Calendar.getInstance();
							byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoImportar();
							Servicio servicio = new Servicio();  
							servicio.setUsuarioId(usuario.getUsuarioId());
							servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " " + VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
							servicio.setNombre(messageResources.getResource("jsp.asistente.importacion.titulocalculo"));
							
							String[] argsReemplazo = new String[2];
						    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
						    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

							servicio.setMensaje(messageResources.getResource("jsp.asistente.importacion.log.fechafin.programada", argsReemplazo));
							servicio.setEstatus(CalcularStatus.getCalcularStatusCalculado());
							
							frameworkService.registrarAuditoriaEvento((Object) servicio, usuario, tipoEvento);
						}
					}

					importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusImportado());
					importarMedicionesForm.setRespuesta(res);
				}
			}
			
			frameworkService.close();
		}
	}
	
	private void Verificar(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm)
	{
	    if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePlano().byteValue())
	    	VerificarTxt(request, importarMedicionesForm);
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarMedicionesForm.getExcelTipo().byteValue() == 0)
	    	VerificarExcel2007(request, importarMedicionesForm);
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarMedicionesForm.getExcelTipo().byteValue() == 1)
	    	VerificarExcel2010(request, importarMedicionesForm);
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue()
	    		|| importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue()
	    		|| importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
	    	ObtenerTablasBd(request, importarMedicionesForm);
	}
	
	private void ObtenerTablasBd(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm)
	{
		String tablas = null;
		ActionMessages messages = getMessages(request);

	    Connection cn;
		String driver = null;
		String url = null;
		String sql = null;
		Boolean verificarTabla = Boolean.parseBoolean(request.getParameter("verificarTabla"));
		if (importarMedicionesForm.getBdPassword() == null || importarMedicionesForm.getBdPassword().equals("") || (importarMedicionesForm.getBdPassword() != null && !importarMedicionesForm.getBdPassword().equals(request.getParameter("password"))))
			importarMedicionesForm.setBdPasswrod(request.getParameter("password"));
		
		if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue())
		{
			driver = "org.postgresql.Driver";
			url = "jdbc:postgresql://" + importarMedicionesForm.getBdServidor() + ":" + importarMedicionesForm.getBdPuerto() + "/" + importarMedicionesForm.getBdNombre() + "?compatible=7.1";
			sql = "SELECT table_name AS TBNAME FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name";
		}
		else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue())
		{
			driver = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + importarMedicionesForm.getBdServidor() + ":" + importarMedicionesForm.getBdPuerto() + ":" + importarMedicionesForm.getBdNombre();
			sql = "SELECT TABLE_NAME AS TBNAME FROM DBA_TABLES UNION SELECT VIEW_NAME AS TBNAME FROM USER_VIEWS ORDER BY TBNAME";
		}
		else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
		{
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			url ="jdbc:sqlserver://" + importarMedicionesForm.getBdServidor() + ":" + importarMedicionesForm.getBdPuerto() + ";databaseName=" + importarMedicionesForm.getBdNombre() + ";user=" + importarMedicionesForm.getBdUsuario() + ";password=" + importarMedicionesForm.getBdPassword() + ";";
			sql = "SELECT name AS TBNAME FROM sysobjects WHERE (type = 'U' OR type = 'V') ORDER BY name";
		}
	    
		Boolean conexionExitosa = false;
	    try 
	    {
	    	Class.forName(driver);
	    	cn = DriverManager.getConnection(url, importarMedicionesForm.getBdUsuario(), importarMedicionesForm.getBdPassword());
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
				if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue())
					sql = "SELECT column_name AS columnName FROM information_schema.columns WHERE UPPER(table_name) = upper('" + importarMedicionesForm.getBdTabla().toUpperCase() + "') ORDER BY column_name";
				else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue())
					sql = "SELECT column_name AS columnName FROM all_tab_columns WHERE table_name = '" + importarMedicionesForm.getBdTabla().toUpperCase() + "'";
				else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
					sql = "SELECT COLUMN_NAME AS columnName FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + importarMedicionesForm.getBdTabla().toUpperCase() + "'";

				rs = stm.executeQuery(sql);
				
				while (rs.next()) 
				{
					String campo = rs.getString("columnName");
					if (!hayCodigo && campo.equalsIgnoreCase("Codigo"))
						hayCodigo = true;
					else if (!hayAno && campo.equalsIgnoreCase("Ano"))
						hayAno = true;
					else if (!hayPeriodo && campo.equalsIgnoreCase("Periodo"))
						hayPeriodo = true;
					else if (!hayMedicion && campo.equalsIgnoreCase("Medicion"))
						hayMedicion = true;
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
	    
	    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    if (!conexionExitosa)
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.archivo.seleccion.bd.servidor.noconected"));
		    saveMessages(request, messages);
	    }
	    else
	    {
	    	String respuesta = tablas;
	    	importarMedicionesForm.setBdListaTablas(respuesta);
		    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusValidado());
		    if (verificarTabla)
		    {
			    if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
			    	importarMedicionesForm.setBdStatusTabla("10000");
			    else
			    {
			    	importarMedicionesForm.setBdStatusTabla("10001");
				    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.medicion.seleccion.seleccionar.verificar.tabla.invalido"));
				    saveMessages(request, messages);
			    }
		    	respuesta = importarMedicionesForm.getBdListaTablas() + "," + importarMedicionesForm.getBdStatusTabla();
		    }
		    importarMedicionesForm.setRespuesta(respuesta);
	    }
	}
	
	private void VerificarTxt(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm)
	{
	    String separador = importarMedicionesForm.getSeparador();
	    int indice;
	    String campo = "";
	    BufferedReader entrada;

	    FormFile archivo = (FormFile) importarMedicionesForm.getMultipartRequestHandler().getFileElements().get("file_browse");
	    importarMedicionesForm.setFileForm(archivo);
	    
	    String res;
	    try 
	    {
	    	entrada = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
	    	String linea;

	    	while(entrada.ready()) 	    
	    	{
	    		linea = entrada.readLine();
	    		indice = 0;
				while (indice > -1)
				{
					indice = linea.indexOf(separador);
					if (indice != -1)
					{
						campo = linea.substring(0, indice);
						linea = linea.substring(indice + 1, linea.length());
						if (!hayCodigo && campo.equalsIgnoreCase("Codigo"))
							hayCodigo = true;
						else if (!hayAno && campo.equalsIgnoreCase("Ano"))
							hayAno = true;
						else if (!hayPeriodo && campo.equalsIgnoreCase("Periodo"))
							hayPeriodo = true;
						else if (!hayMedicion && campo.equalsIgnoreCase("Medicion"))
							hayMedicion = true;
					}
					else if (linea.length() > 0)
					{
						if (!hayCodigo && linea.equalsIgnoreCase("Codigo"))
							hayCodigo = true;
						else if (!hayAno && linea.equalsIgnoreCase("Ano"))
							hayAno = true;
						else if (!hayPeriodo && linea.equalsIgnoreCase("Periodo"))
							hayPeriodo = true;
						else if (!hayMedicion && linea.equalsIgnoreCase("Medicion"))
							hayMedicion = true;
					}
					
					if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
						break;
				}
				
				if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
					break;
	    	}
	    	
	    	entrada.close();
	    	
	    	res = "Codigo=" + (hayCodigo ? "true" : "false") + ",";
		    res = res + "Ano=" + (hayAno ? "true" : "false") + ",";
		    res = res + "Periodo=" + (hayPeriodo ? "true" : "false") + ",";
		    res = res + "Medicion=" + (hayMedicion ? "true" : "false");	    	
	    }
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }

	    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    importarMedicionesForm.setRespuesta(res);
    	importarMedicionesForm.setBdStatusTabla(res);
	}
	
	private void VerificarExcel2007(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm)
	{
	    String res;
	    String campo;
	    
	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = (FormFile) importarMedicionesForm.getMultipartRequestHandler().getFileElements().get("file_browse");
	    	importarMedicionesForm.setFileForm(archivo);
	    	Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	Sheet sheet = workbook.getSheet(0);
	    	Cell celda = null;
	    	
	    	for ( int i=0, z=sheet.getRows(); i<z; i++ )
	    	{
	    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
	    		{
	    			celda = sheet.getCell(j, i);
	    			
	    			// Obtenemos el contenido de la celda
	    			campo = celda.getContents();
	    			
					if (!hayCodigo && campo.equalsIgnoreCase("Codigo"))
						hayCodigo = true;
					else if (!hayAno && campo.equalsIgnoreCase("Ano"))
						hayAno = true;
					else if (!hayPeriodo && campo.equalsIgnoreCase("Periodo"))
						hayPeriodo = true;
					else if (!hayMedicion && campo.equalsIgnoreCase("Medicion"))
						hayMedicion = true;

					if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
						break;
	    		}

	    		if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
					break;
	    	}
	    	
	    	workbook.close();
	    	
	    	res = "Codigo=" + (hayCodigo ? "true" : "false") + ",";
		    res = res + "Ano=" + (hayAno ? "true" : "false") + ",";
		    res = res + "Periodo=" + (hayPeriodo ? "true" : "false") + ",";
		    res = res + "Medicion=" + (hayMedicion ? "true" : "false");	    	
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }	        

	    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    importarMedicionesForm.setRespuesta(res);
	    importarMedicionesForm.setBdStatusTabla(res);
	}
	
	private void VerificarExcel2010(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm)
	{
	    String res;
	    String campo;
	    
	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = (FormFile) importarMedicionesForm.getMultipartRequestHandler().getFileElements().get("file_browse");
	    	importarMedicionesForm.setFileForm(archivo);
	    	XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());
	    	
	    	//Elegimos la primera hoja
	    	XSSFSheet hssfSheet = workBook.getSheetAt(0);
	    	
	    	Iterator<Row> rowIterator = hssfSheet.rowIterator();
	    	while (rowIterator.hasNext())
	    	{
	    		XSSFRow hssfRow = (XSSFRow) rowIterator.next();
		    	Iterator<org.apache.poi.ss.usermodel.Cell> iterator = hssfRow.cellIterator();
		    	while (iterator.hasNext())
		    	{
		    		XSSFCell hssfCell = (XSSFCell) iterator.next();

	    			// Obtenemos el contenido de la celda
	    			campo = hssfCell.toString();
	    			
					if (!hayCodigo && campo.equalsIgnoreCase("Codigo"))
						hayCodigo = true;
					else if (!hayAno && campo.equalsIgnoreCase("Ano"))
						hayAno = true;
					else if (!hayPeriodo && campo.equalsIgnoreCase("Periodo"))
						hayPeriodo = true;
					else if (!hayMedicion && campo.equalsIgnoreCase("Medicion"))
						hayMedicion = true;

					if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
						break;
		    	}
				if (hayCodigo && hayAno && hayPeriodo && hayMedicion)
					break;
	    	}
	    	
	    	res = "Codigo=" + (hayCodigo ? "true" : "false") + ",";
		    res = res + "Ano=" + (hayAno ? "true" : "false") + ",";
		    res = res + "Periodo=" + (hayPeriodo ? "true" : "false") + ",";
		    res = res + "Medicion=" + (hayMedicion ? "true" : "false");	    	
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }	        

	    importarMedicionesForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    importarMedicionesForm.setRespuesta(res);
	    importarMedicionesForm.setBdStatusTabla(res);
	}
	
	private int Salvar(HttpServletRequest request, ImportarMedicionesForm importarMedicionesForm) throws Exception
	{
		int respuesta = 10000;
		
		ActionMessages messages = getMessages(request);
		Usuario usuario = getUsuarioConectado(request);

		Importacion importacion = new Importacion();
		boolean nuevo = false;

		importarMedicionesForm.setBdPasswrod(request.getParameter("password"));
		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
		if ((importarMedicionesForm.getId() != null) && (!importarMedicionesForm.getId().equals(Long.valueOf("0"))))
			importacion = (Importacion)importarService.load(Importacion.class, importarMedicionesForm.getId());
		else
		{
			importacion = new Importacion();
			importacion.setId(new Long(0L));
			nuevo = true;
		}
		
		importacion.setNombre(importarMedicionesForm.getNombre());
		importacion.setTipo(importarMedicionesForm.getTipoFuente());
		importacion.setUsuario(usuario);
		importacion.setUsuarioId(usuario.getUsuarioId());
		importacion.setConfiguracion(importarMedicionesForm.getXml());
		
		respuesta = importarService.saveImportacion(importacion, usuario);
		if (respuesta == 10000) 
		{
			importarService.unlockObject(request.getSession().getId(), importarMedicionesForm.getId());
			destruirPoolLocksUsoEdicion(request, importarService);

			if (nuevo)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		importarService.close();
		
	    if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePlano().byteValue())
	    	importarMedicionesForm.setRespuesta(importarMedicionesForm.getBdStatusTabla());
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarMedicionesForm.getExcelTipo().byteValue() == 0)
	    	importarMedicionesForm.setRespuesta(importarMedicionesForm.getBdStatusTabla());
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarMedicionesForm.getExcelTipo().byteValue() == 1)
	    	importarMedicionesForm.setRespuesta(importarMedicionesForm.getBdStatusTabla());
	    else if (importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypePostGreSQL().byteValue()
	    		|| importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeOracle().byteValue()
	    		|| importarMedicionesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeSqlServer().byteValue())
	    	importarMedicionesForm.setRespuesta(importarMedicionesForm.getBdListaTablas() + "," + importarMedicionesForm.getBdStatusTabla());

		saveMessages(request, messages);
		
		return respuesta;
	}
	
	private int Eliminar(Long id, HttpServletRequest request)
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();

		importarService.unlockObject(request.getSession().getId(), id);

	    boolean bloqueado = !importarService.lockForDelete(request.getSession().getId(), id);

	    Importacion importacion = (Importacion)importarService.load(Importacion.class, new Long(id));
		
	    if (importacion != null)
	    {
	    	if (bloqueado)
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", importacion.getNombre()));
	    	else
	    	{
	    		importacion.setId(Long.valueOf(id));
	    		int res = importarService.deleteImportacion(importacion, getUsuarioConectado(request));
	    		if (res == 10004)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", importacion.getNombre()));
	    		else
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", importacion.getNombre()));
	    	}
	    }
	    else 
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    importarService.unlockObject(request.getSession().getId(), id);
	    importarService.close();

	    saveMessages(request, messages);

		return result; 
	}
}
