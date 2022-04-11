package com.visiongc.app.strategos.web.struts.organizaciones.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class EditarOrganizacionForm extends ValidatorActionForm
{
	static final long serialVersionUID = 0L;
  
	private String codigoParcialEnlace;
	private Long organizacionId;
	private String organizacionSeleccion;
	private Long organizacionSeleccionId;
	private String nombre;
	private String rif;
  	private String padre;
  	private Long padreId;
  	private String creado;
  	private String modificado;
  	private Long creadoId;
  	private Long modificadoId;
  	private String direccion;
  	private String telefono;
  	private String fax;
  	private String enlaceParcial;
  	private Integer porcentajeZonaAmarillaMinMaxIndicadores;
  	private Integer porcentajeZonaVerdeMetaIndicadores;
  	private Integer porcentajeZonaAmarillaMetaIndicadores;
  	private Integer porcentajeZonaVerdeIniciativas;
  	private Integer porcentajeZonaAmarillaIniciativas;
  	private String descripcion;
  	private String observaciones;
  	private String personalDirectivo;
  	private String mision;
  	private String vision;
  	private String oportunidadesRetos;
  	private String lineamientosEstrategicos;
  	private String factoresClave;
  	private String politicas;
  	private String valores;
  	private Boolean bloqueado;
  	private Boolean enEdicion;
  	private Byte mesCierre;
  	private Boolean visible;
  	private String nombreUsuarioCreado;
  	private String nombreUsuarioModificado;
  	private Boolean soloLectura;
  	private Byte status;
  	private Long rootId;
  	
  	// Variables para copiar organizacion
  	private Boolean copiarIndicadores;
  	private Boolean copiarArbol;
  	private Boolean copiarMediciones;
  	private Boolean copiarInsumos;
  	private Boolean copiarPlantillasGraficos;
  	private Boolean copiarPlantillasReportes;
  	private Boolean copiarOrganizacionHija;
  	private Boolean copiarPlanes;
  	private Boolean copiarIniciativas;
  	private String nuevoNombre;
  	private Boolean showPresentacion;
  
  	// Variables para concatenar codigos de enlace
  	private Byte seleccion;
  	private Boolean concatenarCodigos;

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

  	public String getCreado() 
  	{
  		return this.creado;
  	}	

  	public void setCreado(String creado) 
  	{
  		this.creado = creado;
  	}

  	public String getModificado() 
  	{
  		return this.modificado;
  	}

  	public void setModificado(String modificado) 
  	{
  		this.modificado = modificado;
  	}

  	public Long getModificadoId() 
  	{
  		return this.modificadoId;
  	}

  	public void setModificadoId(Long modificadoId) 
  	{
  		this.modificadoId = modificadoId;
  	}

  	public Long getCreadoId() 
  	{
  		return this.creadoId;
  	}

  	public void setCreadoId(Long creadoId) 
  	{
  		this.creadoId = creadoId;
  	}	

  	public String getDireccion() 
  	{
  		return this.direccion;
  	}

  	public void setDireccion(String direccion) 
  	{
  		if ((direccion == null) || (direccion.trim().equals("")))
  			this.direccion = null;
  		else
  			this.direccion = direccion.trim();
  	}

  	public String getTelefono()
  	{
  		return this.telefono;
  	}

  	public void setTelefono(String telefono) 
  	{
  		if ((telefono == null) || (telefono.trim().equals("")))
  			this.telefono = null;
  		else
  			this.telefono = telefono.trim();
  	}

  	public String getFax()
  	{
  		return this.fax;
  	}

  	public void setFax(String fax) 
  	{
  		if ((fax == null) || (fax.trim().equals("")))
  			this.fax = null;
  		else
  			this.fax = fax.trim();
  	}

  	public String getEnlaceParcial()
  	{
  		return this.enlaceParcial;
  	}

  	public void setEnlaceParcial(String enlaceParcial) 
  	{
  		if ((enlaceParcial == null) || (enlaceParcial.trim().equals("")))
  			this.enlaceParcial = null;
  		else
  			this.enlaceParcial = enlaceParcial.trim();
  	}

  	public Integer getPorcentajeZonaAmarillaMetaIndicadores()
  	{
  		return this.porcentajeZonaAmarillaMetaIndicadores;
  	}

  	public void setPorcentajeZonaAmarillaMetaIndicadores(Integer porcentajeZonaAmarillaMetaIndicadores) 
  	{
  		this.porcentajeZonaAmarillaMetaIndicadores = porcentajeZonaAmarillaMetaIndicadores;
  	}

  	public Integer getPorcentajeZonaAmarillaMinMaxIndicadores() 
  	{
  		return this.porcentajeZonaAmarillaMinMaxIndicadores;
  	}

  	public void setPorcentajeZonaAmarillaMinMaxIndicadores(Integer porcentajeZonaAmarillaMinMaxIndicadores) 
  	{
  		this.porcentajeZonaAmarillaMinMaxIndicadores = porcentajeZonaAmarillaMinMaxIndicadores;
  	}

  	public Integer getPorcentajeZonaVerdeMetaIndicadores() 
  	{
  		return this.porcentajeZonaVerdeMetaIndicadores;
  	}

  	public void setPorcentajeZonaVerdeMetaIndicadores(Integer porcentajeZonaVerdeMetaIndicadores) 
  	{
  		this.porcentajeZonaVerdeMetaIndicadores = porcentajeZonaVerdeMetaIndicadores;
  	}

  	public Integer getPorcentajeZonaAmarillaIniciativas() 
  	{
  		return this.porcentajeZonaAmarillaIniciativas;
  	}

  	public void setPorcentajeZonaAmarillaIniciativas(Integer porcentajeZonaAmarillaIniciativas) 
  	{
  		this.porcentajeZonaAmarillaIniciativas = porcentajeZonaAmarillaIniciativas;
  	}

  	public Integer getPorcentajeZonaVerdeIniciativas() 
  	{
  		return this.porcentajeZonaVerdeIniciativas;
  	}

  	public void setPorcentajeZonaVerdeIniciativas(Integer porcentajeZonaVerdeIniciativas) 
  	{
  		this.porcentajeZonaVerdeIniciativas = porcentajeZonaVerdeIniciativas;
  	}

  	public String getDescripcion() 
  	{
  		return this.descripcion;
  	}

  	public void setDescripcion(String descripcion) 
  	{
  		if ((descripcion == null) || (descripcion.trim().equals("")))
  			this.descripcion = null;
  		else
  			this.descripcion = descripcion.trim();
  	}

  	public String getObservaciones()
  	{
  		return this.observaciones;
  	}

  	public void setObservaciones(String observaciones) 
  	{
  		if ((observaciones == null) || (observaciones.trim().equals("")))
  			this.observaciones = null;
  		else
  			this.observaciones = observaciones.trim();
  	}

  	public String getPersonalDirectivo()
  	{
  		return this.personalDirectivo;
  	}

  	public void setPersonalDirectivo(String personalDirectivo) 
  	{
  		if ((personalDirectivo == null) || (personalDirectivo.trim().equals("")))
  			this.personalDirectivo = null;
  		else
  			this.personalDirectivo = personalDirectivo.trim();
  	}

  	public String getMision()
  	{
  		return this.mision;
  	}

  	public void setMision(String mision) 
  	{
  		if ((mision == null) || (mision.trim().equals("")))
  			this.mision = null;
  		else
  			this.mision = mision.trim();
  	}

  	public String getVision()
  	{
  		return this.vision;
  	}

  	public void setVision(String vision) 
  	{
  		if ((vision == null) || (vision.trim().equals("")))
  			this.vision = null;
  		else
  			this.vision = vision.trim();
  	}

  	public String getOportunidadesRetos()
  	{
  		return this.oportunidadesRetos;
  	}

  	public void setOportunidadesRetos(String oportunidadesRetos) 
  	{
  		if ((oportunidadesRetos == null) || (oportunidadesRetos.trim().equals("")))
  			this.oportunidadesRetos = null;
  		else
  			this.oportunidadesRetos = oportunidadesRetos.trim();
  	}

  	public String getLineamientosEstrategicos()
  	{
  		return this.lineamientosEstrategicos;
  	}

  	public void setLineamientosEstrategicos(String lineamientosEstrategicos) 
  	{
  		if ((lineamientosEstrategicos == null) || (lineamientosEstrategicos.trim().equals("")))
  			this.lineamientosEstrategicos = null;
  		else
  			this.lineamientosEstrategicos = lineamientosEstrategicos.trim();
  	}

  	public String getFactoresClave()
  	{
  		return this.factoresClave;
  	}

  	public void setFactoresClave(String factoresClave) 
  	{
  		if ((factoresClave == null) || (factoresClave.trim().equals("")))
  			this.factoresClave = null;
  		else
  			this.factoresClave = factoresClave.trim();
  	}

  	public String getPoliticas()
  	{
  		return this.politicas;
  	}

  	public void setPoliticas(String politicas) 
  	{
  		if ((politicas == null) || (politicas.trim().equals("")))
  			this.politicas = null;
  		else
  			this.politicas = politicas.trim();
  	}

  	public String getValores()
  	{
  		return this.valores;
  	}

  	public void setValores(String valores) 
  	{
  		if ((valores == null) || (valores.trim().equals("")))
  			this.valores = null;
  		else
  			this.valores = valores.trim();
  	}

  	public Boolean getBloqueado()
  	{
  		return this.bloqueado;
  	}

  	public void setBloqueado(Boolean bloqueado) 
  	{
  		this.bloqueado = bloqueado;
  	}	

  	public Boolean getEnEdicion() 
  	{
  		return this.enEdicion;
  	}

  	public void setEnEdicion(Boolean enEdicion) 
  	{
  		this.enEdicion = enEdicion;
  	}

  	public Byte getMesCierre() 
  	{
  		return this.mesCierre;
  	}

  	public void setMesCierre(Byte mesCierre) 
  	{
  		this.mesCierre = mesCierre;
  	}

  	public String getRif() 
  	{
  		return this.rif;
  	}

  	public void setRif(String rif) 
  	{
  		this.rif = rif;
  	}

  	public String getCodigoParcialEnlace() 
  	{
  		return this.codigoParcialEnlace;
  	}

  	public void setCodigoParcialEnlace(String codigoParcialEnlace) 
  	{
  		this.codigoParcialEnlace = codigoParcialEnlace;
  	}	

  	public Boolean getVisible() 
  	{
  		return this.visible;
  	}

  	public void setVisible(Boolean visible) 
  	{
  		this.visible = visible;
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

  	public Boolean getSoloLectura() 
  	{
  		if (this.soloLectura == null) 
  			this.soloLectura = new Boolean(false);

  		return this.soloLectura;
  	}

  	public void setSoloLectura(Boolean soloLectura) 
  	{
  		this.soloLectura = soloLectura;
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

  	public Boolean getCopiarOrganizacionHija() 
  	{
  		if (this.copiarOrganizacionHija == null) 
  			this.copiarOrganizacionHija = new Boolean(false);
  		
  		return this.copiarOrganizacionHija;
  	}

  	public void setCopiarOrganizacionHija(Boolean copiarOrganizacionHija) 
  	{
  		this.copiarOrganizacionHija = copiarOrganizacionHija;
  	}

  	public Boolean getCopiarPlanes() 
  	{
  		if (this.copiarPlanes == null) 
  			this.copiarPlanes = new Boolean(false);

  		return this.copiarPlanes;
  	}

  	public void setCopiarPlanes(Boolean copiarPlanes) 
  	{
  		this.copiarPlanes = copiarPlanes;
  	}

  	public Boolean getCopiarIniciativas() 
  	{
  		if (this.copiarIniciativas == null) 
  			this.copiarIniciativas = new Boolean(false);

  		return this.copiarIniciativas;
  	}

  	public void setCopiarIniciativas(Boolean copiarIniciativas) 
  	{
  		this.copiarIniciativas = copiarIniciativas;
  	}
  	
  	public String getNuevoNombre() 
  	{
  		return this.nuevoNombre;
  	}
  	
  	public void setNuevoNombre(String nuevoNombre) 
  	{
  		this.nuevoNombre = nuevoNombre;
  	}

  	public Byte getSeleccion() 
  	{
  		return this.seleccion;
  	}
  	
  	public void setSeleccion(Byte seleccion) 
  	{
  		this.seleccion = seleccion;
  	}
  	
  	public Boolean getConcatenarCodigos() 
  	{
  		return this.concatenarCodigos;
  	}

  	public void setConcatenarCodigos(Boolean concatenarCodigos) 
  	{
  		this.concatenarCodigos = concatenarCodigos;
  	}	
  
  	public Long getOrganizacionSeleccionId()
  	{
  		return this.organizacionSeleccionId;
  	}

  	public void setOrganizacionSeleccionId(Long organizacionSeleccionId) 
  	{
  		this.organizacionSeleccionId = organizacionSeleccionId;
  	}
  
  	public String getOrganizacionSeleccion()
  	{
  		return this.organizacionSeleccion;
  	}

  	public void setOrganizacionSeleccion(String organizacionSeleccion) 
  	{
  		this.organizacionSeleccion = organizacionSeleccion;
  	}
  
  	public Byte getStatus()
  	{
  		return this.status;
  	}

  	public void setStatus(Byte status) 
  	{
  		this.status = status;
  	}

  	public Long getRootId()
  	{
  		return this.rootId;
  	}

  	public void setRootId(Long rootId) 
  	{
  		this.rootId = rootId;
  	}
  
	public Boolean getShowPresentacion() 
	{
		return this.showPresentacion;
	}

	public void setShowPresentacion(Boolean showPresentacion) 
	{
		this.showPresentacion = showPresentacion;
	}
  
	public void clear() 
	{
		this.porcentajeZonaVerdeMetaIndicadores = null;
		this.porcentajeZonaAmarillaMetaIndicadores = null;
		this.porcentajeZonaAmarillaMinMaxIndicadores = null;
		this.porcentajeZonaVerdeIniciativas = null;
		this.porcentajeZonaAmarillaIniciativas = null;
    	this.bloqueado = new Boolean(false);
    	this.creado = null;
    	this.creadoId = null;
    	this.descripcion = null;
    	this.direccion = null;
    	this.enEdicion = new Boolean(false);
    	this.enlaceParcial = null;
    	this.factoresClave = null;
    	this.fax = null;
    	this.lineamientosEstrategicos = null;
    	this.mision = null;
    	this.modificado = null;
    	this.modificadoId = null;
    	this.nombre = null;
    	this.observaciones = null;
    	this.oportunidadesRetos = null;
    	this.organizacionId = null;
    	this.padre = null;
    	this.padreId = null;
    	this.personalDirectivo = null;
    	this.politicas = null;
    	this.telefono = null;
    	this.valores = null;
    	this.vision = null;
    	this.mesCierre = new Byte("12");
    	this.rif = null;
    	this.visible = new Boolean(true);
    	
    	this.copiarIndicadores = new Boolean(false);
    	this.copiarIniciativas = new Boolean(false);
    	this.copiarArbol = new Boolean(false);
    	this.copiarMediciones = new Boolean(false);
    	this.copiarInsumos = new Boolean(false);
    	this.copiarPlantillasGraficos = new Boolean(false);
    	this.copiarPlantillasReportes = new Boolean(false);
    	this.copiarOrganizacionHija = new Boolean(false);
    	this.nuevoNombre = "";
    	
    	this.concatenarCodigos = new Boolean(false);;
	}
}