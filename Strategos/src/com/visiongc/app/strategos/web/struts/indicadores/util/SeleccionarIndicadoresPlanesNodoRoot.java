package com.visiongc.app.strategos.web.struts.indicadores.util;

import com.visiongc.framework.arboles.NodoArbol;
import java.util.Collection;

public class SeleccionarIndicadoresPlanesNodoRoot
  implements NodoArbol
{
  private Collection nodoArbolHijos;

  public String getNodoArbolId()
  {
    return "0";
  }

  public String getNodoArbolNombre() {
    return "planes";
  }

  	public String getNodoArbolNombreImagen(Byte tipo) 
  	{
  		if (tipo == 1)
  			return "PlanNodoRoot";
  		else
  			return "";
  	}

  	public String getNodoArbolNombreToolTipImagen(Byte tipo)
  	{
  		return "";
  	}
  
  public Collection getNodoArbolHijos() {
    return this.nodoArbolHijos;
  }

  public void setNodoArbolHijos(Collection nodoArbolHijos) {
    this.nodoArbolHijos = nodoArbolHijos;
  }

  public boolean getNodoArbolHijosCargados() {
    return true;
  }

  public String getNodoArbolNombreCampoId() {
    return "nodoArbolId";
  }

  public String getNodoArbolNombreCampoNombre() {
    return "nodoArbolNombre";
  }

  public String getNodoArbolNombreCampoPadreId() {
    return null;
  }

  public void setNodoArbolPadre(NodoArbol nodoArbol)
  {
  }

  public NodoArbol getNodoArbolPadre() {
    return null;
  }

  public String getNodoArbolPadreId() {
    return null;
  }

  public void setNodoArbolHijosCargados(boolean cargados)
  {
  }

  public void setNodoArbolNombre(String nombre)
  {
  }
}