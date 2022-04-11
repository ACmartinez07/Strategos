package com.visiongc.app.strategos.web.struts.planes.forms;

import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import org.apache.struts.action.ActionForm;

public class GestionarPlanForm extends ActionForm
{
	static final long serialVersionUID = 0L;
  
	private Long organizacionId;
	private Long planId;
	private Long perspectivaId;
	private String nombrePlan;
	private Long claseId;
	private PlantillaPlanes plantillaPlanes;
	private String anchoPorDefecto;
	private String altoPorDefecto;

	public Long getOrganizacionId()
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public Long getPerspectivaId() 
	{
		return this.perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) 
	{
		this.perspectivaId = perspectivaId;
	}

	public String getNombrePlan() 
	{
		return this.nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) 
	{
		this.nombrePlan = nombrePlan;
	}

	public Long getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}

	public PlantillaPlanes getPlantillaPlanes() 
	{
		return this.plantillaPlanes;
	}

	public void setPlantillaPlanes(PlantillaPlanes plantillaPlanes) 
	{
		this.plantillaPlanes = plantillaPlanes;
	}

	public String getAnchoPorDefecto() 
	{
		return this.anchoPorDefecto;
	}

	public void setAnchoPorDefecto(String anchoPorDefecto) 
	{
		this.anchoPorDefecto = anchoPorDefecto;
	}
	
	public String getAltoPorDefecto() 
	{
		return this.altoPorDefecto;
	}

	public void setAltoPorDefecto(String altoPorDefecto) 
	{
		this.altoPorDefecto = altoPorDefecto;
	}
}