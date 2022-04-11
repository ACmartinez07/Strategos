package com.visiongc.framework.web.struts.auditorias.forms;

import com.visiongc.framework.web.struts.forms.VisorGenericoForm;

import java.util.Date;
import java.util.List;

public class GestionarAuditoriasForm extends VisorGenericoForm
{
  static final long serialVersionUID = 0L;  
  
  private Long auditoriaId;
  private String fechaDesde;
  private String fechaHasta;
  private String usuario;
  private Long usuarioId;
  private String entidad;
  private String claseEntidad;
  private String tipoEvento;
  private String indicador;
  private String detalle;
  private List tiposEventos;
  
  public GestionarAuditoriasForm() {}

	public Long getAuditoriaId() {
		return auditoriaId;
	}
	
	public void setAuditoriaId(Long auditoriaId) {
		this.auditoriaId = auditoriaId;
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
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getEntidad() {
		return entidad;
	}
	
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	public String getClaseEntidad() {
		return claseEntidad;
	}
	
	public void setClaseEntidad(String claseEntidad) {
		this.claseEntidad = claseEntidad;
	}
	
	public String getTipoEvento() {
		return tipoEvento;
	}
	
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List getTiposEventos() {
		return tiposEventos;
	}

	public void setTiposEventos(List tiposEventos) {
		this.tiposEventos = tiposEventos;
	}
	 
}
