/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.forms;

import java.util.List;

import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class EditarPortafolioForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long id;
	private String nombre;
	private Byte activo;
	private Double porcentajeCompletado;
	private Long estatusId;
	private IniciativaEstatus estatus;
	private OrganizacionStrategos organizacion;
	private Byte frecuencia;
	private List<Frecuencia> frecuencias;
	private Portafolio portafolio;
	private Integer ancho;
	private Integer alto;
	private Byte filas;
	private Byte columnas;
	
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
		  
	public Byte getActivo() 
	{
		return this.activo;
	}

	public void setActivo(Byte activo) 
	{
		this.activo = activo;
	}

	public Double getPorcentajeCompletado() 
	{
		return this.porcentajeCompletado;
	}

	public void setPorcentajeCompletado(Double porcentajeCompletado) 
	{
		this.porcentajeCompletado = porcentajeCompletado;
	}

	public Long getEstatusId() 
	{
		return this.estatusId;
	}	

	public void setEstatusId(Long estatusId) 
	{
		this.estatusId = estatusId;
	}
	
	public IniciativaEstatus getEstatus() 
	{
		return this.estatus;
	}	

	public void setEstatus(IniciativaEstatus estatus) 
	{
		this.estatus = estatus;
	}
	
	public OrganizacionStrategos getOrganizacion() 
	{
		return this.organizacion;
	}

	public void setOrganizacion(OrganizacionStrategos organizacion) 
	{
		this.organizacion = organizacion;
	}
	
	public Byte getFrecuencia() 
	{
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
		this.frecuencia = frecuencia;
	}
	
	public List<Frecuencia> getFrecuencias() 
	{
		return this.frecuencias;
	}

	public void setFrecuencias(List<Frecuencia> frecuencias) 
	{
		this.frecuencias = frecuencias;
	}

	public Portafolio getPortafolio() 
	{
		return this.portafolio;
	}

	public void setPortafolio(Portafolio portafolio) 
	{
		this.portafolio = portafolio;
	}
	
	public Integer getAncho()
	{
		return this.ancho;
	}
	
	public void setAncho(Integer ancho)
	{
		this.ancho = ancho;
	}

	public Integer getAlto()
	{
		return this.alto;
	}

	public void setAlto(Integer alto)
	{
		this.alto = alto;
	}

	public Byte getFilas()
	{
		return this.filas;
	}

	public void setFilas(Byte filas)
	{
		this.filas = filas;
	}

	public Byte getColumnas()
	{
		return this.columnas;
	}

	public void setColumnas(Byte columnas)
	{
		this.columnas = columnas;
	}
	
  	public void clear()
	{
  		this.id = new Long(0L);
		this.nombre = null;
		this.activo = CondicionType.getFiltroCondicionActivo();
		this.porcentajeCompletado = null;
		this.estatusId = null;
		this.estatus = null;
		this.organizacion = null;
		this.frecuencia = Frecuencia.getFrecuenciaMensual();
		this.frecuencias = Frecuencia.getFrecuencias();
		this.portafolio = null;
		this.filas = 2;
		this.columnas = 2;
		this.alto = 300;
		this.ancho = 300;
	}
}
