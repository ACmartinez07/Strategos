package com.visiongc.app.strategos.web.struts.planes.metas.forms;

import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.util.VgcFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.struts.validator.ValidatorActionForm;

public class EditarMetasForm extends ValidatorActionForm
{
  static final long serialVersionUID = 0L;
  private Long organizacionId;
  private String nombreOrganizacion;
  private Long planId;
  private String nombrePlan;
  private Long perspectivaId;
  private Boolean verIndicadoresLogroPlan;
  private List metasIndicadores;
  private boolean establecerMetasSoloIndicadoresSeleccionados;
  private Integer anoDesde;
  private Integer anoHasta;
  private List listaAnos;
  private List anos;
  private String anchoMatriz;
  private boolean mostrarUnidadMedida;
  private boolean mostrarCodigoEnlace;
  private List frecuencias;
  private Byte frecuencia;
  private Integer numeroMaximoPeriodos;
  private List periodos;
  private Integer periodoDesde;
  private Integer periodoHasta;
  private String fechaDesde;
  private String fechaHasta;
  private boolean bloquear;

  public Long getOrganizacionId()
  {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public Long getPlanId() {
    return this.planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public String getNombrePlan() {
    return this.nombrePlan;
  }

  public void setNombrePlan(String nombrePlan) {
    this.nombrePlan = nombrePlan;
  }

  public Long getPerspectivaId() {
    return this.perspectivaId;
  }

  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }

  public Boolean getVerIndicadoresLogroPlan() {
    return this.verIndicadoresLogroPlan;
  }

  public void setVerIndicadoresLogroPlan(Boolean verIndicadoresLogroPlan) {
    this.verIndicadoresLogroPlan = verIndicadoresLogroPlan;
  }

  public List getMetasIndicadores() {
    return this.metasIndicadores;
  }

  public void setMetasIndicadores(List metasIndicadores) {
    this.metasIndicadores = metasIndicadores;
  }

  public List getFrecuencias() {
    return this.frecuencias;
  }

  public void setFrecuencias(List frecuencias) {
    this.frecuencias = frecuencias;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public List getAnos() {
    return this.anos;
  }

  public void setAnos(List anos) {
    this.anos = anos;
  }

  public Integer getAnoDesde() {
    return this.anoDesde;
  }

  public void setAnoDesde(Integer anoDesde) {
    this.anoDesde = anoDesde;
  }

  public Integer getAnoHasta() {
    return this.anoHasta;
  }

  public void setAnoHasta(Integer anoHasta) {
    this.anoHasta = anoHasta;
  }

  public Integer getNumeroMaximoPeriodos() {
    return this.numeroMaximoPeriodos;
  }

  public void setNumeroMaximoPeriodos(Integer numeroMaximoPeriodos) {
    this.numeroMaximoPeriodos = numeroMaximoPeriodos;
  }

  public List getPeriodos() {
    return this.periodos;
  }

  public void setPeriodos(List periodos) {
    this.periodos = periodos;
  }

  public Integer getPeriodoDesde() {
    return this.periodoDesde;
  }

  public void setPeriodoDesde(Integer periodoDesde) {
    this.periodoDesde = periodoDesde;
  }

  public Integer getPeriodoHasta() {
    return this.periodoHasta;
  }

  public void setPeriodoHasta(Integer periodoHasta) {
    this.periodoHasta = periodoHasta;
  }

  public String getFechaDesde() {
    return this.fechaDesde;
  }

  public void setFechaDesde(String fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public String getFechaHasta() {
    return this.fechaHasta;
  }

  public void setFechaHasta(String fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public boolean isEstablecerMetasSoloIndicadoresSeleccionados() {
    return this.establecerMetasSoloIndicadoresSeleccionados;
  }

  public void setEstablecerMetasSoloIndicadoresSeleccionados(boolean establecerMetasSoloIndicadoresSeleccionados) {
    this.establecerMetasSoloIndicadoresSeleccionados = establecerMetasSoloIndicadoresSeleccionados;
  }

  public List getListaAnos() {
    return this.listaAnos;
  }

  public void setListaAnos(List listaAnos) {
    this.listaAnos = listaAnos;
  }

  public boolean isMostrarUnidadMedida() {
    return this.mostrarUnidadMedida;
  }

  public void setMostrarUnidadMedida(boolean mostrarUnidadMedida) {
    this.mostrarUnidadMedida = mostrarUnidadMedida;
  }

  public boolean isMostrarCodigoEnlace() {
    return this.mostrarCodigoEnlace;
  }

  public void setMostrarCodigoEnlace(boolean mostrarCodigoEnlace) {
    this.mostrarCodigoEnlace = mostrarCodigoEnlace;
  }

  public String getAnchoMatriz() {
    return this.anchoMatriz;
  }

  public void setAnchoMatriz(String anchoMatriz) {
    this.anchoMatriz = anchoMatriz;
  }

  public Long getSerieId() {
    return SerieTiempo.getSerieMetaId();
  }

	public boolean getBloquear() 
	{
	  return this.bloquear;
	}

	public void setBloquear(boolean bloquear) 
	{
		this.bloquear = bloquear;
	}
  
  public void clear() {
    this.organizacionId = null;
    this.nombreOrganizacion = null;
    this.planId = null;
    this.nombrePlan = null;
    this.perspectivaId = null;
    this.verIndicadoresLogroPlan = new Boolean(false);
    this.establecerMetasSoloIndicadoresSeleccionados = true;
    this.metasIndicadores = new ArrayList();
    Calendar fecha = Calendar.getInstance();
    this.anoDesde = new Integer(fecha.get(1));
    this.anoHasta = new Integer(fecha.get(1));
    this.listaAnos = new ArrayList();
    this.mostrarUnidadMedida = true;
    this.mostrarCodigoEnlace = true;
    this.anchoMatriz = null;
    this.anos = null;
    this.bloquear = false;

    fecha.set(2, 0);
    fecha.set(5, 1);
    this.fechaDesde = VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta");
    fecha.set(2, 11);
    fecha.set(5, 31);
    this.fechaHasta = VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta");
    this.frecuencia = null;
    this.frecuencias = null;
    this.numeroMaximoPeriodos = null;
    this.periodoDesde = null;
    this.periodoHasta = null;
    this.periodos = null;
  }
}