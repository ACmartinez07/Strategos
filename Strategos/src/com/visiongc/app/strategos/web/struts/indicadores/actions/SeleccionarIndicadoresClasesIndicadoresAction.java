package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.web.struts.indicadores.forms.SeleccionarIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class SeleccionarIndicadoresClasesIndicadoresAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarIndicadoresClasesIndicadoresAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarIndicadoresForm seleccionarIndicadoresForm = (SeleccionarIndicadoresForm)form;

		ActionMessages messages = getMessages(request);

		Boolean visible = null;
		if (request.getParameter("cambiarEstadoVisible") != null && request.getSession().getAttribute("claseVisible") != null)
		{
			request.getSession().removeAttribute("seleccionarIndicadoresArbolClasesBean");
			seleccionarIndicadoresForm.setClaseSeleccionadaId(null);
			visible = Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible"));
			if (visible)
				visible = false;
			else
				visible = true;
		}
		else if (request.getParameter("cambiarEstadoVisible") != null && request.getSession().getAttribute("claseVisible") == null)
		{
			request.getSession().removeAttribute("seleccionarIndicadoresArbolClasesBean");
			seleccionarIndicadoresForm.setClaseSeleccionadaId(null);
			visible = request.getParameter("claseVisible") != null ? Boolean.parseBoolean(request.getParameter("claseVisible")) : true;
			if (visible)
				visible = false;
			else
				visible = true;
		}
		else if (request.getParameter("cambiarEstadoVisible") == null && request.getSession().getAttribute("claseVisible") != null)
			visible = Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible"));
		else
			visible = request.getParameter("claseVisible") != null ? Boolean.parseBoolean(request.getParameter("claseVisible")) : true;
		request.getSession().setAttribute("claseVisible", visible.toString());
		
		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		ArbolBean arbolClasesBean = (ArbolBean)request.getSession().getAttribute("seleccionarIndicadoresArbolClasesBean");

		if ((arbolClasesBean == null) || (!seleccionarIndicadoresForm.getIniciado().booleanValue())) 
		{
			arbolClasesBean = new ArbolBean();
			arbolClasesBean.clear();
			request.getSession().setAttribute("seleccionarIndicadoresArbolClasesBean", arbolClasesBean);
		}

		if (arbolClasesBean.getNodoSeleccionado() == null) 
		{
			if (seleccionarIndicadoresForm.getClaseSeleccionadaId() != null)
				construirArbolSeleccionado(seleccionarIndicadoresForm.getOrganizacionSeleccionadaId(), seleccionarIndicadoresForm.getClaseSeleccionadaId(), getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService, arbolesService, visible);
			else 
				setNodoRoot(seleccionarIndicadoresForm.getOrganizacionSeleccionadaId(), getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService, visible);
			seleccionarIndicadoresForm.setClaseSeleccionadaId(new Long(arbolClasesBean.getSeleccionadoId()));
			setRutaCompletaClaseIndicadores(seleccionarIndicadoresForm, arbolClasesBean.getNodoSeleccionado());

			if (!seleccionarIndicadoresForm.getPermitirCambiarClase().booleanValue()) 
			{
				arbolesService.close();
				strategosClasesIndicadoresService.close();
				
				return mapping.findForward(forward);
			}
		}
		else
		{
			String seleccionarClaseId = request.getParameter("seleccionarClaseId");
			String abrirClaseId = request.getParameter("abrirClaseId");
			String cerrarClaseId = request.getParameter("cerrarClaseId");
			
			NodoArbol nodoSeleccionado = null;

			if (request.getAttribute("SeleccionarIndicadoresClasesIndicadoresAction.reloadId") != null) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get((String)request.getAttribute("SeleccionarIndicadoresClasesIndicadoresAction.reloadId"));
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, abrirClaseId);
			} 
			else if ((seleccionarClaseId != null) && (!seleccionarClaseId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(seleccionarClaseId);
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, abrirClaseId);
			} 
			else if ((abrirClaseId != null) && (!abrirClaseId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(abrirClaseId);
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, abrirClaseId);
			} 
			else if ((cerrarClaseId != null) && (!cerrarClaseId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(cerrarClaseId);
				TreeviewWeb.publishArbolCerrarNodo(arbolClasesBean, cerrarClaseId);
			} 
			else 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(arbolClasesBean.getSeleccionadoId());
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, abrirClaseId);
			}

			if (!refrescarArbol(seleccionarIndicadoresForm.getOrganizacionSeleccionadaId(), getUsuarioConectado(request), arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService, visible))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));

			seleccionarIndicadoresForm.setClaseSeleccionadaId(new Long(arbolClasesBean.getSeleccionadoId()));
			setRutaCompletaClaseIndicadores(seleccionarIndicadoresForm, nodoSeleccionado);
		}

		seleccionarIndicadoresForm.setPanelIndicadores("clases");

		arbolesService.close();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	/**
	 * Documentacion
	 *  
	 * Funcion para fijar la ruta completa de las clases.
	 * @author Kerwin Arias
	 * @param seleccionarIndicadoresForm = Forma seleccion de indicadores 
	 * @param clase = Nodo de la clase a fijar la ruta completa
	 */	
	private void setRutaCompletaClaseIndicadores(SeleccionarIndicadoresForm seleccionarIndicadoresForm, NodoArbol clase)
	{
		NodoArbol nodo = clase;
		String rutaCompleta = nodo.getNodoArbolNombre();
		while (nodo.getNodoArbolPadre() != null) 
		{
			nodo = nodo.getNodoArbolPadre();
			rutaCompleta = nodo.getNodoArbolNombre() + " / " + rutaCompleta;
		}	
		seleccionarIndicadoresForm.setRutaCompletaClaseIndicadores(rutaCompleta);
	}

	private void construirArbolSeleccionado(Long organizacionId, Long claseId, Usuario usuario, ArbolBean arbolClasesBean, StrategosClasesIndicadoresService strategosClasesIndicadoresService, ArbolesService arbolesService, Boolean visible) throws Exception 
	{
		ClaseIndicadores claseIndicadores = (ClaseIndicadores)arbolesService.load(ClaseIndicadores.class, claseId);
		if (claseIndicadores != null) 
		{
			setNodoRoot(claseIndicadores.getOrganizacionId(), usuario, arbolClasesBean, strategosClasesIndicadoresService, visible);
			List<NodoArbol> nodos = arbolesService.getRutaCompleta(claseIndicadores);
			NodoArbol nodoSeleccionado = arbolClasesBean.getNodoSeleccionado();
			for (Iterator<NodoArbol> iter = nodos.iterator(); iter.hasNext(); ) 
			{
				NodoArbol nodo = (NodoArbol)iter.next();
				if (nodo.getNodoArbolPadreId() != null) 
				{
					TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, nodo.getNodoArbolId());
					nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(nodo.getNodoArbolId());
					if (nodoSeleccionado != null)
						refrescarClase(arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService, visible);
				}
			}
			
			if (nodoSeleccionado == null)
				nodoSeleccionado = arbolClasesBean.getNodoSeleccionado();
			arbolClasesBean.setNodoSeleccionado(nodoSeleccionado);
			arbolClasesBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());
		}
	}

	private boolean refrescarArbol(Long organizacionId, Usuario usuario, ArbolBean arbolClasesBean, NodoArbol nodoSeleccionado, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean visible) throws Exception 
	{
		boolean eliminado = strategosClasesIndicadoresService.load(nodoSeleccionado.getClass(), new Long(nodoSeleccionado.getNodoArbolId())) == null;
		
		if (eliminado) 
		{
			setNodoRoot(organizacionId, usuario, arbolClasesBean, strategosClasesIndicadoresService, visible);
			return false;
		}
		if (nodoSeleccionado != null)
			refrescarClase(arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService, visible);

		arbolClasesBean.setNodoSeleccionado(nodoSeleccionado);
		arbolClasesBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());
		return true;
	}

	private void refrescarClase(ArbolBean arbolClasesBean, NodoArbol clase, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean visible)
	{
		List nodosHijos = strategosClasesIndicadoresService.getClasesHijas(new Long(clase.getNodoArbolId()), visible);
		if (clase.getNodoArbolHijosCargados())
		{
			for (Iterator<NodoArbol> iter = nodosHijos.iterator(); iter.hasNext(); ) 
			{
				NodoArbol nodoHijo = (NodoArbol)iter.next();
				if (arbolClasesBean.getNodos().get(nodoHijo.getNodoArbolId()) == null) 
				{
					clase.getNodoArbolHijos().add(nodoHijo);
					arbolClasesBean.getNodos().put(nodoHijo.getNodoArbolId(), nodoHijo);
				}
			}

			int index = 0;
			while (index < clase.getNodoArbolHijos().size()) 
			{
				NodoArbol claseHija = (NodoArbol)((List<NodoArbol>)clase.getNodoArbolHijos()).get(index);
				if (nodosHijos.contains(claseHija)) 
				{
					if (claseHija != null)
						refrescarClase(arbolClasesBean, claseHija, strategosClasesIndicadoresService, visible);
					index++;
				} 
				else 
					((List<NodoArbol>)clase.getNodoArbolHijos()).remove(index);
			}
		} 
		else 
		{
			clase.setNodoArbolHijos(nodosHijos);
			clase.setNodoArbolHijosCargados(true);
			for (Iterator iter = clase.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol claseHija = (NodoArbol)iter.next();
				claseHija.setNodoArbolPadre(clase);
				arbolClasesBean.getNodos().put(claseHija.getNodoArbolId(), claseHija);
			}	
		}
	}

	private void setNodoRoot(Long organizacionId, Usuario usuario, ArbolBean arbolClasesBean, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean visible) throws Exception
	{
		ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), usuario);
		arbolClasesBean.getNodos().put(claseRoot.getNodoArbolId(), claseRoot);
		
		claseRoot.setNodoArbolHijos(strategosClasesIndicadoresService.getClasesHijas(claseRoot.getClaseId(), visible));
		claseRoot.setNodoArbolHijosCargados(true);
		for (Iterator<NodoArbol> iter = claseRoot.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
		{
			NodoArbol clase = (NodoArbol)iter.next();
			if (clase != null)
			{
				arbolClasesBean.getNodos().put(clase.getNodoArbolId(), clase);
				refrescarClase(arbolClasesBean, clase, strategosClasesIndicadoresService, visible);
			}
		}

		TreeviewWeb.publishArbol(arbolClasesBean, claseRoot.getNodoArbolId(), true);

		arbolClasesBean.setNodoRaiz(claseRoot);
		arbolClasesBean.setNodoSeleccionado(claseRoot);
		arbolClasesBean.setSeleccionadoId(claseRoot.getNodoArbolId());
	}
}