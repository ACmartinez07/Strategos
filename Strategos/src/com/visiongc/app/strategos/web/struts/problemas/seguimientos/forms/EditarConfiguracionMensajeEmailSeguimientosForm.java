package com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarConfiguracionMensajeEmailSeguimientosForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long mensajeId;
  private String descripcion;
  private String mensaje;
  private Boolean acuseRecibo;
  private Boolean soloAuxiliar;
  private Long creadoId;
  private Long modificadoId;

  public Long getMensajeId()
  {
    return this.mensajeId;
  }

  public void setMensajeId(Long mensajeId) {
    this.mensajeId = mensajeId;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getMensaje() {
    return this.mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public Boolean getAcuseRecibo() {
    return this.acuseRecibo;
  }

  public void setAcuseRecibo(Boolean acuseRecibo) {
    this.acuseRecibo = acuseRecibo;
  }

  public Boolean getSoloAuxiliar() {
    return this.soloAuxiliar;
  }

  public void setSoloAuxiliar(Boolean soloAuxiliar) {
    this.soloAuxiliar = soloAuxiliar;
  }

  public Long getCreadoId() {
    return this.creadoId;
  }

  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }

  public Long getModificadoId() {
    return this.modificadoId;
  }

  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }

  public void clear() {
    this.mensajeId = new Long(0L);
    this.descripcion = null;
    this.mensaje = null;
    this.acuseRecibo = new Boolean(false);
    this.soloAuxiliar = new Boolean(false);
  }
}