package com.visiongc.app.strategos.indicadores.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesInsumoService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionInsumo;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesInsumoPersistenceSession;
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

public class StrategosMedicionesInsumoServiceImpl
  extends StrategosServiceImpl
  implements StrategosMedicionesInsumoService
{
	
	private StrategosMedicionesInsumoPersistenceSession persistenceSession = null;
	  
	 
	public StrategosMedicionesInsumoServiceImpl(StrategosMedicionesInsumoPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
	  {
	    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
	    this.persistenceSession = persistenceSession;
	}

	
	public int deleteMedicionInsumo(MedicionInsumo medicion, Usuario usuario) {
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
	      if ( medicion.getIndicadorId() != null) {
	        resultado = this.persistenceSession.deleteMedicionInsumo(medicion);
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

	
	public int saveMedicionInsumo(MedicionInsumo medicion,	Usuario usuario) {
		boolean transActiva = false;
	    int resultado = 10000;
	    String[] fieldNames = new String[9];
	    Object[] fieldValues = new Object[9];
	    try
	    {
	      if (!this.persistenceSession.isTransactionActive())
	      {
	        this.persistenceSession.beginTransaction();
	        transActiva = true;
	      }
	      
	      	fieldNames[0] = "indicadorId";
	        fieldValues[0] = medicion.getIndicadorId();
	        fieldNames[1] = "formulaId";
	        fieldValues[1] = medicion.getFormulaId();
	        fieldNames[2] = "organizacionId";
	        fieldValues[2] = medicion.getOrganizacionId();
	        fieldNames[3] = "periodoInicial";
	        fieldValues[3] = medicion.getPeriodoInicial();
	        fieldNames[4] = "periodoFinal";
	        fieldValues[4] = medicion.getPeriodoFinal();
	        fieldNames[5] = "anoInicial";
	        fieldValues[5] = medicion.getAnoInicial();
	        fieldNames[6] = "anoFinal";
	        fieldValues[6] = medicion.getAnoFinal();
	        fieldNames[7] = "resultado";
	        fieldValues[7] = medicion.getResultado();
	        fieldNames[8] = "inventario";
	        fieldValues[8] = medicion.getInventario();
	        if (!this.persistenceSession.existsObject(medicion, fieldNames, fieldValues)) {
	          resultado = this.persistenceSession.insert(medicion, usuario);
	        } else {
	          resultado = this.persistenceSession.update(medicion, usuario);
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

	
	public List<MedicionInsumo> getMedicionInsumo(Long indicadorId, Long formulaId, Long organizacionId) {
		
		 
	    return this.persistenceSession.getMedicionesInsumo(indicadorId, formulaId, organizacionId);
	  
	}

	
	
	

}
