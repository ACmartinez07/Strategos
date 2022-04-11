package com.visiongc.framework.web.struts.forms;

import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.HistoricoType;
import java.util.List;
import org.apache.struts.action.ActionForm;


public class FiltroForm
  extends ActionForm
{
  private static final long serialVersionUID = 1L;
  private String nombre;
  private String anio;
  private String fechaInicio;
  private String fechaFin;
  private Byte historico;
  private List<HistoricoType> tiposHistoricos;
  private Boolean incluirTodos;
  private Byte condicion;
  private List<CondicionType> tiposCondiciones;
  
  public FiltroForm()
  {
    nombre = "";
    anio ="";
    fechaInicio = "";
    fechaFin = "";
    historico = null;
    incluirTodos = Boolean.valueOf(true);
    tiposHistoricos = HistoricoType.getHistoricosTypes();
    condicion = null;
    tiposCondiciones = CondicionType.getCondicionesTypes();
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getAnio()
  {
    return anio;
  }
  
  public void setAnio(String anio)
  {
    this.anio = anio;
  }
  
  public String getFechaInicio()
  {
    return fechaInicio;
  }
  
  public void setFechaInicio(String fechaInicio)
  {
    this.fechaInicio = fechaInicio;
  }
  
  public String getFechaFin()
  {
    return fechaFin;
  }
  
  public void setFechaFin(String fechaFin)
  {
    this.fechaFin = fechaFin;
  }
  
  public Byte getHistorico()
  {
    return historico;
  }
  
  public void setHistorico(Byte historico)
  {
    this.historico = HistoricoType.getFiltroHistoricoType(historico);
  }
  
  public List<HistoricoType> getTiposHistoricos()
  {
    return tiposHistoricos;
  }
  
  public void setTiposHistoricos(List<HistoricoType> tiposHistoricos)
  {
    this.tiposHistoricos = tiposHistoricos;
  }
  
  public Byte getCondicion()
  {
    return condicion;
  }
  
  public void setCondicion(Byte condicion)
  {
    this.condicion = CondicionType.getFiltroCondicionType(condicion);
  }
  
  public List<CondicionType> getTiposCondiciones()
  {
    return tiposCondiciones;
  }
  
  public void setTiposCondiciones(List<CondicionType> tiposCondiciones)
  {
    this.tiposCondiciones = tiposCondiciones;
  }
  
  public Boolean getIncluirTodos()
  {
    return incluirTodos;
  }
  
  public void setIncluirTodos(Boolean incluirTodos)
  {
    this.incluirTodos = incluirTodos;
  }
}
