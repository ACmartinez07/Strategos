package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarSeriesTiempoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SelectorListaForm seleccionarSeriesTiempoForm = (SelectorListaForm)form;

    String atributoOrden = seleccionarSeriesTiempoForm.getAtributoOrden();
    String tipoOrden = seleccionarSeriesTiempoForm.getTipoOrden();

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarSeriesTiempoForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarSeriesTiempoForm.setTipoOrden(tipoOrden);
    }

    if (seleccionarSeriesTiempoForm.getFuncionCierre() != null) {
      if (!seleccionarSeriesTiempoForm.getFuncionCierre().equals("")) {
        if (seleccionarSeriesTiempoForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarSeriesTiempoForm.setFuncionCierre(seleccionarSeriesTiempoForm.getFuncionCierre() + ";");
      }
      else {
        seleccionarSeriesTiempoForm.setFuncionCierre(null);
      }

    }

    StrategosSeriesTiempoService seriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

    PaginaLista paginaSeriesTiempo = seriesTiempoService.getSeriesTiempo(0, 0, atributoOrden, tipoOrden, true, null);

    request.setAttribute("paginaSeriesTiempo", paginaSeriesTiempo);

    seriesTiempoService.close();

    return mapping.findForward(forward);
  }
}