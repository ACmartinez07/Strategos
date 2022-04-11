package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.SeleccionarOrganizacionesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class SeleccionarOrganizacionesAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarOrganizacionesAction";
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		SeleccionarOrganizacionesForm seleccionarOrganizacionesForm = (SeleccionarOrganizacionesForm)form;
		String organizacionId = request.getParameter("organizacionId");
		seleccionarOrganizacionesForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (seleccionarOrganizacionesForm.getFuncionCierre() != null) 
		{
			if (!seleccionarOrganizacionesForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarOrganizacionesForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarOrganizacionesForm.setFuncionCierre(seleccionarOrganizacionesForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarOrganizacionesForm.setFuncionCierre(null);
		}

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_VIEWALL");

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		ArbolBean arbolBean = (ArbolBean)request.getSession().getAttribute("seleccionarOrganizacionesArbolBean");
	  
		if (arbolBean == null) 
		{
			arbolBean = new ArbolBean();
			arbolBean.clear();
			request.getSession().setAttribute("seleccionarOrganizacionesArbolBean", arbolBean);
		}

		if (arbolBean.getNodoSeleccionado() == null)
		{
			OrganizacionStrategos organizacionRoot = new OrganizacionStrategos();
			OrganizacionStrategos organizacion = null;
			if ((organizacionId != null) && (!organizacionId.equals(""))) 
			{
				organizacion = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, new Long(organizacionId));
				if (organizacion != null) 
				{
					List<?> nodos = arbolesService.getRutaCompleta(organizacion);
					for (Iterator<?> iter = nodos.iterator(); iter.hasNext(); ) 
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
			
			if (request.getAttribute("SeleccionarOrganizacionesAction.reloadId") != null) 
				organizacionSeleccionada = (OrganizacionStrategos)arbolesService.load(OrganizacionStrategos.class, (Long)request.getAttribute("SeleccionarOrganizacionesAction.reloadId"));
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
		}

		arbolesService.close();

		saveMessages(request, messages);
		
		return mapping.findForward(forward);
  	}
}