package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.MemoProblema;
import com.visiongc.app.strategos.problemas.model.MemoProblemaPK;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.web.struts.problemas.forms.EditarProblemaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
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

public class EditarProblemaAction extends VgcAction
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

    ActionMessages messages = getMessages(request);

    String problemaId = request.getParameter("problemaId");

	boolean verForm = getPermisologiaUsuario(request).tienePermiso("PROBLEMA_VIEWALL");
	boolean editarForm = getPermisologiaUsuario(request).tienePermiso("PROBLEMA_EDIT");
    boolean bloqueado = false;

    if ((problemaId != null) && (!problemaId.equals("")) && (!problemaId.equals("0")))
    {
      StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

      bloqueado = !strategosProblemasService.lockForUpdate(request.getSession().getId(), problemaId, null);

      editarProblemaForm.setBloqueado(new Boolean(bloqueado));

      Problema problema = (Problema)strategosProblemasService.load(Problema.class, new Long(problemaId));

      if (problema != null)
      {
        if (problema.getReadOnly().booleanValue())
        {
          editarProblemaForm.setBloqueado(new Boolean(true));

          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
        }

        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarProblemaForm.setProblemaId(problema.getProblemaId());
        editarProblemaForm.setOrganizacionId(problema.getOrganizacionId());
        editarProblemaForm.setClaseId(problema.getClaseId());
        editarProblemaForm.setFecha(VgcFormatter.formatearFecha(problema.getFecha(), "formato.fecha.corta"));
        editarProblemaForm.setIndicadorEfectoId(problema.getIndicadorEfectoId());
        editarProblemaForm.setIniciativaEfectoId(problema.getIniciativaEfectoId());
        editarProblemaForm.setNombreObjetoEfecto(null);
        editarProblemaForm.setResponsable(problema.getResponsable());
        editarProblemaForm.setAuxiliar(problema.getAuxiliar());
        editarProblemaForm.setResponsableId(problema.getResponsableId());
        editarProblemaForm.setAuxiliarId(problema.getAuxiliarId());
        editarProblemaForm.setEstadoId(problema.getEstadoId());
        editarProblemaForm.setNombre(problema.getNombre());
        editarProblemaForm.setCreadoId(problema.getClaseId());
        editarProblemaForm.setModificadoId(problema.getModificadoId());
        editarProblemaForm.setReadOnly(problema.getReadOnly());

        if ((problema.getIndicadorEfectoId() != null) && (!problema.getIndicadorEfectoId().toString().equals("")) && (problema.getIndicadorEfectoId().longValue() != 0L)) {
          Indicador indicador = (Indicador)strategosProblemasService.load(Indicador.class, problema.getIndicadorEfectoId());
          editarProblemaForm.setNombreObjetoEfecto(indicador.getNombre());
        } else if ((problema.getIniciativaEfectoId() != null) && (!problema.getIniciativaEfectoId().toString().equals("")) && (problema.getIniciativaEfectoId().longValue() != 0L)) {
          Iniciativa iniciativa = (Iniciativa)strategosProblemasService.load(Iniciativa.class, problema.getIniciativaEfectoId());
          editarProblemaForm.setNombreObjetoEfecto(iniciativa.getNombre());
        }

        if (problema.getFechaEstimadaInicio() != null)
          editarProblemaForm.setFechaEstimadaInicio(VgcFormatter.formatearFecha(problema.getFechaEstimadaInicio(), "formato.fecha.corta"));
        else {
          editarProblemaForm.setFechaEstimadaInicio(null);
        }

        if (problema.getFechaRealInicio() != null)
          editarProblemaForm.setFechaRealInicio(VgcFormatter.formatearFecha(problema.getFechaRealInicio(), "formato.fecha.corta"));
        else {
          editarProblemaForm.setFechaRealInicio(null);
        }

        if (problema.getFechaEstimadaFinal() != null)
          editarProblemaForm.setFechaEstimadaFinal(VgcFormatter.formatearFecha(problema.getFechaEstimadaFinal(), "formato.fecha.corta"));
        else {
          editarProblemaForm.setFechaEstimadaFinal(null);
        }

        if (problema.getFechaRealFinal() != null)
          editarProblemaForm.setFechaRealFinal(VgcFormatter.formatearFecha(problema.getFechaRealFinal(), "formato.fecha.corta"));
        else {
          editarProblemaForm.setFechaRealFinal(null);
        }

        for (Iterator i = problema.getMemosProblema().iterator(); i.hasNext(); ) {
          MemoProblema memoProblema = (MemoProblema)i.next();
          Byte tipoMemo = memoProblema.getPk().getTipo();
          if (tipoMemo.equals(new Byte((byte) 0)))
            editarProblemaForm.setMemoDescripcion(memoProblema.getMemo());
          else if (tipoMemo.equals(new Byte((byte) 1)))
            editarProblemaForm.setMemoEstrategiaDeSolucion(memoProblema.getMemo());
          else if (tipoMemo.equals(new Byte((byte) 2)))
            editarProblemaForm.setMemoConclusionesResultados(memoProblema.getMemo());
          else if (tipoMemo.equals(new Byte((byte) 3))) {
            editarProblemaForm.setMemoEspecificacion(memoProblema.getMemo());
          }

        }

        Set causas = problema.getCausas();

        String listaCausas = editarProblemaForm.getSeparador();

        for (Iterator i = causas.iterator(); i.hasNext(); ) {
          Causa causa = (Causa)i.next();
          listaCausas = listaCausas + causa.getCausaId() + editarProblemaForm.getSeparador();
        }
        editarProblemaForm.setCausas(listaCausas);
        strategosProblemasService.close();
      }
      else
      {
        strategosProblemasService.unlockObject(request.getSession().getId(), new Long(problemaId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarProblemaForm.clear();
    }

    publishArbolCausas(request, editarProblemaForm);

    StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

    List paginaEstadosAcciones = strategosEstadosService.getEstadosAcciones(0, 0, "nombre", "asc", false, null).getLista();

    request.getSession().setAttribute("paginaEstadosAcciones", paginaEstadosAcciones);

    strategosEstadosService.close();

	if (!bloqueado && verForm && !editarForm)
	{
		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
		editarProblemaForm.setBloqueado(true);
	}
	else if (!bloqueado && !verForm && !editarForm)
		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
    
    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }

  private void publishArbolCausas(HttpServletRequest request, EditarProblemaForm editarProblemaForm)
  {
    try
    {
      ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
      Causa causa = new Causa();
      causa = (Causa)arbolesService.getNodoArbolRaiz(causa);

      editarProblemaForm.setCausaIdRoot(causa.getCausaId().toString());
      request.getSession().setAttribute("editarProblemaCausaRoot", causa);
      TreeviewWeb.publishTree("editarProblemaArbolCausas", causa.getCausaId().toString(), "session", request, true);
      String listaPadresHijos = abrirArbolCausas(causa.getHijos(), "editarProblemaArbolCausas", "request", request);
      editarProblemaForm.setListaPadresHijosCausas(listaPadresHijos);

      arbolesService.close();
    }
    catch (Throwable t) {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
  }

  private String abrirArbolCausas(Set conj, String nameObject, String scope, HttpServletRequest request)
  {
    String listaPadresHijos = "";
    try
    {
      for (Iterator i = conj.iterator(); i.hasNext(); ) {
        Causa hijo = (Causa)i.next();
        if (hijo.getPadre() != null) {
          listaPadresHijos = listaPadresHijos + "#%#" + hijo.getPadreId() + "#hijo#" + hijo.getCausaId() + "#%#";
        }
        if (hijo.getHijos().size() > 0) {
          TreeviewWeb.publishTree(nameObject, hijo.getCausaId().toString(), "request", request);
          listaPadresHijos = listaPadresHijos + abrirArbolCausas(hijo.getHijos(), nameObject, scope, request);
        }
      }
    }
    catch (Throwable t) {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }

    return listaPadresHijos;
  }
}