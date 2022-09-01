package com.visiongc.app.strategos.web.struts.instrumentos.forms;

import java.util.Date;
import java.util.List;

import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class EditarInstrumentosForm extends EditarObjetoForm
{
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
	private String fechaInicio;
	private String fechaInicioTexto;
	private String fechaTerminacion;
	private String fechaTerminacionTexto;
	private String fechaProrroga;
	private String fechaProrrogaTexto;
	private Byte alcance;

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
	private List<TipoConvenio> convenios;
	private List<Cooperante> cooperantes;
	
	private String fechaFinal;
	private String respuesta;
	private Integer ano;
	private Integer mesInicial;
	private Integer mesFinal;
	private List<Long> indicadores;
	private Long claseId;
	private Long organizacionId;
	private Long indicadorId;
	private Long iniciativaId;
	private String altoForma;

	public Byte getAlcance() {
		return alcance;
	}

	public void setAlcance(Byte alcance) {
		this.alcance = alcance;
	}

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
			
	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaInicioTexto() {
		return fechaInicioTexto;
	}

	public void setFechaInicioTexto(String fechaInicioTexto) {
		this.fechaInicioTexto = fechaInicioTexto;
	}

	public String getFechaTerminacion() {
		return fechaTerminacion;
	}

	public void setFechaTerminacion(String fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public String getFechaTerminacionTexto() {
		return fechaTerminacionTexto;
	}

	public void setFechaTerminacionTexto(String fechaTerminacionTexto) {
		this.fechaTerminacionTexto = fechaTerminacionTexto;
	}

	public String getFechaProrroga() {
		return fechaProrroga;
	}

	public void setFechaProrroga(String fechaProrroga) {
		this.fechaProrroga = fechaProrroga;
	}

	public String getFechaProrrogaTexto() {
		return fechaProrrogaTexto;
	}

	public void setFechaProrrogaTexto(String fechaProrrogaTexto) {
		this.fechaProrrogaTexto = fechaProrrogaTexto;
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
	
	public List<TipoConvenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<TipoConvenio> convenios) {
		this.convenios = convenios;
	}

	public List<Cooperante> getCooperantes() {
		return cooperantes;
	}

	public void setCooperantes(List<Cooperante> cooperantes) {
		this.cooperantes = cooperantes;
	}
		
	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMesInicial() {
		return mesInicial;
	}

	public void setMesInicial(Integer mesInicial) {
		this.mesInicial = mesInicial;
	}

	public Integer getMesFinal() {
		return mesFinal;
	}

	public void setMesFinal(Integer mesFinal) {
		this.mesFinal = mesFinal;
	}

	public List<Long> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Long> indicadores) {
		this.indicadores = indicadores;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public Long getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public String getAltoForma() {
		return altoForma;
	}

	public void setAltoForma(String altoForma) {
		this.altoForma = altoForma;
	}

	public void clear() {
		
		this.instrumentoId = new Long(0L);
		this.nombreCorto = null;
		this.nombreInstrumento = null;
		this.objetivoInstrumento = null;
		this.productos = null;
		this.cooperanteId = new Long(0L);
		this.tiposConvenioId= new Long(0L);
		this.anio = null;
		this.instrumentoMarco=null;
		this.fechaInicio = null;
		this.fechaTerminacion = null;
		this.fechaProrroga = null;
		this.recursosPesos = 0.0;
		this.recursosDolares = 0.0;
		this.nombreEjecutante = null;
		this.estatus = 0;
		this.areasCargo = null;
		this.nombreReposnsableAreas = null;
		this.responsableCgi = null;
		this.observaciones = null;
		this.cooperante = null;
		this.tipoConvenio = null;				
	}

	
}