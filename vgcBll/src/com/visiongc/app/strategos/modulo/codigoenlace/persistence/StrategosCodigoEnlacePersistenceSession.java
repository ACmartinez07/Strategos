package com.visiongc.app.strategos.modulo.codigoenlace.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosCodigoEnlacePersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getCodigoEnlace(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract boolean clearReferencia(String paramString);
  
  public abstract boolean updateReferencia(String paramString1, String paramString2);
}
