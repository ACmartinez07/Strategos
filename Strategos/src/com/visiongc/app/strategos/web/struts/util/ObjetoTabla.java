/**
 * 
 */
package com.visiongc.app.strategos.web.struts.util;

import java.util.ArrayList;
import java.util.List;

import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class ObjetoTabla extends EditarObjetoForm
{
	private static final long serialVersionUID = 1L;
	
	private String encabezado;
	private Integer columnas;
	private List<List<DatoCelda>> matrizDatos;

	public String getEncabezado() 
	{
		return this.encabezado;
	}

	public void setEncabezado(String encabezado) 
	{
		this.encabezado = encabezado;
	}

	public Integer getColumnas() 
	{
		return this.columnas;
	}

	public void setColumnas(Integer columnas) 
	{
		this.columnas = columnas;
	}
	
	public List<List<DatoCelda>> getMatrizDatos()
  	{
  		return this.matrizDatos;
  	}

  	public void setMatrizDatos(List<List<DatoCelda>> matrizDatos) 
  	{
  		this.matrizDatos = matrizDatos;
  	}
  	
  	public void clear()
  	{
  		this.matrizDatos = new ArrayList<List<DatoCelda>>();
  		this.encabezado = null;
  		this.columnas = null;
  	}
}