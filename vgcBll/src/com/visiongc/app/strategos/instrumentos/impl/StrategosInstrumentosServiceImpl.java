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
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativaPK;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosInstrumentosPersistenceSession;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativaPK;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosInstrumentosServiceImpl extends StrategosServiceImpl implements StrategosInstrumentosService{
	
	private StrategosInstrumentosPersistenceSession persistenceSession = null;
	  
	  public StrategosInstrumentosServiceImpl(StrategosInstrumentosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
	    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
	    this.persistenceSession = persistenceSession;
	  }

	
	public PaginaLista getInstrumentos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros) {
		
		return this.persistenceSession.getInstrumentos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
	
	}

	
	public int deleteInstrumentos(Instrumentos instrumento, Usuario usuario) {
		
		 boolean transActiva = false;
		    int resultado = 10000;
		    try
		    {
		      if (!persistenceSession.isTransactionActive()) {
		        persistenceSession.beginTransaction();
		        transActiva = true;
		      }
		      
		      if (instrumento.getInstrumentoId() != null)
		      {
		        resultado = persistenceSession.delete(instrumento, usuario);
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

	
	public int saveInstrumentos(Instrumentos instrumento, Usuario usuario) {
		
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
	      
	      fieldNames[0] = "nombreCorto";
	      fieldValues[0] = instrumento.getNombreCorto();
	      
	      if ((instrumento.getInstrumentoId() == null) || (instrumento.getInstrumentoId().longValue() == 0L)) {
	        if (persistenceSession.existsObject(instrumento, fieldNames, fieldValues)) {
	          resultado = 10003;
	        }
	        else {
	          instrumento.setInstrumentoId(new Long(persistenceSession.getUniqueId()));
	          resultado = persistenceSession.insert(instrumento, usuario);
	        }
	      } else {
	        String[] idFieldNames = new String[1];
	        Object[] idFieldValues = new Object[1];
	        
	        idFieldNames[0] = "instrumentoId";
	        idFieldValues[0] = instrumento.getInstrumentoId();
	        if (persistenceSession.existsObject(instrumento, fieldNames, fieldValues, idFieldNames, idFieldValues)) {
	          resultado = 10003;
	        } else {
	          resultado = persistenceSession.update(instrumento, usuario);
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
	  
	public int asociarInstrumento(Long instrumentoId, Long iniciativaId, Usuario usuario)
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
	      
	      InstrumentoIniciativa instrumentoIniciativa = new InstrumentoIniciativa();
	      
	      instrumentoIniciativa.setPk(new InstrumentoIniciativaPK());
	      instrumentoIniciativa.getPk().setIniciativaId(iniciativaId);
	      instrumentoIniciativa.getPk().setInstrumentoId(instrumentoId);
	      
	      fieldNames[0] = "pk.iniciativaId";
	      fieldValues[0] = instrumentoIniciativa.getPk().getIniciativaId();
	      fieldNames[1] = "pk.instrumentoId";
	      fieldValues[1] = instrumentoIniciativa.getPk().getInstrumentoId();
	      
	      if (!persistenceSession.existsObject(instrumentoIniciativa, fieldNames, fieldValues)) {
	        resultado = persistenceSession.insert(instrumentoIniciativa, usuario);
	      }
	      if (transActiva)
	      {
	        persistenceSession.commitTransaction();
	        transActiva = false;
	      }
	    }
	    catch (Throwable t)
	    {
	      if (transActiva)
	        persistenceSession.rollbackTransaction();
	      throw new ChainedRuntimeException(t.getMessage(), t);
	    }
	    
	    return resultado;
	  }
	  
	  public int desasociarInstrumento(Long instrumentoId, Long iniciativaId, Usuario usuario)
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
	      
	      InstrumentoIniciativa instrumentoIniciativa = new InstrumentoIniciativa();
	      
	      instrumentoIniciativa.setPk(new InstrumentoIniciativaPK());
	      instrumentoIniciativa.getPk().setIniciativaId(iniciativaId);
	      instrumentoIniciativa.getPk().setInstrumentoId(instrumentoId);
	      
	      resultado = persistenceSession.delete(instrumentoIniciativa, usuario);
	      
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
