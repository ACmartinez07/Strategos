package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import java.util.ArrayList;
import java.util.List;

public class SeleccionarIndicadorForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private Byte seleccionMultiple;
  private Byte frecuencia;
  private String nombreFrecuencia;
  private Byte frecuenciaBloqueada;
  private String filtroNombre;
  private Long filtroOrganizacionId;
  private String filtroOrganizacionNombre;
  private Long filtroPlanId;
  private String filtroPlanNombre;
  private Long filtroUnidadId;
  private String filtroUnidadNombre;
  private String filtroNaturaleza;
  private String filtroCaracteristica;
  private String filtroTipoIndicador;
  private List listaIndicadores;
  private List listaNaturalezas;
  private List listaCaracteristicas;
  private List listaTiposIndicadores;
  private Long filtroResponsableFijarMetaId;
  private String filtroResponsableFijarMetaNombre;
  private Long filtroResponsableLograrMetaId;
  private String filtroResponsableLograrMetaNombre;
  private Long filtroResponsableSeguimientoId;
  private String filtroResponsableSeguimientoNombre;
  private Boolean primeraVez;

  public Byte getSeleccionMultiple()
  {
    return this.seleccionMultiple;
  }

  public void setSeleccionMultiple(Byte seleccionMultiple) {
    this.seleccionMultiple = seleccionMultiple;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public String getNombreFrecuencia() {
    return this.nombreFrecuencia;
  }

  public void setNombreFrecuencia(String nombreFrecuencia) {
    this.nombreFrecuencia = nombreFrecuencia;
  }

  public Byte getFrecuenciaBloqueada() {
    return this.frecuenciaBloqueada;
  }

  public void setFrecuenciaBloqueada(Byte frecuenciaBloqueada) {
    this.frecuenciaBloqueada = frecuenciaBloqueada;
  }

  public String getFiltroNombre() {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public Long getFiltroOrganizacionId() {
    return this.filtroOrganizacionId;
  }

  public void setFiltroOrganizacionId(Long filtroOrganizacionId) {
    this.filtroOrganizacionId = filtroOrganizacionId;
  }

  public String getFiltroOrganizacionNombre() {
    return this.filtroOrganizacionNombre;
  }

  public void setFiltroOrganizacionNombre(String filtroOrganizacionNombre) {
    this.filtroOrganizacionNombre = filtroOrganizacionNombre;
  }

  public Long getFiltroPlanId() {
    return this.filtroPlanId;
  }

  public void setFiltroPlanId(Long filtroPlanId) {
    this.filtroPlanId = filtroPlanId;
  }

  public String getFiltroPlanNombre() {
    return this.filtroPlanNombre;
  }

  public void setFiltroPlanNombre(String filtroPlanNombre) {
    this.filtroPlanNombre = filtroPlanNombre;
  }

  public Long getFiltroUnidadId() {
    return this.filtroUnidadId;
  }

  public void setFiltroUnidadId(Long filtroUnidadId) {
    this.filtroUnidadId = filtroUnidadId;
  }

  public String getFiltroUnidadNombre() {
    return this.filtroUnidadNombre;
  }

  public void setFiltroUnidadNombre(String filtroUnidadNombre) {
    this.filtroUnidadNombre = filtroUnidadNombre;
  }

  public String getFiltroNaturaleza() {
    return this.filtroNaturaleza;
  }

  public void setFiltroNaturaleza(String filtroNaturaleza) {
    this.filtroNaturaleza = filtroNaturaleza;
  }

  public String getFiltroCaracteristica() {
    return this.filtroCaracteristica;
  }

  public void setFiltroCaracteristica(String filtroCaracteristica) {
    this.filtroCaracteristica = filtroCaracteristica;
  }

  public String getFiltroTipoIndicador() {
    return this.filtroTipoIndicador;
  }

  public void setFiltroTipoIndicador(String filtroTipoIndicador) {
    this.filtroTipoIndicador = filtroTipoIndicador;
  }

  public List getListaIndicadores() {
    return this.listaIndicadores;
  }

  public void setListaIndicadores(List listaIndicadores) {
    this.listaIndicadores = listaIndicadores;
  }

  public List getListaNaturalezas() {
    return this.listaNaturalezas;
  }

  public void setListaNaturalezas(List listaNaturalezas) {
    this.listaNaturalezas = listaNaturalezas;
  }

  public List getListaCaracteristicas() {
    return this.listaCaracteristicas;
  }

  public void setListaCaracteristicas(List listaCaracteristicas) {
    this.listaCaracteristicas = listaCaracteristicas;
  }

  public List getListaTiposIndicadores() {
    return this.listaTiposIndicadores;
  }

  public void setListaTiposIndicadores(List listaTiposIndicadores) {
    this.listaTiposIndicadores = listaTiposIndicadores;
  }

  public Long getFiltroResponsableFijarMetaId() {
    return this.filtroResponsableFijarMetaId;
  }

  public void setFiltroResponsableFijarMetaId(Long filtroResponsableFijarMetaId) {
    this.filtroResponsableFijarMetaId = filtroResponsableFijarMetaId;
  }

  public String getFiltroResponsableFijarMetaNombre() {
    return this.filtroResponsableFijarMetaNombre;
  }

  public void setFiltroResponsableFijarMetaNombre(String filtroResponsableFijarMetaNombre) {
    this.filtroResponsableFijarMetaNombre = filtroResponsableFijarMetaNombre;
  }

  public Long getFiltroResponsableLograrMetaId() {
    return this.filtroResponsableLograrMetaId;
  }

  public void setFiltroResponsableLograrMetaId(Long filtroResponsableLograrMetaId) {
    this.filtroResponsableLograrMetaId = filtroResponsableLograrMetaId;
  }

  public String getFiltroResponsableLograrMetaNombre() {
    return this.filtroResponsableLograrMetaNombre;
  }

  public void setFiltroResponsableLograrMetaNombre(String filtroResponsableLograrMetaNombre) {
    this.filtroResponsableLograrMetaNombre = filtroResponsableLograrMetaNombre;
  }

  public Long getFiltroResponsableSeguimientoId() {
    return this.filtroResponsableSeguimientoId;
  }

  public void setFiltroResponsableSeguimientoId(Long filtroResponsableSeguimientoId) {
    this.filtroResponsableSeguimientoId = filtroResponsableSeguimientoId;
  }

  public String getFiltroResponsableSeguimientoNombre() {
    return this.filtroResponsableSeguimientoNombre;
  }

  public void setFiltroResponsableSeguimientoNombre(String filtroResponsableSeguimientoNombre) {
    this.filtroResponsableSeguimientoNombre = filtroResponsableSeguimientoNombre;
  }

  public Boolean getPrimeraVez() {
    return this.primeraVez;
  }

  public void setPrimeraVez(Boolean primeraVez) {
    this.primeraVez = primeraVez;
  }

  public void clear()
  {
    this.filtroNombre = null;
    this.filtroOrganizacionId = null;
    this.filtroOrganizacionNombre = null;
    this.filtroPlanId = null;
    this.filtroPlanNombre = null;
    this.filtroUnidadId = null;
    this.filtroUnidadNombre = null;
    this.filtroNaturaleza = null;
    this.filtroCaracteristica = null;
    this.filtroTipoIndicador = null;

    this.listaIndicadores = new ArrayList();
    this.listaNaturalezas = new ArrayList();
    this.listaCaracteristicas = new ArrayList();
    this.listaTiposIndicadores = new ArrayList();

    this.filtroResponsableFijarMetaId = null;
    this.filtroResponsableFijarMetaNombre = null;
    this.filtroResponsableLograrMetaId = null;
    this.filtroResponsableLograrMetaNombre = null;
    this.filtroResponsableSeguimientoId = null;
    this.filtroResponsableSeguimientoNombre = null;

    this.primeraVez = new Boolean(false);
  }
}