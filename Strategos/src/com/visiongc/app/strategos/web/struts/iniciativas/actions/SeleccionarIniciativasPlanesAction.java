package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.SeleccionarIniciativasForm;
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

public final class SeleccionarIniciativasPlanesAction extends VgcAction
{
  public static final String ACTION_KEY = "SeleccionarIniciativasPlanesAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SeleccionarIniciativasForm seleccionarIniciativasForm = (SeleccionarIniciativasForm)form;

    if (!seleccionarIniciativasForm.getPermitirCambiarPlan().booleanValue())
    {
      return mapping.findForward(forward);
    }

    if (seleccionarIniciativasForm.getAtributoOrdenPlanes() == null) {
      seleccionarIniciativasForm.setAtributoOrdenPlanes("nombre");
    }
    if (seleccionarIniciativasForm.getTipoOrdenPlanes() == null) {
      seleccionarIniciativasForm.setTipoOrdenPlanes("ASC");
    }

    StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

    Map filtros = new HashMap();

    if ((seleccionarIniciativasForm.getOrganizacionSeleccionadaId() != null) && (!seleccionarIniciativasForm.getOrganizacionSeleccionadaId().equals(""))) {
      filtros.put("organizacionId", seleccionarIniciativasForm.getOrganizacionSeleccionadaId().toString());
    }

    PaginaLista paginaPlanes = planesService.getPlanes(0, 0, seleccionarIniciativasForm.getAtributoOrdenPlanes(), seleccionarIniciativasForm.getTipoOrdenPlanes(), true, filtros);

    request.getSession().setAttribute("paginaPlanes", paginaPlanes);

    planesService.close();

    if (paginaPlanes.getLista().size() > 0) {
      if ((seleccionarIniciativasForm.getPlanSeleccionadoId() == null) || (seleccionarIniciativasForm.getPlanSeleccionadoId().equals(""))) {
        Long planId = ((Plan)paginaPlanes.getLista().get(0)).getPlanId();
        seleccionarIniciativasForm.setPlanSeleccionadoId(planId);
      }
      else if (seleccionarIniciativasForm.isCambioOrganizacion()) {
        Long planId = ((Plan)paginaPlanes.getLista().get(0)).getPlanId();
        seleccionarIniciativasForm.setPlanSeleccionadoId(planId);
      }
    }
    else {
      seleccionarIniciativasForm.setPlanSeleccionadoId(null);
    }

    seleccionarIniciativasForm.setCambioOrganizacion(false);

    return mapping.findForward(forward);
  }
}