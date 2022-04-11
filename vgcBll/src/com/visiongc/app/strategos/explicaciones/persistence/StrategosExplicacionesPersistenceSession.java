package com.visiongc.app.strategos.explicaciones.persistence;

import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.List;
import java.util.Map;

public abstract interface StrategosExplicacionesPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getExplicaciones(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract List getAdjuntosIdPorExplicacion(Long paramLong);
  
  public abstract void deleteAdjuntosExplicacion(List paramList);
  
  public abstract Long getNumeroAdjuntos(Long paramLong);
  
  public abstract Long getNumeroExplicaciones(Long paramLong);
  
  public abstract AdjuntoExplicacion getAdjunto(Long parmaLong);
}
