package com.visiongc.app.strategos.indicadores.model;

import com.visiongc.framework.model.Transaccion;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;










public class TransaccionIndicador
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private TransaccionIndicadorPK pk;
  private Transaccion transaccion;
  private Indicador indicador;
  
  public TransaccionIndicador(TransaccionIndicadorPK pk, Transaccion transaccion, Indicador indicador)
  {
    this.pk = pk;
    this.transaccion = transaccion;
    this.indicador = indicador;
  }
  

  public TransaccionIndicador() {}
  

  public TransaccionIndicadorPK getPk()
  {
    return pk;
  }
  
  public void setPk(TransaccionIndicadorPK pk)
  {
    this.pk = pk;
  }
  
  public Transaccion getTransaccion()
  {
    return transaccion;
  }
  
  public void setTransaccion(Transaccion transaccion)
  {
    this.transaccion = transaccion;
  }
  
  public Indicador getIndicador()
  {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador)
  {
    this.indicador = indicador;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
