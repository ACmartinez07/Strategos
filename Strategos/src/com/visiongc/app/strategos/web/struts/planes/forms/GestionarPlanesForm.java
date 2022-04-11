package com.visiongc.app.strategos.web.struts.planes.forms;

import com.visiongc.app.strategos.planes.model.util.TipoPlan;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarPlanesForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private Byte tipoPlanes;

  public Byte getTipoPlanes()
  {
    return this.tipoPlanes;
  }

  public void setTipoPlanes(Byte tipoPlanes) {
    this.tipoPlanes = tipoPlanes;
  }

  public String getFiltroNombre() {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public Byte getTipoPlanEstrategico() {
    return TipoPlan.getTipoPlanEstrategico();
  }

  public Byte getTipoPlanOperativo() {
    return TipoPlan.getTipoPlanOperativo();
  }
}