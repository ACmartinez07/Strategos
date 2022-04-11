package com.visiongc.framework.modulo.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.modulo.persistence.ModuloPersistenceSession;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ModuloHibernateSession extends VgcHibernateSession
    implements ModuloPersistenceSession
{

    public ModuloHibernateSession(Session session)
    {
        super(session);
    }

    public ModuloHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public Modulo getModulo(String id)
    {
        Criteria consulta = session.createCriteria(Modulo.class);
        consulta.add(Restrictions.eq("id", id));
        List modulos = consulta.list();
        Modulo modulo = null;
        if(modulos.size() > 0)
            modulo = (Modulo)modulos.get(0);
        return modulo;
    }
}