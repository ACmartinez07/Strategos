package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IniciativaPerspectiva
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IniciativaPerspectivaPK pk;
  private Perspectiva perspectiva;
  private Iniciativa iniciativa;
  
  public IniciativaPerspectiva(IniciativaPerspectivaPK pk)
  {
    this.pk = pk;
  }
  

  public IniciativaPerspectiva() {}
  

  public IniciativaPerspectivaPK getPk()
  {
    return pk;
  }
  
  public void setPk(IniciativaPerspectivaPK pk) {
    this.pk = pk;
  }
  
  public Iniciativa getIniciativa() {
    return iniciativa;
  }
  
  public void setIniciativa(Iniciativa iniciativa) {
    this.iniciativa = iniciativa;
  }
  
  public Perspectiva getPerspectiva() {
    return perspectiva;
  }
  
  public void setPerspectiva(Perspectiva perspectiva) {
    this.perspectiva = perspectiva;
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof IniciativaPerspectiva))
      return false;
    IniciativaPerspectiva castOther = (IniciativaPerspectiva)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
}
