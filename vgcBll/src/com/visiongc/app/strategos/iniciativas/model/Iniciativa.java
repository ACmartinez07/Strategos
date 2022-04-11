package com.visiongc.app.strategos.iniciativas.model;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.util.AlertaObjetivo;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Iniciativa
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long iniciativaId;
  private String nombre;
  private Long proyectoId;
  private Byte tipoAlerta;
  private Double alertaZonaVerde;
  private Double alertaZonaAmarilla;
  private Long organizacionId;
  private Byte frecuencia;
  private String nombreLargo;
  private String enteEjecutor;
  private Byte naturaleza;
  private Long responsableFijarMetaId;
  private Long responsableLograrMetaId;
  private Long responsableSeguimientoId;
  private Long responsableCargarMetaId;
  private Long responsableCargarEjecutadoId;
  private OrganizacionStrategos organizacion;
  private Responsable responsableFijarMeta;
  private Responsable responsableLograrMeta;
  private Responsable responsableSeguimiento;
  private Responsable responsableCargarMeta;
  private Responsable responsableCargarEjecutado;
  private MemoIniciativa memoIniciativa;
  private Set<ResultadoEspecificoIniciativa> resultadosEspecificosIniciativa;
  private String descripcion;
  private Boolean soloLectura;
  private Set<IniciativaPerspectiva> iniciativaPerspectivas;
  private Long claseId;
  private Byte alerta;
  private NodoArbol nodoArbolPadre;
  private Collection nodoArbolHijos;
  private boolean nodoArbolHijosCargados;
  private List<PryActividad> listaActividades;
  private Double porcentajeCompletado;
  private String fechaUltimaMedicion;
  private Set<IniciativaPlan> iniciativaPlanes;
  private List<Iniciativa> iniciativasAsociadas;
  private Set<IndicadorIniciativa> iniciativaIndicadores;
  private Double porcentajeEsperado;
  private Double ultimaMedicion;
  private Byte tipoMedicion;
  private Date historicoDate;
  private Byte condicion;
  private Long estatusId;
  private Long tipoId;
  private IniciativaEstatus estatus;
  private String anioFormulacion;
  private TipoProyecto tipoProyecto;
  
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
  
  //estos son campos temporales, no tienen mapeo de base de datos
  private String fechaesperado;
  private int dias;
  private String observacion;
  private String organizacionNombre;
  
  public Iniciativa(Long iniciativaId, Long organizacionId, String nombre, Byte naturaleza, Byte frecuencia)
  {
    this.iniciativaId = iniciativaId;
    this.organizacionId = organizacionId;
    this.nombre = nombre;
    
    this.naturaleza = naturaleza;
    this.frecuencia = frecuencia;
  }
  
  public Iniciativa(Long iniciativaId, Long organizacionId, String nombre, Byte naturaleza, Byte frecuencia, Double porcentajeCompletado)
  {
    this.iniciativaId = iniciativaId;
    this.organizacionId = organizacionId;
    this.nombre = nombre;
    this.naturaleza = naturaleza;
    this.frecuencia = frecuencia;
    this.porcentajeCompletado = porcentajeCompletado;
  }
  
  public Iniciativa(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
  }
  

  public Iniciativa() {}
  
  

  public Long getTipoId() {
	return tipoId;
  }

  public void setTipoId(Long tipoId) {
	this.tipoId = tipoId;
  }

  public Long getIniciativaId()
  {
    return iniciativaId;
  }
  
  public void setIniciativaId(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Long getProyectoId()
  {
    return proyectoId;
  }
  
  public void setProyectoId(Long proyectoId)
  {
    this.proyectoId = proyectoId;
  }
  
  public Byte getTipoAlerta()
  {
    return tipoAlerta;
  }
  
  public void setTipoAlerta(Byte tipoAlerta)
  {
    this.tipoAlerta = tipoAlerta;
  }
  
  public Double getAlertaZonaVerde()
  {
    return alertaZonaVerde;
  }
  
  public void setAlertaZonaVerde(Double alertaZonaVerde)
  {
    this.alertaZonaVerde = alertaZonaVerde;
  }
  
  public Double getAlertaZonaAmarilla()
  {
    return alertaZonaAmarilla;
  }
  
  public void setAlertaZonaAmarilla(Double alertaZonaAmarilla)
  {
    this.alertaZonaAmarilla = alertaZonaAmarilla;
  }
  
  public Long getOrganizacionId()
  {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId)
  {
    this.organizacionId = organizacionId;
  }
  
  public Byte getFrecuencia()
  {
    return frecuencia;
  }
  
  public void setFrecuencia(Byte frecuencia)
  {
    this.frecuencia = frecuencia;
  }
  
  public String getFrecuenciaNombre()
  {
    if (frecuencia != null) {
      return Frecuencia.getNombre(frecuencia.byteValue());
    }
    return "";
  }
  
  public String getNombreLargo()
  {
    return nombreLargo;
  }
  
  public void setNombreLargo(String nombreLargo)
  {
    this.nombreLargo = nombreLargo;
  }
  
  public String getEnteEjecutor()
  {
    return enteEjecutor;
  }
  
  public void setEnteEjecutor(String enteEjecutor)
  {
    this.enteEjecutor = enteEjecutor;
  }
  
  public Byte getNaturaleza()
  {
    return naturaleza;
  }
  
  public void setNaturaleza(Byte naturaleza)
  {
    this.naturaleza = naturaleza;
  }
  
  public String getNaturalezaNombre()
  {
    if (naturaleza != null) {
      return Naturaleza.getNombre(naturaleza.byteValue());
    }
    return "";
  }
  
  public Long getResponsableFijarMetaId()
  {
    return responsableFijarMetaId;
  }
  
  public void setResponsableFijarMetaId(Long responsableFijarMetaId)
  {
    this.responsableFijarMetaId = responsableFijarMetaId;
  }
  
  public Long getResponsableLograrMetaId()
  {
    return responsableLograrMetaId;
  }
  
  public void setResponsableLograrMetaId(Long responsableLograrMetaId)
  {
    this.responsableLograrMetaId = responsableLograrMetaId;
  }
  
  public Long getResponsableSeguimientoId()
  {
    return responsableSeguimientoId;
  }
  
  public void setResponsableSeguimientoId(Long responsableSeguimientoId)
  {
    this.responsableSeguimientoId = responsableSeguimientoId;
  }
  
  public Long getResponsableCargarMetaId()
  {
    return responsableCargarMetaId;
  }
  
  public void setResponsableCargarMetaId(Long responsableCargarMetaId)
  {
    this.responsableCargarMetaId = responsableCargarMetaId;
  }
  
  public Long getResponsableCargarEjecutadoId()
  {
    return responsableCargarEjecutadoId;
  }
  
  public void setResponsableCargarEjecutadoId(Long responsableCargarEjecutadoId)
  {
    this.responsableCargarEjecutadoId = responsableCargarEjecutadoId;
  }
  
  public OrganizacionStrategos getOrganizacion()
  {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion)
  {
    this.organizacion = organizacion;
  }
  
  public MemoIniciativa getMemoIniciativa()
  {
    return memoIniciativa;
  }
  
  public Responsable getResponsableFijarMeta()
  {
    return responsableFijarMeta;
  }
  
  public void setResponsableFijarMeta(Responsable responsableFijarMeta)
  {
    this.responsableFijarMeta = responsableFijarMeta;
  }
  
  public Responsable getResponsableLograrMeta()
  {
    return responsableLograrMeta;
  }
  
  public void setResponsableLograrMeta(Responsable responsableLograrMeta)
  {
    this.responsableLograrMeta = responsableLograrMeta;
  }
  
  public Responsable getResponsableSeguimiento()
  {
    return responsableSeguimiento;
  }
  
  public void setResponsableSeguimiento(Responsable responsableSeguimiento)
  {
    this.responsableSeguimiento = responsableSeguimiento;
  }
  
  public Responsable getResponsableCargarMeta()
  {
    return responsableCargarMeta;
  }
  
  public void setResponsableCargarMeta(Responsable responsableCargarMeta)
  {
    this.responsableCargarMeta = responsableCargarMeta;
  }
  
  public Responsable getResponsableCargarEjecutado()
  {
    return responsableCargarEjecutado;
  }
  
  public void setResponsableCargarEjecutado(Responsable responsableCargarEjecutado)
  {
    this.responsableCargarEjecutado = responsableCargarEjecutado;
  }
  
  public void setMemoIniciativa(MemoIniciativa memoIniciativa)
  {
    this.memoIniciativa = memoIniciativa;
  }
  
  public Set<ResultadoEspecificoIniciativa> getResultadosEspecificosIniciativa()
  {
    return resultadosEspecificosIniciativa;
  }
  
  public void setResultadosEspecificosIniciativa(Set<ResultadoEspecificoIniciativa> resultadosEspecificosIniciativa)
  {
    this.resultadosEspecificosIniciativa = resultadosEspecificosIniciativa;
  }
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public Set<IniciativaPerspectiva> getIniciativaPerspectivas()
  {
    return iniciativaPerspectivas;
  }
  
  public void setIniciativaPerspectivas(Set<IniciativaPerspectiva> iniciativaPerspectivas)
  {
    this.iniciativaPerspectivas = iniciativaPerspectivas;
  }
  
  public Long getClaseId()
  {
    return claseId;
  }
  
  public void setClaseId(Long claseId)
  {
    this.claseId = claseId;
  }
  
  public Byte getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(Byte alerta)
  {
    this.alerta = alerta;
  }
  
  public Boolean getSoloLectura()
  {
    return soloLectura;
  }
  
  public void setSoloLectura(Boolean soloLectura)
  {
    this.soloLectura = soloLectura;
  }
  
  public Collection getNodoArbolHijos()
  {
    return nodoArbolHijos;
  }
  
  public boolean getNodoArbolHijosCargados()
  {
    return nodoArbolHijosCargados;
  }
  
  public String getNodoArbolId()
  {
    return iniciativaId.toString();
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId()
  {
    return "iniciativaId";
  }
  
  public String getNodoArbolNombreCampoNombre()
  {
    return "nombre";
  }
  
  public String getNodoArbolNombreCampoPadreId()
  {
    return null;
  }
  
  public String getNodoArbolNombreImagen(Byte tipo)
  {
    if (tipo.byteValue() == 1) {
      return "Iniciativa" + AlertaObjetivo.getNombreImagen(alerta);
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbolPadre)
  {
    this.nodoArbolPadre = nodoArbolPadre;
  }
  
  public NodoArbol getNodoArbolPadre()
  {
    return nodoArbolPadre;
  }
  
  public String getNodoArbolPadreId()
  {
    return null;
  }
  
  public void setNodoArbolHijos(Collection nodoArbolHijos)
  {
    this.nodoArbolHijos = nodoArbolHijos;
  }
  
  public void setNodoArbolHijosCargados(boolean cargados)
  {
    nodoArbolHijosCargados = cargados;
  }
  
  public void setNodoArbolNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Double getPorcentajeCompletado()
  {
    return porcentajeCompletado;
  }
  
  public void setPorcentajeCompletado(Double porcentajeCompletado)
  {
    this.porcentajeCompletado = porcentajeCompletado;
  }
  
  public String getPorcentajeCompletadoFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeCompletado, 2);
  }
  
  public Double getPorcentajeEsperado()
  {
    return porcentajeEsperado;
  }
  
  public String getPorcentajeEsperadoFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeEsperado, 2);
  }
  
  public void setPorcentajeEsperado(Double porcentajeEsperado)
  {
    this.porcentajeEsperado = porcentajeEsperado;
  }
  
  public Double getUltimaMedicion()
  {
    return ultimaMedicion;
  }
  
  public void setUltimaMedicion(Double ultimaMedicion)
  {
    this.ultimaMedicion = ultimaMedicion;
  }
  
  public String getUltimaMedicionFormateado()
  {
    return VgcFormatter.formatearNumero(ultimaMedicion, 2);
  }
  
  public List<PryActividad> getListaActividades()
  {
    return listaActividades;
  }
  
  public void setListaActividades(List<PryActividad> listaActividades)
  {
    this.listaActividades = listaActividades;
  }
  
  public String getFechaUltimaMedicion()
  {
    return fechaUltimaMedicion;
  }
  
  public void setFechaUltimaMedicion(String fechaUltimaMedicion)
  {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }
  
  public Set<IniciativaPlan> getIniciativaPlanes()
  {
    return iniciativaPlanes;
  }
  
  public void setIniciativaPlanes(Set<IniciativaPlan> iniciativaPlanes)
  {
    this.iniciativaPlanes = iniciativaPlanes;
  }
  
  public List<Iniciativa> getIniciativasAsociadas()
  {
    return iniciativasAsociadas;
  }
  
  public void setIniciativasAsociadas(List<Iniciativa> iniciativasAsociadas)
  {
    this.iniciativasAsociadas = iniciativasAsociadas;
  }
  
  public Set<IndicadorIniciativa> getIniciativaIndicadores()
  {
    return iniciativaIndicadores;
  }
  
  public void setIniciativaIndicadores(Set<IndicadorIniciativa> iniciativaIndicadores)
  {
    this.iniciativaIndicadores = iniciativaIndicadores;
  }
  
  
  
  public String getFechaesperado() {
	return fechaesperado;
  }

  public void setFechaesperado(String fechaesperado) {
	this.fechaesperado = fechaesperado;
  }
	
  public int getDias() {
	return dias;
  }
	
  public void setDias(int dias) {
	this.dias = dias;
  }
	
	public String getObservacion() {
		return observacion;
	}
	
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getOrganizacionNombre() {
		return organizacionNombre;
	}

	public void setOrganizacionNombre(String organizacionNombre) {
		this.organizacionNombre = organizacionNombre;
	}

