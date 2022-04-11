package com.visiongc.framework.auditoria.persistence.hibernate;


import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;

import com.visiongc.framework.auditoria.persistence.hibernate.util.ComparatorAuditoriaAtributo;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class AuditoriaMedicionHibernateSession extends VgcHibernateSession
    implements AuditoriaMedicionPersistenceSession
{

    public AuditoriaMedicionHibernateSession(Session session)
    {
        super(session);
    }

    public AuditoriaMedicionHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

   
	@Override
	public PaginaLista getAuditoriaMedicion(int pagina, int tamanoPagina,
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
	public List<AuditoriaMedicion> getAuditoriasMedicion(String orden[], String tipoOrden[], boolean getTotal,
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
                if(fieldName.equals("accion"))
                {
                	condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoriaMedicion.accion").append(")").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
               
                if(fieldName.equals("fechaDesde"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaMedicion.fechaEjecucion >= :fechaDesde").append(" and ").toString();
                    fechaDesde = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaHasta"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaMedicion.fechaEjecucion <= :fechaHasta").append(" and ").toString();
                    fechaHasta = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                }else
                if(fieldName.equals("usuario"))
                {
                    	condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoriaMedicion.usuario").append(")").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
                        hayCondicionesConsulta = true;
                }else
                if(fieldName.equals("organizacion"))
                {
                	condicionesConsulta = condicionesConsulta + "auditoriaMedicion.organizacion" + " like '%" + (String)filtros.get(fieldName) + "%' and ";
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
       
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        String objetoConsulta = "distinct(auditoriaMedicion)";
        if(soloContar)
            objetoConsulta = "count(*)";
        Query consulta = session.createQuery((new StringBuilder("select ")).append(objetoConsulta).append(" from AuditoriaMedicion auditoriaMedicion").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
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