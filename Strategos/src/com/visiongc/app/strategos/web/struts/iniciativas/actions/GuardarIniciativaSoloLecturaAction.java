package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.EditarIniciativaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GuardarIniciativaSoloLecturaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    EditarIniciativaForm editarIniciativaForm = (EditarIniciativaForm)form;

    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

    Iniciativa iniciativa = new Iniciativa();

    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];

    fieldNames[0] = "iniciativaId";
    fieldValues[0] = editarIniciativaForm.getIniciativaId();

    strategosIniciativasService.saveSoloLectura(iniciativa, editarIniciativaForm.getBloqueado().booleanValue(), fieldNames, fieldValues);

    strategosIniciativasService.close();

    return mapping.findForward("ajaxResponse");
  }
}