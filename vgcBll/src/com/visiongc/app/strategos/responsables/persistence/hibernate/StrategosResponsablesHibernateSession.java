package com.visiongc.app.strategos.responsables.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.responsables.persistence.StrategosResponsablesPersistenceSession;
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
import org.hibernate.criterion.Restrictions;

public class StrategosResponsablesHibernateSession
  extends StrategosHibernateSession implements StrategosResponsablesPersistenceSession
{
  public StrategosResponsablesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosResponsablesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getResponsables(int pagina, int tamanoPagina, String[] orden, String[] tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(Responsable.class);
    
    if (filtros != null)
    {
      boolean orgIdOrGlobal = (filtros.containsKey("organizacionId")) && (filtros.containsKey("tipo")) && (getValorCondicionConsulta(filtros.get("tipo")).equals("1"));
      
      if (orgIdOrGlobal)
      {
        consulta.add(Restrictions.or(Restrictions.eq("organizacionId", new Long((String)filtros.get("organizacionId"))), Restrictions.eq("tipo", Boolean.valueOf(getValorCondicionConsulta(filtros.get("tipo")) == "1"))));
        
        filtros.remove("organizacionId");
        filtros.remove("tipo");
      }
      
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
        if (fieldName.equals("nombre"))
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        if (fieldName.equals("cargo"))
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        if (fieldName.equals("organizacionId"))
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        if (fieldName.equals("responsableId"))
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        if (fieldName.equals("tipo"))
          consulta.add(Restrictions.eq(fieldName, new Boolean(fieldValue)));
        if (fieldName.equals("esGrupo"))
          consulta.add(Restrictions.eq(fieldName, new Boolean(fieldValue)));
        if (fieldName.equals("usuarioId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public List getResponsablesAsociables(boolean global, long organizacionId)
  {
    return session.createQuery("select responsable from Responsable responsable where responsable.organizacionId = ? or tipo=1").setLong(0, organizacionId).list();
  }
  
  public ListaMap getDependencias(Responsable responsable)
  {
    ListaMap dependencias = new ListaMap();
    
    Long responsableId = responsable.getResponsableId();
    Criteria consulta = null;
    

    consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("responsableCargarEjecutadoId", responsableId));
    List<Indicador> responsableCargarEjecutado = consulta.list();
    dependencias.add(responsableCargarEjecutado, "IndicadorresponsableCargarEjecutadoId");
    
    consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("responsableCargarMetaId", responsableId));
    List<Indicador> responsableCargarMeta = consulta.list();
    dependencias.add(responsableCargarMeta, "IndicadorresponsableCargarMetaId");
    
    consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("responsableFijarMetaId", responsableId));
    List<Indicador> responsableFijarMeta = consulta.list();
    dependencias.add(responsableFijarMeta, "IndicadorresponsableFijarMetaId");
    
    consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("responsableLograrMetaId", responsableId));
    List<Indicador> responsableLograrMeta = consulta.list();
    dependencias.add(responsableLograrMeta, "IndicadorresponsableLograrMetaId");
    
    consulta = session.createCriteria(Indicador.class);
    consulta.add(Restrictions.eq("responsableSeguimientoId", responsableId));
    List<Indicador> responsableSeguimiento = consulta.list();
    dependencias.add(responsableSeguimiento, "IndicadorresponsableSeguimientoId");
    

    consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.eq("responsableCargarEjecutadoId", responsableId));
    List<Iniciativa> responsableIniciativa = consulta.list();
    dependencias.add(responsableIniciativa, "IniciativaresponsableCargarEjecutadoId");
    
    consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.eq("responsableCargarMetaId", responsableId));
    responsableIniciativa = consulta.list();
    dependencias.add(responsableIniciativa, "IniciativaresponsableCargarMetaId");
    
    consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.eq("responsableFijarMetaId", responsableId));
    responsableIniciativa = consulta.list();
    dependencias.add(responsableIniciativa, "IniciativaresponsableFijarMetaId");
    
    consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.eq("responsableLograrMetaId", responsableId));
    responsableIniciativa = consulta.list();
    dependencias.add(responsableIniciativa, "IniciativaresponsableLograrMetaId");
    
    consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.eq("responsableSeguimientoId", responsableId));
    responsableIniciativa = consulta.list();
    dependencias.add(responsableIniciativa, "IniciativaresponsableSeguimientoId");
    

    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("responsableCargarEjecutadoId", responsableId));
    List<PryActividad> responsableActividad = consulta.list();
    dependencias.add(responsableActividad, "ActividadresponsableCargarEjecutadoId");
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("responsableCargarMetaId", responsableId));
    responsableActividad = consulta.list();
    dependencias.add(responsableActividad, "ActividadresponsableCargarMetaId");
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("responsableFijarMetaId", responsableId));
    responsableActividad = consulta.list();
    dependencias.add(responsableActividad, "ActividadresponsableFijarMetaId");
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("responsableLograrMetaId", responsableId));
    responsableActividad = consulta.list();
    dependencias.add(responsableActividad, "ActividadresponsableLograrMetaId");
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("responsableSeguimientoId", responsableId));
    responsableActividad = consulta.list();
    dependencias.add(responsableActividad, "ActividadresponsableSeguimientoId");
    

    consulta = session.createCriteria(Problema.class);
    consulta.add(Restrictions.eq("auxiliarId", responsableId));
    List<Problema> responsableProblema = consulta.list();
    dependencias.add(responsableProblema, "ProblemaauxiliarId");
    
    consulta = session.createCriteria(Problema.class);
    consulta.add(Restrictions.eq("responsableId", responsableId));
    responsableProblema = consulta.list();
    dependencias.add(responsableProblema, "ProblemaresponsableId");
    

    consulta = session.createCriteria(ResponsableAccion.class);
    consulta.add(Restrictions.eq("pk.responsableId", responsableId));
    List<ResponsableAccion> responsableAccion = consulta.list();
    dependencias.add(responsableAccion, "AccionresponsableId");
    

    consulta = session.createCriteria(Perspectiva.class);
    consulta.add(Restrictions.eq("responsableId", responsableId));
    List<Perspectiva> responsablePerspectiva = consulta.list();
    dependencias.add(responsablePerspectiva, "PerspectivaresponsableId");
    
    return dependencias;
  }
}
