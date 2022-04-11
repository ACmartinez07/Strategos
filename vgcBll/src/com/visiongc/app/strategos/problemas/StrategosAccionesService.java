package com.visiongc.app.strategos.problemas;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.framework.model.Usuario;
import java.util.List;

public abstract interface StrategosAccionesService
  extends StrategosService
{
  public abstract int deleteAccion(Accion paramAccion, Usuario paramUsuario);
  
  public abstract int saveAccion(Accion paramAccion, Usuario paramUsuario);
  
  public abstract int saveAccion(Accion paramAccion, Usuario paramUsuario, Boolean paramBoolean);
  
  public abstract Accion crearAccionRaiz(Long paramLong, Usuario paramUsuario);
  
  public abstract Accion getAccionRaiz(Long paramLong);
  
  public abstract List getAccionesCorrectivas(Long paramLong);
}
