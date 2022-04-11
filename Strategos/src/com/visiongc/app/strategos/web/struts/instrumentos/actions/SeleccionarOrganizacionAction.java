package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.SeleccionarOrganizacionForm;
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

public final class SeleccionarOrganizacionAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SeleccionarOrganizacionForm seleccionarOrganizacionForm = (SeleccionarOrganizacionForm)form;

    String atributoOrden = seleccionarOrganizacionForm
      .getAtributoOrden();
    String tipoOrden = seleccionarOrganizacionForm.getTipoOrden();

    seleccionarOrganizacionForm.setFuncionCierre(request
      .getParameter("funcionCierre"));

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarOrganizacionForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarOrganizacionForm.setTipoOrden(tipoOrden);
    }

    if (seleccionarOrganizacionForm.getFuncionCierre() != null) {
      if (!seleccionarOrganizacionForm.getFuncionCierre().equals("")) {
        if (seleccionarOrganizacionForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarOrganizacionForm
            .setFuncionCierre(seleccionarOrganizacionForm
            .getFuncionCierre() + 
            ";");
      }
      else {
        seleccionarOrganizacionForm.setFuncionCierre(null);
      }

    }

    if (request.getParameter("seleccionMultiple") != null) {
      seleccionarOrganizacionForm.setSeleccionMultiple(
        new Byte(request.getParameter("seleccionMultiple")));
    }

    Map filtros = new HashMap();

    if ((seleccionarOrganizacionForm.getFiltroNombre() != null) && (!seleccionarOrganizacionForm.getFiltroNombre().equals(""))) {
      filtros.put("nombre", seleccionarOrganizacionForm.getFiltroNombre());
    }

    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

    PaginaLista paginaOrganizaciones = strategosOrganizacionesService.getOrganizaciones(0, 0, atributoOrden, tipoOrden, true, filtros);

    seleccionarOrganizacionForm
      .setListaOrganizaciones(getListaOrganizaciones(paginaOrganizaciones.getLista(), strategosOrganizacionesService));

    if (seleccionarOrganizacionForm.getListaOrganizaciones().size() > 0) {
      seleccionarOrganizacionForm.setSeleccionados(((OrganizacionStrategos)seleccionarOrganizacionForm.getListaOrganizaciones().get(0)).getOrganizacionId().toString());
      seleccionarOrganizacionForm.setValoresSeleccionados(((OrganizacionStrategos)seleccionarOrganizacionForm.getListaOrganizaciones().get(0)).getNombre());
    }

    strategosOrganizacionesService.close();

    return mapping.findForward(forward);
  }

  private List getListaOrganizaciones(List organizaciones, StrategosOrganizacionesService strategosOrganizacionesService)
  {
    for (int i = 0; i < organizaciones.size(); i++) {
      OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)organizaciones.get(i);

      organizacionStrategos.setRutaCompleta(strategosOrganizacionesService.getRutaCompletaNombresOrganizacion(organizacionStrategos.getOrganizacionId(), "\\"));
    }

    return organizaciones;
  }
}