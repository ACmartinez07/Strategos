package com.visiongc.app.strategos.problemas.model;

import com.visiongc.app.strategos.responsables.model.Responsable;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ResponsableAccion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  public static final byte RESPONSABLE = 1;
  public static final byte RESPONSABLE_AUXILIAR = 2;
  private ResponsableAccionPK pk;
  private Byte tipo;
  private Accion accion;
  private Responsable responsable;
  
  public ResponsableAccion(ResponsableAccionPK pk, Byte tipo)
  {
    this.pk = pk;
    this.tipo = tipo;
  }
  

  public ResponsableAccion() {}
  

  public ResponsableAccionPK getPk()
  {
    return pk;
  }
  
  public void setPk(ResponsableAccionPK pk) {
    this.pk = pk;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public Accion getAccion() {
    return accion;
  }
  
  public void setAccion(Accion accion) {
    this.accion = accion;
  }
  
  public Responsable getResponsable() {
    return responsable;
  }
  
  public void setResponsable(Responsable responsable) {
    this.responsable = responsable;
  }
  
  public int compareTo(Object o) {
    ResponsableAccion or = (ResponsableAccion)o;
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
    ResponsableAccion other = (ResponsableAccion)obj;
    return new EqualsBuilder().append(getPk(), other.getPk()).isEquals();
  }
}
