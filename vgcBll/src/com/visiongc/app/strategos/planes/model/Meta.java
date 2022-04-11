package com.visiongc.app.strategos.planes.model;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Meta
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private MetaPK metaId;
  private Double valor;
  private String valorString;
  private BigDecimal valorGrande;
  private Boolean protegido;
  private Byte tipoCargaMeta;
  
  public Meta(MetaPK metaId, Double valor, Boolean protegido)
  {
    this.metaId = metaId;
    this.valor = valor;
    this.protegido = protegido;
  }
  

  public Meta() {}
  

  public MetaPK getMetaId()
  {
    return metaId;
  }
  
  public void setMetaId(MetaPK metaId)
  {
    this.metaId = metaId;
  }
  
  public BigDecimal getValorGrande()
  {
    return valorGrande;
  }
  
  public void setValorGrande(BigDecimal valorGrande)
  {
    this.valorGrande = valorGrande;
  }
  
  public Double getValor()
  {
    return valor;
  }
  
  public void setValor(Double valor)
  {
    this.valor = valor;
  }
  
  public String getValorString()
  {
    return valorString;
  }
  
  public void setValorString(String valorString)
  {
    this.valorString = valorString;
  }
  
  public Boolean getProtegido()
  {
    return protegido;
  }
  
  public void setProtegido(Boolean protegido)
  {
    this.protegido = protegido;
  }
  
  public Byte getTipoCargaMeta()
  {
    return tipoCargaMeta;
  }
  
  public void setTipoCargaMeta(Byte tipoCargaMeta)
  {
    this.tipoCargaMeta = tipoCargaMeta;
  }
  
  public boolean equals(Object other)
  {
    if (this == other)
      return true;
    if (!(other instanceof Meta))
      return false;
    Meta castOther = (Meta)other;
    
    return new EqualsBuilder().append(getMetaId(), castOther.getMetaId()).isEquals();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("id", getMetaId()).toString();
  }
  
  public Meta clonar()
  {
    Meta meta = new Meta();
    MetaPK metaPk = new MetaPK();
    meta.setMetaId(metaPk);
    meta.getMetaId().setIndicadorId(getMetaId().getIndicadorId());
    meta.getMetaId().setPlanId(getMetaId().getPlanId());
    meta.getMetaId().setSerieId(getMetaId().getSerieId());
    meta.getMetaId().setTipo(getMetaId().getTipo());
    meta.getMetaId().setAno(getMetaId().getAno());
    meta.getMetaId().setPeriodo(getMetaId().getPeriodo());
    meta.setValor(getValor());
    meta.setProtegido(getProtegido());
    
    return meta;
  }
  
  public Medicion clonarComoMedicion()
  {
    Medicion medicion = new Medicion();
    MedicionPK medicionPk = new MedicionPK();
    medicion.setMedicionId(medicionPk);
    medicion.getMedicionId().setIndicadorId(getMetaId().getIndicadorId());
    medicion.getMedicionId().setSerieId(getMetaId().getSerieId());
    medicion.getMedicionId().setAno(getMetaId().getAno());
    medicion.getMedicionId().setPeriodo(getMetaId().getPeriodo());
    medicion.setValor(getValor());
    medicion.setProtegido(getProtegido());
    
    return medicion;
  }
  
  public String getValorFormateado(String format)
  {
    String valor = null;
    Locale currentLocale = new Locale("en", "US");
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
    decimalformat.applyPattern(format);
    
    if (this.valor != null) {
      valor = decimalformat.format(this.valor);
    } else {
      valor = null;
    }
    return valor;
  }
}
