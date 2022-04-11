package com.visiongc.app.strategos.web.struts.indicadores.forms;

import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarIndicadoresForm extends SelectorListaForm
{
	static final long serialVersionUID = 0L;
  
	public static final String SEPARADOR_INDICADORES = "!;!";
	public static final String SEPARADOR_SERIES = "!@!";
	public static final String SEPARADOR_RUTA = "!#!";
	public static final String CODIGO_INDICADOR_ELIMINADO = "!ELIMINADO!";
	private Long organizacionSeleccionadaId;
	private Long claseSeleccionadaId;
	private String nombreCampoRutasCompletas;
	private Boolean mostrarSeriesTiempo;
  	private Byte frecuenciaSeleccionada;
  	private Byte frecuenciasContenidasSeleccionada;
  	private Boolean permitirCambiarOrganizacion;
  	private Boolean permitirCambiarClase;
  	private String funcionCierre;
  	private String panelSeleccionado;
  	private String rutaCompletaOrganizacion;
  	private String rutaCompletaClaseIndicadores;
  	private Boolean iniciado;
  	private Boolean seleccionMultiple;
  	private Boolean permitirCualitativos;
  	private Boolean soloCompuestos;
  	private String excluirIds;
  	private Boolean permitirIniciativas;
  	private String panelIndicadores;
  	private String iniciativasNodoSeleccionadoId;
  	private NodoArbol iniciativasNodoSeleccionado;
  	private String planesNodoSeleccionadoId;
  	private NodoArbol planesNodoSeleccionado;
  	private Long planId;
  	private Boolean mostrarPanelIndicadores;
  	private Long indicadorId;
  	private Boolean permitirPlanes;
  	private Boolean agregarSerieMeta;
  	private String seleccionadosPlanId;
  
  	public Long getClaseSeleccionadaId()
  	{
  		return this.claseSeleccionadaId;
  	}

  	public void setClaseSeleccionadaId(Long claseSeleccionadaId) 
  	{
  		this.claseSeleccionadaId = claseSeleccionadaId;
  	}

  	public Long getOrganizacionSeleccionadaId() 
  	{
  		return this.organizacionSeleccionadaId;
  	}

  	public void setOrganizacionSeleccionadaId(Long organizacionSeleccionadaId) 
  	{
  		this.organizacionSeleccionadaId = organizacionSeleccionadaId;
  	}

  	public String getNombreCampoRutasCompletas() 
  	{
  		return this.nombreCampoRutasCompletas;
  	}

  	public void setNombreCampoRutasCompletas(String nombreCampoRutasCompletas) 
  	{
  		this.nombreCampoRutasCompletas = nombreCampoRutasCompletas;
  	}

  	public Boolean getMostrarSeriesTiempo() 
  	{
  		return this.mostrarSeriesTiempo;
  	}

  	public void setMostrarSeriesTiempo(Boolean mostrarSeriesTiempo) 
  	{
  		this.mostrarSeriesTiempo = mostrarSeriesTiempo;
  	}

  	public Byte getFrecuenciaSeleccionada() 
  	{
  		return this.frecuenciaSeleccionada;
  	}

  	public void setFrecuenciaSeleccionada(Byte frecuenciaSeleccionada) 
  	{
  		this.frecuenciaSeleccionada = frecuenciaSeleccionada;
  	}

  	public Byte getFrecuenciasContenidasSeleccionada() 
  	{
  		return this.frecuenciasContenidasSeleccionada;
  	}

  	public void setFrecuenciasContenidasSeleccionada(Byte frecuenciasContenidasSeleccionada) 
  	{
  		this.frecuenciasContenidasSeleccionada = frecuenciasContenidasSeleccionada;
  	}

  	public Boolean getPermitirCambiarOrganizacion() 
  	{
  		return this.permitirCambiarOrganizacion;
  	}

  	public void setPermitirCambiarOrganizacion(Boolean permitirCambiarOrganizacion) 
  	{
  		this.permitirCambiarOrganizacion = permitirCambiarOrganizacion;
  	}

  	public Boolean getPermitirCambiarClase() 
  	{
  		return this.permitirCambiarClase;
  	}

  	public void setPermitirCambiarClase(Boolean permitirCambiarClase) 
  	{
  		this.permitirCambiarClase = permitirCambiarClase;
  	}

  	public String getPanelSeleccionado() 
  	{
  		return this.panelSeleccionado;
  	}

  	public void setPanelSeleccionado(String panelSeleccionado) 
  	{
  		this.panelSeleccionado = panelSeleccionado;
  	}

  	public String getFuncionCierre() 
  	{
  		return this.funcionCierre;
  	}

  	public void setFuncionCierre(String funcionCierre) 
  	{
  		this.funcionCierre = funcionCierre;
  	}

  	public String getRutaCompletaClaseIndicadores() 
  	{
  		return this.rutaCompletaClaseIndicadores;
  	}

  	public void setRutaCompletaClaseIndicadores(String rutaCompletaClaseIndicadores) 
  	{
  		this.rutaCompletaClaseIndicadores = rutaCompletaClaseIndicadores;
  	}

  	public String getRutaCompletaOrganizacion() 
  	{
  		return this.rutaCompletaOrganizacion;
  	}

  	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
  	{
  		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
  	}

  	public Boolean getIniciado() 
  	{
  		return this.iniciado;
  	}

  	public void setIniciado(Boolean iniciado) 
  	{
  		this.iniciado = iniciado;
  	}

  	public Boolean getSeleccionMultiple() 
  	{
  		return this.seleccionMultiple;
  	}
  	
  	public void setSeleccionMultiple(Boolean seleccionMultiple) 
  	{
  		this.seleccionMultiple = seleccionMultiple;
  	}

  	public Boolean getPermitirCualitativos() 
  	{
  		return this.permitirCualitativos;
  	}

  	public void setPermitirCualitativos(Boolean permitirCualitativos) 
  	{
  		this.permitirCualitativos = permitirCualitativos;
  	}

  	public Boolean getSoloCompuestos() 
  	{
  		return this.soloCompuestos;
  	}

  	public void setSoloCompuestos(Boolean soloCompuestos) 
  	{
  		this.soloCompuestos = soloCompuestos;
  	}

  	public Boolean getPermitirPlanes() 
  	{
  		return this.permitirPlanes;
  	}

  	public void setPermitirPlanes(Boolean permitirPlanes) 
  	{
  		this.permitirPlanes = permitirPlanes;
  	}
  	
  	public String getExcluirIds() 
  	{
  		return this.excluirIds;
  	}

  	public void setExcluirIds(String exluirIds) 
  	{
  		this.excluirIds = exluirIds;
  	}

  	public Boolean getPermitirIniciativas() 
  	{
  		return this.permitirIniciativas;
  	}

  	public void setPermitirIniciativas(Boolean permitirIniciativas) 
  	{
  		this.permitirIniciativas = permitirIniciativas;
  	}

  	public String getPanelIndicadores() 
  	{
  		return this.panelIndicadores;
  	}

  	public void setPanelIndicadores(String panelIndicadores) 
  	{
  		this.panelIndicadores = panelIndicadores;
  	}

  	public String getIniciativasNodoSeleccionadoId() 
  	{
  		return this.iniciativasNodoSeleccionadoId;
  	}

  	public void setIniciativasNodoSeleccionadoId(String iniciativasNodoSeleccionadoId) 
  	{
  		this.iniciativasNodoSeleccionadoId = iniciativasNodoSeleccionadoId;
  	}

  	public NodoArbol getIniciativasNodoSeleccionado() 
  	{
  		return this.iniciativasNodoSeleccionado;
  	}

  	public void setIniciativasNodoSeleccionado(NodoArbol iniciativasNodoSeleccionado) 
  	{
  		this.iniciativasNodoSeleccionado = iniciativasNodoSeleccionado;
  	}

  	public String getPlanesNodoSeleccionadoId() 
  	{
  		return this.planesNodoSeleccionadoId;
  	}

  	public void setPlanesNodoSeleccionadoId(String planesNodoSeleccionadoId) 
  	{
  		this.planesNodoSeleccionadoId = planesNodoSeleccionadoId;
  	}

  	public NodoArbol getPlanesNodoSeleccionado() 
  	{
  		return this.planesNodoSeleccionado;
  	}

  	public void setPlanesNodoSeleccionado(NodoArbol planesNodoSeleccionado) 
  	{
	  this.planesNodoSeleccionado = planesNodoSeleccionado;
  	}

  	public Long getPlanId() 
  	{
  		return this.planId;
  	}

  	public void setPlanId(Long planId) 
  	{
  		this.planId = planId;
  	}

  	public Long getIndicadorId() 
  	{
  		return this.indicadorId;
  	}

  	public void setIndicadorId(Long indicadorId) 
  	{
  		this.indicadorId = indicadorId;
  	}
  	
  	public Boolean getMostrarPanelIndicadores() 
  	{
  		return this.mostrarPanelIndicadores;
  	}
  	
  	public void setMostrarPanelIndicadores(Boolean mostrarPanelIndicadores) 
  	{
  		this.mostrarPanelIndicadores = mostrarPanelIndicadores;
  	}
  	
  	public Boolean getAgregarSerieMeta() 
  	{
  		return this.agregarSerieMeta;
  	}
  	
  	public void setAgregarSerieMeta(Boolean agregarSerieMeta) 
  	{
	  	this.agregarSerieMeta = agregarSerieMeta;
  	}

  	public String getSeleccionadosPlanId() 
  	{
  		return this.seleccionadosPlanId;
  	}
  	
  	public void setSeleccionadosPlanId(String seleccionadosPlanId) 
  	{
	  	this.seleccionadosPlanId = seleccionadosPlanId;
  	}
  	
  	public String getSeparadorIndicadores() 
  	{
  		return "!;!";
  	}

  	public String getSeparadorSeries() 
  	{
  		return "!@!";
  	}

  	public String getCodigoIndicadorEliminado() 
  	{
  		return "!ELIMINADO!";
  	}

  	public void clear() 
  	{
  		setNombreForma(null);
  		setNombreCampoValor(null);
  		setNombreCampoOculto(null);
  		setSeleccionados(null);
  		setValoresSeleccionados(null);
  		setSeleccionadosPlanId(null);
  		
  		this.organizacionSeleccionadaId = null;
  		this.claseSeleccionadaId = null;
  		this.frecuenciaSeleccionada = null;
  		this.frecuenciasContenidasSeleccionada = null;
  		this.funcionCierre = null;
  		this.mostrarSeriesTiempo = new Boolean(false);
  		this.permitirCambiarOrganizacion = new Boolean(false);
  		this.permitirCambiarClase = new Boolean(false);
  		this.nombreCampoRutasCompletas = null;
  		this.panelSeleccionado = "panelIndicadores";
  		this.rutaCompletaClaseIndicadores = null;
  		this.rutaCompletaOrganizacion = null;
  		this.iniciado = new Boolean(false);
  		this.seleccionMultiple = new Boolean(false);
  		this.permitirCualitativos = new Boolean(false);
  		this.soloCompuestos = new Boolean(false);
  		this.excluirIds = null;
  		this.permitirIniciativas = new Boolean(false);
  		this.panelIndicadores = "clases";
  		this.planesNodoSeleccionado = null;
  		this.planesNodoSeleccionadoId = null;
  		this.iniciativasNodoSeleccionado = null;
  		this.iniciativasNodoSeleccionadoId = null;
  		this.planId = null;
  		this.indicadorId = null;
  		this.permitirPlanes = new Boolean(true);
  		this.agregarSerieMeta = null;
  	}
}