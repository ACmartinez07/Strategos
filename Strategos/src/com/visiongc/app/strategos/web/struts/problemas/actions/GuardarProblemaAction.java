package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.MemoProblema;
import com.visiongc.app.strategos.problemas.model.MemoProblemaPK;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.web.struts.problemas.forms.EditarProblemaForm;
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

public class GuardarProblemaAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarProblemaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarProblemaForm editarProblemaForm = (EditarProblemaForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    boolean copiar = mapping.getPath().toLowerCase().indexOf("copia") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarProblemaAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

    if (cancelar)
    {
      strategosProblemasService.unlockObject(request.getSession().getId(), editarProblemaForm.getProblemaId());

      strategosProblemasService.close();

      return getForwardBack(request, 1, true);
    }

    Problema problema = new Problema();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarProblemaForm.getProblemaId() != null) && (!editarProblemaForm.getProblemaId().equals(Long.valueOf("0"))))
    {
      problema = (Problema)strategosProblemasService.load(Problema.class, editarProblemaForm.getProblemaId());
    }
    else if (copiar)
    {
      problema = new Problema();
      problema.setProblemaId(new Long(0L));
      problema.setMemosProblema(new HashSet());
      problema.setCausas(new HashSet());
    }
    else
    {
      nuevo = true;
      problema = new Problema();
      problema.setProblemaId(new Long(0L));
      problema.setMemosProblema(new HashSet());
      problema.setCausas(new HashSet());
    }

    problema.setNombre(editarProblemaForm.getNombre());
    problema.setOrganizacionId(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
    problema.setClaseId(new Long((String)request.getSession().getAttribute("claseProblemasId")));

    Date fechaFormulacion = new Date();
    Date fechaEstimadaInicio = new Date();
    Date fechaEstimadaFinal = new Date();
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
      fechaFormulacion = simpleDateFormat.parse(editarProblemaForm.getFecha());
      fechaEstimadaInicio = simpleDateFormat.parse(editarProblemaForm.getFechaEstimadaInicio());
      fechaEstimadaFinal = simpleDateFormat.parse(editarProblemaForm.getFechaEstimadaFinal());
    } catch (Exception localException) {
    }
    problema.setFecha(fechaFormulacion);

    if ((editarProblemaForm.getIndicadorEfectoId() != null) && (!editarProblemaForm.getIndicadorEfectoId().toString().equals("")) && (editarProblemaForm.getIndicadorEfectoId().longValue() != 0L)) {
      problema.setIndicadorEfectoId(editarProblemaForm.getIndicadorEfectoId());
      problema.setIniciativaEfectoId(null);
    } else {
      problema.setIndicadorEfectoId(null);
    }

    if ((editarProblemaForm.getIniciativaEfectoId() != null) && (!editarProblemaForm.getIniciativaEfectoId().toString().equals("")) && (editarProblemaForm.getIniciativaEfectoId().longValue() != 0L)) {
      problema.setIniciativaEfectoId(editarProblemaForm.getIniciativaEfectoId());
      problema.setIndicadorEfectoId(null);
    } else {
      problema.setIniciativaEfectoId(null);
    }

    problema.getCausas().clear();
    String causaIdRoot = editarProblemaForm.getCausaIdRoot();
    if ((editarProblemaForm.getCausas() != null) && (!editarProblemaForm.getCausas().equals(""))) {
      String[] causas = editarProblemaForm.getCausas().split(editarProblemaForm.getSeparador());
      for (int i = 0; i < causas.length; i++) {
        String causaId = causas[i];
        if ((causaId != null) && (!causaId.equals("")) && (!causaId.equals(causaIdRoot))) {
          Causa causa = new Causa();
          causa.setCausaId(new Long(causaId));
          problema.getCausas().add(causa);
        }
      }

    }

    if ((editarProblemaForm.getResponsableId() != null) && (editarProblemaForm.getResponsableId().longValue() != 0L))
      problema.setResponsableId(editarProblemaForm.getResponsableId());
    else {
      problema.setResponsableId(null);
    }

    if ((editarProblemaForm.getAuxiliarId() != null) && (editarProblemaForm.getAuxiliarId().longValue() != 0L))
      problema.setAuxiliarId(editarProblemaForm.getAuxiliarId());
    else {
      problema.setAuxiliarId(null);
    }

    if ((editarProblemaForm.getEstadoId() != null) && (editarProblemaForm.getEstadoId().longValue() != 0L))
      problema.setEstadoId(editarProblemaForm.getEstadoId());
    else {
      problema.setEstadoId(null);
    }

    if (editarProblemaForm.getFechaEstimadaInicio() != null)
      problema.setFechaEstimadaInicio(fechaEstimadaInicio);
    else {
      problema.setFechaEstimadaInicio(null);
    }

    if (editarProblemaForm.getFechaEstimadaFinal() != null)
      problema.setFechaEstimadaFinal(fechaEstimadaFinal);
    else {
      problema.setFechaEstimadaFinal(null);
    }

    problema.getMemosProblema().clear();

    if (editarProblemaForm.getMemoDescripcion() != null) {
      MemoProblema memoProblema = new MemoProblema();
      memoProblema.setPk(new MemoProblemaPK(problema.getProblemaId(), new Byte((byte) 0)));
      memoProblema.setMemo(editarProblemaForm.getMemoDescripcion());
      problema.getMemosProblema().add(memoProblema);
    }

    if (editarProblemaForm.getMemoEstrategiaDeSolucion() != null) {
      MemoProblema memoProblema = new MemoProblema();
      memoProblema.setPk(new MemoProblemaPK(problema.getProblemaId(), new Byte((byte) 1)));
      memoProblema.setMemo(editarProblemaForm.getMemoEstrategiaDeSolucion());
      problema.getMemosProblema().add(memoProblema);
    }

    if (editarProblemaForm.getMemoConclusionesResultados() != null) {
      MemoProblema memoProblema = new MemoProblema();
      memoProblema.setPk(new MemoProblemaPK(problema.getProblemaId(), new Byte((byte) 2)));
      memoProblema.setMemo(editarProblemaForm.getMemoConclusionesResultados());
      problema.getMemosProblema().add(memoProblema);
    }

    if (editarProblemaForm.getMemoEspecificacion() != null) {
      MemoProblema memoProblema = new MemoProblema();
      memoProblema.setPk(new MemoProblemaPK(problema.getProblemaId(), new Byte((byte) 3)));
      memoProblema.setMemo(editarProblemaForm.getMemoEspecificacion());
      problema.getMemosProblema().add(memoProblema);
    }

    respuesta = strategosProblemasService.saveProblema(problema, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();

      Accion accion = strategosAccionesService.getAccionRaiz(problema.getProblemaId());
      if (accion != null) {
        accion.setNombre(problema.getNombre());
        respuesta = strategosAccionesService.saveAccion(accion, getUsuarioConectado(request));
      }

      strategosAccionesService.close();
    }

    if (respuesta == 10000)
    {
      strategosProblemasService.unlockObject(request.getSession().getId(), editarProblemaForm.getProblemaId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearProblema";
      }
      else if (copiar)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.copiar.ok"));
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

    strategosProblemasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarProblemaAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}