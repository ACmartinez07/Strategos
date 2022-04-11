package com.visiongc.app.strategos.web.struts.planes.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.indicadores.forms.GestionarIndicadoresPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.GestionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarIndicadoresPlanAction extends VgcAction
{
	private PaginaLista paginaIndicadores = null;

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		String forward = mapping.getParameter();

		GestionarIndicadoresPlanForm gestionarIndicadoresPlanForm = (GestionarIndicadoresPlanForm)form;
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)request.getSession().getAttribute("gestionarPlanForm");
		GestionarPerspectivasForm gestionarPerspectivasForm = (GestionarPerspectivasForm)request.getSession().getAttribute("gestionarPerspectivasForm");
		gestionarIndicadoresPlanForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL"));
		gestionarIndicadoresPlanForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT"));
		Boolean actualizarForma = request.getSession().getAttribute("actualizarForma") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarForma")) : false;
		if (!actualizarForma)
		{
			actualizarForma = request.getSession().getAttribute("GuardarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("GuardarIndicador")) : false;
			if (request.getSession().getAttribute("GuardarIndicador") != null)
				request.getSession().removeAttribute("GuardarIndicador");
			if (!actualizarForma)
			{
				actualizarForma = request.getSession().getAttribute("AsociarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("AsociarIndicador")) : false;
				if (request.getSession().getAttribute("AsociarIndicador") != null)
					request.getSession().removeAttribute("AsociarIndicador");
			}
			if (!actualizarForma)
			{
				actualizarForma = request.getSession().getAttribute("DesAsociarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("DesAsociarIndicador")) : false;
				if (request.getSession().getAttribute("DesAsociarIndicador") != null)
					request.getSession().removeAttribute("DesAsociarIndicador");
			}
		}

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Perspectiva perspectiva = (Perspectiva)strategosIndicadoresService.load(Perspectiva.class, gestionarPlanForm.getPerspectivaId());

		if (perspectiva != null) 
		{
			gestionarIndicadoresPlanForm.setNombreIndicadorPlural(perspectiva.getPlan().getMetodologia().getNombreIndicadorPlural());
			gestionarIndicadoresPlanForm.setNombreIndicadorSingular(perspectiva.getPlan().getMetodologia().getNombreIndicadorSingular());
		}

		String atributoOrden = gestionarIndicadoresPlanForm.getAtributoOrden();
		String tipoOrden = gestionarIndicadoresPlanForm.getTipoOrden();
		int pagina = gestionarIndicadoresPlanForm.getPagina();

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarIndicadoresPlanForm.setAtributoOrden(atributoOrden);
		}
		
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarIndicadoresPlanForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		Map<String, Object> filtros = new HashMap<String, Object>();
		if (perspectiva.getPadreId() != null) 
			filtros.put("perspectivaId", gestionarPlanForm.getPerspectivaId().toString());
		else if (perspectiva.getPadreId() == null)
		{
			if (gestionarPerspectivasForm.getVerIndicadoresLogroPlan())
				filtros.put("indicadoresLogroPlanId", gestionarPlanForm.getPlanId().toString());
			else 
				filtros.put("indicadoresLogroPerspectivasPrincipalesPlanId", gestionarPlanForm.getPlanId().toString());
		}

		Integer totalPaginas = 0;
		pagina = 0;
		if (paginaIndicadores != null && paginaIndicadores.getFiltros() != null)
		{
			if (!paginaIndicadores.samePage(pagina, totalPaginas, atributoOrden, tipoOrden, filtros))
				paginaIndicadores = null;
		}
		if (actualizarForma)
		{
			paginaIndicadores = null;
			request.getSession().removeAttribute("actualizarForma");
		}
		
		boolean actualizarIndicadores = false;
		if (paginaIndicadores == null)
		{
			Plan plan = (Plan)strategosIndicadoresService.load(Plan.class, gestionarPlanForm.getPlanId());
			if (gestionarPerspectivasForm.getVerIndicadoresLogroPlan())
				paginaIndicadores = strategosIndicadoresService.getIndicadoresLogroPlan(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros);
			else 
				paginaIndicadores = strategosIndicadoresService.getIndicadores(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros, plan.getAnoInicial(), plan.getAnoFinal(), true);
			paginaIndicadores.setFiltros(filtros);
			paginaIndicadores.setNroPagina(pagina);
			paginaIndicadores.setTamanoPagina(totalPaginas);
			paginaIndicadores.setOrden(atributoOrden);
			paginaIndicadores.setTipoOrden(tipoOrden);
			
			actualizarIndicadores = true;
		}

		if (!gestionarPerspectivasForm.getVerIndicadoresLogroPlan() && actualizarIndicadores)
		{
			if (!perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
			{
				int numeroVeces = paginaIndicadores.getLista().size();
				for (int k = 1; k <= numeroVeces; k++) 
				{
					for (Iterator<?> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
					{
						boolean eliminarIndicador = false;
						Indicador indicador = (Indicador)i.next();
						for (Iterator<?> p = perspectiva.getHijos().iterator(); p.hasNext(); ) 
						{
							Perspectiva perspectivaHija = (Perspectiva)p.next();
							if ((indicador.getIndicadorId().longValue() == perspectivaHija.getNlAnoIndicadorId().longValue()) || (indicador.getIndicadorId().longValue() == perspectivaHija.getNlParIndicadorId().longValue())) 
							{
								eliminarIndicador = true;
								break;
							}
						}
						
						if (eliminarIndicador) 
						{
							paginaIndicadores.getLista().remove(indicador);
							break;
						}
					}
				}
			}
		}

		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(strategosMetasService);
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();

		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		gestionarIndicadoresPlanForm.setConfiguracionPlan(configuracionPlan);
		
		if (actualizarIndicadores)
		{
			for (Iterator<?> iter = paginaIndicadores.getLista().iterator(); iter.hasNext(); ) 
			{
				Indicador indicador = (Indicador)iter.next();
				if (indicador.getFechaUltimaMedicion() != null) 
				{
					List<?> metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), gestionarPlanForm.getPlanId(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), false);
					if (metas.size() > 0) 
						indicador.setMetaAnual(((Meta)metas.get(0)).getValor());
					metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), gestionarPlanForm.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (metas.size() > 0) 
					{
						Meta metaParcial = (Meta)metas.get(0);
						indicador.setMetaParcial(metaParcial.getValor());
					}

					List<?> estados = strategosPlanesService.getIndicadorEstados(indicador.getIndicadorId(), gestionarPlanForm.getPlanId(), indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (estados.size() > 0) 
					{
						IndicadorEstado indEstado = (IndicadorEstado)estados.get(0);
						indicador.setEstadoParcial(indEstado.getEstado());
					}
					estados = strategosPlanesService.getIndicadorEstados(indicador.getIndicadorId(), gestionarPlanForm.getPlanId(), indicador.getFrecuencia(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (estados.size() > 0) 
					{
						IndicadorEstado indEstado = (IndicadorEstado)estados.get(0);
						indicador.setEstadoAnual(indEstado.getEstado());
					}
					
					if (indicador.getUltimaMedicion() != null && (indicador.getMetaParcial() != null || indicador.getUltimaMedicionAnoAnterior() != null))
					{
						Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), (indicador.getMetaParcial() != null ? indicador.getMetaParcial() : indicador.getUltimaMedicionAnoAnterior()), strategosMedicionesService);
	  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionPeriodo(), (indicador.getMetaParcial() != null ? indicador.getMetaParcial() : indicador.getUltimaMedicionAnoAnterior()), zonaVerde, strategosMedicionesService);
	  					Byte alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, indicador.getUltimaMedicion(), indicador.getMetaParcial(), null, indicador.getUltimaMedicionAnoAnterior());
						indicador.setAlerta(alerta);
					}
				}
				IndicadorPerspectivaPK indicadorPerspectivaPk = new IndicadorPerspectivaPK();
				indicadorPerspectivaPk.setIndicadorId(indicador.getIndicadorId());
				indicadorPerspectivaPk.setPerspectivaId(gestionarPlanForm.getPerspectivaId());
				IndicadorPerspectiva indicadorPerspectiva = (IndicadorPerspectiva)strategosIndicadoresService.load(IndicadorPerspectiva.class, indicadorPerspectivaPk);
				if (indicadorPerspectiva != null) 
					indicador.setPeso(indicadorPerspectiva.getPeso());
			}
		}

		strategosPlanesService.close();
		strategosMetasService.close();
		strategosMedicionesService.close();

		paginaIndicadores.setTotal(paginaIndicadores.getLista().size());
		paginaIndicadores.setTamanoSetPaginas(5);

		request.setAttribute("paginaIndicadores", paginaIndicadores);
		strategosIndicadoresService.close();

		if (paginaIndicadores.getLista().size() > 0) 
		{
			Indicador indicador = (Indicador)paginaIndicadores.getLista().get(0);
			gestionarIndicadoresPlanForm.setSeleccionados(indicador.getIndicadorId().toString());
			gestionarIndicadoresPlanForm.setValoresSeleccionados(indicador.getNombre());
		} 
		else 
			gestionarIndicadoresPlanForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}
}