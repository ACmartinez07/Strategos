package com.visiongc.app.strategos.unidadesmedida.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosUnidadesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getUnidadesMedida(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
}
