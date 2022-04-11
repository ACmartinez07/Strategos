/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.forms;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Kerwin
 *
 */
public class IndicadorConsolidadoForm extends ValidatorActionForm
{
	static final long serialVersionUID = 0L;

	private Long organizacionId;
	private Long claseConsolidacionId;
	private String claseConsolidacionNombre;
	private String insumosClases;
	private Boolean logIndicadores = false;
	private Boolean logErrores = false;
	private String respuesta;
	private Byte status = 0;
	  
	public Long getOrganizacionId()
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
	    this.organizacionId = organizacionId;
	}

	public Long getClaseConsolidacionId()
	{
	    return this.claseConsolidacionId;
	}

	public void setClaseConsolidacionId(Long claseConsolidacionId) 
	{
	    this.claseConsolidacionId = claseConsolidacionId;
	}

	public String getClaseConsolidacionNombre()
	{
	    return this.claseConsolidacionNombre;
	}
	  
	public void setClaseConsolidacionNombre(String claseConsolidacionNombre) 
	{
	    this.claseConsolidacionNombre = claseConsolidacionNombre;
	}
	  
	public String getInsumosClases() 
	{
		return this.insumosClases;
	}

	public void setInsumosClases(String insumosClases) 
	{
		this.insumosClases = insumosClases;
	}
	  
	public Boolean getLogIndicadores()
	{
		return this.logIndicadores;
	}

	public void setLogIndicadores(Boolean logIndicadores) 
	{
		this.logIndicadores = logIndicadores;
	}
		
	public Boolean getLogErrores()
	{
		return this.logErrores;
	}

	public void setLogErrores(Boolean logErrores) 
	{
		this.logErrores = logErrores;
	}

	public String getRespuesta()
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}

	public Byte getStatus()
	{
		return this.status;
	}

	public void setStatus(Byte status) 
	{
		this.status = ConsolidarStatus.getConsolidarStatus(status);
	}
	
	public String getSeparadorClases() 
	{
		return new com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.SeleccionarMultiplesClasesForm().getSeparadorClases();
	}

	public String getSeparadorCampos() 
	{
		return new com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.SeleccionarMultiplesClasesForm().getSeparadorCampos();
	}
	  
	public void clear() 
	{
		this.organizacionId = new Long(0L);
		this.claseConsolidacionId = new Long(0L);
		this.claseConsolidacionNombre = null;
		this.insumosClases = null;
		this.logErrores = false;
		this.logIndicadores = false;
		this.respuesta = null;
	}
	  
	public static class ConsolidarStatus
	{
		private static final byte CONSOLIDARSTATUS_LOAD = 0;
		private static final byte CONSOLIDARSTATUS_SUCCESS = 1;
		private static final byte CONSOLIDARSTATUS_NOSUCCESS = 2;
		private static final byte CONSOLIDARSTATUS_REPORTE = 3;
			
		private static Byte getConsolidarStatus(Byte status)
		{
			if (status == CONSOLIDARSTATUS_LOAD)
				return new Byte(CONSOLIDARSTATUS_LOAD);
			else if (status == CONSOLIDARSTATUS_SUCCESS)
				return new Byte(CONSOLIDARSTATUS_SUCCESS);
			else if (status == CONSOLIDARSTATUS_NOSUCCESS)
				return new Byte(CONSOLIDARSTATUS_NOSUCCESS);
			else if (status == CONSOLIDARSTATUS_REPORTE)
				return new Byte(CONSOLIDARSTATUS_REPORTE);
			else
				return null;
		}
			
		public static Byte getConsolidarStatusLoad() 
		{
			return new Byte(CONSOLIDARSTATUS_LOAD);
		}

		public static Byte getConsolidarStatusSuccess() 
		{
			return new Byte(CONSOLIDARSTATUS_SUCCESS);
		}
			
		public static Byte getConsolidarStatusNoSuccess() 
		{
			return new Byte(CONSOLIDARSTATUS_NOSUCCESS);
		}

		public static Byte getConsolidarStatusReporte() 
		{
			return new Byte(CONSOLIDARSTATUS_REPORTE);
		}
	}
}
