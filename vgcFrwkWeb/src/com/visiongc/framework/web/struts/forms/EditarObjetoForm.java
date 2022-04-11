package com.visiongc.framework.web.struts.forms;

import org.apache.struts.validator.ValidatorActionForm;







public abstract class EditarObjetoForm
  extends ValidatorActionForm
{
  static final long serialVersionUID = 0L;
  private Boolean bloqueado;
  private Boolean enEdicion;
  private String creado;
  private String modificado;
  private String creadoUsuarioNombre;
  private String modificadoUsuarioNombre;
  private Byte status;
  private String respuesta;
  private Boolean showPresentacion;
  
  public EditarObjetoForm() {}
  
  public Boolean getBloqueado()
  {
    return bloqueado;
  }
  
  public void setBloqueado(Boolean bloqueado)
  {
    this.bloqueado = bloqueado;
  }
  
  public Boolean getEnEdicion()
  {
    return enEdicion;
  }
  
  public void setEnEdicion(Boolean enEdicion)
  {
    this.enEdicion = enEdicion;
  }
  
  public String getCreado()
  {
    return creado;
  }
  
  public void setCreado(String creado)
  {
    this.creado = creado;
  }
  
  public String getCreadoUsuarioNombre()
  {
    return creadoUsuarioNombre;
  }
  
  public void setCreadoUsuarioNombre(String creadoUsuarioNombre)
  {
    this.creadoUsuarioNombre = creadoUsuarioNombre;
  }
  
  public String getModificado()
  {
    return modificado;
  }
  
  public void setModificado(String modificado)
  {
    this.modificado = modificado;
  }
  
  public String getModificadoUsuarioNombre()
  {
    return modificadoUsuarioNombre;
  }
  
  public void setModificadoUsuarioNombre(String modificadoUsuarioNombre)
  {
    this.modificadoUsuarioNombre = modificadoUsuarioNombre;
  }
  
  public Byte getStatus()
  {
    return status;
  }
  
  public void setStatus(Byte status)
  {
    this.status = status;
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
  
  public Boolean getShowPresentacion()
  {
    return showPresentacion;
  }
  
  public void setShowPresentacion(Boolean showPresentacion)
  {
    this.showPresentacion = showPresentacion;
  }
  
  public abstract void clear();
}
