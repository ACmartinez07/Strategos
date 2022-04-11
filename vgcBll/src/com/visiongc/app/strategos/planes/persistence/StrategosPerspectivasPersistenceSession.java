package com.visiongc.app.strategos.planes.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPerspectivasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract List<Perspectiva> getPerspectivas(String[] paramArrayOfString1, String[] paramArrayOfString2, Map<String, Object> paramMap);
  
  public abstract long getNumeroAsociacionesIniciativaPerspectivaPorPlan(Long paramLong1, Long paramLong2);
  
  public abstract Perspectiva getPerspectivaRaiz(Long paramLong);
  
  public abstract int deleteReferenciasForaneasPerspectiva(Long paramLong);
  
  public abstract ListaMap getDependenciasPerspectiva(Perspectiva paramPerspectiva);
  
  public abstract int deletePerspectivaEstados(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List<?> getIndicadoresPerspectiva(Long paramLong);
  
  public abstract List<Perspectiva> getIndicadoresPorPerspectiva(Long paramLong1, Long paramLong2);
  
  public abstract int actualizarPerspectivaUltimoEstado(Long paramLong);
  
  public abstract int updatePesosIndicadoresPerspectiva(List<?> paramList, Usuario paramUsuario);
  
  public abstract Map<Long, Byte> getAlertasPerspectivas(Map<String, String> paramMap, Byte paramByte);
  
  public abstract List<PerspectivaEstado> getPerspectivaEstados(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract Perspectiva getPerspectivaValoresOriginales(Long paramLong);
  
  public abstract int actualizarDatosPerspectiva(Long paramLong, Double paramDouble1, Double paramDouble2, String paramString)
    throws Throwable;
  
  public abstract List<Perspectiva> getPerspectivasAsociadas(Long paramLong);
  
  public abstract List<PerspectivaEstado> getPerspectivaEstadosUltimoPeriodo(Long paramLong, Byte paramByte, Integer paramInteger);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
}
