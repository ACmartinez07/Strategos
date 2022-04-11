package com.visiongc.app.strategos.web.struts.presentaciones.celdas.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarCeldasForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private Integer numeroPagina;
  private Long paginaId;
  private Byte filasPagina;
  private Byte columnasPagina;
  private String nombreVista;
  private String seleccionados;
  private String bloqueados;

  public Long getPaginaId()
  {
    return this.paginaId;
  }

  public void setPaginaId(Long paginaId) {
    this.paginaId = paginaId;
  }

  public String getFiltroNombre() {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public Integer getNumeroPagina() {
    return this.numeroPagina;
  }

  public void setNumeroPagina(Integer numeroPagina) {
    this.numeroPagina = numeroPagina;
  }

  public Byte getFilasPagina() {
    return this.filasPagina;
  }

  public void setFilasPagina(Byte filasPagina) {
    this.filasPagina = filasPagina;
  }

  public Byte getColumnasPagina() {
    return this.columnasPagina;
  }

  public void setColumnasPagina(Byte columnasPagina) {
    this.columnasPagina = columnasPagina;
  }

  public String getNombreVista() {
    return this.nombreVista;
  }

  public void setNombreVista(String nombreVista) {
    this.nombreVista = nombreVista;
  }

  public String getSeleccionados() {
    return this.seleccionados;
  }

  public void setSeleccionados(String seleccionados) {
    this.seleccionados = seleccionados;
  }

  public String getBloqueados() {
    return this.bloqueados;
  }

  public void setBloqueados(String bloqueados) {
    this.bloqueados = bloqueados;
  }
}