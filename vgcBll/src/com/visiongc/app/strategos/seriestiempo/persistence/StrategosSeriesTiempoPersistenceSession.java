package com.visiongc.app.strategos.seriestiempo.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosSeriesTiempoPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getSeriesTiempo(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<Object, Object> paramMap);
}
