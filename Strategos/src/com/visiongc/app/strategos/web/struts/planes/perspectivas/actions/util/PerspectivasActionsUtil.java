package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.util;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public final class PerspectivasActionsUtil
{
	public static void setIniciativas(HttpServletRequest request, StrategosIniciativasService strategosIniciativasService, Perspectiva perspectiva)
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();

		Map<String, String> filtros = new HashMap<String, String>();

		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		filtros.put("historicoDate", "IS NULL");
		
		List<Iniciativa> iniciativasAsociadas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", false, filtros).getLista();
		for (Iterator<Iniciativa> iter = iniciativasAsociadas.iterator(); iter.hasNext(); ) 
		{
			Iniciativa iniciativa = (Iniciativa)iter.next();
			if (iniciativa.getPorcentajeCompletado() != null)
			{
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				
				Medicion medicionProgramada = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
				if (medicionProgramada != null)
					iniciativa.setPorcentajeEsperado(medicionProgramada.getValor());
				
				Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
				if (medicionReal != null)
					iniciativa.setUltimaMedicion(medicionReal.getValor());
			}
		}

		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		
		perspectiva.setListaIniciativas(iniciativasAsociadas);
	}

	public static void setIndicadoresAsociados(HttpServletRequest request, StrategosIndicadoresService strategosIndicadoresService, StrategosPlanesService strategosPlanesService, StrategosPerspectivasService strategosPerspectivasService, Perspectiva perspectiva, Plan plan, boolean filtrarIndicadoresLogro, Integer ano)
	{
		Map filtros = new HashMap();
		
		filtros.put("perspectivaId", perspectiva.getPerspectivaId());

		if ((!perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico())) || (filtrarIndicadoresLogro))
			filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
		List indicadoresAsociados = strategosIndicadoresService.getIndicadores(filtros);
		List indicadoresPerspectiva = strategosPerspectivasService.getIndicadoresPerspectiva(perspectiva.getPerspectivaId());

		for (Iterator iterInd = indicadoresAsociados.iterator(); iterInd.hasNext(); ) 
		{
			Indicador indicador = (Indicador)iterInd.next();
			strategosPlanesService.readDatosIndicadorPlan(indicador, indicador.getOrganizacion().getMesCierre(), ano, plan);
			indicador.setAlerta(strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(), plan.getPlanId()));			
			for (Iterator iterIndPer = indicadoresPerspectiva.iterator(); iterIndPer.hasNext(); ) 
			{
				IndicadorPerspectiva indicadorPerspectiva = (IndicadorPerspectiva)iterIndPer.next();
				if (indicadorPerspectiva.getPk().getIndicadorId().longValue() == indicador.getIndicadorId().longValue()) 
				{
					indicador.setPeso(indicadorPerspectiva.getPeso());
					break;
				}
			}
		}
		perspectiva.setListaIndicadores(new ArrayList());
		perspectiva.setListaIndicadoresGuia(new ArrayList());
		for (Iterator iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
		{
			Indicador indicador = (Indicador)iter.next();
			if (indicador.getResponsableLograrMeta() != null) 
				indicador.getResponsableLograrMeta().getNombre();
			if (indicador.getGuia().booleanValue())
				perspectiva.getListaIndicadoresGuia().add(indicador);
			else
				perspectiva.getListaIndicadores().add(indicador);
		}
	}
}