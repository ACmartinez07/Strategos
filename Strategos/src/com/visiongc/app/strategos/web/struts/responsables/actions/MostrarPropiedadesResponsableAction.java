package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.responsables.forms.EditarResponsableForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesResponsableAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarResponsableForm editarResponsableForm = (EditarResponsableForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;

    StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

    if (cancelar)
    {
      strategosResponsablesService.unlockObject(request.getSession().getId(), editarResponsableForm.getResponsableId());

      strategosResponsablesService.close();

      return getForwardBack(request, 1, true);
    }

    String responsableId = request.getParameter("responsableId");

    Responsable responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(responsableId));

    if (responsable != null)
    {
      editarResponsableForm.setOrganizacionId(responsable.getOrganizacionId());
      editarResponsableForm.setNombre(responsable.getNombre());
      editarResponsableForm.setEmail(responsable.getEmail());
      editarResponsableForm.setCargo(responsable.getCargo());
      editarResponsableForm.setOrganizacion(responsable.getOrganizacion());
      editarResponsableForm.setUsuario(responsable.getUsuario());
      editarResponsableForm.setResponsables(responsable.getResponsables());
    }

    strategosResponsablesService.close();

    saveMessages(request, messages);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}