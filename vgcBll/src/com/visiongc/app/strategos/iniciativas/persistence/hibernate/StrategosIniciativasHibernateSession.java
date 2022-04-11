package com.visiongc.app.strategos.iniciativas.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativasPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class StrategosIniciativasHibernateSession
  extends StrategosHibernateSession
  implements StrategosIniciativasPersistenceSession
{
  public StrategosIniciativasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosIniciativasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getIniciativas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    int total = 0;
    
    String tablasConsulta = "";
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
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(iniciativa." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("organizacionId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("iniciativaId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuencia"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("anio"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + "anioFormulacion" + " like '%" + fieldValue + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("perspectivaId"))
        {
          tablasConsulta = tablasConsulta + ", IniciativaPerspectiva  iniPers";
          condicionesConsulta = condicionesConsulta + "iniciativa.iniciativaId = iniPers.pk.iniciativaId and iniPers.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("planId"))
        {
          tablasConsulta = tablasConsulta + ", IniciativaPlan  iniPlan";
          condicionesConsulta = condicionesConsulta + "iniciativa.iniciativaId = iniPlan.pk.iniciativaId and iniPlan.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("portafolioId"))
        {
          tablasConsulta = tablasConsulta + ", PortafolioIniciativa portafolioIniciativa";
          condicionesConsulta = condicionesConsulta + "iniciativa.iniciativaId = portafolioIniciativa.pk.iniciativaId and portafolioIniciativa.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("instrumentoId"))
        {
          tablasConsulta = tablasConsulta + ", InstrumentoIniciativa instrumentoIniciativa";
          condicionesConsulta = condicionesConsulta + "iniciativa.iniciativaId = instrumentoIniciativa.pk.iniciativaId and instrumentoIniciativa.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("tipoAlerta"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("historicoDate")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("estatusId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("tipoId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuencias"))
        {
          String[] ids = fieldValue.split(",");
          String frecuencias = null;
          for (int i = 0; i < ids.length; i++)
          {
            if (frecuencias == null) {
              frecuencias = ids[i] + ", ";
            } else {
              frecuencias = frecuencias + ids[i] + ", ";
            }
          }
          if (frecuencias != null)
          {
            frecuencias = frecuencias.substring(0, frecuencias.length() - 2);
            condicionesConsulta = condicionesConsulta + "iniciativa.frecuencia in (" + frecuencias + ") and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("excluirIds"))
        {
          String[] ids = fieldValue.split(",");
          String excluirIds = null;
          for (int i = 0; i < ids.length; i++)
          {
            if (excluirIds == null) {
              excluirIds = ids[i] + ", ";
            } else {
              excluirIds = excluirIds + ids[i] + ", ";
            }
          }
          if (excluirIds != null)
          {
            excluirIds = excluirIds.substring(0, excluirIds.length() - 2);
            condicionesConsulta = condicionesConsulta + "iniciativa.iniciativaId not in (" + excluirIds + ") and ";
            hayCondicionesConsulta = true;
          }
        }
      }
    }
    
    String ordenConsulta = "";
    
    if ((orden != null) && (!orden.equals("")))
    {
    	if(orden.equals("anio")){
        	orden="anioFormulacion";
        }
    	
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by iniciativa." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by iniciativa." + orden + " asc";
      } else {
        ordenConsulta = " order by iniciativa." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select distinct(iniciativa) from Iniciativa iniciativa" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List iniciativas = consulta.list();
    if (!getTotal) {
      total = iniciativas.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(iniciativas);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  
  public PaginaLista getIniciativasResponsable(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    int total = 0;
    
    String tablasConsulta = "";
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
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(iniciativa." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableCargarEjecutadoId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + "=" + fieldValue + " or ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableLograrMetaId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + "=" + fieldValue + " or ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableSeguimientoId"))
        {
          condicionesConsulta = condicionesConsulta + "iniciativa." + fieldName + "=" + fieldValue + " or ";
          hayCondicionesConsulta = true;
        }
       
      }
    }
    
    String ordenConsulta = "";
    if(orden.equals("anio")){
    	orden="anioFormulacion";
    }
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by iniciativa." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by iniciativa." + orden + " asc";
      } else {
        ordenConsulta = " order by iniciativa." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 4);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select distinct(iniciativa) from Iniciativa iniciativa" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List iniciativas = consulta.list();
    if (!getTotal) {
      total = iniciativas.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(iniciativas);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  
  public ListaMap getDependenciasIniciativa(Iniciativa iniciativa)
  {
    ListaMap dependencias = new ListaMap();
    Long proyectoId = iniciativa.getProyectoId() != null ? iniciativa.getProyectoId() : new Long(0L);
    
    Criteria consulta = null;
    
    consulta = session.createCriteria(PryProyecto.class);
    consulta.add(Restrictions.eq("proyectoId", proyectoId));
    List pryProyectos = consulta.list();
    dependencias.add(pryProyectos, "pryProyectos");
    
    consulta = session.createCriteria(PrdSeguimiento.class);
    consulta.add(Restrictions.eq("pk.iniciativaId", iniciativa.getIniciativaId()));
    List prdSeguimientos = consulta.list();
    dependencias.add(prdSeguimientos, "prdSeguimientos");
    
    consulta = session.createCriteria(PrdProducto.class);
    consulta.add(Restrictions.eq("iniciativaId", iniciativa.getIniciativaId()));
    List prdProductos = consulta.list();
    dependencias.add(prdProductos, "prdProductos");
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("objetoAsociadoId", iniciativa.getIniciativaId()));
    List objetosAsociados = consulta.list();
    dependencias.add(objetosAsociados, "objetosAsociados");
    
    consulta = session.createCriteria(PryActividad.class);
    consulta.add(Restrictions.eq("proyectoId", iniciativa.getProyectoId()));
    List actividades = consulta.list();
    dependencias.add(actividades, "actividades");
    
    return dependencias;
  }
  
  public ListaMap getDependenciasCiclicasIniciativa(Iniciativa iniciativa)
  {
    ListaMap dependenciasCiclicas = new ListaMap();
    
    Criteria consulta = null;
    
    if (iniciativa.getClaseId() != null)
    {
      consulta = session.createCriteria(ClaseIndicadores.class);
      consulta.add(Restrictions.eq("claseId", iniciativa.getClaseId()));
      List clases = consulta.list();
      dependenciasCiclicas.add(clases, "clases");
    }
    
    return dependenciasCiclicas;
  }
  
  public Iniciativa getIniciativaBasica(Long iniciativaId)
  {
    Query consulta = session.createQuery("select new Iniciativa(iniciativa.iniciativaId, iniciativa.organizacionId, iniciativa.nombre, iniciativa.naturaleza, iniciativa.frecuencia) from Iniciativa iniciativa where iniciativa.iniciativaId=?").setLong(0, iniciativaId.longValue());
    consulta.setCacheMode(CacheMode.IGNORE);
    Iniciativa iniciativa = (Iniciativa)consulta.uniqueResult();
    return iniciativa;
  }
  
  public boolean clearReferenciaProyectoIniciativa(Long iniciativaId)
  {
    int actualizados = 0;
    
    String hqlUpdate = null;
    
    hqlUpdate = "update Iniciativa ini set ini.proyectoId = null where ini.iniciativaId = :iniciativaId";
    Query actualizacion = session.createQuery(hqlUpdate).setLong("iniciativaId", iniciativaId.longValue());
    actualizados = actualizacion.executeUpdate();
    
    return actualizados == 1;
  }
  
  public boolean clearReferenciaIniciativaRelacionada(Long iniciativaId)
  {
    int actualizados = 0;
    
    String hqlUpdate = null;
    
    hqlUpdate = "update PryActividad act set act.objetoAsociadoId = null where act.objetoAsociadoId = :iniciativaId";
    Query actualizacion = session.createQuery(hqlUpdate).setLong("iniciativaId", iniciativaId.longValue());
    actualizados = actualizacion.executeUpdate();
    
    return actualizados == 1;
  }
  
  public boolean clearReferenciaIniciativaProblema(Long iniciativaEfectoId)
  {
    int actualizados = 0;
    
    String hqlUpdate = null;
    
    hqlUpdate = "update Problema p set p.iniciativaEfectoId = null where p.iniciativaEfectoId = :iniciativaId";
    Query actualizacion = session.createQuery(hqlUpdate).setLong("iniciativaId", iniciativaEfectoId.longValue());
    actualizados = actualizacion.executeUpdate();
    
    return actualizados == 1;
  }
  
  public boolean verificarOrganizacionIniciativaPorNombre(String nombre, Long organizacionId)
  {
    Criteria consulta = session.createCriteria(Iniciativa.class);
    consulta.add(Restrictions.ilike("nombre", nombre, MatchMode.EXACT));
    consulta.add(Restrictions.eq("organizacionId", organizacionId));
    List iniciativas = consulta.list();
    
    return iniciativas.size() > 0;
  }
  
  public boolean actualizarIniciativaProyecto(Long iniciativaId, Long proyectoId)
  {
    int actualizados = 0;
    String hqlUpdate = null;
    
    hqlUpdate = "update Iniciativa i set i.proyectoId = :proyectoId where i.iniciativaId = :iniciativaId";
    Query actualizacion = session.createQuery(hqlUpdate).setLong("iniciativaId", iniciativaId.longValue()).setLong("proyectoId", proyectoId.longValue());
    actualizados = actualizacion.executeUpdate();
    
    return actualizados == 1;
  }
  
  public int deleteReferenciasRelacionalesIniciativa(Long iniciativaId)
  {
    int resultado = 10000;
    
    try
    {
      String hqlUpdate = "delete IniciativaPerspectiva iniPers where iniPers.pk.iniciativaId = :iniciativaId";
      session.createQuery(hqlUpdate).setLong("iniciativaId", iniciativaId.longValue()).executeUpdate();
    }
    catch (Throwable t)
    {
      resultado = 10006;
    }
    
    return resultado;
  }
  
  public Byte getAlertaIniciativaPorSeguimientos(Long iniciativaId)
  {
    Byte alerta = null;
    Query consulta = session.createQuery("select seg from PrdSeguimiento seg where seg.pk.iniciativaId=? order by seg.pk.ano desc, seg.pk.periodo desc").setLong(0, iniciativaId.longValue());
    List seguimientos = consulta.list();
    if (seguimientos.size() > 0)
    {
      PrdSeguimiento seguimiento = (PrdSeguimiento)seguimientos.get(0);
      if (seguimiento != null) {
        alerta = seguimiento.getAlerta();
      }
    }
    return alerta;
  }
  
  public Iniciativa getIniciativaByIndicador(long indicadorId)
  {
    String sql = "select new Iniciativa(iniciativa.iniciativaId) from Iniciativa iniciativa, IndicadorIniciativa indicadorIniciativa where iniciativa.iniciativaId = indicadorIniciativa.pk.iniciativaId and indicadorIniciativa.pk.indicadorId=:indicadorId";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("indicadorId", indicadorId);
    
    Iniciativa iniciativa = (Iniciativa)consulta.uniqueResult();
    
    if (iniciativa != null) {
      return (Iniciativa)load(Iniciativa.class, new Long(iniciativa.getIniciativaId().longValue()));
    }
    return null;
  }
  
  public int updateCampo(Long iniciativaId, Map<?, ?> filtros) throws Throwable
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
        hqlUpdate = "update Iniciativa i set " + hqlUpdate + " where i.iniciativaId = :iniciativaId";
        
        Query actualizacion = session.createQuery(hqlUpdate).setLong("iniciativaId", iniciativaId.longValue());
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
  
  public Iniciativa getIniciativaByProyecto(long proyectoId)
  {
    String sql = "select new Iniciativa(iniciativa.iniciativaId) from Iniciativa iniciativa where iniciativa.proyectoId=:proyectoId";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("proyectoId", proyectoId);
    
    Iniciativa iniciativa = (Iniciativa)consulta.uniqueResult();
    
    if (iniciativa != null) {
      return (Iniciativa)load(Iniciativa.class, new Long(iniciativa.getIniciativaId().longValue()));
    }
    return null;
  }
  
  public Iniciativa getValoresOriginales(Long iniciativaId)
  {
    Iniciativa iniciativa = null;
    
    String hqlQuery = "select frecuencia, iniciativaId from Iniciativa iniciativa where iniciativa.iniciativaId = :iniciativaId";
    
    List<Object[]> resultado = session.createQuery(hqlQuery).setLong("iniciativaId", iniciativaId.longValue()).list();
    if (resultado.size() > 0)
    {
      iniciativa = new Iniciativa();
      
      Object[] valores = (Object[])resultado.get(0);
      
      iniciativa.setFrecuencia((Byte)valores[0]);
    }
    
    return iniciativa;
  }
  
  public List<Iniciativa> getIniciativasAsociadas(Long iniciativaId)
  {
    String sql = "select distinct(iniciativaAsociada) from PryActividad actividad, Iniciativa iniciativa, Iniciativa iniciativaAsociada";
    sql = sql + " where iniciativa.iniciativaId = :iniciativaId";
    sql = sql + " and actividad.objetoAsociadoClassName = 'Iniciativa'";
    sql = sql + " and actividad.proyectoId = iniciativa.proyectoId";
    sql = sql + " and iniciativaAsociada.iniciativaId = actividad.objetoAsociadoId";
    sql = sql + " order by iniciativaAsociada.nombre";
    
    Query query = session.createQuery(sql);
    query.setLong("iniciativaId", iniciativaId.longValue());
    
    return query.list();
  }
  
  public Map<Long, Byte> getAlertasIniciativas(Map<String, String> iniciativaIds)
  {
    Map<Long, Byte> alertas = new HashMap();
    
    if ((iniciativaIds != null) && (iniciativaIds.size() > 0))
    {
      String ids = "";
      
      for (Iterator<String> iter = iniciativaIds.values().iterator(); iter.hasNext();)
      {
        String iniciativaId = (String)iter.next();
        ids = ids + iniciativaId.toString() + ",";
      }
      ids = ids.substring(0, ids.length() - 1);
      String sql = "select iniciativa.iniciativaId, iniciativa.alerta from Iniciativa iniciativa where iniciativa.iniciativaId in (" + ids + ")";
      Query consulta = session.createQuery(sql);
      List<Object[]> resultados = consulta.list();
      if (resultados.size() > 0)
      {
        for (Iterator<Object[]> iter = resultados.iterator(); iter.hasNext();)
        {
          Object[] resultado = (Object[])iter.next();
          alertas.put((Long)resultado[0], (Byte)resultado[1]);
        }
      }
    }
    
    return alertas;
  }
  
  public int marcarHistorico(String iniciativas)
  {
    Date fecha = new Date();
    String hqlUpdate = null;
    hqlUpdate = "update Iniciativa p set p.historicoDate = :fecha where p.iniciativaId in (" + iniciativas + ")";
    Query actualizacion = session.createQuery(hqlUpdate).setDate("fecha", fecha);
    actualizacion.executeUpdate();
    
    return 10000;
  }
  
  public int desMarcarHistorico(String iniciativas)
  {
    String hqlUpdate = null;
    hqlUpdate = "update Iniciativa p set p.historicoDate = null where p.iniciativaId in (" + iniciativas + ")";
    Query actualizacion = session.createQuery(hqlUpdate);
    actualizacion.executeUpdate();
    
    return 10000;
  }


  public List<Iniciativa> getIniciativasEjecutar(Long iniciativaId, Long organizacionId, Long planId, Integer ano) {
		
	  Query query= null;
	  
	  if(iniciativaId != null){
		 String sql ="select distinct(ini) from Iniciativa ini"; 
		 sql = sql + " where ini.iniciativaId = :iniciativaId";
		 sql = sql + " and ini.estatusId = 2";
		 sql = sql + " and ini.anioFormulacion = :anio";
		 
		 query=session.createQuery(sql);
		 
		 if(ano != null){
			 query.setLong("iniciativaId", iniciativaId.longValue());
			 query.setLong("anio", ano);
		 }
		 
	  }else if(organizacionId != null){
		  String sql ="select distinct(ini) from Iniciativa ini"; 
			 sql = sql + " where ini.organizacionId = :organizacionId";
			 sql = sql + " and ini.estatusId = 2";
			 sql = sql + " and ini.anioFormulacion = :anio";
			 
			 query=session.createQuery(sql);
			 
			 if(ano != null){
				 query.setLong("organizacionId", organizacionId.longValue());
				 query.setLong("anio", ano);
			 }
	  }
	  else if(planId != null){
		
		  String sql ="select distinct(ini) from Iniciativa ini , IniciativaPlan iniPlan"; 
		     sql = sql +" where ini.iniciativaId = iniPlan.pk.iniciativaId "
		     		+ " and iniPlan.pk.planId = :planId";
		  		  
			 sql = sql + " and ini.estatusId = 2";
			 sql = sql + " and ini.anioFormulacion = :anio";
			 
			 query=session.createQuery(sql);
			 
			 if(ano != null){
				query.setLong("planId", planId); 
				query.setLong("anio", ano);
			 }
		  
		  
	  }
	  else{
		  String sql ="select distinct(ini) from Iniciativa ini"; 
		  
			 sql = sql + " where ini.estatusId = 2";
			 sql = sql + " and ini.anioFormulacion = :anio";
			 
			 query=session.createQuery(sql);
			 
			 if(ano != null){
				query.setLong("anio", ano);
			 }
	  }
	  
	  
	 return query.list();
  }
}
