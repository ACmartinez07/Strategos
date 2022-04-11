/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;

/**
 * @author Kerwin
 *
 */
public class PlanMetaReportePdfAction extends VgcReporteBasicoAction
{
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("jsp.reportes.plan.meta.titulo");
	}
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{	
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
  		Calendar fecha = Calendar.getInstance();
		reporte.setAno(request.getParameter("ano") != null ? Integer.parseInt(request.getParameter("ano")) : fecha.get(1));
		reporte.setPlanId(request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
		reporte.setAcumular((request.getParameter("acumular") != null ? (request.getParameter("acumular").equals("1") ? true : false) : false));

		MetaPlan(reporte, documento, request, mensajes);
	}
	
	private void MetaPlan(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes) throws Exception
	{
	    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    
	    Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
	    reporte.setPlantillaPlanes((PlantillaPlanes)strategosPerspectivasService.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
	    reporte.setAnoInicial(plan.getAnoInicial().toString());
		reporte.setAnoFinal(reporte.getAno().toString());
		reporte.setMesInicial("1");
		reporte.setMesFinal("12");

	    //Raiz del plan
	    lineas = 2;
	    tamanoPagina = inicioTamanoPagina;
	    Perspectiva perspectiva = null;
    	perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
	    
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		perspectiva.setConfiguracionPlan(configuracionPlan);
	    
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);

	    Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.meta.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.meta.titulo.rango") + " : " + reporte.getAno(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		Integer nivel = 0;
		if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.meta.plan") + " : " + perspectiva.getNombreCompleto(), font);
		else
		{
			Perspectiva perspectivaRaiz = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
			perspectivaRaiz.setConfiguracionPlan(configuracionPlan);
			nivel = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().buscarNivelPerspectiva(0, perspectivaRaiz.getPerspectivaId(), perspectiva.getPerspectivaId(), strategosPerspectivasService);
			texto = new Paragraph(new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel != null ? nivel : 1)) + " : " + perspectiva.getNombreCompleto(), font);
		}
	    texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);
		
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);
		
	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("padreId", perspectiva.getPerspectivaId());
	    String[] orden = new String[1];
	    String[] tipoOrden = new String[1];
	    orden[0] = "nombre";
	    tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		if (perspectivas.size() > 0)
		{
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;
			
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				perspectivaHija.setConfiguracionPlan(configuracionPlan);
				
		        // nombre de la perspectiva primer nivel 
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
				
				texto = new Paragraph(new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				lineas = getNumeroLinea(lineas, inicioLineas);

				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
				
				buildReporte(nivel, reporte, font, perspectivaHija, documento, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			}
		}
		else
			dibujarInformacionIndicador(nivel, reporte, font, perspectiva, documento, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
		
	    strategosPerspectivasService.close();
	    strategosIndicadoresService.close();
	    strategosMedicionesService.close();
	}

	private void buildReporte(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento, ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
	    //Lista de perspectivas del primer nivel
		Paragraph texto;
	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("padreId", perspectiva.getPerspectivaId());
	    String[] orden = new String[1];
	    String[] tipoOrden = new String[1];
	    orden[0] = "nombre";
	    tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		
		if (perspectivas.size() > 0)
		{
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				perspectivaHija.setConfiguracionPlan(configuracionPlan);
				
		        // nombre de la perspectiva primer nivel 
				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}

				texto = new Paragraph(new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				lineas = getNumeroLinea(lineas, inicioLineas);

				if (lineas >= tamanoPagina)
				{
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
				
				buildReporte((nivel + 1), reporte, font, perspectivaHija, documento, configuracionPlan, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			}
		}
		else
			dibujarInformacionIndicador(nivel, reporte, font, perspectiva, documento, strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
	}
	
	private void dibujarInformacionIndicador(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosMetasService);
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		TablaBasicaPDF tabla;
		String format = "##0.00";
		StringBuilder string;
		boolean tablaIniciada = false;
		
		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();
		
		if (indicadores.size() > 0)
		{
			if (lineas >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			tabla = crearTabla(true, reporte, font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
			
			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();)
			{
				Indicador indicador = (Indicador)iter.next();
				
    			boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
				List<Medicion> medicionesMetas = new ArrayList<Medicion>();
				List<Meta> metas = new ArrayList<Meta>();
				if (reporte.getAcumular())
					metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), reporte.getPlanId(), Integer.parseInt(reporte.getAnoInicial()), reporte.getAno(), acumular);
				else
					metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), reporte.getPlanId(), reporte.getAno(), reporte.getAno(), acumular);
				
				for (Iterator<Meta> iterMeta = metas.iterator(); iterMeta.hasNext();)
				{
					Meta meta = (Meta)iterMeta.next();
					medicionesMetas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), meta.getMetaId().getAno(), new Integer(meta.getMetaId().getPeriodo()), meta.getMetaId().getSerieId()), (meta.getValor() != null ? new Double(meta.getValor()) : null)));
				}

				Double metaPlan = null;
				if (medicionesMetas.size() > 0 && medicionesMetas.get(0).getValor() != null)
					metaPlan = medicionesMetas.get(0).getValor();
				
				List<Medicion> mediciones = new ArrayList<Medicion>();
				if (reporte.getAcumular())
				{
			    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicador.getFrecuencia().byteValue());
			    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
			    	int periodoFin = periodoInicio; 
			    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
			    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
					mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), reporte.getAno(), periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
				}
				
				if (!reporte.getAcumular())
				{
	    		    // Alerta
					Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), reporte.getPlanId());
					if (metaPlan != null && indicador.getUltimaMedicion() != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), metaPlan, strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), metaPlan, zonaVerde, strategosMedicionesService);
					
						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), metaPlan, null, null);
					}
					
					texto = new Paragraph("", font);
					
					String url=obtenerCadenaRecurso(request);
								    
					if (alerta == null) 
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
					tabla.agregarCelda(texto);
					
					// Nombre
					string = new StringBuilder();
					string.append(indicador.getNombre());
					string.append("\n");
					string.append("\n");
					
					tabla.agregarCelda(string.toString());

	    		    // Unidad
					if (indicador.getUnidadId() != null)
					{
						UnidadMedida unidad = (UnidadMedida)strategosPlanesService.load(UnidadMedida.class, indicador.getUnidadId());
						if (unidad != null)
							tabla.agregarCelda(unidad.getNombre());
						else
							tabla.agregarCelda("");
					}
					else
						tabla.agregarCelda("");

					// Medicion
					if (indicador.getUltimaMedicion() != null)
						tabla.agregarCelda(indicador.getUltimaMedicionFormateada());
					else
						tabla.agregarCelda("");

	    		    // Fecha Ultima Medicion
					if (indicador.getFechaUltimaMedicion() != null)
						tabla.agregarCelda(indicador.getFechaUltimaMedicion());
					else
						tabla.agregarCelda("");
	    		    
					if (medicionesMetas.size() > 0 && medicionesMetas.get(0).getValor() != null)
					{
						// Meta Plan
						tabla.agregarCelda(medicionesMetas.get(0).getValorFormateado(format));
	
		    		    // % Estado Meta Plan
		    		    indicador.setUltimoProgramado(metaPlan);
		    		    tabla.agregarCelda(indicador.getPorcentajeCumplimientoFormateado(null, TipoIndicadorEstado.getTipoIndicadorEstadoParcial()));
					}
					else
					{
						// Meta Plan
						tabla.agregarCelda("");
	
		    		    // % Estado Meta Plan
						tabla.agregarCelda("");
					}
				}
				// si no 
				else
				{
					Double valor = null;
					boolean esAcumulado = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue());
					
					if (medicionesMetas.size() > 0)
					{
						if (!esAcumulado)
						{
							for (Iterator<Medicion> iterMed = medicionesMetas.iterator(); iterMed.hasNext();)
							{
								Medicion medicion = (Medicion)iterMed.next();
								if (valor == null && medicion.getValor() != null)
									valor = medicion.getValor();
								else if (valor != null && medicion.getValor() != null)
									valor = valor + medicion.getValor();
							}
						}
						else 
							valor = medicionesMetas.get((medicionesMetas.size() - 1)).getValor();
					}
	    		    indicador.setUltimoProgramado(valor);
					
					valor = null;
					Integer numeroMaximoPeriodos = null;
					Integer ano = null;
					if (mediciones.size() > 0)
					{
						if (!esAcumulado)
						{
							for (Iterator<Medicion> iterMed = mediciones.iterator(); iterMed.hasNext();)
							{
								Medicion medicion = (Medicion)iterMed.next();
								if (ano == null)
									ano = medicion.getMedicionId().getAno();
								
								if (numeroMaximoPeriodos == null || ano.intValue() != medicion.getMedicionId().getAno().intValue())
								{
									ano = medicion.getMedicionId().getAno();
									numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), ano);
								}
								
								if (medicion.getMedicionId().getPeriodo().intValue() == numeroMaximoPeriodos.intValue())
								{
									if (valor == null && medicion.getValor() != null)
										valor = medicion.getValor();
									else if (valor != null && medicion.getValor() != null)
										valor = valor + medicion.getValor();
								}
							}
						}
						else
							valor = mediciones.get((mediciones.size() - 1)).getValor();
					}
					indicador.setUltimaMedicion(valor);
	    		    
	    		    // Alerta
					Byte alerta = AlertaIndicador.getAlertaNoDefinible();
					if (indicador.getUltimoProgramado() != null && indicador.getUltimaMedicion() != null)
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(), Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), zonaVerde, strategosMedicionesService);
					
						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), indicador.getUltimoProgramado(), null, null);
					}
					
					texto = new Paragraph("", font);	
					
					String url=obtenerCadenaRecurso(request);
					
					if (alerta == null) 
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
		    		else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
		    		{
		    			Image image = Image.getInstance(new URL(url+ "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
		    			texto.add(new Chunk(image, 0, 0));
		    		}
					tabla.agregarCelda(texto);
					
					// Nombre
					string = new StringBuilder();
					string.append(indicador.getNombre());
					string.append("\n");
					string.append("\n");
					
					tabla.agregarCelda(string.toString());

	    		    // Unidad
					if (indicador.getUnidadId() != null)
					{
						UnidadMedida unidad = (UnidadMedida)strategosPlanesService.load(UnidadMedida.class, indicador.getUnidadId());
						if (unidad != null)
							tabla.agregarCelda(unidad.getNombre());
						else
							tabla.agregarCelda("");
					}
					else
						tabla.agregarCelda("");
					
					 //Meta Plan
					if (indicador.getUltimoProgramado() != null)
						tabla.agregarCelda(VgcFormatter.formatearNumero(indicador.getUltimoProgramado(), 2));
					else
						tabla.agregarCelda("");
					
					// Medicion
					if (indicador.getUltimaMedicion() != null)
						tabla.agregarCelda(VgcFormatter.formatearNumero(indicador.getUltimaMedicion(), 2));
					else
						tabla.agregarCelda("");

	    		    // % Estado 
	    		    valor = indicador.getPorcentajeCumplimiento(null, TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
	    		    tabla.agregarCelda(VgcFormatter.formatearNumero(valor, 2));
	    		    
	    		    if (valor != null && valor > 100D)
	    		    	valor = 100D;
	    		    
	    		    // % Estado Suavisado
	    		    tabla.agregarCelda(VgcFormatter.formatearNumero(valor, 2));
				}

				lineas = getNumeroLinea((lineas + 3), inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
				{
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(false, reporte, font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
				}
			}
			
			if (!tablaIniciada)
				documento.add(tabla.getTabla());
		}
		else
		{
			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores", reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(), new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction().getNombrePerspectiva(reporte, (nivel)).toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}

			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
			{
				lineas = inicioLineas;
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, null, null, request);
			}
		}
		
		strategosPlanesService.close();
		strategosMetasService.close();
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
	
	private TablaBasicaPDF crearTabla(boolean newTable, ReporteForm reporte, Font font, MessageResources mensajes, StrategosMedicionesService strategosMedicionesService, StrategosIndicadoresService strategosIndicadoresService, Document documento, HttpServletRequest request) throws Exception
	{
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}

	    String[][] columnas = new String[7][2];
	    StringBuilder string;
	    columnas[0][0] = "15";
	    columnas[0][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.alerta");
	    
	    columnas[1][0] = "100";
	    columnas[1][1] = reporte.getPlantillaPlanes().getNombreIndicadorSingular();

	    columnas[2][0] = "15";
	    columnas[2][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.unidad");

	    if (!reporte.getAcumular())
	    {
		    columnas[3][0] = "30";
		    columnas[3][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.ejecutado");

		    columnas[4][0] = "35";
		    columnas[4][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.fechaejecutado");
		    
		    columnas[5][0] = "30";
		    columnas[5][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.meta");

		    columnas[6][0] = "35";
		    string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.porcentaje"));
			string.append("\n");
			string.append("\n");
		    columnas[6][1] = string.toString();
	    }
	    else
	    {
		    columnas[3][0] = "30";
		    columnas[3][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.meta") + " " + mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.al") + " " + reporte.getAno().toString();

		    columnas[4][0] = "35";
		    columnas[4][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.ejecutado") + " " + mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.al") + " " + reporte.getAno().toString();
		    
		    columnas[5][0] = "30";
		    columnas[5][1] = mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.podentaje.cumplimiento") + " " + mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.al") + " " + reporte.getAno().toString();

		    columnas[6][0] = "35";
		    string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.meta.reporte.columna.podentaje.cumplimiento.suavisado"));
			string.append("\n");
			string.append("\n");
		    columnas[6][1] = string.toString();
	    }
	    
		Color colorLetra = null; 
		Color colorFondo = null;
		Integer anchoBorde = null;
		Integer anchoBordeCelda = null;
		Boolean bold = true;
		Integer alineacionHorizontal = null;
		Integer alineacionVertical = null;
		
		colorLetra = new Color(255, 255, 255); 
		colorFondo = new Color(128, 128, 128);			
		
		if (tablaHeader != null && newTable)
			tablaHeader = null;
		TablaBasicaPDF tabla = inicializarTabla(font, columnas, anchoBorde, anchoBordeCelda, bold, colorLetra, colorFondo, alineacionHorizontal, alineacionVertical, request);

		lineas = getNumeroLinea(lineas, inicioLineas);
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina)
		{
			lineas = inicioLineas;
			documento.add(tabla.getTabla());
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
			tabla = crearTabla(false, reporte, font, mensajes, strategosMedicionesService, strategosIndicadoresService, documento, request);
		}
		
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
	    tabla.setFont(Font.NORMAL);
	    tabla.setFormatoFont(Font.NORMAL);
	    tabla.setColorLetra(0, 0, 0);
	    tabla.setColorFondo(255, 255, 255);
	    tabla.setTamanoFont(7);
		
		return tabla;
	}
}