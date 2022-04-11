package com.visiongc.app.strategos.indicadores.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadorAsignarInventarioService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesInsumoService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadorAsignarInventarioPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesPersistenceSession;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class StrategosIndicadorAsignarInventarioServiceImpl
  extends StrategosServiceImpl
  implements StrategosIndicadorAsignarInventarioService
{
	
	private StrategosIndicadorAsignarInventarioPersistenceSession persistenceSession = null;
	  
	 
	public StrategosIndicadorAsignarInventarioServiceImpl(StrategosIndicadorAsignarInventarioPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
	  {
	    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
	    this.persistenceSession = persistenceSession;
	}

	
	
	public int deleteIndicadorInventario(IndicadorAsignarInventario indicadorInv, Usuario usuario) {
		// TODO Auto-generated method stub
		boolean transActiva = false;
		int resultado = 10000;
		try
		   {
		      if (!this.persistenceSession.isTransactionActive())
		      {
		        this.persistenceSession.beginTransaction();
		        transActiva = true;
		      }
		      if (indicadorInv.getAsignarId() != null) {
		        resultado = this.persistenceSession.deleteIndicadorInventario(indicadorInv);
		      }
		      if (resultado == 10000)
		      {
		        if (transActiva)
		        {
		          this.persistenceSession.commitTransaction();
		          transActiva = false;
		        }
		      }
		      else if (transActiva)
		      {
		        this.persistenceSession.rollbackTransaction();
		        transActiva = false;
		      }
		    }
		    catch (Throwable t)
		    {
		      if (transActiva)
		      {
		        this.persistenceSession.rollbackTransaction();
		        throw new ChainedRuntimeException(t.getMessage(), t);
		      }
		    }
		return resultado;
	}


	
	public int saveIndicadorInventario(IndicadorAsignarInventario indicadorInv, Usuario usuario) {
		// TODO Auto-generated method stub
		boolean transActiva = false;
	    int resultado = 10000;
	    String[] fieldNames = new String[2];
	    Object[] fieldValues = new Object[2];
	    try
	    {
	      if (!this.persistenceSession.isTransactionActive())
	      {
	        this.persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      	if(indicadorInv.getAsignarId() == null || (indicadorInv.getAsignarId().longValue() == 0L)){
	      		fieldNames[0] = "indicadorId";
		        fieldValues[0] = indicadorInv.getIndicadorId();
		        fieldNames[1] = "indicadorInsumoId";
		        fieldValues[1] = indicadorInv.getIndicadorInsumoId();
		        if (persistenceSession.existsObject(indicadorInv, fieldNames, fieldValues)) {
		            resultado = 10003;
		        }else{
		        	indicadorInv.setAsignarId(new Long(persistenceSession.getUniqueId()));	
				    resultado = this.persistenceSession.insert(indicadorInv, usuario);
		        }
		            
		    }else{
	      		fieldNames[0] = "asignarId";
		        fieldValues[0] = indicadorInv.getAsignarId();
		        
		        if (persistenceSession.existsObject(indicadorInv, fieldNames, fieldValues)){
		            resultado = 10003;
		        }
		        else {
		        	resultado = this.persistenceSession.update(indicadorInv, usuario);
		        }
		        
	      	}
	    	
	      if (transActiva)
	      {
	        this.persistenceSession.commitTransaction();
	        transActiva = false;
	      }
	    }
	    catch (Throwable t)
	    {
	      if (transActiva) {
	        this.persistenceSession.rollbackTransaction();
	      }
	      throw new ChainedRuntimeException(t.getMessage(), t);
	    }
	    return resultado;
	}



	public List<IndicadorAsignarInventario> getIndicadorInventario(Long indicadorId) {
		// TODO Auto-generated method stub
		return this.persistenceSession.getIndicadorInventario(indicadorId);
	}
	
	
	
	public IndicadorAsignarInventario getIndicadorInventario(Long indicadorId, Long indicadorInsumoId) {
		// TODO Auto-generated method stub
		IndicadorAsignarInventario indicadorInventario = this.persistenceSession.getIndicadorInventario(indicadorId, indicadorInsumoId);
		
		return indicadorInventario;
	}


	public boolean esInsumoInventario(Long indicadorInsumoId) {
		// TODO Auto-generated method stub
		return this.persistenceSession.getInsumo(indicadorInsumoId);
	}
	
	public IndicadorAsignarInventario getInsumo(Long indicadorInsumoId) {
		// TODO Auto-generated method stub
		IndicadorAsignarInventario indicadorInventario = this.persistenceSession.getIndicadorInsumo(indicadorInsumoId);
		
		return indicadorInventario;
	}

}
