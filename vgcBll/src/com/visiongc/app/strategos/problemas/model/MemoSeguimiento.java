package com.visiongc.app.strategos.problemas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoSeguimiento
  implements Serializable
{
  static final long serialVersionUID = 0L;
  public static final byte RESUMEN = 0;
  public static final byte COMENTARIO = 1;
  private MemoSeguimientoPK pk;
  private String memo;
  private Seguimiento seguimiento;
  
  public MemoSeguimiento(MemoSeguimientoPK pk, String memo)
  {
    this.pk = pk;
    this.memo = memo;
  }
  

  public MemoSeguimiento() {}
  

  public MemoSeguimientoPK getPk()
  {
    return pk;
  }
  
  public void setPk(MemoSeguimientoPK pk) {
    this.pk = pk;
  }
  
  public String getMemo() {
    return memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public Seguimiento getSeguimiento() {
    return seguimiento;
  }
  
  public void setSeguimiento(Seguimiento seguimiento) {
    this.seguimiento = seguimiento;
  }
  
  public int compareTo(Object o) {
    MemoSeguimiento or = (MemoSeguimiento)o;
    return getPk().compareTo(or.getPk());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MemoSeguimiento other = (MemoSeguimiento)obj;
    return new EqualsBuilder().append(getPk(), other.getPk()).isEquals();
  }
}
