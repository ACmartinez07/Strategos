package com.visiongc.app.strategos.foros.persistence.hibernate;

import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.app.strategos.foros.persistence.StrategosForosPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class StrategosForosHibernateSession extends StrategosHibernateSession implements StrategosForosPersistenceSession
{
  public StrategosForosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosForosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public com.visiongc.commons.util.PaginaLista getForos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    Criteria consulta = prepareQuery(Foro.class);
    
    if (filtros != null)
    {
      Iterator<String> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("objetoKey"))
          consulta.add(Restrictions.eq(fieldName, new Byte(fieldValue)));
        if (fieldName.equals("objetoId"))
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        if (fieldName.equals("padreId"))
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        if (fieldName.equals("tipo")) {
          consulta.add(Restrictions.eq(fieldName, new Byte(fieldValue)));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public Long getNumeroHijosForo(Long foroId)
  {
    Query consulta = session.createQuery("select count(foroId) from Foro where padreId = ?").setLong(0, foroId.longValue());
    if (consulta.list().get(0) != null) {
      return (Long)consulta.list().get(0);
    }
    return new Long(0L);
  }
  
  public Foro getUltimaRepuestaForo(Long foroId)
  {
    Query consulta = session.createQuery("from Foro where padreId = ? order by modificado desc").setLong(0, foroId.longValue());
    consulta.setMaxResults(1);
    if (consulta.list().size() > 0) {
      return (Foro)consulta.list().get(0);
    }
    return null;
  }
  
  public Long getNumeroForos(Long indicadorId)
  {
    Query consulta = session.createQuery("select count(*) from Foro foro where foro.objetoId = ?").setLong(0, indicadorId.longValue());
    List<?> resultado = consulta.list();
    if (resultado.get(0) != null) {
      return (Long)resultado.get(0);
    }
    return new Long(0L);
  }
}
