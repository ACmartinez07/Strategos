package com.visiongc.framework.web.struts.taglib.interfaz.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








public class FilaVisorListaInfo
{
  protected Map valoresFilaColumna = null;
  protected VisorListaValorSeleccionInfo valorSeleccion = null;
  protected String eventoOnclick = null;
  protected String eventoDoubleclick = null;
  protected String eventoOnmouseout = null;
  protected String eventoOnmouseover = null;
  protected String valorId = null;
  protected List acciones = null;
  protected String codigoInicial = null;
  protected String codigoFinal = null;
  
  public VisorListaValorSeleccionInfo getValorSeleccion()
  {
    return valorSeleccion;
  }
  
  public void setValorSeleccion(VisorListaValorSeleccionInfo valorSeleccion)
  {
    this.valorSeleccion = valorSeleccion;
  }
  
  public Map getValoresFilaColumna()
  {
    return valoresFilaColumna;
  }
  
  public void setValoresFilaColumna(Map valoresFilaColumna)
  {
    this.valoresFilaColumna = valoresFilaColumna;
  }
  
  public String getCodigoFinal()
  {
    return codigoFinal;
  }
  
  public void setCodigoFinal(String codigoFinal)
  {
    this.codigoFinal = codigoFinal;
  }
  
  public String getCodigoInicial()
  {
    return codigoInicial;
  }
  
  public void setCodigoInicial(String codigoInicial)
  {
    this.codigoInicial = codigoInicial;
  }
  
  public FilaVisorListaInfo()
  {
    valoresFilaColumna = new HashMap();
    acciones = new ArrayList();
  }
  
  public List getAcciones()
  {
    return acciones;
  }
  
  public void setAcciones(List acciones)
  {
    this.acciones = acciones;
  }
  
  public String getEventoOnclick()
  {
    return eventoOnclick;
  }
  
  public void setEventoOnclick(String eventoOnclick)
  {
    this.eventoOnclick = eventoOnclick;
  }
  
  public String getEventoDoubleclick()
  {
    return eventoDoubleclick;
  }
  
  public void setEventoDoubleclick(String eventoDoubleclick)
  {
    this.eventoDoubleclick = eventoDoubleclick;
  }
  
  public String getEventoOnmouseout()
  {
    return eventoOnmouseout;
  }
  
  public void setEventoOnmouseout(String eventoOnmouseout)
  {
    this.eventoOnmouseout = eventoOnmouseout;
  }
  
  public String getEventoOnmouseover()
  {
    return eventoOnmouseover;
  }
  
  public void setEventoOnmouseover(String eventoOnmouseover)
  {
    this.eventoOnmouseover = eventoOnmouseover;
  }
  
  public String getValorId()
  {
    return valorId;
  }
  
  public void setValorId(String valorId)
  {
    this.valorId = valorId;
  }
}
