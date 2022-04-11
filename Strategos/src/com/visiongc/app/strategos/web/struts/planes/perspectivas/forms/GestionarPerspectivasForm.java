package com.visiongc.app.strategos.web.struts.planes.perspectivas.forms;

import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.framework.web.struts.forms.VisorArbolForm;
import java.util.List;

public class GestionarPerspectivasForm extends VisorArbolForm
{
	static final long serialVersionUID = 0L;
	
	private List nivelesPlantillaPlan;
	private Integer nivelSeleccionadoArbol;
	private ElementoPlantillaPlanes elementoPlantillaPlanes;
	private Boolean verIndicadoresLogroPlan;
	private Long seleccionadoId;
	private String respuesta;

	public List getNivelesPlantillaPlan()
	{
		return this.nivelesPlantillaPlan;
	}

	public void setNivelesPlantillaPlan(List nivelesPlantillaPlan) 
	{
		this.nivelesPlantillaPlan = nivelesPlantillaPlan;
	}

	public Integer getNivelSeleccionadoArbol() 
	{
		return this.nivelSeleccionadoArbol;
	}

	public void setNivelSeleccionadoArbol(Integer nivelSeleccionadoArbol) 
	{
		this.nivelSeleccionadoArbol = nivelSeleccionadoArbol;
	}

	public ElementoPlantillaPlanes getElementoPlantillaPlanes() 
	{
		return this.elementoPlantillaPlanes;
	}

	public void setElementoPlantillaPlanes(ElementoPlantillaPlanes elementoPlantillaPlanes) 
	{
		this.elementoPlantillaPlanes = elementoPlantillaPlanes;
	}

	public boolean getVerIndicadoresLogroPlan() 
	{
		return this.verIndicadoresLogroPlan.booleanValue();
	}

	public void setVerIndicadoresLogroPlan(Boolean verIndicadoresLogroPlan) 
	{
		this.verIndicadoresLogroPlan = verIndicadoresLogroPlan;
	}

	public Byte getTipoCalculoPerspectivaAutomatico() 
	{
		return TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico();
	}

	public Byte getTipoCalculoPerspectivaManual() 
	{
		return TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual();
	}
	
	public Long getSeleccionadoId() 
	{
		return this.seleccionadoId;
	}

	public void setSeleccionadoId(Long seleccionadoId) 
	{
		this.seleccionadoId = seleccionadoId;
	}
	
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}	
}