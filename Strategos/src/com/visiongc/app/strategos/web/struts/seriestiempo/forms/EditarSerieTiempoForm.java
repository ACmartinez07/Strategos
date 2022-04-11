package com.visiongc.app.strategos.web.struts.seriestiempo.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarSerieTiempoForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long serieId;
  private String nombre;
  private String identificador;
  private Boolean fija;
  private Boolean preseleccionada;

  public Long getSerieId()
  {
    return this.serieId;
  }

  public void setSerieId(Long serieId) {
    this.serieId = serieId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Boolean getFija() {
    return this.fija;
  }

  public void setFija(Boolean fija) {
    this.fija = fija;
  }

  public String getIdentificador() {
    return this.identificador;
  }

  public void setIdentificador(String identificador) {
    this.identificador = identificador;
  }

  public Boolean getPreseleccionada() {
    return this.preseleccionada;
  }

  public void setPreseleccionada(Boolean preseleccionada) {
    this.preseleccionada = preseleccionada;
  }

  public void clear() {
    this.serieId = new Long(0L);
    this.nombre = null;
    this.identificador = null;
    this.fija = new Boolean(false);
    this.preseleccionada = new Boolean(false);
    setBloqueado(new Boolean(false));
  }
}