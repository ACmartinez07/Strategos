/**
 * 
 */
package com.visiongc.app.strategos.web.struts.transacciones.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm.ImportarStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.app.strategos.web.struts.transacciones.forms.TransaccionForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.Columna.ColumnaTipo;
import com.visiongc.framework.transaccion.TransaccionService;
import com.visiongc.framework.util.FrameworkConnection;

/**
 * @author Kerwin
 *
 */
public final class ReporteTransaccionAction extends VgcAction
{
	public static final String ACTION_KEY = "MostrarVistaDatosAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		ActionMessages messages = getMessages(request);

		Long transaccionId = (request.getParameter("transaccionId") != null ? Long.parseLong(request.getParameter("transaccionId")) : 0L);
		String fechaInicial = (request.getParameter("fechaInicial") != null ? request.getParameter("fechaInicial") : null);
		String fechaFinal = (request.getParameter("fechaFinal") != null ? request.getParameter("fechaFinal") : null);
		Integer numeroRegistros = ((request.getParameter("numeroRegistros") != null && request.getParameter("numeroRegistros") != "") ? Integer.parseInt(request.getParameter("numeroRegistros")) : null);
		
		TransaccionForm transaccionForm = (TransaccionForm)form;
		Boolean showTablaParametro = transaccionForm.getShowTablaParametro();
		transaccionForm.clear();
		transaccionForm.setTransaccionId(transaccionId);
		
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

