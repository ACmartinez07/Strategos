package com.visiongc.commons.report;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import java.awt.Color;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;




public class TablaPDF
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
  private PdfPTable tabla;
  private int rowspan;
  private int colspan;
  private int cellspacing;
  private int cellpadding;
  private Font letra;
  
  public TablaPDF(ConfiguracionPagina configuracionPagina, HttpServletRequest request)
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
    setDefaultAnchoBorde();
    setAnchoBordeCelda(1);
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
    catch (Exception e) {
      throw new Exception("TablaPDF.insertarLineas\\" + e.getMessage());
    }
  }
  
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
  
  public void setDefaultCellspacingHtml()
  {
    cellspacing = 1;
  }
  
  public void setDefaultCellspacingPdf()
  {
    cellspacing = 0;
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
  


  public void crearTabla(int[] anchoColumnas)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.crearTabla";
    

    try
    {
      tabla = new PdfPTable(anchoColumnas.length);
      

      tabla.setWidths(anchoColumnas);
      
      setDefaultFormatoFont();
      setDefaultTamanoFont();
      setDefaultAlineacionVertical();
      setDefaultAlineacionHorizontal();
      setDefaultRowspan();
      setDefaultColspan();
      

      tabla.getDefaultCell().setBorderWidth(getAnchoBorde());
      tabla.getDefaultCell().setBorderColor(getColorBorde());
      tabla.getDefaultCell().setBackgroundColor(getColorFondo());
      tabla.getDefaultCell().setPadding(getCellpadding());
      tabla.getDefaultCell().setColspan(1);
      tabla.getDefaultCell().setHorizontalAlignment(1);
      tabla.setWidthPercentage(getAmplitudTabla());
      letra = null;
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
    tabla.setHeaderRows(1);
  }
  
  public PdfPTable getTabla()
  {
    return tabla;
  }
  
  public void agregarCelda(String info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  








  public void agregarCelda(String info, Integer alignHorizontal)
    throws Exception
  {
    PdfPCell celda = new PdfPCell(new Paragraph(info, new Font(getFont(), getTamanoFont(), getFormatoFont(), getColorLetra())));
    celda.setBackgroundColor(getColorFondo());
    celda.setBorderColor(getColorBorde());
    celda.setBorderWidth(getAnchoBordeCelda());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    
    celda.setColspan(getColspan());
    

    tabla.addCell(celda);
    
    setDefaultColspan();
  }
  








  public void agregarCelda(String info, Font font)
    throws Exception
  {
    letra = font;
    agregarCelda(info);
  }
  
  public void agregarCelda(Paragraph info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  








  public void agregarCelda(Paragraph info, Integer alignHorizontal)
    throws Exception
  {
    PdfPCell celda = new PdfPCell(info);
    celda.setBorderWidth(getAnchoBordeCelda());
    celda.setBorderColor(getColorBorde());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    
    celda.setColspan(getColspan());
    

    tabla.addCell(celda);
    
    setDefaultColspan();
  }
  



  public void agregarCelda(Object info)
    throws Exception
  {
    String CLASS_METHOD = "TablaPDF.agregarCelda";
    
    try
    {
      if ((info instanceof String)) {
        agregarCelda((String)info);
      } else if ((info instanceof TablaPDF)) {
        agregarCelda((TablaPDF)info);
      } else if ((info instanceof Image)) {
        agregarCelda((Image)info);
      }
    }
    catch (Exception e) {
      throw new Exception("TablaPDF.agregarCelda\\" + e.getMessage());
    }
  }
  
  public void agregarCelda(TablaPDF info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  








  public void agregarCelda(TablaPDF info, Integer alignHorizontal)
    throws Exception
  {
    PdfPCell celda = new PdfPCell(info.getTabla());
    celda.setBorderWidth(getAnchoBorde());
    celda.setBorderColor(getColorBorde());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    
    celda.setColspan(getColspan());
    

    tabla.addCell(celda);
    
    setDefaultColspan();
  }
  
  public void agregarCelda(Image info) throws Exception
  {
    Integer alignHorizontal = null;
    agregarCelda(info, alignHorizontal);
  }
  








  public void agregarCelda(Image info, Integer alignHorizontal)
    throws Exception
  {
    PdfPCell celda = new PdfPCell(info);
    celda.setBorderWidth(getAnchoBorde());
    celda.setBorderColor(getColorBorde());
    if (alignHorizontal == null) {
      celda.setHorizontalAlignment(getAlineacionHorizontal());
    } else
      celda.setHorizontalAlignment(alignHorizontal.intValue());
    celda.setVerticalAlignment(getAlineacionVertical());
    
    celda.setColspan(getColspan());
    

    tabla.addCell(celda);
    
    setDefaultColspan();
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
    catch (Exception e) {
      throw new Exception("TablaPDF.agregarFila\\" + e.getMessage());
    }
  }
  
  public void endTabla(PdfWriter writer, Document document)
  {
    writer.getDirectContent();
    

    tabla.setTotalWidth(document.right() - document.left());
  }
}
