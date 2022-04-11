package com.visiongc.app.strategos.unidadesmedida.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.unidadesmedida.persistence.StrategosUnidadesPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosUnidadesHibernateSession
  extends StrategosHibernateSession implements StrategosUnidadesPersistenceSession
{
  public StrategosUnidadesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosUnidadesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getUnidadesMedida(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(UnidadMedida.class);
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("nombre"))
        {
          if (fieldValue.toLowerCase().equals("%")) {
            consulta.add(Restrictions.eq(fieldName, fieldValue));
          } else {
            consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
          }
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
}
