/**
 * 
 */
package com.visiongc.app.strategos.web.struts.mediciones.actions;

import java.io.File;
import java.io.FileOutputStream;
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
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoSource;
import com.visiongc.app.strategos.web.struts.util.Columna;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ImprimirMedicionesXLSAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

	    EditarMedicionesForm editarMedicionesForm = new EditarMedicionesForm();

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(usuario.getUsuarioId(), "Strategos.Forma.Configuracion.Columnas", "visorLista.Medicion");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Forma.Configuracion.Columnas");
			pk.setObjeto("visorLista.Medicion");
			pk.setUsuarioId(usuario.getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData(editarMedicionesForm.setColumnasDefault());
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, usuario);
		}
		frameworkService.close();
		editarMedicionesForm.setColumnas(configuracionUsuario.getData());
		
		List<SerieIndicador> seriesIndicadores = new com.visiongc.app.strategos.web.struts.mediciones.actions.ImprimirMedicionesPDFAction().cargarDatos(editarMedicionesForm, request);
		
		return construirReporte(seriesIndicadores, editarMedicionesForm, request);
	}

	private StreamInfo construirReporte(List<SerieIndicador> seriesIndicadores, EditarMedicionesForm editarMedicionesForm, HttpServletRequest request) throws Exception
	{
		MessageResources mensajes = getResources(request);
		HSSFWorkbook objWB = null;

		// Proceso la información y genero el xls
		objWB = new HSSFWorkbook();

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = null;

		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet("Medicion");

		// Proceso la información y genero el xls.
		int numeroFila = 1;
		int numeroCelda = 1;
		HSSFRow fila = hoja1.createRow(numeroFila++);

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		celda = fila.createCell(numeroCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);

		// Finalmente, establecemos el valor
		celda.setCellValue(mensajes.getMessage("jsp.editarmediciones.ficha.organizacion") + ": " + request.getSession().getAttribute("organizacionNombre").toString());

		Byte source = Byte.parseByte(request.getParameter("source"));
		if (source.byteValue() == TipoSource.SOURCE_CLASE)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_CLASE);
		else if (source.byteValue() == TipoSource.SOURCE_PLAN)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_PLAN);
		else if (source.byteValue() == TipoSource.SOURCE_INICIATIVA)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_INICIATIVA);
		else
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_ACTIVIDAD);
	    
		String subTitulo = null;
		if (source.byteValue() == TipoSource.SOURCE_CLASE)
		{
			if (request.getQueryString().indexOf("claseId=") > -1) 
				editarMedicionesForm.setClaseId(new Long(request.getParameter("claseId")));
			
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, editarMedicionesForm.getClaseId());
			strategosIndicadoresService.close();
			
			if (clase != null) 
			{
				editarMedicionesForm.setClase(clase.getNombre());
				subTitulo = mensajes.getMessage("jsp.editarmediciones.ficha.clase") + ": " + editarMedicionesForm.getClase(); 
			}
		}
				
		if (subTitulo != null)
		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(subTitulo);
		}
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");
		
		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		int numeroColumnas = 0;
		SerieIndicador serie = seriesIndicadores.get(0);
		for (Iterator<?> iter = editarMedicionesForm.getColumnas().iterator(); iter.hasNext(); ) 
		{
			Columna columna = (Columna)iter.next();
			if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
				numeroColumnas++;
			else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
				numeroColumnas = numeroColumnas + serie.getMediciones().size();
		}

	    int i = 0;
	    String[] columnasTitulo = new String[numeroColumnas];
		for (Iterator<?> iter = editarMedicionesForm.getColumnas().iterator(); iter.hasNext(); ) 
		{
			Columna columna = (Columna)iter.next();
			Integer tamanoColumna = null;
			if (columna.getTamano() != null)
				tamanoColumna = (Integer.parseInt(columna.getTamano()) * 30);
			if (tamanoColumna == null || tamanoColumna > (255*256))
				tamanoColumna = 3000;
			if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
			{
				columnasTitulo[i] = columna.getNombre();
				hoja1.setColumnWidth(i+1, tamanoColumna);
				i++;
			}
			else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
			{
	    		Indicador indicador = serie.getIndicador();
	    		for (Iterator<?> iterSerie = serie.getMediciones().iterator(); iterSerie.hasNext(); )
	    		{
	    			Medicion medicion = (Medicion)iterSerie.next();
	    			columnasTitulo[i] = PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), medicion.getMedicionId().getAno());
	    			hoja1.setColumnWidth(i+1, tamanoColumna);
	    			i++;
	    		}
			}
		}
	    
		if (seriesIndicadores.size() > 0)
		{
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			for (int k = 0; k < columnasTitulo.length; k++)
			{
				celda = fila.createCell(k+numeroCelda);
				celda.setCellStyle(getEstiloEncabezado(objWB));

				celda.setCellType(HSSFCell.CELL_TYPE_STRING);
				celda.setCellValue(columnasTitulo[k]);
			}
			
	        Long indicadorId = 0L;
			for (int f = 0; f < seriesIndicadores.size(); f++)
			{
				SerieIndicador serieIndicador = (SerieIndicador)seriesIndicadores.get(f);
				numeroCelda = 1;
				int nc = 0;
				fila = hoja1.createRow(numeroFila++);
				for (int k = 0; k < editarMedicionesForm.getColumnas().size(); k++)
				{
					Columna columna = (Columna)editarMedicionesForm.getColumnas().get(k);
					if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
					{
						if (indicadorId.longValue() != serieIndicador.getIndicador().getIndicadorId().longValue())
						{
							if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.indicador")))
							{
								celda = fila.createCell(nc+numeroCelda);
								celda.setCellStyle(getEstiloCuerpo(objWB));
								celda.setCellType(HSSFCell.CELL_TYPE_STRING);
								celda.setCellValue(serieIndicador.getIndicador().getNombre());
								nc++;
							}
							else if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.unidad")))
							{
								celda = fila.createCell(nc+numeroCelda);
								celda.setCellStyle(getEstiloCuerpo(objWB));
								celda.setCellType(HSSFCell.CELL_TYPE_STRING);
								celda.setCellValue(serieIndicador.getIndicador().getUnidad() != null ? serieIndicador.getIndicador().getUnidad().getNombre() : "");
								nc++;
							}
							else if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.serie")))
							{
								celda = fila.createCell(nc+numeroCelda);
								celda.setCellStyle(getEstiloCuerpo(objWB));
								celda.setCellType(HSSFCell.CELL_TYPE_STRING);
								celda.setCellValue(serieIndicador.getSerieTiempo().getNombre());
								nc++;
							}
						}
						else if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.serie")))
						{
							celda = fila.createCell(nc+numeroCelda);
							celda.setCellStyle(getEstiloCuerpo(objWB));
							celda.setCellType(HSSFCell.CELL_TYPE_STRING);
							celda.setCellValue(serieIndicador.getSerieTiempo().getNombre());
							nc++;
						}
						else
						{
							celda = fila.createCell(nc+numeroCelda);
							celda.setCellStyle(getEstiloCuerpo(objWB));
							celda.setCellType(HSSFCell.CELL_TYPE_STRING);
							celda.setCellValue("");
							nc++;
						}
					}
					else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
					{
						indicadorId = serieIndicador.getIndicador().getIndicadorId();
			    		for (Iterator<?> iterMedicion = serieIndicador.getMediciones().iterator(); iterMedicion.hasNext(); )
			    		{
			    			Medicion medicion = (Medicion)iterMedicion.next();

			    			celda = fila.createCell(nc+numeroCelda);
							celda.setCellStyle(getEstiloCuerpo(objWB));
							celda.setCellType(HSSFCell.CELL_TYPE_STRING);
							celda.setCellValue(medicion.getValorString() != null ? medicion.getValorString() : "");
							nc++;
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
}
