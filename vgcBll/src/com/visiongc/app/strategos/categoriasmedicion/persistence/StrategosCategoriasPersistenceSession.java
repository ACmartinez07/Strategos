package com.visiongc.app.strategos.categoriasmedicion.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosCategoriasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getCategoriasMedicion(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
}
