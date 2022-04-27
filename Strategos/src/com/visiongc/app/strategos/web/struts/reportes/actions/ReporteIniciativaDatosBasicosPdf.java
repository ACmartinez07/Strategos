package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaDatosBasicosPdf extends VgcReporteBasicoAction{

	@Override
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.datos.basicos");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String alcance = (request.getParameter("alcance"));
		String orgId = (request.getParameter("organizacionId"));
		String tipo = (request.getParameter("tipo"));
		String estatus = (request.getParameter("estatus"));	
		String ano = (request.getParameter("ano"));		
		int estatusId = Integer.parseInt(estatus);		
		
		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
		
		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
		
				
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;		
	
		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		
		List<OrganizacionStrategos> organizaciones = organizacionservice.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();
		
		// organizacion seleccionada
		if(request.getParameter("alcance").equals("1")){					
			
			OrganizacionStrategos org = (OrganizacionStrategos)organizacionservice.load(OrganizacionStrategos.class,  ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			
			if(org != null) {
				
				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
								
			    //Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);
				
				Paragraph textoOrg = new Paragraph("Organización: "+ org.getNombre(), font);
				textoOrg.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoOrg);
				
				documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			}
				
				
			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();
	
			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			filtro.setAnio(""+ano);
			if (filtroNombre.equals(""))
				filtro.setNombre(null);
			else
				filtro.setNombre(filtroNombre);		
			
			reporte.setFiltro(filtro);
			
	
		    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
		    	filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if(!tipo.equals("0")) {
				filtros.put("tipoId", tipo);
			}
																		
	    	Font fuente = getConfiguracionPagina(request).getFuente();
	        MessageResources messageResources = getResources(request);
	        
	       
	        
	        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
	               
	        int index = iniciativas.size();       	      	        
	        
		        if (iniciativas.size() > 0)
				{
					for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
					{
						Iniciativa iniciativa = (Iniciativa)iter.next();
						
							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
							
							Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
							
							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
							
							List<PryActividad> actividades = new ArrayList<PryActividad>();
							
							
							if(iniciativa.getProyectoId() != null){
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}
							dibujarTabla0(messageResources, request, documento);
							if(estatusId == 8) {
																
								dibujarTabla1(iniciativa,messageResources, request, documento, null, true);																
								
								dibujarTabla2(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla3(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla4(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla5(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla6(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla7(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla8(iniciativa,  messageResources, request, documento, null, true);
								
								documento.add(lineaEnBlanco(fuente));
								
								dibujarTabla9(messageResources, request, documento);
								
								dibujarTabla10(iniciativa,  messageResources, request, documento, null, true);
								
								documento.add(lineaEnBlanco(fuente));
								documento.add(lineaEnBlanco(fuente));
							}else {
								
								Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
								
								if(est) {
									dibujarTabla1(iniciativa,messageResources, request, documento, null, true);																
									
									dibujarTabla2(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla3(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla4(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla5(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla6(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla7(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla8(iniciativa,  messageResources, request, documento, null, true);
									
									documento.add(lineaEnBlanco(fuente));
									
									dibujarTabla9(messageResources, request, documento);
									
									dibujarTabla10(iniciativa,  messageResources, request, documento, null, true);
									
									documento.add(lineaEnBlanco(fuente));
									documento.add(lineaEnBlanco(fuente));
								}
							}
							
					}
					
				} 
	        
        
		}
		// suborganizaciones 
		else if(request.getParameter("alcance").equals("4")) {
			
			Map<String, Object> filtro = new HashMap<String, Object>();
			
			filtro.put("padreId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			
			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId(), true);
			
			Font fuente = getConfiguracionPagina(request).getFuente();
	        MessageResources messageResources = getResources(request);
	        
			
	        
	        filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if(!tipo.equals("0")) {
				filtros.put("tipoId", tipo);
			}			
			
	        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
	               
	        int index = iniciativas.size();       
	      	        	        
		        if (iniciativas.size() > 0)
				{
					for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
					{
						Iniciativa iniciativa = (Iniciativa)iter.next();
						
							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
							
							Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
							
							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
							
							List<PryActividad> actividades = new ArrayList<PryActividad>();
							
							
							if(iniciativa.getProyectoId() != null){
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}
							
							dibujarTabla0(messageResources, request, documento);
									
							if(estatusId == 8) {
								dibujarTabla1(iniciativa,messageResources, request, documento, null, true);																
								
								dibujarTabla2(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla3(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla4(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla5(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla6(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla7(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla8(iniciativa,  messageResources, request, documento, null, true);
								
								documento.add(lineaEnBlanco(fuente));
								
								dibujarTabla9(messageResources, request, documento);
								
								dibujarTabla10(iniciativa,  messageResources, request, documento, null, true);
								
								documento.add(lineaEnBlanco(fuente));
								documento.add(lineaEnBlanco(fuente));
							}else {
								
								Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
								
								if(est) {
									dibujarTabla1(iniciativa,messageResources, request, documento, null, true);																
									
									dibujarTabla2(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla3(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla4(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla5(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla6(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla7(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla8(iniciativa,  messageResources, request, documento, null, true);
									
									documento.add(lineaEnBlanco(fuente));
									
									dibujarTabla9(messageResources, request, documento);
									
									dibujarTabla10(iniciativa,  messageResources, request, documento, null, true);
									
									documento.add(lineaEnBlanco(fuente));
									documento.add(lineaEnBlanco(fuente));
								}
							}
						
					}
					
				} 
		        
		        
		        
		    //suborganizaciones   	        
			if(organizacionesSub.size() > 0 || organizacionesSub != null) {
				
				
				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();)
				{
					
					OrganizacionStrategos organizacion = (OrganizacionStrategos)iter.next();
				
				    filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
					if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
						filtros.put("historicoDate", "IS NULL");
					else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
						filtros.put("historicoDate", "IS NOT NULL");
					if (reporte.getFiltro().getNombre() != null)
						filtros.put("nombre", reporte.getFiltro().getNombre());
					if(!tipo.equals("0")) {
						filtros.put("tipoId", tipo);
					}									
					
			        List<Iniciativa> iniciativasSub = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
		               
		       
			        if (iniciativasSub.size() > 0)
					{
						for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();)
						{
							Iniciativa iniciativa = (Iniciativa)iter1.next();
							
							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
							
							Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
							
							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
							
							List<PryActividad> actividades = new ArrayList<PryActividad>();
							
							
							if(iniciativa.getProyectoId() != null){
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}
							
							dibujarTabla0(messageResources, request, documento);
							
							if(estatusId == 8) {
								dibujarTabla1(iniciativa,messageResources, request, documento, null, true);																
								
								dibujarTabla2(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla3(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla4(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla5(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla6(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla7(iniciativa,  messageResources, request, documento, null, true);
								
								dibujarTabla8(iniciativa,  messageResources, request, documento, null, true);
								
								documento.add(lineaEnBlanco(fuente));
								
								dibujarTabla9(messageResources, request, documento);
								
								dibujarTabla10(iniciativa,  messageResources, request, documento, null, true);
								
								documento.add(lineaEnBlanco(fuente));
								documento.add(lineaEnBlanco(fuente));
							}else {
								
								Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
								
								if(est) {

									dibujarTabla1(iniciativa,messageResources, request, documento, null, true);																
									
									dibujarTabla2(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla3(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla4(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla5(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla6(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla7(iniciativa,  messageResources, request, documento, null, true);
									
									dibujarTabla8(iniciativa,  messageResources, request, documento, null, true);
									
									documento.add(lineaEnBlanco(fuente));
									
									dibujarTabla9(messageResources, request, documento);
									
									dibujarTabla10(iniciativa,  messageResources, request, documento, null, true);
									
									documento.add(lineaEnBlanco(fuente));
									documento.add(lineaEnBlanco(fuente));
								}
							}
						}						
					}		        	
				}								
			}									
		}
	}
	
	public String obtenerObjetivo(Iniciativa iniciativa) throws SQLException{
		String objetivo=null;
		Long id=iniciativa.getIniciativaId();
			
		Map<String, Object> filtros = new HashMap<String, Object>();
			
		if((iniciativa.getIniciativaPerspectivas() != null) && (iniciativa.getIniciativaPerspectivas().size() > 0)){
				
		IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iniciativa.getIniciativaPerspectivas().toArray()[0];
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
	            
	    objetivo= perspectiva.getNombre();
            
		}
		return objetivo;
	}
		
		
	public void dibujarTabla0(MessageResources messageResources, HttpServletRequest request, Document documento) throws Exception {	
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[2];
        	
        columnas = new int[1];
        
        columnas[0] = 100;
        
        tabla.setAmplitudTabla(60);
        tabla.crearTabla(columnas);        
        tabla.setAlineacionHorizontal(1);
        tabla.setFormatoFont(Font.BOLD);
        
        tabla.agregarCelda("Formato Formulacion iniciativa");
        tabla.agregarCelda("REG-PE-00-002");
        
        tabla.setAmplitudTabla(100);
        tabla.setColorFondo(255, 217, 102);
        tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.datos.basicos"));
        
        documento.add(tabla.getTabla());
        
	}
	
	public void dibujarTabla1(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
		
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[7];
		
       
       	columnas = new int[6];
        	
	    columnas[0] = 10;
	    columnas[1] = 10;
	    columnas[2] = 10;
	    columnas[3] = 10;
	    columnas[4] = 10;
	    columnas[5] = 10;
	        
	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencia"));
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getOrganizacion().getNombre());   	   
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.proyecto"));	  
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getResponsableProyecto());
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.cargo"));   
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getCargoResponsable());
	        
		documento.add(tabla.getTabla());        
	}
	
	public void dibujarTabla2(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[5];
		       
       	columnas = new int[4];
        	
	    columnas[0] = 10;
	    columnas[1] = 20;
	    columnas[2] = 10;
	    columnas[3] = 20;	    	        
	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	        
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.proyecto"));
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getNombre());
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.vigencia"));
        tabla.setColorFondo(255, 255, 255);
        tabla.setFormatoFont(Font.NORMAL);
        tabla.agregarCelda(iniciativa.getAnioFormulacion());
	        
		documento.add(tabla.getTabla());   
	}
		
		
	public void dibujarTabla3(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[5];
		       
       	columnas = new int[4];
        	
	    columnas[0] = 10;
	    columnas[1] = 20;
	    columnas[2] = 10;
	    columnas[3] = 20;	    	        
	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);	
	    
	    tabla.setColorFondo(255, 217, 102);	 
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.estrategico"));
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getObjetivoEstrategico());	
	    
	    tabla.setColorFondo(255, 217, 102);	   
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.iniciativa.estrategica"));
        tabla.setColorFondo(255, 255, 255);
        tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getIniciativaEstrategica());		
        
	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla4(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[5];
		       
       	columnas = new int[4];
        	
	    columnas[0] = 10;
	    columnas[1] = 10;
	    columnas[2] = 10;
	    columnas[3] = 30;	    	        
	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	        
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.lider.iniciativa"));
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getLiderIniciativa());
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.tipo.iniciativa"));
        tabla.setColorFondo(255, 255, 255);
        tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getTipoIniciativa());	
        	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla5(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[3];
		       
       	columnas = new int[2];
        	
	    columnas[0] = 50;
	    columnas[1] = 50;
	        	        	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	        
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setAlineacionHorizontal(1);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencias"));
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.poblacion.beneficiada"));
        
        tabla.setColorFondo(255, 255, 255);
        tabla.setFormatoFont(Font.NORMAL);
        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(iniciativa.getOrganizacionesInvolucradas());
        tabla.agregarCelda(iniciativa.getPoblacionBeneficiada());
        	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla6(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[2];
		       
       	columnas = new int[1];
        	
	    columnas[0] = 100;	    
	        	        	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    
	    tabla.setAlineacionHorizontal(1);
	        	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.contexto"));
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getContexto());
        
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.definicion.problema"));    
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
        tabla.agregarCelda(iniciativa.getDefinicionProblema());
        	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla7(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[3];
		       
       	columnas = new int[2];
        	
	    columnas[0] = 50;	
	    columnas[1] = 50;	
	        	        	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    
	    tabla.setColorFondo(255, 217, 102);    
	    tabla.setAlineacionHorizontal(1);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.causas"));    
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.efectos"));    
	    
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.setDefaultAlineacionHorizontal();
	    tabla.agregarCelda("aqui las causas del problema");                      
        tabla.agregarCelda("efectos del problema");
        	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla8(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[2];
		       
       	columnas = new int[1];
        	
	    columnas[0] = 100;	    
	        	        	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    
	    tabla.setAlineacionHorizontal(1);
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.alcance"));    
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getAlcance());
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.general"));    
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getObjetivoGeneral());
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivos.especificos")); 
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getObjetivoEspecificos());
        	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla9(MessageResources messageResources, HttpServletRequest request, Document documento) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[2];
		       
       	columnas = new int[1];
        	
	    columnas[0] = 100;	    
	        	        	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    
	    tabla.setAlineacionHorizontal(1);
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	        
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.financiacion")); 	    
        	        
		documento.add(tabla.getTabla());   
	}
	
	public void dibujarTabla10(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request, Document documento,  OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception{
		TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[6];
		       
       	columnas = new int[5];
        	
	    columnas[0] = 10;
	    columnas[1] = 15;
	    columnas[2] = 10;
	    columnas[3] = 15;	
	    columnas[4] = 10;	
	        
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	        
	    tabla.setColorFondo(255, 217, 102);	    
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.fuente")); 	 
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.agregarCelda(iniciativa.getFuenteFinanciacion());
	    
	    
	    tabla.setColorFondo(200, 200, 200);
	    tabla.agregarCelda("");
	    
	    tabla.setColorFondo(255, 217, 102);
	    tabla.setFormatoFont(Font.BOLD);
	    tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.monto")); 	 
        tabla.setColorFondo(255, 255, 255);
        tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getMontoFinanciamiento());	
        	        
		documento.add(tabla.getTabla());   
	}
		
	
		
		public Boolean tieneEstatus(Iniciativa iniciativa, List<Medicion> medicionesEjecutadas, List<Medicion> medicionesProgramadas, int estatus){
			
			Boolean tiene=false;
			//estatus
			if (medicionesProgramadas.size() == 0 && estatus == 0) {
				//EstatusIniciar
				tiene = true;
			}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0 && estatus == 1) {
				//EstatusIniciardesfasado
				tiene = true;
			}					
			else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D && estatus == 2) {
				//EnEjecucionSinRetrasos
				tiene = true;
			}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue() && estatus == 3) {
				//EnEjecucionConRetrasosSuperables
				tiene = true;
			}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue() && estatus == 4) {
				//EnEjecucionConRetrasosSignificativos
				tiene = true;
			}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D && estatus == 5) {
				//EstatusConcluidas
				tiene = true;
			}
			else if(iniciativa.getEstatusId() == 3 && estatus == 6) {
				//EstatusCancelado
				tiene = true;
			}
			else if(iniciativa.getEstatusId() == 4 && estatus == 7) {
				//EstatusSuspendido
				tiene = true;
			}else if(iniciativa.getEstatusId() == 1  && estatus == 0) {
				//EstatusSuspendido
				tiene = true;
			}
			
			return tiene;
		}
		
	}
	
	

