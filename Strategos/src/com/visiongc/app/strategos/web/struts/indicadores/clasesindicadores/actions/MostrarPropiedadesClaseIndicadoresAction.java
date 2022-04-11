package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.EditarClaseIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesClaseIndicadoresAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarClaseIndicadoresForm editarClaseIndicadoresForm = (EditarClaseIndicadoresForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;

    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

    if (cancelar)
    {
      strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), editarClaseIndicadoresForm.getClaseId());

      strategosClasesIndicadoresService.close();

      return getForwardBack(request, 1, true);
    }

    String claseId = request.getParameter("claseId");

    ClaseIndicadores claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(claseId));

    UsuariosService strategosUsuarioService = FrameworkServiceFactory.getInstance().openUsuariosService();

    Long creadoId = claseIndicadores.getCreadoId();
    Usuario nombreUsuarioCreado = null;
    if (creadoId != null) {
      nombreUsuarioCreado = (Usuario)strategosUsuarioService.load(Usuario.class, creadoId);
    }

    Long modificadoId = claseIndicadores.getModificadoId();
    Usuario nombreUsuarioModificado = null;
    if (modificadoId != null) {
      nombreUsuarioModificado = (Usuario)strategosUsuarioService.load(Usuario.class, modificadoId);
    }

    if (claseIndicadores != null)
    {
      if (claseIndicadores.getHijos() != null)
        editarClaseIndicadoresForm.setCantidadHijos(new Integer(claseIndicadores.getHijos().size()));
      else {
        editarClaseIndicadoresForm.setCantidadHijos(null);
      }

      if (claseIndicadores.getCreado() != null) {
        editarClaseIndicadoresForm.setFechaCreado(VgcFormatter.formatearFecha(claseIndicadores.getCreado(), "formato.fecha.larga"));
      }
      else {
        editarClaseIndicadoresForm.setFechaCreado(null);
      }

      if (claseIndicadores.getModificado() != null)
        editarClaseIndicadoresForm.setFechaModificado(VgcFormatter.formatearFecha(claseIndicadores.getModificado(), "formato.fecha.larga"));
      else {
        editarClaseIndicadoresForm.setFechaModificado(null);
      }

      if (nombreUsuarioCreado != null) {
        editarClaseIndicadoresForm.setNombreUsuarioCreado(nombreUsuarioCreado.getFullName());
      }

      if (nombreUsuarioModificado != null) {
        editarClaseIndicadoresForm.setNombreUsuarioModificado(nombreUsuarioModificado.getFullName());
      }

      editarClaseIndicadoresForm.setNombre(claseIndicadores.getNombre());
    }

    strategosClasesIndicadoresService.close();

    strategosUsuarioService.close();

    saveMessages(request, messages);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}