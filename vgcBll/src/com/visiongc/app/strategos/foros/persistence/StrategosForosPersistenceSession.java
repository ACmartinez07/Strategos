package com.visiongc.app.strategos.foros.persistence;

import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosForosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getForos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Long getNumeroHijosForo(Long paramLong);
  
  public abstract Foro getUltimaRepuestaForo(Long paramLong);
  
  public abstract Long getNumeroForos(Long paramLong);
}
