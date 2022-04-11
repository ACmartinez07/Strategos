package com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryVistasPersistenceSession;
import org.hibernate.Session;

public class StrategosPryVistasHibernateSession
  extends StrategosHibernateSession implements StrategosPryVistasPersistenceSession
{
  public StrategosPryVistasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPryVistasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
}
