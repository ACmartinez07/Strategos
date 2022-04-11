package com.visiongc.app.strategos.web.struts.causas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarCausaForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long causaId;
  private Long padreId;
  private String nombre;
  private String descripcion;
  private Integer nivel;

  public String getNombre()
  {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Long getCausaId() {
    return this.causaId;
  }

  public void setCausaId(Long causaId) {
    this.causaId = causaId;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public Integer getNivel() {
    return this.nivel;
  }

  public void setNivel(Integer nivel) {
    this.nivel = nivel;
  }

  public void clear() {
    this.causaId = new Long(0L);
    this.padreId = new Long(0L);
    this.nombre = null;
    this.descripcion = null;
    this.nivel = new Integer(0);
    setBloqueado(new Boolean(false));
  }
}