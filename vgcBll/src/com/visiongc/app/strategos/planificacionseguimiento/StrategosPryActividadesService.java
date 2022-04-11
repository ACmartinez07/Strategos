package com.visiongc.app.strategos.planificacionseguimiento;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPryActividadesService
  extends StrategosService
{
  public abstract List<?> getActividadesHijasPorPlan(Long paramLong1, Long paramLong2, String paramString1, String paramString2);
  
  public abstract PaginaLista getActividades(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<?, ?> paramMap);
  
  public abstract int deleteActividad(PryActividad paramPryActividad, Usuario paramUsuario);
  
  public abstract int saveActividad(PryActividad paramPryActividad, Usuario paramUsuario, Boolean paramBoolean);
  
  public abstract PryActividad getHermanoInmediatoAnterior(long paramLong);
  
  public abstract void moverFila(long paramLong, boolean paramBoolean, Usuario paramUsuario);
  
  public abstract void setHijoHermanoInmediatoAnterior(long paramLong, Usuario paramUsuario);
  
  public abstract void setHijoAbuelo(long paramLong, Usuario paramUsuario);
  
  public abstract List<?> getValoresLimiteAlcanceHijosActividad(long paramLong, Boolean paramBoolean, Usuario paramUsuario);
  
  public abstract int getMaximaFila(long paramLong, Usuario paramUsuario);
  
  public abstract List<PryActividad> getActividades(Long paramLong, byte paramByte);
  
  public abstract int updatePesosActividad(List<?> paramList, Usuario paramUsuario);
  
  public abstract PryActividad getActividadByIndicador(long paramLong);
  
  public abstract List<PryActividad> getObjetoAsociados(Long paramLong, String paramString);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
  
  public abstract List<PryActividad> getActividades(Long paramLong);
}
