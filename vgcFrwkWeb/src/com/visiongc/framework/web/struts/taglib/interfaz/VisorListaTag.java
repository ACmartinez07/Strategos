package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.xmldata.XmlAtributo;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.util.xmldata.XmlUtil;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.VgcConfiguracionPorUsuario;
import com.visiongc.framework.configuracion.VgcObjetoConfigurable;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.configuracion.VgcConfiguracionesBaseWeb;
import com.visiongc.framework.web.struts.taglib.interfaz.util.AccionVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ColumnaVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.FilaVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.ValorFilaColumnaVisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.VisorListaInfo;
import com.visiongc.framework.web.struts.taglib.interfaz.util.VisorListaValorSeleccionInfo;
import com.visiongc.framework.web.util.HtmlUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;







public class VisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista";
  protected String nombre = null;
  protected String width = null;
  protected String namePaginaLista = null;
  protected String propertyPaginaLista = null;
  protected String scopePaginaLista = null;
  protected String messageKeyNoElementos = null;
  protected String nombreConfiguracionBase = null;
  protected String useFrame = null;
  protected String esSelector = null;
  protected String seleccionSimple = null;
  protected String seleccionMultiple = null;
  protected String nombreForma = null;
  protected String nombreCampoSeleccionados = null;
  protected String nombreCampoValores = null;
  protected String idVisorLista = null;
  
  private VisorListaColumnaSeleccionTag columnaSeleccion = null;
  private ColumnaAccionesVisorListaTag columnaAcciones = null;
  private int numeroAcciones = 0;
  private ListaMap columnas = null;
  private List columnasConfiguradas = null;
  private List filas = null;
  private PaginaLista paginaLista = null;
  private String anchoCalculado = null;
  private boolean agregarDependenciasJs = false;
  private boolean hayConfiguracion = false;
  
  public VisorListaTag() {}
  
  public String getNombre() { return nombre; }
  

  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getWidth()
  {
    return width;
  }
  
  public void setWidth(String width)
  {
    this.width = width;
  }
  
  public String getNamePaginaLista()
  {
    return namePaginaLista;
  }
  
  public void setNamePaginaLista(String namePaginaLista)
  {
    this.namePaginaLista = namePaginaLista;
  }
  
  public String getPropertyPaginaLista()
  {
    return propertyPaginaLista;
  }
  
  public void setPropertyPaginaLista(String propertyPaginaLista)
  {
    this.propertyPaginaLista = propertyPaginaLista;
  }
  
  public String getScopePaginaLista()
  {
    return scopePaginaLista;
  }
  
  public void setScopePaginaLista(String scopePaginaLista)
  {
    this.scopePaginaLista = scopePaginaLista;
  }
  
  public String getMessageKeyNoElementos()
  {
    return messageKeyNoElementos;
  }
  
  public void setMessageKeyNoElementos(String messageKeyNoElementos)
  {
    this.messageKeyNoElementos = messageKeyNoElementos;
  }
  
  public String getNombreConfiguracionBase()
  {
    return nombreConfiguracionBase;
  }
  
  public void setNombreConfiguracionBase(String nombreConfiguracionBase)
  {
    this.nombreConfiguracionBase = nombreConfiguracionBase;
  }
  
  public String getUseFrame()
  {
    return useFrame;
  }
  
  public void setUseFrame(String useFrame)
  {
    this.useFrame = useFrame;
  }
  
  public String getEsSelector()
  {
    return esSelector;
  }
  
  public void setEsSelector(String esSelector)
  {
    this.esSelector = esSelector;
  }
  
  public String getSeleccionSimple()
  {
    return seleccionSimple;
  }
  
  public void setSeleccionSimple(String seleccionSimple)
  {
    this.seleccionSimple = seleccionSimple;
  }
  
  public String getSeleccionMultiple()
  {
    return seleccionMultiple;
  }
  
  public void setSeleccionMultiple(String seleccionMultiple)
  {
    this.seleccionMultiple = seleccionMultiple;
  }
  
  public String getNombreForma()
  {
    return nombreForma;
  }
  
  public void setNombreForma(String nombreForma)
  {
    this.nombreForma = nombreForma;
  }
  
  public String getNombreCampoSeleccionados()
  {
    return nombreCampoSeleccionados;
  }
  
  public void setNombreCampoSeleccionados(String nombreCampoSeleccionados)
  {
    this.nombreCampoSeleccionados = nombreCampoSeleccionados;
  }
  
  public String getNombreCampoValores()
  {
    return nombreCampoValores;
  }
  
  public void setNombreCampoValores(String nombreCampoValores)
  {
    this.nombreCampoValores = nombreCampoValores;
  }
  
  public PaginaLista getPaginaLista()
  {
    return paginaLista;
  }
  
  public VisorListaColumnaSeleccionTag getColumnaSeleccion()
  {
    return columnaSeleccion;
  }
  
  public void setColumnaSeleccion(VisorListaColumnaSeleccionTag columnaSeleccion)
  {
    this.columnaSeleccion = columnaSeleccion;
  }
  
  public ColumnaAccionesVisorListaTag getColumnaAcciones()
  {
    return columnaAcciones;
  }
  
  public void setColumnaAcciones(ColumnaAccionesVisorListaTag columnaAcciones)
  {
    this.columnaAcciones = columnaAcciones;
  }
  
  public int getNumeroAcciones()
  {
    return numeroAcciones;
  }
  
  public void setNumeroAcciones(int numeroAcciones)
  {
    this.numeroAcciones = numeroAcciones;
  }
  
  public ListaMap getColumnas()
  {
    return columnas;
  }
  
  public void setColumnas(ListaMap columnas)
  {
    this.columnas = columnas;
  }
  
  public List getFilas()
  {
    return filas;
  }
  
  public void setFilas(List filas)
  {
    this.filas = filas;
  }
  
  public String getIdVisorLista()
  {
    return idVisorLista;
  }
  
  public void setIdVisorLista(String idVisorLista)
  {
    this.idVisorLista = idVisorLista;
  }
  





  private void configurarPaginaLista()
  {
    paginaLista = null;
    
    Object objetoPaginaLista = null;
    
    if ((scopePaginaLista != null) && (!scopePaginaLista.equals("")))
    {
      if (scopePaginaLista.equalsIgnoreCase("session")) {
        objetoPaginaLista = pageContext.getSession().getAttribute(namePaginaLista);
      } else if (scopePaginaLista.equalsIgnoreCase("request")) {
        objetoPaginaLista = pageContext.getRequest().getAttribute(namePaginaLista);
      } else {
        objetoPaginaLista = pageContext.getAttribute(namePaginaLista);
      }
    }
    else {
      objetoPaginaLista = pageContext.getAttribute(namePaginaLista);
      if (objetoPaginaLista == null)
        objetoPaginaLista = pageContext.getSession().getAttribute(namePaginaLista);
      if (objetoPaginaLista == null) {
        objetoPaginaLista = pageContext.getRequest().getAttribute(namePaginaLista);
      }
    }
    if ((propertyPaginaLista != null) && (!propertyPaginaLista.equals("")))
    {
      String funcionProperty = "get" + propertyPaginaLista.substring(0, 1).toUpperCase() + propertyPaginaLista.substring(1);
      paginaLista = ((PaginaLista)ObjetoAbstracto.ejecutarReturnObject(objetoPaginaLista, funcionProperty));
    }
    else {
      paginaLista = ((PaginaLista)objetoPaginaLista);
    }
  }
  
  private String agregarDependenciasJs() {
    StringBuffer resultado = new StringBuffer();
    
    if (agregarDependenciasJs) {
      resultado.append("<script language=\"Javascript\" src=\"" + getUrlAplicacion() + "/componentes/visorLista/visorListaJsTag.jsp\"></script>");
    }
    return resultado.toString();
  }
  






  private String agregarInicioVisorLista()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    




    if ((useFrame != null) && (useFrame.equalsIgnoreCase("true"))) {
      resultado.append("\n<div style=\"position:relative;\" class=\"contenedorPrincipal\">");
    }
    
    resultado.append("\n<table class=\"listView\" cellpadding=\"5\" cellspacing=\"1\" ");
    
    if ((anchoCalculado != null) && (!anchoCalculado.equals(""))) {
      resultado.append(" width=\"" + anchoCalculado + "\"");
    } else if ((width != null) && (width.length() > 0))
      resultado.append(" width=\"" + width + "\"");
    resultado.append(" id=\"");
    resultado.append(nombre + "\">" + "\n");
    
    return resultado.toString();
  }
  






  private String agregarFinVisorLista()
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("\n</table>\n");
    
    if ((useFrame != null) && (useFrame.equalsIgnoreCase("true"))) {
      resultado.append("</div>\n");
    }
    if (((seleccionSimple != null) && (seleccionSimple.equalsIgnoreCase("true"))) || ((esSelector != null) && (esSelector.equalsIgnoreCase("true")) && (nombreForma != null) && (!nombreForma.equals("")) && (nombreCampoSeleccionados != null) && (!nombreCampoSeleccionados.equals(""))))
    {
      resultado.append("<script>\n");
      resultado.append("inicializarVisorListaSeleccionSimple(document." + nombreForma + "." + nombreCampoSeleccionados + ", '" + nombre + "');" + "\n");
      resultado.append("</script>\n");
    }
    
    if ((seleccionMultiple != null) && (seleccionMultiple.equalsIgnoreCase("true")))
    {
      resultado.append("<script>\n");
      resultado.append("inicializarVisorListaSeleccionMultiple(document." + nombreForma + "." + nombreCampoSeleccionados + ", '" + nombre + "');" + "\n");
      resultado.append("</script>\n");
    }
    
    return resultado.toString();
  }
  
  private String agregarEncabezadoVisorLista()
  {
    StringBuffer resultado = new StringBuffer();
    
    resultado.append("  <tr class=\"encabezadoListView\" height=\"20px\">\n");
    
    resultado.append(agregarColumnasVisorLista());
    
    resultado.append("  </tr>\n");
    
    return resultado.toString();
  }
  






  private String agregarColumnasVisorLista()
  {
    StringBuffer resultado = new StringBuffer();
    boolean todasTienenAncho = true;
    int anchoTotal = 0;
    
    if ((esSelector != null) && (esSelector.equalsIgnoreCase("true")))
    {
      resultado.append("    <td align=\"center\" width=\"10px\">");
      resultado.append("<img src=\"" + getUrlAplicacion() + "/componentes/visorLista/seleccionado.gif\" border=\"0\" width=\"10\" height=\"10\">");
      resultado.append("</td>\n");
    }
    if ((columnaSeleccion != null) && (paginaLista.getLista().size() > 0))
    {
      resultado.append("    <td align=\"" + columnaSeleccion.getAlign() + "\" width=\"" + columnaSeleccion.getWidth() + "\">");
      resultado.append("<input name='" + columnaSeleccion.getNombreCampoObjetoId() + "Control' type='checkbox' class=\"botonSeleccionMultiple\" onClick=\"javascript:selectUnselectAllObject(self.document." + columnaSeleccion.getNombreFormaHtml() + ", self.document." + columnaSeleccion.getNombreFormaHtml() + "." + columnaSeleccion.getNombreCampoObjetoId() + "Control, '" + columnaSeleccion.getNombreCampoObjetoId() + "')\">");
      resultado.append("</td>\n");
    }
    if ((columnaAcciones != null) && (numeroAcciones > 0) && (paginaLista.getLista().size() > 0)) {
      resultado.append("    <td align=\"" + columnaAcciones.getAlign() + "\" colspan=\"" + numeroAcciones + "\" width=\"" + columnaAcciones.getWidth() + "\">" + columnaAcciones.getTitulo() + "</td>" + "\n");
    }
    
    configurarColumnas();
    
    if (hayConfiguracion)
    {
      for (Iterator i = columnasConfiguradas.iterator(); i.hasNext();)
      {
        XmlNodo confColumna = (XmlNodo)i.next();
        
        ColumnaVisorListaInfo columna = (ColumnaVisorListaInfo)columnas.get(confColumna.getAtributo("id").getValor());
        
        String ancho = confColumna.getValorAtributo("ancho");
        if ((ancho != null) && (!ancho.equals("")))
        {

          columna.setAncho(ancho);
        }
        else if ((columna.getAncho() != null) || (!columna.getAncho().equals(""))) {
          ancho = columna.getAncho();
        }
        if (HtmlUtil.isTamanoEnPixeles(ancho)) {
          anchoTotal += HtmlUtil.getTamanoPixeles(columna.getAncho());
        } else {
          todasTienenAncho = false;
        }
        resultado.append(columna.getCodigo());
      }
      
    }
    else
    {
      for (Iterator i = columnas.iterator(); i.hasNext();)
      {
        ColumnaVisorListaInfo columna = (ColumnaVisorListaInfo)i.next();
        
        resultado.append(columna.getCodigo());
        
        if (HtmlUtil.isTamanoEnPixeles(columna.getAncho())) {
          anchoTotal += HtmlUtil.getTamanoPixeles(columna.getAncho());
        } else {
          todasTienenAncho = false;
        }
      }
    }
    if (todasTienenAncho)
    {
      if (columnaSeleccion != null)
        anchoTotal += HtmlUtil.getTamanoPixeles(columnaSeleccion.getWidth());
      if (columnaAcciones != null)
        anchoTotal += HtmlUtil.getTamanoPixeles(columnaAcciones.getWidth());
      anchoCalculado = (Integer.toString(anchoTotal) + "px");
    }
    else {
      anchoCalculado = "";
    }
    return resultado.toString();
  }
  




  private void configurarColumnas()
  {
    columnasConfiguradas = new ArrayList();
    
    if (nombreConfiguracionBase != null)
    {
      Usuario usuario = (Usuario)pageContext.getSession().getAttribute("usuario");
      
      XmlNodo configuracion = (XmlNodo)pageContext.getSession().getAttribute(nombreConfiguracionBase + ".visorLista." + nombre);
      
      if (configuracion == null)
      {
        configuracion = VgcConfiguracionPorUsuario.getInstance(nombreConfiguracionBase).getConfiguracion("visorLista." + nombre, usuario.getUsuarioId());
        pageContext.getSession().setAttribute(nombreConfiguracionBase + ".visorLista." + nombre, configuracion);
      }
      
      if (configuracion == null)
      {
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        Configuracion configuracionGeneral = frameworkService.getConfiguracion("visorLista." + nombre);
        frameworkService.close();
        
        if (configuracionGeneral != null) {
          configuracion = XmlUtil.readXml(configuracionGeneral.getValor());
        }
      }
      if (configuracion != null)
      {
        VgcConfiguracionesBaseWeb configuracionesBaseWeb = (VgcConfiguracionesBaseWeb)VgcConfiguracionPorUsuario.getInstance(nombreConfiguracionBase).getConfiguracionesBase();
        
        VgcObjetoConfigurable visorLista = configuracionesBaseWeb.getObjetoConfigurable(nombreConfiguracionBase + ".objetoConfigurable.visorLista." + nombre, (HttpServletRequest)pageContext.getRequest());
        
        if (!visorLista.configuracionesSonIguales(visorLista.getConfiguracionBase(), configuracion))
          return;
      }
      if (configuracion != null)
      {

        hayConfiguracion = true;
        
        ListaMap configuracionColumnasXml = ((XmlNodo)configuracion.getElemLista("columnas")).getLista();
        List configuracionColumnas = new ArrayList();
        
        for (int i = 0; i < configuracionColumnasXml.size(); i++) {
          configuracionColumnas.add(configuracionColumnasXml.get(i));
        }
        int orden = 1;
        while (configuracionColumnas.size() > 0)
        {
          boolean encontrado = false;
          for (int i = 0; i < configuracionColumnas.size(); i++)
          {
            XmlNodo confColumna = (XmlNodo)configuracionColumnas.get(i);
            if (Integer.parseInt(confColumna.getValorAtributo("orden")) == orden)
            {
              if (confColumna.getValorAtributo("visible").equalsIgnoreCase("true"))
                columnasConfiguradas.add(confColumna);
              configuracionColumnas.remove(i);
              encontrado = true;
              break;
            }
          }
          if (!encontrado) {
            orden++;
          }
        }
      }
    }
  }
  





  private String agregarFilas()
    throws JspException
  {
    StringBuffer resultado = new StringBuffer();
    
    if (filas.size() == 0)
    {
      resultado.append("  <tr class=\"mouseFueraCuerpoListView\" id=\"0\" height=\"20px\">\n");
      int colspan = 1;
      if ((columnasConfiguradas != null) && (columnasConfiguradas.size() > 0)) {
        colspan = columnasConfiguradas.size();
      } else
        colspan = columnas.size();
      resultado.append("    <td valign=\"middle\" align=\"center\" colspan=\"" + colspan + "\">");
      resultado.append(getMessageResource(null, null, getMessageKeyNoElementos(), Boolean.valueOf(true)));
      resultado.append("</td>\n");
      resultado.append("  </tr>\n");

    }
    else
    {
      for (Iterator iter = filas.iterator(); iter.hasNext();)
      {
        FilaVisorListaInfo fila = (FilaVisorListaInfo)iter.next();
        
        agregarFila(resultado, fila);
      }
    }
    
    return resultado.toString();
  }
  
  private void agregarAcciones(StringBuffer resultado, FilaVisorListaInfo fila)
  {
    for (Iterator iter = fila.getAcciones().iterator(); iter.hasNext();)
    {
      AccionVisorListaInfo accion = (AccionVisorListaInfo)iter.next();
      resultado.append(accion.getCodigo());
    }
  }
  






  private void agregarFila(StringBuffer resultado, FilaVisorListaInfo fila)
    throws JspException
  {
    resultado.append(fila.getCodigoInicial());
    if ((esSelector != null) && (esSelector.equalsIgnoreCase("true")))
      resultado.append("    <td align=\"center\" id=\"selector" + fila.getValorId() + "\" >&nbsp;</td>" + "\n");
    if (fila.getValorSeleccion() != null)
      resultado.append(fila.getValorSeleccion().getCodigo());
    agregarAcciones(resultado, fila);
    if (hayConfiguracion)
    {
      for (Iterator i = columnasConfiguradas.iterator(); i.hasNext();)
      {
        XmlNodo confColumna = (XmlNodo)i.next();
        
        ColumnaVisorListaInfo columna = (ColumnaVisorListaInfo)columnas.get(confColumna.getAtributo("id").getValor());
        
        if (fila.getValoresFilaColumna().get(columna.getNombre()) == null)
          throw new JspException("No existe el valor de fila de una columna con nombre '" + columna.getNombre() + "'");
        resultado.append(((ValorFilaColumnaVisorListaInfo)fila.getValoresFilaColumna().get(columna.getNombre())).getCodigo());
      }
      resultado.append(fila.getCodigoFinal());
    }
    else
    {
      for (Iterator iter = columnas.iterator(); iter.hasNext();)
      {
        ColumnaVisorListaInfo columna = (ColumnaVisorListaInfo)iter.next();
        if (fila.getValoresFilaColumna().get(columna.getNombre()) == null)
          throw new JspException("No existe el valor de fila de una columna con nombre '" + columna.getNombre() + "'");
        resultado.append(((ValorFilaColumnaVisorListaInfo)fila.getValoresFilaColumna().get(columna.getNombre())).getCodigo());
      }
      resultado.append(fila.getCodigoFinal());
    }
  }
  




  public int doStartTag()
    throws JspException
  {
    if (pageContext.getRequest().getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista") == null) {
      agregarDependenciasJs = true;
    }
    configurarPaginaLista();
    
    columnaSeleccion = null;
    
    columnaAcciones = null;
    
    columnas = new ListaMap();
    
    filas = new ArrayList();
    
    anchoCalculado = null;
    
    hayConfiguracion = false;
    
    pageContext.getRequest().setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista", this);
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista", this);
    
    return 2;
  }
  


  public int doEndTag()
    throws JspException
  {
    VisorListaInfo visorListaInfo = new VisorListaInfo();
    
    visorListaInfo.setColumnas(columnas);
    
    pageContext.getSession().setAttribute(nombreConfiguracionBase + ".objetoConfigurable.visorLista." + nombre, visorListaInfo);
    

    String encabezado = agregarEncabezadoVisorLista();
    
    StringBuffer resultado = new StringBuffer();
    resultado.append(agregarDependenciasJs());
    resultado.append(agregarInicioVisorLista());
    resultado.append(encabezado);
    resultado.append(agregarFilas());
    resultado.append(agregarFinVisorLista());
    
    TagUtils.getInstance().write(pageContext, resultado.toString());
    
    return 6;
  }
  



  public void release()
  {
    super.release();
  }
}
