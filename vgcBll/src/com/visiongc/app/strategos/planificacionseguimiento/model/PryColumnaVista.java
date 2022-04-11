package com.visiongc.app.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PryColumnaVista
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private PryColumnaVistaPK pk;
  private Byte alineacion;
  private Integer ancho;
  private Integer orden;
  private PryVista pryVista;
  
  public PryColumnaVista(PryColumnaVistaPK pk, Byte alineacion, Integer ancho, Integer orden)
  {
    this.pk = pk;
    this.alineacion = alineacion;
    this.ancho = ancho;
    this.orden = orden;
  }
  

  public PryColumnaVista() {}
  

  public PryColumnaVistaPK getPk()
  {
    return pk;
  }
  
  public void setPk(PryColumnaVistaPK pk) {
    this.pk = pk;
  }
  
  public Byte getAlineacion() {
    return alineacion;
  }
  
  public void setAlineacion(Byte alineacion) {
    this.alineacion = alineacion;
  }
  
  public Integer getAncho() {
    return ancho;
  }
  
  public void setAncho(Integer ancho) {
    this.ancho = ancho;
  }
  
  public Integer getOrden() {
    return orden;
  }
  
  public void setOrden(Integer orden) {
    this.orden = orden;
  }
  
  public PryVista getPryVista() {
    return pryVista;
  }
  
  public void setPryVista(PryVista pryVista) {
    this.pryVista = pryVista;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryColumnaVista other = (PryColumnaVista)obj;
    if (pk == null) {
      if (pk != null)
        return false;
    } else if (!pk.equals(pk))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
