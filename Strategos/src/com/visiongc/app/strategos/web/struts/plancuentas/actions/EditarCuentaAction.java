package com.visiongc.app.strategos.web.struts.plancuentas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.model.Cuenta;
import com.visiongc.app.strategos.web.struts.plancuentas.forms.EditarCuentaForm;
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

public final class EditarCuentaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarCuentaForm editarCuentaForm = (EditarCuentaForm)form;

    ActionMessages messages = getMessages(request);

    String cuentaId = request.getParameter("cuentaId");

    boolean bloqueado = false;

    StrategosCuentasService strategosCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();

    if ((cuentaId != null) && (!cuentaId.equals("")) && (!cuentaId.equals("0")))
    {
      bloqueado = !strategosCuentasService.lockForUpdate(request.getSession().getId(), cuentaId, null);

      editarCuentaForm.setBloqueado(new Boolean(bloqueado));

      Cuenta cuenta = (Cuenta)strategosCuentasService.load(Cuenta.class, new Long(cuentaId));

      if (cuenta != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        Cuenta padre = cuenta.getPadre();
        long padreId = 0L;
        if (padre != null)
        {
          padreId = padre.getCuentaId().longValue();
        }

        editarCuentaForm.setCuentaId(cuenta.getCuentaId());
        editarCuentaForm.setPadreId(new Long(padreId));
        editarCuentaForm.setCodigo(cuenta.getCodigo());
        editarCuentaForm.setDescripcion(cuenta.getDescripcion());
      }
      else
      {
        strategosCuentasService.unlockObject(request.getSession().getId(), new Long(cuentaId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarCuentaForm.clear();
      Cuenta padre = (Cuenta)request.getSession().getAttribute("cuenta");
      editarCuentaForm.setPadreId(padre.getCuentaId());
    }

    strategosCuentasService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}