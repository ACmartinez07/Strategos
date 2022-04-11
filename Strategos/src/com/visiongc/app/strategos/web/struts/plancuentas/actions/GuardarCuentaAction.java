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

public final class GuardarCuentaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarCuentaAction";

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

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarCuentaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosCuentasService strategosCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();

    if (cancelar)
    {
      strategosCuentasService.unlockObject(request.getSession().getId(), editarCuentaForm.getCuentaId());

      strategosCuentasService.close();

      return getForwardBack(request, 1, true);
    }

    Cuenta cuenta = new Cuenta();
    boolean nuevo = false;
    int respuesta = 10000;

    cuenta.setCuentaId(editarCuentaForm.getCuentaId());

    if ((editarCuentaForm.getCuentaId() != null) && (!editarCuentaForm.getCuentaId().equals(Long.valueOf("0"))))
    {
      cuenta = (Cuenta)strategosCuentasService.load(Cuenta.class, editarCuentaForm.getCuentaId());
    }
    else
    {
      nuevo = true;
      cuenta = new Cuenta();
      cuenta.setCuentaId(new Long(0L));

      Cuenta cuentaPadre = (Cuenta)strategosCuentasService.load(Cuenta.class, editarCuentaForm.getPadreId());
      cuenta.setPadreId(cuentaPadre.getCuentaId());
    }

    if (cuenta.getPadreId() != null) {
      cuenta.setCodigo(editarCuentaForm.getCodigo());
    }
    cuenta.setDescripcion(editarCuentaForm.getDescripcion());

    respuesta = strategosCuentasService.saveCuenta(cuenta, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosCuentasService.unlockObject(request.getSession().getId(), null);
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearCuenta";
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

    strategosCuentasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarCuentaAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}