package com.visiongc.app.strategos.problemas.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.problemas.persistence.StrategosAccionesPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



public class StrategosAccionesHibernateSession
  extends StrategosHibernateSession
  implements StrategosAccionesPersistenceSession
{
  public StrategosAccionesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosAccionesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public ListaMap getDependenciasAcciones(Accion accion)
  {
    List seguimiento = new ArrayList();
    ListaMap dependencias = new ListaMap();
    
    Long accionId = accion.getAccionId();
    Criteria consulta = null;
    
    consulta = session.createCriteria(Seguimiento.class);
    consulta.add(Restrictions.eq("accionId", accionId));
    consulta.addOrder(Order.desc("seguimientoId"));
    seguimiento = consulta.list();
    dependencias.add(seguimiento, "seguimiento");
    
    return dependencias;
  }
  
  public Accion getAccionRaiz(Long problemaId)
  {
    Accion accion = new Accion();
    
    Criteria consulta = session.createCriteria(Accion.class);
    consulta.add(Restrictions.isNull("padreId"));
    consulta.add(Restrictions.eq("problemaId", problemaId));
    accion = (Accion)consulta.uniqueResult();
    
    return accion;
  }
  
  public List getAccionesCorrectivas(Long problemaId)
  {
    List consulta = session.createQuery("from Accion accion where accion.problemaId = ?").setLong(0, problemaId.longValue()).list();
    
    return consulta;
  }
}
