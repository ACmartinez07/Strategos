package com.visiongc.app.strategos.web.struts.configuracion.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.util.CorreoIniciativa;
import com.visiongc.app.strategos.web.struts.calculos.actions.TrabajoCalculo;
import com.visiongc.app.strategos.web.struts.configuracion.forms.EditarConfiguracionSistemaForm;
import com.visiongc.app.strategos.web.struts.iniciativas.actions.CorreoIniciativaResponsable;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class SalvarCorreoIniciativaAction extends VgcAction{

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
		 
		 StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		 CorreoIniciativa correoIniciativa = strategosIniciativasService.getCorreoIniciativa();
		 
		 if(correoIniciativa != null) {
			 
		 
			 String dia = correoIniciativa.getDia();
			 String hora = correoIniciativa.getHora();
			    
			 if(dia.equals("") || hora.equals("")){
			    	
			   	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.configurar.servicio.error"));
				saveMessages(request, messages);	 
			    	
			 }else {
			    
			    String subhora= hora.substring(0,2);
			    String submin= hora.substring(3,5);
			    
			    int hor= Integer.parseInt(subhora);
			    int min= Integer.parseInt(submin);
			    int diap= Integer.parseInt(dia);
			   		    
			    try{
		            
		            SchedulerFactory sf = new StdSchedulerFactory();
		            Scheduler scheduler = sf.getScheduler();
		         
		            
		            JobKey jobKey = new JobKey("Job2", "group2");
		            Boolean isJobKeyExist = scheduler.checkExists(jobKey);
		            
		            if (isJobKeyExist) {
		            	// apagar servicio
		            	scheduler.shutdown();
		            	scheduler = null;
		            	
		            	SchedulerFactory sfp = new StdSchedulerFactory();
			            Scheduler schedulerp = sfp.getScheduler();
		            	
			            scheduler=schedulerp;
		            }
		            	
			            JobDetail job = JobBuilder.newJob(CorreoIniciativaResponsable.class).withIdentity("Job2", "group2").build();
			          
			            
			            job.getJobDataMap().put(CorreoIniciativaResponsable.USER_ID, usuario.getUsuarioId());
			            
			            
			            
			            Date startTime = DateBuilder.nextGivenSecondDate(null, 10);
			            
			                  
			            String cronograma="0" +" "+ min+" " +hor+" "+diap+" * ?";
			            
			            CronTrigger crontrigger = TriggerBuilder
								.newTrigger()
								.withIdentity("Trigger", "group2")
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
		
		 
		 }else {
			 messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.configurar.servicio.error"));
			 saveMessages(request, messages);
		 }
		 
		 
		 
		 strategosIniciativasService.close();   
		    
		 return getForwardBack(request, 1, true);
	}
}
