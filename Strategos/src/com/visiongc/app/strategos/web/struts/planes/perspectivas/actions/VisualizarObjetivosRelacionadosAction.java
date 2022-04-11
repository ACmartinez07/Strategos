/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import java.util.Collection;
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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaEstado;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.SeleccionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;

/**
 * @author Kerwin
 *
 */
public class VisualizarObjetivosRelacionadosAction extends VgcAction
{
	public static final String ACTION_KEY = "VisualizarObjetivosRelacionadosAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		if (request.getParameter("accion") != null && request.getParameter("accion").equals("cancelar"))
			return getForwardBack(request, 2, true);
		
		ActionMessages messages = getMessages(request);

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		SeleccionarPerspectivasForm seleccionarPerspectivasForm = (SeleccionarPerspectivasForm)form;
		seleccionarPerspectivasForm.clear();

		String perspectivaId = request.getParameter("perspectivaId") != null ? request.getParameter("perspectivaId") : null;
		seleccionarPerspectivasForm.setOrganizacionSeleccionadaId(new Long((String)request.getSession().getAttribute("organizacionId")));
		
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan(); 
		strategosPlanesService.close();
		
		Perspectiva perspectiva = null;
		if (perspectivaId != null && !perspectivaId.equals("") && !perspectivaId.equals("0"))
		{
			seleccionarPerspectivasForm.setPerspectivaSeleccionadaId(new Long(perspectivaId));
			perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaId));
			if (perspectiva != null)
			{
				perspectiva.setConfiguracionPlan(configuracionPlan);
				
				Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectiva.getPlanId()));
				seleccionarPerspectivasForm.setNombrePlan(plan.getNombre());
				perspectiva.setPlan(plan);

				OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(seleccionarPerspectivasForm.getOrganizacionSeleccionadaId()));
				perspectiva.setOrganizacion(organizacionStrategos);
				perspectiva.setPerspectivasAsociadas(strategosPerspectivasService.getPerspectivasAsociadas(perspectiva.getPerspectivaId()));
				for (Iterator<?> iter = perspectiva.getPerspectivasAsociadas().iterator(); iter.hasNext(); )
				{
					Perspectiva perspectivaAsociada = (Perspectiva)iter.next();
					perspectivaAsociada.setConfiguracionPlan(configuracionPlan);
					if (perspectivaAsociada.getNombre().indexOf(" / ") == -1)
					{
						plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectivaAsociada.getPlanId()));
						OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
						perspectivaAsociada.setPlan(plan);
						perspectivaAsociada.setOrganizacion(organizacion);
						perspectivaAsociada.setNombre(organizacion.getNombre() + " / " + perspectivaAsociada.getNombre());
					}
				}
				
				setRutaCompletaOrganizacion(seleccionarPerspectivasForm, perspectiva.getOrganizacion(), strategosPerspectivasService);
			}
		}

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		ArbolBean arbolRelacionBean = (ArbolBean)request.getSession().getAttribute("gestionarObjetivosRelacionadosArbolBean");
		if (request.getParameter("accion") != null && request.getParameter("accion").equals("refrescar"))
			arbolRelacionBean = null;

		if (arbolRelacionBean == null) 
		{
			arbolRelacionBean = new ArbolBean();
			arbolRelacionBean.clear();
			request.getSession().setAttribute("gestionarObjetivosRelacionadosArbolBean", arbolRelacionBean);
		}

		if (arbolRelacionBean.getNodoSeleccionado() == null) 
			setNodoRoot(request, perspectiva, arbolRelacionBean, strategosPerspectivasService, configuracionPlan);
		else
		{
			String selectedId = request.getParameter("selectedId");
			String openId = request.getParameter("openId");
			String closeId = request.getParameter("closeId");
			
			NodoArbol nodoSeleccionado = null;

			if (request.getAttribute("VisualizarObjetivosRelacionadosAction.reloadId") != null) 
			{
				nodoSeleccionado = (NodoArbol)arbolRelacionBean.getNodos().get((String)request.getAttribute("VisualizarObjetivosRelacionadosAction.reloadId"));
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, nodoSeleccionado.getNodoArbolId());
			} 
			else if ((selectedId != null) && (!selectedId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolRelacionBean.getNodos().get(selectedId);
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, nodoSeleccionado.getNodoArbolId());
			} 
			else if ((openId != null) && (!openId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolRelacionBean.getNodos().get(openId);
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, openId);
			} 
			else if ((closeId != null) && (!closeId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolRelacionBean.getNodos().get(closeId);
				TreeviewWeb.publishArbolCerrarNodo(arbolRelacionBean, closeId);
			} 
			else 
			{
				nodoSeleccionado = (NodoArbol)arbolRelacionBean.getNodos().get(arbolRelacionBean.getSeleccionadoId());
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, nodoSeleccionado.getNodoArbolId());
			}

			if (!refrescarArbol(request, perspectiva, arbolRelacionBean, nodoSeleccionado, strategosPerspectivasService, configuracionPlan))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		}

		arbolesService.close();
		seleccionarPerspectivasForm.setIniciado(new Boolean(true));
		
		strategosPerspectivasService.close();
		
		saveMessages(request, messages);
		
		actualizarAlertas(perspectiva, request);

		return mapping.findForward(forward);
	}
	
	private void setRutaCompletaOrganizacion(SeleccionarPerspectivasForm seleccionarPerspectivasForm, OrganizacionStrategos organizacion, StrategosPerspectivasService strategosPerspectivasService)
	{
		OrganizacionStrategos org = organizacion;
		String rutaCompleta = org.getNombre();
		if (org.getPadreId() != null)
		{
			org.setPadre((OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(org.getPadreId())));
			while (org.getPadre() != null) 
			{
				org = org.getPadre();
				rutaCompleta = org.getNombre() + " / " + rutaCompleta;
			}
		}
    
		seleccionarPerspectivasForm.setRutaCompletaOrganizacion(rutaCompleta);
	}
	
	private void setNodoRoot(HttpServletRequest request, Perspectiva perspectiva, ArbolBean arbolRelacionBean, StrategosPerspectivasService strategosPerspectivasService, ConfiguracionPlan configuracionPlan) throws Exception 
	{
		arbolRelacionBean.setNodoRaiz(perspectiva);
		arbolRelacionBean.getNodos().put(perspectiva.getNodoArbolId(), perspectiva);
		perspectiva.setNodoArbolHijos(perspectiva.getPerspectivasAsociadas());
		perspectiva.setNodoArbolHijosCargados(true);
		perspectiva.setConfiguracionPlan(configuracionPlan);
		for (Iterator<?> iter = perspectiva.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
		{
			Perspectiva perspectivaAsociada = (Perspectiva)iter.next();
			perspectivaAsociada.setPerspectivasAsociadas(strategosPerspectivasService.getPerspectivasAsociadas(perspectivaAsociada.getPerspectivaId()));
			perspectivaAsociada.setConfiguracionPlan(configuracionPlan);
			for (Iterator<?> iterAsociadas = perspectivaAsociada.getPerspectivasAsociadas().iterator(); iterAsociadas.hasNext(); )
			{
				Perspectiva hija = (Perspectiva)iterAsociadas.next();
				hija.setConfiguracionPlan(configuracionPlan);
				if (hija.getNombre().indexOf(" / ") == -1)
				{
					Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(hija.getPlanId()));
					OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
					hija.setPlan(plan);
					hija.setOrganizacion(organizacion);
					hija.setNombre(organizacion.getNombre() + " / " + hija.getNombre());
				}
			}

			arbolRelacionBean.getNodos().put(perspectivaAsociada.getNodoArbolId(), perspectivaAsociada);
			refrescarRelacion(arbolRelacionBean, perspectivaAsociada, perspectivaAsociada.getPerspectivasAsociadas(), strategosPerspectivasService, configuracionPlan);
		}
		TreeviewWeb.publishArbol(arbolRelacionBean, perspectiva.getNodoArbolId(), true);

		request.getSession().setAttribute("perspectiva", perspectiva);
		request.getSession().setAttribute("perspectivaId", perspectiva.getNodoArbolId());
		arbolRelacionBean.setNodoSeleccionado(perspectiva);
		arbolRelacionBean.setSeleccionadoId(perspectiva.getNodoArbolId());
	}
	
	private void refrescarRelacion(ArbolBean arbolRelacionBean, NodoArbol perspectiva, List<Perspectiva> perspectivasAsociadas, StrategosPerspectivasService strategosPerspectivasService, ConfiguracionPlan configuracionPlan)
	{
		List<Perspectiva> nodosHijos = perspectivasAsociadas;
		if (perspectiva.getNodoArbolHijosCargados())
		{
			for (Iterator<?> iter = nodosHijos.iterator(); iter.hasNext(); ) 
			{
				NodoArbol nodoHijo = (NodoArbol)iter.next();
				if (arbolRelacionBean.getNodos().get(nodoHijo.getNodoArbolId()) == null) 
				{
					perspectiva.getNodoArbolHijos().add(nodoHijo);
					arbolRelacionBean.getNodos().put(nodoHijo.getNodoArbolId(), nodoHijo);
				}
			}

			int index = 0;
			while (index < perspectiva.getNodoArbolHijos().size()) 
			{
				NodoArbol perspectivaHija = (NodoArbol)((List<NodoArbol>)perspectiva.getNodoArbolHijos()).get(index);
				if (nodosHijos.contains(perspectivaHija)) 
				{
					Perspectiva perspectivaInsumo = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaHija.getNodoArbolId()));
					perspectivaInsumo.setConfiguracionPlan(configuracionPlan);
					if (perspectivaInsumo.getNombre().indexOf(" / ") == -1)
					{
						Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectivaInsumo.getPlanId()));
						OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
						perspectivaInsumo.setPlan(plan);
						perspectivaInsumo.setOrganizacion(organizacion);
						perspectivaInsumo.setNombre(organizacion.getNombre() + " / " + perspectivaInsumo.getNombre());
					}
					perspectivaInsumo.setPerspectivasAsociadas(strategosPerspectivasService.getPerspectivasAsociadas(perspectivaInsumo.getPerspectivaId()));
					for (Iterator<?> iter = perspectivaInsumo.getPerspectivasAsociadas().iterator(); iter.hasNext(); )
					{
						Perspectiva perspectivaAsociada = (Perspectiva)iter.next();
						perspectivaAsociada.setConfiguracionPlan(configuracionPlan);
						if (perspectivaAsociada.getNombre().indexOf(" / ") == -1)
						{
							Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectivaAsociada.getPlanId()));
							OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
							perspectivaAsociada.setPlan(plan);
							perspectivaAsociada.setOrganizacion(organizacion);
							perspectivaAsociada.setNombre(organizacion.getNombre() + " / " + perspectivaAsociada.getNombre());
						}
					}
					
					refrescarRelacion(arbolRelacionBean, perspectivaHija, perspectivaInsumo.getPerspectivasAsociadas(), strategosPerspectivasService, configuracionPlan);
					index++;
				} 
				else 
					((List<NodoArbol>)perspectiva.getNodoArbolHijos()).remove(index);
			}
		} 
		else 
		{
			perspectiva.setNodoArbolHijos(nodosHijos);
			perspectiva.setNodoArbolHijosCargados(true);
			for (Iterator<NodoArbol> iter = perspectiva.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol perspectivaHija = (NodoArbol)iter.next();
				perspectivaHija.setNodoArbolPadre(perspectiva);
				arbolRelacionBean.getNodos().put(perspectivaHija.getNodoArbolId(), perspectivaHija);
			}
		}
	}
	
	private boolean refrescarArbol(HttpServletRequest request, Perspectiva perspectiva, ArbolBean arbolRelacionBean, NodoArbol nodoSeleccionado, StrategosPerspectivasService strategosPerspectivasService, ConfiguracionPlan configuracionPlan) throws Exception 
	{
		boolean rootCargado = false;
		NodoArbol nodoActualizado = (NodoArbol)strategosPerspectivasService.load(Perspectiva.class, new Long(nodoSeleccionado.getNodoArbolId()));
		if (nodoActualizado == null || nodoActualizado.getNodoArbolId().equals(perspectiva.getPerspectivaId().toString())) 
		{
			Long perspectivaId = perspectiva.getPerspectivaId();
			perspectiva = null;
			perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaId));
			if (perspectiva != null)
			{
				rootCargado = true;
				perspectiva.setConfiguracionPlan(configuracionPlan);
				Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectiva.getPlanId()));
				perspectiva.setPlan(plan);

				OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
				perspectiva.setOrganizacion(organizacionStrategos);
				
				perspectiva.setPerspectivasAsociadas(strategosPerspectivasService.getPerspectivasAsociadas(perspectiva.getPerspectivaId()));
				for (Iterator<?> iter = perspectiva.getPerspectivasAsociadas().iterator(); iter.hasNext(); )
				{
					Perspectiva perspectivaAsociada = (Perspectiva)iter.next();
					perspectivaAsociada.setConfiguracionPlan(configuracionPlan);

					if (perspectivaAsociada.getNombre().indexOf(" / ") == -1)
					{
						plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectivaAsociada.getPlanId()));
						OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
						perspectivaAsociada.setPlan(plan);
						perspectivaAsociada.setOrganizacion(organizacion);
						perspectivaAsociada.setNombre(organizacion.getNombre() + " / " + perspectivaAsociada.getNombre());
					}
				}
			}

			setNodoRoot(request, perspectiva, arbolRelacionBean, strategosPerspectivasService, configuracionPlan);
			if (nodoActualizado == null)
				return false;
		}

		Perspectiva perspectivaHija = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(nodoSeleccionado.getNodoArbolId()));
		if (!nodoActualizado.getNodoArbolId().equals(perspectiva.getPerspectivaId().toString()) && perspectivaHija.getNombre().indexOf(" / ") == -1)
		{
			Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectivaHija.getPlanId()));
			OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
			perspectivaHija.setConfiguracionPlan(configuracionPlan);
			perspectivaHija.setPlan(plan);
			perspectivaHija.setOrganizacion(organizacion);
			perspectivaHija.setNombre(organizacion.getNombre() + " / " + perspectivaHija.getNombre());
		}

		nodoSeleccionado.setNodoArbolNombre(nodoActualizado.getNodoArbolNombre());
		if (!rootCargado)
		{
			perspectivaHija.setPerspectivasAsociadas(strategosPerspectivasService.getPerspectivasAsociadas(perspectivaHija.getPerspectivaId()));
			for (Iterator<?> iter = perspectivaHija.getPerspectivasAsociadas().iterator(); iter.hasNext(); )
			{
				Perspectiva perspectivaAsociada = (Perspectiva)iter.next();
				perspectivaAsociada.setConfiguracionPlan(configuracionPlan);
				if (perspectivaAsociada.getNombre().indexOf(" / ") == -1)
				{
					Plan plan = (Plan)strategosPerspectivasService.load(Plan.class, new Long(perspectivaAsociada.getPlanId()));
					OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPerspectivasService.load(OrganizacionStrategos.class, new Long(plan.getOrganizacionId()));
					perspectivaAsociada.setPlan(plan);
					perspectivaAsociada.setOrganizacion(organizacion);
					perspectivaAsociada.setNombre(organizacion.getNombre() + " / " + perspectivaAsociada.getNombre());
				}
			}
		}
		refrescarRelacion(arbolRelacionBean, nodoSeleccionado, perspectivaHija.getPerspectivasAsociadas(), strategosPerspectivasService, configuracionPlan);

		request.getSession().setAttribute("perspectiva", nodoSeleccionado);
		request.getSession().setAttribute("perspectivaId", nodoSeleccionado.getNodoArbolId());
		arbolRelacionBean.setNodoSeleccionado(nodoSeleccionado);
		arbolRelacionBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());

		return true;
	}
	
	private void actualizarAlertas(Perspectiva raiz, HttpServletRequest request)
	{
		Map<String, String> perspectivaIds = new HashMap<String, String>();
		getIds(raiz.getNodoArbolHijos(), perspectivaIds);
		
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, raiz.getPlanId());
		List<PerspectivaEstado> estados = strategosPerspectivasService.getPerspectivaEstadosUltimoPeriodo(plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), plan.getAnoFinal());
		Map<Long, Byte> alertasParcial = new HashMap<Long, Byte>();
		Map<Long, Double> nivelParcial = new HashMap<Long, Double>();
		for (Iterator<PerspectivaEstado> iter = estados.iterator(); iter.hasNext(); ) 
		{
			PerspectivaEstado estado = (PerspectivaEstado)iter.next();
			if (estado.getPk().getTipo().byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial())
			{
				alertasParcial.put(estado.getPk().getPerspectivaId(), estado.getAlerta());
				nivelParcial.put(estado.getPk().getPerspectivaId(), estado.getEstado());
			}
		}
		
		Map<Long, Byte> alertasAnual = new HashMap<Long, Byte>();
		Map<Long, Double> nivelAnual = new HashMap<Long, Double>();
		for (Iterator<PerspectivaEstado> iter = estados.iterator(); iter.hasNext(); ) 
		{
			PerspectivaEstado estado = (PerspectivaEstado)iter.next();
			if (estado.getPk().getTipo().byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual())
			{
				alertasAnual.put(estado.getPk().getPerspectivaId(), estado.getAlerta());
				nivelAnual.put(estado.getPk().getPerspectivaId(), estado.getEstado());
			}
		}
		strategosPerspectivasService.close();
		
		new com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.GestionarPerspectivasAction().actualizarAlertas(raiz.getNodoArbolHijos(), alertasParcial, nivelParcial, TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
		new com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.GestionarPerspectivasAction().actualizarAlertas(raiz.getNodoArbolHijos(), alertasAnual, nivelAnual, TipoIndicadorEstado.getTipoIndicadorEstadoAnual());
	}

	private void getIds(Collection<NodoArbol> nodos, Map<String, String> ids) 
	{
		if (nodos != null)
		{
			for (Iterator<NodoArbol> iter = nodos.iterator(); iter.hasNext(); ) 
			{
				NodoArbol nodo = (NodoArbol)iter.next();
				ids.put(nodo.getNodoArbolId(), nodo.getNodoArbolId());
				getIds(nodo.getNodoArbolHijos(), ids);
			}
		}
	}
}