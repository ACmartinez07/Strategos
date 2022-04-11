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

public class EditarUnidadMedidaAction extends VgcAction
{
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

    String unidadId = request.getParameter("unidadId");

    boolean bloqueado = false;

    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

    if ((unidadId != null) && (!unidadId.equals("")) && (!unidadId.equals("0")))
    {
      bloqueado = !strategosUnidadesService.lockForUpdate(request.getSession().getId(), unidadId, null);

      editarUnidadMedidaForm.setBloqueado(new Boolean(bloqueado));

      UnidadMedida unidadMedida = (UnidadMedida)strategosUnidadesService.load(UnidadMedida.class, new Long(unidadId));

      if (unidadMedida != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarUnidadMedidaForm.setUnidadId(unidadMedida.getUnidadId());
        editarUnidadMedidaForm.setNombre(unidadMedida.getNombre());
        editarUnidadMedidaForm.setTipo(unidadMedida.getTipo());
      }
      else
      {
        strategosUnidadesService.unlockObject(request.getSession().getId(), new Long(unidadId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarUnidadMedidaForm.clear();
    }

    strategosUnidadesService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}