package com.visiongc.app.strategos.web.struts.unidadesmedida.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.web.struts.unidadesmedida.forms.EditarUnidadMedidaForm;
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

public class GuardarUnidadMedidaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarUnidadMedidaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarUnidadMedidaForm editarUnidadMedidaForm = (EditarUnidadMedidaForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarUnidadMedidaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

    if (cancelar)
    {
      strategosUnidadesService.unlockObject(request.getSession().getId(), editarUnidadMedidaForm.getUnidadId());

      strategosUnidadesService.close();

      return getForwardBack(request, 1, true);
    }

    UnidadMedida unidadMedida = new UnidadMedida();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarUnidadMedidaForm.getUnidadId() != null) && (!editarUnidadMedidaForm.getUnidadId().equals(Long.valueOf("0"))))
    {
      unidadMedida = (UnidadMedida)strategosUnidadesService.load(UnidadMedida.class, editarUnidadMedidaForm.getUnidadId());
    }
    else
    {
      nuevo = true;
      unidadMedida = new UnidadMedida();
      unidadMedida.setUnidadId(new Long(0L));
    }

    unidadMedida.setNombre(editarUnidadMedidaForm.getNombre());
    unidadMedida.setTipo(editarUnidadMedidaForm.getTipo());

    respuesta = strategosUnidadesService.saveUnidadMedida(unidadMedida, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosUnidadesService.unlockObject(request.getSession().getId(), editarUnidadMedidaForm.getUnidadId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearUnidadMedida";
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

    strategosUnidadesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarUnidadMedidaAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}