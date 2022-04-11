package com.visiongc.app.strategos.persistence.hibernate;

import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.ListaMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class StrategosHibernateSession extends VgcHibernateSession
{
  public StrategosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public ListaMap getDependenciasGenericas(Long objetoId)
  {
    List explicaciones = null;
    List foros = null;
    List graficos = null;
    ListaMap dependencias = new ListaMap();
    
    Criteria consulta = session.createCriteria(Explicacion.class);
    consulta.add(Restrictions.eq("objetoId", objetoId));
    explicaciones = consulta.list();
    dependencias.add(explicaciones, "explicaciones");
    
    consulta = session.createCriteria(Foro.class);
    consulta.add(Restrictions.eq("objetoId", objetoId));
    consulta.add(Restrictions.isNull("padreId"));
    foros = consulta.list();
    dependencias.add(foros, "foros");
    
    consulta = session.createCriteria(Grafico.class);
    consulta.add(Restrictions.eq("objetoId", objetoId));
    graficos = consulta.list();
    dependencias.add(graficos, "graficos");
    
    return dependencias;
  }
}
