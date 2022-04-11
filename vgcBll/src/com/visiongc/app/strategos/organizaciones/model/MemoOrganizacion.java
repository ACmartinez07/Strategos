package com.visiongc.app.strategos.organizaciones.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MemoOrganizacion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  public static final int TIPO_DESCRIPCION = 0;
  public static final int TIPO_OBSERVACIONES = 1;
  public static final int TIPO_PERSONAL_DIRECTIVO = 2;
  public static final int TIPO_MISION = 3;
  public static final int TIPO_VISION = 4;
  public static final int TIPO_OPORTUNIDAD = 5;
  public static final int TIPO_ESTRATEGIA = 6;
  public static final int TIPO_FACTORES_CLAVE = 7;
  public static final int TIPO_POLITICAS = 8;
  public static final int TIPO_VALORES = 9;
  private MemoOrganizacionPK pk;
  private String descripcion;
  private OrganizacionStrategos organizacion;
  
  public MemoOrganizacion(MemoOrganizacionPK pk, String descripcion, OrganizacionStrategos organizacion)
  {
    this.pk = pk;
    this.descripcion = descripcion;
    this.organizacion = organizacion;
  }
  

  public MemoOrganizacion() {}
  

  public MemoOrganizacion(MemoOrganizacionPK pk)
  {
    this.pk = pk;
  }
  
  public MemoOrganizacionPK getPk() {
    return pk;
  }
  
  public void setPk(MemoOrganizacionPK pk) {
    this.pk = pk;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public OrganizacionStrategos getOrganizacion() {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
  
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof MemoOrganizacion))
      return false;
    MemoOrganizacion castOther = (MemoOrganizacion)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }
}
