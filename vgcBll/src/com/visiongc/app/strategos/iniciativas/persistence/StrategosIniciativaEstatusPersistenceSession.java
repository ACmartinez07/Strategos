package com.visiongc.app.strategos.iniciativas.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosIniciativaEstatusPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract boolean buscarReferenciasRelacionales(Long paramLong);
  
  public abstract PaginaLista getIniciativaEstatus(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
}
