package com.visiongc.app.strategos.web.struts.categoriasmedicion.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.GestionarCategoriasMedicionForm;
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

public class GestionarCategoriasMedicionAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrl(url, nombre);
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    GestionarCategoriasMedicionForm gestionarCategoriasMedicionForm = (GestionarCategoriasMedicionForm)form;

    String atributoOrden = gestionarCategoriasMedicionForm.getAtributoOrden();
    String tipoOrden = gestionarCategoriasMedicionForm.getTipoOrden();
    int pagina = gestionarCategoriasMedicionForm.getPagina();

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      gestionarCategoriasMedicionForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      gestionarCategoriasMedicionForm.setTipoOrden(tipoOrden);
    }

    if (pagina < 1) {
      pagina = 1;
    }

    StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();

    Map filtros = new HashMap();

    if ((gestionarCategoriasMedicionForm.getFiltroNombre() != null) && (!gestionarCategoriasMedicionForm.getFiltroNombre().equals(""))) {
      filtros.put("nombre", gestionarCategoriasMedicionForm.getFiltroNombre());
    }

    PaginaLista paginaCategorias = strategosCategoriasService.getCategoriasMedicion(pagina, 30, atributoOrden, tipoOrden, true, filtros);

    paginaCategorias.setTamanoSetPaginas(5);

    request.setAttribute("paginaCategorias", paginaCategorias);

    strategosCategoriasService.close();

    if (paginaCategorias.getLista().size() > 0) {
      CategoriaMedicion categoriaMedicion = (CategoriaMedicion)paginaCategorias.getLista().get(0);
      gestionarCategoriasMedicionForm.setSeleccionados(categoriaMedicion.getCategoriaId().toString());
      gestionarCategoriasMedicionForm.setValoresSeleccionados(categoriaMedicion.getNombre());
    } else {
      gestionarCategoriasMedicionForm.setSeleccionados(null);
    }

    return mapping.findForward(forward);
  }
}