package com.visiongc.app.strategos.planes;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPlanesService
  extends StrategosService
{
  public abstract PaginaLista getReporteSeguimiento(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract PaginaLista getPlanes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deletePlan(Plan paramPlan, Usuario paramUsuario, Boolean paramBoolean);
  
  public abstract int savePlan(Plan paramPlan, Usuario paramUsuario);
  
  public abstract int activarPlan(Long paramLong, boolean paramBoolean);
  
  public abstract List getPlanesPorOrganizacion(Long paramLong, String paramString1, String paramString2);
  
  public abstract int asociarIniciativa(Long paramLong1, Long paramLong2, Long paramLong3, Usuario paramUsuario);
  
  public abstract int desasociarIniciativa(Long paramLong1, Long paramLong2, Usuario paramUsuario);
  
  public abstract List getPlanesAsociadosPorIndicador(Long paramLong, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract int deleteIndicadorEstado(Long paramLong1, Long paramLong2, Byte paramByte, Integer paramInteger1, Integer paramInteger2);
  
  public abstract int deleteIndicadorEstados(Long paramLong1, Long paramLong2, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int saveIndicadorEstado(Long paramLong1, Long paramLong2, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Double paramDouble, Usuario paramUsuario);
  
  public abstract int saveAlertaIndicadorPlan(Long paramLong1, Long paramLong2, Byte paramByte, Usuario paramUsuario);
  
  public abstract Byte getTipoCargaMedicionMeta(Long paramLong1, Long paramLong2);
  
  public abstract List getIndicadorEstados(Long paramLong1, Long paramLong2, Byte paramByte1, Byte paramByte2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract int deletePlanEstados(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);
  
  public abstract List getIndicadoresPlan(Long paramLong);
  
  public abstract Byte getFrecuenciaPlan(Long paramLong);
  
  public abstract int savePlanEstado(Long paramLong, Byte paramByte, Integer paramInteger1, Integer paramInteger2, Double paramDouble, Usuario paramUsuario);
  
  public abstract int actualizarPlanUltimoEstado(Long paramLong);
  
  public abstract int actualizarPlanAlerta(Plan paramPlan);
  
  public abstract Byte getAlertaIndicadorPorPlan(Long paramLong1, Long paramLong2);
  
  public abstract IndicadorPlan getFirstAlertaIndicadorPorPlan(Long paramLong);
  
  public abstract boolean tienePermisoPlan(Usuario paramUsuario, Long paramLong, String paramString);
  
  public abstract void readDatosIndicadorPlan(Indicador paramIndicador, Byte paramByte, Integer paramInteger, Plan paramPlan);
  
  public abstract boolean getIndicadorEstaAsociadoPlan(Long paramLong1, Long paramLong2);
  
  public abstract ConfiguracionPlan getConfiguracionPlan();
}
