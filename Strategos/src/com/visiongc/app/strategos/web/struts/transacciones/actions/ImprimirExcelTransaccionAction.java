/**
 * 
 */
package com.visiongc.app.strategos.web.struts.transacciones.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.transacciones.forms.TransaccionForm;

import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Columna.ColumnaTipo;
import com.visiongc.framework.transaccion.TransaccionService;

/**
 * @author Kerwin
 *
 */
public class ImprimirExcelTransaccionAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Long transaccionId = (request.getParameter("transaccionId") != null ? Long.parseLong(request.getParameter("transaccionId")) : 0L);
		String fechaInicial = (request.getParameter("fechaInicial") != null ? request.getParameter("fechaInicial") : null);
		String fechaFinal = (request.getParameter("fechaFinal") != null ? request.getParameter("fechaFinal") : null);
		Integer numeroRegistros = ((request.getParameter("numeroRegistros") != null && request.getParameter("numeroRegistros") != "") ? Integer.parseInt(request.getParameter("numeroRegistros")) : null);

		TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
		Transaccion transaccion = (Transaccion) transaccionService.load(Transaccion.class, transaccionId);
		transaccionService.close();

		TransaccionForm transaccionForm = new TransaccionForm();
		transaccionForm.clear();
		transaccionForm.setTransaccionId(transaccionId);
		
		transaccionForm.setFechaInicial(fechaInicial);
		transaccionForm.setFechaFinal(fechaFinal);
		transaccionForm.setNumeroRegistros(numeroRegistros);
		if (transaccion != null)
		{
			if (transaccion.getTabla() != null)
				transaccionForm.setTransaccion(transaccion);
			
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador;
			transaccionForm.setFrecuencia(transaccion.getFrecuencia());
			transaccionForm.setNombre(transaccion.getNombre());
			if (transaccion.getTabla() != null)
			{
				transaccionForm.setTransaccion(transaccion);
				for (Iterator<Columna> i = transaccion.getTabla().getColumnas().iterator(); i.hasNext(); )
				{
					Columna columna = (Columna)i.next();
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoDate().byteValue())
					{
						transaccionForm.setHayColumnaFecha(true);
						if (transaccion.getIndicadorId() != null)
						{
							indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, transaccion.getIndicadorId());
							if (indicador != null)
							{
								transaccionForm.setIndicadorTransaccionesId(indicador.getIndicadorId());
								transaccionForm.setIndicadorTransaccionesNombre(indicador.getNombre());
							}
						}
					}
					
					if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
					{
						transaccionForm.setHayColumnaMonto(true);
						if (columna.getIndicadorId() != null)
						{
							indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, columna.getIndicadorId());
							if (indicador != null)
							{
								transaccionForm.setIndicadorMontoId(indicador.getIndicadorId());
								transaccionForm.setIndicadorMontoNombre(indicador.getNombre());
							}
						}
					}
				}
			}
			strategosIndicadoresService.close();
		}
		
		List<Columna> columnas = new ArrayList<Columna>();
		String atributos = request.getParameter("xmlAtributos") != null && !request.getParameter("xmlAtributos").equals("") ? request.getParameter("xmlAtributos") : "";
		if (!atributos.equals(""))
		{
			String[] tipos = atributos.split("\\|");
			for (int i = 0; i < tipos.length; i++)
			{
				String[] campos = tipos[i].split(",");
				if (campos.length == 6)
				{
					for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
					{
						Columna columna = (Columna)col.next();
						if (columna.getNombre().equals(campos[1]))
						{
							columna.setOrden(campos[0]);
							columna.setVisible(campos[3].equals("1") ? true : false);
							columna.setAncho(campos[4]);
							columna.setAgrupar(campos[5].equals("1") ? true : false);
							
							columnas.add(columna);
						}
					}
				}
			}
			transaccionForm.getTransaccion().getTabla().setColumnas(columnas);
		}
		
		List<List<Columna>> datos = new ReporteTransaccionAction().getDatos(false, request, transaccionForm, getMessages(request));
		
		HSSFWorkbook objWB = null;

		// Proceso la información y genero el xls
		objWB = new HSSFWorkbook();
		
		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;
		
		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet(transaccionForm.getNombre());
		
		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		HSSFRow fila = hoja1.createRow(numeroFila++);
		
		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(transaccionForm.getNombre());
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
				
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
	
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		for (Iterator<Columna> col = transaccionForm.getTransaccion().getTabla().getColumnas().iterator(); col.hasNext(); )
		{
			Columna columna = (Columna)col.next();
			if (columna.getVisible())
			{
				celda = fila.createCell(numeroCelda++);
				celda.setCellStyle(GetEstiloEncabezado(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(columna.getAlias());
			}
		}

		for (Iterator<List<Columna>> iter = datos.iterator(); iter.hasNext();)
	    {
	    	List<Columna> columna = (List<Columna>)iter.next();
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
	    	for (Iterator<Columna> col = columna.iterator(); col.hasNext(); )
	    	{
				Columna c = (Columna)col.next();
				if (c.getVisible())
				{
					celda = fila.createCell(numeroCelda++);
					celda.setCellType(HSSFCell.CELL_TYPE_STRING);
					celda.setCellValue(c.getValorArchivo());
				}
	    	}
	    }

		MessageResources mensajes = getResources(request);
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda++);
		celda.setCellStyle(GetEstiloEncabezado(objWB));
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reporte.transaccion.reporte.montoconsolidado"));
		celda = fila.createCell(numeroCelda++);
		celda.setCellStyle(GetEstiloEncabezado(objWB));
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reporte.transaccion.reporte.totaloperacion"));

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda++);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(transaccionForm.getTotalConsolidado());
		celda = fila.createCell(numeroCelda++);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(transaccionForm.getTotalOperacion());
		
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
}
