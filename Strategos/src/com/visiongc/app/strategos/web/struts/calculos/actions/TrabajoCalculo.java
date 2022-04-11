package com.visiongc.app.strategos.web.struts.calculos.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionNegativo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
import com.visiongc.servicio.strategos.calculos.CalcularManager;
import com.visiongc.servicio.strategos.calculos.CalcularManager2;
import com.visiongc.servicio.strategos.servicio.model.Servicio;

public class TrabajoCalculo implements Job{
	
	
	public static final String USER_ID = "param_1";
	/*
	public static final String ALCANCE = "param_2";
	public static final String AÑO = "param_2";
	public static final String AÑOFIN = "param_2";
	public static final String BLOQUEADO = "param_2";
	public static final String CALCULADO = "param_2";
	public static final String CLASE_ID = "param_2";
	public static final String FRECUENCIA = "param_2";
	public static final String INDICADOR_ID = "param_2";
	public static final String INICIATIVA_ID = "param_2";
	public static final String MES_INICIAL = "param_2";
	public static final String MES_FINAL = "param_2";
	public static final String NOMBRE_INDICADOR = "param_2";
	public static final String ORGANIZACION_ID = "param_2";
	public static final String ORIGEN = "param_2";
	public static final String PERSPECTIVA_ID = "param_2";
	public static final String PERIODOS_CERO = "param_2";
	public static final String PLAN_ID = "param_2";
	public static final String SERIE_ID = "param_2";
	public static final String STATUS = "param_2";
	*/
	
	
		
	
	public void execute(JobExecutionContext jec) throws JobExecutionException{
		
		
		JobDataMap dataMap = jec.getJobDetail().getJobDataMap();
		
		CalculoIndicadoresForm calculoIndicadoresForm = new CalculoIndicadoresForm();
		
		String anoF = "";
		String mesF = "";
		
		Calendar fecha = Calendar.getInstance();
        int ano = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        
        if(mes == 1) {
        	mes = 12;
        	ano = ano-1;
        
        	anoF=""+ano;
        }else {
        	mes=mes-1;
        	anoF=""+ano;
        }
		
		// prueba ejecucion
        
        
        // todas las organizaciones
        
        
        calculoIndicadoresForm.setAlcance((byte) 4);
		calculoIndicadoresForm.setAno(anoF);
		calculoIndicadoresForm.setAnoFin("2040");
		calculoIndicadoresForm.setAnoInicio("2000");
		calculoIndicadoresForm.setCalculado(false);
		calculoIndicadoresForm.setMesFinal((byte) mes);
		calculoIndicadoresForm.setMesInicial((byte) mes);
		calculoIndicadoresForm.setOrganizacionId((long) 64);
		calculoIndicadoresForm.setOrigen((byte) 1);
		calculoIndicadoresForm.setPeriodosCero(true);
		calculoIndicadoresForm.setPorNombre(false);
		calculoIndicadoresForm.setPuntoEdicion("");
		calculoIndicadoresForm.setReportarErrores(true);
		calculoIndicadoresForm.setReportarIndicadores(true);
		calculoIndicadoresForm.setSerieId((long) 0);
		calculoIndicadoresForm.setShowPresentacion(false);
		calculoIndicadoresForm.setStatus((byte) 0);
        
		
        //una organizacion
        
        /*
		calculoIndicadoresForm.setAlcance((byte) 2);
		calculoIndicadoresForm.setAno(anoF);
		calculoIndicadoresForm.setAnoFin("2040");
		calculoIndicadoresForm.setAnoInicio("2000");
		calculoIndicadoresForm.setCalculado(false);
		calculoIndicadoresForm.setMesFinal((byte) mes);
		calculoIndicadoresForm.setMesInicial((byte) mes);
		calculoIndicadoresForm.setOrganizacionId((long) 52);
		calculoIndicadoresForm.setOrigen((byte) 1);
		calculoIndicadoresForm.setPeriodosCero(true);
		calculoIndicadoresForm.setPorNombre(false);
		calculoIndicadoresForm.setPuntoEdicion("");
		calculoIndicadoresForm.setReportarErrores(true);
		calculoIndicadoresForm.setReportarIndicadores(true);
		calculoIndicadoresForm.setSerieId((long) 0);
		calculoIndicadoresForm.setShowPresentacion(false);
		calculoIndicadoresForm.setStatus((byte) 0);
		
		*/
		// solo indicador
		
        /*
		calculoIndicadoresForm.setAlcance((byte) 0);
		calculoIndicadoresForm.setAno(anoF);
		calculoIndicadoresForm.setAnoFin("2040");
		calculoIndicadoresForm.setAnoInicio("2000");
		calculoIndicadoresForm.setCalculado(false);
		calculoIndicadoresForm.setClaseId((long)5848);
		calculoIndicadoresForm.setIndicadorId((long)511971);
		calculoIndicadoresForm.setNombreIndicador("Requerimiento 824-3 3");
		calculoIndicadoresForm.setMesFinal((byte) mes);
		calculoIndicadoresForm.setMesInicial((byte) mes);
		calculoIndicadoresForm.setOrganizacionId((long) 43);
		calculoIndicadoresForm.setOrigen((byte) 2);
		calculoIndicadoresForm.setPeriodosCero(true);
		calculoIndicadoresForm.setPorNombre(false);
		calculoIndicadoresForm.setPuntoEdicion("");
		calculoIndicadoresForm.setReportarErrores(true);
		calculoIndicadoresForm.setReportarIndicadores(true);
		calculoIndicadoresForm.setSerieId((long) 0);
		calculoIndicadoresForm.setShowPresentacion(false);
		calculoIndicadoresForm.setStatus((byte) 0);
		
		
		
		//solo clase
		
        /*
		calculoIndicadoresForm.setAlcance((byte) 1);
		calculoIndicadoresForm.setAno(anoF);
		calculoIndicadoresForm.setAnoFin("2040");
		calculoIndicadoresForm.setAnoInicio("2000");
		calculoIndicadoresForm.setCalculado(false);
		calculoIndicadoresForm.setClaseId((long)196428);
		calculoIndicadoresForm.setMesFinal((byte) mes);
		calculoIndicadoresForm.setMesInicial((byte) mes);
		calculoIndicadoresForm.setOrganizacionId((long) 43);
		calculoIndicadoresForm.setOrigen((byte) 2);
		calculoIndicadoresForm.setPeriodosCero(true);
		calculoIndicadoresForm.setPorNombre(false);
		calculoIndicadoresForm.setPuntoEdicion("");
		calculoIndicadoresForm.setReportarErrores(true);
		calculoIndicadoresForm.setReportarIndicadores(true);
		calculoIndicadoresForm.setSerieId((long) 0);
		calculoIndicadoresForm.setShowPresentacion(false);
		calculoIndicadoresForm.setStatus((byte) 0);
		*/
		
		Long usuarioId = dataMap.getLong(USER_ID);
	
		
		System.out.println("Inicio ejecución Calculo - fecha"+ new Date());
		
		try {
			
			
			Calcular(calculoIndicadoresForm, usuarioId);
			
			
			/*
			Correo correo = new Correo();
			correo.sendEmail();
			*/
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		JobKey jobKey = jec.getJobDetail().getKey();
		
		
	}
		
		
		
		
	private void Calcular (CalculoIndicadoresForm calculoForm, Long usuarioId )throws Exception{
		

		ServicioForm servicioForm = new ServicioForm();
	    StringBuffer log = new StringBuffer();
	    
	   
	    FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	    Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
	    
	   
	    if (configuracion == null)
	    {
	      calculoForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
	     
	    }
	    else
	    {
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	      DocumentBuilder db = dbf.newDocumentBuilder();
	      Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
	      doc.getDocumentElement().normalize();
	      NodeList nList = doc.getElementsByTagName("properties");
	      Element eElement = (Element)nList.item(0);
	      
	      String url = VgcAbstractService.getTagValue("url", eElement);
	      String driver = VgcAbstractService.getTagValue("driver", eElement);
	      String user = VgcAbstractService.getTagValue("user", eElement);
	      String password = VgcAbstractService.getTagValue("password", eElement);
	      password = new GestionarServiciosForm().getPasswordConexionDecriptado(password);
	      if (!new FrameworkConnection().testConnection(url, driver, user, password))
	      {
	        calculoForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
	       
	      }
	      else
	      {
	       
	        
	        com.visiongc.commons.util.VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	        log.append(messageResources.getResource("jsp.asistente.calculo.log.titulocalculo") + "\n");
	        
	        Calendar ahora = Calendar.getInstance();
	        String[] argsReemplazo = new String[2];
	        argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	        argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	        log.append(messageResources.getResource("jsp.asistente.calculo.log.fechainiciocalculo", argsReemplazo) + "\n\n");
	        
	        servicioForm.setProperty("url", url);
	        servicioForm.setProperty("driver", driver);
	        servicioForm.setProperty("user", user);
	        servicioForm.setProperty("password", password);
	        
	        servicioForm.setProperty("logMediciones", calculoForm.getReportarIndicadores().toString());
	        servicioForm.setProperty("logErrores", calculoForm.getReportarErrores().toString());
	        servicioForm.setProperty("tomarPeriodosSinMedicionConValorCero", calculoForm.getPeriodosCero().toString());
	        servicioForm.setProperty("planId", calculoForm.getPlanId() != null ? calculoForm.getPlanId().toString() : "");
	        servicioForm.setProperty("organizacionId", calculoForm.getOrganizacionId() != null ? calculoForm.getOrganizacionId().toString() : "");
	        servicioForm.setProperty("perspectivaId", calculoForm.getPerspectivaId() != null ? calculoForm.getPerspectivaId().toString() : "");
	        servicioForm.setProperty("frecuencia", calculoForm.getFrecuencia() != null ? calculoForm.getFrecuencia().toString() : "");
	        if ((calculoForm.getOrigen().byteValue() != calculoForm.getOrigenIniciativas().byteValue()) && (calculoForm.getClaseId() != null))
	        {
	          servicioForm.setProperty("claseId", (calculoForm.getAlcance().byteValue() == calculoForm.getAlcanceClase().byteValue()) && (calculoForm.getClaseId() != null) ? calculoForm.getClaseId().toString() : "");
	        }
	        else if (calculoForm.getIniciativaId() != null)
	        {
	          servicioForm.setProperty("claseId", (calculoForm.getAlcance().byteValue() == calculoForm.getAlcanceIniciativa().byteValue()) && (calculoForm.getIniciativaId() != null) ? calculoForm.getClaseId().toString() : "");
	          servicioForm.setProperty("desdeIniciativas", "true");
	        }
	        else
	        {
	          servicioForm.setProperty("claseId", "");
	        }
	        servicioForm.setProperty("ano", calculoForm.getAno());
	        servicioForm.setProperty("mesInicial", calculoForm.getMesInicial().toString());
	        servicioForm.setProperty("mesFinal", calculoForm.getMesFinal().toString());
	        servicioForm.setProperty("arbolCompletoOrganizacion", Boolean.valueOf(calculoForm.getAlcance().byteValue() == calculoForm.getAlcanceOrganizacion().byteValue()).toString());
	        servicioForm.setProperty("todasOrganizaciones", Boolean.valueOf(calculoForm.getAlcance().byteValue() == calculoForm.getAlcanceOrganizacionTodas().byteValue()).toString());
	        servicioForm.setProperty("indicadorId", calculoForm.getIndicadorId() != null ? calculoForm.getIndicadorId().toString() : "");
	        servicioForm.setProperty("porNombre", calculoForm.getPorNombre().toString());
	        servicioForm.setProperty("nombreIndicador", calculoForm.getNombreIndicador() != null ? calculoForm.getNombreIndicador() : "");
	        
	        servicioForm.setProperty("logConsolaMetodos", Boolean.valueOf(false).toString());
	        servicioForm.setProperty("logConsolaDetallado", Boolean.valueOf(false).toString());
	        
	        servicioForm.setProperty("usuarioId", usuarioId.toString());
	        
	        servicioForm.setProperty("activarSheduler", Boolean.valueOf(true).toString());
	        servicioForm.setProperty("unidadTiempo", Integer.valueOf(3).toString());
	        servicioForm.setProperty("numeroEjecucion", Integer.valueOf(1).toString());
	        
	        StringBuffer logBefore = log;
	        
	        // ejecucion calculo 
	        
	        int anov= new Integer(calculoForm.getAno());
	      
	        
	        new CalcularManager2(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb"))
	        .Ejecutar(calculoForm.getSerieId(), usuarioId, calculoForm.getMesInicial(), anov);
	        log = logBefore;
	        
	        
	        	        
	        calculoForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusCalculado());
	        
	        ahora = Calendar.getInstance();
	        argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	        argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	        
	        log.append("\n\n");
	        log.append(messageResources.getResource("jsp.asistente.calculo.log.fechafin.programada", argsReemplazo) + "\n\n");
	        
	        UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	        	       
	        Usuario usuario = (Usuario)usuariosService.load(Usuario.class, new Long(usuarioId));
	        
	        
	        if (usuario != null)
	        {
	          byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoCalculo();
	          Servicio servicio = new Servicio();
	          servicio.setUsuarioId(usuario.getUsuarioId());
	          servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " " + VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
	          servicio.setNombre(messageResources.getResource("jsp.asistente.calculo.log.titulocalculo"));
	          servicio.setMensaje(messageResources.getResource("jsp.asistente.calculo.log.fechafin.programada", argsReemplazo));
	          servicio.setEstatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusCalculado());
	          
	          frameworkService.registrarAuditoriaEvento(servicio, usuario, tipoEvento);
	        }
	        
	      
	      }
	    }
	    frameworkService.close();
	  }
	
		
}
		
	
	

