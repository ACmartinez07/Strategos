package com.visiongc.app.strategos.planes.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPlanesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getReporteSeguimiento(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract PaginaLista getPlanes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int activarPlan(Long paramLong, boolean paramBoolean);
  
  public abstract ListaMap getDependenciasPlan(Plan paramPlan);
  
  public abstract ListaMap getPostDependenciasPlan(Plan paramPlan);
  
  public abstract int deleteReferenciasForaneasPlan(Long paramLong);
  
  public abstract List getPlanesAsociadosPorIndicador(Long paramLong, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract int deleteIndicadorEstados(Long paramLong1, Long paramLong2, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List getIndicadorEstados(Long paramLong1, Long paramLong2, Byte paramByte1, Byte paramByte2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int deletePlanEstados(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List getIndicadoresPlan(Long paramLong);
  
  public abstract Byte getFrecuenciaPlan(Long paramLong);
  
  public abstract int actualizarPlanUltimoEstado(Long paramLong);
  
  public abstract int actualizarPlanAlerta(Plan paramPlan);
  
  public abstract boolean tienePermisoPlan(Long paramLong1, Long paramLong2, String paramString);
  
  public abstract boolean getIndicadorEstaAsociadoPlan(Long paramLong1, Long paramLong2);
  
  public abstract IndicadorPlan getFirstAlertaIndicadorPorPlan(Long paramLong);
}
