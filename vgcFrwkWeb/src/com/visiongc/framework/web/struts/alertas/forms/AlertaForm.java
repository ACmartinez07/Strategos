package com.visiongc.framework.web.struts.alertas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;








public class AlertaForm
  extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Boolean hayAlertas = Boolean.valueOf(false);
  
  public AlertaForm() {}
  
  public Boolean getHayAlertas() { return hayAlertas; }
  

  public void setHayAlertas(Boolean hayAlertas)
  {
    this.hayAlertas = hayAlertas;
  }
  
  public void clear() {}
}
