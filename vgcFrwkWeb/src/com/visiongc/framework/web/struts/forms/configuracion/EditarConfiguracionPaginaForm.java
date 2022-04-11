package com.visiongc.framework.web.struts.forms.configuracion;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.List;





public class EditarConfiguracionPaginaForm
  extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private String tamanoMargenSuperior;
  private String tamanoMargenInferior;
  private String tamanoMargenIzquierdo;
  private String tamanoMargenDerecho;
  private String encabezadoIzquierdo;
  private String encabezadoCentro;
  private String encabezadoDerecho;
  private String piePaginaIzquierdo;
  private String piePaginaCentro;
  private String piePaginaDerecho;
  private String nombreFuente;
  private String estiloFuente;
  private Integer tamanoFuente;
  private List fuentes;
  private List estilos;
  private List tamanosFuente;
  private String configuracionBase;
  private String nombreImagenSupIzq;
  private String nombreImagenSupCen;
  private String nombreImagenSupDer;
  private String nombreImagenInfIzq;
  private String nombreImagenInfCen;
  private String nombreImagenInfDer;
  private String respuesta;
  
  public EditarConfiguracionPaginaForm() {}
  
  public String getEncabezadoCentro()
  {
    return encabezadoCentro;
  }
  
  public void setEncabezadoCentro(String encabezadoCentro)
  {
    this.encabezadoCentro = encabezadoCentro;
  }
  
  public String getEncabezadoDerecho()
  {
    return encabezadoDerecho;
  }
  
  public void setEncabezadoDerecho(String encabezadoDerecho)
  {
    this.encabezadoDerecho = encabezadoDerecho;
  }
  
  public String getEncabezadoIzquierdo()
  {
    return encabezadoIzquierdo;
  }
  
  public void setEncabezadoIzquierdo(String encabezadoIzquierdo)
  {
    this.encabezadoIzquierdo = encabezadoIzquierdo;
  }
  
  public String getEstiloFuente()
  {
    return estiloFuente;
  }
  
  public void setEstiloFuente(String estiloFuente)
  {
    this.estiloFuente = estiloFuente;
  }
  
  public String getNombreFuente()
  {
    return nombreFuente;
  }
  
  public void setNombreFuente(String nombreFuente)
  {
    this.nombreFuente = nombreFuente;
  }
  
  public String getPiePaginaCentro()
  {
    return piePaginaCentro;
  }
  
  public void setPiePaginaCentro(String piePaginaCentro)
  {
    this.piePaginaCentro = piePaginaCentro;
  }
  
  public String getPiePaginaDerecho()
  {
    return piePaginaDerecho;
  }
  
  public void setPiePaginaDerecho(String piePaginaDerecho)
  {
    this.piePaginaDerecho = piePaginaDerecho;
  }
  
  public String getPiePaginaIzquierdo()
  {
    return piePaginaIzquierdo;
  }
  
  public void setPiePaginaIzquierdo(String piePaginaIzquierdo)
  {
    this.piePaginaIzquierdo = piePaginaIzquierdo;
  }
  
  public Integer getTamanoFuente()
  {
    return tamanoFuente;
  }
  
  public void setTamanoFuente(Integer tamanoFuente)
  {
    this.tamanoFuente = tamanoFuente;
  }
  
  public String getTamanoMargenDerecho()
  {
    return tamanoMargenDerecho;
  }
  
  public void setTamanoMargenDerecho(String tamanoMargenDerecho)
  {
    this.tamanoMargenDerecho = tamanoMargenDerecho;
  }
  
  public String getTamanoMargenInferior()
  {
    return tamanoMargenInferior;
  }
  
  public void setTamanoMargenInferior(String tamanoMargenInferior)
  {
    this.tamanoMargenInferior = tamanoMargenInferior;
  }
  
  public String getTamanoMargenIzquierdo()
  {
    return tamanoMargenIzquierdo;
  }
  
  public void setTamanoMargenIzquierdo(String tamanoMargenIzquierdo)
  {
    this.tamanoMargenIzquierdo = tamanoMargenIzquierdo;
  }
  
  public String getTamanoMargenSuperior()
  {
    return tamanoMargenSuperior;
  }
  
  public void setTamanoMargenSuperior(String tamanoMargenSuperior)
  {
    this.tamanoMargenSuperior = tamanoMargenSuperior;
  }
  
  public List getFuentes()
  {
    return fuentes;
  }
  
  public void setFuentes(List fuentes)
  {
    this.fuentes = fuentes;
  }
  
  public List getEstilos()
  {
    return estilos;
  }
  
  public void setEstilos(List estilos)
  {
    this.estilos = estilos;
  }
  
  public List getTamanosFuente()
  {
    return tamanosFuente;
  }
  
  public void setTamanosFuente(List tamanosFuente)
  {
    this.tamanosFuente = tamanosFuente;
  }
  
  public String getNombreImagenSupIzq()
  {
    return nombreImagenSupIzq;
  }
  
  public void setNombreImagenSupIzq(String nombreImagenSupIzq)
  {
    this.nombreImagenSupIzq = nombreImagenSupIzq;
  }
  
  public String getNombreImagenSupCen()
  {
    return nombreImagenSupCen;
  }
  
  public void setNombreImagenSupCen(String nombreImagenSupCen)
  {
    this.nombreImagenSupCen = nombreImagenSupCen;
  }
  
  public String getNombreImagenSupDer()
  {
    return nombreImagenSupDer;
  }
  
  public void setNombreImagenSupDer(String nombreImagenSupDer)
  {
    this.nombreImagenSupDer = nombreImagenSupDer;
  }
  
  public String getNombreImagenInfIzq()
  {
    return nombreImagenInfIzq;
  }
  
  public void setNombreImagenInfIzq(String nombreImagenInfIzq)
  {
    this.nombreImagenInfIzq = nombreImagenInfIzq;
  }
  
  public String getNombreImagenInfCen()
  {
    return nombreImagenInfCen;
  }
  
  public void setNombreImagenInfCen(String nombreImagenInfCen)
  {
    this.nombreImagenInfCen = nombreImagenInfCen;
  }
  
  public String getNombreImagenInfDer()
  {
    return nombreImagenInfDer;
  }
  
  public void setNombreImagenInfDer(String nombreImagenInfDer)
  {
    this.nombreImagenInfDer = nombreImagenInfDer;
  }
  
  public String getConfiguracionBase()
  {
    return configuracionBase;
  }
  
  public void setConfiguracionBase(String configuracionBase)
  {
    this.configuracionBase = configuracionBase;
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
  
  public void clear() {}
}
