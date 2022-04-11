package com.visiongc.app.strategos.planes.model.util;

import com.visiongc.app.strategos.planes.model.Meta;

public class MetaAnualParciales
{
  private Meta metaAnual;
  private java.util.List metasParciales;
  private Integer numeroPeriodos;
  private Byte tipoCargaMeta;
  
  public MetaAnualParciales() {}
  
  public Meta getMetaAnual() {
    return metaAnual;
  }
  
  public void setMetaAnual(Meta metaAnual) {
    this.metaAnual = metaAnual;
  }
  
  public java.util.List getMetasParciales() {
    return metasParciales;
  }
  
  public void setMetasParciales(java.util.List metasParciales) {
    this.metasParciales = metasParciales;
  }
  
  public Integer getNumeroPeriodos() {
    return numeroPeriodos;
  }
  
  public void setNumeroPeriodos(Integer numeroPeriodos) {
    this.numeroPeriodos = numeroPeriodos;
  }
  
  public Byte getTipoCargaMeta() {
    return tipoCargaMeta;
  }
  
  public void setTipoCargaMeta(Byte tipoCargaMeta) {
    this.tipoCargaMeta = tipoCargaMeta;
  }
}
