package com.visiongc.app.strategos.plancuentas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.model.Cuenta;
import com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentasPK;
import com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.plancuentas.persistence.StrategosCuentasPersistenceSession;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StrategosCuentasServiceImpl
  extends VgcAbstractService implements StrategosCuentasService
{
  private StrategosCuentasPersistenceSession persistenceSession = null;
  
  public StrategosCuentasServiceImpl(StrategosCuentasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteCuenta(Cuenta cuenta, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (cuenta.getCuentaId() != null)
      {
        resultado = persistenceSession.delete(cuenta, usuario);
      }
      
      if (resultado == 10000)
      {
        if (transActiva) {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
        
      }
      else if (transActiva) {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
      
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    

    return resultado;
  }
  
  public int saveCuenta(Cuenta cuenta, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if ((cuenta.getCuentaId() == null) || (cuenta.getCuentaId().longValue() == 0L))
      {
        cuenta.setCuentaId(new Long(persistenceSession.getUniqueId()));
        resultado = persistenceSession.insert(cuenta, usuario);
      } else {
        resultado = persistenceSession.update(cuenta, usuario);
      }
      
      if (transActiva) {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public List getMascaras()
  {
    return persistenceSession.getMascarasCodigoPlanCuentas();
  }
  
  public int definirMascara(MascaraCodigoPlanCuentas mascara, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if ((mascara.getMascaraId() == null) || (mascara.getMascaraId().longValue() == 0L))
      {
        mascara.setMascaraId(new Long(persistenceSession.getUniqueId()));
        for (Iterator iter = mascara.getGruposMascara().iterator(); iter.hasNext();) {
          GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentas = (GrupoMascaraCodigoPlanCuentas)iter.next();
          grupoMascaraCodigoPlanCuentas.getPk().setMascaraId(mascara.getMascaraId());
        }
        resultado = persistenceSession.insert(mascara, usuario);
      } else {
        resultado = persistenceSession.update(mascara, usuario);
        
        ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService(this);
        Cuenta cuentaRoot = (Cuenta)nodosArbolService.getNodoArbolRaiz(new Cuenta());
        cuentaRoot.getHijos().clear();
        nodosArbolService.close();
        saveCuenta(cuentaRoot, usuario);
      }
      
      if (transActiva) {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public List getMaximoNivelGrupo()
  {
    List niveles = persistenceSession.getMaximoNivelGrupo();
    return niveles;
  }
  
  public Cuenta crearCuentaRaiz(Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    Cuenta cuenta = new Cuenta();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      List mascarasCodigoPlanCuentas = persistenceSession.getMascarasCodigoPlanCuentas();
      if (mascarasCodigoPlanCuentas.size() < 1)
      {
        MascaraCodigoPlanCuentas mascaraCodigoPlanCuentas = new MascaraCodigoPlanCuentas();
        mascaraCodigoPlanCuentas.setMascara("X");
        mascaraCodigoPlanCuentas.setNiveles(new Integer(1));
        mascaraCodigoPlanCuentas.setGruposMascara(new HashSet());
        mascaraCodigoPlanCuentas.getGruposMascara().clear();
        
        GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentas = new GrupoMascaraCodigoPlanCuentas();
        grupoMascaraCodigoPlanCuentas.setNombre(messageResources.getResource("mascara.pordefecto.nombre"));
        grupoMascaraCodigoPlanCuentas.setMascara("X");
        grupoMascaraCodigoPlanCuentas.setPk(new GrupoMascaraCodigoPlanCuentasPK());
        grupoMascaraCodigoPlanCuentas.getPk().setMascaraId(new Long(0L));
        grupoMascaraCodigoPlanCuentas.getPk().setNivel(new Integer(1));
        mascaraCodigoPlanCuentas.getGruposMascara().add(grupoMascaraCodigoPlanCuentas);
        
        resultado = definirMascara(mascaraCodigoPlanCuentas, usuario);
      }
      
      if (resultado == 10000)
      {
        cuenta.setCuentaId(new Long(0L));
        cuenta.setPadreId(null);
        cuenta.setDescripcion(messageResources.getResource("cuenta.raiz.nombre"));
        cuenta.setCodigo("X");
        
        resultado = saveCuenta(cuenta, usuario);
      }
      
      if (resultado == 10000) {
        cuenta = (Cuenta)persistenceSession.load(Cuenta.class, cuenta.getCuentaId());
      } else {
        cuenta = null;
      }
      
      if (transActiva) {
        if ((resultado == 10000) && (cuenta != null)) {
          persistenceSession.commitTransaction();
        } else {
          persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return cuenta;
  }
}
