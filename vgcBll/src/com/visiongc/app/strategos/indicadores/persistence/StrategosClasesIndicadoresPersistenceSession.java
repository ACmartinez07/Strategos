package com.visiongc.app.strategos.indicadores.persistence;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import java.util.List;
import java.util.Map;

public abstract interface StrategosClasesIndicadoresPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract ClaseIndicadores getClaseIndicadorResultado(Long paramLong);
  
  public abstract ListaMap getDependenciasClasesIndicadores(ClaseIndicadores paramClaseIndicadores);
  
  public abstract List<ClaseIndicadores> getClases(String[] paramArrayOfString1, String[] paramArrayOfString2, Map<String, Object> paramMap);
}
