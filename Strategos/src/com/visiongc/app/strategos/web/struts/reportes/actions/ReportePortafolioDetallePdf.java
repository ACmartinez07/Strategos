package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.taglibs.standard.lang.jpath.adapter.Convert;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jmx.HibernateService;
import org.w3c.dom.NodeList;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.persistence.hibernate.StrategosIniciativasHibernateSession;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;

public class ReportePortafolioDetallePdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("jsp.reportes.portafolio.ejecucion.detallado");
	}
	
	
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String portafolioId = (request.getParameter("portafolioId"));
		String filtroNombre = (request.getParameter("filtroNombre"));
		//String estatus = (request.getParameter("tipo"));
		
		
		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
		
		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        
       
		
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		
		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);
	
		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		StrategosPortafoliosService portafoliosservice = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();
					
		Portafolio portafolio = (Portafolio)portafoliosservice.load(Portafolio.class,new Long(portafolioId));	
		
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(8);
		font.setStyle(Font.BOLD);
		
		
		texto = new Paragraph("Portafolio: "+ portafolio.getNombre(), font);
		texto.setAlignment(Element.ALIGN_LEFT);
		texto.setIndentationLeft(16);
		documento.add(texto);
		
		
		documento.add(lineaEnBlanco(fuente));
		
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[5];
        
               
        columnas[0] = 20;
        columnas[1] = 4;
        columnas[2] = 3;
        columnas[3] = 4;
        columnas[4] = 4;
		
        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);
        
        tabla.setAlineacionHorizontal(0);
        
        tabla.agregarCelda(messageResources.getMessage("action.reportecomiteejecutivo.organizacion"));
        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.ultimoperiodocalculo"));
        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.porcentajecompletado"));
        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.estatus"));
        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.frecuencia"));
        
        
		
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
		//
        
        if(portafolio.getOrganizacion() != null) {
        	tabla.agregarCelda(portafolio.getOrganizacion().getNombre());
        }else {
        	tabla.agregarCelda("");
        }
                
        
        tabla.agregarCelda(portafolio.getFechaUltimoCalculo());
        
        if(portafolio.getPorcentajeCompletadoFormateado() != null) {
        	tabla.agregarCelda(portafolio.getPorcentajeCompletadoFormateado());
        }else {
        	tabla.agregarCelda("");
        }
        
        if(portafolio.getEstatus().getNombre() != null) {
        	tabla.agregarCelda(portafolio.getEstatus().getNombre());
        }else {
        	tabla.agregarCelda("");
        }
        
        if(portafolio.getFrecuenciaNombre() != null) {
        	tabla.agregarCelda(portafolio.getFrecuenciaNombre());
        }else {
        	tabla.agregarCelda("");
        }
        
		
        documento.add(tabla.getTabla());
		
        //
        
        font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(8);
		font.setStyle(Font.BOLD);
		
        
		documento.add(lineaEnBlanco(fuente));
		
		List<PortafolioIniciativa> iniciativasPortafolio = portafoliosservice.getIniciativasPortafolio(new Long(portafolioId));
		
		
		for(PortafolioIniciativa iniX: iniciativasPortafolio) {
			
			//iniciativa
			
			Long iniciativaId = iniX.getPk().getIniciativaId();
			PryProyecto proyecto = null;
			
			Iniciativa iniciativa = (Iniciativa)iniciativaservice.load(Iniciativa.class, iniciativaId);
						
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
			
			Indicador indicador = (Indicador)iniciativaservice.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			
			List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
			List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
			
			if (iniciativa.getProyectoId() != null)
			{
				StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
				proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
				strategosPryProyectosService.close();
			}
			
			texto = new Paragraph("Iniciativa: "+ iniciativa.getNombre(), font);
			texto.setAlignment(Element.ALIGN_LEFT);
			texto.setIndentationLeft(16);
			documento.add(texto);
			
			documento.add(lineaEnBlanco(fuente));
			
			TablaPDF tablaIni = null;
	        tablaIni = new TablaPDF(getConfiguracionPagina(request), request);
	        int[] columnasIni = new int[6];
			
	        
	        columnasIni[0] = 5;
	        columnasIni[1] = 10;
	        columnasIni[2] = 10;
	        columnasIni[3] = 8;
	        columnasIni[4] = 8;
	        columnasIni[5] = 25;
			
	        tablaIni.setAmplitudTabla(100);
	        tablaIni.crearTabla(columnasIni);
	        
	        tablaIni.setAlineacionHorizontal(1);
	        
	        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
	        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
	        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
	        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
	        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
	        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));
	        
	      
			
	        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
			
	        Double programado = 0D;
		    Double porcentajeEsperado = 0D;
		    for (Iterator<Medicion> iterEjecutado = medicionesEjecutado.iterator(); iterEjecutado.hasNext();)
		    {
		    	Medicion ejecutado = (Medicion)iterEjecutado.next();
				for (Iterator<Medicion> iterMeta = medicionesProgramado.iterator(); iterMeta.hasNext();)
				{
					Medicion meta = (Medicion)iterMeta.next();
					if (ejecutado.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue() &&
							ejecutado.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo().intValue())
					{
						if (meta.getValor() != null)
							programado = programado + meta.getValor();
						break;
					}
				}
		    }
			if (programado.doubleValue() != 0)
				porcentajeEsperado = (porcentajeEsperado * 100D) / 100D;
	        
			if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
				tablaIni.agregarCelda(indicador.getUnidad().getNombre());
		    else
		    	tablaIni.agregarCelda("");
			
			tablaIni.agregarCelda(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
			tablaIni.agregarCelda(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
			
			tablaIni.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
			tablaIni.agregarCelda(VgcFormatter.formatearNumero(programado));
			
			if(iniciativa.getResponsableSeguimiento() !=null) {
				tablaIni.agregarCelda(iniciativa.getResponsableSeguimiento().getNombre());
			}else {
				tablaIni.agregarCelda("");
			}
			
						
			documento.add(tablaIni.getTabla());
			
			documento.add(lineaEnBlanco(fuente));
			
			

			// actividades
			
			List<PryActividad> actividades = new ArrayList<PryActividad>();
			
			if(iniciativa.getProyectoId() != null){
				actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
				
				if(actividades != null && actividades.size() > 0) {
					
					texto = new Paragraph("Listado de Actividades", font);
					texto.setAlignment(Element.ALIGN_LEFT);
					texto.setIndentationLeft(16);
					documento.add(texto);
					
					documento.add(lineaEnBlanco(fuente));
						
					
					TablaPDF tablaAct = null;
			        tablaAct = new TablaPDF(getConfiguracionPagina(request), request);
			        int[] columnasAct = new int[9];
					
			        
			        columnasAct[0] = 25;
			        columnasAct[1] = 5;
			        columnasAct[2] = 8;
			        columnasAct[3] = 8;
			        columnasAct[4] = 5;
			        columnasAct[5] = 6;
			        columnasAct[6] = 6;
			        columnasAct[7] = 6;
			        columnasAct[8] = 20;
					
			        tablaAct.setAmplitudTabla(100);
			        tablaAct.crearTabla(columnasAct);
			        
			        tablaAct.setAlineacionHorizontal(1);
			        
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reporte.actividad.nombre.actividad"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));
			        tablaAct.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));
			        
					
			        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
				    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
					
					for(PryActividad act: actividades) {
						
						
				        
				        tablaAct.agregarCelda(act.getNombre());
				        
				        if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
				        	tablaAct.agregarCelda(indicador.getUnidad().getNombre());					    	
					    else
					    	tablaAct.agregarCelda("");
				        
				        tablaAct.agregarCelda(VgcFormatter.formatearFecha(act.getComienzoPlan(),"formato.fecha.corta"));
				        tablaAct.agregarCelda(VgcFormatter.formatearFecha(act.getFinPlan(), "formato.fecha.corta"));
				        tablaAct.agregarCelda (VgcFormatter.formatearNumero(act.getPeso()));
				        tablaAct.agregarCelda(VgcFormatter.formatearNumero(act.getDuracionPlan()));
				        
				        tablaAct.agregarCelda(act.getPorcentajeEjecutado() != null ? act.getPorcentajeEjecutadoFormateado() : "");
				        tablaAct.agregarCelda(act.getPorcentajeEsperado() != null ? act.getPorcentajeEsperadoFormateado() : "");
				        
				        if(act.getResponsableSeguimiento() !=null) {
							tablaAct.agregarCelda(act.getResponsableSeguimiento().getNombre());
						}else {
							tablaAct.agregarCelda("");
						}
						
				        
				        
				        
					}
					
					documento.add(tablaAct.getTabla());
					
					documento.add(lineaEnBlanco(fuente));
				}
			}
			
			
			
			
			
		}
        
		documento.newPage();
        organizacionservice.close();
        iniciativaservice.close(); 
		
	}
	
		
	
	}
	
	

