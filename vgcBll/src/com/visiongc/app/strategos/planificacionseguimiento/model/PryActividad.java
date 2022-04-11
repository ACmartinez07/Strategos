package com.visiongc.app.strategos.planificacionseguimiento.model;

import com.visiongc.app.strategos.planificacionseguimiento.model.util.RelacionIniciativaActividad;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;



public class PryActividad
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long actividadId;
  private Long proyectoId;
  private Long padreId;
  private String nombre;
  private Long unidadId;
  private String descripcion;
  private Date comienzoPlan;
  private Date comienzoReal;
  private Date finPlan;
  private Date finReal;
  private Double duracionPlan;
  private Double duracionReal;
  private Integer fila;
  private Integer nivel;
  private Boolean compuesta;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Long indicadorId;
  private Byte naturaleza;
  private Long responsableFijarMetaId;
  private Long responsableLograrMetaId;
  private Long responsableSeguimientoId;
  private Long responsableCargarMetaId;
  private Long responsableCargarEjecutadoId;
  private Long claseId;
  private Long objetoAsociadoId;
  private Byte tipoMedicion;
  private Responsable responsableFijarMeta;
  private Responsable responsableLograrMeta;
  private Responsable responsableSeguimiento;
  private Responsable responsableCargarMeta;
  private Responsable responsableCargarEjecutado;
  private PryInformacionActividad pryInformacionActividad;
  private UnidadMedida unidadMedida;
  private Boolean tieneHijos;
  private PryActividad padre;
  private Set hijos;
  private Double peso;
  private String objetoAsociadoClassName;
  private NodoArbol nodoArbolPadre;
  private Collection nodoArbolHijos;
  private boolean nodoArbolHijosCargados;
  private String fechaUltimaMedicion;
  private Byte relacion;
  private Byte alerta;
  private Double porcentajeCompletado;
  private Double porcentajeEjecutado;
  private Double porcentajeEsperado;
  
  public PryActividad(Long actividadId)
  {
    this.actividadId = actividadId;
  }
  

  public PryActividad() {}
  

  public Long getActividadId()
  {
    return actividadId;
  }
  
  public void setActividadId(Long actividadId)
  {
    this.actividadId = actividadId;
  }
  
  public Long getProyectoId()
  {
    return proyectoId;
  }
  
  public void setProyectoId(Long proyectoId)
  {
    this.proyectoId = proyectoId;
  }
  
  public Long getPadreId()
  {
    return padreId;
  }
  
  public void setPadreId(Long padreId)
  {
    this.padreId = padreId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public String getObjetoAsociadoClassName()
  {
    return objetoAsociadoClassName;
  }
  
  public void setObjetoAsociadoClassName(String objetoAsociadoClassName)
  {
    this.objetoAsociadoClassName = objetoAsociadoClassName;
  }
  
  public Long getUnidadId()
  {
    return unidadId;
  }
  
  public void setUnidadId(Long unidadId)
  {
    this.unidadId = unidadId;
  }
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public Date getComienzoPlan()
  {
    return comienzoPlan;
  }
  
  public String getComienzoPlanFormateada()
  {
    return VgcFormatter.formatearFecha(comienzoPlan, "formato.fecha.corta");
  }
  
  public void setComienzoPlan(Date comienzoPlan)
  {
    this.comienzoPlan = comienzoPlan;
  }
  
  public Date getComienzoReal()
  {
    return comienzoReal;
  }
  
  public void setComienzoReal(Date comienzoReal)
  {
    this.comienzoReal = comienzoReal;
  }
  
  public String getComienzoRealFormateada()
  {
    return VgcFormatter.formatearFecha(comienzoReal, "formato.fecha.corta");
  }
  
  public Date getFinPlan()
  {
    return finPlan;
  }
  
  public String getFinPlanFormateada()
  {
    return VgcFormatter.formatearFecha(finPlan, "formato.fecha.corta");
  }
  
  public void setFinPlan(Date finPlan)
  {
    this.finPlan = finPlan;
  }
  
  public Date getFinReal()
  {
    return finReal;
  }
  
  public void setFinReal(Date finReal)
  {
    this.finReal = finReal;
  }
  
  public String getFinRealFormateada()
  {
    return VgcFormatter.formatearFecha(finReal, "formato.fecha.corta");
  }
  
  public Double getDuracionPlan()
  {
    return duracionPlan;
  }
  
  public void setDuracionPlan(Double duracionPlan)
  {
    this.duracionPlan = duracionPlan;
  }
  
  public Double getDuracionReal()
  {
    return duracionReal;
  }
  
  public void setDuracionReal(Double duracionReal)
  {
    this.duracionReal = duracionReal;
  }
  
  public Integer getFila()
  {
    return fila;
  }
  
  public void setFila(Integer fila)
  {
    this.fila = fila;
  }
  
  public Integer getNivel()
  {
    return nivel;
  }
  
  public void setNivel(Integer nivel)
  {
    this.nivel = nivel;
  }
  
  public Boolean getCompuesta()
  {
    return compuesta;
  }
  
  public void setCompuesta(Boolean compuesta)
  {
    this.compuesta = compuesta;
  }
  
  public Date getCreado()
  {
    return creado;
  }
  
  public void setCreado(Date creado)
  {
    this.creado = creado;
  }
  
  public Date getModificado()
  {
    return modificado;
  }
  
  public void setModificado(Date modificado)
  {
    this.modificado = modificado;
  }
  
  public Long getCreadoId()
  {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId)
  {
    this.creadoId = creadoId;
  }
  
  public Long getModificadoId()
  {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId)
  {
    this.modificadoId = modificadoId;
  }
  
  public Long getIndicadorId()
  {
    return indicadorId;
  }
  
  public void setIndicadorId(Long indicadorId)
  {
    this.indicadorId = indicadorId;
  }
  
  public Byte getNaturaleza()
  {
    return naturaleza;
  }
  
  public void setNaturaleza(Byte naturaleza)
  {
    this.naturaleza = naturaleza;
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
  
  public Long getClaseId()
  {
    return claseId;
  }
  
  public void setClaseId(Long claseId)
  {
    this.claseId = claseId;
  }
  
  public Long getObjetoAsociadoId()
  {
    return objetoAsociadoId;
  }
  
  public void setObjetoAsociadoId(Long objetoAsociadoId)
  {
    this.objetoAsociadoId = objetoAsociadoId;
  }
  
  public Byte getTipoMedicion()
  {
    return tipoMedicion;
  }
  
  public void setTipoMedicion(Byte tipoMedicion)
  {
    this.tipoMedicion = tipoMedicion;
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
  
  public PryInformacionActividad getPryInformacionActividad()
  {
    return pryInformacionActividad;
  }
  
  public void setPryInformacionActividad(PryInformacionActividad pryInformacionActividad)
  {
    this.pryInformacionActividad = pryInformacionActividad;
  }
  
  public UnidadMedida getUnidadMedida()
  {
    return unidadMedida;
  }
  
  public void setUnidadMedida(UnidadMedida unidadMedida)
  {
    this.unidadMedida = unidadMedida;
  }
  
  public String getProductoEsperado()
  {
    if (pryInformacionActividad.getProductoEsperado() != null) {
      return pryInformacionActividad.getProductoEsperado();
    }
    return "";
  }
  
  public String getRecursosHumanos()
  {
    if (pryInformacionActividad.getRecursosHumanos() != null) {
      return pryInformacionActividad.getRecursosHumanos();
    }
    return "";
  }
  
  public String getRecursosMateriales()
  {
    if (pryInformacionActividad.getRecursosMateriales() != null) {
      return pryInformacionActividad.getRecursosMateriales();
    }
    return "";
  }
  
  public Set getHijos()
  {
    return hijos;
  }
  
  public void setHijos(Set hijos)
  {
    this.hijos = hijos;
  }
  
  public PryActividad getPadre()
  {
    return padre;
  }
  
  public void setPadre(PryActividad padre)
  {
    this.padre = padre;
  }
  
  public String getNombreTruncado()
  {
    String nombreTruncado = nombre;
    
    int nivelActividad = nivel == null ? 1 : nivel.intValue();
    nivelActividad--;
    
    int tamanoMaximo = 50 - nivelActividad * 6;
    
    if (nombreTruncado.length() >= tamanoMaximo) {
      nombreTruncado = nombreTruncado.substring(0, tamanoMaximo) + "...";
    }
    return nombreTruncado;
  }
  
  public List getIdentacion()
  {
    int nivelActividad = nivel == null ? 1 : nivel.intValue();
    nivelActividad--;
    List identacion = new ArrayList();
    
    for (int i = 0; i < nivelActividad * 6; i++) {
      identacion.add(new Integer(i));
    }
    return identacion;
  }
  
  public Boolean getTieneHijos()
  {
    return tieneHijos;
  }
  
  public void setTieneHijos(Boolean tieneHijos)
  {
    this.tieneHijos = tieneHijos;
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
    return actividadId.toString();
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId()
  {
    return "actividadId";
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
      return "Actividad";
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
  
  public int compareTo(Object o)
  {
    PryActividad or = (PryActividad)o;
    
    return getActividadId().compareTo(or.getActividadId());
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PryActividad other = (PryActividad)obj;
    if (actividadId == null)
    {
      if (actividadId != null) {
        return false;
      }
    } else if (!actividadId.equals(actividadId)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("actividadId", getActividadId()).toString();
  }
  
  public Double getPeso()
  {
    return pryInformacionActividad.getPeso();
  }
  
  public String getPesoFormateado()
  {
    return VgcFormatter.formatearNumero(getPeso(), 3);
  }
  
  public void setPeso(Double peso)
  {
    pryInformacionActividad.setPeso(peso);
  }
  
  public String getFechaUltimaMedicion()
  {
    return fechaUltimaMedicion;
  }
  
  public void setFechaUltimaMedicion(String fechaUltimaMedicion)
  {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }
  
  public Byte getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(Byte alerta)
  {
    this.alerta = alerta;
  }
  
  public Double getPorcentajeCompletado()
  {
    return porcentajeCompletado;
  }
  
  public void setPorcentajeCompletado(Double porcentajeCompletado)
  {
    this.porcentajeCompletado = porcentajeCompletado;
  }
  
  public Double getPorcentajeEjecutado()
  {
    return porcentajeEjecutado;
  }
  
  public void setPorcentajeEjecutado(Double porcentajeEjecutado)
  {
    this.porcentajeEjecutado = porcentajeEjecutado;
  }
  
  public Double getPorcentajeEsperado()
  {
    return porcentajeEsperado;
  }
  
  public void setPorcentajeEsperado(Double porcentajeEsperado)
  {
    this.porcentajeEsperado = porcentajeEsperado;
  }
  
  public String getPorcentajeCompletadoFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeCompletado, 2);
  }
  
  public String getPorcentajeEjecutadoFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeEjecutado, 2);
  }
  
  public String getPorcentajeEsperadoFormateado()
  {
    return VgcFormatter.formatearNumero(porcentajeEsperado, 2);
  }
  
  public Byte getRelacion()
  {
    return this.relacion = objetoAsociadoId != null ? RelacionIniciativaActividad.getRelacionDefinible() : null;
  }
  
  public void setRelacion(Byte relacion)
  {
    this.relacion = relacion;
  }
}
