package com.visiongc.app.strategos.causas.impl;

import com.visiongc.app.strategos.causas.StrategosCausasService;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.causas.persistence.StrategosCausasPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosCausasServiceImpl
  extends StrategosServiceImpl implements StrategosCausasService
{
  private StrategosCausasPersistenceSession persistenceSession = null;
  
  public StrategosCausasServiceImpl(StrategosCausasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteCausa(Causa causa, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (causa.getCausaId() != null)
      {
        resultado = persistenceSession.delete(causa, usuario);
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
  
  public int saveCausa(Causa causa, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldNames[1] = "padreId";
      fieldValues[0] = causa.getNombre();
      fieldValues[1] = causa.getPadreId();
      
      if ((causa.getCausaId() == null) || (causa.getCausaId().longValue() == 0L)) {
        if (persistenceSession.existsObject(causa, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else
        {
          causa.setCausaId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(causa, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "causaId";
        idFieldValues[0] = causa.getCausaId();
        if (persistenceSession.existsObject(causa, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(causa, usuario);
        }
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
  
  public Causa crearCausaRaiz(Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    Causa causa = new Causa();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      causa.setCausaId(new Long(0L));
      causa.setPadreId(null);
      causa.setNombre(messageResources.getResource("causas.raiz.nombre"));
      causa.setDescripcion(messageResources.getResource("causas.raiz.nombre"));
      causa.setNivel(new Integer(1));
      
      resultado = saveCausa(causa, usuario);
      
      if (resultado == 10000) {
        causa = (Causa)persistenceSession.load(Causa.class, causa.getCausaId());
      } else {
        causa = null;
      }
      
      if (transActiva) {
        if ((resultado == 10000) && (causa != null)) {
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
    
    return causa;
  }
}
