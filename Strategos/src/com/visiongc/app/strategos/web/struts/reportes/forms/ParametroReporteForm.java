/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.forms;

import java.util.Calendar;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class ParametroReporteForm extends EditarObjetoForm
{
	private static final long serialVersionUID = 1L;
	
	private String anoDesde;
	private String anoHasta;
	private Integer periodoDesde;
	private Integer periodoHasta;
	private String fechaDesde;
	private String fechaHasta;
	private Byte frecuencia;
	private Integer limitePeriodo;

	public String getAnoDesde() 
	{
	    return this.anoDesde;
	}

	public void setAnoDesde(String anoDesde) 
	{
	    this.anoDesde = anoDesde;
	}

	public String getAnoHasta() 
	{
	    return this.anoHasta;
	}

	public void setAnoHasta(String anoHasta) 
	{
	    this.anoHasta = anoHasta;
	}
	
	public Integer getPeriodoDesde() 
	{
		return this.periodoDesde;
	}

	public void setPeriodoDesde(Integer periodoDesde) 
	{
		this.periodoDesde = periodoDesde;
	}

	public Integer getPeriodoHasta() 
	{
		return this.periodoHasta;
	}

	public void setPeriodoHasta(Integer periodoHasta) 
	{
		this.periodoHasta = periodoHasta;
	}

	public String getFechaDesde() 
	{
		return this.fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) 
	{
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() 
	{
		return this.fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) 
	{
		this.fechaHasta = fechaHasta;
	}

	public Byte getFrecuencia() 
	{
	    return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
	    this.frecuencia = frecuencia;
	}

	public void setLimitePeriodo(Integer limitePeriodo) 
	{
		this.limitePeriodo = limitePeriodo;
	}

	public Integer getLimitePeriodo() 
	{
		return this.limitePeriodo;
	}

  	public void clear() 
  	{
  		Calendar fecha = Calendar.getInstance();
  		this.anoDesde = new Integer(fecha.get(1)).toString();
  		this.anoHasta = new Integer(fecha.get(1)).toString();
  		
  		fecha.set(2, 0);
  		fecha.set(5, 1);
  		this.fechaDesde = VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta");
  		fecha.set(2, 11);
  		fecha.set(5, 31);
  		this.fechaHasta = VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta");

  		this.frecuencia = Frecuencia.getFrecuenciaMensual();
  		this.periodoDesde = null;
  		this.periodoHasta = null;
  		this.limitePeriodo = 4;
  	}
}
