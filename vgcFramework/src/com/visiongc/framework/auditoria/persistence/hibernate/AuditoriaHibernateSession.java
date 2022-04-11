package com.visiongc.framework.auditoria.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.auditoria.persistence.hibernate.util.ComparatorAuditoriaAtributo;
import java.util.*;
import org.hibernate.*;

public class AuditoriaHibernateSession extends VgcHibernateSession
    implements AuditoriaPersistenceSession
{

    public AuditoriaHibernateSession(Session session)
    {
        super(session);
    }

    public AuditoriaHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public List getObjetosAuditables()
    {
        Criteria consulta = prepareQuery(ObjetoAuditable.class);
        List objetos = consulta.list();
        List resultados = new ArrayList();
        for(Iterator iter = objetos.iterator(); iter.hasNext();)
        {
            Object objeto = iter.next();
            session.evict(objeto);
            if(!resultados.contains(objeto))
                resultados.add(objeto);
        }

        return resultados;
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
                if(fieldName.equals("tipoEvento") || fieldName.equals("pk.tipoEvento"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaEvento.pk.tipoEvento").append(getCondicionConsulta(filtros.get(fieldName), "=")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("instanciaId") || fieldName.equals("pk.instanciaId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoriaEvento.pk.instanciaId").append(")").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("instanciaNombre"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoriaEvento.instanciaNombre").append(")").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("usuarioId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaEvento.usuarioId").append(getCondicionConsulta(filtros.get(fieldName), "=")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("objetoId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaEvento.objetoId").append(getCondicionConsulta(filtros.get(fieldName), "=")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaDesde") || fieldName.equals("pk.fechaDesde"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaEvento.pk.fecha >= :fechaDesde").append(" and ").toString();
                    fechaDesde = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaHasta") || fieldName.equals("pk.fechaHasta"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaEvento.pk.fecha <= :fechaHasta").append(" and ").toString();
                    fechaHasta = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
        if(orden != null && tipoOrden != null)
        {
            for(int i = 0; i < orden.length && i < tipoOrden.length; i++)
                if(orden[i] != null && !orden.equals(""))
                    if(tipoOrden[i] == null || tipoOrden[i].equals(""))
                        ordenConsulta = (new StringBuilder(String.valueOf(ordenConsulta))).append("auditoriaEvento.").append(orden[i]).append(" asc, ").toString();
                    else
                    if(tipoOrden[i].equalsIgnoreCase("asc"))
                        ordenConsulta = (new StringBuilder(String.valueOf(ordenConsulta))).append("auditoriaEvento.").append(orden[i]).append(" asc, ").toString();
                    else
                        ordenConsulta = (new StringBuilder(String.valueOf(ordenConsulta))).append("auditoriaEvento.").append(orden[i]).append(" desc, ").toString();

        }
        if(ordenConsulta.length() > 0)
        {
            ordenConsulta = (new StringBuilder(" order by ")).append(ordenConsulta).toString();
            ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 2);
        }
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        String objetoConsulta = "distinct(auditoriaEvento)";
        if(soloContar)
            objetoConsulta = "count(*)";
        Query consulta = session.createQuery((new StringBuilder("select ")).append(objetoConsulta).append(" from AuditoriaEvento auditoriaEvento").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
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

    private Query prepararQueryAuditoriaPorAtributo(String nombreClaseAuditoriaAtributo, String orden[], String tipoOrden[], Map filtros, boolean soloContar)
    {
        String tablasConsulta = "";
        String condicionesConsulta = " where ";
        boolean hayCondicionesConsulta = false;
        Date fecha = null;
        Date fechaDesde = null;
        Date fechaHasta = null;
        if(filtros != null)
        {
            Iterator iter = filtros.keySet().iterator();
            String fieldName = null;
            while(iter.hasNext()) 
            {
                fieldName = (String)iter.next();
                if(fieldName.equals("instanciaId") || fieldName.equals("pk.instanciaId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoriaAtributo.pk.instanciaId)").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("nombreAtributo") || fieldName.equals("pk.nombreAtributo"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(auditoriaAtributo.pk.nombreAtributo").append(")").append(getCondicionConsulta(filtros.get(fieldName), "like")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("usuarioId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaAtributo.usuarioId").append(getCondicionConsulta(filtros.get(fieldName), "=")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("objetoId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaAtributo.objetoId").append(getCondicionConsulta(filtros.get(fieldName), "=")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("tipoEvento"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaAtributo.tipoEvento").append(getCondicionConsulta(filtros.get(fieldName), "=")).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaDesde") || fieldName.equals("pk.fechaDesde"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaAtributo.pk.fecha >= :fechaDesde").append(" and ").toString();
                    fechaDesde = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaHasta") || fieldName.equals("pk.fechaHasta"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaAtributo.pk.fecha <= :fechaHasta").append(" and ").toString();
                    fechaHasta = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fecha") || fieldName.equals("pk.fecha"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("auditoriaAtributo.pk.fecha = :fecha").append(" and ").toString();
                    fecha = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
        if(orden != null && tipoOrden != null)
        {
            for(int i = 0; i < orden.length && i < tipoOrden.length; i++)
                if(orden[i] != null && !orden.equals(""))
                    if(tipoOrden[i] == null || tipoOrden[i].equals(""))
                        ordenConsulta = (new StringBuilder(String.valueOf(ordenConsulta))).append("auditoriaAtributo.").append(orden[i]).append(" asc, ").toString();
                    else
                    if(tipoOrden[i].equalsIgnoreCase("asc"))
                        ordenConsulta = (new StringBuilder(String.valueOf(ordenConsulta))).append("auditoriaAtributo.").append(orden[i]).append(" asc, ").toString();
                    else
                        ordenConsulta = (new StringBuilder(String.valueOf(ordenConsulta))).append("auditoriaAtributo.").append(orden[i]).append(" desc, ").toString();

        }
        if(ordenConsulta.length() > 0)
        {
            ordenConsulta = (new StringBuilder(" order by ")).append(ordenConsulta).toString();
            ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 2);
        }
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        String objetoConsulta = "distinct(auditoriaAtributo)";
        if(soloContar)
            objetoConsulta = "count(*)";
        Query consulta = session.createQuery((new StringBuilder("select ")).append(objetoConsulta).append(" from ").append(nombreClaseAuditoriaAtributo).append(" auditoriaAtributo").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
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
        if(fecha != null)
            consulta.setTimestamp("fecha", fecha);
        return consulta;
    }

    public PaginaLista getAuditoriasEventos(int pagina, int tamanoPagina, String orden[], String tipoOrden[], boolean getTotal, Map filtros)
    {
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

    private List getAuditoriasPorTipoAtributo(String nombreClaseAuditoriaAtributo, int total[], int pagina, int tamanoPagina, String orden[], String tipoOrden[], boolean getTotal, 
            Map filtros)
    {
        Query consulta = prepararQueryAuditoriaPorAtributo(nombreClaseAuditoriaAtributo, orden, tipoOrden, filtros, false);
        if(getTotal)
            total[0] = consulta.list().size();
        if(tamanoPagina > 0 && pagina > 0)
            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
        List auditorias = consulta.list();
        if(!getTotal)
            total[0] = auditorias.size();
        return auditorias;
    }

    public PaginaLista getAuditoriasPorAtributo(int pagina, int tamanoPagina, String orden[], String tipoOrden[], boolean getTotal, Map filtros)
    {
        int total = 0;
        int totalTipoAtributo[] = new int[1];
        boolean hayRegistros = true;
        List auditoriasString = new ArrayList();
        List auditoriasMemo = new ArrayList();
        List auditoriasFecha = new ArrayList();
        List auditoriasEntero = new ArrayList();
        List auditoriasFlotante = new ArrayList();
        while(total == 0 && hayRegistros) 
        {
            auditoriasString = getAuditoriasPorTipoAtributo("AuditoriaString", totalTipoAtributo, pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(totalTipoAtributo[0] > total)
                total = totalTipoAtributo[0];
            auditoriasMemo = getAuditoriasPorTipoAtributo("AuditoriaMemo", totalTipoAtributo, pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(totalTipoAtributo[0] > total)
                total = totalTipoAtributo[0];
            auditoriasFecha = getAuditoriasPorTipoAtributo("AuditoriaFecha", totalTipoAtributo, pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(totalTipoAtributo[0] > total)
                total = totalTipoAtributo[0];
            auditoriasEntero = getAuditoriasPorTipoAtributo("AuditoriaEntero", totalTipoAtributo, pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(totalTipoAtributo[0] > total)
                total = totalTipoAtributo[0];
            auditoriasFlotante = getAuditoriasPorTipoAtributo("AuditoriaFlotante", totalTipoAtributo, pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
            if(totalTipoAtributo[0] > total)
                total = totalTipoAtributo[0];
            if(total == 0)
                if(pagina > 1)
                    pagina = 1;
                else
                    hayRegistros = false;
        }
        Set auditorias = null;
        if(orden != null && orden.length > 0)
        {
            ComparatorAuditoriaAtributo comparator = new ComparatorAuditoriaAtributo(orden[0], tipoOrden[0]);
            auditorias = new TreeSet(comparator);
            auditorias.addAll(auditoriasString);
            auditorias.addAll(auditoriasMemo);
            auditorias.addAll(auditoriasFecha);
            auditorias.addAll(auditoriasEntero);
            auditorias.addAll(auditoriasFlotante);
        }
        PaginaLista paginaLista = new PaginaLista();
        paginaLista.setLista(new ArrayList());
        paginaLista.getLista().addAll(auditorias);
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
}