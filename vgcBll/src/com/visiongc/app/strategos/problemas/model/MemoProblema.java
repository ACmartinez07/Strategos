package com.visiongc.app.strategos.problemas.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoProblema
  implements Serializable
{
  static final long serialVersionUID = 0L;
  public static final byte TIPO_DESCRIPCION = 0;
  public static final byte TIPO_ESTRATEGIA_DE_SOLUCION = 1;
  public static final byte TIPO_CONCLUSIONES_RESULTADOS = 2;
  public static final byte TIPO_ESPECIFICACION = 3;
  private MemoProblemaPK pk;
  private String memo;
  private Problema problema;
  
  public MemoProblema(MemoProblemaPK pk, String memo)
  {
    this.pk = pk;
    this.memo = memo;
  }
  

  public MemoProblema() {}
  

  public MemoProblema(MemoProblemaPK pk)
  {
    this.pk = pk;
  }
  
  public MemoProblemaPK getPk() {
    return pk;
  }
  
  public void setPk(MemoProblemaPK pk) {
    this.pk = pk;
  }
  
  public String getMemo() {
    return memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public Problema getProblema() {
    return problema;
  }
  
  public void setProblema(Problema problema) {
    this.problema = problema;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MemoProblema))
      return false;
    MemoProblema castOther = (MemoProblema)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
