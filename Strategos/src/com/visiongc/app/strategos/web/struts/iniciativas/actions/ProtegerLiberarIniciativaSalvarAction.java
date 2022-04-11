package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.util.PeriodoUtil;

import com.visiongc.app.strategos.web.struts.iniciativas.forms.ProtegerLiberarIniciativasForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ProtegerLiberarIniciativasForm.ProtegerLiberarAccion;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ProtegerLiberarIniciativasForm.ProtegerLiberarSeleccion;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ProtegerLiberarIniciativasForm.ProtegerLiberarStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Modulo.ModuloType.Actividades;
import com.visiongc.framework.model.Modulo.ModuloType.Iniciativas;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visongc.servicio.strategos.protegerliberar.ProtegerLiberarManager;
import com.visongc.servicio.strategos.protegerliberar.ProtegerLiberarManagerIniciativas;
import com.visongc.servicio.strategos.protegerliberar.ProtegerLiberarManagerMetas;

public class ProtegerLiberarIniciativaSalvarAction extends VgcAction{

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		super.execute(mapping, form, request, response);
		
		boolean cancel = (request.getParameter("cancel") != null) && (request.getParameter("cancel").equals("1"));
	    if (cancel)
	    {
	      request.getSession().removeAttribute("ProtegerLiberarIniciativasForm");
	      request.getSession().removeAttribute("verArchivoLog");
	      
	      return getForwardBack(request, 1, true);
	    }
	    String forward = mapping.getParameter();
	    
	    MessageResources mensajes = getResources(request);


