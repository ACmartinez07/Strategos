package com.visiongc.framework.auditoria.model;

import java.io.Serializable;
import java.util.Date;

public class AuditoriaMedicion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long auditoriaMedicionId;
	private String sesion;
	private Date fechaEjecucion;
	private String accion;
	private String organizacion;
	private Long organizacionId;
	private String periodo;
	private String periodoFinal;
	private String usuario;
	private String indicador;
	private String clase;
	private String iniciativa;
	private Long indicadorId;
	private String detalle;
	
	
	public Long getAuditoriaMedicionId() {
		return auditoriaMedicionId;
	}
	
	public void setAuditoriaMedicionId(Long auditoriaMedicionId) {
		this.auditoriaMedicionId = auditoriaMedicionId;
	}
	
	public String getOrganizacion() {
		return organizacion;
	}
	
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getIndicador() {
		return indicador;
	}
	
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	public String getDetalle() {
		return detalle;
	}
	
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	public Long getOrganizacionId() {
		return organizacionId;
	}
	
	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	
	public String getPeriodoFinal() {
		return periodoFinal;
	}
	
	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	
	public Long getIndicadorId() {
		return indicadorId;
	}
	
	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
	
	public String getSesion() {
		return sesion;
	}
	
	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	
	public String getAccion() {
		return accion;
	}
	
	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(String iniciativa) {
		this.iniciativa = iniciativa;
	}

	public AuditoriaMedicion(){}
}
