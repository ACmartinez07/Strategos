package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionNegativo;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.CorreoIniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.calculos.actions.Correo;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.ReporteServicioService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;


public class CorreoIniciativaResponsable implements Job{

	public static final String USER_ID = "param_1";
	

	public void execute(JobExecutionContext jec) throws JobExecutionException{
		
		
		JobDataMap dataMap = jec.getJobDetail().getJobDataMap();
		
		CalculoIndicadoresForm calculoIndicadoresForm = new CalculoIndicadoresForm();
		UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
		
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
        
                		
		Long usuarioId = dataMap.getLong(USER_ID);
	
		Usuario user = (Usuario)usuariosService.load(Usuario.class, usuarioId);
		
		System.out.println("Inicio ejecución Correos - fecha"+ new Date());
		
		try {
			
			//Calcular(calculoIndicadoresForm, usuarioId);
			informarResponsable(user);
			
			/*
			Correo correo = new Correo();
			correo.sendEmail("smtp.gmail.com", "587", "anrrecai", "amc97111014268", "titulo", "prueba", "amc0197@hotmail.com");
			*/
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		JobKey jobKey = jec.getJobDetail().getKey();
		
		
	}
	
	public String informarResponsable(Usuario usuario) throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		
		List<String> configuracion = obtenerConfiguracion(); 
		List<ReporteServicio> reportes = new ArrayList();
		List<String> responsablesId = new ArrayList();
		List<Iniciativa> iniciativas = new ArrayList();
		String respuesta = "";
		
		Map<String, String> filtros = new HashMap();
		
		
		if(configuracion != null) {
	    	
			String texto= configuracion.get(0);
			String titulo= configuracion.get(1);
			Boolean envResCar= Boolean.parseBoolean(configuracion.get(2));
			Boolean envResMetr= Boolean.parseBoolean(configuracion.get(3));
			Boolean envResFij= Boolean.parseBoolean(configuracion.get(4));
			Boolean envResLog= Boolean.parseBoolean(configuracion.get(5));
			Boolean envResSeg= Boolean.parseBoolean(configuracion.get(6));
			String host = configuracion.get(7);
			String port = configuracion.get(8);
			String user = configuracion.get(9);
			String pass = configuracion.get(10);
			
			if(!port.equals("") || !user.equals("") || !pass.equals("") || !host.equals("")) {
				
				// estatus id = 2
				
				String atributoOrden = null;
			    String tipoOrden = null;
			    
			    //iniciativa
			    validarResponsableIniciativa(usuario, texto, titulo, envResCar, envResMetr, envResFij, envResLog, envResSeg, host, port, user, pass);
			    
			    //indicador
			    validarResponsableIndicador(usuario, texto, titulo, envResCar, envResMetr, envResFij, envResLog, envResSeg, host, port, user, pass);
			  
				
				
			}else {
				respuesta = "no se puede enviar correo, no existe la configuración de correo completa";
			}
			
			
			
	    }else {
			respuesta = "no existe configuración";
		}   

		
		return respuesta;
	}
	
