package com.visiongc.app.strategos.iniciativas.persistence.hibernate;

import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativaEstatusPersistenceSession;
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






public class StrategosIniciativaEstatusHibernateSession
  extends StrategosHibernateSession
  implements StrategosIniciativaEstatusPersistenceSession
{
  public StrategosIniciativaEstatusHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosIniciativaEstatusHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public boolean buscarReferenciasRelacionales(Long estatusId)
  {
    boolean hayRelacion = false;
    

    String sql = "select distinct(iniciativa) from Iniciativa iniciativa";
    sql = sql + " where iniciativa.estatusId = :iniciativaEstatusId";
    
    Query query = session.createQuery(sql);
    query.setLong("iniciativaEstatusId", estatusId.longValue());
    
    hayRelacion = query.list().size() > 0;
    

    if (!hayRelacion)
    {
      sql = "select distinct(portafolio) from Portafolio portafolio";
      sql = sql + " where portafolio.estatusId = :portafolioEstatusId";
      
      query = session.createQuery(sql);
      query.setLong("portafolioEstatusId", estatusId.longValue());
      
      hayRelacion = query.list().size() > 0;
    }
    
    return hayRelacion;
  }
  
  public PaginaLista getIniciativaEstatus(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    Criteria consulta = prepareQuery(IniciativaEstatus.class);
    
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
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
}
