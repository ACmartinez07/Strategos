package com.visiongc.app.strategos.web.struts.responsables.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class AsociarResponsablesForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long responsableId;

  public Long getResponsableId()
  {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public void clear() {
    this.responsableId = new Long(0L);
    setBloqueado(new Boolean(false));
  }
}