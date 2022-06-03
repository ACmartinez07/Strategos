package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaXls extends VgcAction {

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {

		/**
		 * Se agrega el url porque este es un nivel de navegaci�n v�lido
		 */

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
		String tipoI = (request.getParameter("tipo"));
		String estatus = (request.getParameter("estatus"));
		String ano = (request.getParameter("ano"));
		int estatusId = Integer.parseInt(estatus);
		String todos = (request.getParameter("todos"));

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();

		List<OrganizacionStrategos> organizaciones = organizacionservice
				.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

		//
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
			if (!tipoI.equals("0")) {
				filtros.put("tipoId", tipoI);
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

			String header = "Reporte Iniciativas Resumido";
			HSSFCell cell = headerRow.createCell(1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);

			HSSFRow dataRow = sheet.createRow(1);			
			dataRow.createCell(0)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
			dataRow.createCell(1)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
			dataRow.createCell(2)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje"));
			dataRow.createCell(3)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
			dataRow.createCell(4)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.responsable"));
			dataRow.createCell(5)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.anio"));
			dataRow.createCell(6)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.objetivo"));
			dataRow.createCell(7)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));				

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();
					
					HSSFRow dataRow1 = sheet.createRow(x + 1);
					dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
					dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
					dataRow1.createCell(2).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
					dataRow1.createCell(3).setCellValue(iniciativa.getFechaUltimaMedicion());
					if (iniciativa.getResponsableLograrMeta() == null) {
						dataRow1.createCell(4).setCellValue("");

					} else {
						dataRow1.createCell(4).setCellValue(iniciativa.getResponsableLograrMeta().getNombre());

					}

					dataRow1.createCell(5).setCellValue(iniciativa.getAnioFormulacion());
					dataRow1.createCell(6).setCellValue(obtenerObjetivo(iniciativa));

					if (iniciativa.getTipoProyecto() == null) {
						dataRow1.createCell(7).setCellValue("");
					} else {
						dataRow1.createCell(7).setCellValue(iniciativa.getTipoProyecto().getNombre());
					}

					x = x + 1;
				}
			}
			
			Date date = new Date();
			SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
			String archivo = "IniciativasResumido_" + hourdateFormat.format(date) + ".xls";

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

			ServletOutputStream file = response.getOutputStream();

			workbook.write(file);
			file.close();

		}
		// Suborganizaciones
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
			if (!tipoI.equals("0")) {
				filtros.put("tipoId", tipoI);
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

			String header = "Reporte Iniciativas Resumido";
			HSSFCell cell = headerRow.createCell(1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);

			HSSFRow dataRow = sheet.createRow(1);			
			dataRow.createCell(0)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
			dataRow.createCell(1)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
			dataRow.createCell(2)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje"));
			dataRow.createCell(3)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
			dataRow.createCell(4)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.responsable"));
			dataRow.createCell(5)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.anio"));
			dataRow.createCell(6)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.objetivo"));
			dataRow.createCell(7)
					.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));				

			if (iniciativas.size() > 0) {

				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {

					Iniciativa iniciativa = (Iniciativa) iter.next();

					HSSFRow dataRow1 = sheet.createRow(x + 1);
					dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
					dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
					dataRow1.createCell(2).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
					dataRow1.createCell(3).setCellValue(iniciativa.getFechaUltimaMedicion());
					if (iniciativa.getResponsableLograrMeta() == null) {
						dataRow1.createCell(4).setCellValue("");

					} else {
						dataRow1.createCell(4).setCellValue(iniciativa.getResponsableLograrMeta().getNombre());

					}

					dataRow1.createCell(5).setCellValue(iniciativa.getAnioFormulacion());
					dataRow1.createCell(6).setCellValue(obtenerObjetivo(iniciativa));

					if (iniciativa.getTipoProyecto() == null) {
						dataRow1.createCell(7).setCellValue("");
					} else {
						dataRow1.createCell(7).setCellValue(iniciativa.getTipoProyecto().getNombre());
					}

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
					if (!tipoI.equals("0")) {
						filtro.put("tipoId", tipoI);
					}
					if (todos.equals("false")) {
						filtros.put("anio", ano);
					}

					List<Iniciativa> iniciativaSub = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
					System.out.println("Si llega aqui");

					if (iniciativaSub.size() > 0) {
						for (Iterator<Iniciativa> iter1 = iniciativaSub.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter1.next();
							
							HSSFRow dataRow1 = sheet.createRow(x + 1);
							dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
							dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
							dataRow1.createCell(2).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
							dataRow1.createCell(3).setCellValue(iniciativa.getFechaUltimaMedicion());
							if (iniciativa.getResponsableLograrMeta() == null) {
								dataRow1.createCell(4).setCellValue("");

							} else {
								dataRow1.createCell(4).setCellValue(iniciativa.getResponsableLograrMeta().getNombre());

							}

							dataRow1.createCell(5).setCellValue(iniciativa.getAnioFormulacion());
							dataRow1.createCell(6).setCellValue(obtenerObjetivo(iniciativa));

							if (iniciativa.getTipoProyecto() == null) {
								dataRow1.createCell(7).setCellValue("");
							} else {
								dataRow1.createCell(7).setCellValue(iniciativa.getTipoProyecto().getNombre());
							}

							x = x + 1;
							System.out.println("Si llega aqui");	
						}
					}
				}
			}				
			Date date = new Date();
			SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

			String archivo = "IniciativasResumido_" + hourdateFormat.format(date) + ".xls";

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

			ServletOutputStream file = response.getOutputStream();

			workbook.write(file);
			file.close();
		}
		// Todas las organizaciones
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
			if (!tipoI.equals("0")) {
				filtros.put("tipoId", tipoI);
			}
			if (todos.equals("false")) {
				filtros.put("anio", ano);
			}

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

			String header = "Reporte Iniciativas Resumido";
			HSSFCell cell = headerRow.createCell(1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);

			if (organizaciones.size() > 0) {

				MessageResources messageResources = getResources(request);

				HSSFRow dataRow = sheet.createRow(1);
				dataRow.createCell(0)
						.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.ruta"));
				dataRow.createCell(1)
						.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
				dataRow.createCell(2).setCellValue(
						messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
				dataRow.createCell(3).setCellValue(
						messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje"));
				dataRow.createCell(4)
						.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
				dataRow.createCell(5).setCellValue(
						messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.responsable"));
				dataRow.createCell(6)
						.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.anio"));
				dataRow.createCell(7)
						.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.objetivo"));
				dataRow.createCell(8)
						.setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));

				int x = 1;

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
					if (reporte.getFiltro().getNombre() != null)
						filtros.put("nombre", reporte.getFiltro().getNombre());
					if (!tipoI.equals("0")) {
						filtros.put("tipoId", tipoI);
					}
					if (todos.equals("false")) {
						filtros.put("anio", ano);
					}

					List<Iniciativa> iniciativas = iniciativaservice
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

					if (iniciativas.size() > 0) {

						for (Iterator<Iniciativa> iter1 = iniciativas.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = (Iniciativa) iter1.next();

							String ruta = null;
							HSSFRow dataRow1 = sheet.createRow(x + 1);
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
							dataRow1.createCell(0).setCellValue(ruta);
							dataRow1.createCell(1).setCellValue(iniciativa.getOrganizacion().getNombre());
							dataRow1.createCell(2).setCellValue(iniciativa.getNombre());
							dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
							dataRow1.createCell(4).setCellValue(iniciativa.getFechaUltimaMedicion());
							if (iniciativa.getResponsableLograrMeta() == null) {
								dataRow1.createCell(5).setCellValue("");

							} else {
								dataRow1.createCell(5).setCellValue(iniciativa.getResponsableLograrMeta().getNombre());

							}

							dataRow1.createCell(6).setCellValue(iniciativa.getAnioFormulacion());
							dataRow1.createCell(7).setCellValue(obtenerObjetivo(iniciativa));

							if (iniciativa.getTipoProyecto() == null) {
								dataRow1.createCell(8).setCellValue("");
							} else {
								dataRow1.createCell(8).setCellValue(iniciativa.getTipoProyecto().getNombre());
							}

							x = x + 1;
						}

					}

				}
			}

			Date date = new Date();
			SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

			String archivo = "IniciativasResumido_" + hourdateFormat.format(date) + ".xls";

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

			ServletOutputStream file = response.getOutputStream();

			workbook.write(file);
			file.close();
		}
		forward = "exito";
		organizacionservice.close();
		iniciativaservice.close();
		/** C�digo de l�gica de Negocio del action */

		/** Otherwise, return "success" */
		return mapping.findForward(forward);

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

}
