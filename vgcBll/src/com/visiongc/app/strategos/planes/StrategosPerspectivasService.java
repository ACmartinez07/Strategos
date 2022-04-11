package com.visiongc.app.strategos.planes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPerspectivasService
  extends StrategosService
{
  public abstract Perspectiva getPerspectivaAndLockForUse(Long paramLong, String paramString);
  
  public abstract String getRutaCompletaPerspectiva(Long paramLong, String paramString);
  
  public abstract String getRutaCompletaPerspectivaSinPorcentajes(Perspectiva paramPerspectiva, String paramString);
  
  public abstract String getRutaCompletaPerspectiva(Perspectiva paramPerspectiva, String paramString);
  
  public abstract int deletePerspectiva(Perspectiva paramPerspectiva, Usuario paramUsuario);
  
  public abstract int savePerspectiva(Perspectiva paramPerspectiva, Usuario paramUsuario);
  
  public abstract Perspectiva crearPerspectivaRaiz(Long paramLong, Usuario paramUsuario);
  
  public abstract boolean asociarIniciativa(Perspectiva paramPerspectiva, Long paramLong1, Long paramLong2, Long paramLong3, Usuario paramUsuario);
  
  public abstract boolean desasociarIniciativa(Long paramLong1, Long paramLong2, Long paramLong3, Usuario paramUsuario);
  
  public abstract boolean asociarIndicador(Long paramLong1, Perspectiva paramPerspectiva, Long paramLong2, Boolean paramBoolean, Usuario paramUsuario);
  
  public abstract int desasociarIndicador(IndicadorPerspectiva paramIndicadorPerspectiva, IndicadorPlan paramIndicadorPlan, Usuario paramUsuario);
  
  public abstract int desasociarIndicador(Long paramLong1, Long paramLong2,IndicadorPerspectiva paramIndicadorPerspectiva, IndicadorPlan paramIndicadorPlan, Usuario paramUsuario);
  
  public abstract List<Perspectiva> getPerspectivas(String[] paramArrayOfString1, String[] paramArrayOfString2, Map<String, Object> paramMap);
  
  public abstract Perspectiva getPerspectivaRaiz(Long paramLong);
  
  public abstract int deletePerspectivaEstados(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List getIndicadoresPerspectiva(Long paramLong);
  
  public abstract List<Perspectiva> getIndicadoresPorPerspectiva(Long paramLong1, Long paramLong2);
  
  public abstract int savePerspectivaEstado(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Double paramDouble, Usuario paramUsuario);
  
  public abstract int actualizarPerspectivaUltimoEstado(Long paramLong);
  
  public abstract int updatePesosIndicadoresPerspectiva(List paramList, Usuario paramUsuario);
  
  public abstract Map<Long, Byte> getAlertasPerspectivas(Map<String, String> paramMap, Byte paramByte);
  
  public abstract List<PerspectivaEstado> getPerspectivaEstados(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<Perspectiva> getPerspectivasAsociadas(Long paramLong);
  
  public abstract List<PerspectivaEstado> getPerspectivaEstadosUltimoPeriodo(Long paramLong, Byte paramByte, Integer paramInteger);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
}
