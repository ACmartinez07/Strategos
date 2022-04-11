package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.web.struts.problemas.forms.EditarProblemaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesProblemaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarProblemaForm editarProblemaForm = (EditarProblemaForm)form;

    String problemaId = request.getParameter("problemaId");
    boolean cancelar = false;

    ActionMessages messages = getMessages(request);

    if ((problemaId == null) || (problemaId.equals(""))) {
      cancelar = true;
    }

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

    if (cancelar)
    {
      strategosProblemasService.unlockObject(request.getSession().getId(), editarProblemaForm.getProblemaId());

      strategosProblemasService.close();

      return getForwardBack(request, 1, true);
    }

    Problema problema = (Problema)strategosProblemasService.load(Problema.class, new Long(problemaId));

    if (problema != null)
    {
      editarProblemaForm.setProblemaId(problema.getProblemaId());
      editarProblemaForm.setNombre(problema.getNombre());
      editarProblemaForm.setNombreUsuarioCreado(problema.getUsuarioCreado().getFullName());
      editarProblemaForm.setNombreUsuarioModificado(problema.getUsuarioModificado().getFullName());
      editarProblemaForm.setReadOnly(problema.getReadOnly());

      if ((problema.getEstadoId() != null) && (problema.getEstadoId().byteValue() != 0)) {
        EstadoAcciones estadoAcciones = (EstadoAcciones)strategosProblemasService.load(EstadoAcciones.class, problema.getEstadoId());
        editarProblemaForm.setNombreEstado(estadoAcciones.getNombre());
      } else {
        editarProblemaForm.setNombreEstado(null);
      }

      if (problema.getFechaEstimadaInicio() != null)
        editarProblemaForm.setFechaEstimadaInicio(VgcFormatter.formatearFecha(problema.getFechaEstimadaInicio(), "formato.fecha.corta"));
      else {
        editarProblemaForm.setFechaEstimadaInicio(null);
      }

      if (problema.getFechaEstimadaFinal() != null)
        editarProblemaForm.setFechaEstimadaFinal(VgcFormatter.formatearFecha(problema.getFechaEstimadaFinal(), "formato.fecha.corta"));
      else {
        editarProblemaForm.setFechaEstimadaFinal(null);
      }

      if (problema.getCreado() != null)
        editarProblemaForm.setCreado(VgcFormatter.formatearFecha(problema.getCreado(), "formato.fecha.larga"));
      else {
        editarProblemaForm.setCreado(null);
      }

      if (problema.getModificado() != null)
        editarProblemaForm.setModificado(VgcFormatter.formatearFecha(problema.getModificado(), "formato.fecha.larga"));
      else {
        editarProblemaForm.setModificado(null);
      }

    }

    strategosProblemasService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}