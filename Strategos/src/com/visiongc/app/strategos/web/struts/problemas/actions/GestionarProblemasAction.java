package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.web.struts.problemas.forms.GestionarProblemasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarProblemasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarProblemasForm gestionarProblemasForm = (GestionarProblemasForm)form;

		String atributoOrden = gestionarProblemasForm.getAtributoOrden();
		String tipoOrden = gestionarProblemasForm.getTipoOrden();
		Long organizacionId = new Long(request.getSession().getAttribute("organizacionId").toString());
		int pagina = gestionarProblemasForm.getPagina();
		Integer tipo = null;
		if (gestionarProblemasForm != null && gestionarProblemasForm.getTipo() != null && request.getParameter("tipo") == null)
			tipo = gestionarProblemasForm.getTipo();
		else if (request.getParameter("tipo") != null)
			tipo = Integer.parseInt(request.getParameter("tipo"));

		gestionarProblemasForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("PROBLEMA_VIEWALL"));
		gestionarProblemasForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("PROBLEMA_EDIT"));
		
		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarProblemasForm.setAtributoOrden(atributoOrden);
		}

		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarProblemasForm.setTipoOrden(tipoOrden);
		}
		
		if (pagina < 1) 
			pagina = 1;

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("PROBLEMA_VIEWALL", organizacionId.longValue());

		StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();
		Long claseProblemasId = new Long((String)request.getSession().getAttribute("claseProblemasId"));
		filtros.put("claseId", claseProblemasId.toString());
		if ((gestionarProblemasForm.getFiltroNombre() != null) && (!gestionarProblemasForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarProblemasForm.getFiltroNombre());
		if (!mostrarTodas) 
			filtros.put("visible", true);

		PaginaLista paginaProblemas = strategosProblemasService.getProblemas(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		paginaProblemas.setTamanoSetPaginas(5);

		request.setAttribute("paginaProblemas", paginaProblemas);

		strategosProblemasService.close();

		if (paginaProblemas.getLista().size() > 0) 
		{
			Problema problema = (Problema)paginaProblemas.getLista().get(0);
			gestionarProblemasForm.setSeleccionados(problema.getProblemaId().toString());
			gestionarProblemasForm.setValoresSeleccionados(problema.getNombre());
		} 
		else 
			gestionarProblemasForm.setSeleccionados(null);
		gestionarProblemasForm.setTipo(tipo);
		
		return mapping.findForward(forward);
	}
}