package com.visiongc.commons.report;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.visiongc.commons.report.util.ImagenPDFUtil;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.model.ObjetoBinario;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.struts.util.MessageResources;








public class VgcPdfPageEventHandler
  extends PdfPageEventHelper
{
  protected Font fuenteEncabezadoPiePagina;
  protected MessageResources messageResources;
  protected VgcReportBuilderPDF reportBuilder;
  protected ConfiguracionPagina configuracionPagina;
  protected Image headerImage;
  protected PdfGState gstate;
  protected PdfTemplate totalPaginas;
  protected BaseFont fuenteBase;
  private String macroD;
  private String macroL;
  private String macroT;
  private String macroY;
  private String macroM;
  private boolean hayEncabezadoIzquierdo = false;
  private boolean hayEncabezadoCentro = false;
  private boolean hayEncabezadoDerecho = false;
  private boolean hayPiePaginaIzquierdo = false;
  private boolean hayPiePaginaCentro = false;
  private boolean hayPiePaginaDerecho = false;
  private float topeDocumento = 0.0F;
  private float pieDocumento = 0.0F;
  
  public float getPieDocumento()
  {
    return pieDocumento;
  }
  
  public void setPieDocumento(float pieDocumento)
  {
    this.pieDocumento = pieDocumento;
  }
  
  public float getTopeDocumento()
  {
    return topeDocumento;
  }
  
  public void setTopeDocumento(float topeDocumento)
  {
    this.topeDocumento = topeDocumento;
  }
  
  public VgcPdfPageEventHandler(MessageResources messageResources, VgcReportBuilderPDF reportBuilder)
  {
    this.messageResources = messageResources;
    this.reportBuilder = reportBuilder;
  }
  



  public void onOpenDocument(PdfWriter writer, Document document)
  {
    if (configuracionPagina != null)
    {
      fuenteEncabezadoPiePagina = new Font(configuracionPagina.getCodigoFuente());
      fuenteEncabezadoPiePagina.setSize(configuracionPagina.getTamanoFuente().floatValue());
    }
    else
    {
      fuenteEncabezadoPiePagina = new Font(2);
      fuenteEncabezadoPiePagina.setSize(10.0F);
    }
    
    totalPaginas = writer.getDirectContent().createTemplate(100.0F, 100.0F);
    totalPaginas.setBoundingBox(new Rectangle(-20.0F, -20.0F, 100.0F, 100.0F));
    

    Date fechaAhora = new Date();
    
    String formatoFecha = VgcResourceManager.getResourceApp("formato.fecha.corta");
    if (formatoFecha == null) {
      throw new RuntimeException("No está configurado el Recurso de Lenguaje de Formato de Fecha");
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha);
    
    macroD = dateFormat.format(fechaAhora);
    

    formatoFecha = VgcResourceManager.getResourceApp("formato.fecha.larga");
    if (formatoFecha == null) {
      throw new RuntimeException("No está configurado el Recurso de Lenguaje de Formato de Fecha");
    }
    dateFormat = new SimpleDateFormat(formatoFecha);
    
    macroL = dateFormat.format(fechaAhora);
    

    formatoFecha = VgcResourceManager.getResourceApp("formato.hora.corta");
    if (formatoFecha == null) {
      throw new RuntimeException("No está configurado el Recurso de Lenguaje de Formato de Fecha");
    }
    dateFormat = new SimpleDateFormat(formatoFecha);
    
    macroT = dateFormat.format(fechaAhora);
    

    Calendar calendarAhora = Calendar.getInstance();
    macroY = Integer.toString(calendarAhora.get(1));
    

    macroM = Integer.toString(calendarAhora.get(2));
    
    if ((configuracionPagina.getEncabezadoIzquierdo() != null) && (!configuracionPagina.getEncabezadoIzquierdo().equals("")))
    {
      hayEncabezadoIzquierdo = true;
      configuracionPagina.setEncabezadoIzquierdo(prepararEncabezadoPiePagina(configuracionPagina.getEncabezadoIzquierdo()));
    }
    
    if ((configuracionPagina.getEncabezadoCentro() != null) && (!configuracionPagina.getEncabezadoCentro().equals("")))
    {
      hayEncabezadoCentro = true;
      configuracionPagina.setEncabezadoCentro(prepararEncabezadoPiePagina(configuracionPagina.getEncabezadoCentro()));
    }
    
    if ((configuracionPagina.getEncabezadoDerecho() != null) && (!configuracionPagina.getEncabezadoDerecho().equals("")))
    {
      hayEncabezadoDerecho = true;
      configuracionPagina.setEncabezadoDerecho(prepararEncabezadoPiePagina(configuracionPagina.getEncabezadoDerecho()));
    }
    
    if ((configuracionPagina.getPiePaginaIzquierdo() != null) && (!configuracionPagina.getPiePaginaIzquierdo().equals("")))
    {
      hayPiePaginaIzquierdo = true;
      configuracionPagina.setPiePaginaIzquierdo(prepararEncabezadoPiePagina(configuracionPagina.getPiePaginaIzquierdo()));
    }
    
    if ((configuracionPagina.getPiePaginaCentro() != null) && (!configuracionPagina.getPiePaginaCentro().equals("")))
    {
      hayPiePaginaCentro = true;
      configuracionPagina.setPiePaginaCentro(prepararEncabezadoPiePagina(configuracionPagina.getPiePaginaCentro()));
    }
    
    if ((configuracionPagina.getPiePaginaDerecho() != null) && (!configuracionPagina.getPiePaginaDerecho().equals("")))
    {
      hayPiePaginaDerecho = true;
      configuracionPagina.setPiePaginaDerecho(prepararEncabezadoPiePagina(configuracionPagina.getPiePaginaDerecho()));
    }
    
    try
    {
      if (configuracionPagina != null) {
        fuenteBase = BaseFont.createFont(configuracionPagina.getNombreFuente(), "Cp1252", false);
      } else {
        fuenteBase = BaseFont.createFont("Times-Roman", "Cp1252", false);
      }
    } catch (Exception e) {
      throw new ExceptionConverter(e);
    }
    
    reportBuilder.onOpenDocument(writer, document);
  }
  
  private String prepararEncabezadoPiePagina(String encabezadoPiePagina)
  {
    encabezadoPiePagina = encabezadoPiePagina.replaceAll("&D", macroD);
    encabezadoPiePagina = encabezadoPiePagina.replaceAll("&L", macroL);
    encabezadoPiePagina = encabezadoPiePagina.replaceAll("&T", macroT);
    encabezadoPiePagina = encabezadoPiePagina.replaceAll("&Y", macroY);
    encabezadoPiePagina = encabezadoPiePagina.replaceAll("&M", macroM);
    return encabezadoPiePagina;
  }
  
  public void onCloseDocument(PdfWriter writer, Document document)
  {
    totalPaginas.beginText();
    totalPaginas.setFontAndSize(fuenteBase, 12.0F);
    totalPaginas.setTextMatrix(0.0F, 0.0F);
    totalPaginas.showText(String.valueOf(writer.getPageNumber() - 1));
    totalPaginas.endText();
    reportBuilder.onCloseDocument(writer, document);
  }
  
  public void onChapter(PdfWriter writer, Document document, float coordY, Paragraph paragraph)
  {
    reportBuilder.onChapter(writer, document, coordY, paragraph);
  }
  
  public void onChapterEnd(PdfWriter writer, Document document, float coordY)
  {
    reportBuilder.onChapterEnd(writer, document, coordY);
  }
  
  public void onGenericTag(PdfWriter writer, Document document, Rectangle r, String s)
  {
    reportBuilder.onGenericTag(writer, document, r, s);
  }
  
  public void onParagraph(PdfWriter writer, Document document, float coordY)
  {
    reportBuilder.onParagraph(writer, document, coordY);
  }
  
  public void onParagraphEnd(PdfWriter writer, Document document, float coordY)
  {
    reportBuilder.onParagraphEnd(writer, document, coordY);
  }
  
  public void onSection(PdfWriter writer, Document document, float coordY, int i, Paragraph paragraph)
  {
    reportBuilder.onSection(writer, document, coordY, i, paragraph);
  }
  
  public void onSectionEnd(PdfWriter writer, Document document, float coordY)
  {
    reportBuilder.onSectionEnd(writer, document, coordY);
  }
  
  public void onStartPage(PdfWriter writer, Document document)
  {
    PdfContentByte cb = writer.getDirectContent();
    cb.saveState();
    
    String macroP = messageResources.getMessage("action.reportebasico.pagina") + " " + writer.getPageNumber();
    
    Chunk chunk = null;
    Phrase frase = null;
    float ajustePorImagenSuperior = 0.0F;
    float ajustePorImagenInferior = 0.0F;
    

    try
    {
      if (configuracionPagina.getImagenSupIzqId() != null)
      {
        headerImage = Image.getInstance(getConfiguracionPagina().getImagenSupIzq().getData());
        ImagenPDFUtil.setEscalaImagen(headerImage);
        headerImage.setAbsolutePosition(document.leftMargin(), topeDocumento - ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage));
        document.add(headerImage);
        ajustePorImagenSuperior = ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage);
      }
      if (configuracionPagina.getImagenSupCenId() != null)
      {
        headerImage = Image.getInstance(getConfiguracionPagina().getImagenSupCen().getData());
        ImagenPDFUtil.setEscalaImagen(headerImage);
        headerImage.setAbsolutePosition(document.leftMargin() + (document.right() - document.left()) / 2.0F - ImagenPDFUtil.getAnchoAjustadoPorEscala(headerImage) / 2.0F, topeDocumento - ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage));
        document.add(headerImage);
        if (headerImage.height() > ajustePorImagenSuperior)
          ajustePorImagenSuperior = ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage);
      }
      if (configuracionPagina.getImagenSupDerId() != null)
      {
        headerImage = Image.getInstance(getConfiguracionPagina().getImagenSupDer().getData());
        ImagenPDFUtil.setEscalaImagen(headerImage);
        headerImage.setAbsolutePosition(document.leftMargin() + (document.right() - document.left()) - ImagenPDFUtil.getAnchoAjustadoPorEscala(headerImage), topeDocumento - ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage));
        document.add(headerImage);
        if (headerImage.height() > ajustePorImagenSuperior)
          ajustePorImagenSuperior = ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage);
      }
      if (ajustePorImagenSuperior > 0.0F)
        ajustePorImagenSuperior += 15.0F;
      if (configuracionPagina.getImagenInfIzqId() != null)
      {
        headerImage = Image.getInstance(getConfiguracionPagina().getImagenInfIzq().getData());
        ImagenPDFUtil.setEscalaImagen(headerImage);
        headerImage.setAbsolutePosition(document.leftMargin(), pieDocumento);
        document.add(headerImage);
        ajustePorImagenInferior = ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage);
      }
      if (configuracionPagina.getImagenInfCenId() != null)
      {
        headerImage = Image.getInstance(getConfiguracionPagina().getImagenInfCen().getData());
        ImagenPDFUtil.setEscalaImagen(headerImage);
        headerImage.setAbsolutePosition(document.leftMargin() + (document.right() - document.left()) / 2.0F - ImagenPDFUtil.getAnchoAjustadoPorEscala(headerImage) / 2.0F, pieDocumento);
        document.add(headerImage);
        if (headerImage.height() > ajustePorImagenInferior)
          ajustePorImagenInferior = ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage);
      }
      if (configuracionPagina.getImagenInfDerId() != null)
      {
        headerImage = Image.getInstance(getConfiguracionPagina().getImagenInfDer().getData());
        ImagenPDFUtil.setEscalaImagen(headerImage);
        headerImage.setAbsolutePosition(document.leftMargin() + (document.right() - document.left()) - ImagenPDFUtil.getAnchoAjustadoPorEscala(headerImage), pieDocumento);
        document.add(headerImage);
        if (headerImage.height() > ajustePorImagenInferior) {
          ajustePorImagenInferior = ImagenPDFUtil.getAltoAjustadoPorEscala(headerImage);
        }
      }
      if (ajustePorImagenInferior > 0.0F) {
        ajustePorImagenInferior += 15.0F;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (hayEncabezadoIzquierdo)
    {
      chunk = new Chunk(configuracionPagina.getEncabezadoIzquierdo().replaceAll("&P", macroP), fuenteEncabezadoPiePagina);
      frase = new Phrase(chunk);
      ColumnText.showTextAligned(cb, 0, frase, document.leftMargin(), topeDocumento - ajustePorImagenSuperior - configuracionPagina.getAjustePorTextoEncabezado(), 0.0F);
    }
    
    if (hayEncabezadoCentro)
    {
      chunk = new Chunk(configuracionPagina.getEncabezadoCentro().replaceAll("&P", macroP), fuenteEncabezadoPiePagina);
      frase = new Phrase(chunk);
      ColumnText.showTextAligned(cb, 0, frase, document.leftMargin() + (document.right() - document.left()) / 2.0F - chunk.getWidthPoint() / 2.0F, topeDocumento - ajustePorImagenSuperior - configuracionPagina.getAjustePorTextoEncabezado(), 0.0F);
    }
    
    if (hayEncabezadoDerecho)
    {
      chunk = new Chunk(configuracionPagina.getEncabezadoDerecho().replaceAll("&P", macroP), fuenteEncabezadoPiePagina);
      frase = new Phrase(chunk);
      ColumnText.showTextAligned(cb, 0, frase, document.leftMargin() + (document.right() - document.left()) - chunk.getWidthPoint(), topeDocumento - ajustePorImagenSuperior - configuracionPagina.getAjustePorTextoEncabezado(), 0.0F);
    }
    
    if (hayPiePaginaIzquierdo)
    {
      chunk = new Chunk(configuracionPagina.getPiePaginaIzquierdo().replaceAll("&P", macroP), fuenteEncabezadoPiePagina);
      frase = new Phrase(chunk);
      ColumnText.showTextAligned(cb, 0, frase, document.leftMargin(), pieDocumento + ajustePorImagenInferior, 0.0F);
    }
    
    if (hayPiePaginaCentro)
    {
      chunk = new Chunk(configuracionPagina.getPiePaginaCentro().replaceAll("&P", macroP), fuenteEncabezadoPiePagina);
      frase = new Phrase(chunk);
      ColumnText.showTextAligned(cb, 0, frase, document.leftMargin() + (document.right() - document.left()) / 2.0F - chunk.getWidthPoint() / 2.0F, pieDocumento + ajustePorImagenInferior, 0.0F);
    }
    
    if (hayPiePaginaDerecho)
    {
      chunk = new Chunk(configuracionPagina.getPiePaginaDerecho().replaceAll("&P", macroP), fuenteEncabezadoPiePagina);
      frase = new Phrase(chunk);
      ColumnText.showTextAligned(cb, 0, frase, document.leftMargin() + (document.right() - document.left()) - chunk.getWidthPoint(), pieDocumento + ajustePorImagenInferior, 0.0F);
    }
    
    cb.restoreState();
    reportBuilder.onStartPage(writer, document);
  }
  
  public void onEndPage(PdfWriter writer, Document document)
  {
    PdfContentByte cb = writer.getDirectContent();
    cb.saveState();
    
    reportBuilder.onEndTable(writer, document);
    

    String text = "Page " + writer.getPageNumber() + " of ";
    float textSize = fuenteBase.getWidthPoint(text, 12.0F);
    float textBase = document.bottom() - 20.0F;
    cb.beginText();
    cb.setFontAndSize(fuenteBase, 12.0F);
    

    if ((writer.getPageNumber() & 0x1) == 1)
    {
      cb.setTextMatrix(document.left(), textBase);
      cb.showText(text);
      cb.endText();
      cb.addTemplate(totalPaginas, document.left() + textSize, textBase);
    }
    else
    {
      float adjust = fuenteBase.getWidthPoint("0", 12.0F);
      cb.setTextMatrix(document.right() - textSize - adjust, textBase);
      cb.showText(text);
      cb.endText();
      cb.addTemplate(totalPaginas, document.right() - adjust, textBase);
    }
    
    cb.saveState();
    







    cb.restoreState();
    
    reportBuilder.onEndPage(writer, document);
  }
  
  public ConfiguracionPagina getConfiguracionPagina()
  {
    return configuracionPagina;
  }
  
  public void setConfiguracionPagina(ConfiguracionPagina configuracionPagina)
  {
    this.configuracionPagina = configuracionPagina;
  }
}
