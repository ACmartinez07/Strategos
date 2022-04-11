package com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryActividadesPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;




public class StrategosPryActividadesHibernateSession
  extends StrategosHibernateSession
  implements StrategosPryActividadesPersistenceSession
{
  public StrategosPryActividadesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPryActividadesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getActividades(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    int total = 0;
    
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
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
        if (fieldName.equals("visible"))
        {
          condicionesConsulta = condicionesConsulta + "actividad." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(actividad." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("padreId"))
        {
          if (fieldValue == null) {
            condicionesConsulta = condicionesConsulta + "actividad.padreId is null and ";
          } else
            condicionesConsulta = condicionesConsulta + "actividad." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("proyectoId"))
        {
          condicionesConsulta = condicionesConsulta + "actividad." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("indicadorId"))
        {
          condicionesConsulta = condicionesConsulta + "actividad." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("iniciativaId"))
        {
          tablasConsulta = tablasConsulta + ", Iniciativa  iniciativa";
          condicionesConsulta = condicionesConsulta + "actividad.proyectoId = iniciativa.proyectoId and iniciativa." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("objetoAsociadoId"))
        {
          condicionesConsulta = condicionesConsulta + "actividad." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by actividad." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by actividad." + orden + " asc";
      } else {
        ordenConsulta = " order by actividad." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select distinct(actividad) from PryActividad actividad" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List actividades = consulta.list();
    if (!getTotal) {
      total = actividades.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(actividades);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  public List getRaices(long proyectoId)
  {
    String sql = "select actividad from PryActividad actividad where actividad.proyectoId = :proyectoId and actividad.padreId is null order by actividad.fila";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("proyectoId", proyectoId);
    
    return consulta.list();
  }
  
  public int getMaximaFila(long proyectoId)
  {
    String sql = "select max(fila) from PryActividad actividad where actividad.proyectoId = :proyectoId";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("proyectoId", proyectoId);
    
    Integer maximaFila = (Integer)consulta.uniqueResult();
    
    return maximaFila == null ? 0 : maximaFila.intValue();
  }
  
  public ListaMap getDependenciasActividad(PryActividad actividad)
  {
    ListaMap dependencias = new ListaMap();
    
    Criteria consulta = null;
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("padreId", actividad.getActividadId()));
    List<PryActividad> pryActividadesHijas = consulta.list();
    dependencias.add(pryActividadesHijas, "pryActividades");
    
    consulta = session.createCriteria(ClaseIndicadores.class);
    consulta.add(Restrictions.eq("claseId", actividad.getClaseId()));
    List<PryActividad> pryActividadesClase = consulta.list();
    dependencias.add(pryActividadesClase, "pryActividadesClase");
    
    return dependencias;
  }
  
  public List<PryActividad> getActividades(Long actividadId, byte tipo)
  {
    List<PryActividad> actividades = new ArrayList();
    
    if (tipo == 3)
    {
      PryActividad pryActividad = (PryActividad)load(PryActividad.class, actividadId);
      if (pryActividad != null) {
        actividades.add(pryActividad);
      }
    }
    return actividades;
  }
  
  public int updatePesosActividad(List<?> actividades, Usuario usuario)
  {
    if (actividades != null)
    {
      String sql = "update PryInformacionActividad act set act.peso = :peso where act.actividadId = :actividadId";
      String sqlNulo = "update PryInformacionActividad act set act.peso = null where act.actividadId = :actividadId";
      for (Iterator<?> iter = actividades.iterator(); iter.hasNext();)
      {
        PryActividad actividad = (PryActividad)iter.next();
        
        Query update = null;
        if ((actividad != null) && (actividad.getPryInformacionActividad() != null) && (actividad.getPryInformacionActividad().getPeso() != null)) {
          update = session.createQuery(sql);
        } else
          update = session.createQuery(sqlNulo);
        update.setLong("actividadId", actividad.getActividadId().longValue());
        if ((actividad != null) && (actividad.getPryInformacionActividad() != null) && (actividad.getPryInformacionActividad().getPeso() != null)) {
          update.setDouble("peso", actividad.getPryInformacionActividad().getPeso().doubleValue());
        }
        update.executeUpdate();
      }
    }
    
    return 10000;
  }
  
  public PryActividad getActividadByIndicador(long indicadorId)
  {
    String sql = "select new PryActividad(actividad.actividadId) from PryActividad actividad where actividad.indicadorId=:indicadorId";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("indicadorId", indicadorId);
    
    PryActividad actividad = (PryActividad)consulta.uniqueResult();
    
    if (actividad != null) {
      return (PryActividad)load(PryActividad.class, new Long(actividad.getActividadId().longValue()));
    }
    return null;
  }
  
  public List<PryActividad> getObjetoAsociados(Long proyectoId, String className)
  {
    String sql = "select actividad from PryActividad actividad where actividad.proyectoId=:proyectoId and actividad.objetoAsociadoClassName=:className";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("proyectoId", proyectoId.longValue());
    consulta.setString("className", className);
    
    return consulta.list();
  }
  
  public int updateCampo(Long actividadId, Map<?, ?> filtros) throws Throwable
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
        hqlUpdate = "update PryActividad i set " + hqlUpdate + " where i.actividadId = :actividadId";
        
        Query actualizacion = session.createQuery(hqlUpdate).setLong("actividadId", actividadId.longValue());
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


  public List<PryActividad> getActividades(Long proyectoId) {
	  Query query= null;
	  
	  if(proyectoId != null){
		  
		 String sql ="select distinct(act) from PryActividad act"; 
		 sql = sql + " where act.proyectoId = :proyectoId";
		
		 query=session.createQuery(sql);
		 query.setLong("proyectoId", proyectoId.longValue());
		
		
	  }
	  
	 return query.list();
  }
  
}
