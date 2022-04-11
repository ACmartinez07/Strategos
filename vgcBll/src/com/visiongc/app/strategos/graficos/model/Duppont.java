package com.visiongc.app.strategos.graficos.model;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;















public class Duppont
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private DuppontPK pk;
  private String configuracion;
  private Usuario usuario;
  private Indicador indicador;
  
  public Duppont(DuppontPK pk, Usuario usuario, Indicador indicador, String configuracion)
  {
    this.pk = pk;
    this.usuario = usuario;
    this.indicador = indicador;
    this.configuracion = configuracion;
  }
  

  public Duppont() {}
  

  public String getConfiguracion()
  {
    return configuracion;
  }
  
  public void setConfiguracion(String configuracion)
  {
    this.configuracion = configuracion;
  }
  
  public Usuario getUsuario()
  {
    return usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public Indicador getIndicador()
  {
    return indicador;
  }
  
  public void setIndicador(Indicador indicador)
  {
    this.indicador = indicador;
  }
  
  public DuppontPK getPk()
  {
    return pk;
  }
  
  public void setPk(DuppontPK pk)
  {
    this.pk = pk;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }
}
