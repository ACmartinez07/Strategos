package com.visiongc.framework.auditoria.model;

import java.util.Date;

import com.visiongc.commons.util.VgcFormatter;

public class Auditoria {

	private Long auditoriaId;
	private Date fechaEjecucion;
	private String fechaFormateada;
	private String usuario;
	private String entidad;
	private String claseEntidad;
	private String tipoEvento;
	private String detalle;
	
	
			
	public Long getAuditoriaId() {
		return auditoriaId;
	}

	public void setAuditoriaId(Long auditoriaId) {
		this.auditoriaId = auditoriaId;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
		
	public String getDetalle() {
		return detalle;
	}
	
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getClaseEntidad() {
		return claseEntidad;
	}

	public void setClaseEntidad(String claseEntidad) {
		this.claseEntidad = claseEntidad;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getFechaFormateada() {
		return fechaFormateada;
	}

	public void setFechaFormateada(String fechaFormateada) {
		this.fechaFormateada = fechaFormateada;
	}
	
	
	
	
}
