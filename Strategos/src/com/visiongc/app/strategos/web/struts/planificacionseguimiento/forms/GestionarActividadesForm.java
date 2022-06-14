package com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;
import java.util.List;

public class GestionarActividadesForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
  
	private Long iniciativaId;
	private Long iniciativaClaseId;
	private Long organizacionId;
	private Byte frecuenciaIniciativa;
	private Long proyectoId;
	private Long planId;
	private List listaActividades;
	private Boolean soloLectura;
	private Boolean iniciativaVinculada;
	private String nombreIniciativaSingular;
	private String nombreActividadSingular;
	private String nombreIniciativaPlural;
	private String nombreActividadPlural;
	private String filtroNombre;
	private Boolean mostrarGantt;
	private Byte frecuenciaGantt;
	private List frecuenciasGantt;
	private Integer totalPixeles;
	private List escalaSuperior;
	private List escalaInferior;
	private Byte zoom;
	private String respuesta;
	private Boolean bloqueado;
	private String nombreIniciativa;
	private Boolean desdeInstrumento;
		
	public Boolean getDesdeInstrumento() {
		return desdeInstrumento;
	}

	public void setDesdeInstrumento(Boolean desdeInstrumento) {
		this.desdeInstrumento = desdeInstrumento;
	}

	public Long getIniciativaId()
	{
		return this.iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) 
	{
		this.iniciativaId = iniciativaId;
	}

	public Long getIniciativaClaseId()
	{
		return this.iniciativaClaseId;
	}

	public void setIniciativaClaseId(Long iniciativaClaseId) 
	{
		this.iniciativaClaseId = iniciativaClaseId;
	}
	
	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public Byte getFrecuenciaIniciativa() 
	{
		return this.frecuenciaIniciativa;
	}

	public void setFrecuenciaIniciativa(Byte frecuenciaIniciativa) 
	{
		this.frecuenciaIniciativa = frecuenciaIniciativa;
	}

	public Long getProyectoId() 
	{
		return this.proyectoId;
	}

	public void setProyectoId(Long proyectoId) 
	{
		this.proyectoId = proyectoId;
	}

	public Long getPlanId() 
	{
		return this.planId;
	}	

	public void setPlanId(Long planId) 
	{
		this.planId = planId;
	}

	public Boolean getSoloLectura() 
	{
		return this.soloLectura;
	}

	public void setSoloLectura(Boolean soloLectura) 
	{
		this.soloLectura = soloLectura;
	}

	public Boolean getIniciativaVinculada() 
	{
		return this.iniciativaVinculada;
	}

	public void setIniciativaVinculada(Boolean iniciativaVinculada) 
	{
		this.iniciativaVinculada = iniciativaVinculada;
	}

	public String getNombreActividadPlural() 
	{
		return this.nombreActividadPlural;
	}

	public void setNombreActividadPlural(String nombreActividadPlural) 
	{
		this.nombreActividadPlural = nombreActividadPlural;
	}	

	public String getNombreActividadSingular() 
	{
		return this.nombreActividadSingular;
	}

	public void setNombreActividadSingular(String nombreActividadSingular) 
	{
		this.nombreActividadSingular = nombreActividadSingular;
	}

	public String getNombreIniciativaPlural() 
	{
		return this.nombreIniciativaPlural;
	}

	public void setNombreIniciativaPlural(String nombreIniciativaPlural) 
	{
		this.nombreIniciativaPlural = nombreIniciativaPlural;
	}

	public String getNombreIniciativaSingular() 
	{
		return this.nombreIniciativaSingular;
	}

	public void setNombreIniciativaSingular(String nombreIniciativaSingular) 
	{
		this.nombreIniciativaSingular = nombreIniciativaSingular;
	}

	public String getFiltroNombre() 
	{
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) 
	{
		this.filtroNombre = filtroNombre;
	}

	public Byte getFrecuenciaGantt()
	{
		return this.frecuenciaGantt;
	}

	public void setFrecuenciaGantt(Byte frecuenciaGantt) 
	{
		this.frecuenciaGantt = frecuenciaGantt;
	}

	public List getFrecuenciasGantt() 
	{
		return this.frecuenciasGantt;
	}

	public void setFrecuenciasGantt(List frecuenciasGantt) 
	{
		this.frecuenciasGantt = frecuenciasGantt;
	}

	public Integer getTotalPixeles() 
	{
		return this.totalPixeles;
	}

	public void setTotalPixeles(Integer totalPixeles) 
	{
		this.totalPixeles = totalPixeles;
	}	

	public List getEscalaInferior() 
	{
		return this.escalaInferior;
	}

	public void setEscalaInferior(List escalaInferior) 
	{
		this.escalaInferior = escalaInferior;
	}	

	public List getEscalaSuperior() 
	{
		return this.escalaSuperior;
	}

	public void setEscalaSuperior(List escalaSuperior) 
	{
		this.escalaSuperior = escalaSuperior;
	}	
	
	public List getListaActividades() 
	{
		return this.listaActividades;
	}	

	public void setListaActividades(List listaActividades) 
	{
		this.listaActividades = listaActividades;
	}

	public Byte getZoom() 
	{
		return this.zoom;
	}	

	public void setZoom(Byte zoom) 
	{
		this.zoom = zoom;
	}

	public Boolean getMostrarGantt() 
	{
		return this.mostrarGantt;
	}

	public void setMostrarGantt(Boolean mostrarGantt) 
	{
		this.mostrarGantt = mostrarGantt;
	}	
  
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public Boolean getBloqueado() 
	{
		return this.bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) 
	{
		this.bloqueado = bloqueado;
	}
	
	public String getNombreIniciativa() 
	{
		return this.nombreIniciativa;
	}

	public void setNombreIniciativa(String nombreIniciativa) 
	{
		this.nombreIniciativa = nombreIniciativa;
	}
	
	public void clear() {
		this.iniciativaId = new Long(0L);
		this.iniciativaClaseId = null;
		this.organizacionId = null;
		this.frecuenciaIniciativa = null;
		this.proyectoId = null;
		this.planId = null;
		this.listaActividades = null;
		this.soloLectura = null;
		this.iniciativaVinculada = null;
		this.nombreIniciativaSingular = null;
		this.nombreActividadSingular = null;
		this.nombreIniciativaPlural = null;
		this.nombreActividadPlural = null;
		this.filtroNombre = null;
		this.mostrarGantt = null;
		this.frecuenciaGantt = null;
		this.frecuenciasGantt = null;
		this.totalPixeles = null;
		this.escalaSuperior = null;
		this.escalaInferior = null;
		this.zoom = null;
		this.respuesta = null;
		this.bloqueado = null;
		this.nombreIniciativa = null;
		this.desdeInstrumento = null;
	}
}