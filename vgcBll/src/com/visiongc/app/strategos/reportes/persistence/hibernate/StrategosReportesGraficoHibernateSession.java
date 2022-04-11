package com.visiongc.app.strategos.reportes.persistence.hibernate;

import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.app.strategos.reportes.persistence.StrategosReportesGraficoPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;


public class StrategosReportesGraficoHibernateSession
  extends StrategosHibernateSession
  implements StrategosReportesGraficoPersistenceSession
{
  public StrategosReportesGraficoHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosReportesGraficoHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getReportes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros, Long usuarioId)
  {
    int total = 0;
    
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    boolean hayPublico = false;
    boolean hayUsuario = false;
    
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
          condicionesConsulta = condicionesConsulta + "lower(reporteGrafico." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("reporteId"))
        {
          condicionesConsulta = condicionesConsulta + "reporteGrafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("tipo"))
        {
          condicionesConsulta = condicionesConsulta + "reporteGrafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("publico"))
        {
          condicionesConsulta = condicionesConsulta + "reporteGrafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
          hayPublico = true;
        }
        else if (fieldName.equals("usuarioId"))
        {
          condicionesConsulta = condicionesConsulta + "reporteGrafico." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
          hayUsuario = true;
        }
      }
      
      if ((!hayUsuario) && (!hayPublico))
      {
        condicionesConsulta = condicionesConsulta + "(reporteGrafico.usuarioId = " + usuarioId.toString() + " or reporteGrafico.publico = 1) and ";
        hayCondicionesConsulta = true;
      }
    }
    
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals("")))
    {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by reporteGrafico." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by reporteGrafico." + orden + " asc";
      } else {
        ordenConsulta = " order by reporteGrafico." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = session.createQuery("select reporteGrafico from ReporteGrafico reporteGrafico" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List<?> reportes = consulta.list();
    if (!getTotal) {
      total = reportes.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(reportes);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }


	public ReporteGrafico obtenerReporte(Long reporteId) {
		
		ReporteGrafico reporte = new ReporteGrafico();
		
		if(reporteId != null) {
			String sql = "from ReporteGrafico reporte where reporte.reporteId ="+ reporteId;
			Query consulta = session.createQuery(sql);
			reporte = (ReporteGrafico) consulta.uniqueResult();
		}
		
		return reporte; 
		
	}
}
