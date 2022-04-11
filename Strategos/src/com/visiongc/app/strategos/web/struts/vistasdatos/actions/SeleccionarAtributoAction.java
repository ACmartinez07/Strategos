package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.SeleccionarAtributoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarAtributoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SeleccionarAtributoForm seleccionarAtributoForm = (SeleccionarAtributoForm)form;

    String atributoOrden = seleccionarAtributoForm
      .getAtributoOrden();
    String tipoOrden = seleccionarAtributoForm.getTipoOrden();

    seleccionarAtributoForm.setFuncionCierre(request
      .getParameter("funcionCierre"));

    if (atributoOrden == null) {
      atributoOrden = "nombre";
      seleccionarAtributoForm.setAtributoOrden(atributoOrden);
    }
    if (tipoOrden == null) {
      tipoOrden = "ASC";
      seleccionarAtributoForm.setTipoOrden(tipoOrden);
    }

    if (seleccionarAtributoForm.getFuncionCierre() != null) {
      if (!seleccionarAtributoForm.getFuncionCierre().equals("")) {
        if (seleccionarAtributoForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarAtributoForm
            .setFuncionCierre(seleccionarAtributoForm
            .getFuncionCierre() + 
            ";");
      }
      else {
        seleccionarAtributoForm.setFuncionCierre(null);
      }

    }

    if (request.getParameter("seleccionMultiple") != null) {
      seleccionarAtributoForm.setSeleccionMultiple(
        new Byte(request.getParameter("seleccionMultiple")));
    }

    seleccionarAtributoForm
      .setListaAtributos(getListaAtributos(seleccionarAtributoForm
      .getFiltroNombre()));

    if (seleccionarAtributoForm.getListaAtributos().size() > 0) {
      seleccionarAtributoForm.setSeleccionados(((ObjetoValorNombre)seleccionarAtributoForm.getListaAtributos().get(0)).getValor());
      seleccionarAtributoForm.setValoresSeleccionados(((ObjetoValorNombre)seleccionarAtributoForm.getListaAtributos().get(0)).getNombre());
    }

    return mapping.findForward(forward);
  }

  private List getListaAtributos(String filtroNombre)
  {
    List listaAtributos = new ArrayList();
    List atributos = TipoAtributo.getTiposAtributos();

    for (int i = 0; i < atributos.size(); i++) {
      TipoAtributo tipoAtributo = (TipoAtributo)atributos.get(i);
      ObjetoValorNombre elementoAtributo = new ObjetoValorNombre();
      elementoAtributo.setNombre(tipoAtributo.getNombre());
      elementoAtributo.setValor(tipoAtributo.getTipoAtributoId().toString());

      if ((filtroNombre != null) && (!filtroNombre.equals(""))) {
        if (tipoAtributo.getNombre().toLowerCase().indexOf(filtroNombre.toLowerCase()) > -1)
          listaAtributos.add(elementoAtributo);
      }
      else {
        listaAtributos.add(elementoAtributo);
      }
    }

    return listaAtributos;
  }
}