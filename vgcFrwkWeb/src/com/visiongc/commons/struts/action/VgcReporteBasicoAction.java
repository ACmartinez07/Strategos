package com.visiongc.commons.struts.action;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.xml.XmlWriter;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcPdfPageEventHandler;
import com.visiongc.commons.report.VgcReportBuilderPDF;
import com.visiongc.commons.struts.action.util.ByteArrayStreamInfo;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.VgcConfiguracionPorUsuario;
import com.visiongc.framework.configuracion.VgcConfiguracionesBase;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ObjetoBinario;
import com.visiongc.framework.model.Usuario;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.util.MessageResources;



public abstract class VgcReporteBasicoAction
  extends DownloadAction
  implements PdfPageEvent, VgcReportBuilderPDF
{
  protected PdfWriter pdfWriter = null;
  protected HtmlWriter htmlWriter = null;
  protected XmlWriter xmlWriter = null;
  protected ConfiguracionPagina configuracionPagina = null;
  
  private int linea = 0;
  private Boolean starPage = Boolean.valueOf(false);
  
  private String tipoPresentacion;
  public TablaBasicaPDF tablaHeader = null;
  public Image headerImage = null;
  public PdfGState gstate = null;
  public PdfTemplate totalPaginas = null;
  public BaseFont fuenteBase = null;
  public Tabla tabla = null;
  public Float paginaAlto = null;
  public Float paginaAncho = null;
  
  public VgcReporteBasicoAction() {}
  
  protected final DownloadAction.StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String contentType = "application/pdf";
    
    Document documento = new Document();
    tablaHeader = null;
    
    byte[] myPdfBytes = (byte[])null;
    ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
    

    String nombreConfiguracionBase = getResources(request).getMessage("aplicacion.configuracionbase.clase");
    nombreConfiguracionBase = StringUtil.trim(nombreConfiguracionBase);
    VgcConfiguracionesBase configuracionBase = VgcConfiguracionPorUsuario.getInstance("com.visiongc.framework.configuracion.FrameworkConfiguracionesBase").getConfiguracionesBase();
    configuracionPagina = ((ConfiguracionPagina)configuracionBase.getObjetoConfiguracionBase("configuracionPagina"));
    configuracionPagina.setAjustePorTextoEncabezado(configuracionPagina.getTamanoFuente().floatValue());
    configuracionPagina.setAjustePorTextoPiePagina(configuracionPagina.getTamanoFuente().floatValue());
    if (nombreConfiguracionBase != null)
    {

      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      
      ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId(), nombreConfiguracionBase, "configuracionPagina");
      
      if (configuracionUsuario != null) {
        configuracionPagina.readFromXml(configuracionUsuario.getData());
      }
      frameworkService.close();
    }
    
    request.setAttribute("configuracionPagina", configuracionPagina);
    configuracionPagina.loadImagenes(true);
    
    tipoPresentacion = request.getParameter("tipoPresentacion");
    if (tipoPresentacion == null) {
      tipoPresentacion = "pdf";
    }
    VgcPdfPageEventHandler eventHandler = null;
    
    if ("PDF".equalsIgnoreCase(tipoPresentacion))
    {
      pdfWriter = PdfWriter.getInstance(documento, baosPDF);
      eventHandler = new VgcPdfPageEventHandler(getResources(request), this);
      eventHandler.setConfiguracionPagina(configuracionPagina);
      pdfWriter.setPageEvent(eventHandler);
    }
    else if ("HTML".equalsIgnoreCase(tipoPresentacion))
    {
      contentType = "text/html";
      htmlWriter = HtmlWriter.getInstance(documento, baosPDF);

    }
    else if ("XML".equalsIgnoreCase(tipoPresentacion))
    {
      contentType = "text/xml";
      xmlWriter = XmlWriter.getInstance(documento, baosPDF);
    }
    else
    {
      pdfWriter = PdfWriter.getInstance(documento, baosPDF);
      pdfWriter.setPageEvent(new VgcPdfPageEventHandler(getResources(request), this));
    }
    
    documento.addAuthor(getClass().getName());
    documento.addCreationDate();
    documento.addProducer();
    documento.addCreator(getClass().getName());
    
    if (request.getParameter("orientacion") != null) {
      documento.setPageSize(PageSize.LETTER.rotate());
    } else {
      documento.setPageSize(configurarTamanoPagina());
    }
    float[] margenes = new float[4];
    configurarMargenes(margenes);
    if (margenes[0] < 0.0F)
    {
      margenes[0] = (configuracionPagina.getTamanoMargenIzquierdo().floatValue() * 28.34F);
      margenes[1] = (configuracionPagina.getTamanoMargenDerecho().floatValue() * 28.34F);
      margenes[2] = (configuracionPagina.getTamanoMargenSuperior().floatValue() * 28.34F);
      margenes[3] = (configuracionPagina.getTamanoMargenInferior().floatValue() * 28.34F);
    }
    
    documento.setMargins(margenes[0], margenes[1], margenes[2], margenes[3]);
    
    if ("PDF".equalsIgnoreCase(tipoPresentacion))
    {
      eventHandler.setTopeDocumento(documento.top());
      eventHandler.setPieDocumento(documento.bottom());
      ajustarMargenes(configuracionPagina, documento);
    }
    

    Float alto = null;
    Float ancho = null;
    if (documento.getPageSize() == PageSize.LETTER)
    {
      String orientacion = request.getParameter("orientacion");
      
      if ((orientacion != null) && (orientacion.equals("H")))
      {
        alto = Float.valueOf(816.0F);
        ancho = Float.valueOf(1056.0F);
      }
      else
      {
        alto = Float.valueOf(1056.0F);
        ancho = Float.valueOf(816.0F);
      }
    }
    else
    {
      alto = Float.valueOf(documento.getPageSize().height());
      ancho = Float.valueOf(documento.getPageSize().width());
    }
    
    paginaAlto = alto;
    paginaAncho = ancho;
    

    MessageResources mensajes = getResources(request);
    
    documento.open();
    
    String titulo = agregarTitulo(request, mensajes);
    if (titulo != null)
      imprimirTitulo(titulo, documento);
    starPage = Boolean.valueOf(false);
    
    construirReporte(form, request, response, documento);
    
    if (documento != null)
      documento.close();
    if (pdfWriter != null)
      pdfWriter.close();
    if (htmlWriter != null)
      htmlWriter.close();
    if (xmlWriter != null) {
      xmlWriter.close();
    }
    myPdfBytes = baosPDF.toByteArray();
    
    return new ByteArrayStreamInfo(contentType, myPdfBytes);
  }
  
  protected Rectangle configurarTamanoPagina() throws Exception
  {
    return PageSize.LETTER;
  }
  
  protected void configurarMargenes(float[] margenes) throws Exception
  {
    margenes[0] = -1.0F;
    margenes[1] = -1.0F;
    margenes[2] = -1.0F;
    margenes[3] = -1.0F;
  }
  
  public int lineasxPagina(com.lowagie.text.Font font)
  {
    int totalLineas = 0;
    
    BufferedImage img = new BufferedImage(1, 1, 2);
    java.awt.Font fontNormal = new java.awt.Font(font.getFamilyname(), font.style(), Float.valueOf(font.size()).intValue());
    FontMetrics metrics = img.getGraphics().getFontMetrics(fontNormal);
    int alto = metrics.getHeight();
    totalLineas = paginaAlto.intValue() / alto;
    
    return totalLineas;
  }
  
  public int anchoLetra(com.lowagie.text.Font font)
  {
    BufferedImage img = new BufferedImage(1, 1, 2);
    java.awt.Font fontNormal = new java.awt.Font(font.getFamilyname(), font.style(), Float.valueOf(font.size()).intValue());
    FontMetrics metrics = img.getGraphics().getFontMetrics(fontNormal);
    
    return metrics.stringWidth("A");
  }
  



  public void onOpenDocument(PdfWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  



  public void onStartPage(PdfWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  



  public void onEndPage(PdfWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  



  public void onCloseDocument(PdfWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  
  public void onParagraph(PdfWriter writer, Document document, float coordY)
  {
    linea += 1;
  }
  

  public void onParagraphEnd(PdfWriter writer, Document document, float coordY) {}
  

  public void onChapter(PdfWriter writer, Document document, float coordY, Paragraph paragraph)
  {
    linea += 1;
  }
  

  public void onChapterEnd(PdfWriter writer, Document document, float coordY) {}
  

  public void onSection(PdfWriter writer, Document document, float coordY, int i, Paragraph paragraph)
  {
    linea += 1;
  }
  


  public void onSectionEnd(PdfWriter writer, Document document, float coordY) {}
  


  public void onGenericTag(PdfWriter writer, Document document, Rectangle r, String s) {}
  

  public void onOpenDocument(XmlWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  
  public void onStartPage(XmlWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  
  public void onEndPage(XmlWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  
  public void onCloseDocument(XmlWriter writer, Document document)
  {
    linea = 0;
    starPage = Boolean.valueOf(true);
  }
  


  public void onParagraph(XmlWriter writer, Document document, float coordY) {}
  


  public void onParagraphEnd(XmlWriter writer, Document document, float coordY) {}
  


  public void onChapter(XmlWriter writer, Document document, float coordY, Paragraph paragraph) {}
  


  public void onChapterEnd(XmlWriter writer, Document document, float coordY) {}
  


  public void onSection(XmlWriter writer, Document document, float coordY, int i, Paragraph paragraph) {}
  


  public void onSectionEnd(XmlWriter writer, Document document, float coordY) {}
  

  public void onGenericTag(XmlWriter writer, Document document, Rectangle r, String s) {}
  

  public ConfiguracionPagina getConfiguracionPagina(HttpServletRequest request)
  {
    return (ConfiguracionPagina)request.getAttribute("configuracionPagina");
  }
  
  public int getNumeroLinea(int lineaPagina, int lineaInicial)
  {
    if ((linea == 0) || (starPage.booleanValue()))
    {
      starPage = Boolean.valueOf(false);
      return lineaInicial;
    }
    
    return lineaPagina + 1;
  }
  
  protected abstract void construirReporte(ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Document paramDocument) throws Exception;
  
  protected abstract String agregarTitulo(HttpServletRequest paramHttpServletRequest, MessageResources paramMessageResources) throws Exception;
  
  protected Element lineaEnBlanco(com.lowagie.text.Font font)
  {
    return new Paragraph(" ", font);
  }
  
  protected void agregarSubTitulo(Document documento, ConfiguracionPagina configuracionPagina, String subtitulo) throws Exception
  {
    agregarSubTitulo(documento, configuracionPagina, subtitulo, false);
  }
  
  protected void agregarSubTitulo(Document documento, ConfiguracionPagina configuracionPagina, String subtitulo, boolean ajustarParaTabla) throws Exception
  {
    agregarSubTitulo(documento, configuracionPagina, subtitulo, ajustarParaTabla, false);
  }
  
  protected void agregarSubTitulo(Document documento, ConfiguracionPagina configuracionPagina, String subtitulo, boolean ajustarParaTabla, boolean centrarTitulo) throws Exception
  {
    agregarSubTitulo(documento, configuracionPagina, subtitulo, ajustarParaTabla, centrarTitulo, null);
  }
  
  protected void agregarSubTitulo(Document documento, ConfiguracionPagina configuracionPagina, String subtitulo, boolean ajustarParaTabla, boolean centrarTitulo, Float TamanoFuente) throws Exception
  {
    com.lowagie.text.Font fuente = configuracionPagina.getFuente();
    if (TamanoFuente == null)
      TamanoFuente = Float.valueOf(14.0F);
    fuente.setSize(TamanoFuente.floatValue());
    fuente.setStyle(1);
    Paragraph texto = new Paragraph(subtitulo, fuente);
    if (!centrarTitulo) {
      texto.setAlignment(0);
    } else
      texto.setAlignment(1);
    texto.setLeading(TamanoFuente.floatValue());
    if (ajustarParaTabla) {
      texto.setSpacingAfter(TamanoFuente.floatValue() * -1.0F + 4.0F);
    }
    documento.add(texto);
  }
  
  public void ajustarMargenes(ConfiguracionPagina configuracionPagina, Document documento)
  {
    float ajustePorImagenSuperior = 0.0F;
    float ajustePorImagenInferior = 0.0F;
    float ajustePorEncabezado = 0.0F;
    float ajustePorPiePagina = 0.0F;
    

    try
    {
      if (configuracionPagina.getImagenSupIzqId() != null)
      {
        headerImage = Image.getInstance(configuracionPagina.getImagenSupIzq().getData());
        ajustePorImagenSuperior = headerImage.height();
      }
      if (configuracionPagina.getImagenSupCenId() != null)
      {
        headerImage = Image.getInstance(configuracionPagina.getImagenSupCen().getData());
        if (headerImage.height() > ajustePorImagenSuperior)
          ajustePorImagenSuperior = headerImage.height();
      }
      if (configuracionPagina.getImagenSupDerId() != null)
      {
        headerImage = Image.getInstance(configuracionPagina.getImagenSupDer().getData());
        if (headerImage.height() > ajustePorImagenSuperior)
          ajustePorImagenSuperior = headerImage.height();
      }
      if (configuracionPagina.getImagenInfIzqId() != null)
      {
        headerImage = Image.getInstance(configuracionPagina.getImagenInfIzq().getData());
        ajustePorImagenInferior = headerImage.height();
      }
      if (configuracionPagina.getImagenInfCenId() != null)
      {
        headerImage = Image.getInstance(configuracionPagina.getImagenInfCen().getData());
        if (headerImage.height() > ajustePorImagenInferior)
          ajustePorImagenInferior = headerImage.height();
      }
      if (configuracionPagina.getImagenInfDerId() != null)
      {
        headerImage = Image.getInstance(configuracionPagina.getImagenInfDer().getData());
        if (headerImage.height() > ajustePorImagenInferior) {
          ajustePorImagenInferior = headerImage.height();
        }
      }
      if (ajustePorImagenSuperior > 0.0F)
        ajustePorEncabezado = ajustePorImagenSuperior + 15.0F;
      if (configuracionPagina.tieneEncabezado())
        ajustePorEncabezado = ajustePorEncabezado + configuracionPagina.getAjustePorTextoEncabezado() + 15.0F;
      if (ajustePorImagenInferior > 0.0F)
        ajustePorPiePagina = ajustePorImagenInferior + 15.0F;
      if (configuracionPagina.tienePiePagina())
        ajustePorPiePagina = ajustePorPiePagina + configuracionPagina.getAjustePorTextoPiePagina() + 15.0F;
      documento.setMargins(documento.leftMargin(), documento.rightMargin(), documento.topMargin() + ajustePorEncabezado, documento.bottomMargin() + ajustePorPiePagina);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  protected TablaBasicaPDF saltoPaginaTabla(com.lowagie.text.Font font, boolean saltarPagina, Document documento, TablaBasicaPDF tabla, int[] columnas, String[] columnasTitulo, String titulo, String subTitulo, HttpServletRequest request) throws Exception
  {
    if (saltarPagina)
    {
      documento.add(tabla.getTabla());
      documento.newPage();
      starPage = Boolean.valueOf(false);
      linea = 1;
      
      if (titulo != null)
        imprimirTitulo(titulo, documento);
      if (subTitulo != null)
      {
        documento.add(new Paragraph(" "));
        agregarSubTitulo(documento, configuracionPagina, subTitulo, true, true, Float.valueOf(13.0F));
        documento.add(new Paragraph(" "));
      }
      else {
        documento.add(new Paragraph(" "));
      }
    }
    if (tablaHeader == null)
    {
      tabla.setAnchoBorde(0);
      tabla.setAmplitudTabla(100);
      tabla.crearTabla(columnas);
      tabla.setFormatoFont(font.style());
      tabla.setAlineacionHorizontal(1);
      tabla.setTamanoFont(8.0F);
      tabla.setCellpadding(0);
      tabla.setColorLetra(255, 255, 255);
      tabla.setColorFondo(67, 67, 67);
      tabla.setColorBorde(120, 114, 77);
      tabla.setColspan(1);
      
      for (int k = 0; k < columnasTitulo.length; k++) {
        tabla.agregarCelda(columnasTitulo[k]);
      }
      tabla.setDefaultColorFondo();
      tabla.setDefaultColorLetra();
    }
    else {
      tabla = inicializarTabla(font, tabla.getTabla().columns(), request);
    }
    return tabla;
  }
  
  protected TablaBasicaPDF saltarPagina(Document documento, boolean iniciatizarTabla, com.lowagie.text.Font font, Integer totalColumnas, String titulo, HttpServletRequest request) throws Exception
  {
    TablaBasicaPDF tabla = null;
    Float tamanoFont = Float.valueOf(font.size());
    documento.newPage();
    starPage = Boolean.valueOf(false);
    linea = 1;
    font.setSize(tamanoFont.floatValue());
    if (titulo != null) {
      imprimirTitulo(titulo, documento);
    }
    if (iniciatizarTabla) {
      tabla = inicializarTabla(font, totalColumnas.intValue(), request);
    }
    return tabla;
  }
  
  protected TablaBasicaPDF inicializarTabla(com.lowagie.text.Font font, int totalColumnas, HttpServletRequest request) throws Exception
  {
    TablaBasicaPDF tabla = null;
    
    if (tablaHeader == null)
    {
      int[] columnas = new int[totalColumnas];
      columnas[0] = 8;
      for (int f = 0; f < columnas.length; f++) {
        columnas[f] = 8;
      }
      tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
      tabla.setAmplitudTabla(100);
      tabla.crearTabla(columnas);
      tabla.setAlineacionHorizontal(1);
      tabla.setAlineacionVertical(5);
      tabla.setTamanoFont((int)font.size());
    }
    else
    {
      int[] cols = new int[tablaHeader.getTamanoCol().length];
      for (int f = 0; f < tablaHeader.getTamanoCol().length; f++) {
        cols[f] = tablaHeader.getTamanoCol()[f].intValue();
      }
      tabla = new TablaBasicaPDF(Integer.valueOf(tablaHeader.getAnchoBorde()), Integer.valueOf(tablaHeader.getAnchoBordeCelda()), getConfiguracionPagina(request), request);
      tabla.setAmplitudTabla(tablaHeader.getAmplitudTabla());
      tabla.crearTabla(cols);
      tabla.setAlineacionHorizontal(tablaHeader.getAlineacionHorizontal());
      tabla.setAlineacionVertical(tablaHeader.getAlineacionVertical());
      tabla.setTamanoFont(tablaHeader.getTamanoFont());
      

      tabla.setFont(tablaHeader.getFont());
      tabla.setFormatoFont(tablaHeader.getFormatoFont());
      tabla.setColorLetra(tablaHeader.getColorLetra().getRed(), tablaHeader.getColorLetra().getGreen(), tablaHeader.getColorLetra().getBlue());
      tabla.setColorFondo(tablaHeader.getColorFondo().getRed(), tablaHeader.getColorFondo().getGreen(), tablaHeader.getColorFondo().getBlue());
      
      for (int f = 0; f < tablaHeader.getTamanoCol().length; f++) {
        tabla.agregarCelda(tablaHeader.getTituloCol()[f]);
      }
    }
    return tabla;
  }
  
  protected TablaBasicaPDF inicializarTabla(com.lowagie.text.Font font, int[] columnas, Integer anchoBorde, Integer anchoBordeCelda, HttpServletRequest request) throws Exception
  {
    TablaBasicaPDF tabla = null;
    
    tabla = new TablaBasicaPDF(anchoBorde, anchoBordeCelda, getConfiguracionPagina(request), request);
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);
    tabla.setAlineacionHorizontal(1);
    tabla.setAlineacionVertical(5);
    tabla.setTamanoFont((int)font.size());
    
    return tabla;
  }
  
  protected TablaBasicaPDF inicializarTabla(com.lowagie.text.Font font, String[][] columnas, Integer anchoBorde, Integer anchoBordeCelda, Boolean bold, Color colorLetra, Color colorFondo, Integer alineacionHorizontal, Integer alineacionVertical, HttpServletRequest request) throws Exception
  {
    TablaBasicaPDF tabla = null;
    
    if (tablaHeader == null)
    {
      int[] cols = new int[columnas.length];
      Integer[] colms = new Integer[columnas.length];
      String[] colNames = new String[columnas.length];
      for (int f = 0; f < columnas.length; f++)
      {
        cols[f] = Integer.parseInt(columnas[f][0]);
        colms[f] = Integer.valueOf(Integer.parseInt(columnas[f][0]));
        colNames[f] = columnas[f][1];
      }
      
      tablaHeader = new TablaBasicaPDF(anchoBorde, anchoBordeCelda, getConfiguracionPagina(request), request);
      tablaHeader.setAmplitudTabla(100);
      tablaHeader.setTamanoCol(colms);
      tablaHeader.setTituloCol(colNames);
      tablaHeader.crearTabla(cols);
      

      tablaHeader.setAlineacionHorizontal(alineacionHorizontal == null ? 1 : alineacionHorizontal.intValue());
      tablaHeader.setAlineacionVertical(alineacionVertical == null ? 5 : alineacionVertical.intValue());
      tablaHeader.setTamanoFont((int)font.size());
      tablaHeader.setFont(bold.booleanValue() ? 1 : 0);
      tablaHeader.setFormatoFont(bold.booleanValue() ? 1 : 0);
      tablaHeader.setColorLetra(colorLetra.getRed(), colorLetra.getGreen(), colorLetra.getBlue());
      tablaHeader.setColorFondo(colorFondo.getRed(), colorFondo.getGreen(), colorFondo.getBlue());
      for (int f = 0; f < columnas.length; f++) {
        tablaHeader.agregarCelda(columnas[f][1]);
      }
      tabla = tablaHeader;
    }
    else
    {
      tabla = new TablaBasicaPDF(anchoBorde, anchoBordeCelda, getConfiguracionPagina(request), request);
      tabla.setAmplitudTabla(100);
      
      int[] cols = new int[tablaHeader.getTamanoCol().length];
      for (int f = 0; f < tablaHeader.getTamanoCol().length; f++) {
        cols[f] = tablaHeader.getTamanoCol()[f].intValue();
      }
      tabla.crearTabla(cols);
      

      tabla.setAlineacionHorizontal(alineacionHorizontal == null ? 1 : alineacionHorizontal.intValue());
      tabla.setAlineacionVertical(alineacionVertical == null ? 5 : alineacionVertical.intValue());
      tabla.setTamanoFont((int)font.size());
      tabla.setFont(bold.booleanValue() ? 1 : 0);
      tabla.setFormatoFont(bold.booleanValue() ? 1 : 0);
      tabla.setColorLetra(colorLetra.getRed(), colorLetra.getGreen(), colorLetra.getBlue());
      tabla.setColorFondo(colorFondo.getRed(), colorFondo.getGreen(), colorFondo.getBlue());
      if (columnas.length > 0)
      {
        for (int f = 0; f < columnas.length; f++) {
          tabla.agregarCelda(columnas[f][1]);
        }
        
      } else {
        for (int f = 0; f < tablaHeader.getTamanoCol().length; f++) {
          tabla.agregarCelda(tablaHeader.getTituloCol()[f]);
        }
      }
    }
    return tabla;
  }
  
  protected TablaBasicaPDF inicializarTabla(com.lowagie.text.Font font, String[][] columnas, Integer anchoBorde, Integer anchoBordeCelda, Boolean bold, Color colorLetra, Color colorFondo, HttpServletRequest request) throws Exception
  {
    return inicializarTabla(font, columnas, anchoBorde, anchoBordeCelda, bold, colorLetra, colorFondo, null, null, request);
  }
  
  private void imprimirTitulo(String titulo, Document documento) throws DocumentException
  {
    com.lowagie.text.Font fuente = configuracionPagina.getFuente();
    fuente.setSize(16.0F);
    fuente.setStyle(1);
    Paragraph texto = new Paragraph(titulo, fuente);
    texto.setAlignment(1);
    boolean hayImagenes = (configuracionPagina.getImagenSupCen() != null) || (configuracionPagina.getImagenSupIzq() != null) || (configuracionPagina.getImagenSupDer() != null);
    boolean hayEncabezado = ((configuracionPagina.getEncabezadoCentro() != null) && (!configuracionPagina.getEncabezadoCentro().equals(""))) || 
      ((configuracionPagina.getEncabezadoDerecho() != null) && (!configuracionPagina.getEncabezadoDerecho().equals(""))) || (
      (configuracionPagina.getEncabezadoIzquierdo() != null) && (!configuracionPagina.getEncabezadoIzquierdo().equals("")));
    if ((hayImagenes) || (hayEncabezado)) {
      texto.setSpacingBefore(-50.0F);
    }
    documento.add(texto);
  }
  
  public void onEndTable(PdfWriter writer, Document document)
  {
    if (tabla != null) {
      tabla.endTabla(writer, document);
    }
  }
  
  public Tabla creatTabla(int[] anchoColumnas, HttpServletRequest request) throws Exception {
    if (("PDF".equalsIgnoreCase(tipoPresentacion)) || (tipoPresentacion == null)) {
      tabla = new TablaPDF(configuracionPagina, request);
    } else
      tabla = new TablaBasicaPDF(configuracionPagina, request);
    tabla.crearTabla(anchoColumnas);
    
    return tabla;
  }
  
  public void agregarTabla(Document documento, Object Tabla)
    throws DocumentException
  {
    if (tabla != null)
    {
      if ((("PDF".equalsIgnoreCase(tipoPresentacion)) || (tipoPresentacion == null)) && ((tabla instanceof TablaPDF))) {
        documento.add((PdfPTable)tabla.getTabla());
      } else if ((tabla instanceof TablaBasicaPDF))
        documento.add((Table)tabla.getTabla());
      tabla = null;
    }
  }
}
