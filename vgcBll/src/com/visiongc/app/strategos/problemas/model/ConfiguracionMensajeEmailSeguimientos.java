package com.visiongc.app.strategos.problemas.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ConfiguracionMensajeEmailSeguimientos
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long mensajeId;
  private String descripcion;
  private String mensaje;
  private Boolean acuseRecibo;
  private Boolean soloAuxiliar;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  
  public ConfiguracionMensajeEmailSeguimientos(Long mensajeId, String descripcion, String mensaje, Boolean acuseRecibo, Boolean soloAuxiliar, Date creado, Date modificado, Long creadoId, Long modificadoId)
  {
    this.mensajeId = mensajeId;
    this.descripcion = descripcion;
    this.mensaje = mensaje;
    this.acuseRecibo = acuseRecibo;
    this.soloAuxiliar = soloAuxiliar;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
  }
  

  public ConfiguracionMensajeEmailSeguimientos() {}
  

  public Long getMensajeId()
  {
    return mensajeId;
  }
  
  public void setMensajeId(Long mensajeId) {
    this.mensajeId = mensajeId;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public String getMensaje() {
    return mensaje;
  }
  
  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }
  
  public Boolean getAcuseRecibo() {
    return acuseRecibo;
  }
  
  public void setAcuseRecibo(Boolean acuseRecibo) {
    this.acuseRecibo = acuseRecibo;
  }
  
  public Boolean getSoloAuxiliar() {
    return soloAuxiliar;
  }
  
  public void setSoloAuxiliar(Boolean soloAuxiliar) {
    this.soloAuxiliar = soloAuxiliar;
  }
  
  public Date getCreado() {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public int compareTo(Object o) {
    ConfiguracionMensajeEmailSeguimientos or = (ConfiguracionMensajeEmailSeguimientos)o;
    return getMensajeId().compareTo(or.getMensajeId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("mensajeId", getMensajeId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ConfiguracionMensajeEmailSeguimientos other = (ConfiguracionMensajeEmailSeguimientos)obj;
    return new EqualsBuilder().append(getMensajeId(), other.getMensajeId()).isEquals();
  }
}
