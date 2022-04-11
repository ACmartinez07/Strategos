package com.visiongc.app.strategos.problemas.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosClasesProblemasPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getClaseProblemas(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract ListaMap getDependenciasClaseProblemas(ClaseProblemas paramClaseProblemas);
}
