package com.visiongc.framework.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.impl.VgcServiceFactory;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.ReporteServicioService;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.AuditoriasService;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.persistence.ReporteServicioPersistenceSession;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReporteServicioServiceImpl extends VgcAbstractService
    implements ReporteServicioService
{

	
	public ReporteServicioServiceImpl(ReporteServicioPersistenceSession persistenceSession,
			boolean persistenceOwned, VgcServiceFactory serviceFactory,	VgcMessageResources messageResources) {
		super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
		this.persistenceSession = persistenceSession;
		// TODO Auto-generated constructor stub
	}

    private ReporteServicioPersistenceSession persistenceSession = null;

	

	public List<ReporteServicio> getReporte(Long responsableId) {
		
		return persistenceSession.getReporte(responsableId);
	}

	@Override
	public int deleteReporte(ReporteServicio reporteServicio, Usuario usuario) {
		boolean transActiva = false;
	    int resultado = 10000;
	    try
	    {
	      if (!persistenceSession.isTransactionActive()) {
	        persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      if (reporteServicio.getReporteId() != null)
	      {
	        resultado = persistenceSession.delete(reporteServicio, usuario);
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
	public int saveReporte(ReporteServicio reporteServicio, Usuario usuario) {
		
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
	      
	      if(reporteServicio.getReporteId() == null){
	    	  reporteServicio.setReporteId(new Long(persistenceSession.getUniqueId()));
	    	  resultado = persistenceSession.insert(reporteServicio, usuario);
	      }else{
	    	  
	    	  fieldNames[0] = "reporteId";
		      fieldValues[0] = reporteServicio.getReporteId();
	    	  
	    	  if(persistenceSession.existsObject(reporteServicio, fieldNames, fieldValues)){
	    		  resultado = 10003;
	    	  }else{
	    		  resultado = persistenceSession.update(reporteServicio, usuario);
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
	public void deleteReportes() {
		
		persistenceSession.deleteReportes();
		
	}
}
