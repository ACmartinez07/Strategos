package com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryProyectosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class StrategosPryProyectosHibernateSession extends StrategosHibernateSession implements StrategosPryProyectosPersistenceSession
{
  public StrategosPryProyectosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPryProyectosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public ListaMap getDependenciasPryProyectos(PryProyecto pryProyecto)
  {
    List<PryActividad> pryActividades = new ArrayList();
    List<PryCalendario> pryCalendarios = new ArrayList();
    ListaMap dependecias = new ListaMap();
    Long proyectoId = pryProyecto.getProyectoId();
    
    Criteria consulta = null;
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("proyectoId", proyectoId));
    consulta.add(Restrictions.isNull("padreId"));
    pryActividades = consulta.list();
    dependecias.add(pryActividades, "pryActividades");
    
    consulta = session.createCriteria(PryCalendario.class);
    consulta.add(Restrictions.eq("proyectoId", proyectoId));
    pryCalendarios = consulta.list();
    dependecias.add(pryCalendarios, "pryCalendarios");
    
    return dependecias;
  }
  
  public PryCalendario getCalendarioPorDefecto()
  {
    Query consulta = session.createQuery("select calendario from PryCalendario calendario where calendario.proyectoId is null");
    
    PryCalendario calendario = (PryCalendario)consulta.uniqueResult();
    
    return calendario;
  }
  
  public PryCalendario getCalendarioProyecto(Long proyectoId)
  {
    Query consulta = session.createQuery("select calendario from PryCalendario calendario where calendario.proyectoId = :proyectoId");
    consulta.setLong("proyectoId", proyectoId.longValue());
    PryCalendario calendario = (PryCalendario)consulta.uniqueResult();
    
    return calendario;
  }
}
