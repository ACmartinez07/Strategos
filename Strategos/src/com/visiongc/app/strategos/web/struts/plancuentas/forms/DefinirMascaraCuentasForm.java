package com.visiongc.app.strategos.web.struts.plancuentas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Set;

public class DefinirMascaraCuentasForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long mascaraId;
  private Integer niveles;
  private String mascara;
  private Set gruposMascaraCodigoPlanCuentas;

  public Long getMascaraId()
  {
    return this.mascaraId;
  }

  public void setMascaraId(Long mascaraId) {
    this.mascaraId = mascaraId;
  }

  public Integer getNiveles() {
    return this.niveles;
  }

  public void setNiveles(Integer niveles) {
    this.niveles = niveles;
  }

  public String getMascara() {
    return this.mascara;
  }

  public void setMascara(String mascara) {
    this.mascara = mascara;
  }

  public Set getGruposMascaraCodigoPlanCuentas() {
    return this.gruposMascaraCodigoPlanCuentas;
  }

  public void setGruposMascaraCodigoPlanCuentas(Set gruposMascaraCodigoPlanCuentas) {
    this.gruposMascaraCodigoPlanCuentas = gruposMascaraCodigoPlanCuentas;
  }

  public void clear() {
    this.mascaraId = null;
    this.niveles = null;
    this.mascara = null;
    this.gruposMascaraCodigoPlanCuentas = null;
    setBloqueado(new Boolean(false));
  }
}