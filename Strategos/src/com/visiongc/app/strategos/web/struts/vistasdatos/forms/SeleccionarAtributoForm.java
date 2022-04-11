package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import java.util.List;

public class SeleccionarAtributoForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private Byte seleccionMultiple;
  List listaAtributos;

  public Byte getSeleccionMultiple()
  {
    return this.seleccionMultiple;
  }

  public void setSeleccionMultiple(Byte seleccionMultiple) {
    this.seleccionMultiple = seleccionMultiple;
  }

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public List getListaAtributos() {
    return this.listaAtributos;
  }

  public void setListaAtributos(List listaAtributos) {
    this.listaAtributos = listaAtributos;
  }

  public void clear()
  {
  }
}