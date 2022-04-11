package com.visiongc.framework.arboles.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.framework.arboles.*;
import com.visiongc.framework.arboles.persistence.ArbolesPersistenceSession;
import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ArbolesHibernateSession extends VgcHibernateSession
    implements ArbolesPersistenceSession
{

    public ArbolesHibernateSession(Session session)
    {
        super(session);
    }

    public ArbolesHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public NodoArbol getNodoArbolRaiz(NodoArbol nodoArbol)
    {
        return getNodoArbolRaiz(nodoArbol, null);
    }

    public NodoArbol getNodoArbolRaiz(NodoArbol nodoArbol, Object grupoNodosId[])
    {
        Criteria consulta = session.createCriteria(nodoArbol.getClass());
        consulta.add(Restrictions.isNull(nodoArbol.getNodoArbolNombreCampoPadreId()));
        if(grupoNodosId != null)
        {
            for(int i = 0; i < grupoNodosId.length / 2 + 1; i += 2)
                consulta.add(Restrictions.eq(grupoNodosId[i].toString(), grupoNodosId[i + 1]));

        }
        NodoArbol nodoArbolRaiz = (NodoArbol)consulta.uniqueResult();
        return nodoArbolRaiz;
    }

    public List getNodosArbolHijos(NodoArbol nodoArbol)
    {
        return getNodosArbolHijos(nodoArbol, null);
    }

    public List getNodosArbolHijos(NodoArbol nodoArbol, String order)
    {
        Long nodoArbolId = new Long(nodoArbol.getNodoArbolId());
        Criteria consulta = session.createCriteria(nodoArbol.getClass());
        consulta.add(Restrictions.eq(nodoArbol.getNodoArbolNombreCampoPadreId(), nodoArbolId));
        if(order != null)
            consulta.addOrder(Order.asc(order));
        consulta.addOrder(Order.asc(nodoArbol.getNodoArbolNombreCampoNombre()));
        session.clear();
        return consulta.list();
    }

    public List getNodosHoja(NodoArbol nodoArbol, ObjetoNodoHoja nodoHoja)
    {
        Byte tipoNodoHoja = nodoHoja.getTipoNodoHoja();
        String nombreClaseNodoHoja = ObjetoAbstracto.getClassSimpleName(nodoHoja);
        String nombreClaseIntermedia = ObjetoAbstracto.getClassSimpleName(nodoHoja.getClaseIntermedia());
        Query consulta = null;
        Long nodoIdNodoArbol = new Long(nodoArbol.getNodoArbolId());
        if(tipoNodoHoja.byteValue() == TipoNodoHoja.getTipoNodoHojaConplejo().byteValue())
        {
            consulta = session.createQuery((new StringBuilder("select claseNodoHoja from ")).append(nombreClaseNodoHoja).append(" claseNodoHoja, ").append(nombreClaseIntermedia).append(" claseIntermedia ").append("where claseIntermedia.pk.").append(nodoHoja.getNombreCampoPadreIdIntermedio()).append("=:padreId and ").append("claseIntermedia.pk.").append(nodoHoja.getNombreCampoObjetoIdIntermedio()).append("=claseNodoHoja.").append(nodoHoja.getNombreCampoId()).append(" ").append("order by claseNodoHoja.").append(nodoHoja.getNombreCampoNombre()).append(" asc").toString());
            consulta.setLong("padreId", nodoIdNodoArbol.longValue());
        } else
        {
            consulta = session.createQuery((new StringBuilder("select claseNodoHoja from ")).append(nombreClaseNodoHoja).append(" claseNodoHoja ").append("where claseNodoHoja.").append(nodoHoja.getNombreCampoPadreId()).append("=:padreId ").append("order by claseNodoHoja.").append(nodoHoja.getNombreCampoNombre()).append(" asc").toString());
            consulta.setLong("padreId", nodoIdNodoArbol.longValue());
        }
        return consulta.list();
    }
}