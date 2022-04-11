/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planes.modelos.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.web.struts.planes.modelos.forms.GestionarModelosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GestionarModelosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		if (url.indexOf("listaModelo") == -1)
			navBar.agregarUrlSinParametros(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarModelosForm gestionarModelosForm = (GestionarModelosForm)form;

		Long planId = (request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);
		String atributoOrden = gestionarModelosForm.getAtributoOrden();
		String tipoOrden = gestionarModelosForm.getTipoOrden();
		int pagina = gestionarModelosForm.getPagina();
		gestionarModelosForm.setPlanId(planId);

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarModelosForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarModelosForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosModelosService strategosModelosService = StrategosServiceFactory.getInstance().openStrategosModelosService();

		Map<String, Object> filtros = new HashMap<String, Object>();

		if (gestionarModelosForm.getFiltroNombre() != null && !gestionarModelosForm.getFiltroNombre().equals("")) 
			filtros.put("nombre", gestionarModelosForm.getFiltroNombre());
		
		filtros.put("planId", gestionarModelosForm.getPlanId());

		PaginaLista paginaModelos = strategosModelosService.getModelos(pagina, 29, atributoOrden, tipoOrden, true, filtros);

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("getModeloId")) 
			{
				String modeloId = "";
				List<Modelo> modelos = paginaModelos.getLista();
				for (Iterator<Modelo> iter = modelos.iterator(); iter.hasNext(); ) 
				{
					Modelo modelo = (Modelo)iter.next();
					modeloId = modeloId + modelo.getPk().getModeloId().toString() + ",";
				}
				if (modeloId.length() > 1) 
					modeloId = modeloId.substring(0, modeloId.length() - 1);

				request.setAttribute("ajaxResponse", modeloId);
				
				return mapping.findForward("ajaxResponse");
			}
		}
		
		request.setAttribute("paginaModelos", paginaModelos);

		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosModelosService.load(OrganizacionStrategos.class, new Long((String)request.getSession().getAttribute("organizacionId")));
		gestionarModelosForm.setRutaCompletaOrganizacion(organizacionStrategos.getNombre());
		
		strategosModelosService.close();

		return mapping.findForward(forward);
	}
}
