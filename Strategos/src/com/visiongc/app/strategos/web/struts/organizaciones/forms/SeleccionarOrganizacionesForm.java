package com.visiongc.app.strategos.web.struts.organizaciones.forms;

import com.visiongc.framework.web.struts.forms.SelectorArbolForm;

public class SeleccionarOrganizacionesForm extends SelectorArbolForm
{
  static final long serialVersionUID = 0L;
  private String organizacionId;

  public String getOrganizacionId()
  {
    return this.organizacionId;
  }

  public void setOrganizacionId(String organizacionId) {
    this.organizacionId = organizacionId;
  }
}