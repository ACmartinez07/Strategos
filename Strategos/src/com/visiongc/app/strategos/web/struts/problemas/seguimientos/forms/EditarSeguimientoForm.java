package com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarSeguimientoForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long seguimientoId;
	private Long estadoId;
	private String nombreEstado;
	private Long accionId;
	private String nombreAccion;
	private String nota;
	private String fechaEmision;
  	private Boolean emisionEnviado;
  	private String fechaRecepcion;
  	private Boolean recepcionEnviado;
  	private String fechaAprobacion;
  	private String preparadoPor;
  	private Integer numeroReporte;
  	private String aprobadoPor;
  	private Boolean aprobado;
  	private String claveCorreo;
  	private Long creadoId;
  	private Long modificadoId;
  	private String fechaCreado;
  	private String fechaModificado;
  	private String nombreUsuarioCreado;
  	private String nombreUsuarioModificado;
  	private String nombreSupervisor;
  	private String nombreResponsable;
  	private String memoResumen;
  	private String memoComentario;
  	private Boolean verSeguimiento;
  	private Boolean soloLectura;

  	public Boolean getVerSeguimiento()
  	{
  		return this.verSeguimiento;
  	}

  	public void setVerSeguimiento(Boolean verSeguimiento) 
  	{
  		this.verSeguimiento = verSeguimiento;
  	}

  	public Long getSeguimientoId() 
  	{
  		return this.seguimientoId;
  	}

  	public void setSeguimientoId(Long seguimientoId) 
  	{
  		this.seguimientoId = seguimientoId;
  	}

  	public Long getEstadoId() 
  	{
  		return this.estadoId;
  	}

  	public void setEstadoId(Long estadoId) 
  	{
  		this.estadoId = estadoId;
  	}

  	public String getNombreEstado() 
  	{
  		return this.nombreEstado;
  	}

  	public void setNombreEstado(String nombreEstado) 
  	{
  		this.nombreEstado = nombreEstado;
  	}

  	public Long getAccionId() 
  	{
  		return this.accionId;
  	}

  	public void setAccionId(Long accionId) 
  	{
  		this.accionId = accionId;
  	}

  	public String getNombreAccion() 
  	{
  		return this.nombreAccion;
  	}

  	public void setNombreAccion(String nombreAccion) 
  	{
  		this.nombreAccion = nombreAccion;
  	}

  	public String getNota() 
  	{
  		return this.nota;
  	}
  	
  	public void setNota(String nota) 
  	{
  		this.nota = nota;
  	}

  	public String getFechaEmision() 
  	{
  		return this.fechaEmision;
  	}

  	public void setFechaEmision(String fechaEmision) 
  	{
  		this.fechaEmision = fechaEmision;
  	}

  	public Boolean getEmisionEnviado() 
  	{
  		return this.emisionEnviado;
  	}

  	public void setEmisionEnviado(Boolean emisionEnviado) 
  	{
  		this.emisionEnviado = emisionEnviado;
  	}

  	public String getFechaRecepcion() 
  	{
  		return this.fechaRecepcion;
  	}

  	public void setFechaRecepcion(String fechaRecepcion) 
  	{
  		this.fechaRecepcion = fechaRecepcion;
  	}

  	public Boolean getRecepcionEnviado() 
  	{
  		return this.recepcionEnviado;
  	}

  	public void setRecepcionEnviado(Boolean recepcionEnviado) 
  	{
  		this.recepcionEnviado = recepcionEnviado;
  	}

  	public String getFechaAprobacion() 
  	{
  		return this.fechaAprobacion;
  	}

  	public void setFechaAprobacion(String fechaAprobacion) 
  	{
  		this.fechaAprobacion = fechaAprobacion;
  	}

  	public String getPreparadoPor() 
  	{
  		return this.preparadoPor;
  	}

  	public void setPreparadoPor(String preparadoPor) 
  	{
  		this.preparadoPor = preparadoPor;
  	}

  	public Integer getNumeroReporte() 
  	{
  		return this.numeroReporte;
  	}

  	public void setNumeroReporte(Integer numeroReporte) 
  	{
  		this.numeroReporte = numeroReporte;
  	}

  	public String getAprobadoPor() 
  	{
  		return this.aprobadoPor;
  	}

  	public void setAprobadoPor(String aprobadoPor) 
  	{
  		this.aprobadoPor = aprobadoPor;
  	}

  	public Boolean getAprobado() 
  	{
  		return this.aprobado;
  	}

  	public void setAprobado(Boolean aprobado) 
  	{
  		this.aprobado = aprobado;
  	}

  	public String getClaveCorreo() 
  	{
  		return this.claveCorreo;
  	}

  	public void setClaveCorreo(String claveCorreo) 
  	{
  		this.claveCorreo = claveCorreo;
  	}

  	public Long getCreadoId() 
  	{
  		return this.creadoId;
  	}

  	public void setCreadoId(Long creadoId) 
  	{
  		this.creadoId = creadoId;
  	}

  	public Long getModificadoId() 
  	{
  		return this.modificadoId;
  	}

  	public void setModificadoId(Long modificadoId) 
  	{
  		this.modificadoId = modificadoId;
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

  	public String getNombreSupervisor() 
  	{
  		return this.nombreSupervisor;
  	}

  	public void setNombreSupervisor(String nombreSupervisor) 
  	{
  		this.nombreSupervisor = nombreSupervisor;
  	}

  	public String getNombreResponsable() 
  	{
  		return this.nombreResponsable;
  	}

  	public void setNombreResponsable(String nombreResponsable) 
  	{
  		this.nombreResponsable = nombreResponsable;
  	}

  	public String getMemoResumen() 
  	{
  		return this.memoResumen;
  	}

  	public void setMemoResumen(String memoResumen) 
  	{
  		this.memoResumen = memoResumen;
  	}

  	public String getMemoComentario() 
  	{
  		return this.memoComentario;
  	}

  	public void setMemoComentario(String memoComentario) 
  	{
  		this.memoComentario = memoComentario;
  	}
  
  	public Boolean getSoloLectura()
  	{
  		return this.soloLectura;
  	}

  	public void setSoloLectura(Boolean soloLectura) 
  	{
  		this.soloLectura = soloLectura;
  	}

  	public void clear() 
  	{
  		this.seguimientoId = new Long(0L);
  		this.estadoId = null;
  		this.nombreEstado = null;
  		this.accionId = null;
  		this.nombreAccion = null;
  		this.nota = null;
  		this.fechaEmision = null;
  		this.emisionEnviado = null;
  		this.fechaRecepcion = null;
  		this.recepcionEnviado = null;
  		this.fechaAprobacion = null;
  		this.preparadoPor = null;
  		this.numeroReporte = null;
  		this.aprobadoPor = null;
  		this.aprobado = null;
  		this.claveCorreo = null;
  		this.creadoId = null;
  		this.modificadoId = null;
  		this.fechaCreado = null;
  		this.fechaModificado = null;
  		this.nombreUsuarioCreado = null;
  		this.nombreUsuarioModificado = null;
  		this.nombreSupervisor = null;
  		this.nombreResponsable = null;
  		this.memoResumen = null;
  		this.memoComentario = null;
  		this.verSeguimiento = new Boolean(false);
  		this.soloLectura = new Boolean(false);
  	}
}