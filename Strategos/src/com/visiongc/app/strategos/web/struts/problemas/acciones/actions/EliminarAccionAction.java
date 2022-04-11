package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarAccionAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarAccionAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String accionId = request.getParameter("accionId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarAccionAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((accionId == null) || (accionId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(accionId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();

    strategosAccionesService.unlockObject(request.getSession().getId(), accionId);

    bloqueado = !strategosAccionesService.lockForDelete(request.getSession().getId(), accionId);

    Accion accion = (Accion)strategosAccionesService.load(Accion.class, new Long(accionId));

    if (accion != null) {
      if ((accion.getHijos() == null) || ((accion.getHijos() != null) && (accion.getHijos().size() == 0))) {
        if (accion.getPadreId() != null) {
          if (!accion.getReadOnly().booleanValue())
          {
            if (bloqueado)
            {
              messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", accion.getNombre()));
            }
            else
            {
              strategosAccionesService.unlockObject(request.getSession().getId(), accionId);

              int res = strategosAccionesService.deleteAccion(accion, getUsuarioConectado(request));

              if (res == 10004)
              {
                messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", accion.getNombre()));
              }
              else {
                messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", accion.getNombre()));

                Accion padre = accion.getPadre();
                request.setAttribute("GestionarAccionesAction.reloadId", padre.getAccionId());
              }
            }
          }
          else {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.sololectura", accion.getNombre()));
          }
        }
        else {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", accion.getNombre()));
        }
      }
      else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", accion.getNombre()));
      }
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosAccionesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarAccionAction.ultimoTs", accionId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}