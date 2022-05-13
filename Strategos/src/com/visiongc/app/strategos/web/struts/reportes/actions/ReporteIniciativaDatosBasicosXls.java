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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

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
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaDatosBasicosXls extends VgcAction {

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

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

			int x = 1;

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
			filtro.setAnio("" + ano);
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
			if (!tipo.equals("0")) {
				filtros.put("tipoId", tipo);
			}
			if (todos.equals("false")) {
				filtros.put("anio", ano);
			}

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();

			MessageResources messageResources = getResources(request);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Hoja excel");

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			HSSFRow headerRow = sheet.createRow(0);

			String header = "Reporte Datos Basicos Iniciativa";
			HSSFCell cell = headerRow.createCell(1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);

			HSSFRow dataRow = sheet.createRow(1);
			dataRow.createCell(0)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencia"));
			dataRow.createCell(1).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.proyecto"));
			dataRow.createCell(2).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.cargo"));
			dataRow.createCell(3)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.proyecto"));
			dataRow.createCell(4)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.vigencia"));
			dataRow.createCell(5).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.estrategico"));
			dataRow.createCell(6).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.iniciativa.estrategica"));
			dataRow.createCell(7)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.lider.iniciativa"));
			dataRow.createCell(8)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.tipo.iniciativa"));
			dataRow.createCell(9)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencias"));
			dataRow.createCell(10).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.poblacion.beneficiada"));
			dataRow.createCell(11)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.contexto"));
			dataRow.createCell(12)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.definicion.problema"));
			dataRow.createCell(13)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.alcance"));
			dataRow.createCell(14)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.general"));
			dataRow.createCell(15).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.objetivos.especificos"));
			dataRow.createCell(16)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.fuente"));
			dataRow.createCell(17).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.monto"));

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();

					HSSFRow dataRow1 = sheet.createRow(x + 1);
					dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
					dataRow1.createCell(1).setCellValue(iniciativa.getResponsableProyecto());
					dataRow1.createCell(2).setCellValue(iniciativa.getCargoResponsable());
					dataRow1.createCell(3).setCellValue(iniciativa.getNombre());
					dataRow1.createCell(4).setCellValue(iniciativa.getAnioFormulacion());
					dataRow1.createCell(5).setCellValue(iniciativa.getObjetivoEstrategico());
					dataRow1.createCell(6).setCellValue(iniciativa.getIniciativaEstrategica());
					dataRow1.createCell(7).setCellValue(iniciativa.getLiderIniciativa());
					dataRow1.createCell(8).setCellValue(iniciativa.getTipoIniciativa());
					dataRow1.createCell(9).setCellValue(iniciativa.getOrganizacionesInvolucradas());
					dataRow1.createCell(10).setCellValue(iniciativa.getPoblacionBeneficiada());
					dataRow1.createCell(11).setCellValue(iniciativa.getContexto());
					dataRow1.createCell(12).setCellValue(iniciativa.getDefinicionProblema());
					dataRow1.createCell(13).setCellValue(iniciativa.getAlcance());
					dataRow1.createCell(14).setCellValue(iniciativa.getObjetivoGeneral());
					dataRow1.createCell(15).setCellValue(iniciativa.getObjetivoEspecificos());
					dataRow1.createCell(16).setCellValue(iniciativa.getFuenteFinanciacion());
					dataRow1.createCell(17).setCellValue(iniciativa.getMontoFinanciamiento());

					x = x + 1;
				}
			}

			Date date = new Date();

			SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
			String archivo = "IniciativasDatosBasicos_" + hourdateFormat.format(date) + ".xls";
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

			ServletOutputStream file = response.getOutputStream();

			workbook.write(file);
			file.close();
		}

		// SUBORGANIZACIONES
		else if (request.getParameter("alcance").equals("4")) {

			Map<String, Object> filtro = new HashMap<String, Object>();

			filtro.put("padreId",
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId(),
					true);

			int x = 1;

			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre")
					: "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
					&& request.getParameter("selectHitoricoType") != "")
							? Byte.parseByte(request.getParameter("selectHitoricoType"))
							: HistoricoType.getFiltroHistoricoNoMarcado();

			FiltroForm filtrou = new FiltroForm();
			filtrou.setHistorico(selectHitoricoType);
			if (filtroNombre.equals(""))
				filtrou.setNombre(null);
			else
				filtrou.setNombre(filtroNombre);
			reporte.setFiltro(filtrou);

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

			MessageResources messageResources = getResources(request);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Hoja excel");

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			HSSFRow headerRow = sheet.createRow(0);

			String header = "Reporte Datos Basicos Iniciativa";
			HSSFCell cell = headerRow.createCell(1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);

			HSSFRow dataRow = sheet.createRow(1);
			dataRow.createCell(0)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencia"));
			dataRow.createCell(1).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.proyecto"));
			dataRow.createCell(2)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.iniciativa.cargo"));
			dataRow.createCell(3).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.nombre.proyecto"));
			dataRow.createCell(4)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.iniciativa.vigencia"));
			dataRow.createCell(5).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.estrategico"));
			dataRow.createCell(6).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.iniciativa.estrategica"));
			dataRow.createCell(7)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.lider.iniciativa"));
			dataRow.createCell(8)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.tipo.iniciativa"));
			dataRow.createCell(9)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.dependencias"));
			dataRow.createCell(10).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.poblacion.beneficiada"));
			dataRow.createCell(11)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.contexto"));
			dataRow.createCell(12)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.definicion.problema"));
			dataRow.createCell(13)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.alcance"));
			dataRow.createCell(14)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.objetivo.general"));
			dataRow.createCell(15).setCellValue(
					messageResources.getMessage("action.reporte.estatus.iniciativa.objetivos.especificos"));
			dataRow.createCell(16)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.fuente"));
			dataRow.createCell(17).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.monto"));

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();

					HSSFRow dataRow1 = sheet.createRow(x + 1);

					dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
					dataRow1.createCell(1).setCellValue(iniciativa.getResponsableProyecto());
					dataRow1.createCell(2).setCellValue(iniciativa.getCargoResponsable());
					dataRow1.createCell(3).setCellValue(iniciativa.getNombre());
					dataRow1.createCell(4).setCellValue(iniciativa.getAnioFormulacion());
					dataRow1.createCell(5).setCellValue(iniciativa.getObjetivoEstrategico());
					dataRow1.createCell(6).setCellValue(iniciativa.getIniciativaEstrategica());
					dataRow1.createCell(7).setCellValue(iniciativa.getLiderIniciativa());
					dataRow1.createCell(8).setCellValue(iniciativa.getTipoIniciativa());
					dataRow1.createCell(9).setCellValue(iniciativa.getOrganizacionesInvolucradas());
					dataRow1.createCell(10).setCellValue(iniciativa.getPoblacionBeneficiada());
					dataRow1.createCell(11).setCellValue(iniciativa.getContexto());
					dataRow1.createCell(12).setCellValue(iniciativa.getDefinicionProblema());
					dataRow1.createCell(13).setCellValue(iniciativa.getAlcance());
					dataRow1.createCell(14).setCellValue(iniciativa.getObjetivoGeneral());
					dataRow1.createCell(15).setCellValue(iniciativa.getObjetivoEspecificos());
					dataRow1.createCell(16).setCellValue(iniciativa.getFuenteFinanciacion());
					dataRow1.createCell(17).setCellValue(iniciativa.getMontoFinanciamiento());
					x = x + 1;
				}
			}

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

							HSSFRow dataRow1 = sheet.createRow(x + 1);
							dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
							dataRow1.createCell(1).setCellValue(iniciativa.getResponsableProyecto());
							dataRow1.createCell(2).setCellValue(iniciativa.getCargoResponsable());
							dataRow1.createCell(3).setCellValue(iniciativa.getNombre());
							dataRow1.createCell(4).setCellValue(iniciativa.getAnioFormulacion());
							dataRow1.createCell(5).setCellValue(iniciativa.getObjetivoEstrategico());
							dataRow1.createCell(6).setCellValue(iniciativa.getIniciativaEstrategica());
							dataRow1.createCell(7).setCellValue(iniciativa.getLiderIniciativa());
							dataRow1.createCell(8).setCellValue(iniciativa.getTipoIniciativa());
							dataRow1.createCell(9).setCellValue(iniciativa.getOrganizacionesInvolucradas());
							dataRow1.createCell(10).setCellValue(iniciativa.getPoblacionBeneficiada());
							dataRow1.createCell(11).setCellValue(iniciativa.getContexto());
							dataRow1.createCell(12).setCellValue(iniciativa.getDefinicionProblema());
							dataRow1.createCell(13).setCellValue(iniciativa.getAlcance());
							dataRow1.createCell(14).setCellValue(iniciativa.getObjetivoGeneral());
							dataRow1.createCell(15).setCellValue(iniciativa.getObjetivoEspecificos());
							dataRow1.createCell(16).setCellValue(iniciativa.getFuenteFinanciacion());
							dataRow1.createCell(17).setCellValue(iniciativa.getMontoFinanciamiento());
							x = x + 1;
						}
					}
				}
			}

			Date date = new Date();
			SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

			String archivo = "IniciativasDatosBasicos_" + hourdateFormat.format(date) + ".xls";

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

			ServletOutputStream file = response.getOutputStream();

			workbook.write(file);
			file.close();
		}

		forward = "exito";
		organizacionservice.close();
		iniciativaservice.close();

		return mapping.findForward(forward);
	}
}
