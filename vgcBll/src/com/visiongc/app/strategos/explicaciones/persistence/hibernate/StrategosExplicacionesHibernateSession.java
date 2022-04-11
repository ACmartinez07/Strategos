package com.visiongc.app.strategos.explicaciones.persistence.hibernate;

import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.persistence.StrategosExplicacionesPersistenceSession;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

public class StrategosExplicacionesHibernateSession
  extends StrategosHibernateSession
  implements StrategosExplicacionesPersistenceSession
{
  public StrategosExplicacionesHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosExplicacionesHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getExplicaciones(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    String[] ordenArray = new String[1];
    String[] tipoOrdenArray = new String[1];
    ordenArray[0] = orden;
    tipoOrdenArray[0] = tipoOrden;
    
    Query consulta = prepararQuery(ordenArray, tipoOrdenArray, filtros, false);
    
    int total = 0;
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List<Explicacion> explicaciones = consulta.list();
    if (!getTotal) {
      total = explicaciones.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(explicaciones);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTamanoSetPaginas(5);
    paginaLista.setTotal(total);
    if ((orden != null) && (ordenArray.length > 0))
    {
      paginaLista.setOrden(ordenArray[0]);
      paginaLista.setTipoOrden(tipoOrdenArray[0]);
    }
    else
    {
      paginaLista.setOrden("");
      paginaLista.setTipoOrden("");
    }
    return paginaLista;
  }
  
  private Query prepararQuery(String[] orden, String[] tipoOrden, Map filtros, boolean soloContar)
  {
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    Date fechaDesde = null;
    Date fechaHasta = null;
    if (filtros != null)
    {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if (fieldName.equals("titulo"))
        {
          condicionesConsulta = condicionesConsulta + "lower(explicacion.titulo)" + getCondicionConsulta(filtros.get(fieldName), "like") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("objetoKey"))
        {
          condicionesConsulta = condicionesConsulta + "explicacion.objetoKey" + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("objetoId"))
        {
          condicionesConsulta = condicionesConsulta + "explicacion.objetoId" + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("tipo"))
        {
          condicionesConsulta = condicionesConsulta + "explicacion.tipo" + getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
          
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("fechaDesde"))
        {
          try
          {
            fechaDesde = FechaUtil.convertirStringToDate((String)filtros.get(fieldName), "formato.fecha.corta");
            condicionesConsulta = condicionesConsulta + "explicacion.fecha >= :fechaDesde" + " and ";
            hayCondicionesConsulta = true;
          }
          catch (Exception localException) {}
        }
        else if (fieldName.equals("fechaHasta"))
        {
          try
          {
            fechaHasta = FechaUtil.convertirStringToDate((String)filtros.get(fieldName), "formato.fecha.corta");
            condicionesConsulta = condicionesConsulta + "explicacion.fecha <= :fechaHasta" + " and ";
            hayCondicionesConsulta = true;
          }
          catch (Exception localException1) {}
        }
      }
    }
    String ordenConsulta = "";
    if ((orden != null) && (tipoOrden != null)) {
      for (int i = 0; (i < orden.length) && (i < tipoOrden.length); i++) {
        if ((orden[i] != null) && (!orden.equals(""))) {
          if ((tipoOrden[i] == null) || (tipoOrden[i].equals(""))) {
            ordenConsulta = ordenConsulta + "explicacion." + orden[i] + " asc, ";
          } else if (tipoOrden[i].equalsIgnoreCase("asc")) {
            ordenConsulta = ordenConsulta + "explicacion." + orden[i] + " asc, ";
          } else {
            ordenConsulta = ordenConsulta + "explicacion." + orden[i] + " desc, ";
          }
        }
      }
    }
    if (ordenConsulta.length() > 0)
    {
      ordenConsulta = " order by " + ordenConsulta;
      ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 2);
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    String objetoConsulta = "distinct(explicacion)";
    if (soloContar) {
      objetoConsulta = "count(*)";
    }
    Query consulta = this.session.createQuery("select " + objetoConsulta + " from Explicacion explicacion" + tablasConsulta + condicionesConsulta + ordenConsulta);
    if (fechaDesde != null)
    {
      FechaUtil.setHoraInicioDia(fechaDesde);
      consulta.setTimestamp("fechaDesde", fechaDesde);
    }
    if (fechaHasta != null)
    {
      FechaUtil.setHoraFinalDia(fechaHasta);
      consulta.setTimestamp("fechaHasta", fechaHasta);
    }
    return consulta;
  }
  
  public List getAdjuntosIdPorExplicacion(Long explicacionId)
  {
    return this.session.createQuery("select adjunto.pk.adjuntoId from AdjuntoExplicacion adjunto where adjunto.pk.explicacionId = ?").setLong(0, explicacionId.longValue()).list();
  }
  
  public void deleteAdjuntosExplicacion(List adjuntosId)
  {
    for (Iterator j = adjuntosId.iterator(); j.hasNext();)
    {
      Long adjuntoId = (Long)j.next();
      this.session.createQuery("delete from AdjuntoExplicacion where AdjuntoExplicacion.pk.explicacionId = ?").setLong(0, adjuntoId.longValue()).executeUpdate();
    }
  }
  
  public Long getNumeroAdjuntos(Long explicacionId)
  {
    Query consulta = this.session.createQuery("select count(*) from AdjuntoExplicacion adjunto where adjunto.pk.explicacionId = ?").setLong(0, explicacionId.longValue());
    List resultado = consulta.list();
    if (resultado.get(0) != null) {
      return (Long)resultado.get(0);
    }
    return new Long(0L);
  }
  
  public Long getNumeroExplicaciones(Long indicadorId)
  {
    Query consulta = this.session.createQuery("select count(*) from Explicacion explicacion where explicacion.objetoId = ?").setLong(0, indicadorId.longValue());
    List resultado = consulta.list();
    if (resultado.get(0) != null) {
      return (Long)resultado.get(0);
    }
    return new Long(0L);
  }
  
  public AdjuntoExplicacion getAdjunto(Long explicacionId)
  {
	  AdjuntoExplicacion adjunto = new AdjuntoExplicacion();
		
		 if (explicacionId !=null )
		    {
		      String sql = "from AdjuntoExplicacion adjunto where adjunto.AdjuntoExplicacionPK.explicacionId ="+explicacionId;
		 
		      Query consulta = session.createQuery(sql);
		      
		      adjunto = (AdjuntoExplicacion) consulta.uniqueResult();
		    }
		 
		return adjunto;
  }
}
