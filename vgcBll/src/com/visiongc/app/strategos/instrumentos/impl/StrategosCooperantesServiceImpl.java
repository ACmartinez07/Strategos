package com.visiongc.app.strategos.instrumentos.impl;

import java.util.Map;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.categoriasmedicion.persistence.StrategosCategoriasPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosTipoProyectoPersistenceSession;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosCooperantesServiceImpl extends StrategosServiceImpl implements StrategosCooperantesService{
	
	private StrategosCooperantesPersistenceSession persistenceSession = null;
	  
	  public StrategosCooperantesServiceImpl(StrategosCooperantesPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
	    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
	    this.persistenceSession = persistenceSession;
	  }

	
	public PaginaLista getCooperantes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros) {
		
		return this.persistenceSession.getCooperantes(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
	
	}

	
	public int deleteCooperantes(Cooperante cooperante, Usuario usuario) {
		
		 boolean transActiva = false;
		    int resultado = 10000;
		    try
		    {
		      if (!persistenceSession.isTransactionActive()) {
		        persistenceSession.beginTransaction();
		        transActiva = true;
		      }
		      
		      if (cooperante.getCooperanteId() != null)
		      {
		        resultado = persistenceSession.delete(cooperante, usuario);
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

	
	public int saveCooperantes(Cooperante cooperante, Usuario usuario) {
		
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
	      fieldValues[0] = cooperante.getNombre();
	      
	      if ((cooperante.getCooperanteId() == null) || (cooperante.getCooperanteId().longValue() == 0L)) {
	        if (persistenceSession.existsObject(cooperante, fieldNames, fieldValues)) {
	          resultado = 10003;
	        }
	        else {
	          cooperante.setCooperanteId(new Long(persistenceSession.getUniqueId()));
	          resultado = persistenceSession.insert(cooperante, usuario);
	        }
	      } else {
	        String[] idFieldNames = new String[1];
	        Object[] idFieldValues = new Object[1];
	        
	        idFieldNames[0] = "cooperanteId";
	        idFieldValues[0] = cooperante.getCooperanteId();
	        if (persistenceSession.existsObject(cooperante, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
	          resultado = 10003;
	        } else {
	          resultado = persistenceSession.update(cooperante, usuario);
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
