package com.visiongc.app.strategos.web.struts.planes.metas.forms;

import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import org.apache.struts.validator.ValidatorActionForm;

public class EditarMetasParcialesForm extends ValidatorActionForm
{
  static final long serialVersionUID = 0L;
  private MetaAnualParciales metaAnualParciales;
  private Boolean cerrarVentana;
  private Long indicadorId;
  private Long serieId;
  private Integer ano;
  private Integer periodo;
  private String valor;
  private Integer numeroPeriodos;
  private String nombreIndicador;
  private Byte numeroDecimales;
  private Byte tipoCorte;
  private Byte tipoCargaMedicion;
  private Byte tipoCargaMeta;
  private String total;
  private String valorTotalMetasParciales;
  private boolean bloquear;

  public Integer getAno()
  {
    return this.ano;
  }

  public void setAno(Integer ano) {
    this.ano = ano;
  }

  public String getValor() {
    return this.valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public Integer getNumeroPeriodos() {
    return this.numeroPeriodos;
  }

  public void setNumeroPeriodos(Integer numeroPeriodos) {
    this.numeroPeriodos = numeroPeriodos;
  }

  public String getNombreIndicador() {
    return this.nombreIndicador;
  }

  public void setNombreIndicador(String nombreIndicador) {
    this.nombreIndicador = nombreIndicador;
  }

  public Long getIndicadorId() {
    return this.indicadorId;
  }

  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }

  public Long getSerieId() {
    return this.serieId;
  }

  public void setSerieId(Long serieId) {
    this.serieId = serieId;
  }

  public Integer getPeriodo() {
    return this.periodo;
  }

  public void setPeriodo(Integer periodo) {
    this.periodo = periodo;
  }

  public MetaAnualParciales getMetaAnualParciales() {
    return this.metaAnualParciales;
  }

  public void setMetaAnualParciales(MetaAnualParciales metaAnualParciales) {
    this.metaAnualParciales = metaAnualParciales;
  }

  public Boolean getCerrarVentana() {
    return this.cerrarVentana;
  }

  public void setCerrarVentana(Boolean cerrarVentana) {
    this.cerrarVentana = cerrarVentana;
  }

  public Byte getNumeroDecimales() {
    return this.numeroDecimales;
  }

  public void setNumeroDecimales(Byte numeroDecimales) {
    this.numeroDecimales = numeroDecimales;
  }

  public Byte getTipoCargaMeta() {
    return this.tipoCargaMeta;
  }

  public void setTipoCargaMeta(Byte tipoCargaMeta) {
    this.tipoCargaMeta = tipoCargaMeta;
  }

  public Byte getIndicadorTipoCorteLongitudinal() {
    return TipoCorte.getTipoCorteLongitudinal();
  }

  public Byte getIndicadorTipoCargaMedicionEnElPeriodo() {
    return TipoMedicion.getTipoMedicionEnPeriodo();
  }

  public Byte getTipoCorte() {
    return this.tipoCorte;
  }

  public void setTipoCorte(Byte tipoCorte) {
    this.tipoCorte = tipoCorte;
  }

  public Byte getTipoCargaMedicion() {
    return this.tipoCargaMedicion;
  }

  public void setTipoCargaMedicion(Byte tipoCargaMedicion) {
    this.tipoCargaMedicion = tipoCargaMedicion;
  }

  public String getTotal() {
    return this.total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getValorTotalMetasParciales() {
    return this.valorTotalMetasParciales;
  }

  public void setValorTotalMetasParciales(String valorTotalMetasParciales) {
    this.valorTotalMetasParciales = valorTotalMetasParciales;
  }

	public boolean getBloquear() 
	{
	  return this.bloquear;
	}

	public void setBloquear(boolean bloquear) 
	{
		this.bloquear = bloquear;
	}
}