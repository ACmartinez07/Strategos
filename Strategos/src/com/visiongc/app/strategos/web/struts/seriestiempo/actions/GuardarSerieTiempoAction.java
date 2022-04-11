package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.seriestiempo.forms.EditarSerieTiempoForm;
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

public class GuardarSerieTiempoAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarSerieTiempoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarSerieTiempoForm editarSerieTiempoForm = (EditarSerieTiempoForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarSerieTiempoAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

    if (cancelar)
    {
      strategosSeriesTiempoService.unlockObject(request.getSession().getId(), editarSerieTiempoForm.getSerieId());

      strategosSeriesTiempoService.close();

      return getForwardBack(request, 1, true);
    }

    SerieTiempo serieTiempo = new SerieTiempo();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarSerieTiempoForm.getSerieId() != null) && (!editarSerieTiempoForm.getSerieId().equals(Long.valueOf("0"))))
    {
      serieTiempo = (SerieTiempo)strategosSeriesTiempoService.load(SerieTiempo.class, editarSerieTiempoForm.getSerieId());
    }
    else
    {
      nuevo = true;
      serieTiempo = new SerieTiempo();
    }

    serieTiempo.setNombre(editarSerieTiempoForm.getNombre());
    serieTiempo.setIdentificador(editarSerieTiempoForm.getIdentificador());
    serieTiempo.setFija(editarSerieTiempoForm.getFija());
    serieTiempo.setPreseleccionada(editarSerieTiempoForm.getPreseleccionada());
    serieTiempo.setOculta(new Boolean(false));

    respuesta = strategosSeriesTiempoService.saveSerieTiempo(serieTiempo, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosSeriesTiempoService.unlockObject(request.getSession().getId(), editarSerieTiempoForm.getSerieId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearSerieTiempo";
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

    strategosSeriesTiempoService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarSerieTiempoAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}