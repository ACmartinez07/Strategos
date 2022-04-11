package com.visiongc.app.strategos.web.struts.plancuentas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.model.Cuenta;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarCuentaAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarCuenta";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String cuentaId = request.getParameter("cuentaId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarCuenta.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((cuentaId == null) || (cuentaId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(cuentaId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosCuentasService strategosCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();

    strategosCuentasService.unlockObject(request.getSession().getId(), cuentaId);

    bloqueado = !strategosCuentasService.lockForDelete(request.getSession().getId(), cuentaId);

    Cuenta cuenta = (Cuenta)strategosCuentasService.load(Cuenta.class, new Long(cuentaId));

    if (cuenta != null) {
      if ((cuenta.getHijos() == null) || ((cuenta.getHijos() != null) && (cuenta.getHijos().size() == 0))) {
        if (cuenta.getPadre() != null)
        {
          if (bloqueado)
          {
            messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", cuenta.getDescripcion()));
          }
          else
          {
            Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
            int res = strategosCuentasService.deleteCuenta(cuenta, usuario);

            strategosCuentasService.unlockObject(request.getSession().getId(), cuentaId);

            if (res == 10004)
            {
              messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", cuenta.getDescripcion()));
            }
            else {
              messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", cuenta.getDescripcion()));

              Cuenta padre = cuenta.getPadre();
              request.setAttribute("GestionarCuentasAction.reloadId", padre.getCuentaId());
            }
          }

        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", cuenta.getDescripcion()));
        }
      }
      else {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", cuenta.getDescripcion()));
      }
    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosCuentasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarCuenta.ultimoTs", cuentaId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}