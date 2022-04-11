package com.visiongc.framework.web.struts.auditorias.forms;

import java.util.Date;
import java.util.List;

import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;

public class ReporteAuditoriaForm extends EditarObjetoForm{
	
	static final long serialVersionUID = 0L;
	
	private Long organizacionId;
	private List<OrganizacionStrategos> organizaciones;
	private Date fechaInicio;
	private Date fechaFinal;
	private String anoInicial;
	private String anoFinal;
	private String mesInicial;
	private String mesFinal;

	private String fechaDesde;
	private String fechaHasta;

	private Byte alcance;
	private String nombreOrganizacion;
	private List<ObjetoClaveValor> grupoAnos;
	private List<ObjetoClaveValor> grupoMeses;
	
	private String reporteSeleccion;
	private String reporteNombre;
	private Long reporteSeleccionId;
	
	private String respuesta;
	private String className;
	
	private Byte tipoReporte;
	private String filtroNombre;
	private FiltroForm filtro = new FiltroForm();
	private String source;
	
	//Campos para comunicacion
	private String valoresSeleccionados = null;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;
	
	// Campos para JavaScript
	private Byte orientacion;
	private String titulo;
	
		

	
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


	public List<ObjetoClaveValor> getGrupoAnos() {
		return grupoAnos;
	}

	public void setGrupoAnos(List<ObjetoClaveValor> grupoAnos) {
		this.grupoAnos = grupoAnos;
	}

	public List<ObjetoClaveValor> getGrupoMeses() {
		return grupoMeses;
	}

	public void setGrupoMeses(List<ObjetoClaveValor> grupoMeses) {
		this.grupoMeses = grupoMeses;
	}

	public String getNombreOrganizacion() {
		return nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion) {
		this.nombreOrganizacion = nombreOrganizacion;
	}

	public Byte getAlcance()
  	{
  		return this.alcance;
  	}

  	public void setAlcance(Byte alcance) 
  	{
  		this.alcance = ReporteAuditoriaForm.AlcanceStatus.getAlcanceStatus(alcance);
  	}
  	
  	public Byte getAlcanceObjetivo() 
	{
		return new Byte(AlcanceStatus.ALCANCE_OBJETIVO);
	}

	public Byte getAlcancePlan() 
	{
		return new Byte(AlcanceStatus.ALCANCE_PLAN);
	}

	public Byte getAlcanceOrganizacion() 
	{
		return new Byte(AlcanceStatus.ALCANCE_ORGANIZACION);
	}
	
	public Byte getAlcanceSubOrganizacion() 
	{
		return new Byte(AlcanceStatus.ALCANCE_SUB_ORGANIZACION);
	}


	public Byte getPeriodoAlCorte() 
	{
		return new Byte(PeriodoStatus.PERIODO_ALCORTE);
	}

	public Byte getPeriodoPorPeriodo() 
	{
		return new Byte(PeriodoStatus.PERIODO_PORPERIODO);
	}
		
	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public List<OrganizacionStrategos> getOrganizaciones() {
		return organizaciones;
	}

