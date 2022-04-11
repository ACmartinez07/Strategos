package com.visiongc.app.strategos.web.struts.indicadores.forms;

import java.util.List;

public class InsumoFormulaForm
{
	private int indice;
	private List<?> path;

	public int getIndice()
	{
		return this.indice;
	}
	
	public void setIndice(int indice) 
	{
		this.indice = indice;
	}
	
	public List<?> getPath() 
	{
		return this.path;
	}
  
	public void setPath(List<?> path) 
	{
		this.path = path;
	}
}