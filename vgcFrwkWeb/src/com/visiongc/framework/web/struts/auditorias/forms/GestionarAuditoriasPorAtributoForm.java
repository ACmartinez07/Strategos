package com.visiongc.framework.web.struts.auditorias.forms;

import com.visiongc.framework.web.struts.forms.VisorGenericoForm;
import java.util.List;

public class GestionarAuditoriasPorAtributoForm extends VisorGenericoForm
{
  static final long serialVersionUID = 0L;
 
  private String fechaDesde;
  private String fechaHasta;
  private String tipoEvento;
  private List tiposEventos;
  private Long auditoriaId;
  private List atributos;
  private List valores;
  private String entidad;
  private Long usuarioId;
  private String usuarioNombre;
  
  public GestionarAuditoriasPorAtributoForm() {}

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
	
	public String getTipoEvento() {
		return tipoEvento;
	}
	
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public List getTiposEventos() {
		return tiposEventos;
	}
	
	public void setTiposEventos(List tiposEventos) {
		this.tiposEventos = tiposEventos;
	}
	
	public Long getAuditoriaId() {
		return auditoriaId;
	}
	
	public void setAuditoriaId(Long auditoriaId) {
		this.auditoriaId = auditoriaId;
	}
	
	public List getAtributos() {
		return atributos;
	}
	
	public void setAtributos(List atributos) {
		this.atributos = atributos;
	}
	
	public List getValores() {
		return valores;
	}
	
	public void setValores(List valores) {
		this.valores = valores;
	}
	
	public String getEntidad() {
		return entidad;
	}
	
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public String getUsuarioNombre() {
		return usuarioNombre;
	}
	
	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}
  
  
}
