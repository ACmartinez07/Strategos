package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoCalculoEstadoIniciativa;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.indicadores.forms.SeleccionarIndicadoresForm;
import com.visiongc.app.strategos.web.struts.indicadores.util.SeleccionarIndicadoresIniciativasNodoRoot;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.NodoArbol;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class SeleccionarIndicadoresIniciativasAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarIndicadoresIniciativasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarIndicadoresForm seleccionarIndicadoresForm = (SeleccionarIndicadoresForm)form;

		ActionMessages messages = getMessages(request);

		ArbolBean arbolIniciativasBean = (ArbolBean)request.getSession().getAttribute("seleccionarIndicadoresArbolIniciativasBean");

		if ((arbolIniciativasBean == null) || (!seleccionarIndicadoresForm.getIniciado().booleanValue())) 
		{
			arbolIniciativasBean = new ArbolBean();
			arbolIniciativasBean.clear();
			request.getSession().setAttribute("seleccionarIndicadoresArbolIniciativasBean", arbolIniciativasBean);
		}

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPlanesService);
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService(strategosPlanesService);

		if (arbolIniciativasBean.getNodoSeleccionado() == null) 
			setNodoRoot(seleccionarIndicadoresForm, arbolIniciativasBean, strategosPlanesService, strategosIniciativasService, strategosPryActividadesService);
		else
		{
			SeleccionarIndicadoresIniciativasNodoRoot nodoRoot = (SeleccionarIndicadoresIniciativasNodoRoot)arbolIniciativasBean.getNodoRaiz();
			String seleccionarId = request.getParameter("seleccionarId");
			String abrirId = request.getParameter("abrirId");
			String cerrarId = request.getParameter("cerrarId");
			NodoArbol nodoSeleccionado = null;

			if (request.getAttribute("SeleccionarIndicadoresIniciativasAction.reloadId") != null) 
				nodoSeleccionado = (NodoArbol)arbolIniciativasBean.getNodos().get((String)request.getAttribute("SeleccionarIndicadoresIniciativasAction.reloadId"));
			else if ((seleccionarId != null) && (!seleccionarId.equals(""))) 
				nodoSeleccionado = (NodoArbol)arbolIniciativasBean.getNodos().get(seleccionarId);
			else if ((abrirId != null) && (!abrirId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolIniciativasBean.getNodos().get(abrirId);
				TreeviewWeb.publishArbolAbrirNodo(arbolIniciativasBean, abrirId);
			} 
			else if ((cerrarId != null) && (!cerrarId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolIniciativasBean.getNodos().get(cerrarId);
				TreeviewWeb.publishArbolCerrarNodo(arbolIniciativasBean, cerrarId);
			} 
			else 
				nodoSeleccionado = (NodoArbol)arbolIniciativasBean.getNodos().get(arbolIniciativasBean.getSeleccionadoId());

			boolean eliminado = false;
			if (!nodoSeleccionado.getNodoArbolId().equals("0")) 
				eliminado = strategosPlanesService.load(nodoSeleccionado.getClass(), new Long(nodoSeleccionado.getNodoArbolId())) == null;

			if (eliminado) 
			{
				nodoSeleccionado = setNodoRoot(seleccionarIndicadoresForm, arbolIniciativasBean, strategosPlanesService, strategosIniciativasService, strategosPryActividadesService);
				TreeviewWeb.publishArbol(arbolIniciativasBean, nodoSeleccionado.getNodoArbolId(), true);

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			}
			else if (cerrarId == null) 
				TreeviewWeb.publishArbolAbrirNodo(arbolIniciativasBean, nodoSeleccionado.getNodoArbolId());

			if (!nodoSeleccionado.getNodoArbolId().equals("0")) 
				refrescarNodosArbol(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, nodoSeleccionado, arbolIniciativasBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), strategosIniciativasService, strategosPryActividadesService);

			arbolIniciativasBean.setNodoSeleccionado(nodoSeleccionado);
			arbolIniciativasBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());
			seleccionarIndicadoresForm.setIniciativasNodoSeleccionadoId(arbolIniciativasBean.getSeleccionadoId());
			seleccionarIndicadoresForm.setIniciativasNodoSeleccionado(nodoSeleccionado);
		}

		seleccionarIndicadoresForm.setPanelIndicadores("iniciativas");

		strategosPryActividadesService.close();
		strategosIniciativasService.close();
		strategosPlanesService.close();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private void refrescarNodosArbol(SeleccionarIndicadoresForm seleccionarIndicadoresForm, ArbolBean arbolIniciativasBean, SeleccionarIndicadoresIniciativasNodoRoot nodoRoot, NodoArbol nodoSeleccionado, String nodosAbiertos, String separador, StrategosIniciativasService strategosIniciativasService, StrategosPryActividadesService strategosPryActividadesService)
	{
		if ((nodoSeleccionado instanceof Plan))
			refrescarPlan(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, nodoSeleccionado, strategosIniciativasService, strategosPryActividadesService);
		else if ((nodoSeleccionado instanceof Iniciativa))
			refrescarIniciativa(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, nodoSeleccionado, strategosPryActividadesService);
		else if ((nodoSeleccionado instanceof PryActividad))
			refrescarActividad(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, nodoSeleccionado, strategosPryActividadesService);
	}

	private void refrescarActividad(SeleccionarIndicadoresForm seleccionarIndicadoresForm, ArbolBean arbolIniciativasBean, SeleccionarIndicadoresIniciativasNodoRoot nodoRoot, NodoArbol actividad, StrategosPryActividadesService strategosPryActividadesService)
	{
		if (actividad.getNodoArbolHijosCargados()) 
		{
			for (Iterator<NodoArbol> iter = actividad.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol actividadHija = (NodoArbol)iter.next();
				refrescarActividad(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, actividadHija, strategosPryActividadesService);
			}
		} 
		else 
		{
			actividad.setNodoArbolHijos(strategosPryActividadesService.getActividadesHijasPorPlan(new Long(actividad.getNodoArbolId()), null, "nombre", "asc"));
			actividad.setNodoArbolHijosCargados(true);
			for (Iterator<NodoArbol> iter = actividad.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol actividadHija = (NodoArbol)iter.next();
				actividadHija.setNodoArbolPadre(actividad);
				arbolIniciativasBean.getNodos().put(actividadHija.getNodoArbolId(), actividadHija);
			}
		}
	}

	private void refrescarIniciativa(SeleccionarIndicadoresForm seleccionarIndicadoresForm, ArbolBean arbolIniciativasBean, SeleccionarIndicadoresIniciativasNodoRoot nodoRoot, NodoArbol iniciativa, StrategosPryActividadesService strategosPryActividadesService) 
	{
		if (iniciativa.getNodoArbolHijosCargados() && iniciativa.getNodoArbolHijos() != null) 
		{
			for (Iterator<NodoArbol> iter = iniciativa.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol actividad = (NodoArbol)iter.next();
				refrescarActividad(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, actividad, strategosPryActividadesService);
			}
		} 
		else 
		{
			Iniciativa ini = (Iniciativa) strategosPryActividadesService.load(Iniciativa.class, new Long(iniciativa.getNodoArbolId()));
			Map<String, Object> filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", ini.getProyectoId());
			filtros.put("padreId", null);
			iniciativa.setNodoArbolHijos(strategosPryActividadesService.getActividades(1, 0, "nombre", "asc", true, filtros).getLista());
			iniciativa.setNodoArbolHijosCargados(true);
			for (Iterator<NodoArbol> iter = iniciativa.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol actividad = (NodoArbol)iter.next();
				actividad.setNodoArbolPadre(iniciativa);
				arbolIniciativasBean.getNodos().put(actividad.getNodoArbolId(), actividad);
			}
		}
	}

	private void refrescarPlan(SeleccionarIndicadoresForm seleccionarIndicadoresForm, ArbolBean arbolIniciativasBean, SeleccionarIndicadoresIniciativasNodoRoot nodoRoot, NodoArbol plan, StrategosIniciativasService strategosIniciativasService, StrategosPryActividadesService strategosPryActividadesService) 
	{
		if (plan.getNodoArbolHijosCargados()) 
		{
			for (Iterator<NodoArbol> iter = plan.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol iniciativa = (NodoArbol)iter.next();
				refrescarIniciativa(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, iniciativa, strategosPryActividadesService);
			}
		} 
		else 
		{
			plan.setNodoArbolHijos(strategosIniciativasService.getIniciativasPorPlan(new Long(plan.getNodoArbolId()), TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorActividades(), "nombre", "asc"));
			plan.setNodoArbolHijosCargados(true);
			for (Iterator<NodoArbol> iter = plan.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol iniciativa = (NodoArbol)iter.next();
				iniciativa.setNodoArbolPadre(plan);
				arbolIniciativasBean.getNodos().put(iniciativa.getNodoArbolId(), iniciativa);
			}
		}
	}

	private SeleccionarIndicadoresIniciativasNodoRoot setNodoRoot(SeleccionarIndicadoresForm seleccionarIndicadoresForm, ArbolBean arbolIniciativasBean, StrategosPlanesService strategosPlanesService, StrategosIniciativasService strategosIniciativasService, StrategosPryActividadesService strategosPryActividadesService) throws Exception 
	{
		SeleccionarIndicadoresIniciativasNodoRoot nodoRoot = new SeleccionarIndicadoresIniciativasNodoRoot();
		arbolIniciativasBean.getNodos().put(nodoRoot.getNodoArbolId(), nodoRoot);
		seleccionarIndicadoresForm.setIniciativasNodoSeleccionado(nodoRoot);
		seleccionarIndicadoresForm.setIniciativasNodoSeleccionadoId(nodoRoot.getNodoArbolId());
		
		nodoRoot.setNodoArbolHijos(strategosPlanesService.getPlanesPorOrganizacion(seleccionarIndicadoresForm.getOrganizacionSeleccionadaId(), "nombre", "asc"));
		nodoRoot.setNodoArbolHijosCargados(true);
		for (Iterator<Plan> iter = nodoRoot.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
		{
			Plan plan = (Plan)iter.next();
			arbolIniciativasBean.getNodos().put(plan.getNodoArbolId(), plan);
			refrescarPlan(seleccionarIndicadoresForm, arbolIniciativasBean, nodoRoot, plan, strategosIniciativasService, strategosPryActividadesService);
		}
		
		TreeviewWeb.publishArbol(arbolIniciativasBean, null, nodoRoot.getNodoArbolId(), null, null, true);
    
		arbolIniciativasBean.setNodoRaiz(nodoRoot);
		arbolIniciativasBean.setNodoSeleccionado(nodoRoot);
		arbolIniciativasBean.setSeleccionadoId(nodoRoot.getNodoArbolId());
		
		return nodoRoot;
	}
}