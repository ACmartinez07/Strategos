package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.problemas.model.ResponsableAccionPK;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.EditarAccionForm;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.GestionarAccionesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarAccionAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarAccionAction";

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

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarAccionAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();

    if (cancelar)
    {
      strategosAccionesService.unlockObject(request.getSession().getId(), editarAccionForm.getAccionId());

      strategosAccionesService.close();

      return getForwardBack(request, 1, true);
    }

    Accion accion = new Accion();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarAccionForm.getAccionId() != null) && (!editarAccionForm.getAccionId().equals(Long.valueOf("0"))))
    {
      accion = (Accion)strategosAccionesService.load(Accion.class, editarAccionForm.getAccionId());
    }
    else
    {
      GestionarAccionesForm gestionarAccionesForm = (GestionarAccionesForm)request.getSession().getAttribute("gestionarAccionesForm");

      nuevo = true;
      accion.setAccionId(new Long(0L));
      accion.setResponsablesAccion(new HashSet());
      accion.setProblemaId(gestionarAccionesForm.getProblemaId());

      Accion accionPadre = (Accion)strategosAccionesService.load(Accion.class, editarAccionForm.getPadreId());
      accion.setPadreId(accionPadre.getAccionId());
    }

    Date fechaInicio = new Date();
    Date fechaFin = new Date();
    String fechaEstimadaInicio = editarAccionForm.getFechaEstimadaInicio();
    String fechaEstimadaFin = editarAccionForm.getFechaEstimadaFinal();
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
      fechaInicio = simpleDateFormat.parse(fechaEstimadaInicio);
      fechaFin = simpleDateFormat.parse(fechaEstimadaFin);
    }
    catch (Exception localException) {
    }
    accion.setNombre(editarAccionForm.getNombre());

    if ((editarAccionForm.getDescripcion() != null) && (!editarAccionForm.getDescripcion().equals("")))
      accion.setDescripcion(editarAccionForm.getDescripcion());
    else {
      accion.setDescripcion(null);
    }

    if ((editarAccionForm.getFechaEstimadaInicio() != null) && (!editarAccionForm.getFechaEstimadaFinal().equals("")))
      accion.setFechaEstimadaInicio(fechaInicio);
    else {
      accion.setFechaEstimadaInicio(null);
    }

    if ((editarAccionForm.getFechaEstimadaFinal() != null) && (!editarAccionForm.getFechaEstimadaFinal().equals("")))
      accion.setFechaEstimadaFinal(fechaFin);
    else {
      accion.setFechaEstimadaFinal(null);
    }

    if ((editarAccionForm.getFrecuencia() != null) && (!editarAccionForm.getFrecuencia().equals("")))
      accion.setFrecuencia(editarAccionForm.getFrecuencia());
    else {
      accion.setFrecuencia(null);
    }

    if ((editarAccionForm.getOrden() != null) && (!editarAccionForm.getOrden().equals("")))
      accion.setOrden(editarAccionForm.getOrden());
    else {
      accion.setOrden(null);
    }

    accion.getResponsablesAccion().clear();

    if (!editarAccionForm.getResponsableId().equals(new Long(0L))) {
      ResponsableAccion responsableAccion = new ResponsableAccion();
      responsableAccion.setPk(new ResponsableAccionPK());
      responsableAccion.getPk().setAccionId(editarAccionForm.getAccionId());
      responsableAccion.getPk().setResponsableId(editarAccionForm.getResponsableId());
      responsableAccion.setTipo(new Byte((byte) 1));
      accion.getResponsablesAccion().add(responsableAccion);
    }

    if (!editarAccionForm.getAuxiliarId().equals(new Long(0L))) {
      ResponsableAccion responsableAccion = new ResponsableAccion();
      responsableAccion.setPk(new ResponsableAccionPK());
      responsableAccion.getPk().setAccionId(accion.getAccionId());
      responsableAccion.getPk().setResponsableId(editarAccionForm.getAuxiliarId());
      responsableAccion.setTipo(new Byte((byte) 2));
      accion.getResponsablesAccion().add(responsableAccion);
    }

    respuesta = strategosAccionesService.saveAccion(accion, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosAccionesService.unlockObject(request.getSession().getId(), editarAccionForm.getAccionId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearAccion";
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }

    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosAccionesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarAccionAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}