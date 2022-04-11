package com.visiongc.app.strategos.planes.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.app.strategos.planes.model.PerspectivaEstadoPK;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class StrategosPerspectivasHibernateSession extends StrategosHibernateSession implements com.visiongc.app.strategos.planes.persistence.StrategosPerspectivasPersistenceSession
{
  public StrategosPerspectivasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPerspectivasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public List<Perspectiva> getPerspectivas(String[] orden, String[] tipoOrden, Map<String, Object> filtros)
  {
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtros != null)
    {
      Iterator<String> iter = filtros.keySet().iterator();
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
          condicionesConsulta = condicionesConsulta + "lower(perspectiva." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("descripcion"))
        {
          condicionesConsulta = condicionesConsulta + "lower(perspectiva." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuencia"))
        {
          condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("excluirIds"))
        {
          String[] ids = fieldValue.split(",");
          for (int i = 0; i < ids.length; i++)
          {
            Long id = new Long(ids[i]);
            condicionesConsulta = condicionesConsulta + "perspectiva.perspectivaId != " + id.toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("indicadorId"))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPerspectiva indPers";
          condicionesConsulta = condicionesConsulta + "perspectiva.perspectivaId = indPers.pk.perspectivaId and indPers.pk." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("planId"))
        {
          condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("padreId"))
        {
          if (fieldValue != null) {
            condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          } else
            condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + " is null and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("ano"))
        {
          if (fieldValue != null) {
            condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          } else
            condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + " is null and ";
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
          ordenConsulta = ordenConsulta + "perspectiva." + ordenActual + " asc,";
        } else if (tipoOrdenActual.equalsIgnoreCase("asc")) {
          ordenConsulta = ordenConsulta + "perspectiva." + ordenActual + " asc,";
        } else {
          ordenConsulta = ordenConsulta + "perspectiva." + ordenActual + " desc,";
        }
      }
      ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 1);
    }
    
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select distinct(perspectiva) from Perspectiva perspectiva" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    return consulta.list();
  }
  
  public long getNumeroAsociacionesIniciativaPerspectivaPorPlan(Long iniciativaId, Long planId)
  {
    Query consulta = session.createQuery("select count(*) FROM IniciativaPerspectiva iniciativaPerspectiva where iniciativaPerspectiva.pk.perspectivaId in (select perspectiva.perspectivaId from Perspectiva perspectiva where perspectiva.planId = ?) and iniciativaPerspectiva.pk.iniciativaId = ?");
    consulta.setLong(0, planId.longValue()).setLong(1, iniciativaId.longValue());
    Long total = (Long)consulta.list().get(0);
    
    return total.longValue();
  }
  
  public Perspectiva getPerspectivaRaiz(Long planId)
  {
    Perspectiva perspectiva = new Perspectiva();
    
    Criteria consulta = session.createCriteria(Perspectiva.class);
    consulta.add(Restrictions.isNull("padreId"));
    consulta.add(Restrictions.eq("planId", planId));
    perspectiva = (Perspectiva)consulta.uniqueResult();
    
    return perspectiva;
  }
  
  public int deleteReferenciasForaneasPerspectiva(Long perspectivaId)
  {
    int resultado = 10000;
    
    try
    {
      String hqlUpdate = "update Perspectiva perspectiva set perspectiva.claseId = null where perspectiva.perspectivaId = :perspectivaId";
      session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).executeUpdate();
      
      hqlUpdate = "delete IniciativaPerspectiva iniPers where iniPers.pk.perspectivaId = :perspectivaId";
      session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).executeUpdate();
      
      hqlUpdate = "delete IndicadorPerspectiva indPers where indPers.pk.perspectivaId = :perspectivaId";
      session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).executeUpdate();
      
      hqlUpdate = "delete PerspectivaEstado perspectivaEstado where perspectivaEstado.pk.perspectivaId = :perspectivaId";
      session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).executeUpdate();
      
      hqlUpdate = "delete PerspectivaRelacion perspectivaRelacion where perspectivaRelacion.perspectiva.perspectivaId = :perspectivaId";
      session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).executeUpdate();
      
      hqlUpdate = "delete PerspectivaRelacion perspectivaRelacion where perspectivaRelacion.relacion.perspectivaId = :perspectivaId";
      session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).executeUpdate();
    }
    catch (Throwable t)
    {
      resultado = 10004;
    }
    
    return resultado;
  }
  
  public ListaMap getDependenciasPerspectiva(Perspectiva perspectiva)
  {
    ListaMap dependencias = new ListaMap();
    
    Criteria consulta = session.createCriteria(ClaseIndicadores.class);
    consulta.add(Restrictions.eq("claseId", perspectiva.getClaseId()));
    List clases = consulta.list();
    dependencias.add(clases, "clases");
    
    return dependencias;
  }
  
  public int deletePerspectivaEstados(Long perspectivaId, Byte tipoEstado, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    String hqlUpdate = "delete PerspectivaEstado persEstado where persEstado.pk.perspectivaId = :perspectivaId and persEstado.pk.tipo = :tipo";
    boolean continuar = false;
    
    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) {
      if (anoInicio.intValue() != anoFinal.intValue()) {
        hqlUpdate = hqlUpdate + " and (((persEstado.pk.ano = :anoInicio) and (persEstado.pk.periodo >= :periodoInicio))";
        hqlUpdate = hqlUpdate + " or ((persEstado.pk.ano > :anoInicio) and (persEstado.pk.ano < :anoFinal))";
        hqlUpdate = hqlUpdate + " or ((persEstado.pk.ano = :anoFinal) and (persEstado.pk.periodo <= :periodoFinal)))";
      } else {
        hqlUpdate = hqlUpdate + " and ((persEstado.pk.ano = :anoInicio) and (persEstado.pk.periodo >= :periodoInicio)";
        hqlUpdate = hqlUpdate + " and (persEstado.pk.ano = :anoFinal) and (persEstado.pk.periodo <= :periodoFinal))";
      }
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and persEstado.pk.ano >= :anoInicio";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and persEstado.pk.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and persEstado.pk.ano >= :anoInicio";
      hqlUpdate = hqlUpdate + " and persEstado.pk.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and (((persEstado.pk.ano = :anoInicio) and (persEstado.pk.periodo >= :periodoInicio))";
      hqlUpdate = hqlUpdate + " or (persEstado.pk.ano > :anoInicio))";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) {
      hqlUpdate = hqlUpdate + " and (((persEstado.pk.ano = :anoFinal) and (persEstado.pk.periodo <= :periodoFinal))";
      hqlUpdate = hqlUpdate + " or (persEstado.pk.ano < :anoFinal))";
      continuar = true;
    }
    
    if (continuar) {
      Query update = session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue()).setByte("tipo", tipoEstado.byteValue());
      
      if (anoInicio != null) {
        update.setInteger("anoInicio", anoInicio.intValue());
      }
      if (anoFinal != null) {
        update.setInteger("anoFinal", anoFinal.intValue());
      }
      if (periodoInicio != null) {
        update.setInteger("periodoInicio", periodoInicio.intValue());
      }
      if (periodoFinal != null) {
        update.setInteger("periodoFinal", periodoFinal.intValue());
      }
      
      update.executeUpdate();
    }
    
    return 10000;
  }
  
  public List getIndicadoresPerspectiva(Long perspectivaId)
  {
    Query consulta = session.createQuery("select indicadorPerspectiva from IndicadorPerspectiva indicadorPerspectiva where indicadorPerspectiva.pk.perspectivaId = :perspectivaId");
    consulta.setLong("perspectivaId", perspectivaId.longValue());
    
    return consulta.list();
  }
  
  public List<Perspectiva> getIndicadoresPorPerspectiva(Long indicadorId, Long planId)
  {
    String queryConsulta = "select distinct(perspectiva) from Perspectiva perspectiva where perspectiva.perspectivaId in (select indicadorPerspectiva.pk.perspectivaId from IndicadorPerspectiva indicadorPerspectiva where indicadorPerspectiva.pk.indicadorId = " + indicadorId.toString() + ")";
    if (planId.longValue() != 0L)
      queryConsulta = queryConsulta + " and perspectiva.planId = " + planId.toString();
    Query consulta = session.createQuery(queryConsulta);
    
    return consulta.list();
  }
  
  public int actualizarPerspectivaUltimoEstado(Long perspectivaId) {
    int resultado = 10000;
    
    String sql = "select perspectivaEstado from PerspectivaEstado perspectivaEstado where perspectivaEstado.pk.perspectivaId = :perspectivaId and perspectivaEstado.pk.tipo = :tipo order by perspectivaEstado.pk.ano desc, perspectivaEstado.pk.periodo desc";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("perspectivaId", perspectivaId.longValue()).setByte("tipo", TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue());
    List estados = consulta.list();
    
    boolean actualizado = false;
    
    int index = 0;
    while ((!actualizado) && (index < estados.size())) {
      PerspectivaEstado estado = (PerspectivaEstado)estados.get(index);
      
      if (estado.getEstado() != null) {
        String fechaUltimaMedicion = estado.getPk().getPeriodo().toString() + "/" + estado.getPk().getAno();
        
        Query update = session.createQuery("update Perspectiva perspectiva set perspectiva.fechaUltimaMedicion = :fechaUltimaMedicion, perspectiva.ultimaMedicionAnual = :ultimaMedicion where perspectiva.perspectivaId = :perspectivaId");
        update.setString("fechaUltimaMedicion", fechaUltimaMedicion).setDouble("ultimaMedicion", estado.getEstado().doubleValue()).setLong("perspectivaId", perspectivaId.longValue());
        update.executeUpdate();
        actualizado = true;
      } else {
        index++;
      }
    }
    

    consulta = session.createQuery(sql);
    consulta.setLong("perspectivaId", perspectivaId.longValue()).setByte("tipo", TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue());
    estados = consulta.list();
    
    actualizado = false;
    
    index = 0;
    while ((!actualizado) && (index < estados.size())) {
      PerspectivaEstado estado = (PerspectivaEstado)estados.get(index);
      
      if (estado.getEstado() != null) {
        Query update = session.createQuery("update Perspectiva perspectiva set perspectiva.ultimaMedicionParcial = :ultimaMedicion where perspectiva.perspectivaId = :perspectivaId");
        update.setDouble("ultimaMedicion", estado.getEstado().doubleValue()).setLong("perspectivaId", perspectivaId.longValue());
        update.executeUpdate();
        actualizado = true;
      } else {
        index++;
      }
    }
    
    return resultado;
  }
  
  public int updatePesosIndicadoresPerspectiva(List indicadoresPerspectiva, Usuario usuario) {
    if (indicadoresPerspectiva != null) {
      String sql = "update IndicadorPerspectiva indPerspectiva set indPerspectiva.peso = :peso where indPerspectiva.pk.indicadorId = :indicadorId and indPerspectiva.pk.perspectivaId = :perspectivaId";
      String sqlNulo = "update IndicadorPerspectiva indPerspectiva set indPerspectiva.peso = null where indPerspectiva.pk.indicadorId = :indicadorId and indPerspectiva.pk.perspectivaId = :perspectivaId";
      for (Iterator iter = indicadoresPerspectiva.iterator(); iter.hasNext();) {
        IndicadorPerspectiva indicadorPerspectiva = (IndicadorPerspectiva)iter.next();
        
        Query update = null;
        if (indicadorPerspectiva.getPeso() != null) {
          update = session.createQuery(sql);
        } else {
          update = session.createQuery(sqlNulo);
        }
        update.setLong("indicadorId", indicadorPerspectiva.getPk().getIndicadorId().longValue());
        update.setLong("perspectivaId", indicadorPerspectiva.getPk().getPerspectivaId().longValue());
        if (indicadorPerspectiva.getPeso() != null) {
          update.setDouble("peso", indicadorPerspectiva.getPeso().doubleValue());
        }
        update.executeUpdate();
      }
    }
    
    return 10000;
  }
  
  public Map<Long, Byte> getAlertasPerspectivas(Map<String, String> perspectivaIds, Byte tipo)
  {
    Map<Long, Byte> alertas = new HashMap();
    
    if ((perspectivaIds != null) && (perspectivaIds.size() > 0))
    {
      String ids = "";
      
      for (Iterator<String> iter = perspectivaIds.values().iterator(); iter.hasNext();)
      {
        String perspectivaId = (String)iter.next();
        ids = ids + perspectivaId.toString() + ",";
      }
      
      ids = ids.substring(0, ids.length() - 1);
      
      String sql = "select perspectiva.perspectivaId, ";
      sql = sql + (tipo.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue() ? "perspectiva.alertaAnual" : tipo.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue() ? "perspectiva.alertaParcial" : "");
      sql = sql + " from Perspectiva perspectiva";
      sql = sql + " where perspectiva.perspectivaId in (" + ids + ")";
      
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
  
  public List<PerspectivaEstado> getPerspectivaEstados(Long perspectivaId, Byte tipo, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta)
  {
    String sql = "from PerspectivaEstado perspectivaEstado";
    
    if ((perspectivaId != null) || (tipo != null) || (anoDesde != null) || (anoHasta != null) || (periodoDesde != null) || (periodoHasta != null)) {
      sql = sql + " where ";
    }
    sql = sql + "perspectivaEstado.pk.perspectivaId = :perspectivaId";
    
    if (tipo != null) {
      sql = sql + " and perspectivaEstado.pk.tipo = :tipo";
    }
    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (anoDesde.intValue() == anoHasta.intValue()))
    {
      sql = sql + " and (" + "((perspectivaEstado.pk.periodo >= :periodoDesde and perspectivaEstado.pk.ano = :anoDesde) and " + "(perspectivaEstado.pk.periodo <= :periodoHasta and perspectivaEstado.pk.ano = :anoHasta)) ";
      sql = sql + ")";
    }
    else if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null)) {
      sql = sql + " and ((perspectivaEstado.pk.periodo >= :periodoDesde and perspectivaEstado.pk.ano = :anoDesde) or " + "(perspectivaEstado.pk.periodo <= :periodoHasta and perspectivaEstado.pk.ano = :anoHasta) " + " or (perspectivaEstado.pk.ano > :anoDesde and perspectivaEstado.pk.ano < :anoHasta))";
    } else if ((anoDesde != null) && (periodoDesde != null)) {
      sql = sql + " and (" + "(perspectivaEstado.pk.periodo >= :periodoDesde and perspectivaEstado.pk.ano = :anoDesde) or " + "(perspectivaEstado.pk.ano > :anoDesde)" + ")";
    } else if ((anoHasta != null) && (periodoHasta != null)) {
      sql = sql + " and (" + "(perspectivaEstado.pk.periodo <= :periodoHasta and perspectivaEstado.pk.ano = :anoHasta) or " + "(perspectivaEstado.pk.ano < :anoHasta)" + ")";
    } else if ((anoDesde != null) && (anoHasta != null)) {
      sql = sql + " and (perspectivaEstado.pk.ano >= :anoDesde and perspectivaEstado.pk.ano <= :anoHasta)";
    } else if (anoDesde != null) {
      sql = sql + " and perspectivaEstado.pk.ano >= :anoDesde";
    } else if (anoHasta != null) {
      sql = sql + " and perspectivaEstado.pk.ano <= :anoHasta";
    }
    sql = sql + " order by perspectivaEstado.pk.perspectivaId, perspectivaEstado.pk.tipo, perspectivaEstado.pk.ano, perspectivaEstado.pk.periodo";
    
    Query query = session.createQuery(sql);
    
    if (perspectivaId != null)
      query.setLong("perspectivaId", perspectivaId.longValue());
    if (tipo != null)
      query.setByte("tipo", tipo.byteValue());
    if (anoDesde != null)
      query.setInteger("anoDesde", anoDesde.intValue());
    if (anoHasta != null)
      query.setInteger("anoHasta", anoHasta.intValue());
    if (periodoDesde != null)
      query.setInteger("periodoDesde", periodoDesde.intValue());
    if (periodoHasta != null) {
      query.setInteger("periodoHasta", periodoHasta.intValue());
    }
    return query.list();
  }
  
  public Perspectiva getPerspectivaValoresOriginales(Long perspectivaId)
  {
    Perspectiva perspectiva = null;
    
    String hqlQuery = "select frecuencia from Perspectiva pers where pers.perspectivaId = :perspectivaId";
    
    List<Byte> resultado = session.createQuery(hqlQuery).setLong("perspectivaId", perspectivaId.longValue()).list();
    if (resultado.size() > 0)
    {
      perspectiva = new Perspectiva();
      
      Byte frecuencia = (Byte)resultado.get(0);
      
      perspectiva.setFrecuencia(frecuencia);
    }
    
    return perspectiva;
  }
  
  public int actualizarDatosPerspectiva(Long perspectivaId, Double ultimaMedicionAnual, Double ultimaMedicionParcial, String fechaUltimaMedicion) throws Throwable
  {
    int actualizados = 0;
    try
    {
      String hqlUpdate = "update Perspectiva p set";
      
      if (ultimaMedicionAnual == null) {
        hqlUpdate = hqlUpdate + " p.ultimaMedicionAnual = null";
      } else {
        hqlUpdate = hqlUpdate + " p.ultimaMedicionAnual = :ultimaMedicionAnual";
      }
      if (fechaUltimaMedicion == null) {
        hqlUpdate = hqlUpdate + ", p.fechaUltimaMedicion = null";
      } else {
        hqlUpdate = hqlUpdate + ", p.fechaUltimaMedicion = :fechaUltimaMedicion";
      }
      if (ultimaMedicionAnual == null)
        hqlUpdate = hqlUpdate + ", p.alertaAnual = null";
      if (ultimaMedicionParcial == null) {
        hqlUpdate = hqlUpdate + ", p.alertaParcial = null";
      }
      if (ultimaMedicionParcial == null) {
        hqlUpdate = hqlUpdate + ", p.ultimaMedicionParcial = null where p.perspectivaId = :perspectivaId";
      } else {
        hqlUpdate = hqlUpdate + ", p.ultimaMedicionParcial = :ultimaMedicionParcial where p.perspectivaId = :perspectivaId";
      }
      Query actualizacion = session.createQuery(hqlUpdate);
      
      if (ultimaMedicionAnual != null)
        actualizacion.setDouble("ultimaMedicionAnual", ultimaMedicionAnual.doubleValue());
      if (ultimaMedicionParcial != null)
        actualizacion.setDouble("ultimaMedicionParcial", ultimaMedicionParcial.doubleValue());
      if (fechaUltimaMedicion != null) {
        actualizacion.setString("fechaUltimaMedicion", fechaUltimaMedicion);
      }
      actualizacion = actualizacion.setLong("perspectivaId", perspectivaId.longValue());
      actualizados = actualizacion.executeUpdate();
    }
    catch (Throwable t)
    {
      if (ConstraintViolationException.class.isInstance(t)) {
        return 10006;
      }
      throw t;
    }
    
    if (actualizados == 1) {
      return 10000;
    }
    return 10001;
  }
  
  public List<Perspectiva> getPerspectivasAsociadas(Long perspectivaId)
  {
    String sql = "select distinct(perspectiva) from Perspectiva perspectiva, PerspectivaRelacion persRelacion";
    sql = sql + " where persRelacion.pk.perspectivaId = perspectiva.perspectivaId";
    sql = sql + " and persRelacion.pk.relacionId = :perspectivaId";
    sql = sql + " order by perspectiva.padreId";
    
    Query query = session.createQuery(sql);
    query.setLong("perspectivaId", perspectivaId.longValue());
    
    return query.list();
  }
  
  public List<PerspectivaEstado> getPerspectivaEstadosUltimoPeriodo(Long planId, Byte tipo, Integer ano)
  {
    String sql = "SELECT pe ";
    sql = sql + "FROM Perspectiva per, PerspectivaEstado pe ";
    sql = sql + "WHERE per.planId = :planId ";
    if (tipo != null)
      sql = sql + "AND pe.pk.tipo = :tipo ";
    if (ano != null)
      sql = sql + "AND pe.pk.ano <= :ano ";
    sql = sql + "AND per.perspectivaId = pe.pk.perspectivaId ";
    sql = sql + " order by pe.pk.perspectivaId, pe.pk.tipo, pe.pk.ano, pe.pk.periodo";
    
    Query query = session.createQuery(sql);
    
    query.setLong("planId", planId.longValue());
    if (tipo != null)
      query.setByte("tipo", tipo.byteValue());
    if (ano != null) {
      query.setInteger("ano", ano.intValue());
    }
    List<PerspectivaEstado> resultados = query.list();
    List<PerspectivaEstado> estados = new ArrayList();
    if (resultados.size() > 0)
    {
      for (Iterator<PerspectivaEstado> iter = resultados.iterator(); iter.hasNext();)
      {
        PerspectivaEstado resultado = (PerspectivaEstado)iter.next();
        Boolean hayEstado = Boolean.valueOf(false);
        for (Iterator<PerspectivaEstado> iterEstado = estados.iterator(); iterEstado.hasNext();)
        {
          PerspectivaEstado estado = (PerspectivaEstado)iterEstado.next();
          if (estado.getPk().getPerspectivaId().longValue() == resultado.getPk().getPerspectivaId().longValue())
          {
            hayEstado = Boolean.valueOf(true);
            if (estado.getPk().getAno().intValue() < resultado.getPk().getAno().intValue())
            {
              estado.getPk().setAno(resultado.getPk().getAno());
              estado.getPk().setPeriodo(resultado.getPk().getPeriodo());
              estado.getPk().setTipo(resultado.getPk().getTipo());
              estado.setEstado(resultado.getEstado());
              estado.setMeta(resultado.getMeta());
              estado.setAlerta(resultado.getAlerta());
            }
            else if ((estado.getPk().getAno().intValue() == resultado.getPk().getAno().intValue()) && (estado.getPk().getPeriodo().intValue() < resultado.getPk().getPeriodo().intValue()))
            {
              estado.getPk().setPeriodo(resultado.getPk().getPeriodo());
              estado.getPk().setTipo(resultado.getPk().getTipo());
              estado.setEstado(resultado.getEstado());
              estado.setMeta(resultado.getMeta());
              estado.setAlerta(resultado.getAlerta());
            }
          }
        }
        if (!hayEstado.booleanValue()) {
          estados.add(resultado);
        }
      }
    }
    return estados;
  }
  
  public int updateCampo(Long perspectivaId, Map<?, ?> filtros) throws Throwable
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
        hqlUpdate = "update Perspectiva i set " + hqlUpdate + " where i.perspectivaId = :perspectivaId";
        
        Query actualizacion = session.createQuery(hqlUpdate).setLong("perspectivaId", perspectivaId.longValue());
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
