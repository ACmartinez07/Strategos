package com.visiongc.app.strategos.web.struts.planes.indicadores.forms;

import java.util.List;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarIndicadoresPlanForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;
		
	private String nombreIndicadorSingular;
	private String nombreIndicadorPlural;
	private String respuesta;
	private Long reporteSeleccionadoId;
	private Long graficoSeleccionadoId;
	private ConfiguracionPlan configuracionPlan;
	
	public Byte getNaturalezaFormula()
	{
		return Naturaleza.getNaturalezaFormula();
	}

	public String getNombreIndicadorSingular() 
	{
		return this.nombreIndicadorSingular;
	}

	public void setNombreIndicadorSingular(String nombreIndicadorSingular) 
	{
		this.nombreIndicadorSingular = nombreIndicadorSingular;
	}

	public String getNombreIndicadorPlural() 
	{
		return this.nombreIndicadorPlural;
	}

	public void setNombreIndicadorPlural(String nombreIndicadorPlural) 
	{
		this.nombreIndicadorPlural = nombreIndicadorPlural;
	}

	public Byte getNaturalezaCualitativoOrdinal() 
	{
		return Naturaleza.getNaturalezaCualitativoOrdinal();
	}

	public Byte getNaturalezaCualitativoNominal() 
	{
		return Naturaleza.getNaturalezaCualitativoNominal();
	}

	public String getNombreIndicadorTipoGuia() 
	{
		return TipoIndicador.getNombre(TipoIndicador.getTipoIndicadorGuia().byteValue());
	}

	public String getNombreIndicadorTipoResultado() 
	{
		return TipoIndicador.getNombre(TipoIndicador.getTipoIndicadorResultado().byteValue());
	}
	
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public Long getReporteSeleccionadoId()
	{
		return this.reporteSeleccionadoId;
	}

	public void setReporteSeleccionadoId(Long reporteSeleccionadoId) 
	{
		this.reporteSeleccionadoId = reporteSeleccionadoId;
	}
  
	public Long getGraficoSeleccionadoId()
	{
		return this.graficoSeleccionadoId;
	}

	public void setGraficoSeleccionadoId(Long graficoSeleccionadoId) 
	{
		this.graficoSeleccionadoId = graficoSeleccionadoId;
	}
	
	public ConfiguracionPlan getConfiguracionPlan() 
	{
		return this.configuracionPlan;
	}

	public void setConfiguracionPlan(ConfiguracionPlan configuracionPlan) 
	{
		this.configuracionPlan = configuracionPlan;
	}
}