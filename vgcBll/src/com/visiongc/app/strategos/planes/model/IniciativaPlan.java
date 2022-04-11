package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;









public class IniciativaPlan
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private IniciativaPlanPK pk;
  private Iniciativa iniciativa;
  private Plan plan;
  
  public IniciativaPlan(IniciativaPlanPK pk, Iniciativa iniciativa, Plan plan)
  {
    this.pk = pk;
    this.iniciativa = iniciativa;
    this.plan = plan;
  }
  

  public IniciativaPlan() {}
  

  public IniciativaPlanPK getPk()
  {
    return pk;
  }
  
  public void setPk(IniciativaPlanPK pk)
  {
    this.pk = pk;
  }
  
  public Iniciativa getIniciativa()
  {
    return iniciativa;
  }
  
  public void setIniciativa(Iniciativa iniciativa)
  {
    this.iniciativa = iniciativa;
  }
  
  public Plan getPlan()
  {
    return plan;
  }
  
  public void setPlan(Plan plan)
  {
    this.plan = plan;
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof IniciativaPlan)) {
      return false;
    }
    IniciativaPlan castOther = (IniciativaPlan)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
}
