package com.visiongc.app.strategos.modulo.codigoenlace.persistence.hibernate;

import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.app.strategos.modulo.codigoenlace.persistence.StrategosCodigoEnlacePersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;







public class StrategosCodigoEnlaceHibernateSession
  extends StrategosHibernateSession
  implements StrategosCodigoEnlacePersistenceSession
{
  public StrategosCodigoEnlaceHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosCodigoEnlaceHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getCodigoEnlace(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    int total = 0;
    
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
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
          condicionesConsulta = condicionesConsulta + "lower(codigoEnlace." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("codigo"))
        {
          condicionesConsulta = condicionesConsulta + "lower(codigoEnlace." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("valorBusqueda"))
        {
          condicionesConsulta = condicionesConsulta + "lower(codigoEnlace.codigo) like '%" + fieldValue.toLowerCase() + "%' or ";
          condicionesConsulta = condicionesConsulta + "lower(codigoEnlace.nombre) like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by codigoEnlace." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by codigoEnlace." + orden + " asc";
      } else {
        ordenConsulta = " order by codigoEnlace." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select distinct(codigoEnlace) from CodigoEnlace codigoEnlace" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List<CodigoEnlace> codigoEnlaces = consulta.list();
    if (!getTotal) {
      total = codigoEnlaces.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(codigoEnlaces);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  public boolean clearReferencia(String codigoEnlace)
  {
    int actualizados = 0;
    
    String hqlUpdate = null;
    
    hqlUpdate = "update Indicador ind set ind.codigoEnlace = null where ind.codigoEnlace = :codigoEnlace";
    Query actualizacion = session.createQuery(hqlUpdate).setString("codigoEnlace", codigoEnlace);
    actualizados = actualizacion.executeUpdate();
    
    return actualizados == 1;
  }
  
  public boolean updateReferencia(String codigoEnlaceOriginal, String codigoEnlaceActualizado)
  {
    int actualizados = 0;
    
    String hqlUpdate = null;
    
    hqlUpdate = "update Indicador ind set ind.codigoEnlace = :codigoEnlaceActualizado where ind.codigoEnlace = :codigoEnlaceOriginal";
    Query actualizacion = session.createQuery(hqlUpdate).setString("codigoEnlaceActualizado", codigoEnlaceActualizado).setString("codigoEnlaceOriginal", codigoEnlaceOriginal);
    actualizados = actualizacion.executeUpdate();
    
    return actualizados == 1;
  }
}
