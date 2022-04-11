/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.actions;

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
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.RelacionIniciativaForm;

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
public class VisualizarIniciativasRelacionadasAction extends VgcAction 
{
	public static final String ACTION_KEY = "VisualizarIniciativasRelacionadasAction";

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

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		RelacionIniciativaForm relacionIniciativaForm = (RelacionIniciativaForm) form;
		relacionIniciativaForm.clear();

		String iniciativaId = request.getParameter("iniciativaId") != null ? request.getParameter("iniciativaId") : null;
		relacionIniciativaForm.setOrganizacionSeleccionadaId(new Long((String) request.getSession().getAttribute("organizacionId")));

		Iniciativa iniciativa = null;
		if (iniciativaId != null && !iniciativaId.equals("") && !iniciativaId.equals("0")) 
		{
			relacionIniciativaForm.setIniciativaSeleccionadaId(new Long(iniciativaId));
			iniciativa = (Iniciativa) strategosIniciativasService.load(Iniciativa.class, new Long(relacionIniciativaForm.getIniciativaSeleccionadaId()));
			if (iniciativa != null) 
			{
				if (iniciativa.getNombre().indexOf(" / ") == -1) 
				{
					OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, iniciativa.getOrganizacionId());
					iniciativa.setOrganizacion(organizacionStrategos);
					iniciativa.setNombre(organizacionStrategos.getNombre() + " / " + iniciativa.getNombre());
				}
				iniciativa.setIniciativasAsociadas(strategosIniciativasService.getIniciativasAsociadas(iniciativa.getIniciativaId()));
				for (Iterator<Iniciativa> iter = iniciativa.getIniciativasAsociadas().iterator(); iter.hasNext();) 
				{
					Iniciativa iniciativaAsociada = (Iniciativa) iter.next();
					if (iniciativaAsociada.getNombre().indexOf(" / ") == -1) 
					{
						OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativaAsociada.getOrganizacionId()));
						iniciativaAsociada.setOrganizacion(organizacion);
						iniciativaAsociada.setNombre(organizacion.getNombre() + " / " + iniciativaAsociada.getNombre());
					}
				}

				setRutaCompletaOrganizacion(relacionIniciativaForm, iniciativa.getOrganizacion(), strategosIniciativasService);
			}
		}

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		ArbolBean arbolRelacionBean = (ArbolBean) request.getSession().getAttribute("gestionarIniciativasRelacionadasArbolBean");
		if (request.getParameter("accion") != null && request.getParameter("accion").equals("refrescar")) arbolRelacionBean = null;

		if (arbolRelacionBean == null) 
		{
			arbolRelacionBean = new ArbolBean();
			arbolRelacionBean.clear();
			request.getSession().setAttribute("gestionarIniciativasRelacionadasArbolBean", arbolRelacionBean);
		}

		if (arbolRelacionBean.getNodoSeleccionado() == null)
			setNodoRoot(request, iniciativa, arbolRelacionBean, strategosIniciativasService);
		else 
		{
			String selectedId = request.getParameter("selectedId");
			String openId = request.getParameter("openId");
			String closeId = request.getParameter("closeId");

			NodoArbol nodoSeleccionado = null;

			if (request.getAttribute("VisualizarIniciativasRelacionadasAction.reloadId") != null) {
				nodoSeleccionado = (NodoArbol) arbolRelacionBean.getNodos().get((String) request.getAttribute("VisualizarIniciativasRelacionadasAction.reloadId"));
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, nodoSeleccionado.getNodoArbolId());
			} 
			else if ((selectedId != null) && (!selectedId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol) arbolRelacionBean.getNodos().get(selectedId);
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, nodoSeleccionado.getNodoArbolId());
			} 
			else if ((openId != null) && (!openId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol) arbolRelacionBean.getNodos().get(openId);
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, openId);
			} 
			else if ((closeId != null) && (!closeId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol) arbolRelacionBean.getNodos().get(closeId);
				TreeviewWeb.publishArbolCerrarNodo(arbolRelacionBean, closeId);
			} 
			else 
			{
				nodoSeleccionado = (NodoArbol) arbolRelacionBean.getNodos().get(arbolRelacionBean.getSeleccionadoId());
				TreeviewWeb.publishArbolAbrirNodo(arbolRelacionBean, nodoSeleccionado.getNodoArbolId());
			}

			if (!refrescarArbol(request, iniciativa, arbolRelacionBean, nodoSeleccionado, strategosIniciativasService)) 
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		}

		arbolesService.close();
		relacionIniciativaForm.setIniciado(new Boolean(true));

		strategosIniciativasService.close();

		saveMessages(request, messages);

		actualizarAlertas(iniciativa, request);

		return mapping.findForward(forward);
	}

	private void setRutaCompletaOrganizacion(RelacionIniciativaForm relacionIniciativaForm, OrganizacionStrategos organizacion, StrategosIniciativasService strategosIniciativasService) 
	{
		OrganizacionStrategos org = organizacion;
		String rutaCompleta = org.getNombre();
		if (org.getPadreId() != null) 
		{
			org.setPadre((OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(org.getPadreId())));
			while (org.getPadre() != null) 
			{
				org = org.getPadre();
				rutaCompleta = org.getNombre() + " / " + rutaCompleta;
			}
		}

		relacionIniciativaForm.setRutaCompletaOrganizacion(rutaCompleta);
	}

	private void setNodoRoot(HttpServletRequest request, Iniciativa iniciativa, ArbolBean arbolRelacionBean, StrategosIniciativasService strategosIniciativasService) throws Exception 
	{
		arbolRelacionBean.setNodoRaiz(iniciativa);
		arbolRelacionBean.getNodos().put(iniciativa.getNodoArbolId(), iniciativa);
		iniciativa.setNodoArbolHijos(iniciativa.getIniciativasAsociadas());
		iniciativa.setNodoArbolHijosCargados(true);
		for (Iterator<Iniciativa> iter = iniciativa.getNodoArbolHijos().iterator(); iter.hasNext();) 
		{
			Iniciativa iniciativaAsociada = (Iniciativa) iter.next();
			iniciativaAsociada.setIniciativasAsociadas(strategosIniciativasService.getIniciativasAsociadas(iniciativaAsociada.getIniciativaId()));
			for (Iterator<Iniciativa> iterAsociadas = iniciativaAsociada.getIniciativasAsociadas().iterator(); iterAsociadas.hasNext();) 
			{
				Iniciativa hija = (Iniciativa) iterAsociadas.next();
				if (hija.getNombre().indexOf(" / ") == -1) 
				{
					OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(hija.getOrganizacionId()));
					hija.setOrganizacion(organizacion);
					hija.setNombre(organizacion.getNombre() + " / " + hija.getNombre());
				}
			}

			arbolRelacionBean.getNodos().put(iniciativaAsociada.getNodoArbolId(), iniciativaAsociada);
			refrescarRelacion(arbolRelacionBean, iniciativaAsociada, iniciativaAsociada.getIniciativasAsociadas(), strategosIniciativasService);
		}
		TreeviewWeb.publishArbol(arbolRelacionBean, iniciativa.getNodoArbolId(), true);

		request.getSession().setAttribute("iniciativa", iniciativa);
		request.getSession().setAttribute("iniciativaId", iniciativa.getNodoArbolId());
		arbolRelacionBean.setNodoSeleccionado(iniciativa);
		arbolRelacionBean.setSeleccionadoId(iniciativa.getNodoArbolId());
	}

	private void refrescarRelacion(ArbolBean arbolRelacionBean, NodoArbol iniciativa, List<Iniciativa> iniciativasAsociadas, StrategosIniciativasService strategosIniciativasService) 
	{
		List<Iniciativa> nodosHijos = iniciativasAsociadas;
		if (iniciativa.getNodoArbolHijosCargados()) 
		{
			for (Iterator<?> iter = nodosHijos.iterator(); iter.hasNext();) 
			{
				NodoArbol nodoHijo = (NodoArbol) iter.next();
				if (arbolRelacionBean.getNodos().get(nodoHijo.getNodoArbolId()) == null) 
				{
					iniciativa.getNodoArbolHijos().add(nodoHijo);
					arbolRelacionBean.getNodos().put(nodoHijo.getNodoArbolId(), nodoHijo);
				}
			}

			int index = 0;
			while (index < iniciativa.getNodoArbolHijos().size()) 
			{
				NodoArbol iniciativaHija = (NodoArbol) ((List<NodoArbol>) iniciativa.getNodoArbolHijos()).get(index);
				if (nodosHijos.contains(iniciativaHija)) 
				{
					Iniciativa iniciativaInsumo = (Iniciativa) strategosIniciativasService.load(Iniciativa.class, new Long(iniciativaHija.getNodoArbolId()));
					if (iniciativaInsumo.getNombre().indexOf(" / ") == -1) 
					{
						OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativaInsumo.getOrganizacionId()));
						iniciativaInsumo.setOrganizacion(organizacion);
						iniciativaInsumo.setNombre(organizacion.getNombre() + " / " + iniciativaInsumo.getNombre());
					}
					iniciativaInsumo.setIniciativasAsociadas(strategosIniciativasService.getIniciativasAsociadas(iniciativaInsumo.getIniciativaId()));
					for (Iterator<Iniciativa> iter = iniciativaInsumo.getIniciativasAsociadas().iterator(); iter.hasNext();) 
					{
						Iniciativa iniciativaAsociada = (Iniciativa) iter.next();
						if (iniciativaAsociada.getNombre().indexOf(" / ") == -1) 
						{
							OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativaAsociada.getOrganizacionId()));
							iniciativaAsociada.setOrganizacion(organizacion);
							iniciativaAsociada.setNombre(organizacion.getNombre() + " / " + iniciativaAsociada.getNombre());
						}
					}

					refrescarRelacion(arbolRelacionBean, iniciativaHija, iniciativaInsumo.getIniciativasAsociadas(), strategosIniciativasService);
					index++;
				} 
				else
					((List<NodoArbol>) iniciativa.getNodoArbolHijos()).remove(index);
			}
		} 
		else 
		{
			iniciativa.setNodoArbolHijos(nodosHijos);
			iniciativa.setNodoArbolHijosCargados(true);
			for (Iterator<NodoArbol> iter = iniciativa.getNodoArbolHijos().iterator(); iter.hasNext();) 
			{
				NodoArbol iniciativaHija = (NodoArbol) iter.next();
				iniciativaHija.setNodoArbolPadre(iniciativa);
				arbolRelacionBean.getNodos().put(iniciativaHija.getNodoArbolId(), iniciativaHija);
			}
		}
	}

	private boolean refrescarArbol(HttpServletRequest request, Iniciativa iniciativa, ArbolBean arbolRelacionBean, NodoArbol nodoSeleccionado, StrategosIniciativasService strategosIniciativasService) throws Exception 
	{
		boolean rootCargado = false;
		NodoArbol nodoActualizado = (NodoArbol) strategosIniciativasService.load(Iniciativa.class, new Long(nodoSeleccionado.getNodoArbolId()));
		if (nodoActualizado == null || nodoActualizado.getNodoArbolId().equals(iniciativa.getIniciativaId().toString())) 
		{
			Long iniciativaId = iniciativa.getIniciativaId();
			iniciativa = null;
			iniciativa = (Iniciativa) strategosIniciativasService.load(Iniciativa.class, new Long(iniciativaId));
			if (iniciativa != null) 
			{
				rootCargado = true;

				OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativa.getOrganizacionId()));
				iniciativa.setOrganizacion(organizacionStrategos);

				iniciativa.setIniciativasAsociadas(strategosIniciativasService.getIniciativasAsociadas(iniciativa.getIniciativaId()));
				for (Iterator<Iniciativa> iter = iniciativa.getIniciativasAsociadas().iterator(); iter.hasNext();) 
				{
					Iniciativa iniciativaAsociada = (Iniciativa) iter.next();
					if (iniciativaAsociada.getNombre().indexOf(" / ") == -1) 
					{
						OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativaAsociada.getOrganizacionId()));
						iniciativaAsociada.setOrganizacion(organizacion);
						iniciativaAsociada.setNombre(organizacion.getNombre() + " / " + iniciativaAsociada.getNombre());
					}
				}
			}

			setNodoRoot(request, iniciativa, arbolRelacionBean, strategosIniciativasService);
			if (nodoActualizado == null)
				return false;
		}

		Iniciativa iniciativaHija = (Iniciativa) strategosIniciativasService.load(Iniciativa.class, new Long(nodoSeleccionado.getNodoArbolId()));
		if (!nodoActualizado.getNodoArbolId().equals(iniciativa.getIniciativaId().toString()) && iniciativaHija.getNombre().indexOf(" / ") == -1) 
		{
			OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativaHija.getOrganizacionId()));
			iniciativaHija.setOrganizacion(organizacion);
			iniciativaHija.setNombre(organizacion.getNombre() + " / " + iniciativaHija.getNombre());
		}

		nodoSeleccionado.setNodoArbolNombre(nodoActualizado.getNodoArbolNombre());
		if (!rootCargado) 
		{
			iniciativaHija.setIniciativasAsociadas(strategosIniciativasService.getIniciativasAsociadas(iniciativaHija.getIniciativaId()));
			for (Iterator<?> iter = iniciativaHija.getIniciativasAsociadas().iterator(); iter.hasNext();) 
			{
				Iniciativa iniciativaAsociada = (Iniciativa) iter.next();
				if (iniciativaAsociada.getNombre().indexOf(" / ") == -1) 
				{
					OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativaAsociada.getOrganizacionId()));
					iniciativaAsociada.setOrganizacion(organizacion);
					iniciativaAsociada.setNombre(organizacion.getNombre() + " / " + iniciativaAsociada.getNombre());
				}
			}
		}
		refrescarRelacion(arbolRelacionBean, nodoSeleccionado, iniciativaHija.getIniciativasAsociadas(), strategosIniciativasService);

		request.getSession().setAttribute("iniciativa", nodoSeleccionado);
		request.getSession().setAttribute("iniciativaId", nodoSeleccionado.getNodoArbolId());
		arbolRelacionBean.setNodoSeleccionado(nodoSeleccionado);
		arbolRelacionBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());

		return true;
	}

	private void actualizarAlertas(Iniciativa raiz, HttpServletRequest request) 
	{
		Map<String, String> iniciativaIds = new HashMap<String, String>();
		getIds(raiz.getNodoArbolHijos(), iniciativaIds);
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		Map<Long, Byte> alertas = strategosIniciativasService.getAlertasIniciativas(iniciativaIds);
		strategosIniciativasService.close();
		actualizarAlertas(raiz.getNodoArbolHijos(), alertas);
	}

	private void getIds(Collection<NodoArbol> nodos, Map<String, String> ids) 
	{
		if (nodos != null) 
		{
			for (Iterator<NodoArbol> iter = nodos.iterator(); iter.hasNext();) 
			{
				NodoArbol nodo = (NodoArbol) iter.next();
				ids.put(nodo.getNodoArbolId(), nodo.getNodoArbolId());
				getIds(nodo.getNodoArbolHijos(), ids);
			}
		}
	}

	private void actualizarAlertas(Collection<NodoArbol> iniciativas, Map<Long, Byte> alertas) 
	{
		if (iniciativas != null) 
		{
			for (Iterator<NodoArbol> iter = iniciativas.iterator(); iter.hasNext();) 
			{
				Iniciativa iniciativa = (Iniciativa) iter.next();
				Byte alerta = (Byte) alertas.get(iniciativa.getIniciativaId());
				if (alerta != null)
					iniciativa.setAlerta(alerta);
				actualizarAlertas(iniciativa.getNodoArbolHijos(), alertas);
			}
		}
	}
}