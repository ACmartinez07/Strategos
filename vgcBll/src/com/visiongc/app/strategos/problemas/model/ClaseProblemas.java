package com.visiongc.app.strategos.problemas.model;

import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


public class ClaseProblemas
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long claseId;
  private Long organizacionId;
  private Long padreId;
  private String nombre;
  private String descripcion;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private ClaseProblemas padre;
  private Set hijos;
  private Boolean hijosCargados;
  private Integer tipo;
  
  public ClaseProblemas(Long claseId, Long padreId, Long organizacionId, String nombre, String descripcion, Date creado, Date modificado, Long creadoId, Long modificadoId, ClaseProblemas padre, Set hijos, Integer tipo)
  {
    this.claseId = claseId;
    this.organizacionId = organizacionId;
    this.padreId = padreId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.padre = padre;
    this.hijos = hijos;
    this.tipo = tipo;
  }
  

  public ClaseProblemas() {}
  

  public Long getClaseId()
  {
    return claseId;
  }
  
  public void setClaseId(Long claseId)
  {
    this.claseId = claseId;
  }
  
  public Long getOrganizacionId()
  {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId)
  {
    this.organizacionId = organizacionId;
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
  
  public String getDescripcion()
  {
    return descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
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
  
  public Integer getTipo()
  {
    return tipo;
  }
  
  public void setTipo(Integer tipo)
  {
    this.tipo = TipoClaseProblema.getTipo(tipo.intValue());
  }
  
  public ClaseProblemas getPadre()
  {
    return padre;
  }
  
  public void setPadre(ClaseProblemas padre)
  {
    this.padre = padre;
  }
  
  public Set getHijos()
  {
    return hijos;
  }
  
  public void setHijos(Set hijos)
  {
    this.hijos = hijos;
  }
  
  public List getHijosHoja()
  {
    return new ArrayList();
  }
  

  public void setHijosHoja(List hijosHoja) {}
  

  public Collection getNodoArbolHijos()
  {
    return hijos;
  }
  
  public boolean getNodoArbolHijosCargados()
  {
    if (hijosCargados == null) {
      hijosCargados = new Boolean(false);
    }
    return hijosCargados.booleanValue();
  }
  
  public String getNodoArbolId()
  {
    if (claseId != null) {
      return claseId.toString();
    }
    return null;
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId()
  {
    return "claseId";
  }
  
  public String getNodoArbolNombreCampoNombre()
  {
    return "nombre";
  }
  
  public String getNodoArbolNombreCampoPadreId()
  {
    return "padreId";
  }
  
  public String getNodoArbolNombreImagen(Byte tipo)
  {
    if (tipo.byteValue() == 1) {
      return "ClaseProblemas";
    }
    return "";
  }
  
  public String getNodoArbolNombreToolTipImagen(Byte tipo)
  {
    return "";
  }
  
  public void setNodoArbolPadre(NodoArbol nodoArbol)
  {
    setPadre((ClaseProblemas)nodoArbol);
  }
  
  public NodoArbol getNodoArbolPadre()
  {
    return padre;
  }
  
  public String getNodoArbolPadreId()
  {
    if (padreId != null) {
      return padreId.toString();
    }
    return null;
  }
  
  public void setNodoArbolHijos(Collection nodoArbolHijos)
  {
    hijos = ((Set)nodoArbolHijos);
  }
  
  public void setNodoArbolHijosCargados(boolean cargados)
  {
    hijosCargados = new Boolean(cargados);
  }
  
  public void setNodoArbolNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public int compareTo(Object o)
  {
    ClaseProblemas or = (ClaseProblemas)o;
    
    return getClaseId().compareTo(or.getClaseId());
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("claseId", getClaseId()).toString();
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ClaseProblemas other = (ClaseProblemas)obj;
    if (claseId == null)
    {
      if (claseId != null) {
        return false;
      }
    } else if (!claseId.equals(claseId)) {
      return false;
    }
    return true;
  }
  
  public static class TipoClaseProblema {
    private static final int TIPO_PROBLEMA = 0;
    private static final int TIPO_RIESGO = 1;
    
    public TipoClaseProblema() {}
    
    private static Integer getTipo(int tipo) {
      if (tipo == 0)
        return new Integer(0);
      if (tipo == 1) {
        return new Integer(1);
      }
      return null;
    }
    
    public static Integer getTipoProblema()
    {
      return new Integer(0);
    }
    
    public static Integer getTipoRiesgo()
    {
      return new Integer(1);
    }
  }
}
