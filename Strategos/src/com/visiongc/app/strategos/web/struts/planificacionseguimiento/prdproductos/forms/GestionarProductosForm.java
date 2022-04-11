package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms;

import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaProducto;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarProductosForm extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  public static final int GESTIONAR_PRODUCTOS_AUTONOMO = 1;
  public static final int GESTIONAR_PRODUCTOS_INICIATIVA = 2;
  private String filtroNombre;
  private Long iniciativaId;
  private String atributoOrdenProductos;
  private String tipoOrdenProductos;
  private int paginaProductos;
  private String nombrePlan;
  private String nombreIniciativa;
  private int llamada;

  public int getTipoLlamadaGestionarProductosAutonomo()
  {
    return 1;
  }

  public int getTipoLlamadaGestionarProductosIniciativa() {
    return 2;
  }

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public Long getIniciativaId() {
    return this.iniciativaId;
  }

  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }

  public String getAtributoOrdenProductos() {
    return this.atributoOrdenProductos;
  }

  public void setAtributoOrdenProductos(String atributoOrdenProductos) {
    this.atributoOrdenProductos = atributoOrdenProductos;
  }

  public String getTipoOrdenProductos() {
    return this.tipoOrdenProductos;
  }

  public void setTipoOrdenProductos(String tipoOrdenProductos) {
    this.tipoOrdenProductos = tipoOrdenProductos;
  }

  public int getPaginaProductos() {
    return this.paginaProductos;
  }

  public void setPaginaProductos(int paginaProductos) {
    this.paginaProductos = paginaProductos;
  }

  public String getNombrePlan() {
    return this.nombrePlan;
  }

  public void setNombrePlan(String nombrePlan) {
    this.nombrePlan = nombrePlan;
  }

  public String getNombreIniciativa() {
    return this.nombreIniciativa;
  }

  public void setNombreIniciativa(String nombreIniciativa) {
    this.nombreIniciativa = nombreIniciativa;
  }

  public int getLlamada() {
    return this.llamada;
  }

  public void setLlamada(int llamada) {
    this.llamada = llamada;
  }

  public Byte getAlertaProductoEnEsperaComienzo() {
    return AlertaProducto.getAlertaEnEsperaComienzo();
  }

  public Byte getAlertaProductoEntregado() {
    return AlertaProducto.getAlertaEntregado();
  }

  public Byte getAlertaProductoNoEntregado() {
    return AlertaProducto.getAlertaNoEntregado();
  }
}