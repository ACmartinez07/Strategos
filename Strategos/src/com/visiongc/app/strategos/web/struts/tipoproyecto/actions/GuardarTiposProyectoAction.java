package com.visiongc.app.strategos.web.struts.tipoproyecto.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.EditarCategoriaMedicionForm;
import com.visiongc.app.strategos.web.struts.tipoproyecto.forms.EditarTiposProyectoForm;
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

public class GuardarTiposProyectoAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarTiposProyectoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarTiposProyectoForm editarTiposProyectoForm = (EditarTiposProyectoForm)form;
    
    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarTiposProyectoAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosTipoProyectoService strategosTipoProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();

    if (cancelar)
    {
      strategosTipoProyectoService.unlockObject(request.getSession().getId(), editarTiposProyectoForm);

      strategosTipoProyectoService.close();

      return getForwardBack(request, 1, true);
    }

    TipoProyecto tipoProyecto = new TipoProyecto();
    boolean nuevo = false;
    int respuesta = 10000;

    tipoProyecto.setTipoProyectoId(editarTiposProyectoForm.getTipoProyectoId());
    tipoProyecto.setNombre(editarTiposProyectoForm.getNombre());

    if ((editarTiposProyectoForm.getTipoProyectoId() != null) && (!editarTiposProyectoForm.getTipoProyectoId().equals(Long.valueOf("0"))))
    {
      tipoProyecto = (TipoProyecto)strategosTipoProyectoService.load(TipoProyecto.class, editarTiposProyectoForm.getTipoProyectoId());
    }
    else
    {
      nuevo = true;
      tipoProyecto = new TipoProyecto();
      tipoProyecto.setTipoProyectoId(new Long(0L));
    }

    tipoProyecto.setNombre(editarTiposProyectoForm.getNombre());

    respuesta = strategosTipoProyectoService.saveTipoProyecto(tipoProyecto, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosTipoProyectoService.unlockObject(request.getSession().getId(), editarTiposProyectoForm.getTipoProyectoId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearTiposProyecto";
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

    strategosTipoProyectoService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarTiposProyectoAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}