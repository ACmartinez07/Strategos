package com.visiongc.app.strategos.web.struts.presentaciones.vistas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.GestionarVistasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.controles.SplitControl;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class GestionarVistasAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarVistasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		getBarraAreas(request, "strategos").setBotonSeleccionado("presentacionEjecutiva");

		String forward = mapping.getParameter();

		GestionarVistasForm gestionarVistasForm = (GestionarVistasForm)form;
		ActionMessages messages = getMessages(request);
    
		request.getSession().setAttribute("alerta", new com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));

		String organizacionId = (String)request.getSession().getAttribute("organizacionId");
		String atributoOrden = gestionarVistasForm.getAtributoOrdenVistas();
		String tipoOrden = gestionarVistasForm.getTipoOrdenVistas();
		int pagina = gestionarVistasForm.getPaginaSeleccionadaVistas();
		String seleccionados = gestionarVistasForm.getSeleccionadosVistas();
		
		gestionarVistasForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("VISTA_VIEWALL"));
		gestionarVistasForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("VISTA_EDIT"));

		if ((atributoOrden == null) || (atributoOrden.equals(""))) 
		{
			atributoOrden = "nombre";
			gestionarVistasForm.setAtributoOrdenVistas(atributoOrden);
		}
		
		if ((tipoOrden == null) || (tipoOrden.equals(""))) 
		{
			tipoOrden = "ASC";
			gestionarVistasForm.setTipoOrdenVistas(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();

		Map<String, String> filtros = new HashMap<String, String>();
		filtros.put("organizacionId", organizacionId);

		PaginaLista paginaVistas = strategosVistasService.getVistas(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		gestionarVistasForm.setNombreVistaSeleccionada(null);

		int indiceVista = 0;
		int totalVistas = 0;
		boolean interrumpirCiclo = false;
		if ((paginaVistas != null) && (paginaVistas.getLista() != null) && (paginaVistas.getLista().size() > 0)) 
		{
			totalVistas = paginaVistas.getLista().size();
			interrumpirCiclo = totalVistas == 0;
		} 
		else 
		{	
			seleccionados = null;
      		gestionarVistasForm.setSeleccionadosVistas(seleccionados);
		}
		
		Vista vistaSeleccionada = null;

		while (!interrumpirCiclo) 
		{
			if ((seleccionados == null) || (seleccionados.equals(""))) 
			{
				Long vistaId = null;
				if ((paginaVistas != null) && (paginaVistas.getLista() != null) && (paginaVistas.getLista().size() > indiceVista))
				{
					vistaId = ((Vista)paginaVistas.getLista().get(indiceVista)).getVistaId();

					seleccionados = vistaId.toString();
					gestionarVistasForm.setSeleccionadosVistas(seleccionados);
					indiceVista++;
				}
			}

			vistaSeleccionada = null;
			if ((seleccionados != null) && (!seleccionados.equals(""))) 
			{
				vistaSeleccionada = (Vista)strategosVistasService.load(Vista.class, new Long(gestionarVistasForm.getSeleccionadosVistas()));

				if (vistaSeleccionada != null) 
				{
					if (vistaSeleccionada.getOrganizacionId().longValue() != new Long(organizacionId).longValue())
						vistaSeleccionada = null;
					else 
						gestionarVistasForm.setNombreVistaSeleccionada(vistaSeleccionada.getNombre());
				}

				if (vistaSeleccionada == null) 
				{
					seleccionados = null;
					gestionarVistasForm.setSeleccionadosVistas(seleccionados);
					gestionarVistasForm.setBloqueadosVistas(seleccionados);
				}
			}

			interrumpirCiclo = seleccionados != null;

			if (!interrumpirCiclo) 
				interrumpirCiclo = indiceVista >= totalVistas;
			if (interrumpirCiclo) 
				gestionarVistasForm.setBloqueadosVistas(seleccionados);
		}

		paginaVistas.setTamanoSetPaginas(5);

		request.getSession().setAttribute("paginaVistas", paginaVistas);

		request.getSession().setAttribute("vista", vistaSeleccionada);
		request.getSession().setAttribute("vistaId", vistaSeleccionada != null ? vistaSeleccionada.getVistaId().toString() : null);

		strategosVistasService.close();

		SplitControl.setConfiguracion(request, "splitPresentaciones");

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}