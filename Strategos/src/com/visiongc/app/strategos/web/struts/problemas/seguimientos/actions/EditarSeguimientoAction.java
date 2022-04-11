package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.MemoSeguimiento;
import com.visiongc.app.strategos.problemas.model.MemoSeguimientoPK;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.EditarSeguimientoForm;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.GestionarSeguimientosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarSeguimientoAction extends VgcAction
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
    GestionarSeguimientosForm gestionarSeguimientosForm = (GestionarSeguimientosForm)request.getSession().getAttribute("gestionarSeguimientosForm");

    ActionMessages messages = getMessages(request);

    String seguimientoId = request.getParameter("seguimientoId");

    boolean bloqueado = false;

    StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

    if ((seguimientoId != null) && (!seguimientoId.equals("")) && (!seguimientoId.equals("0")))
    {
      bloqueado = !strategosSeguimientosService.lockForUpdate(request.getSession().getId(), seguimientoId, null);

      editarSeguimientoForm.setBloqueado(new Boolean(bloqueado));

      Seguimiento seguimiento = (Seguimiento)strategosSeguimientosService.load(Seguimiento.class, new Long(seguimientoId));

      if (seguimiento != null)
      {
        if (seguimiento.getReadOnly().booleanValue())
        {
          editarSeguimientoForm.setBloqueado(new Boolean(true));

          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
        }

        if (editarSeguimientoForm.getVerSeguimiento().booleanValue())
        {
          editarSeguimientoForm.setBloqueado(new Boolean(true));
        }
        else if ((seguimiento.getRecepcionEnviado() != null) && (seguimiento.getRecepcionEnviado().booleanValue()) && (seguimiento.getFechaAprobacion() != null))
        {
          editarSeguimientoForm.setBloqueado(new Boolean(true));

          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarseguimiento.seguimiento.cerrado"));
        }

        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarSeguimientoForm.setEstadoId(seguimiento.getEstadoId());
        editarSeguimientoForm.setPreparadoPor(seguimiento.getPreparadoPor());
        editarSeguimientoForm.setNumeroReporte(seguimiento.getNumeroReporte());
        editarSeguimientoForm.setAprobado(seguimiento.getAprobado());
        editarSeguimientoForm.setAprobadoPor(seguimiento.getAprobadoPor());
        editarSeguimientoForm.setEmisionEnviado(seguimiento.getEmisionEnviado());
        editarSeguimientoForm.setRecepcionEnviado(seguimiento.getRecepcionEnviado());

        if (seguimiento.getEstado() != null)
          editarSeguimientoForm.setNombreEstado(seguimiento.getEstado().getNombre());
        else {
          editarSeguimientoForm.setNombreEstado(null);
        }

        if (seguimiento.getFechaEmision() != null)
          editarSeguimientoForm.setFechaEmision(VgcFormatter.formatearFecha(seguimiento.getFechaEmision(), "formato.fecha.corta"));
        else {
          editarSeguimientoForm.setFechaEmision(null);
        }

        if ((seguimiento.getFechaRecepcion() != null) && (!seguimiento.getFechaRecepcion().equals("")))
          editarSeguimientoForm.setFechaRecepcion(VgcFormatter.formatearFecha(seguimiento.getFechaRecepcion(), "formato.fecha.corta"));
        else {
          editarSeguimientoForm.setFechaRecepcion(null);
        }

        if ((seguimiento.getFechaAprobacion() != null) && (!seguimiento.getFechaAprobacion().equals("")))
          editarSeguimientoForm.setFechaAprobacion(VgcFormatter.formatearFecha(seguimiento.getFechaAprobacion(), "formato.fecha.corta"));
        else {
          editarSeguimientoForm.setFechaAprobacion(null);
        }

        if (seguimiento.getAccion() != null)
          editarSeguimientoForm.setNombreAccion(seguimiento.getAccion().getNombre());
        else {
          editarSeguimientoForm.setNombreAccion(null);
        }

        if (seguimiento.getAccion().getProblema().getResponsableId() != null)
          editarSeguimientoForm.setNombreSupervisor(seguimiento.getAccion().getProblema().getResponsable().getNombreCargo());
        else {
          editarSeguimientoForm.setNombreSupervisor(null);
        }

        for (Iterator i = seguimiento.getMemosSeguimiento().iterator(); i.hasNext(); ) {
          MemoSeguimiento memoSeguimiento = (MemoSeguimiento)i.next();
          Byte tipoMemo = memoSeguimiento.getPk().getTipo();
          if (tipoMemo.equals(new Byte((byte) 0)))
            editarSeguimientoForm.setMemoResumen(memoSeguimiento.getMemo());
          else if (tipoMemo.equals(new Byte((byte) 1))) {
            editarSeguimientoForm.setMemoComentario(memoSeguimiento.getMemo());
          }

        }

        if (seguimiento.getAccion() != null)
        {
          for (Iterator i = seguimiento.getAccion().getResponsablesAccion().iterator(); i.hasNext(); ) {
            ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
            Byte tipoResponsable = responsableAccion.getTipo();
            if (tipoResponsable.equals(new Byte((byte) 1)))
              editarSeguimientoForm.setNombreResponsable(responsableAccion.getResponsable().getNombreCargo());
          }
        }
        else {
          editarSeguimientoForm.setNombreSupervisor(null);
        }

      }
      else
      {
        strategosSeguimientosService.unlockObject(request.getSession().getId(), new Long(seguimientoId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarSeguimientoForm.clear();

      if (gestionarSeguimientosForm.getSeguimientoCerrado().booleanValue())
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarseguimiento.accioncorrectiva.cerrada"));
        forward = "noencontrado";
      }
      else if (!gestionarSeguimientosForm.getEsDiaSeguimiento().booleanValue())
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarseguimiento.no.diaseguimiento"));
        forward = "noencontrado";
      }
      else if (gestionarSeguimientosForm.getExisteSeguimiento().booleanValue())
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarseguimiento.seguimiento.yaexiste"));
        forward = "noencontrado";
      }

    }

    StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

    List paginaEstadoAcciones = strategosEstadosService.getEstadosAcciones(0, 0, "nombre", "asc", false, null).getLista();

    request.setAttribute("paginaEstadoAcciones", paginaEstadoAcciones);

    strategosEstadosService.close();

    strategosSeguimientosService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}