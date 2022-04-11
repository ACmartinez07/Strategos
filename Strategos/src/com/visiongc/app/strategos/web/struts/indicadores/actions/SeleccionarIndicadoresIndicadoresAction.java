package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.SeleccionarIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarIndicadoresIndicadoresAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarIndicadoresForm seleccionarIndicadoresForm = (SeleccionarIndicadoresForm)form;

		if (seleccionarIndicadoresForm.getAtributoOrden() == null) 
			seleccionarIndicadoresForm.setAtributoOrden("nombre");
		if (seleccionarIndicadoresForm.getTipoOrden() == null) 
			seleccionarIndicadoresForm.setTipoOrden("ASC");

		String llamadaDesde = request.getParameter("llamadaDesde");
		boolean agregarSerieMeta = seleccionarIndicadoresForm.getAgregarSerieMeta();
		boolean desdePlanes = false;
		if (llamadaDesde != null) 
		{
			if (llamadaDesde.equals("Organizaciones"))
				seleccionarIndicadoresForm.setPanelSeleccionado("panelOrganizaciones");
			else if (llamadaDesde.equals("ClasesIndicadores")) 
				seleccionarIndicadoresForm.setPanelSeleccionado("panelClases");
		}

		StrategosIndicadoresService indicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		if (seleccionarIndicadoresForm.getPanelIndicadores().equals("clases")) 
		{
			if (seleccionarIndicadoresForm.getOrganizacionSeleccionadaId() != null) 
				filtros.put("organizacionId", seleccionarIndicadoresForm.getOrganizacionSeleccionadaId().toString());
			if (seleccionarIndicadoresForm.getClaseSeleccionadaId() != null)
				filtros.put("claseId", seleccionarIndicadoresForm.getClaseSeleccionadaId().toString());
		}
		else if (seleccionarIndicadoresForm.getPanelIndicadores().equals("iniciativas")) 
		{
			if ((seleccionarIndicadoresForm.getIniciativasNodoSeleccionado() instanceof Iniciativa)) 
			{
				Iniciativa iniciativa = (Iniciativa)seleccionarIndicadoresForm.getIniciativasNodoSeleccionado();
				if (iniciativa.getClaseId() != null)
					filtros.put("claseId", iniciativa.getClaseId().toString());
				else
					filtros.put("organizacionId", "0");
			}
			else if ((seleccionarIndicadoresForm.getIniciativasNodoSeleccionado() instanceof PryActividad)) 
			{
				PryActividad actividad = (PryActividad)seleccionarIndicadoresForm.getIniciativasNodoSeleccionado();
				if (actividad.getClaseId() != null)
					filtros.put("claseId", actividad.getClaseId().toString());
				else
					filtros.put("organizacionId", "0");
			}
			else 
				filtros.put("organizacionId", "0");
		} 
		else if (seleccionarIndicadoresForm.getPanelIndicadores().equals("planes")) 
		{
			desdePlanes = true;
			if (seleccionarIndicadoresForm.getPlanesNodoSeleccionado().getClass().equals(Plan.class)) 
			{
				Plan plan = (Plan)seleccionarIndicadoresForm.getPlanesNodoSeleccionado();
				filtros.put("planId", plan.getPlanId().toString());
			} 
			else if (seleccionarIndicadoresForm.getPlanesNodoSeleccionado().getClass().equals(Perspectiva.class)) 
			{
				Perspectiva perspectiva = (Perspectiva)seleccionarIndicadoresForm.getPlanesNodoSeleccionado();
								
				if (perspectiva.getPadreId() != null) 
					filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
				else if (perspectiva.getPadreId() == null)
					filtros.put("indicadoresLogroPlanId", perspectiva.getPlanId().toString());
			} 
			else 
				filtros.put("organizacionId", "0");
		}
		if ((seleccionarIndicadoresForm.getFrecuenciaSeleccionada() != null) && (!seleccionarIndicadoresForm.getFrecuenciaSeleccionada().equals(""))) 
			filtros.put("frecuencia", seleccionarIndicadoresForm.getFrecuenciaSeleccionada().toString());
		if ((seleccionarIndicadoresForm.getFrecuenciasContenidasSeleccionada() != null) && (!seleccionarIndicadoresForm.getFrecuenciasContenidasSeleccionada().equals(""))) 
			filtros.put("frecuenciasContenidas", seleccionarIndicadoresForm.getFrecuenciasContenidasSeleccionada().toString());
		if (!seleccionarIndicadoresForm.getPermitirCualitativos().booleanValue()) 
			filtros.put("noCualitativos", true);
		if (seleccionarIndicadoresForm.getSoloCompuestos().booleanValue()) 
			filtros.put("soloCompuestos", true);
		if ((seleccionarIndicadoresForm.getExcluirIds() != null) && (!seleccionarIndicadoresForm.getExcluirIds().equals(""))) 
			filtros.put("excluirIds", seleccionarIndicadoresForm.getExcluirIds());
		if (seleccionarIndicadoresForm.getIndicadorId() != null && seleccionarIndicadoresForm.getIndicadorId().longValue() != 0) 
			filtros.put("indicadorId", seleccionarIndicadoresForm.getIndicadorId().toString());

		PaginaLista paginaIndicadores = indicadoresService.getIndicadores(0, 0, seleccionarIndicadoresForm.getAtributoOrden(), seleccionarIndicadoresForm.getTipoOrden(), true, filtros, null, null, true);
		
		if (desdePlanes && agregarSerieMeta)
		{
			for (Iterator<?> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
			{
				Indicador indicador = (Indicador)i.next();
				
				SerieIndicador serieIndicador = new SerieIndicador();
				serieIndicador.setIndicador(indicador);
				serieIndicador.setPk(new SerieIndicadorPK());
				serieIndicador.getPk().setSerieId(new Long(SerieTiempo.getSerieMetaId()));
				serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
				serieIndicador.setFormulas(new HashSet<Object>());
				serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
	
		  		SerieTiempo serieTiempo = new SerieTiempo();
		  		serieTiempo.setNombre(SerieTiempo.getSerieMeta().getNombre());
		  		serieTiempo.setSerieId(new Long(SerieTiempo.getSerieMetaId()));
				serieIndicador.setSerieTiempo(serieTiempo);
	
				indicador.getSeriesIndicador().add(serieIndicador);
			}
		}

		request.setAttribute("paginaIndicadores", paginaIndicadores);

		indicadoresService.close();

		if ((paginaIndicadores.getLista().size() > 0) && (!seleccionarIndicadoresForm.getMostrarSeriesTiempo().booleanValue())) 
		{
			Indicador indicador = (Indicador)paginaIndicadores.getLista().get(0);
			seleccionarIndicadoresForm.setSeleccionados(indicador.getIndicadorId().toString());
			seleccionarIndicadoresForm.setValoresSeleccionados(indicador.getNombre());
		} 
		else 
			seleccionarIndicadoresForm.setSeleccionados(null);

		seleccionarIndicadoresForm.setIniciado(new Boolean(true));

		return mapping.findForward(forward);
	}
}