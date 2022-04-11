package com.visiongc.app.strategos.web.struts.foros.actions;

import com.visiongc.app.strategos.foros.StrategosForosService;
import com.visiongc.app.strategos.foros.model.Foro;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.foros.forms.EditarForoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarForoAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarForoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarForoForm editarForoForm = (EditarForoForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarForoAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosForosService strategosForosService = StrategosServiceFactory.getInstance().openStrategosForosService();

    Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    Foro foro = new Foro();
    int respuesta = 10000;

    foro = new Foro();
    foro.setForoId(new Long(0L));
    if ((editarForoForm.getPadreId() == null) || (editarForoForm.getPadreId().byteValue() == 0))
      foro.setPadreId(null);
    else {
      foro.setPadreId(editarForoForm.getPadreId());
    }
    foro.setObjetoKey(editarForoForm.getObjetoKey());
    foro.setObjetoId(editarForoForm.getObjetoId());
    foro.setAsunto(editarForoForm.getAsunto());
    foro.setEmail(editarForoForm.getEmail());
    foro.setComentario(editarForoForm.getComentario());
    foro.setTipo(editarForoForm.getTipo());

    respuesta = strategosForosService.saveForo(foro, usuario);

    if (respuesta == 10000) {
      strategosForosService.unlockObject(request.getSession().getId(), editarForoForm.getForoId());
      forward = "exito";

      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
      forward = "crearForo";
    } else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosForosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarForoAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}