package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.EditarCategoriaMedicionForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarTiposConvenioForm;
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

public class GuardarConveniosAction extends VgcAction
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

    EditarTiposConvenioForm editarTiposConvenioForm = (EditarTiposConvenioForm)form;
    
    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarConveniosAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }
    
    StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();

    if (cancelar)
    {
      strategosConveniosService.unlockObject(request.getSession().getId(), editarTiposConvenioForm);

      strategosConveniosService.close();

      return getForwardBack(request, 1, true);
    }

    TipoConvenio convenio = new com.visiongc.app.strategos.instrumentos.model.TipoConvenio();
    boolean nuevo = false;
    int respuesta = 10000;

    convenio.setTiposConvenioId(editarTiposConvenioForm.getTiposConvenioId());
    convenio.setDescripcion(editarTiposConvenioForm.getDescripcion());
    convenio.setNombre(editarTiposConvenioForm.getNombre());

    if ((editarTiposConvenioForm.getTiposConvenioId() != null) && (!editarTiposConvenioForm.getTiposConvenioId().equals(Long.valueOf("0"))))
    {
      convenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, editarTiposConvenioForm.getTiposConvenioId());
    }
    else
    {
      nuevo = true;
      convenio = new TipoConvenio();
      convenio.setTiposConvenioId(new Long(0L));
    }

    convenio.setDescripcion(editarTiposConvenioForm.getDescripcion());
    convenio.setNombre(editarTiposConvenioForm.getNombre());

    respuesta = strategosConveniosService.saveTiposConvenio(convenio, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosConveniosService.unlockObject(request.getSession().getId(), editarTiposConvenioForm.getTiposConvenioId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearConvenio";
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

    strategosConveniosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarConveniosAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}