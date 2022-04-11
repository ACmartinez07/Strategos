/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.model.Duppont;
import com.visiongc.app.strategos.graficos.model.DuppontPK;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.graficos.util.AnoPeriodo;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.graficos.forms.DuppontForm;
import com.visiongc.app.strategos.web.struts.graficos.forms.DuppontForm.SourceType;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class DuppontActionGrafico extends VgcAction
{
	private List<Insumos> _insumos = null;
	private List<IndicadorNodo> _indicadores = null;
	
	private class Insumos
	{
		private Long padreId;
		private Integer nivel;
		
		public Long getPadreId()
		{
			return this.padreId;
		}

		public void setPadreId(Long padreId) 
		{
			this.padreId = padreId;
		}
		
		public Integer getNivel()
		{
			return this.nivel;
		}

		public void setNivel(Integer nivel) 
		{
			this.nivel = nivel;
		}
	}

	private class IndicadorNodo
	{
		private Long id;
		private Boolean desplegado;
		
		public IndicadorNodo()
		{
			this.id = null;
			this.desplegado = false;
		}
		
		public Long getId()
		{
			return this.id;
		}

		public void setId(Long id) 
		{
			this.id = id;
		}
		
		public Boolean getDesplegado()
		{
			return this.desplegado;
		}

		public void setDesplegado(Boolean desplegado) 
		{
			this.desplegado = desplegado;
		}
	}
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		String format = "##0.00";
		_insumos = new ArrayList<Insumos>();
		_indicadores = new ArrayList<IndicadorNodo>();
		
		String indicadorId = request.getParameter("indicadorId");
		Long planId = ((request.getParameter("planId") != null && request.getParameter("planId") != "") ? Long.parseLong(request.getParameter("planId")) : 0L);
		Integer nivel = (request.getParameter("nivel") != null ? Integer.parseInt(request.getParameter("nivel")) : null);
		Byte source = (request.getParameter("source") != null ? Byte.parseByte(request.getParameter("source")) : SourceType.getSourceGestionar());
		String[] indicadores = (request.getParameter("indicadores") != null && !request.getParameter("indicadores").equals("")) ? request.getParameter("indicadores").split("-") : null;
		
		if (indicadores != null)
		{
			for (int i = 0; i < indicadores.length; i++)
			{
				IndicadorNodo ind = new IndicadorNodo();
				String[] obj = indicadores[i].split(",");
				ind.setId(Long.parseLong(obj[0]));
				ind.setDesplegado(Boolean.parseBoolean(obj[1]));
				_indicadores.add(ind);
			}			
		}

		DuppontForm duppontForm = (DuppontForm)form;
		
		duppontForm.setSource(source);
		Duppont duppont = null;
		if ((indicadorId == null) && (duppontForm != null) && (duppontForm.getIndicador() != null)) 
			indicadorId = duppontForm.getIndicador().getIndicadorId().toString();

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		StrategosGraficosService graficosService = StrategosServiceFactory.getInstance().openStrategosGraficosService();
		Usuario usuario = getUsuarioConectado(request);
		duppont = (Duppont)graficosService.load(Duppont.class, new DuppontPK(usuario.getUsuarioId(), new Long(indicadorId)));
		
		Indicador indicador = (Indicador)strategosPlanesService.load(Indicador.class, new Long(indicadorId));

		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
	    	if (funcion.equals("salvar")) 
	    	{
				int respuesta = VgcReturnCode.DB_OK;
				if (duppont != null)
					respuesta = graficosService.deleteDuppont(duppont, usuario);
	    		
				if (respuesta == VgcReturnCode.DB_OK)
				{
		    		duppont = new Duppont();
		    		duppont.setPk(new DuppontPK(usuario.getUsuarioId(), new Long(indicadorId)));
		    		duppont.setIndicador(indicador);
		    		duppont.setUsuario(usuario);
		    		duppont.setConfiguracion(duppontForm.getXml());
		    		respuesta = graficosService.saveDuppont(duppont, usuario);
				}
	    		
	    		if (respuesta == VgcReturnCode.DB_OK)
	    		{
	    			ActionMessages messages = getMessages(request);
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.dupontindicador.save.success"));
				    saveMessages(request, messages);
	    		}
	    	}
		}
		
		if ((duppontForm != null) && (duppontForm.getIndicador() == null) && duppont != null)
		{
			duppontForm.setXml(duppont.getConfiguracion());
			if (nivel == null && duppontForm.getNumerodeNiveles() != null)
				nivel = duppontForm.getNumerodeNiveles(); 
		}
		else if (duppont == null)
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			ConfiguracionIndicador configuracionIndicador = strategosIndicadoresService.getConfiguracionIndicador(); 
			strategosIndicadoresService.close();
	
			if (nivel == null && configuracionIndicador != null)
				nivel = configuracionIndicador.getIndicadorNivel();
		}
		graficosService.close();
		
		duppontForm.setPlanId(planId);

		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();		
		List<SerieTiempo> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, null).getLista();		
		strategosSeriesTiempoService.close();
		
		StringBuffer sb = new StringBuffer();
		if (indicador != null)
		{
			indicador.getOrganizacion().getNombre();
			indicador.getClase().getNombre();
			duppontForm.setFrecuencia(indicador.getFrecuencia());
			duppontForm.setIndicador(indicador);
			if (duppontForm.getTamanoLetra() == null)
				duppontForm.setTamanoLetra(12);
			int numeroMaximoPeriodos = 0;
			if (duppontForm.getAnoInicial() == null)
			{
				Calendar now = Calendar.getInstance();
				duppontForm.setAnoInicial((new Integer(now.get(1))).toString());
				duppontForm.setAnoFinal((new Integer(now.get(1))).toString());
				
				duppontForm.setPeriodoInicial(new Integer(1));
				if ((new Integer(duppontForm.getAnoInicial()).intValue() % 4 == 0) && (duppontForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
					numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(duppontForm.getFrecuencia().byteValue(), new Integer(duppontForm.getAnoInicial()).intValue());
				else if ((new Integer(duppontForm.getAnoFinal()).intValue() % 4 == 0) && (duppontForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
					numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), new Integer(duppontForm.getAnoFinal()).intValue());
				else 
					numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), new Integer(duppontForm.getAnoInicial()).intValue());
				duppontForm.setPeriodoFinal(new Integer(numeroMaximoPeriodos));
			}
			else
			{
				if ((new Integer(duppontForm.getAnoInicial()).intValue() % 4 == 0) && (duppontForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
					numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(duppontForm.getFrecuencia().byteValue(), new Integer(duppontForm.getAnoInicial()).intValue());
				else if ((new Integer(duppontForm.getAnoFinal()).intValue() % 4 == 0) && (duppontForm.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue()))
					numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), new Integer(duppontForm.getAnoFinal()).intValue());
				else 
					numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), new Integer(duppontForm.getAnoInicial()).intValue());
			}
			
			List<AnoPeriodo> listaAnosPeriodos = AnoPeriodo.getListaAnosPeriodos(new Integer(duppontForm.getAnoInicial()).intValue(), new Integer(duppontForm.getAnoFinal()).intValue(), duppontForm.getPeriodoInicial(), duppontForm.getPeriodoFinal(), numeroMaximoPeriodos);
			StringBuffer sbEjeX = new StringBuffer();
			boolean firsOne = true;
			for (int index = 0; index < listaAnosPeriodos.size(); index++)
			{
				AnoPeriodo periodo = (AnoPeriodo)listaAnosPeriodos.get(index);
				if (!firsOne)
					sbEjeX.append(",");
				sbEjeX.append(periodo.getAno() + "/" + periodo.getPeriodo());
				firsOne = false;
			}
			
			StringBuffer sbSerieName = new StringBuffer();
		    Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
		    firsOne = true;
		    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext(); ) 
		    {
		    	SerieIndicador serie = (SerieIndicador)i.next();

				if (!firsOne)
					sbSerieName.append(duppontForm.getSeparadorSeries());

		        for (Iterator<SerieTiempo> ky = seriesTiempo.iterator(); ky.hasNext(); ) 
		        {
		            SerieTiempo serieTiempo = (SerieTiempo)ky.next();
	
		            if (serieTiempo.getSerieId().equals(serie.getPk().getSerieId())) 
		            {
				    	sbSerieName.append(serieTiempo.getNombre());
		            	break;
		            }
		        }
		    	firsOne = false;
		    }
			if (!firsOne && planId != null && planId != 0L)
			{
				sbSerieName.append(duppontForm.getSeparadorSeries());
				sbSerieName.append(SerieTiempo.getSerieMeta().getNombre());
			}
			
			StringBuffer sbSerieData = new StringBuffer();
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
			boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
			boolean firsOneSerie = true;
		    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext(); ) 
		    {
		    	SerieIndicador serie = (SerieIndicador)i.next();
		    	List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicador.getIndicadorId(), serie.getPk().getSerieId(), new Integer(duppontForm.getAnoInicial()), new Integer(duppontForm.getAnoFinal()), duppontForm.getPeriodoInicial(), duppontForm.getPeriodoFinal(), duppontForm.getFrecuencia(), duppontForm.getFrecuencia(), acumular, false);
				if (!firsOneSerie)
					sbSerieData.append(duppontForm.getSeparadorSeries());
		    	firsOne = true;
				for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext(); ) 
				{
					Medicion medicion = (Medicion)iter.next();
					if (!firsOne)
						sbSerieData.append(",");
					if (medicion.getValor() != null)
						sbSerieData.append(medicion.getValorFormateado(format));
					else
						sbSerieData.append("null");
					firsOne = false;
				}
				
				firsOneSerie = false;
		    }
		    if (!firsOneSerie && planId != null && planId != 0L)
		    {
		    	sbSerieData.append(duppontForm.getSeparadorSeries());
		    	List<MetaAnualParciales> metas = (List<MetaAnualParciales>) strategosMetasService.getMetasAnualesParciales(indicador.getIndicadorId(), planId, duppontForm.getFrecuencia(), new Integer(duppontForm.getAnoInicial()), new Integer(duppontForm.getAnoFinal()), false);
		    	firsOne = true;
		    	for (Iterator<MetaAnualParciales> iter = metas.iterator(); iter.hasNext(); )
				{
					MetaAnualParciales metaAnualParciales = (MetaAnualParciales)iter.next();
					for (Iterator<Meta> iterMeta = metaAnualParciales.getMetasParciales().iterator(); iterMeta.hasNext(); )
					{
						Meta meta = (Meta)iterMeta.next();

						if (!firsOne)
							sbSerieData.append(",");
						if (meta.getValor() != null)
							sbSerieData.append(meta.getValorFormateado(format));
						else
							sbSerieData.append("null");
						firsOne = false;
					}
				}
		    }
		    	
			sb.append("{");
			sb.append("\"name\": \"" + indicador.getNombre() + "\",");
			sb.append("\"value\": \"10\",");
			sb.append("\"type\": \"black\",");
			sb.append("\"indicadorId\": \"" + indicador.getIndicadorId().toString() + "\",");
			if (indicador.getUnidadId() != null && indicador.getUnidad() != null)
				sb.append("\"unidad\": \"" + indicador.getUnidad().getNombre() + "\",");
			else if (indicador.getUnidadId() != null && indicador.getUnidad() == null)
			{
				UnidadMedida unidad = (UnidadMedida) strategosPlanesService.load(UnidadMedida.class, indicador.getUnidadId());
				if (unidad != null)
					sb.append("\"unidad\": \"" + unidad.getNombre() + "\",");
				else
					sb.append("\"unidad\": \"\",");
			}
			else
				sb.append("\"unidad\": \"\",");
			sb.append("\"ejeX\": \"" + sbEjeX.toString() + "\",");
			sb.append("\"serieName\": \"" + sbSerieName.toString() + "\",");
			sb.append("\"serieData\": \"" + sbSerieData.toString() + "\",");
			
			Byte alerta = null;
			if (planId != null && planId != 0L)
				alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), planId);
			if (alerta == null)
				alerta = indicador.getAlerta();
			
			String color = "white";
			String icon = ""; 
			if (alerta != null)
			{
				if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
					color = "#69fd82";
	            else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	            	color = "#f1ff10";
	            else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) 
	            	color = "#f44020";
	            else if (alerta.byteValue() == AlertaIndicador.getAlertaInalterada().byteValue())
	            	icon = "alertaInalterada.gif";
			}
			sb.append("\"alerta\": \"" + (alerta == null ? -1 : alerta) + "\",");
			sb.append("\"organizacion\": \"" + indicador.getOrganizacion().getNombre() + "\",");
			sb.append("\"clase\": \"" + indicador.getClase().getNombre() + "\",");
			sb.append("\"icon\": \"" + icon + "\",");
			
	  		Formula formulaIndicador = getChildren(indicador);
	  		if (formulaIndicador != null && formulaIndicador.getInsumos().size() > 0)
	  		{
	  			Insumos insumo = new Insumos();
	  			insumo.setPadreId(indicador.getIndicadorId());
	  			insumo.setNivel(1);
	  			_insumos.add(insumo);
	  			
	  			sb.append("\"hadChildren\": \"true\",");
	  			if (nivel == null || (nivel != null && (getNivel(indicador.getIndicadorId()) + 1) <= nivel))
	  			{
	  				sb.append("\"level\": \"" + color + "\",");
	  				sb.append("\"desplegado\": \"true\",");
	  			}
	  			else
	  			{
	  				color = "lightsteelblue";
	  				if (alerta != null)
	  				{
	  					if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	  						color = "#64926C";
	  		            else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	  		            	color = "#b2ba23";
	  		            else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) 
	  		            	color = "#B22C2C";
	  				}
	  				sb.append("\"level\": \"" + color + "\",");
	  				sb.append("\"desplegado\": \"false\",");
	  			}
	  		}
	  		else
	  		{
	  			sb.append("\"level\": \"" + color + "\",");
	  			sb.append("\"hadChildren\": \"false\",");
	  			sb.append("\"desplegado\": \"false\",");
	  		}

			Medicion medicion = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieReal().getSerieId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
			if (medicion != null)
			{
				int ano = medicion.getMedicionId().getAno().intValue() - 1;
				int periodoMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), ano);
				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), ano, ano, periodoMaximoIndicador, periodoMaximoIndicador);
				if (mediciones.size() > 0)
				{
					medicion = (Medicion)mediciones.iterator().next();
					indicador.setUltimaMedicionAnoAnterior(medicion.getValor());
				}
			}	
			
			Plan plan = null;
			if (planId != null && planId != 0L)
			{
				plan = (Plan) strategosPlanesService.load(Plan.class, planId);
				if (plan != null)
				{
					sb.append("\"plan\": \"" + plan.getNombre() + "\",");
					
					List<?> estados = strategosPlanesService.getIndicadorEstados(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (estados.size() > 0) 
					{
						IndicadorEstado indEstado = (IndicadorEstado)estados.get(0);
						indicador.setEstadoParcial(indEstado.getEstado());
					}
					estados = strategosPlanesService.getIndicadorEstados(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (estados.size() > 0) 
					{
						IndicadorEstado indEstado = (IndicadorEstado)estados.get(0);
						indicador.setEstadoAnual(indEstado.getEstado());
					}
					
					sb.append("\"pa\": \"" + indicador.getEstadoAnualFormateado() + "\",");
					sb.append("\"pp\": \"" + indicador.getEstadoParcialFormateado() + "\",");
				}
				else
				{
					sb.append("\"plan\": \"\",");
					sb.append("\"pa\": \"" + indicador.getPorcentajeCumplimientoFormateado(indicador.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial()) + "\",");
					sb.append("\"pp\": \"" + indicador.getPorcentajeCumplimientoFormateado(indicador.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual()) + "\",");
				}
			}
			else
			{
				sb.append("\"plan\": \"\",");
				sb.append("\"pa\": \"" + indicador.getPorcentajeCumplimientoFormateado(indicador.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial()) + "\",");
				sb.append("\"pp\": \"" + indicador.getPorcentajeCumplimientoFormateado(indicador.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual()) + "\",");
			}
			
			sb.append("\"insumo\": \"\",");
			sb.append("\"parent\": \"null\"");
			
			if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
				sb.append(", \"children\": [" + setDefinicionFomula(duppontForm, indicador, planId, sbEjeX, seriesTiempo, format, nivel, strategosPlanesService, strategosMedicionesService, strategosMetasService) + "]");		
			
			nivel = (nivel != null ? (((getNivelMaximo() + 1) < nivel) ? (getNivelMaximo() + 1) : nivel) : null);  
			duppontForm.setNumerodeNiveles(nivel != null ? nivel : getNivelMaximo() + 1);
			sb.append("}");
			duppontForm.setArbol(sb.toString());
			
			strategosMedicionesService.close();
			strategosMetasService.close();
		}

		strategosPlanesService.close();
		
		return mapping.findForward(forward);
	}
	
	private Formula getChildren(Indicador indicador)
	{
	    Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();

	    SerieIndicador serieIndicador = null;
	    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext(); ) 
	    {
	    	SerieIndicador serie = (SerieIndicador)i.next();

	    	if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) 
	    		serieIndicador = serie;
	    }

  		Formula formulaIndicador = null;
  		if (serieIndicador.getFormulas().size() > 0) 
  			formulaIndicador = (Formula)serieIndicador.getFormulas().toArray()[0];

  		return formulaIndicador;
	}
	
	private String setDefinicionFomula(DuppontForm duppontForm, Indicador indicador, Long planId, StringBuffer sbEjeX, List<SerieTiempo> seriesTiempo, String format, Integer nivel, StrategosPlanesService strategosPlanesService, StrategosMedicionesService strategosMedicionesService, StrategosMetasService strategosMetasService)
	{
		StringBuffer sb = null;
		
		Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
  		Formula formulaIndicador = getChildren(indicador);

  		if (formulaIndicador != null)
  		{
  			if (nivel == null || (nivel != null && (getNivel(indicador.getIndicadorId()) + 1) <= nivel) || ((getDesplegado(indicador.getIndicadorId()))))
  			{	
	  			for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext(); ) 
	  			{
	  				InsumoFormula insumo = (InsumoFormula)k.next();
	  				Indicador indicadorInsumo = (Indicador) strategosPlanesService.load(Indicador.class, insumo.getPk().getIndicadorId());
	  				
	  				if (indicadorInsumo != null && indicadorInsumo.getMostrarEnArbol())
	  				{
	  					StringBuffer sbSerieName = new StringBuffer();
	  				    seriesIndicador = indicadorInsumo.getSeriesIndicador();
	  				    boolean firsOne = true;
	
	  					IndicadorPlan indicadorPlan = null;
	  					Long planActivoId = null;
	  					if (planId != null && planId != 0L)
	  						indicadorPlan = strategosPlanesService.getFirstAlertaIndicadorPorPlan(indicadorInsumo.getIndicadorId());
	  				    
	  				    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext(); ) 
	  				    {
	  				    	SerieIndicador serie = (SerieIndicador)i.next();
	
	  						if (!firsOne)
	  							sbSerieName.append(duppontForm.getSeparadorSeries());
	
	  				        for (Iterator<SerieTiempo> ky = seriesTiempo.iterator(); ky.hasNext(); ) 
	  				        {
	  				            SerieTiempo serieTiempo = (SerieTiempo)ky.next();
	  			
	  				            if (serieTiempo.getSerieId().equals(serie.getPk().getSerieId())) 
	  				            {
	  						    	sbSerieName.append(serieTiempo.getNombre());
	  				            	break;
	  				            }
	  				        }
	  				    	firsOne = false;
	  				    }
				    	List<MetaAnualParciales> metas = null;
	  					if (!firsOne && planId != null && planId != 0L)
	  					{
	  				    	metas = (List<MetaAnualParciales>) strategosMetasService.getMetasAnualesParciales(indicadorInsumo.getIndicadorId(), planId, duppontForm.getFrecuencia(), new Integer(duppontForm.getAnoInicial()), new Integer(duppontForm.getAnoFinal()), false);
	  				    	if (metas != null && metas.size() > 0 && metas.get(0).getMetasParciales().size() > 0 && ((Meta)metas.get(0).getMetasParciales().get(0)).getValor() == null)
	  				    		metas = null;
	  				    	else
	  				    		planActivoId = planId;
	  				    	if (metas == null && indicadorPlan != null)
	  				    		metas = (List<MetaAnualParciales>) strategosMetasService.getMetasAnualesParciales(indicadorInsumo.getIndicadorId(), indicadorPlan.getPk().getPlanId(), duppontForm.getFrecuencia(), new Integer(duppontForm.getAnoInicial()), new Integer(duppontForm.getAnoFinal()), false);
	  				    	if (metas != null && metas.size() > 0 && metas.get(0).getMetasParciales().size() > 0 && ((Meta)metas.get(0).getMetasParciales().get(0)).getValor() == null)
	  				    		metas = null;
	  				    	else if (indicadorPlan != null)
	  				    		planActivoId = indicadorPlan.getPk().getPlanId();
	
	  				    	if (metas != null && metas.size() > 0)
	  				    	{
	  	  						sbSerieName.append(duppontForm.getSeparadorSeries());
	  	  						sbSerieName.append(SerieTiempo.getSerieMeta().getNombre());
	  				    	}
	  					}
	  					
	  					StringBuffer sbSerieData = new StringBuffer();
	  					boolean acumular = (indicadorInsumo.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicadorInsumo.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
	  					boolean firsOneSerie = true;
	  				    for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext(); ) 
	  				    {
	  				    	SerieIndicador serie = (SerieIndicador)i.next();
	  				    	List<Medicion> mediciones = (List<Medicion>) strategosMedicionesService.getMedicionesPorFrecuencia(indicadorInsumo.getIndicadorId(), serie.getPk().getSerieId(), new Integer(duppontForm.getAnoInicial()), new Integer(duppontForm.getAnoFinal()), duppontForm.getPeriodoInicial(), duppontForm.getPeriodoFinal(), duppontForm.getFrecuencia(), duppontForm.getFrecuencia(), acumular, false);
	  						if (!firsOneSerie)
	  							sbSerieData.append(duppontForm.getSeparadorSeries());
	  				    	firsOne = true;
	  						for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext(); ) 
	  						{
	  							Medicion medicion = (Medicion)iter.next();
		  							if (!firsOne)
	  	  								sbSerieData.append(",");
	  							if (medicion.getValor() != null)
	  								sbSerieData.append(medicion.getValorFormateado(format));
	  							else
	  								sbSerieData.append("null");
	  							firsOne = false;
	  						}
	  						
	  						firsOneSerie = false;
	  				    }
	
	  				    if (!firsOneSerie && planId != null && planId != 0L && metas != null && metas.size() > 0)
	  				    {
	  				    	sbSerieData.append(duppontForm.getSeparadorSeries());
	  				    	firsOne = true;
	  				    	for (Iterator<MetaAnualParciales> iter = metas.iterator(); iter.hasNext(); )
	  						{
	  							MetaAnualParciales metaAnualParciales = (MetaAnualParciales)iter.next();
	  							for (Iterator<Meta> iterMeta = metaAnualParciales.getMetasParciales().iterator(); iterMeta.hasNext(); )
	  							{
	  								Meta meta = (Meta)iterMeta.next();
	
	  								if (!firsOne)
	  									sbSerieData.append(",");
	  								if (meta.getValor() != null)
	  									sbSerieData.append(meta.getValorFormateado(format));
	  								else
	  									sbSerieData.append("null");
	  								firsOne = false;
	  							}
	  						}
	  				    }
	  				    
	  					if (sb == null)
	  						sb = new StringBuffer();
	  					else
	  						sb.append(",");
	  					
	  					sb.append("{");
	  					sb.append("\"name\": \"" + indicadorInsumo.getNombre() + "\",");
	  					sb.append("\"value\": \"10\",");
	  					sb.append("\"type\": \"black\",");
	  					sb.append("\"indicadorId\": \"" + indicadorInsumo.getIndicadorId().toString() + "\",");
	  					if (indicadorInsumo.getUnidadId() != null && indicadorInsumo.getUnidad() != null)
	  						sb.append("\"unidad\": \"" + indicadorInsumo.getUnidad().getNombre() + "\",");
	  					else if (indicadorInsumo.getUnidadId() != null && indicadorInsumo.getUnidad() == null)
	  					{
	  						UnidadMedida unidad = (UnidadMedida) strategosPlanesService.load(UnidadMedida.class, indicadorInsumo.getUnidadId());
	  						if (unidad != null)
	  							sb.append("\"unidad\": \"" + unidad.getNombre() + "\",");
	  						else
	  							sb.append("\"unidad\": \"\",");
	  					}
	  					else
	  						sb.append("\"unidad\": \"\",");
	  					sb.append("\"ejeX\": \"" + sbEjeX.toString() + "\",");
	  					sb.append("\"serieName\": \"" + sbSerieName.toString() + "\",");
	  					sb.append("\"serieData\": \"" + sbSerieData.toString() + "\",");
	  					
	  					Byte alerta = null;
	  					if (planActivoId != null && planActivoId != 0L)
	  					{
	  						if (indicadorPlan != null && planActivoId.longValue() == indicadorPlan.getPk().getPlanId().longValue())
	  							alerta = indicadorPlan.getCrecimiento();
	  						else
	  							alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicadorInsumo.getIndicadorId(), planActivoId);
	  					}
	  					if (alerta == null)
	  						alerta = indicadorInsumo.getAlerta();
	  					
	  					String color = "white";
	  					String icon = "";
	  					if (alerta != null)
	  					{
	  						if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	  							color = "#69fd82";
	  			            else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	  			            	color = "#f1ff10";
	  			            else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) 
	  			            	color = "#f44020";
	  			            else if (alerta.byteValue() == AlertaIndicador.getAlertaInalterada().byteValue())
	  			            	icon = "alertaInalterada.gif";
	  					}
	  					sb.append("\"alerta\": \"" + (alerta == null ? -1 : alerta) + "\",");
	  					sb.append("\"organizacion\": \"" + indicadorInsumo.getOrganizacion().getNombre() + "\",");
	  					sb.append("\"clase\": \"" + indicadorInsumo.getClase().getNombre() + "\",");
	  					sb.append("\"icon\": \"" + icon + "\",");

	  			  		Formula formulaIndicadorInsumo = getChildren(indicadorInsumo);
	  			  		if (formulaIndicadorInsumo != null && formulaIndicadorInsumo.getInsumos().size() > 0 && indicadorInsumo.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue())
	  			  		{
	  			  			if (!existe(indicadorInsumo.getIndicadorId()))
	  			  			{
		  			  			Insumos insumoPadre = new Insumos();
		  			  			insumoPadre.setPadreId(indicadorInsumo.getIndicadorId());
		  			  			insumoPadre.setNivel((getNivel(indicador.getIndicadorId()) + 1));
		  			  			_insumos.add(insumoPadre);
	  			  			}
	  			  			
	  			  			sb.append("\"hadChildren\": \"true\",");
	  			  			if (nivel == null || (nivel != null && (getNivel(indicadorInsumo.getIndicadorId()) + 1) <= nivel) || (getDesplegado(indicadorInsumo.getIndicadorId())))
	  			  			{
	  			  				sb.append("\"level\": \"" + color + "\",");
	  			  				sb.append("\"desplegado\": \"true\",");
	  			  			}
	  			  			else
	  			  			{
	  			  				color = "lightsteelblue";
	  			  				if (alerta != null)
	  			  				{
	  			  					if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
	  			  						color = "#64926C";
	  			  		            else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
	  			  		            	color = "#b2ba23";
	  			  		            else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) 
	  			  		            	color = "#B22C2C";
	  			  				}
	  			  				sb.append("\"level\": \"" + color + "\",");
	  			  				sb.append("\"desplegado\": \"false\",");
	  			  			}
	  			  		}
	  			  		else
	  			  		{
	  			  			sb.append("\"level\": \"" + color + "\",");
	  			  			sb.append("\"hadChildren\": \"false\",");
	  			  			sb.append("\"desplegado\": \"false\",");
	  			  		}

	  					Medicion medicion = strategosMedicionesService.getUltimaMedicion(indicadorInsumo.getIndicadorId(), indicadorInsumo.getFrecuencia(), indicadorInsumo.getMesCierre(), SerieTiempo.getSerieReal().getSerieId(), indicadorInsumo.getValorInicialCero(), indicadorInsumo.getCorte(), indicadorInsumo.getTipoCargaMedicion());
	  					if (medicion != null)
	  					{
	  						int ano = medicion.getMedicionId().getAno().intValue() - 1;
	  						int periodoMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicadorInsumo.getFrecuencia(), ano);
	  						List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorInsumo.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), ano, ano, periodoMaximoIndicador, periodoMaximoIndicador);
	  						if (mediciones.size() > 0)
	  						{
	  							medicion = (Medicion)mediciones.iterator().next();
	  							indicador.setUltimaMedicionAnoAnterior(medicion.getValor());
	  						}
	  					}	
	  				    
	  					Plan plan = null;
	  					if (planActivoId != null && planActivoId != 0L)
	  					{
	  						plan = (Plan) strategosPlanesService.load(Plan.class, planActivoId);
	  						if (plan != null)
	  						{
	  							sb.append("\"plan\": \"" + plan.getNombre() + "\",");
	
								List<?> estados = strategosPlanesService.getIndicadorEstados(indicadorInsumo.getIndicadorId(), planActivoId, indicadorInsumo.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), indicadorInsumo.getFechaUltimaMedicionAno(), indicadorInsumo.getFechaUltimaMedicionAno(), indicadorInsumo.getFechaUltimaMedicionPeriodo(), indicadorInsumo.getFechaUltimaMedicionPeriodo());
								if (estados.size() > 0) 
								{
									IndicadorEstado indEstado = (IndicadorEstado)estados.get(0);
									indicadorInsumo.setEstadoParcial(indEstado.getEstado());
								}
								estados = strategosPlanesService.getIndicadorEstados(indicadorInsumo.getIndicadorId(), planActivoId, indicadorInsumo.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), indicadorInsumo.getFechaUltimaMedicionAno(), indicadorInsumo.getFechaUltimaMedicionAno(), indicadorInsumo.getFechaUltimaMedicionPeriodo(), indicadorInsumo.getFechaUltimaMedicionPeriodo());
								if (estados.size() > 0) 
								{
									IndicadorEstado indEstado = (IndicadorEstado)estados.get(0);
									indicadorInsumo.setEstadoAnual(indEstado.getEstado());
								}
								
								sb.append("\"pa\": \"" + indicadorInsumo.getEstadoAnualFormateado() + "\",");
								sb.append("\"pp\": \"" + indicadorInsumo.getEstadoParcialFormateado() + "\",");
							}
							else
							{
								sb.append("\"plan\": \"\",");
								sb.append("\"pa\": \"" + indicadorInsumo.getPorcentajeCumplimientoFormateado(indicadorInsumo.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial()) + "\",");
								sb.append("\"pp\": \"" + indicadorInsumo.getPorcentajeCumplimientoFormateado(indicadorInsumo.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual()) + "\",");
							}
						}
						else
						{
							sb.append("\"plan\": \"\",");
							sb.append("\"pa\": \"" + indicadorInsumo.getPorcentajeCumplimientoFormateado(indicadorInsumo.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial()) + "\",");
							sb.append("\"pp\": \"" + indicadorInsumo.getPorcentajeCumplimientoFormateado(indicadorInsumo.getUltimaMedicionAnoAnterior(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual()) + "\",");
						}
	  					
	  					sb.append("\"insumo\": \"" + insumo.getSerieTiempo().getNombre() + "\",");
	  					sb.append("\"parent\": \"" + indicador.getNombre() + "\"");
	  					
	  					if (indicadorInsumo.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
	  						sb.append(", \"children\": [" + setDefinicionFomula(duppontForm, indicadorInsumo, planId, sbEjeX, seriesTiempo, format, nivel, strategosPlanesService, strategosMedicionesService, strategosMetasService) + "]");		
	
	  					sb.append("}");
	  				}
	  			}
  			}
  		}

  		if (sb != null)
  			return sb.toString();
  		else
  			return "";
	}
	
	private boolean existe(Long id)
	{
		boolean existe = false;
		
		for (Iterator<Insumos> iter = _insumos.iterator(); iter.hasNext(); )
		{
			Insumos insumo = (Insumos)iter.next();
			if (id.longValue() == insumo.getPadreId().longValue())
				existe = true;
		}
		
		return existe;
	}

	private Integer getNivel(Long padreId)
	{
		Integer nivel = null;
		
		for (Iterator<Insumos> iter = _insumos.iterator(); iter.hasNext(); )
		{
			Insumos insumo = (Insumos)iter.next();
			if (padreId.longValue() == insumo.getPadreId().longValue())
				nivel = insumo.getNivel();
		}
		
		return nivel;
	}
	
	private Integer getNivelMaximo()
	{
		Integer nivel = 0;
		
		for (Iterator<Insumos> iter = _insumos.iterator(); iter.hasNext(); )
		{
			Insumos insumo = (Insumos)iter.next();
			if (nivel < insumo.getNivel())
				nivel = insumo.getNivel();
		}
		
		return nivel;
	}
	
	private Boolean getDesplegado(Long indicadorId)
	{
		Boolean desplegado = false;
		for (Iterator<IndicadorNodo> iter = _indicadores.iterator(); iter.hasNext(); )
		{
			IndicadorNodo ind = (IndicadorNodo)iter.next();
			if (ind.getId().longValue() == indicadorId.longValue())
			{
				desplegado = ind.getDesplegado();
				break;
			}
		}
		
		return desplegado;
	}
}
