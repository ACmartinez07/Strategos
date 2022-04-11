package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.web.struts.indicadores.forms.SeleccionarIndicadoresForm;
import com.visiongc.app.strategos.web.struts.indicadores.util.SeleccionarIndicadoresPlanesNodoRoot;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.NodoArbol;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class SeleccionarIndicadoresPlanesAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarIndicadoresPlanesAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarIndicadoresForm seleccionarIndicadoresForm = (SeleccionarIndicadoresForm)form;

		ActionMessages messages = getMessages(request);

		ArbolBean arbolPlanesBean = (ArbolBean)request.getSession().getAttribute("seleccionarIndicadoresArbolPlanesBean");

		if ((arbolPlanesBean == null) || (!seleccionarIndicadoresForm.getIniciado().booleanValue())) 
		{
			arbolPlanesBean = new ArbolBean();
			arbolPlanesBean.clear();
			request.getSession().setAttribute("seleccionarIndicadoresArbolPlanesBean", arbolPlanesBean);
			seleccionarIndicadoresForm.setPlanId(null);
		}

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService(strategosPlanesService);

		if (arbolPlanesBean.getNodoSeleccionado() == null) 
		{
			setNodoRoot(seleccionarIndicadoresForm, arbolPlanesBean, strategosPlanesService, strategosPerspectivasService);
			seleccionarIndicadoresForm.setPlanId(null);
		}
		else
		{
			SeleccionarIndicadoresPlanesNodoRoot nodoRoot = (SeleccionarIndicadoresPlanesNodoRoot)arbolPlanesBean.getNodoRaiz();
			String seleccionarId = request.getParameter("seleccionarId");
			String abrirId = request.getParameter("abrirId");
			String cerrarId = request.getParameter("cerrarId");
			NodoArbol nodoSeleccionado = null;
			
			if (request.getAttribute("SeleccionarIndicadoresPlanesAction.reloadId") != null) 
				nodoSeleccionado = (NodoArbol)arbolPlanesBean.getNodos().get((String)request.getAttribute("SeleccionarIndicadoresPlanesAction.reloadId"));
			else if ((seleccionarId != null) && (!seleccionarId.equals(""))) 
				nodoSeleccionado = (NodoArbol)arbolPlanesBean.getNodos().get(seleccionarId);
			else if ((abrirId != null) && (!abrirId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolPlanesBean.getNodos().get(abrirId);
				TreeviewWeb.publishArbolAbrirNodo(arbolPlanesBean, abrirId);
			} 
			else if ((cerrarId != null) && (!cerrarId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolPlanesBean.getNodos().get(cerrarId);
				TreeviewWeb.publishArbolCerrarNodo(arbolPlanesBean, cerrarId);
			} 
			else 
				nodoSeleccionado = (NodoArbol)arbolPlanesBean.getNodos().get(arbolPlanesBean.getSeleccionadoId());

			boolean eliminado = false;
			if (!nodoSeleccionado.getNodoArbolId().equals("0")) 
				eliminado = strategosPlanesService.load(nodoSeleccionado.getClass(), new Long(nodoSeleccionado.getNodoArbolId())) == null;
			if (eliminado) 
			{
				nodoSeleccionado = setNodoRoot(seleccionarIndicadoresForm, arbolPlanesBean, strategosPlanesService, strategosPerspectivasService);
				TreeviewWeb.publishArbol(arbolPlanesBean, nodoSeleccionado.getNodoArbolId(), true);

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				String reloadId = nodoSeleccionado.getNodoArbolId();
				if (cerrarId == null) 
					TreeviewWeb.publishArbolAbrirNodo(arbolPlanesBean, reloadId);
			}

			if (!nodoSeleccionado.getNodoArbolId().equals("0")) 
				refrescarNodosArbol(arbolPlanesBean, nodoRoot, nodoSeleccionado, arbolPlanesBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), strategosPerspectivasService, strategosPlanesService);

			arbolPlanesBean.setNodoSeleccionado(nodoSeleccionado);
			arbolPlanesBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());
			seleccionarIndicadoresForm.setPlanesNodoSeleccionadoId(arbolPlanesBean.getSeleccionadoId());
			seleccionarIndicadoresForm.setPlanesNodoSeleccionado(nodoSeleccionado);
			Plan plan = (Plan) strategosPlanesService.load(Plan.class, new Long(nodoSeleccionado.getNodoArbolId()));
			Perspectiva perspectiva = (Perspectiva) strategosPlanesService.load(Perspectiva.class, new Long(nodoSeleccionado.getNodoArbolId()));
			if (plan != null && plan.getPlanId() != null)
				seleccionarIndicadoresForm.setPlanId(plan.getPlanId());
			else if (perspectiva != null && perspectiva.getPlanId() != null)
				seleccionarIndicadoresForm.setPlanId(perspectiva.getPlanId());
		}

		seleccionarIndicadoresForm.setPanelIndicadores("planes");

		strategosPerspectivasService.close();
		strategosPlanesService.close();
		
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private void refrescarNodosArbol(ArbolBean arbolPlanesBean, SeleccionarIndicadoresPlanesNodoRoot nodoRoot, NodoArbol nodoSeleccionado, String nodosAbiertos, String separador, StrategosPerspectivasService strategosPerspectivasService, StrategosPlanesService strategosPlanesService)
	{
		if (nodoSeleccionado.getClass().getSimpleName().equals("Perspectiva"))
		{
			ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan();
			refrescar(arbolPlanesBean, nodoRoot, nodoSeleccionado, configuracionPlan, strategosPerspectivasService);
		}
	}

	private void refrescar(ArbolBean arbolPlanesBean, SeleccionarIndicadoresPlanesNodoRoot nodoRoot, NodoArbol perspectiva, ConfiguracionPlan configuracionPlan, StrategosPerspectivasService strategosPerspectivasService) 
	{
		if (perspectiva.getNodoArbolHijosCargados()) 
		{
			for (Iterator<NodoArbol> iter = perspectiva.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol perspectivaHijo = (NodoArbol)iter.next();
				refrescar(arbolPlanesBean, nodoRoot, perspectivaHijo, configuracionPlan, strategosPerspectivasService);
			}
		} 
		else 
		{
			Map<String, Object> filtros = new HashMap<String, Object>();
			String[] orden = new String[2];
			String[] tipoOrden = new String[2];
			orden[0] = "tipo";
			orden[1] = "nombre";
			tipoOrden[0] = "desc";
			tipoOrden[1] = "asc";

			filtros.put("padreId", perspectiva.getNodoArbolId());
			List<Perspectiva> nodosHijos = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
				
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
				
			perspectiva.setNodoArbolHijos(nodosHijos);
			perspectiva.setNodoArbolHijosCargados(true);
			for (Iterator<Perspectiva> iter = nodosHijos.iterator(); iter.hasNext();) 
			{
				NodoArbol nodo = (NodoArbol)iter.next();
				
				Perspectiva perspectivaHijos = (Perspectiva)nodo; 
				new com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.GestionarPerspectivasAction().setConfiguracion(perspectivaHijos, configuracionPlan, null, strategosIndicadoresService, strategosMedicionesService, strategosMetasService);
				
				arbolPlanesBean.getNodos().put(nodo.getNodoArbolId(), nodo);
			}
				
			strategosIndicadoresService.close();
			strategosMedicionesService.close();
			strategosMetasService.close();
		}
	}

	private SeleccionarIndicadoresPlanesNodoRoot setNodoRoot(SeleccionarIndicadoresForm seleccionarIndicadoresForm, ArbolBean arbolPlanesBean, StrategosPlanesService strategosPlanesService, StrategosPerspectivasService strategosPerspectivasService) throws Exception
	{
		SeleccionarIndicadoresPlanesNodoRoot nodoRoot = new SeleccionarIndicadoresPlanesNodoRoot();
		arbolPlanesBean.getNodos().put(nodoRoot.getNodoArbolId(), nodoRoot);
		seleccionarIndicadoresForm.setPlanesNodoSeleccionado(nodoRoot);
		seleccionarIndicadoresForm.setPlanesNodoSeleccionadoId(nodoRoot.getNodoArbolId());
		
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put("organizacionId", seleccionarIndicadoresForm.getOrganizacionSeleccionadaId().toString());
		List<Plan> planes = strategosPlanesService.getPlanes(0, 0, "nombre", "asc", false, filtros).getLista();
		List<Perspectiva> perspectivas = new ArrayList<Perspectiva>();
		for (Iterator<Plan> iter = planes.iterator(); iter.hasNext(); ) 
		{
			Plan plan = (Plan)iter.next();

			String[] orden = new String[1];
			String[] tipoOrden = new String[1];
			orden[0] = "nombre";
			tipoOrden[0] = "asc";

			filtros = new HashMap<String, Object>();
			filtros.put("padreId", null);
			filtros.put("ano", null);
			filtros.remove("planId");
			filtros.put("planId", plan.getNodoArbolId());
			List<Perspectiva> perspectivasDummy = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
			if (perspectivasDummy.size() > 0)
			{
				// Setear Valores al nodo Padre
				Perspectiva perspectiva = perspectivasDummy.get(0);
				
				Map<String, String> perspectivaIds = new HashMap<String, String>();
				perspectivaIds.put(perspectiva.getNodoArbolId(), perspectiva.getNodoArbolId());
				Map<Long, Byte> alertasParcialPadre = strategosPerspectivasService.getAlertasPerspectivas(perspectivaIds, TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
				Map<Long, Byte> alertasAnualPadre = strategosPerspectivasService.getAlertasPerspectivas(perspectivaIds, TipoIndicadorEstado.getTipoIndicadorEstadoAnual());
				perspectiva.setAlertaParcial((Byte)alertasParcialPadre.get(perspectiva.getPerspectivaId()));
				perspectiva.setAlertaAnual((Byte)alertasAnualPadre.get(perspectiva.getPerspectivaId()));
				perspectiva.setConfiguracionPlan(configuracionPlan);
				
				perspectiva.setUltimaMedicionParcial(plan.getUltimaMedicionParcial());
				perspectiva.setUltimaMedicionAnual(plan.getUltimaMedicionAnual());
				
				perspectivas.add(perspectiva);
			}
			else
			{
				Perspectiva perspectiva = new Perspectiva();
				perspectiva.setPerspectivaId(plan.getPlanId());
				perspectiva.setNombre(plan.getNombre());
				
				perspectivas.add(perspectiva);
			}
		}
		
		if (perspectivas.size() > 0)
		{
			nodoRoot.setNodoArbolHijos(perspectivas);
			nodoRoot.setNodoArbolHijosCargados(true);
			for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext(); ) 
			{
				Perspectiva perspectiva = (Perspectiva)iter.next();
				arbolPlanesBean.getNodos().put(perspectiva.getNodoArbolId(), perspectiva);
				
				refrescar(arbolPlanesBean, nodoRoot, perspectiva, configuracionPlan, strategosPerspectivasService);
			}
		}

		TreeviewWeb.publishArbol(arbolPlanesBean, null, nodoRoot.getNodoArbolId(), null, null, true);

		arbolPlanesBean.setNodoRaiz(nodoRoot);
		arbolPlanesBean.setNodoSeleccionado(nodoRoot);
		arbolPlanesBean.setSeleccionadoId(nodoRoot.getNodoArbolId());

		return nodoRoot;
	}
}