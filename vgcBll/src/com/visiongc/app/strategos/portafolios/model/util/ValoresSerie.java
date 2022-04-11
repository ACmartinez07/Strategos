package com.visiongc.app.strategos.portafolios.model.util;

import com.visiongc.commons.util.ObjetoClaveValor;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;







public class ValoresSerie
{
  private String serieId;
  private String serieNombre;
  private List<ObjetoClaveValor> objetos;
  
  public ValoresSerie() {}
  
  public String getSerieId()
  {
    return serieId;
  }
  
  public void setSerieId(String serieId)
  {
    this.serieId = serieId;
  }
  
  public String getSerieNombre()
  {
    return serieNombre;
  }
  
  public void setSerieNombre(String serieNombre)
  {
    this.serieNombre = serieNombre;
  }
  
  public List<ObjetoClaveValor> getObjetos()
  {
    return objetos;
  }
  
  public void setObjetos(List<ObjetoClaveValor> objetos)
  {
    this.objetos = objetos;
  }
  
  public String getValorFormateado(String format, Double valorDouble)
  {
    Locale currentLocale = new Locale("en", "US");
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
    decimalformat.applyPattern(format);
    
    String valor = null;
    if (valorDouble != null) {
      valor = decimalformat.format(valorDouble);
    }
    return valor;
  }
}
