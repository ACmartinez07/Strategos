package com.visiongc.app.strategos.web.struts.planes.modelos.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarModeloForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	private Long modeloId;
	private Long planId;
	private String nombre;
	private String descripcion;
	private String datosArbolPlan;
	private String binario;
	private String editar;
	private String previsualizar;
	private Byte source;

	public String getEditar()
	{
		return this.editar;
	}
	
	public void setEditar(String editar) 
	{
		this.editar = editar;
	}
	
	public String getBinario() 
	{
		return this.binario;
	}

	public void setBinario(String binario) 
	{
		this.binario = binario;
	}

	public String getDatosArbolPlan() 
	{
		return this.datosArbolPlan;
	}
	
	public void setDatosArbolPlan(String datosArbolPlan) 
	{
		this.datosArbolPlan = datosArbolPlan;
	}

	public String getDescripcion() 
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public Long getModeloId() 
	{
		return this.modeloId;
	}

	public void setModeloId(Long modeloId) 
	{
		this.modeloId = modeloId;
	}

	public String getPrevisualizar() 
	{
		return this.previsualizar;
	}

	public void setPrevisualizar(String previsualizar) 
	{
		this.previsualizar = previsualizar;
	}
	
 	public Byte getSource()
  	{
  		return this.source;
  	}

  	public void setSource(Byte source) 
  	{
  		this.source = SourceType.getTypeSource(source);
  	}

	public void clear() 
	{
		this.planId = new Long(0L);
		this.descripcion = null;
		this.datosArbolPlan = null;
		this.modeloId = new Long(0L);

		setBloqueado(new Boolean(false));
	}
	
	public static class SourceType
	{
		private static final byte SOURCE_GESTIONAR = 1;
		private static final byte SOURCE_PLAN = 2;

		private static Byte getTypeSource(Byte source)
		{
			if (source == SOURCE_GESTIONAR)
				return new Byte(SOURCE_GESTIONAR);
			else if (source == SOURCE_PLAN)
				return new Byte(SOURCE_PLAN);
			else
				return null;
		}
		
		public static byte getSourceGestionar() 
		{
			return SOURCE_GESTIONAR;
		}

		public static byte getSourcePlan() 
		{
			return SOURCE_PLAN;
		}
	}
}