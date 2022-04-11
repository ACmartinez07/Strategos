package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.iniciativas.model.util.TipoCalculoEstadoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Calendar;
import java.util.List;

public class EditarIniciativaForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
  
	private final String SEPARADOR = "|+|";
	private String nombreIniciativaSingular;
	private Long iniciativaId;
	private String nombre;
	private String nombreLargo;
	private Byte frecuencia;
	private List frecuencias;
	private List grupoAnos;
	private String enteEjecutor;
	private Byte tipoAlerta;
  	private Double alertaZonaVerde;
  	private Double alertaZonaAmarilla;
  	private Long organizacionId;
  	private Long responsableFijarMetaId;
  	private Long responsableLograrMetaId;
  	private Long responsableSeguimientoId;
  	private Long responsableCargarMetaId;
  	private Long responsableCargarEjecutadoId;
  	private String descripcion;
  	private String resultado;
  	private String resultadoEspecificoIniciativa;
  	private Integer ano;
  	private Boolean visible;
  	private String responsableFijarMeta;
  	private String responsableLograrMeta;
  	private String responsableSeguimiento;
  	private String responsableCargarMeta;
  	private String responsableCargarEjecutado;
  	private Long planId;
  	private Long perspectivaId;
  	private Long iniciativaAsociadaPlanId;
  	private String planNombre;
  	private String perspectivaNombre;
  	private String nombreObjetoPerspectiva;
  	private String nombreObjetoIniciativa;
  	private String frecuenciaNombre;
  	private String unidadNombre;
  	private String seriesIndicador;
  	private Boolean hayValorPorcentajeAmarillo;
  	private Boolean hayValorPorcentajeVerde;
  	private String organizacionNombre;
  	private Byte tipoMedicion;
  	private Boolean eliminarMediciones;
	private Long estatusId;
	private IniciativaEstatus estatus;
	private List<IniciativaEstatus> estatuses;
	private Double porcentajeCompletado;
	private String anioFormulacion;
	private Long tipoId;
	private TipoProyecto tipoProyecto;
	private List<TipoProyecto> tipos;
	private Boolean desdeInstrumento;
	private Long instrumentoId;
	
	//Campos nuevos
	private String responsableProyecto;	
	private String cargoResponsable;
	private String organizacionesInvolucradas;
	private String objetivoEstrategico;
	private String fuenteFinanciacion;
	private String montoFinanciamiento;
	private String iniciativaEstrategica;
	private String liderIniciativa;
	private String tipoIniciativa;
	private String poblacionBeneficiada;
	private String contexto;
	private String definicionProblema;
	private String alcance;
	private String objetivoGeneral;
	private String objetivoEspecificos;
	
		
    public Long getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public List<TipoProyecto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoProyecto> tipos) {
		this.tipos = tipos;
	}
	
	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public TipoProyecto getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyecto tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

  public String getNombreIniciativaSingular()
  {
    return this.nombreIniciativaSingular;
  }

  public void setNombreIniciativaSingular(String nombreIniciativaSingular) {
    this.nombreIniciativaSingular = nombreIniciativaSingular;
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

  public String getSEPARADOR() {
    return "|+|";
  }

  public String getResultadoEspecificoIniciativa() {
    return this.resultadoEspecificoIniciativa;
  }

  public void setResultadoEspecificoIniciativa(String resultadoEspecificoIniciativa) {
    this.resultadoEspecificoIniciativa = resultadoEspecificoIniciativa;
  }

  public String getResultado() {
    return this.resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Long getIniciativaId() {
    return this.iniciativaId;
  }

  public void setIniciativaId(Long iniciativaId) {
    this.iniciativaId = iniciativaId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getAnioFormulacion() {
	    return this.anioFormulacion;
  }

  public void setAnioFormulacion(String anioFormulacion) {
	    this.anioFormulacion = anioFormulacion;
  }

  public Byte getTipoAlerta() {
    return this.tipoAlerta;
  }

  public void setTipoAlerta(Byte tipoAlerta) {
    this.tipoAlerta = tipoAlerta;
  }

  public Double getAlertaZonaVerde() {
    return this.alertaZonaVerde;
  }

  public void setAlertaZonaVerde(Double alertaZonaVerde) {
    this.alertaZonaVerde = alertaZonaVerde;
  }

  public Double getAlertaZonaAmarilla() {
    return this.alertaZonaAmarilla;
  }

  public void setAlertaZonaAmarilla(Double alertaZonaAmarilla) {
    this.alertaZonaAmarilla = alertaZonaAmarilla;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public List getFrecuencias() {
    return this.frecuencias;
  }

  public void setFrecuencias(List frecuencias) {
    this.frecuencias = frecuencias;
  }

  public List getGrupoAnos() {
    return this.grupoAnos;
  }

  public void setGrupoAnos(List grupoAnos) {
    this.grupoAnos = grupoAnos;
  }

  public String getNombreLargo() {
    return this.nombreLargo;
  }

  public void setNombreLargo(String nombreLargo) {
    this.nombreLargo = nombreLargo;
  }

  public String getEnteEjecutor() {
    return this.enteEjecutor;
  }

  public void setEnteEjecutor(String enteEjecutor) {
    this.enteEjecutor = enteEjecutor;
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

  public Integer getAno() {
    return this.ano;
  }

  public void setAno(Integer ano) {
    this.ano = ano;
  }

  public Boolean getVisible() {
    return this.visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public Long getIniciativaAsociadaPlanId() {
    return this.iniciativaAsociadaPlanId;
  }

  public void setIniciativaAsociadaPlanId(Long iniciativaAsociadaPlanId) {
    this.iniciativaAsociadaPlanId = iniciativaAsociadaPlanId;
  }

  public Long getPlanId() {
    return this.planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public Long getPerspectivaId() {
    return this.perspectivaId;
  }

  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }

  public String getPerspectivaNombre() {
    return this.perspectivaNombre;
  }

  public void setPerspectivaNombre(String perspectivaNombre) {
    this.perspectivaNombre = perspectivaNombre;
  }

  public String getPlanNombre() {
    return this.planNombre;
  }

  public void setPlanNombre(String planNombre) {
    this.planNombre = planNombre;
  }

  public String getNombreObjetoPerspectiva() {
    return this.nombreObjetoPerspectiva;
  }

  public void setNombreObjetoPerspectiva(String nombreObjetoPerspectiva) {
    this.nombreObjetoPerspectiva = nombreObjetoPerspectiva;
  }

  public String getFrecuenciaNombre() {
    return this.frecuenciaNombre;
  }

  public void setFrecuenciaNombre(String frecuenciaNombre) {
    this.frecuenciaNombre = frecuenciaNombre;
  }

  public String getUnidadNombre() {
    return this.unidadNombre;
  }

  public void setUnidadNombre(String unidadNombre) {
    this.unidadNombre = unidadNombre;
  }

  public String getNombreObjetoIniciativa() {
    return this.nombreObjetoIniciativa;
  }

  public void setNombreObjetoIniciativa(String nombreObjetoIniciativa) {
    this.nombreObjetoIniciativa = nombreObjetoIniciativa;
  }
  
  public String getSeriesIndicador() 
  {
    return this.seriesIndicador;
  }

  public void setSeriesIndicador(String seriesIndicador) 
  {
    this.seriesIndicador = seriesIndicador;
  }
  
  public String getSeparadorSeries() 
  {
    return new EditarIndicadorForm().getSeparadorSeries();
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

  	public String getOrganizacionNombre() 
  	{
  		return this.organizacionNombre;
  	}

	public void setOrganizacionNombre(String organizacionNombre) 
	{
		this.organizacionNombre = organizacionNombre;
	}
	
  	public Byte getTipoMedicion() 
  	{
	  return this.tipoMedicion;
  	}

  	public void setTipoMedicion(Byte tipoMedicion) 
  	{
	  this.tipoMedicion = tipoMedicion;
  	}

  	public Boolean getEliminarMediciones() 
  	{
  		return this.eliminarMediciones;
  	}

  	public void setEliminarMediciones(Boolean eliminarMediciones) 
  	{
  		this.eliminarMediciones = eliminarMediciones;
  	}

	public Long getEstatusId() 
	{
		return this.estatusId;
	}	

	public void setEstatusId(Long estatusId) 
	{
		this.estatusId = estatusId;
	}
	
	public IniciativaEstatus getEstatus() 
	{
		return this.estatus;
	}	

	public void setEstatus(IniciativaEstatus estatus) 
	{
		this.estatus = estatus;
	}

	public List<IniciativaEstatus> getEstatuses() 
	{
		return this.estatuses;
	}	

	public void setEstatuses(List<IniciativaEstatus> estatuses) 
	{
		this.estatuses = estatuses;
	}
	
	public Double getPorcentajeCompletado() 
	{
	    return this.porcentajeCompletado;
	}

	public void setPorcentajeCompletado(Double porcentajeCompletado) 
	{
	    this.porcentajeCompletado = porcentajeCompletado;
	}
		
  	public Boolean getDesdeInstrumento() {
		return desdeInstrumento;
	}

	public void setDesdeInstrumento(Boolean desdeInstrumento) {
		this.desdeInstrumento = desdeInstrumento;
	}
	
	public String getResponsableProyecto() {
		return responsableProyecto;
	}

	public void setResponsableProyecto(String responsableProyecto) {
		this.responsableProyecto = responsableProyecto;
	}

	public String getCargoResponsable() {
		return cargoResponsable;
	}

	public void setCargoResponsable(String cargoResponsable) {
		this.cargoResponsable = cargoResponsable;
	}

	public String getOrganizacionesInvolucradas() {
		return organizacionesInvolucradas;
	}

	public void setOrganizacionesInvolucradas(String organizacionesInvolucradas) {
		this.organizacionesInvolucradas = organizacionesInvolucradas;
	}

	public String getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(String objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	public String getFuenteFinanciacion() {
		return fuenteFinanciacion;
	}

	public void setFuenteFinanciacion(String fuenteFinanciacion) {
		this.fuenteFinanciacion = fuenteFinanciacion;
	}

	public String getMontoFinanciamiento() {
		return montoFinanciamiento;
	}

	public void setMontoFinanciamiento(String montoFinanciamiento) {
		this.montoFinanciamiento = montoFinanciamiento;
	}

	public String getIniciativaEstrategica() {
		return iniciativaEstrategica;
	}

	public void setIniciativaEstrategica(String iniciativaEstrategica) {
		this.iniciativaEstrategica = iniciativaEstrategica;
	}

	public String getLiderIniciativa() {
		return liderIniciativa;
	}

	public void setLiderIniciativa(String liderIniciativa) {
		this.liderIniciativa = liderIniciativa;
	}

	public String getTipoIniciativa() {
		return tipoIniciativa;
	}

	public void setTipoIniciativa(String tipoIniciativa) {
		this.tipoIniciativa = tipoIniciativa;
	}

	public String getPoblacionBeneficiada() {
		return poblacionBeneficiada;
	}

	public void setPoblacionBeneficiada(String poblacionBeneficiada) {
		this.poblacionBeneficiada = poblacionBeneficiada;
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	public String getDefinicionProblema() {
		return definicionProblema;
	}

	public void setDefinicionProblema(String definicionProblema) {
		this.definicionProblema = definicionProblema;
	}

	public String getAlcance() {
		return alcance;
	}

	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getObjetivoEspecificos() {
		return objetivoEspecificos;
	}

	public void setObjetivoEspecificos(String objetivoEspecificos) {
		this.objetivoEspecificos = objetivoEspecificos;
	}


	public void clear()
	{
		Calendar fechaActual = Calendar.getInstance();

		int anoActual = fechaActual.get(1);

		this.nombreIniciativaSingular = null;
		this.iniciativaId = new Long(0L);
		this.nombre = null;
		this.nombreLargo = null;
		this.descripcion = null;
		this.enteEjecutor = null;
		this.frecuencia = Frecuencia.getFrecuenciaTrimestral();
		this.responsableCargarEjecutado = null;
		this.responsableCargarMeta = null;
		this.responsableFijarMeta = null;
		this.responsableLograrMeta = null;
		this.responsableSeguimiento = null;
		this.tipoAlerta = TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorActividades();
		this.alertaZonaAmarilla = null;
		this.alertaZonaVerde = null;
	    this.hayValorPorcentajeAmarillo = false;
	    this.hayValorPorcentajeVerde = false;
		this.ano = new Integer(anoActual);
		this.resultado = null;
		int anoTemp = anoActual - 5;
		int anoFinal = anoActual + 5;
		String resultadosEspecificos = "";
		while (anoTemp < anoFinal) 
		{
			resultadosEspecificos = resultadosEspecificos + "|+|";
			anoTemp++;
		}
		this.resultadoEspecificoIniciativa = resultadosEspecificos;
		this.planId = null;
		this.visible = new Boolean(true);
		setBloqueado(new Boolean(false));
		this.organizacionNombre = "";
		this.tipoMedicion = TipoMedicion.getTipoMedicionEnPeriodo();
		this.eliminarMediciones = new Boolean(false);
		this.estatusId = EstatusType.getEstatusSinIniciar();
		this.estatus = EstatusType.setEstatusIniciado();
		this.tipoId = Long.valueOf(1L);
		this.tipoProyecto = new TipoProyecto();
		this.porcentajeCompletado = null;
		this.desdeInstrumento = false;
		
		this.responsableProyecto = null;
		this.cargoResponsable = null;
		this.organizacionesInvolucradas = null;
		this.objetivoEstrategico = null;
		this.fuenteFinanciacion = null;
		this.montoFinanciamiento = null;
		this.iniciativaEstrategica = null;
		this.liderIniciativa = null;
		this.tipoIniciativa = null;
		this.poblacionBeneficiada = null;
		this.contexto = null;
		this.definicionProblema = null;
		this.alcance = null;
		this.objetivoGeneral = null;
		this.objetivoEspecificos = null;
				
		this.seriesIndicador = getSeparadorSeries() + SerieTiempo.getSerieRealId() + getSeparadorSeries() + SerieTiempo.getSerieProgramadoId() + getSeparadorSeries();    
	}
}