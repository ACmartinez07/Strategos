package com.visiongc.app.strategos.graficos.persistence.hibernate;

import com.visiongc.app.strategos.graficos.persistence.StrategosGraficosPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;






public class StrategosGraficosHibernateSession
  extends StrategosHibernateSession
  implements StrategosGraficosPersistenceSession
{
  public StrategosGraficosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosGraficosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getGraficos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    int total = 0;
    
    String tablasConsulta = "";
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
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("nombre"))
        {
          condicionesConsulta = condicionesConsulta + "lower(grafico." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("organizacionId"))
        {
          condicionesConsulta = condicionesConsulta + "grafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("graficoId"))
        {
          condicionesConsulta = condicionesConsulta + "grafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("usuarioId"))
        {
          condicionesConsulta = condicionesConsulta + "grafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("objetoId"))
        {
          if (fieldValue.equals("NULL")) {
            condicionesConsulta = condicionesConsulta + "grafico." + fieldName + " IS " + fieldValue + " and ";
          } else
            condicionesConsulta = condicionesConsulta + "grafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by grafico." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by grafico." + orden + " asc";
      } else {
        ordenConsulta = " order by grafico." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select distinct(grafico) from Grafico grafico" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List<?> graficos = consulta.list();
    if (!getTotal) {
      total = graficos.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(graficos);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
}
