package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;

public class ReporteProyectosAsociadosPdf extends VgcReporteBasicoAction{

	private static final String Plan = null;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("jsp.pagina.instrumentos.reporte.proyectos.asociados");
	}
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
		/*Parametros para el reporte*/
		String source = request.getParameter("source");
		
		String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
		if(anio != "") {
			reporte.setAno(new Integer(anio));
		}else {
			reporte.setAno(null);
		}
		
		Date fecha = new Date();
		Integer ano= fecha.getYear();		
		
		
		reporte.setAnoInicial(ano.toString());
		reporte.setAnoFinal(ano.toString());
		reporte.setMesInicial("1");
		reporte.setMesFinal("12");
		
		
		reporte.setCooperanteId(request.getParameter("cooperante") != null && !request.getParameter("cooperante").toString().equals("") ? Long.parseLong(request.getParameter("cooperante")) : null);
		reporte.setTipoCooperanteId(request.getParameter("tipoconvenio") != null && !request.getParameter("tipoconvenio").toString().equals("") ? Long.parseLong(request.getParameter("tipoconvenio")) : null);
		reporte.setNombre(request.getParameter("nombre") != null ? request.getParameter("nombre") : "");
		reporte.setId(request.getParameter("instrumentoId") != null && !request.getParameter("instrumentoId").toString().equals("") ? Long.parseLong(request.getParameter("instrumentoId")) : null);
		String alcance = (request.getParameter("alcance"));		
		
		EjecucionInstrumento(reporte, documento, request, mensajes, source, alcance);
		
	}
	
	private TablaBasicaPDF crearTabla(boolean newTable, boolean isInformativo, String[][] columnas, ReporteForm reporte, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		Color colorLetra = null;  
		Color colorFondo = null;
		Integer anchoBorde = null;
		Integer anchoBordeCelda = null;
		Boolean bold = true;
		Integer alineacionHorizontal = null;
		Integer alineacionVertical = null;
		
		if (isInformativo)
		{
			colorLetra = new Color(0, 0, 0); 
			colorFondo = new Color(255, 255, 255);			
			anchoBorde = 0;
			anchoBordeCelda = 0;
			bold = false;
			alineacionHorizontal = TablaBasicaPDF.H_ALINEACION_LEFT;
			alineacionVertical = TablaBasicaPDF.V_ALINEACION_TOP;
		}
		else
		{
			colorLetra = new Color(255, 255, 255); 
			colorFondo = new Color(128, 128, 128);			
		}
		
		if (tablaHeader != null && newTable)
			tablaHeader = null;
		
		TablaBasicaPDF tabla = inicializarTabla(font, columnas, anchoBorde, anchoBordeCelda, bold, colorLetra, colorFondo, alineacionHorizontal, alineacionVertical, request);
				
		if (!isInformativo)
		{
		    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
		    tabla.setFont(Font.NORMAL);
		    tabla.setFormatoFont(Font.NORMAL);
		    tabla.setColorLetra(0, 0, 0);
		    tabla.setColorFondo(255, 255, 255);
		    tabla.setTamanoFont(7);
		}
	    
		return tabla;
	}
	
	private void EjecucionInstrumento(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String source, String alcance) throws Exception
	{
		//Raiz del plan
	    lineas = 2;

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);
		
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		Integer nivel = 0;
		inicioTamanoPagina = lineasxPagina(font);
	    tamanoPagina = inicioTamanoPagina;
	    
	    //instrumento seleccionado
	    if(alcance.equals("1")) {
	    	
	    	Map<String, String> filtros = new HashMap<String, String>();
	    	
	    	int pagina = 0;
		    String atributoOrden = null;
		    String tipoOrden = null;
	    	
	    	StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
			StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
			
	    	
	    	Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, reporte.getId());
	    	
	    	if(instrumento != null) {
	    		dibujarInformacionInstrumento(reporte, font, source, instrumento, documento, mensajes, request);
	    		
	    		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
	    		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
	    		
	    		filtros = new HashMap();
	    		
	    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
	    		
	    		
	    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, atributoOrden, tipoOrden, true, filtros);
	    		PaginaLista paginaPlanes = strategosPlanesService.getPlanes(pagina, 30, atributoOrden, tipoOrden, true, filtros);
	    		
	    		EjecucionIniciativa(reporte, documento, request, mensajes, source, paginaIniciativas.getLista(), paginaPlanes.getLista());
	    	
	    	}
	    	
	    	
	    }else {
	    	
	    	StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();			
	    	
			Map<String, String> filtros = new HashMap<String, String>();
		    int pagina = 0;
		    String atributoOrden = null;
		    String tipoOrden = null;

		    if (atributoOrden == null) 
		    	atributoOrden = "nombreCorto";
		    if (tipoOrden == null) 
		    	tipoOrden = "ASC";
		    if (pagina < 1) 
		    	pagina = 1;
		    
		    if(reporte.getNombre() != null && reporte.getNombre() != "") {
		    	filtros.put("nombreCorto", reporte.getNombre());
		    }else if(reporte.getAno() != null && reporte.getAno() != 0) {
		    	filtros.put("anio", reporte.getAno().toString());
		    }else if(reporte.getCooperanteId() != null && reporte.getCooperanteId() != 0) {
		    	filtros.put("cooperanteId", reporte.getCooperanteId().toString());
		    }else if(reporte.getTipoCooperanteId() != null && reporte.getTipoCooperanteId() != 0) {
		    	filtros.put("tiposConvenioId", reporte.getTipoCooperanteId().toString());
		    }
		        
		    
		    PaginaLista paginaInstrumentos = strategosInstrumentosService.getInstrumentos(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		    
		    if (paginaInstrumentos.getLista().size() > 0) 
			{
							
		    	for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext(); ) 
				{
		    		Instrumentos instrumento = (Instrumentos)iter.next();
		    		
		    		dibujarInformacionInstrumento(reporte, font, source, instrumento, documento, mensajes, request);
		    		
		    		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		    		//StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		    		
		    		filtros = new HashMap();
		    		
		    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
		    		
		    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, null, tipoOrden, true, filtros);	
		    		//PaginaLista paginaPlanes = strategosPlanesService.getPlanes(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		    		List paginaPlanes = null;
		    		
		    		EjecucionIniciativa(reporte, documento, request, mensajes, source, paginaIniciativas.getLista(), paginaPlanes);		    		
		    				    		
				}
			}
	    }
	}
	
	private void dibujarInformacionInstrumento(ReporteForm reporte, Font font, String source, Instrumentos instrumento, Document documento, MessageResources mensajes, HttpServletRequest request) throws Exception{
		
		StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
		StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
			
		Paragraph texto;
		font.setStyle(Font.NORMAL);

			
		if(instrumento != null) {
			
				documento.add(lineaEnBlanco(font));
				
    			texto = new Paragraph("Instrumento" + " : " + instrumento.getNombreCorto(), font);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);
    			    			
				documento.add(lineaEnBlanco(font));
				
				// Dibujar Informacion de la Iniciativa
    			crearTablaInstrumento(reporte, instrumento, font, mensajes, documento, request);				
		}
		else
		{
			documento.add(lineaEnBlanco(font));
			

			font.setColor(0, 0, 255);
			texto = new Paragraph("No hay datos de los instrumentos para presentar", font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			
			documento.add(lineaEnBlanco(font));
			
		}
		
		strategosTiposConvenioService.close();
		strategosCooperantesService.close();
	}

	private void crearTablaInstrumento(ReporteForm reporte, Instrumentos instrumento, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception{
	
		StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
		StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();

	
		String encabezado = mensajes.getMessage("jsp.pagina.instrumentos.tipo") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.pagina.instrumentos.cooperante") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.pagina.instrumentos.fecha") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.pagina.instrumentos.fecha.terminacion") + ",";		
		encabezado = encabezado + mensajes.getMessage("jsp.pagina.instrumentos.estatus") + ",";
	
	

		String[] titulo = encabezado.split(",");

		boolean tablaIniciada = false;

		String[][] columnas = new String[titulo.length][2];
		StringBuilder string;
    	for (int f = 0; f < titulo.length; f++)
    	{
			columnas[f][0] = "5";
			if (f == (titulo.length - 1))
			{
		    	string = new StringBuilder();
				string.append(titulo[f]);
				string.append("\n");
				string.append("\n");
		    	columnas[f][1] = string.toString();
			}
			else
				columnas[f][1] = titulo[f];
    	}
    
		TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
	
		tablaIniciada = false;

		string = new StringBuilder();
		string.append("\n");
		string.append("\n");
	
	
		if(instrumento.getTiposConvenioId() != null) {
			TipoConvenio tipoConvenio = (TipoConvenio)strategosTiposConvenioService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
			if(tipoConvenio != null) {
				tabla.agregarCelda(tipoConvenio.getNombre() + "\n \n");
			}
		}else {
			tabla.agregarCelda("\n \n");
		}	
	
	
		if(instrumento.getCooperanteId() != null) {
			Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
			if(cooperante != null) {
				tabla.agregarCelda(cooperante.getNombre() + "\n");
			}
		
		}else {
			tabla.agregarCelda("\n");
		}
	
	
		if(instrumento.getFechaInicio() != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			tabla.agregarCelda(format.format(instrumento.getFechaInicio())+ "\n");
		}else {
			tabla.agregarCelda("\n");
		}
	
	
		if(instrumento.getFechaTerminacion() != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			tabla.agregarCelda(format.format(instrumento.getFechaTerminacion())+ "\n");
		}else {
			tabla.agregarCelda("\n");
		}
			
		

		tabla.agregarCelda(obtenerEstatus(instrumento.getEstatus()) + "\n");	    
	
			
    	documento.add(tabla.getTabla());
	
		strategosTiposConvenioService.close();
		strategosCooperantesService.close();
		
	}
	public String obtenerEstatus(Byte estatus) {
	
		String nombre = "";
	
		switch(estatus) {
			case 1:
				nombre="Sin Iniciar";
				break;
			case 2:
				nombre="En Ejecucion";
				break;
			case 3:
				nombre="Cancelado";
				break;
			case 4:
				nombre="Suspendido";
				break;
			case 5:
				nombre="Culminado";
				break;
		}
	
		return nombre;
	}		
	
	private void EjecucionIniciativa(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String source, List<Iniciativa> iniciativas, List<Plan> planes) throws Exception
	{
	    //Raiz del plan
	    lineas = 2;

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    						
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);
		
		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
		
		inicioTamanoPagina = lineasxPagina(font);
	    tamanoPagina = inicioTamanoPagina;
	    	    
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();		
		
		
					
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		font.setStyle(Font.NORMAL);

		filtros = new HashMap<String, Object>();
				
		Font fontIni = new Font(getConfiguracionPagina(request).getCodigoFuente());				
		   
		
		if (iniciativas.size() > 0)
		{

			fontIni.setSize(8);
			fontIni.setStyle(Font.BOLD);
			fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_SUBTITULO);
			fontBold.setStyle(Font.BOLD);																								
															
		    documento.add(lineaEnBlanco(font));    			

		    texto = new Paragraph("Tabla Iniciativas" + " : " , fontIni);
			texto.setAlignment(Element.ALIGN_LEFT);
			texto.setIndentationLeft(16);
			documento.add(texto);
									
			boolean tablaIniciada = false;
			
			Plan infoPlan = new Plan();

		    String[][] columnas = new String[7][2];
		    StringBuilder string;

		    columnas[0][0] = "20";
		    columnas[0][1] =  mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa");
		    columnas[1][0] = "9";
		    columnas[1][1] = mensajes.getMessage("jsp.visualizariniciativasplan.columna.porcentajecompletado");
		    columnas[2][0] = "10";
		    columnas[2][1] = mensajes.getMessage("jsp.gestionariniciativasplan.columna.fechaUltimaMedicion");
		    columnas[3][0] = "10";
		    columnas[3][1] = mensajes.getMessage("jsp.visualizariniciativasplan.columna.estatus");
		    columnas[4][0] = "10";
		    columnas[4][1] = mensajes.getMessage("jsp.editariniciativa.ficha.anioformulacion") + "\n\n";		    
		    columnas[5][0] = "15";
		    columnas[5][1] = mensajes.getMessage("jsp.mostrarplanesasociadosiniciativa.columna.nombreplan"); 
		    columnas[6][0] = "15";
		    columnas[6][1] = mensajes.getMessage("jsp.mostrarplanesasociadosiniciativa.columna.objetivo");
		    		    		    			
		    TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
				
			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
			{
				Iniciativa iniciativa = (Iniciativa)iter.next();												    			    							
								
				Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				
				List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
				List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
				
				// Dibujar Informacion de la Iniciativa    			
    			string = new StringBuilder();
    		    if (iniciativa.getNombre() != null && iniciativa.getNombre() != "")
    		    	string.append(iniciativa.getNombre());
    		    else
    		    	string.append("");
    			string.append("\n");
    			string.append("\n");
    			tabla.agregarCelda(string.toString());
    			
    		    tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
    		    tabla.agregarCelda(iniciativa.getFechaUltimaMedicion());	    
    		    
    		    //estatus
    			if (medicionesProgramadas.size() == 0) {
    				//EstatusIniciar
    				tabla.agregarCelda(mensajes.getMessage("estado.sin.iniciar"));
    			}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
    				//EstatusIniciardesfasado
    				tabla.agregarCelda(mensajes.getMessage("estado.sin.iniciar.desfasada"));
    			}					
    			else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
    				//EnEjecucionSinRetrasos
    				tabla.agregarCelda(mensajes.getMessage("estado.en.ejecucion.sin.retrasos"));
    			}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
    				//EnEjecucionConRetrasosSuperables
    				tabla.agregarCelda(mensajes.getMessage("estado.en.ejecucion.con.retrasos.superables"));
    			}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
    				//EnEjecucionConRetrasosSignificativos
    				tabla.agregarCelda(mensajes.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
    			}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
    				//EstatusConcluidas
    				tabla.agregarCelda(mensajes.getMessage("estado.concluidas"));
    			}
    			else if(iniciativa.getEstatusId() == 3) {
    				//EstatusCancelado
    				tabla.agregarCelda("Cancelado");
    			}
    			else if(iniciativa.getEstatusId() == 4) {
    				//EstatusSuspendido
    				tabla.agregarCelda("Suspendido");
    			}else {
    				//EstatusIniciar
    				tabla.agregarCelda(mensajes.getMessage("estado.sin.iniciar"));    				
    			}    		        		    
    		    
    		    tabla.agregarCelda(iniciativa.getAnioFormulacion());
    		        		    
    		    infoPlan = obtenerPlan(iniciativa);		
    		    
    		    if(infoPlan != null)    		    
    		    	tabla.agregarCelda(infoPlan.getNombre());
    		    else {
    		    	tabla.agregarCelda("No hay plan asociado");
    		    }
    		    
    		    if(infoPlan != null)    		    
    		    	tabla.agregarCelda(obtenerObjetivo(iniciativa.getIniciativaId(), infoPlan.getPlanId()));
    		    else {
    		    	tabla.agregarCelda("No hay Objetivo descrito");    		    
    		    }
			}									
			
			documento.add(tabla.getTabla());			
		}
		else
		{
			documento.add(lineaEnBlanco(font));
			

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas", "INICIATIVAS", "PERSPECTIVA"), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			
			documento.add(lineaEnBlanco(font));			
		}				
		strategosIniciativasService.close();
				
	}	
	
	public Plan obtenerPlan(Iniciativa iniciativa) {		 
		  ArrayList<Plan> listaPlanes = new ArrayList(); 
		    if (iniciativa != null)
		      {		        		        		        
		        Set<IniciativaPlan> iniciativaPlanes = iniciativa.getIniciativaPlanes();
		        StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		        for (Iterator<IniciativaPlan> iter = iniciativaPlanes.iterator(); iter.hasNext();)
		        {
		          IniciativaPlan iniciativaPlan = (IniciativaPlan)iter.next();
		          		         		          
		            Plan plan = (Plan)strategosPlanesService.load(Plan.class, iniciativaPlan.getPk().getPlanId());
		            OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPlanesService.load(OrganizacionStrategos.class, plan.getOrganizacionId());
		            plan.setOrganizacion(organizacion);
		            
		            listaPlanes.add(plan);		            		            		          
		        }
		        strategosPlanesService.close();    		            		          		    
		      }
		    
		    if (listaPlanes.size() > 0)
		    {
		      Plan plan = (Plan)listaPlanes.get(0);
			    return plan;		     
		    }
			return null;
		   
	}
	
	 public String obtenerObjetivo(Long iniciativaId, Long planId) throws SQLException{
		  
			String objetivo="";
			Long id=iniciativaId;
			
			if(id != null && planId != null){
			
				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
				
				Iniciativa ini = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(id));
				
				
				if((ini.getIniciativaPerspectivas() != null) && (ini.getIniciativaPerspectivas().size() > 0)){
					
				  IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)ini.getIniciativaPerspectivas().toArray()[0];
		          StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		          Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
		          
		        	  objetivo= perspectiva.getNombre();
		         
				}
				strategosIniciativasService.close();
			}
			
			return objetivo;
	  }
}
