package com.visiongc.app.strategos.web.struts.foros.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarForoForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long foroId;
  private Long padreId;
  private Byte objetoKey;
  private Long objetoId;
  private String asunto;
  private String email;
  private String comentario;
  private Byte tipo;
  private String nombreObjetoKey;
  private String nombreTipoObjetoKey;
  private String nombreOrganizacion;

  public Long getForoId()
  {
    return this.foroId;
  }

  public void setForoId(Long foroId) {
    this.foroId = foroId;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public Byte getObjetoKey() {
    return this.objetoKey;
  }

  public void setObjetoKey(Byte objetoKey) {
    this.objetoKey = objetoKey;
  }

  public Long getObjetoId() {
    return this.objetoId;
  }

  public void setObjetoId(Long objetoId) {
    this.objetoId = objetoId;
  }

  public String getAsunto() {
    return this.asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getComentario() {
    return this.comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public String getNombreObjetoKey() {
    return this.nombreObjetoKey;
  }

  public void setNombreObjetoKey(String nombreObjetoKey) {
    this.nombreObjetoKey = nombreObjetoKey;
  }

  public String getNombreTipoObjetoKey() {
    return this.nombreTipoObjetoKey;
  }

  public void setNombreTipoObjetoKey(String nombreTipoObjetoKey) {
    this.nombreTipoObjetoKey = nombreTipoObjetoKey;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public void clear() {
    this.foroId = new Long(0L);
    this.padreId = null;
    this.objetoKey = null;
    this.objetoId = new Long(0L);
    this.asunto = null;
    this.email = null;
    this.comentario = null;
    this.tipo = new Byte("0");
    this.nombreObjetoKey = null;
    this.nombreOrganizacion = null;
    this.nombreTipoObjetoKey = null;
    setBloqueado(new Boolean(false));
  }
}