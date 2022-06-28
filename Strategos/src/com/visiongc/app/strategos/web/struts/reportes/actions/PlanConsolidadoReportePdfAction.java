/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DecimalFormat;
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

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
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
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planes.model.util.TipoPerspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;

/**
 * Documentacion:
 * 
 * Clase para Crear el reportes consolidado en PDF.
 * 
 * @author Kerwin
 *
 */
public class PlanConsolidadoReportePdfAction extends VgcReporteBasicoAction {
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 70;
	private int maxLineasAntesTabla = 4;

	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.plan.consolidado.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		reporte.setPlanId(
				request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
		reporte.setObjetoSeleccionadoId(request.getParameter("objetoSeleccionadoId") != null
				? Long.parseLong(request.getParameter("objetoSeleccionadoId"))
				: null);
		reporte.setMesFinal(request.getParameter("mes"));
		reporte.setAnoFinal(request.getParameter("ano"));
		reporte.setAlcance(request.getParameter("alcance") != null ? Byte.parseByte(request.getParameter("alcance"))
				: reporte.getAlcancePlan());
		reporte.setTipoPeriodo(
				request.getParameter("tipoPeriodo") != null ? Byte.parseByte(request.getParameter("tipoPeriodo"))
						: reporte.getPeriodoAlCorte());
		reporte.setVisualizarIndicadores((request.getParameter("visualizarIndicadores") != null
				? (request.getParameter("visualizarIndicadores").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIniciativas((request.getParameter("visualizarIniciativas") != null
				? (request.getParameter("visualizarIniciativas").equals("1") ? true : false)
				: false));
		reporte.setVisualizarActividad((request.getParameter("visualizarActividades") != null
				? (request.getParameter("visualizarActividades").equals("1") ? true : false)
				: false));
		Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
				&& request.getParameter("selectHitoricoType") != "")
						? Byte.parseByte(request.getParameter("selectHitoricoType"))
						: HistoricoType.getFiltroHistoricoNoMarcado();
		FiltroForm filtro = new FiltroForm();
		filtro.setHistorico(selectHitoricoType);
		reporte.setFiltro(filtro);

		ConsolidadoPlan(reporte, documento, request, mensajes);
	}

	private void ConsolidadoPlan(ReporteForm reporte, Document documento, HttpServletRequest request,
			MessageResources mensajes) throws Exception {
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance()
				.openStrategosPerspectivasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
				.openStrategosMedicionesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();

		Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
		reporte.setPlantillaPlanes((PlantillaPlanes) strategosPerspectivasService.load(PlantillaPlanes.class,
				new Long(plan.getMetodologiaId())));
		reporte.setAnoInicial(plan.getAnoInicial().toString());
		reporte.setMesInicial("1");

		// Raiz del plan
		lineas = 10;
		tamanoPagina = inicioTamanoPagina;
		Perspectiva perspectiva = null;
		if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
			perspectiva = (Perspectiva) strategosPerspectivasService.load(Perspectiva.class,
					reporte.getObjetoSeleccionadoId());
		else
			perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
		SetMedicionesPerspectiva(perspectiva, reporte, strategosIndicadoresService, strategosMedicionesService);

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan();
		strategosPlanesService.close();
		perspectiva.setConfiguracionPlan(configuracionPlan);

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());

		// Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);

		Paragraph texto = new Paragraph(
				mensajes.getMessage("jsp.reportes.plan.consolidado.organizacion") + " : "
						+ ((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getNombre(),
				font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);

		Integer nivel = 0;
		if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
			texto = new Paragraph(
					mensajes.getMessage("jsp.reportes.plan.meta.plan") + " : " + perspectiva.getNombreCompleto(), font);
		else {
			Perspectiva perspectivaRaiz = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());
			perspectivaRaiz.setConfiguracionPlan(configuracionPlan);
			nivel = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
					.buscarNivelPerspectiva(0, perspectivaRaiz.getPerspectivaId(), perspectiva.getPerspectivaId(),
							strategosPerspectivasService);
			texto = new Paragraph(
					new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
							.getNombrePerspectiva(reporte, (nivel != null ? nivel : 1)) + " : "
							+ perspectiva.getNombreCompleto(),
					font);
		}
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);

		if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue()) {
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango") + " : "
					+ PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " - " + reporte.getAnoFinal(),
					font);
			texto.setAlignment(Element.ALIGN_CENTER);
			documento.add(texto);
			lineas = getNumeroLinea((lineas + 5), inicioLineas);
		}

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
		if (perspectivas.size() > 0) {
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;

			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) {
				Perspectiva perspectivaHija = (Perspectiva) iter.next();
				perspectivaHija.setConfiguracionPlan(configuracionPlan);
				SetMedicionesPerspectiva(perspectivaHija, reporte, strategosIndicadoresService,
						strategosMedicionesService);

				// nombre de la perspectiva primer nivel
				if (lineas >= tamanoPagina) {
					lineas = inicioLineas;
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, null, null, request);
				}