		/** Se obtiene el FormBean haciendo el casting respectivo */
		ProtegerLiberarIniciativasForm protegerLiberarIniciativasForm = (ProtegerLiberarIniciativasForm) form;
		if(request.getParameter("accion").equals("proteger")){
		protegerLiberarIniciativasForm.setAccion(ProtegerLiberarAccion.getAccionBloquear());
		}else if(request.getParameter("accion").equals("liberar")){
		protegerLiberarIniciativasForm.setAccion(ProtegerLiberarAccion.getAccionLiberar());
		}
		if (request.getParameter("funcion") != null)
		{
	    	String funcion = request.getParameter("funcion");
	    	
	    	
	    	if (funcion.equals("importar")) 
	    	{	    		
	    		ProtegerLiberar(request, protegerLiberarIniciativasForm);	    		
	    	}
		}
		return mapping.findForward(forward);
	}
	
	
	public void ProtegerLiberar(HttpServletRequest request, ProtegerLiberarIniciativasForm protegerLiberarIniciativasForm) throws Exception{
		
		StringBuffer log = new StringBuffer();
	    
	    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.titulo") + "\n");

	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.fechainicio", argsReemplazo) + "\n\n");
	     
	    protegerLiberarEjecutar(request, log, messageResources, protegerLiberarIniciativasForm);

	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.fechafin", argsReemplazo) + "\n\n");
	    
	    request.getSession().setAttribute("verArchivoLog", log);
	}
	
	private void protegerLiberarEjecutar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ProtegerLiberarIniciativasForm protegerLiberarIniciativasForm) throws Exception
	{
    		ActionMessages messages = getMessages(request);
		
			ServicioForm servicioForm = new ServicioForm();
			
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
			
			if (configuracion == null)
			{
				protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
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
					protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
					saveMessages(request, messages);
				}
				else
				{
					
					
					
								 
					Usuario usuario = getUsuarioConectado(request);
					Boolean todosOrganizacion = request.getParameter("todosOrganizacion") != null ? (request.getParameter("todosOrganizacion").toString().equals("1") ? true : false) : false; 
					byte accion = protegerLiberarIniciativasForm.getAccion().byteValue();
					
					
					servicioForm.setProperty("url", url);
					servicioForm.setProperty("driver", driver);
					servicioForm.setProperty("user", user);
					servicioForm.setProperty("password", password);
					servicioForm.setProperty("accion", ((Byte)(accion)).toString());
					servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
					if (!todosOrganizacion)
						servicioForm.setProperty("organizacionId", (String)request.getSession().getAttribute("organizacionId"));
					
					StringBuffer logBefore = log;
					
					
					StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					
				
		    		Boolean actualizarForma = request.getSession().getAttribute("actualizarFormaProteger") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarFormaProteger")) : null;
		    		int respuesta = VgcReturnCode.DB_OK;
		    		if (actualizarForma == null)
		    		{
		    			List<Iniciativa> iniciativas = new ArrayList();
		    			Iniciativa ini = null;
		    			
		    			
		    			if(protegerLiberarIniciativasForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionIniciativa().byteValue()) {
		    				ini= (Iniciativa)strategosIniciativasService.load(Iniciativa.class, protegerLiberarIniciativasForm.getIniciativaId());
		    				if(ini != null && ini.getEstatusId() == 1 || ini.getEstatusId() == 2){
		    					
		    					iniciativas.add(ini);
		    				}else {
		    					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.iniciativas.estatus.culminado"));
		    					saveMessages(request, messages);
		    					iniciativas = null;
		    				}
		    			}
		    				
		    				
		    				
		    			if(protegerLiberarIniciativasForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionPlan().byteValue())
		    			 	iniciativas = strategosIniciativasService.getIniciativasParaEjecutar(null, null, protegerLiberarIniciativasForm.getPlanId(), protegerLiberarIniciativasForm.getAno());
		    			if(protegerLiberarIniciativasForm.getTipoSeleccion() ==ProtegerLiberarSeleccion.getSeleccionOrganizacion().byteValue())
		    				iniciativas = strategosIniciativasService.getIniciativasParaEjecutar(null, protegerLiberarIniciativasForm.getOrganizacionId(), null, protegerLiberarIniciativasForm.getAno());
		    			if(protegerLiberarIniciativasForm.getTipoSeleccion()== ProtegerLiberarSeleccion.getSeleccionOrganizacionTodas().byteValue())
		    				iniciativas = strategosIniciativasService.getIniciativasParaEjecutar(null, null, null, protegerLiberarIniciativasForm.getAno());
		    			
		    			
		    			if(protegerLiberarIniciativasForm.getTipoSeleccion() != ProtegerLiberarSeleccion.getSeleccionIniciativa().byteValue()) {
		    			
			    			Long selectTipoProyecto = (request.getParameter("selectTipoProyecto") != null) && (request.getParameter("selectTipoProyecto") != "") && (!request.getParameter("selectTipoProyecto").equals("0")) ? Long.valueOf(Long.parseLong(request.getParameter("selectTipoProyecto"))) : null;
			    			List<Iniciativa> iniciativasTipo = new ArrayList();
			    			
			    			if(selectTipoProyecto != null && selectTipoProyecto >0) {
			    				
			    				for(Iniciativa iniTipo: iniciativas){
			    					if(iniTipo.getTipoId() != null) {
			    						if(iniTipo.getTipoId() == selectTipoProyecto) {
			    							iniciativasTipo.add(iniTipo);
			    						}
			    					}
			    				}
			    				
			    				iniciativas = iniciativasTipo;
			    			}			    			
			    			
		    			}
		    			
		    			// filtro tipo proyecto
		    			
		    			
		    			
		    			if(iniciativas == null || iniciativas.size() == 0){
		    				respuesta = VgcReturnCode.DB_NOT_FOUND;
		    			}
		    			else{
		    				
		    				List<Indicador> ids = obtenerIndicadoresActividad(iniciativas, strategosIniciativasService);
		    				
		    					Map<String, Object> filtros = new HashMap<String, Object>();
				    			filtros.put("anoDesde", protegerLiberarIniciativasForm.getAno());
				    			filtros.put("anoHasta", protegerLiberarIniciativasForm.getAno());
				    			filtros.put("mesDesde", protegerLiberarIniciativasForm.getMesInicial());
				    			filtros.put("mesHasta", protegerLiberarIniciativasForm.getMesFinal());
				    			
				    			Long serieId = new Long(0);
				    			
				    			if(protegerLiberarIniciativasForm.getSerieId() != 7){
				    				serieId = protegerLiberarIniciativasForm.getSerieId().longValue();
				    				filtros.put("serieId", serieId);
				    			}if(protegerLiberarIniciativasForm.getSerieId() == 7){
				    				serieId = null;
				    			}
				    			
				    			respuesta = strategosIndicadoresService.protegerMedicionesIndicadores(ids, filtros, (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar()));
				    			
		    					
						
				    	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				    			Calendar fecha = Calendar.getInstance();
				    			Date fechaDate = null;
				    			if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar())
				    			{
				    				fecha.setTime(simpleDateFormat.parse("01/" + protegerLiberarIniciativasForm.getMesInicial() + "/" + protegerLiberarIniciativasForm.getAno()));
				    				fecha = PeriodoUtil.inicioDelDia(fecha);
				    				fecha.add(5, -1);
				    			}
				    			else {
				    				fecha = PeriodoUtil.getCalendarFinMes(protegerLiberarIniciativasForm.getMesFinal().intValue(), protegerLiberarIniciativasForm.getAno().intValue());				
				    				fechaDate = fecha.getTime();
				    			}
		
				    			// ejecución segundo plano 
				    			
				    			String [][]datos=null; 
								boolean respuestas= new ProtegerLiberarManager
										(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(datos, ids, fecha, strategosIndicadoresService, accion, serieId);
							
				    		
								log = logBefore;
								String res = "";
								if(respuestas == true){
							
								res = "Successful";
							    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.protegerliberar.archivo.iniciativas.configuracion.alerta.fin"));
							    saveMessages(request, messages);
							    
							    protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
							    protegerLiberarIniciativasForm.setRespuesta(res);
								}
				    			
								// ejecución finalizada
								
				    			if (request.getSession().getAttribute("actualizarFormaProteger") == null)
				    				request.getSession().setAttribute("actualizarFormaProteger", "false");
		    			}
		    		}
		    		else {
		    			request.getSession().removeAttribute("actualizarFormaProteger");
		    		}
		    		
		    		
		    		strategosIniciativasService.close();
		    		
		    		if (respuesta == VgcReturnCode.DB_OK)
		    		{
		    			protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
		    			if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.iniciativas.estatus.success"));
		    			else if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.iniciativas.estatus.success"));
		    		}
		    		else if (respuesta == VgcReturnCode.DB_NOT_FOUND)
		    		{
		    			protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.iniciativas.estatus.medicionesempty"));
		    			else if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.iniciativas.estatus.medicionesempty"));
		    		}
		    		else
		    		{
		    			protegerLiberarIniciativasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionLiberar().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.liberar.iniciativas.estatus.nosuccess"));
		    			else if (protegerLiberarIniciativasForm.getAccion().byteValue() == ProtegerLiberarAccion.getAccionBloquear().byteValue())
		    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.bloquear.iniciativas.estatus.nosuccess"));
		    		}			
					
		    		saveMessages(request, messages);
					
				  // si la respuesta es false 
				 
					
				}
			}
			
			frameworkService.close();
		
	}
	
	
	/* obtener indicadores por actividad */
	
	private List<Indicador> obtenerIndicadoresActividad(List<Iniciativa> iniciativas, StrategosIniciativasService strategosIniciativasService){
		
		
		List<Indicador> ids= new ArrayList();
		List<PryActividad> actividades = new ArrayList();
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosPryActividadesService strategosPryActividadService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		
		
		for(Iniciativa ini: iniciativas){
			
			
			if(ini.getProyectoId() != null){
				actividades = strategosPryActividadService.getActividades(ini.getProyectoId());
				
				if(actividades != null && actividades.size() >0 ){
					for(PryActividad act: actividades){
											
						if(act.getIndicadorId() != null) {
							Indicador ind = (Indicador) strategosIndicadoresService.load(Indicador.class, act.getIndicadorId());
							ids.add(ind);
						}
													
					}
				}
				
			}
						
		}
				
		
		return ids;
	}
	
}
