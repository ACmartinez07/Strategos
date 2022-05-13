package com.visiongc.app.strategos.web.struts.reportes.actions;

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
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaDatosBasicosPdf extends VgcReporteBasicoAction {

	@Override
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.datos.basicos");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		String tipo = (request.getParameter("tipo"));
		String ano = (request.getParameter("ano"));
		String todos = (request.getParameter("todos"));

		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();

		// organizacion seleccionada
		if (request.getParameter("alcance").equals("1")) {

			OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (org != null) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

				// Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);

				Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), font);
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

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();

					dibujarTabla0(messageResources, request, documento);

					dibujarTabla1(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla2(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla3(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla4(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla5(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla6(iniciativa, messageResources, request, documento, null, true);

					documento.add(lineaEnBlanco(fuente));

					dibujarTabla7(messageResources, request, documento);

					dibujarTabla8(iniciativa, messageResources, request, documento, null, true);

					documento.add(lineaEnBlanco(fuente));
					documento.add(lineaEnBlanco(fuente));

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

			OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (org != null) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

				// Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);

				Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), font);
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

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();

					dibujarTabla0(messageResources, request, documento);

					dibujarTabla1(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla2(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla3(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla4(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla5(iniciativa, messageResources, request, documento, null, true);

					dibujarTabla6(iniciativa, messageResources, request, documento, null, true);

					documento.add(lineaEnBlanco(fuente));

					dibujarTabla7(messageResources, request, documento);

					dibujarTabla8(iniciativa, messageResources, request, documento, null, true);

					documento.add(lineaEnBlanco(fuente));
					documento.add(lineaEnBlanco(fuente));
				}
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

						Paragraph textoOrg = new Paragraph("Organización: " + organizacion.getNombre(), font);
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

					List<Iniciativa> iniciativasSub = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativasSub.size() > 0) {
						for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter1.next();

							dibujarTabla0(messageResources, request, documento);

							dibujarTabla1(iniciativa, messageResources, request, documento, null, true);

							dibujarTabla2(iniciativa, messageResources, request, documento, null, true);

							dibujarTabla3(iniciativa, messageResources, request, documento, null, true);

							dibujarTabla4(iniciativa, messageResources, request, documento, null, true);

							dibujarTabla5(iniciativa, messageResources, request, documento, null, true);

							dibujarTabla6(iniciativa, messageResources, request, documento, null, true);

							documento.add(lineaEnBlanco(fuente));

							dibujarTabla7(messageResources, request, documento);

							dibujarTabla8(iniciativa, messageResources, request, documento, null, true);

							documento.add(lineaEnBlanco(fuente));
							documento.add(lineaEnBlanco(fuente));
						}
					} else {
						documento.add(lineaEnBlanco(fuente));

						fuente.setColor(0, 0, 255);
						texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
								"INICIATIVAS", "PERSPECTIVA"), fuente);
						texto.setIndentationLeft(50);
						documento.add(texto);
						fuente.setColor(0, 0, 0);

						documento.add(lineaEnBlanco(fuente));
					}
				}
			}
		}
	}

	public void dibujarTabla0(MessageResources messageResources, HttpServletRequest request, Document documento)
			throws Exception {
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

		tabla.setAmplitudTabla(100);
		tabla.setColorFondo(180, 198, 231);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.datos.basicos"));

		documento.add(tabla.getTabla());

	}

	public void dibujarTabla1(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {

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
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencia"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getOrganizacion().getNombre());

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.proyecto"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getResponsableProyecto());

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.cargo"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getCargoResponsable());

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla2(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
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

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.proyecto"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getNombre());

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.vigencia"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getAnioFormulacion());

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla3(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
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

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.estrategico"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getObjetivoEstrategico());

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.iniciativa.estrategica"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getIniciativaEstrategica());

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla4(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
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

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.lider.iniciativa"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getLiderIniciativa());

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.tipo.iniciativa"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getTipoIniciativa());

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla5(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[3];

		columnas = new int[2];

		columnas[0] = 50;
		columnas[1] = 50;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);

		tabla.setColorFondo(180, 198, 231);
		tabla.setAlineacionHorizontal(1);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencias"));
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.poblacion.beneficiada"));

		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.setDefaultAlineacionHorizontal();

		if (iniciativa.getOrganizacionesInvolucradas() != null)
			tabla.agregarCelda(iniciativa.getOrganizacionesInvolucradas());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);

		if (iniciativa.getPoblacionBeneficiada() != null)
			tabla.agregarCelda(iniciativa.getPoblacionBeneficiada());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla6(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[2];

		columnas = new int[1];

		columnas[0] = 100;

		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);

		tabla.setAlineacionHorizontal(1);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.contexto"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		if (iniciativa.getContexto() != null)
			tabla.agregarCelda(iniciativa.getContexto());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.definicion.problema"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		if (iniciativa.getDefinicionProblema() != null)
			tabla.agregarCelda(iniciativa.getDefinicionProblema());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.alcance"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		if (iniciativa.getAlcance() != null)
			tabla.agregarCelda(iniciativa.getAlcance());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.general"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		if (iniciativa.getObjetivoGeneral() != null)
			tabla.agregarCelda(iniciativa.getObjetivoGeneral());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivos.especificos"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		if (iniciativa.getObjetivoEspecificos() != null)
			tabla.agregarCelda(iniciativa.getObjetivoEspecificos());
		else
			tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda("--");
		tabla.setColorLetra(0, 0, 0);
		tabla.setColorLetra(0, 0, 0);

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla7(MessageResources messageResources, HttpServletRequest request, Document documento)
			throws Exception {
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[2];

		columnas = new int[1];

		columnas[0] = 100;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);

		tabla.setAlineacionHorizontal(1);
		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);

		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.financiacion"));

		documento.add(tabla.getTabla());
	}

	public void dibujarTabla8(Iniciativa iniciativa, MessageResources messageResources, HttpServletRequest request,
			Document documento, OrganizacionStrategos organizacion, Boolean solaOrg) throws Exception {
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[6];

		columnas = new int[5];

		columnas[0] = 10;
		columnas[1] = 20;
		columnas[2] = 10;
		columnas[3] = 15;
		columnas[4] = 10;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.fuente"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getFuenteFinanciacion());

		tabla.setColorFondo(200, 200, 200);
		tabla.agregarCelda("");

		tabla.setColorFondo(180, 198, 231);
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.monto"));
		tabla.setColorFondo(255, 255, 255);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getMontoFinanciamiento());

		documento.add(tabla.getTabla());
	}
}
