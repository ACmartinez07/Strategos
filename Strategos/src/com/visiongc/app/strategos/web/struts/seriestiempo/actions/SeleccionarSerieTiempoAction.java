package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarSerieTiempoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SelectorListaForm seleccionarUnidadesMedidaForm = (SelectorListaForm)form;

    String atributoOrden = seleccionarUnidadesMedidaForm.getAtributoOrden();
    String tipoOrden = seleccionarUnidadesMedidaForm.getTipoOrden();

    StrategosUnidadesService unidadesMedidaService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

    PaginaLista paginaUnidadesMedida = unidadesMedidaService.getUnidadesMedida(0, 0, atributoOrden, tipoOrden, true, null);

    request.setAttribute("paginaUnidadesMedida", paginaUnidadesMedida);

    unidadesMedidaService.close();

    return mapping.findForward(forward);
  }
}