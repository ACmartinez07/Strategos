package com.visiongc.app.strategos.graficos.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosGraficosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getGraficos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
}
