package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Problema;
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

public class EliminarProblemaAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarProblemaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String problemaId = request.getParameter("problemaId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarProblemaAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((problemaId == null) || (problemaId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(problemaId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

    bloqueado = !strategosProblemasService.lockForDelete(request.getSession().getId(), problemaId);

    Problema problema = (Problema)strategosProblemasService.load(Problema.class, new Long(problemaId));

    if (problema != null) {
      if (!problema.getReadOnly().booleanValue())
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", problema.getNombre()));
        }
        else
        {
          problema.setProblemaId(Long.valueOf(problemaId));
          int res = strategosProblemasService.deleteProblema(problema, getUsuarioConectado(request));

          strategosProblemasService.unlockObject(request.getSession().getId(), problemaId);

          if (res == 10004)
          {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", problema.getNombre()));
          }
          else {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", problema.getNombre()));
          }
        }

      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.sololectura", problema.getNombre()));
      }
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosProblemasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarProblemaAction.ultimoTs", problemaId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}