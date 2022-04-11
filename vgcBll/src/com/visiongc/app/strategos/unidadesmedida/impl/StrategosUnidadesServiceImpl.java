package com.visiongc.app.strategos.unidadesmedida.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.unidadesmedida.persistence.StrategosUnidadesPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StrategosUnidadesServiceImpl extends StrategosServiceImpl implements StrategosUnidadesService
{
  private StrategosUnidadesPersistenceSession persistenceSession = null;
  
  public StrategosUnidadesServiceImpl(StrategosUnidadesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getUnidadesMedida(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    Map filtroLocal = new HashMap();
    filtroLocal.put("nombre", "%");
    PaginaLista paginaUnidades = persistenceSession.getUnidadesMedida(0, 0, null, null, true, filtroLocal);
    if ((paginaUnidades.getLista() == null) || (paginaUnidades.getLista().size() < 1)) {
      crearUnidadPorcentaje();
    }
    return persistenceSession.getUnidadesMedida(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public int deleteUnidadMedida(UnidadMedida unidadMedida, Usuario usuario)
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
      
      if (unidadMedida.getUnidadId() != null) {
        resultado = persistenceSession.delete(unidadMedida, usuario);
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
  
  public int saveUnidadMedida(UnidadMedida unidadMedida, Usuario usuario)
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
      
      fieldNames[0] = "nombre";
      fieldValues[0] = unidadMedida.getNombre();
      
      if ((unidadMedida.getUnidadId() == null) || (unidadMedida.getUnidadId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(unidadMedida, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          unidadMedida.setUnidadId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(unidadMedida, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "unidadId";
        idFieldValues[0] = unidadMedida.getUnidadId();
        
        if (persistenceSession.existsObject(unidadMedida, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(unidadMedida, usuario);
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
  
  private UnidadMedida crearUnidadPorcentaje()
  {
    boolean transActiva = false;
    int resultado = 10000;
    UnidadMedida unidad = new UnidadMedida();
    try
    {
      if (!persistenceSession.isTransactionActive())
      {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      unidad.setUnidadId(new Long(0L));
      unidad.setNombre("%");
      unidad.setTipo(new Boolean(false));
      Usuario usuario = new Usuario();
      
      resultado = saveUnidadMedida(unidad, usuario);
      
      if (resultado == 10000) {
        unidad = (UnidadMedida)persistenceSession.load(UnidadMedida.class, unidad.getUnidadId());
      } else {
        unidad = null;
      }
      if (transActiva)
      {
        if ((resultado == 10000) && (unidad != null)) {
          persistenceSession.commitTransaction();
        }
        else {
          persistenceSession.rollbackTransaction();
        }
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        persistenceSession.rollbackTransaction();
      }
      
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return unidad;
  }
  
  public UnidadMedida getUnidadMedidaPorcentaje()
  {
    UnidadMedida porcentaje = null;
    
    List<UnidadMedida> unidades = persistenceSession.getUnidadesMedida(0, 0, "nombre", "asc", false, null).getLista();
    
    for (Iterator<UnidadMedida> iter = unidades.iterator(); iter.hasNext();)
    {
      UnidadMedida unidad = (UnidadMedida)iter.next();
      if (unidad.getNombre().equalsIgnoreCase("%"))
      {
        porcentaje = unidad;
        break;
      }
    }
    
    if (porcentaje == null) {
      porcentaje = crearUnidadPorcentaje();
    }
    return porcentaje;
  }
}
