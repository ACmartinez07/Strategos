package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.EditarAccionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GuardarAccionSoloLecturaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarAccionSoloLecturaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarAccionForm editarAccionForm = (EditarAccionForm)form;

    boolean cancelar = false;
    String ts = request.getParameter("ts");
    String accionId = request.getParameter("accionId");
    Boolean soloLectura = new Boolean(request.getParameter("soloLectura"));
    String ultimoTs = (String)request.getSession().getAttribute("GuardarAccionSoloLecturaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();

    if (cancelar)
    {
      strategosAccionesService.unlockObject(request.getSession().getId(), editarAccionForm.getAccionId());

      strategosAccionesService.close();

      return getForwardBack(request, 1, true);
    }

    Accion accion = new Accion();
    int respuesta = 10000;

    accion = (Accion)strategosAccionesService.load(Accion.class, new Long(accionId));

    accion.setReadOnly(soloLectura);

    respuesta = strategosAccionesService.saveAccion(accion, getUsuarioConectado(request));

    if (respuesta == 10000) {
      strategosAccionesService.unlockObject(request.getSession().getId(), editarAccionForm.getAccionId());
    }

    strategosAccionesService.close();

    request.getSession().setAttribute("GuardarAccionSoloLecturaAction.ultimoTs", ts);

    return mapping.findForward(forward);
  }
}