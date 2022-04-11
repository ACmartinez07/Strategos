/**
 * 
 */
package com.visiongc.app.strategos.web.struts.mediciones.actions;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm.CalcularStatus;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm.ImportarStatus;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarAccion;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarSeleccion;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;

import com.visiongc.commons.VgcReturnCode;
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
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
import com.visiongc.servicio.strategos.calculos.CalcularManager;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visongc.servicio.strategos.protegerliberar.ProtegerLiberarManager;

/**
 * @author Kerwin
 *
 */
public class ProtegerLiberarMedicionesSalvarAction extends VgcAction
{
    public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		super.execute(mapping, form, request, response);
		
		boolean cancel = (request.getParameter("cancel") != null) && (request.getParameter("cancel").equals("1"));
	    if (cancel)
	    {
	      request.getSession().removeAttribute("ProtegerLiberarMedicionesForm");
	      request.getSession().removeAttribute("verArchivoLog");
	      
	      return getForwardBack(request, 1, true);
	    }
	    String forward = mapping.getParameter();
	    
	    

		/** Se obtiene el FormBean haciendo el casting respectivo */
		ProtegerLiberarMedicionesForm protegerLiberarMedicionesForm = (ProtegerLiberarMedicionesForm) form;

		if (request.getParameter("funcion") != null)
		{
	    	String funcion = request.getParameter("funcion");
	    	
	    	
	    	if (funcion.equals("importar")) 
	    	{	    		
	    		ProtegerLiberar(request, protegerLiberarMedicionesForm);	    		
	    	}
		}
		return mapping.findForward(forward);
	}
	
	
	public void ProtegerLiberar(HttpServletRequest request, ProtegerLiberarMedicionesForm protegerLiberarMedicionesForm) throws Exception{
		
		StringBuffer log = new StringBuffer();
	    
	    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.titulo") + "\n");

	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.fechainicio", argsReemplazo) + "\n\n");
	    
	    protegerLiberarEjecutar(request, log, messageResources, protegerLiberarMedicionesForm);

	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.fechafin", argsReemplazo) + "\n\n");
	    
	    request.getSession().setAttribute("verArchivoLog", log);
	}
	
	private void protegerLiberarEjecutar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ProtegerLiberarMedicionesForm protegerLiberarMedicionesForm) throws Exception
	{
    		ActionMessages messages = getMessages(request);
		
			ServicioForm servicioForm = new ServicioForm();
			
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
			
			if (configuracion == null)
			{
				protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
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
					protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
					saveMessages(request, messages);
				}
				else
				{
								 
					Usuario usuario = getUsuarioConectado(request);
					Boolean todosOrganizacion = request.getParameter("todosOrganizacion") != null ? (request.getParameter("todosOrganizacion").toString().equals("1") ? true : false) : false; 
					byte accion = protegerLiberarMedicionesForm.getAccion().byteValue();
					byte seleccionSubClase= ProtegerLiberarSeleccion.getSeleccionSubClase().byteValue();
					byte seleccionSubOrg=ProtegerLiberarSeleccion.getSeleccionSubOrganizacion().byteValue();
					byte seleccionOrgAll=ProtegerLiberarSeleccion.getSeleccionOrganizacionTodas().byteValue();
					servicioForm.setProperty("url", url);
					servicioForm.setProperty("driver", driver);
					servicioForm.setProperty("user", user);
					servicioForm.setProperty("password", password);
					servicioForm.setProperty("accion", ((Byte)(accion)).toString());
					servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
					if (!todosOrganizacion)
						servicioForm.setProperty("organizacionId", (String)request.getSession().getAttribute("organizacionId"));
					
					StringBuffer logBefore = log;
					
					
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		    		if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionIndicador().byteValue())
		    			protegerLiberarMedicionesForm.setIndicadorId(null);
		    		if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionIndicadoresSeleccionados().byteValue())
		    			protegerLiberarMedicionesForm.setIndicadores(null);
		    		if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionClase().byteValue())
		    			if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionSubClase().byteValue())
		    				protegerLiberarMedicionesForm.setClaseId(null);
		    		if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionPlan().byteValue())
		    			protegerLiberarMedicionesForm.setPlanId(null);
		    		if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionPerspectiva().byteValue())
		    			protegerLiberarMedicionesForm.setPerspectivaId(null);
		    		if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionOrganizacion().byteValue())
		    			if (protegerLiberarMedicionesForm.getTipoSeleccion() != null && protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() != ProtegerLiberarSeleccion.getSeleccionSubOrganizacion().byteValue())
		    				protegerLiberarMedicionesForm.setOrganizacionId(null);
		    		
		    		Boolean actualizarForma = request.getSession().getAttribute("actualizarFormaProteger") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarFormaProteger")) : null;
		    		int respuesta = VgcReturnCode.DB_OK;
		    		if (actualizarForma == null)
		    		{
		    			List<Indicador> indicadores = strategosIndicadoresService.getIndicadoresParaEjecutar(protegerLiberarMedicionesForm.getIndicadorId(), protegerLiberarMedicionesForm.getIndicadores(), protegerLiberarMedicionesForm.getClaseId(), (protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() == ProtegerLiberarSeleccion.getSeleccionSubClase().byteValue()), protegerLiberarMedicionesForm.getOrganizacionId(), (protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() == ProtegerLiberarSeleccion.getSeleccionSubOrganizacion().byteValue()), (protegerLiberarMedicionesForm.getTipoSeleccion().byteValue() == ProtegerLiberarSeleccion.getSeleccionOrganizacionTodas().byteValue()), protegerLiberarMedicionesForm.getPerspectivaId(), protegerLiberarMedicionesForm.getPlanId());
		    			
		    			Map<String, Object> filtros = new HashMap<String, Object>();
		    			filtros.put("anoDesde", protegerLiberarMedicionesForm.getAno());
		    			filtros.put("anoHasta", protegerLiberarMedicionesForm.getAno());
		    			filtros.put("mesDesde", protegerLiberarMedicionesForm.getMesInicial());
		    			filtros.put("mesHasta", protegerLiberarMedicionesForm.getMesFinal());
		    			
		    			Long serieId = new Long(0);
		    			
		    			if(protegerLiberarMedicionesForm.getSerieId() != 7){
		    				serieId = protegerLiberarMedicionesForm.getSerieId().longValue();
		    				filtros.put("serieId", serieId);
		    			}if(protegerLiberarMedicionesForm.getSerieId() == 7){
		    				serieId = null;
		    			}
		    			
		    			respuesta = strategosIndicadoresService.protegerMedicionesIndicadores(indicadores, filtros, (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar()));
		    			
		    	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    			Calendar fecha = Calendar.getInstance();
		    			Date fechaDate = null;
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar())
		    			{
		    				fecha.setTime(simpleDateFormat.parse("01/" + protegerLiberarMedicionesForm.getMesInicial() + "/" + protegerLiberarMedicionesForm.getAno()));
		    				fecha = PeriodoUtil.inicioDelDia(fecha);
		    				fecha.add(5, -1);
		    			}
		    			else
		    				fecha = PeriodoUtil.getCalendarFinMes(protegerLiberarMedicionesForm.getMesFinal().intValue(), protegerLiberarMedicionesForm.getAno().intValue());				
		    			fechaDate = fecha.getTime();
		    			Set<SerieIndicador> seriesIndicador = null;

		    			// ejecución segundo plano 
		    			
		    			String [][]datos=null; 
						boolean respuestas= new ProtegerLiberarManager
								(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(datos, indicadores, fecha, strategosIndicadoresService, accion, serieId);
					


						log = logBefore;
						String res = "";
						if(respuestas == true){
					
						res = "Successful";
					    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.protegerliberar.archivo.configuracion.alerta.fin"));
					    saveMessages(request, messages);
					    
					    /*
						if (usuario != null)
						{
							Calendar ahora = Calendar.getInstance();
							byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoProtegerLiberar();
							Servicio servicio = new Servicio();  
							servicio.setUsuarioId(usuario.getUsuarioId());
							servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " " + VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
							servicio.setNombre(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.titulo"));
							
							String[] argsReemplazo = new String[2];
						    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
						    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

							servicio.setMensaje(messageResources.getResource("jsp.asistente.protegerliberar.log.fechafin.programada", argsReemplazo));
							servicio.setEstatus(ProtegerLiberarStatus.getImportarStatusSuccess());
							
							frameworkService.registrarAuditoriaEvento((Object) servicio, usuario, tipoEvento);
						}
						*/
					
					    protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
					    protegerLiberarMedicionesForm.setRespuesta(res);
						}
		    			
						// ejecución finalizada
						
		    			if (request.getSession().getAttribute("actualizarFormaProteger") == null)
		    				request.getSession().setAttribute("actualizarFormaProteger", "false");
		    		}
		    		else
		    			request.getSession().removeAttribute("actualizarFormaProteger");
		    		
		    		strategosIndicadoresService.close();
		    		
		    		if (respuesta == VgcReturnCode.DB_OK)
		    		{
		    			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.estatus.success"));
		    			else if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.estatus.success"));
		    		}
		    		else if (respuesta == VgcReturnCode.DB_NOT_FOUND)
		    		{
		    			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.estatus.medicionesempty"));
		    			else if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.estatus.medicionesempty"));
		    		}
		    		else
		    		{
		    			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.estatus.nosuccess"));
		    			else if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.estatus.nosuccess"));
		    		}			
					
					
					
				  // si la respuesta es false 
				 
					
				}
			}
			
			frameworkService.close();
		
	}
	
	

}
