package com.visiongc.app.strategos.web.struts.indicadores.forms;

import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoAlerta;
import com.visiongc.app.strategos.indicadores.model.util.TipoAsociadoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoComparacionIndicadorIndice;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoSuma;
import com.visiongc.app.strategos.indicadores.model.util.TipoVariacionIndicadorIndice;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Date;
import java.util.List;

public class EditarIndicadorForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	public static final String SEPARADOR_CATEGORIAS = "!;!";
	public static final String SEPARADOR_ORDEN = "-";
	public static final String SEPARADOR_INDICADORES = "!;!";
	public static final String SEPARADOR_SERIES = "!@!";

	private String nombreIndicadorSingular;
	private Long indicadorId;
	private Long claseId;
	private Long organizacionId;
	private String nombre;
	private String nombreLargo;
	private String descripcion;
	private Byte naturaleza;
	private Byte naturalezaOriginal;
	private Byte frecuencia;
	private Byte frecuenciaOriginal;
	private String comportamiento;
	private Long unidadId;
	private ClaseIndicadores claseIndicadores;
	private String seriesIndicador;
	private List<?> seriesTiempo;
	private List<?> unidadesMedida;
	private List<?> frecuencias;
	private List<?> naturalezas;
	private List<?> categorias;
	private List<?> tiposAsociado;
	private List<?> indicadorAsociadoRevisiones;
	private List<?> caracteristicas;
	private List<?> tipos;
	private List<?> tiposCorte;
	private List<?> tiposMedicion;
	private String codigoEnlace;
	private Byte prioridad;
	private Boolean mostrarEnArbol;
	private String fuente;
	private String orden;
	private Byte indicadorAsociadoTipo;
	private Long indicadorAsociadoId;
	private String indicadorAsociadoNombre;
	private String indicadorAsociadoFrecuencia;
	private String indicadorAsociadoUnidad;
	private Byte indicadorAsociadoRevision;
	private Boolean soloLectura;
	private Byte caracteristica;
	private Byte tipoGuiaResultado;
	private Long responsableNotificacionId;
	private Long responsableFijarMetaId;
	private Long responsableLograrMetaId;
	private Long responsableSeguimientoId;
	private Long responsableCargarMetaId;
	private Long responsableCargarEjecutadoId;
	private String responsableNotificacion;
	private String responsableFijarMeta;
	private String responsableLograrMeta;
	private String responsableSeguimiento;
	private String responsableCargarMeta;
	private String responsableCargarEjecutado;
	private Boolean guia;
	private Byte corte;
	private String enlaceParcial;
	private Integer alertaMinMax;
	private Double alertaMetaZonaVerde;
	private Double alertaMetaZonaAmarilla;
	private Byte alertaTipoZonaVerde;
	private Byte alertaTipoZonaAmarilla;
	private Byte alertaValorVariableZonaVerde;
	private Byte alertaValorVariableZonaAmarilla;
	private Long alertaIndicadorIdZonaVerde;
	private Long alertaIndicadorIdZonaAmarilla;
	private Boolean valorInicialCero;
	private Byte numeroDecimales;
	private Byte tipoCargaMedicion;
	private Byte parametroTipoAcotamientoSuperior;
	private Byte parametroTipoAcotamientoInferior;
	private Long parametroSuperiorIndicadorId;
	private String parametroSuperiorIndicadorNombre;
	private Long parametroInferiorIndicadorId;
	private String parametroInferiorIndicadorNombre;
	private Double parametroSuperiorValorFijo;
	private Double parametroInferiorValorFijo;
	private String url;
	private Date fechaBloqueoEjecutado;
	private Date fechaBloqueoMeta;
	private Boolean multidimensional;
	private String mediciones;
	private String insumosFormula;
	private String formula;
	private List<?> insumosNaturalezaFormula;
	private List<?> funcionesFormula;
	private String escalaCualitativa;
	private String puntoEdicion;
	private String nombreIndicadorZonaVerde;
	private String nombreIndicadorZonaAmarilla;
	private List<?> pathInsumosFormula;
	private Boolean indicadoresPlan;
	private Long planId;
	private Long perspectivaId;
	private Boolean tieneMediciones;
	private String indicadorIndice;
	private String indicadorIndiceId;
	private String indicadorIndiceFrecuencia;
	private Byte indicadorIndiceFrecuenciaId;
	private String indicadorIndiceUnidad;
	private String indicadorIndiceTipoVariacion;
	private String indicadorIndiceTipoComparacion;
	private String indicadorPromedio;
	private String indicadorPromedioId;
	private String indicadorPromedioFrecuencia;
	private Byte indicadorPromedioFrecuenciaId;
	private String indicadorPromedioUnidad;
	private String indicadorSumatoria;
	private String indicadorSumatoriaId;
	private String indicadorSumatoriaFrecuencia;
	private Byte indicadorSumatoriaFrecuenciaId;
	private String indicadorSumatoriaUnidad;
	private String mesCierreOrganizacion;
	private Long iniciativaId;
	private Boolean bloquearIndicadorIniciativa;
	private Boolean desdeIniciativasPlanes;
	private Boolean asignarInventario;
	private String codigoEnlaceFormula;
	private String enlaceParcialFormula;
	private Boolean esIndicadorIniciativa;
	
	// Visualizar
	private String naturalezaNombre;
	private Integer numeroUsosComoIndicadorInsumo;
	private String frecuenciaNombre;
	private String unidadNombre;
	private String caracteristicaNombre;
	private String tipoCorteNombre;
	private String tipoNombre;
	private String claseNombre;
	private String categoriaNombre;
	
	// Variables para copiar organizacion
	private Boolean copiarArbol;
	private Boolean copiarMediciones;
	private Boolean copiarPlantillasGraficos;
	private Boolean copiarPlantillasReportes;
	private String nuevoNombre;
	private Byte tipoSumaMedicion;
	private OrganizacionStrategos organizacion;
	private Boolean copiarInsumos;
	
	// Variables para mover indicadores
	private List<Long> indicadores;

	public String getCategoriaNombre() 
	{
		return this.categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) 
	{
		this.categoriaNombre = categoriaNombre;
	}

	public String getNaturalezaNombre() 
	{
		return this.naturalezaNombre;
	}

	public void setNaturalezaNombre(String naturalezaNombre) 
	{
		this.naturalezaNombre = naturalezaNombre;
	}
	
	public String getClaseNombre() 
	{
		return this.claseNombre;
	}

	public void setClaseNombre(String claseNombre) 
	{
		this.claseNombre = claseNombre;
	}
	
	public String getTipoNombre() 
	{
		return this.tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) 
	{
		this.tipoNombre = tipoNombre;
	}

	public String getTipoCorteNombre() 
	{
		return this.tipoCorteNombre;
	}

	public void setTipoCorteNombre(String tipoCorteNombre) 
	{
		this.tipoCorteNombre = tipoCorteNombre;
	}

	public String getCaracteristicaNombre() 
	{
		return this.caracteristicaNombre;
	}

	public void setCaracteristicaNombre(String caracteristicaNombre) 
	{
		this.caracteristicaNombre = caracteristicaNombre;
	}

	public String getUnidadNombre() 
	{
		return this.unidadNombre;
	}

	public void setUnidadNombre(String unidadNombre) 
	{
		this.unidadNombre = unidadNombre;
	}
	
	public String getFrecuenciaNombre() 
	{
		return this.frecuenciaNombre;
	}

	public void setFrecuenciaNombre(String frecuenciaNombre) 
	{
		this.frecuenciaNombre = frecuenciaNombre;
	}

	public Integer getNumeroUsosComoIndicadorInsumo() 
	{
		return this.numeroUsosComoIndicadorInsumo;
	}

	public void setNumeroUsosComoIndicadorInsumo(Integer numeroUsosComoIndicadorInsumo) 
	{
		this.numeroUsosComoIndicadorInsumo = numeroUsosComoIndicadorInsumo;
	}
	
	public String getNombreIndicadorSingular()
	{
		return this.nombreIndicadorSingular;
	}

	public void setNombreIndicadorSingular(String nombreIndicadorSingular) 
	{
		this.nombreIndicadorSingular = nombreIndicadorSingular;
	}

	public List<?> getPathInsumosFormula() 
	{
		return this.pathInsumosFormula;
	}

  public void setPathInsumosFormula(List<?> pathInsumosFormula) {
    this.pathInsumosFormula = pathInsumosFormula;
  }

  public String getSeriesIndicador() {
    return this.seriesIndicador;
  }

  public void setSeriesIndicador(String seriesIndicador) {
    this.seriesIndicador = seriesIndicador;
  }

  public List<?> getFrecuencias() {
    return this.frecuencias;
  }

  public void setFrecuencias(List<?> frecuencias) {
    this.frecuencias = frecuencias;
  }

  public List<?> getNaturalezas() {
    return this.naturalezas;
  }

  public void setNaturalezas(List<?> naturalezas) {
    this.naturalezas = naturalezas;
  }

  public List<?> getCategorias() {
    return this.categorias;
  }

  public void setCategorias(List<?> categorias) {
    this.categorias = categorias;
  }

  public List<?> getTiposAsociado() {
    return this.tiposAsociado;
  }

  public void setTiposAsociado(List<?> tiposAsociado) {
    this.tiposAsociado = tiposAsociado;
  }

  public Byte getIndicadorAsociadoTipoProgramado() {
    return TipoAsociadoIndicador.getTipoAsociadoIndicadorProgramado();
  }

  public List<?> getIndicadorAsociadoRevisiones() {
    return this.indicadorAsociadoRevisiones;
  }

  public void setIndicadorAsociadoRevisiones(List<?> indicadorAsociadoRevisiones) {
    this.indicadorAsociadoRevisiones = indicadorAsociadoRevisiones;
  }

  public List<?> getCaracteristicas() {
    return this.caracteristicas;
  }

  public String getCategoriaId()
  {
    return null;
  }

  public void setCaracteristicas(List<?> caracteristicas) {
    this.caracteristicas = caracteristicas;
  }

  public List<?> getTipos() {
    return this.tipos;
  }

  public void setTipos(List<?> tipos) {
    this.tipos = tipos;
  }

  public List<?> getTiposCorte() {
    return this.tiposCorte;
  }

  public void setTiposCorte(List<?> tiposCorte) {
    this.tiposCorte = tiposCorte;
  }

  public List<?> getTiposMedicion() {
    return this.tiposMedicion;
  }

  public void setTiposMedicion(List<?> tiposMedicion) {
    this.tiposMedicion = tiposMedicion;
  }

  public List<?> getSeriesTiempo() {
    return this.seriesTiempo;
  }

  public void setSeriesTiempo(List<?> seriesTiempo) {
    this.seriesTiempo = seriesTiempo;
  }

  public String getNombreLargo() {
    return this.nombreLargo;
  }

  public void setNombreLargo(String nombreLargo) {
    this.nombreLargo = nombreLargo;
  }

  public Long getIndicadorId() {
    return this.indicadorId;
  }

  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }

  public Long getClaseId() {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Byte getNaturaleza() {
    return this.naturaleza;
  }

  public void setNaturaleza(Byte naturaleza) {
    this.naturaleza = naturaleza;
  }

  public Byte getNaturalezaOriginal() {
    return this.naturalezaOriginal;
  }

  public void setNaturalezaOriginal(Byte naturalezaOriginal) {
    this.naturalezaOriginal = naturalezaOriginal;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public Byte getFrecuenciaOriginal() {
    return this.frecuenciaOriginal;
  }

  public void setFrecuenciaOriginal(Byte frecuenciaOriginal) {
    this.frecuenciaOriginal = frecuenciaOriginal;
  }

  public String getComportamiento() {
    return this.comportamiento;
  }

  public void setComportamiento(String comportamiento) {
    this.comportamiento = comportamiento;
  }

  public Long getUnidadId() {
    return this.unidadId;
  }

  public void setUnidadId(Long unidadId) {
    this.unidadId = unidadId;
  }

  public List<?> getUnidadesMedida() {
    return this.unidadesMedida;
  }

  public void setUnidadesMedida(List<?> unidadesMedidas) {
    this.unidadesMedida = unidadesMedidas;
  }

  public ClaseIndicadores getClaseIndicadores() {
    return this.claseIndicadores;
  }

  public void setClaseIndicadores(ClaseIndicadores claseIndicadores) {
    this.claseIndicadores = claseIndicadores;
  }

  	public String getCodigoEnlace() 
  	{
  		return this.codigoEnlace;
  	}

  	public void setCodigoEnlace(String codigoEnlace) 
  	{
  		this.codigoEnlace = codigoEnlace;
  	}

  	public String getCodigoEnlaceFormula() 
  	{
  		return this.codigoEnlaceFormula;
  	}

  	public void setCodigoEnlaceFormula(String codigoEnlaceFormula) 
  	{
  		this.codigoEnlaceFormula = codigoEnlaceFormula;
  	}
  	
    public String getEnlaceParcialFormula() 
    {
        return this.enlaceParcialFormula;
	}

    public void setEnlaceParcialFormula(String enlaceParcialFormula) 
    {
        this.enlaceParcialFormula = enlaceParcialFormula;
    }
  	
  public Byte getPrioridad() {
    return this.prioridad;
  }

  public void setPrioridad(Byte prioridad) {
    this.prioridad = prioridad;
  }

  public Boolean getMostrarEnArbol() {
    return this.mostrarEnArbol;
  }

  public void setMostrarEnArbol(Boolean mostrarEnArbol) {
    this.mostrarEnArbol = mostrarEnArbol;
  }

  public String getFuente() {
    return this.fuente;
  }

  public void setFuente(String fuente) {
    this.fuente = fuente;
  }

  public String getOrden() {
    return this.orden;
  }

  public void setOrden(String orden) {
    this.orden = orden;
  }

  public Byte getIndicadorAsociadoTipo() {
    return this.indicadorAsociadoTipo;
  }

  public void setIndicadorAsociadoTipo(Byte indicadorAsociadoTipo) {
    this.indicadorAsociadoTipo = indicadorAsociadoTipo;
  }

  public Long getIndicadorAsociadoId() {
    return this.indicadorAsociadoId;
  }

  public void setIndicadorAsociadoId(Long indicadorAsociadoId) {
    this.indicadorAsociadoId = indicadorAsociadoId;
  }

  public String getIndicadorAsociadoNombre() {
    return this.indicadorAsociadoNombre;
  }

  public void setIndicadorAsociadoNombre(String indicadorAsociadoNombre) {
    this.indicadorAsociadoNombre = indicadorAsociadoNombre;
  }

  public String getIndicadorAsociadoFrecuencia() {
    return this.indicadorAsociadoFrecuencia;
  }

  public void setIndicadorAsociadoFrecuencia(String indicadorAsociadoFrecuencia) {
    this.indicadorAsociadoFrecuencia = indicadorAsociadoFrecuencia;
  }

  public String getIndicadorAsociadoUnidad() {
    return this.indicadorAsociadoUnidad;
  }

  public void setIndicadorAsociadoUnidad(String indicadorAsociadoUnidad) {
    this.indicadorAsociadoUnidad = indicadorAsociadoUnidad;
  }

  public Byte getIndicadorAsociadoRevision() {
    return this.indicadorAsociadoRevision;
  }

  public void setIndicadorAsociadoRevision(Byte indicadorAsociadoRevision) {
    this.indicadorAsociadoRevision = indicadorAsociadoRevision;
  }

  public Boolean getSoloLectura() {
    if (this.soloLectura == null) {
      this.soloLectura = new Boolean(false);
    }
    return this.soloLectura;
  }

  public void setSoloLectura(Boolean soloLectura) {
    this.soloLectura = soloLectura;
  }

  public Byte getCaracteristica() {
    return this.caracteristica;
  }

  public void setCaracteristica(Byte caracteristica) {
    this.caracteristica = caracteristica;
  }

  public Byte getTipoGuiaResultado() {
    return this.tipoGuiaResultado;
  }

  public void setTipoGuiaResultado(Byte tipoGuiaResultado) {
    this.tipoGuiaResultado = tipoGuiaResultado;
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

  public String getResponsableFijarMeta() {
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
  
  public Long getResponsableNotificacionId() {
	return responsableNotificacionId;
  }

  public void setResponsableNotificacionId(Long responsableNotificacionId) {
	this.responsableNotificacionId = responsableNotificacionId;
  }

  public String getResponsableNotificacion() {
	return responsableNotificacion;
  }

  public void setResponsableNotificacion(String responsableNotificacion) {
	this.responsableNotificacion = responsableNotificacion;
  }

 public Boolean getGuia() {
    return this.guia;
  }

  public void setGuia(Boolean guia) {
    this.guia = guia;
  }

  public Byte getCorte() {
    return this.corte;
  }

  public void setCorte(Byte corte) {
    this.corte = corte;
  }

  public String getEnlaceParcial() {
    return this.enlaceParcial;
  }

  public void setEnlaceParcial(String enlaceParcial) {
    this.enlaceParcial = enlaceParcial;
  }

  public Integer getAlertaMinMax() {
    return this.alertaMinMax;
  }

  public void setAlertaMinMax(Integer alertaMinMax) {
    this.alertaMinMax = alertaMinMax;
  }

  public Double getAlertaMetaZonaVerde() {
    return this.alertaMetaZonaVerde;
  }

  public void setAlertaMetaZonaVerde(Double alertaMetaZonaVerde) {
    this.alertaMetaZonaVerde = alertaMetaZonaVerde;
  }

  public Double getAlertaMetaZonaAmarilla() {
    return this.alertaMetaZonaAmarilla;
  }

  public void setAlertaMetaZonaAmarilla(Double alertaMetaZonaAmarilla) {
    this.alertaMetaZonaAmarilla = alertaMetaZonaAmarilla;
  }

  public Byte getAlertaValorVariableZonaVerde() {
    return this.alertaValorVariableZonaVerde;
  }

  public void setAlertaValorVariableZonaVerde(Byte alertaValorVariableZonaVerde) {
    this.alertaValorVariableZonaVerde = alertaValorVariableZonaVerde;
  }

  public Byte getAlertaValorVariableZonaAmarilla() {
    return this.alertaValorVariableZonaAmarilla;
  }

  public void setAlertaValorVariableZonaAmarilla(Byte alertaValorVariableZonaAmarilla) {
    this.alertaValorVariableZonaAmarilla = alertaValorVariableZonaAmarilla;
  }

  public Long getAlertaIndicadorIdZonaVerde() {
    return this.alertaIndicadorIdZonaVerde;
  }

  public void setAlertaIndicadorIdZonaVerde(Long alertaIndicadorIdZonaVerde) {
    this.alertaIndicadorIdZonaVerde = alertaIndicadorIdZonaVerde;
  }

  public Long getAlertaIndicadorIdZonaAmarilla() {
    return this.alertaIndicadorIdZonaAmarilla;
  }

  public void setAlertaIndicadorIdZonaAmarilla(Long alertaIndicadorIdZonaAmarilla) {
    this.alertaIndicadorIdZonaAmarilla = alertaIndicadorIdZonaAmarilla;
  }

  public Boolean getValorInicialCero() {
    return this.valorInicialCero;
  }

  public void setValorInicialCero(Boolean valorInicialCero) {
    this.valorInicialCero = valorInicialCero;
  }

  public Byte getNumeroDecimales() {
    return this.numeroDecimales;
  }

  public void setNumeroDecimales(Byte numeroDecimales) {
    this.numeroDecimales = numeroDecimales;
  }

  public Byte getTipoSumaMedicion() 
  {
	  return this.tipoSumaMedicion;
  }

  public void setTipoSumaMedicion(Byte tipoSumaMedicion) 
  {
	  this.tipoSumaMedicion = tipoSumaMedicion;
  }
  
  public Byte getTipoCargaMedicion() 
  {
	  return this.tipoCargaMedicion;
  }

  public void setTipoCargaMedicion(Byte tipoCargaMedicion) 
  {
	  this.tipoCargaMedicion = tipoCargaMedicion;
  }

  public Byte getTipoAcotamientoValorFijo() {
    return new Byte((byte) 1);
  }

  public Byte getTipoAcotamientoValorIndicador() {
    return new Byte((byte) 2);
  }

  public Byte getParametroTipoAcotamientoInferior() {
    return this.parametroTipoAcotamientoInferior;
  }

  public void setParametroTipoAcotamientoInferior(Byte parametroTipoAcotamientoInferior) {
    this.parametroTipoAcotamientoInferior = parametroTipoAcotamientoInferior;
  }

  public Byte getParametroTipoAcotamientoSuperior() {
    return this.parametroTipoAcotamientoSuperior;
  }

  public void setParametroTipoAcotamientoSuperior(Byte parametroTipoAcotamientoSuperior) {
    this.parametroTipoAcotamientoSuperior = parametroTipoAcotamientoSuperior;
  }

  public Long getParametroSuperiorIndicadorId() {
    return this.parametroSuperiorIndicadorId;
  }

  public void setParametroSuperiorIndicadorId(Long parametroSuperiorIndicadorId) {
    this.parametroSuperiorIndicadorId = parametroSuperiorIndicadorId;
  }

  public Long getParametroInferiorIndicadorId() {
    return this.parametroInferiorIndicadorId;
  }

  public void setParametroInferiorIndicadorId(Long parametroInferiorIndicadorId) {
    this.parametroInferiorIndicadorId = parametroInferiorIndicadorId;
  }

  public String getParametroInferiorIndicadorNombre() {
    return this.parametroInferiorIndicadorNombre;
  }

  public void setParametroInferiorIndicadorNombre(String parametroInferiorIndicadorNombre) {
    this.parametroInferiorIndicadorNombre = parametroInferiorIndicadorNombre;
  }

  public String getParametroSuperiorIndicadorNombre() {
    return this.parametroSuperiorIndicadorNombre;
  }

  public void setParametroSuperiorIndicadorNombre(String parametroSuperiorIndicadorNombre) {
    this.parametroSuperiorIndicadorNombre = parametroSuperiorIndicadorNombre;
  }

  public Double getParametroSuperiorValorFijo() {
    return this.parametroSuperiorValorFijo;
  }

  public void setParametroSuperiorValorFijo(Double parametroSuperiorValorFijo) {
    this.parametroSuperiorValorFijo = parametroSuperiorValorFijo;
  }

  public Double getParametroInferiorValorFijo() {
    return this.parametroInferiorValorFijo;
  }

  public void setParametroInferiorValorFijo(Double parametroInferiorValorFijo) {
    this.parametroInferiorValorFijo = parametroInferiorValorFijo;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Date getFechaBloqueoEjecutado() {
    return this.fechaBloqueoEjecutado;
  }

  public void setFechaBloqueoEjecutado(Date fechaBloqueoEjecutado) {
    this.fechaBloqueoEjecutado = fechaBloqueoEjecutado;
  }

  public Date getFechaBloqueoMeta() {
    return this.fechaBloqueoMeta;
  }

  public void setFechaBloqueoMeta(Date fechaBloqueoMeta) {
    this.fechaBloqueoMeta = fechaBloqueoMeta;
  }

  public Boolean getMultidimensional() {
    return this.multidimensional;
  }

  public void setMultidimensional(Boolean multidimensional) {
    this.multidimensional = multidimensional;
  }

  public Byte getAlertaTipoZonaVerde() {
    return this.alertaTipoZonaVerde;
  }

  public void setAlertaTipoZonaVerde(Byte alertaTipoZonaVerde) {
    this.alertaTipoZonaVerde = alertaTipoZonaVerde;
  }

  public Byte getAlertaTipoZonaAmarilla() {
    return this.alertaTipoZonaAmarilla;
  }

  public void setAlertaTipoZonaAmarilla(Byte alertaTipoZonaAmarilla) {
    this.alertaTipoZonaAmarilla = alertaTipoZonaAmarilla;
  }

  public Byte getCaracteristicaRetoAumento() {
    return Caracteristica.getCaracteristicaRetoAumento();
  }

  public Byte getCaracteristicaRetoDisminucion() {
    return Caracteristica.getCaracteristicaRetoDisminucion();
  }

  public Byte getCaracteristicaCondicionValorMaximo() {
    return Caracteristica.getCaracteristicaCondicionValorMaximo();
  }

  public Byte getCaracteristicaCondicionValorMinimo() {
    return Caracteristica.getCaracteristicaCondicionValorMinimo();
  }

  public Byte getCaracteristicaCondicionBandas() {
    return Caracteristica.getCaracteristicaCondicionBandas();
  }

  public Byte getTipoCorteLongitudinal() {
    return TipoCorte.getTipoCorteLongitudinal();
  }

  public Byte getTipoCorteTransversal() {
    return TipoCorte.getTipoCorteTransversal();
  }

  public Byte getTipoMedicionEnPeriodo() 
  {
	  return TipoMedicion.getTipoMedicionEnPeriodo();
  }
  
  public Byte getTipoMedicionAlPeriodo() {
    return TipoMedicion.getTipoMedicionAlPeriodo();
  }
  
  public Byte getTipoSumaSumarMediciones() 
  {
	  return TipoSuma.getTipoSumaSumarMediciones();
  }

  public Byte getTipoSumaUltimoPeriodo() 
  {
	  return TipoSuma.getTipoSumaUltimoPeriodo();
  }

  public Byte getTipoResGuiaResultado() {
    return TipoIndicador.getTipoIndicadorResultado();
  }

  public Byte getTipoResGuiaGuia() {
    return TipoIndicador.getTipoIndicadorGuia();
  }

  public Byte getTipoAlertaPorcentaje() {
    return TipoAlerta.getTipoAlertaPorcentaje();
  }

  public Byte getTipoAlertaZonaValorAbsolutoIndicador() {
    return TipoAlerta.getTipoAlertaValorAbsolutoIndicador();
  }

  public Byte getTipoAlertaValorAbsolutoMagnitud() {
    return TipoAlerta.getTipoAlertaValorAbsolutoMagnitud();
  }

  public String getMediciones() {
    return this.mediciones;
  }

  public void setMediciones(String mediciones) {
    this.mediciones = mediciones;
  }

  public List<?> getFuncionesFormula() {
    return this.funcionesFormula;
  }

  public void setFuncionesFormula(List<?> funcionesFormula) {
    this.funcionesFormula = funcionesFormula;
  }

  public String getInsumosFormula() {
    return this.insumosFormula;
  }

  public void setInsumosFormula(String insumosFormula) {
    this.insumosFormula = insumosFormula;
  }

  public String getFormula() {
    return this.formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public String getSeparadorCategorias() {
    return "!;!";
  }

  public String getSeparadorOrden() {
    return "-";
  }

  public List<?> getInsumosNaturalezaFormula() {
    return this.insumosNaturalezaFormula;
  }

  public void setInsumosNaturalezaFormula(List<?> insumosNaturalezaFormula) {
    this.insumosNaturalezaFormula = insumosNaturalezaFormula;
  }

  public String getEscalaCualitativa() {
    return this.escalaCualitativa;
  }

  public void setEscalaCualitativa(String escalaCualitativa) {
    this.escalaCualitativa = escalaCualitativa;
  }

  public Byte getNaturalezaSimple() {
    return Naturaleza.getNaturalezaSimple();
  }

  public Byte getNaturalezaFormula() {
    return Naturaleza.getNaturalezaFormula();
  }

  public Byte getNaturalezaCualitativoOrdinal() {
    return Naturaleza.getNaturalezaCualitativoOrdinal();
  }

  public Byte getNaturalezaCualitativoNominal() {
    return Naturaleza.getNaturalezaCualitativoNominal();
  }

  public Byte getNaturalezaSumatoria() {
    return Naturaleza.getNaturalezaSumatoria();
  }

  public Byte getNaturalezaPromedio() {
    return Naturaleza.getNaturalezaPromedio();
  }

  public Byte getNaturalezaIndice() {
    return Naturaleza.getNaturalezaIndice();
  }

  public Long getSerieReal() {
    return SerieTiempo.getSerieReal().getSerieId();
  }

  public String getPuntoEdicion() {
    return this.puntoEdicion;
  }

  public void setPuntoEdicion(String puntoEdicion) {
    this.puntoEdicion = puntoEdicion;
  }

  public String getFuncion() {
    return "";
  }

  	public void setFuncion(String funcion) 
  	{
  	}

  	public String getSeparadorIndicadores() 
  	{
  		return SEPARADOR_INDICADORES;
  	}

  	public String getSeparadorSeries() 
  	{
  		return SEPARADOR_SERIES;
  	}

  public String getSeparadorRuta() {
    return "!#!";
  }

  public String getCodigoIndicadorEliminado() {
    return "!ELIMINADO!";
  }

  public String getNombreIndicadorZonaVerde() {
    return this.nombreIndicadorZonaVerde;
  }

  public void setNombreIndicadorZonaVerde(String nombreIndicadorZonaVerde) {
    this.nombreIndicadorZonaVerde = nombreIndicadorZonaVerde;
  }

  public String getNombreIndicadorZonaAmarilla() {
    return this.nombreIndicadorZonaAmarilla;
  }

  public void setNombreIndicadorZonaAmarilla(String nombreIndicadorZonaAmarilla) {
    this.nombreIndicadorZonaAmarilla = nombreIndicadorZonaAmarilla;
  }

  public Boolean getIndicadoresPlan() {
    return this.indicadoresPlan;
  }

  public void setIndicadoresPlan(Boolean indicadoresPlan) {
    this.indicadoresPlan = indicadoresPlan;
  }

  public Long getPlanId() {
    return this.planId;
  }

  public void setPlanId(Long planId) {
    if ((planId != null) && (planId.longValue() > 0L)) {
      this.planId = planId;
    }
    else
      this.planId = null;
  }

  public Long getPerspectivaId()
  {
    return this.perspectivaId;
  }

  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }

  public Long getIniciativaId() {
    return this.iniciativaId;
  }

  public void setIniciativaId(Long iniciativaId) {
    if ((iniciativaId != null) && (iniciativaId.longValue() > 0L))
      this.iniciativaId = iniciativaId;
    else
      this.iniciativaId = null;
  }

  public Boolean getBloquearIndicadorIniciativa()
  {
    return this.bloquearIndicadorIniciativa;
  }

  public void setBloquearIndicadorIniciativa(Boolean bloquearIndicadorIniciativa) {
    this.bloquearIndicadorIniciativa = bloquearIndicadorIniciativa;
  }

  public Boolean getDesdeIniciativasPlanes() {
    return this.desdeIniciativasPlanes;
  }

  public void setDesdeIniciativasPlanes(Boolean desdeIniciativasPlanes) {
    this.desdeIniciativasPlanes = desdeIniciativasPlanes;
  }

  public Boolean getTieneMediciones() {
    return this.tieneMediciones;
  }

  public void setTieneMediciones(Boolean tieneMediciones) {
    this.tieneMediciones = tieneMediciones;
  }

  public String getIndicadorIndice() {
    return this.indicadorIndice;
  }

  public void setIndicadorIndice(String indicadorIndice) {
    this.indicadorIndice = indicadorIndice;
  }

  public String getIndicadorIndiceId() {
    return this.indicadorIndiceId;
  }

  public void setIndicadorIndiceId(String indicadorIndiceId) {
    this.indicadorIndiceId = indicadorIndiceId;
  }

  public String getIndicadorIndiceTipoComparacion() {
    return this.indicadorIndiceTipoComparacion;
  }

  public void setIndicadorIndiceTipoComparacion(String indicadorIndiceTipoComparacion) {
    this.indicadorIndiceTipoComparacion = indicadorIndiceTipoComparacion;
  }

  public String getIndicadorIndiceTipoVariacion() {
    return this.indicadorIndiceTipoVariacion;
  }

  public void setIndicadorIndiceTipoVariacion(String indicadorIndiceTipoVariacion) {
    this.indicadorIndiceTipoVariacion = indicadorIndiceTipoVariacion;
  }

  public String getIndicadorPromedio() {
    return this.indicadorPromedio;
  }

  public void setIndicadorPromedio(String indicadorPromedio) {
    this.indicadorPromedio = indicadorPromedio;
  }

  public String getIndicadorPromedioId() {
    return this.indicadorPromedioId;
  }

  public void setIndicadorPromedioId(String indicadorPromedioId) {
    this.indicadorPromedioId = indicadorPromedioId;
  }

  public String getIndicadorSumatoria() {
    return this.indicadorSumatoria;
  }

  public void setIndicadorSumatoria(String indicadorSumatoria) {
    this.indicadorSumatoria = indicadorSumatoria;
  }

  public String getIndicadorSumatoriaId() {
    return this.indicadorSumatoriaId;
  }

  public void setIndicadorSumatoriaId(String indicadorSumatoriaId) {
    this.indicadorSumatoriaId = indicadorSumatoriaId;
  }

  public String getIndicadorIndiceFrecuencia() {
    return this.indicadorIndiceFrecuencia;
  }

  public void setIndicadorIndiceFrecuencia(String indicadorIndiceFrecuencia) {
    this.indicadorIndiceFrecuencia = indicadorIndiceFrecuencia;
  }

  public Byte getIndicadorIndiceFrecuenciaId() {
    return this.indicadorIndiceFrecuenciaId;
  }

  public void setIndicadorIndiceFrecuenciaId(Byte indicadorIndiceFrecuenciaId) {
    this.indicadorIndiceFrecuenciaId = indicadorIndiceFrecuenciaId;
  }

  public Byte getIndicadorPromedioFrecuenciaId() {
    return this.indicadorPromedioFrecuenciaId;
  }

  public void setIndicadorPromedioFrecuenciaId(Byte indicadorPromedioFrecuenciaId) {
    this.indicadorPromedioFrecuenciaId = indicadorPromedioFrecuenciaId;
  }

  public String getIndicadorIndiceUnidad() {
    return this.indicadorIndiceUnidad;
  }

  public void setIndicadorIndiceUnidad(String indicadorIndiceUnidad) {
    this.indicadorIndiceUnidad = indicadorIndiceUnidad;
  }

  public String getIndicadorPromedioFrecuencia() {
    return this.indicadorPromedioFrecuencia;
  }

  public void setIndicadorPromedioFrecuencia(String indicadorPromedioFrecuencia) {
    this.indicadorPromedioFrecuencia = indicadorPromedioFrecuencia;
  }

  public String getIndicadorPromedioUnidad() {
    return this.indicadorPromedioUnidad;
  }

  public void setIndicadorPromedioUnidad(String indicadorPromedioUnidad) {
    this.indicadorPromedioUnidad = indicadorPromedioUnidad;
  }

  public String getIndicadorSumatoriaFrecuencia() {
    return this.indicadorSumatoriaFrecuencia;
  }

  public void setIndicadorSumatoriaFrecuencia(String indicadorSumatoriaFrecuencia) {
    this.indicadorSumatoriaFrecuencia = indicadorSumatoriaFrecuencia;
  }

  public Byte getIndicadorSumatoriaFrecuenciaId() {
    return this.indicadorSumatoriaFrecuenciaId;
  }

  public void setIndicadorSumatoriaFrecuenciaId(Byte indicadorSumatoriaFrecuenciaId) {
    this.indicadorSumatoriaFrecuenciaId = indicadorSumatoriaFrecuenciaId;
  }

  public String getIndicadorSumatoriaUnidad() {
    return this.indicadorSumatoriaUnidad;
  }

  public void setIndicadorSumatoriaUnidad(String indicadorSumatoriaUnidad) {
    this.indicadorSumatoriaUnidad = indicadorSumatoriaUnidad;
  }

  public String getTipoVariacionDiferenciaPorcentualIndicadorIndice() {
    return TipoVariacionIndicadorIndice.getTipoVariacionDiferenciaPorcentual();
  }

  public String getTipoVariacionDiferenciaAbsolutaIndicadorIndice() {
    return TipoVariacionIndicadorIndice.getTipoVariacionDiferenciaAbsoluta();
  }

  public String getTipoVariacionVariacionPorcentualIndicadorIndice() {
    return TipoVariacionIndicadorIndice.getTipoVariacionVariacionPorcentual();
  }

  public String getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndice() {
    return TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndice();
  }

  public String getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice() {
    return TipoComparacionIndicadorIndice.getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice();
  }

  public String getTipoComparacionAnoActualPrimerPeriodoIndicadorIndice() {
    return TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPrimerPeriodoIndicadorIndice();
  }

  public String getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice() {
    return TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice();
  }

  public String getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndiceNombre() {
    return TipoComparacionIndicadorIndice.getNombre(TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndice());
  }

  public String getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndiceNombre() {
    return TipoComparacionIndicadorIndice.getNombre(TipoComparacionIndicadorIndice.getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice());
  }

  public String getTipoComparacionAnoActualPrimerPeriodoIndicadorIndiceNombre() {
    return TipoComparacionIndicadorIndice.getNombre(TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPrimerPeriodoIndicadorIndice());
  }

  public String getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndiceNombre() {
    return TipoComparacionIndicadorIndice.getNombre(TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice());
  }

  public String getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndiceFormula() {
    return TipoComparacionIndicadorIndice.getFormula(TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndice());
  }

  public String getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndiceFormula() {
    return TipoComparacionIndicadorIndice.getFormula(TipoComparacionIndicadorIndice.getTipoComparacionAnoAnteriorMismoPeriodoIndicadorIndice());
  }

  public String getTipoComparacionAnoActualPrimerPeriodoIndicadorIndiceFormula() {
    return TipoComparacionIndicadorIndice.getFormula(TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPrimerPeriodoIndicadorIndice());
  }

  public String getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndiceFormula() {
    String formula = TipoComparacionIndicadorIndice.getFormula(TipoComparacionIndicadorIndice.getTipoComparacionAnoActualPeriodoCierreAnteriorIndicadorIndice());

    if (this.mesCierreOrganizacion != null) {
      formula = formula.replaceAll("\\{0\\}", this.mesCierreOrganizacion);
    }

    return formula;
  }

  public Byte getFrecuenciaDiaria() {
    return Frecuencia.getFrecuenciaDiaria();
  }

  public Byte getFrecuenciaSemanal() {
    return Frecuencia.getFrecuenciaSemanal();
  }

  public Byte getFrecuenciaQuincenal() {
    return Frecuencia.getFrecuenciaQuincenal();
  }

  public Byte getFrecuenciaMensual() {
    return Frecuencia.getFrecuenciaMensual();
  }

  public Byte getFrecuenciaBimensual() {
    return Frecuencia.getFrecuenciaBimensual();
  }

  public Byte getFrecuenciaTrimestral() {
    return Frecuencia.getFrecuenciaTrimestral();
  }

  public Byte getFrecuenciaCuatrimestral() {
    return Frecuencia.getFrecuenciaCuatrimestral();
  }

  public Byte getFrecuenciaSemestral() {
    return Frecuencia.getFrecuenciaSemestral();
  }

  public Byte getFrecuenciaAnual() {
    return Frecuencia.getFrecuenciaAnual();
  }

  public String getMesCierreOrganizacion() {
    return this.mesCierreOrganizacion;
  }

  public void setMesCierreOrganizacion(String mesCierreOrganizacion) {
    this.mesCierreOrganizacion = mesCierreOrganizacion;
  }

  public Boolean getCopiarArbol() 
  {
    if (this.copiarArbol == null) 
    {
      this.copiarArbol = new Boolean(false);
    }

    return this.copiarArbol;
  }

  public void setCopiarArbol(Boolean copiarArbol) 
  {
    this.copiarArbol = copiarArbol;
  }

  	public Boolean getCopiarMediciones() 
  	{
  		if (this.copiarMediciones == null) 
  			this.copiarMediciones = new Boolean(false);

  		return this.copiarMediciones;
  	}

  public void setCopiarMediciones(Boolean copiarMediciones) 
  {
    this.copiarMediciones = copiarMediciones;
  }

  public Boolean getCopiarPlantillasGraficos() 
  {
    if (this.copiarPlantillasGraficos == null) 
    {
      this.copiarPlantillasGraficos = new Boolean(false);
    }

    return this.copiarPlantillasGraficos;
  }

  public void setCopiarPlantillasGraficos(Boolean copiarPlantillasGraficos) 
  {
    this.copiarPlantillasGraficos = copiarPlantillasGraficos;
  }

  public Boolean getCopiarPlantillasReportes() 
  {
    if (this.copiarPlantillasReportes == null) 
    {
      this.copiarPlantillasReportes = new Boolean(false);
    }

    return this.copiarPlantillasReportes;
  }

  public void setCopiarPlantillasReportes(Boolean copiarPlantillasReportes) 
  {
    this.copiarPlantillasReportes = copiarPlantillasReportes;
  }

  public String getNuevoNombre() 
  {
    return this.nuevoNombre;
  }

  public void setNuevoNombre(String nuevoNombre) 
  {
    this.nuevoNombre = nuevoNombre;
  }
  
  	public OrganizacionStrategos getOrganizacion() 
  	{
  		return this.organizacion;
  	}

	public void setOrganizacion(OrganizacionStrategos organizacion) 
	{
		this.organizacion = organizacion;
	}
 
	public Boolean getAsignarInventario() 
	{
		return this.asignarInventario;
	}

	public void setAsignarInventario(Boolean asignarInventario) 
	{
		this.asignarInventario = asignarInventario;
	}

	public Boolean getCopiarInsumos() 
	{
  		if (this.copiarInsumos == null) 
  			this.copiarInsumos = new Boolean(false);
		return this.copiarInsumos;
	}

	public void setCopiarInsumos(Boolean copiarInsumos) 
	{
		this.copiarInsumos = copiarInsumos;
	}

	public List<Long> getIndicadores() 
	{
		return this.indicadores;
	}

	public void setIndicadores(List<Long> indicadores) 
	{
		this.indicadores = indicadores;
	}
	
	public Boolean getEsIndicadorIniciativa() 
	{
		return this.esIndicadorIniciativa;
	}

	public void setEsIndicadorIniciativa(Boolean esIndicadorIniciativa) 
	{
		this.esIndicadorIniciativa = esIndicadorIniciativa;
	}
	
	public void clear()
	{
		clear(false);
	}
	
	public void clear(Boolean mantenerVariables)
	{
		this.nombreIndicadorSingular = null;
		this.indicadorId = new Long(0L);
		this.claseId = new Long(0L);
		this.organizacionId = new Long(0L);
		this.descripcion = null;
		this.naturalezaOriginal = null;
		this.frecuenciaOriginal = null;
		this.codigoEnlace = null;
		this.nombre = null;
		this.nombreLargo = null;
		this.insumosFormula = null;
		this.formula = null;
		this.indicadorAsociadoTipo = null;
		this.indicadorAsociadoId = null;
		this.indicadorAsociadoNombre = null;
		this.indicadorAsociadoFrecuencia = null;
		this.indicadorAsociadoUnidad = null;
		this.indicadorAsociadoRevision = null;
		this.indicadores = null;
		this.setStatus(StatusUtil.getStatusSuccess());
		if (this.tipoCargaMedicion == null)
			this.tipoCargaMedicion = TipoMedicion.getTipoMedicionEnPeriodo();
		
		if (!mantenerVariables)
		{
			this.naturaleza = Naturaleza.getNaturalezaSimple();
			this.frecuencia = Frecuencia.getFrecuenciaMensual();
			this.comportamiento = null;
			this.unidadId = new Long(0L);
			this.unidadesMedida = null;
			this.prioridad = null;
			this.mostrarEnArbol = new Boolean(true);
			this.fuente = null;
			this.orden = null;
			this.soloLectura = new Boolean(false);
			this.caracteristica = Caracteristica.getCaracteristicaRetoAumento();
			this.tipoGuiaResultado = TipoIndicador.getTipoIndicadorResultado();
			
			String listaSeries = getSeparadorSeries() + SerieTiempo.getSerieRealId() + getSeparadorSeries();
			this.seriesIndicador = listaSeries;
			
			this.responsableNotificacionId = new Long(0L);
			this.responsableFijarMetaId = new Long(0L);
			this.responsableLograrMetaId = new Long(0L);
			this.responsableSeguimientoId = new Long(0L);
			this.responsableCargarMetaId = new Long(0L);
			this.responsableCargarEjecutadoId = new Long(0L);
			this.responsableCargarEjecutado = null;
			this.responsableCargarMeta = null;
			this.responsableFijarMeta = null;
			this.responsableLograrMeta = null;
			this.responsableSeguimiento = null;
			this.responsableNotificacion = null;
			this.guia = new Boolean(false);
			this.corte = TipoCorte.getTipoCorteTransversal();
			this.enlaceParcial = null;
			this.alertaMinMax = new Integer(0);
			this.alertaMetaZonaVerde = null;
			this.alertaMetaZonaAmarilla = null;
			this.alertaTipoZonaAmarilla = TipoAlerta.getTipoAlertaPorcentaje();
			this.alertaTipoZonaVerde = TipoAlerta.getTipoAlertaPorcentaje();
	    
			byte valorVariable = 0;
			this.alertaValorVariableZonaVerde = new Byte(valorVariable);
			this.alertaValorVariableZonaAmarilla = new Byte(valorVariable);
			this.alertaIndicadorIdZonaVerde = new Long(0L);
			this.alertaIndicadorIdZonaAmarilla = new Long(0L);
			this.valorInicialCero = new Boolean(false);
			this.numeroDecimales = new Byte("2");
			this.tipoCargaMedicion = TipoMedicion.getTipoMedicionEnPeriodo();
			this.tipoSumaMedicion = TipoSuma.getTipoSumaSumarMediciones();

			this.parametroTipoAcotamientoSuperior = new Byte((byte) 1);
			this.parametroTipoAcotamientoInferior = new Byte((byte) 1);
			this.parametroSuperiorValorFijo = null;
			this.parametroInferiorValorFijo = null;
			this.parametroSuperiorIndicadorId = null;
			this.parametroInferiorIndicadorId = null;
			this.parametroSuperiorIndicadorNombre = null;
			this.parametroInferiorIndicadorNombre = null;
			this.url = null;
			this.fechaBloqueoEjecutado = null;
			this.fechaBloqueoMeta = null;
			this.multidimensional = new Boolean(false);
			this.mediciones = null;
			this.nombreIndicadorZonaAmarilla = null;
			this.nombreIndicadorZonaVerde = null;
			this.tieneMediciones = null;
			this.escalaCualitativa = null;
			this.indicadorIndiceId = null;
			this.indicadorIndice = null;
			this.indicadorIndiceFrecuencia = null;
			this.indicadorIndiceFrecuenciaId = null;
			this.indicadorIndiceTipoVariacion = getTipoVariacionDiferenciaPorcentualIndicadorIndice();
			this.indicadorIndiceTipoComparacion = getTipoComparacionAnoActualPeriodoAnteriorIndicadorIndice();
			this.indicadorIndiceUnidad = null;
			this.indicadorPromedioId = null;
			this.indicadorPromedio = null;
			this.indicadorPromedioFrecuencia = null;
			this.indicadorPromedioFrecuenciaId = null;
			this.indicadorPromedioUnidad = null;
			this.indicadorSumatoria = null;
			this.indicadorSumatoriaId = null;
			this.indicadorSumatoriaFrecuencia = null;
			this.indicadorSumatoriaFrecuenciaId = null;
			this.indicadorSumatoriaUnidad = null;
			this.iniciativaId = null;
			this.planId = null;
			this.bloquearIndicadorIniciativa = new Boolean(false);
			this.desdeIniciativasPlanes = new Boolean(false);
			this.asignarInventario = new Boolean(false);
	    
			this.copiarArbol = new Boolean(false);
			this.copiarMediciones = new Boolean(false);
			this.copiarPlantillasGraficos = new Boolean(false);
			this.copiarPlantillasReportes = new Boolean(false);
			this.nuevoNombre = "";
			this.organizacion = new OrganizacionStrategos();
			this.copiarMediciones = new Boolean(false);
			this.esIndicadorIniciativa = new Boolean(false);
		}
	}
}