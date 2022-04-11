package com.visiongc.app.strategos.web.struts.presentaciones.paginas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.web.struts.presentaciones.paginas.forms.EditarPaginaForm;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.GestionarVistasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarPaginaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
    
		String forward = mapping.getParameter();

		EditarPaginaForm editarPaginaForm = (EditarPaginaForm)form;
		GestionarVistasForm gestionarVistasForm = (GestionarVistasForm)request.getSession().getAttribute("gestionarVistasForm");

		ActionMessages messages = getMessages(request);

		String paginaId = request.getParameter("paginaId");

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("PAGINA_EDIT");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("PAGINA_EDIT");
		boolean bloqueado = false;

		StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();

		if ((paginaId != null) && (!paginaId.equals("")) && (!paginaId.equals("0")))
		{
			bloqueado = !strategosPaginasService.lockForUpdate(request.getSession().getId(), paginaId, null);

			editarPaginaForm.setBloqueado(new Boolean(bloqueado));

			Pagina pagina = (Pagina)strategosPaginasService.load(Pagina.class, new Long(paginaId));

			if (pagina != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarPaginaForm.setPaginaId(pagina.getPaginaId());
				editarPaginaForm.setVistaId(pagina.getVistaId());
				editarPaginaForm.setDescripcion(pagina.getDescripcion());
				editarPaginaForm.setFilas(pagina.getFilas());
				editarPaginaForm.setColumnas(pagina.getColumnas());
				editarPaginaForm.setAlto(pagina.getAlto());
				editarPaginaForm.setAncho(pagina.getAncho());
				editarPaginaForm.setNumero(pagina.getNumero());
				editarPaginaForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
				editarPaginaForm.setNombreVista(((Vista)request.getSession().getAttribute("vista")).getNombre());
			}
			else
			{
				strategosPaginasService.unlockObject(request.getSession().getId(), new Long(paginaId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			editarPaginaForm.clear();

			editarPaginaForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			editarPaginaForm.setNombreVista(gestionarVistasForm.getNombreVistaSeleccionada());
			editarPaginaForm.setNumero(new Integer(strategosPaginasService.getOrdenMaximoPaginas(new Long(gestionarVistasForm.getSeleccionadosVistas()))));
			editarPaginaForm.setVistaId(new Long(gestionarVistasForm.getSeleccionadosVistas()));
		}

		strategosPaginasService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarPaginaForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}