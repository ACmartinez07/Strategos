package com.visiongc.app.strategos.web.struts.causas.actions;

import com.visiongc.app.strategos.causas.StrategosCausasService;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.causas.forms.EditarCausaForm;
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

public class EditarCausaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarCausaForm editarCausaForm = (EditarCausaForm)form;

    ActionMessages messages = getMessages(request);

    String causaId = request.getParameter("causaId");

    StrategosCausasService strategosCausasService = StrategosServiceFactory.getInstance().openStrategosCausasService();

    boolean bloqueado = false;

    if ((causaId != null) && (!causaId.equals("")) && (!causaId.equals("0")))
    {
      bloqueado = !strategosCausasService.lockForUpdate(request.getSession().getId(), causaId, null);
      editarCausaForm.setBloqueado(new Boolean(bloqueado));
      Causa causa = (Causa)strategosCausasService.load(Causa.class, new Long(causaId));

      if (causa != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        Causa padre = causa.getPadre();
        long padreId = 0L;
        if (padre != null) {
          padreId = padre.getCausaId().longValue();
        }

        editarCausaForm.setPadreId(new Long(padreId));
        editarCausaForm.setCausaId(causa.getCausaId());
        editarCausaForm.setNombre(causa.getNombre());
        editarCausaForm.setDescripcion(causa.getDescripcion());
        editarCausaForm.setNivel(causa.getNivel());
      }
      else
      {
        strategosCausasService.unlockObject(request.getSession().getId(), new Long(causaId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarCausaForm.clear();
      Causa padre = (Causa)request.getSession().getAttribute("causa");
      editarCausaForm.setPadreId(padre.getCausaId());
    }

    strategosCausasService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado"))
    {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}