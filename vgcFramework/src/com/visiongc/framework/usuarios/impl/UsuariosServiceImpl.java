package com.visiongc.framework.usuarios.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Licencia;
import com.visiongc.framework.model.PwdHistoria;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.model.UsuarioGrupoPK;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.usuarios.persistence.UsuariosPersistenceSession;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class UsuariosServiceImpl
  extends VgcAbstractService
  implements UsuariosService
{
  private UsuariosPersistenceSession persistenceSession = null;
  
  public UsuariosServiceImpl(UsuariosPersistenceSession inPersistenceSession, boolean persistenceOwned, FrameworkServiceFactory inServiceFactory, VgcMessageResources messageResources)
  {
    super(inPersistenceSession, persistenceOwned, inServiceFactory, messageResources);
    this.persistenceSession = inPersistenceSession;
    
    this.persistenceSession = inPersistenceSession;
  }
  
  public PaginaLista getUsuarios(int pagina, String orden, String tipoOrden)
  {
    return getUsuarios(pagina, orden, tipoOrden, true, null);
  }
  
  public PaginaLista getUsuarios(int pagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros)
  {
    String tamanoPagina = "20";
    String tamanoSetPaginas = "5";
    
    PaginaLista paginaUsuarios = this.persistenceSession.getUsuarios(pagina, Integer.parseInt(tamanoPagina), orden, tipoOrden, getTotal, filtros);
    if ((tamanoSetPaginas != null) && (!tamanoSetPaginas.equals(""))) {
      paginaUsuarios.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
    }
    if (paginaUsuarios.getTotal() / paginaUsuarios.getTamanoPagina() + 1.0F <= pagina)
    {
      if (pagina > 1) {
        paginaUsuarios = this.persistenceSession.getUsuarios(pagina, Integer.parseInt(tamanoPagina), orden, tipoOrden, getTotal, filtros);
      }
      if ((tamanoSetPaginas != null) && (!tamanoSetPaginas.equals(""))) {
        paginaUsuarios.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
      }
    }
    return paginaUsuarios;
  }
  
  public PaginaLista getGrupos(int pagina, String orden, String tipoOrden)
  {
    return getGrupos(pagina, orden, tipoOrden, true, null);
  }
  
  public PaginaLista getGrupos(int pagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros)
  {
    String tamanoPagina = "20";
    String tamanoSetPaginas = "5";
    
    PaginaLista paginaGrupos = this.persistenceSession.getGrupos(pagina, Integer.parseInt(tamanoPagina), orden, tipoOrden, getTotal, filtros);
    if ((tamanoSetPaginas != null) && (!tamanoSetPaginas.equals(""))) {
      paginaGrupos.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
    }
    if (paginaGrupos.getTotal() / paginaGrupos.getTamanoPagina() + 1.0F <= pagina)
    {
      if (pagina > 1) {
        paginaGrupos = this.persistenceSession.getGrupos(pagina - 1, Integer.parseInt(tamanoPagina), orden, tipoOrden, getTotal, filtros);
      }
      if ((tamanoSetPaginas != null) && (!tamanoSetPaginas.equals(""))) {
        paginaGrupos.setTamanoSetPaginas(Integer.parseInt(tamanoSetPaginas));
      }
    }
    return paginaGrupos;
  }
  
  public int bloquearUsuario(Long usuarioId)
  {
    return this.persistenceSession.bloquearUsuario(usuarioId);
  }
  
  public int deshabilitarUsuario(Long usuarioId)
  {
    return this.persistenceSession.deshabilitarUsuario(usuarioId);
  }
  
  public int savePwdHistoria(PwdHistoria pwdHistoria)
  {
    boolean transActiva = false;
    int resDb = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      resDb = this.persistenceSession.insert(pwdHistoria, null);
      if (transActiva)
      {
        if (resDb == 10000) {
          this.persistenceSession.commitTransaction();
        } else {
          this.persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      resDb = 10003;
    }
    return resDb;
  }
  
  public boolean checkPwdHistoria(PwdHistoria pwdHistoria, int numeroClaves)
  {
    return this.persistenceSession.checkPwdHistoria(pwdHistoria, numeroClaves);
  }
  
  public int saveUsuario(Usuario usuario, Usuario gestor)
  {
    boolean transActiva = false;
    int resDb = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      fieldNames[0] = "UId";
      fieldValues[0] = usuario.getUId();
      if ((usuario.getUsuarioId() == null) || (usuario.getUsuarioId().equals(Long.valueOf("0"))))
      {
        if (this.persistenceSession.existsObject(usuario, fieldNames, fieldValues))
        {
          resDb = 10003;
        }
        else
        {
          usuario.setUsuarioId(new Long(this.persistenceSession.getUniqueId()));
          for (Iterator<?> i = usuario.getUsuarioGrupos().iterator(); i.hasNext();)
          {
            UsuarioGrupo ug = (UsuarioGrupo)i.next();
            ug.getPk().setUsuarioId(usuario.getUsuarioId());
          }
          if (gestor != null)
          {
            usuario.setCreadoId(gestor.getUsuarioId());
            usuario.setCreado(new Date());
          }
          resDb = this.persistenceSession.insert(usuario, gestor);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "usuarioId";
        idFieldValues[0] = usuario.getUsuarioId();
        if (this.persistenceSession.existsObject(usuario, fieldNames, fieldValues, idFieldNames, idFieldValues))
        {
          resDb = 10003;
        }
        else
        {
          if (gestor != null)
          {
            usuario.setModificadoId(gestor.getUsuarioId());
            usuario.setModificado(new Date());
          }
          resDb = this.persistenceSession.update(usuario, gestor);
        }
      }
      if (transActiva)
      {
        if (resDb == 10000) {
          this.persistenceSession.commitTransaction();
        } else {
          this.persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resDb;
  }
  
  public int deleteUsuario(Usuario usuario, Usuario gestor)
  {
    boolean transActiva = false;
    int resultado = 10001;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (usuario.getUsuarioId() != null)
      {
        this.persistenceSession.deleteDependencias(usuario.getUsuarioId());
        resultado = this.persistenceSession.delete(usuario, gestor);
      }
      if ((resultado == 10000) || (resultado == 10001))
      {
        if (transActiva)
        {
          this.persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resultado;
  }
  
  public int saveGrupo(Grupo grupo, Usuario gestor)
  {
    boolean transActiva = false;
    int resDb = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      fieldNames[0] = "grupo";
      fieldValues[0] = grupo.getGrupo();
      if ((grupo.getGrupoId() == null) || (grupo.getGrupoId().equals(Long.valueOf("0"))))
      {
        if (this.persistenceSession.existsObject(grupo, fieldNames, fieldValues))
        {
          resDb = 10003;
        }
        else
        {
          grupo.setGrupoId(new Long(this.persistenceSession.getUniqueId()));
          if (gestor != null)
          {
            grupo.setCreadoId(gestor.getUsuarioId());
            grupo.setCreado(new Date());
          }
          resDb = this.persistenceSession.insert(grupo, gestor);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "grupoId";
        idFieldValues[0] = grupo.getGrupoId();
        if (this.persistenceSession.existsObject(grupo, fieldNames, fieldValues, idFieldNames, idFieldValues))
        {
          resDb = 10003;
        }
        else
        {
          if (gestor != null)
          {
            grupo.setModificadoId(gestor.getUsuarioId());
            grupo.setModificado(new Date());
          }
          resDb = this.persistenceSession.update(grupo, gestor);
        }
      }
      if (transActiva)
      {
        if (resDb == 10000) {
          this.persistenceSession.commitTransaction();
        } else {
          this.persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resDb;
  }
  
  public int deleteGrupo(Grupo grupo, Usuario gestor)
  {
    boolean transActiva = false;
    int resDb = 10001;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (grupo.getGrupoId() != null)
      {
        grupo = (Grupo)this.persistenceSession.load(Grupo.class, grupo.getGrupoId());
        resDb = this.persistenceSession.delete(grupo, gestor);
      }
      if (resDb == 10000)
      {
        this.persistenceSession.commitTransaction();
        transActiva = false;
      }
      else
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resDb;
  }
  
  public int getTotalOrganizacionesPorUsuario(Long usuarioId)
  {
    return this.persistenceSession.getTotalOrganizacionesPorUsuario(usuarioId);
  }
  
  public int getTotalGruposPorUsuario(Long usuarioId)
  {
    return this.persistenceSession.getTotalGruposPorUsuario(usuarioId);
  }
  
  public int getTotalOrganizacionesPorGrupo(Long grupoId)
  {
    return this.persistenceSession.getTotalOrganizacionesPorGrupo(grupoId);
  }
  
  public int getTotalUsuariosPorGrupo(Long grupoId)
  {
    return this.persistenceSession.getTotalUsuariosPorGrupo(grupoId);
  }
  
  public boolean checkLicencia(HttpServletRequest request)
  {
    boolean tienePermiso = true;
    try
    {
      Licencia licencia = new Licencia().getLicencia(request);
      if (!licencia.getChequearLicencia().booleanValue())
      {
        PaginaLista paginaUsuarios = getUsuarios(0, "fullName", "ASC", false, null);
        if ((paginaUsuarios.getLista() != null) && (paginaUsuarios.getLista().size() > 0)) {
          if (paginaUsuarios.getLista().size() >= licencia.getNumeroUsuarios().intValue()) {
            tienePermiso = false;
          }
        }
      }
    }
    catch (Exception localException) {}
    return tienePermiso;
  }
}
