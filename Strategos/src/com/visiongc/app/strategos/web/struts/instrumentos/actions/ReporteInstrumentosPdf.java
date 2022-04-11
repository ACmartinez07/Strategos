/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.awt.Color;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.lowagie.text.Chunk;
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
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoVariable;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;

/**
 * @author Kerwin
 *
 */
public class ReporteInstrumentosPdf extends VgcReporteBasicoAction
{
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("jsp.pagina.instrumentos.reporte.titulo.detalle");
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
		
		
		/*
	    Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.plantilla.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		
		Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : " + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesInicial())) + "/" + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		*/
		
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
	    		
	    		filtros = new HashMap();
	    		
	    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
	    		
	    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, atributoOrden, tipoOrden, true, filtros);
	    		
	    		EjecucionIniciativa(reporte, documento, request, mensajes, source, paginaIniciativas.getLista());
	    	
	    	}
	    	
	    	
	    }else {
	    	
	    	StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
			StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
			
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
		    		
		    		filtros = new HashMap();
		    		
		    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
		    		
		    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, null, tipoOrden, true, filtros);
		    		
		    		EjecucionIniciativa(reporte, documento, request, mensajes, source, paginaIniciativas.getLista());
		    		
		    				    		
				}
			}
	    }
	    
	    
	    /* 	    
	    
		if (reporte.getVisualizarIniciativas())
		{
			StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
			StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
								
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();

		    dibujarInformacionInstrumento();
		    
			//dibujarInformacionIniciativa(nivel, reporte, font, source, null, documento, strategosMedicionesService, strategosIndicadoresService, mensajes, request);

		    strategosIndicadoresService.close();
		    strategosMedicionesService.close();
		}
		*/
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

				documento.add(lineaEnBlanco(font));
				
				
				
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
		encabezado = encabezado + mensajes.getMessage("jsp.pagina.instrumentos.descripcion") + ",";
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
				
		
		tabla.agregarCelda(instrumento.getNombreInstrumento() + "\n");		

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
	
	
	private void dibujarInformacionIniciativa(int nivel, ReporteForm reporte, Font font, String source, List<Iniciativa> iniciativas, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
					
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		font.setStyle(Font.NORMAL);

		filtros = new HashMap<String, Object>();
				
				
		if (iniciativas.size() > 0)
		{
			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
			{
				Iniciativa iniciativa = (Iniciativa)iter.next();
				
				Font fontIni = new Font(getConfiguracionPagina(request).getCodigoFuente());
				Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
				    //Nombre de la Organizacion, plan y periodo del reporte
				fontIni.setSize(8);
				fontIni.setStyle(Font.BOLD);
				fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_SUBTITULO);
				fontBold.setStyle(Font.BOLD);
					
				String organizacion = iniciativa.getOrganizacion().getNombre();
				
				Paragraph textoIni = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.plantilla.organizacion") + " : " + organizacion, fontIni);
				textoIni.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoIni);
			
				
				
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				
		    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio; 
		    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
		    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
				
				
    		    documento.add(lineaEnBlanco(font));
    			

    		    texto = new Paragraph("Iniciativa" + " : " + iniciativa.getNombre(), fontIni);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);
    			 
    			documento.add(lineaEnBlanco(font));
    			
    			
				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

				// Dibujar Informacion de la Iniciativa
    			crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado, medicionesEjecutado, font, mensajes, documento, request);
								
				documento.add(lineaEnBlanco(font));
    			 								
				texto = new Paragraph("Listado de Actividades", fontIni);
    			texto.setAlignment(Element.ALIGN_LEFT);
    			texto.setIndentationLeft(16);
    			documento.add(texto);    
    			    			
				dibujarInformacionActividad(reporte, font, iniciativa, documento, strategosMedicionesService, strategosIndicadoresService, mensajes, request);
				
				documento.add(lineaEnBlanco(font));
    			
			}	
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
		
		strategosMetasService.close();
		strategosIniciativasService.close();
	}

	private void crearTablaIniciativa(ReporteForm reporte, Iniciativa iniciativa, Indicador indicador, List<Medicion> medicionesProgramado, List<Medicion> medicionesEjecutado, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		

		PryProyecto proyecto = null;
		if (iniciativa.getProyectoId() != null)
		{
			StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
			proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
			strategosPryProyectosService.close();
		}
		
		String encabezado = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio") + ",";	
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion") + ",";
	
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance") + ",";
	
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado") + ",";
	
			encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable");
		
		String[] titulo = encabezado.split(",");
		boolean tablaIniciada = false;

	    String[][] columnas = new String[titulo.length][2];
	    StringBuilder string;

	    for (int f = 0; f < titulo.length; f++)
	    {
    		columnas[f][0] = "8";
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

		tablaIniciada = false;
		string = new StringBuilder();
	    if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
	    	string.append(indicador.getUnidad().getNombre());
	    else
	    	string.append("");
		string.append("\n");
		string.append("\n");
		tabla.agregarCelda(string.toString());
		
	    tabla.agregarCelda(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
	    tabla.agregarCelda(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
	  
	    	tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
	    
	    	tabla.agregarCelda(VgcFormatter.formatearNumero(programado));
	    
	    	String url=obtenerCadenaRecurso(request);
			if (iniciativa.getAlerta() == null) 
				tabla.agregarCelda("");
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
	    
	    tabla.agregarCelda(iniciativa.getResponsableSeguimiento() !=null ? iniciativa.getResponsableSeguimiento() : "");	    

		documento.add(tabla.getTabla());
	}
	
	private void dibujarInformacionActividad(ReporteForm reporte, Font font, Iniciativa iniciativa, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		font.setStyle(Font.NORMAL);

		filtros = new HashMap<String, Object>();
		filtros.put("proyectoId", iniciativa.getProyectoId());
		List<PryActividad> actividades = strategosPryActividadesService.getActividades(0, 0, "fila", "ASC", true, filtros).getLista(); 
		
		
		
		if (actividades.size() > 0)
		{
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();)
			{
				PryActividad actividad = (PryActividad)iter.next();
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, actividad.getIndicadorId());
				
		    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
		    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		    	int periodoFin = periodoInicio; 
		    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
		    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
				    			    			
				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				
				// Dibujar Informacion de la Iniciativa
    			crearTablaActividad(reporte, actividad, indicador, font, mensajes, documento, request);
							   
			}
			
			
		}
		else
		{
			documento.add(lineaEnBlanco(font));
			
			

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noactividades", "ACTIVIDAD", "INICIATIVA"), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			

			documento.add(lineaEnBlanco(font));
			
			
		}
		
		strategosMetasService.close();
		strategosPryActividadesService.close();
	}

	private void crearTablaActividad(ReporteForm reporte, PryActividad actividad, Indicador indicador, Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception
	{
		String encabezado = mensajes.getMessage("jsp.asignarpesosactividad.actividad") + ",";
		
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio") + ",";	
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion") + ",";

		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion") + ",";
	
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado") + ",";
		encabezado = encabezado + mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado");
		

		String[] titulo = encabezado.split(",");

		boolean tablaIniciada = false;

		String[][] columnas = new String[titulo.length][2];
		StringBuilder string;
	    for (int f = 0; f < titulo.length; f++)
	    {
    		columnas[f][0] = "6";
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

		tabla.agregarCelda(actividad.getNombre() != null ? actividad.getNombre() : "");	
					
	    tabla.agregarCelda(VgcFormatter.formatearFecha(actividad.getComienzoPlan(),"formato.fecha.corta"));
	    tabla.agregarCelda(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));

    	tabla.agregarCelda(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
	    
	    	String url=obtenerCadenaRecurso(request);
			if (actividad.getAlerta() == null) 
				tabla.agregarCelda("");
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	        	tabla.agregarCelda(Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
	    

    	tabla.agregarCelda(actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
    	tabla.agregarCelda(actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
	    
    	documento.add(tabla.getTabla());
	}
	
	private String obtenerCadenaRecurso(HttpServletRequest request){
		String result= null;
		if(request.getServerPort()==80 && request.getScheme().equals("http")){
			
		    result = request.getServerName() + "/" + request.getContextPath();
		    result = "https" + "://" + result.replaceAll("//", "/");
			
		}else{
			result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
		    result = request.getScheme() + "://" + result.replaceAll("//", "/");
		   
		}
		return result;
		
	}
	
	public String getNombrePerspectiva(ReporteForm reporte, int nivel){

		String nombre = "";
		
		Set<?> elementosPlantillaPlanes = reporte.getPlantillaPlanes().getElementos();
		if (elementosPlantillaPlanes != null) 
		{
			for (Iterator<?> iterElemento = elementosPlantillaPlanes.iterator(); iterElemento.hasNext(); ) 
			{
				ElementoPlantillaPlanes elemento = (ElementoPlantillaPlanes)iterElemento.next();
				if (elemento.getOrden().intValue() == nivel) 
				{
					nombre = elemento.getNombre();
					break;
				}
			}
		}
		
		return nombre;
	}
	
	private void EjecucionIniciativa(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String source, List<Iniciativa> iniciativas) throws Exception
	{
	    //Raiz del plan
	    lineas = 2;

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    		
		/*
		texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : " + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesInicial())) + "/" + PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " -- " + (reporte.getAnoInicial().equals(reporte.getAnoFinal()) ? reporte.getAnoInicial() : (reporte.getAnoInicial() + "/" + reporte.getAnoFinal())), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		*/
		
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		Integer nivel = 0;
		inicioTamanoPagina = lineasxPagina(font);
	    tamanoPagina = inicioTamanoPagina;
		
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();

		dibujarInformacionIniciativa(nivel, reporte, font, source, iniciativas, documento, strategosMedicionesService, strategosIndicadoresService, mensajes, request);

	    strategosIndicadoresService.close();
	    strategosMedicionesService.close();
	}
	
}
