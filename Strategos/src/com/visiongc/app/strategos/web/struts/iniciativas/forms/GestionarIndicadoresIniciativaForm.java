package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarIndicadoresIniciativaForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
  
	private Long iniciativaId;
	private Long claseId;
	private Long indicadorId;
	private String respuesta;
	private Long graficoSeleccionadoId;
	private String source;

	public Long getIniciativaId()
	{
		return this.iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) 
	{
		this.iniciativaId = iniciativaId;
	}

	public Long getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}

	public Long getIndicadorId() 
	{
		return this.indicadorId;
	}

	public void setIndicadorId(Long indicadorId) 
	{
		this.indicadorId = indicadorId;
	}
  
	public Byte getNaturalezaFormula() 
	{
		return Naturaleza.getNaturalezaFormula();
	}

	public Byte getNaturalezaCualitativoOrdinal() 
	{
		return Naturaleza.getNaturalezaCualitativoOrdinal();
	}

	public Byte getNaturalezaCualitativoNominal() 
	{
		return Naturaleza.getNaturalezaCualitativoNominal();
	}
	
	public String getRespuesta()
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public Long getGraficoSeleccionadoId()
	{
		return this.graficoSeleccionadoId;
	}

	public void setGraficoSeleccionadoId(Long graficoSeleccionadoId) 
	{
		this.graficoSeleccionadoId = graficoSeleccionadoId;
	}
	
	public String getSource() 
	{
		return this.source;
	}

	public void setSource(String source) 
	{
		this.source = source;
	}	
}