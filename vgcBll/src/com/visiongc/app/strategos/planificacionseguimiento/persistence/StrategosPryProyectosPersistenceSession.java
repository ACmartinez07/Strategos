package com.visiongc.app.strategos.planificacionseguimiento.persistence;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.commons.util.ListaMap;

public abstract interface StrategosPryProyectosPersistenceSession
  extends StrategosPersistenceSession
{
  public abstract ListaMap getDependenciasPryProyectos(PryProyecto paramPryProyecto);
  
  public abstract PryCalendario getCalendarioPorDefecto();
  
  public abstract PryCalendario getCalendarioProyecto(Long paramLong);
}
