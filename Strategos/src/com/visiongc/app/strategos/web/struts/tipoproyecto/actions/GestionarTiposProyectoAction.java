package com.visiongc.app.strategos.web.struts.tipoproyecto.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.GestionarCategoriasMedicionForm;
import com.visiongc.app.strategos.web.struts.tipoproyecto.forms.GestionarTiposProyectoForm;
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

public class GestionarTiposProyectoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrl(url, nombre);
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    GestionarTiposProyectoForm gestionarTipoProyectoForm = (GestionarTiposProyectoForm)form;

    String atributoOrden = gestionarTipoProyectoForm.getAtributoOrden();
    String tipoOrden = gestionarTipoProyectoForm.getTipoOrden();
    int pagina = gestionarTipoProyectoForm.getPagina();

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      gestionarTipoProyectoForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      gestionarTipoProyectoForm.setTipoOrden(tipoOrden);
    }

    if (pagina < 1) {
      pagina = 1;
    }
    StrategosTipoProyectoService strategosTipoProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();

    Map filtros = new HashMap();

    if ((gestionarTipoProyectoForm.getFiltroNombre() != null) && (!gestionarTipoProyectoForm.getFiltroNombre().equals(""))) {
      filtros.put("nombre", gestionarTipoProyectoForm.getFiltroNombre());
    }

    PaginaLista paginaProyectos = strategosTipoProyectoService.getTiposProyecto(pagina, 30, atributoOrden, tipoOrden, true, filtros);

    paginaProyectos.setTamanoSetPaginas(5);

    request.setAttribute("paginaProyectos", paginaProyectos);

    strategosTipoProyectoService.close();

    if (paginaProyectos.getLista().size() > 0) {
      TipoProyecto tipoProyecto = (TipoProyecto)paginaProyectos.getLista().get(0);
      gestionarTipoProyectoForm.setSeleccionados(tipoProyecto.getTipoProyectoId().toString());
      gestionarTipoProyectoForm.setValoresSeleccionados(tipoProyecto.getNombre());
    } else {
      gestionarTipoProyectoForm.setSeleccionados(null);
    }

    return mapping.findForward(forward);
  }
}