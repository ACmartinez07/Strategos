package com.visiongc.app.strategos.responsables.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GrupoResponsable
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private GrupoResponsablePK pk;
  
  public GrupoResponsable(GrupoResponsablePK pk)
  {
    this.pk = pk;
  }
  

  public GrupoResponsable() {}
  
  public GrupoResponsablePK getPk()
  {
    return pk;
  }
  
  public void setPk(GrupoResponsablePK pk) {
    this.pk = pk;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof GrupoResponsable))
      return false;
    GrupoResponsable castOther = (GrupoResponsable)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
