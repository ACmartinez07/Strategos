package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.EditarActividadForm;
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

public class MostrarPropiedadesActividadAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarActividadForm editarActividadForm = (EditarActividadForm)form;

    String actividadId = request.getParameter("actividadId");

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;

    if ((actividadId == null) || (actividadId.equals(""))) {
      cancelar = true;
    }

    StrategosPryActividadesService strategosActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

    if (cancelar)
    {
      strategosActividadesService.unlockObject(request.getSession().getId(), editarActividadForm.getActividadId());

      strategosActividadesService.close();

      return getForwardBack(request, 1, true);
    }

    PryActividad actividad = (PryActividad)strategosActividadesService.load(PryActividad.class, new Long(actividadId));

    if (actividad != null)
    {
      editarActividadForm.setNombre(actividad.getNombre());

      if (actividad.getResponsableSeguimientoId() != null) 
        editarActividadForm.setResponsableSeguimiento(actividad.getResponsableSeguimiento().getNombreCargo());
      
      if (actividad.getResponsableLograrMetaId() != null) 
        editarActividadForm.setResponsableLograrMeta(actividad.getResponsableLograrMeta().getNombreCargo());
      
      if (actividad.getResponsableFijarMetaId() != null) 
        editarActividadForm.setResponsableFijarMeta(actividad.getResponsableFijarMeta().getNombreCargo());

      if (actividad.getCreado() != null) 
        editarActividadForm.setFechaCreado(VgcFormatter.formatearFecha(actividad.getCreado(), "formato.fecha.larga"));

      if (actividad.getModificado() != null) 
        editarActividadForm.setFechaModificado(VgcFormatter.formatearFecha(actividad.getModificado(), "formato.fecha.larga"));

      if (actividad.getCreadoId() != null) 
      {
        Usuario usuario = (Usuario)strategosActividadesService.load(Usuario.class, actividad.getCreadoId());
        editarActividadForm.setNombreUsuarioCreado(usuario.getFullName());
      }

      if (actividad.getModificadoId() != null) 
      {
        Usuario usuario = (Usuario)strategosActividadesService.load(Usuario.class, actividad.getCreadoId());
        editarActividadForm.setNombreUsuarioModificado(usuario.getFullName());
      }

    }

    strategosActividadesService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}