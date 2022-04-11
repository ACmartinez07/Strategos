package com.visiongc.framework.auditoria.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.impl.VgcServiceFactory;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.AuditoriasService;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

import java.util.List;
import java.util.Map;

public class AuditoriasServiceImpl extends VgcAbstractService
    implements AuditoriasService
{

	
	public AuditoriasServiceImpl(AuditoriasPersistenceSession persistenceSession,
			boolean persistenceOwned, VgcServiceFactory serviceFactory,	VgcMessageResources messageResources) {
		super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
		this.persistenceSession = persistenceSession;
		// TODO Auto-generated constructor stub
	}

    private AuditoriasPersistenceSession persistenceSession = null;

	@Override
	public PaginaLista getAuditoria(int pagina, int tamanoPagina,
			String orden[], String tipoOrden[], boolean getTotal,
			Map filtros) {
		// TODO Auto-generated method stub
			 
		return persistenceSession.getAuditoria(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros); 
		
	}

	@Override
	public int deleteAuditoria(
			Auditoria auditoria, Usuario usuario) {
		// TODO Auto-generated method stub
		
		
		 
		boolean transActiva = false;
	    int resultado = 10000;
	    try
	    {
	      if (!persistenceSession.isTransactionActive()) {
	        persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      if (auditoria.getAuditoriaId() != null)
	      {
	        resultado = persistenceSession.delete(auditoria, usuario);
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

	@Override
	public int saveAuditoria(Auditoria auditoria,
			Usuario usuario) {
		// TODO Auto-generated method stub
		
				 
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
	      
	      if(auditoria.getAuditoriaId() == null){
	    	  auditoria.setAuditoriaId(new Long(persistenceSession.getUniqueId()));
	    	  resultado = persistenceSession.insert(auditoria, usuario);
	      }else{
	    	  
	    	  fieldNames[0] = "auditoriaId";
		      fieldValues[0] = auditoria.getAuditoriaId();
	    	  
	    	  if(persistenceSession.existsObject(auditoria, fieldNames, fieldValues)){
	    		  resultado = 10003;
	    	  }else{
	    		  resultado = persistenceSession.update(auditoria, usuario);
	    	  }
	     }
	      
	      if (transActiva) {
	        persistenceSession.commitTransaction();
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

	@Override
	public List<Auditoria> getAuditorias(String orden[], String tipoOrden[], boolean getTotal,
			Map filtros) {
		// TODO Auto-generated method stub
		return persistenceSession.getAuditorias(orden, tipoOrden, getTotal, filtros);
	}
}
