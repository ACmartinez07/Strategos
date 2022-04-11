package com.visiongc.app.strategos.planes.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosPlantillasPlanesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getPlantillasPlanes(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List getNivelesPlantillaPlan(Long paramLong);
}
