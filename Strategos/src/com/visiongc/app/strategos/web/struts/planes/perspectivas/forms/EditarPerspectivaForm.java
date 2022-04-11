package com.visiongc.app.strategos.web.struts.planes.perspectivas.forms;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarPerspectivaForm extends EditarObjetoForm
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
  private String insumosAsociados;

  // Variables para copiar organizacion
  private Boolean copiarArbol;
  private Boolean copiarMediciones;
  private Boolean copiarPlantillasGraficos;
  private Boolean copiarPlantillasReportes;
  private String nuevoNombre;
  
  public Long getPerspectivaId()
  {
    return this.perspectivaId;
  }

  public void setPerspectivaId(Long perspectivaId) {
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
    {
      this.copiarMediciones = new Boolean(false);
    }

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
  
  	public String getInsumosAsociados() 
  	{
  		return this.insumosAsociados;
  	}

	public void setInsumosAsociados(String insumosAsociados) 
	{
	    this.insumosAsociados = insumosAsociados;
	}
	
	public String getSeparadorRuta() 
	{
		return "!#!";
	}
	
	public String getSeparadorObjetivos() 
	{
		return "!;!";
	}
	
	public String getCodigoEliminado() 
	{
		return "!ELIMINADO!";
	}
  
	public void clear() 
	{
	    this.perspectivaId = new Long(0L);
	    this.planId = null;
	    this.responsableId = null;
	    this.nombreResponsable = null;
	    this.nombre = null;
	    this.padreId = null;
	    this.descripcion = null;
	    this.orden = null;
	    this.frecuencia = Frecuencia.getFrecuenciaTrimestral();
	    this.objetivoImpactaId = null;
	    this.titulo = null;
	    this.tipo = null;
	    this.fechaUltimaMedicion = null;
	    this.ultimaMedicionAnual = null;
	    this.ultimaMedicionParcial = null;
	    this.creado = null;
	    this.modificado = null;
	    this.creadoId = null;
	    this.modificadoId = null;
	    this.nombreUsuarioCreado = null;
	    this.nombreUsuarioModificado = null;
	    this.crecimiento = null;
	    this.ano = null;
	    this.claseId = null;
	    this.nlAnoIndicadorId = null;
	    this.nlParIndicadorId = null;
	    this.tipoCalculo = null;
	    this.elementosAsociados = null;
	    this.indicadoresAsociados = null;
	    this.iniciativasAsociadas = null;
	
	    this.copiarArbol = new Boolean(false);
	    this.copiarMediciones = new Boolean(false);
	    this.copiarPlantillasGraficos = new Boolean(false);
	    this.copiarPlantillasReportes = new Boolean(false);
	    this.nuevoNombre = "";       
	}
}