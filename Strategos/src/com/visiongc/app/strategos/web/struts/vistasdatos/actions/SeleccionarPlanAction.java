package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.SeleccionarPlanForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarPlanAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SeleccionarPlanForm seleccionarPlanForm = (SeleccionarPlanForm)form;

    String atributoOrden = seleccionarPlanForm
      .getAtributoOrden();
    String tipoOrden = seleccionarPlanForm.getTipoOrden();

    seleccionarPlanForm.setFuncionCierre(request
      .getParameter("funcionCierre"));

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarPlanForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarPlanForm.setTipoOrden(tipoOrden);
    }

    if (seleccionarPlanForm.getFuncionCierre() != null) {
      if (!seleccionarPlanForm.getFuncionCierre().equals("")) {
        if (seleccionarPlanForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarPlanForm
            .setFuncionCierre(seleccionarPlanForm
            .getFuncionCierre() + 
            ";");
      }
      else {
        seleccionarPlanForm.setFuncionCierre(null);
      }

    }

    if (request.getParameter("seleccionMultiple") != null) {
      seleccionarPlanForm.setSeleccionMultiple(
        new Byte(request.getParameter("seleccionMultiple")));
    }

    Map filtros = new HashMap();

    if ((seleccionarPlanForm.getFiltroNombre() != null) && (!seleccionarPlanForm.getFiltroNombre().equals(""))) {
      filtros.put("nombre", seleccionarPlanForm.getFiltroNombre());
    }

    StrategosPlanesService strategosPlanesPersistenceSession = StrategosServiceFactory.getInstance().openStrategosPlanesService();

    PaginaLista paginaPlanes = strategosPlanesPersistenceSession.getPlanes(0, 0, atributoOrden, tipoOrden, true, filtros);

    seleccionarPlanForm
      .setListaPlanes(paginaPlanes.getLista());

    if (seleccionarPlanForm.getListaPlanes().size() > 0) {
      seleccionarPlanForm.setSeleccionados(((Plan)seleccionarPlanForm.getListaPlanes().get(0)).getPlanId().toString());
      seleccionarPlanForm.setValoresSeleccionados(((Plan)seleccionarPlanForm.getListaPlanes().get(0)).getNombre());
    }

    strategosPlanesPersistenceSession.close();

    return mapping.findForward(forward);
  }
}