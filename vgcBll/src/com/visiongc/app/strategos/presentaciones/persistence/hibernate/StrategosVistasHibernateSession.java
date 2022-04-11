package com.visiongc.app.strategos.presentaciones.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosVistasPersistenceSession;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosVistasHibernateSession extends StrategosHibernateSession implements StrategosVistasPersistenceSession
{
  public StrategosVistasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosVistasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public com.visiongc.commons.util.PaginaLista getVistas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(Vista.class);
    
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if (filtros.get(fieldName) == null) {
          fieldValue = null;
        } else if ((filtros.get(fieldName) instanceof String)) {
          fieldValue = (String)filtros.get(fieldName);
        } else {
          fieldValue = getValorCondicionConsulta(filtros.get(fieldName));
        }
        if (fieldName.equals("visible")) {
          consulta.add(Restrictions.eq(fieldName, Integer.valueOf(new Boolean(fieldValue).booleanValue() ? 1 : 0)));
        }
        if (fieldName.equals("nombre")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        }
        if (fieldName.equals("organizacionId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
}
