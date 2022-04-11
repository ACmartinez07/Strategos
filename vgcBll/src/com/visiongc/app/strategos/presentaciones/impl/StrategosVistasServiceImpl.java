package com.visiongc.app.strategos.presentaciones.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosVistasPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StrategosVistasServiceImpl
  extends StrategosServiceImpl
  implements StrategosVistasService
{
  private StrategosVistasPersistenceSession persistenceSession = null;
  
  public StrategosVistasServiceImpl(StrategosVistasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public PaginaLista getVistas(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
  {
    return persistenceSession.getVistas(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  private int deleteDependencias(Long vistaId, Usuario usuario)
  {
    int resultado = 10000;
    
    StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
    
    Map filtros = new HashMap();
    filtros.put("vistaId", vistaId.toString());
    
    PaginaLista paginaPaginas = strategosPaginasService.getPaginas(1, 30, "numero", "ASC", true, filtros);
    
    for (Iterator<Pagina> i = paginaPaginas.getLista().iterator(); i.hasNext();)
    {
      Pagina pagina = (Pagina)i.next();
      resultado = strategosPaginasService.deletePagina(pagina, usuario);
      if (resultado != 10000) {
        break;
      }
    }
    strategosPaginasService.close();
    
    return resultado;
  }
  
  public int deleteVista(Vista vista, Usuario usuario)
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
      
      if (vista.getVistaId() != null)
      {
        resultado = deleteDependencias(vista.getVistaId(), usuario);
        if (resultado == 10000) {
          resultado = persistenceSession.delete(vista, usuario);
        }
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
  
  public int saveVista(Vista vista, Usuario usuario)
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
      fieldValues[0] = vista.getNombre();
      fieldNames[1] = "organizacionId";
      fieldValues[1] = vista.getOrganizacionId();
      
      if ((vista.getVistaId() == null) || 
        (vista.getVistaId().longValue() == 0L)) {
        if (persistenceSession.existsObject(vista, fieldNames, 
          fieldValues)) {
          resultado = 10003;
        }
        else
        {
          vista.setVistaId(new Long(persistenceSession
            .getUniqueId()));
          resultado = persistenceSession.insert(vista, usuario);
        }
      } else {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "vistaId";
        idFieldValues[0] = vista.getVistaId();
        
        if (persistenceSession.existsObject(vista, fieldNames, 
          fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(vista, usuario);
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
