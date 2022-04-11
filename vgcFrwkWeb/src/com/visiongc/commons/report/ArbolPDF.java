package com.visiongc.commons.report;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfSpotColor;
import com.lowagie.text.pdf.PdfWriter;
import com.visiongc.framework.arboles.util.ArbolesTipoNodo;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;










public class ArbolPDF
{
  public ArbolPDF() {}
  
  public static void crearReporteArbol(HttpServletRequest request, ConfiguracionPagina configuracionPagina, Document documento, PdfWriter pdfWriter, String urlImagenNodo, String urlImagenNodoExp, List listaNodos, String separador)
    throws Exception
  {
    TablaPDF tablaNodo = null;
    


    float positionYnodo = 0.0F;
    float positionY = 0.0F;
    float positionX = 0.0F;
    float rowHeight = 0.0F;
    int identX = 0;
    byte tipoNodoActual = 0;
    byte tipoNodoAnterior = 0;
    float[] coordinatesXY = new float[2];
    float[] levelCoordinatesXY = new float[2];
    float OFFSET_MARGIN = documento.leftMargin();
    float OFFSET_POSITIONY = 1.0F;
    float OFFSET_LENGTHLINE = 21.0F;
    float OFFSET_IMAGEHEIGHT = 6.0F;
    int SIZE_IMAGEWIDTH = 10;
    boolean anularTrazado = false;
    Map nivelesAbiertos = new HashMap();
    

    Image nodoExpandido = TablaBasicaPDF.crearImagen(request, urlImagenNodoExp, SIZE_IMAGEWIDTH, SIZE_IMAGEWIDTH);
    
    Image nodo = TablaBasicaPDF.crearImagen(request, urlImagenNodo, SIZE_IMAGEWIDTH, SIZE_IMAGEWIDTH);
    
    if ((listaNodos != null) && (listaNodos.size() > 0))
    {
      anularTrazado = true;
      
      positionY = 0.0F;
      positionX = 0.0F;
      rowHeight = 0.0F;
      
      for (int k = 0; k < 2; k++) {
        coordinatesXY[k] = 0.0F;
      }
      for (Iterator iter = listaNodos.iterator(); iter.hasNext();)
      {
        String nombreNodo = (String)iter.next();
        
        String[] datosNodo = nombreNodo.split(separador);
        
        identX = new Integer(datosNodo[0]).intValue();
        int porcentajeTotal = 98;
        int porcentajeIdent = identX * 5;
        int[] columnas = new int[3];
        columnas[0] = porcentajeIdent;
        columnas[1] = 2;
        columnas[2] = (porcentajeTotal - porcentajeIdent);
        
        tablaNodo = new TablaPDF(configuracionPagina, request);
        tablaNodo.setAmplitudTabla(100);
        tablaNodo.setCellspacing(0);
        tablaNodo.setCellpadding(0);
        tablaNodo.setColorBorde(255, 255, 255);
        tablaNodo.crearTabla(columnas);
        
        tipoNodoActual = new Byte(datosNodo[1]).byteValue();
        
        tablaNodo.agregarCelda("");
        tablaNodo.setAlineacionVertical(6);
        
        if (tipoNodoActual == ArbolesTipoNodo.getArbolesTipoNodoPadre()) {
          tablaNodo.agregarCelda(nodoExpandido);
        } else {
          tablaNodo.agregarCelda(nodo);
        }
        tablaNodo.setAlineacionVertical(5);
        tablaNodo.agregarCelda(datosNodo[2]);
        
        documento.add(tablaNodo.getTabla());
        float[] headerAbsoluteWidths = tablaNodo.getTabla().getAbsoluteWidths();
        
        PdfContentByte cb = pdfWriter.getDirectContent();
        PdfSpotColor spc_rgb = new PdfSpotColor("COLOR GRIS", 0.9F, new Color(128, 128, 128));
        
        rowHeight = tablaNodo.getTabla().getRowHeight(0);
        positionY = pdfWriter.getVerticalPosition(false);
        tablaNodo.getTabla().deleteBodyRows();
        
        positionY = positionY + rowHeight / 2.0F - OFFSET_POSITIONY;
        positionX = getCoordinateX(headerAbsoluteWidths) + OFFSET_MARGIN;
        

        cb.setColorStroke(spc_rgb, spc_rgb.getTint());
        cb.setLineWidth(0.3F);
        
        if (!anularTrazado)
        {
          cb.moveTo(positionX - OFFSET_LENGTHLINE, positionY);
          cb.lineTo(positionX, positionY);
          cb.stroke();
          




          if (coordinatesXY[1] < 0.0F)
          {
            coordinatesXY[1] = positionY;
            




            Iterator iterNiveles = nivelesAbiertos.keySet().iterator();
            String keyField = null;
            
            while (iterNiveles.hasNext())
            {
              keyField = (String)iterNiveles.next();
              
              levelCoordinatesXY = new float[2];
              levelCoordinatesXY = (float[])nivelesAbiertos.get(keyField);
              levelCoordinatesXY[1] = positionY;
            }
          }
          positionYnodo = coordinatesXY[1];
          if (tipoNodoAnterior == ArbolesTipoNodo.getArbolesTipoNodoPadre())
            positionYnodo -= OFFSET_IMAGEHEIGHT;
          cb.moveTo(positionX - OFFSET_LENGTHLINE, positionYnodo);
          cb.lineTo(positionX - OFFSET_LENGTHLINE, positionY);
          cb.stroke();
          
          if (nivelesAbiertos.containsKey(String.valueOf(identX)))
          {
            levelCoordinatesXY = new float[2];
            levelCoordinatesXY = (float[])nivelesAbiertos.get(String.valueOf(identX));
            
            cb.moveTo(levelCoordinatesXY[0] - OFFSET_LENGTHLINE, levelCoordinatesXY[1]);
            cb.lineTo(levelCoordinatesXY[0] - OFFSET_LENGTHLINE, positionY);
            cb.stroke();
            
            Map nivelesAbiertosTemp = new HashMap();
            nivelesAbiertosTemp.putAll(nivelesAbiertos);
            Iterator iterNiveles = nivelesAbiertosTemp.keySet().iterator();
            String keyField = null;
            
            while (iterNiveles.hasNext())
            {
              keyField = (String)iterNiveles.next();
              
              if (new Integer(keyField).intValue() >= identX + 1) {
                nivelesAbiertos.remove(keyField);
              }
            }
          }
          if ((positionY - (rowHeight + documento.bottomMargin()) < 0.0F) && (positionY - (rowHeight + documento.bottomMargin()) < rowHeight))
          {
            Iterator iterNiveles = nivelesAbiertos.keySet().iterator();
            String keyField = null;
            
            while (iterNiveles.hasNext())
            {
              keyField = (String)iterNiveles.next();
              
              levelCoordinatesXY = new float[2];
              levelCoordinatesXY = (float[])nivelesAbiertos.get(keyField);
              
              cb.moveTo(levelCoordinatesXY[0] - OFFSET_LENGTHLINE, levelCoordinatesXY[1]);
              cb.lineTo(levelCoordinatesXY[0] - OFFSET_LENGTHLINE, positionY);
              cb.stroke();
            }
          }
        }
        

        if (tipoNodoAnterior == ArbolesTipoNodo.getArbolesTipoNodoPadre())
        {
          levelCoordinatesXY = new float[2];
          levelCoordinatesXY[0] = positionX;
          levelCoordinatesXY[1] = positionY;
          nivelesAbiertos.put(String.valueOf(identX), levelCoordinatesXY);
        }
        
        anularTrazado = false;
        coordinatesXY[0] = positionX;
        coordinatesXY[1] = positionY;
        tipoNodoAnterior = tipoNodoActual;
        
        if ((positionY - (rowHeight + documento.bottomMargin()) < 0.0F) && (positionY - (rowHeight + documento.bottomMargin()) < rowHeight)) {
          coordinatesXY[1] = -1.0F;
        }
      }
    }
  }
  
  private static float getCoordinateX(float[] widthColumns) {
    float coordinateX = widthColumns[0];
    
    return coordinateX;
  }
}
