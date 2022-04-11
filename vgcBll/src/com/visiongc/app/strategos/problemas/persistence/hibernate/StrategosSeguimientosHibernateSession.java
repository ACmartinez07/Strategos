package com.visiongc.app.strategos.problemas.persistence.hibernate;

import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.problemas.persistence.StrategosSeguimientosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

public class StrategosSeguimientosHibernateSession
  extends StrategosHibernateSession implements StrategosSeguimientosPersistenceSession
{
  public StrategosSeguimientosHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosSeguimientosHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getSeguimientos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    int total = 0;
    String tablasConsulta = "";
    String condicionesConsulta = " where ";
    boolean hayCondicionesConsulta = false;
    
    if (filtros != null) {
      Iterator iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      while (iter.hasNext()) {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        
        if (fieldName.equals("accionId")) {
          condicionesConsulta = condicionesConsulta + "seguimiento." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("responsableAccionId")) {
          tablasConsulta = tablasConsulta + ", ResponsableAccion respAccion";
          condicionesConsulta = condicionesConsulta + "seguimiento.accionId = respAccion.pk.accionId and respAccion.pk.responsableId = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        } else if (fieldName.equals("responsableProblemaId")) {
          tablasConsulta = tablasConsulta + ", Problema problema, Accion accion";
          condicionesConsulta = condicionesConsulta + "seguimiento.accionId = accion.accionId and accion.problemaId = problema.problemaId and (problema.responsableId = " + fieldValue + " or problema.auxiliarId = " + fieldValue + ") and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    

    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals(""))) {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by seguimiento." + orden + " asc";
      }
      else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by seguimiento." + orden + " asc";
      } else {
        ordenConsulta = " order by seguimiento." + orden + " desc";
      }
    }
    

    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    
    Query consulta = session.createQuery("select distinct(seguimiento) from Seguimiento seguimiento" + tablasConsulta + condicionesConsulta + ordenConsulta);
    
    if (getTotal) {
      total = consulta.list().size();
    }
    
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    
    List indicadores = consulta.list();
    if (!getTotal) {
      total = indicadores.size();
    }
    
    PaginaLista paginaLista = new PaginaLista();
    paginaLista.setLista(indicadores);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  public int getOrdenMaxNumeroReporte(Long accionId)
  {
    Query consulta = session.createQuery("select max(numeroReporte) from Seguimiento s where s.accionId =" + accionId);
    
    List resultados = consulta.list();
    
    int ordenMaximo = 0;
    if (resultados.get(0) != null) {
      ordenMaximo = ((Integer)resultados.get(0)).intValue();
    }
    return ordenMaximo;
  }
  
  public Seguimiento getUltimoSeguimiento(Long accionId)
  {
    int numeroReporte = getOrdenMaxNumeroReporte(accionId);
    
    Query consulta = session.createQuery("select seguimiento from Seguimiento seguimiento where seguimiento.numeroReporte = ? and seguimiento.accionId = ?").setInteger(0, numeroReporte).setLong(1, accionId.longValue());
    
    List seguimientos = consulta.list();
    
    if (seguimientos.size() > 0) {
      return (Seguimiento)seguimientos.get(0);
    }
    return null;
  }
  
  public ConfiguracionMensajeEmailSeguimientos getConfiguracionMensajeEmailSeguimientos()
  {
    Query consulta = session.createQuery("select configuracion from ConfiguracionMensajeEmailSeguimientos configuracion");
    
    List configuraciones = consulta.list();
    if (configuraciones.size() > 0) {
      return (ConfiguracionMensajeEmailSeguimientos)configuraciones.get(0);
    }
    return null;
  }
}
