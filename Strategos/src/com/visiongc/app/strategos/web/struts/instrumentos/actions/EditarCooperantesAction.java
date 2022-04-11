package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
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

public class EditarCooperantesAction extends VgcAction
{
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

    String cooperanteId = request.getParameter("cooperanteId");

    boolean bloqueado = false;

    StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();

    if ((cooperanteId != null) && (!cooperanteId.equals("")) && (!cooperanteId.equals("0")))
    {
      bloqueado = !strategosCooperantesService.lockForUpdate(request.getSession().getId(), cooperanteId, null);

      editarCooperantesForm.setBloqueado(new Boolean(bloqueado));

      Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(cooperanteId));

      if (cooperante != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarCooperantesForm.setCooperanteId(cooperante.getCooperanteId());
        editarCooperantesForm.setNombre(cooperante.getNombre());
        editarCooperantesForm.setDescripcion(cooperante.getDescripcion());
        editarCooperantesForm.setPais(cooperante.getPais());
      }
      else
      {
        strategosCooperantesService.unlockObject(request.getSession().getId(), new Long(cooperanteId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarCooperantesForm.clear();
    }

    strategosCooperantesService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado"))
    {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}