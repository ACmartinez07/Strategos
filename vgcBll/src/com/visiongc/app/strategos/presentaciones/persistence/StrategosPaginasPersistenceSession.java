package com.visiongc.app.strategos.presentaciones.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosPaginasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getPaginas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Pagina getPaginasPorOrden(int paramInt);
  
  public abstract int getOrdenMaximoPaginas(Long paramLong);
}
