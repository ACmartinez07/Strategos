/**
 * 
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class EditarCodigoEnlaceForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long id;
	private String codigo;
	private String nombre;
	private Long bi;
	private Long categoria;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getCodigo()
	{
		return this.codigo;
	}

	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	public String getNombre()
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Long getBi()
	{
		return this.bi;
	}

	public void setBi(Long bi) 
	{
		this.bi = bi;
	}
	
	public Long getCategoria()
	{
		return this.categoria;
	}

	public void setCategoria(Long categoria) 
	{
		this.categoria = categoria;
	}

	public void clear() 
	{
		this.id = new Long(0L);
		this.codigo = null;
		this.nombre = null;
		this.bi = null;
		this.categoria = null;
		setBloqueado(new Boolean(false));
	}	
}
