package com.visiongc.app.strategos.instrumentos.persistence.hibernate;

import java.util.Iterator;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;


import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.PaginaLista;


public class StrategosCooperantesHibernateSession extends StrategosHibernateSession implements StrategosCooperantesPersistenceSession{
	
	public StrategosCooperantesHibernateSession(Session session)
	  {
	    super(session);
	  }
	  
	  public StrategosCooperantesHibernateSession(StrategosHibernateSession parentSession)
	  {
	    super(parentSession);
	  }
	  
	  public PaginaLista getCooperantes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
	  {
	    Criteria consulta = prepareQuery(Cooperante.class);
	    
	    if (filtros != null)
	    {
	      Iterator iter = filtros.keySet().iterator();
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
