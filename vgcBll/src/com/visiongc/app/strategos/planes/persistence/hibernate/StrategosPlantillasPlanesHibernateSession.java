package com.visiongc.app.strategos.planes.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.persistence.StrategosPlantillasPlanesPersistenceSession;
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

public class StrategosPlantillasPlanesHibernateSession
  extends StrategosHibernateSession implements StrategosPlantillasPlanesPersistenceSession
{
  public StrategosPlantillasPlanesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPlantillasPlanesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getPlantillasPlanes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(PlantillaPlanes.class);
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext()) {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("nombre")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        }
      }
    }
    

    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public List getNivelesPlantillaPlan(Long plantillaPlanesId)
  {
    Query consulta = session.createQuery("from ElementoPlantillaPlanes nivel where plantillaPlanesId=? order by tipo, orden");
    consulta.setLong(0, plantillaPlanesId.longValue());
    return consulta.list();
  }
}
