package com.visiongc.app.strategos.estadosacciones.persistence;

import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Map;

public abstract interface StrategosEstadosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract PaginaLista getEstadosAcciones(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract EstadoAcciones getEstadoAccionesPorOrden(int paramInt);
  
  public abstract int getOrdenMaximoEstadosAcciones();
  
  public abstract Boolean estadoAccionesEstaEnUso(Long paramLong);
  
  public abstract EstadoAcciones estadoAccionesIndicaFinalizacion();
}
