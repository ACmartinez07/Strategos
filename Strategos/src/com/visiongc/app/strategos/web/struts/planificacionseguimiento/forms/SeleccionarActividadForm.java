/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

/**
 * @author Kerwin
 *
 */
public class SeleccionarActividadForm extends SelectorListaForm
{
	static final long serialVersionUID = 0L;
	
	public static final String SEPARADOR_ACTIVIDADES = "!;!";
	public static final String SEPARADOR_RUTA = "!#!";
	public static final String CODIGO_ACTIVIDAD_ELIMINADA = "!ELIMINADO!";
	private Long organizacionSeleccionadaId;
	private Long planSeleccionadoId;
	private Long iniciativaSeleccionadaId;
	private String rutaCompletaOrganizacion;
	private String nombreOrganizacion;
	private String nombrePlan;
	private String nombreIniciativa;
	private Boolean permitirCambiarOrganizacion;
	private Boolean permitirCambiarPlan;
	private Boolean permitirCambiarIniciativa;
	private Boolean cambioOrganizacion;
	private Boolean cambioPlan;
	private Boolean cambioIniciativa;
	private String funcionCierre;
	private String nombreCampoRutasCompletas;
	private String nombreCampoPlanId;
	private Byte frecuenciaSeleccionada;
	private String panelSeleccionado;
	private String atributoOrdenPlanes;
	private String tipoOrdenPlanes;
	private Boolean iniciado;
	private String excluirIds;

	public Long getPlanSeleccionadoId()
	{
		return this.planSeleccionadoId;
	}

	public void setPlanSeleccionadoId(Long planSeleccionadoId) 
	{
		this.planSeleccionadoId = planSeleccionadoId;
	}

	public Long getIniciativaSeleccionadaId()
	{
		return this.iniciativaSeleccionadaId;
	}

	public void setIniciativaSeleccionadaId(Long iniciativaSeleccionadaId) 
	{
		this.iniciativaSeleccionadaId = iniciativaSeleccionadaId;
	}
	
	public String getNombreOrganizacion() 
	{
		return this.nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion) 
	{
		this.nombreOrganizacion = nombreOrganizacion;
	}

	public String getNombrePlan() 
	{
		return this.nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) 
	{
		this.nombrePlan = nombrePlan;
	}

	public String getNombreIniciativa() 
	{
		return this.nombreIniciativa;
	}

	public void setNombreIniciativa(String nombreIniciativa) 
	{
		this.nombreIniciativa = nombreIniciativa;
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

	public Boolean getPermitirCambiarIniciativa() 
	{
		return this.permitirCambiarIniciativa;
	}

	public void setPermitirCambiarIniciativa(Boolean permitirCambiarIniciativa) 
	{
		this.permitirCambiarIniciativa = permitirCambiarIniciativa;
	}
	
	public Boolean isCambioOrganizacion() 
	{
		return this.cambioOrganizacion;
	}

	public void setCambioOrganizacion(Boolean cambioOrganizacion) 
	{
		this.cambioOrganizacion = cambioOrganizacion;
	}

	public Boolean getCambioPlan() 
	{
		return this.cambioPlan;
	}

	public void setCambioPlan(Boolean cambioPlan) 
	{
		this.cambioPlan = cambioPlan;
	}

	public Boolean getCambioIniciativa() 
	{
		return this.cambioIniciativa;
	}

	public void setCambioIniciativa(Boolean cambioIniciativa) 
	{
		this.cambioIniciativa = cambioIniciativa;
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

	public String getSeparadorActividades() 
	{
		return "!;!";
	}

	public String getSeparadorRuta() 
	{
		return "!#!";
	}

	public String getCodigoActividadEliminada() 
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
	
	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setSeleccionados(null);
		setValoresSeleccionados(null);
		this.cambioOrganizacion = false;
		this.cambioPlan = false;
		this.cambioIniciativa = false;
		this.organizacionSeleccionadaId = null;
		this.planSeleccionadoId = null;
		this.iniciativaSeleccionadaId = null;
		this.frecuenciaSeleccionada = null;
		this.funcionCierre = null;
		this.permitirCambiarOrganizacion = new Boolean(false);
		this.permitirCambiarPlan = new Boolean(false);
		this.nombreCampoRutasCompletas = null;
		this.nombreCampoPlanId = null;
		this.panelSeleccionado = "panelActividades";
		this.nombrePlan = null;
		this.nombreOrganizacion = null;
		this.nombreIniciativa = null;
		this.rutaCompletaOrganizacion = null;
		this.iniciado = new Boolean(false);
		this.excluirIds = null;
	}
}