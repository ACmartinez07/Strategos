package com.visiongc.app.strategos.web.struts.categoriasmedicion.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarCategoriaMedicionForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long categoriaId;
  private String nombre;

  public Long getCategoriaId()
  {
    return this.categoriaId;
  }

  public void setCategoriaId(Long categoriaId) {
    this.categoriaId = categoriaId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void clear() {
    this.categoriaId = new Long(0L);
    this.nombre = null;
    setBloqueado(new Boolean(false));
  }
}