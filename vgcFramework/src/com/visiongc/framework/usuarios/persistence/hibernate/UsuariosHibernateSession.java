package com.visiongc.framework.usuarios.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.PwdHistoria;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.persistence.UsuariosPersistenceSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class UsuariosHibernateSession
  extends VgcHibernateSession
  implements UsuariosPersistenceSession
{
  public UsuariosHibernateSession(Session session)
  {
    super(session);
  }
  
  public UsuariosHibernateSession(VgcHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public PaginaLista getUsuarios(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros)
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
        if (filtros.get(fieldName) == null) {
          fieldValue = null;
        } else if ((filtros.get(fieldName) instanceof String)) {
          fieldValue = (String)filtros.get(fieldName);
        } else {
          fieldValue = getValorCondicionConsulta(filtros.get(fieldName));
        }
        if (fieldName.equals("fullName"))
        {
          condicionesConsulta = condicionesConsulta + "usuario." + fieldName + " like '%" + fieldValue + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("UId"))
        {
          condicionesConsulta = condicionesConsulta + "lower(usuario." + fieldName + ") like '%" + fieldValue + "%' and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("isAdmin"))
        {
          condicionesConsulta = condicionesConsulta + "usuario." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("estatus"))
        {
          condicionesConsulta = condicionesConsulta + "usuario." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("deshabilitado"))
        {
          condicionesConsulta = condicionesConsulta + "usuario." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("organizacionId"))
        {
          tablasConsulta = tablasConsulta + ", UsuarioGrupo ug";
          condicionesConsulta = condicionesConsulta + "usuario.usuarioId = ug.pk.usuarioId and ug.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("grupoId"))
        {
          tablasConsulta = tablasConsulta + ", UsuarioGrupo ug";
          condicionesConsulta = condicionesConsulta + "usuario.usuarioId = ug.pk.usuarioId and ug.pk." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
        else if (fieldName.equals("isConnected"))
        {
          condicionesConsulta = condicionesConsulta + "usuario." + fieldName + " = " + fieldValue + " and ";
          hayCondicionesConsulta = true;
        }
      }
    }
    String ordenConsulta = "";
    if ((orden != null) && (!orden.equals(""))) {
      if ((tipoOrden == null) || (tipoOrden.equals(""))) {
        ordenConsulta = " order by usuario." + orden + " asc";
      } else if (tipoOrden.equalsIgnoreCase("asc")) {
        ordenConsulta = " order by usuario." + orden + " asc";
      } else {
        ordenConsulta = " order by usuario." + orden + " desc";
      }
    }
    if (hayCondicionesConsulta) {
      condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
    } else {
      condicionesConsulta = "";
    }
    Query consulta = this.session.createQuery("select distinct(usuario) from Usuario usuario" + tablasConsulta + condicionesConsulta + ordenConsulta);
    if (getTotal) {
      total = consulta.list().size();
    }
    if ((tamanoPagina > 0) && (pagina > 0)) {
      consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
    }
    List<Usuario> usuarios = consulta.list();
    if (!getTotal) {
      total = usuarios.size();
    }
    PaginaLista paginaLista = new PaginaLista();
    
    paginaLista.setLista(usuarios);
    paginaLista.setNroPagina(pagina);
    paginaLista.setTamanoPagina(tamanoPagina);
    paginaLista.setTotal(total);
    paginaLista.setOrden(orden);
    paginaLista.setTipoOrden(tipoOrden);
    
    return paginaLista;
  }
  
  public PaginaLista getGrupos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros)
  {
    Criteria consulta = prepareQuery(Grupo.class);
    if (filtros != null)
    {
      Iterator<String> iter = filtros.keySet().iterator();
      String fieldName = null;
      String fieldValue = null;
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        fieldValue = (String)filtros.get(fieldName);
        if (fieldName.equals("grupoId")) {
          consulta.add(Restrictions.eq(fieldName, new Long(fieldValue)));
        } else if (fieldName.equals("grupo")) {
          consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.EXACT));
        }
      }
    }
    PaginaLista paginaLista = executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
    
    return paginaLista;
  }
  
  public int getTotalOrganizacionesPorUsuario(Long usuarioId)
  {
    return ((Integer)this.session.createQuery("select count(distinct pk.organizacionId) from UsuarioGrupo where pk.usuarioId = ?").setLong(0, usuarioId.longValue()).iterate().next()).intValue();
  }
  
  public int getTotalGruposPorUsuario(Long usuarioId)
  {
    return ((Integer)this.session.createQuery("select count(distinct pk.grupoId) from UsuarioGrupo where pk.usuarioId = ?").setLong(0, usuarioId.longValue()).iterate().next()).intValue();
  }
  
  public int getTotalOrganizacionesPorGrupo(Long grupoId)
  {
    return ((Integer)this.session.createQuery("select count(distinct pk.organizacionId) from UsuarioGrupo where pk.grupoId = ?").setLong(0, grupoId.longValue()).iterate().next()).intValue();
  }
  
  public int getTotalUsuariosPorGrupo(Long grupoId)
  {
    return ((Integer)this.session.createQuery("select count(distinct pk.usuarioId) from UsuarioGrupo where pk.grupoId = ?").setLong(0, grupoId.longValue()).iterate().next()).intValue();
  }
  
  public int bloquearUsuario(Long usuarioId)
  {
    int resultado = 10000;
    Query update = this.session.createQuery("update Usuario usuario set usuario.bloqueado = 1 where usuario.usuarioId = :usuarioId");
    update.setLong("usuarioId", usuarioId.longValue());
    update.executeUpdate();
    return resultado;
  }
  
  public int deshabilitarUsuario(Long usuarioId)
  {
    int resultado = 10000;
    Query update = this.session.createQuery("update Usuario usuario set usuario.estatus = 2 where usuario.usuarioId = :usuarioId");
    update.setLong("usuarioId", usuarioId.longValue());
    update.executeUpdate();
    return resultado;
  }
  
  public boolean checkPwdHistoria(PwdHistoria pwdHistoria, int numeroClaves)
  {
    Query consulta = this.session.createQuery("select hist from PwdHistoria hist where hist.usuarioId = :usuarioId order by hist.fecha DESC");
    consulta.setLong("usuarioId", pwdHistoria.getUsuarioId().longValue());
    List<PwdHistoria> listaConsulta = consulta.list();
    boolean hayPassword = false;
    if (listaConsulta.size() > 0)
    {
      int index = 1;
      for (Iterator<PwdHistoria> iter = listaConsulta.iterator(); iter.hasNext();)
      {
        if (index > numeroClaves) {
          break;
        }
        PwdHistoria pwdH = (PwdHistoria)iter.next();
        if (pwdH.getPwdPlain().equals(pwdHistoria.getPwdPlain()))
        {
          hayPassword = true;
          break;
        }
        index++;
      }
    }
    return hayPassword;
  }
  
  public int deleteDependencias(Long usuarioId)
  {
    String hqlDelete = "delete PwdHistoria pwdHis where usuarioId = :usuarioId";
    int borrados = this.session.createQuery(hqlDelete).setLong("usuarioId", usuarioId.longValue()).executeUpdate();
    
    return borrados;
  }
}