public Long getIndicadorId(Byte tipo)
  {
    for (Iterator<IndicadorIniciativa> iter = this.iniciativaIndicadores.iterator(); iter.hasNext();)
    {
      IndicadorIniciativa iniciativaIndicadores = (IndicadorIniciativa)iter.next();
      if (iniciativaIndicadores.getTipo().byteValue() == tipo.byteValue()) {
        return iniciativaIndicadores.getPk().getIndicadorId();
      }
    }
    return null;
  }
  
  public void setIndicadorId(Long indicadorId, Byte tipo)
  {
    boolean indicadorExiste = false;
    
    if (this.iniciativaIndicadores != null)
    {
      for (Iterator<IndicadorIniciativa> iter = this.iniciativaIndicadores.iterator(); iter.hasNext();)
      {
        IndicadorIniciativa iniciativaIndicadores = (IndicadorIniciativa)iter.next();
        if (iniciativaIndicadores.getTipo().byteValue() == tipo.byteValue())
        {
          indicadorExiste = true;
          iniciativaIndicadores.getPk().setIndicadorId(indicadorId);
          iniciativaIndicadores.getPk().setIniciativaId(iniciativaId);
          break;
        }
      }
    }
    
    if (!indicadorExiste)
    {
      IndicadorIniciativaPK pk = new IndicadorIniciativaPK(iniciativaId, indicadorId);
      IndicadorIniciativa indicadorIniciativa = new IndicadorIniciativa();
      indicadorIniciativa.setTipo(tipo);
      indicadorIniciativa.setPk(pk);
      if (this.iniciativaIndicadores == null) {
        this.iniciativaIndicadores = new HashSet();
      }
      this.iniciativaIndicadores.add(indicadorIniciativa);
    }
  }
  
  public Byte getTipoMedicion()
  {
    return tipoMedicion;
  }
  
  public void setTipoMedicion(Byte tipoMedicion)
  {
    this.tipoMedicion = tipoMedicion;
  }
  
  public Date getHistoricoDate()
  {
    return historicoDate;
  }
  
  public void setHistoricoDate(Date historicoDate)
  {
    this.historicoDate = historicoDate;
  }
  
  public Byte getCondicion()
  {
    return this.condicion = Byte.valueOf(historicoDate != null ? HistoricoType.getFiltroHistoricoMarcado() : HistoricoType.getFiltroHistoricoNoMarcado());
  }
  
  public void setCondicion(Byte condicion)
  {
    this.condicion = condicion;
  }
  
  public Long getEstatusId()
  {
    return estatusId;
  }
  
  public void setEstatusId(Long estatusId)
  {
    this.estatusId = estatusId;
  }
  
  public IniciativaEstatus getEstatus()
  {
    return estatus;
  }
  
  
  
  public TipoProyecto getTipoProyecto() {
	return tipoProyecto;
  }

  public void setTipoProyecto(TipoProyecto tipoProyecto) {
	this.tipoProyecto = tipoProyecto;
  }

  public void setEstatus(IniciativaEstatus estatus)
  {
    this.estatus = estatus;
  }
  
  public String getAnioFormulacion()
  {
    return anioFormulacion;
  }
  
  public void setAnioFormulacion(String anio)
  {
    this.anioFormulacion = anio;
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
  
  public int compareTo(Object o)
  {
    Iniciativa or = (Iniciativa)o;
    return getIniciativaId().compareTo(or.getIniciativaId());
  }
  
  public Iniciativa copyFrom(Iniciativa iniciativa)
  {
    Iniciativa iniciativaCopia = new Iniciativa();
    iniciativaCopia.setIniciativaId(new Long(0L));
    iniciativaCopia.setMemoIniciativa(new MemoIniciativa());
    iniciativaCopia.setResultadosEspecificosIniciativa(new HashSet());
    iniciativaCopia.setIniciativaPerspectivas(new HashSet());
    iniciativaCopia.setIniciativaPlanes(new HashSet());
    
    iniciativaCopia.setNombre(iniciativa.getNombre());
    iniciativaCopia.setProyectoId(iniciativa.getProyectoId());
    iniciativaCopia.setTipoAlerta(iniciativa.getTipoAlerta());
    iniciativaCopia.setAlertaZonaVerde(iniciativa.getAlertaZonaVerde());
    iniciativaCopia.setAlertaZonaAmarilla(iniciativa.getAlertaZonaAmarilla());
    iniciativaCopia.setOrganizacionId(iniciativa.getOrganizacionId());
    iniciativaCopia.setFrecuencia(iniciativa.getFrecuencia());
    iniciativaCopia.setNombreLargo(iniciativa.getNombreLargo());
    iniciativaCopia.setEnteEjecutor(iniciativa.getEnteEjecutor());
    iniciativaCopia.setNaturaleza(iniciativa.getNaturaleza());
    iniciativaCopia.setResponsableFijarMetaId(iniciativa.getResponsableFijarMetaId());
    iniciativaCopia.setResponsableLograrMetaId(iniciativa.getResponsableLograrMetaId());
    iniciativaCopia.setResponsableSeguimientoId(iniciativa.getResponsableSeguimientoId());
    iniciativaCopia.setResponsableCargarMetaId(iniciativa.getResponsableCargarMetaId());
    iniciativaCopia.setResponsableCargarEjecutadoId(iniciativa.getResponsableCargarEjecutadoId());
    iniciativaCopia.setDescripcion(iniciativa.getDescripcion());
    iniciativaCopia.setSoloLectura(iniciativa.getSoloLectura());
    iniciativaCopia.setClaseId(iniciativa.getClaseId());
    iniciativaCopia.setAlerta(iniciativa.getAlerta());
    iniciativaCopia.setPorcentajeCompletado(iniciativa.getPorcentajeCompletado());
    iniciativaCopia.setFechaUltimaMedicion(iniciativa.getFechaUltimaMedicion());
    iniciativaCopia.setPorcentajeEsperado(iniciativa.getPorcentajeEsperado());
    iniciativaCopia.setUltimaMedicion(iniciativa.getUltimaMedicion());
    iniciativaCopia.setTipoMedicion(iniciativa.getTipoMedicion());
    iniciativaCopia.setHistoricoDate(iniciativa.getHistoricoDate());
    iniciativaCopia.setCondicion(iniciativa.getCondicion());
    iniciativaCopia.setEstatusId(iniciativa.getEstatusId());
    iniciativaCopia.setTipoId(iniciativa.getTipoId());
    iniciativaCopia.setAnioFormulacion(iniciativa.getAnioFormulacion());
    
    iniciativaCopia.setResponsableProyecto(iniciativa.getResponsableProyecto());
    iniciativaCopia.setCargoResponsable(iniciativa.getCargoResponsable());
    iniciativaCopia.setOrganizacionesInvolucradas(iniciativa.getOrganizacionesInvolucradas());
    iniciativaCopia.setObjetivoEstrategico(iniciativa.getObjetivoEstrategico());
    iniciativaCopia.setFuenteFinanciacion(iniciativa.getFuenteFinanciacion());
    iniciativaCopia.setMontoFinanciamiento(iniciativa.getMontoFinanciamiento());
    iniciativaCopia.setIniciativaEstrategica(iniciativa.getIniciativaEstrategica());
    iniciativaCopia.setLiderIniciativa(iniciativa.getLiderIniciativa());
    iniciativaCopia.setTipoIniciativa(iniciativa.getTipoIniciativa());
    iniciativaCopia.setPoblacionBeneficiada(iniciativa.getPoblacionBeneficiada());
    iniciativaCopia.setContexto(iniciativa.getContexto());
    iniciativaCopia.setDefinicionProblema(iniciativa.getDefinicionProblema());
    iniciativaCopia.setAlcance(iniciativa.getAlcance());
    iniciativaCopia.setObjetivoGeneral(iniciativa.getObjetivoGeneral());
    iniciativaCopia.setObjetivoEspecificos(iniciativa.getObjetivoEspecificos());
    
    if (iniciativa.getMemoIniciativa() != null)
    {
      if (iniciativa.getMemoIniciativa().getDescripcion() != null) {
        iniciativaCopia.getMemoIniciativa().setDescripcion(iniciativa.getMemoIniciativa().getDescripcion());
      } else
        iniciativaCopia.getMemoIniciativa().setDescripcion(null);
      if (iniciativa.getMemoIniciativa().getResultado() != null) {
        iniciativaCopia.getMemoIniciativa().setResultado(iniciativa.getMemoIniciativa().getResultado());
      } else {
        iniciativaCopia.getMemoIniciativa().setResultado(null);
      }                       
    }
    iniciativaCopia.getResultadosEspecificosIniciativa().clear();
    for (Iterator<ResultadoEspecificoIniciativa> iter = iniciativa.getResultadosEspecificosIniciativa().iterator(); iter.hasNext();)
    {
      ResultadoEspecificoIniciativa resultadoEspecificoIniciativa = (ResultadoEspecificoIniciativa)iter.next();
      iniciativaCopia.getResultadosEspecificosIniciativa().add(resultadoEspecificoIniciativa);
    }
    
    for (Iterator<IniciativaPerspectiva> iter = iniciativa.getIniciativaPerspectivas().iterator(); iter.hasNext();)
    {
      IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iter.next();
      iniciativaCopia.getIniciativaPerspectivas().add(iniciativaPerspectiva);
    }
    
    for (Iterator<IniciativaPlan> iter = iniciativa.getIniciativaPlanes().iterator(); iter.hasNext();)
    {
      IniciativaPlan iniciativaPlan = (IniciativaPlan)iter.next();
      iniciativaCopia.getIniciativaPlanes().add(iniciativaPlan);
    }
    
    return iniciativaCopia;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Iniciativa other = (Iniciativa)obj;
    if (iniciativaId == null)
    {
      if (iniciativaId != null) {
        return false;
      }
    } else if (!iniciativaId.equals(iniciativaId))
      return false;
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).toString();
  }  
}
