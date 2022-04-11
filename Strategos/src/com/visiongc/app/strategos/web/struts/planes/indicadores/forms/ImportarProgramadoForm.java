/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planes.indicadores.forms;

import java.util.ArrayList;
import java.util.List;

import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class ImportarProgramadoForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;

	private List<ObjetoClaveValor> grupoAnos;
	private String anoSeleccion;

	//Campos para comunicacion
	private String valoresSeleccionados = null;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;

	public String getNombreForma() 
	{
		return nombreForma;
	}

	public void setNombreForma(String nombreForma) 
	{
		this.nombreForma = nombreForma;
	}

	public String getNombreCampoOculto() 
	{
		return nombreCampoOculto;
	}

	public void setNombreCampoOculto(String nombreCampoOculto) 
	{
		this.nombreCampoOculto = nombreCampoOculto;
	}

	public String getNombreCampoValor() 
	{
		return nombreCampoValor;
	}

	public void setNombreCampoValor(String nombreCampoValor) 
	{
		this.nombreCampoValor = nombreCampoValor;
	}

	public String getFuncionCierre() 
	{
		return funcionCierre;
	}

	public void setFuncionCierre(String funcionCierre) 
	{
		this.funcionCierre = funcionCierre;
	}
	
	public String getValoresSeleccionados() 
	{
		return valoresSeleccionados;
	}

	public void setValoresSeleccionados(String valoresSeleccionados) 
	{
		this.valoresSeleccionados = valoresSeleccionados;
	}

	public String getAnoSeleccion() 
	{
		return this.anoSeleccion;
	}

	public void setAnoSeleccion(String anoSeleccion) 
	{
		this.anoSeleccion = anoSeleccion;
	}
	  
	public List<ObjetoClaveValor> getGrupoAnos() 
	{
		return this.grupoAnos;
	}
	
	public void setGrupoAnos(List<ObjetoClaveValor> grupoAnos) 
	{
		this.grupoAnos = grupoAnos;
	}
	
	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setValoresSeleccionados(null);

		this.grupoAnos = new ArrayList<ObjetoClaveValor>();
		this.anoSeleccion = null;
	}
}