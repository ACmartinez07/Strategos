package com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPrdProductosPersistenceSession;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosPrdProductosHibernateSession
  extends StrategosHibernateSession implements StrategosPrdProductosPersistenceSession
{
  public StrategosPrdProductosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosPrdProductosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getProductos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Criteria consulta = prepareQuery(PrdProducto.class);
    
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext()) {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("nombre")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
        } else if (fieldName.equals("iniciativaId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        }
      }
    }
    

    return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
  }
  
  public List getProductosPorIniciativa(Long iniciativaId)
  {
    Query consulta = session.createQuery("from PrdProducto producto where producto.iniciativaId =:iniciativaId order by producto.nombre");
    
    consulta.setLong("iniciativaId", iniciativaId.longValue());
    
    return consulta.list();
  }
  
  public List getSeguimientosPorIniciativa(Long iniciativaId, String[] orden, String[] tipoOrden) {
    String ordenConsulta = "";
    if ((orden != null) && (tipoOrden != null)) {
      for (int i = 0; (i < orden.length) && (i < tipoOrden.length); i++) {
        if ((orden[i] != null) && (!orden[i].equals(""))) {
          if ((tipoOrden[i] == null) || (tipoOrden[i].equals(""))) {
            ordenConsulta = ordenConsulta + "seg." + orden[i] + " asc, ";
          }
          else if (tipoOrden[i].equalsIgnoreCase("asc")) {
            ordenConsulta = ordenConsulta + "seg." + orden[i] + " asc, ";
          } else {
            ordenConsulta = ordenConsulta + "seg." + orden[i] + " desc, ";
          }
        }
      }
      
      if (ordenConsulta.length() > 0) {
        ordenConsulta = " order by " + ordenConsulta.substring(0, ordenConsulta.length() - 2);
      }
    }
    
    Query consulta = session.createQuery("from PrdSeguimiento seg where seg.pk.iniciativaId =:iniciativaId" + ordenConsulta);
    
    consulta.setLong("iniciativaId", iniciativaId.longValue());
    
    return consulta.list();
  }
  
  public ListaMap getDependenciasPrdProducto(PrdProducto prdProducto)
  {
    List prdSeguimientosProducto = null;
    ListaMap dependencias = new ListaMap();
    
    Long productoId = prdProducto.getProductoId();
    Criteria consulta = null;
    
    consulta = session.createCriteria(PrdSeguimientoProducto.class);
    consulta.add(Restrictions.eq("pk.productoId", productoId));
    prdSeguimientosProducto = consulta.list();
    dependencias.add(prdSeguimientosProducto, "prdSeguimientosProducto");
    
    return dependencias;
  }
}
