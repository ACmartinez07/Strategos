package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import java.util.List;

public class SeleccionarOrganizacionForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private Byte seleccionMultiple;
  private List listaOrganizaciones;

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public List getListaOrganizaciones() {
    return this.listaOrganizaciones;
  }

  public void setListaOrganizaciones(List listaOrganizaciones) {
    this.listaOrganizaciones = listaOrganizaciones;
  }

  public Byte getSeleccionMultiple() {
    return this.seleccionMultiple;
  }

  public void setSeleccionMultiple(Byte seleccionMultiple) {
    this.seleccionMultiple = seleccionMultiple;
  }

  public void clear()
  {
  }
}