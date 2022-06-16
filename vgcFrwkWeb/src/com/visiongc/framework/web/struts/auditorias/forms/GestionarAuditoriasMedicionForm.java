package com.visiongc.framework.web.struts.auditorias.forms;

import com.visiongc.framework.web.struts.forms.VisorGenericoForm;

import java.util.Date;
import java.util.List;

public class GestionarAuditoriasMedicionForm extends VisorGenericoForm
{
  static final long serialVersionUID = 0L;
  
    private Long auditoriaMedicionId;
	private Date fechaEjecucion;
	private String accion;
	private String fechaDesde;
	private String fechaHasta;
	private List acciones;  
	private String organizacion;
	private Long organizacionId;	
	private String periodo;
	private String periodoFinal;
	private String usuario;
	private Long usuarioId;
	private String indicador;
	private String detalle;
	
	public Long getAuditoriaMedicionId() {
		return auditoriaMedicionId;
	}
	
	public void setAuditoriaMedicionId(Long auditoriaMedicionId) {
		this.auditoriaMedicionId = auditoriaMedicionId;
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
	
	public String getPeriodoFinal() {
		return periodoFinal;
	}
	
	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
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

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public List getAcciones() {
		return acciones;
	}

	public void setAcciones(List acciones) {
		this.acciones = acciones;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	
	public Long getOrganizacionId() {
		return this.organizacionId;
	}
  
}