	public List<String> obtenerConfiguracion() throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException{
		
		List<String> lista = new ArrayList();
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
		CorreoIniciativa correoIniciativa = strategosIniciativasService.getCorreoIniciativa();
		String cadena = "";
		
		if(correoIniciativa != null) {
			
			cadena =correoIniciativa.getTexto();
			lista.add(cadena);
			cadena =correoIniciativa.getTitulo();
			lista.add(cadena);
			cadena =correoIniciativa.getEnviarResponsableCargarEjecutado().toString();
			lista.add(cadena);
			cadena =correoIniciativa.getEnviarResponsableCargarMeta().toString();
			lista.add(cadena);
			cadena =correoIniciativa.getEnviarResponsableFijarMeta().toString();
			lista.add(cadena);
			cadena =correoIniciativa.getEnviarResponsableLograrMeta().toString();
			lista.add(cadena);
			cadena =correoIniciativa.getEnviarResponsableSeguimiento().toString();
			lista.add(cadena);
			
		}
				
		cadena = "";
		
		Configuracion configuracion = new Configuracion();
		
		configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Login");
	    if (configuracion != null)
	    {
	    	 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         DocumentBuilder db = dbf.newDocumentBuilder();
	         Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("Configuracion");
	         Element eElement = (Element)nList.item(0);
	         
	         cadena =VgcAbstractService.getTagValue("conexionMAILHost", eElement);
	         lista.add(cadena);
	         cadena =VgcAbstractService.getTagValue("conexionMAILPort", eElement);
	         lista.add(cadena);
	         cadena =VgcAbstractService.getTagValue("conexionMAILUser", eElement);
	         lista.add(cadena);
	         
	         String password = TextEncoder.uRLDecode(VgcAbstractService.getTagValue("conexionMAILPassword", eElement));
	         if (!password.equals(""))
	           password = Password.desencripta(password);
	         cadena =password;
	         
	         lista.add(cadena);
	    }
		
		return lista;
	}
	
	public Boolean validarId(String busqueda, List<String> ids) {
		
		boolean existe = false;
		
		if(ids !=null || ids.size() >0) {
			
			existe = ids.contains(busqueda);
			if (existe) {
				System.out.println("El elemento SÍ existe en la lista");
			} else {
				System.out.println("El elemento no existe");
			}
			
		}		
		
		return existe;
	}
	
	public void enviarCorreoResponsable(String host, String port, String user, String pass, String titulo, String texto, List<String> reponsablesId){
		
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();	
		StrategosResponsablesService strategosResponsableService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		
		
		
		for(String id:reponsablesId) {
			
			Long resId = Long.parseLong(id);
			
			Responsable responsable = (Responsable)strategosResponsableService.load(Responsable.class, resId);
			
			// valida si el responsbale tiene email
			if(responsable.getEmail() != null || !responsable.getEmail().equals("")) {
				
				
				//obtiene mediciones 
				List<ReporteServicio> reportes = reporteServicio.getReporte(resId);
				Correo correo = new Correo();
				String textoIn = texto;
	  			String cuerpo = textoIn;
				
				
				for (Iterator iter = reportes.iterator(); iter.hasNext(); ) 
				{
		  			ReporteServicio reporte = (ReporteServicio)iter.next();
		  			
		  			Iniciativa iniciativa = (Iniciativa) strategosIniciativasService.load(Iniciativa.class, reporte.getIndicadorId());
	  				Organizacion organizacion = (Organizacion)strategosOrganizacionesService.load(Organizacion.class, new Long(iniciativa.getOrganizacionId()));
	  				
	  				
	  				
	  				cuerpo += "\n \n";
	  				cuerpo += "Iniciativa: \n";
	  				cuerpo += "Nombre Organización: "+organizacion.getNombre() +"\n";
	  				cuerpo += "Nombre Iniciativa: "+iniciativa.getNombre() +"\n";
	  				if(iniciativa.getDescripcion() != null) {
	  					cuerpo += "Descripción: "+iniciativa.getDescripcion() +"\n";
	  				}
	  				cuerpo += "Fecha Ultima Medición: "+iniciativa.getFechaUltimaMedicion() +"\n";
	  				cuerpo += "Porcentaje Completado: "+iniciativa.getPorcentajeCompletadoFormateado() +"\n";
	  				
	  				if (iniciativa.getPorcentajeCompletado() != null)
	  				{
	  					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
	  					if (indicador != null)
	  					{
	  						boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
	  						
	  						Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
	  						if (medicionReal != null)
	  						{
	  							iniciativa.setUltimaMedicion(medicionReal.getValor());

	  							List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null, medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
	  							Double programado = null;
	  							for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext(); ) 
	  							{
	  			            		Medicion medicion = (Medicion)iter2.next();
	  			            		if (medicion.getValor() != null && programado == null)
	  			            			programado = medicion.getValor();
	  			            		else if (medicion.getValor() != null && programado != null && acumular)
	  			            			programado = programado + medicion.getValor();
	  			            		else if (medicion.getValor() != null && programado != null && !acumular)
	  			            			programado = medicion.getValor();
	  							}
	  							
	  							if (programado != null)
	  								iniciativa.setPorcentajeEsperado(programado);
	  						}
	  					}
	  				
	  					cuerpo += "Porcentaje Esperado: "+iniciativa.getPorcentajeEsperado() +"\n";
	  				}
	  				
	  				cuerpo += "Año Formulación: "+iniciativa.getAnioFormulacion() +"\n";
	  				if(iniciativa.getUltimaMedicion() != null) {
	  					cuerpo += "Ultima Medición: "+iniciativa.getUltimaMedicion() +"\n";
	  				}
	  				if(iniciativa.getTipoProyecto() != null) {
	  					cuerpo += "Tipo Proyecto: "+iniciativa.getTipoProyecto().getNombre() +"\n";
	  				}
	  				
	  							
	  				
	  				
				}
				
				correo.sendEmail(host, port, user, pass, titulo, cuerpo, responsable.getEmail());
				
			}
						
		}
		
	
		
