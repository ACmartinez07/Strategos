package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.EditarAccionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesAccionAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarAccionForm editarAccionForm = (EditarAccionForm)form;

    String accionId = request.getParameter("accionId");
    boolean cancelar = false;

    ActionMessages messages = getMessages(request);

    if ((accionId == null) || (accionId.equals(""))) {
      cancelar = true;
    }

    StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();

    if (cancelar)
    {
      strategosAccionesService.unlockObject(request.getSession().getId(), editarAccionForm.getAccionId());

      strategosAccionesService.close();

      return getForwardBack(request, 1, true);
    }

    Accion accion = (Accion)strategosAccionesService.load(Accion.class, new Long(accionId));

    if (accion != null)
    {
      editarAccionForm.setNombre(accion.getNombre());
      editarAccionForm.setReadOnly(accion.getReadOnly());

      UsuariosService strategosUsuarioService = FrameworkServiceFactory.getInstance().openUsuariosService();

      if ((accion.getCreadoId() != null) && (accion.getCreadoId().byteValue() != 0)) {
        editarAccionForm.setNombreUsuarioCreado(((Usuario)strategosUsuarioService.load(Usuario.class, accion.getCreadoId())).getFullName());
      }

      if ((accion.getModificadoId() != null) && (accion.getModificadoId().byteValue() != 0)) {
        editarAccionForm.setNombreUsuarioModificado(((Usuario)strategosUsuarioService.load(Usuario.class, accion.getModificadoId())).getFullName());
      }

      strategosUsuarioService.close();

      if (accion.getCreado() != null)
        editarAccionForm.setFechaCreado(VgcFormatter.formatearFecha(accion.getCreado(), "formato.fecha.larga"));
      else {
        editarAccionForm.setFechaCreado(null);
      }

      if (accion.getModificado() != null)
        editarAccionForm.setFechaModificado(VgcFormatter.formatearFecha(accion.getModificado(), "formato.fecha.larga"));
      else {
        editarAccionForm.setFechaModificado(null);
      }

      if (accion.getFechaEstimadaInicio() != null)
        editarAccionForm.setFechaEstimadaInicio(VgcFormatter.formatearFecha(accion.getFechaEstimadaInicio(), "formato.fecha.corta"));
      else {
        editarAccionForm.setFechaEstimadaInicio(null);
      }

      if (accion.getFechaEstimadaFinal() != null)
        editarAccionForm.setFechaEstimadaFinal(VgcFormatter.formatearFecha(accion.getFechaEstimadaFinal(), "formato.fecha.corta"));
      else {
        editarAccionForm.setFechaEstimadaFinal(null);
      }

      if (accion.getFechaRealInicio() != null)
        editarAccionForm.setFechaRealInicio(VgcFormatter.formatearFecha(accion.getFechaRealInicio(), "formato.fecha.corta"));
      else {
        editarAccionForm.setFechaRealInicio(null);
      }

      if (accion.getFechaRealFinal() != null)
        editarAccionForm.setFechaRealFinal(VgcFormatter.formatearFecha(accion.getFechaRealFinal(), "formato.fecha.corta"));
      else {
        editarAccionForm.setFechaRealFinal(null);
      }

    }

    strategosAccionesService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}