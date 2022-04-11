/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.forms;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

/**
 * @author Kerwin
 *
 */
public class GestionarReporteGraficoForm  extends SelectorListaForm
{
	static final long serialVersionUID = 0L;
	
	private String nombreVistaSeleccionada;
	private int paginaSeleccionadaVistas;
	private String atributoOrdenVistas;
	private String tipoOrdenVistas;
	private String seleccionadosVistas;
	private String bloqueadosVistas;

	public String getNombreVistaSeleccionada()
	{
		return this.nombreVistaSeleccionada;
	}

	public void setNombreVistaSeleccionada(String nombreVistaSeleccionada) 
	{
	    this.nombreVistaSeleccionada = nombreVistaSeleccionada;
	}

	public int getPaginaSeleccionadaVistas() 
	{
	    return this.paginaSeleccionadaVistas;
	}

	public void setPaginaSeleccionadaVistas(int paginaSeleccionadaVistas) 
	{
	    this.paginaSeleccionadaVistas = paginaSeleccionadaVistas;
	}

	public String getAtributoOrdenVistas() 
	{
	    return this.atributoOrdenVistas;
	}

	public void setAtributoOrdenVistas(String atributoOrdenVistas) 
	{
	    this.atributoOrdenVistas = atributoOrdenVistas;
	}

	public String getTipoOrdenVistas() 
	{
	    return this.tipoOrdenVistas;
	}

	public void setTipoOrdenVistas(String tipoOrdenVistas) 
	{
	    this.tipoOrdenVistas = tipoOrdenVistas;
	}

	public String getBloqueadosVistas() 
	{
	    return this.bloqueadosVistas;
	}

	public void setBloqueadosVistas(String bloqueadosVistas) 
	{
	    this.bloqueadosVistas = bloqueadosVistas;
	}

	public String getSeleccionadosVistas() 
	{
	    return this.seleccionadosVistas;
	}

	public void setSeleccionadosVistas(String seleccionadosVistas) 
	{
	    this.seleccionadosVistas = seleccionadosVistas;
	}

	public void clear()
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setSeleccionados(null);
		setValoresSeleccionados(null);

		this.nombreVistaSeleccionada = null;
	    this.paginaSeleccionadaVistas = 1;
	    this.atributoOrdenVistas = "nombre";
	    this.tipoOrdenVistas = "ASC";
	    this.seleccionadosVistas = null;
	}
}