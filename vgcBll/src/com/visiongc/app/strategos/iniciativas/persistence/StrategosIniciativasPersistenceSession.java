package com.visiongc.app.strategos.iniciativas.persistence;

import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosIniciativasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getIniciativas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract PaginaLista getIniciativasResponsable(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract ListaMap getDependenciasIniciativa(Iniciativa paramIniciativa);
  
  public abstract ListaMap getDependenciasCiclicasIniciativa(Iniciativa paramIniciativa);
  
  public abstract Iniciativa getIniciativaBasica(Long paramLong);
  
  public abstract boolean clearReferenciaProyectoIniciativa(Long paramLong);
  
  public abstract boolean clearReferenciaIniciativaProblema(Long paramLong);
  
  public abstract boolean clearReferenciaIniciativaRelacionada(Long paramLong);
  
  public abstract boolean verificarOrganizacionIniciativaPorNombre(String paramString, Long paramLong);
  
  public abstract boolean actualizarIniciativaProyecto(Long paramLong1, Long paramLong2);
  
  public abstract int deleteReferenciasRelacionalesIniciativa(Long paramLong);
  
  public abstract Byte getAlertaIniciativaPorSeguimientos(Long paramLong);
  
  public abstract Iniciativa getIniciativaByIndicador(long paramLong);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
  
  public abstract Iniciativa getIniciativaByProyecto(long paramLong);
  
  public abstract Iniciativa getValoresOriginales(Long paramLong);
  
  public abstract List<Iniciativa> getIniciativasAsociadas(Long paramLong);
  
  public abstract Map<Long, Byte> getAlertasIniciativas(Map<String, String> paramMap);
  
  public abstract int marcarHistorico(String paramString);
  
  public abstract int desMarcarHistorico(String paramString);
  
  public abstract List<Iniciativa> getIniciativasEjecutar(Long paramLong1, Long paramLong2, Long paramLong3, Integer paramInteger1);


  
  
}
