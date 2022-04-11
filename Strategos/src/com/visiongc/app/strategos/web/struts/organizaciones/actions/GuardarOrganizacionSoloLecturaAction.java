package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GuardarOrganizacionSoloLecturaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    EditarOrganizacionForm editarOrganizacionForm = (EditarOrganizacionForm)form;

    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

    OrganizacionStrategos organizacionStrategos = new OrganizacionStrategos();

    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];

    fieldNames[0] = "organizacionId";
    fieldValues[0] = editarOrganizacionForm.getOrganizacionId();

    strategosOrganizacionesService.saveSoloLectura(organizacionStrategos, editarOrganizacionForm.getSoloLectura().booleanValue(), fieldNames, fieldValues);

    strategosOrganizacionesService.close();

    return mapping.findForward("ajaxResponse");
  }
}