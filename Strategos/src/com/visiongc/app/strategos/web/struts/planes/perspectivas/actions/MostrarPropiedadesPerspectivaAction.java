package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesPerspectivaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarPerspectivaForm editarPerspectivaForm = (EditarPerspectivaForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String perspectivaId = request.getParameter("perspectivaId");

    StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

    if (cancelar)
    {
      strategosPerspectivasService.unlockObject(request.getSession().getId(), editarPerspectivaForm.getPerspectivaId());

      strategosPerspectivasService.close();

      return getForwardBack(request, 1, true);
    }

    Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaId));

    if (perspectiva != null)
    {
      editarPerspectivaForm.setPerspectivaId(perspectiva.getPerspectivaId());
      editarPerspectivaForm.setNombre(perspectiva.getNombre());
      editarPerspectivaForm.setNombreFrecuencia(Frecuencia.getFrecuencia(perspectiva.getFrecuencia().byteValue()).getNombre());
      editarPerspectivaForm.setCreadoId(perspectiva.getCreadoId());
      editarPerspectivaForm.setModificadoId(perspectiva.getModificadoId());
      if (perspectiva.getCreado() != null)
        editarPerspectivaForm.setCreado(VgcFormatter.formatearFecha(perspectiva.getCreado(), "formato.fecha.larga"));
      else {
        editarPerspectivaForm.setCreado(null);
      }
      if (perspectiva.getModificado() != null)
        editarPerspectivaForm.setModificado(VgcFormatter.formatearFecha(perspectiva.getModificado(), "formato.fecha.larga"));
      else {
        editarPerspectivaForm.setModificado(null);
      }
      if ((editarPerspectivaForm.getCreadoId() != null) && (editarPerspectivaForm.getCreadoId().byteValue() != 0)) {
        FrameworkService strategosUsuariosService = FrameworkServiceFactory.getInstance().openFrameworkService();
        Usuario usuarioCreado = (Usuario)strategosUsuariosService.load(Usuario.class, editarPerspectivaForm.getCreadoId());
        editarPerspectivaForm.setNombreUsuarioCreado(usuarioCreado.getFullName());
        strategosUsuariosService.close();
      } else {
        editarPerspectivaForm.setNombreUsuarioCreado(null);
      }
      if ((editarPerspectivaForm.getModificadoId() != null) && (editarPerspectivaForm.getModificadoId().byteValue() != 0)) {
        FrameworkService strategosUsuariosService = FrameworkServiceFactory.getInstance().openFrameworkService();
        Usuario usuarioModificado = (Usuario)strategosUsuariosService.load(Usuario.class, editarPerspectivaForm.getModificadoId());
        editarPerspectivaForm.setNombreUsuarioModificado(usuarioModificado.getFullName());
        strategosUsuariosService.close();
      } else {
        editarPerspectivaForm.setNombreUsuarioModificado(null);
      }
      editarPerspectivaForm.setElementosAsociados(new Integer(perspectiva.getHijos().size()));
      editarPerspectivaForm.setIndicadoresAsociados(new Integer(0));
      editarPerspectivaForm.setIniciativasAsociadas(new Integer(0));
    }

    strategosPerspectivasService.close();

    saveMessages(request, messages);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}