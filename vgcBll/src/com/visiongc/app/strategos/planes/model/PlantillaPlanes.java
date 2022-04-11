package com.visiongc.app.strategos.planes.model;

import com.visiongc.commons.util.StringUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PlantillaPlanes
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long plantillaPlanesId;
  private String nombre;
  private String descripcion;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private String nombreIndicadorSingular;
  private String nombreIniciativaSingular;
  private String nombreActividadSingular;
  private Byte tipo;
  private Set elementos;
  
  public PlantillaPlanes(Long plantillaPlanesId, String nombre, String descripcion, Date creado, Date modificado, Long creadoId, Long modificadoId, String nombreIndicadorSingular, String nombreIniciativaSingular, String nombreActividadSingular, String nombreIndicadorPlural, String nombreIniciativaPlural, String nombreActividadPlural, String articuloIndicadorSingular, String articuloIniciativaSingular, String articuloActividadSingular, String articuloIndicadorPlural, String articuloIniciativaPlural, String articuloActividadPlural, Byte tipo)
  {
    this.plantillaPlanesId = plantillaPlanesId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.nombreIndicadorSingular = nombreIndicadorSingular;
    this.nombreIniciativaSingular = nombreIniciativaSingular;
    this.nombreActividadSingular = nombreActividadSingular;
    this.tipo = tipo;
  }
  

  public PlantillaPlanes() {}
  

  public Date getCreado()
  {
    return creado;
  }
  
  public void setCreado(Date creado) {
    this.creado = creado;
  }
  
  public Long getCreadoId() {
    return creadoId;
  }
  
  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }
  
  public String getDescripcion() {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
  public Date getModificado() {
    return modificado;
  }
  
  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }
  
  public Long getModificadoId() {
    return modificadoId;
  }
  
  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public String getNombreActividadSingular() {
    return nombreActividadSingular;
  }
  
  public String getNombreActividadPlural() {
    return StringUtil.getPlural(nombreActividadSingular);
  }
  
  public void setNombreActividadSingular(String nombreActividadSingular) {
    this.nombreActividadSingular = nombreActividadSingular;
  }
  
  public String getNombreIndicadorSingular() {
    return nombreIndicadorSingular;
  }
  
  public String getNombreIndicadorPlural() {
    return StringUtil.getPlural(nombreIndicadorSingular);
  }
  
  public void setNombreIndicadorSingular(String nombreIndicadorSingular) {
    this.nombreIndicadorSingular = nombreIndicadorSingular;
  }
  
  public String getNombreIniciativaSingular() {
    return nombreIniciativaSingular;
  }
  
  public String getNombreIniciativaPlural() {
    return StringUtil.getPlural(nombreIniciativaSingular);
  }
  
  public void setNombreIniciativaSingular(String nombreIniciativaSingular) {
    this.nombreIniciativaSingular = nombreIniciativaSingular;
  }
  
  public Long getPlantillaPlanesId() {
    return plantillaPlanesId;
  }
  
  public void setPlantillaPlanesId(Long plantillaPlanesId) {
    this.plantillaPlanesId = plantillaPlanesId;
  }
  
  public Byte getTipo() {
    return tipo;
  }
  
  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }
  
  public Set getElementos() {
    return elementos;
  }
  
  public void setElementos(Set elementos) {
    this.elementos = elementos;
  }
  
  public int compareTo(Object o) {
    PlantillaPlanes or = (PlantillaPlanes)o;
    return getPlantillaPlanesId().compareTo(or.getPlantillaPlanesId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("plantillaPlanesId", getPlantillaPlanesId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PlantillaPlanes other = (PlantillaPlanes)obj;
    if (plantillaPlanesId == null) {
      if (plantillaPlanesId != null)
        return false;
    } else if (!plantillaPlanesId.equals(plantillaPlanesId))
      return false;
    return true;
  }
}
