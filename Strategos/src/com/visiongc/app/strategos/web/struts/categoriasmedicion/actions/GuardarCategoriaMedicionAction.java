package com.visiongc.app.strategos.web.struts.categoriasmedicion.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.EditarCategoriaMedicionForm;
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

public class GuardarCategoriaMedicionAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarCategoriaMedicionAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarCategoriaMedicionForm editarCategoriaMedicionForm = (EditarCategoriaMedicionForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarCategoriaMedicionAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();

    if (cancelar)
    {
      strategosCategoriasService.unlockObject(request.getSession().getId(), editarCategoriaMedicionForm.getCategoriaId());

      strategosCategoriasService.close();

      return getForwardBack(request, 1, true);
    }

    CategoriaMedicion categoriaMedicion = new CategoriaMedicion();
    boolean nuevo = false;
    int respuesta = 10000;

    categoriaMedicion.setCategoriaId(editarCategoriaMedicionForm.getCategoriaId());
    categoriaMedicion.setNombre(editarCategoriaMedicionForm.getNombre());

    if ((editarCategoriaMedicionForm.getCategoriaId() != null) && (!editarCategoriaMedicionForm.getCategoriaId().equals(Long.valueOf("0"))))
    {
      categoriaMedicion = (CategoriaMedicion)strategosCategoriasService.load(CategoriaMedicion.class, editarCategoriaMedicionForm.getCategoriaId());
    }
    else
    {
      nuevo = true;
      categoriaMedicion = new CategoriaMedicion();
      categoriaMedicion.setCategoriaId(new Long(0L));
    }

    categoriaMedicion.setNombre(editarCategoriaMedicionForm.getNombre());

    respuesta = strategosCategoriasService.saveCategoriaMedicion(categoriaMedicion, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosCategoriasService.unlockObject(request.getSession().getId(), editarCategoriaMedicionForm.getCategoriaId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearCategoriaMedicion";
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

    strategosCategoriasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarCategoriaMedicionAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}