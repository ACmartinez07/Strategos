package com.visiongc.app.strategos.web.struts.organizaciones.forms;

import com.visiongc.framework.web.struts.forms.VisorArbolForm;

public class GestionarOrganizacionesForm extends VisorArbolForm
{
	static final long serialVersionUID = 0L;
	private Boolean mostrarMisionVision;
	private String vision;
	private String mision;
	private String alerta;
	private String descripcion;
	private String lineamientosEstrategicos;
	private Long graficoSeleccionadoId;
	private String respuesta;

	public Boolean getMostrarMisionVision()
	{
		return this.mostrarMisionVision;
	}

	public void setMostrarMisionVision(Boolean mostrarMisionVision) 
	{
		this.mostrarMisionVision = mostrarMisionVision;
	}

	public String getVision() 
	{
		return this.vision;
	}

	public void setVision(String vision) 
	{
		this.vision = vision;
	}

	public String getMision() 
	{
		return this.mision;
	}

	public void setMision(String mision) 
	{
		this.mision = mision;
	}

	public String getAlerta() 
	{
		return this.alerta;
	}

	public void setAlerta(String alerta) 
	{
		this.alerta = alerta;
	}
	
	public String getDescripcion() 
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public String getLineamientosEstrategicos() 
	{
		return this.lineamientosEstrategicos;
	}

	public void setLineamientosEstrategicos(String lineamientosEstrategicos) 
	{
		this.lineamientosEstrategicos = lineamientosEstrategicos;
	}
  
	public Long getGraficoSeleccionadoId()
	{
		return this.graficoSeleccionadoId;
	}

	public void setGraficoSeleccionadoId(Long graficoSeleccionadoId) 
	{
		this.graficoSeleccionadoId = graficoSeleccionadoId;
	}
  
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public void clear() 
	{
		this.mostrarMisionVision = new Boolean(false);
	}
}