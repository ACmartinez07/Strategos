package com.visiongc.app.strategos.web.struts.planes.perspectivas.forms;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import org.apache.struts.action.ActionForm;

public class VisualizarPerspectivaForm extends ActionForm
{
	static final long serialVersionUID = 0L;
	
	private Long perspectivaId;
	private Long planId;
	private Long responsableId;
	private String nombreResponsable;
	private String nombre;
	private Long padreId;
	private String descripcion;
	private Integer orden;
	private Byte frecuencia;
	private String nombreFrecuencia;
	private Long objetivoImpactaId;
	private String titulo;
	private Byte tipo;
	private String fechaUltimaMedicion;
	private Double ultimaMedicionAnual;
	private Double ultimaMedicionParcial;
	private Integer periodoEvaluacion;
	private Integer anoEvaluacion;
	private String estadoAnual;
	private String estadoParcial;
	private String evaluacion;
	private String creado;
	private String modificado;
	private Long creadoId;
	private Long modificadoId;
	private String nombreUsuarioCreado;
	private String nombreUsuarioModificado;
	private Byte crecimiento;
	private Integer ano;
	private Long claseId;
	private Long nlAnoIndicadorId;
  	private Long nlParIndicadorId;
  	private Byte tipoCalculo;
  	private Integer elementosAsociados;
  	private Integer indicadoresAsociados;
  	private Integer iniciativasAsociadas;
  	private Boolean mostrarObjetosAsociados;
  	private String indicadoresIds;
  	private Integer nivel;
  	private String nombreObjetoPerspectiva;
  	private PlantillaPlanes plantillaPlan;
  	private Plan plan;
  	private Byte tipoVista;
  	private String respuesta;
  	private Boolean vinculoCausaEfecto;

  	public String getRespuesta()
  	{
  		return this.respuesta;
  	}

  	public void setRespuesta(String respuesta) 
  	{
  		this.respuesta = respuesta;
  	}

  	public Long getPerspectivaId()
  	{
  		return this.perspectivaId;
  	}

  	public void setPerspectivaId(Long perspectivaId) 
  	{
  		this.perspectivaId = perspectivaId;
  	}

