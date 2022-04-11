package com.visiongc.app.strategos.problemas.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosProblemasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getProblemas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract Long getNumeroProblemas(Long paramLong);
  
  public abstract ListaMap getDependenciasProblema(Problema paramProblema);
  
  public abstract int updateCampo(Long paramLong, Map<?, ?> paramMap)
    throws Throwable;
}
