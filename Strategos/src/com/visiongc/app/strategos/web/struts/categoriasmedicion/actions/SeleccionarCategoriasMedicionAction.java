package com.visiongc.app.strategos.web.struts.categoriasmedicion.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarCategoriasMedicionAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SelectorListaForm seleccionarCategoriasMedicionForm = (SelectorListaForm)form;

    String atributoOrden = seleccionarCategoriasMedicionForm.getAtributoOrden();
    String tipoOrden = seleccionarCategoriasMedicionForm.getTipoOrden();

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarCategoriasMedicionForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarCategoriasMedicionForm.setTipoOrden(tipoOrden);
    }

    StrategosCategoriasService categoriasMedicionService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();

    PaginaLista paginaCategoriasMedicion = categoriasMedicionService.getCategoriasMedicion(0, 0, atributoOrden, tipoOrden, true, null);

    request.setAttribute("paginaCategorias", paginaCategoriasMedicion);

    categoriasMedicionService.close();

    return mapping.findForward(forward);
  }
}