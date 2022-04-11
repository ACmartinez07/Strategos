package com.visiongc.app.strategos.web.struts.tipoproyecto.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarTiposProyectoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SelectorListaForm seleccionarTiposProyectoForm = (SelectorListaForm)form;

    String atributoOrden = seleccionarTiposProyectoForm.getAtributoOrden();
    String tipoOrden = seleccionarTiposProyectoForm.getTipoOrden();

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarTiposProyectoForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarTiposProyectoForm.setTipoOrden(tipoOrden);
    }

    StrategosTipoProyectoService strategosTipoProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();

    PaginaLista paginaTiposProyecto = strategosTipoProyectoService.getTiposProyecto(0, 0, atributoOrden, tipoOrden, true, null);

    request.setAttribute("paginaTiposProyecto", paginaTiposProyecto);

    strategosTipoProyectoService.close();

    return mapping.findForward(forward);
  }
}