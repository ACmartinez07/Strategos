package com.visiongc.app.strategos.plancuentas.model;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MascaraCodigoPlanCuentas
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long mascaraId;
  private Integer niveles;
  private String mascara;
  private Set gruposMascara;
  
  public MascaraCodigoPlanCuentas(Long mascaraId, Integer niveles, String mascara, Set gruposMascara)
  {
    this.mascaraId = mascaraId;
    this.niveles = niveles;
    this.mascara = mascara;
    this.gruposMascara = gruposMascara;
  }
  

  public MascaraCodigoPlanCuentas() {}
  
  public Long getMascaraId()
  {
    return mascaraId;
  }
  
  public void setMascaraId(Long mascaraId) {
    this.mascaraId = mascaraId;
  }
  
  public Integer getNiveles() {
    return niveles;
  }
  
  public void setNiveles(Integer niveles) {
    this.niveles = niveles;
  }
  
  public String getMascara() {
    return mascara;
  }
  
  public void setMascara(String mascara) {
    this.mascara = mascara;
  }
  
  public Set getGruposMascara() {
    return gruposMascara;
  }
  
  public void setGruposMascara(Set gruposMascara) {
    this.gruposMascara = gruposMascara;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("mascaraId", getMascaraId()).toString();
  }
}
