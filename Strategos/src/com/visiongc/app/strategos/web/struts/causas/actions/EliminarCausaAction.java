package com.visiongc.app.strategos.web.struts.causas.actions;

import com.visiongc.app.strategos.causas.StrategosCausasService;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
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

public class EliminarCausaAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarCausaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String causaId = request.getParameter("causaId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarCausaAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((causaId == null) || (causaId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(causaId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosCausasService strategosCausasService = StrategosServiceFactory.getInstance().openStrategosCausasService();

    strategosCausasService.unlockObject(request.getSession().getId(), causaId);

    bloqueado = !strategosCausasService.lockForDelete(request.getSession().getId(), causaId);

    Causa causa = (Causa)strategosCausasService.load(Causa.class, new Long(causaId));

    if (causa != null) {
      if ((causa.getHijos() == null) || ((causa.getHijos() != null) && (causa.getHijos().size() == 0))) {
        if (causa.getPadreId() != null)
        {
          if (bloqueado)
          {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", causa.getNombre()));
          }
          else
          {
            strategosCausasService.unlockObject(request.getSession().getId(), causaId);

            int res = strategosCausasService.deleteCausa(causa, getUsuarioConectado(request));

            if (res == 10004)
            {
              messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", causa.getNombre()));
            }
            else {
              messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", causa.getNombre()));

              Causa padre = causa.getPadre();
              request.setAttribute("GestionarCausasAction.reloadId", padre.getCausaId());
            }
          }
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", causa.getNombre()));
        }
      }
      else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", causa.getNombre()));
      }
    }
    else {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosCausasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarCausaAction.ultimoTs", causaId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}