/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.File;
import java.io.FileOutputStream;
//import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

/**
 * @author Kerwin
 * 
 */
public class CrearFormatoAction extends DownloadAction 
{
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		StringBuffer sbFilename = new StringBuffer();
		sbFilename.append("filename_");
		sbFilename.append(System.currentTimeMillis());
		sbFilename.append(".xls");

		//String fileName = sbFilename.toString();
		String contentType = "application/vnd.ms-excel";
		//String url = new URL(request.getScheme() + "://"
		//		+ request.getServerName() + ":" + request.getServerPort()
		//		+ request.getContextPath() + "/temp/").toString();

		String name = "D:\\Desarrollo\\Eclipse\\Strategos\\PLUS\\Strategos\\WebContent\\temp\\filename4.xls";
		name = "filename4.xls";

		// Proceso la información y genero el xls
		HSSFWorkbook objWB = new HSSFWorkbook();

		// Creo la hoja
		HSSFSheet hoja1 = objWB.createSheet("hoja 1");

		// Proceso la información y genero el xls.
		HSSFRow fila = hoja1.createRow((short) 1);

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
		estiloCelda.setFillForegroundColor((short) 22);
		estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// Creamos la celda, aplicamos el estilo y definimos
		// el tipo de dato que contendrá la celda
		HSSFCell celda = fila.createCell(0);
		celda.setCellStyle(estiloCelda);
		celda.setCellType(HSSFCell.CELL_TYPE_STRING);

		// Finalmente, establecemos el valor
		celda.setCellValue("Un valor");

		// Volcamos la información a un archivo.
		String strNombreArchivo = name;
		File objFile = new File(strNombreArchivo);

		FileOutputStream archivoSalida = new FileOutputStream(objFile);
		objWB.write(archivoSalida);
		archivoSalida.close();

		File file = new File(name);

		/*
		 * 
		 * //if (!file.exists()) //{ WorkbookSettings wbSettings = new
		 * WorkbookSettings(); wbSettings.setWriteAccess(name);
		 * 
		 * Workbook workbook = Workbook.getWorkbook(new File(name));
		 * 
		 * WritableWorkbook ws = Workbook.createWorkbook(new File(
		 * "D:\\Desarrollo\\Eclipse\\Strategos\\PLUS\\Strategos\\WebContent\\temp\\filename3.xls"
		 * ), workbook);
		 * 
		 * //WritableSheet sheet = workbook.createSheet("Roles", 0);
		 * 
		 * int row = 1; int columm = 1;
		 * 
		 * //BufferedImage input = ImageIO.read(new URL(request.getScheme() +
		 * "://" + request.getServerName() + ":" + request.getServerPort() +
		 * request.getContextPath() + "/logo/logo.jpg"));
		 * //ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 * //ImageIO.write(input, "PNG", baos); //sheet.addImage(new
		 * WritableImage(columm, row, 2, 4, baos.toByteArray()));
		 * 
		 * WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
		 * WritableFont.BOLD); WritableCellFormat cf = new
		 * WritableCellFormat(wf); cf.setWrap(false);
		 * 
		 * row = 6; //sheet.addCell(new Label(columm, row, "Roles", cf)); row++;
		 * 
		 * wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
		 * cf = new WritableCellFormat(wf); cf.setWrap(false);
		 * 
		 * //sheet.addCell(new Label(columm, row, "Hola Mundo", cf));
		 * 
		 * ws.write(); ws.close();
		 * 
		 * workbook.close(); //}
		 */

		return new FileStreamInfo(contentType, file);
	}
}
