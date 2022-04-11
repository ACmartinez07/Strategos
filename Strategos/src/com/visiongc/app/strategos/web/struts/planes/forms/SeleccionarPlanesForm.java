package com.visiongc.app.strategos.web.struts.planes.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarPlanesForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  public static final String SEPARADOR_PLANES = "!;!";
  public static final String SEPARADOR_RUTA = "!#!";
  public static final String CODIGO_PLAN_ELIMINADO = "!ELIMINADO!";
  private Long organizacionSeleccionadaId;
  private String rutaCompletaOrganizacion;
  private String nombrePlan;
  private Boolean permitirCambiarOrganizacion;
  private String nombreCampoRutasCompletas;
  private String panelSeleccionado;
  private Boolean iniciado;
  private boolean cambioOrganizacion;
  private Boolean mostrarSoloPlanesActivos;

  public String getNombrePlan()
  {
    return this.nombrePlan;
  }

  public void setNombrePlan(String nombrePlan) {
    this.nombrePlan = nombrePlan;
  }

  public Boolean getPermitirCambiarOrganizacion() {
    return this.permitirCambiarOrganizacion;
  }

  public void setPermitirCambiarOrganizacion(Boolean permitirCambiarOrganizacion) {
    this.permitirCambiarOrganizacion = permitirCambiarOrganizacion;
  }

  public boolean isCambioOrganizacion() {
    return this.cambioOrganizacion;
  }

  public void setCambioOrganizacion(boolean cambioOrganizacion) {
    this.cambioOrganizacion = cambioOrganizacion;
  }

  public String getNombreCampoRutasCompletas() {
    return this.nombreCampoRutasCompletas;
  }

  public void setNombreCampoRutasCompletas(String nombreCampoRutasCompletas) {
    this.nombreCampoRutasCompletas = nombreCampoRutasCompletas;
  }

  public String getPanelSeleccionado() {
    return this.panelSeleccionado;
  }

  public void setPanelSeleccionado(String panelSeleccionado) {
    this.panelSeleccionado = panelSeleccionado;
  }

  public Long getOrganizacionSeleccionadaId() {
    return this.organizacionSeleccionadaId;
  }

  public void setOrganizacionSeleccionadaId(Long organizacionSeleccionadaId) {
    this.organizacionSeleccionadaId = organizacionSeleccionadaId;
  }

  public String getRutaCompletaOrganizacion() {
    return this.rutaCompletaOrganizacion;
  }

  public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) {
    this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
  }

  public Boolean getIniciado() {
    return this.iniciado;
  }

  public void setIniciado(Boolean iniciado) {
    this.iniciado = iniciado;
  }

  public Boolean getMostrarSoloPlanesActivos() {
    return this.mostrarSoloPlanesActivos;
  }

  public void setMostrarSoloPlanesActivos(Boolean mostrarSoloPlanesActivos) {
    this.mostrarSoloPlanesActivos = mostrarSoloPlanesActivos;
  }

  public String getSeparadorPlanes() {
    return "!;!";
  }

  public String getSeparadorRuta() {
    return "!#!";
  }

  public String getCodigoPlanEliminado() {
    return "!ELIMINADO!";
  }

  public void clear() {
    setNombreForma(null);
    setNombreCampoValor(null);
    setNombreCampoOculto(null);
    setSeleccionados(null);
    setValoresSeleccionados(null);
    setFuncionCierre(null);
    this.cambioOrganizacion = false;
    this.organizacionSeleccionadaId = null;
    this.permitirCambiarOrganizacion = new Boolean(false);
    this.nombreCampoRutasCompletas = null;
    this.panelSeleccionado = "panelPlanes";
    this.rutaCompletaOrganizacion = null;
    this.iniciado = new Boolean(false);
    this.mostrarSoloPlanesActivos = new Boolean(false);
  }
}