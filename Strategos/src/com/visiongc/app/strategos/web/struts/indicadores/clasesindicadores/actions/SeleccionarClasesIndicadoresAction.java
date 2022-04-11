package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.SeleccionarClasesIndicadoresForm;
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

public final class SeleccionarClasesIndicadoresAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarClasesIndicadoresAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		ActionMessages messages = getMessages(request);
		
		SeleccionarClasesIndicadoresForm seleccionarClasesIndicadoresForm = (SeleccionarClasesIndicadoresForm)form;
		if (seleccionarClasesIndicadoresForm.getOrganizacionSeleccionadaId() == null)
			seleccionarClasesIndicadoresForm.setOrganizacionSeleccionadaId(new Long(request.getParameter("organizacionId")));
		if (seleccionarClasesIndicadoresForm.getIniciado() == null)
			seleccionarClasesIndicadoresForm.setIniciado(false);
		String llamadaDesde = request.getParameter("llamadaDesde");
		if (llamadaDesde != null) 
		{
			if (llamadaDesde.equals("Organizaciones"))
				seleccionarClasesIndicadoresForm.setPanelSeleccionado("panelOrganizaciones");
			else if (llamadaDesde.equals("Clases")) 
				seleccionarClasesIndicadoresForm.setPanelSeleccionado("panelClases");
		}
		else
			seleccionarClasesIndicadoresForm.setPanelSeleccionado("panelClases");
	
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("CLASE_VIEWALL");

		seleccionarClasesIndicadoresForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (seleccionarClasesIndicadoresForm.getFuncionCierre() != null) 
		{
			if (!seleccionarClasesIndicadoresForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarClasesIndicadoresForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarClasesIndicadoresForm.setFuncionCierre(seleccionarClasesIndicadoresForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarClasesIndicadoresForm.setFuncionCierre(null);
		}

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

		ArbolBean arbolClasesBean = (ArbolBean)request.getSession().getAttribute("seleccionarClasesIndicadoresArbolBean");

		if (arbolClasesBean == null)
		{
			arbolClasesBean = new ArbolBean();
			arbolClasesBean.clear();
			request.getSession().setAttribute("seleccionarClasesIndicadoresArbolBean", arbolClasesBean);
		}

		NodoArbol nodoActual = arbolClasesBean.getNodoSeleccionado();
		if (!seleccionarClasesIndicadoresForm.getIniciado()) 
		{
			seleccionarClasesIndicadoresForm.setIniciado(true);
			arbolClasesBean.clear();
			setNodoRoot(new Long(seleccionarClasesIndicadoresForm.getOrganizacionSeleccionadaId()), getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService);
		}
		else
		{
			String seleccionarClaseId = request.getParameter("seleccionarClaseId");
			String abrirClaseId = request.getParameter("abrirClaseId");
			String cerrarClaseId = request.getParameter("cerrarClaseId");
			
			NodoArbol nodoSeleccionado = null;
			
			if (request.getAttribute("SeleccionarClasesIndicadoresAction.reloadId") != null) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get((String)request.getAttribute("SeleccionarClasesIndicadoresAction.reloadId"));
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

			if (!refrescarArbol(new Long(seleccionarClasesIndicadoresForm.getOrganizacionSeleccionadaId()), getUsuarioConectado(request), arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		}

		arbolesService.close();
		strategosClasesIndicadoresService.close();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private boolean refrescarArbol(Long organizacionId, Usuario usuario, ArbolBean arbolClasesBean, NodoArbol nodoSeleccionado, StrategosClasesIndicadoresService strategosClasesIndicadoresService) throws Exception 
	{
		boolean eliminado = strategosClasesIndicadoresService.load(nodoSeleccionado.getClass(), new Long(nodoSeleccionado.getNodoArbolId())) == null;

		if (eliminado) 
		{
			setNodoRoot(organizacionId, usuario, arbolClasesBean, strategosClasesIndicadoresService);
			return false;
		}
    
		refrescarClase(arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService);

		arbolClasesBean.setNodoSeleccionado(nodoSeleccionado);
		arbolClasesBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());
		return true;
	}

	private void refrescarClase(ArbolBean arbolClasesBean, NodoArbol clase, StrategosClasesIndicadoresService strategosClasesIndicadoresService)
	{
		List nodosHijos = strategosClasesIndicadoresService.getClasesHijas(new Long(clase.getNodoArbolId()), null);
		if (clase.getNodoArbolHijosCargados())
		{
			for (Iterator iter = nodosHijos.iterator(); iter.hasNext(); ) 
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
				NodoArbol claseHija = (NodoArbol)((List)clase.getNodoArbolHijos()).get(index);
				if (nodosHijos.contains(claseHija)) 
				{
					refrescarClase(arbolClasesBean, claseHija, strategosClasesIndicadoresService);
					index++;
				} 
				else 
					((List)clase.getNodoArbolHijos()).remove(index);
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

	private void setNodoRoot(Long organizacionId, Usuario usuario, ArbolBean arbolClasesBean, StrategosClasesIndicadoresService strategosClasesIndicadoresService) throws Exception
	{
		ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), usuario);
		arbolClasesBean.getNodos().put(claseRoot.getNodoArbolId(), claseRoot);
		
		claseRoot.setNodoArbolHijos(strategosClasesIndicadoresService.getClasesHijas(claseRoot.getClaseId(), null));
		claseRoot.setNodoArbolHijosCargados(true);
		for (Iterator iter = claseRoot.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
		{
			NodoArbol clase = (NodoArbol)iter.next();
			arbolClasesBean.getNodos().put(clase.getNodoArbolId(), clase);
			refrescarClase(arbolClasesBean, clase, strategosClasesIndicadoresService);
		}

		TreeviewWeb.publishArbol(arbolClasesBean, claseRoot.getNodoArbolId(), true);

		arbolClasesBean.setNodoRaiz(claseRoot);
		arbolClasesBean.setNodoSeleccionado(claseRoot);
		arbolClasesBean.setSeleccionadoId(claseRoot.getNodoArbolId());
	}
}