package com.visiongc.app.strategos.organizaciones.persistence.hibernate;

import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.organizaciones.persistence.StrategosOrganizacionesPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
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

public class StrategosOrganizacionesHibernateSession
  extends StrategosHibernateSession
  implements StrategosOrganizacionesPersistenceSession
{
  public StrategosOrganizacionesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosOrganizacionesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public ListaMap getDependenciasOrganizaciones(OrganizacionStrategos organizacion)
  {
    List<?> planes = null;
    List<?> vistas = null;
    List<?> iniciativas = null;
    List<?> clasesIndicadores = null;
    List<Indicador> indicadores = null;
    List<?> clasesProblemas = null;
    List<Problema> problemas = null;
    List<?> responsables = null;
    List<?> graficos = null;
    List<Reporte> reportes = null;
    List<Portafolio> portafolios = null;
    ListaMap dependencias = new ListaMap();
    
    Long organizacionId = organizacion.getOrganizacionId();
    Criteria consulta = null;
    
    consulta = session.createCriteria(Vista.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("vistaId"));
    vistas = consulta.list();
    dependencias.add(vistas, "vistas");
    
    consulta = session.createCriteria(Plan.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.add(Restrictions.isNull("padreId"));
    planes = consulta.list();
    dependencias.add(planes, "planes");
    
    consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("iniciativaId"));
    iniciativas = consulta.list();
    dependencias.add(iniciativas, "iniciativas");
    
    consulta = session.createCriteria(Portafolio.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("nombre"));
    portafolios = consulta.list();
    dependencias.add(portafolios, "portafolios");
    
    consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("indicadorId"));
    indicadores = consulta.list();
    dependencias.add(indicadores, "indicadores");
    
    consulta = session.createCriteria(ClaseIndicadores.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("claseId"));
    clasesIndicadores = consulta.list();
    dependencias.add(clasesIndicadores, "clasesIndicadores");
    
    consulta = session.createCriteria(Problema.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("problemaId"));
    problemas = consulta.list();
    dependencias.add(problemas, "problemas");
    
    consulta = session.createCriteria(ClaseProblemas.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("claseId"));
    clasesProblemas = consulta.list();
    dependencias.add(clasesProblemas, "clasesProblemas");
    
    consulta = session.createCriteria(Responsable.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("responsableId"));
    responsables = consulta.list();
    dependencias.add(responsables, "responsables");
    
    consulta = session.createCriteria(Grafico.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("nombre"));
    graficos = consulta.list();
    dependencias.add(graficos, "graficos");
    
    consulta = session.createCriteria(Reporte.class);
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    consulta.addOrder(Order.desc("nombre"));
    reportes = consulta.list();
    dependencias.add(reportes, "reportes");
    
    return dependencias;
  }
  
  public PaginaLista getOrganizaciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(OrganizacionStrategos.class);
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
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
        if (fieldName.equals("nombre")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public List<?> getOrganizaciones(String[] orden, String[] tipoOrden, Map<?, ?> filtros)
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
        if (fieldName.equals("organizacionId")) {
          condicionesConsulta = condicionesConsulta + "organizaciones." + fieldName + " = " + fieldValue + " and ";
        } else if (fieldName.equals("padreId"))
        {
          if (fieldValue != null) {
            condicionesConsulta = condicionesConsulta + "organizaciones." + fieldName + " = " + fieldValue + " and ";
          } else
            condicionesConsulta = condicionesConsulta + "organizaciones." + fieldName + " is null and ";
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
          ordenConsulta = ordenConsulta + "organizaciones." + ordenActual + " asc,";
        } else if (tipoOrdenActual.equalsIgnoreCase("asc")) {
          ordenConsulta = ordenConsulta + "organizaciones." + ordenActual + " asc,";
        } else {
          ordenConsulta = ordenConsulta + "organizaciones." + ordenActual + " desc,";
        }
      }
      ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 1);
    }
    
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select organizaciones from OrganizacionStrategos organizaciones" + condicionesConsulta + ordenConsulta);
    
    return consulta.list();
  }
}
