package com.visiongc.app.strategos.web.struts.causas.actions;

import com.visiongc.app.strategos.causas.StrategosCausasService;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.causas.forms.EditarCausaForm;
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

public class GuardarCausaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarCausaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarCausaForm editarCausaForm = (EditarCausaForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarCausaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosCausasService strategosCausasService = StrategosServiceFactory.getInstance().openStrategosCausasService();

    if (cancelar)
    {
      strategosCausasService.unlockObject(request.getSession().getId(), editarCausaForm.getCausaId());

      strategosCausasService.close();

      return getForwardBack(request, 1, true);
    }

    Causa causa = new Causa();
    boolean nuevo = false;

    causa.setCausaId(editarCausaForm.getCausaId());

    if ((editarCausaForm.getCausaId() != null) && (!editarCausaForm.getCausaId().equals(Long.valueOf("0"))))
    {
      causa = (Causa)strategosCausasService.load(Causa.class, editarCausaForm.getCausaId());
    }
    else
    {
      nuevo = true;
      causa = new Causa();
      causa.setCausaId(new Long(0L));

      Causa causaPadre = (Causa)strategosCausasService.load(Causa.class, editarCausaForm.getPadreId());
      causa.setNivel(new Integer(causaPadre.getNivel().intValue() + 1));
      causa.setPadreId(causaPadre.getCausaId());
    }

    causa.setNombre(editarCausaForm.getNombre());

    if ((editarCausaForm.getDescripcion() != null) && (!editarCausaForm.getDescripcion().equals("")))
      causa.setDescripcion(editarCausaForm.getDescripcion());
    else {
      causa.setDescripcion(null);
    }

    int respuesta = 10000;
    respuesta = strategosCausasService.saveCausa(causa, getUsuarioConectado(request));

    if (respuesta == 10000) {
      strategosCausasService.unlockObject(request.getSession().getId(), editarCausaForm.getCausaId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearCausa";
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

    strategosCausasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarCausaAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}