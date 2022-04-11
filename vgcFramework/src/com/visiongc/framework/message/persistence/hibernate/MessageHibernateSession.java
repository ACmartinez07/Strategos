package com.visiongc.framework.message.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.message.persistence.MessagePersistenceSession;
import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;

public class MessageHibernateSession extends VgcHibernateSession
    implements MessagePersistenceSession
{

    public MessageHibernateSession(Session session)
    {
        super(session);
    }

    public MessageHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public PaginaLista getMessages(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
    {
        int total = 0;
        String tablasConsulta = "";
        String condicionesConsulta = " where ";
        boolean hayCondicionesConsulta = false;
        Date fechaDesde = null;
        Date fechaHasta = null;
        if(filtros != null)
        {
            Iterator iter = filtros.keySet().iterator();
            String fieldName = null;
            String fieldValue = null;
            while(iter.hasNext()) 
            {
                fieldName = (String)iter.next();
                if(filtros.get(fieldName) == null)
                    fieldValue = null;
                else
                if(filtros.get(fieldName) instanceof String)
                    fieldValue = (String)filtros.get(fieldName);
                else
                    fieldValue = getValorCondicionConsulta(filtros.get(fieldName));
                if(fieldName.equals("usuarioId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fecha"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("estatus"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("tipo"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaDesde"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.fecha >= :fechaDesde").append(" and ").toString();
                    fechaDesde = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaHasta"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.fecha <= :fechaHasta").append(" and ").toString();
                    fechaHasta = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fuente"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("message.").append(fieldName).append(" = '").append(fieldValue).append("' and ").toString();
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
        if(orden != null && !orden.equals(""))
            if(tipoOrden == null || tipoOrden.equals(""))
                ordenConsulta = (new StringBuilder(" order by message.")).append(orden).append(" asc").toString();
            else
            if(tipoOrden.equalsIgnoreCase("asc"))
                ordenConsulta = (new StringBuilder(" order by message.")).append(orden).append(" asc").toString();
            else
                ordenConsulta = (new StringBuilder(" order by message.")).append(orden).append(" desc").toString();
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        Query consulta = session.createQuery((new StringBuilder("select distinct(message) from Message message")).append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
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
        if(getTotal)
            total = consulta.list().size();
        if(tamanoPagina > 0 && pagina > 0)
            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
        List message = consulta.list();
        if(!getTotal)
            total = message.size();
        PaginaLista paginaLista = new PaginaLista();
        paginaLista.setLista(message);
        paginaLista.setNroPagina(pagina);
        paginaLista.setTamanoPagina(tamanoPagina);
        paginaLista.setTotal(total);
        paginaLista.setOrden(orden);
        paginaLista.setTipoOrden(tipoOrden);
        return paginaLista;
    }

    public int setVisto(Long usuarioId, Long fecha, Byte estatus)
        throws Exception
    {
        int respuesta = 10000;
        String sql = "update Message message";
        sql = (new StringBuilder(String.valueOf(sql))).append(" set message.estatus = :estatus").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" where message.usuarioId = :usuarioId").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" and message.fecha = :fecha").toString();
        Query update = session.createQuery(sql);
        update.setLong("usuarioId", usuarioId.longValue());
        update.setTimestamp("fecha", new Date(fecha.longValue()));
        update.setByte("estatus", estatus.byteValue());
        int actualizaciones = update.executeUpdate();
        if(actualizaciones == 0)
            respuesta = 10001;
        return respuesta;
    }
}