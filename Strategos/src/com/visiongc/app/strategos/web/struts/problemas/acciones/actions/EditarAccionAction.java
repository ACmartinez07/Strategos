package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.ResponsableAccionPK;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.EditarAccionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarAccionAction extends VgcAction
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

    ActionMessages messages = getMessages(request);

    String accionId = request.getParameter("accionId");

    StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();

	boolean verForm = getPermisologiaUsuario(request).tienePermiso("ACCION_VIEWALL");
	boolean editarForm = getPermisologiaUsuario(request).tienePermiso("ACCION_EDIT");
    boolean bloqueado = false;

    if ((accionId != null) && (!accionId.equals("")) && (!accionId.equals("0")))
    {
      bloqueado = !strategosAccionesService.lockForUpdate(request.getSession().getId(), accionId, null);

      editarAccionForm.setBloqueado(new Boolean(bloqueado));

      Accion accion = (Accion)strategosAccionesService.load(Accion.class, new Long(accionId));

      if (accion != null)
      {
        if (accion.getReadOnly().booleanValue())
        {
          editarAccionForm.setBloqueado(new Boolean(true));

          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
        }

        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        Accion padre = accion.getPadre();
        long padreId = 0L;
        if (padre != null) {
          padreId = padre.getAccionId().longValue();
        }

        editarAccionForm.setPadreId(new Long(padreId));
        editarAccionForm.setAccionId(accion.getAccionId());
        editarAccionForm.setNombre(accion.getNombre());

        if (accion.getDescripcion() != null)
          editarAccionForm.setDescripcion(accion.getDescripcion());
        else {
          editarAccionForm.setDescripcion(null);
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

        if (accion.getFrecuencia() != null)
          editarAccionForm.setFrecuencia(accion.getFrecuencia());
        else {
          editarAccionForm.setFrecuencia(null);
        }

        if (accion.getOrden() != null)
          editarAccionForm.setOrden(accion.getOrden());
        else {
          editarAccionForm.setOrden(null);
        }

        for (Iterator i = accion.getResponsablesAccion().iterator(); i.hasNext(); ) {
          ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
          Byte tipoResponsable = responsableAccion.getTipo();
          if (tipoResponsable.equals(new Byte((byte) 1))) {
            editarAccionForm.setResponsableId(responsableAccion.getPk().getResponsableId());
            editarAccionForm.setNombreResponsable(responsableAccion.getResponsable().getNombreCargo());
          }
          if (tipoResponsable.equals(new Byte((byte) 2))) {
            editarAccionForm.setAuxiliarId(responsableAccion.getPk().getResponsableId());
            editarAccionForm.setNombreAuxiliar(responsableAccion.getResponsable().getNombreCargo());
          }
        }

      }
      else
      {
        strategosAccionesService.unlockObject(request.getSession().getId(), new Long(accionId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarAccionForm.clear();
      Accion padre = (Accion)request.getSession().getAttribute("accionCorrectiva");
      editarAccionForm.setPadreId(padre.getAccionId());
    }

    strategosAccionesService.close();

	if (!bloqueado && verForm && !editarForm)
	{
		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
		editarAccionForm.setBloqueado(true);
	}
	else if (!bloqueado && !verForm && !editarForm)
		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
    
    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}