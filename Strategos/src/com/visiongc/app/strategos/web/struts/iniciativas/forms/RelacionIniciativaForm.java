/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
import com.visiongc.framework.web.struts.forms.SelectorArbolForm;

/**
 * @author Kerwin
 *
 */
public class RelacionIniciativaForm  extends SelectorArbolForm
{
	static final long serialVersionUID = 0L;
  
	public static final String SEPARADOR_RUTA = "!#!";
	public static final String CODIGO_PERSPECTIVA_ELIMINADA = "!ELIMINADO!";
	private Long organizacionSeleccionadaId;
	private Long iniciativaSeleccionadaId;
	private String rutaCompletaOrganizacion;
	private String seleccionado;
	private String nombreCampoRutaCompleta;
	private String panelSeleccionado;
	private Boolean iniciado;

	public Long getOrganizacionSeleccionadaId()
	{
		return this.organizacionSeleccionadaId;
	}

	public void setOrganizacionSeleccionadaId(Long organizacionSeleccionadaId) 
	{
		this.organizacionSeleccionadaId = organizacionSeleccionadaId;
	}

	public Long getIniciativaSeleccionadaId() 
	{
		return this.iniciativaSeleccionadaId;
	}

	public void setIniciativaSeleccionadaId(Long iniciativaSeleccionadaId) 
	{
		this.iniciativaSeleccionadaId = iniciativaSeleccionadaId;
	}

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}

	public String getSeleccionado() 
	{
		return this.seleccionado;
	}

	public void setSeleccionado(String seleccionado) 
	{
		this.seleccionado = seleccionado;
	}

	public String getNombreCampoRutaCompleta() 
	{
		return this.nombreCampoRutaCompleta;
	}

	public void setNombreCampoRutaCompleta(String nombreCampoRutaCompleta) 
	{
		this.nombreCampoRutaCompleta = nombreCampoRutaCompleta;
	}

	public String getPanelSeleccionado() 
	{
		return this.panelSeleccionado;
	}

	public void setPanelSeleccionado(String panelSeleccionado) 
	{
		this.panelSeleccionado = panelSeleccionado;
	}

	public Boolean getIniciado() 
	{
		return this.iniciado;
	}

	public void setIniciado(Boolean iniciado) 
	{
		this.iniciado = iniciado;
	}

  	public String getSeparadorRuta() 
  	{
  		return new EditarPerspectivaForm().getSeparadorRuta();
  	}

  	public String getCodigoPerspectivaEliminada() 
  	{
  		return "!ELIMINADO!";
  	}

  	public void clear() 
  	{
  		setNombreForma(null);
  		setNombreCampoValor(null);
  		setNombreCampoOculto(null);
  		setValoresSeleccionados(null);
  		setFuncionCierre(null);
  		this.organizacionSeleccionadaId = null;
  		this.iniciativaSeleccionadaId = null;
  		this.seleccionado = null;
  		this.nombreCampoRutaCompleta = null;
  		this.panelSeleccionado = "panelPerspectivas";
  		this.rutaCompletaOrganizacion = null;
  		this.iniciado = new Boolean(false);
  	}
}