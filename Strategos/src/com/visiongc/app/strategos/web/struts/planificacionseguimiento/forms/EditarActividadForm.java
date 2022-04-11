package com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms;

import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.NaturalezaActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditarActividadForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	private Long actividadId;
	private Long proyectoId;
	private Long iniciativaId;
	private Long organizacionId;
	private Long padreId;
	private Byte frecuencia;
	private String nombre;
	private String descripcion;
	private String productoEsperado;
	private Long unidadId;
	private String unidadMedida;
	private List<UnidadMedida> unidadesMedida;
	private Byte naturaleza;
	private List<?> naturalezas;
	private Byte tipoMedicion;
	private String comienzoPlan;
	private String comienzoPlanTexto;
	private String finPlan;
	private String finPlanTexto;
	private Integer duracionPlan;
	private Long responsableFijarMetaId;
	private Long responsableLograrMetaId;
	private Long responsableSeguimientoId;
	private Long responsableCargarMetaId;
	private Long responsableCargarEjecutadoId;
	private String responsableFijarMeta;
	private String responsableLograrMeta;
	private String responsableSeguimiento;
	private String responsableCargarMeta;
	private String responsableCargarEjecutado;
	private Long claseId;
	private Long objetoAsociadoId;
	private String recursosHumanos;
	private String recursosMateriales;
	private Double porcentajeAmarillo;
	private Double porcentajeVerde;
	private Boolean hayValorPorcentajeAmarillo;
  	private Boolean hayValorPorcentajeVerde;
  	private Long seleccionados;
  	private Integer fila;
  	private Integer nivel;
  	private Boolean compuesta;
  	private Long indicadorId;
  	private PryCalendario calendario;
  	private Long creadoId;
  	private Long modificadoId;
  	private String fechaCreado;
  	private String fechaModificado;
  	private String nombreUsuarioCreado;
  	private String nombreUsuarioModificado;
  	private String asociadaNombre;
  	private String asociadaOrganizacion;
  	private String insumosFormula;
  	private Boolean desdeIniciativasPlanes;
  	private String formula;
  	private List<?> funcionesFormula;
  	private String seriesIndicador;
  	private Boolean esPadre;
  	private Boolean eliminarMediciones;
  	private String codigoEnlace;
  	private String enlaceParcial;

  public Long getActividadId()
  {
    return this.actividadId;
  }

  public void setActividadId(Long actividadId) {
    this.actividadId = actividadId;
  }

  public Long getProyectoId() {
    return this.proyectoId;
  }

  public void setProyectoId(Long proyectoId) {
    this.proyectoId = proyectoId;
  }

  public Long getIniciativaId() {
    return this.iniciativaId;
  }

  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getUnidadId() {
    return this.unidadId;
  }

  public void setUnidadId(Long unidadId) {
    this.unidadId = unidadId;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getComienzoPlan() {
    return this.comienzoPlan;
  }

  public void setComienzoPlan(String comienzoPlan) {
    this.comienzoPlan = comienzoPlan;
  }

  public String getFinPlan() {
    return this.finPlan;
  }

  public void setFinPlan(String finPlan) {
    this.finPlan = finPlan;
  }

  public String getComienzoPlanTexto() {
    return this.comienzoPlanTexto;
  }

  public void setComienzoPlanTexto(String comienzoPlanTexto) {
    this.comienzoPlanTexto = comienzoPlanTexto;
  }

  public String getFinPlanTexto() {
    return this.finPlanTexto;
  }

  public void setFinPlanTexto(String finPlanTexto) {
    this.finPlanTexto = finPlanTexto;
  }

  public Integer getDuracionPlan() {
    return this.duracionPlan;
  }

  public void setDuracionPlan(Integer duracionPlan) {
    this.duracionPlan = duracionPlan;
  }

  public Integer getFila() {
    return this.fila;
  }

  public void setFila(Integer fila) {
    this.fila = fila;
  }

  public Integer getNivel() {
    return this.nivel;
  }

  public void setNivel(Integer nivel) {
    this.nivel = nivel;
  }

  public Boolean getCompuesta() {
    return this.compuesta;
  }

  public void setCompuesta(Boolean compuesta) {
    this.compuesta = compuesta;
  }

  public Long getCreadoId() {
    return this.creadoId;
  }

  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }

  public Long getModificadoId() {
    return this.modificadoId;
  }

  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }

  public Long getIndicadorId() {
    return this.indicadorId;
  }

  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }

  	public Byte getNaturaleza() 
  	{
  		return this.naturaleza;
  	}

  public void setNaturaleza(Byte naturaleza) {
    this.naturaleza = naturaleza;
  }

  public Long getResponsableFijarMetaId() {
    return this.responsableFijarMetaId;
  }

  public void setResponsableFijarMetaId(Long responsableFijarMetaId) {
    this.responsableFijarMetaId = responsableFijarMetaId;
  }

  public Long getResponsableLograrMetaId() {
    return this.responsableLograrMetaId;
  }

  public void setResponsableLograrMetaId(Long responsableLograrMetaId) {
    this.responsableLograrMetaId = responsableLograrMetaId;
  }

  public Long getResponsableSeguimientoId() {
    return this.responsableSeguimientoId;
  }

  public void setResponsableSeguimientoId(Long responsableSeguimientoId) {
    this.responsableSeguimientoId = responsableSeguimientoId;
  }

  public Long getResponsableCargarMetaId() {
    return this.responsableCargarMetaId;
  }

  public void setResponsableCargarMetaId(Long responsableCargarMetaId) {
    this.responsableCargarMetaId = responsableCargarMetaId;
  }

  public Long getResponsableCargarEjecutadoId() {
    return this.responsableCargarEjecutadoId;
  }

  public void setResponsableCargarEjecutadoId(Long responsableCargarEjecutadoId) {
    this.responsableCargarEjecutadoId = responsableCargarEjecutadoId;
  }

  public Long getClaseId() {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public Long getObjetoAsociadoId() {
    return this.objetoAsociadoId;
  }

  public void setObjetoAsociadoId(Long objetoAsociadoId) {
    this.objetoAsociadoId = objetoAsociadoId;
  }

  	public Byte getTipoMedicion() 
  	{
	  return this.tipoMedicion;
  	}

  	public void setTipoMedicion(Byte tipoMedicion) 
  	{
	  this.tipoMedicion = tipoMedicion;
  	}

  	public String getUnidadMedida() 
  	{
	  return this.unidadMedida;
  	}

  	public void setUnidadMedida(String unidadMedida) 
  	{
  		this.unidadMedida = unidadMedida;
  	}
  
  	public List<UnidadMedida> getUnidadesMedida() 
  	{
	    return this.unidadesMedida;
	}

	public void setUnidadesMedida(List<UnidadMedida> unidadesMedidas) 
	{
	    this.unidadesMedida = unidadesMedidas;
	}

	public String getResponsableFijarMeta() 
	{
		return this.responsableFijarMeta;
	}

  public void setResponsableFijarMeta(String responsableFijarMeta) {
    this.responsableFijarMeta = responsableFijarMeta;
  }

  public String getResponsableLograrMeta() {
    return this.responsableLograrMeta;
  }

  public void setResponsableLograrMeta(String responsableLograrMeta) {
    this.responsableLograrMeta = responsableLograrMeta;
  }

  public String getResponsableSeguimiento() {
    return this.responsableSeguimiento;
  }

  public void setResponsableSeguimiento(String responsableSeguimiento) {
    this.responsableSeguimiento = responsableSeguimiento;
  }

  public String getResponsableCargarMeta() {
    return this.responsableCargarMeta;
  }

  public void setResponsableCargarMeta(String responsableCargarMeta) {
    this.responsableCargarMeta = responsableCargarMeta;
  }

  public String getResponsableCargarEjecutado() {
    return this.responsableCargarEjecutado;
  }

  public void setResponsableCargarEjecutado(String responsableCargarEjecutado) {
    this.responsableCargarEjecutado = responsableCargarEjecutado;
  }

  public String getProductoEsperado() {
    return this.productoEsperado;
  }

  public void setProductoEsperado(String productoEsperado) {
    this.productoEsperado = productoEsperado;
  }

  public String getRecursosHumanos() {
    return this.recursosHumanos;
  }

  public void setRecursosHumanos(String recursosHumanos) {
    this.recursosHumanos = recursosHumanos;
  }

  public String getRecursosMateriales() {
    return this.recursosMateriales;
  }

  public void setRecursosMateriales(String recursosMateriales) {
    this.recursosMateriales = recursosMateriales;
  }

  public Double getPorcentajeAmarillo() 
  {
    return this.porcentajeAmarillo;
  }

  public void setPorcentajeAmarillo(Double porcentajeAmarillo) 
  {
    this.porcentajeAmarillo = porcentajeAmarillo;
  }

  	public Boolean getHayValorPorcentajeAmarillo() 
  	{
	  return this.hayValorPorcentajeAmarillo;
  	}

  	public void setHayValorPorcentajeAmarillo(Boolean hayValorPorcentajeAmarillo) 
  	{
  		this.hayValorPorcentajeAmarillo = hayValorPorcentajeAmarillo;
  	}

  	public Boolean getHayValorPorcentajeVerde() 
  	{
	  return this.hayValorPorcentajeVerde;
  	}

  	public void setHayValorPorcentajeVerde(Boolean hayValorPorcentajeVerde) 
  	{
  		this.hayValorPorcentajeVerde = hayValorPorcentajeVerde;
  	}
  	
  	public Double getPorcentajeVerde() 
  	{
  		return this.porcentajeVerde;
  	}

  	public void setPorcentajeVerde(Double porcentajeVerde) 
  	{
  		this.porcentajeVerde = porcentajeVerde;
  	}

  	public Byte getFrecuencia() 
  	{
  		return this.frecuencia;
  	}

  	public void setFrecuencia(Byte frecuencia) 
  	{
  		this.frecuencia = frecuencia;
  	}

  public Long getSeleccionados() {
    return this.seleccionados;
  }

  public void setSeleccionados(Long seleccionados) {
    this.seleccionados = seleccionados;
  }

  public String getFechaCreado() {
    return this.fechaCreado;
  }

  public void setFechaCreado(String fechaCreado) {
    this.fechaCreado = fechaCreado;
  }

  public String getFechaModificado() {
    return this.fechaModificado;
  }

  public void setFechaModificado(String fechaModificado) {
    this.fechaModificado = fechaModificado;
  }

  public String getNombreUsuarioCreado() {
    return this.nombreUsuarioCreado;
  }

  public void setNombreUsuarioCreado(String nombreUsuarioCreado) {
    this.nombreUsuarioCreado = nombreUsuarioCreado;
  }

  public String getNombreUsuarioModificado() {
    return this.nombreUsuarioModificado;
  }

  public void setNombreUsuarioModificado(String nombreUsuarioModificado) {
    this.nombreUsuarioModificado = nombreUsuarioModificado;
  }

  public String getAsociadaNombre() {
    return this.asociadaNombre;
  }

  public void setAsociadaNombre(String asociadaNombre) {
    this.asociadaNombre = asociadaNombre;
  }

  public String getAsociadaOrganizacion() {
    return this.asociadaOrganizacion;
  }

  public void setAsociadaOrganizacion(String asociadaOrganizacion) {
    this.asociadaOrganizacion = asociadaOrganizacion;
  }

  public List<?> getNaturalezas() {
    return this.naturalezas;
  }

  public void setNaturalezas(List<?> naturalezas) {
    this.naturalezas = naturalezas;
  }

  public PryCalendario getCalendario() {
    return this.calendario;
  }

  public void setCalendario(PryCalendario calendario) {
    this.calendario = calendario;
  }

  public Byte getNaturalezaActividadSimple() {
    return NaturalezaActividad.getNaturalezaSimple();
  }

  	public Byte getNaturalezaActividadFormula() 
  	{
  		return NaturalezaActividad.getNaturalezaFormula();
  	}
  	
  	public Byte getNaturalezaActividadAsociado() 
  	{
  		return NaturalezaActividad.getNaturalezaAsociado();
  	}

  public Boolean getDesdeIniciativasPlanes() 
  {
    return this.desdeIniciativasPlanes;
  }

  public void setDesdeIniciativasPlanes(Boolean desdeIniciativasPlanes) 
  {
    this.desdeIniciativasPlanes = desdeIniciativasPlanes;
  }

  public String getInsumosFormula() 
  {
    return this.insumosFormula;
  }

  public void setInsumosFormula(String insumosFormula) 
  {
    this.insumosFormula = insumosFormula;
  }
  
  public String getFormula() 
  {
    return this.formula;
  }

  public void setFormula(String formula) 
  {
    this.formula = formula;
  }
  
  public String getFuncion() 
  {
    return "";
  }

  public void setFuncion(String funcion) 
  {
  }
  
  public List<?> getFuncionesFormula() 
  {
    return this.funcionesFormula;
  }

  public void setFuncionesFormula(List<?> funcionesFormula) 
  {
    this.funcionesFormula = funcionesFormula;
  }
  
  public String getSeparadorIndicadores() 
  {
    return new EditarIndicadorForm().getSeparadorIndicadores();
  }

  public String getSeparadorSeries() 
  {
    return new EditarIndicadorForm().getSeparadorSeries();
  }

  public String getSeparadorRuta() 
  {
    return new EditarIndicadorForm().getSeparadorRuta();
  }

  public String getCodigoIndicadorEliminado() 
  {
    return new EditarIndicadorForm().getCodigoIndicadorEliminado();
  }
  
  	public String getSeriesIndicador() 
  	{
  		return this.seriesIndicador;
  	}

  	public void setSeriesIndicador(String seriesIndicador) 
  	{
  		this.seriesIndicador = seriesIndicador;
  	}
  
  	public Boolean getEsPadre() 
  	{
  		return this.esPadre;
  	}

  	public void setEsPadre(Boolean esPadre) 
  	{
  		this.esPadre = esPadre;
  	}

  	public Boolean getEliminarMediciones() 
  	{
  		return this.eliminarMediciones;
  	}

  	public void setEliminarMediciones(Boolean eliminarMediciones) 
  	{
  		this.eliminarMediciones = eliminarMediciones;
  	}
  	
    public String getEnlaceParcial() 
    {
        return this.enlaceParcial;
    }

    public void setEnlaceParcial(String enlaceParcial) 
    {
        this.enlaceParcial = enlaceParcial;
    }
  	
  	public String getCodigoEnlace() 
  	{
  		return this.codigoEnlace;
  	}

  	public void setCodigoEnlace(String codigoEnlace) 
  	{
  		this.codigoEnlace = codigoEnlace;
  	}
  	
  	public void clear() 
  	{
  		this.actividadId = new Long(0L);
    	this.padreId = null;
    	this.nombre = null;
    	this.descripcion = null;
    	this.productoEsperado = null;
    	this.naturalezas = null;
    	this.naturaleza = new Byte((byte) 0);
    	this.unidadId = null;
    	this.unidadMedida = null;
    	this.unidadesMedida = new ArrayList<UnidadMedida>();
    	this.tipoMedicion = TipoMedicion.getTipoMedicionEnPeriodo();
    	this.responsableCargarMetaId = null;
    	this.responsableCargarMeta = null;
    	this.responsableCargarEjecutadoId = null;
    	this.responsableCargarEjecutado = null;
    	this.responsableFijarMetaId = null;
    	this.responsableLograrMetaId = null;
    	this.responsableSeguimientoId = null;
    	Date ahora = new Date();
    	this.comienzoPlan = VgcFormatter.formatearFecha(ahora, "formato.fecha.corta");
    	this.finPlan = VgcFormatter.formatearFecha(ahora, "formato.fecha.corta");
    	this.duracionPlan = new Integer(1);
    	this.porcentajeAmarillo = null;
    	this.porcentajeVerde = null;
    	this.hayValorPorcentajeAmarillo = false;
    	this.hayValorPorcentajeVerde = false;
    	this.fechaCreado = null;
    	this.fechaModificado = null;
    	this.nombreUsuarioCreado = null;
    	this.nombreUsuarioModificado = null;
    	this.desdeIniciativasPlanes = new Boolean(false);
    	this.esPadre = new Boolean(false);
    	this.eliminarMediciones = new Boolean(false);
    	this.asociadaNombre = "";
    	this.setStatus(StatusUtil.getStatusSuccess());
		this.codigoEnlace = null;
		this.enlaceParcial = null;
		this.setBloqueado(false);
		
    	String listaSeries = getSeparadorSeries() + SerieTiempo.getSerieRealId() + getSeparadorSeries() + SerieTiempo.getSerieProgramadoId() + getSeparadorSeries();
    	this.seriesIndicador = listaSeries;
    	this.insumosFormula = null;
    	this.formula = null;
  	}
}