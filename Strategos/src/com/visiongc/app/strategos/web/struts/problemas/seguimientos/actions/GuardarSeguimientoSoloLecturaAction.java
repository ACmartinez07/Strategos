package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.EditarSeguimientoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GuardarSeguimientoSoloLecturaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarSeguimientoSoloLecturaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarSeguimientoForm editarSeguimientoForm = (EditarSeguimientoForm)form;

    boolean cancelar = false;
    String ts = request.getParameter("ts");
    String seguimientoId = request.getParameter("seguimientoId");
    Boolean soloLectura = new Boolean(request.getParameter("soloLectura"));
    String ultimoTs = (String)request.getSession().getAttribute("GuardarSeguimientoSoloLecturaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    if (cancelar)
    {
      strategosSeguimientosService.unlockObject(request.getSession().getId(), editarSeguimientoForm.getSeguimientoId());

      strategosSeguimientosService.close();

      return getForwardBack(request, 1, true);
    }

    Seguimiento seguimiento = new Seguimiento();
    int respuesta = 10000;

    seguimiento = (Seguimiento)strategosSeguimientosService.load(Seguimiento.class, new Long(seguimientoId));

    seguimiento.setReadOnly(soloLectura);

    respuesta = strategosSeguimientosService.saveSeguimiento(seguimiento, getUsuarioConectado(request));

    if (respuesta == 10000) {
      strategosSeguimientosService.unlockObject(request.getSession().getId(), editarSeguimientoForm.getSeguimientoId());
    }

    strategosSeguimientosService.close();

    request.getSession().setAttribute("GuardarSeguimientoSoloLecturaAction.ultimoTs", ts);

    return mapping.findForward(forward);
  }
}