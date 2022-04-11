package com.visiongc.app.strategos.seriestiempo.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosSeriesTiempoHibernateSession extends StrategosHibernateSession implements com.visiongc.app.strategos.seriestiempo.persistence.StrategosSeriesTiempoPersistenceSession
{
  public StrategosSeriesTiempoHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosSeriesTiempoHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getSeriesTiempo(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<Object, Object> filtros)
  {
    Criteria consulta = prepareQuery(SerieTiempo.class);
    
    if (filtros != null)
    {
      Iterator<Object> iter = filtros.keySet().iterator();
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
        if (fieldName.equals("nombre"))
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        if (fieldName.equals("oculta"))
          consulta.add(Restrictions.eq(fieldName, new Boolean(fieldValue)));
        if (fieldName.equals("identificador")) {
          consulta.add(Restrictions.eq(fieldName, fieldValue));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
}
