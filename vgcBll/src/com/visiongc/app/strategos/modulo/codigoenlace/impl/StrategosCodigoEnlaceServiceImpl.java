package com.visiongc.app.strategos.modulo.codigoenlace.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.app.strategos.modulo.codigoenlace.persistence.StrategosCodigoEnlacePersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Map;








public class StrategosCodigoEnlaceServiceImpl
  extends StrategosServiceImpl
  implements StrategosCodigoEnlaceService
{
  private StrategosCodigoEnlacePersistenceSession persistenceSession = null;
  
  public StrategosCodigoEnlaceServiceImpl(StrategosCodigoEnlacePersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getCodigoEnlace(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    return persistenceSession.getCodigoEnlace(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int saveCodigoEnlace(CodigoEnlace codigoEnlace, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "codigo";
      fieldValues[0] = codigoEnlace.getCodigo();
      
      if ((codigoEnlace.getId() == null) || (codigoEnlace.getId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(codigoEnlace, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          fieldNames[0] = "nombre";
          fieldValues[0] = codigoEnlace.getNombre();
          if (persistenceSession.existsObject(codigoEnlace, fieldNames, fieldValues)) {
            resultado = 10003;
          }
          else {
            codigoEnlace.setId(new Long(persistenceSession.getUniqueId()));
            resultado = persistenceSession.insert(codigoEnlace, usuario);
          }
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "id";
        idFieldValues[0] = codigoEnlace.getId();
        if (persistenceSession.existsObject(codigoEnlace, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        }
        else {
          fieldNames[0] = "nombre";
          fieldValues[0] = codigoEnlace.getNombre();
          if (persistenceSession.existsObject(codigoEnlace, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
            resultado = 10003;
          }
          else {
            CodigoEnlace codigoEnlaceOriginal = (CodigoEnlace)persistenceSession.load(CodigoEnlace.class, new Long(codigoEnlace.getId().longValue()));
            if (!codigoEnlaceOriginal.getCodigo().equals(codigoEnlace.getCodigo()))
              persistenceSession.updateReferencia(codigoEnlaceOriginal.getCodigo(), codigoEnlace.getCodigo());
            resultado = persistenceSession.update(codigoEnlace, usuario);
          }
        }
      }
      
      if (transActiva)
      {
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
  
  public int deleteCodigoEnlace(CodigoEnlace codigoEnlace, Usuario usuario)
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
      
      if (codigoEnlace.getId() != null)
      {
        persistenceSession.clearReferencia(codigoEnlace.getCodigo());
        resultado = persistenceSession.delete(codigoEnlace, usuario);
      }
      
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
}
