package com.visiongc.app.strategos.web.struts.presentaciones.vistas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.EditarVistaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarVistaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarVistaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarVistaForm editarVistaForm = (EditarVistaForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarVistaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();

    if (cancelar)
    {
      strategosVistasService.unlockObject(request.getSession().getId(), editarVistaForm.getVistaId());

      strategosVistasService.close();

      return getForwardBack(request, 1, true);
    }

    Vista vista = new Vista();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarVistaForm.getVistaId() != null) && (!editarVistaForm.getVistaId().equals(Long.valueOf("0"))))
    {
      vista = (Vista)strategosVistasService.load(Vista.class, editarVistaForm.getVistaId());
    }
    else
    {
      nuevo = true;
      vista = new Vista();
      vista.setVistaId(new Long(0L));
      vista.setOrganizacionId(new Long(organizacionId));
    }

    vista.setNombre(editarVistaForm.getNombre());

    if ((editarVistaForm.getDescripcion() != null) && (!editarVistaForm.getDescripcion().equals("")))
      vista.setDescripcion(editarVistaForm.getDescripcion());
    else {
      vista.setDescripcion(null);
    }

    if ((editarVistaForm.getMesInicio() != null) && (!editarVistaForm.getMesInicio().equals("")) && (editarVistaForm.getAnoInicio() != null) && (!editarVistaForm.getAnoInicio().equals("")))
      vista.setFechaInicio(editarVistaForm.getMesInicio().toString() + EditarVistaForm.getSeparadorPeriodos() + editarVistaForm.getAnoInicio().toString());
    else {
      vista.setFechaInicio(null);
    }

    if ((editarVistaForm.getMesFinal() != null) && (!editarVistaForm.getMesFinal().equals("")) && (editarVistaForm.getAnoFinal() != null) && (!editarVistaForm.getAnoFinal().equals("")))
      vista.setFechaFin(editarVistaForm.getMesFinal().toString() + EditarVistaForm.getSeparadorPeriodos() + editarVistaForm.getAnoFinal().toString());
    else {
      vista.setFechaFin(null);
    }

    if ((editarVistaForm.getVisible() != null) && (!editarVistaForm.getVisible().equals("")))
      vista.setVisible(editarVistaForm.getVisible());
    else {
      vista.setVisible(null);
    }

    respuesta = strategosVistasService.saveVista(vista, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosVistasService.unlockObject(request.getSession().getId(), editarVistaForm.getVistaId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearVista";
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }

    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosVistasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarVistaAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}