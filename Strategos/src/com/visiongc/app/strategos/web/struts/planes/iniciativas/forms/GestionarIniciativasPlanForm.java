package com.visiongc.app.strategos.web.struts.planes.iniciativas.forms;

import java.util.List;

import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarIniciativasPlanForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
	

	private String nombreIniciativaSingular;
	private String nombreIniciativaPlural;
	private String respuesta;
  	private List<IniciativaEstatus> tiposEstatus;
  	private Long estatus;
  	private List<TipoProyecto> tipos;
  	private Long tipo;
  	
  	
  	
	public List<TipoProyecto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoProyecto> tipos) {
		this.tipos = tipos;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public String getNombreIniciativaSingular()
	{
		return this.nombreIniciativaSingular;
	}

	public void setNombreIniciativaSingular(String nombreIniciativaSingular) 
	{
		this.nombreIniciativaSingular = nombreIniciativaSingular;
	}

	public String getNombreIniciativaPlural() 
	{
		return this.nombreIniciativaPlural;
	}

	public void setNombreIniciativaPlural(String nombreIniciativaPlural) 
	{
		this.nombreIniciativaPlural = nombreIniciativaPlural;
	}
	
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public List<IniciativaEstatus> getTiposEstatus() 
	{
		return this.tiposEstatus;
	}

	public void setTiposEstatus(List<IniciativaEstatus> tiposEstatus) 
	{
		this.tiposEstatus = tiposEstatus;
	}
  	
  	public Long getEstatus() 
  	{
  		return this.estatus;
  	}

  	public void setEstatus(Long estatus) 
  	{
  		this.estatus = estatus;
  	}
}