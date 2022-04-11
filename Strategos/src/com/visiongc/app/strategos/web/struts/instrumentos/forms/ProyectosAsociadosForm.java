package com.visiongc.app.strategos.web.struts.instrumentos.forms;

import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class ProyectosAsociadosForm extends EditarObjetoForm{
	
	private static final long serialVersionUID = 0L;
	private String nombreInstrumento; 
	private Long tiposConvenioId;
	private Cooperante cooperante;
	private String fechaInicio;
	private Integer estatus;
	
	private Byte tipoReporte;
	
	
	public String getNombreInstrumento() {
		return nombreInstrumento;
	}
	public void setNombreInstrumento(String nombreInstrumento) {
		this.nombreInstrumento = nombreInstrumento;
	}
	public Long getTiposConvenioId() {
		return tiposConvenioId;
	}
	public void setTiposConvenioId(Long tiposConvenioId) {
		this.tiposConvenioId = tiposConvenioId;
	}
	public Cooperante getCooperante() {
		return cooperante;
	}
	public void setCooperante(Cooperante cooperante) {
		this.cooperante = cooperante;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Integer getEstatus(Integer estatus) {
		return this.estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = ProyectosAsociadosForm.Estatus.getEstatus(estatus);
	}
	
	
	public Byte getTipoReporte() 
	{
		return this.tipoReporte;
	}

	public void setTipoReporte(Byte tipoReporte) 
	{
		this.tipoReporte = ProyectosAsociadosForm.TipoReportes.getTipoReportes(tipoReporte);
	}
	
	public void clear() {						
		this.nombreInstrumento = null;
		this.tiposConvenioId= new Long(0L);
		this.fechaInicio = null;
		this.estatus = null;
		this.cooperante = null;	
		this.tipoReporte = ProyectosAsociadosForm.TipoReportes.TIPO_PDF;
	}
	
	public static class TipoReportes
	{
		private static final byte TIPO_PDF = 1;
		private static final byte TIPO_EXCEL = 2;
		
		public static Byte getTipoReportes(Byte tipoReporte)
		{
			if (tipoReporte == TIPO_PDF)
				return new Byte(TIPO_PDF);
			else if (tipoReporte == TIPO_EXCEL)
				return new Byte(TIPO_EXCEL);
			else
				return null;
		}
	}
	
	public static class Estatus{
		private static final Integer ACTIVO = 1;
		private static final Integer INACTIVO = 0;
		
		public static Integer getEstatus(Integer estatus) {
			if (estatus == ACTIVO)
				return new Integer(ACTIVO);
			else if (estatus == INACTIVO)
				return new Integer(INACTIVO);
			else 
				return null;		
		}
	}
}
