package com.visiongc.app.strategos.web.struts.tipoproyecto.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
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

public class EditarTiposProyectoAction extends VgcAction
{
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

    String tipoProyectoId = request.getParameter("tipoProyectoId");

    boolean bloqueado = false;

    StrategosTipoProyectoService strategosTipoProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();

    if ((tipoProyectoId != null) && (!tipoProyectoId.equals("")) && (!tipoProyectoId.equals("0")))
    {
      bloqueado = !strategosTipoProyectoService.lockForUpdate(request.getSession().getId(), tipoProyectoId, null);

      editarTiposProyectoForm.setBloqueado(new Boolean(bloqueado));

      TipoProyecto tipoProyecto = (TipoProyecto)strategosTipoProyectoService.load(TipoProyecto.class, new Long(tipoProyectoId));

      if (tipoProyecto != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarTiposProyectoForm.setTipoProyectoId(tipoProyecto.getTipoProyectoId());
        editarTiposProyectoForm.setNombre(tipoProyecto.getNombre());
      }
      else
      {
        strategosTipoProyectoService.unlockObject(request.getSession().getId(), new Long(tipoProyectoId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarTiposProyectoForm.clear();
    }

    strategosTipoProyectoService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado"))
    {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}