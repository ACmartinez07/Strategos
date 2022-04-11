package com.visiongc.framework.auditoria.impl;


import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.impl.VgcServiceFactory;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

import java.util.List;
import java.util.Map;

public class AuditoriaMedicionServiceImpl extends VgcAbstractService
    implements AuditoriaMedicionService
{

	
	public AuditoriaMedicionServiceImpl(AuditoriaMedicionPersistenceSession persistenceSession,
			boolean persistenceOwned, VgcServiceFactory serviceFactory,	VgcMessageResources messageResources) {
		super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
		this.persistenceSession = persistenceSession;
		// TODO Auto-generated constructor stub
	}

    private AuditoriaMedicionPersistenceSession persistenceSession = null;

	@Override
	public PaginaLista getAuditoriaMedicion(int pagina, int tamanoPagina,
			String orden[], String tipoOrden[], boolean getTotal,
			Map filtros) {
		// TODO Auto-generated method stub
			 
		return persistenceSession.getAuditoriaMedicion(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros); 
		
	}

	@Override
	public int deleteAuditoriaMedicion(
			AuditoriaMedicion auditoriaMedicion, Usuario usuario) {
		// TODO Auto-generated method stub
		
		
		 
		boolean transActiva = false;
	    int resultado = 10000;
	    try
	    {
	      if (!persistenceSession.isTransactionActive()) {
	        persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      if (auditoriaMedicion.getAuditoriaMedicionId() != null)
	      {
	        resultado = persistenceSession.delete(auditoriaMedicion, usuario);
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
	public int saveAuditoriaMedicion(AuditoriaMedicion auditoriaMedicion,
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
	      
	      if(auditoriaMedicion.getAuditoriaMedicionId() == null){
	    	  auditoriaMedicion.setAuditoriaMedicionId(new Long(persistenceSession.getUniqueId()));
	    	  resultado = persistenceSession.insert(auditoriaMedicion, usuario);
	      }else{
	    	  
	    	  fieldNames[0] = "auditoriaMedicionId";
		      fieldValues[0] = auditoriaMedicion.getAuditoriaMedicionId();
	    	  
	    	  if(persistenceSession.existsObject(auditoriaMedicion, fieldNames, fieldValues)){
	    		  resultado = 10003;
	    	  }else{
	    		  resultado = persistenceSession.update(auditoriaMedicion, usuario);
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
	public List<AuditoriaMedicion> getAuditoriasMedicion(String orden[], String tipoOrden[], boolean getTotal,
			Map filtros) {
		// TODO Auto-generated method stub
		return persistenceSession.getAuditoriasMedicion(orden, tipoOrden, getTotal, filtros);
	}
}