package com.visiongc.app.strategos.problemas.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.persistence.StrategosClasesProblemasPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class StrategosClasesProblemasHibernateSession
  extends StrategosHibernateSession implements StrategosClasesProblemasPersistenceSession
{
  public StrategosClasesProblemasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosClasesProblemasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getClaseProblemas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = session.createCriteria(ClaseProblemas.class);
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValueNombre = null;
      Long fieldValuePadreId = null;
      while (iter.hasNext()) {
        fieldName = (String)iter.next();
        
        if (fieldName.equals("nombre")) {
          fieldValueNombre = (String)filtros.get(fieldName);
          consulta.add(Restrictions.ilike(fieldName, fieldValueNombre, MatchMode.ANYWHERE));
        }
        
        if (fieldName.equals("padreId")) {
          fieldValuePadreId = (Long)filtros.get(fieldName);
          
          if (fieldValuePadreId == null) {
            consulta.add(Restrictions.isNull(fieldName));
          } else {
            consulta.add(Restrictions.eq(fieldName, fieldValuePadreId));
          }
        }
      }
    }
    

    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public ListaMap getDependenciasClaseProblemas(ClaseProblemas claseProblemas)
  {
    List problemas = new ArrayList();
    ListaMap dependencias = new ListaMap();
    
    Long claseId = claseProblemas.getClaseId();
    Criteria consulta = null;
    
    consulta = session.createCriteria(Problema.class);
    consulta.add(Restrictions.eq("claseId", claseId));
    consulta.addOrder(Order.desc("problemaId"));
    problemas = consulta.list();
    dependencias.add(problemas, "problemas");
    
    return dependencias;
  }
}
