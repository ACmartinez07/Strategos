package com.visiongc.app.strategos.graficos.model;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.model.Usuario;
import java.io.Serializable;
import java.util.Collection;












public class Grafico
  implements Serializable, NodoArbol
{
  static final long serialVersionUID = 0L;
  private Long graficoId;
  private String nombre;
  private Long organizacionId;
  private String configuracion;
  private OrganizacionStrategos organizacion;
  private NodoArbol nodoArbolPadre;
  private Collection<?> nodoArbolHijos;
  private boolean nodoArbolHijosCargados;
  private Long usuarioId;
  private Usuario usuario;
  private Long objetoId;
  private String className;
  
  public Grafico() {}
  
  public Long getGraficoId()
  {
    return graficoId;
  }
  
  public void setGraficoId(Long graficoId)
  {
    this.graficoId = graficoId;
  }
  
  public String getNombre()
  {
    return nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Long getOrganizacionId()
  {
    return organizacionId;
  }
  
  public void setOrganizacionId(Long organizacionId)
  {
    this.organizacionId = organizacionId;
  }
  
  public String getConfiguracion()
  {
    return configuracion;
  }
  
  public void setConfiguracion(String configuracion)
  {
    this.configuracion = configuracion;
  }
  
  public Long getUsuarioId()
  {
    return usuarioId;
  }
  
  public void setUsuarioId(Long usuarioId)
  {
    this.usuarioId = usuarioId;
  }
  
  public OrganizacionStrategos getOrganizacion()
  {
    return organizacion;
  }
  
  public void setOrganizacion(OrganizacionStrategos organizacion)
  {
    this.organizacion = organizacion;
  }
  
  public Usuario getUsuario()
  {
    return usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public Long getObjetoId()
  {
    return objetoId;
  }
  
  public void setObjetoId(Long objetoId)
  {
    this.objetoId = objetoId;
  }
  
  public String getClassName()
  {
    return className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public Collection<?> getNodoArbolHijos()
  {
    return nodoArbolHijos;
  }
  
  public boolean getNodoArbolHijosCargados()
  {
    return nodoArbolHijosCargados;
  }
  
  public String getNodoArbolId()
  {
    return graficoId.toString();
  }
  
  public String getNodoArbolNombre()
  {
    return nombre;
  }
  
  public String getNodoArbolNombreCampoId()
  {
    return "graficoId";
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
      return "Grafico";
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
    Grafico or = (Grafico)o;
    return getGraficoId().compareTo(or.getGraficoId());
  }
}
