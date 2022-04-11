package com.visiongc.app.strategos.web.struts.plancuentas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarCuentaForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long cuentaId;
  private String codigo;
  private Long padreId;
  private String descripcion;

  public String getCodigo()
  {
    return this.codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public Long getCuentaId() {
    return this.cuentaId;
  }

  public void setCuentaId(Long cuentaId) {
    this.cuentaId = cuentaId;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void clear() {
    this.cuentaId = new Long(0L);
    this.codigo = null;
    this.padreId = null;
    this.descripcion = null;
    setBloqueado(new Boolean(false));
  }
}