package com.visiongc.app.strategos.indicadores.model;

import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CategoriaIndicador
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private CategoriaIndicadorPK pk;
  private Integer orden;
  private CategoriaMedicion categoriaMedicion;
  private Indicador indicador;
  
  public CategoriaIndicador(CategoriaIndicadorPK pk, Integer orden, CategoriaMedicion categoriaMedicion, Indicador indicador)
  {
    this.pk = pk;
    this.orden = orden;
    this.categoriaMedicion = categoriaMedicion;
    this.indicador = indicador;
  }
  

  public CategoriaIndicador() {}
  

  public CategoriaIndicador(CategoriaIndicadorPK pk)
  {
    this.pk = pk;
  }
  
  public CategoriaIndicadorPK getPk()
  {
    return pk;
  }
  
  public void setPk(CategoriaIndicadorPK pk)
  {
    this.pk = pk;
  }
  
  public Integer getOrden()
  {
    return orden;
  }
  
  public void setOrden(Integer orden)
  {
    this.orden = orden;
  }
  
  public CategoriaMedicion getCategoriaMedicion()
  {
    return categoriaMedicion;
  }
  
  public void setCategoriaMedicion(CategoriaMedicion categoriaMedicion)
  {
    this.categoriaMedicion = categoriaMedicion;
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
