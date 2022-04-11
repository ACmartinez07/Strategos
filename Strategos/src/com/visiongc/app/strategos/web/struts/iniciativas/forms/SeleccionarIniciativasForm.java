package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarIniciativasForm extends SelectorListaForm
{
	static final long serialVersionUID = 0L;
	
	public static final String SEPARADOR_INICIATIVAS = "!;!";
	public static final String SEPARADOR_RUTA = "!#!";
	public static final String CODIGO_INICIATIVA_ELIMINADA = "!ELIMINADO!";
	private Long organizacionSeleccionadaId;
	private String rutaCompletaOrganizacion;
	private Long planSeleccionadoId;
	private String nombrePlan;
	private Boolean permitirCambiarOrganizacion;
	private Boolean permitirCambiarPlan;
	private String funcionCierre;
	private String nombreCampoRutasCompletas;
	private String nombreCampoPlanId;
	private Byte frecuenciaSeleccionada;
	private String panelSeleccionado;
	private String atributoOrdenPlanes;
	private String tipoOrdenPlanes;
	private Boolean iniciado;
	private boolean cambioOrganizacion;
	private String excluirIds;
	private Boolean seleccionMultiple;

	public Long getPlanSeleccionadoId()
	{
		return this.planSeleccionadoId;
	}

	public void setPlanSeleccionadoId(Long planSeleccionadoId) 
	{
		this.planSeleccionadoId = planSeleccionadoId;
	}

	public String getNombrePlan() 
	{
		return this.nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) 
	{
		this.nombrePlan = nombrePlan;
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

	public String getFuncionCierre() 
	{
		return this.funcionCierre;
	}

	public void setFuncionCierre(String funcionCierre) 
	{
		this.funcionCierre = funcionCierre;
	}

	public String getNombreCampoRutasCompletas() 
	{
		return this.nombreCampoRutasCompletas;
	}

	public void setNombreCampoRutasCompletas(String nombreCampoRutasCompletas) 
	{
		this.nombreCampoRutasCompletas = nombreCampoRutasCompletas;
	}

	public Byte getFrecuenciaSeleccionada() 
	{
		return this.frecuenciaSeleccionada;
	}

	public void setFrecuenciaSeleccionada(Byte frecuenciaSeleccionada) 
	{
		this.frecuenciaSeleccionada = frecuenciaSeleccionada;
	}

	public String getPanelSeleccionado() 
	{
		return this.panelSeleccionado;
	}

	public void setPanelSeleccionado(String panelSeleccionado) 
	{
		this.panelSeleccionado = panelSeleccionado;
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

	public Long getOrganizacionSeleccionadaId() 
	{
		return this.organizacionSeleccionadaId;
	}

	public void setOrganizacionSeleccionadaId(Long organizacionSeleccionadaId) 
	{
		this.organizacionSeleccionadaId = organizacionSeleccionadaId;
	}

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}

	public String getNombreCampoPlanId() 
	{
		return this.nombreCampoPlanId;
	}

	public void setNombreCampoPlanId(String nombreCampoPlanId) 
	{
		this.nombreCampoPlanId = nombreCampoPlanId;
	}

	public Boolean getIniciado() 
	{
		return this.iniciado;
	}

	public void setIniciado(Boolean iniciado) 
	{
		this.iniciado = iniciado;
	}

	public String getSeparadorIniciativas() 
	{
		return SEPARADOR_INICIATIVAS;
	}

	public String getSeparadorRuta() 
	{
		return "!#!";
	}

	public String getCodigoIniciativaEliminada() 
	{
		return "!ELIMINADO!";
	}

	public String getExcluirIds() 
	{
		return this.excluirIds;
	}

	public void setExcluirIds(String excluirIds) 
	{
		this.excluirIds = excluirIds;
	}
	
  	public Boolean getSeleccionMultiple() 
  	{
  		return this.seleccionMultiple;
  	}
  	
  	public void setSeleccionMultiple(Boolean seleccionMultiple) 
  	{
  		this.seleccionMultiple = seleccionMultiple;
  	}
	
	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setSeleccionados(null);
		setValoresSeleccionados(null);
		this.cambioOrganizacion = false;
		this.organizacionSeleccionadaId = null;
		this.planSeleccionadoId = null;
		this.frecuenciaSeleccionada = null;
		this.funcionCierre = null;
		this.permitirCambiarOrganizacion = new Boolean(false);
		this.permitirCambiarPlan = new Boolean(false);
		this.nombreCampoRutasCompletas = null;
		this.nombreCampoPlanId = null;
		this.panelSeleccionado = "panelIniciativas";
		this.rutaCompletaOrganizacion = null;
		this.iniciado = new Boolean(false);
		this.excluirIds = null;
		this.seleccionMultiple = new Boolean(false);
	}
}