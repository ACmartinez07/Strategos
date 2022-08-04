package com.visiongc.app.strategos.instrumentos.persistence.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosInstrumentosPersistenceSession;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;


public class StrategosInstrumentosHibernateSession extends StrategosHibernateSession implements StrategosInstrumentosPersistenceSession{
	
	public StrategosInstrumentosHibernateSession(Session session)
	  {
	    super(session);
	  }
	  
	  public StrategosInstrumentosHibernateSession(StrategosHibernateSession parentSession)
	  {
	    super(parentSession);
	  }
	  
	  private Query prepararQueryGetInstrumentos(String orden, String tipoOrden, Map filtros, boolean soloContar)
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
	        if (fieldName.equals("nombreCorto"))
	        {
	          condicionesConsulta = condicionesConsulta + "lower(instrumento." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
	          hayCondicionesConsulta = true;
	        }
	        
	        else if (fieldName.equals("anio"))
	        {
	          condicionesConsulta = condicionesConsulta + "lower(instrumento." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
		      hayCondicionesConsulta = true;
	        }
	        
	        else if (fieldName.equals("estatus"))
	        {
	          condicionesConsulta = condicionesConsulta + "instrumento." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	          hayCondicionesConsulta = true;
	        }
	        else if (fieldName.equals("tiposConvenioId"))
	        {
	          condicionesConsulta = condicionesConsulta + "instrumento." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	          hayCondicionesConsulta = true;
	        }
	        else if (fieldName.equals("cooperanteId"))
	        {
	          condicionesConsulta = condicionesConsulta + "instrumento." + fieldName + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	          hayCondicionesConsulta = true;
	        }
	        
	        
	      }
	    }
	    
	    String ordenConsulta = "";
	    if ((orden != null) && (!orden.equals("")))
	    {
	      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
	        ordenConsulta = " order by instrumento." + orden + " asc";
	      } else if (tipoOrden.equalsIgnoreCase("asc")) {
	        ordenConsulta = " order by instrumento." + orden + " asc";
	      } else {
	        ordenConsulta = " order by instrumento." + orden + " desc";
	      }
	    }
	    if (hayCondicionesConsulta) {
	      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 4);
	    } else {
	      condicionesConsulta = "";
	    }
	    String objetoConsulta = "distinct(instrumento)";
	    if (soloContar) {
	      objetoConsulta = "count(*)";
	    }
	    Query consulta = session.createQuery("select " + objetoConsulta + " from Instrumentos instrumento" + tablasConsulta + condicionesConsulta + ordenConsulta);
	    
	    return consulta;
	  }
	  
	  
	  public PaginaLista getInstrumentos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
	  {
	    Query consulta = prepararQueryGetInstrumentos(orden, tipoOrden, filtros, false);
	    
	    int total = 0;
	    
	    if (getTotal) {
	      total = consulta.list().size();
	    }
	    if ((tamanoPagina > 0) && (pagina > 0)) {
	      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
	    }
	    List instrumentos = consulta.list();
	    if (!getTotal) {
	      total = instrumentos.size();
	    }
	    PaginaLista paginaLista = new PaginaLista();
	    
	    paginaLista.setLista(instrumentos);
	    paginaLista.setNroPagina(pagina);
	    paginaLista.setTamanoPagina(tamanoPagina);
	    paginaLista.setTotal(total);
	    paginaLista.setOrden(orden);
	    paginaLista.setTipoOrden(tipoOrden);
	    
	    return paginaLista;
	  }
	
	public List<InstrumentoIniciativa> getIniciativasInstrumento(Long instrumentoId) {
		Query consulta = session.createQuery("select instrumentoIniciativa from InstrumentoIniciativa instrumentoIniciativa where instrumentoIniciativa.pk.instrumentoId = :instrumentoId");
		consulta.setLong("instrumentoId", instrumentoId.longValue());
		
		return consulta.list();
	}

	public int updatePesos(InstrumentoIniciativa instrumentoIniciativa, Usuario Usuario) {
		
		String sql = "update InstrumentoIniciativa instrumentoIniciativa set instrumentoIniciativa.peso = :peso ";
		String sqlNulo = "update InstrumentoIniciativa instrumentoIniciativa set instrumentoIniciativa.peso = null ";
		
		String sqlWhere = "where instrumentoIniciativa.pk.instrumentoId = :instrumentoId";
		sqlWhere = sqlWhere + " and instrumentoIniciativa.pk.iniciativaId = :iniciativaId";
		
		Query update = null;
		if(instrumentoIniciativa.getPeso() != null) {
			update = session.createQuery(sql + sqlWhere);
		}else {
			update = session.createQuery(sqlNulo + sqlWhere);
		}
		update.setLong("instrumentoId", instrumentoIniciativa.getPk().getInstrumentoId().longValue());
		update.setLong("iniciativaId", instrumentoIniciativa.getPk().getIniciativaId().longValue());
		
		if(instrumentoIniciativa.getPeso() != null) {
			update.setDouble("peso", instrumentoIniciativa.getPeso().doubleValue());
		}
		
		int actualizados = update.executeUpdate();
				
		
		return actualizados != 0 ? 10000 : 10001;
	}

	
	public Instrumentos getValoresOriginales(Long instrumentoId) {
		
		Instrumentos instrumento = null;
		
		String hqlQuery = "select instrumentoId from Instrumentos instrumento where instrumento.instrumentoId = :instrumentoId";
		
		List<Long> resultado = session.createQuery(hqlQuery).setLong("instrumentoId", instrumentoId.longValue()).list();
		if(resultado.size() > 0) {
			instrumento = new Instrumentos();
			
			Long valores = (Long)resultado.get(0);
			
			instrumento.setFrecuencia(Frecuencia.getFrecuenciaTrimestral());
		}
		
		return instrumento;
	}
	
	public Instrumentos getInstrumentoByIndicador(long indicadorId) {
		
		String sql = "select nre Instrumentos(instrumento.instrumentoId) from Instrumentos instrumento, IndicadorInstrumento indicadorInstrumento where instrumento.instrumentoId = indicadorInstrumento.pk.instrumentoId and indicadorInstrumento.pk.indicadorId=:indicadorId";
		
		Query consulta = session.createQuery(sql);
		consulta.setLong("indicadorId", indicadorId);
		
		Instrumentos instrumento = (Instrumentos)consulta.uniqueResult();
		
		if(instrumento != null) {
			return (Instrumentos)load(Instrumentos.class, new Long(instrumento.getInstrumentoId().longValue()));
		}
		
		return null;
	}		
	
	public ListaMap getDependenciasCiclicasInstrumento(Instrumentos instrumento)
	  {
	    ListaMap dependenciasCiclicas = new ListaMap();
	    
	    Criteria consulta = null;	    
	    if (instrumento.getClaseId() != null)
	    {
	      consulta = session.createCriteria(ClaseIndicadores.class);
	      consulta.add(Restrictions.eq("claseId", instrumento.getClaseId()));
	      List clases = consulta.list();
	      dependenciasCiclicas.add(clases, "clases");
	    }
	    
	    return dependenciasCiclicas;
	  }
	
	public ListaMap getDependenciasInstrumento(Instrumentos instrumento)
	  {
	    ListaMap dependencias = new ListaMap();	    
	    
	    Criteria consulta = null;
	    	    	    
	    
	    consulta = session.createCriteria(PryActividad.class);
	    consulta.add(Restrictions.eq("objetoAsociadoId", instrumento.getInstrumentoId()));
	    List objetosAsociados = consulta.list();
	    dependencias.add(objetosAsociados, "objetosAsociados");
	    
	    consulta = session.createCriteria(PryActividad.class);
	    consulta.add(Restrictions.eq("proyectoId", instrumento.getInstrumentoId()));
	    List actividades = consulta.list();
	    dependencias.add(actividades, "actividades");
	    
	    return dependencias;
	  }
	
}
