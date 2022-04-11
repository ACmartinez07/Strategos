package com.visiongc.framework.web.struts.alertas.forms;

import com.visiongc.framework.model.Message.MessageStatus;
import com.visiongc.framework.model.Message.MessageTypeEvent;
import com.visiongc.framework.web.struts.forms.VisorListaForm;
import java.util.List;






public class GestionarAlertasForm
  extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String fechaDesde;
  private String fechaHasta;
  private Byte tipoEvento;
  private List tiposEventos;
  private String respuesta;
  private Byte tipoEstatus;
  private List tiposEstatus;
  
  public GestionarAlertasForm() {}
  
  public String getFechaDesde()
  {
    return fechaDesde;
  }
  
  public void setFechaDesde(String fechaDesde)
  {
    this.fechaDesde = fechaDesde;
  }
  
  public String getFechaHasta()
  {
    return fechaHasta;
  }
  
  public void setFechaHasta(String fechaHasta)
  {
    this.fechaHasta = fechaHasta;
  }
  
  public List getTiposEventos()
  {
    return tiposEventos;
  }
  
  public void setTiposEventos(List tiposEventos)
  {
    this.tiposEventos = tiposEventos;
  }
  
  public Byte getTipoEvento()
  {
    return tipoEvento;
  }
  
  public void setTipoEvento(Byte tipoEvento)
  {
    this.tipoEvento = tipoEvento;
  }
  
  public List getTiposEstatus()
  {
    return tiposEstatus;
  }
  
  public void setTiposEstatus(List tiposEstatus)
  {
    this.tiposEstatus = tiposEstatus;
  }
  
  public Byte getTipoEstatus()
  {
    return tipoEstatus;
  }
  
  public void setTipoEstatus(Byte tipoEstatus)
  {
    this.tipoEstatus = tipoEstatus;
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
}
