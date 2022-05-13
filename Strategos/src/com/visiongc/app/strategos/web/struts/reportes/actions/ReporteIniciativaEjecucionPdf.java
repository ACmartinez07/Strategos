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

public class ReporteIniciativaEjecucionPdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String alcance = (request.getParameter("alcance"));
		String orgId = (request.getParameter("organizacionId"));
		String tipo = (request.getParameter("tipo"));
		String estatus = (request.getParameter("estatus"));
		String ano = (request.getParameter("ano"));
		int estatusId = Integer.parseInt(estatus);
		String todos = (request.getParameter("todos"));

		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

		Calendar fecha = Calendar.getInstance();
		int anoTemp = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH) + 1;

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();

		List<OrganizacionStrategos> organizaciones = organizacionservice
				.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

		// organizacion seleccionada
		if (request.getParameter("alcance").equals("1")) {

			OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (org != null) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

				// Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);

				Paragraph textoOrg = new Paragraph("Organizaci�n: " + org.getNombre(), font);
				textoOrg.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoOrg);

				documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			}

			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre")
					: "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
					&& request.getParameter("selectHitoricoType") != "")
							? Byte.parseByte(request.getParameter("selectHitoricoType"))
							: HistoricoType.getFiltroHistoricoNoMarcado();

			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			filtro.setAnio("" + ano);
			if (filtroNombre.equals(""))
				filtro.setNombre(null);
			else
				filtro.setNombre(filtroNombre);

			reporte.setFiltro(filtro);

			if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
				filtros.put("organizacionId",
						((OrganizacionStrategos) request.getSession().getAttribute("organizacion"))
								.getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (!tipo.equals("0")) {
				filtros.put("tipoId", tipo);
			}

			if (todos.equals("false")) {
				filtros.put("anio", ano);
			}

			Font fuente = getConfiguracionPagina(request).getFuente();
			MessageResources messageResources = getResources(request);

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();

			int index = iniciativas.size();

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();

					StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
							.openStrategosMedicionesService();
					StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
							.openStrategosIniciativasService();
					StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory
							.getInstance().openStrategosPryActividadesService();

					Indicador indicador = (Indicador) strategosIniciativasService.load(Indicador.class,
							iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000,
							mes);
					List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp,
							000, mes);

					List<PryActividad> actividades = new ArrayList<PryActividad>();

					if (iniciativa.getProyectoId() != null) {
						actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
					}

					if (estatusId == 8) {
						dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false, null, true);

						documento.add(lineaEnBlanco(fuente));

						dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
								medicionesEjecutadas, medicionesProgramadas);

						documento.add(lineaEnBlanco(fuente));
						documento.add(lineaEnBlanco(fuente));
					} else {

						Boolean est = tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);

						if (est) {
							dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false, null,
									true);

							documento.add(lineaEnBlanco(fuente));

							dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
									medicionesEjecutadas, medicionesProgramadas);

							documento.add(lineaEnBlanco(fuente));
							documento.add(lineaEnBlanco(fuente));
						}
					}

				}

			}

		}
		// suborganizaciones
		else if (request.getParameter("alcance").equals("4")) {

			Map<String, Object> filtro = new HashMap<String, Object>();

			filtro.put("padreId",
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId(),
					true);

			Font fuente = getConfiguracionPagina(request).getFuente();
			MessageResources messageResources = getResources(request);

			filtros.put("organizacionId",
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (!tipo.equals("0")) {
				filtros.put("tipoId", tipo);
			}
			if (todos.equals("false")) {
				filtros.put("anio", ano);
			}

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();

			int index = iniciativas.size();

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();

					StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
							.openStrategosMedicionesService();
					StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
							.openStrategosIniciativasService();
					StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory
							.getInstance().openStrategosPryActividadesService();

					Indicador indicador = (Indicador) strategosIniciativasService.load(Indicador.class,
							iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000,
							mes);
					List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp,
							000, mes);

					List<PryActividad> actividades = new ArrayList<PryActividad>();

					if (iniciativa.getProyectoId() != null) {
						actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
					}

					if (estatusId == 8) {
						dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false, null,
								false);

						documento.add(lineaEnBlanco(fuente));

						dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
								medicionesEjecutadas, medicionesProgramadas);

						documento.add(lineaEnBlanco(fuente));
						documento.add(lineaEnBlanco(fuente));
					} else {

						Boolean est = tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);

						if (est) {
							dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false, null,
									false);

							documento.add(lineaEnBlanco(fuente));

							dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
									medicionesEjecutadas, medicionesProgramadas);

							documento.add(lineaEnBlanco(fuente));
							documento.add(lineaEnBlanco(fuente));
						}
					}

				}

			}

			// suborganizaciones

			if (organizacionesSub.size() > 0 || organizacionesSub != null) {

				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();) {

					OrganizacionStrategos organizacion = (OrganizacionStrategos) iter.next();

					filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
					if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
						filtros.put("historicoDate", "IS NULL");
					else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoMarcado())
						filtros.put("historicoDate", "IS NOT NULL");
					if (reporte.getFiltro().getNombre() != null)
						filtros.put("nombre", reporte.getFiltro().getNombre());
					if (!tipo.equals("0")) {
						filtros.put("tipoId", tipo);
					}
					if (todos.equals("false")) {
						filtros.put("anio", ano);
					}

					List<Iniciativa> iniciativasSub = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativasSub.size() > 0) {
						for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter1.next();

							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory
									.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory
									.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory
									.getInstance().openStrategosPryActividadesService();

							Indicador indicador = (Indicador) strategosIniciativasService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp,
									000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000,
									anoTemp, 000, mes);

							List<PryActividad> actividades = new ArrayList<PryActividad>();

							if (iniciativa.getProyectoId() != null) {
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}

							if (estatusId == 8) {
								dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false,
										null, false);

								documento.add(lineaEnBlanco(fuente));

								dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
										medicionesEjecutadas, medicionesProgramadas);

								documento.add(lineaEnBlanco(fuente));
								documento.add(lineaEnBlanco(fuente));
							} else {

								Boolean est = tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas,
										estatusId);

								if (est) {
									dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false,
											null, false);

									documento.add(lineaEnBlanco(fuente));

									dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
											medicionesEjecutadas, medicionesProgramadas);

									documento.add(lineaEnBlanco(fuente));
									documento.add(lineaEnBlanco(fuente));
								}
							}
						}
					}
				}
			}
		}
		// todas las organizaciones
		else {
			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre")
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

			Font fuente = getConfiguracionPagina(request).getFuente();
			MessageResources messageResources = getResources(request);

			if (organizaciones.size() > 0) {

				for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();) {

					OrganizacionStrategos organizacion = (OrganizacionStrategos) iter.next();

					filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
					if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
						filtros.put("historicoDate", "IS NULL");
					else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico()
							.byteValue() == HistoricoType.getFiltroHistoricoMarcado())
						filtros.put("historicoDate", "IS NOT NULL");
					if (reporte.getFiltro().getNombre() != null)
						filtros.put("nombre", reporte.getFiltro().getNombre());
					if (!tipo.equals("0")) {
						filtros.put("tipoId", tipo);
					}
					if (todos.equals("false")) {
						filtros.put("anio", ano);
					}

					List<Iniciativa> iniciativas = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativas.size() > 0) {
						for (Iterator<Iniciativa> iter1 = iniciativas.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter1.next();

							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory
									.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory
									.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory
									.getInstance().openStrategosPryActividadesService();

							Indicador indicador = (Indicador) strategosIniciativasService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp,
									000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000,
									anoTemp, 000, mes);

							List<PryActividad> actividades = new ArrayList<PryActividad>();

							if (iniciativa.getProyectoId() != null) {
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}

							if (estatusId == 8) {
								dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false,
										null, false);

								documento.add(lineaEnBlanco(fuente));

								dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
										medicionesEjecutadas, medicionesProgramadas);

								documento.add(lineaEnBlanco(fuente));
								documento.add(lineaEnBlanco(fuente));
							} else {

								Boolean est = tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas,
										estatusId);

								if (est) {
									dibujarTabla1(iniciativa, actividades, messageResources, request, documento, false,
											null, false);

									documento.add(lineaEnBlanco(fuente));

									dibujarTabla2(iniciativa, actividades, messageResources, request, documento,
											medicionesEjecutadas, medicionesProgramadas);

									documento.add(lineaEnBlanco(fuente));
									documento.add(lineaEnBlanco(fuente));
								}
							}
						}

					}

				}

			}

		}

		documento.newPage();
		organizacionservice.close();
		iniciativaservice.close();

	}

	public String obtenerObjetivo(Iniciativa iniciativa) throws SQLException {
		String objetivo = null;
		Long id = iniciativa.getIniciativaId();

		Map<String, Object> filtros = new HashMap<String, Object>();

		if ((iniciativa.getIniciativaPerspectivas() != null) && (iniciativa.getIniciativaPerspectivas().size() > 0)) {

			IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva) iniciativa.getIniciativaPerspectivas()
					.toArray()[0];
			StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance()
					.openStrategosPerspectivasService();
			Perspectiva perspectiva = (Perspectiva) strategosPerspectivasService.load(Perspectiva.class,
					iniciativaPerspectiva.getPk().getPerspectivaId());

			objetivo = perspectiva.getNombre();

		}
		return objetivo;
	}

	public Date obtenerFechaFinal(List<PryActividad> actividades) {

		Date fecha = null;

		for (PryActividad act : actividades) {

			fecha = act.getFinPlan();

		}

		return fecha;
	}

	public void dibujarTabla1(Iniciativa iniciativa, List<PryActividad> actividades, MessageResources messageResources,
			HttpServletRequest request, Document documento, Boolean todas, OrganizacionStrategos organizacion,
			Boolean solaOrg) throws Exception {

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[7];

		if (solaOrg == true) {

			columnas = new int[6];

			columnas[0] = 30;
			columnas[1] = 10;
			columnas[2] = 10;
			columnas[3] = 10;
			columnas[4] = 10;
			columnas[5] = 10;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);

			tabla.setAlineacionHorizontal(1);

			tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre"));
			tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia"));
			tabla.agregarCelda(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado"));
			tabla.agregarCelda(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado"));
			tabla.agregarCelda(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion"));
			tabla.agregarCelda(messageResources
					.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada"));

			tabla.setDefaultAlineacionHorizontal();

		} else {
			columnas[0] = 21;
			columnas[1] = 30;
			columnas[2] = 10;
			columnas[3] = 10;
			columnas[4] = 10;
			columnas[5] = 10;
			columnas[6] = 10;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);

			tabla.setAlineacionHorizontal(1);

			tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
			tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre"));
			tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia"));
			tabla.agregarCelda(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado"));
			tabla.agregarCelda(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado"));
			tabla.agregarCelda(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion"));
			tabla.agregarCelda(messageResources
					.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada"));

			tabla.setDefaultAlineacionHorizontal();
		}

		if (todas) {

			String ruta = null;

			OrganizacionStrategos org = new OrganizacionStrategos();
			ruta = organizacion.getNombre() + "/";
			org = organizacion.getPadre();
			while (org != null) {
				ruta = org.getNombre() + "/" + ruta;
				if (org.getPadre() == null) {
					org = null;
				} else {
					org = org.getPadre();
				}
			}

			tabla.agregarCelda(ruta);
		} else {
			if (solaOrg == false) {
				tabla.agregarCelda(iniciativa.getOrganizacion().getNombre());
			}

		}

		tabla.agregarCelda(iniciativa.getNombre());

		if (iniciativa.getFrecuenciaNombre() != null) {
			tabla.agregarCelda(iniciativa.getFrecuenciaNombre());
		} else {
			tabla.agregarCelda("");
		}

		if (iniciativa.getPorcentajeCompletadoFormateado() != null) {
			tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
			tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
		} else {
			tabla.agregarCelda("");
			tabla.agregarCelda("");
		}

		// porcentaje programado

		// fecha actualizacion

		if (iniciativa.getFechaUltimaMedicion() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getFechaUltimaMedicion());
		}

		Date fechaAh = new Date();
		Date fechaAc = new Date();

		// fecha esperada

		fechaAh = obtenerFechaFinal(actividades);

		if (fechaAh != null) {
			SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

			tabla.agregarCelda(objSDF.format(fechaAh).toString());
		} else {
			tabla.agregarCelda("");
		}

		tabla.agregarCelda("" + fechaAh);

		documento.add(tabla.getTabla());

	}

	public void dibujarTabla2(Iniciativa iniciativa, List<PryActividad> actividades, MessageResources messageResources,
			HttpServletRequest request, Document documento, List<Medicion> medicionesEjecutadas,
			List<Medicion> medicionesProgramadas) throws Exception {

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[6];

		columnas[0] = 5;
		columnas[1] = 10;
		columnas[2] = 10;
		columnas[3] = 5;
		columnas[4] = 10;
		columnas[5] = 10;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);

		tabla.setAlineacionHorizontal(1);

		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.dias"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.estatus"));
		tabla.agregarCelda(messageResources.getMessage("jsp.editariniciativa.ficha.tipoproyecto"));
		tabla.agregarCelda(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.lograr"));

		tabla.setDefaultAlineacionHorizontal();

		Date fechaAh = new Date();
		Date fechaAc = new Date();

		// fecha esperada

		fechaAh = obtenerFechaFinal(actividades);

		// dias de atraso

		if (fechaAh != null) {

			int milisecondsByDay = 86400000;

			int dias = (int) ((fechaAh.getTime() - fechaAc.getTime()) / milisecondsByDay);

			int diasposi = dias * -1;

			tabla.agregarCelda("" + diasposi);

		} else {
			tabla.agregarCelda("");
		}

		// estatus
		if (medicionesProgramadas.size() == 0) {
			// EstatusIniciar
			tabla.agregarCelda(messageResources.getMessage("estado.sin.iniciar"));
		} else if (medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
			// EstatusIniciardesfasado
			tabla.agregarCelda(messageResources.getMessage("estado.sin.iniciar.desfasada"));
		} else if (iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null
				&& iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()
				&& iniciativa.getPorcentajeCompletado() != null
				&& iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
			// EnEjecucionSinRetrasos
			tabla.agregarCelda(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
		} else if (iniciativa.getEstatusId() == 2
				&& iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
			// EnEjecucionConRetrasosSuperables
			tabla.agregarCelda(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
		} else if (iniciativa.getEstatusId() == 2
				&& iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
			// EnEjecucionConRetrasosSignificativos
			tabla.agregarCelda(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
		} else if (iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null
				&& iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
			// EstatusConcluidas
			tabla.agregarCelda(messageResources.getMessage("estado.concluidas"));
		} else if (iniciativa.getEstatusId() == 3) {
			// EstatusCancelado
			tabla.agregarCelda("Cancelado");
		} else if (iniciativa.getEstatusId() == 4) {
			// EstatusSuspendido
			tabla.agregarCelda("Suspendido");
		} else {
			// EstatusIniciar
			tabla.agregarCelda(messageResources.getMessage("estado.sin.iniciar"));
		}

		if (iniciativa.getTipoProyecto() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getTipoProyecto().getNombre());
		}

		if (iniciativa.getAnioFormulacion() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getAnioFormulacion());
		}

		// responsable ejecutar

		if (iniciativa.getResponsableCargarEjecutado() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getResponsableCargarEjecutado().getNombre());
		}

		// responsable lograr meta

		if (iniciativa.getResponsableLograrMeta() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getResponsableLograrMeta().getNombre());
		}

		documento.add(tabla.getTabla());
	}

	public Boolean tieneEstatus(Iniciativa iniciativa, List<Medicion> medicionesEjecutadas,
			List<Medicion> medicionesProgramadas, int estatus) {

		Boolean tiene = false;
		// estatus
		if (medicionesProgramadas.size() == 0 && estatus == 0) {
			// EstatusIniciar
			tiene = true;
		} else if (medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0 && estatus == 1) {
			// EstatusIniciardesfasado
			tiene = true;
		} else if (iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null
				&& iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()
				&& iniciativa.getPorcentajeCompletado() != null
				&& iniciativa.getPorcentajeCompletado().doubleValue() < 100D && estatus == 2) {
			// EnEjecucionSinRetrasos
			tiene = true;
		} else if (iniciativa.getEstatusId() == 2
				&& iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()
				&& estatus == 3) {
			// EnEjecucionConRetrasosSuperables
			tiene = true;
		} else if (iniciativa.getEstatusId() == 2
				&& iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue() && estatus == 4) {
			// EnEjecucionConRetrasosSignificativos
			tiene = true;
		} else if (iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null
				&& iniciativa.getPorcentajeCompletado().doubleValue() >= 100D && estatus == 5) {
			// EstatusConcluidas
			tiene = true;
		} else if (iniciativa.getEstatusId() == 3 && estatus == 6) {
			// EstatusCancelado
			tiene = true;
		} else if (iniciativa.getEstatusId() == 4 && estatus == 7) {
			// EstatusSuspendido
			tiene = true;
		} else if (iniciativa.getEstatusId() == 1 && estatus == 0) {
			// EstatusSuspendido
			tiene = true;
		}

		return tiene;
	}

}
