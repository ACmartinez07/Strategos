package com.visiongc.commons.report;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import java.awt.Color;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;







public class TablaBasicaPDF
  implements Tabla
{
  private int font;
  private float tamanoFont;
  private int formatoFont;
  private Color colorBorde;
  private Color colorFondo;
  private Color colorLetra;
  private int anchoBorde;
  private int anchoBordeCelda;
  private int alineacionHorizontal;
  private int alineacionVertical;
  private int amplitudTabla;
  private Table tabla;
  private int rowspan;
  private int colspan;
  private int cellspacing;
  private int cellpadding;
  private int indexColumn = 0;
  private int rowSpanSize = 0;
  private int rowSpanIndex = 0;
  private int rowSpanIndexColumn = 0;
  private Font letra;
  private Integer[] tamanoCol;
  private String[] tituloCol;
  
  public void setDefaultAmplitudTabla()
  {
    amplitudTabla = 100;
  }
  
  public void setDefaultRowspan()
  {
    rowspan = 1;
  }
  
  public void setDefaultColspan()
  {
    colspan = 1;
  }
  
  public void setDefaultCellspacingPdf()
  {
    cellspacing = 0;
  }
  
  public void setDefaultCellspacingHtml()
  {
    cellspacing = 1;
  }
  
  public void setDefaultCellpadding()
  {
    cellpadding = 0;
  }
  
  public void setDefaultAnchoBorde()
  {
    anchoBorde = 1;
  }
  
  public void setDefaultFormatoFont()
  {
    formatoFont = 0;
  }
  
  public void setDefaultTamanoFont()
  {
    tamanoFont = 8.0F;
  }
  
  public void setDefaultColorBorde()
  {
    colorBorde = new Color(0, 0, 0);
  }
  
  public void setDefaultColorFondo()
  {
    colorFondo = new Color(255, 255, 255);
  }
  
  public void setDefaultColorLetra()
  {
    colorLetra = new Color(0, 0, 0);
  }
  
  public void setDefaultAlineacionVertical()
  {
    alineacionVertical = 5;
  }
  
  public void setDefaultAlineacionHorizontal()
  {
    alineacionHorizontal = 0;
  }
  
  public void setFont(int font)
  {
    this.font = font;
  }
  
  public int getFont()
  {
    if (letra != null) {
      return letra.style();
    }
    return font;
  }
  
  public void setTamanoFont(float tamanoFont)
  {
    this.tamanoFont = tamanoFont;
  }
  
  public float getTamanoFont()
  {
    if (letra != null) {
      return letra.size();
    }
    return tamanoFont;
  }
  
  public void setFormatoFont(int formatoFont)
  {
    this.formatoFont = formatoFont;
  }
  
  public int getFormatoFont()
  {
    if (letra != null) {
      return letra.family();
    }
    return formatoFont;
  }
  
  public void setColorBorde(int r, int g, int b)
  {
    colorBorde = new Color(r, g, b);
  }
  
  public Color getColorBorde()
  {
    return colorBorde;
  }
  
  public void setColorFondo(int r, int g, int b)
  {
    colorFondo = new Color(r, g, b);
  }
  
  public Color getColorFondo()
  {
    return colorFondo;
  }
  
  public void setColorLetra(int r, int g, int b)
  {
    colorLetra = new Color(r, g, b);
  }
  
  public Color getColorLetra()
  {
    if (letra != null) {
      return letra.color();
    }
    return colorLetra;
  }
  
  public void setAnchoBorde(int ancho)
  {
    anchoBorde = ancho;
  }
  
  public int getAnchoBorde()
  {
    return anchoBorde;
  }
  
  public void setAlineacionHorizontal(int alineacionHorizontal)
  {
    this.alineacionHorizontal = alineacionHorizontal;
  }
  
  public int getAlineacionHorizontal()
  {
    return alineacionHorizontal;
  }
  
  public void setAlineacionVertical(int alineacionVertical)
  {
    this.alineacionVertical = alineacionVertical;
  }
  
  public int getAlineacionVertical()
  {
    return alineacionVertical;
  }
  
  public void setAmplitudTabla(int amplitudTabla)
  {
    this.amplitudTabla = amplitudTabla;
  }
  
  public int getAmplitudTabla()
  {
    return amplitudTabla;
  }
  
  public void setRowspan(int rowspan)
  {
    this.rowspan = rowspan;
  }
  
  public int getRowspan()
  {
    return rowspan;
  }
  
  public void setColspan(int colspan)
  {
    this.colspan = colspan;
  }
  
  public int getColspan()
  {
    return colspan;
  }
  
  public void setCellspacing(int cellspacing)
  {
    this.cellspacing = cellspacing;
  }
  
  public int getCellspacing()
  {
    return cellspacing;
  }
  
  public void setCellpadding(int cellpadding)
  {
    this.cellpadding = cellpadding;
  }
  
  public int getCellpadding()
  {
    return cellpadding;
  }
  
  public int getAnchoBordeCelda()
  {
    return anchoBordeCelda;
  }
  
  public void setAnchoBordeCelda(int anchoBordeCelda)
  {
    this.anchoBordeCelda = anchoBordeCelda;
  }
  
  public Font getLetra()
  {
    return letra;
  }
  
  public void setLetra(Font letra)
  {
    this.letra = letra;
  }
  
  public Integer[] getTamanoCol()
  {
    return tamanoCol;
  }
  
  public void setTamanoCol(Integer[] tamanoCol)
  {
    this.tamanoCol = tamanoCol;
  }
  
  public String[] getTituloCol()
  {
    return tituloCol;
  }
  
  public void setTituloCol(String[] tituloCol)
  {
    this.tituloCol = tituloCol;
  }
  
  public TablaBasicaPDF(ConfiguracionPagina configuracionPagina, HttpServletRequest request)
  {
    font = configuracionPagina.getCodigoFuente();
    formatoFont = configuracionPagina.getFuente().style();
    tamanoFont = configuracionPagina.getTamanoFuente().intValue();
    
    String tipoPresentacion = request.getParameter("tipoPresentacion");
    if (tipoPresentacion == null) {
      tipoPresentacion = "pdf";
    }
    if ("PDF".equalsIgnoreCase(tipoPresentacion)) {
      setDefaultCellspacingPdf();
    } else if ("HTML".equalsIgnoreCase(tipoPresentacion))
      setDefaultCellspacingHtml();
    setDefaultCellpadding();
    setDefaultAmplitudTabla();
    setDefaultColorBorde();
    setDefaultColorFondo();
    setDefaultColorLetra();
    setDefaultAnchoBorde();
    setAnchoBordeCelda(1);
  }
  
  public TablaBasicaPDF(Integer anchoBorde, Integer anchoBordeCelda, ConfiguracionPagina configuracionPagina, HttpServletRequest request)
  {
    if (anchoBorde == null)
      anchoBorde = Integer.valueOf(1);
    if (anchoBordeCelda == null) {
      anchoBordeCelda = Integer.valueOf(1);
    }
    font = configuracionPagina.getCodigoFuente();
    formatoFont = configuracionPagina.getFuente().style();
    tamanoFont = configuracionPagina.getTamanoFuente().intValue();
    
    String tipoPresentacion = request.getParameter("tipoPresentacion");
    if (tipoPresentacion == null) {
      tipoPresentacion = "pdf";
    }
    if ("PDF".equalsIgnoreCase(tipoPresentacion)) {
      setDefaultCellspacingPdf();
    } else if ("HTML".equalsIgnoreCase(tipoPresentacion))
      setDefaultCellspacingHtml();
    setDefaultCellpadding();
    setDefaultAmplitudTabla();
    setDefaultColorBorde();
    setDefaultColorFondo();
    setDefaultColorLetra();
    this.anchoBorde = anchoBorde.intValue();
    setAnchoBordeCelda(anchoBordeCelda.intValue());
  }
  






  public void crearTabla(int[] anchoColumnas)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.crearTabla";
    

    try
    {
      tabla = new Table(anchoColumnas.length);
      

      tabla.setWidths(anchoColumnas);
      indexColumn = 1;
      
      tabla.setSpaceInsideCell(getCellpadding());
      tabla.setSpaceBetweenCells(getCellspacing());
      
      setDefaultAlineacionVertical();
      setDefaultAlineacionHorizontal();
      setDefaultRowspan();
      setDefaultColspan();
      

      tabla.setBorderWidth(getAnchoBorde());
      tabla.setBorderColor(getColorBorde());
      tabla.setBackgroundColor(getColorFondo());
      tabla.setWidth(getAmplitudTabla());
      tabla.setLastHeaderRow(1);
    }
    catch (Exception e)
    {
      throw new Exception("TablaPDF.crearTabla\\" + e.getMessage());
    }
  }
  
  public void encabezadoTabla(Font font, boolean saltarPagina, Document documento, int[] columnas, String[] columnasTitulo, String titulo, String subTitulo) throws Exception
  {
    if (tabla == null) {
      crearTabla(columnas);
    }
    letra = font;
    agregarFila(columnasTitulo);
    tabla.setLastHeaderRow(1);
  }
  
  public Table getTabla()
  {
    return tabla;
  }
  






  public static Image crearImagen(HttpServletRequest request, String urlImagen)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.crearImagen";
    
    try
    {
      Image imagen = Image.getInstance(new URL(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/" + urlImagen));
      imagen.setWidthPercentage(100.0F);
      
      return imagen;
    }
    catch (Exception e)
    {
      throw new Exception("TablaPDF.crearImagen\\" + e.getMessage());
    }
  }
  





  public static Image crearImagen(HttpServletRequest request, String urlImagen, int ancho, int alto)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.crearImagen";
    
    try
    {
      Image imagen = Image.getInstance(new URL(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/" + urlImagen));
      imagen.scaleToFit(imagen.scaledWidth() / imagen.scaledHeight() * ancho, alto);
      
      return imagen;

    }
    catch (Exception e)
    {
      throw new Exception("TablaPDF.crearImagen\\" + e.getMessage());
    }
  }
  




  public void inicializarArreglo(Object[] arreglo)
  {
    for (int i = 0; i < arreglo.length; i++) {
      arreglo[i] = null;
    }
  }
  





  public void insertarLineas(Document documento, int lineas)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.insertarLineas";
    
    try
    {
      for (int i = 1; i <= lineas; i++) {
        documento.add(new Paragraph(Chunk.NEWLINE));
      }
    }
    catch (Exception e)
    {
      throw new Exception("TablaPDF.insertarLineas\\" + e.getMessage());
    }
  }
  
  public void agregarCelda(String info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  






  public void agregarCelda(String info, Integer alignHorizontal)
    throws Exception
  {
    float[] anchoColumnas = tabla.getProportionalWidths();
    

    Cell celda = new Cell(new Paragraph(info, new Font(getFont(), getTamanoFont(), getFormatoFont(), getColorLetra())));
    celda.setBackgroundColor(getColorFondo());
    celda.setBorderWidth(getAnchoBordeCelda());
    celda.setBorderColor(getColorBorde());
    celda.setBackgroundColor(getColorFondo());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    
    celda.setColspan(getColspan());
    celda.setRowspan(getRowspan());
    
    if (getRowspan() > 1)
    {
      if (rowSpanSize == 0)
      {
        rowSpanSize = getRowspan();
        rowSpanIndex = 1;
        rowSpanIndexColumn = (indexColumn + 1);
      }
      else {
        rowSpanIndexColumn = (indexColumn + 1);
      }
    }
    if (getColspan() > 1)
    {
      int lastColum = indexColumn + getColspan() - 1;
      float widthColums = 0.0F;
      for (int j = indexColumn; j <= lastColum; j++)
      {
        widthColums += anchoColumnas[(indexColumn - 1)];
        if (indexColumn == anchoColumnas.length)
        {
          if (rowSpanSize > 0)
          {
            if (rowSpanIndex == rowSpanSize)
            {
              indexColumn = 1;
              rowSpanIndex = 0;
              rowSpanSize = 0;
              rowSpanIndexColumn = 0;
            }
            else
            {
              indexColumn = rowSpanIndexColumn;
              rowSpanIndex += 1;
            }
          }
          else {
            indexColumn = 1;
          }
        } else
          indexColumn += 1;
      }
      celda.setWidth(widthColums + "%");
    }
    else
    {
      celda.setWidth(Float.toString(anchoColumnas[(indexColumn - 1)]) + "%");
      if (indexColumn == anchoColumnas.length)
      {
        if (rowSpanSize > 0)
        {
          if (rowSpanIndex == rowSpanSize)
          {
            indexColumn = 1;
            rowSpanIndex = 0;
            rowSpanSize = 0;
            rowSpanIndexColumn = 0;
          }
          else
          {
            indexColumn = rowSpanIndexColumn;
            rowSpanIndex += 1;
          }
        }
        else {
          indexColumn = 1;
        }
      } else {
        indexColumn += 1;
      }
    }
    
    tabla.addCell(celda);
    
    setDefaultColspan();
    setDefaultRowspan();
  }
  
  public void agregarCelda(Paragraph info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  







  public void agregarCelda(Paragraph info, Integer alignHorizontal)
    throws Exception
  {
    float[] anchoColumnas = tabla.getProportionalWidths();
    

    Cell celda = new Cell(info);
    celda.setBorderWidth(getAnchoBordeCelda());
    celda.setBorderColor(getColorBorde());
    celda.setBackgroundColor(getColorFondo());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    
    celda.setColspan(getColspan());
    celda.setRowspan(getRowspan());
    
    if (getRowspan() > 1)
    {
      if (rowSpanSize == 0)
      {
        rowSpanSize = getRowspan();
        rowSpanIndex = 1;
        rowSpanIndexColumn = (indexColumn + 1);
      }
      else {
        rowSpanIndexColumn = (indexColumn + 1);
      }
    }
    if (getColspan() > 1)
    {
      int lastColum = indexColumn + getColspan() - 1;
      float widthColums = 0.0F;
      for (int j = indexColumn; j <= lastColum; j++)
      {
        widthColums += anchoColumnas[(indexColumn - 1)];
        if (indexColumn == anchoColumnas.length)
        {
          if (rowSpanSize > 0)
          {
            if (rowSpanIndex == rowSpanSize)
            {
              indexColumn = 1;
              rowSpanIndex = 0;
              rowSpanSize = 0;
              rowSpanIndexColumn = 0;
            }
            else
            {
              indexColumn = rowSpanIndexColumn;
              rowSpanIndex += 1;
            }
          }
          else {
            indexColumn = 1;
          }
        } else
          indexColumn += 1;
      }
      celda.setWidth(widthColums + "%");
    }
    else
    {
      celda.setWidth(Float.toString(anchoColumnas[(indexColumn - 1)]) + "%");
      if (indexColumn == anchoColumnas.length)
      {
        if (rowSpanSize > 0)
        {
          if (rowSpanIndex == rowSpanSize)
          {
            indexColumn = 1;
            rowSpanIndex = 0;
            rowSpanSize = 0;
            rowSpanIndexColumn = 0;
          }
          else
          {
            indexColumn = rowSpanIndexColumn;
            rowSpanIndex += 1;
          }
        }
        else {
          indexColumn = 1;
        }
      } else {
        indexColumn += 1;
      }
    }
    
    tabla.addCell(celda);
    
    setDefaultColspan();
    setDefaultRowspan();
  }
  





  public void agregarCelda(Object info)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.agregarCelda";
    
    try
    {
      if ((info instanceof String)) {
        agregarCelda((String)info);
      } else if ((info instanceof TablaBasicaPDF)) {
        agregarCelda((TablaBasicaPDF)info);
      } else if ((info instanceof Image)) {
        agregarCelda((Image)info);
      }
    }
    catch (Exception e) {
      throw new Exception("TablaPDF.agregarCelda\\" + e.getMessage());
    }
  }
  
  public void agregarCelda(TablaBasicaPDF info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  







  public void agregarCelda(TablaBasicaPDF info, Integer alignHorizontal)
    throws Exception
  {
    float[] anchoColumnas = tabla.getProportionalWidths();
    

    Cell celda = new Cell(info.getTabla());
    celda.setBorderWidth(getAnchoBorde());
    celda.setBorderColor(getColorBorde());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    celda.setBackgroundColor(getColorFondo());
    celda.setColspan(getColspan());
    celda.setRowspan(getRowspan());
    
    if (getRowspan() > 1)
    {
      if (rowSpanSize == 0)
      {
        rowSpanSize = getRowspan();
        rowSpanIndex = 1;
        rowSpanIndexColumn = (indexColumn + 1);
      }
      else {
        rowSpanIndexColumn = (indexColumn + 1);
      }
    }
    if (getColspan() > 1)
    {
      int lastColum = indexColumn + getColspan() - 1;
      float widthColums = 0.0F;
      for (int j = indexColumn; j <= lastColum; j++)
      {
        widthColums += anchoColumnas[(indexColumn - 1)];
        if (indexColumn == anchoColumnas.length)
        {
          if (rowSpanSize > 0)
          {
            if (rowSpanIndex == rowSpanSize)
            {
              indexColumn = 1;
              rowSpanIndex = 0;
              rowSpanSize = 0;
              rowSpanIndexColumn = 0;
            }
            else
            {
              indexColumn = rowSpanIndexColumn;
              rowSpanIndex += 1;
            }
          }
          else {
            indexColumn = 1;
          }
        } else
          indexColumn += 1;
      }
      celda.setWidth(widthColums + "%");
    }
    else
    {
      celda.setWidth(Float.toString(anchoColumnas[(indexColumn - 1)]) + "%");
      if (indexColumn == anchoColumnas.length)
      {
        if (rowSpanSize > 0)
        {
          if (rowSpanIndex == rowSpanSize)
          {
            indexColumn = 1;
            rowSpanIndex = 0;
            rowSpanSize = 0;
            rowSpanIndexColumn = 0;
          }
          else
          {
            indexColumn = rowSpanIndexColumn;
            rowSpanIndex += 1;
          }
        }
        else {
          indexColumn = 1;
        }
      } else {
        indexColumn += 1;
      }
    }
    
    tabla.addCell(celda);
    
    setDefaultColspan();
    setDefaultRowspan();
  }
  
  public void agregarCelda(Image info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  







  public void agregarCelda(Image info, Integer alignHorizontal)
    throws Exception
  {
    float[] anchoColumnas = tabla.getProportionalWidths();
    

    Cell celda = new Cell(info);
    celda.setBorderWidth(getAnchoBorde());
    celda.setBorderColor(getColorBorde());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    celda.setBackgroundColor(getColorFondo());
    celda.setColspan(getColspan());
    celda.setRowspan(getRowspan());
    
    if (getRowspan() > 1)
    {
      if (rowSpanSize == 0)
      {
        rowSpanSize = getRowspan();
        rowSpanIndex = 1;
        rowSpanIndexColumn = (indexColumn + 1);
      }
      else {
        rowSpanIndexColumn = (indexColumn + 1);
      }
    }
    if (getColspan() > 1)
    {
      int lastColum = indexColumn + getColspan() - 1;
      float widthColums = 0.0F;
      for (int j = indexColumn; j <= lastColum; j++)
      {
        widthColums += anchoColumnas[(indexColumn - 1)];
        if (indexColumn == anchoColumnas.length)
        {
          if (rowSpanSize > 0)
          {
            if (rowSpanIndex == rowSpanSize)
            {
              indexColumn = 1;
              rowSpanIndex = 0;
              rowSpanSize = 0;
              rowSpanIndexColumn = 0;
            }
            else
            {
              indexColumn = rowSpanIndexColumn;
              rowSpanIndex += 1;
            }
          }
          else {
            indexColumn = 1;
          }
        } else
          indexColumn += 1;
      }
      celda.setWidth(widthColums + "%");
    }
    else
    {
      celda.setWidth(Float.toString(anchoColumnas[(indexColumn - 1)]) + "%");
      if (indexColumn == anchoColumnas.length)
      {
        if (rowSpanSize > 0)
        {
          if (rowSpanIndex == rowSpanSize)
          {
            indexColumn = 1;
            rowSpanIndex = 0;
            rowSpanSize = 0;
            rowSpanIndexColumn = 0;
          }
          else
          {
            indexColumn = rowSpanIndexColumn;
            rowSpanIndex += 1;
          }
        }
        else {
          indexColumn = 1;
        }
      } else {
        indexColumn += 1;
      }
    }
    
    tabla.addCell(celda);
    
    setDefaultColspan();
    setDefaultRowspan();
  }
  








  public void agregarCelda(String info, Font font)
    throws Exception
  {
    letra = font;
    agregarCelda(info);
  }
  



  public void agregarFila(Object[] arreglo)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.agregarFila";
    
    try
    {
      for (int i = 0; i < arreglo.length; i++) {
        agregarCelda(arreglo[i]);
      }
    }
    catch (Exception e)
    {
      throw new Exception("TablaPDF.agregarFila\\" + e.getMessage());
    }
  }
  



  public void agregarFila(Object[][] arreglo)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.agregarFila";
    
    try
    {
      for (int i = 0; i < arreglo.length; i++)
      {
        if (((arreglo[i][0] instanceof String)) && ((arreglo[i][1] instanceof Integer))) {
          agregarCelda((String)arreglo[i][0], (Integer)arreglo[i][1]);
        } else if (((arreglo[i][0] instanceof Paragraph)) && ((arreglo[i][1] instanceof Integer))) {
          agregarCelda((Paragraph)arreglo[i][0], (Integer)arreglo[i][1]);
        } else if (((arreglo[i][0] instanceof TablaBasicaPDF)) && ((arreglo[i][1] instanceof Integer))) {
          agregarCelda((TablaBasicaPDF)arreglo[i][0], (Integer)arreglo[i][1]);
        } else if (((arreglo[i][0] instanceof Image)) && ((arreglo[i][1] instanceof Integer))) {
          agregarCelda((Image)arreglo[i][0], (Integer)arreglo[i][1]);
        } else {
          agregarCelda(arreglo[i][0]);
        }
      }
    }
    catch (Exception e) {
      throw new Exception("TablaPDF.agregarFila\\" + e.getMessage());
    }
  }
  
  public int lineasFila(Object[][] arreglo, Float anchoPagina) throws Exception
  {
    String CLASS_METHOD = "TablaPDF.lineasFila";
    
    try
    {
      int lineasMaximo = 1;
      float[] anchoColumnas = tabla.getProportionalWidths();
      int lineasCelda = 0;
      for (int i = 0; i < arreglo.length; i++)
      {
        Float anchoCelda = Float.valueOf(anchoColumnas[i] * anchoPagina.floatValue() / 100.0F);
        Float numerosLineas = Float.valueOf(((Integer)arreglo[i][2]).intValue() / anchoCelda.floatValue());
        Integer lineasExactas = Integer.valueOf(Math.round(numerosLineas.floatValue()));
        if (numerosLineas.floatValue() > lineasExactas.intValue()) {
          lineasCelda = numerosLineas.intValue() + 1;
        } else
          lineasCelda = lineasExactas.intValue();
        if ((arreglo[i][0] instanceof String))
          lineasCelda += buscarEnter((String)arreglo[i][0]);
        if (lineasCelda > lineasMaximo) {
          lineasMaximo = lineasCelda;
        }
      }
      return lineasMaximo;
    }
    catch (Exception e)
    {
      throw new Exception("TablaPDF.lineasFila\\" + e.getMessage());
    }
  }
  
  private int buscarEnter(String texto)
  {
    int numeroEnters = 0;
    if (texto.indexOf("\n") != -1)
    {
      String[] cantidadSaltos = texto.split("\n");
      
      if (cantidadSaltos.length > 0) {
        numeroEnters = cantidadSaltos.length * 2;
      } else {
        numeroEnters = 0;
      }
    } else {
      numeroEnters = 0;
    }
    return numeroEnters;
  }
  
  public void endTabla(PdfWriter writer, Document document) {}
}
