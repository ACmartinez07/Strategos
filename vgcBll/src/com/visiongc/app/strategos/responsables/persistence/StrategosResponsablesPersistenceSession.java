package com.visiongc.app.strategos.responsables.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosResponsablesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getResponsables(int paramInt1, int paramInt2, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean, Map paramMap);
  
  public abstract List getResponsablesAsociables(boolean paramBoolean, long paramLong);
  
  public abstract ListaMap getDependencias(Responsable paramResponsable);
}
