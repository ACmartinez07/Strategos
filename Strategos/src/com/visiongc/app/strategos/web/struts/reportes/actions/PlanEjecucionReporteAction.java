/**
 *
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

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
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
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
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

/**
 * @author Andres Martinez 23-05-2022
 */
public class PlanEjecucionReporteAction extends VgcReporteBasicoAction {
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		String source = request.getParameter("source");
		if (source.equals("Plan"))
			return mensajes.getMessage("jsp.reportes.plan.ejecucion.titulo");
		else
			return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.detallado.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {

		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		// Obtencion del request
		String tipo = (request.getParameter("tipo"));
		String ano = (request.getParameter("ano"));
		String estatus = (request.getParameter("estatus"));		
		String todos = (request.getParameter("todos"));
		Font fuente = getConfiguracionPagina(request).getFuente();

		/* Parametros para el reporte */
		String source = request.getParameter("source");
		reporte.setPlanId(
				request.getParameter("planId") != null && !request.getParameter("planId").toString().equals("")
						? Long.parseLong(request.getParameter("planId"))
						: null);
		reporte.setObjetoSeleccionadoId(request.getParameter("objetoSeleccionadoId") != null
				&& !request.getParameter("objetoSeleccionadoId").toString().equals("")
						? Long.parseLong(request.getParameter("objetoSeleccionadoId"))
						: null);
		reporte.setMesInicial(request.getParameter("mesInicial"));
		reporte.setMesFinal(request.getParameter("mesFinal"));
		reporte.setAnoInicial(request.getParameter("anoInicial"));
		reporte.setAnoFinal(request.getParameter("anoFinal"));
		reporte.setAlcance(request.getParameter("alcance") != null ? Byte.parseByte(request.getParameter("alcance"))
				: reporte.getAlcancePlan());
		reporte.setAno(Integer.parseInt(ano));
		reporte.setTipo(Long.parseLong(tipo));
		reporte.setEstatus(estatus);
		// Visualizar Objetivos
		reporte.setVisualizarObjetivo((request.getParameter("visualizarObjetivo") != null
				? (request.getParameter("visualizarObjetivo").equals("1") ? true : false)
				: false));
		reporte.setVisualizarObjetivoEstadoParcial((request.getParameter("visualizarObjetivoEstadoParcial") != null
				? (request.getParameter("visualizarObjetivoEstadoParcial").equals("1") ? true : false)
				: false));
		reporte.setVisualizarObjetivoEstadoAnual((request.getParameter("visualizarObjetivoEstadoAnual") != null
				? (request.getParameter("visualizarObjetivoEstadoAnual").equals("1") ? true : false)
				: false));
		reporte.setVisualizarObjetivoAlerta((request.getParameter("visualizarObjetivoAlerta") != null
				? (request.getParameter("visualizarObjetivoAlerta").equals("1") ? true : false)
				: false));
		// Visualizar Indicadores
		reporte.setVisualizarIndicadores((request.getParameter("visualizarIndicadores") != null
				? (request.getParameter("visualizarIndicadores").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIndicadoresEjecutado((request.getParameter("visualizarIndicadoresEjecutado") != null
				? (request.getParameter("visualizarIndicadoresEjecutado").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIndicadoresMeta((request.getParameter("visualizarIndicadoresMeta") != null
				? (request.getParameter("visualizarIndicadoresMeta").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIndicadoresEstadoParcial(
				(request.getParameter("visualizarIndicadoresEstadoParcial") != null
						? (request.getParameter("visualizarIndicadoresEstadoParcial").equals("1") ? true : false)
						: false));
		reporte.setVisualizarIndicadoresEstadoParcialSuavisado(
				(request.getParameter("visualizarIndicadoresEstadoParcialSuavisado") != null
						? (request.getParameter("visualizarIndicadoresEstadoParcialSuavisado").equals("1") ? true
								: false)
						: false));
		reporte.setVisualizarIndicadoresEstadoAnual((request.getParameter("visualizarIndicadoresEstadoAnual") != null
				? (request.getParameter("visualizarIndicadoresEstadoAnual").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIndicadoresEstadoAnualSuavisado(
				(request.getParameter("visualizarIndicadoresEstadoAnualSuavisado") != null
						? (request.getParameter("visualizarIndicadoresEstadoAnualSuavisado").equals("1") ? true : false)
						: false));
		reporte.setVisualizarIndicadoresAlerta((request.getParameter("visualizarIndicadoresAlerta") != null
				? (request.getParameter("visualizarIndicadoresAlerta").equals("1") ? true : false)
				: false));
		// Visualizar Iniciativas
		reporte.setVisualizarIniciativas((request.getParameter("visualizarIniciativas") != null
				? (request.getParameter("visualizarIniciativas").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIniciativasEjecutado((request.getParameter("visualizarIniciativasEjecutado") != null
				? (request.getParameter("visualizarIniciativasEjecutado").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIniciativasMeta((request.getParameter("visualizarIniciativasMeta") != null
				? (request.getParameter("visualizarIniciativasMeta").equals("1") ? true : false)
				: false));
		reporte.setVisualizarIniciativasAlerta((request.getParameter("visualizarIniciativasAlerta") != null
				? (request.getParameter("visualizarIniciativasAlerta").equals("1") ? true : false)
				: false));
		// Visualizar Actividades
		reporte.setVisualizarActividad((request.getParameter("visualizarActividad") != null
				? (request.getParameter("visualizarActividad").equals("1") ? true : false)
				: false));
		reporte.setVisualizarActividadEjecutado((request.getParameter("visualizarActividadEjecutado") != null
				? (request.getParameter("visualizarActividadEjecutado").equals("1") ? true : false)
				: false));
		reporte.setVisualizarActividadMeta((request.getParameter("visualizarActividadMeta") != null
				? (request.getParameter("visualizarActividadMeta").equals("1") ? true : false)
				: false));
		reporte.setVisualizarActividadAlerta((request.getParameter("visualizarActividadAlerta") != null
				? (request.getParameter("visualizarActividadAlerta").equals("1") ? true : false)
				: false));

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());

		// Titulo de filtros aplicados
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_SUBTITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);
		
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		
		Paragraph texto = new Paragraph("Filtros del reporte: ", font);
		documento.add(texto);
		documento.add(lineaEnBlanco(fuente));
		crearTablaFiltros(tabla, mensajes, reporte, documento, request, todos, fuente);
		documento.add(tabla.getTabla());
		
		documento.add(lineaEnBlanco(fuente));
		documento.add(lineaEnBlanco(fuente));

		if (source.equals("Plan"))
			todos = "";
		// EjecucionPlan(reporte, documento, request, mensajes, source);
		else if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan"))
			EjecucionIniciativa(reporte, documento, request, mensajes, source, todos, fuente);

	}

	private void EjecucionIniciativa(ReporteForm reporte, Document documento, HttpServletRequest request,
			MessageResources mensajes, String source, String todos, Font fuente)
			throws Exception {
		
		Font fontTitulos = new Font(getConfiguracionPagina(request).getCodigoFuente());
		fontTitulos.setSize(14);
		fontTitulos.setStyle(Font.BOLD);	 	
		Integer nivel = 0;
		inicioTamanoPagina = lineasxPagina(fuente);
		tamanoPagina = inicioTamanoPagina;

		if (reporte.getVisualizarIniciativas()) {
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
					.openStrategosIndicadoresService();
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
					.openStrategosMedicionesService();
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
					.openStrategosIniciativasService();
			StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
					.openStrategosOrganizacionesService();

			Map<String, Object> filtros = new HashMap<String, Object>();
			//font.setStyle(Font.NORMAL);

			List<OrganizacionStrategos> organizaciones = organizacionservice
					.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

			filtros = new HashMap<String, Object>();

			if (source.equals("Iniciativa") || source.equals("IniciativaGeneral") || source.equals("IniciativaPlan")) {

				// Iniciativa Seleccionada
				if (request.getParameter("alcance").equals("1")) {
					OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(
							OrganizacionStrategos.class,
							((OrganizacionStrategos) request.getSession().getAttribute("organizacion"))
									.getOrganizacionId());

					if (org != null) {
						Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), fontTitulos);
						textoOrg.setAlignment(Element.ALIGN_LEFT);
						documento.add(textoOrg);
						documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
					}

					String filtroNombre = (request.getParameter("filtroNombre") != null)
							? request.getParameter("filtroNombre")
							: "";
					Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
							&& request.getParameter("selectHitoricoType") != "")
									? Byte.parseByte(request.getParameter("selectHitoricoType"))
									: HistoricoType.getFiltroHistoricoNoMarcado();

					FiltroForm filtro = new FiltroForm();
					filtro.setHistorico(selectHitoricoType);
					if (filtroNombre.equals(""))
						filtro.setNombre(null);
					else
						filtro.setNombre(filtroNombre);
					reporte.setFiltro(filtro);

					Plan plan = null;
					if (reporte.getPlanId() != null) {
						plan = (Plan) strategosIniciativasService.load(Plan.class, reporte.getPlanId());
						reporte.setPlantillaPlanes((PlantillaPlanes) strategosIniciativasService
								.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
					} else {
						PlantillaPlanes plantilla = new PlantillaPlanes();
						plantilla.setNombreActividadSingular(
								mensajes.getMessage("jsp.modulo.actividad.titulo.singular"));
						plantilla.setNombreIniciativaSingular(
								((NavegadorForm) request.getSession().getAttribute("activarIniciativa"))
										.getNombreSingular());
						plantilla.setNombreIndicadorSingular(
								mensajes.getMessage("jsp.modulo.indicador.titulo.singular"));
						reporte.setPlantillaPlanes(plantilla);
					}
					if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
						filtros.put("iniciativaId", reporte.getObjetoSeleccionadoId());
					else if (plan != null)
						filtros.put("planId", plan.getPlanId());
					if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
						filtros.put("historicoDate", "IS NULL");
					else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoMarcado())
						filtros.put("historicoDate", "IS NOT NULL");
					if (reporte.getFiltro().getNombre() != null)
						filtros.put("nombre", reporte.getFiltro().getNombre());
					if (reporte.getTipo() != 0) {
						filtros.put("tipoId", reporte.getTipo());
					}
					if (todos.equals("false")) {
						filtros.put("anio", reporte.getAno());
					}
					if (reporte.getEstatus() != null)
						filtros.put("estatus", reporte.getEstatus());

					List<Iniciativa> iniciativas = strategosIniciativasService
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativas.size() > 0) {

						// Inicializacion Encabezado Tabla Iniciativas
						TablaPDF tabla = null;
						tabla = new TablaPDF(getConfiguracionPagina(request), request);

						// Se asigna el header de la tabla
						crearTablaTitulo(tabla, mensajes, 1);

						for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter.next();
							Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

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
							List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin);
							List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin);

							// Dibujar Informacion de la Iniciativa
							crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado,
									medicionesEjecutado, fuente, mensajes, documento, request, tabla);
							documento.add(tabla.getTabla());

							Paragraph texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorSingular()
									+ " : " + indicador.getNombre(), fontTitulos);
							texto.setAlignment(Element.ALIGN_LEFT);
							texto.setIndentationLeft(16);
							documento.add(texto);

							// Crear tabla del Indicador
							if (medicionesEjecutado.size() != 0)
								crearTablaIndicador(indicador, reporte, medicionesEjecutado, medicionesProgramado,
										new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(),
										reporte.getVisualizarIniciativasEjecutado(),
										reporte.getVisualizarIniciativasMeta(), false, false, false, false,
										reporte.getVisualizarIniciativasAlerta(), fuente, mensajes,
										strategosMedicionesService, strategosIndicadoresService, documento, request);
							else {
								documento.add(lineaEnBlanco(fuente));

								fontTitulos.setColor(0, 0, 255);								
								texto = new Paragraph(mensajes.getMessage(
										"jsp.reportes.plan.ejecucion.reporte.indicadores.nomediciones",
										reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()),
										fontTitulos);
								texto.setIndentationLeft(50);
								documento.add(texto);
								fontTitulos.setColor(0, 0, 0);
							}
							if (reporte.getVisualizarActividad())
								dibujarInformacionActividad(reporte, fuente, fontTitulos, iniciativa, documento,
										strategosMedicionesService, strategosIndicadoresService, mensajes, request);
						}
					}

				} // Organizacion Seleccionada
				else if (request.getParameter("alcance").equals("5")) {
					OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(
							OrganizacionStrategos.class,
							((OrganizacionStrategos) request.getSession().getAttribute("organizacion"))
									.getOrganizacionId());

					if (org != null) {
						// Nombre de la Organizacion, plan y periodo del reporte												
						Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), fontTitulos);
						textoOrg.setAlignment(Element.ALIGN_LEFT);
						documento.add(textoOrg);

						documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
					}

					String filtroNombre = (request.getParameter("filtroNombre") != null)
							? request.getParameter("filtroNombre")
							: "";
					Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
							&& request.getParameter("selectHitoricoType") != "")
									? Byte.parseByte(request.getParameter("selectHitoricoType"))
									: HistoricoType.getFiltroHistoricoNoMarcado();

					FiltroForm filtro = new FiltroForm();
					filtro.setHistorico(selectHitoricoType);
					if (filtroNombre.equals(""))
						filtro.setNombre(null);
					else
						filtro.setNombre(filtroNombre);
					reporte.setFiltro(filtro);

					Plan plan = null;
					if (reporte.getPlanId() != null) {
						plan = (Plan) strategosIniciativasService.load(Plan.class, reporte.getPlanId());
						reporte.setPlantillaPlanes((PlantillaPlanes) strategosIniciativasService
								.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
					} else {
						PlantillaPlanes plantilla = new PlantillaPlanes();
						plantilla.setNombreActividadSingular(
								mensajes.getMessage("jsp.modulo.actividad.titulo.singular"));
						plantilla.setNombreIniciativaSingular(
								((NavegadorForm) request.getSession().getAttribute("activarIniciativa"))
										.getNombreSingular());
						plantilla.setNombreIndicadorSingular(
								mensajes.getMessage("jsp.modulo.indicador.titulo.singular"));
						reporte.setPlantillaPlanes(plantilla);
					}
					if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivoOrganizacion().byteValue())
						filtros.put("organizacionId",
								((OrganizacionStrategos) request.getSession().getAttribute("organizacion"))
										.getOrganizacionId());
					else if (plan != null)
						filtros.put("planId", plan.getPlanId());
					if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
						filtros.put("historicoDate", "IS NULL");
					else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoMarcado())
						filtros.put("historicoDate", "IS NOT NULL");
					if (reporte.getFiltro().getNombre() != null)
						filtros.put("nombre", reporte.getFiltro().getNombre());
					if (reporte.getTipo() != 0) {
						filtros.put("tipoId", reporte.getTipo());
					}
					if (todos.equals("false")) {
						filtros.put("anio", reporte.getAno());
					}
					if (reporte.getEstatus() != null)
						filtros.put("estatus", reporte.getEstatus());

					List<Iniciativa> iniciativas = strategosIniciativasService
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativas.size() > 0) {
						// Inicializacion Encabezado Tabla Iniciativas
						TablaPDF tabla = null;
						tabla = new TablaPDF(getConfiguracionPagina(request), request);

						// Se asigna el header de la tabla
						crearTablaTitulo(tabla, mensajes, 1);

						for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter.next();
							Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

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
							List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin);
							List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin);

							// Dibujar Informacion de la Iniciativa
							crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado,
									medicionesEjecutado, fuente, mensajes, documento, request, tabla);
						}
						documento.add(tabla.getTabla());
						documento.add(lineaEnBlanco(fuente));

						for (Iterator<Iniciativa> iter2 = iniciativas.iterator(); iter2.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter2.next();
							Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

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
							List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin);
							List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()),
									periodoInicio, periodoFin);

							Paragraph texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIniciativaSingular()
									+ " : " + iniciativa.getNombre(), fontTitulos);
							texto.setAlignment(Element.ALIGN_LEFT);
							texto.setIndentationLeft(16);
							documento.add(texto);

							texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorSingular() + " : "
									+ indicador.getNombre(), fontTitulos);
							texto.setAlignment(Element.ALIGN_LEFT);
							texto.setIndentationLeft(16);
							documento.add(texto);

							// Crear tabla del Indicador
							if (medicionesEjecutado.size() != 0)
								crearTablaIndicador(indicador, reporte, medicionesEjecutado, medicionesProgramado,
										new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(),
										reporte.getVisualizarIniciativasEjecutado(),
										reporte.getVisualizarIniciativasMeta(), false, false, false, false,
										reporte.getVisualizarIniciativasAlerta(), fuente, mensajes,
										strategosMedicionesService, strategosIndicadoresService, documento, request);
							else {
								documento.add(lineaEnBlanco(fuente));

								fontTitulos.setColor(0, 0, 255);
								texto = new Paragraph(mensajes.getMessage(
										"jsp.reportes.plan.ejecucion.reporte.indicadoresw.nomediciones",
										reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()),
										fontTitulos);
								texto.setIndentationLeft(50);
								documento.add(texto);
								fontTitulos.setColor(0, 0, 0);
							}
							if (reporte.getVisualizarActividad())
								dibujarInformacionActividad(reporte, fuente, fontTitulos, iniciativa, documento,
										strategosMedicionesService, strategosIndicadoresService, mensajes, request);
						}
					} else {
						documento.add(lineaEnBlanco(fuente));

						fuente.setColor(0, 0, 255);
						Paragraph texto = new Paragraph(
								mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas", "INICIATIVAS",
										"ORGANIZACIÓN"),
								fuente);
						texto.setIndentationLeft(50);
						documento.add(texto);
						fuente.setColor(0, 0, 0);
						documento.add(lineaEnBlanco(fuente));
					}

				} // Todas las Organizaciones
				else {

					String filtroNombre = (request.getParameter("filtroNombre") != null)
							? request.getParameter("filtroNombre")
							: "";
					Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
							&& request.getParameter("selectHitoricoType") != "")
									? Byte.parseByte(request.getParameter("selectHitoricoType"))
									: HistoricoType.getFiltroHistoricoNoMarcado();

					FiltroForm filtro = new FiltroForm();
					filtro.setHistorico(selectHitoricoType);
					if (filtroNombre.equals(""))
						filtro.setNombre(null);
					else
						filtro.setNombre(filtroNombre);
					reporte.setFiltro(filtro);

					if (organizaciones.size() > 0) {
						for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();) {

							OrganizacionStrategos organizacion = (OrganizacionStrategos) iter.next();
							if (organizacion != null) {
								// Nombre de la Organizacion, plan y periodo del reporte
								Paragraph textoOrg = new Paragraph("Organización: " + organizacion.getNombre(), fontTitulos);
								textoOrg.setAlignment(Element.ALIGN_LEFT);
								documento.add(textoOrg);
								documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
							}

							Plan plan = null;
							if (reporte.getPlanId() != null) {
								plan = (Plan) strategosIniciativasService.load(Plan.class, reporte.getPlanId());
								reporte.setPlantillaPlanes((PlantillaPlanes) strategosIniciativasService
										.load(PlantillaPlanes.class, new Long(plan.getMetodologiaId())));
							} else {
								PlantillaPlanes plantilla = new PlantillaPlanes();
								plantilla.setNombreActividadSingular(
										mensajes.getMessage("jsp.modulo.actividad.titulo.singular"));
								plantilla.setNombreIniciativaSingular(
										((NavegadorForm) request.getSession().getAttribute("activarIniciativa"))
												.getNombreSingular());
								plantilla.setNombreIndicadorSingular(
										mensajes.getMessage("jsp.modulo.indicador.titulo.singular"));
								reporte.setPlantillaPlanes(plantilla);
							}
							filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
							if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
									.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
								filtros.put("historicoDate", "IS NULL");
							else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
									.byteValue() == HistoricoType.getFiltroHistoricoMarcado())
								filtros.put("historicoDate", "IS NOT NULL");
							else if (plan != null)
								filtros.put("planId", plan.getPlanId());
							if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
									.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
								filtros.put("historicoDate", "IS NULL");
							else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
									.byteValue() == HistoricoType.getFiltroHistoricoMarcado())
								filtros.put("historicoDate", "IS NOT NULL");
							if (reporte.getFiltro().getNombre() != null)
								filtros.put("nombre", reporte.getFiltro().getNombre());
							if (reporte.getTipo() != 0) {
								filtros.put("tipoId", reporte.getTipo());
							}
							if (todos.equals("false")) {
								filtros.put("anio", reporte.getAno());
							}
							if (reporte.getEstatus() != null)
								filtros.put("estatus", reporte.getEstatus());

							List<Iniciativa> iniciativas = strategosIniciativasService
									.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

							if (iniciativas.size() > 0) {

								// Inicializacion Encabezado Tabla Iniciativas
								TablaPDF tabla = null;
								tabla = new TablaPDF(getConfiguracionPagina(request), request);

								// Se asigna el header de la tabla
								crearTablaTitulo(tabla, mensajes, 1);

								for (Iterator<Iniciativa> iter2 = iniciativas.iterator(); iter2.hasNext();) {
									Iniciativa iniciativa = (Iniciativa) iter2.next();
									Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
											iniciativa
													.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

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
									List<Medicion> medicionesEjecutado = strategosMedicionesService
											.getMedicionesPeriodo(indicador.getIndicadorId(),
													SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()),
													new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
									List<Medicion> medicionesProgramado = strategosMedicionesService
											.getMedicionesPeriodo(indicador.getIndicadorId(),
													SerieTiempo.getSerieProgramadoId(),
													new Integer(reporte.getAnoInicial()),
													new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

									// Dibujar Informacion de la Iniciativa
									crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado,
											medicionesEjecutado, fuente, mensajes, documento, request, tabla);
								}
								documento.add(tabla.getTabla());
								for (Iterator<Iniciativa> iter2 = iniciativas.iterator(); iter2.hasNext();) {
									Iniciativa iniciativa = (Iniciativa) iter2.next();
									Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
											iniciativa
													.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

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
									List<Medicion> medicionesEjecutado = strategosMedicionesService
											.getMedicionesPeriodo(indicador.getIndicadorId(),
													SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()),
													new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
									List<Medicion> medicionesProgramado = strategosMedicionesService
											.getMedicionesPeriodo(indicador.getIndicadorId(),
													SerieTiempo.getSerieProgramadoId(),
													new Integer(reporte.getAnoInicial()),
													new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

									Paragraph texto = new Paragraph(
											reporte.getPlantillaPlanes().getNombreIniciativaSingular() + " : "
													+ iniciativa.getNombre(),
													fontTitulos);
									texto.setAlignment(Element.ALIGN_LEFT);
									texto.setIndentationLeft(16);
									documento.add(texto);

									texto = new Paragraph(reporte.getPlantillaPlanes().getNombreIndicadorSingular()
											+ " : " + indicador.getNombre(), fontTitulos);
									texto.setAlignment(Element.ALIGN_LEFT);
									texto.setIndentationLeft(16);
									documento.add(texto);

									// Crear tabla del Indicador
									if (medicionesEjecutado.size() != 0)
										crearTablaIndicador(indicador, reporte, medicionesEjecutado,
												medicionesProgramado, new ArrayList<IndicadorEstado>(),
												new ArrayList<IndicadorEstado>(),
												reporte.getVisualizarIniciativasEjecutado(),
												reporte.getVisualizarIniciativasMeta(), false, false, false, false,
												reporte.getVisualizarIniciativasAlerta(), fuente, mensajes,
												strategosMedicionesService, strategosIndicadoresService, documento,
												request);
									else {										
										fontTitulos.setColor(0, 0, 255);
										texto = new Paragraph(mensajes.getMessage(
												"jsp.reportes.plan.ejecucion.reporte.iniciativas.nomediciones",
												reporte.getPlantillaPlanes().getNombreIniciativaSingular()
														.toLowerCase()),
												fontTitulos);
										texto.setIndentationLeft(50);
										documento.add(texto);
										fontTitulos.setColor(0, 0, 0);
									}
									if (reporte.getVisualizarActividad())										
										dibujarInformacionActividad(reporte, fuente, fontTitulos, iniciativa, documento,
												strategosMedicionesService, strategosIndicadoresService, mensajes,
												request);
								}
							}
						}
					} else {
						documento.add(lineaEnBlanco(fuente));

						fuente.setColor(0, 0, 255);
						Paragraph texto = new Paragraph(
								mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas", "INICIATIVAS",
										"ORGANIZACIÓN"),
								fuente);
						texto.setIndentationLeft(50);
						documento.add(texto);
						fuente.setColor(0, 0, 0);
						documento.add(lineaEnBlanco(fuente));
					}
				}
			}
			strategosIndicadoresService.close();
			strategosMedicionesService.close();
			strategosIniciativasService.close();
		}

	}

	private void dibujarInformacionActividad(ReporteForm reporte, Font font, Font fontTitulos, Iniciativa iniciativa, Document documento,
			StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;		
		
		filtros = new HashMap<String, Object>();
		filtros.put("proyectoId", iniciativa.getProyectoId());
		List<PryActividad> actividades = strategosPryActividadesService
				.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		// Se asigna el header de la tabla
		crearTablaTitulo(tabla, mensajes, 2);

		tabla.setAlineacionHorizontal(1);

		if (actividades.size() > 0) {
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
						actividad.getIndicadorId());

				// Dibujar Informacion de la Iniciativa
				crearTablaActividad(reporte, actividad, indicador, font, mensajes, documento, request, tabla);

				//TODO
			}
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(tabla.getTabla());
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
						actividad.getIndicadorId());

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

				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
						indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()),
						new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
						indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
						new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
						periodoFin);

				texto = new Paragraph(
						reporte.getPlantillaPlanes().getNombreIniciativaSingular() + " : " + iniciativa.getNombre(),
						fontTitulos);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);

				texto = new Paragraph(
						reporte.getPlantillaPlanes().getNombreActividadSingular() + " : " + actividad.getNombre(),
						fontTitulos);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);
				//TODO

				// Crear tabla del Indicador
				if (medicionesEjecutado.size() != 0)
					crearTablaIndicador(indicador, reporte, medicionesEjecutado, medicionesProgramado,
							new ArrayList<IndicadorEstado>(), new ArrayList<IndicadorEstado>(),
							reporte.getVisualizarActividadEjecutado(), reporte.getVisualizarActividadMeta(), false,
							false, false, false, reporte.getVisualizarActividadAlerta(), font, mensajes,
							strategosMedicionesService, strategosIndicadoresService, documento, request);
				else {
					//TODO
					fontTitulos.setColor(0, 0, 255);
					texto = new Paragraph(
							mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.actividades.nomediciones",
									reporte.getPlantillaPlanes().getNombreActividadSingular().toLowerCase()),
							fontTitulos);
					texto.setIndentationLeft(50);
					documento.add(texto);
					fontTitulos.setColor(0, 0, 0);
					documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
					documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
				}
			}
		} else {
			//TODO
			fontTitulos.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noactividades",
					reporte.getPlantillaPlanes().getNombreActividadPlural().toLowerCase(),
					reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()), fontTitulos);
			texto.setIndentationLeft(50);
			documento.add(texto);
			fontTitulos.setColor(0, 0, 0);
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
		}

		strategosMetasService.close();
		strategosPryActividadesService.close();
	}

	private void crearTablaIniciativa(ReporteForm reporte, Iniciativa iniciativa, Indicador indicador,
			List<Medicion> medicionesProgramado, List<Medicion> medicionesEjecutado, Font font,
			MessageResources mensajes, Document documento, HttpServletRequest request, TablaPDF tabla)
			throws Exception {
		if (lineas >= tamanoPagina) {
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}

		PryProyecto proyecto = null;
		if (iniciativa.getProyectoId() != null) {
			StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance()
					.openStrategosPryProyectosService();
			proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
			strategosPryProyectosService.close();
		}

		Double programado = 0D;
		Double porcentajeEsperado = 0D;
		for (Iterator<Medicion> iterEjecutado = medicionesEjecutado.iterator(); iterEjecutado.hasNext();) {
			Medicion ejecutado = (Medicion) iterEjecutado.next();
			for (Iterator<Medicion> iterMeta = medicionesProgramado.iterator(); iterMeta.hasNext();) {
				Medicion meta = (Medicion) iterMeta.next();
				if (ejecutado.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue()
						&& ejecutado.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo()
								.intValue()) {
					if (meta.getValor() != null)
						programado = programado + meta.getValor();
					break;
				}
			}
		}
		if (programado.doubleValue() != 0)
			porcentajeEsperado = (porcentajeEsperado * 100D) / 100D;

		tabla.agregarCelda(iniciativa.getNombre());
		tabla.agregarCelda(proyecto != null && proyecto.getComienzoPlan() != null
				? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(), "formato.fecha.corta"))
				: "");
		tabla.agregarCelda(proyecto != null && proyecto.getFinPlan() != null
				? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta"))
				: "");
		if (reporte.getVisualizarIniciativasEjecutado())
			tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
		if (reporte.getVisualizarIniciativasMeta())
			tabla.agregarCelda(VgcFormatter.formatearNumero(programado));
		if (reporte.getVisualizarIniciativasAlerta()) {
			String url = obtenerCadenaRecurso(request);
			if (iniciativa.getAlerta() == null)
				tabla.agregarCelda("");
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
				tabla.agregarCelda(
						Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
				tabla.agregarCelda(
						Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
				tabla.agregarCelda(
						Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
		}
		if(iniciativa.getResponsableSeguimiento() != null )
			tabla.agregarCelda(iniciativa.getResponsableSeguimiento().getNombre());
		else
			tabla.agregarCelda("  ");
	}

	private void crearTablaActividad(ReporteForm reporte, PryActividad actividad, Indicador indicador, Font font,
			MessageResources mensajes, Document documento, HttpServletRequest request, TablaPDF tabla)
			throws Exception {
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
		if (reporte.getVisualizarActividadAlerta()) {
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
		}

		tabla.agregarCelda(
				actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
		tabla.agregarCelda(
				actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
		tabla.agregarCelda(actividad.getResponsableSeguimiento() != null ? actividad.getResponsableSeguimiento() : "");

	}

	private TablaPDF crearTablaTitulo(TablaPDF tabla, MessageResources mensajes, Integer tipo) throws Exception {

		if (tipo == 1) {
			int[] columnas = new int[7];
			columnas[0] = 30;
			columnas[1] = 8;
			columnas[2] = 11;
			columnas[3] = 8;
			columnas[4] = 10;
			columnas[5] = 6;
			columnas[6] = 25;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);

			tabla.setColorFondo(128, 128, 128);
			tabla.setColorLetra(255, 255, 255);
			tabla.setTamanoFont(12);
			tabla.setFormatoFont(Font.BOLD);
			tabla.setAlineacionHorizontal(1);

			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));

			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);
			tabla.setTamanoFont(10);
			tabla.setFormatoFont(Font.NORMAL);
		}

		if (tipo == 2) {
			int[] columnas = new int[9];
			columnas[0] = 21;
			columnas[1] = 8;
			columnas[2] = 11;
			columnas[3] = 5;
			columnas[4] = 10;
			columnas[5] = 7;
			columnas[6] = 10;
			columnas[7] = 12;
			columnas[8] = 25;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);
			tabla.setColorFondo(128, 128, 128);
			tabla.setColorLetra(255, 255, 255);
			tabla.setTamanoFont(12);
			tabla.setFormatoFont(Font.BOLD);
			tabla.setAlineacionHorizontal(1);

			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.actividades"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.alerta"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));

			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);
			tabla.setTamanoFont(10);
			tabla.setFormatoFont(Font.NORMAL);
		}

		return tabla;

	}

	private void crearTablaIndicador(Indicador indicador, ReporteForm reporte, List<Medicion> mediciones,
			List<Medicion> medicionesMeta, List<IndicadorEstado> estadosParciales, List<IndicadorEstado> estadosAnuales,
			Boolean showEjecutado, Boolean showMeta, Boolean showEstadoParcial, Boolean showEstadoParcialSuavisado,
			Boolean showEstadoAnual, Boolean showEstadoAnualSuavisado, Boolean showAlerta, Font font,
			MessageResources mensajes, StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService, Document documento, HttpServletRequest request)
			throws Exception {
		boolean tablaIniciada = false;
		if (lineas >= tamanoPagina) {
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
		StrategosVistasDatosService strategosVistasDatosService = StrategosServiceFactory.getInstance()
				.openStrategosVistasDatosService();

		String[][] columnas = new String[mediciones.size() + 1][2];
		StringBuilder string;
		int contador = 0;
		columnas[contador][0] = "8";
		columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.periodo");
		for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();) {
			contador++;
			Medicion medicion = (Medicion) iter.next();
			columnas[contador][0] = "8";

			if (contador == (mediciones.size())) {
				string = new StringBuilder();
				string.append(PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(),
						indicador.getFrecuencia(), medicion.getMedicionId().getAno()));
				
				columnas[contador][1] = string.toString();
			} else
				columnas[contador][1] = PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(),
						indicador.getFrecuencia(), medicion.getMedicionId().getAno());
		}

		TablaBasicaPDF tabla = crearTabla(true, false, columnas, reporte, font, mensajes, documento, request);

		if (showEjecutado) {
			tablaIniciada = false;

			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.ejecutado"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();) {
				Medicion medicion = (Medicion) iter.next();
				tabla.agregarCelda(
						(medicion.getValor() != null ? VgcFormatter.formatearNumero(medicion.getValor()) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
		}

		if (showMeta) {
			tablaIniciada = false;

			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.meta"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();) {
				Medicion medicion = (Medicion) iter.next();
				Double valor = null;
				for (Iterator<Medicion> iterMeta = medicionesMeta.iterator(); iterMeta.hasNext();) {
					Medicion meta = (Medicion) iterMeta.next();
					if (medicion.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue()
							&& medicion.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo()
									.intValue()) {
						valor = meta.getValor();
						break;
					}
				}
				tabla.agregarCelda((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
		}

		if (showEstadoParcial) {
			tablaIniciada = false;

			string = new StringBuilder();
			if (!showEstadoParcialSuavisado)
				string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial"));
			else
				string.append(
						mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoparcial.suavisado"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();) {
				Medicion medicion = (Medicion) iter.next();
				Double valor = null;
				for (Iterator<IndicadorEstado> iterEstado = estadosParciales.iterator(); iterEstado.hasNext();) {
					IndicadorEstado estado = (IndicadorEstado) iterEstado.next();
					if (medicion.getMedicionId().getAno().intValue() == estado.getPk().getAno().intValue() && medicion
							.getMedicionId().getPeriodo().intValue() == estado.getPk().getPeriodo().intValue()) {
						valor = estado.getEstado();
						break;
					}
				}
				if (showEstadoParcialSuavisado && valor != null && valor > 100D)
					valor = 100D;

				tabla.agregarCelda((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
		}

		if (showEstadoAnual) {
			tablaIniciada = false;

			string = new StringBuilder();
			if (!showEstadoAnualSuavisado)
				string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual"));
			else
				string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.estadoanual.suavisado"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();) {
				Medicion medicion = (Medicion) iter.next();
				Double valor = null;
				for (Iterator<IndicadorEstado> iterEstado = estadosAnuales.iterator(); iterEstado.hasNext();) {
					IndicadorEstado estado = (IndicadorEstado) iterEstado.next();
					if (medicion.getMedicionId().getAno().intValue() == estado.getPk().getAno().intValue() && medicion
							.getMedicionId().getPeriodo().intValue() == estado.getPk().getPeriodo().intValue()) {
						valor = estado.getEstado();
						break;
					}
				}
				if (showEstadoAnualSuavisado && valor != null && valor > 100D)
					valor = 100D;
				tabla.agregarCelda((valor != null ? VgcFormatter.formatearNumero(valor) : ""));
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
		}

		if (showAlerta) {
			tablaIniciada = false;

			string = new StringBuilder();
			string.append(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.columna.alerta"));
			string.append("\n");
			string.append("\n");
			tabla.agregarCelda(string.toString());

			for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext();) {
				Medicion medicion = (Medicion) iter.next();
				Medicion valorMeta = null;
				for (Iterator<Medicion> iterMeta = medicionesMeta.iterator(); iterMeta.hasNext();) {
					Medicion meta = (Medicion) iterMeta.next();
					if (medicion.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue()
							&& medicion.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo()
									.intValue()) {
						valorMeta = meta;
						break;
					}
				}
				if (valorMeta != null && valorMeta.getValor() != null && medicion != null
						&& medicion.getValor() != null) {
					Double zonaVerde = null;
					Double zonaAmarilla = null;
					zonaVerde = strategosIndicadoresService.zonaVerde(indicador, medicion.getMedicionId().getAno(),
							medicion.getMedicionId().getPeriodo(), valorMeta.getValor(), strategosMedicionesService);
					zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador,
							medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(),
							valorMeta.getValor(), zonaVerde, strategosMedicionesService);

					Byte alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(),
							indicador.getCaracteristica(), zonaVerde, zonaAmarilla, medicion.getValor(),
							valorMeta.getValor(), null, null);

					String url = obtenerCadenaRecurso(request);

					if (alerta == null)
						tabla.agregarCelda("");
					else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
						tabla.agregarCelda(Image
								.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
					else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
						tabla.agregarCelda(Image
								.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
					else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
						tabla.agregarCelda(Image.getInstance(
								new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
				} else
					tabla.agregarCelda("");
			}

			lineas = getNumeroLinea((lineas + 3), inicioLineas);
			if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
				lineas = inicioLineas;
				documento.add(tabla.getTabla());
				tamanoPagina = inicioTamanoPagina;
				saltarPagina(documento, false, font, tabla.getTabla().columns(), null, request);
				tabla = crearTabla(false, false, columnas, reporte, font, mensajes, documento, request);
				tablaIniciada = true;
			}
		}

		strategosVistasDatosService.close();

		if (!tablaIniciada)
			documento.add(tabla.getTabla());
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
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
			alineacionVertical = TablaBasicaPDF.V_ALINEACION_TOP;
		} else {
			colorLetra = new Color(255, 255, 255);
			colorFondo = new Color(128, 128, 128);
		}

		if (tablaHeader != null && newTable)
			tablaHeader = null;

		TablaBasicaPDF tabla = inicializarTabla(font, columnas, anchoBorde, anchoBordeCelda, bold, colorLetra,
				colorFondo, alineacionHorizontal, alineacionVertical, request);

		lineas = getNumeroLinea((lineas + (isInformativo ? 0 : 3)), inicioLineas);
		if ((lineas + maxLineasAntesTabla) >= tamanoPagina) {
			lineas = inicioLineas;			
			tamanoPagina = inicioTamanoPagina;			
			tabla = crearTabla(false, isInformativo, columnas, reporte, font, mensajes, documento, request);
		}

		if (!isInformativo) {
			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
			tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_MIDDLE);
			tabla.setFont(Font.NORMAL);
			tabla.setFormatoFont(Font.NORMAL);
			tabla.setColorLetra(0, 0, 0);
			tabla.setColorFondo(255, 255, 255);
			tabla.setTamanoFont(8);
		}

		return tabla;
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

	public String getNombrePerspectiva(ReporteForm reporte, int nivel) {
		String nombre = "";

		Set<?> elementosPlantillaPlanes = reporte.getPlantillaPlanes().getElementos();
		if (elementosPlantillaPlanes != null) {
			for (Iterator<?> iterElemento = elementosPlantillaPlanes.iterator(); iterElemento.hasNext();) {
				ElementoPlantillaPlanes elemento = (ElementoPlantillaPlanes) iterElemento.next();
				if (elemento.getOrden().intValue() == nivel) {
					nombre = elemento.getNombre();
					break;
				}
			}
		}

		return nombre;
	}

	public Integer buscarNivelPerspectiva(int nivel, Long perspectivaPadreId, Long perspectivaId,
			StrategosPerspectivasService strategosPerspectivasService) {
		Integer nivelInterno = null;
		if (perspectivaPadreId.longValue() != perspectivaId.longValue()) {
			Map<String, Object> filtros = new HashMap<String, Object>();

			filtros.put("padreId", perspectivaPadreId);
			String[] orden = new String[1];
			String[] tipoOrden = new String[1];
			orden[0] = "nombre";
			tipoOrden[0] = "asc";
			List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext();) {
				Perspectiva perspectivaHija = (Perspectiva) iter.next();
				if (perspectivaHija.getPerspectivaId().longValue() == perspectivaId.longValue()) {
					nivelInterno = nivel;
					break;
				} else {
					nivelInterno = buscarNivelPerspectiva((nivel + 1), perspectivaHija.getPerspectivaId(),
							perspectivaId, strategosPerspectivasService);
					if (nivelInterno != null)
						break;
				}
			}
		} else
			nivelInterno = nivel;

		return nivelInterno;
	}

	public String obtenerEstatus(String estatus) {
		String nombre = "";
		Integer est = Integer.parseInt(estatus);

		switch (est) {
		case 0:
			nombre = "Sin Iniciar";
			break;
		case 1:
			nombre = "Sin Iniciar Desfasadas";
			break;
		case 2:
			nombre = "En Ejecucion sin Retrasos";
			break;
		case 3:
			nombre = "En Ejecucion con Retrasos Superables";
			break;
		case 4:
			nombre = "En Ejecucion con Retrasos Significativos";
			break;
		case 5:
			nombre = "Concluidas";
			break;
		case 6:
			nombre = "Cancelado";
			break;
		case 7:
			nombre = "Suspendido";
			break;
		case 8:
			nombre = "Todos";
			break;
		}
		return nombre;
	}

	public String obtenerTipo(Long tipo) {
		String nombre = "";

		if (tipo == 656054) {
			nombre = "Estrategicos Dependencias";
		}
		if (tipo == 656056) {
			nombre = "Bid";
		}
		if (tipo == 656058) {
			nombre = "Cooperación";
		}
		if (tipo == 656060) {
			nombre = "Bpin";
		}
		if (tipo == 1253394) {
			nombre = "Iniciativa Estratégica";
		}
		if (tipo == 1253396) {
			nombre = "Proyecto de Dependencia";
		}
		if (tipo == 1253398) {
			nombre = "Proyecto Preventivo";
		}
		if (tipo == 0) {
			nombre = "Todos";
		}
		return nombre;
	}
	
	private TablaPDF crearTablaFiltros(TablaPDF tabla, MessageResources mensajes, ReporteForm reporte,  Document documento, HttpServletRequest request,  String todos, Font fuente) throws Exception {
					
		String tipo = obtenerTipo(reporte.getTipo());
		String estatus = obtenerEstatus(reporte.getEstatus());
		String anio = "";
		if (todos.equals("false")) {
			anio = String.valueOf(reporte.getAno());
		} else {
			anio = "Todos";
		}

		Paragraph texto = new Paragraph( PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesInicial())) + "/"
				+ PeriodoUtil.getMesNombre(Byte.parseByte(reporte.getMesFinal())) + " -- "
				+ (reporte.getAnoInicial().equals(reporte.getAnoFinal()) ? reporte.getAnoInicial()
						: (reporte.getAnoInicial() + "/" + reporte.getAnoFinal())), fuente);
		
		int[] columnas = new int[4];
		columnas[0] = 20;
		columnas[1] = 15;
		columnas[2] = 10;
		columnas[3] = 15;
		
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setColorFondo(128, 128, 128);
		tabla.setColorLetra(255, 255, 255);
		tabla.setTamanoFont(12);
		tabla.setFormatoFont(Font.BOLD);
		tabla.setAlineacionHorizontal(1);
	
		tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.rango"));
		tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));
		tabla.agregarCelda(mensajes.getMessage("jsp.editariniciativa.ficha.estatus"));
		tabla.agregarCelda(mensajes.getMessage("jsp.gestionariniciativas.filtro.anio"));
		
		tabla.setColorFondo(255, 255, 255);
		tabla.setColorLetra(0, 0, 0);
		tabla.setTamanoFont(10);
		tabla.setFormatoFont(Font.NORMAL);
		
		tabla.agregarCelda(texto);
		tabla.agregarCelda(tipo);
		tabla.agregarCelda(estatus);
		tabla.agregarCelda(anio);
		
		return tabla;		
	}
}