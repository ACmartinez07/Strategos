package com.visiongc.app.strategos.categoriasmedicion.impl;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.categoriasmedicion.persistence.StrategosCategoriasPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public class StrategosCategoriasServiceImpl
  extends StrategosServiceImpl implements StrategosCategoriasService
{
  private StrategosCategoriasPersistenceSession persistenceSession = null;
  
  public StrategosCategoriasServiceImpl(StrategosCategoriasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getCategoriasMedicion(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getCategoriasMedicion(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteCategoriaMedicion(CategoriaMedicion categoriaMedicion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (categoriaMedicion.getCategoriaId() != null)
      {
        resultado = persistenceSession.delete(categoriaMedicion, usuario);
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
  
  public int saveCategoriaMedicion(CategoriaMedicion categoriaMedicion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      fieldNames[0] = "nombre";
      fieldValues[0] = categoriaMedicion.getNombre();
      
      if ((categoriaMedicion.getCategoriaId() == null) || (categoriaMedicion.getCategoriaId().longValue() == 0L)) {
        if (persistenceSession.existsObject(categoriaMedicion, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          categoriaMedicion.setCategoriaId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(categoriaMedicion, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "categoriaId";
        idFieldValues[0] = categoriaMedicion.getCategoriaId();
        if (persistenceSession.existsObject(categoriaMedicion, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(categoriaMedicion, usuario);
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
}
