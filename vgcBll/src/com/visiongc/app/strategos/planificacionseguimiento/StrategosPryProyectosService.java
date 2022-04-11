package com.visiongc.app.strategos.planificacionseguimiento;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.framework.model.Usuario;

public abstract interface StrategosPryProyectosService
  extends StrategosService
{
  public abstract int deleteProyecto(PryProyecto paramPryProyecto, Usuario paramUsuario);
  
  public abstract int saveProyecto(PryProyecto paramPryProyecto, Usuario paramUsuario);
  
  public abstract Iniciativa verificarProyectoIniciativa(Long paramLong, Usuario paramUsuario);
  
  public abstract Long crearProyectoIniciativa(String paramString, Long paramLong, Usuario paramUsuario);
  
  public abstract PryCalendario getCalendarioProyecto(Long paramLong, Usuario paramUsuario);
}
