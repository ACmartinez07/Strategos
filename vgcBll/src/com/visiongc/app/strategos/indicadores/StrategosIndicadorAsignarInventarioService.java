package com.visiongc.app.strategos.indicadores;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosIndicadorAsignarInventarioService
  extends StrategosService
{
  public abstract int deleteIndicadorInventario(IndicadorAsignarInventario paramIndicador, Usuario paramUsuario);
  
  public abstract int saveIndicadorInventario(IndicadorAsignarInventario paramIndicador, Usuario paramUsuario);
  
  public abstract List<IndicadorAsignarInventario> getIndicadorInventario(Long paramLong1);
  
  public abstract IndicadorAsignarInventario getIndicadorInventario(Long paramLong1, Long paramLong2);
  
  public abstract boolean esInsumoInventario(Long paramLong1);
  
  public abstract IndicadorAsignarInventario getInsumo(Long paramLong1);
  
}
