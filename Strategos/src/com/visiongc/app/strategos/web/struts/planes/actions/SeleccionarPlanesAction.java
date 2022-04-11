package com.visiongc.app.strategos.web.struts.planes.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.forms.SeleccionarPlanesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarPlanesAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    if (request.getParameter("funcion") != null) {
      String funcion = request.getParameter("funcion");
      if (funcion.equals("getRutaCompletaPlanesSeleccionados")) {
        getRutaCompletaPlanesSeleccionados(request);
        return mapping.findForward("ajaxResponse");
      }
    }

    String actualizar = request.getParameter("actualizar");
    if ((actualizar != null) && (actualizar.equalsIgnoreCase("true")))
    {
      return mapping.findForward(forward);
    }

    SeleccionarPlanesForm seleccionarPlanesForm = (SeleccionarPlanesForm)form;

    seleccionarPlanesForm.clear();

    seleccionarPlanesForm.setNombreForma(request.getParameter("nombreForma"));
    seleccionarPlanesForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
    seleccionarPlanesForm.setNombreCampoValor(request.getParameter("nombreCampoValor"));
    seleccionarPlanesForm.setNombreCampoRutasCompletas(request.getParameter("nombreCampoRutasCompletas"));
    seleccionarPlanesForm.setSeleccionados(request.getParameter("seleccionados"));
    seleccionarPlanesForm.setFuncionCierre(request.getParameter("funcionCierre"));

    String permitirCambiarOrganizacion = request.getParameter("permitirCambiarOrganizacion");
    String organizacionId = request.getParameter("organizacionId");
    String mostrarSoloPlanesActivos = request.getParameter("mostrarSoloPlanesActivos");

    if ((permitirCambiarOrganizacion != null) && (permitirCambiarOrganizacion.equalsIgnoreCase("true"))) {
      seleccionarPlanesForm.setPermitirCambiarOrganizacion(new Boolean(true));
    }
    if ((organizacionId != null) && (!organizacionId.equals("")) && (!organizacionId.equals("0")))
      seleccionarPlanesForm.setOrganizacionSeleccionadaId(new Long(organizacionId));
    else {
      seleccionarPlanesForm.setOrganizacionSeleccionadaId(new Long((String)request.getSession().getAttribute("organizacionId")));
    }
    if ((mostrarSoloPlanesActivos != null) && (mostrarSoloPlanesActivos.equalsIgnoreCase("true"))) {
      seleccionarPlanesForm.setMostrarSoloPlanesActivos(new Boolean(true));
    }
    if (seleccionarPlanesForm.getFuncionCierre() != null) {
      if (!seleccionarPlanesForm.getFuncionCierre().equals("")) {
        if (seleccionarPlanesForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarPlanesForm.setFuncionCierre(seleccionarPlanesForm.getFuncionCierre() + ";");
      }
      else {
        seleccionarPlanesForm.setFuncionCierre(null);
      }

    }

    return mapping.findForward(forward);
  }

  private void getRutaCompletaPlanesSeleccionados(HttpServletRequest request)
  {
    String seleccionados = request.getParameter("seleccionados");
    String rutasCompletasPlanesSeleccionados = "";
    String[] arregloPlanesSeleccionados = seleccionados.split("!;!");

    StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

    for (int i = 0; i < arregloPlanesSeleccionados.length; i++) {
      String seleccionadoId = arregloPlanesSeleccionados[i];
      String rutaCompletaPlanSeleccionado = "";
      Plan plan = (Plan)planesService.load(Plan.class, new Long(seleccionadoId));
      if (plan != null)
        rutaCompletaPlanSeleccionado = plan.getOrganizacion().getNombre();
      else {
        rutaCompletaPlanSeleccionado = "!ELIMINADO!";
      }
      rutasCompletasPlanesSeleccionados = rutasCompletasPlanesSeleccionados + "!;!" + rutaCompletaPlanSeleccionado;
    }
    rutasCompletasPlanesSeleccionados = rutasCompletasPlanesSeleccionados.substring("!;!".length());
    planesService.close();

    request.setAttribute("ajaxResponse", rutasCompletasPlanesSeleccionados);
  }
}