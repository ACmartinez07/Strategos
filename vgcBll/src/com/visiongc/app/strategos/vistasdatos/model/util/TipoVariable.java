package com.visiongc.app.strategos.vistasdatos.model.util;

import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoVariable implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final long TIPO_VARIABLE_ALERTA = 4L;
  private static final long TIPO_VARIABLE_PORCENTAJE = 5L;
  private static final long TIPO_VARIABLE_ADSOLUTA = 6L;
  private static final long TIPO_VARIABLE_PORCENTAJE_CUMPLIMIENTO_PARCIAL = 9L;
  private static final long TIPO_VARIABLE_PORCENTAJE_CUMPLIMIENTO_ANUAL = 10L;
  private Long tipoVariableId;
  private String nombre;
  
  public TipoVariable() {}
  
  public Long getTipoVariableId()
  {
    return tipoVariableId;
  }
  
  public void setTipoVariableId(Long tipoVariableId)
  {
    this.tipoVariableId = tipoVariableId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public static Long getTipoVariableReal()
  {
    return new Long(SerieTiempo.getSerieRealId().longValue());
  }
  
  public static Long getTipoVariableMeta()
  {
    return new Long(SerieTiempo.getSerieMetaId().longValue());
  }
  
  public static Long getTipoVariableProgramado()
  {
    return new Long(SerieTiempo.getSerieProgramadoId().longValue());
  }
  
  public static Long getTipoVariableAlerta()
  {
    return new Long(4L);
  }
  
  public static Long getTipoVariablePorcentaje()
  {
    return new Long(5L);
  }
  
  public static Long getTipoVariableAdsoluta()
  {
    return new Long(6L);
  }
  
  public static Long getTipoVariablePorcentajeCumplimientoParcial()
  {
    return new Long(9L);
  }
  
  public static Long getTipoVariablePorcentajeCumplimientoAnual()
  {
    return new Long(10L);
  }
  
  public static List<TipoVariable> getTiposVariables()
  {
    return getTiposVariables(null);
  }
  
  public static List<TipoVariable> getTiposVariables(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("Strategos");
    }
    List<TipoVariable> tiposVariables = new ArrayList();
    
    TipoVariable tipoVariable = new TipoVariable();
    
    tipoVariable.tipoVariableId = new Long(SerieTiempo.getSerieRealId().longValue());
    tipoVariable.nombre = messageResources.getResource("tipovariable.real");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(SerieTiempo.getSerieMetaId().longValue());
    tipoVariable.nombre = messageResources.getResource("tipovariable.meta");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(SerieTiempo.getSerieProgramadoId().longValue());
    tipoVariable.nombre = messageResources.getResource("tipovariable.programado");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(4L);
    tipoVariable.nombre = messageResources.getResource("tipovariable.alerta");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(5L);
    tipoVariable.nombre = messageResources.getResource("tipovariable.porcentaje");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(6L);
    tipoVariable.nombre = messageResources.getResource("tipovariable.adsoluta");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(9L);
    tipoVariable.nombre = messageResources.getResource("tipovariable.porcentaje.cumplimiento.parcial");
    tiposVariables.add(tipoVariable);
    
    tipoVariable = new TipoVariable();
    tipoVariable.tipoVariableId = new Long(10L);
    tipoVariable.nombre = messageResources.getResource("tipovariable.porcentaje.cumplimiento.anual");
    tiposVariables.add(tipoVariable);
    
    return tiposVariables;
  }
  
  public static String getNombre(Long tipo)
  {
    String nombre = "";
    
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
    
    if (tipo.longValue() == SerieTiempo.getSerieRealId().longValue()) {
      nombre = messageResources.getResource("tipovariable.real");
    }
    if (tipo.longValue() == SerieTiempo.getSerieMetaId().longValue()) {
      nombre = messageResources.getResource("tipovariable.meta");
    }
    if (tipo.longValue() == SerieTiempo.getSerieProgramadoId().longValue()) {
      nombre = messageResources.getResource("tipovariable.programado");
    }
    if (tipo.longValue() == new Long(4L).longValue()) {
      nombre = messageResources.getResource("tipovariable.alerta");
    }
    if (tipo.longValue() == new Long(5L).longValue()) {
      nombre = messageResources.getResource("tipovariable.porcentaje");
    }
    if (tipo.longValue() == new Long(6L).longValue()) {
      nombre = messageResources.getResource("tipovariable.adsoluta");
    }
    if (tipo.longValue() == new Long(9L).longValue()) {
      nombre = messageResources.getResource("tipovariable.porcentaje.cumplimiento.parcial");
    }
    if (tipo.longValue() == new Long(10L).longValue()) {
      nombre = messageResources.getResource("tipovariable.porcentaje.cumplimiento.anual");
    }
    return nombre;
  }
}
