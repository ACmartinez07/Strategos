package com.visiongc.app.strategos.indicadores.model;

import java.io.Serializable;

public class MedicionInsumo implements Serializable{
	static final long serialVersionUID = 0L;
	
	private Long indicadorId;
	private Long formulaId;
	private boolean inventario;
	private Long organizacionId;
	private Integer periodoInicial;
	private Integer periodoFinal;
	private Integer anoInicial;
	private Integer anoFinal;
	private Double resultado;
	
	public Long getIndicadorId() {
		return indicadorId;
	}
	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
	public Long getFormulaId() {
		return formulaId;
	}
	public void setFormulaId(Long formulaId) {
		this.formulaId = formulaId;
	}
	public boolean getInventario() {
		return inventario;
	}
	public void setInventario(boolean inventario) {
		this.inventario = inventario;
	}
	public Long getOrganizacionId() {
		return organizacionId;
	}
	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	public Integer getPeriodoInicial() {
		return periodoInicial;
	}
	public void setPeriodoInicial(Integer periodoInicial) {
		this.periodoInicial = periodoInicial;
	}
	public Integer getPeriodoFinal() {
		return periodoFinal;
	}
	public void setPeriodoFinal(Integer periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	public Integer getAnoInicial() {
		return anoInicial;
	}
	public void setAnoInicial(Integer anoInicial) {
		this.anoInicial = anoInicial;
	}
	public Integer getAnoFinal() {
		return anoFinal;
	}
	public void setAnoFinal(Integer anoFinal) {
		this.anoFinal = anoFinal;
	}
	public Double getResultado() {
		return resultado;
	}
	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}
	public MedicionInsumo(Long indicadorId, Long formulaId, boolean inventario,
			Long organizacionId, Integer periodoInicial, Integer periodoFinal,
			Integer anoInicial, Integer anoFinal, Double resultado) {
		super();
		this.indicadorId = indicadorId;
		this.formulaId = formulaId;
		this.inventario = inventario;
		this.organizacionId = organizacionId;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
		this.anoInicial = anoInicial;
		this.anoFinal = anoFinal;
		this.resultado = resultado;
	}
	
	public MedicionInsumo(){
		
	}
	
	public MedicionInsumo(Long indicadorId, Long formulaId, Long organizacionId){
		this.indicadorId = indicadorId;
		this.formulaId = formulaId;
		this.organizacionId = organizacionId;
	}
	
	
}
