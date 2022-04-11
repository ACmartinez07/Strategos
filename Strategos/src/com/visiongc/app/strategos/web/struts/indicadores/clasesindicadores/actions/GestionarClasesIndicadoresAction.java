package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.GestionarClaseIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.controles.SplitControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionarClasesIndicadoresAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarClasesIndicadoresAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		getBarraAreas(request, "strategos").setBotonSeleccionado("indicadores");

		String forward = mapping.getParameter();
		ActionMessages messages = getMessages(request);
		GestionarClaseIndicadoresForm gestionarClaseIndicadoresForm = (GestionarClaseIndicadoresForm)form;
    
		request.getSession().setAttribute("alerta", new com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		Boolean actualizarForma = request.getParameter("actualizarForma") != null ? Boolean.parseBoolean((String)request.getParameter("actualizarForma")) : false;

		boolean cambioOrganizacion = false;
		ClaseIndicadores claseIndicadores = (ClaseIndicadores)request.getSession().getAttribute("claseIndicadores");
		Boolean visible = null;
		Boolean cambiarVisible = null;
		Boolean cambiarEstadoVisible = false;
		if (request.getParameter("cambiarEstadoVisible") != null && request.getSession().getAttribute("claseVisible") != null)
		{
			visible = request.getSession().getAttribute("claseVisible") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible")) : true;
			cambiarVisible = request.getParameter("cambiarEstadoVisible") != null ? Boolean.parseBoolean(request.getParameter("cambiarEstadoVisible")) : visible;
			if (visible.booleanValue() != cambiarVisible.booleanValue())
			{
				if (visible)
					visible = false;
				else
					visible = true;
				cambiarEstadoVisible = true;
			}
		}
		else if (request.getParameter("cambiarEstadoVisible") != null && request.getSession().getAttribute("claseVisible") == null)
		{
			visible = request.getSession().getAttribute("claseVisible") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible")) : true;
			cambiarVisible = request.getParameter("cambiarEstadoVisible") != null ? Boolean.parseBoolean(request.getParameter("cambiarEstadoVisible")) : visible;
			if (visible.booleanValue() != cambiarVisible.booleanValue())
			{
				if (visible)
					visible = false;
				else
					visible = true;
				cambiarEstadoVisible = true;
			}
		}
		else if (request.getParameter("cambiarEstadoVisible") == null && request.getSession().getAttribute("claseVisible") != null)
			visible = Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible"));
		else
			visible = request.getSession().getAttribute("claseVisible") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("claseVisible")) : true;
		request.getSession().setAttribute("claseVisible", visible.toString());
		
		if (claseIndicadores != null) 
			cambioOrganizacion = !claseIndicadores.getOrganizacionId().equals(organizacionId);

		ArbolBean arbolClasesBean = (ArbolBean)request.getSession().getAttribute("gestionarClasesIndicadoresArbolBean");
		if (request.getParameter("accion") != null && request.getParameter("accion").equals("refrescar"))
			arbolClasesBean = null;

		String selectedClaseIndicadoresId = request.getParameter("selectedClaseIndicadoresId");
		String openClaseIndicadoresId = request.getParameter("openClaseIndicadoresId");
		String closeClaseIndicadoresId = request.getParameter("closeClaseIndicadoresId");

		if (arbolClasesBean == null || cambioOrganizacion || cambiarEstadoVisible) 
		{
			arbolClasesBean = new ArbolBean();
			arbolClasesBean.clear();
			request.getSession().setAttribute("gestionarClasesIndicadoresArbolBean", arbolClasesBean);
		}

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario foco = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Clase.Arbol", "ClaseId");
		if (arbolClasesBean.getNodoSeleccionado() == null)
			setNodoRoot(request, organizacionId, foco, getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService, visible);
		else
		{
			NodoArbol nodoSeleccionado = null;

			if (request.getAttribute("GestionarClasesIndicadoresAction.reloadId") != null) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get((String)request.getAttribute("GestionarClasesIndicadoresAction.reloadId"));
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, nodoSeleccionado.getNodoArbolId());
			} 
			else if ((selectedClaseIndicadoresId != null) && (!selectedClaseIndicadoresId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(selectedClaseIndicadoresId);
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, nodoSeleccionado.getNodoArbolId());
			} 
			else if ((openClaseIndicadoresId != null) && (!openClaseIndicadoresId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(openClaseIndicadoresId);
				TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, openClaseIndicadoresId);
			} 
			else if ((closeClaseIndicadoresId != null) && (!closeClaseIndicadoresId.equals(""))) 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(closeClaseIndicadoresId);
				TreeviewWeb.publishArbolCerrarNodo(arbolClasesBean, closeClaseIndicadoresId);
			} 
			else 
			{
				nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(arbolClasesBean.getSeleccionadoId());
				if (nodoSeleccionado != null)
					TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, nodoSeleccionado.getNodoArbolId());
				else
				{
					arbolClasesBean = new ArbolBean();
					arbolClasesBean.clear();
					request.getSession().setAttribute("gestionarClasesIndicadoresArbolBean", arbolClasesBean);
					
					setNodoRoot(request, organizacionId, foco, getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService, visible);
				}
			}

			if (nodoSeleccionado != null && !refrescarArbol(foco, request, organizacionId, arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService, visible))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			else if (nodoSeleccionado != null)
			{
				if (foco == null)
				{
					foco = new ConfiguracionUsuario(); 
					ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
					pk.setConfiguracionBase("Strategos.Foco.Clase.Arbol");
					pk.setObjeto("ClaseId");
					pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
					foco.setPk(pk);
				}
				foco.setData(nodoSeleccionado.getNodoArbolId());
				frameworkService.saveConfiguracionUsuario(foco, this.getUsuarioConectado(request));
			}
			
		}
		frameworkService.close();

		strategosClasesIndicadoresService.close();
		
		request.getSession().setAttribute("verClase", getPermisologiaUsuario(request).tienePermiso("CLASE_VIEWALL"));
		request.getSession().setAttribute("editarClase", getPermisologiaUsuario(request).tienePermiso("CLASE_EDIT"));
		request.getSession().setAttribute("actualizarForma", actualizarForma.toString());
		
		SplitControl.setConfiguracion(request, "splitIndicadores");

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private boolean refrescarArbol(ConfiguracionUsuario focoClase, HttpServletRequest request, Long organizacionId, ArbolBean arbolClasesBean, NodoArbol nodoSeleccionado, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean visible) throws Exception 
	{
		NodoArbol nodoActualizado = (NodoArbol)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(nodoSeleccionado.getNodoArbolId()));
		
		if (nodoActualizado == null) 
		{
			setNodoRoot(request, organizacionId, focoClase, getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService, visible);
			return false;
		}
    
		nodoSeleccionado.setNodoArbolNombre(nodoActualizado.getNodoArbolNombre());
		refrescarClase(arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService, visible);

		request.getSession().setAttribute("claseIndicadores", nodoSeleccionado);
		request.getSession().setAttribute("claseId", nodoSeleccionado.getNodoArbolId());
		request.getSession().setAttribute("hijos", ((Boolean)(nodoSeleccionado.getNodoArbolHijos().size() > 0)).toString());

		arbolClasesBean.setNodoSeleccionado(nodoSeleccionado);
		arbolClasesBean.setSeleccionadoId(nodoSeleccionado.getNodoArbolId());
		
		return true;
	}

	private void refrescarClase(ArbolBean arbolClasesBean, NodoArbol clase, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean visible)
	{
		List nodosHijos = strategosClasesIndicadoresService.getClasesHijas(new Long(clase.getNodoArbolId()), visible);
		if (clase.getNodoArbolHijosCargados())
		{
			for (Iterator<?> iter = nodosHijos.iterator(); iter.hasNext(); ) 
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
			for (Iterator<NodoArbol> iter = clase.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
			{
				NodoArbol claseHija = (NodoArbol)iter.next();
				claseHija.setNodoArbolPadre(clase);
				arbolClasesBean.getNodos().put(claseHija.getNodoArbolId(), claseHija);
				refrescarClase(arbolClasesBean, claseHija, strategosClasesIndicadoresService, visible);
			}
		}
	}

	private void setNodoRoot(HttpServletRequest request, Long organizacionId, ConfiguracionUsuario foco, Usuario usuario, ArbolBean arbolClasesBean, StrategosClasesIndicadoresService strategosClasesIndicadoresService, Boolean visible) throws Exception 
	{
		ClaseIndicadores clase = strategosClasesIndicadoresService.getClaseRaiz(organizacionId, TipoClaseIndicadores.getTipoClaseIndicadores(), usuario);
		arbolClasesBean.setNodoRaiz(clase);
		arbolClasesBean.getNodos().put(clase.getNodoArbolId(), clase);

		clase.setNodoArbolHijos(strategosClasesIndicadoresService.getClasesHijas(clase.getClaseId(), visible));
		clase.setNodoArbolHijosCargados(true);

		for (Iterator<?> iter = clase.getNodoArbolHijos().iterator(); iter.hasNext(); ) 
		{
			ClaseIndicadores claseHija = (ClaseIndicadores)iter.next();
			arbolClasesBean.getNodos().put(claseHija.getNodoArbolId(), claseHija);
			refrescarClase(arbolClasesBean, claseHija, strategosClasesIndicadoresService, visible);
		}
		
		TreeviewWeb.publishArbol(arbolClasesBean, clase.getNodoArbolId(), true);

		if (foco != null)
		{
			ClaseIndicadores claseFoco = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(foco.getData()));
			if (claseFoco != null && claseFoco.getOrganizacionId().longValue() == clase.getOrganizacionId().longValue())
				clase = SetFoco(arbolClasesBean, visible, foco, strategosClasesIndicadoresService, clase, request);
		}
		
		request.getSession().setAttribute("claseIndicadores", clase);
		request.getSession().setAttribute("claseId", clase.getNodoArbolId());
		request.getSession().setAttribute("hijos", ((Boolean)(clase.getNodoArbolHijos().size() > 0)).toString());
		
		arbolClasesBean.setNodoSeleccionado(clase);
		arbolClasesBean.setSeleccionadoId(clase.getNodoArbolId());
	}
	
	private ClaseIndicadores SetFoco(ArbolBean arbolClasesBean, Boolean visible, ConfiguracionUsuario foco, StrategosClasesIndicadoresService strategosClasesIndicadoresService, ClaseIndicadores claseRoot, HttpServletRequest request) throws Exception
	{
		ClaseIndicadores clase = null;
		Long claseId = new Long(foco.getData());
		NodoArbol nodoClaseSeleccionado = null;
		if (claseRoot.getClaseId().longValue() != claseId.longValue())
		{
			String [] idsPadres = GetPathPadre(claseId, strategosClasesIndicadoresService, claseId.toString()).split(",");
			for (int i = 0; i < idsPadres.length; i++)
			{
				if (!idsPadres[i].equals(""))
				{
					NodoArbol nodoSeleccionado = (NodoArbol)arbolClasesBean.getNodos().get(idsPadres[i]);
					if (nodoSeleccionado != null && nodoSeleccionado.getNodoArbolId() != null)
					{
						TreeviewWeb.publishArbolAbrirNodo(arbolClasesBean, nodoSeleccionado.getNodoArbolId());
						refrescarArbol(foco, request, claseRoot.getOrganizacionId(), arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService, visible);
						nodoClaseSeleccionado = nodoSeleccionado;
					}
				}
			}			
			
			if (nodoClaseSeleccionado != null)
			{
				if (nodoClaseSeleccionado.getNodoArbolId().equals(claseId.toString()))
					clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, claseId);
				else
					clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(nodoClaseSeleccionado.getNodoArbolId()));
			}
		}
		
		if (clase == null)
			clase = claseRoot;
		
		return clase;
	}
	
	private String GetPathPadre(Long claseId, StrategosClasesIndicadoresService strategosClasesIndicadoresService, String path)
	{
		ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, claseId);
		if (clase.getPadreId() != null)
			path = GetPathPadre(clase.getPadreId(), strategosClasesIndicadoresService, clase.getPadreId().toString() + "," + path);
		
		return path;
	}	
}