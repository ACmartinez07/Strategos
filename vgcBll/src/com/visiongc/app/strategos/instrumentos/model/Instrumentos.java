package com.visiongc.app.strategos.instrumentos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativa;
import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativaPK;
import com.visiongc.app.strategos.model.util.Frecuencia;

public class Instrumentos implements Serializable {

	static final long serialVersionUID = 0L;

	private Long instrumentoId;
	private String nombreCorto;
	private String nombreInstrumento;
	private String objetivoInstrumento;
	private String productos;
	private Long cooperanteId;
	private Long tiposConvenioId;
	private String anio;
	private String instrumentoMarco;
	private Date fechaInicio;
	private Date fechaTerminacion;
	private Date fechaProrroga;
	private String fechaInicioTexto;
	private String fechaTerminacionTexto;
	private String fechaProrrogaTexto;
	private Double recursosPesos;
	private Double recursosDolares;
	private String nombreEjecutante;
	private Byte estatus;
	private String areasCargo;
	private String nombreReposnsableAreas;
	private String responsableCgi;
	private String observaciones;
	private Cooperante cooperante;
	private TipoConvenio tipoConvenio;
	private Byte frecuencia;
	private Long claseId;
	private Byte tipoMedicion;
	
	private InstrumentoPeso instrumentoPeso;
	private Double peso;

	private Set<IndicadorInstrumento> instrumentoIndicadores;

	public Long getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getNombreInstrumento() {
		return nombreInstrumento;
	}

	public void setNombreInstrumento(String nombreInstrumento) {
		this.nombreInstrumento = nombreInstrumento;
	}

	public String getObjetivoInstrumento() {
		return objetivoInstrumento;
	}

	public void setObjetivoInstrumento(String objetivoInstrumento) {
		this.objetivoInstrumento = objetivoInstrumento;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	public Long getCooperanteId() {
		return cooperanteId;
	}

	public void setCooperanteId(Long cooperanteId) {
		this.cooperanteId = cooperanteId;
	}

	public Long getTiposConvenioId() {
		return tiposConvenioId;
	}

	public void setTiposConvenioId(Long tiposConvenioId) {
		this.tiposConvenioId = tiposConvenioId;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getInstrumentoMarco() {
		return instrumentoMarco;
	}

	public void setInstrumentoMarco(String instrumentoMarco) {
		this.instrumentoMarco = instrumentoMarco;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTerminacion() {
		return fechaTerminacion;
	}

	public void setFechaTerminacion(Date fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public Date getFechaProrroga() {
		return fechaProrroga;
	}

	public void setFechaProrroga(Date fechaProrroga) {
		this.fechaProrroga = fechaProrroga;
	}

	public Double getRecursosPesos() {
		return recursosPesos;
	}

	public void setRecursosPesos(Double recursosPesos) {
		this.recursosPesos = recursosPesos;
	}

	public Double getRecursosDolares() {
		return recursosDolares;
	}

	public void setRecursosDolares(Double recursosDolares) {
		this.recursosDolares = recursosDolares;
	}

	public String getNombreEjecutante() {
		return nombreEjecutante;
	}

	public void setNombreEjecutante(String nombreEjecutante) {
		this.nombreEjecutante = nombreEjecutante;
	}

	public Byte getEstatus() {
		return estatus;
	}

	public void setEstatus(Byte estatus) {
		this.estatus = estatus;
	}

	public String getAreasCargo() {
		return areasCargo;
	}

	public void setAreasCargo(String areasCargo) {
		this.areasCargo = areasCargo;
	}

	public String getNombreReposnsableAreas() {
		return nombreReposnsableAreas;
	}

	public void setNombreReposnsableAreas(String nombreReposnsableAreas) {
		this.nombreReposnsableAreas = nombreReposnsableAreas;
	}

	public String getResponsableCgi() {
		return responsableCgi;
	}

	public void setResponsableCgi(String responsableCgi) {
		this.responsableCgi = responsableCgi;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Cooperante getCooperante() {
		return cooperante;
	}

	public void setCooperante(Cooperante cooperante) {
		this.cooperante = cooperante;
	}

	public TipoConvenio getTipoConvenio() {
		return tipoConvenio;
	}

	public void setTipoConvenio(TipoConvenio tipoConvenio) {
		this.tipoConvenio = tipoConvenio;
	}

	public String getFechaInicioTexto() {
		return fechaInicioTexto;
	}

	public void setFechaInicioTexto(String fechaInicioTexto) {
		this.fechaInicioTexto = fechaInicioTexto;
	}

	public String getFechaTerminacionTexto() {
		return fechaTerminacionTexto;
	}

	public void setFechaTerminacionTexto(String fechaTerminacionTexto) {
		this.fechaTerminacionTexto = fechaTerminacionTexto;
	}

	public String getFechaProrrogaTexto() {
		return fechaProrrogaTexto;
	}

	public void setFechaProrrogaTexto(String fechaProrrogaTexto) {
		this.fechaProrrogaTexto = fechaProrrogaTexto;
	}

	public Set<IndicadorInstrumento> getInstrumentoIndicadores() {
		return instrumentoIndicadores;
	}

	public void setInstrumentoIndicadores(Set<IndicadorInstrumento> instrumentoIndicadores) {
		this.instrumentoIndicadores = instrumentoIndicadores;
	}

	public Long getIndicadorId(Byte tipo) {
		for (Iterator<IndicadorInstrumento> iter = this.instrumentoIndicadores.iterator(); iter.hasNext();) {
			IndicadorInstrumento instrumentoIndicadores = (IndicadorInstrumento) iter.next();
			if (instrumentoIndicadores.getTipo().byteValue() == tipo.byteValue()) {
				return instrumentoIndicadores.getPk().getIndicadorId();
			}
		}
		return null;
	}

	public void setIndicadorId(Long indicadorId, Byte tipo) {
		boolean indicadorExiste = false;

		if (this.instrumentoIndicadores != null) {
			for (Iterator<IndicadorInstrumento> iter = this.instrumentoIndicadores.iterator(); iter.hasNext();) {
				IndicadorInstrumento instrumentoIndicadores = (IndicadorInstrumento) iter.next();
				if (instrumentoIndicadores.getTipo().byteValue() == tipo.byteValue()) {
					indicadorExiste = true;
					instrumentoIndicadores.getPk().setIndicadorId(indicadorId);
					instrumentoIndicadores.getPk().setInstrumentoId(instrumentoId);
					break;
				}
			}
		}

		if (!indicadorExiste) {
			IndicadorInstrumentoPK pk = new IndicadorInstrumentoPK(instrumentoId, indicadorId);
			IndicadorInstrumento instrumentoIndicadores = new IndicadorInstrumento();
			instrumentoIndicadores.setTipo(tipo);
			instrumentoIndicadores.setPk(pk);
			if (this.instrumentoIndicadores == null) {
				this.instrumentoIndicadores = new HashSet();
			}
			this.instrumentoIndicadores.add(instrumentoIndicadores);
		}
	}

	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Byte getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(Byte tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public InstrumentoPeso getInstrumentoPeso() {
		return instrumentoPeso;
	}

	public void setInstrumentoPeso(InstrumentoPeso instrumentoPeso) {
		this.instrumentoPeso = instrumentoPeso;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

		
}
