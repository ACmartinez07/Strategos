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
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosTiposConvenioPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosTiposConvenioServiceImpl extends StrategosServiceImpl implements StrategosTiposConvenioService{
	private StrategosTiposConvenioPersistenceSession persistenceSession = null;
	  
	  public StrategosTiposConvenioServiceImpl(StrategosTiposConvenioPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
	    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
	    this.persistenceSession = persistenceSession;
	  }

	
	public PaginaLista getTiposConvenio(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros) {
		
		return this.persistenceSession.getTiposConvenio(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
		
	}


	public int deleteTiposConvenio(TipoConvenio tiposConvenio, Usuario usuario) {
		
		boolean transActiva = false;
	    int resultado = 10000;
	    try
	    {
	      if (!persistenceSession.isTransactionActive()) {
	        persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      if (tiposConvenio.getTiposConvenioId() != null)
	      {
	        resultado = persistenceSession.delete(tiposConvenio, usuario);
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


	public int saveTiposConvenio(TipoConvenio tiposConvenio, Usuario usuario) {
		
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
	      fieldValues[0] = tiposConvenio.getNombre();
	      
	      if ((tiposConvenio.getTiposConvenioId() == null) || (tiposConvenio.getTiposConvenioId().longValue() == 0L)) {
	        if (persistenceSession.existsObject(tiposConvenio, fieldNames, fieldValues)) {
	          resultado = 10003;
	        }
	        else {
	        	tiposConvenio.setTiposConvenioId(new Long(persistenceSession.getUniqueId()));
	          resultado = persistenceSession.insert(tiposConvenio, usuario);
	        }
	      } else {
	        String[] idFieldNames = new String[1];
	        Object[] idFieldValues = new Object[1];
	        
	        idFieldNames[0] = "tiposConvenioId";
	        idFieldValues[0] = tiposConvenio.getTiposConvenioId();
	        if (persistenceSession.existsObject(tiposConvenio, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
	          resultado = 10003;
	        } else {
	          resultado = persistenceSession.update(tiposConvenio, usuario);
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
