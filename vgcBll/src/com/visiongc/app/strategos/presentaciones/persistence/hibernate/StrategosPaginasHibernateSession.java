package com.visiongc.app.strategos.presentaciones.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosPaginasHibernateSession extends StrategosHibernateSession implements com.visiongc.app.strategos.presentaciones.persistence.StrategosPaginasPersistenceSession
{
  public StrategosPaginasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPaginasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public com.visiongc.commons.util.PaginaLista getPaginas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    Criteria consulta = prepareQuery(Pagina.class);
    
    if (filtros != null)
    {
      Iterator<String> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("descripcion")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        }
        if (fieldName.equals("vistaId"))
        {
          if (fieldValue == null) {
            consulta.add(Restrictions.isNull(fieldName));
          } else {
            consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
          }
        }
        if (fieldName.equals("portafolioId"))
        {
          if (fieldValue == null) {
            consulta.add(Restrictions.isNull(fieldName));
          } else {
            consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
          }
        }
        if (fieldName.equals("numero")) {
          consulta.add(Restrictions.gt(fieldName, new Integer(fieldValue)));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public Pagina getPaginasPorOrden(int numero)
  {
    Criteria consulta = session.createCriteria(Pagina.class);
    consulta.add(Restrictions.eq("numero", new Integer(numero)));
    
    return (Pagina)consulta.uniqueResult();
  }
  
  public int getOrdenMaximoPaginas(Long vistaId)
  {
    Query consulta = session.createQuery("select max(numero) from Pagina where vista_id = " + vistaId);
    if (consulta.list().get(0) != null) {
      return ((Integer)consulta.list().get(0)).intValue();
    }
    return 0;
  }
}
