package com.visiongc.app.strategos.planes.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosModelosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getModelos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap);
}
