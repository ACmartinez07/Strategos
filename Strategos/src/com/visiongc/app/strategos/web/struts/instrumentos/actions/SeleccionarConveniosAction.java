package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarConveniosAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SelectorListaForm seleccionarConveniosForm = (SelectorListaForm)form;

    String atributoOrden = seleccionarConveniosForm.getAtributoOrden();
    String tipoOrden = seleccionarConveniosForm.getTipoOrden();

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarConveniosForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarConveniosForm.setTipoOrden(tipoOrden);
    }

    StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
    
    PaginaLista paginaConvenio = strategosConveniosService.getTiposConvenio(0, 0, atributoOrden, tipoOrden, true, null);

    request.setAttribute("paginaConvenio", paginaConvenio);

    strategosConveniosService.close();

    return mapping.findForward(forward);
  }
}