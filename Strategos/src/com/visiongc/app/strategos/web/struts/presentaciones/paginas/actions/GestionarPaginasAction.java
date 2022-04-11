package com.visiongc.app.strategos.web.struts.presentaciones.paginas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.web.struts.presentaciones.paginas.forms.GestionarPaginasForm;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.GestionarVistasForm;
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

public class GestionarPaginasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		GestionarVistasForm gestionarVistasForm = (GestionarVistasForm)request.getSession().getAttribute("gestionarVistasForm");
		String vistaId = gestionarVistasForm.getSeleccionadosVistas();
		String nombreVista = gestionarVistasForm.getNombreVistaSeleccionada();

		GestionarPaginasForm gestionarPaginasForm = (GestionarPaginasForm)form;

		String atributoOrden = gestionarPaginasForm.getAtributoOrdenPaginas();
		String tipoOrden = gestionarPaginasForm.getTipoOrdenPaginas();
		int pagina = gestionarPaginasForm.getPaginaSeleccionadaPaginas();
		String seleccionados = gestionarPaginasForm.getSeleccionadosPaginas();
		
		gestionarPaginasForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("VISTA_VIEWALL"));
		gestionarPaginasForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("PAGINA_EDIT"));
		
		if ((atributoOrden == null) || (atributoOrden.equals(""))) 
		{
			atributoOrden = "numero";
			gestionarPaginasForm.setAtributoOrdenPaginas(atributoOrden);
		}
		if ((tipoOrden == null) || (tipoOrden.equals(""))) 
		{
			tipoOrden = "ASC";
			gestionarPaginasForm.setTipoOrdenPaginas(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();

		Map<String, String> filtros = new HashMap<String, String>();

		if ((vistaId == null) || (vistaId.equals(""))) 
			vistaId = "0";

		filtros.put("vistaId", vistaId);

		PaginaLista paginaPaginas = strategosPaginasService.getPaginas(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		Pagina paginaSeleccionada = null;
		int indicePagina = 0;
		int totalPaginas = 0;
		boolean interrumpirCiclo = false;
		if ((paginaPaginas != null) && (paginaPaginas.getLista() != null) && (paginaPaginas.getLista().size() > 0)) 
		{
			totalPaginas = paginaPaginas.getLista().size();
			interrumpirCiclo = totalPaginas == 0;
		} 
		else 
		{
			seleccionados = null;
			gestionarPaginasForm.setSeleccionadosPaginas(seleccionados);
		}

		while (!interrumpirCiclo) 
		{
			if ((seleccionados == null) || (seleccionados.equals(""))) 
			{
				Long paginaId = null;
				if ((paginaPaginas != null) && (paginaPaginas.getLista() != null) && (paginaPaginas.getLista().size() > indicePagina))
				{
					paginaId = ((Pagina)paginaPaginas.getLista().get(indicePagina)).getPaginaId();
					
					seleccionados = paginaId.toString();
					gestionarPaginasForm.setSeleccionadosPaginas(seleccionados);
					indicePagina++;
				}
			}

			if ((seleccionados != null) && (!seleccionados.equals(""))) 
			{
				paginaSeleccionada = (Pagina)strategosPaginasService.load(Pagina.class, new Long(gestionarPaginasForm.getSeleccionadosPaginas()));

				if ((paginaSeleccionada != null) && ((vistaId == null) || (paginaSeleccionada.getVista().getVistaId().longValue() != new Long(vistaId).longValue()))) 
					paginaSeleccionada = null;

				if (paginaSeleccionada == null) 
				{
					seleccionados = null;
					gestionarPaginasForm.setSeleccionadosPaginas(seleccionados);
					gestionarPaginasForm.setBloqueadosPaginas(seleccionados);
				}	
			}

			interrumpirCiclo = seleccionados != null;

			if (!interrumpirCiclo) 
				interrumpirCiclo = indicePagina >= totalPaginas;

				if (interrumpirCiclo) 
					gestionarPaginasForm.setBloqueadosPaginas(seleccionados);
		}

		paginaPaginas.setTamanoSetPaginas(5);

		request.setAttribute("paginaPaginas", paginaPaginas);

		request.getSession().setAttribute("pagina", paginaSeleccionada);
		request.getSession().setAttribute("paginaId", paginaSeleccionada != null ? paginaSeleccionada.getPaginaId().toString() : null);
		
		gestionarPaginasForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		gestionarPaginasForm.setNombreVista(nombreVista);

		strategosPaginasService.close();

		return mapping.findForward(forward);
	}
}