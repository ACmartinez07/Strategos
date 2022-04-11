/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class AsignarPesosActividadForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	private String organizacionNombre;
	private String padreNombre;
	private Byte tipoPadre;
	private String funcionCierre;
	private Long actividadId;
	private Long proyectoId;
	private Long iniciativaId;
	private Long organizacionId;

	public String getOrganizacionNombre() 
	{
		return this.organizacionNombre;
	}

	public void setOrganizacionNombre(String organizacionNombre) 
	{
		this.organizacionNombre = organizacionNombre;
	}

	public String getPadreNombre() 
	{
		return this.padreNombre;
	}

	public void setPadreNombre(String planNombre) 
	{
		this.padreNombre = planNombre;
	}

	public String getFuncionCierre() 
	{
		return this.funcionCierre;
	}

	public void setFuncionCierre(String funcionCierre) 
	{
		this.funcionCierre = funcionCierre;
	}

	public Byte getTipoPadre() 
	{
		return this.tipoPadre;
	}

	public void setTipoPadre(Byte tipoPadre) 
	{
		this.tipoPadre = tipoPadre;
	}

	public Long getActividadId() 
	{
		return this.actividadId;
	}

	public void setActividadId(Long actividadId) 
	{
		this.actividadId = actividadId;
	}

	public Long getProyectoId() 
	{
		return this.proyectoId;
	}

	public void setProyectoId(Long proyectoId) 
	{
		this.proyectoId = proyectoId;
	}

	public Long getIniciativaId() 
	{
		return this.iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) 
	{
		this.iniciativaId = iniciativaId;
	}

	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}
	
	public void clear() 
	{
		this.organizacionNombre = null;
		this.padreNombre = null;
		this.funcionCierre = null;
		this.tipoPadre = new Byte((byte) 0);
		this.actividadId = 0L;
		this.iniciativaId = 0L;
		this.proyectoId = 0L;
		this.organizacionId = 0L;
	}
}
