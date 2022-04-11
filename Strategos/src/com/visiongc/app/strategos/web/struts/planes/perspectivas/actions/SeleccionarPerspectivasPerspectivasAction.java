package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.SeleccionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.util.PermisologiaUsuario;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class SeleccionarPerspectivasPerspectivasAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarPerspectivasPerspectivasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarPerspectivasForm seleccionarPerspectivasForm = (SeleccionarPerspectivasForm)form;

		ActionMessages messages = getMessages(request);

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("PERSPECTIVA_VIEWALL");

		String llamadaDesde = request.getParameter("llamadaDesde");
		if (llamadaDesde != null) 
		{
			if (llamadaDesde.equals("Organizaciones"))
				seleccionarPerspectivasForm.setPanelSeleccionado("panelOrganizaciones");
			else if (llamadaDesde.equals("Planes"))
				seleccionarPerspectivasForm.setPanelSeleccionado("panelPlanes");
			else if (llamadaDesde.equals("Perspectivas")) 
				seleccionarPerspectivasForm.setPanelSeleccionado("panelPerspectivas");
		}

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
		ArbolBean arbolBean = (ArbolBean)request.getSession().getAttribute("seleccionarPerspectivasArbolPerspectivasBean");

		if ((arbolBean == null) || (!seleccionarPerspectivasForm.getIniciado().booleanValue()) || (seleccionarPerspectivasForm.isCambioPlan())) 
		{
			arbolBean = new ArbolBean();
			arbolBean.clear();
			request.getSession().setAttribute("seleccionarPerspectivasArbolPerspectivasBean", arbolBean);
		}

		if ((seleccionarPerspectivasForm.getPlanSeleccionadoId() != null) && (seleccionarPerspectivasForm.getPlanSeleccionadoId().byteValue() != 0)) 
		{
			StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
			seleccionarPerspectivasForm.setNombrePlan(((Plan)planesService.load(Plan.class, seleccionarPerspectivasForm.getPlanSeleccionadoId())).getNombre());
			planesService.close();
		} 
		else 
		{
			seleccionarPerspectivasForm.setNombrePlan(null);
			seleccionarPerspectivasForm.setPerspectivaSeleccionadaId(null);
			seleccionarPerspectivasForm.setIniciado(new Boolean(true));
			arbolBean = new ArbolBean();
			arbolBean.clear();
			request.getSession().setAttribute("seleccionarPerspectivasArbolPerspectivasBean", arbolBean);
			arbolesService.close();
			
			return mapping.findForward(forward);
		}

		seleccionarPerspectivasForm.setCambioPlan(false);

		if (arbolBean.getNodoSeleccionado() == null)
		{
			Perspectiva perspectivaRoot = new Perspectiva();
			Perspectiva perspectiva = null;
			if ((seleccionarPerspectivasForm.getPerspectivaSeleccionadaId() != null) && (!seleccionarPerspectivasForm.getIniciado().booleanValue())) 
			{
				perspectiva = (Perspectiva)arbolesService.load(Perspectiva.class, seleccionarPerspectivasForm.getPerspectivaSeleccionadaId());
				if (perspectiva != null) 
				{
					List nodos = arbolesService.getRutaCompleta(perspectiva);
					for (Iterator iter = nodos.iterator(); iter.hasNext(); ) 
					{
						Perspectiva pers = (Perspectiva)iter.next();
						TreeviewWeb.publishArbolAbrirNodo(arbolBean, pers.getPerspectivaId().toString());
					}
          
					perspectivaRoot = new Perspectiva();
					perspectivaRoot.setPerspectivaId(((Perspectiva)nodos.get(0)).getPerspectivaId());
					perspectivaRoot.setPadreId(((Perspectiva)nodos.get(0)).getPadreId());
					perspectivaRoot.setNombre(((Perspectiva)nodos.get(0)).getNombre());
				} 
				else 
				{
					Object[] arregloIdentificadores = new Object[2];
					arregloIdentificadores[0] = "planId";
					arregloIdentificadores[1] = seleccionarPerspectivasForm.getPlanSeleccionadoId();
					perspectivaRoot = (Perspectiva)arbolesService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);
					if (perspectivaRoot == null) 
					{
						StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
						perspectivaRoot = strategosPerspectivasService.crearPerspectivaRaiz(seleccionarPerspectivasForm.getPlanSeleccionadoId(), getUsuarioConectado(request));
						strategosPerspectivasService.close();
					}
          
					TreeviewWeb.publishArbol(arbolBean, null, perspectivaRoot.getPerspectivaId().toString(), null, null, true);
					perspectiva = perspectivaRoot;
				}
			} 
			else 
			{
				Object[] arregloIdentificadores = new Object[2];
				arregloIdentificadores[0] = "planId";
				arregloIdentificadores[1] = seleccionarPerspectivasForm.getPlanSeleccionadoId();
				perspectivaRoot = (Perspectiva)arbolesService.getNodoArbolRaiz(perspectivaRoot, arregloIdentificadores);
				if (perspectivaRoot == null) 
				{
					StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
					perspectivaRoot = strategosPerspectivasService.crearPerspectivaRaiz(seleccionarPerspectivasForm.getPlanSeleccionadoId(), getUsuarioConectado(request));
					strategosPerspectivasService.close();
				}
        
				TreeviewWeb.publishArbol(arbolBean, null, perspectivaRoot.getPerspectivaId().toString(), null, null, true);
				perspectiva = perspectivaRoot;
			}

			seleccionarPerspectivasForm.setPerspectivaSeleccionadaId(perspectiva.getPerspectivaId());

			arbolBean.setNodoRaiz(perspectivaRoot);

			arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
			arbolBean.setNodoSeleccionado(perspectiva);
			arbolBean.setSeleccionadoId(perspectiva.getPerspectivaId().toString());
		}
		else
		{
			String seleccionarPerspectivaId = request.getParameter("seleccionarPerspectivaId");
			String abrirPerspectivaId = request.getParameter("abrirPerspectivaId");
			String cerrarPerspectivaId = request.getParameter("cerrarPerspectivaId");
			Perspectiva perspectivaSeleccionada = null;

			if (request.getAttribute("SeleccionarPerspectivasPerspectivasAction.reloadId") != null) 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, (Long)request.getAttribute("SeleccionarPerspectivasPerspectivasAction.reloadId"));
			else if ((seleccionarPerspectivaId != null) && (!seleccionarPerspectivaId.equals(""))) 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(seleccionarPerspectivaId));
			else if ((abrirPerspectivaId != null) && (!abrirPerspectivaId.equals(""))) 
			{
				TreeviewWeb.publishArbolAbrirNodo(arbolBean, abrirPerspectivaId);
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(abrirPerspectivaId));
			} 
			else if ((cerrarPerspectivaId != null) && (!cerrarPerspectivaId.equals(""))) 
			{
				TreeviewWeb.publishArbolCerrarNodo(arbolBean, cerrarPerspectivaId);
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(cerrarPerspectivaId));
			} 
			else 
				perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(arbolBean.getSeleccionadoId()));

			Long reloadId;
			if (perspectivaSeleccionada == null) 
			{
				perspectivaSeleccionada = (Perspectiva)arbolBean.getNodoRaiz();
				reloadId = perspectivaSeleccionada.getPerspectivaId();
				TreeviewWeb.publishArbol(arbolBean, perspectivaSeleccionada.getPerspectivaId().toString(), true);
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = perspectivaSeleccionada.getPerspectivaId();
				if (cerrarPerspectivaId == null) 
					TreeviewWeb.publishArbolAbrirNodo(arbolBean, reloadId.toString());
			}

			arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);

			arbolBean.setNodoSeleccionado(perspectivaSeleccionada);
			arbolBean.setSeleccionadoId(perspectivaSeleccionada.getPerspectivaId().toString());
		}

		seleccionarPerspectivasForm.setPerspectivaSeleccionadaId(new Long(arbolBean.getSeleccionadoId()));

		arbolesService.close();

		seleccionarPerspectivasForm.setIniciado(new Boolean(true));

		saveMessages(request, messages);
		
		return mapping.findForward(forward);
	}
}