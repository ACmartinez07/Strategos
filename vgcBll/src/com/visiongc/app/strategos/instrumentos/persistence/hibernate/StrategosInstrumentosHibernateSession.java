package com.visiongc.app.strategos.instrumentos.persistence.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;


import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosInstrumentosPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
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
		
		String hqlQuery = "select frecuencia, instrumentoId from Instrumentos instrumento where instrumento.instrumentoId = :instrumentoId";
		
		List<Object[]> resultado = session.createQuery(hqlQuery).setLong("iniciativaId", instrumentoId.longValue()).list();
		if(resultado.size() > 0) {
			instrumento = new Instrumentos();
			
			Object[] valores = (Object[])resultado.get(0);
			
			instrumento.setFrecuencia((Byte)valores[0]);
		}
		
		return null;
	}
}
