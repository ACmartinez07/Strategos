
package com.visiongc.framework.auditoria.persistence.hibernate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;

public class AuditoriasHibernateSession extends VgcHibernateSession
implements AuditoriasPersistenceSession{

	 public AuditoriasHibernateSession(Session session)
	    {
	        super(session);
	    }

	    public AuditoriasHibernateSession(VgcHibernateSession parentSession)
	    {
	        super(parentSession);
	    }

	   
		@Override
		public PaginaLista getAuditoria(int pagina, int tamanoPagina,
				String orden[], String tipoOrden[], boolean getTotal,
				Map filtros) {
			// TODO Auto-generated method stub
			
			
			Query consulta = prepararQuery(orden, tipoOrden, filtros, false);
	        int total = 0;
	        if(getTotal)
	            total = consulta.list().size();
	        if(tamanoPagina > 0 && pagina > 0)
	            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
	        List auditorias = consulta.list();
	        if(!getTotal)
	            total = auditorias.size();
	        PaginaLista paginaLista = new PaginaLista();
	        paginaLista.setLista(auditorias);
	        paginaLista.setNroPagina(pagina);
	        paginaLista.setTamanoPagina(tamanoPagina);
	        paginaLista.setTamanoSetPaginas(5);
	        paginaLista.setTotal(total);
	        if(orden != null && orden.length > 0)
	        {
	            paginaLista.setOrden(orden[0]);
	            paginaLista.setTipoOrden(tipoOrden[0]);
	        } else
	        {
	            paginaLista.setOrden("");
	            paginaLista.setTipoOrden("");
	        }
	        return paginaLista;
			
		}

		@Override
		public List<Auditoria> getAuditorias(String orden[], String tipoOrden[], boolean getTotal,
				Map filtros) {
			// TODO Auto-generated method stub
			
			
			Query consulta = prepararQuery(orden, tipoOrden, filtros, false);
	        
	        List auditorias = consulta.list();
	        
	        return auditorias;
		
		}
		
		
		private Query prepararQuery(String orden[], String tipoOrden[], Map filtros, boolean soloContar)
	    {
	        String tablasConsulta = "";
	        String condicionesConsulta = " where ";
	        boolean hayCondicionesConsulta = false;
	        Date fechaDesde = null;
	        Date fechaHasta = null;
	        if(filtros != null)
	        {
	            Iterator iter = filtros.keySet().iterator();
	            String fieldName = null;
	            while(iter.hasNext()) 
	            {
	                fieldName = (String)iter.next();
	                if(fieldName.equals("tipoEvento"))
	                {
	                	 condicionesConsulta = condicionesConsulta + "lower(auditoria.tipoEvento)" + " like '%" + (String)filtros.get(fieldName) + "%' and ";
	                     hayCondicionesConsulta = true;
	                } else
	               
	                if(fieldName.equals("fechaDesde"))
	                {
	                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoria.fechaEjecucion >= :fechaDesde").append(" and ").toString();
	                    fechaDesde = (Date)filtros.get(fieldName);
	                    hayCondicionesConsulta = true;
	                } else
	                if(fieldName.equals("fechaHasta"))
	                {
	                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoria.fechaEjecucion <= :fechaHasta").append(" and ").toString();
	                    fechaHasta = (Date)filtros.get(fieldName);
	                    hayCondicionesConsulta = true;
	                }else
	                if(fieldName.equals("usuario"))
	                {
	                    	condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoria.usuario").append(")").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
	                        hayCondicionesConsulta = true;
	                }else 
	                if(fieldName.equals("entidad"))
	                {
	                	 condicionesConsulta = condicionesConsulta + "auditoria.entidad" + " like '%" + (String)filtros.get(fieldName) + "%' and ";
	                     hayCondicionesConsulta = true;
	                }
	            }
	        }
	        String ordenConsulta = "";
	       
	        if(hayCondicionesConsulta)
	            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
	        else
	            condicionesConsulta = "";
	        String objetoConsulta = "distinct(auditoria)";
	        if(soloContar)
	            objetoConsulta = "count(*)";
	        Query consulta = session.createQuery((new StringBuilder("select ")).append(objetoConsulta).append(" from Auditoria auditoria").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
	        if(fechaDesde != null)
	        {
	            FechaUtil.setHoraInicioDia(fechaDesde);
	            consulta.setTimestamp("fechaDesde", fechaDesde);
	        }
	        if(fechaHasta != null)
	        {
	            FechaUtil.setHoraFinalDia(fechaHasta);
	            consulta.setTimestamp("fechaHasta", fechaHasta);
	        }
	        return consulta;
	    }
}
