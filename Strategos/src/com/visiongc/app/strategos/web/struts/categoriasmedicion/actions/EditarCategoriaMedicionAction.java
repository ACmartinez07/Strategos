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

public class EditarCategoriaMedicionAction extends VgcAction
{
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

    String categoriaId = request.getParameter("categoriaId");

    boolean bloqueado = false;

    StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();

    if ((categoriaId != null) && (!categoriaId.equals("")) && (!categoriaId.equals("0")))
    {
      bloqueado = !strategosCategoriasService.lockForUpdate(request.getSession().getId(), categoriaId, null);

      editarCategoriaMedicionForm.setBloqueado(new Boolean(bloqueado));

      CategoriaMedicion categoriaMedicion = (CategoriaMedicion)strategosCategoriasService.load(CategoriaMedicion.class, new Long(categoriaId));

      if (categoriaMedicion != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarCategoriaMedicionForm.setCategoriaId(categoriaMedicion.getCategoriaId());
        editarCategoriaMedicionForm.setNombre(categoriaMedicion.getNombre());
      }
      else
      {
        strategosCategoriasService.unlockObject(request.getSession().getId(), new Long(categoriaId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarCategoriaMedicionForm.clear();
    }

    strategosCategoriasService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado"))
    {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}