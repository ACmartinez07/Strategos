package com.visiongc.app.strategos.estadosacciones;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public abstract interface StrategosEstadosService
  extends StrategosService
{
  public abstract PaginaLista getEstadosAcciones(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteEstadoAcciones(EstadoAcciones paramEstadoAcciones, Usuario paramUsuario);
  
  public abstract int saveEstadoAcciones(EstadoAcciones paramEstadoAcciones, Usuario paramUsuario);
  
  public abstract int cambiarOrdenEstadoAcciones(Long paramLong, boolean paramBoolean, Usuario paramUsuario);
  
  public abstract Boolean estadoAccionesEstaEnUso(Long paramLong);
  
  public abstract EstadoAcciones estadoAccionesIndicaFinalizacion();
}
