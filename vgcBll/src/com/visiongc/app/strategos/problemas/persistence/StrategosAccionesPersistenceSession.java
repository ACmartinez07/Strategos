package com.visiongc.app.strategos.problemas.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.commons.util.ListaMap;
import java.util.List;

public abstract interface StrategosAccionesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract ListaMap getDependenciasAcciones(Accion paramAccion);
  
  public abstract Accion getAccionRaiz(Long paramLong);
  
  public abstract List getAccionesCorrectivas(Long paramLong);
}
