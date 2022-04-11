package com.visiongc.app.strategos.planes.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;








public class PerspectivaRelacion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private PerspectivaRelacionPK pk;
  private Perspectiva perspectiva;
  private Perspectiva relacion;
  
  public PerspectivaRelacion(PerspectivaRelacionPK pk, Perspectiva perspectiva, Perspectiva relacion)
  {
    this.pk = pk;
    this.perspectiva = perspectiva;
    this.relacion = relacion;
  }
  

  public PerspectivaRelacion() {}
  

  public PerspectivaRelacion(PerspectivaRelacionPK pk)
  {
    this.pk = pk;
  }
  
  public PerspectivaRelacionPK getPk()
  {
    return pk;
  }
  
  public void setPk(PerspectivaRelacionPK pk)
  {
    this.pk = pk;
  }
  
  public Perspectiva getPerspectiva()
  {
    return perspectiva;
  }
  
  public void setPerspectiva(Perspectiva perspectiva)
  {
    this.perspectiva = perspectiva;
  }
  
  public Perspectiva getRelacion()
  {
    return relacion;
  }
  
  public void setRelacion(Perspectiva relacion)
  {
    this.relacion = relacion;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
