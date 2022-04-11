package com.visiongc.app.strategos.presentaciones.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosVistasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getVistas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
}
