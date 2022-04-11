package com.visiongc.framework.importacion.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.importacion.persistence.ImportacionPersistenceSession;
import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;

public class ImportacionHibernateSession extends VgcHibernateSession
    implements ImportacionPersistenceSession
{

    public ImportacionHibernateSession(Session session)
    {
        super(session);
    }

    public ImportacionHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public PaginaLista getImportaciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
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
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("lower(importacion.").append(fieldName).append(") like '%").append(fieldValue.toLowerCase()).append("%' and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("usuarioId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("importacion.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
        if(orden != null && !orden.equals(""))
            if(tipoOrden == null || tipoOrden.equals(""))
                ordenConsulta = (new StringBuilder(" order by importacion.")).append(orden).append(" asc").toString();
            else
            if(tipoOrden.equalsIgnoreCase("asc"))
                ordenConsulta = (new StringBuilder(" order by importacion.")).append(orden).append(" asc").toString();
            else
                ordenConsulta = (new StringBuilder(" order by importacion.")).append(orden).append(" desc").toString();
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        Query consulta = session.createQuery((new StringBuilder("select distinct(importacion) from Importacion importacion")).append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
        if(getTotal)
            total = consulta.list().size();
        if(tamanoPagina > 0 && pagina > 0)
            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
        List importaciones = consulta.list();
        if(!getTotal)
            total = importaciones.size();
        PaginaLista paginaLista = new PaginaLista();
        paginaLista.setLista(importaciones);
        paginaLista.setNroPagina(pagina);
        paginaLista.setTamanoPagina(tamanoPagina);
        paginaLista.setTotal(total);
        paginaLista.setOrden(orden);
        paginaLista.setTipoOrden(tipoOrden);
        return paginaLista;
    }
}