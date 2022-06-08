package com.visiongc.app.strategos.portafolios.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativaPK;
import com.visiongc.app.strategos.portafolios.persistence.StrategosPortafoliosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosPortafoliosHibernateSession extends StrategosHibernateSession implements StrategosPortafoliosPersistenceSession
{
  public StrategosPortafoliosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPortafoliosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getPortafolios(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    Criteria consulta = prepareQuery(Portafolio.class);
    
    if (filtros != null)
    {
      Iterator<String> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("nombre")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        } else if (fieldName.equals("activo")) {
          consulta.add(Restrictions.eq(fieldName, new Byte(fieldValue)));
        } else if (fieldName.equals("organizacionId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        }
      }
    }
    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public Portafolio getValoresOriginales(Long portafolioId)
  {
    Portafolio portafolio = null;
    
    String hqlQuery = "select frecuencia, id from Portafolio portafolio where portafolio.id = :portafolioId";
    
    List<Object[]> resultado = session.createQuery(hqlQuery).setLong("portafolioId", portafolioId.longValue()).list();
    if (resultado.size() > 0)
    {
      portafolio = new Portafolio();
      
      Object[] valores = (Object[])resultado.get(0);
      
      portafolio.setFrecuencia((Byte)valores[0]);
    }
    
    return portafolio;
  }
  
  public List<PortafolioIniciativa> getIniciativasPortafolio(Long portafolioId)
  {
    Query consulta = session.createQuery("select portafolioIniciativa from PortafolioIniciativa portafolioIniciativa where portafolioIniciativa.pk.portafolioId = :portafolioId");
    consulta.setLong("portafolioId", portafolioId.longValue());
    
    return consulta.list();
  }
  
  public int updatePesos(PortafolioIniciativa portafolioIniciativa, Usuario usuario)
  {
    String sql = "update PortafolioIniciativa portafolioIniciativa set portafolioIniciativa.peso = :peso ";
    String sqlNulo = "update PortafolioIniciativa portafolioIniciativa set portafolioIniciativa.peso = null ";
    
    String sqlWhere = "where portafolioIniciativa.pk.portafolioId = :portafolioId";
    sqlWhere = sqlWhere + " and portafolioIniciativa.pk.iniciativaId = :iniciativaId";
    
    Query update = null;
    if (portafolioIniciativa.getPeso() != null) {
      update = session.createQuery(sql + sqlWhere);
    } else {
      update = session.createQuery(sqlNulo + sqlWhere);
    }
    update.setLong("portafolioId", portafolioIniciativa.getPk().getPortafolioId().longValue());
    update.setLong("iniciativaId", portafolioIniciativa.getPk().getIniciativaId().longValue());
    
    if (portafolioIniciativa.getPeso() != null) {
      update.setDouble("peso", portafolioIniciativa.getPeso().doubleValue());
    }
    int actualizados = update.executeUpdate();
        
    return actualizados != 0 ? 10000 : 10001;
  }
}
