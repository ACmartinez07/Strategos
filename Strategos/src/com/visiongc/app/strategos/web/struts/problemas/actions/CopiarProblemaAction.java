package com.visiongc.app.strategos.web.struts.problemas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.model.ClaseProblemas;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.web.struts.problemas.forms.EditarProblemaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CopiarProblemaAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarProblemaForm editarProblemaForm = (EditarProblemaForm)form;

    ActionMessages messages = getMessages(request);

    String problemaId = request.getParameter("problemaId");

    boolean bloqueado = false;

    StrategosProblemasService strategosProblemasService = StrategosServiceFactory.getInstance().openStrategosProblemasService();

    if ((problemaId != null) && (!problemaId.equals("")) && (!problemaId.equals("0")))
    {
      bloqueado = !strategosProblemasService.lockForUpdate(request.getSession().getId(), problemaId, null);

      editarProblemaForm.setBloqueado(new Boolean(bloqueado));

      Problema problema = (Problema)strategosProblemasService.load(Problema.class, new Long(problemaId));

      if (problema != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        ClaseProblemas claseProblemas = (ClaseProblemas)strategosProblemasService.load(ClaseProblemas.class, problema.getClaseId());

        editarProblemaForm.setProblemaId(new Long(0L));
        editarProblemaForm.setOrganizacionId(problema.getOrganizacionId());
        editarProblemaForm.setClaseId(problema.getClaseId());
        editarProblemaForm.setNombre(problema.getNombre());
        editarProblemaForm.setNombreClase(claseProblemas.getNombre());
        editarProblemaForm.setFecha(VgcFormatter.formatearFecha(problema.getFecha(), "formato.fecha.corta"));
        editarProblemaForm.setFechaEstimadaInicio(problema.getFechaEstimadaInicioFormateada());
        editarProblemaForm.setFechaEstimadaFinal(problema.getFechaEstimadaFinalFormateada());
      }
      else
      {
        strategosProblemasService.unlockObject(request.getSession().getId(), new Long(problemaId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }

    strategosProblemasService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) 
    	return getForwardBack(request, 1, true);

    return mapping.findForward(forward);
  }
}