/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.SeleccionarMultiplesClasesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public final class SeleccionarMultiplesClasesAction  extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarMultiplesClasesAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarMultiplesClasesForm seleccionarMultiplesClasesForm = (SeleccionarMultiplesClasesForm)form;

		boolean inicializar = false;
		String llamadaDesde = request.getParameter("llamadaDesde");
		if (request.getParameter("organizacionId") != null)
		{
			seleccionarMultiplesClasesForm.setOrganizacionSeleccionadaId(null);
			seleccionarMultiplesClasesForm.setIniciado(null);
			inicializar = true;
			llamadaDesde = "Organizaciones";
			seleccionarMultiplesClasesForm.setPanelSeleccionado("panelClases");
		}
		else if (llamadaDesde != null)
		{
			if (llamadaDesde.equals("Organizaciones"))
				seleccionarMultiplesClasesForm.setPanelSeleccionado("panelOrganizaciones");
			else if (llamadaDesde.equals("Clases")) 
				seleccionarMultiplesClasesForm.setPanelSeleccionado("panelClases");
			else if (llamadaDesde.equals("Agregar"))
			{
				StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
				StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
				
				String[] clases = ((String)request.getParameter("clases")).split(seleccionarMultiplesClasesForm.getSeparadorClases());
				String respuesta = "";
				for (int i = 0; i < clases.length; i++) 
				{
					ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(clases[i]));
					OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, clase.getOrganizacionId());
					
					respuesta = respuesta + clase.getClaseId().toString() + seleccionarMultiplesClasesForm.getSeparadorCampos() + 
							clase.getNombre() + seleccionarMultiplesClasesForm.getSeparadorCampos() + organizacionStrategos.getNombre() + " / " +  
							setRutaCompletaClases(clase, strategosClasesIndicadoresService) + seleccionarMultiplesClasesForm.getSeparadorClases();   
				}
				
				strategosClasesIndicadoresService.close();
				strategosOrganizacionesService.close();
				
				request.setAttribute("ajaxResponse", respuesta);
				return mapping.findForward("ajaxResponse");
			}
		}

		if (seleccionarMultiplesClasesForm.getOrganizacionSeleccionadaId() == null)
			seleccionarMultiplesClasesForm.setOrganizacionSeleccionadaId(new Long(request.getParameter("organizacionId")));
		if (seleccionarMultiplesClasesForm.getIniciado() == null)
			seleccionarMultiplesClasesForm.setIniciado(false);

		ActionMessages messages = getMessages(request);
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_VIEWALL");

		if (llamadaDesde.equals("Organizaciones") || inicializar)
		{
			getArbolOrganizacion(seleccionarMultiplesClasesForm, inicializar, mostrarTodas, request, messages);
			inicializar = true;
		}
		  
		getArbolClases(seleccionarMultiplesClasesForm, inicializar, mostrarTodas, request, messages);
		  
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
	  
	private void getArbolOrganizacion(SeleccionarMultiplesClasesForm seleccionarMultiplesClasesForm, boolean inicializar, boolean mostrarTodas, HttpServletRequest request, ActionMessages messages) throws Exception
	{
		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		ArbolBean arbolBean = (ArbolBean)request.getSession().getAttribute("seleccionarOrganizacionesArbolBean");
		  
		if (arbolBean == null || inicializar)
		{
			arbolBean = new ArbolBean();
			arbolBean.clear();
			request.getSession().setAttribute("seleccionarOrganizacionesArbolBean", arbolBean);
		}

		if (arbolBean.getNodoSeleccionado() == null)
		{
			OrganizacionStrategos organizacionRoot = new OrganizacionStrategos();
			OrganizacionStrategos organizacion = null;
			if (seleccionarMultiplesClasesForm.getOrganizacionSeleccionadaId() != null) 
			{
				organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, seleccionarMultiplesClasesForm.getOrganizacionSeleccionadaId());
				if (organizacion != null) 
				{
					List<NodoArbol> nodos = arbolesService.getRutaCompleta(organizacion);
					for (Iterator<NodoArbol> iter = nodos.iterator(); iter.hasNext(); ) 
					{
						OrganizacionStrategos org = (OrganizacionStrategos)iter.next();
						TreeviewWeb.publishArbolAbrirNodo(arbolBean, org.getOrganizacionId().toString());
					}
					organizacionRoot = new OrganizacionStrategos();
					organizacionRoot.setOrganizacionId(((OrganizacionStrategos)nodos.get(0)).getOrganizacionId());
					organizacionRoot.setPadreId(((OrganizacionStrategos)nodos.get(0)).getPadreId());
					organizacionRoot.setNombre(((OrganizacionStrategos)nodos.get(0)).getNombre());
				} 
				else 
				{
					organizacionRoot = (OrganizacionStrategos)arbolesService.getNodoArbolRaiz(organizacionRoot);
					TreeviewWeb.publishArbol(arbolBean, null, organizacionRoot.getOrganizacionId().toString(), null, null, true);
					organizacion = organizacionRoot;
				}
			} 
			else 
			{
				organizacionRoot = (OrganizacionStrategos)arbolesService.getNodoArbolRaiz(organizacionRoot);
				TreeviewWeb.publishArbol(arbolBean, null, organizacionRoot.getOrganizacionId().toString(), null, null, true);
				organizacion = organizacionRoot;
			}
			  
			seleccionarMultiplesClasesForm.setOrganizacionSeleccionadaId(organizacion.getOrganizacionId());
			seleccionarMultiplesClasesForm.setIniciado(false);
			setRutaCompletaOrganizacion(seleccionarMultiplesClasesForm, organizacion, arbolesService);
			  
			arbolBean.setNodoRaiz(organizacionRoot);
			  
			arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
			arbolBean.setNodoSeleccionado(organizacion);
			arbolBean.setSeleccionadoId(organizacion.getOrganizacionId().toString());
		}
		else
		{
			String seleccionarOrganizacionId = request.getParameter("seleccionarOrganizacionId");
			String abrirOrganizacionId = request.getParameter("abrirOrganizacionId");
			String cerrarOrganizacionId = request.getParameter("cerrarOrganizacionId");
			OrganizacionStrategos organizacionSeleccionada = null;
			  
			if (request.getAttribute("SeleccionarIndicadoresOrganizacionesAction.reloadId") != null) 
				organizacionSeleccionada = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, (Long)request.getAttribute("SeleccionarIndicadoresOrganizacionesAction.reloadId"));
			else if ((seleccionarOrganizacionId != null) && (!seleccionarOrganizacionId.equals(""))) 
				organizacionSeleccionada = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(seleccionarOrganizacionId));
			else if ((abrirOrganizacionId != null) && (!abrirOrganizacionId.equals(""))) 
			{
				TreeviewWeb.publishArbolAbrirNodo(arbolBean, abrirOrganizacionId);
				organizacionSeleccionada = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(abrirOrganizacionId));
			} 
			else if ((cerrarOrganizacionId != null) && (!cerrarOrganizacionId.equals(""))) 
			{
				TreeviewWeb.publishArbolCerrarNodo(arbolBean, cerrarOrganizacionId);
				organizacionSeleccionada = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(cerrarOrganizacionId));
			} 
			else 
				organizacionSeleccionada = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(arbolBean.getSeleccionadoId()));
			  
			Long reloadId;
			if (organizacionSeleccionada == null) 
			{
				organizacionSeleccionada = (OrganizacionStrategos)arbolBean.getNodoRaiz();
				reloadId = organizacionSeleccionada.getOrganizacionId();
				TreeviewWeb.publishArbol(arbolBean, organizacionSeleccionada.getOrganizacionId().toString(), true);
				  
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = organizacionSeleccionada.getOrganizacionId();
				if (cerrarOrganizacionId == null) 
					TreeviewWeb.publishArbolAbrirNodo(arbolBean, reloadId.toString());
			}
			  
			arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);
			  
			arbolBean.setNodoSeleccionado(organizacionSeleccionada);
			arbolBean.setSeleccionadoId(organizacionSeleccionada.getOrganizacionId().toString());
			setRutaCompletaOrganizacion(seleccionarMultiplesClasesForm, organizacionSeleccionada, arbolesService);
		}
		  
		if (!seleccionarMultiplesClasesForm.getOrganizacionSeleccionadaId().toString().equals(arbolBean.getSeleccionadoId())) 
		{
			seleccionarMultiplesClasesForm.setClaseSeleccionadaId(null);
			seleccionarMultiplesClasesForm.setIniciado(false);
		}

		seleccionarMultiplesClasesForm.setOrganizacionSeleccionadaId(new Long(arbolBean.getSeleccionadoId()));

		arbolesService.close();
	}

	private void setRutaCompletaOrganizacion(SeleccionarMultiplesClasesForm seleccionarMultiplesClasesForm, OrganizacionStrategos organizacion, ArbolesService arbolesService)
	{
		OrganizacionStrategos org = organizacion;
		String rutaCompleta = org.getNombre();
		if (organizacion.getPadreId() != null)
		{
			org.setPadre((OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(organizacion.getPadreId())));
			while (org.getPadre() != null) 
			{
				org = org.getPadre();
				rutaCompleta = org.getNombre() + " / " + rutaCompleta;
			}
		}
		seleccionarMultiplesClasesForm.setRutaCompletaOrganizacion(rutaCompleta);
	}
	  
	private void getArbolClases(SeleccionarMultiplesClasesForm seleccionarMultiplesClasesForm, boolean inicializar, boolean mostrarTodas, HttpServletRequest request, ActionMessages messages) throws Exception
	{
		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

		ArbolBean arbolClasesBean = (ArbolBean)request.getSession().getAttribute("seleccionarClasesArbolBean");

		if (arbolClasesBean == null)
		{
			arbolClasesBean = new ArbolBean();
			arbolClasesBean.clear();
			request.getSession().setAttribute("seleccionarClasesArbolBean", arbolClasesBean);
		}

		if (inicializar) 
		{
			arbolClasesBean.clear();
			setNodoRoot(new Long(seleccionarMultiplesClasesForm.getOrganizacionSeleccionadaId()), getUsuarioConectado(request), arbolClasesBean, strategosClasesIndicadoresService);
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

			if (!refrescarArbol(new Long(seleccionarMultiplesClasesForm.getOrganizacionSeleccionadaId()), getUsuarioConectado(request), arbolClasesBean, nodoSeleccionado, strategosClasesIndicadoresService))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		}

		arbolesService.close();
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
	
	private String setRutaCompletaClases(ClaseIndicadores clase, StrategosClasesIndicadoresService strategosClasesIndicadoresService)
	{
		ClaseIndicadores clas = clase;
		String rutaCompleta = clas.getNombre();
		if (clas.getPadreId() != null)
		{
			clas.setPadre((ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(clas.getPadreId())));
			while (clas.getPadre() != null) 
			{
				clas = clas.getPadre();
				rutaCompleta = setRutaCompletaClases(clas, strategosClasesIndicadoresService) + " / " + rutaCompleta;
			}
		}
		
		return rutaCompleta;
	}
}