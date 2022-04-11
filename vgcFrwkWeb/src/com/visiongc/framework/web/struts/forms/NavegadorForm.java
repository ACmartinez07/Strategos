package com.visiongc.framework.web.struts.forms;

import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.web.NavegadorInfo;
import org.apache.struts.action.ActionForm;







public class NavegadorForm
  extends ActionForm
  implements NavegadorInfo
{
  static final long serialVersionUID = 0L;
  private Integer pantallaAncho;
  private Integer pantallaAlto;
  private String navegadorNombre;
  private String navegadorVersion;
  private Modulo objeto = new Modulo();
  private Boolean hayConfiguracion = Boolean.valueOf(false);
  private String logo1;
  private String logo2;
  private String logo3;
  private String logo4;
  private String imageBackground;
  private String estiloSuperior;
  private String estiloInferior;
  private String estiloFormaInterna;
  private String estiloSuperiorForma;
  private String estiloSuperiorFormaIzquierda;
  private String estiloLetras;
  private String mouseFueraBarraSuperiorForma;
  private String mouseFueraBarraSuperiorFormaColor;
  private String nombreSingular;
  private String nombrePlural;
  private String background;
  
  public NavegadorForm() {}
  
  public String getNavegadorNombre() { return navegadorNombre; }
  

  public void setNavegadorNombre(String navegadorNombre)
  {
    this.navegadorNombre = navegadorNombre;
  }
  
  public String getNavegadorVersion()
  {
    return navegadorVersion;
  }
  
  public void setNavegadorVersion(String navegadorVersion)
  {
    this.navegadorVersion = navegadorVersion;
  }
  
  public Integer getPantallaAlto()
  {
    return pantallaAlto;
  }
  
  public void setPantallaAlto(Integer pantallaAlto)
  {
    this.pantallaAlto = pantallaAlto;
  }
  
  public Integer getPantallaAncho()
  {
    return pantallaAncho;
  }
  
  public void setPantallaAncho(Integer pantallaAncho)
  {
    this.pantallaAncho = pantallaAncho;
  }
  
  public Modulo getObjeto()
  {
    return objeto;
  }
  
  public void setObjeto(Modulo objeto)
  {
    this.objeto = objeto;
  }
  
  public Boolean getHayConfiguracion()
  {
    return hayConfiguracion;
  }
  
  public void setHayConfiguracion(Boolean hayConfiguracion)
  {
    this.hayConfiguracion = hayConfiguracion;
  }
  
  public String getLogo1()
  {
    return logo1;
  }
  
  public void setLogo1(String logo1)
  {
    this.logo1 = logo1;
  }
  
  public String getLogo2()
  {
    return logo2;
  }
  
  public void setLogo2(String logo2)
  {
    this.logo2 = logo2;
  }
  
  public String getLogo3()
  {
    return logo3;
  }
  
  public void setLogo3(String logo3)
  {
    this.logo3 = logo3;
  }
  
  public String getLogo4()
  {
    return logo4;
  }
  
  public void setLogo4(String logo4)
  {
    this.logo4 = logo4;
  }
  
  public String getEstiloSuperior()
  {
    return estiloSuperior;
  }
  
  public void setEstiloSuperior(String estiloSuperior)
  {
    this.estiloSuperior = estiloSuperior;
  }
  
  public String getEstiloInferior()
  {
    return estiloInferior;
  }
  
  public void setEstiloInferior(String estiloInferior)
  {
    this.estiloInferior = estiloInferior;
  }
  
  public String getImageBackground()
  {
    return imageBackground;
  }
  
  public void setImageBackground(String imageBackground)
  {
    this.imageBackground = imageBackground;
  }
  
  public String getEstiloFormaInterna()
  {
    return estiloFormaInterna;
  }
  
  public void setEstiloFormaInterna(String estiloFormaInterna)
  {
    this.estiloFormaInterna = estiloFormaInterna;
  }
  
  public String getEstiloSuperiorForma()
  {
    return estiloSuperiorForma;
  }
  
  public void setEstiloSuperiorForma(String estiloSuperiorForma)
  {
    this.estiloSuperiorForma = estiloSuperiorForma;
  }
  
  public String getEstiloSuperiorFormaIzquierda()
  {
    return estiloSuperiorFormaIzquierda;
  }
  
  public void setEstiloSuperiorFormaIzquierda(String estiloSuperiorFormaIzquierda)
  {
    this.estiloSuperiorFormaIzquierda = estiloSuperiorFormaIzquierda;
  }
  
  public String getEstiloLetras()
  {
    return estiloLetras;
  }
  
  public void setEstiloLetras(String estiloLetras)
  {
    this.estiloLetras = estiloLetras;
  }
  
  public String getMouseFueraBarraSuperiorForma()
  {
    return mouseFueraBarraSuperiorForma;
  }
  
  public void setMouseFueraBarraSuperiorForma(String mouseFueraBarraSuperiorForma)
  {
    this.mouseFueraBarraSuperiorForma = mouseFueraBarraSuperiorForma;
  }
  
  public String getMouseFueraBarraSuperiorFormaColor()
  {
    return mouseFueraBarraSuperiorFormaColor;
  }
  
  public void setMouseFueraBarraSuperiorFormaColor(String mouseFueraBarraSuperiorFormaColor)
  {
    this.mouseFueraBarraSuperiorFormaColor = mouseFueraBarraSuperiorFormaColor;
  }
  
  public String getNombreSingular()
  {
    return nombreSingular;
  }
  
  public void setNombreSingular(String nombreSingular)
  {
    this.nombreSingular = nombreSingular;
  }
  
  public String getNombrePlural()
  {
    return nombrePlural;
  }
  
  public void setNombrePlural(String nombrePlural)
  {
    this.nombrePlural = nombrePlural;
  }
  
  public String getBackground()
  {
    return background;
  }
  
  public void setBackground(String background)
  {
    this.background = background;
  }
  
  public void clear()
  {
    objeto = new Modulo();
    hayConfiguracion = null;
    logo1 = null;
    logo2 = null;
    logo3 = null;
    logo4 = null;
    estiloSuperior = null;
    estiloInferior = null;
    imageBackground = null;
    estiloFormaInterna = null;
    estiloSuperiorForma = null;
    estiloSuperiorFormaIzquierda = null;
    estiloLetras = null;
    mouseFueraBarraSuperiorForma = null;
    mouseFueraBarraSuperiorFormaColor = null;
    nombreSingular = null;
    nombrePlural = null;
    background = null;
  }
}
