package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.SeleccionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarPerspectivasPlanesAction extends VgcAction
{
	public static final String ACTION_KEY = "SeleccionarPerspectivasPlanesAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SeleccionarPerspectivasForm seleccionarPerspectivasForm = (SeleccionarPerspectivasForm)form;
		
		if (!seleccionarPerspectivasForm.getPermitirCambiarPlan().booleanValue())
			return mapping.findForward(forward);

		if (seleccionarPerspectivasForm.getAtributoOrdenPlanes() == null) 
			seleccionarPerspectivasForm.setAtributoOrdenPlanes("nombre");
		if (seleccionarPerspectivasForm.getTipoOrdenPlanes() == null) 
			seleccionarPerspectivasForm.setTipoOrdenPlanes("ASC");

		StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

		Map filtros = new HashMap();
		
		if ((seleccionarPerspectivasForm.getOrganizacionSeleccionadaId() != null) && (!seleccionarPerspectivasForm.getOrganizacionSeleccionadaId().equals(""))) 
			filtros.put("organizacionId", seleccionarPerspectivasForm.getOrganizacionSeleccionadaId().toString());

		PaginaLista paginaPlanes = planesService.getPlanes(0, 0, seleccionarPerspectivasForm.getAtributoOrdenPlanes(), seleccionarPerspectivasForm.getTipoOrdenPlanes(), true, filtros);

		request.getSession().setAttribute("paginaPlanes", paginaPlanes);

		planesService.close();

		if (paginaPlanes.getLista().size() > 0) 
		{
			Long planId = ((Plan)paginaPlanes.getLista().get(0)).getPlanId();
			if ((seleccionarPerspectivasForm.getPlanSeleccionadoId() != null) && (seleccionarPerspectivasForm.getPlanSeleccionadoId().byteValue() != 0)) 
			{
				if (seleccionarPerspectivasForm.getPlanSeleccionadoId() != planId)
					seleccionarPerspectivasForm.setCambioPlan(true);
				else 
					seleccionarPerspectivasForm.setCambioPlan(false);
			}
			seleccionarPerspectivasForm.setPlanSeleccionadoId(planId);
		} 
		else 
			seleccionarPerspectivasForm.setPlanSeleccionadoId(null);

		return mapping.findForward(forward);
	}
}