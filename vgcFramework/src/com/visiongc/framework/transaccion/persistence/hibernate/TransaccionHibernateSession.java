package com.visiongc.framework.transaccion.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.transaccion.persistence.TransaccionPersistenceSession;
import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;

public class TransaccionHibernateSession extends VgcHibernateSession
    implements TransaccionPersistenceSession
{

    public TransaccionHibernateSession(Session session)
    {
        super(session);
    }

    public TransaccionHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public PaginaLista getTransacciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros, boolean soloContar)
    {
        int total = 0;
        String tablasConsulta = "";
        String condicionesConsulta = " where ";
        boolean hayCondicionesConsulta = false;
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
                if(fieldName.equals("nombre"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(transaccion.").append(fieldName).append(") like '%").append(fieldValue.toLowerCase()).append("%' and ").toString();
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
        if(orden != null && !orden.equals(""))
            if(tipoOrden == null || tipoOrden.equals(""))
                ordenConsulta = (new StringBuilder(" order by transaccion.")).append(orden).append(" asc").toString();
            else
            if(tipoOrden.equalsIgnoreCase("asc"))
                ordenConsulta = (new StringBuilder(" order by transaccion.")).append(orden).append(" asc").toString();
            else
                ordenConsulta = (new StringBuilder(" order by transaccion.")).append(orden).append(" desc").toString();
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        String objetoConsulta = "transaccion";
        if(soloContar)
            objetoConsulta = "count(*)";
        Query consulta = session.createQuery((new StringBuilder("select ")).append(objetoConsulta).append(" from Transaccion transaccion").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
        if(getTotal)
            total = consulta.list().size();
        if(tamanoPagina > 0 && pagina > 0)
            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
        List transacciones = consulta.list();
        if(!getTotal)
            total = transacciones.size();
        PaginaLista paginaLista = new PaginaLista();
        paginaLista.setLista(transacciones);
        paginaLista.setNroPagina(pagina);
        paginaLista.setTamanoPagina(tamanoPagina);
        paginaLista.setTotal(total);
        paginaLista.setOrden(orden);
        paginaLista.setTipoOrden(tipoOrden);
        return paginaLista;
    }
}