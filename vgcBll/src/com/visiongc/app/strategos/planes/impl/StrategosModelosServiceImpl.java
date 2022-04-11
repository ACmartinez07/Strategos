package com.visiongc.app.strategos.planes.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.ModeloPK;
import com.visiongc.app.strategos.planes.persistence.StrategosModelosPersistenceSession;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public class StrategosModelosServiceImpl extends StrategosServiceImpl implements StrategosModelosService
{
  private StrategosModelosPersistenceSession persistenceSession = null;
  
  public StrategosModelosServiceImpl(StrategosModelosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public com.visiongc.commons.util.PaginaLista getModelos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros)
  {
    return persistenceSession.getModelos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteModelo(Modelo modelo, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      resultado = persistenceSession.delete(modelo, usuario);
      
      if (resultado == 10000)
      {
        if (transActiva)
        {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    
    return resultado;
  }
  
  public int saveModelo(Modelo modelo, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[2];
    Object[] fieldValues = new Object[2];
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "pk.planId";
      fieldValues[0] = modelo.getPk().getPlanId();
      fieldNames[1] = "nombre";
      fieldValues[1] = modelo.getNombre();
      
      if ((modelo.getPk().getModeloId() == null) || (modelo.getPk().getModeloId().longValue() == 0L))
      {
        if (!persistenceSession.existsObject(modelo, fieldNames, fieldValues))
        {
          modelo.getPk().setModeloId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(modelo, usuario);
        }
        else {
          resultado = 10003;
        }
      }
      else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "pk.modeloId";
        idFieldValues[0] = modelo.getPk().getModeloId();
        if (persistenceSession.existsObject(modelo, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(modelo, usuario);
        }
      }
      if (transActiva)
      {
        if (resultado == 10000) {
          persistenceSession.commitTransaction();
        } else
          persistenceSession.rollbackTransaction();
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
}
