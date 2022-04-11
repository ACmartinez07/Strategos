package com.visiongc.app.strategos.web.struts.seriestiempo.forms;

import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarSeriesTiempoForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public SerieTiempo getSerieReal() {
    return SerieTiempo.getSerieReal();
  }

  public SerieTiempo getSerieProgramado() {
    return SerieTiempo.getSerieProgramado();
  }

  public SerieTiempo getSerieMaximo() {
    return SerieTiempo.getSerieMaximo();
  }

  public SerieTiempo getSerieMinimo() {
    return SerieTiempo.getSerieMinimo();
  }
}