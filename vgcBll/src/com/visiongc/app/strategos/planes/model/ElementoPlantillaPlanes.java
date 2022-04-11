package com.visiongc.app.strategos.planes.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ElementoPlantillaPlanes
{
  private Long elementoId;
  private String nombre;
  private Long plantillaPlanesId;
  private Integer orden;
  private Byte tipo;
  private PlantillaPlanes plantillaPlanes;
  
  public ElementoPlantillaPlanes(Long elementoId, String nombre, Long plantillaPlanesId, Integer orden, Byte tipo)
  {
    this.elementoId = elementoId;
    this.nombre = nombre;
    this.plantillaPlanesId = plantillaPlanesId;
    this.orden = orden;
    this.tipo = tipo;
  }
  

  public ElementoPlantillaPlanes() {}
  

  public Long getElementoId()
  {
    return elementoId;
  }
  
  public void setElementoId(Long elementoId) {
    this.elementoId = elementoId;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public Integer getOrden() {
    return orden;
  }
  
  public void setOrden(Integer orden) {
    this.orden = orden;
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
  
  public PlantillaPlanes getPlantillaPlanes() {
    return plantillaPlanes;
  }
  
  public void setPlantillaPlanes(PlantillaPlanes plantillaPlanes) {
    this.plantillaPlanes = plantillaPlanes;
  }
  
  public int compareTo(Object o) {
    ElementoPlantillaPlanes or = (ElementoPlantillaPlanes)o;
    return getElementoId().compareTo(or.getElementoId());
  }
  
  public String toString() {
    return new ToStringBuilder(this).append("elementoId", getElementoId()).toString();
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ElementoPlantillaPlanes other = (ElementoPlantillaPlanes)obj;
    if (elementoId == null) {
      if (elementoId != null)
        return false;
    } else if (!elementoId.equals(elementoId))
      return false;
    return true;
  }
}
