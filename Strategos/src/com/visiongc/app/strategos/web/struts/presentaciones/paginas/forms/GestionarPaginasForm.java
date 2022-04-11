package com.visiongc.app.strategos.web.struts.presentaciones.paginas.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarPaginasForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroDescripcion;
  private String nombreOrganizacion;
  private String nombreVista;
  private int paginaSeleccionadaPaginas;
  private String atributoOrdenPaginas;
  private String tipoOrdenPaginas;
  private String seleccionadosPaginas;
  private String bloqueadosPaginas;

  public String getFiltroDescripcion()
  {
    return this.filtroDescripcion;
  }

  public void setFiltroDescripcion(String filtroDescripcion) {
    this.filtroDescripcion = filtroDescripcion;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public String getNombreVista() {
    return this.nombreVista;
  }

  public void setNombreVista(String nombreVista) {
    this.nombreVista = nombreVista;
  }

  public int getPaginaSeleccionadaPaginas() {
    return this.paginaSeleccionadaPaginas;
  }

  public void setPaginaSeleccionadaPaginas(int paginaSeleccionadaPaginas) {
    this.paginaSeleccionadaPaginas = paginaSeleccionadaPaginas;
  }

  public String getAtributoOrdenPaginas() {
    return this.atributoOrdenPaginas;
  }

  public void setAtributoOrdenPaginas(String atributoOrdenPaginas) {
    this.atributoOrdenPaginas = atributoOrdenPaginas;
  }

  public String getTipoOrdenPaginas() {
    return this.tipoOrdenPaginas;
  }

  public void setTipoOrdenPaginas(String tipoOrdenPaginas) {
    this.tipoOrdenPaginas = tipoOrdenPaginas;
  }

  public String getSeleccionadosPaginas() {
    return this.seleccionadosPaginas;
  }

  public void setSeleccionadosPaginas(String seleccionadosPaginas) {
    this.seleccionadosPaginas = seleccionadosPaginas;
  }

  public String getBloqueadosPaginas()
  {
    return this.bloqueadosPaginas;
  }

  public void setBloqueadosPaginas(String bloqueadosPaginas) {
    this.bloqueadosPaginas = bloqueadosPaginas;
  }
}