package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Set;

public class EditarClaseIndicadoresForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	private Long claseId;
	private String padre;
	private Long padreId;
	private Long organizacionId;
	private String nombre;
	private String descripcion;
	private String enlaceParcial;
	private Byte tipo;
	private Boolean visible;
	private Set<ClaseIndicadores> hijos;
	private String nombreUsuarioCreado;
	private String nombreUsuarioModificado;
	private String fechaCreado;
	private String fechaModificado;
	private Integer cantidadHijos;
	
	// Variables para copiar clase
	private Boolean copiarIndicadores;
	private Boolean copiarArbol;
	private Boolean copiarMediciones;
	private Boolean copiarInsumos;
	private Boolean copiarPlantillasGraficos;
	private Boolean copiarPlantillasReportes;
	private String nuevoNombre;
	private Long claseIdDestino;
	
	// Variables para mover clase
	private String claseSeleccion;
	private Long claseSeleccionId;

	public Long getClaseId()
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}
	
	public String getPadre() 
	{
		return this.padre;
	}

	public void setPadre(String padre) 
	{
		this.padre = padre;
	}
  
	public Long getPadreId() 
	{
		return this.padreId;
	}

	public void setPadreId(Long padreId) 
	{
		this.padreId = padreId;
	}
	
	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}	

	public String getDescripcion() 
	{
		return this.descripcion;
	}	

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public String getEnlaceParcial() 
	{
		return this.enlaceParcial;
	}

	public void setEnlaceParcial(String enlaceParcial) 
	{
		this.enlaceParcial = enlaceParcial;
	}

	public Byte getTipo() 
	{
		return this.tipo;
	}

	public void setTipo(Byte tipo) 
	{
		this.tipo = tipo;
	}

	public Boolean getVisible() 
	{
		return this.visible;
	}

	public void setVisible(Boolean visible) 
	{
		this.visible = visible;
	}

	public Set<ClaseIndicadores> getHijos() 
	{
		return this.hijos;
	}

	public void setHijos(Set<ClaseIndicadores> hijos) 
	{
		this.hijos = hijos;
	}

	public Integer getCantidadHijos() 
	{
		return this.cantidadHijos;
	}

	public void setCantidadHijos(Integer cantidadHijos) 
	{
		this.cantidadHijos = cantidadHijos;
	}

	public String getNombreUsuarioCreado() 
	{
		return this.nombreUsuarioCreado;
	}

	public void setNombreUsuarioCreado(String nombreUsuarioCreado) 
	{
		this.nombreUsuarioCreado = nombreUsuarioCreado;
	}

	public String getNombreUsuarioModificado() 
	{
		return this.nombreUsuarioModificado;
	}

	public void setNombreUsuarioModificado(String nombreUsuarioModificado) 
	{
		this.nombreUsuarioModificado = nombreUsuarioModificado;
	}

	public String getFechaCreado() 
	{
		return this.fechaCreado;
	}

	public void setFechaCreado(String fechaCreado) 
	{
		this.fechaCreado = fechaCreado;
	}

	public String getFechaModificado() 
	{
		return this.fechaModificado;
	}

	public void setFechaModificado(String fechaModificado) 
	{
		this.fechaModificado = fechaModificado;
	}

	public Boolean getCopiarIndicadores() 
	{
		if (this.copiarIndicadores == null) 
			this.copiarIndicadores = new Boolean(false);

		return this.copiarIndicadores;
	}

	public void setCopiarIndicadores(Boolean copiarIndicadores) 
	{
		this.copiarIndicadores = copiarIndicadores;
	}

	public Boolean getCopiarArbol() 
  	{
		if (this.copiarArbol == null) 
			this.copiarArbol = new Boolean(false);

		return this.copiarArbol;
  	}

	public void setCopiarArbol(Boolean copiarArbol) 
	{
		this.copiarArbol = copiarArbol;
	}

	public Boolean getCopiarMediciones() 
	{
		if (this.copiarMediciones == null) 
			this.copiarMediciones = new Boolean(false);

		return this.copiarMediciones;
	}

	public void setCopiarMediciones(Boolean copiarMediciones) 
	{
		this.copiarMediciones = copiarMediciones;
	}

	public Boolean getCopiarInsumos() 
	{
		if (this.copiarInsumos == null) 
			this.copiarInsumos = new Boolean(false);

		return this.copiarInsumos;
	}

	public void setCopiarInsumos(Boolean copiarInsumos) 
	{
		this.copiarInsumos = copiarInsumos;
	}
	
	public Boolean getCopiarPlantillasGraficos() 
	{
		if (this.copiarPlantillasGraficos == null) 
			this.copiarPlantillasGraficos = new Boolean(false);

		return this.copiarPlantillasGraficos;
	}

	public void setCopiarPlantillasGraficos(Boolean copiarPlantillasGraficos) 
	{
		this.copiarPlantillasGraficos = copiarPlantillasGraficos;
	}

	public Boolean getCopiarPlantillasReportes() 
	{
		if (this.copiarPlantillasReportes == null) 
			this.copiarPlantillasReportes = new Boolean(false);

		return this.copiarPlantillasReportes;
	}

	public void setCopiarPlantillasReportes(Boolean copiarPlantillasReportes) 
	{
		this.copiarPlantillasReportes = copiarPlantillasReportes;
	}

	public String getNuevoNombre() 
	{
		return this.nuevoNombre;
	}

	public void setNuevoNombre(String nuevoNombre) 
	{
		this.nuevoNombre = nuevoNombre;
	}
  
	public Long getClaseIdDestino()
	{
		return this.claseIdDestino;
	}

	public void setClaseIdDestino(Long claseIdDestino) 
	{
		this.claseIdDestino = claseIdDestino;
	}
 
	public Long getClaseSeleccionId()
	{
		return this.claseSeleccionId;
	}

	public void setClaseSeleccionId(Long claseSeleccionId) 
	{
		this.claseSeleccionId = claseSeleccionId;
	}
	
	public String getClaseSeleccion()
	{
		return this.claseSeleccion;
	}

	public void setClaseSeleccion(String claseSeleccion) 
	{
		this.claseSeleccion = claseSeleccion;
	}
	
	public void clear() 
	{
		this.claseId = new Long(0L);
		this.padre = null;
		this.padreId = new Long(0L);
		this.organizacionId = new Long(0L);
		this.nombre = null;
		this.descripcion = null;
		this.enlaceParcial = null;
		this.tipo = null;
		this.visible = new Boolean(true);
		this.hijos = null;
		setBloqueado(new Boolean(false));

		this.copiarIndicadores = new Boolean(false);
		this.copiarArbol = new Boolean(false);
		this.copiarMediciones = new Boolean(false);
		this.copiarInsumos = new Boolean(false);
		this.copiarPlantillasGraficos = new Boolean(false);
		this.copiarPlantillasReportes = new Boolean(false);
		this.nuevoNombre = "";
		
		this.claseSeleccionId = null;
		this.claseSeleccion = null;
		this.setStatus(StatusUtil.getStatusSuccess());
	}
}