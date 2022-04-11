package com.visiongc.app.strategos.planes.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorEstadoPK;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlanEstado;
import com.visiongc.app.strategos.planes.model.PlanEstadoPK;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.persistence.StrategosPlanesPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosPlanesHibernateSession
  extends StrategosHibernateSession implements StrategosPlanesPersistenceSession
{
  public StrategosPlanesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPlanesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  private Query prepararQueryGetIndicadores(String orden, String tipoOrden, Map filtros, boolean soloContar)
  {
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtros != null) {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext()) {
        fieldName = (String)iter.next();
        if ((filtros.get(fieldName) instanceof String)) {
          fieldValue = (String)filtros.get(fieldName);
        }
        
        if (fieldName.equals("nombre")) {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("descripcion")) {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("organizacionId")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("unidadId")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("responsableFijarMetaId")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("responsableLograrMetaId")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("responsableSeguimientoId")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("naturaleza")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("caracteristica")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("guia")) {
          if (((Boolean)filtros.get(fieldName)).booleanValue()) {
            condicionesConsulta = condicionesConsulta + "indicador.guia = 1 and ";
          } else {
            condicionesConsulta = condicionesConsulta + "indicador.guia = 0 and ";
          }
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("claseId")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("frecuencia")) {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("frecuenciasContenidas")) {
          List frecuencias = Frecuencia.getFrecuenciasContenidas(Byte.parseByte(fieldValue));
          condicionesConsulta = condicionesConsulta + "(";
          for (int i = 0; i < frecuencias.size(); i++) {
            Frecuencia frecuencia = (Frecuencia)frecuencias.get(i);
            condicionesConsulta = condicionesConsulta + "indicador.frecuencia = " + frecuencia.getFrecuenciaId().toString() + " or ";
          }
          condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 4);
          condicionesConsulta = condicionesConsulta + ") and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("noCualitativos")) {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        } else if (fieldName.equals("noCompuestos")) {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaFormula().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaIndice().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaPromedio() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSumatoria().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        } else if (fieldName.equals("soloCompuestos")) {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSimple().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        } else if (fieldName.equals("excluirIds")) {
          String[] ids = fieldValue.split(",");
          for (int i = 0; i < ids.length; i++) {
            Long id = new Long(ids[i]);
            condicionesConsulta = condicionesConsulta + "indicador.indicadorId != " + id.toString() + " and ";
            hayCondicionesConsulta = true;
          }
        } else if (fieldName.equals("perspectivaId")) {
          tablasConsulta = tablasConsulta + ", IndicadorPerspectiva indPers";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPers.pk.indicadorId and indPers.pk.perspectivaId = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("planId")) {
          tablasConsulta = tablasConsulta + ", IndicadorPlan indPlan";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPlan.pk.indicadorId and indPlan.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("indicadoresLogroPlanId")) {
          tablasConsulta = tablasConsulta + ", Plan plan";
          condicionesConsulta = condicionesConsulta + "(indicador.indicadorId = plan.nlAnoIndicadorId or indicador.indicadorId = plan.nlParIndicadorId) and plan.planId = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals(""))) {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by indicador." + orden + " asc";
      }
      else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by indicador." + orden + " asc";
      } else {
        ordenConsulta = " order by indicador." + orden + " desc";
      }
    }
    

    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    
    String objetoConsulta = "distinct(indicador)";
    if (soloContar) {
      objetoConsulta = "count(indicador)";
    }
    
    Query consulta = session.createQuery("select " + objetoConsulta + " from Indicador indicador" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    return consulta;
  }
  
  public PaginaLista getReporteSeguimiento(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Long planId = Long.valueOf(0L);
    int ano = 0;
    int mes = 0;
    int tipoReporte = 0;
    Long organizacionId = Long.valueOf(0L);
    Long perspectivaId = Long.valueOf(0L);
    String organizacionNombre = "";
    
    String proyectoId = "";
    String planNombre = "";
    Long metodologiaId = Long.valueOf(0L);
    String metodologiaNombre = "";
    String actividadNombre = "";
    int numeroPerspectivasRaiz = 0;
    String iniciativaNombre = "";
    
    float montoInterno = 0.0F;
    float montoExterno = 0.0F;
    
    List<String> listaPerspectivas = new ArrayList();
    List<Perspectiva> listaValoresPerspectivas = new ArrayList();
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext()) {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        if (fieldName.equals("planId")) {
          planId = new Long(fieldValue);
        } else if (fieldName.equals("organizacionId")) {
          organizacionId = new Long(fieldValue);
        } else if (fieldName.equals("perspectivaId")) {
          perspectivaId = new Long(fieldValue);
        } else if (fieldName.equals("tipoReporte")) {
          tipoReporte = new Integer(fieldValue).intValue();
        } else if (fieldName.equals("ano")) {
          ano = new Integer(fieldValue).intValue();
        } else if (fieldName.equals("mes")) {
          mes = new Integer(fieldValue).intValue();
        }
      }
    }
    Query consulta = session.createQuery("select plan.planId, plan.nombre, plan.metodologiaId from Plan plan where plan.planId = :planId");
    consulta.setLong("planId", planId.longValue());
    Object[] valores = (Object[])consulta.list().get(0);
    planNombre = (String)valores[1];
    metodologiaId = (Long)valores[2];
    
    if (metodologiaId == null)
    {
      metodologiaNombre = "BSC";
      iniciativaNombre = "Iniciativa";
      actividadNombre = "Actividad";
      
      listaPerspectivas.add("Perspectiva Primer Nivel...");
      listaPerspectivas.add("Perspectiva...");
      listaPerspectivas.add("Objetivo...");
    }
    else
    {
      String sql = "select ";
      sql = sql + " metodologia.nombre,";
      sql = sql + " metodologia.nombreIniciativaSingular,";
      sql = sql + " metodologia.nombreActividadSingular,";
      

      sql = sql + " metodologia_template.nombre AS PerspectivaNombre ";
      sql = sql + " from PlantillaPlanes metodologia , ";
      sql = sql + " ElementoPlantillaPlanes metodologia_template ";
      sql = sql + " where ";
      sql = sql + " metodologia_template.plantillaPlanesId = metodologia.plantillaPlanesId and ";
      sql = sql + " metodologia.plantillaPlanesId = :metodologiaId ";
      sql = sql + " order by ";
      sql = sql + " metodologia_template.orden ASC";
      
      consulta = session.createQuery(sql);
      consulta.setLong("metodologiaId", metodologiaId.longValue());
      List metodologias = consulta.list();
      
      int indexmet = 0;
      while (indexmet < metodologias.size()) {
        valores = (Object[])metodologias.get(indexmet);
        metodologiaNombre = (String)valores[0];
        iniciativaNombre = (String)valores[1];
        actividadNombre = (String)valores[2];
        listaPerspectivas.add((String)valores[3]);
        
        indexmet++;
      }
      
      listaValoresPerspectivas.addAll(getPerspectivas("", new ArrayList(), planId));
    }
    
    if (listaValoresPerspectivas.size() > 0)
    {
      ((Perspectiva)listaValoresPerspectivas.get(0)).setListaPerspectivasMetod(listaPerspectivas);
    }
    
    int total = 0;
    
    if (getTotal) {
      total = listaValoresPerspectivas.size();
    }
    
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    
    if (!getTotal) {
      total = listaValoresPerspectivas.size();
    }
    
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(listaValoresPerspectivas);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  private List<Perspectiva> getPerspectivas(String padreId, List<String> listaPadres, Long planId)
  {
    String sql = "";
    
    int numeroPerspectivasRaiz = 0;
    Long perspectivaId = Long.valueOf(0L);
    String perspectivaNombre = "";
    String perspectivaPadreId = "";
    List<Perspectiva> perspectivas = new ArrayList();
    

    String clausulaPadreId = "";
    if (padreId.equals("")) {
      clausulaPadreId = "padreId is null";
    } else {
      clausulaPadreId = "padreId = " + padreId;
    }
    
    sql = "select perspectiva.perspectivaId, perspectiva.nombre, perspectiva.padreId from Perspectiva perspectiva where planId = :planId and " + clausulaPadreId + " order by nombre asc";
    Query consulta = session.createQuery(sql);
    consulta.setLong("planId", planId.longValue());
    List perpecs = consulta.list();
    
    int index = 0;
    while (index < perpecs.size()) {
      List iniciativas = new ArrayList();
      Perspectiva perspectiva = new Perspectiva();
      Object[] valores = (Object[])perpecs.get(index);
      
      if (listaPadres != null)
      {
        List listaPadres1 = new ArrayList();
        for (Iterator<String> r = listaPadres.iterator(); r.hasNext();) {
          listaPadres1.add(r.next());
        }
        
        perspectiva.setListaPadres(listaPadres1);
      }
      

      perspectiva.setPerspectivaId((Long)valores[0]);
      perspectiva.setNombre((String)valores[1]);
      perspectiva.setPadreId((Long)valores[2]);
      
      if (perspectiva.getPadreId() == null)
      {
        perspectiva.setPadreId(Long.valueOf(0L));
        numeroPerspectivasRaiz++;
      }
      

      sql = "select iniciativa.iniciativaId, iniciativa.nombre, iniciativa.proyectoId, iniciativa.porcentajeCompletado, iniciativa.responsableLograrMetaId ";
      sql = sql + " from IniciativaPerspectiva iniciativa_por_perspectiva, Iniciativa iniciativa ";
      sql = sql + " where iniciativa_por_perspectiva.pk.iniciativaId = iniciativa.iniciativaId ";
      sql = sql + " and iniciativa_por_perspectiva.pk.perspectivaId = :perspectivaId ";
      sql = sql + " order by iniciativa.nombre";
      consulta = session.createQuery(sql);
      consulta.setLong("perspectivaId", perspectiva.getPerspectivaId().longValue());
      List iniciats = consulta.list();
      int index2 = 0;
      while (index2 < iniciats.size()) {
        List actividades = new ArrayList();
        Iniciativa iniciativa = new Iniciativa();
        valores = (Object[])iniciats.get(index2);
        
        iniciativa.setProyectoId((Long)valores[2]);
        
        if (iniciativa.getProyectoId() == null) {
          iniciativa.setProyectoId(Long.valueOf(0L));
        }
        
        iniciativa.setIniciativaId((Long)valores[0]);
        iniciativa.setNombre((String)valores[1]);
        

        iniciativa.setResponsableLograrMetaId((Long)valores[4]);
        if (iniciativa.getResponsableLograrMetaId() != null)
        {
          Criteria busqueda = session.createCriteria(Responsable.class);
          busqueda.add(Restrictions.eq("responsableId", iniciativa.getResponsableLograrMetaId()));
          Responsable resp = (Responsable)busqueda.list().get(0);
          iniciativa.setResponsableLograrMeta(resp);
        }
        


        sql = "SELECT pry_actividad.padreId, pry_actividad.comienzoPlan, pry_actividad.comienzoReal, ";
        sql = sql + " pry_actividad.finPlan, pry_actividad.finReal, pry_actividad.proyectoId, pry_actividad.nombre, ";
        sql = sql + " pry_actividad.actividadId, pry_actividad.claseId, pry_actividad.nivel, pry_actividad.descripcion, ";
        sql = sql + " pry_actividad.indicadorId, pry_actividad.fila,  ";
        sql = sql + " pry_actividad.responsableFijarMetaId, pry_actividad.responsableLograrMetaId, pry_actividad.responsableSeguimientoId, ";
        sql = sql + " pry_actividad.responsableCargarMetaId, pry_actividad.responsableCargarEjecutadoId, pry_actividad.tipoMedicion, ";
        sql = sql + " pry_actividad.naturaleza, pry_actividad.unidadId ";
        sql = sql + " FROM Iniciativa iniciativa, PryActividad pry_actividad WHERE iniciativa.iniciativaId = :iniciativaId AND ";
        sql = sql + " pry_actividad.proyectoId = iniciativa.proyectoId AND pry_actividad.padreId IS NULL ORDER BY pry_actividad.fila";
        consulta = session.createQuery(sql);
        consulta.setLong("iniciativaId", iniciativa.getIniciativaId().longValue());
        List activids = consulta.list();
        int index3 = 0;
        while (index3 < activids.size()) {
          PryActividad actividad = new PryActividad();
          valores = (Object[])activids.get(index3);
          
          actividad.setPadreId((Long)valores[0]);
          actividad.setComienzoPlan((Date)valores[1]);
          actividad.setComienzoReal((Date)valores[2]);
          actividad.setFinPlan((Date)valores[3]);
          actividad.setFinReal((Date)valores[4]);
          actividad.setProyectoId((Long)valores[5]);
          
          if (actividad.getProyectoId() != null)
          {
            double duracion = 0.0D;
            

            actividad.setDuracionPlan(Double.valueOf(duracion));
          }
          
          actividad.setNombre((String)valores[6]);
          actividad.setActividadId((Long)valores[7]);
          actividad.setClaseId((Long)valores[8]);
          actividad.setNivel((Integer)valores[9]);
          actividad.setDescripcion((String)valores[10]);
          actividad.setIndicadorId((Long)valores[11]);
          actividad.setFila((Integer)valores[12]);
          actividad.setResponsableFijarMetaId((Long)valores[13]);
          actividad.setResponsableLograrMetaId((Long)valores[14]);
          actividad.setResponsableSeguimientoId((Long)valores[15]);
          actividad.setResponsableCargarMetaId((Long)valores[16]);
          actividad.setResponsableCargarEjecutadoId((Long)valores[17]);
          actividad.setTipoMedicion((Byte)valores[18]);
          actividad.setNaturaleza((Byte)valores[19]);
          actividad.setUnidadId((Long)valores[20]);
          if (actividad.getUnidadId() != null)
          {
            Criteria busqueda = session.createCriteria(UnidadMedida.class);
            busqueda.add(Restrictions.eq("unidadId", actividad.getUnidadId()));
            UnidadMedida unidad = (UnidadMedida)busqueda.list().get(0);
            actividad.setUnidadMedida(unidad);
          }
          

          consulta = session.createQuery("select inc_actividad.peso from PryInformacionActividad inc_actividad where inc_actividad.actividadId = :actividad_id");
          consulta.setLong("actividad_id", actividad.getActividadId().longValue());
          List incActividad = consulta.list();
          if (incActividad.get(0) != null) {
            actividad.setPeso((Double)incActividad.get(0));
          }
          
          if (actividad.getResponsableSeguimientoId() != null)
          {
            Criteria busqueda = session.createCriteria(Responsable.class);
            busqueda.add(Restrictions.eq("responsableId", actividad.getResponsableSeguimientoId()));
            Responsable resp = (Responsable)busqueda.list().get(0);
            actividad.setResponsableSeguimiento(resp);
          }
          
          if (actividad.getResponsableLograrMetaId() != null)
          {
            Criteria busqueda = session.createCriteria(Responsable.class);
            busqueda.add(Restrictions.eq("responsableId", actividad.getResponsableLograrMetaId()));
            Responsable resp = (Responsable)busqueda.list().get(0);
            actividad.setResponsableLograrMeta(resp);
          }
          if (actividad.getResponsableFijarMetaId() != null)
          {
            Criteria busqueda = session.createCriteria(Responsable.class);
            busqueda.add(Restrictions.eq("responsableId", actividad.getResponsableFijarMetaId()));
            Responsable resp = (Responsable)busqueda.list().get(0);
            actividad.setResponsableFijarMeta(resp);
          }
          if (actividad.getResponsableCargarEjecutadoId() != null)
          {
            Criteria busqueda = session.createCriteria(Responsable.class);
            busqueda.add(Restrictions.eq("responsableId", actividad.getResponsableCargarEjecutadoId()));
            Responsable resp = (Responsable)busqueda.list().get(0);
            actividad.setResponsableCargarEjecutado(resp);
          }
          if (actividad.getResponsableCargarMetaId() != null)
          {
            Criteria busqueda = session.createCriteria(Responsable.class);
            busqueda.add(Restrictions.eq("responsableId", actividad.getResponsableCargarMetaId()));
            Responsable resp = (Responsable)busqueda.list().get(0);
            actividad.setResponsableCargarMeta(resp);
          }
          






          actividades.add(actividad);
          index3++;
        }
        
        iniciativa.setListaActividades(actividades);
        iniciativas.add(iniciativa);
        


        index2++;
      }
      
      perspectiva.setListaIniciativas(iniciativas);
      perspectivas.add(perspectiva);
      
      List<String> nombrePadres = new ArrayList();
      nombrePadres = perspectiva.getListaPadres();
      nombrePadres.add(perspectiva.getNombre());
      perspectiva.setListaPadres(nombrePadres);
      
      perspectivas.addAll(getPerspectivas(perspectiva.getPerspectivaId().toString(), perspectiva.getListaPadres(), planId));
      
      index++;
    }
    
    return perspectivas;
  }
  
  public PaginaLista getPlanes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(Plan.class);
    
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
        } else if (fieldName.equals("organizacionId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        } else if (fieldName.equals("planId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        } else if (fieldName.equals("activo")) {
          consulta.add(Restrictions.eq(fieldName, new Boolean(fieldValue)));
        } else if (fieldName.equals("tipo"))
        {
          if (fieldValue.getClass().getSimpleName().indexOf("Byte") > -1) {
            consulta.add(Restrictions.eq(fieldName, fieldValue));
          } else {
            consulta.add(Restrictions.eq(fieldName, new Byte(fieldValue)));
          }
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public int activarPlan(Long planId, boolean activar)
  {
    int resultado = 10000;
    
    String nuevoValor = null;
    if (activar) {
      nuevoValor = "1";
    } else {
      nuevoValor = "0";
    }
    String hqlUpdate = "update Plan plan set plan.activo = " + nuevoValor + " where plan.planId = :planId";
    int actualizaciones = session.createQuery(hqlUpdate).setLong("planId", planId.intValue()).executeUpdate();
    
    if (actualizaciones == 0) {
      resultado = 10001;
    }
    return resultado;
  }
  
  public ListaMap getDependenciasPlan(Plan plan)
  {
    ListaMap dependencias = new ListaMap();
    
    Long planId = plan.getPlanId();
    Criteria consulta = null;
    
    consulta = session.createCriteria(Modelo.class);
    consulta.add(Restrictions.eq("pk.planId", planId));
    List<Modelo> modelos = consulta.list();
    dependencias.add(modelos, "modelos");
    
    consulta = session.createCriteria(Perspectiva.class);
    consulta.add(Restrictions.eq("planId", planId));
    List<Perspectiva> perspectivas = consulta.list();
    dependencias.add(perspectivas, "perspectivas");
    
    return dependencias;
  }
  
  public int deleteReferenciasForaneasPlan(Long planId)
  {
    int resultado = 10000;
    try
    {
      String hqlUpdate = "update Plan plan set plan.nlAnoIndicadorId = null where plan.planId = :planId";
      session.createQuery(hqlUpdate).setLong("planId", planId.longValue()).executeUpdate();
      
      hqlUpdate = "update Plan plan set plan.nlParIndicadorId = null where plan.planId = :planId";
      session.createQuery(hqlUpdate).setLong("planId", planId.longValue()).executeUpdate();
      
      hqlUpdate = "delete IndicadorPlan indPlan where indPlan.pk.planId = :planId";
      session.createQuery(hqlUpdate).setLong("planId", planId.longValue()).executeUpdate();
      
      hqlUpdate = "delete PlanEstado planEstado where planEstado.pk.planId = :planId";
      session.createQuery(hqlUpdate).setLong("planId", planId.longValue()).executeUpdate();
      
      hqlUpdate = "delete Meta meta where meta.metaId.planId = :planId";
      session.createQuery(hqlUpdate).setLong("planId", planId.longValue()).executeUpdate();
    }
    catch (Throwable t)
    {
      resultado = 10004;
    }
    
    return resultado;
  }
  
  public ListaMap getPostDependenciasPlan(Plan plan)
  {
    ListaMap postDependencias = new ListaMap();
    List<Indicador> indicadoresLogro = new ArrayList();
    boolean hayIndicador = false;
    
    Indicador indicadorLogro = null;
    if (plan.getNlAnoIndicadorId() != null)
    {
      indicadorLogro = (Indicador)load(Indicador.class, plan.getNlAnoIndicadorId());
      if (indicadorLogro != null)
      {
        indicadoresLogro.add(indicadorLogro);
        hayIndicador = true;
      }
    }
    
    if (plan.getNlParIndicadorId() != null)
    {
      indicadorLogro = (Indicador)load(Indicador.class, plan.getNlParIndicadorId());
      if (indicadorLogro != null)
      {
        indicadoresLogro.add(indicadorLogro);
        hayIndicador = true;
      }
    }
    
    if (hayIndicador) {
      postDependencias.add(indicadoresLogro, "indicadoresLogro");
    }
    return postDependencias;
  }
  
  public List getPlanesAsociadosPorIndicador(Long indicadorId, boolean activos, boolean inactivos)
  {
    Query consulta = session.createQuery("select distinct(plan) from Plan plan, Perspectiva perspectiva, IndicadorPerspectiva indPers where plan.planId = perspectiva.planId and perspectiva.perspectivaId = indPers.pk.perspectivaId and indPers.pk.indicadorId = :indicadorId").setLong("indicadorId", indicadorId.longValue());
    
    List planes = consulta.list();
    
    boolean removerActivos = (inactivos) && (!activos);
    boolean removerInactivos = (!inactivos) && (activos);
    
    if ((removerActivos) || (removerInactivos)) {
      int index = 0;
      while (index < planes.size()) {
        Plan plan = (Plan)planes.get(index);
        if ((plan.getActivo().booleanValue()) && (removerActivos)) {
          planes.remove(index);
        } else if ((!plan.getActivo().booleanValue()) && (removerInactivos)) {
          planes.remove(index);
        } else {
          index++;
        }
      }
    }
    
    return planes;
  }
  
  public int deleteIndicadorEstados(Long indicadorId, Long planId, Byte tipo, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    String hqlUpdate = "delete IndicadorEstado indEstado where indEstado.pk.indicadorId = :indicadorId and indEstado.pk.planId = :planId and indEstado.pk.tipo = :tipo";
    boolean continuar = false;
    
    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) {
      if (anoInicio.intValue() != anoFinal.intValue()) {
        hqlUpdate = hqlUpdate + " and (((indEstado.pk.ano = :anoInicio) and (indEstado.pk.periodo >= :periodoInicio))";
        hqlUpdate = hqlUpdate + " or ((indEstado.pk.ano > :anoInicio) and (indEstado.pk.ano < :anoFinal))";
        hqlUpdate = hqlUpdate + " or ((indEstado.pk.ano = :anoFinal) and (indEstado.pk.periodo <= :periodoFinal)))";
      } else {
        hqlUpdate = hqlUpdate + " and ((indEstado.pk.ano = :anoInicio) and (indEstado.pk.periodo >= :periodoInicio)";
        hqlUpdate = hqlUpdate + " and (indEstado.pk.ano = :anoFinal) and (indEstado.pk.periodo <= :periodoFinal))";
      }
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and indEstado.pk.ano >= :anoInicio";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and indEstado.pk.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and indEstado.pk.ano >= :anoInicio";
      hqlUpdate = hqlUpdate + " and indEstado.pk.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and (((indEstado.pk.ano = :anoInicio) and (indEstado.pk.periodo >= :periodoInicio))";
      hqlUpdate = hqlUpdate + " or (indEstado.pk.ano > :anoInicio))";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) {
      hqlUpdate = hqlUpdate + " and (((indEstado.pk.ano = :anoFinal) and (indEstado.pk.periodo <= :periodoFinal))";
      hqlUpdate = hqlUpdate + " or (indEstado.pk.ano < :anoFinal))";
      continuar = true;
    }
    
    if (continuar) {
      Query update = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue()).setLong("planId", planId.longValue()).setByte("tipo", tipo.byteValue());
      
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
  
  public List getIndicadorEstados(Long indicadorId, Long planId, Byte frecuencia, Byte tipoEstado, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta) {
    String sql = "from IndicadorEstado indEstado";
    
    if ((indicadorId != null) || (planId != null) || (tipoEstado != null) || (anoDesde != null) || (anoHasta != null) || (periodoDesde != null) || (periodoHasta != null)) {
      sql = sql + " where ";
    }
    
    sql = sql + "indEstado.pk.indicadorId = :indicadorId and indEstado.pk.planId = :planId and indEstado.pk.tipo = :tipo";
    
    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (anoDesde.intValue() == anoHasta.intValue()))
    {
      sql = sql + " and (indEstado.pk.periodo >= :periodoInicio and indEstado.pk.ano = :anoInicio and indEstado.pk.periodo <= :periodoFinal and indEstado.pk.ano = :anoFinal)";
    } else if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null))
    {
      sql = sql + " and ((indEstado.pk.periodo >= :periodoInicio and indEstado.pk.ano = :anoInicio) or " + "(indEstado.pk.periodo <= :periodoFinal and indEstado.pk.ano = :anoFinal) " + " or (indEstado.pk.ano > :anoInicio and indEstado.pk.ano < :anoFinal))";
    } else if ((anoDesde != null) && (periodoDesde != null)) {
      sql = sql + " and (" + "(indEstado.pk.periodo >= :periodoInicio and indEstado.pk.ano = :anoInicio) or " + "(indEstado.pk.ano > :anoInicio)" + ")";
    } else if ((anoHasta != null) && (periodoHasta != null)) {
      sql = sql + " and (" + "(indEstado.pk.periodo <= :periodoFinal and indEstado.pk.ano = :anoFinal) or " + "(indEstado.pk.ano < :anoFinal)" + ")";
    } else if ((anoDesde != null) && (anoHasta != null)) {
      sql = sql + " and (indEstado.pk.ano >= :anoInicio and indEstado.pk.ano <= :anoFinal)";
    } else if (anoDesde != null) {
      sql = sql + " and indEstado.pk.ano >= :anoInicio";
    } else if (anoHasta != null) {
      sql = sql + " and indEstado.pk.ano <= :anoFinal";
    }
    
    sql = sql + " order by indEstado.pk.indicadorId, indEstado.pk.planId, indEstado.pk.tipo, indEstado.pk.ano, indEstado.pk.periodo";
    
    Query query = session.createQuery(sql);
    
    if (indicadorId != null) {
      query.setLong("indicadorId", indicadorId.longValue());
    }
    
    if (planId != null) {
      query.setLong("planId", planId.longValue());
    }
    
    if (tipoEstado != null) {
      query.setByte("tipo", tipoEstado.byteValue());
    }
    
    if (anoDesde != null) {
      query.setInteger("anoInicio", anoDesde.intValue());
    }
    
    if (anoHasta != null) {
      query.setInteger("anoFinal", anoHasta.intValue());
    }
    
    if (periodoDesde != null) {
      query.setInteger("periodoInicio", periodoDesde.intValue());
    }
    
    if (periodoHasta != null) {
      query.setInteger("periodoFinal", periodoHasta.intValue());
    }
    
    List estadosExistentes = query.list();
    List estados = new ArrayList();
    
    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (frecuencia != null))
    {
      int ano = anoDesde.intValue();
      Iterator iterEstadosExistentes = estadosExistentes.iterator();
      boolean getExistente = iterEstadosExistentes.hasNext();
      IndicadorEstado indicadorEstado = null;
      IndicadorEstado indicadorEstadoExistente = null;
      
      while (ano <= anoHasta.intValue())
      {
        int periodo = 1;
        if (ano == anoDesde.intValue()) {
          periodo = periodoDesde.intValue();
        }
        int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        if (ano == anoHasta.intValue()) {
          periodoMaximo = periodoHasta.intValue();
        }
        while (periodo <= periodoMaximo) {
          if (getExistente) {
            indicadorEstadoExistente = (IndicadorEstado)iterEstadosExistentes.next();
          }
          if (indicadorEstadoExistente != null) {
            if ((indicadorEstadoExistente.getPk().getAno().intValue() == ano) && (indicadorEstadoExistente.getPk().getPeriodo().intValue() == periodo)) {
              indicadorEstado = indicadorEstadoExistente;
              getExistente = iterEstadosExistentes.hasNext();
              indicadorEstadoExistente = null;
            } else {
              indicadorEstado = null;
              getExistente = false;
            }
          } else {
            indicadorEstado = null;
            getExistente = false;
          }
          if (indicadorEstado == null) {
            indicadorEstado = new IndicadorEstado();
            IndicadorEstadoPK indicadorEstadoPk = new IndicadorEstadoPK();
            indicadorEstadoPk.setIndicadorId(indicadorId);
            indicadorEstadoPk.setPlanId(planId);
            indicadorEstadoPk.setTipo(tipoEstado);
            indicadorEstadoPk.setAno(new Integer(ano));
            indicadorEstadoPk.setPeriodo(new Integer(periodo));
            indicadorEstado.setPk(indicadorEstadoPk);
          }
          
          estados.add(indicadorEstado);
          periodo++;
        }
        ano++;
      }
    } else {
      estados = estadosExistentes;
    }
    
    return estados;
  }
  
  public int deletePlanEstados(Long planId, Byte tipoEstado, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    String hqlUpdate = "delete PlanEstado planEstado where planEstado.pk.planId = :planId and planEstado.pk.tipo = :tipo";
    boolean continuar = false;
    
    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) {
      if (anoInicio.intValue() != anoFinal.intValue()) {
        hqlUpdate = hqlUpdate + " and (((planEstado.pk.ano = :anoInicio) and (planEstado.pk.periodo >= :periodoInicio))";
        hqlUpdate = hqlUpdate + " or ((planEstado.pk.ano > :anoInicio) and (planEstado.pk.ano < :anoFinal))";
        hqlUpdate = hqlUpdate + " or ((planEstado.pk.ano = :anoFinal) and (planEstado.pk.periodo <= :periodoFinal)))";
      } else {
        hqlUpdate = hqlUpdate + " and ((planEstado.pk.ano = :anoInicio) and (planEstado.pk.periodo >= :periodoInicio)";
        hqlUpdate = hqlUpdate + " and (planEstado.pk.ano = :anoFinal) and (planEstado.pk.periodo <= :periodoFinal))";
      }
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and planEstado.pk.ano >= :anoInicio";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and planEstado.pk.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and planEstado.pk.ano >= :anoInicio";
      hqlUpdate = hqlUpdate + " and planEstado.pk.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and (((planEstado.pk.ano = :anoInicio) and (planEstado.pk.periodo >= :periodoInicio))";
      hqlUpdate = hqlUpdate + " or (planEstado.pk.ano > :anoInicio))";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) {
      hqlUpdate = hqlUpdate + " and (((planEstado.pk.ano = :anoFinal) and (planEstado.pk.periodo <= :periodoFinal))";
      hqlUpdate = hqlUpdate + " or (planEstado.pk.ano < :anoFinal))";
      continuar = true;
    }
    
    if (continuar) {
      Query update = session.createQuery(hqlUpdate).setLong("planId", planId.longValue()).setByte("tipo", tipoEstado.byteValue());
      
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
  
  public List getIndicadoresPlan(Long planId) {
    Query consulta = session.createQuery("select indicadorPlan from IndicadorPlan indicadorPlan where indicadorPlan.pk.planId = :planId");
    consulta.setLong("planId", planId.longValue());
    
    return consulta.list();
  }
  
  public Byte getFrecuenciaPlan(Long planId) {
    Byte frecuencia = null;
    
    String sql = "select max(indicador.frecuencia) from Indicador indicador where indicador.indicadorId in (";
    sql = sql + "select indPlan.pk.indicadorId from IndicadorPlan indPlan where indPlan.pk.planId = :planId)";
    Query consulta = session.createQuery(sql).setLong("planId", planId.longValue());
    
    List resultados = consulta.list();
    
    if (resultados.size() > 0) {
      frecuencia = (Byte)resultados.get(0);
    }
    return frecuencia;
  }
  
  public int actualizarPlanUltimoEstado(Long planId)
  {
    int resultado = 10000;
    
    String sql = "select planEstado from PlanEstado planEstado where planEstado.pk.planId = :planId and planEstado.pk.tipo = :tipo order by planEstado.pk.ano desc, planEstado.pk.periodo desc";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("planId", planId.longValue()).setByte("tipo", TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue());
    List estados = consulta.list();
    
    boolean actualizado = false;
    
    int index = 0;
    while ((!actualizado) && (index < estados.size())) {
      PlanEstado estado = (PlanEstado)estados.get(index);
      
      if (estado.getEstado() != null) {
        String fechaUltimaMedicion = estado.getPk().getPeriodo().toString() + "/" + estado.getPk().getAno();
        
        Query update = session.createQuery("update Plan plan set plan.fechaUltimaMedicion = :fechaUltimaMedicion, plan.ultimaMedicionAnual = :ultimaMedicion where plan.planId = :planId");
        update.setString("fechaUltimaMedicion", fechaUltimaMedicion).setDouble("ultimaMedicion", estado.getEstado().doubleValue()).setLong("planId", planId.longValue());
        update.executeUpdate();
        actualizado = true;
      } else {
        index++;
      }
    }
    

    consulta = session.createQuery(sql);
    consulta.setLong("planId", planId.longValue()).setByte("tipo", TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue());
    estados = consulta.list();
    
    actualizado = false;
    
    index = 0;
    while ((!actualizado) && (index < estados.size())) {
      PlanEstado estado = (PlanEstado)estados.get(index);
      
      if (estado.getEstado() != null) {
        Query update = session.createQuery("update Plan plan set plan.ultimaMedicionParcial = :ultimaMedicion where plan.planId = :planId");
        update.setDouble("ultimaMedicion", estado.getEstado().doubleValue()).setLong("planId", planId.longValue());
        update.executeUpdate();
        actualizado = true;
      } else {
        index++;
      }
    }
    
    return resultado;
  }
  
  public int actualizarPlanAlerta(Plan plan)
  {
    int resultado = 10000;
    
    Byte alerta = null;
    IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
    indicadorPlanPk.setIndicadorId(plan.getNlParIndicadorId());
    indicadorPlanPk.setPlanId(plan.getPlanId());
    IndicadorPlan indicadorPlan = (IndicadorPlan)load(IndicadorPlan.class, indicadorPlanPk);
    
    if (indicadorPlan != null) {
      alerta = indicadorPlan.getCrecimiento();
    }
    String sql = null;
    
    if (alerta != null) {
      sql = "update Plan plan set plan.alerta = :alerta where plan.planId = :planId";
    } else {
      sql = "update Plan plan set plan.alerta = null where plan.planId = :planId";
    }
    Query update = session.createQuery(sql);
    update.setLong("planId", plan.getPlanId().longValue());
    if (alerta != null) {
      update.setByte("alerta", alerta.byteValue());
    }
    update.executeUpdate();
    
    return resultado;
  }
  
  public boolean tienePermisoPlan(Long usuarioId, Long planId, String permisoId)
  {
    boolean tienePermiso = false;
    
    String sql = "select count(indicador.frecuencia) from Indicador indicador where indicador.indicadorId in (";
    sql = sql + "select indPlan.pk.indicadorId from IndicadorPlan indPlan where indPlan.pk.planId = :planId)";
    Query consulta = session.createQuery(sql).setLong("planId", planId.longValue());
    
    List resultados = consulta.list();
    
    if (resultados.size() > 0) {
      tienePermiso = ((Long)resultados.get(0)).longValue() > 0L;
    }
    return tienePermiso;
  }
  
  public boolean getIndicadorEstaAsociadoPlan(Long indicadorId, Long planId)
  {
    boolean estaAsociado = false;
    
    String sql = "select count(*) as rec_count from Perspectiva perspectiva, IndicadorPerspectiva indicadorPerspectiva ";
    sql = sql + "where indicadorPerspectiva.pk.perspectivaId = perspectiva.perspectivaId and ";
    sql = sql + "indicadorPerspectiva.pk.indicadorId = :indicadorId and perspectiva.planId = :planId";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("indicadorId", indicadorId.longValue());
    consulta.setLong("planId", planId.longValue());
    
    List resultados = consulta.list();
    
    if (resultados.size() > 0) {
      estaAsociado = ((Long)resultados.get(0)).longValue() > 0L;
    }
    return estaAsociado;
  }
  
  public IndicadorPlan getFirstAlertaIndicadorPorPlan(Long indicadorId)
  {
    IndicadorPlan indicadorPlan = null;
    
    String sql = "select indicadorplan ";
    sql = sql + "from IndicadorPlan indicadorplan, Plan plan ";
    sql = sql + "where indicadorplan.pk.indicadorId = :indicadorId ";
    sql = sql + "and plan.planId = indicadorplan.pk.planId ";
    sql = sql + "and plan.activo = 1 ";
    sql = sql + "order by plan.anoInicial DESC";
    
    Query consulta = session.createQuery(sql);
    consulta.setLong("indicadorId", indicadorId.longValue());
    
    List<IndicadorPlan> resultados = consulta.list();
    
    if (resultados.size() > 0) {
      indicadorPlan = (IndicadorPlan)resultados.get(0);
    }
    return indicadorPlan;
  }
}
