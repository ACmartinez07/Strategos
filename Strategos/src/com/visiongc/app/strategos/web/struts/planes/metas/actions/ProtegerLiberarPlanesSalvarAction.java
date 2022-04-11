package com.visiongc.app.strategos.web.struts.planes.metas.actions;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.util.PeriodoUtil;
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
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visongc.servicio.strategos.protegerliberar.ProtegerLiberarManager;
import com.visongc.servicio.strategos.protegerliberar.ProtegerLiberarManagerMetas;

public class ProtegerLiberarPlanesSalvarAction extends VgcAction{
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
		if(request.getParameter("accion").equals("proteger")){
		protegerLiberarMedicionesForm.setAccion(ProtegerLiberarAccion.getAccionBloquear());
		}else if(request.getParameter("accion").equals("liberar")){
		protegerLiberarMedicionesForm.setAccion(ProtegerLiberarAccion.getAccionLiberar());
		}
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
					
			
					StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
					
				
		    		Boolean actualizarForma = request.getSession().getAttribute("actualizarFormaProteger") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarFormaProteger")) : null;
		    		int respuesta = VgcReturnCode.DB_OK;
		    		if (actualizarForma == null)
		    		{
		    			List<Meta> metas = null;
		    			
		    			if(protegerLiberarMedicionesForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionIndicador().byteValue())
							metas = strategosMetasService.getMetasParaEjecutar(protegerLiberarMedicionesForm.getIndicadorId(), null, null, false, protegerLiberarMedicionesForm.getPlanId(), protegerLiberarMedicionesForm.getAno());	
						if(protegerLiberarMedicionesForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionIndicadoresSeleccionados().byteValue())
							metas = strategosMetasService.getMetasParaEjecutar(null, protegerLiberarMedicionesForm.getIndicadores(), null, false, protegerLiberarMedicionesForm.getPlanId(), protegerLiberarMedicionesForm.getAno());
						if(protegerLiberarMedicionesForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionPlan().byteValue())
							metas = strategosMetasService.getMetasParaEjecutar(null, null, null, false, protegerLiberarMedicionesForm.getPlanId(), protegerLiberarMedicionesForm.getAno());
						if(protegerLiberarMedicionesForm.getTipoSeleccion() ==ProtegerLiberarSeleccion.getSeleccionOrganizacion().byteValue())
							metas = strategosMetasService.getMetasParaEjecutar(null, null, protegerLiberarMedicionesForm.getOrganizacionId(), false, null, protegerLiberarMedicionesForm.getAno());
						if(protegerLiberarMedicionesForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionOrganizacionTodas().byteValue())
							metas = strategosMetasService.getMetasParaEjecutar(null, null, null, true, null, protegerLiberarMedicionesForm.getAno());
							
						
		    			if(metas == null){
		    				respuesta = VgcReturnCode.DB_CANCELED;
		    			}else{
		    				
		    					    			
						
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
				    		
								boolean respuestas= new ProtegerLiberarManagerMetas
										(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(datos, metas, fecha, strategosMetasService, accion, usuario);
								
				    		
								log = logBefore;
								String res = "";
								if(respuestas == true){
							
								res = "Successful";
							    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.protegerliberar.archivo.meta.configuracion.alerta.fin"));
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
		    		}
		    		else
		    			request.getSession().removeAttribute("actualizarFormaProteger");
		    		
		    		strategosMetasService.close();
		    		
		    		if (respuesta == VgcReturnCode.DB_OK)
		    		{
		    			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.metas.estatus.success"));
		    			else if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.metas.estatus.success"));
		    		}
		    		else if (respuesta == VgcReturnCode.DB_NOT_FOUND)
		    		{
		    			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.metas.estatus.medicionesempty"));
		    			else if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.metas.estatus.medicionesempty"));
		    		}
		    		else
		    		{
		    			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.metas.estatus.nosuccess"));
		    			else if (protegerLiberarMedicionesForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.metas.estatus.nosuccess"));
		    		}			
					
					
					
				  // si la respuesta es false 
				 
					
				}
			}
			
			frameworkService.close();
		
	}
	
}
