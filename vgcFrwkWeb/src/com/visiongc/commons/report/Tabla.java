package com.visiongc.commons.report;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;

public abstract interface Tabla
{
  public static final int FORMATO_BOLD = 1;
  public static final int FORMATO_ITALIC = 2;
  public static final int FORMATO_NORMAL = 0;
  public static final int FORMATO_UNDERLINE = 4;
  public static final int DEFAULT_FONT_SIZE = 8;
  public static final int DEFAULT_BORDER_WIDTH = 1;
  public static final int DEFAULT_CELL_BORDER_WIDTH = 1;
  public static final int DEFAULT_CELLPADDING = 0;
  public static final int DEFAULT_CELLSPACINGHTML = 1;
  public static final int DEFAULT_CELLSPACINGPDF = 0;
  public static final int DEFAULT_ROWSPAN = 1;
  public static final int DEFAULT_COLSPAN = 1;
  public static final int DEFAULT_AMPLITUD_TABLA = 100;
  public static final int DEFAULT_HEADER_ROWS = 1;
  public static final int V_ALINEACION_TOP = 4;
  public static final int V_ALINEACION_MIDDLE = 5;
  public static final int V_ALINEACION_BOTTOM = 6;
  public static final int H_ALINEACION_LEFT = 0;
  public static final int H_ALINEACION_CENTER = 1;
  public static final int H_ALINEACION_RIGHT = 2;
  public static final int H_ALINEACION_JUSTIFIED = 3;
  public static final int HEADER_ROWS = 1;
  
  public abstract void inicializarArreglo(Object[] paramArrayOfObject);
  
  public abstract void insertarLineas(Document paramDocument, int paramInt)
    throws Exception;
  
  public abstract void setDefaultAmplitudTabla();
  
  public abstract void setDefaultRowspan();
  
  public abstract void setDefaultColspan();
  
  public abstract void setDefaultCellspacingHtml();
  
  public abstract void setDefaultCellspacingPdf();
  
  public abstract void setDefaultCellpadding();
  
  public abstract void setDefaultAnchoBorde();
  
  public abstract void setDefaultFormatoFont();
  
  public abstract void setDefaultTamanoFont();
  
  public abstract void setDefaultColorBorde();
  
  public abstract void setDefaultAlineacionVertical();
  
  public abstract void setDefaultAlineacionHorizontal();
  
  public abstract void setFont(int paramInt);
  
  public abstract int getFont();
  
  public abstract void setTamanoFont(float paramFloat);
  
  public abstract float getTamanoFont();
  
  public abstract void setFormatoFont(int paramInt);
  
  public abstract int getFormatoFont();
  
  public abstract void setColorBorde(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Color getColorBorde();
  
  public abstract void setColorFondo(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Color getColorFondo();
  
  public abstract void setColorLetra(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Color getColorLetra();
  
  public abstract void setAnchoBorde(int paramInt);
  
  public abstract int getAnchoBorde();
  
  public abstract void setAlineacionHorizontal(int paramInt);
  
  public abstract int getAlineacionHorizontal();
  
  public abstract void setAlineacionVertical(int paramInt);
  
  public abstract int getAlineacionVertical();
  
  public abstract void setAmplitudTabla(int paramInt);
  
  public abstract int getAmplitudTabla();
  
  public abstract void setRowspan(int paramInt);
  
  public abstract int getRowspan();
  
  public abstract void setColspan(int paramInt);
  
  public abstract int getColspan();
  
  public abstract void setCellspacing(int paramInt);
  
  public abstract int getCellspacing();
  
  public abstract void setCellpadding(int paramInt);
  
  public abstract int getCellpadding();
  
  public abstract int getAnchoBordeCelda();
  
  public abstract void setAnchoBordeCelda(int paramInt);
  
  public abstract Font getLetra();
  
  public abstract void setLetra(Font paramFont);
  
  public abstract void crearTabla(int[] paramArrayOfInt)
    throws Exception;
  
  public abstract void encabezadoTabla(Font paramFont, boolean paramBoolean, Document paramDocument, int[] paramArrayOfInt, String[] paramArrayOfString, String paramString1, String paramString2)
    throws Exception;
  
  public abstract Object getTabla();
  
  public abstract void agregarCelda(String paramString)
    throws Exception;
  
  public abstract void agregarCelda(String paramString, Integer paramInteger)
    throws Exception;
  
  public abstract void agregarCelda(String paramString, Font paramFont)
    throws Exception;
  
  public abstract void agregarCelda(Paragraph paramParagraph)
    throws Exception;
  
  public abstract void agregarCelda(Paragraph paramParagraph, Integer paramInteger)
    throws Exception;
  
  public abstract void agregarCelda(Object paramObject)
    throws Exception;
  
  public abstract void agregarCelda(Image paramImage)
    throws Exception;
  
  public abstract void agregarCelda(Image paramImage, Integer paramInteger)
    throws Exception;
  
  public abstract void agregarFila(Object[] paramArrayOfObject)
    throws Exception;
  
  public abstract void endTabla(PdfWriter paramPdfWriter, Document paramDocument);
}
