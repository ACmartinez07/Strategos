/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.reportes.model.Reporte.ReporteCorte;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.web.struts.reportes.actions.MostrarReporteAction;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm.SourceType;

/**
 * @author Kerwin
 *
 */
public class ImprimirVistaDatosXLSAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
	    ConfigurarVistaDatosForm configurarVistaDatosForm = (ConfigurarVistaDatosForm)form;
	    MessageResources mensajes = getResources(request);
	    
		Long reporteId = ((request.getParameter("reporteId") != null && request.getParameter("reporteId") != "") ? new Long(request.getParameter("reporteId")) : null);
		Byte source = ((request.getParameter("source") != null && request.getParameter("source") != "") ? new Byte(request.getParameter("source")) : null);
		Byte corte = ((request.getParameter("corte") != null && request.getParameter("corte") != "") ? new Byte(request.getParameter("corte")) : null);
		
		if (corte != null && ReporteCorte.getCorteLongitudinal().byteValue() == corte.byteValue())
			return getCorteLongitudinal(source, reporteId, configurarVistaDatosForm, request, mensajes);
		else
			return getCorteTransversal(reporteId, request, mensajes);
	}

	private StreamInfo getCorteTransversal(Long reporteId, HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		HSSFWorkbook objWB = null;
		if (reporteId != null)
		{
			StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
			Reporte reporte = null;
    		if (reporteId != null)
    			reporte = (Reporte) reportesService.load(Reporte.class, reporteId);
    		reportesService.close();
    		if (reporte != null)
    		{
    			ReporteForm reporteForm = new ReporteForm();
    			reporteForm.clear();
    			
    			new MostrarReporteAction().getReporte(reporteForm, reporte, reporteId, request);
		
				// Proceso la información y genero el xls
				objWB = new HSSFWorkbook();
		
				// Creamos la celda, aplicamos el estilo y definimos
				// el tipo de dato que contendrá la celda
				HSSFCell celda = null;
		
				// Creo la hoja
				HSSFSheet hoja1 = objWB.createSheet("Vista Datos");
		
				// Proceso la información y genero el xls.
				int numeroFila = 1;
				int numeroCelda = 1;
				HSSFRow fila = hoja1.createRow(numeroFila++);
		
				// Creamos la celda, aplicamos el estilo y definimos
				// el tipo de dato que contendrá la celda
				celda = fila.createCell(numeroCelda);
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		
				// Finalmente, establecemos el valor
				celda.setCellValue(mensajes.getMessage("jsp.mostrarvistadatos.titulo") + " / " + request.getSession().getAttribute("organizacionNombre"));
		
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(reporteForm.getReporteNombre());
		
				if (reporteForm.getPeriodoInicial() != null && reporteForm.getPeriodoFinal() != null && reporteForm.getAnoInicial() != null && reporteForm.getAnoFinal() != null)
				{
					numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					celda = fila.createCell(numeroCelda);
					String periodo = "Desde " + PeriodoUtil.convertirPeriodoToTexto(reporteForm.getPeriodoInicial(), reporteForm.getFrecuencia(), new Integer(reporteForm.getAnoInicial())) + " Hasta " + PeriodoUtil.convertirPeriodoToTexto(reporteForm.getPeriodoFinal(), reporteForm.getFrecuencia(), new Integer(reporteForm.getAnoFinal()));
					celda.setCellValue(periodo);
				}
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
				
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
	
				for (int f = 0; f < reporteForm.getMatrizDatos().size(); f++)
				{
					List<DatoCelda> filaDatos = (List<DatoCelda>)reporteForm.getMatrizDatos().get(f);
		
					numeroCelda = 1;
					fila = hoja1.createRow(numeroFila++);
					for (int k = 0; k < filaDatos.size(); k++)
					{
						DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
						
						celda = fila.createCell(k+numeroCelda);
						if (f == 0)
							celda.setCellStyle(GetEstiloEncabezado(objWB));
						else
							celda.setCellStyle(GetEstiloCuerpo(objWB));
						if (datoCelda.getValor() != null) 
						{
							celda.setCellType(HSSFCell.CELL_TYPE_STRING);
							celda.setCellValue(datoCelda.getValor());
						} 
					}
				}
    		}
		}
		
		// Volcamos la información a un archivo.
		String strNombreArchivo = "exportar.xls";
		File objFile = new File(strNombreArchivo);

		FileOutputStream archivoSalida = new FileOutputStream(objFile);
		objWB.write(archivoSalida);
		archivoSalida.close();

		return new FileStreamInfo("application/vnd.ms-excel", new File(strNombreArchivo));
	}
	
	private StreamInfo getCorteLongitudinal(Byte source, Long reporteId, ConfigurarVistaDatosForm configurarVistaDatosForm, HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		if (source == null)
		{
			if (reporteId == null)
				configurarVistaDatosForm.setSource(SourceType.getSourceEditar());
			else
			{
				if (configurarVistaDatosForm == null)
					configurarVistaDatosForm = new ConfigurarVistaDatosForm();
				configurarVistaDatosForm.clear(); 
				configurarVistaDatosForm.setSource(SourceType.getSourceGestionar());
				new ConfigurarVistaDatosAction().cargarConfiguracion(configurarVistaDatosForm, request);
			}
		}
		else
		{
			if (configurarVistaDatosForm == null)
			{
				configurarVistaDatosForm = new ConfigurarVistaDatosForm();
				configurarVistaDatosForm.clear(); 
				configurarVistaDatosForm.setSource(SourceType.getSourceGestionar());
				new ConfigurarVistaDatosAction().cargarConfiguracion(configurarVistaDatosForm, request);
			}
		}
	    
  		String valorSelectorOrganizacion = ((request.getParameter("organizacionId") != null && request.getParameter("organizacionId") != "") ? request.getParameter("organizacionId") : null);
  		if (valorSelectorOrganizacion != null && !valorSelectorOrganizacion.equals(""))
  		{
  			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
  			OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(valorSelectorOrganizacion));
  			strategosIndicadoresService.close();
  			
  			valorSelectorOrganizacion = null;
  			if (organizacion != null)
  			{
  				valorSelectorOrganizacion = organizacion.getNombre();
  	  			configurarVistaDatosForm.setNombreSelector1(organizacion.getNombre());
  	  			configurarVistaDatosForm.setSelector1Id(new Long(TipoDimension.getTipoDimensionOrganizacion()));
  	  			configurarVistaDatosForm.setValorSelector1(organizacion.getOrganizacionId().toString());
  			}
  			
  		}
  		
  		String valorSelectorTiempo = ((request.getParameter("tiempo") != null && request.getParameter("tiempo") != "") ? request.getParameter("tiempo") : null);
  		if (valorSelectorTiempo != null && !valorSelectorTiempo.equals(""))
  		{
  			configurarVistaDatosForm.setNombreSelector1(valorSelectorTiempo);
  			configurarVistaDatosForm.setSelector1Id(new Long(TipoDimension.getTipoDimensionTiempo()));
  			configurarVistaDatosForm.setValorSelector1(valorSelectorTiempo);
  		}

  		new MostrarVistaDatosAction().setConfigurarVistaDatosForm(configurarVistaDatosForm, getResources(request));
		
		// Proceso la información y genero el xls
		HSSFWorkbook objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;

		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet("Vista Datos");

		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		HSSFRow fila = hoja1.createRow(numeroFila++);

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);

		// Finalmente, establecemos el valor
		celda.setCellValue(mensajes.getMessage("jsp.mostrarvistadatos.titulo") + " / " + request.getSession().getAttribute("organizacionNombre"));

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue(configurarVistaDatosForm.getNombre());

		if (configurarVistaDatosForm.getPeriodoInicial() != null && configurarVistaDatosForm.getPeriodoFinal() != null && configurarVistaDatosForm.getAnoInicial() != null && configurarVistaDatosForm.getAnoFinal() != null)
		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			String periodo = "Desde " + PeriodoUtil.convertirPeriodoToTexto(configurarVistaDatosForm.getPeriodoInicial(), configurarVistaDatosForm.getFrecuencia(), configurarVistaDatosForm.getAnoInicial()) + " Hasta " + PeriodoUtil.convertirPeriodoToTexto(configurarVistaDatosForm.getPeriodoFinal(), configurarVistaDatosForm.getFrecuencia(), configurarVistaDatosForm.getAnoFinal());
			celda.setCellValue(periodo);
		}
		
  		if (valorSelectorOrganizacion != null && !valorSelectorOrganizacion.equals(""))
  		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			String organizacionSeleccionada = "Organizacion : " + valorSelectorOrganizacion;    
			celda.setCellValue(organizacionSeleccionada);
  		}
  		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		for (int f = 0; f < configurarVistaDatosForm.getMatrizDatos().size(); f++)
		{
			List<DatoCelda> filaDatos = (List<DatoCelda>)configurarVistaDatosForm.getMatrizDatos().get(f);

			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			for (int k = 0; k < filaDatos.size(); k++)
			{
				DatoCelda datoCelda = (DatoCelda)filaDatos.get(k);
				
				celda = fila.createCell(k+numeroCelda);
				if (f == 0)
					celda.setCellStyle(GetEstiloEncabezado(objWB));
				else
					celda.setCellStyle(GetEstiloCuerpo(objWB));
				if (!datoCelda.getEsAlerta())
				{
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(datoCelda.getValor());
				} 
				else if (datoCelda.getValor() != null)
				{
					String alerta = null;
					switch (new Integer(datoCelda.getValor())) 
			        { 
				        case 0:
					        alerta = "Roja"; 
					        break;
				        case 2:
				        	alerta = "Verde"; 
					    	break;
				        case 3:
				        	alerta = "Amarilla"; 
				        	break;
				        case 1:
				        	alerta = "Blanca";
					        break;
			        }
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(alerta);
				}
				else
				{
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(datoCelda.getValor());
				}
			}
		}
		
		// Volcamos la información a un archivo.
		String strNombreArchivo = "exportar.xls";
		File objFile = new File(strNombreArchivo);

		FileOutputStream archivoSalida = new FileOutputStream(objFile);
		objWB.write(archivoSalida);
		archivoSalida.close();

		return new FileStreamInfo("application/vnd.ms-excel", new File(strNombreArchivo));
	}
	
	private HSSFCellStyle GetEstiloEncabezado(HSSFWorkbook objWB)
	{
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		HSSFFont fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// Luego creamos el objeto que se encargará de aplicar el estilo a la
		// celda
		HSSFCellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// También, podemos establecer bordes...
		estiloCelda.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBottomBorderColor((short) 8);
		estiloCelda.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setLeftBorderColor((short) 8);
		estiloCelda.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setRightBorderColor((short) 8);
		estiloCelda.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		return estiloCelda; 
	}
	
	private HSSFCellStyle GetEstiloCuerpo(HSSFWorkbook objWB)
	{
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		HSSFFont fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// Luego creamos el objeto que se encargará de aplicar el estilo a la
		// celda
		HSSFCellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// También, podemos establecer bordes...
		estiloCelda.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setBottomBorderColor((short) 8);
		estiloCelda.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setLeftBorderColor((short) 8);
		estiloCelda.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setRightBorderColor((short) 8);
		estiloCelda.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		estiloCelda.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		estiloCelda.setFillForegroundColor(HSSFColor.WHITE.index);
		estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		return estiloCelda; 
	}
}
