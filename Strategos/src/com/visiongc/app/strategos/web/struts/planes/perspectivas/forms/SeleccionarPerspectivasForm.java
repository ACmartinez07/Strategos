package com.visiongc.app.strategos.web.struts.planes.perspectivas.forms;

import com.visiongc.framework.web.struts.forms.SelectorArbolForm;

public class SeleccionarPerspectivasForm extends SelectorArbolForm
{
	static final long serialVersionUID = 0L;
  
	public static final String SEPARADOR_RUTA = "!#!";
	public static final String CODIGO_PERSPECTIVA_ELIMINADA = "!ELIMINADO!";
	private Long organizacionSeleccionadaId;
	private Long planSeleccionadoId;
	private Long perspectivaSeleccionadaId;
	private String rutaCompletaOrganizacion;
	private String nombrePlan;
	private Boolean permitirCambiarOrganizacion;
	private Boolean permitirCambiarPlan;
	private String seleccionado;
	private String nombreCampoRutaCompleta;
	private String panelSeleccionado;
	private String atributoOrdenPlanes;
	private String tipoOrdenPlanes;
	private Boolean iniciado;
	private boolean cambioOrganizacion;
	private boolean cambioPlan;

	public Long getOrganizacionSeleccionadaId()
	{
		return this.organizacionSeleccionadaId;
	}

	public void setOrganizacionSeleccionadaId(Long organizacionSeleccionadaId) 
	{
		this.organizacionSeleccionadaId = organizacionSeleccionadaId;
	}

	public Long getPlanSeleccionadoId() 
	{
		return this.planSeleccionadoId;
	}

	public void setPlanSeleccionadoId(Long planSeleccionadoId) 
	{
		this.planSeleccionadoId = planSeleccionadoId;
	}

	public Long getPerspectivaSeleccionadaId() 
	{
		return this.perspectivaSeleccionadaId;
	}

	public void setPerspectivaSeleccionadaId(Long perspectivaSeleccionadaId) 
	{
		this.perspectivaSeleccionadaId = perspectivaSeleccionadaId;
	}

	public String getAtributoOrdenPlanes() 
	{
		return this.atributoOrdenPlanes;
	}

	public void setAtributoOrdenPlanes(String atributoOrdenPlanes) 
	{
		this.atributoOrdenPlanes = atributoOrdenPlanes;
	}

	public String getTipoOrdenPlanes() 
	{
		return this.tipoOrdenPlanes;
	}

	public void setTipoOrdenPlanes(String tipoOrdenPlanes) 
	{
		this.tipoOrdenPlanes = tipoOrdenPlanes;
	}

	public String getNombrePlan() 
	{
		return this.nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) 
	{
		this.nombrePlan = nombrePlan;
	}

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}

	public Boolean getPermitirCambiarOrganizacion() 
	{
		return this.permitirCambiarOrganizacion;
	}

	public void setPermitirCambiarOrganizacion(Boolean permitirCambiarOrganizacion) 
	{
		this.permitirCambiarOrganizacion = permitirCambiarOrganizacion;
	}

	public Boolean getPermitirCambiarPlan() 
	{
		return this.permitirCambiarPlan;
	}

	public void setPermitirCambiarPlan(Boolean permitirCambiarPlan) 
	{
		this.permitirCambiarPlan = permitirCambiarPlan;
	}

	public boolean isCambioOrganizacion() 
	{
		return this.cambioOrganizacion;
	}

	public void setCambioOrganizacion(boolean cambioOrganizacion) 
	{
		this.cambioOrganizacion = cambioOrganizacion;
	}

	public boolean isCambioPlan() 
	{
		return this.cambioPlan;
	}

	public void setCambioPlan(boolean cambioPlan) 
	{
		this.cambioPlan = cambioPlan;
	}

	public String getSeleccionado() 
	{
		return this.seleccionado;
	}

	public void setSeleccionado(String seleccionado) 
	{
		this.seleccionado = seleccionado;
	}

	public String getNombreCampoRutaCompleta() 
	{
		return this.nombreCampoRutaCompleta;
	}

	public void setNombreCampoRutaCompleta(String nombreCampoRutaCompleta) 
	{
		this.nombreCampoRutaCompleta = nombreCampoRutaCompleta;
	}

	public String getPanelSeleccionado() 
	{
		return this.panelSeleccionado;
	}

	public void setPanelSeleccionado(String panelSeleccionado) 
	{
		this.panelSeleccionado = panelSeleccionado;
	}

	public Boolean getIniciado() 
	{
		return this.iniciado;
	}

	public void setIniciado(Boolean iniciado) 
	{
		this.iniciado = iniciado;
	}

  	public String getSeparadorRuta() 
  	{
  		return new EditarPerspectivaForm().getSeparadorRuta();
  	}

  	public String getCodigoPerspectivaEliminada() 
  	{
  		return "!ELIMINADO!";
  	}

  	public void clear() 
  	{
  		setNombreForma(null);
  		setNombreCampoValor(null);
  		setNombreCampoOculto(null);
  		setValoresSeleccionados(null);
  		setFuncionCierre(null);
  		this.cambioOrganizacion = false;
  		this.cambioPlan = false;
  		this.organizacionSeleccionadaId = null;
  		this.perspectivaSeleccionadaId = null;
  		this.planSeleccionadoId = null;
  		this.seleccionado = null;
  		this.permitirCambiarOrganizacion = new Boolean(false);
  		this.permitirCambiarPlan = new Boolean(false);
  		this.nombreCampoRutaCompleta = null;
  		this.panelSeleccionado = "panelPerspectivas";
  		this.rutaCompletaOrganizacion = null;
  		this.iniciado = new Boolean(false);
  	}
}