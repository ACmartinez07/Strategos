package com.visiongc.app.strategos.planes.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PerspectivaEstado implements java.io.Serializable
{
  static final long serialVersionUID = 0L;
  private PerspectivaEstadoPK pk;
  private Double estado;
  private Double meta;
  private Byte alerta;
  private Integer totalConValor;
  private Integer total;
  
  public PerspectivaEstado() {}
  
  public PerspectivaEstadoPK getPk()
  {
    return pk;
  }
  
  public void setPk(PerspectivaEstadoPK pk)
  {
    this.pk = pk;
  }
  
  public Double getEstado()
  {
    return estado;
  }
  
  public String getEstadoFormateado()
  {
    return com.visiongc.commons.util.VgcFormatter.formatearNumero(estado, 2);
  }
  
  public void setEstado(Double estado)
  {
    this.estado = estado;
  }
  
  public Integer getTotalConValor()
  {
    return totalConValor;
  }
  
  public void setTotalConValor(Integer totalConValor)
  {
    this.totalConValor = totalConValor;
  }
  
  public Integer getTotal()
  {
    return total;
  }
  
  public void setTotal(Integer total)
  {
    this.total = total;
  }
  
  public Byte getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(Byte alerta)
  {
    this.alerta = alerta;
  }
  
  public Double getMeta()
  {
    return meta;
  }
  
  public void setMeta(Double meta)
  {
    this.meta = meta;
  }
  
  public String getMetaFormateado()
  {
    return com.visiongc.commons.util.VgcFormatter.formatearNumero(meta, 2);
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof PerspectivaEstado))
      return false;
    PerspectivaEstado castOther = (PerspectivaEstado)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
