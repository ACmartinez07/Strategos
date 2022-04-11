/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

/**
 * @author Kerwin
 *
 */
public class SeleccionarGraficoReporteForm extends SelectorListaForm
{
	static final long serialVersionUID = 0L;

	private Long organizacionId;
	private String rutaCompletaOrganizacion;
	private String graficoNombre;

	public String getRutaCompletaOrganizacion() 
	{
		return this.rutaCompletaOrganizacion;
	}

	public void setRutaCompletaOrganizacion(String rutaCompletaOrganizacion) 
	{
		this.rutaCompletaOrganizacion = rutaCompletaOrganizacion;
	}

	public String getGraficoNombre() 
	{
		return this.graficoNombre;
	}

	public void setGraficoNombre(String graficoNombre) 
	{
		this.graficoNombre = graficoNombre;
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
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setSeleccionados(null);
		setValoresSeleccionados(null);
		  
		this.rutaCompletaOrganizacion = null;
		this.organizacionId = 0L;
	}
}