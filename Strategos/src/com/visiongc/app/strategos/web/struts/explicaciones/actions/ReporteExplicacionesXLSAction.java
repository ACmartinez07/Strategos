/**
 * 
 */
package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.GestionarExplicacionesForm;
import com.visiongc.commons.util.PaginaLista;

/**
 * @author Kerwin
 *
 */
public class ReporteExplicacionesXLSAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MessageResources mensajes = getResources(request);

		String objetoId = request.getParameter("objetoId");
		String objetoKey = request.getParameter("objetoKey");
		Integer tipo = request.getParameter("tipo") != null ? Integer.parseInt(request.getParameter("tipo")) : 1;
		String titulo = request.getParameter("titulo");

		GestionarExplicacionesForm gestionarExplicacionesForm = new GestionarExplicacionesForm();
		gestionarExplicacionesForm.setTipo(tipo);
		gestionarExplicacionesForm.setObjetoId(Long.parseLong(objetoId));
		gestionarExplicacionesForm.setTipoObjetoKey(objetoKey);
		gestionarExplicacionesForm.setFiltroTitulo(titulo);
		
		if (objetoKey.equals("Indicador"))
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));
			gestionarExplicacionesForm.setNombreObjetoKey(indicador.getNombre());
			strategosIndicadoresService.close();
		} 
		else if (objetoKey.equals("Celda"))
		{
			StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
			Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

			String nombreObjetoKey = "";
			if (celda.getIndicadoresCelda() != null) 
			{
				if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) 
					nombreObjetoKey = celda.getTitulo();
				else if (celda.getIndicadoresCelda().size() == 1) 
				{
					IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
					nombreObjetoKey = indicador.getNombre();
					strategosIndicadoresService.close();
				}
			}
			else nombreObjetoKey = celda.getTitulo();
			gestionarExplicacionesForm.setNombreObjetoKey(nombreObjetoKey);
			
			strategosCeldasService.close();
		} 
		else  if (objetoKey.equals("Iniciativa"))
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(objetoId));
			
			gestionarExplicacionesForm.setNombreObjetoKey(iniciativa.getNombre());
			
			strategosIniciativasService.close();
		} 
		else if (objetoKey.equals("Organizacion"))
		{
			OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
			
			gestionarExplicacionesForm.setNombreObjetoKey(organizacion.getNombre());
		}
		
		return dibujarInformacion(mensajes, gestionarExplicacionesForm, request);
	}
	
	private HSSFCellStyle getEstiloEncabezado(HSSFWorkbook objWB)
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
	
	private HSSFCellStyle getEstiloCuerpo(HSSFWorkbook objWB)
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
	
	private StreamInfo dibujarInformacion(MessageResources mensajes, GestionarExplicacionesForm gestionarExplicacionesForm, HttpServletRequest request) throws Exception
	{
		String atributoOrden = "fecha";
		String tipoOrden = "DESC";
		int pagina = 1;

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		if (gestionarExplicacionesForm.getTipo() != null) 
			filtros.put("tipo", gestionarExplicacionesForm.getTipo());
		if ((gestionarExplicacionesForm.getFiltroTitulo() != null) && (!gestionarExplicacionesForm.getFiltroTitulo().equals(""))) 
			filtros.put("titulo", gestionarExplicacionesForm.getFiltroTitulo());
		if ((gestionarExplicacionesForm.getFiltroAutor() != null) && (!gestionarExplicacionesForm.getFiltroAutor().equals(""))) 
			filtros.put("autor", gestionarExplicacionesForm.getFiltroAutor());
		if ((gestionarExplicacionesForm.getObjetoId() != null) && (!gestionarExplicacionesForm.getObjetoId().equals("")) && (gestionarExplicacionesForm.getObjetoId().byteValue() != 0)) 
			filtros.put("objetoId", gestionarExplicacionesForm.getObjetoId().toString());
		if (gestionarExplicacionesForm.getTipoObjetoKey().equals("Indicador"))
			filtros.put("objetoKey", "0");
		if (gestionarExplicacionesForm.getTipoObjetoKey().equals("Celda"))
			filtros.put("objetoKey", "1");

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		HSSFWorkbook objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;

		// Creo la hoja
		HSSFSheet hoja = objWB.createSheet("Explicaciones");

		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFRow fila = hoja.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reporte.explicacion.organizacion") + " : " + ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		
		numeroCelda = 1;
		fila = hoja.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reporte.explicacion.objeto") + " : " + gestionarExplicacionesForm.getNombreObjetoKey());

		numeroCelda = 1;
		fila = hoja.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		if (paginaExplicaciones.getLista().size() > 0)
		{
			numeroFila = crearEncabezado(numeroFila, objWB, hoja, mensajes, request);

			for (Iterator<Explicacion> iter = paginaExplicaciones.getLista().iterator(); iter.hasNext();)
			{
				Explicacion explicacion = (Explicacion)iter.next();

				// Autor
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda++);
				celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(explicacion.getUsuarioCreado().getFullName());
				
    		    // Fecha
				celda = fila.createCell(numeroCelda++);
				celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(explicacion.getFechaFormateada());
    		    
    		    // Titulo
				celda = fila.createCell(numeroCelda++);
				celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(explicacion.getTitulo());
			}
		}
		else
		{
			fila = hoja.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(mensajes.getMessage("jsp.reporte.explicacion.noexplicaciones", gestionarExplicacionesForm.getNombreObjetoKey()));
		}
		
		// Volcamos la información a un archivo.
		String strNombreArchivo = "exportar.xls";
		File objFile = new File(strNombreArchivo);

		FileOutputStream archivoSalida = new FileOutputStream(objFile);
		objWB.write(archivoSalida);
		archivoSalida.close();

		return new FileStreamInfo("application/vnd.ms-excel", new File(strNombreArchivo));
	}
	
	private int crearEncabezado(int numeroFila, HSSFWorkbook objWB, HSSFSheet hoja, MessageResources mensajes, HttpServletRequest request) throws Exception
	{
	    String[][] columnas = new String[3][2];
	    StringBuilder string;
	    columnas[0][0] = "30";
	    columnas[0][1] = mensajes.getMessage("jsp.reporte.explicacion.autor");
	    
	    columnas[1][0] = "15";
	    columnas[1][1] = mensajes.getMessage("jsp.reporte.explicacion.fecha");

	    columnas[2][0] = "60";
	    string = new StringBuilder();
		string.append(mensajes.getMessage("jsp.reporte.explicacion.titulo"));
		string.append("\n");
		string.append("\n");
	    columnas[2][1] = string.toString();
	    
		int numeroCelda = 1;
		HSSFRow fila = hoja.createRow(numeroFila++);
		HSSFCell celda = null;
		for (int k = 0; k < columnas.length; k++)
		{
			celda = fila.createCell(k+numeroCelda);
			celda.setCellStyle(getEstiloEncabezado(objWB));

			celda.setCellType(HSSFCell.CELL_TYPE_STRING);
			celda.setCellValue(columnas[k][1]);
		}
	    
		return numeroFila;
	}
}
