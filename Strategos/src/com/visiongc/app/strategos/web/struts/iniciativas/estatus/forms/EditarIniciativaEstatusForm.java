/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.estatus.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class EditarIniciativaEstatusForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long id;
	private String nombre;
	private Double porcentajeInicial;
	private Double porcentajeFinal;
	private Boolean sistema;
	private Boolean bloquearMedicion;
	private Boolean bloquearIndicadores;
	
	public Long getId()
	{
		return this.id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Double getPorcentajeInicial() 
	{
		return this.porcentajeInicial;
	}

	public void setPorcentajeInicial(Double porcentajeInicial) 
	{
		this.porcentajeInicial = porcentajeInicial;
	}

	public Double getPorcentajeFinal() 
	{
		return this.porcentajeFinal;
	}

	public void setPorcentajeFinal(Double porcentajeFinal) 
	{
		this.porcentajeFinal = porcentajeFinal;
	}

	public Boolean getSistema() 
	{
		return this.sistema;
	}

	public void setSistema(Boolean sistema) 
	{
		this.sistema = sistema;
	}

	public Boolean getBloquearMedicion() 
	{
		return this.bloquearMedicion;
	}

	public void setBloquearMedicion(Boolean bloquearMedicion) 
	{
		this.bloquearMedicion = bloquearMedicion;
	}

	public Boolean getBloquearIndicadores() 
	{
		return this.bloquearIndicadores;
	}

	public void setBloquearIndicadores(Boolean bloquearIndicadores) 
	{
		this.bloquearIndicadores = bloquearIndicadores;
	}
	
  	public void clear()
	{
  		this.id = new Long(0L);
		this.nombre = null;
		this.porcentajeInicial = 0D;
		this.porcentajeFinal = 0D;
		this.sistema = false;
		this.bloquearMedicion = false;
		this.bloquearIndicadores = false;
	}
}
