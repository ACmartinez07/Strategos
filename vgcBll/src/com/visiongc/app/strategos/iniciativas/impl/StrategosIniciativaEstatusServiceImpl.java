package com.visiongc.app.strategos.iniciativas.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativaEstatusPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;







public class StrategosIniciativaEstatusServiceImpl
  extends StrategosServiceImpl
  implements StrategosIniciativaEstatusService
{
  private StrategosIniciativaEstatusPersistenceSession persistenceSession = null;
  
  public StrategosIniciativaEstatusServiceImpl(StrategosIniciativaEstatusPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int save(IniciativaEstatus iniciativaEstatus, Usuario usuario)
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
      fieldValues[0] = iniciativaEstatus.getNombre();
      
      if ((iniciativaEstatus.getId() == null) || (iniciativaEstatus.getId().longValue() == 0L))
      {
        if (persistenceSession.existsObject(iniciativaEstatus, fieldNames, fieldValues)) {
          resultado = 10003;
        }
        else {
          iniciativaEstatus.setId(new Long(persistenceSession.getUniqueId()));
          resultado = persistenceSession.insert(iniciativaEstatus, usuario);
        }
      }
      else
      {
        String[] idFieldNames = new String[1];
        Object[] idFieldValues = new Object[1];
        
        idFieldNames[0] = "id";
        idFieldValues[0] = iniciativaEstatus.getId();
        if (persistenceSession.existsObject(iniciativaEstatus, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
          resultado = 10003;
        } else {
          resultado = persistenceSession.update(iniciativaEstatus, usuario);
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
  
  public int delete(IniciativaEstatus iniciativaEstatus, Usuario usuario)
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
      
      if (iniciativaEstatus.getId() != null)
      {
        if (!persistenceSession.buscarReferenciasRelacionales(iniciativaEstatus.getId())) {
          resultado = persistenceSession.delete(iniciativaEstatus, usuario);
        } else {
          resultado = 10004;
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
  
  public PaginaLista getIniciativaEstatus(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, String> filtros)
  {
    return persistenceSession.getIniciativaEstatus(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
  }
  
  public Long calcularEstatus(Double porcentaje)
  {
    IniciativaEstatus estatus = null;
    
    if (porcentaje != null)
    {
      Map<String, String> filtros = new HashMap();
      PaginaLista paginaIniciativaEstatus = getIniciativaEstatus(0, 0, "id", "asc", true, filtros);
      
      for (Iterator<IniciativaEstatus> iter = paginaIniciativaEstatus.getLista().iterator(); iter.hasNext();)
      {
	        IniciativaEstatus iniciativaEstatus = (IniciativaEstatus)iter.next();
	        
	        if(porcentaje >100 && iniciativaEstatus.getId() == 5){
	        	estatus = iniciativaEstatus;
	        }else{
	        
		        if (iniciativaEstatus.getPorcentajeInicial() != null)
		        {
		          if ((porcentaje.doubleValue() >= iniciativaEstatus.getPorcentajeInicial().doubleValue()) && (porcentaje.doubleValue() <= iniciativaEstatus.getPorcentajeFinal().doubleValue()))
		          {
		            estatus = iniciativaEstatus;
		            break;
		          }
		        }
	      }
      }
    }
    if (estatus != null) {
      return estatus.getId();
    }
    return IniciativaEstatus.EstatusType.getEstatusSinIniciar();

 }
}
