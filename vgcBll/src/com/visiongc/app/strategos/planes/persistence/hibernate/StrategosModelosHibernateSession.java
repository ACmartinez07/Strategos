package com.visiongc.app.strategos.planes.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.persistence.StrategosModelosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;

public class StrategosModelosHibernateSession extends StrategosHibernateSession implements StrategosModelosPersistenceSession
{
  public StrategosModelosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosModelosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getModelos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros)
  {
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtros != null)
    {
      Iterator<String> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if (filtros.get(fieldName) == null) {
          fieldValue = null;
        } else if ((filtros.get(fieldName) instanceof String)) {
          fieldValue = (String)filtros.get(fieldName);
        } else {
          fieldValue = getValorCondicionConsulta(filtros.get(fieldName));
        }
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(modelo." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("descripcion"))
        {
          condicionesConsulta = condicionesConsulta + "lower(modelo." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("planId"))
        {
          condicionesConsulta = condicionesConsulta + "modelo.pk.planId = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by modelo." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by modelo." + orden + " asc";
      } else {
        ordenConsulta = " order by modelo." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    String objetoConsulta = "modelo";
    
    Query consulta = session.createQuery("select " + objetoConsulta + " from Modelo modelo" + condicionesConsulta + ordenConsulta);
    
    int total = 0;
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List<Modelo> modelos = consulta.list();
    if (!getTotal) {
      total = modelos.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(modelos);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
}