		String parametros = leerConfiguracion(transaccionForm, request);
		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("seleccionar")) 
	    	{
	        	Date ahora = new Date();
	        	if (transaccionForm.getFechaInicial() == null)
	        		transaccionForm.setFechaInicial(VgcFormatter.formatearFecha(ahora, "formato.fecha.corta"));
	        	if (transaccionForm.getFechaFinal() == null)
	        		transaccionForm.setFechaFinal(VgcFormatter.formatearFecha(ahora, "formato.fecha.corta"));
	    		
	    		return mapping.findForward(forward);
	    	}
		}
		
		TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
		Transaccion transaccion = (Transaccion) transaccionService.load(Transaccion.class, transaccionId);
		transaccionService.close();
		
		transaccionForm.setFechaInicial(fechaInicial);
		transaccionForm.setFechaFinal(fechaFinal);
		transaccionForm.setNumeroRegistros(numeroRegistros);
		if (transaccion != null)
		{
			if (transaccion.getTabla() != null)
				transaccionForm.setTransaccion(transaccion);
			
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador;
			transaccionForm.setFrecuencia(transaccion.getFrecuencia());
			transaccionForm.setNombre(transaccion.getNombre());
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
			strategosIndicadoresService.close();
		}
		
		List<List<Columna>> datos = null;
		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("chequear")) 
	    	{
	    		datos = getDatos(true, request, transaccionForm, messages);
	    		return mapping.findForward(forward);
	    	}
		}

		List<Columna> columnas = new ArrayList<Columna>();
		String atributos = request.getParameter("xmlAtributos") != null && !request.getParameter("xmlAtributos").equals("") ? request.getParameter("xmlAtributos") : parametros != null ? parametros : "";
		if (!atributos.equals(""))
		{
			String[] tipos = atributos.split("\\|");
			for (int i = 0; i < tipos.length; i++)
			{
				String[] campos = tipos[i].split(",");
				if (campos.length == 6)
				{
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (columna.getNombre().equals(campos[1]))
						{
							columna.setOrden(campos[0]);
							columna.setVisible(campos[3].equals("1") ? true : false);
							columna.setAncho(campos[4]);
							columna.setAgrupar(campos[5].equals("1") ? true : false);
							
							columnas.add(columna);
						}
					}
				}
			}
			transaccionForm.getTransaccion().getTabla().setColumnas(columnas);
		}
		
		datos = getDatos(false, request, transaccionForm, messages);
		transaccionForm.setDatos(datos);
		transaccionForm.setNombreFrecuencia(Frecuencia.getNombre(transaccionForm.getFrecuencia().byteValue()));
		if (transaccionForm.getShowTablaParametro() == null)
			transaccionForm.setShowTablaParametro(true);
		else if (showTablaParametro != null && showTablaParametro.booleanValue() != transaccionForm.getShowTablaParametro().booleanValue())
			transaccionForm.setShowTablaParametro(showTablaParametro);

		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("salvar")) 
	    	{
	    		int respuesta = salvarConfiguracion(atributos, transaccionForm, request);
	    		if (respuesta == 10000)
	    		{
				    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.reporte.transaccion.reporte.save.alerta.success"));
				    saveMessages(request, messages);

				    transaccionForm.setStatus(ImportarStatus.getImportarStatusSalvado());
	    		}
	    		else
	    		{
				    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.reporte.transaccion.reporte.save.alerta.nosuccess"));
				    saveMessages(request, messages);

				    transaccionForm.setStatus(ImportarStatus.getImportarStatusNoSalvado());
	    		}
	    	}
		}
		
  		Integer anchoTablaDatos = 0;
		for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
		{
			Columna columna = (Columna)col.next();
			if (columna.getVisible())
			{
				transaccionForm.getColumnas().add(columna);
				anchoTablaDatos = anchoTablaDatos + new Integer(columna.getAncho());
			}
		}
  		anchoTablaDatos = anchoTablaDatos + (transaccionForm.getColumnas().size() * 20);
  		transaccionForm.setAnchoTablaDatos(anchoTablaDatos);
  		transaccionForm.setTotalColumnas(transaccionForm.getTransaccion().getTabla().getColumnas().size());
		
		PaginaLista paginaColumnas = new PaginaLista();
		paginaColumnas.setLista(transaccionForm.getTransaccion().getTabla().getColumnas());
		paginaColumnas.setNroPagina(0);
		paginaColumnas.setTotal(paginaColumnas.getLista().size());

		request.setAttribute("paginaColumnas", paginaColumnas);
		
		return mapping.findForward(forward);
	}
	
	public List<List<Columna>> getDatos(boolean contar, HttpServletRequest request, TransaccionForm transaccionForm, ActionMessages messages) throws Exception
	{
		List<List<Columna>> datos = new ArrayList<List<Columna>>();
		
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
		transaccion.setFechaInicial(transaccionForm.getFechaInicial());
		transaccion.setFechaFinal(transaccionForm.getFechaFinal());
		transaccion.setNumeroRegistros(transaccionForm.getNumeroRegistros());
		Usuario usuario = getUsuarioConectado(request);

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
				servicio.setProperty("numeroEjecucion", ((Integer)(1)).toString());
				servicio.setProperty("usuarioId", usuario.getUsuarioId().toString());
				servicio.setProperty("calcular", ((Boolean)(true)).toString());
				
				StringBuffer log = new StringBuffer();
				datos = new com.visiongc.servicio.strategos.importar.ImportarManager(servicio.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).getDatos(contar, transaccion);
				boolean hayDatos = false; 
				if (contar && datos.size() > 0)
				{
					List<Columna> dato = datos.get(0);
					Long total = Long.parseLong(dato.get(0).getValorArchivo());
					if (total != 0)
						hayDatos = true;
				}
				else if (!contar && datos.size() > 0)
				{
					Double total = 0D;
				    for (Iterator<List<Columna>> iter = datos.iterator(); iter.hasNext();)
				    {
				    	List<Columna> columna = (List<Columna>)iter.next();
				    	for (Iterator<Columna> col = columna.iterator(); col.hasNext(); )
				    	{
							Columna c = (Columna)col.next();
							if (c.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue() && c.getValorReal() != null && !c.getValorReal().equals(""))
							{
								total = total + VgcFormatter.parsearNumeroFormateado(c.getValorReal());
								break;
							}
				    	}
				    }

		    		Locale currentLocale = new Locale("en","US");
		    	    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
		    	    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
		    	    decimalformat.applyPattern("#,##0.00");
				    transaccionForm.setTotalOperacion(String.valueOf(datos.size()));
					transaccionForm.setTotalConsolidado(decimalformat.format(total));
					hayDatos = true;
				}
					
				if (hayDatos)
					transaccionForm.setStatus(ImportarStatus.getImportarStatusImportado());
				else
				{
				    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.reporte.transaccion.reporte.alerta.nosuccess"));
				    saveMessages(request, messages);

				    transaccionForm.setStatus(ImportarStatus.getImportarStatusNoSuccess());
				}
			}
		}
		
		return datos;
	}
	
	private int salvarConfiguracion(String xmlAtributos, TransaccionForm transaccionForm, HttpServletRequest request)
	{
		int respuesta = 10000;
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Configuracion.Transaccion.Editar.Parametros", "Parametros");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Configuracion.Transaccion.Editar.Parametros");
			pk.setObjeto("Parametros");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
		}
		
		try 
		{
			configuracionUsuario.setData(transaccionForm.getXmlParametros(xmlAtributos));
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
			respuesta = 10003;
		} 
		catch (TransformerFactoryConfigurationError e) 
		{
			e.printStackTrace();
			respuesta = 10003;
		} 
		catch (TransformerException e) 
		{
			e.printStackTrace();
			respuesta = 10003;
		}
		
		frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		frameworkService.close();
		
		return respuesta;
	}
	
	private String leerConfiguracion(TransaccionForm transaccionForm, HttpServletRequest request)
	{
		String atributos = null;
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Configuracion.Transaccion.Editar.Parametros", "Parametros");
		if (configuracionUsuario != null)
		{
			try 
			{
				atributos = transaccionForm.setParametros(configuracionUsuario.getData());
			} 
			catch (ParserConfigurationException e) 
			{
				e.printStackTrace();
			} 
			catch (TransformerFactoryConfigurationError e) 
			{
				e.printStackTrace();
			} 
			catch (TransformerException e) 
			{
				e.printStackTrace();
			} 
			catch (SAXException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		frameworkService.close();
		
		return atributos;
	}
}
