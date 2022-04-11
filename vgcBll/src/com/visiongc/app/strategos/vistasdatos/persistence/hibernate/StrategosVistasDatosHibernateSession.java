package com.visiongc.app.strategos.vistasdatos.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.vistasdatos.persistence.StrategosVistasDatosPersistenceSession;
import org.hibernate.Session;

public class StrategosVistasDatosHibernateSession extends StrategosHibernateSession implements StrategosVistasDatosPersistenceSession
{
  public StrategosVistasDatosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosVistasDatosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
}
