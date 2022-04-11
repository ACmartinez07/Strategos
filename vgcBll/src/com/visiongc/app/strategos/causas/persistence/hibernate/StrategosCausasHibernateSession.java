package com.visiongc.app.strategos.causas.persistence.hibernate;

import com.visiongc.app.strategos.causas.persistence.StrategosCausasPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import org.hibernate.Session;

public class StrategosCausasHibernateSession
  extends StrategosHibernateSession implements StrategosCausasPersistenceSession
{
  public StrategosCausasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosCausasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
}
