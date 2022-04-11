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

public class MostrarPropiedadesIniciativaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarIniciativaForm editarIniciativaForm = (EditarIniciativaForm)form;

    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

    Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, editarIniciativaForm.getIniciativaId());

    if (iniciativa != null)
    {
      if (iniciativa.getResponsableSeguimientoId() != null) {
        editarIniciativaForm.setResponsableSeguimiento(iniciativa.getResponsableSeguimiento().getNombreCargo());
      }

      if (iniciativa.getResponsableFijarMetaId() != null) {
        editarIniciativaForm.setResponsableFijarMeta(iniciativa.getResponsableFijarMeta().getNombreCargo());
      }

      if (iniciativa.getResponsableLograrMetaId() != null) {
        editarIniciativaForm.setResponsableLograrMeta(iniciativa.getResponsableLograrMeta().getNombreCargo());
      }

      if (iniciativa.getResponsableCargarMetaId() != null) {
        editarIniciativaForm.setResponsableCargarMeta(iniciativa.getResponsableCargarMeta().getNombreCargo());
      }

      if (iniciativa.getResponsableCargarEjecutadoId() != null) {
        editarIniciativaForm.setResponsableCargarEjecutado(iniciativa.getResponsableCargarEjecutado().getNombreCargo());
      }

      editarIniciativaForm.setNombre(iniciativa.getNombre());

      editarIniciativaForm.setBloqueado(iniciativa.getSoloLectura());
    }

    strategosIniciativasService.close();

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}