				texto = new Paragraph(
						new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
								.getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto(),
						font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				lineas = getNumeroLinea(lineas, inicioLineas);
				if (lineas >= tamanoPagina) {
					lineas = inicioLineas;
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, null, null, request);
				}

				if (perspectivaHija.getTipo().byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue()) {
					if (reporte.getVisualizarIndicadores())
						dibujarInformacionIndicador(nivel, reporte, font, perspectivaHija, documento,
								strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService,
								mensajes, request);
					if (reporte.getVisualizarIniciativas())
						dibujarInformacionIniciativa(nivel, reporte, font, perspectivaHija, documento,
								strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
								strategosPerspectivasService, mensajes, request);
				}

				buildReporte(nivel, reporte, font, perspectivaHija, documento, configuracionPlan,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);
			}
		} else if (perspectiva.getTipo().byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue()) {
			if (reporte.getVisualizarIndicadores())
				dibujarInformacionIndicador(nivel, reporte, font, perspectiva, documento, strategosMedicionesService,
						strategosIndicadoresService, strategosPerspectivasService, mensajes, request);
			if (reporte.getVisualizarIniciativas())
				dibujarInformacionIniciativa(nivel, reporte, font, perspectiva, documento, strategosMedicionesService,
						strategosIniciativasService, strategosIndicadoresService, strategosPerspectivasService,
						mensajes, request);
		}

		strategosPerspectivasService.close();
		strategosIndicadoresService.close();
		strategosIniciativasService.close();
		strategosMedicionesService.close();
	}

	private void buildReporte(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento,
			ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService,
			StrategosIniciativasService strategosIniciativasService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		// Lista de perspectivas del primer nivel
		Paragraph texto;
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("padreId", perspectiva.getPerspectivaId());
		String[] orden = new String[1];
		String[] tipoOrden = new String[1];
		orden[0] = "nombre";
		tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);

		if (perspectivas.size() > 0) {
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) {
				Perspectiva perspectivaHija = (Perspectiva) iter.next();
				perspectivaHija.setConfiguracionPlan(configuracionPlan);
				SetMedicionesPerspectiva(perspectivaHija, reporte, strategosIndicadoresService,
						strategosMedicionesService);

				// nombre de la perspectiva primer nivel
				if (lineas >= tamanoPagina) {
					lineas = inicioLineas;
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, null, null, request);
				}

				texto = new Paragraph(
						new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
								.getNombrePerspectiva(reporte, (nivel + 1)) + " : "
								+ perspectivaHija.getNombreCompleto(),
						font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				lineas = getNumeroLinea(lineas, inicioLineas);
				if (lineas >= tamanoPagina) {
					lineas = inicioLineas;
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, null, null, request);
				}

				if (perspectivaHija.getTipo().byteValue() == TipoPerspectiva.getTipoPerspectivaObjetivo().byteValue()) {
					if (reporte.getVisualizarIndicadores())
						dibujarInformacionIndicador(nivel, reporte, font, perspectivaHija, documento,
								strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService,
								mensajes, request);
					if (reporte.getVisualizarIniciativas())
						dibujarInformacionIniciativa(nivel, reporte, font, perspectivaHija, documento,
								strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
								strategosPerspectivasService, mensajes, request);
				}

				buildReporte((nivel + 1), reporte, font, perspectivaHija, documento, configuracionPlan,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);
			}
		}
	}

	private void dibujarInformacionIndicador(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva,
			Document documento, StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService(strategosMetasService);
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		TablaBasicaPDF tabla;
		StringBuilder string;
		boolean tablaIniciada = false;

		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService
				.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();

		if (indicadores.size() > 0) {
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}

			String[][] columnas = new String[9][2];
			int contador = 0;
			columnas[contador][0] = "5";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.alerta");

			contador++;
			columnas[contador][0] = "20";
			columnas[contador][1] = reporte.getPlantillaPlanes().getNombreIndicadorSingular();

			contador++;
			columnas[contador][0] = "8";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.unidad");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.ultimoperiodo");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.metaanula");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.real");

			contador++;
			columnas[contador][0] = "15";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.cumplimiento");

			contador++;
			columnas[contador][0] = "20";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.responsable");

			contador++;
			columnas[contador][0] = "30";
			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.explicaciones"));
			string.append("\n");
			string.append("\n");
			columnas[contador][1] = string.toString();

			tablaIniciada = true;
			tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
			Object[][] fila = new Object[9][3];

			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			java.awt.Font fontNormal = new java.awt.Font(font.getFamilyname(), font.style(),
					((Float) (font.size())).intValue());
			FontMetrics metrics = img.getGraphics().getFontMetrics(fontNormal);

			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();) {
				tablaIniciada = false;
				contador = -1;
				fila = new Object[9][3];
				Indicador indicador = (Indicador) iter.next();

				Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(),
						reporte.getPlanId());
				if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue()) {
					LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
							((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
							indicador.getFrecuencia().byteValue());
					int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
					int periodoFin = periodoInicio;
					if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
						periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

					boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
							.byteValue()
							&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
									.byteValue());
					List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(
							indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
					List<Medicion> medicionesMetas = new ArrayList<Medicion>();
					List<Meta> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(),
							reporte.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(),
							indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(),
							Integer.parseInt(reporte.getAnoInicial()), Integer.parseInt(reporte.getAnoFinal()),
							periodoInicio, periodoFin);
					for (Iterator<Meta> iterMeta = metas.iterator(); iterMeta.hasNext();) {
						Meta meta = (Meta) iterMeta.next();
						medicionesMetas.add(new Medicion(
								new MedicionPK(indicador.getIndicadorId(), meta.getMetaId().getAno(),
										new Integer(meta.getMetaId().getPeriodo()), meta.getMetaId().getSerieId()),
								(meta.getValor() != null ? new Double(meta.getValor()) : null)));
					}

					Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);
					Medicion ultimaMeta = null;
					if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
						ultimaMeta = strategosMedicionesService.getUltimaMedicionConValorEnUnPeriodo(medicionesMetas,
								ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo());

					Double metaPlan = null;
					if (ultimaMeta != null && ultimaMeta.getValor() != null)
						metaPlan = ultimaMeta.getValor();

					if (ultimaMedicion != null && ultimaMedicion.getValor() != null) {
						indicador.setFechaUltimaMedicion(ultimaMedicion.getMedicionId().getPeriodo() + "/"
								+ ultimaMedicion.getMedicionId().getAno());
						indicador.setUltimaMedicion(ultimaMedicion.getValor());
					} else {
						indicador.setFechaUltimaMedicion(null);
						indicador.setUltimaMedicion(null);
					}

					if (metaPlan != null && indicador.getUltimaMedicion() != null) {
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(),
								Integer.parseInt(reporte.getMesFinal()), metaPlan, strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(),
								Integer.parseInt(reporte.getMesFinal()), metaPlan, zonaVerde,
								strategosMedicionesService);

						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(),
								indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(),
								metaPlan, null, null);
					}
					indicador.setAlerta(alerta);
				}

				// Alerta
				texto = new Paragraph("", font);

				String url = obtenerCadenaRecurso(request);

				if (alerta == null) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
					texto.add(new Chunk(image, 0, 0));
				}
				contador++;
				fila[contador][0] = texto;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = 1;

				// Nombre
				string = new StringBuilder();
				string.append(indicador.getNombre());
				string.append("\n");
				string.append("\n");
				contador++;
				fila[contador][0] = string.toString();
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_LEFT;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Unidad
				String celda = "";
				if (indicador.getUnidadId() != null) {
					UnidadMedida unidad = (UnidadMedida) strategosPlanesService.load(UnidadMedida.class,
							indicador.getUnidadId());
					if (unidad != null)
						celda = unidad.getNombre();
				}
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Fecha Ultima Medicion
				celda = "";
				if (indicador.getFechaUltimaMedicion() != null)
					celda = indicador.getFechaUltimaMedicion();
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// % Meta Anual
				celda = "";
				if (indicador.getFechaUltimaMedicion() != null) {
					List<?> metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(),
							reporte.getPlanId(), indicador.getFechaUltimaMedicionAno(),
							indicador.getFechaUltimaMedicionAno(), false);
					if (metas.size() > 0) {
						indicador.setMetaAnual(((Meta) metas.get(0)).getValor());
						celda = (indicador.getMetaAnual() != null
								? VgcFormatter.formatearNumero(indicador.getMetaAnual())
								: "");
					}
				}
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Medicion
				celda = "";
				if (indicador.getUltimaMedicion() != null)
					celda = indicador.getUltimaMedicionFormateada();
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// % Cumplimiento
				celda = "";
				if (indicador.getMetaAnual() != null && indicador.getUltimaMedicion() != null
						&& indicador.getMetaAnual() != 0) {
					Double valor = (indicador.getUltimaMedicion() / indicador.getMetaAnual()) * 100;
					if (valor > 100D)
						valor = 100D;
					celda = (valor != null ? VgcFormatter.formatearNumero(valor) : "");
				}
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Responsable
				celda = "";
				if (indicador.getResponsableLograrMetaId() != null)
					celda = (indicador.getResponsableLograrMeta() != null
							? indicador.getResponsableLograrMeta().getNombreCargo()
							: "");
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Explicacion
				celda = "";
				String periodoBuscar = reporte.getMesFinal() + "/" + reporte.getAnoFinal();
				Explicacion explicacion = BuscarExplicacion(reporte, periodoBuscar, indicador.getIndicadorId(),
						ObjetivoKey.getKeyIndicador(), strategosExplicacionesService);
				if (explicacion != null && explicacion.getMemosExplicacion() != null
						&& explicacion.getMemosExplicacion().size() > 0) {
					for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext();) {
						MemoExplicacion memoExplicacion = (MemoExplicacion) i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION))) {
							celda = memoExplicacion.getMemo();
							break;
						}
					}
					if (celda != null && !celda.equals("")) {
						string = new StringBuilder();
						string.append(celda);
						string.append("\n");
						string.append("\n");
						celda = string.toString();
					}
				}
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_JUSTIFIED;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Agregar Fila
				int cantidadLineas = tabla.lineasFila(fila, paginaAncho);
				if ((lineas + cantidadLineas + maxLineasAntesTabla) >= tamanoPagina) {
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
					tablaIniciada = false;
				}
				tabla.agregarFila(fila);
				lineas = getNumeroLinea((lineas + cantidadLineas), inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
					tablaIniciada = true;
				}
			}

			if (!tablaIniciada)
				documento.add(tabla.getTabla());

			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}
		} else {
			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.noindicadores",
					reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(),
					new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
							.getNombrePerspectiva(reporte, (nivel)).toLowerCase()),
					font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}

			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}
		}

		strategosExplicacionesService.close();
		strategosPlanesService.close();
		strategosMetasService.close();
	}

	private void dibujarInformacionIniciativa(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva,
			Document documento, StrategosMedicionesService strategosMedicionesService,
			StrategosIniciativasService strategosIniciativasService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		TablaBasicaPDF tabla;
		StringBuilder string;
		boolean tablaIniciada = false;
		
		

		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		if (reporte.getFiltro().getHistorico() != null
				&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
			filtros.put("historicoDate", "IS NULL");
		else if (reporte.getFiltro().getHistorico() != null
				&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
			filtros.put("historicoDate", "IS NOT NULL");
		List<Iniciativa> iniciativas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
				.getLista();

		if (iniciativas.size() > 0) {
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}

			String[][] columnas = new String[10][2];
			int contador = 0;
			columnas[contador][0] = "5";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.alerta");

			contador++;
			columnas[contador][0] = "20";
			columnas[contador][1] = reporte.getPlantillaPlanes().getNombreIniciativaPlural();

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.ultimoperiodo");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.esperado.acumulado");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.ejecutado.acumulado");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.programado");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.presupuesto.real");

			contador++;
			columnas[contador][0] = "12";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.ejecucion");

			contador++;
			columnas[contador][0] = "20";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.iniciativa.responsable");

			contador++;
			columnas[contador][0] = "30";
			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.explicaciones"));
			string.append("\n");
			string.append("\n");
			columnas[contador][1] = string.toString();

			tablaIniciada = true;
			tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);
			Object[][] fila = new Object[columnas.length][3];

			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			java.awt.Font fontNormal = new java.awt.Font(font.getFamilyname(), font.style(),
					((Float) (font.size())).intValue());
			FontMetrics metrics = img.getGraphics().getFontMetrics(fontNormal);

			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
				Iniciativa iniciativa = (Iniciativa) iter.next();
				Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
						iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

				if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue()) {
					LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
							((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
							indicador.getFrecuencia().byteValue());
					int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
					int periodoFin = periodoInicio;
					if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
						periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

					boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
							.byteValue()
							&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
									.byteValue());
					List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(
							indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);
					List<Medicion> medicionesProgramadas = (List<Medicion>) strategosMedicionesService
							.getMedicionesPorFrecuencia(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(),
									acumular, acumular);

					Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);
					Medicion ultimaMeta = null;
					if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
						ultimaMeta = strategosMedicionesService.getUltimaMedicionConValorEnUnPeriodo(
								medicionesProgramadas, ultimaMedicion.getMedicionId().getAno(),
								ultimaMedicion.getMedicionId().getPeriodo());

					if (ultimaMedicion != null && ultimaMedicion.getValor() != null) {
						indicador.setFechaUltimaMedicion(ultimaMedicion.getMedicionId().getPeriodo() + "/"
								+ ultimaMedicion.getMedicionId().getAno());
						indicador.setUltimaMedicion(ultimaMedicion.getValor());
					} else {
						indicador.setFechaUltimaMedicion(null);
						indicador.setUltimaMedicion(null);
					}

					if (ultimaMeta != null && ultimaMeta.getValor() != null)
						indicador.setUltimoProgramado(ultimaMeta.getValor());
					else
						indicador.setUltimoProgramado(null);

					Byte alerta = null;
					if (indicador.getUltimoProgramado() != null && indicador.getUltimaMedicion() != null) {
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, reporte.getAno(),
								Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(),
								strategosMedicionesService);
						Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, reporte.getAno(),
								Integer.parseInt(reporte.getMesFinal()), indicador.getUltimoProgramado(), zonaVerde,
								strategosMedicionesService);

						alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(),
								indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(),
								indicador.getUltimoProgramado(), null, null);
					}
					indicador.setAlerta(alerta);
				}

				tablaIniciada = false;
				contador = -1;
				fila = new Object[10][3];

				// Alerta
				Byte alerta = indicador.getAlerta();
				texto = new Paragraph("", font);

				String url = obtenerCadenaRecurso(request);

				if (alerta == null) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
					texto.add(new Chunk(image, 0, 0));
				}
				contador++;
				fila[contador][0] = texto;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = 1;

				// Nombre
				string = new StringBuilder();
				string.append(iniciativa.getNombre());
				string.append("\n");
				string.append("\n");
				contador++;
				fila[contador][0] = string.toString();
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_LEFT;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Fecha Ultima Medicion
				String celda = "";
				if (indicador.getFechaUltimaMedicion() != null)
					celda = indicador.getFechaUltimaMedicion();
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				Double totalPresupuestoReal = null;
				Double totalPresupuestoProgramado = null;
				Long indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto());
				if (indicadorId != null) {
					Indicador indicadorPresupuesto = (Indicador) strategosIndicadoresService.load(Indicador.class,
							indicadorId);
					if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoAlCorte()) {
						if (indicadorPresupuesto.getTipoCargaMedicion().byteValue() == TipoMedicion
								.getTipoMedicionEnPeriodo().byteValue()) {
							List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(
									indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(),
									new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							// List<Medicion> medicionesProgramada =
							// strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(),
							// SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new
							// Integer(9999), new Integer(000), new Integer(999));
							List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(
									indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId(),
									new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones
									.hasNext();) {
								Medicion medicion = (Medicion) iterMediciones.next();
								if (medicion.getValor() != null && totalPresupuestoReal == null)
									totalPresupuestoReal = 0D;
								totalPresupuestoReal = totalPresupuestoReal + medicion.getValor();
								for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada
										.iterator(); iterMedicionesProgramadas.hasNext();) {
									Medicion medicionProgramada = (Medicion) iterMedicionesProgramadas.next();
									if (medicion.getMedicionId().getAno().intValue() == medicionProgramada
											.getMedicionId().getAno().intValue()
											&& medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada
													.getMedicionId().getPeriodo().intValue()) {
										if (medicionProgramada.getValor() != null && totalPresupuestoProgramado == null)
											totalPresupuestoProgramado = 0D;
										totalPresupuestoProgramado = totalPresupuestoProgramado
												+ medicionProgramada.getValor();
										break;
									}
								}
							}
						} else {
							totalPresupuestoReal = indicadorPresupuesto.getUltimaMedicion();

							if (totalPresupuestoReal != null) {
								// List<Medicion> medicionesProgramada =
								// strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(),
								// SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new
								// Integer(9999), new Integer(000), new Integer(999));
								List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(
										indicadorPresupuesto.getIndicadorId(),
										SerieTiempo.getSerieMaximo().getSerieId(), new Integer(0000), new Integer(9999),
										new Integer(000), new Integer(999));
								DecimalFormat nf3 = new DecimalFormat("#000");
								int anoPeriodoBuscar = Integer.parseInt(
										((Integer) indicadorPresupuesto.getFechaUltimaMedicionAno()).toString()
												+ nf3.format(indicadorPresupuesto.getFechaUltimaMedicionPeriodo())
														.toString());
								for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada
										.iterator(); iterMedicionesProgramadas.hasNext();) {
									Medicion medProgramada = (Medicion) iterMedicionesProgramadas.next();
									int anoPeriodo = Integer.parseInt(medProgramada.getMedicionId().getAno().toString()
											+ nf3.format(medProgramada.getMedicionId().getPeriodo()).toString());
									if (anoPeriodo <= anoPeriodoBuscar)
										totalPresupuestoProgramado = medProgramada.getValor();
								}
							}
						}
					} else {
						if (indicadorPresupuesto.getTipoCargaMedicion().byteValue() == TipoMedicion
								.getTipoMedicionEnPeriodo().byteValue()) {
							List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(
									indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(),
									new Integer(0000), Integer.parseInt(reporte.getAnoInicial()), new Integer(000),
									Integer.parseInt(reporte.getMesInicial()));
							// List<Medicion> medicionesProgramada =
							// strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(),
							// SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000),
							// Integer.parseInt(reporte.getAnoInicial()), new Integer(000),
							// Integer.parseInt(reporte.getMesInicial()));
							List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(
									indicadorPresupuesto.getIndicadorId(), SerieTiempo.getSerieMaximo().getSerieId(),
									new Integer(0000), Integer.parseInt(reporte.getAnoInicial()), new Integer(000),
									Integer.parseInt(reporte.getMesInicial()));
							for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones
									.hasNext();) {
								Medicion medicion = (Medicion) iterMediciones.next();
								if (medicion.getValor() != null && totalPresupuestoReal == null)
									totalPresupuestoReal = 0D;
								totalPresupuestoReal = totalPresupuestoReal + medicion.getValor();
								for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada
										.iterator(); iterMedicionesProgramadas.hasNext();) {
									Medicion medicionProgramada = (Medicion) iterMedicionesProgramadas.next();
									if (medicion.getMedicionId().getAno().intValue() == medicionProgramada
											.getMedicionId().getAno().intValue()
											&& medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada
													.getMedicionId().getPeriodo().intValue()) {
										if (medicionProgramada.getValor() != null && totalPresupuestoProgramado == null)
											totalPresupuestoProgramado = 0D;
										totalPresupuestoProgramado = totalPresupuestoProgramado
												+ medicionProgramada.getValor();
										break;
									}
								}
							}
						} else {
							totalPresupuestoReal = indicadorPresupuesto.getUltimaMedicion();

							if (totalPresupuestoReal != null) {
								// List<Medicion> medicionesProgramada =
								// strategosMedicionesService.getMedicionesPeriodo(indicadorPresupuesto.getIndicadorId(),
								// SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000),
								// Integer.parseInt(reporte.getAnoInicial()), new Integer(000),
								// Integer.parseInt(reporte.getMesInicial()));
								List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(
										indicadorPresupuesto.getIndicadorId(),
										SerieTiempo.getSerieMaximo().getSerieId(), new Integer(0000),
										Integer.parseInt(reporte.getAnoInicial()), new Integer(000),
										Integer.parseInt(reporte.getMesInicial()));
								DecimalFormat nf3 = new DecimalFormat("#000");
								int anoPeriodoBuscar = Integer.parseInt(
										((Integer) indicadorPresupuesto.getFechaUltimaMedicionAno()).toString()
												+ nf3.format(indicadorPresupuesto.getFechaUltimaMedicionPeriodo())
														.toString());
								for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada
										.iterator(); iterMedicionesProgramadas.hasNext();) {
									Medicion medProgramada = (Medicion) iterMedicionesProgramadas.next();
									int anoPeriodo = Integer.parseInt(medProgramada.getMedicionId().getAno().toString()
											+ nf3.format(medProgramada.getMedicionId().getPeriodo()).toString());
									if (anoPeriodo <= anoPeriodoBuscar)
										totalPresupuestoProgramado = medProgramada.getValor();
								}
							}
						}
					}
				}

				// Esperado Acumulado
				celda = "";
				if (indicador.getUltimoProgramado() != null)
					celda = indicador.getUltimoProgramadoFormateado();
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Ejecutado Acumulado
				celda = "";
				if (indicador.getUltimaMedicion() != null)
					celda = indicador.getUltimaMedicionFormateada();
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Total Esperado Acumulado
				celda = (totalPresupuestoProgramado != null ? VgcFormatter.formatearNumero(totalPresupuestoProgramado)
						: "");
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Total Ejecutado Acumulado
				celda = (totalPresupuestoReal != null ? VgcFormatter.formatearNumero(totalPresupuestoReal) : "");
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// % Cumplimiento
				celda = "";
				if (totalPresupuestoProgramado != null && totalPresupuestoReal != null
						&& totalPresupuestoProgramado != 0D) {
					Double valor = (totalPresupuestoReal / totalPresupuestoProgramado) * 100;
					if (valor > 100D)
						valor = 100D;
					celda = (valor != null ? VgcFormatter.formatearNumero(valor) : "");
				}
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Responsable
				celda = "";
				if (iniciativa.getResponsableLograrMetaId() != null)
					celda = (iniciativa.getResponsableLograrMeta() != null
							? iniciativa.getResponsableLograrMeta().getNombreCargo()
							: "");
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_CENTER;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Explicacion
				celda = "";
				String periodoBuscar = reporte.getMesFinal() + "/" + reporte.getAnoFinal();
				Explicacion explicacion = BuscarExplicacion(reporte, periodoBuscar, iniciativa.getIniciativaId(),
						ObjetivoKey.getKeyIniciativa(), strategosExplicacionesService);
				if (explicacion != null && explicacion.getMemosExplicacion() != null
						&& explicacion.getMemosExplicacion().size() > 0) {
					for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext();) {
						MemoExplicacion memoExplicacion = (MemoExplicacion) i.next();
						Byte tipoMemo = memoExplicacion.getPk().getTipo();
						if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION))) {
							celda = memoExplicacion.getMemo();
							break;
						}
					}
					if (celda != null && !celda.equals("")) {
						string = new StringBuilder();
						string.append(celda);
						string.append("\n");
						string.append("\n");
						celda = string.toString();
					}
				}
				contador++;
				fila[contador][0] = celda;
				fila[contador][1] = TablaBasicaPDF.H_ALINEACION_JUSTIFIED;
				fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);

				// Agregar Fila
				int cantidadLineas = tabla.lineasFila(fila, paginaAncho);
				if ((lineas + cantidadLineas + maxLineasAntesTabla) >= tamanoPagina) {
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
					tablaIniciada = false;
				}
				tabla.agregarFila(fila);
				lineas = getNumeroLinea((lineas + cantidadLineas), inicioLineas);
				if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
					lineas = inicioLineas;
					documento.add(tabla.getTabla());
					tamanoPagina = lineasxPagina(font);
					saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
					tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
					tablaIniciada = true;
				}

			}

			if (!tablaIniciada)
				documento.add(tabla.getTabla());

			documento.add(lineaEnBlanco(font));						

			if (reporte.getVisualizarActividad()) {
				
				
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();
					
					
					
					Paragraph subTitulo = new Paragraph(mensajes.getMessage("jsp.modulo.actividad.titulo.singular")
							+ " : " + iniciativa.getNombre(), font);
					subTitulo.setAlignment(Element.ALIGN_LEFT);
					subTitulo.setIndentationLeft(16);
					documento.add(subTitulo);
	
					dibujarInformacionActividad(reporte, font, iniciativa, documento, strategosMedicionesService,
							strategosIndicadoresService, mensajes, request);

					documento.add(lineaEnBlanco(font));					
				}
			}

			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}
		} else {
			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.noindicadores",
					reporte.getPlantillaPlanes().getNombreIniciativaPlural().toLowerCase(),
					new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
							.getNombrePerspectiva(reporte, (nivel)).toLowerCase()),
					font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}

			documento.add(lineaEnBlanco(font));
			lineas = getNumeroLinea(lineas, inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				tamanoPagina = lineasxPagina(font);
				saltarPagina(documento, false, font, null, null, request);
			}
		}

		strategosExplicacionesService.close();
		strategosPlanesService.close();
	}

	private void dibujarInformacionActividad(ReporteForm reporte, Font font, Iniciativa iniciativa, Document documento,
			StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();
		
		Font fontTitulos = new Font(getConfiguracionPagina(request).getCodigoFuente());
		fontTitulos.setSize(14);
		fontTitulos.setStyle(Font.BOLD);	 

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		filtros = new HashMap<String, Object>();
		filtros.put("proyectoId", iniciativa.getProyectoId());
		List<PryActividad> actividades = strategosPryActividadesService
				.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);

		int[] columnas = new int[10];
		columnas[0] = 21;
		columnas[1] = 9;
		columnas[2] = 12;
		columnas[3] = 5;
		columnas[4] = 10;
		columnas[5] = 7;
		columnas[6] = 10;
		columnas[7] = 12;
		columnas[8] = 22;
		columnas[9] = 10;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setColorFondo(128, 128, 128);
		tabla.setColorLetra(255, 255, 255);
		tabla.setFont(Font.DEFAULTSIZE);
		tabla.setFormatoFont(Font.BOLD);
		tabla.setTamanoFont(8);
		tabla.setAlineacionHorizontal(1);
				

		tabla.agregarCelda(reporte.getPlantillaPlanes().getNombreActividadSingular());
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));
		tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.dias.diferencia"));


		tabla.setAlineacionHorizontal(1);
		
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_MIDDLE);
		tabla.setFont(Font.NORMAL);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.setColorLetra(0, 0, 0);
		tabla.setColorFondo(255, 255, 255);
		tabla.setTamanoFont(7);

		if (actividades.size() > 0) {
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
						actividad.getIndicadorId());

				// Dibujar Informacion de la Iniciativa
				
				String texto1;
				Date fechaAh = new Date();
				Date fechaFi = new Date();

				if (lineas >= tamanoPagina) {
					lineas = inicioLineas;
					tamanoPagina = inicioTamanoPagina;
					saltarPagina(documento, false, font, null, null, request);
				}
				tabla.agregarCelda(actividad.getNombre());
				tabla.agregarCelda(VgcFormatter.formatearFecha(actividad.getComienzoPlan(), "formato.fecha.corta"));
				tabla.agregarCelda(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
				tabla.agregarCelda(VgcFormatter.formatearNumero(actividad.getPeso()));
				tabla.agregarCelda(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));

				String url = obtenerCadenaRecurso(request);
				if (actividad.getAlerta() == null)
					tabla.agregarCelda("");
				else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
					tabla.agregarCelda(
							Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
				else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
					tabla.agregarCelda(
							Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
				else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
					tabla.agregarCelda(
							Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));

				tabla.agregarCelda(
						actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
				tabla.agregarCelda(
						actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
				if (actividad.getResponsableSeguimiento() != null)
					tabla.agregarCelda(actividad.getResponsableSeguimiento().getNombre());
				else {
					tabla.agregarCelda("");
				}

				texto1 = "";
				fechaAh = actividad.getComienzoPlan();
				fechaFi = actividad.getFinPlan();

				if (fechaAh != null && fechaFi != null) {
					int milisecondsByDay = 86400000;
					int dias = (int) ((fechaFi.getTime() - fechaAh.getTime()) / milisecondsByDay);
					texto1 = "" + dias;
				} else {
					texto1 = "";
				}

				tabla.agregarCelda(texto1);
				

			}
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(tabla.getTabla());
			
			
			
			
			
		} else {
			// TODO
			fontTitulos.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noactividades",
					reporte.getPlantillaPlanes().getNombreActividadPlural().toLowerCase(),
					reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			fontTitulos.setColor(0, 0, 255);
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
		}
		strategosPryActividadesService.close();
	}

	private Explicacion BuscarExplicacion(ReporteForm reporte, String fechaMedicion, Long objetoId, Byte objetoKey,
			StrategosExplicacionesService strategosExplicacionesService) {
		Map<String, String> filtros = new HashMap<String, String>();

		String[] periodo = fechaMedicion.split("/");
		int ultimoDiaMes = PeriodoUtil.ultimoDiaMes(Integer.parseInt(periodo[0]), Integer.parseInt(periodo[1]));

		filtros.put("tipo", "0");
		filtros.put("objetoId", objetoId.toString());
		filtros.put("objetoKey", objetoKey.toString());
		filtros.put("fechaDesde", "01/" + periodo[0] + "/" + periodo[1]);
		filtros.put("fechaHasta", ultimoDiaMes + "/" + periodo[0] + "/" + periodo[1]);

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 100, null, null, true,
				filtros);
		Explicacion explicacion = null;
		if (paginaExplicaciones.getLista().size() > 0) {
			explicacion = (Explicacion) paginaExplicaciones.getLista().get(0);
			explicacion = (Explicacion) strategosExplicacionesService.load(Explicacion.class,
					explicacion.getExplicacionId());
		}

		return explicacion;
	}

	private TablaBasicaPDF crearTabla(boolean newTable, boolean isInformativo, String[][] columnas, ReporteForm reporte,
			Font font, MessageResources mensajes, Document documento, HttpServletRequest request) throws Exception {
		Color colorLetra = null;
		Color colorFondo = null;
		Integer anchoBorde = null;
		Integer anchoBordeCelda = null;
		Boolean bold = true;
		Integer alineacionHorizontal = null;
		Integer alineacionVertical = null;

		if (isInformativo) {
			colorLetra = new Color(0, 0, 0);
			colorFondo = new Color(255, 255, 255);
			anchoBorde = 0;
			anchoBordeCelda = 0;
			bold = false;
			alineacionHorizontal = TablaBasicaPDF.H_ALINEACION_LEFT;
			alineacionVertical = TablaBasicaPDF.V_ALINEACION_MIDDLE;
		} else {
			colorLetra = new Color(255, 255, 255);
			colorFondo = new Color(128, 128, 128);
		}

		if (tablaHeader != null && newTable)
			tablaHeader = null;

		TablaBasicaPDF tabla = inicializarTabla(font, columnas, anchoBorde, anchoBordeCelda, bold, colorLetra,
				colorFondo, alineacionHorizontal, alineacionVertical, request);

		if (!isInformativo) {
			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
			tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_MIDDLE);
			tabla.setFont(Font.NORMAL);
			tabla.setFormatoFont(Font.NORMAL);
			tabla.setColorLetra(0, 0, 0);
			tabla.setColorFondo(255, 255, 255);
			tabla.setTamanoFont(7);
		}

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		java.awt.Font fontNormal = new java.awt.Font(font.getFamilyname(), font.style(),
				((Float) (font.size())).intValue());
		FontMetrics metrics = img.getGraphics().getFontMetrics(fontNormal);

		int contador = -1;
		Object[][] fila = new Object[columnas.length][3];
		for (int f = 0; f < columnas.length; f++) {
			contador++;
			fila[contador][0] = columnas[f][1];
			fila[contador][1] = TablaBasicaPDF.H_ALINEACION_LEFT;
			fila[contador][2] = metrics.stringWidth((String) fila[contador][0]);
		}

		int cantidadLineas = tabla.lineasFila(fila, paginaAncho);
		if ((lineas + cantidadLineas + maxLineasAntesTabla) >= tamanoPagina) {
			lineas = inicioLineas;
			tamanoPagina = lineasxPagina(font);
			saltarPagina(documento, false, font, null, null, request);
		}
		lineas = getNumeroLinea((lineas + cantidadLineas), inicioLineas);

		return tabla;
	}

	public void SetMedicionesPerspectiva(Perspectiva perspectiva, ReporteForm reporte,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosMedicionesService strategosMedicionesService) {
		if (reporte.getTipoPeriodo().byteValue() == reporte.getPeriodoPorPeriodo().byteValue()) {
			// Buscar Medicion de Indicador Parcial
			Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
					perspectiva.getNlParIndicadorId());
			if (indicador != null) {
				LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
						((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
						((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
						((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
						((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
						indicador.getFrecuencia().byteValue());
				int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
				int periodoFin = periodoInicio;
				if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
					periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

				boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()
						&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
								.byteValue());
				List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(
						indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()),
						new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin, indicador.getFrecuencia(),
						indicador.getFrecuencia(), acumular, acumular);
				Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);

				if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
					perspectiva.setUltimaMedicionParcial(ultimaMedicion.getValor());
			}

			// Buscar Medicion de Indicador Anual
			indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
					perspectiva.getNlAnoIndicadorId());
			if (indicador != null) {
				LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
						((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
						((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
						((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
						((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
						indicador.getFrecuencia().byteValue());
				int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
				int periodoFin = periodoInicio;
				if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
					periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

				boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()
						&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
								.byteValue());
				List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(
						indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()),
						new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin, indicador.getFrecuencia(),
						indicador.getFrecuencia(), acumular, acumular);
				Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);

				if (ultimaMedicion != null && ultimaMedicion.getValor() != null)
					perspectiva.setUltimaMedicionAnual(ultimaMedicion.getValor());
			}
		}
	}

	private String obtenerCadenaRecurso(HttpServletRequest request) {
		String result = null;
		if (request.getServerPort() == 80 && request.getScheme().equals("http")) {

			result = request.getServerName() + "/" + request.getContextPath();
			result = "https" + "://" + result.replaceAll("//", "/");

		} else {
			result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
			result = request.getScheme() + "://" + result.replaceAll("//", "/");

		}
		return result;

	}

}