package com.visiongc.app.strategos.iniciativas.impl;

import java.util.Map;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.categoriasmedicion.persistence.StrategosCategoriasPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosTipoProyectoPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosTipoProyectoServiceImpl extends StrategosServiceImpl implements StrategosTipoProyectoService{
	private StrategosTipoProyectoPersistenceSession persistenceSession = null;
	  
	  public StrategosTipoProyectoServiceImpl(StrategosTipoProyectoPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
	    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
	    this.persistenceSession = persistenceSession;
	  }
	  
	  public PaginaLista getTiposProyecto(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros)
	  {
	    return persistenceSession.getTiposProyecto(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
	  }
	  
	  public int deleteTipoProyecto(TipoProyecto tipoProyecto, Usuario usuario)
	  {
	    boolean transActiva = false;
	    int resultado = 10000;
	    try
	    {
	      if (!persistenceSession.isTransactionActive()) {
	        persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      if (tipoProyecto.getTipoProyectoId() != null)
	      {
	        resultado = persistenceSession.delete(tipoProyecto, usuario);
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
	  
	  public int saveTipoProyecto(TipoProyecto tipoProyecto, Usuario usuario)
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
	      fieldValues[0] = tipoProyecto.getNombre();
	      
	      if ((tipoProyecto.getTipoProyectoId() == null) || (tipoProyecto.getTipoProyectoId().longValue() == 0L)) {
	        if (persistenceSession.existsObject(tipoProyecto, fieldNames, fieldValues)) {
	          resultado = 10003;
	        }
	        else {
	          tipoProyecto.setTipoProyectoId(new Long(persistenceSession.getUniqueId()));
	          resultado = persistenceSession.insert(tipoProyecto, usuario);
	        }
	      } else {
	        String[] idFieldNames = new String[1];
	        Object[] idFieldValues = new Object[1];
	        
	        idFieldNames[0] = "tipoProyectoId";
	        idFieldValues[0] = tipoProyecto.getTipoProyectoId();
	        if (persistenceSession.existsObject(tipoProyecto, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
	          resultado = 10003;
	        } else {
	          resultado = persistenceSession.update(tipoProyecto, usuario);
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