  public Long getPlanId() {
    return this.planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public String getNombreResponsable() {
    return this.nombreResponsable;
  }

  public void setNombreResponsable(String nombreResponsable) {
    this.nombreResponsable = nombreResponsable;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getOrden() {
    return this.orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public String getNombreFrecuencia() {
    return this.nombreFrecuencia;
  }

  public void setNombreFrecuencia(String nombreFrecuencia) {
    this.nombreFrecuencia = nombreFrecuencia;
  }

  public Long getObjetivoImpactaId() {
    return this.objetivoImpactaId;
  }

  public void setObjetivoImpactaId(Long objetivoImpactaId) {
    this.objetivoImpactaId = objetivoImpactaId;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public String getFechaUltimaMedicion() {
    return this.fechaUltimaMedicion;
  }

  public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }

  public Double getUltimaMedicionAnual() {
    return this.ultimaMedicionAnual;
  }

  public void setUltimaMedicionAnual(Double ultimaMedicionAnual) {
    this.ultimaMedicionAnual = ultimaMedicionAnual;
  }

  public Double getUltimaMedicionParcial() {
    return this.ultimaMedicionParcial;
  }

  public void setUltimaMedicionParcial(Double ultimaMedicionParcial) {
    this.ultimaMedicionParcial = ultimaMedicionParcial;
  }

  public String getCreado() {
    return this.creado;
  }

  public void setCreado(String creado) {
    this.creado = creado;
  }

  public String getModificado() {
    return this.modificado;
  }

  public void setModificado(String modificado) {
    this.modificado = modificado;
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

  public Byte getCrecimiento() {
    return this.crecimiento;
  }

  public void setCrecimiento(Byte crecimiento) {
    this.crecimiento = crecimiento;
  }

  public Integer getAno() {
    return this.ano;
  }

  public void setAno(Integer ano) {
    this.ano = ano;
  }

  public Long getClaseId() {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public Long getNlAnoIndicadorId() {
    return this.nlAnoIndicadorId;
  }

  public void setNlAnoIndicadorId(Long nlAnoIndicadorId) {
    this.nlAnoIndicadorId = nlAnoIndicadorId;
  }

  public Long getNlParIndicadorId() {
    return this.nlParIndicadorId;
  }

  public void setNlParIndicadorId(Long nlParIndicadorId) {
    this.nlParIndicadorId = nlParIndicadorId;
  }

  public Byte getTipoCalculo() {
    return this.tipoCalculo;
  }

  public void setTipoCalculo(Byte tipoCalculo) {
    this.tipoCalculo = tipoCalculo;
  }

  public Boolean getMostrarObjetosAsociados() {
    return this.mostrarObjetosAsociados;
  }

  public void setMostrarObjetosAsociados(Boolean mostrarObjetosAsociados) {
    this.mostrarObjetosAsociados = mostrarObjetosAsociados;
  }

  public Integer getElementosAsociados() {
    return this.elementosAsociados;
  }

  public void setElementosAsociados(Integer elementosAsociados) {
    this.elementosAsociados = elementosAsociados;
  }

  public Integer getIndicadoresAsociados() {
    return this.indicadoresAsociados;
  }

  public void setIndicadoresAsociados(Integer indicadoresAsociados) {
    this.indicadoresAsociados = indicadoresAsociados;
  }

  public Integer getIniciativasAsociadas() {
    return this.iniciativasAsociadas;
  }

  public void setIniciativasAsociadas(Integer iniciativasAsociadas) {
    this.iniciativasAsociadas = iniciativasAsociadas;
  }

  public Byte getTipoCalculoPerspectivaAutomatico() {
    return TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico();
  }

  public Byte getTipoCalculoPerspectivaManual() {
    return TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual();
  }

  public String getIndicadoresIds() {
    return this.indicadoresIds;
  }

  public void setIndicadoresIds(String indicadoresIds) {
    this.indicadoresIds = indicadoresIds;
  }

  public Integer getPeriodoEvaluacion() {
    return this.periodoEvaluacion;
  }

  public void setPeriodoEvaluacion(Integer periodoEvaluacion) {
    this.periodoEvaluacion = periodoEvaluacion;
  }

  public Integer getAnoEvaluacion() {
    return this.anoEvaluacion;
  }

  public void setAnoEvaluacion(Integer anoEvaluacion) {
    this.anoEvaluacion = anoEvaluacion;
  }

  public String getEstadoAnual() {
    return this.estadoAnual;
  }

  public void setEstadoAnual(String estadoAnual) {
    this.estadoAnual = estadoAnual;
  }

  public String getEstadoParcial() {
    return this.estadoParcial;
  }

  public void setEstadoParcial(String estadoParcial) {
    this.estadoParcial = estadoParcial;
  }

  public String getEvaluacion() {
    return this.evaluacion;
  }

  	public void setEvaluacion(String evaluacion) 
  	{
  		this.evaluacion = evaluacion;
  	}

  	public Integer getNivel() 
  	{
  		return this.nivel;
  	}

  	public void setNivel(Integer nivel) 
  	{
  		this.nivel = nivel;
  	}	

  	public String getNombreObjetoPerspectiva() 
  	{
  		return this.nombreObjetoPerspectiva;
  	}

  	public void setNombreObjetoPerspectiva(String nombreObjetoPerspectiva) 
  	{
  		this.nombreObjetoPerspectiva = nombreObjetoPerspectiva;
  	}

  	public PlantillaPlanes getPlantillaPlan() 
  	{
  		return this.plantillaPlan;
  	}	

  	public void setPlantillaPlan(PlantillaPlanes plantillaPlan) 
  	{
  		this.plantillaPlan = plantillaPlan;
  	}

  	public Plan getPlan() 
  	{
  		return this.plan;
  	}

  	public void setPlan(Plan plan) 
  	{
  		this.plan = plan;
  	}

  	public Byte getTipoVista() 
  	{
  		return this.tipoVista;
  	}

  	public void setTipoVista(Byte tipoVista) 
  	{
  		this.tipoVista = tipoVista;
  	}

  	public Boolean getVinculoCausaEfecto() 
  	{
  		return this.vinculoCausaEfecto;
  	}

  	public void setVinculoCausaEfecto(Boolean vinculoCausaEfecto) 
  	{
  		this.vinculoCausaEfecto = vinculoCausaEfecto;
  	}
  	
  	public Byte getIndicadorNaturalezaFormula() 
  	{
  		return Naturaleza.getNaturalezaFormula();
  	}
}