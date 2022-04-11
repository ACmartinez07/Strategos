package com.visiongc.app.strategos.estadosacciones.persistence.hibernate;

import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.estadosacciones.persistence.StrategosEstadosPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosEstadosHibernateSession extends StrategosHibernateSession implements StrategosEstadosPersistenceSession
{
  public StrategosEstadosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosEstadosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public EstadoAcciones getEstadoAccionesPorOrden(int orden)
  {
    Criteria consulta = session.createCriteria(EstadoAcciones.class);
    consulta.add(Restrictions.eq("orden", new Integer(orden)));
    return (EstadoAcciones)consulta.uniqueResult();
  }
  
  public int getOrdenMaximoEstadosAcciones()
  {
    Query consulta = session.createQuery("select max(orden) from EstadoAcciones");
    if (consulta.list().get(0) != null) {
      return ((Integer)consulta.list().get(0)).intValue();
    }
    return 0;
  }
  
  public PaginaLista getEstadosAcciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(EstadoAcciones.class);
    
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("nombre")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        } else if (fieldName.equals("descripcion")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public Boolean estadoAccionesEstaEnUso(Long estadoId)
  {
    Query consulta = session.createQuery("select count(*) from Problema problema where problema.estadoId = ?").setLong(0, estadoId.longValue());
    List<?> listaConsulta = consulta.list();
    if ((listaConsulta.size() > 0) && (listaConsulta.get(0) != null) && (((Long)listaConsulta.get(0)).longValue() > 0L)) {
      return Boolean.valueOf(true);
    }
    consulta = session.createQuery("select count(*) from Accion accion where accion.estadoId = ?").setLong(0, estadoId.longValue());
    listaConsulta = consulta.list();
    if ((listaConsulta.size() > 0) && (listaConsulta.get(0) != null) && (((Long)listaConsulta.get(0)).longValue() > 0L)) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  public EstadoAcciones estadoAccionesIndicaFinalizacion()
  {
    Query consulta = session.createQuery("select estadoAcciones from EstadoAcciones estadoAcciones where estadoAcciones.condicion = 1");
    EstadoAcciones estadoAcciones = null;
    List<?> listaConsulta = consulta.list();
    
    if (listaConsulta.size() > 0) {
      estadoAcciones = (EstadoAcciones)consulta.list().get(0);
    }
    return estadoAcciones;
  }
}
