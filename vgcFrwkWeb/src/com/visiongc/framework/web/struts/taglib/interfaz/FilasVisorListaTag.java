package com.visiongc.framework.web.struts.taglib.interfaz;

import com.visiongc.commons.struts.tag.VgcBodyBaseTag;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.web.struts.taglib.interfaz.util.FilaVisorListaInfo;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;







public class FilasVisorListaTag
  extends VgcBodyBaseTag
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas";
  private Iterator iterator = null;
  private FilaVisorListaInfo fila = null;
  
  private boolean selector;
  private boolean seleccionSimple;
  private boolean seleccionMultiple;
  protected String nombreObjeto = null;
  



  protected boolean iniciado = false;
  
  private VisorListaTag visorLista = null;
  
  public FilasVisorListaTag() {}
  
  public String getNombreObjeto() { return nombreObjeto; }
  

  public void setNombreObjeto(String nombreObjeto)
  {
    this.nombreObjeto = nombreObjeto;
  }
  
  public FilaVisorListaInfo getFila()
  {
    return fila;
  }
  
  public void setFila(FilaVisorListaInfo fila)
  {
    this.fila = fila;
  }
  
  public VisorListaTag getVisorLista()
  {
    return visorLista;
  }
  
  public int doStartTag() throws JspException
  {
    visorLista = ((VisorListaTag)pageContext.getAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista"));
    
    if (visorLista == null) {
      throw new JspException("El tag FilasVisorLista debe estar dentro de un tag VisorLista");
    }
    pageContext.setAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas", this);
    
    if ((visorLista.seleccionSimple != null) && (visorLista.seleccionSimple.equalsIgnoreCase("true")))
      seleccionSimple = true;
    if ((visorLista.seleccionMultiple != null) && (visorLista.seleccionMultiple.equalsIgnoreCase("true")))
      seleccionMultiple = true;
    if ((visorLista.esSelector != null) && (visorLista.esSelector.equalsIgnoreCase("true")))
      selector = true;
    if ((visorLista.nombreForma == null) || (visorLista.nombreForma.equals("")) || (visorLista.nombreCampoSeleccionados == null) || (visorLista.nombreCampoSeleccionados.equals("")))
    {
      selector = false;
      seleccionSimple = false;
      seleccionMultiple = false;
    }
    
    iterator = visorLista.getPaginaLista().getLista().iterator();
    
    if (iterator.hasNext())
    {
      Object element = iterator.next();
      
      if (element == null) {
        pageContext.removeAttribute(nombreObjeto);
      } else {
        pageContext.setAttribute(nombreObjeto, element);
      }
      iniciado = true;
      
      fila = new FilaVisorListaInfo();
      
      return 2;
    }
    
    return 0;
  }
  








  public int doAfterBody()
    throws JspException
  {
    bodyContent.clearBody();
    if (fila.getValoresFilaColumna().size() > 0)
    {
      String eventoOnclick = fila.getEventoOnclick();
      String eventoDoubleclick = fila.getEventoDoubleclick();
      String eventoOnclickSeleccion = "";
      String eventoDoubleclickSeleccion = "";
      String nombreCampoValores = "null";
      if ((seleccionSimple) || (selector))
      {
        if ((visorLista.nombreCampoValores != null) && (visorLista.nombreCampoValores != null))
          nombreCampoValores = "document." + visorLista.nombreForma + "." + visorLista.nombreCampoValores;
        eventoOnclickSeleccion = "eventoClickFilaV2(document." + visorLista.nombreForma + "." + visorLista.nombreCampoSeleccionados + ", " + nombreCampoValores + ", '" + visorLista.nombre + "', this);";
        eventoDoubleclickSeleccion = eventoOnclickSeleccion;
      }
      else if (seleccionMultiple)
      {
        if ((visorLista.nombreCampoValores != null) && (visorLista.nombreCampoValores != null))
          nombreCampoValores = "document." + visorLista.nombreForma + "." + visorLista.nombreCampoValores;
        eventoOnclickSeleccion = "eventoClickFilaSeleccionMultiple(document." + visorLista.nombreForma + "." + visorLista.nombreCampoSeleccionados + ", " + nombreCampoValores + ", '" + visorLista.nombre + "', this);";
      }
      
      if ((eventoOnclick != null) && (!eventoOnclick.equals(""))) {
        eventoOnclick = "onclick=\"" + eventoOnclickSeleccion + eventoOnclick + "\"";

      }
      else if (!eventoOnclickSeleccion.equals("")) {
        eventoOnclick = "onclick=\"" + eventoOnclickSeleccion + "\"";
      } else {
        eventoOnclick = "";
      }
      
      if ((eventoDoubleclick != null) && (!eventoDoubleclick.equals(""))) {
        eventoDoubleclick = "ondblclick=\"" + eventoDoubleclickSeleccion + eventoDoubleclick + "\"";

      }
      else if (!eventoDoubleclickSeleccion.equals("")) {
        eventoDoubleclick = "ondblclick=\"" + eventoDoubleclickSeleccion + "\"";
      } else {
        eventoDoubleclick = "";
      }
      
      String eventoOnmouseout = fila.getEventoOnmouseout();
      if (eventoOnmouseout == null)
        eventoOnmouseout = "";
      if ((seleccionSimple) || (selector)) {
        eventoOnmouseout = "eventoMouseFueraFilaV2(document." + visorLista.nombreForma + "." + visorLista.nombreCampoSeleccionados + ", this);" + eventoOnmouseout;
      } else if (seleccionMultiple)
        eventoOnmouseout = "eventoMouseFueraFilaSeleccionMultiple(document." + visorLista.nombreForma + "." + visorLista.nombreCampoSeleccionados + ", this);" + eventoOnmouseout;
      String eventoOnmouseover = fila.getEventoOnmouseover();
      if (eventoOnmouseover == null)
        eventoOnmouseover = "";
      if ((seleccionSimple) || (selector)) {
        eventoOnmouseover = "eventoMouseEncimaFilaV2(document." + visorLista.nombreForma + "." + visorLista.nombreCampoSeleccionados + ", this);" + eventoOnmouseover;
      } else if (seleccionMultiple) {
        eventoOnmouseover = "eventoMouseEncimaFilaSeleccionMultiple(document." + visorLista.nombreForma + "." + visorLista.nombreCampoSeleccionados + ", this);" + eventoOnmouseover;
      }
      String valorId = fila.getValorId();
      if (valorId == null) {
        valorId = "";
      } else
        valorId = "id=\"" + valorId + "\"";
      fila.setCodigoInicial("  <tr " + valorId + " class=\"mouseFueraCuerpoListView\" onMouseOver=\"this.className='mouseEncimaCuerpoListView';" + eventoOnmouseover + "\" onMouseOut=\"this.className='mouseFueraCuerpoListView';" + eventoOnmouseout + "\" height=\"20px\" " + eventoOnclick + " " + eventoDoubleclick + ">" + "\n");
      fila.setCodigoFinal("  </tr>\n");
      visorLista.getFilas().add(fila);
      if (fila.getAcciones().size() > visorLista.getNumeroAcciones()) {
        visorLista.setNumeroAcciones(fila.getAcciones().size());
      }
    }
    if (iterator.hasNext())
    {
      Object element = iterator.next();
      
      if (element == null) {
        pageContext.removeAttribute(nombreObjeto);
      } else {
        pageContext.setAttribute(nombreObjeto, element);
      }
      fila = new FilaVisorListaInfo();
      
      return 2;
    }
    
    return 0;
  }
  


  public int doEndTag()
    throws JspException
  {
    pageContext.removeAttribute("com.visiongc.framework.web.struts.taglib.interfaz.VisorLista.Filas");
    
    return 6;
  }
  



  public void release()
  {
    super.release();
    iniciado = false;
  }
}
