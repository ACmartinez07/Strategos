package com.visiongc.app.strategos.web.struts.problemas.clasesproblemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas.TipoClaseProblema;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.controles.SplitControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionarClasesProblemasAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarClasesProblemasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		ActionMessages messages = getMessages(request);
    
		request.getSession().setAttribute("alerta", new com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));

		String selectedClaseProblemasId = request.getParameter("selectedClaseProblemasId");
		String openClaseProblemasId = request.getParameter("openClaseProblemasId");
		String closeClaseProblemasId = request.getParameter("closeClaseProblemasId");
		String organizacionId = (String)request.getSession().getAttribute("organizacionId");
		ClaseProblemas claseproblemas = (ClaseProblemas)request.getSession().getAttribute("claseProblemas");
		Integer tipo = null;
		if (claseproblemas != null && request.getParameter("tipo") == null)
			tipo = claseproblemas.getTipo();
		else if (request.getParameter("tipo") != null)
			tipo = Integer.parseInt(request.getParameter("tipo"));
		
		if (tipo.intValue() == TipoClaseProblema.getTipoProblema().intValue())
			getBarraAreas(request, "strategos").setBotonSeleccionado("problemas");
		else
			getBarraAreas(request, "strategos").setBotonSeleccionado("riesgo");
		
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_VIEWALL", new Long(organizacionId).longValue());
		boolean cambioOrganizacion = false;

		if (claseproblemas != null) 
			cambioOrganizacion = !claseproblemas.getOrganizacionId().toString().equals(organizacionId);

		ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();

		if ((request.getSession().getAttribute("claseProblemas") == null) || (cambioOrganizacion) || (claseproblemas != null && claseproblemas.getTipo() != null && tipo.intValue() != claseproblemas.getTipo().intValue()))
		{
			Object[] arregloIdentificadores = new Object[4];
			arregloIdentificadores[0] = "organizacionId";
			arregloIdentificadores[1] = new Long(organizacionId);
			arregloIdentificadores[2] = "tipo";
			arregloIdentificadores[3] = new Integer(tipo);

			ClaseProblemas claseProblemasRoot = new ClaseProblemas();

			claseProblemasRoot = (ClaseProblemas)nodosArbolService.getNodoArbolRaiz(claseProblemasRoot, arregloIdentificadores);

			if (claseProblemasRoot == null || (claseProblemasRoot != null && claseProblemasRoot.getTipo().intValue() != tipo.intValue())) 
			{
				StrategosClasesProblemasService strategosClasesProblemasService = StrategosServiceFactory.getInstance().openStrategosClasesProblemasService();
				claseProblemasRoot = strategosClasesProblemasService.crearClaseRaiz(new Long(organizacionId), getUsuarioConectado(request), tipo);
				strategosClasesProblemasService.close();
			}

			TreeviewWeb.publishTree("arbolClasesProblemas", claseProblemasRoot.getClaseId().toString(), "session", request, true);
			nodosArbolService.refreshNodosArbol(claseProblemasRoot, (String)request.getSession().getAttribute("arbolClasesProblemas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
			request.getSession().setAttribute("claseProblemasRoot", claseProblemasRoot);
			
			request.getSession().setAttribute("claseProblemas", claseProblemasRoot);
			request.getSession().setAttribute("claseProblemasId", claseProblemasRoot.getClaseId().toString());
		}
		else
		{
			ClaseProblemas claseProblemasSeleccionada = null;

			if (request.getAttribute("GestionarClasesProblemasAction.reloadId") != null) 
				claseProblemasSeleccionada = (ClaseProblemas)nodosArbolService.load(ClaseProblemas.class, (Long)request.getAttribute("GestionarClasesProblemasAction.reloadId"));
			else if ((selectedClaseProblemasId != null) && (!selectedClaseProblemasId.equals(""))) 
				claseProblemasSeleccionada = (ClaseProblemas)nodosArbolService.load(ClaseProblemas.class, new Long(selectedClaseProblemasId));
			else if ((openClaseProblemasId != null) && (!openClaseProblemasId.equals(""))) 
			{
				TreeviewWeb.publishTreeOpen("arbolClasesProblemas", openClaseProblemasId, "session", request);
				claseProblemasSeleccionada = (ClaseProblemas)nodosArbolService.load(ClaseProblemas.class, new Long(openClaseProblemasId));
			} 
			else if ((closeClaseProblemasId != null) && (!closeClaseProblemasId.equals(""))) 
			{
				TreeviewWeb.publishTreeClose("arbolClasesProblemas", closeClaseProblemasId, "session", request);
				claseProblemasSeleccionada = (ClaseProblemas)nodosArbolService.load(ClaseProblemas.class, new Long(closeClaseProblemasId));
			} 
			else 
				claseProblemasSeleccionada = (ClaseProblemas)nodosArbolService.load(ClaseProblemas.class, new Long((String)request.getSession().getAttribute("claseProblemasId")));
      
			Long reloadId;
			if (claseProblemasSeleccionada == null) 
			{
				claseProblemasSeleccionada = (ClaseProblemas)request.getSession().getAttribute("claseProblemasRoot");
				reloadId = claseProblemasSeleccionada.getClaseId();
				TreeviewWeb.publishTree("arbolClasesProblemas", claseProblemasSeleccionada.getClaseId().toString(), "session", request, true);

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = claseProblemasSeleccionada.getClaseId();
				if (closeClaseProblemasId == null) 
					TreeviewWeb.publishTreeOpen("arbolClasesProblemas", reloadId.toString(), "session", request);
			}

			nodosArbolService.refreshNodosArbol((ClaseProblemas)request.getSession().getAttribute("claseProblemasRoot"), (String)request.getSession().getAttribute("arbolClasesProblemas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);

			request.getSession().setAttribute("claseProblemas", claseProblemasSeleccionada);
			request.getSession().setAttribute("claseProblemasId", claseProblemasSeleccionada.getClaseId().toString());
		}

		nodosArbolService.close();

		request.getSession().setAttribute("verClase", getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_VIEWALL"));
		request.getSession().setAttribute("editarClase", getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_EDIT"));
		
		SplitControl.setConfiguracion(request, "splitProblemas");

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}	
}