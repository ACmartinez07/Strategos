package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
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

public class EditarConveniosAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarTiposConvenioForm editarConveniosForm = (EditarTiposConvenioForm)form;

    ActionMessages messages = getMessages(request);

    String convenioId = request.getParameter("convenioId");

    boolean bloqueado = false;

    StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();

    if ((convenioId != null) && (!convenioId.equals("")) && (!convenioId.equals("0")))
    {
      bloqueado = !strategosConveniosService.lockForUpdate(request.getSession().getId(), convenioId, null);

      editarConveniosForm.setBloqueado(new Boolean(bloqueado));

      TipoConvenio convenios = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(convenioId));

      if (convenios != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarConveniosForm.setTiposConvenioId(convenios.getTiposConvenioId());
        editarConveniosForm.setDescripcion(convenios.getDescripcion());
        editarConveniosForm.setNombre(convenios.getNombre());
      }
      else
      {
        strategosConveniosService.unlockObject(request.getSession().getId(), new Long(convenioId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarConveniosForm.clear();
    }

    strategosConveniosService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado"))
    {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}