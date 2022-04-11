/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.forms;

import com.visiongc.commons.util.CondicionType;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

/**
 * @author Kerwin
 *
 */
public class GestionarCooperantesForm  extends VisorListaForm 
{
	static final long serialVersionUID = 0;

	private String respuesta;
	private String seleccionadoId;
	private String seleccionadoNombre;
	private Long portafolioId;
	private Long iniciativaId;
	private String anchoPorDefecto;
	private String altoPorDefecto;
	private Long organizacionId;

	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
  	public String getSeleccionadoId() 
  	{
  		return this.seleccionadoId;
  	}

  	public void setSeleccionadoId(String seleccionadoId) 
  	{
  		this.seleccionadoId = seleccionadoId;
  	}

  	public String getSeleccionadoNombre() 
  	{
  		return this.seleccionadoNombre;
  	}

  	public void setSeleccionadoNombre(String seleccionadoNombre) 
  	{
  		this.seleccionadoNombre = seleccionadoNombre;
  	}

  	public Long getPortafolioId() 
  	{
  		return this.portafolioId;
  	}

  	public void setPortafolioId(Long portafolioId) 
  	{
  		this.portafolioId = portafolioId;
  	}

  	public Long getIniciativaId() 
  	{
  		return this.iniciativaId;
  	}

  	public void setIniciativaId(Long iniciativaId) 
  	{
  		this.iniciativaId = iniciativaId;
  	}
  	
	public String getAnchoPorDefecto() 
	{
		return this.anchoPorDefecto;
	}

	public void setAnchoPorDefecto(String anchoPorDefecto) 
	{
		this.anchoPorDefecto = anchoPorDefecto;
	}
	
	public String getAltoPorDefecto() 
	{
		return this.altoPorDefecto;
	}

	public void setAltoPorDefecto(String altoPorDefecto) 
	{
		this.altoPorDefecto = altoPorDefecto;
	}
	
  	public Long getOrganizacionId() 
  	{
  		return this.organizacionId;
  	}

  	public void setOrganizacionId(Long organizacionId) 
  	{
  		this.organizacionId = organizacionId;
  	}
  	
 	public void clear() 
	{
		this.respuesta = "";
		this.seleccionadoId = null;
		this.seleccionadoNombre = null;
		this.portafolioId = null;
		this.iniciativaId = null;
		this.anchoPorDefecto = null;
		this.altoPorDefecto = null;
		this.organizacionId = null;
		
		FiltroForm filtro = new FiltroForm();
		filtro.setCondicion(CondicionType.getFiltroCondicionActivo());
		filtro.setNombre(null);
		this.setFiltro(filtro);
	}
}
