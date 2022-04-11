package com.visiongc.app.strategos.plancuentas.model;

import com.visiongc.framework.model.UsuarioGrupo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GrupoMascaraCodigoPlanCuentas
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private GrupoMascaraCodigoPlanCuentasPK pk;
  private String mascara;
  private String nombre;
  private MascaraCodigoPlanCuentas madre;
  
  public GrupoMascaraCodigoPlanCuentas(GrupoMascaraCodigoPlanCuentasPK pk, String mascara, String nombre)
  {
    this.pk = pk;
    this.mascara = mascara;
    this.nombre = nombre;
  }
  

  public GrupoMascaraCodigoPlanCuentas() {}
  

  public GrupoMascaraCodigoPlanCuentasPK getPk()
  {
    return pk;
  }
  
  public void setPk(GrupoMascaraCodigoPlanCuentasPK pk) {
    this.pk = pk;
  }
  
  public String getMascara() {
    return mascara;
  }
  
  public void setMascara(String mascara) {
    this.mascara = mascara;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public MascaraCodigoPlanCuentas getMadre() {
    return madre;
  }
  
  public void setMadre(MascaraCodigoPlanCuentas madre) {
    this.madre = madre;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof UsuarioGrupo))
      return false;
    UsuarioGrupo castOther = (UsuarioGrupo)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
