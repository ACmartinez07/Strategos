package com.visiongc.app.strategos.reportes.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosReportesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getReportes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap, Long paramLong);
}
