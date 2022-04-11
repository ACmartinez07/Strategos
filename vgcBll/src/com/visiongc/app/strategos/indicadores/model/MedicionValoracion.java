package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class MedicionValoracion
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private MedicionPK medicionId;
  private Double valor;
  private String valorString;
  private Boolean protegido;
  private SerieIndicador serieIndicador;
  
  public MedicionValoracion(MedicionPK medicionId, Double valor, Boolean protegido, SerieIndicador serieIndicador)
  {
    this.medicionId = medicionId;
    this.valor = valor;
    this.protegido = protegido;
    this.serieIndicador = serieIndicador;
  }
  

  public MedicionValoracion() {}
  

  public MedicionValoracion(MedicionPK medicionId, Double valor)
  {
    this.medicionId = medicionId;
    this.valor = valor;
  }
  
  public MedicionValoracion(MedicionPK medicionId, Double valor, Boolean protegido)
  {
    this.medicionId = medicionId;
    this.valor = valor;
    this.protegido = protegido;
  }
  
  public MedicionPK getMedicionId()
  {
    return medicionId;
  }
  
  public void setMedicionId(MedicionPK medicionId)
  {
    this.medicionId = medicionId;
  }
  
  public String getId()
  {
    String id = "";
    
    if (medicionId != null)
    {
      id = medicionId.toString();
      id = id.substring(id.indexOf("[indicadorId"));
    }
    
    return id;
  }
  
  public Double getValor()
  {
    return valor;
  }
  
  public Long getValorCualitativo()
  {
    if (valor != null)
    {
      return new Long(valor.intValue());
    }
    
    return null;
  }
  
  public void setValorCualitativo(Long valor)
  {
    if (this.valor != null) {
      this.valor = new Double(valor.intValue());
    } else {
      this.valor = null;
    }
  }
  
  public void setValor(Double valor) {
    this.valor = valor;
  }
  
  public Boolean getProtegido()
  {
    return protegido;
  }
  
  public void setProtegido(Boolean protegido)
  {
    this.protegido = protegido;
  }
  
  public String getValorString()
  {
    return valorString;
  }
  
  public void setValorString(String valorString)
  {
    this.valorString = valorString;
  }
  
  public SerieIndicador getSerieIndicador()
  {
    return serieIndicador;
  }
  
  public void setSerieIndicador(SerieIndicador serieIndicador)
  {
    this.serieIndicador = serieIndicador;
  }
  
  public boolean equals(Object other)
  {
    if (this == other) return true;
    if (!(other instanceof MedicionValoracion)) return false;
    MedicionValoracion castOther = (MedicionValoracion)other;
    return new EqualsBuilder().append(getMedicionId(), castOther.getMedicionId()).isEquals();
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("id", getMedicionId()).toString();
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
