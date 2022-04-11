package com.visiongc.app.strategos.instrumentos.persistence.hibernate;

import java.util.Iterator;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.categoriasmedicion.persistence.StrategosCategoriasPersistenceSession;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosTipoProyectoPersistenceSession;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosTiposConvenioPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.PaginaLista;

public class StrategosTiposConvenioHibernateSession extends StrategosHibernateSession implements StrategosTiposConvenioPersistenceSession{
	
	public StrategosTiposConvenioHibernateSession(Session session)
	  {
	    super(session);
	  }
	  
	  public StrategosTiposConvenioHibernateSession(StrategosHibernateSession parentSession)
	  {
	    super(parentSession);
	  }
	  
	  public PaginaLista getTiposConvenio(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
	  {
	    Criteria consulta = prepareQuery(TipoConvenio.class);
	    
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
