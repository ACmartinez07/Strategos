package com.visiongc.app.strategos.web.struts.planes.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.web.struts.planes.indicadores.forms.AsignarPesosIndicadoresPlanForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class AsignarPesosIndicadoresPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("cancelar")) 
			{
				request.setAttribute("ajaxResponse", "");
				return mapping.findForward("ajaxResponse");
			}
		}

		AsignarPesosIndicadoresPlanForm asignarPesosIndicadoresPlanForm = (AsignarPesosIndicadoresPlanForm)form;

		ActionMessages messages = getMessages(request);

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, asignarPesosIndicadoresPlanForm.getPerspectivaId());

		if (perspectiva != null)
			perspectiva.getHijos().size();

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("guardar")) 
			{
				request.setAttribute("cerrarAsignarPesosIndicadoresPlan", "true");
				guardarPesosIndicadoresPlan(perspectiva, strategosIndicadoresService, asignarPesosIndicadoresPlanForm, request);
			}
		}
		else if (request.getParameter("funcionCierre") == null) 
			asignarPesosIndicadoresPlanForm.setFuncionCierre(null);
		
		asignarPesosIndicadoresPlanForm.setOrganizacionNombre(perspectiva.getPlan().getOrganizacion().getNombre());
		asignarPesosIndicadoresPlanForm.setPlanNombre(perspectiva.getPlan().getNombre());
		asignarPesosIndicadoresPlanForm.setPerspectivaNombre(perspectiva.getNombre());

		Map<String, Object> filtros = new HashMap<String, Object>();

		if ((request.getParameter("perspectivaId") != null) && (!request.getParameter("perspectivaId").equals(""))) 
			filtros.put("perspectivaId", asignarPesosIndicadoresPlanForm.getPerspectivaId());

		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("guardar")) 
				filtros.put("perspectivaId", asignarPesosIndicadoresPlanForm.getPerspectivaId());
		}
		
		/*
		if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
			filtros.put("tipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
		else 
			filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
		*/
		
		//filtros.put("tipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());		


		List<String> orderBy = new ArrayList<String>();
		orderBy.add("asc");
		orderBy.add("nombre");
		filtros.put("orderBy", orderBy);

		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(filtros);

		if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
		{
			String logroParcial = VgcResourceManager.getMessageResources("Strategos").getResource("indicador.logroparcial");
			
			Map<Long, Long> ids = new HashMap<Long, Long>();
			for (Iterator<?> iter = perspectiva.getHijos().iterator(); iter.hasNext(); ) 
			{
				Perspectiva perspectivaHija = (Perspectiva)iter.next();
				ids.put(perspectivaHija.getNlParIndicadorId(), perspectivaHija.getNlParIndicadorId());
			}

			List<Indicador> indicadoresTemp = new ArrayList<Indicador>();
			for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext(); ) 
			{
				Indicador indicador = iter.next();

				if (ids.containsKey(indicador.getIndicadorId())) 
				{
					Indicador indicadorNuevo = new Indicador();
					int index = indicador.getNombre().indexOf("... (" + logroParcial + ")");
					indicadorNuevo.setIndicadorId(indicador.getIndicadorId());
					indicadorNuevo.setNombre(indicador.getNombre().substring(0, index));
					indicadoresTemp.add(indicadorNuevo);
				}
			}
			indicadores = indicadoresTemp;
		}

		for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext(); ) 
		{
			Indicador indicador = iter.next();
			IndicadorPerspectivaPK indicadorPerspectivaPk = new IndicadorPerspectivaPK();
			indicadorPerspectivaPk.setIndicadorId(indicador.getIndicadorId());
			indicadorPerspectivaPk.setPerspectivaId(asignarPesosIndicadoresPlanForm.getPerspectivaId());
			IndicadorPerspectiva indicadorPerspectiva = (IndicadorPerspectiva)strategosIndicadoresService.load(IndicadorPerspectiva.class, indicadorPerspectivaPk);
			if (indicadorPerspectiva != null) 
				indicador.setPeso(indicadorPerspectiva.getPeso());
		}

		PaginaLista paginaIndicadores = new PaginaLista();

		paginaIndicadores.setLista(indicadores);
		paginaIndicadores.setNroPagina(1);
		paginaIndicadores.setTamanoPagina(indicadores.size());
		paginaIndicadores.setTamanoSetPaginas(1);
		paginaIndicadores.setTotal(indicadores.size());

		request.setAttribute("asignarPesosIndicadoresPlan.paginaIndicadores", paginaIndicadores);

		saveMessages(request, messages);

		strategosIndicadoresService.close();

		return mapping.findForward(forward);
	}

	private void guardarPesosIndicadoresPlan(Perspectiva perspectiva, StrategosIndicadoresService strategosIndicadoresService, AsignarPesosIndicadoresPlanForm asignarPesosIndicadoresPlanForm, HttpServletRequest request) throws Exception
	{
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(strategosIndicadoresService);
		
		Map<?, ?> nombresParametros = request.getParameterMap();
		List<IndicadorPerspectiva> indicadoresPerspectiva = new ArrayList<IndicadorPerspectiva>();
		for (Iterator<?> iter = nombresParametros.keySet().iterator(); iter.hasNext(); ) 
		{
			String nombre = (String)iter.next();
			int index = nombre.indexOf("pesoIndicador");
			if (index > -1) 
			{
				IndicadorPerspectiva indicadorPerspectiva = new IndicadorPerspectiva();
				indicadorPerspectiva.setPk(new IndicadorPerspectivaPK());
				indicadorPerspectiva.getPk().setIndicadorId(new Long(nombre.substring("pesoIndicador".length())));
				indicadorPerspectiva.getPk().setPerspectivaId(asignarPesosIndicadoresPlanForm.getPerspectivaId());
				if (indicadorPerspectiva.getPk().getPerspectivaId() == null) 
					indicadorPerspectiva.getPk().setPerspectivaId(asignarPesosIndicadoresPlanForm.getObjetivoId());

				if ((request.getParameter(nombre) != null) && (!request.getParameter(nombre).equals(""))) 
					indicadorPerspectiva.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(request.getParameter(nombre))));

				indicadoresPerspectiva.add(indicadorPerspectiva);
				if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico())) 
				{
					for (Iterator<?> iterHija = perspectiva.getHijos().iterator(); iterHija.hasNext(); ) 
					{
						Perspectiva perspectivaHija = (Perspectiva)iterHija.next();
						if (indicadorPerspectiva.getPk().getIndicadorId().equals(perspectivaHija.getNlParIndicadorId())) 
						{
							IndicadorPerspectiva indicadorPerspectivaAnual = new IndicadorPerspectiva();
							indicadorPerspectivaAnual.setPk(new IndicadorPerspectivaPK());
							indicadorPerspectivaAnual.getPk().setIndicadorId(perspectivaHija.getNlAnoIndicadorId());
							indicadorPerspectivaAnual.getPk().setPerspectivaId(asignarPesosIndicadoresPlanForm.getPerspectivaId());
							indicadorPerspectivaAnual.setPeso(indicadorPerspectiva.getPeso());
							indicadoresPerspectiva.add(indicadorPerspectivaAnual);
							break;
						}
					}
				}
			}
		}
		strategosPerspectivasService.updatePesosIndicadoresPerspectiva(indicadoresPerspectiva, getUsuarioConectado(request));

		strategosPerspectivasService.close();
	}
}