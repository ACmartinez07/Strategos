package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
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
import com.lowagie.text.DocumentException;
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
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
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
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;

public class ReporteIniciativaPdf extends VgcReporteBasicoAction {

	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.titulo");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		Font fuente = getConfiguracionPagina(request).getFuente();
		Font fontTitulos = new Font(getConfiguracionPagina(request).getCodigoFuente());
		fontTitulos.setSize(12);
		fontTitulos.setStyle(Font.BOLD);

		String alcance = (request.getParameter("alcance"));
		String orgId = (request.getParameter("organizacionId"));
		String tipo = (request.getParameter("tipo"));
		String ano = (request.getParameter("ano"));
		String todos = (request.getParameter("todos"));
		String estatus = (request.getParameter("estatus"));
		
		TablaPDF tablaFiltros = null;
		tablaFiltros = new TablaPDF(getConfiguracionPagina(request), request);
		
		Paragraph textoFiltros = new Paragraph("Filtros del reporte: ", fontTitulos);
		documento.add(textoFiltros);
		documento.add(lineaEnBlanco(fuente));
		crearTablaFiltros(tablaFiltros, mensajes, documento, request, todos, tipo, estatus, ano, fuente);
		documento.add(tablaFiltros.getTabla());
		
		documento.add(lineaEnBlanco(fuente));
		documento.add(lineaEnBlanco(fuente));

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

				Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), fontTitulos);
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
			if (reporte.getEstatus() != null)
				filtros.put("estatus", reporte.getEstatus());
			
			MessageResources messageResources = getResources(request);

			TablaPDF tabla = null;
			tabla = new TablaPDF(getConfiguracionPagina(request), request);
			
			crearTablaTitulo(tabla, messageResources);

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();


			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();
					dibujarTabla(iniciativa, null, tabla, request, documento);
				}
				documento.add(tabla.getTabla());
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
			
			MessageResources messageResources = getResources(request);

			OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (org != null) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

				// Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);

				Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), fontTitulos);
				textoOrg.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoOrg);

				documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			}

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
			if (reporte.getEstatus() != null)
				filtros.put("estatus", reporte.getEstatus());

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();

			TablaPDF tabla = null;
			tabla = new TablaPDF(getConfiguracionPagina(request), request);
			

			if (iniciativas.size() > 0) {
				
				crearTablaTitulo(tabla, messageResources);

				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();
					dibujarTabla(iniciativa, null, tabla, request, documento);
				}
				documento.add(tabla.getTabla());
			} else {
				documento.add(lineaEnBlanco(fuente));

				fuente.setColor(0, 0, 255);
				texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
						"INICIATIVAS", "ORGANIZACIÓN"), fuente);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fuente.setColor(0, 0, 0);
				documento.add(lineaEnBlanco(fuente));
			}
			

			// suborganizaciones
			if (organizacionesSub.size() > 0 || organizacionesSub != null) {
				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();) {
					OrganizacionStrategos organizacion = (OrganizacionStrategos) iter.next();

					if (organizacion != null) {

						Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

						// Nombre de la Organizacion, plan y periodo del reporte
						font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
						font.setStyle(Font.NORMAL);

						Paragraph textoOrg = new Paragraph("Organización: " + organizacion.getNombre(), fontTitulos);
						textoOrg.setAlignment(Element.ALIGN_LEFT);
						documento.add(textoOrg);

						documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
					}

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
					if (reporte.getEstatus() != null)
						filtros.put("estatus", reporte.getEstatus());

					List<Iniciativa> iniciativasSub = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativasSub.size() > 0) {

						TablaPDF tabla1 = null;
						tabla1 = new TablaPDF(getConfiguracionPagina(request), request);
						
						crearTablaTitulo(tabla1, messageResources);
						
						for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();) {

							Iniciativa iniciativa = (Iniciativa) iter1.next();							
							dibujarTabla(iniciativa, null, tabla1, request, documento);
						}
						documento.add(tabla1.getTabla());
					} else {
						documento.add(lineaEnBlanco(fuente));

						fuente.setColor(0, 0, 255);
						texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
								"INICIATIVAS", "ORGANIZACIÓN"), fuente);
						texto.setIndentationLeft(50);
						documento.add(texto);
						fuente.setColor(0, 0, 0);

						documento.add(lineaEnBlanco(fuente));
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

			if (organizaciones.size() > 0) {
				
				MessageResources messageResources = getResources(request);

				for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();) {

					OrganizacionStrategos organizacion = (OrganizacionStrategos) iter.next();

					if (organizacion != null) {
						Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

						// Nombre de la Organizacion, plan y periodo del reporte
						font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
						font.setStyle(Font.NORMAL);

						Paragraph textoOrg = new Paragraph("Organización: " + organizacion.getNombre(), fontTitulos);
						textoOrg.setAlignment(Element.ALIGN_LEFT);
						documento.add(textoOrg);
						documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
					}

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
					if (reporte.getEstatus() != null)
						filtros.put("estatus", reporte.getEstatus());

					List<Iniciativa> iniciativas = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativas.size() > 0) {

						TablaPDF tabla = null;
						tabla = new TablaPDF(getConfiguracionPagina(request), request);
						
						crearTablaTitulo(tabla, messageResources);

						for (Iterator<Iniciativa> iter1 = iniciativas.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter1.next();							
							dibujarTabla(iniciativa, organizacion, tabla, request, documento);
						}
						documento.add(tabla.getTabla());
					} else {
						documento.add(lineaEnBlanco(fuente));

						fuente.setColor(0, 0, 255);
						texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
								"INICIATIVAS", "ORGANIZACIÓN"), fuente);
						texto.setIndentationLeft(50);
						documento.add(texto);
						fuente.setColor(0, 0, 0);
						documento.add(lineaEnBlanco(fuente));
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
	
	private TablaPDF crearTablaTitulo(TablaPDF tabla, MessageResources messageResources) throws Exception {
		int[] columnas = new int[7];		
		columnas[0] = 27;
		columnas[1] = 10;
		columnas[2] = 10;
		columnas[3] = 20;
		columnas[4] = 10;
		columnas[5] = 20;
		columnas[6] = 10;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);

		tabla.setColorFondo(128, 128, 128);
		tabla.setColorLetra(255, 255, 255);
		tabla.setTamanoFont(10);
		tabla.setFormatoFont(Font.BOLD);
		tabla.setAlineacionHorizontal(1);

		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.responsable"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.anio"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.objetivo"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));
		
		tabla.setColorFondo(255, 255, 255);
		tabla.setColorLetra(0, 0, 0);
		tabla.setTamanoFont(8);
		tabla.setFormatoFont(Font.NORMAL);
		
		return tabla;		
	}

	public void dibujarTabla(Iniciativa iniciativa, OrganizacionStrategos organizacion, TablaPDF tabla,
			HttpServletRequest request, Document documento)
			throws Exception {

		tabla.agregarCelda(iniciativa.getNombre());
		tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
		if (iniciativa.getFechaUltimaMedicion() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getFechaUltimaMedicion());
		}

		if (iniciativa.getResponsableLograrMeta() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getResponsableLograrMeta().getNombre());
		}
		if (iniciativa.getAnioFormulacion() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getAnioFormulacion());
		}

		tabla.agregarCelda(obtenerObjetivo(iniciativa));

		if (iniciativa.getTipoProyecto() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda(iniciativa.getTipoProyecto().getNombre());
		}

	}
			
	private TablaPDF crearTablaFiltros(TablaPDF tabla, MessageResources mensajes, Document documento, HttpServletRequest request,  String todos, String tipo, String estatus, String ano, Font fuente) throws Exception {
		
		String tipoT = obtenerTipo(Long.parseLong(tipo));
		String estatusT = obtenerEstatus(estatus);
		String anio = "";
		if (todos.equals("false")) {
			anio = String.valueOf(Integer.parseInt(ano));
		} else {
			anio = "Todos";
		}
		String alcanceT = "";
		if(request.getParameter("alcance").equals("1"))
			alcanceT = "Organización seleccionada";
		else if(request.getParameter("alcance").equals("4"))
			alcanceT = "Organización seleccionada y Sub-Organizaciones";
		else 
			alcanceT = "Todas las Organizaciones";
				
		int[] columnas = new int[4];
		columnas[0] = 20;
		columnas[1] = 15;
		columnas[2] = 10;
		columnas[3] = 15;
		
		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setColorFondo(128, 128, 128);
		tabla.setColorLetra(255, 255, 255);
		tabla.setTamanoFont(10);
		tabla.setFormatoFont(Font.BOLD);
		tabla.setAlineacionHorizontal(1);
	
		tabla.agregarCelda(mensajes.getMessage("jsp.editariniciativa.ficha.alcance") + " del Reporte");
		tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));
		tabla.agregarCelda(mensajes.getMessage("jsp.editariniciativa.ficha.estatus"));
		tabla.agregarCelda(mensajes.getMessage("jsp.gestionariniciativas.filtro.anio"));
		
		tabla.setColorFondo(255, 255, 255);
		tabla.setColorLetra(0, 0, 0);
		tabla.setTamanoFont(8);
		tabla.setFormatoFont(Font.NORMAL);
		
		tabla.agregarCelda(alcanceT);
		tabla.agregarCelda(tipoT);
		tabla.agregarCelda(estatusT);
		tabla.agregarCelda(anio);
		
		return tabla;		
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
}
