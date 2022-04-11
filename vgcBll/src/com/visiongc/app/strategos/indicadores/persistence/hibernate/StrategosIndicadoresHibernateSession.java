package com.visiongc.app.strategos.indicadores.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoAsociadoIndicador;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadoresPersistenceSession;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Plantilla;
import java.util.ArrayList;
import java.util.Date;
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

public class StrategosIndicadoresHibernateSession
  extends StrategosHibernateSession implements StrategosIndicadoresPersistenceSession
{
  public StrategosIndicadoresHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosIndicadoresHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  private Query prepararQueryGetIndicadores(String orden, String tipoOrden, Map filtros, boolean soloContar)
  {
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
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("descripcion"))
        {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("indicadorId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("organizacionId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("unidadId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableFijarMetaId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableLograrMetaId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableSeguimientoId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("naturaleza"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("caracteristica"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("guia"))
        {
          if (((Boolean)filtros.get(fieldName)).booleanValue()) {
            condicionesConsulta = condicionesConsulta + "indicador.guia = 1 and ";
          } else
            condicionesConsulta = condicionesConsulta + "indicador.guia = 0 and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("claseId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuencia"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuenciasContenidas"))
        {
          List frecuencias = Frecuencia.getFrecuenciasContenidas(Byte.parseByte(fieldValue));
          condicionesConsulta = condicionesConsulta + "(";
          for (int i = 0; i < frecuencias.size(); i++)
          {
            Frecuencia frecuencia = (Frecuencia)frecuencias.get(i);
            condicionesConsulta = condicionesConsulta + "indicador.frecuencia = " + frecuencia.getFrecuenciaId().toString() + " or ";
          }
          condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 4);
          condicionesConsulta = condicionesConsulta + ") and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("noCualitativos"))
        {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true")))
          {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("noCompuestos"))
        {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true")))
          {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaFormula().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaIndice().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaPromedio() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSumatoria().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("soloCompuestos"))
        {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true")))
          {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSimple().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("excluirIds"))
        {
          String[] ids = fieldValue.split(",");
          for (int i = 0; i < ids.length; i++)
          {
            Long id = new Long(ids[i]);
            condicionesConsulta = condicionesConsulta + "indicador.indicadorId != " + id.toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("perspectivaId"))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPerspectiva indPers";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPers.pk.indicadorId and indPers.pk.perspectivaId = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("planId")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPlan indPlan";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPlan.pk.indicadorId and indPlan.pk." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("planId")) && (!fieldValue.equals("")))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPlan indPlan";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPlan.pk.indicadorId and indPlan.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("indicadoresLogroPerspectivasPrincipalesPlanId")) && (!fieldValue.equals("")))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPlan indPlan";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPlan.pk.indicadorId and indPlan.pk.planId = " + fieldValue + " and ";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId NOT IN (select ind.indicadorId from Indicador ind, IndicadorPerspectiva indPers, Perspectiva pers ";
          condicionesConsulta = condicionesConsulta + "where ind.indicadorId = indPers.pk.indicadorId and indPers.pk.perspectivaId = pers.perspectivaId and pers.planId = " + fieldValue + ") and ";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId NOT IN (select ind.indicadorId from Indicador ind, Plan plan ";
          condicionesConsulta = condicionesConsulta + "where (ind.indicadorId = plan.nlAnoIndicadorId or ind.indicadorId = plan.nlParIndicadorId) and plan.planId = " + fieldValue + ") and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("indicadoresLogroPlanId"))
        {
          tablasConsulta = tablasConsulta + ", Plan plan";
          condicionesConsulta = condicionesConsulta + "(indicador.indicadorId = plan.nlAnoIndicadorId or indicador.indicadorId = plan.nlParIndicadorId) and plan.planId = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("codigoEnlace")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("codigoEnlace")) && (!fieldValue.equals("")))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("enlaceParcial")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("enlaceParcial")) && (!fieldValue.equals("")))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("tipoFuncionNotIn")) && (!fieldValue.equals("")))
        {
          condicionesConsulta = condicionesConsulta + "indicador.tipoFuncion <> " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("tipoFuncionIn")) && (!fieldValue.equals("")))
        {
          condicionesConsulta = condicionesConsulta + "indicador.tipoFuncion = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by indicador." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
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
      objetoConsulta = "count(*)";
    }
    Query consulta = session.createQuery("select " + objetoConsulta + " from Indicador indicador" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    return consulta;
  }
  
  
  private Query prepararQueryGetIndicadoresResponsables(String orden, String tipoOrden, Map filtros, boolean soloContar)
  {
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
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        
        else if (fieldName.equals("organizacionId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        
        else if (fieldName.equals("responsableCargarEjecutadoId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " or ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableLograrMetaId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " or ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("responsableSeguimientoId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " or ";
          hayCondicionesConsulta = true;
        }
        
        
        
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by indicador." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by indicador." + orden + " asc";
      } else {
        ordenConsulta = " order by indicador." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 4);
    } else {
      condicionesConsulta = "";
    }
    String objetoConsulta = "distinct(indicador)";
    if (soloContar) {
      objetoConsulta = "count(*)";
    }
    Query consulta = session.createQuery("select " + objetoConsulta + " from Indicador indicador" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    return consulta;
  }
  
  
  public PaginaLista getIndicadoresResponsables(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Query consulta = prepararQueryGetIndicadoresResponsables(orden, tipoOrden, filtros, false);
    
    int total = 0;
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List indicadores = consulta.list();
    if (!getTotal) {
      total = indicadores.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(indicadores);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  
  public PaginaLista getIndicadores(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Query consulta = prepararQueryGetIndicadores(orden, tipoOrden, filtros, false);
    
    int total = 0;
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List indicadores = consulta.list();
    if (!getTotal) {
      total = indicadores.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(indicadores);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  public List<Indicador> getIndicadores(Map<?, ?> filtros)
  {
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    String ordenConsulta = " order by ";
    boolean ordenNombre = false;
    boolean hayOrden = false;
    
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
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ")" + getCondicionConsulta(filtros.get(fieldName), "like") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("organizacionId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("claseId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("indicadorId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuencia"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("tipoFuncion"))
        {
          condicionesConsulta = condicionesConsulta + "indicador.tipoFuncion" + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("excluirTipoFuncion"))
        {
          condicionesConsulta = condicionesConsulta + "indicador.tipoFuncion" + getCondicionConsulta(filtros.get(fieldName), "!=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuenciasContenidas"))
        {
          List frecuencias = Frecuencia.getFrecuenciasContenidas(Byte.parseByte(fieldValue));
          condicionesConsulta = condicionesConsulta + "(";
          for (int i = 0; i < frecuencias.size(); i++)
          {
            Frecuencia frecuencia = (Frecuencia)frecuencias.get(i);
            condicionesConsulta = condicionesConsulta + "indicador.frecuencia = " + frecuencia.getFrecuenciaId().toString() + " or ";
          }
          condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 4);
          condicionesConsulta = condicionesConsulta + ") and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("noCualitativos"))
        {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true")))
          {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("noCompuestos"))
        {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true")))
          {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaFormula().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaIndice().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaPromedio() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSumatoria().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("soloCompuestos"))
        {
          if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true")))
          {
            condicionesConsulta = condicionesConsulta + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSimple().toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("excluirIds"))
        {
          String[] ids = fieldValue.split(",");
          for (int i = 0; i < ids.length; i++)
          {
            Long id = new Long(ids[i]);
            condicionesConsulta = condicionesConsulta + "indicador.indicadorId != " + id.toString() + " and ";
            hayCondicionesConsulta = true;
          }
        }
        else if (fieldName.equals("indicadores"))
        {
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId" + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("excluirId"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "!=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("perspectivaId")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPerspectiva indPers";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPers.pk.indicadorId and indPers.pk." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("perspectivaId")) && (!fieldValue.equals("")))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPerspectiva indPers";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPers.pk.indicadorId and indPers.pk." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("planId")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPlan indPlan";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPlan.pk.indicadorId and indPlan.pk." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("planId")) && (!fieldValue.equals("")))
        {
          tablasConsulta = tablasConsulta + ", IndicadorPlan indPlan";
          condicionesConsulta = condicionesConsulta + "indicador.indicadorId = indPlan.pk.indicadorId and indPlan.pk." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("codigoEnlace")) && ((fieldValue.equals("IS NOT NULL")) || (fieldValue.equals("IS NULL"))))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + " " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if ((fieldName.equals("codigoEnlace")) && (!fieldValue.equals("")))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("indicadoresLogroPlanId"))
        {
          tablasConsulta = tablasConsulta + ", Plan plan";
          condicionesConsulta = condicionesConsulta + "(indicador.indicadorId = plan.nlAnoIndicadorId or indicador.indicadorId = plan.nlParIndicadorId) and plan.planId" + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("guia"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("orderBy"))
        {
          List idList = (List)filtros.get(fieldName);
          int j = 1;
          boolean desc = false;
          for (Iterator i = idList.listIterator(); i.hasNext();)
          {
            String param = (String)i.next();
            if (j % 2 == 1)
            {
              if (param.equals("asc")) {
                desc = false;
              } else
                desc = true;
            }
            if (j % 2 == 0)
            {
              if (param.equals("nombre"))
                ordenNombre = true;
              if (desc) {
                ordenConsulta = ordenConsulta + "indicador." + param + " desc, ";
              } else
                ordenConsulta = ordenConsulta + "indicador." + param + " asc, ";
              hayOrden = true;
            }
            j++;
          }
        }
      }
    }
    
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    }
    else {
      condicionesConsulta = "";
    }
    
    if (!ordenNombre) {
      ordenConsulta = ordenConsulta + "indicador.nombre asc";
    } else if (hayOrden) {
      ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 2);
    } else {
      ordenConsulta = "";
    }
    String objetoConsulta = "distinct(indicador)";
    
    session.clear();
    
    Query consulta = session.createQuery("select " + objetoConsulta + " from Indicador indicador" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    return consulta.list();
  }
  
  public int protegerMediciones(Map<?, ?> filtros)
  {
    return protegerMediciones(filtros, Boolean.valueOf(false));
  }
  
  public int protegerMediciones(Map<?, ?> filtros, Boolean liberar)
  {
    String updateSentence = "";
    Integer anoDesde = null;
    Integer anoHasta = null;
    Integer periodoDesde = null;
    Integer periodoHasta = null;
    
    if (filtros.containsKey("fechaFinal")) {
      updateSentence = "update SerieIndicador set fechaBloqueo= :fecha where 1=1 ";
    } else {
      updateSentence = "update Medicion set protegido= :value where 1=1 ";
    }
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
      String fieldName = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if (fieldName.equals("indicadorId")) {
          updateSentence = updateSentence + " and id.indicadorId=" + (Long)filtros.get(fieldName);
        } else if (fieldName.equals("serieId")) {
          updateSentence = updateSentence + " and id.serieId=" + (Long)filtros.get(fieldName);
        } else if (fieldName.equals("indicadores")) {
          updateSentence = updateSentence + " and id.indicadorId" + getCondicionConsulta(filtros.get(fieldName), "=");
        } else if (fieldName.equals("series"))
        {
          List parametros = (List)filtros.get(fieldName);
          String listSeries = " and id.serieId in (";
          for (Iterator i = parametros.iterator(); i.hasNext();)
          {
            Long serie = (Long)i.next();
            listSeries = listSeries + serie.toString() + ",";
          }
          listSeries = listSeries.substring(0, listSeries.length() - 1) + ") ";
          updateSentence = updateSentence + listSeries;
        }
        else if (fieldName.equals("anoDesde")) {
          anoDesde = (Integer)filtros.get(fieldName);
        } else if (fieldName.equals("anoHasta")) {
          anoHasta = (Integer)filtros.get(fieldName);
        } else if (fieldName.equals("periodoDesde")) {
          periodoDesde = (Integer)filtros.get(fieldName);
        } else if (fieldName.equals("periodoHasta")) {
          periodoHasta = (Integer)filtros.get(fieldName);
        }
      }
      if ((anoDesde != null) || (anoHasta != null))
      {
        if ((periodoHasta == null) && (anoHasta == null))
        {
          if (periodoDesde == null) {
            updateSentence = updateSentence + " and id.ano>=" + anoDesde;
          } else {
            updateSentence = updateSentence + " and ((id.ano>" + anoDesde + ") or (id.ano=" + anoDesde + " and id.periodo>=" + periodoDesde + " ))";
          }
        } else if ((periodoDesde == null) && (anoDesde == null))
        {
          if (periodoHasta == null) {
            updateSentence = updateSentence + " and id.ano<=" + anoHasta;
          } else {
            updateSentence = updateSentence + " and ((id.ano<" + anoHasta + ") or (id.ano=" + anoHasta + " and id.periodo<=" + periodoHasta + " ))";
          }
        } else if ((periodoDesde == null) && (periodoHasta == null)) {
          updateSentence = updateSentence + " and (id.ano>=" + anoDesde + " and id.ano<=" + anoHasta + ")";
        } else if ((periodoDesde != null) && (periodoHasta != null) && (anoDesde != null) && (anoHasta != null)) {
          updateSentence = updateSentence + " and (((id.ano>" + anoDesde + ") or (id.ano=" + anoDesde + " and id.periodo>=" + periodoDesde + ")) and ((id.ano<" + anoHasta + ") or (id.ano=" + anoHasta + " and id.periodo<=" + periodoHasta + ")))";
        } else if ((periodoHasta == null) && (anoDesde != null)) {
          updateSentence = updateSentence + " and (((id.ano>" + anoDesde + ") or (id.ano=" + anoDesde + " and id.periodo>=" + periodoDesde + ")) and (id.ano<=" + anoHasta + "))";
        } else if ((periodoDesde == null) && (anoHasta != null))
          updateSentence = updateSentence + " and ((id.ano>=" + anoDesde + ") and ((id.ano<" + anoHasta + ") or (id.ano=" + anoHasta + " and id.periodo<=" + periodoHasta + ")))";
      }
    }
    Query updateQuery;
    if (filtros.containsKey("fechaFinal")) {
      updateQuery = session.createQuery(updateSentence).setDate("fecha", (Date)filtros.get("fechaFinal"));
    } else {
      updateQuery = session.createQuery(updateSentence).setInteger("value", liberar.booleanValue() ? 0 : 1);
    }
    return updateQuery.executeUpdate();
  }
  
  public List getCategoriasIndicador(Long indicadorId)
  {
    List categorias = null;
    
    if (indicadorId != null)
    {
      Query consulta = session.createQuery("select categoriaMedicion from CategoriaMedicion categoriaMedicion, CategoriaIndicador categoriaIndicador where categoriaIndicador.pk.indicadorId =:indicadorId and categoriaIndicador.pk.categoriaId = categoriaMedicion.categoriaId");
      
      consulta.setLong("indicadorId", indicadorId.longValue());
      categorias = consulta.list();
    }
    return categorias;
  }
  
  public List<InsumoFormula> getInsumosFormula(Long indicadorId, Long serieId)
  {
    return session.createQuery("from InsumoFormula insfor where insfor.pk.padreId=:indicadorId and insfor.pk.serieId=:serieId").setLong("indicadorId", indicadorId.longValue()).setLong("serieId", serieId.longValue()).list();
  }
  
  public Indicador getIndicadorBasico(Long indicadorId)
  {
    Query consulta = session.createQuery("select new Indicador(ind.indicadorId, ind.claseId, ind.organizacionId, ind.nombre, ind.naturaleza, ind.frecuencia) from Indicador ind where ind.indicadorId=?").setLong(0, indicadorId.longValue());
    consulta.setCacheMode(CacheMode.IGNORE);
    Indicador ind = (Indicador)consulta.uniqueResult();
    
    return ind;
  }
  
  public ListaMap getDependenciasIndicador(Indicador indicador)
  {
    List mediciones = null;
    List metas = null;
    List insumosFormulaIndicador = null;
    List formulasIndicador = null;
    List celdasIndicador = null;
    List crecimientosIndicador = null;
    List plantillasIndicador = null;
    List indicadorPorPerspectivas = null;
    List indicadorPorPlanes = null;
    List estadosIndicador = null;
    ListaMap dependencias = new ListaMap();
    Long indicadorId = indicador.getIndicadorId();
    List indicadorFormula = null;
    
    Criteria consulta = null;
    
    consulta = session.createCriteria(Medicion.class);
    consulta.add(Restrictions.eq("medicionId.indicadorId", indicadorId));
    mediciones = consulta.list();
    dependencias.add(mediciones, "mediciones");
    
    consulta = session.createCriteria(Meta.class);
    consulta.add(Restrictions.eq("metaId.indicadorId", indicadorId));
    metas = consulta.list();
    dependencias.add(metas, "metas");
    
    consulta = session.createCriteria(InsumoFormula.class);
    consulta.add(Restrictions.eq("pk.indicadorId", indicadorId));
    insumosFormulaIndicador = consulta.list();
    dependencias.add(insumosFormulaIndicador, "insumosFormulaIndicador");
    
    consulta = session.createCriteria(Formula.class);
    consulta.add(Restrictions.eq("pk.indicadorId", indicadorId));
    formulasIndicador = consulta.list();
    dependencias.add(formulasIndicador, "formulasIndicador");
    
    consulta = session.createCriteria(Formula.class);
    consulta.add(Restrictions.ilike("expresion", indicadorId.toString(), MatchMode.ANYWHERE));
    indicadorFormula = consulta.list();
    dependencias.add(indicadorFormula, "indicadorFormula");
    
    consulta = session.createCriteria(IndicadorCelda.class);
    consulta.add(Restrictions.eq("pk.indicadorId", indicadorId));
    celdasIndicador = consulta.list();
    dependencias.add(celdasIndicador, "celdasIndicador");
    
    consulta = session.createCriteria(IndicadorEstado.class);
    consulta.add(Restrictions.eq("pk.indicadorId", indicadorId));
    estadosIndicador = consulta.list();
    dependencias.add(estadosIndicador, "estadosIndicador");
    
    consulta = session.createCriteria(Plantilla.class);
    consulta.add(Restrictions.eq("objetoId", indicadorId));
    plantillasIndicador = consulta.list();
    dependencias.add(plantillasIndicador, "plantillasIndicador");
    
    consulta = session.createCriteria(IndicadorPlan.class);
    consulta.add(Restrictions.eq("pk.indicadorId", indicadorId));
    indicadorPorPlanes = consulta.list();
    dependencias.add(indicadorPorPlanes, "indicadorPorPlanes");
    
    consulta = session.createCriteria(IndicadorPerspectiva.class);
    consulta.add(Restrictions.eq("pk.indicadorId", indicadorId));
    indicadorPorPerspectivas = consulta.list();
    dependencias.add(indicadorPorPerspectivas, "indicadorPorPerspectivas");
    
    return dependencias;
  }
  
  public int updateCampo(Long indicadorId, Map<?, ?> filtros) throws Throwable
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
        hqlUpdate = "update Indicador i set " + hqlUpdate + " where i.indicadorId = :indicadorId";
        
        Query actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
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
  
  public int updateAlerta(Long indicadorId, Byte alerta) throws Throwable
  {
    int actualizados = 0;
    try
    {
      String hqlUpdate = null;
      Query actualizacion;
      if (alerta == null)
      {
        hqlUpdate = "update Indicador i set i.alerta = null where i.indicadorId = :indicadorId";
        actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
      }
      else
      {
        hqlUpdate = "update Indicador i set i.alerta = :alerta where i.indicadorId = :indicadorId";
        actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue()).setByte("alerta", alerta.byteValue());
      }
      actualizados = actualizacion.executeUpdate();
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
  
  public int actualizarDatosIndicador(Long indicadorId, Double ultimaMedicion, Double ultimoProgramado, String fechaUltimaMedicion) throws Throwable
  {
    int actualizados = 0;
    try
    {
      String hqlUpdate = "update Indicador i set";
      
      if (ultimaMedicion == null) {
        hqlUpdate = hqlUpdate + " i.ultimaMedicion = null";
      } else {
        hqlUpdate = hqlUpdate + " i.ultimaMedicion = :ultimaMedicion";
      }
      if (ultimaMedicion == null) {
        hqlUpdate = hqlUpdate + ", i.alerta = null";
      }
      if (fechaUltimaMedicion == null) {
        hqlUpdate = hqlUpdate + ", i.fechaUltimaMedicion = null";
      } else {
        hqlUpdate = hqlUpdate + ", i.fechaUltimaMedicion = :fechaUltimaMedicion";
      }
      if (ultimoProgramado == null) {
        hqlUpdate = hqlUpdate + ", i.ultimoProgramado = null where i.indicadorId = :indicadorId";
      } else {
        hqlUpdate = hqlUpdate + ", i.ultimoProgramado = :ultimoProgramado where i.indicadorId = :indicadorId";
      }
      Query actualizacion = session.createQuery(hqlUpdate);
      
      if (ultimaMedicion != null) {
        actualizacion.setDouble("ultimaMedicion", ultimaMedicion.doubleValue());
      }
      if (fechaUltimaMedicion != null) {
        actualizacion.setString("fechaUltimaMedicion", fechaUltimaMedicion);
      }
      if (ultimoProgramado != null) {
        actualizacion.setDouble("ultimoProgramado", ultimoProgramado.doubleValue());
      }
      actualizacion = actualizacion.setLong("indicadorId", indicadorId.longValue());
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
  
  public Byte getFrecuenciaMaximaIndicadoresPlan(Long planId)
  {
    Byte frecuenciaMaxima = null;
    
    String hqlQuery = "select max(ind.frecuencia) from Indicador ind, IndicadorPlan indPlan where ind.indicadorId = indPlan.pk.indicadorId and indPlan.pk.planId = :planId";
    
    List resultado = session.createQuery(hqlQuery).setLong("planId", planId.longValue()).list();
    if (resultado.size() > 0) {
      frecuenciaMaxima = (Byte)resultado.get(0);
    }
    
    return frecuenciaMaxima;
  }
  
  public Indicador getIndicadorValoresOriginales(Long indicadorId)
  {
    Indicador indicador = null;
    
    String hqlQuery = "select frecuencia, naturaleza from Indicador ind where ind.indicadorId = :indicadorId";
    
    List resultado = session.createQuery(hqlQuery).setLong("indicadorId", indicadorId.longValue()).list();
    if (resultado.size() > 0) {
      indicador = new Indicador();
      
      Object[] valores = (Object[])resultado.get(0);
      
      indicador.setFrecuencia((Byte)valores[0]);
      indicador.setNaturaleza((Byte)valores[1]);
    }
    
    return indicador;
  }
  
  public Long getNumeroIndicadores(Map filtros)
  {
    Query consulta = prepararQueryGetIndicadores(null, null, filtros, true);
    
    return Long.valueOf(((Long)consulta.list().get(0)).longValue());
  }
  
  public int getNumeroUsosComoIndicadorInsumo(Long indicadorId) {
    String hqlQuery = "select distinct(insumoFormula) from InsumoFormula insumoFormula where insumoFormula.pk.indicadorId = :indicadorId";
    
    List resultado = session.createQuery(hqlQuery).setLong("indicadorId", indicadorId.longValue()).list();
    
    return resultado.size();
  }
  
  public int deleteReferenciasForaneasIndicador(Long indicadorId)
  {
    String hqlUpdate = "update Perspectiva p set p.nlAnoIndicadorId = null where p.nlAnoIndicadorId = :indicadorId";
    Query actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
    actualizacion.executeUpdate();
    
    hqlUpdate = "update Perspectiva p set p.nlParIndicadorId = null where p.nlParIndicadorId = :indicadorId";
    actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
    actualizacion.executeUpdate();
    
    hqlUpdate = "update Plan p set p.nlAnoIndicadorId = null where p.nlAnoIndicadorId = :indicadorId";
    actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
    actualizacion.executeUpdate();
    
    hqlUpdate = "update Plan p set p.nlParIndicadorId = null where p.nlParIndicadorId = :indicadorId";
    actualizacion = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
    actualizacion.executeUpdate();
    
    return 10000;
  }
  
  public Indicador getIndicadorProgramado(Long indicadorId, Byte revision) {
    String hqlQuery = "select indicador from Indicador indicador where indicador.indicadorAsociadoId = :indicadorId";
    
    if (revision != null) {
      hqlQuery = hqlQuery + " and indicador.indicadorAsociadoTipo = :tipo and indicador.indicadorAsociadoRevision = :revision";
    } else {
      hqlQuery = hqlQuery + " and indicador.indicadorAsociadoTipo = :tipo order by indicador.indicadorAsociadoRevision desc";
    }
    
    Query consulta = session.createQuery(hqlQuery).setLong("indicadorId", indicadorId.longValue()).setByte("tipo", TipoAsociadoIndicador.getTipoAsociadoIndicadorProgramado().byteValue());
    
    if (revision != null) {
      consulta.setByte("revision", revision.byteValue());
    }
    
    List programados = consulta.list();
    
    Indicador programado = null;
    
    if (programados.size() > 0) {
      programado = (Indicador)programados.get(0);
    }
    
    return programado;
  }
  
  public Indicador getIndicadorMinimo(Long indicadorId)
  {
    return getIndicadorMinimoMaximo(indicadorId, TipoAsociadoIndicador.getTipoAsociadoIndicadorMinimo().byteValue());
  }
  
  public Indicador getIndicadorMaximo(Long indicadorId) {
    return getIndicadorMinimoMaximo(indicadorId, TipoAsociadoIndicador.getTipoAsociadoIndicadorMaximo().byteValue());
  }
  
  public Indicador getIndicadorMinimoMaximo(Long indicadorId, byte tipo) {
    String hqlQuery = "select indicador from Indicador indicador where indicador.indicadorAsociadoId = :indicadorId and indicador.indicadorAsociadoTipo = :tipo";
    
    Query consulta = session.createQuery(hqlQuery).setLong("indicadorId", indicadorId.longValue()).setByte("tipo", tipo);
    
    List minimosMaximos = consulta.list();
    
    Indicador minimoMaximo = null;
    
    if (minimosMaximos.size() > 0) {
      minimoMaximo = (Indicador)minimosMaximos.get(0);
    }
    
    return minimoMaximo;
  }
  
  public boolean tieneIndicadorProgramado(Long indicadorId)
  {
    Query consulta = session.createQuery("select count(*) from Indicador indicador where indicador.indicadorAsociadoId = :indicadorId and indicadorAsociadoTipo = :tipo");
    
    consulta.setLong("indicadorId", indicadorId.longValue()).setByte("tipo", TipoAsociadoIndicador.getTipoAsociadoIndicadorProgramado().byteValue());
    
    return ((Integer)consulta.uniqueResult()).intValue() > 0;
  }
  
  public boolean tieneIndicadorMinimo(Long indicadorId)
  {
    Query consulta = session.createQuery("select count(*) from Indicador indicador where indicador.indicadorAsociadoId = :indicadorId and indicadorAsociadoTipo = :tipo");
    
    consulta.setLong("indicadorId", indicadorId.longValue()).setByte("tipo", TipoAsociadoIndicador.getTipoAsociadoIndicadorMinimo().byteValue());
    
    return ((Integer)consulta.uniqueResult()).intValue() > 0;
  }
  
  public boolean tieneIndicadorMaximo(Long indicadorId)
  {
    Query consulta = session.createQuery("select count(*) from Indicador indicador where indicador.indicadorAsociadoId = :indicadorId and indicadorAsociadoTipo = :tipo");
    
    consulta.setLong("indicadorId", indicadorId.longValue()).setByte("tipo", TipoAsociadoIndicador.getTipoAsociadoIndicadorMaximo().byteValue());
    
    return ((Integer)consulta.uniqueResult()).intValue() > 0;
  }
  
  public List<Indicador> getIndicadoresNombreFrecuencia(String orden, String tipoOrden, List<String> campos, boolean total, Map<String, Object> filtro)
  {
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtro != null)
    {
      Iterator<String> iter = filtro.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if ((filtro.get(fieldName) instanceof String)) {
          fieldValue = (String)filtro.get(fieldName);
        }
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("clases"))
        {
          condicionesConsulta = condicionesConsulta + "indicador.claseId in (" + fieldValue + ") and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by indicador." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
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
    String objetoConsulta = "distinct ";
    String campo = "";
    if (campos.size() > 0)
    {
      for (Iterator<?> iter = campos.iterator(); iter.hasNext();)
      {
        campo = (String)iter.next();
        objetoConsulta = objetoConsulta + "indicador." + campo + ", ";
      }
      
    } else
      objetoConsulta = objetoConsulta + "indicador.nombre, ";
    objetoConsulta = objetoConsulta.substring(0, objetoConsulta.length() - 2);
    
    String hqlQuery = "select " + objetoConsulta + " from Indicador indicador" + tablasConsulta + condicionesConsulta + ordenConsulta;
    
    List resultado = session.createQuery(hqlQuery).list();
    Indicador indicador = new Indicador();
    List<Indicador> indicadores = new ArrayList();
    for (Iterator<?> iter = resultado.iterator(); iter.hasNext();)
    {
      Object[] valores = (Object[])iter.next();
      
      indicador = new Indicador();
      indicador.setNombre((String)valores[0]);
      indicador.setFrecuencia((Byte)valores[1]);
      
      indicadores.add(indicador);
    }
    
    return indicadores;
  }
  
  public List<Long> getIndicadoresIds(Map<String, Object> filtro)
  {
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtro != null)
    {
      Iterator<String> iter = filtro.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if ((filtro.get(fieldName) instanceof String)) {
          fieldValue = (String)filtro.get(fieldName);
        }
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(indicador." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("clases"))
        {
          condicionesConsulta = condicionesConsulta + "indicador.claseId in (" + fieldValue + ") and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("frecuencia"))
        {
          condicionesConsulta = condicionesConsulta + "indicador." + fieldName + getCondicionConsulta(filtro.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    String objetoConsulta = "distinct indicador.indicadorId";
    
    String hqlQuery = "select " + objetoConsulta + " from Indicador indicador" + tablasConsulta + condicionesConsulta;
    
    List<?> resultado = session.createQuery(hqlQuery).list();
    List<Long> indicadores = new ArrayList();
    for (Iterator<?> iter = resultado.iterator(); iter.hasNext();)
    {
      Long valores = (Long)iter.next();
      
      indicadores.add(valores);
    }
    
    return indicadores;
  }
  
  public int saveCodigoEnlace(Long indicadorId, Long organizacionId, String codigoEnlace) throws Throwable
  {
    int actualizados = 0;
    
    try
    {
      String hqlS = "update Indicador indicador ";
      hqlS = hqlS + "set indicador.codigoEnlace = :codigoEnlace ";
      hqlS = hqlS + "where indicador.organizacionId = :organizacionId ";
      hqlS = hqlS + "and indicador.indicadorId = :indicadorId ";
      hqlS = hqlS + "and (indicador.enlaceParcial is not null)";
      
      Query actualizacion = session.createQuery(hqlS);
      actualizacion.setString("codigoEnlace", codigoEnlace).setLong("organizacionId", organizacionId.longValue()).setLong("indicadorId", indicadorId.longValue());
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
  
  public boolean esInsumo(Long indicadorId)
  {
    Criteria consulta = null;
    
    consulta = session.createCriteria(Formula.class);
    consulta.add(Restrictions.ilike("expresion", indicadorId.toString(), MatchMode.ANYWHERE));
    
    return consulta.list().size() > 0;
  }
  
  public Indicador getIndicador(Long indicadorId) {
		// TODO Auto-generated method stub
		Indicador indicador = new Indicador();
		
		 if (indicadorId !=null )
		    {
		      String sql = "from Indicador indicador where indicador.indicadorId ="+indicadorId;
		      
		      sql = sql + " order by indicador.indicadorId";
		      
		      Query consulta = session.createQuery(sql);
		      
		      indicador= (Indicador) consulta.uniqueResult();
		    }
		 
		return indicador;
	}

	public Formula getFormulaIndicador(Long indicadorId, Long serieId) {
		// TODO Auto-generated method stub
		Formula formula = new Formula();
		if (indicadorId !=null){
			String sql = "from Formula formula where formula.pk.indicadorId ="+indicadorId;
		      
		      Query consulta = session.createQuery(sql);
		      
		      formula= (Formula) consulta.uniqueResult();
		}
		return formula;
	}


	public Indicador getIndicador(Long organizacionId, String nombre) {
		// TODO Auto-generated method stub
		Indicador indicador = new Indicador();
		       
		
		 if (organizacionId !=null )
		    {
		      String sql = "from Indicador indicador where indicador.organizacionId ="+organizacionId;
		      
		      sql = sql + " and indicador.nombre = '" +nombre+"'" ;
		      
		      sql = sql + " order by indicador.indicadorId";
		      
		      Query consulta = session.createQuery(sql);
		      
		      indicador= (Indicador) consulta.uniqueResult();
		    }
		 
		return indicador;
	}
}
