package com.visiongc.framework.web.struts.forms.configuracion;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;






public class EditarConfiguracionVisorListaForm
  extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  protected String nombreConfiguracionBase = null;
  protected String nombreVisorLista;
  protected String tituloVisorLista;
  private String alto;
  private String ancho;
  private Boolean esPropio;
  private String respuesta;
  
  public EditarConfiguracionVisorListaForm() {}
  
  public String getNombreConfiguracionBase() { return nombreConfiguracionBase; }
  

  public void setNombreConfiguracionBase(String nombreConfiguracionBase)
  {
    this.nombreConfiguracionBase = nombreConfiguracionBase;
  }
  
  public String getNombreVisorLista()
  {
    return nombreVisorLista;
  }
  
  public void setNombreVisorLista(String nombreVisorLista)
  {
    this.nombreVisorLista = nombreVisorLista;
  }
  
  public String getTituloVisorLista()
  {
    return tituloVisorLista;
  }
  
  public void setTituloVisorLista(String tituloVisorLista)
  {
    this.tituloVisorLista = tituloVisorLista;
  }
  
  public String getAlto()
  {
    return alto;
  }
  
  public void setAlto(String alto)
  {
    this.alto = alto;
  }
  
  public String getAncho()
  {
    return ancho;
  }
  
  public void setAncho(String ancho)
  {
    this.ancho = ancho;
  }
  
  public Boolean getEsPropio()
  {
    return esPropio;
  }
  
  public void setEsPropio(Boolean esPropio)
  {
    this.esPropio = esPropio;
  }
  
  public String getRespuesta()
  {
    return respuesta;
  }
  
  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }
  
  public void clear()
  {
    nombreConfiguracionBase = null;
    nombreVisorLista = null;
    alto = null;
    ancho = null;
    esPropio = null;
    respuesta = null;
  }
}
