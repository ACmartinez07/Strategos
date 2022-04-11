package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;

import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarStatus;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.TrasladarMetasForm;
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
import com.visongc.servicio.strategos.protegerliberar.TrasladarMetasManager;



public class TrasladarMetasSalvarAction extends VgcAction{
    public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		super.execute(mapping, form, request, response);
		
		boolean cancel = (request.getParameter("cancel") != null) && (request.getParameter("cancel").equals("1"));
	    if (cancel)
	    {
	      request.getSession().removeAttribute("trasladarMetasForm");
	      request.getSession().removeAttribute("verArchivoLog");
	      
	      return getForwardBack(request, 1, true);
	    }
	    String forward = mapping.getParameter();
	    
	    
	    TrasladarMetasForm trasladarMetasForm = (TrasladarMetasForm)form;
		/** Se obtiene el FormBean haciendo el casting respectivo */
		
		if (request.getParameter("funcion") != null)
		{
	    	String funcion = request.getParameter("funcion");
	    	
	    	
	    	if (funcion.equals("trasladar")) 
	    	{	    		
	    		TrasladarMetas(request, trasladarMetasForm);	    		
	    	}
		}
		return mapping.findForward(forward);
	}
	
	
	public void TrasladarMetas(HttpServletRequest request, TrasladarMetasForm trasladarMetasForm) throws Exception{
		
		StringBuffer log = new StringBuffer();
	    
	    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.titulo") + "\n");

	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.fechainicio", argsReemplazo) + "\n\n");
	    
	    
	    trasladarMetasEjecutar(request, log, messageResources, trasladarMetasForm);
		
	    
	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.protegerliberar.mediciones.log.fechafin", argsReemplazo) + "\n\n");
	    
	    request.getSession().setAttribute("verArchivoLog", log);
	}
	
	private void trasladarMetasEjecutar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, TrasladarMetasForm trasladarMetasForm) throws Exception
	{
		ActionMessages messages = getMessages(request);
		
		ServicioForm servicioForm = new ServicioForm();
		
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
		
		if (configuracion == null)
		{
			trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
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
				trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
				saveMessages(request, messages);
			}
			else
			{
							 
				Long planId = trasladarMetasForm.getPlanId();
				Usuario usuario = getUsuarioConectado(request);
				
				
				servicioForm.setProperty("url", url);
				servicioForm.setProperty("driver", driver);
				servicioForm.setProperty("user", user);
				servicioForm.setProperty("password", password);
				servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
					
				StringBuffer logBefore = log;
				
				Integer anoIni = trasladarMetasForm.getAno();
				Integer anoFin = trasladarMetasForm.getAnoFinal();
				
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
				StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
				

				
				Boolean actualizarForma = request.getSession().getAttribute("actualizarFormaProteger") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarFormaProteger")) : null;
	    		int respuesta = VgcReturnCode.DB_OK;
	    		if (actualizarForma == null)
	    		{
	    			
	    			Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));

					if(anoFin >plan.getAnoFinal()){
						
						trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
		    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.trasladar.ano.destino.error"));
		    			respuesta = VgcReturnCode.DB_CANCELED;
					}else{
						
							
			    			List<Indicador> indicadores = null;
			    			
			    			indicadores = strategosIndicadoresService.getIndicadoresParaEjecutar(
			    					null, null, null, false, null, false, false, null, planId);
							
			    			if(indicadores == null){
			    				respuesta = VgcReturnCode.DB_NOT_FOUND;
			    			}else{
			    		   					    			
							
					    	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					    			Calendar fecha = Calendar.getInstance();
					    			
			
					    			// ejecución segundo plano 
					    			boolean respuestas= new TrasladarMetasManager
											(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(indicadores, fecha, strategosMetasService, strategosMedicionesService, usuario, anoIni, anoFin, planId);
									
									log = logBefore;
									String res = "";
									if(respuestas == true){
								
									res = "Successful";
								    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.trasladar.archivo.meta.configuracion.alerta.fin"));
								    saveMessages(request, messages);
								   
								    						
								    trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
								    trasladarMetasForm.setRespuesta(res);
									}
					    			
									// ejecución finalizada
									
					    			if (request.getSession().getAttribute("actualizarFormaProteger") == null)
					    				request.getSession().setAttribute("actualizarFormaProteger", "false");
			    			}
					}
	    		}
	    		else
	    			request.getSession().removeAttribute("actualizarFormaProteger");
	    		
	    		strategosIndicadoresService.close();
	    		strategosMetasService.close();
	    		strategosPlanesService.close();
	    		strategosMedicionesService.close();
	    	
	    		if (respuesta == VgcReturnCode.DB_OK)
	    		{
	    			trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.trasladar.metas.estatus.success"));
	    			saveMessages(request, messages);
	    		}
	    		else if (respuesta == VgcReturnCode.DB_NOT_FOUND)
	    		{
	    			trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.trasladar.metas.estatus.medicionesempty"));
	    			saveMessages(request, messages);
	    		}
	    		else
	    		{
	    			trasladarMetasForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoSuccess());
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.alerta.trasladar.metas.estatus.nosuccess"));
	    			saveMessages(request, messages);
	    		}			
				
			
			}
		}
		
		frameworkService.close();
		
	}
}
