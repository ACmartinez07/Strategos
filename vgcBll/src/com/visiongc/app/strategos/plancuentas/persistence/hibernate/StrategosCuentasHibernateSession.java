package com.visiongc.app.strategos.plancuentas.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.plancuentas.model.Cuenta;
import com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.plancuentas.persistence.StrategosCuentasPersistenceSession;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class StrategosCuentasHibernateSession
  extends StrategosHibernateSession implements StrategosCuentasPersistenceSession
{
  public StrategosCuentasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosCuentasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public List getMascarasCodigoPlanCuentas() {
    Criteria consulta = session.createCriteria(MascaraCodigoPlanCuentas.class);
    return consulta.list();
  }
  
  public List getMaximoNivelGrupo() {
    String sql = "from GrupoMascaraCodigoPlanCuentas order by nivel asc";
    Query consulta = session.createQuery(sql);
    return consulta.list();
  }
  
  public Cuenta getCuentaRoot22() {
    Criteria consulta = session.createCriteria(Cuenta.class);
    consulta.add(Restrictions.isNull("padreId"));
    Cuenta claseRoot = (Cuenta)consulta.uniqueResult();
    return claseRoot;
  }
  
  public List getCuentasHijos22(Long cuentaId) {
    Criteria consulta = session.createCriteria(Cuenta.class);
    consulta.add(Restrictions.eq("cuentaId", cuentaId));
    consulta.addOrder(Order.asc("nombre"));
    session.clear();
    return consulta.list();
  }
  
  public String getGrupoMascaraNivel(Integer nivel) {
    String sql = "select grupo.mascara from GrupoMascaraCodigoPlanCuentas grupo where grupo.pk.nivel = :nivel ";
    Query consulta = session.createQuery(sql);
    consulta.setInteger("nivel", nivel.intValue());
    return (String)consulta.uniqueResult();
  }
}
