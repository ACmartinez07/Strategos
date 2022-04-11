package com.visiongc.app.strategos.planificacionseguimiento.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPryActividadesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getActividades(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List getRaices(long paramLong);
  
  public abstract int getMaximaFila(long paramLong);
  
  public abstract ListaMap getDependenciasActividad(PryActividad paramPryActividad);
  
  public abstract List<PryActividad> getActividades(Long paramLong, byte paramByte);
  
  public abstract int updatePesosActividad(List<?> paramList, Usuario paramUsuario);
  
  public abstract PryActividad getActividadByIndicador(long paramLong);
  
  public abstract List<PryActividad> getObjetoAsociados(Long paramLong, String paramString);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
  
  public abstract List<PryActividad> getActividades(Long paramLong);
  
}