	public void setOrganizaciones(List<OrganizacionStrategos> organizaciones) {
		this.organizaciones = organizaciones;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	
		
	public String getAnoInicial() {
		return anoInicial;
	}

	public void setAnoInicial(String anoInicial) {
		this.anoInicial = anoInicial;
	}

	public String getAnoFinal() {
		return anoFinal;
	}

	public void setAnoFinal(String anoFinal) {
		this.anoFinal = anoFinal;
	}

	public String getMesInicial() {
		return mesInicial;
	}

	public void setMesInicial(String mesInicial) {
		this.mesInicial = mesInicial;
	}

	public String getMesFinal() {
		return mesFinal;
	}

	public void setMesFinal(String mesFinal) {
		this.mesFinal = mesFinal;
	}

	public String getReporteSeleccion() {
		return reporteSeleccion;
	}

	public void setReporteSeleccion(String reporteSeleccion) {
		this.reporteSeleccion = reporteSeleccion;
	}

	public String getReporteNombre() {
		return reporteNombre;
	}

	public void setReporteNombre(String reporteNombre) {
		this.reporteNombre = reporteNombre;
	}

	public Long getReporteSeleccionId() {
		return reporteSeleccionId;
	}

	public void setReporteSeleccionId(Long reporteSeleccionId) {
		this.reporteSeleccionId = reporteSeleccionId;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Byte getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Byte tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public String getFiltroNombre() {
		return filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) {
		this.filtroNombre = filtroNombre;
	}

	public FiltroForm getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroForm filtro) {
		this.filtro = filtro;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getValoresSeleccionados() {
		return valoresSeleccionados;
	}

	public void setValoresSeleccionados(String valoresSeleccionados) {
		this.valoresSeleccionados = valoresSeleccionados;
	}

	public String getNombreForma() {
		return nombreForma;
	}

	public void setNombreForma(String nombreForma) {
		this.nombreForma = nombreForma;
	}

	public String getNombreCampoValor() {
		return nombreCampoValor;
	}

	public void setNombreCampoValor(String nombreCampoValor) {
		this.nombreCampoValor = nombreCampoValor;
	}

	public String getNombreCampoOculto() {
		return nombreCampoOculto;
	}

	public void setNombreCampoOculto(String nombreCampoOculto) {
		this.nombreCampoOculto = nombreCampoOculto;
	}

	public String getFuncionCierre() {
		return funcionCierre;
	}

	public void setFuncionCierre(String funcionCierre) {
		this.funcionCierre = funcionCierre;
	}

	public Byte getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(Byte orientacion) {
		this.orientacion = orientacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void clear() 
	{
		
	}

	public static class ReporteStatus
	{
		private static final byte SAVE_EXITO = 1;
		private static final byte SAVE_NOEXITO = 2;
		
		public static Byte getStatus(Byte corte)
		{
			if (corte == SAVE_EXITO)
				return new Byte(SAVE_EXITO);
			else if (corte == SAVE_NOEXITO)
				return new Byte(SAVE_NOEXITO);
			else
				return null;
		}
		
		public static Byte getStatusExito() 
		{
			return new Byte(SAVE_EXITO);
		}

		public static Byte getStatusNoExito() 
		{
			return new Byte(SAVE_NOEXITO);
		}
	}

	public static class OrientacionTipo
	{
		public static final byte ORIENTACION_PORTRAIT = 1;
		public static final byte ORIENTACION_LANDSCAPE = 2;
		
		public static Byte getOrientacionTipo(Byte orientacion)
		{
			if (orientacion == ORIENTACION_PORTRAIT)
				return new Byte(ORIENTACION_PORTRAIT);
			else if (orientacion == ORIENTACION_LANDSCAPE)
				return new Byte(ORIENTACION_LANDSCAPE);
			else
				return null;
		}
	}
	
	public static class AlcanceStatus
	{
		private static final byte ALCANCE_OBJETIVO = 1;
		private static final byte ALCANCE_PLAN = 2;
		private static final byte ALCANCE_ORGANIZACION = 3;
		private static final byte ALCANCE_SUB_ORGANIZACION = 4;
		
		public static Byte getAlcanceStatus(Byte alcance)
		{
			if (alcance == ALCANCE_OBJETIVO)
				return new Byte(ALCANCE_OBJETIVO);
			else if (alcance == ALCANCE_PLAN)
				return new Byte(ALCANCE_PLAN);
			else if (alcance == ALCANCE_ORGANIZACION)
				return new Byte(ALCANCE_ORGANIZACION);
			else if (alcance == ALCANCE_SUB_ORGANIZACION)
				return new Byte(ALCANCE_SUB_ORGANIZACION);
			else
				return null;
		}
	}

	public static class PeriodoStatus
	{
		private static final byte PERIODO_ALCORTE = 1;
		private static final byte PERIODO_PORPERIODO = 2;
		
		public static Byte getPeriodoStatus(Byte tipoPeriodo)
		{
			if (tipoPeriodo == PERIODO_ALCORTE)
				return new Byte(PERIODO_ALCORTE);
			else if (tipoPeriodo == PERIODO_PORPERIODO)
				return new Byte(PERIODO_PORPERIODO);
			else
				return null;
		}
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
	
	public static class TipoObjeto
	{
		public static final byte TIPO_STRING = 1;
		public static final byte TIPO_TABLA = 2;
		
		public static Byte getTipoObjeto(Byte tipoObjeto)
		{
			if (tipoObjeto == TIPO_STRING)
				return new Byte(TIPO_STRING);
			else if (tipoObjeto == TIPO_TABLA)
				return new Byte(TIPO_TABLA);
			else
				return null;
		}
	}
	
}
