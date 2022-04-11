package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms;

import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaProducto;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Calendar;

public class RegistrarSeguimientoForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  public static final String SEPARADOR_SEG_PRD = "#";
  private Byte pasoActual;
  private Long iniciativaId;
  private String iniciativaNombre;
  private Long organizacionId;
  private Byte frecuencia;
  private PaginaLista paginaSeguimientos;
  private String seguimientoSeleccionadoId;
  private Boolean actualizarSeguimientoSeleccionado;
  private Boolean eliminarSeguimientoSeleccionado;
  private String fecha;
  private Integer ano;
  private Integer periodo;
  private String nombrePeriodo;
  private PaginaLista paginaProductos;
  private Long productoSeleccionadoId;
  private PaginaLista paginaSeguimientosProductos;
  private String seguimientosProductos;
  private Byte alerta;
  private String seguimiento;

  public Byte getPasoActual()
  {
    return this.pasoActual;
  }

  public void setPasoActual(Byte pasoActual) {
    this.pasoActual = pasoActual;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public Long getIniciativaId() {
    return this.iniciativaId;
  }

  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }

  public String getIniciativaNombre() {
    return this.iniciativaNombre;
  }

  public void setIniciativaNombre(String iniciativaNombre) {
    this.iniciativaNombre = iniciativaNombre;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public PaginaLista getPaginaSeguimientos() {
    return this.paginaSeguimientos;
  }

  public void setPaginaSeguimientos(PaginaLista paginaSeguimientos) {
    this.paginaSeguimientos = paginaSeguimientos;
  }

  public String getSeguimientoSeleccionadoId() {
    return this.seguimientoSeleccionadoId;
  }

  public void setSeguimientoSeleccionadoId(String seguimientoSeleccionadoId) {
    this.seguimientoSeleccionadoId = seguimientoSeleccionadoId;
  }

  public Boolean getActualizarSeguimientoSeleccionado() {
    return this.actualizarSeguimientoSeleccionado;
  }

  public void setActualizarSeguimientoSeleccionado(Boolean actualizarSeguimientoSeleccionado) {
    this.actualizarSeguimientoSeleccionado = actualizarSeguimientoSeleccionado;
  }

  public Boolean getEliminarSeguimientoSeleccionado() {
    return this.eliminarSeguimientoSeleccionado;
  }

  public void setEliminarSeguimientoSeleccionado(Boolean eliminarSeguimientoSeleccionado) {
    this.eliminarSeguimientoSeleccionado = eliminarSeguimientoSeleccionado;
  }

  public String getFecha() {
    return this.fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public Integer getAno() {
    return this.ano;
  }

  public void setAno(Integer ano) {
    this.ano = ano;
  }

  public Integer getPeriodo() {
    return this.periodo;
  }

  public void setPeriodo(Integer periodo) {
    this.periodo = periodo;
  }

  public String getNombrePeriodo() {
    return this.nombrePeriodo;
  }

  public void setNombrePeriodo(String nombrePeriodo) {
    this.nombrePeriodo = nombrePeriodo;
  }

  public PaginaLista getPaginaProductos() {
    return this.paginaProductos;
  }

  public void setPaginaProductos(PaginaLista paginaProductos) {
    this.paginaProductos = paginaProductos;
  }

  public Long getProductoSeleccionadoId() {
    return this.productoSeleccionadoId;
  }

  public void setProductoSeleccionadoId(Long productoSeleccionadoId) {
    this.productoSeleccionadoId = productoSeleccionadoId;
  }

  public PaginaLista getPaginaSeguimientosProductos() {
    return this.paginaSeguimientosProductos;
  }

  public void setPaginaSeguimientosProductos(PaginaLista paginaSeguimientosProductos) {
    this.paginaSeguimientosProductos = paginaSeguimientosProductos;
  }

  public String getSeguimientosProductos() {
    return this.seguimientosProductos;
  }

  public void setSeguimientosProductos(String seguimientosProductos) {
    this.seguimientosProductos = seguimientosProductos;
  }

  public String getSeguimiento() {
    return this.seguimiento;
  }

  public void setSeguimiento(String seguimiento) {
    this.seguimiento = seguimiento;
  }

  public Byte getAlerta() {
    return this.alerta;
  }

  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
  }

  public String getNombreAlertaProductoEnEsperaComienzo() {
    return AlertaProducto.getNombre(AlertaProducto.getAlertaEnEsperaComienzo().byteValue());
  }

  public String getNombreAlertaProductoEntregado() {
    return AlertaProducto.getNombre(AlertaProducto.getAlertaEntregado().byteValue());
  }

  public String getNombreAlertaProductoNoEntregado() {
    return AlertaProducto.getNombre(AlertaProducto.getAlertaNoEntregado().byteValue());
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

  public String getNombreAlertaIniciativaProductoEnEsperaComienzo() {
    return AlertaIniciativaProducto.getNombre(AlertaIniciativaProducto.getAlertaEnEsperaComienzo().byteValue());
  }

  public String getNombreAlertaIniciativaProductoVerde() {
    return AlertaIniciativaProducto.getNombre(AlertaIniciativaProducto.getAlertaVerde().byteValue());
  }

  public String getNombreAlertaIniciativaProductoAmarilla() {
    return AlertaIniciativaProducto.getNombre(AlertaIniciativaProducto.getAlertaAmarilla().byteValue());
  }

  public String getNombreAlertaIniciativaProductoRoja() {
    return AlertaIniciativaProducto.getNombre(AlertaIniciativaProducto.getAlertaRoja().byteValue());
  }

  public Byte getAlertaIniciativaProductoEnEsperaComienzo() {
    return AlertaIniciativaProducto.getAlertaEnEsperaComienzo();
  }

  public Byte getAlertaIniciativaProductoVerde() {
    return AlertaIniciativaProducto.getAlertaVerde();
  }

  public Byte getAlertaIniciativaProductoAmarilla() {
    return AlertaIniciativaProducto.getAlertaAmarilla();
  }

  public Byte getAlertaIniciativaProductoRoja() {
    return AlertaIniciativaProducto.getAlertaRoja();
  }

  public String getSeparadorSegPrd() {
    return "#";
  }

  public void clear() {
    Calendar ahora = Calendar.getInstance();

    this.pasoActual = new Byte((byte) 1);
    this.iniciativaId = null;
    this.iniciativaNombre = null;
    this.organizacionId = null;
    this.frecuencia = null;
    this.paginaSeguimientos = null;
    this.seguimientoSeleccionadoId = null;
    this.actualizarSeguimientoSeleccionado = new Boolean(false);
    this.eliminarSeguimientoSeleccionado = new Boolean(false);
    this.fecha = VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta");
    this.ano = new Integer(ahora.get(1));
    this.periodo = null;
    this.nombrePeriodo = null;
    this.paginaProductos = null;
    this.productoSeleccionadoId = null;
    this.paginaSeguimientosProductos = null;
    this.seguimientosProductos = null;
    this.alerta = AlertaIniciativaProducto.getAlertaEnEsperaComienzo();
    this.seguimiento = null;
  }
}