package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.EditarSeguimientoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesSeguimientoAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarSeguimientoForm editarSeguimientoForm = (EditarSeguimientoForm)form;

    ActionMessages messages = getMessages(request);

    String seguimientoId = request.getParameter("seguimientoId");
    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    if (cancelar)
    {
      strategosSeguimientosService.unlockObject(request.getSession().getId(), editarSeguimientoForm.getSeguimientoId());

      strategosSeguimientosService.close();

      return getForwardBack(request, 1, true);
    }

    Seguimiento seguimiento = (Seguimiento)strategosSeguimientosService.load(Seguimiento.class, new Long(seguimientoId));

    if (seguimiento != null)
    {
      editarSeguimientoForm.setNombreAccion(seguimiento.getAccion().getNombre());
      editarSeguimientoForm.setNombreSupervisor(seguimiento.getAccion().getProblema().getResponsable().getNombreCargo());
      editarSeguimientoForm.setAprobado(seguimiento.getAprobado());
      editarSeguimientoForm.setBloqueado(seguimiento.getReadOnly());
      Usuario usuarioCreado = (Usuario)strategosSeguimientosService.load(Usuario.class, seguimiento.getCreadoId());
      editarSeguimientoForm.setNombreUsuarioCreado(usuarioCreado.getFullName());
      editarSeguimientoForm.setFechaCreado(VgcFormatter.formatearFecha(seguimiento.getCreado(), "formato.fecha.larga"));

      if (seguimiento.getModificadoId() != null) {
        Usuario usuarioModificado = (Usuario)strategosSeguimientosService.load(Usuario.class, seguimiento.getModificadoId());
        editarSeguimientoForm.setNombreUsuarioModificado(usuarioModificado.getFullName());
      } else {
        editarSeguimientoForm.setNombreUsuarioModificado(null);
      }

      if (seguimiento.getModificado() != null)
        editarSeguimientoForm.setFechaModificado(VgcFormatter.formatearFecha(seguimiento.getModificado(), "formato.fecha.larga"));
      else {
        editarSeguimientoForm.setFechaModificado(null);
      }

      for (Iterator i = seguimiento.getAccion().getResponsablesAccion().iterator(); i.hasNext(); ) {
        ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
        if (responsableAccion.getTipo().equals(new Byte((byte) 1))) {
          editarSeguimientoForm.setNombreResponsable(responsableAccion.getResponsable().getNombreCargo());
        }

      }

      if (seguimiento.getFechaRecepcion() != null)
        editarSeguimientoForm.setFechaRecepcion(VgcFormatter.formatearFecha(seguimiento.getFechaRecepcion(), "formato.fecha.corta"));
      else {
        editarSeguimientoForm.setFechaRecepcion(null);
      }

      editarSeguimientoForm.setPreparadoPor(seguimiento.getPreparadoPor());

      if (seguimiento.getFechaAprobacion() != null)
        editarSeguimientoForm.setFechaAprobacion(VgcFormatter.formatearFecha(seguimiento.getFechaAprobacion(), "formato.fecha.corta"));
      else {
        editarSeguimientoForm.setFechaAprobacion(null);
      }

      editarSeguimientoForm.setAprobadoPor(seguimiento.getAprobadoPor());
    }

    strategosSeguimientosService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}