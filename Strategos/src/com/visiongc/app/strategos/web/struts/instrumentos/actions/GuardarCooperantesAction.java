package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.EditarCategoriaMedicionForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarCooperantesForm;
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

public class GuardarCooperantesAction extends VgcAction
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

    EditarCooperantesForm editarCooperantesForm = (EditarCooperantesForm)form;
    
    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarCooperantesAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();

    if (cancelar)
    {
      strategosCooperantesService.unlockObject(request.getSession().getId(), editarCooperantesForm);

      strategosCooperantesService.close();

      return getForwardBack(request, 1, true);
    }

    Cooperante cooperante = new Cooperante();
    boolean nuevo = false;
    int respuesta = 10000;

    cooperante.setCooperanteId(editarCooperantesForm.getCooperanteId());
    cooperante.setNombre(editarCooperantesForm.getNombre());

    if ((editarCooperantesForm.getCooperanteId() != null) && (!editarCooperantesForm.getCooperanteId().equals(Long.valueOf("0"))))
    {
      cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, editarCooperantesForm.getCooperanteId());
    }
    else
    {
      nuevo = true;
      cooperante = new Cooperante();
      cooperante.setCooperanteId(new Long(0L));
    }

    cooperante.setNombre(editarCooperantesForm.getNombre());
    cooperante.setDescripcion(editarCooperantesForm.getDescripcion());
    cooperante.setPais(editarCooperantesForm.getPais());

    respuesta = strategosCooperantesService.saveCooperantes(cooperante, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosCooperantesService.unlockObject(request.getSession().getId(), editarCooperantesForm.getCooperanteId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearCooperantes";
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

    strategosCooperantesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarCooperantesAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}