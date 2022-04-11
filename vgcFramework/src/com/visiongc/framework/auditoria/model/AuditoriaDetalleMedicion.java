package com.visiongc.framework.auditoria.model;

import java.io.Serializable;

public class AuditoriaDetalleMedicion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long auditoriaMedicionId;
	private String serieNombre;
	private Integer ano;
	private Integer periodo;
	private double valor;
	private double valorAnterior;
	private String serie;
	private String accion;
	
	
	public Long getAuditoriaMedicionId() {
		return auditoriaMedicionId;
	}
	
	public void setAuditoriaMedicionId(Long auditoriaMedicionId) {
		this.auditoriaMedicionId = auditoriaMedicionId;
	}
	
	public String getSerieNombre() {
		return serieNombre;
	}
	
	public void setSerieNombre(String serieNombre) {
		this.serieNombre = serieNombre;
	}
	
	public Integer getAno() {
		return ano;
	}
	
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getValorAnterior() {
		return valorAnterior;
	}
	
	public void setValorAnterior(double valorAnterior) {
		this.valorAnterior = valorAnterior;
	}
	
	public String getSerie() {
		return serie;
	}
	
	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
