package com.visiongc.app.strategos.web.struts.planes.plantillas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarPlantillasPlanesAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SelectorListaForm seleccionarPlantillasPlanesForm = (SelectorListaForm)form;

    String atributoOrden = seleccionarPlantillasPlanesForm.getAtributoOrden();
    String tipoOrden = seleccionarPlantillasPlanesForm.getTipoOrden();

    seleccionarPlantillasPlanesForm.setFuncionCierre(request.getParameter("funcionCierre"));

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarPlantillasPlanesForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarPlantillasPlanesForm.setTipoOrden(tipoOrden);
    }

    if (seleccionarPlantillasPlanesForm.getFuncionCierre() != null) {
      if (!seleccionarPlantillasPlanesForm.getFuncionCierre().equals("")) {
        if (seleccionarPlantillasPlanesForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarPlantillasPlanesForm.setFuncionCierre(seleccionarPlantillasPlanesForm.getFuncionCierre() + ";");
      }
      else {
        seleccionarPlantillasPlanesForm.setFuncionCierre(null);
      }

    }

    StrategosPlantillasPlanesService plantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();

    PaginaLista paginaPlantillasPlanes = plantillasPlanesService.getPlantillasPlanes(0, 0, atributoOrden, tipoOrden, true, null);

    request.setAttribute("paginaPlantillasPlanes", paginaPlantillasPlanes);

    plantillasPlanesService.close();

    return mapping.findForward(forward);
  }
}