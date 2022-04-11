package com.visiongc.app.strategos.web.struts.planes.valoresiniciales.forms;

import com.visiongc.app.strategos.planes.model.util.ValorInicialIndicador;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.validator.ValidatorActionForm;

public class EditarValoresInicialesForm extends ValidatorActionForm
{
	static final long serialVersionUID = 0L;
  
	private Long organizacionId;
	private String nombreOrganizacion;
	private Long planId;
	private String nombrePlan;
	private List<ValorInicialIndicador> valoresInicialesIndicadores;
	private boolean establecerMetasSoloIndicadoresSeleccionados;
	private Boolean visualizarIndicadoresCompuestos;
	private boolean mostrarUnidadMedida;
	private boolean mostrarCodigoEnlace;
	private boolean bloquear;

	public Long getOrganizacionId()
	{
		return this.organizacionId;
	}
	
	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public String getNombreOrganizacion() 
	{
		return this.nombreOrganizacion;
	}
	
	public void setNombreOrganizacion(String nombreOrganizacion) 
	{
		this.nombreOrganizacion = nombreOrganizacion;
	}
	
	public Long getPlanId() 
	{
		return this.planId;
	}

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}
	
	public String getNombrePlan() 
	{
		return this.nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) 
	{
		this.nombrePlan = nombrePlan;
	}

	public List<ValorInicialIndicador> getValoresInicialesIndicadores() 
	{
		return this.valoresInicialesIndicadores;
	}

	public void setValoresInicialesIndicadores(List<ValorInicialIndicador> valoresInicialesIndicadores) 
	{
		this.valoresInicialesIndicadores = valoresInicialesIndicadores;
	}

	public boolean isEstablecerMetasSoloIndicadoresSeleccionados() 
	{
		return this.establecerMetasSoloIndicadoresSeleccionados;
	}
	
	public void setEstablecerMetasSoloIndicadoresSeleccionados(boolean establecerMetasSoloIndicadoresSeleccionados) 
	{
		this.establecerMetasSoloIndicadoresSeleccionados = establecerMetasSoloIndicadoresSeleccionados;
	}
	
	public boolean isMostrarUnidadMedida() 
	{
		return this.mostrarUnidadMedida;
	}
	
	public void setMostrarUnidadMedida(boolean mostrarUnidadMedida) 
	{
		this.mostrarUnidadMedida = mostrarUnidadMedida;
	}
	
	public boolean isMostrarCodigoEnlace() 
	{
	  return this.mostrarCodigoEnlace;
	}

	public void setMostrarCodigoEnlace(boolean mostrarCodigoEnlace) 
	{
		this.mostrarCodigoEnlace = mostrarCodigoEnlace;
	}

	public Long getSerieId() 
	{
		return SerieTiempo.getSerieMetaId();
	}

	public Boolean getVisualizarIndicadoresCompuestos() 
	{
		return this.visualizarIndicadoresCompuestos;
	}

	public void setVisualizarIndicadoresCompuestos(Boolean visualizarIndicadoresCompuestos) 
	{
		this.visualizarIndicadoresCompuestos = visualizarIndicadoresCompuestos;
	}

	public boolean getBloquear() 
	{
	  return this.bloquear;
	}

	public void setBloquear(boolean bloquear) 
	{
		this.bloquear = bloquear;
	}
	
	public void clear() 
	{
		this.organizacionId = null;
		this.nombreOrganizacion = null;
		this.planId = null;
		this.nombrePlan = null;
		this.establecerMetasSoloIndicadoresSeleccionados = true;
		this.visualizarIndicadoresCompuestos = new Boolean(false);
		this.valoresInicialesIndicadores = new ArrayList<ValorInicialIndicador>();
		this.mostrarUnidadMedida = true;
		this.mostrarCodigoEnlace = true;
		this.bloquear = false;
	}
}