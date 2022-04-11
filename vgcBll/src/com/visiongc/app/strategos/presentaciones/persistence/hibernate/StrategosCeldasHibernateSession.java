package com.visiongc.app.strategos.presentaciones.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosCeldasPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class StrategosCeldasHibernateSession extends StrategosHibernateSession implements StrategosCeldasPersistenceSession
{
  public StrategosCeldasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosCeldasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getCeldas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(Celda.class);
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("paginaId"))
        {
          consulta.add(Restrictions.eq(fieldName, 
            new Long(fieldValue)));
        }
      }
    }
    

    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
}