		strategosResponsableService.close();
		strategosIniciativasService.close();
		strategosOrganizacionesService.close();
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		
		reporteServicio.close();
		
	}
	
	
	public void validarResponsableIniciativa(Usuario usuario, String texto, String titulo, Boolean envResCar, Boolean envResMetr, Boolean envResFij, Boolean envResLog, Boolean envResSeg, String host, String port, String user, String pass ) {
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		
		String atributoOrden = null;
	    String tipoOrden = null;
	    
		List<ReporteServicio> reportes = new ArrayList();
		List<String> responsablesId = new ArrayList();
		List<Iniciativa> iniciativas = new ArrayList();
		String respuesta = "";
		
		Map<String, String> filtros = new HashMap();
		
				
		  		int paginaIniciativa = 0;
			  
			    if (atributoOrden == null) {
			      atributoOrden="nombre";
			    }
			    if (tipoOrden == null) {
			      tipoOrden="ASC";
			    }
			     
			    
			    filtros.put("estatusId", "2");
			    filtros.put("organizacionId", "42");
			    
			    //filtros.put("iniciativaId", "263566");
			    
			    
			    PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(paginaIniciativa, 30, atributoOrden, tipoOrden, true, filtros);
				
			    iniciativas = paginaIniciativas.getLista();	  
			    
			    
			    /*
			    Iniciativa inicia = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, 260897);
			    
			    iniciativas.add(inicia);
			    */
			    
			    int res = iniciativas.size();
				 
			    if(res <=0) {
			    	Correo correo = new Correo();
			    	correo.sendEmail(host, port, user, pass, titulo, "no existe iniciativa", "anrrecai@gmail.com");
			    	
			    }
			    
			    
				for (Iterator iter = iniciativas.iterator(); iter.hasNext(); ) 
				{
		  			Iniciativa iniciativa = (Iniciativa)iter.next();
		  			
		  			if(iniciativa.getResponsableCargarEjecutadoId() != null && envResCar == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(iniciativa.getIniciativaId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(iniciativa.getResponsableCargarEjecutadoId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(iniciativa.getResponsableCargarEjecutadoId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(iniciativa.getResponsableCargarEjecutadoId().toString());
	  					}
	  					
		  			}
		  			
		  			if(iniciativa.getResponsableCargarMetaId() != null && envResMetr == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(iniciativa.getIniciativaId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(iniciativa.getResponsableCargarMetaId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(iniciativa.getResponsableCargarMetaId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(iniciativa.getResponsableCargarMetaId().toString());
	  					}
		  			}
		  			
		  			if(iniciativa.getResponsableFijarMetaId() != null && envResFij == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(iniciativa.getIniciativaId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(iniciativa.getResponsableFijarMetaId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(iniciativa.getResponsableFijarMetaId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(iniciativa.getResponsableFijarMetaId().toString());
	  					}
		  			}
		  			
		  			if(iniciativa.getResponsableLograrMetaId() != null && envResLog == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(iniciativa.getIniciativaId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(iniciativa.getResponsableLograrMetaId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(iniciativa.getResponsableLograrMetaId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(iniciativa.getResponsableLograrMetaId().toString());
	  					}
		  			}
		  			
		  			if(iniciativa.getResponsableSeguimientoId() != null && envResSeg == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(iniciativa.getIniciativaId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(iniciativa.getResponsableSeguimientoId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(iniciativa.getResponsableSeguimientoId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(iniciativa.getResponsableSeguimientoId().toString());
	  					}
		  			}
				}
				
				enviarCorreoResponsable(host, port, user, pass, titulo, texto, responsablesId);
				
				// eliminar registros
				for(Iterator iter =reportes.iterator(); iter.hasNext();) {
					
					ReporteServicio reporte = (ReporteServicio)iter.next();
					reporteServicio.deleteReporte(reporte, usuario);
				}
		
	}
	
	
	public void validarResponsableIndicador(Usuario usuario, String texto, String titulo, Boolean envResCar, Boolean envResMetr, Boolean envResFij, Boolean envResLog, Boolean envResSeg, String host, String port, String user, String pass) {
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
  		
		
		String atributoOrden = null;
	    String tipoOrden = null;
	    
		List<ReporteServicio> reportes = new ArrayList();
		List<String> responsablesId = new ArrayList();
		List<Indicador> indicadores = new ArrayList();
		List<Indicador> indicadoresRegistrar = new ArrayList();
		String respuesta = "";
		
		Map<String, String> filtros = new HashMap();
		
				
		  		int paginaIndicador = 0;
			  
			    if (atributoOrden == null) {
			      atributoOrden="nombre";
			    }
			    if (tipoOrden == null) {
			      tipoOrden="ASC";
			    }
			     
			    //filtros.put("claseId","111844");
			    
			    filtros.put("naturaleza","0");
			    
			    
			    filtros.put("organizacionId", "42");
			    
			    //filtros.put("iniciativaId", "263566");
			    
			    PaginaLista paginaIndicadores = strategosIndicadoresService.getIndicadoresLogroPlan(paginaIndicador, 30, atributoOrden, tipoOrden, true, filtros);
			    indicadores = paginaIndicadores.getLista();
			    
			    //busca indicadores para registrar en el mes actual
			    
			    for(Indicador ind: indicadores) {
			    	
			    	Calendar fecha = Calendar.getInstance();
			        int ano = fecha.get(Calendar.YEAR);
			        int mes = fecha.get(Calendar.MONTH) + 1;
					int periodo =0;
					
					if(ind != null) {
						periodo=obtenerPeriodoFrecuencia(mes, ind.getFrecuencia());
		  				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(ind.getIndicadorId(), SerieTiempo.getSerieRealId(), ano, ano, periodo, periodo);
		  					
		  				if(mediciones.size() == 0) {
		  					indicadoresRegistrar.add(ind);
		  				}
					}
			    	
			    }
			    
						    
			    int res = indicadoresRegistrar.size();
				 
			    if(res <=0) {
			    	Correo correo = new Correo();
			    	correo.sendEmail(host, port, user, pass, titulo, "no existen indicadores", "anrrecai@gmail.com");
			    	
			    }
			    
			    
				for (Iterator iter = indicadoresRegistrar.iterator(); iter.hasNext(); ) 
				{
		  			Indicador indicador = (Indicador)iter.next();
		  			
		  			if(indicador.getResponsableCargarEjecutadoId() != null && envResCar == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(indicador.getIndicadorId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(indicador.getResponsableCargarEjecutadoId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(indicador.getResponsableCargarEjecutadoId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(indicador.getResponsableCargarEjecutadoId().toString());
	  					}
	  					
		  			}
		  			
		  			if(indicador.getResponsableCargarMetaId() != null && envResMetr == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(indicador.getIndicadorId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(indicador.getResponsableCargarMetaId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(indicador.getResponsableCargarMetaId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(indicador.getResponsableCargarMetaId().toString());
	  					}
		  			}
		  			
		  			if(indicador.getResponsableFijarMetaId() != null && envResFij == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(indicador.getIndicadorId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(indicador.getResponsableFijarMetaId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(indicador.getResponsableFijarMetaId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(indicador.getResponsableFijarMetaId().toString());
	  					}
		  			}
		  			
		  			if(indicador.getResponsableLograrMetaId() != null && envResLog == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(indicador.getIndicadorId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(indicador.getResponsableLograrMetaId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(indicador.getResponsableLograrMetaId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(indicador.getResponsableLograrMetaId().toString());
	  					}
		  			}
		  			
		  			if(indicador.getResponsableSeguimientoId() != null && envResSeg == true) {
		  				ReporteServicio reporte = new ReporteServicio();
		  				reporte.setIndicadorId(indicador.getIndicadorId());
		  				reporte.setMedicion("");
		  				reporte.setResponsableId(indicador.getResponsableSeguimientoId());
		  				reporteServicio.saveReporte(reporte, usuario);
	  					reportes.add(reporte);
	  					
	  					
	  					Boolean existe=validarId(indicador.getResponsableSeguimientoId().toString(), responsablesId);
	  					
	  					if(!existe) {
	  						responsablesId.add(indicador.getResponsableSeguimientoId().toString());
	  					}
		  			}
				}
				
				enviarCorreoResponsableIndicador(host, port, user, pass, titulo, texto, responsablesId);
				
				// eliminar registros
				for(Iterator iter =reportes.iterator(); iter.hasNext();) {
					
					ReporteServicio reporte = (ReporteServicio)iter.next();
					reporteServicio.deleteReporte(reporte, usuario);
				}
		
	}
	
public void enviarCorreoResponsableIndicador(String host, String port, String user, String pass, String titulo, String texto, List<String> reponsablesId){
		
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();	
		StrategosResponsablesService strategosResponsableService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		
		
		
		for(String id:reponsablesId) {
			
			Long resId = Long.parseLong(id);
			
			Responsable responsable = (Responsable)strategosResponsableService.load(Responsable.class, resId);
			
			// valida si el responsbale tiene email
			if(responsable.getEmail() != null || !responsable.getEmail().equals("")) {
				
				
				//obtiene mediciones 
				List<ReporteServicio> reportes = reporteServicio.getReporte(resId);
				Correo correo = new Correo();
				String textoIn = texto;
	  			String cuerpo = textoIn;
				
				
				for (Iterator iter = reportes.iterator(); iter.hasNext(); ) 
				{
		  			ReporteServicio reporte = (ReporteServicio)iter.next();
		  			
		  			Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, reporte.getIndicadorId());
	  				Organizacion organizacion = (Organizacion)strategosOrganizacionesService.load(Organizacion.class, new Long(indicador.getOrganizacionId()));
	  				
	  				
	  				
	  				cuerpo += "\n \n";
	  				
	  				cuerpo += "Indicador: \n";
	  				cuerpo += "Nombre Indicador: "+indicador.getNombre() +"\n";
	  				
	  				if(indicador.getClase() !=null) {
	  					cuerpo += "Clase Indicador: "+indicador.getClase().getNombre() +"\n";
	  				}
	  				
	  					  				
	  				cuerpo += "Real: "+indicador.getUltimaMedicionFormateada() +"\n";
	  				
	  				
	  				
	  				if(indicador.getUltimoProgramado() != null && indicador.getUltimoProgramado() >0) {
	  					
	  					cuerpo += "Programado: "+indicador.getUltimoProgramadoFormateado() +"\n";
	  					
	  				}else {
	  					
		  				boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
		  						
		  				Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
		  				if (medicionReal != null)
		  				{
		  					indicador.setUltimaMedicion(medicionReal.getValor());

		  					List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null, medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
		  					Double programado = null;
		  					for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext(); ) 
		  						{
		  			           		Medicion medicion = (Medicion)iter2.next();
		  			           		if (medicion.getValor() != null && programado == null)
		  			           			programado = medicion.getValor();
		  			           		else if (medicion.getValor() != null && programado != null && acumular)
		  			           			programado = programado + medicion.getValor();
		  			           		else if (medicion.getValor() != null && programado != null && !acumular)
		  			           			programado = medicion.getValor();
		  					}
		  							
		  					if (programado != null)
		  						indicador.setUltimoProgramado(programado);
		  				}
		  					
		  					
		  				cuerpo += "Programado: "+indicador.getUltimoProgramadoFormateado() +"\n";
		  				
		  				
	  				}
	  				
	  				cuerpo += "Fecha Ultima Medición: "+indicador.getFechaUltimaMedicion() +"\n";
	  				cuerpo += "Fecha Esperada: "+indicador.getFechaUltimaMedicion()+"\n";
	  				
	  				if(organizacion != null) {
	  					cuerpo += "Organización: "+ organizacion.getNombre() +"\n";
	  				}
	  				
	  				if(indicador.getNaturalezaNombre() != null)
	  				cuerpo += "Naturaleza: "+indicador.getNaturalezaNombre() +"\n";
	  				
	  				if(indicador.getFrecuenciaNombre() != null)
	  				cuerpo += "Frecuencia: "+indicador.getFrecuenciaNombre() +"\n";
	  				
	  				
	  				
				}
				
				correo.sendEmail(host, port, user, pass, titulo, cuerpo, responsable.getEmail());
				
			}
						
		}
		
	
		
		strategosResponsableService.close();
		strategosIniciativasService.close();
		strategosOrganizacionesService.close();
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		
		reporteServicio.close();
		
	}

	public int obtenerPeriodoFrecuencia(int mesAct, Byte frecuencia) {
		int mes=0;
		mes = mesAct;
		
		switch(frecuencia) {
  		
	    case 3:
	    	//mensual
	    	mes = mesAct;
	    	break;
	    case 4:
	    	//bimestral
	    	if(mes <=2){
	    		 mes=1;
	    	}else if(mes >2 && mes <=4){
	    		 mes=2;
	    	}else if(mes >4 && mes <=6){
	    		 mes=3;
	    	}else if(mes >6 && mes <=8){
	    		 mes=4;
	    	}else if(mes >8 && mes <=10){
	    		 mes=5;
	    	}else if(mes >10){
	    		 mes=6;
	    	}
	    	break;
	    case 5:
	    	
	    	if(mes <=3){
	    		mes=1;
	    	}else if(mes >3 && mes <=6){
	    		mes=2;
	    	}else if(mes >6 && mes <=9){
	    		mes=3;
	    	}else if(mes >9){
	    		mes=4;
	    	}
	    	//trimestral
	    	break;
	    case 6:
	    	
	    	if(mes <=4){
	    		mes= 1;
	    	}else if(mes >4 && mes <=8){
	    		mes= 2;
	    	}else if(mes >8){
	    		mes= 3;
	    	}
	    	break;
	    	//cuatrimestral

	    case 7:
	    	if(mes<=6){
	    		mes= 1;
	    	}else if(mes>6){
	    		mes= 2;
	    	}
	    	//semestral
	    	break;
	    case 8:
	    	//anual
	      mes= 1;
	      break;
	}
		
		
		return mes;
	}
	
}
