package com.visiongc.app.strategos.indicadores.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.ListaMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class StrategosClasesIndicadoresHibernateSession extends StrategosHibernateSession implements com.visiongc.app.strategos.indicadores.persistence.StrategosClasesIndicadoresPersistenceSession
{
  public StrategosClasesIndicadoresHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosClasesIndicadoresHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public ClaseIndicadores getClaseIndicadorResultado(Long claseId)
  {
    ClaseIndicadores claseIndicadores = (ClaseIndicadores)session.createQuery("select new ClaseIndicadores (claseIndicador.claseId, claseIndicador.organizacionId, claseIndicadores.padreId, claseIndicadores.nombre) from ClaseIndicador claseIndicador where claseIndicadores.claseId=:claseId").setLong("claseId", claseId.longValue()).uniqueResult();
    
    return claseIndicadores;
  }
  
  public ListaMap getDependenciasClasesIndicadores(ClaseIndicadores claseIndicadores)
  {
    List<?> indicadores = null;
    ListaMap dependencias = new ListaMap();
    Long claseId = claseIndicadores.getClaseId();
    
    Criteria consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("claseId", claseId));
    indicadores = consulta.list();
    dependencias.add(indicadores, "indicadores");
    
    return dependencias;
  }
  
  public List<ClaseIndicadores> getClases(String[] orden, String[] tipoOrden, Map<String, Object> filtros)
  {
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
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
        if (fieldName.equals("organizacionId"))
        {
          condicionesConsulta = condicionesConsulta + "claseIndicadores." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(claseIndicadores." + fieldName + ") = '" + fieldValue.toLowerCase() + "' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("padreId"))
        {
          if (fieldValue != null) {
            condicionesConsulta = condicionesConsulta + "claseIndicadores." + fieldName + " = " + fieldValue + " and ";
          } else
            condicionesConsulta = condicionesConsulta + "claseIndicadores." + fieldName + " is null and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("tipo"))
        {
          condicionesConsulta = condicionesConsulta + "claseIndicadores." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("visible"))
        {
          condicionesConsulta = condicionesConsulta + "claseIndicadores." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (orden.length > 0))
    {
      ordenConsulta = " order by ";
      for (int i = 0; i < orden.length; i++)
      {
        String ordenActual = orden[i];
        String tipoOrdenActual = tipoOrden[i];
        if ((tipoOrdenActual == null) || (tipoOrdenActual.equals(""))) {
          ordenConsulta = ordenConsulta + "claseIndicadores." + ordenActual + " asc,";
        } else if (tipoOrdenActual.equalsIgnoreCase("asc")) {
          ordenConsulta = ordenConsulta + "claseIndicadores." + ordenActual + " asc,";
        } else {
          ordenConsulta = ordenConsulta + "claseIndicadores." + ordenActual + " desc,";
        }
      }
      ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 1);
    }
    
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select claseIndicadores from ClaseIndicadores claseIndicadores" + condicionesConsulta + ordenConsulta);
    
    return consulta.list();
  }
}
