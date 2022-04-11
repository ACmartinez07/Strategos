package com.visiongc.app.strategos.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;










public class SerieUtil
{
  private Long serieId;
  private Long indicadorId;
  private Double valor;
  private Boolean serieAnoAnterior;
  
  public SerieUtil() {}
  
  public SerieUtil(Long serieId, Long indicadorId, Double valor, Boolean serieAnoAnterior)
  {
    this.serieId = serieId;
    this.valor = valor;
    this.indicadorId = indicadorId;
    this.serieAnoAnterior = serieAnoAnterior;
  }
  
  public Long getSerieId()
  {
    return serieId;
  }
  
  public void setSerieId(Long serieId)
  {
    this.serieId = serieId;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Double getValor()
  {
    return valor;
  }
  
  public void setValor(Double valor)
  {
    this.valor = valor;
  }
  
  public Boolean getSerieAnoAnterior()
  {
    return serieAnoAnterior;
  }
  
  public void setSerieAnoAnterior(Boolean serieAnoAnterior)
  {
    this.serieAnoAnterior = serieAnoAnterior;
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
