package com.visiongc.app.strategos.web.struts.configuracion.actions;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacionPK;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.calculos.actions.TrabajoCalculo;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.app.strategos.web.struts.configuracion.forms.EditarConfiguracionSistemaForm;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcDownloadAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SalvarConfiguracionServicioAction extends VgcAction
{
	
	 private static final int BYTES_DOWNLOAD = 0;

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	 {
	 }
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			    throws Exception
	 {
	    super.execute(mapping, form, request, response);
	    String forward = mapping.getParameter();
	     	
	    ActionMessages messages = getMessages(request);

		Usuario usuario = getUsuarioConectado(request);
	    
	    EditarConfiguracionSistemaForm editarConfiguracionSistemaForm = (EditarConfiguracionSistemaForm) form;
	    
	    String dia = editarConfiguracionSistemaForm.getDia().toString();
	    String hora = editarConfiguracionSistemaForm.getHora();
	    
	    if(dia.equals("0") || hora.equals("")){
	    	
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.configurar.servicio.error"));
			saveMessages(request, messages);	 
	    	
	    }else {
	    
	    String subhora= hora.substring(0,2);
	    String submin= hora.substring(3,5);
	    
	    int hor= Integer.parseInt(subhora);
	    int min= Integer.parseInt(submin);
	    
	    Boolean enviarResSeguimiento = editarConfiguracionSistemaForm.getEnviarResponsableSeguimientoInv();
	    Boolean enviarResCargarEjec = editarConfiguracionSistemaForm.getEnviarResponsableCargarEjecutadoInv();
	    Boolean enviarResCargarMeta = editarConfiguracionSistemaForm.getEnviarResponsableCargarMetaInv();
	    Boolean enviarResFijarMeta = editarConfiguracionSistemaForm.getEnviarResponsableFijarMetaInv();
	    Boolean enviarResLograrMeta = editarConfiguracionSistemaForm.getEnviarResponsableLograrMetaInv();
	    
	    
	    try{
            
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            
            JobKey jobKey = new JobKey("Job1", "group1");
            Boolean isJobKeyExist = scheduler.checkExists(jobKey);
            
            if (isJobKeyExist) {
            	// apagar servicio
            	scheduler.shutdown();
            	scheduler = null;
            	
            	SchedulerFactory sfp = new StdSchedulerFactory();
	            Scheduler schedulerp = sfp.getScheduler();
            	
	            scheduler=schedulerp;
            }
            
            
            JobDetail job = JobBuilder.newJob(TrabajoCalculo.class).withIdentity("Job1", "group1").build();
          
            
            job.getJobDataMap().put(TrabajoCalculo.USER_ID, usuario.getUsuarioId());
            
            
            
            Date startTime = DateBuilder.nextGivenSecondDate(null, 10);
            
                  
            String cronograma="0" +" "+ min+" " +hor+" "+dia+" * ?";
            
            CronTrigger crontrigger = TriggerBuilder
					.newTrigger()
					.withIdentity("Trigger", "group1")
					.startAt(startTime)
					.withSchedule(CronScheduleBuilder.cronSchedule(cronograma))
					.build();
            
            scheduler.start();
            scheduler.scheduleJob(job, crontrigger);
          
            
            
        }catch (SchedulerException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	    
	    	    
	    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.configurar.servicio.sucess"));
		saveMessages(request, messages);	    
	    
	    }
	    
		return mapping.findForward(forward);
	 }
}