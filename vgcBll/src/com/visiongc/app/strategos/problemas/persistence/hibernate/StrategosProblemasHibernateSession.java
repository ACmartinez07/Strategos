package com.visiongc.app.strategos.problemas.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.persistence.StrategosProblemasPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class StrategosProblemasHibernateSession
  extends StrategosHibernateSession implements StrategosProblemasPersistenceSession
{
  public StrategosProblemasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosProblemasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getProblemas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = session.createCriteria(Problema.class);
    
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
        if (fieldName.equals("claseId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        }
        if (fieldName.equals("responsableId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        }
      }
    }
    


    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public Long getNumeroProblemas(Long claseId)
  {
    Query consulta = session.createQuery("select count(*) from Problema problema where problema.claseId = ?").setLong(0, claseId.longValue());
    if (consulta.list().get(0) != null) {
      return (Long)consulta.list().get(0);
    }
    return new Long(0L);
  }
  
  public ListaMap getDependenciasProblema(Problema problema)
  {
    List accion = new ArrayList();
    ListaMap dependencias = new ListaMap();
    
    Long problemaId = problema.getProblemaId();
    Criteria consulta = null;
    
    consulta = session.createCriteria(Accion.class);
    consulta.add(Restrictions.eq("problemaId", problemaId));
    consulta.addOrder(Order.desc("accionId"));
    accion = consulta.list();
    dependencias.add(accion, "accion");
    
    return dependencias;
  }
  
  public int updateCampo(Long problemaId, Map<?, ?> filtros) throws Throwable
  {
    int actualizados = 0;
    boolean hayCondicionesConsulta = false;
    try
    {
      String hqlUpdate = "";
      if (filtros != null)
      {
        hayCondicionesConsulta = true;
        Iterator<?> iter = filtros.keySet().iterator();
        String fieldName = null;
        while (iter.hasNext())
        {
          fieldName = (String)iter.next();
          hqlUpdate = hqlUpdate + "i." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + ", ";
        }
      }
      
      if (hayCondicionesConsulta)
      {

        hqlUpdate = hqlUpdate.substring(0, hqlUpdate.length() - 2);
        hqlUpdate = "update Problema i set " + hqlUpdate + " where i.problemaId = :problemaId";
        
        Query actualizacion = session.createQuery(hqlUpdate).setLong("problemaId", problemaId.longValue());
        actualizados = actualizacion.executeUpdate();
      }
    }
    catch (Throwable t)
    {
      if (ConstraintViolationException.class.isInstance(t))
        return 10006;
      throw t;
    }
    
    if (actualizados == 1) {
      return 10000;
    }
    return 10001;
  }
}
