package com.visiongc.app.strategos.explicaciones.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoExplicacion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private MemoExplicacionPK pk;
  private String memo;
  private Explicacion explicacion;
  
  public MemoExplicacion(MemoExplicacionPK pk, String memo, Explicacion explicacion)
  {
    this.pk = pk;
    this.memo = memo;
    this.explicacion = explicacion;
  }
  

  public MemoExplicacion() {}
  

  public MemoExplicacion(MemoExplicacionPK pk)
  {
    this.pk = pk;
  }
  
  public MemoExplicacionPK getPk() {
    return pk;
  }
  
  public void setPk(MemoExplicacionPK pk) {
    this.pk = pk;
  }
  
  public String getMemo() {
    return memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public Explicacion getExplicacion() {
    return explicacion;
  }
  
  public void setExplicacion(Explicacion explicacion) {
    this.explicacion = explicacion;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MemoExplicacion))
      return false;
    MemoExplicacion castOther = (MemoExplicacion)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
