package com.visiongc.app.strategos.web.struts.plancuentas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.web.struts.plancuentas.forms.DefinirMascaraCuentasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class DefinirMascaraCuentasAction extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    DefinirMascaraCuentasForm definirMascaraCuentasForm = (DefinirMascaraCuentasForm)form;

    ActionMessages messages = getMessages(request);

    boolean bloqueado = false;

    StrategosCuentasService strategosCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();

    List listaMascaras = strategosCuentasService.getMascaras();
    MascaraCodigoPlanCuentas mascaraCodigoPlanCuentas = new MascaraCodigoPlanCuentas();

    if (listaMascaras.size() > 0) {
      mascaraCodigoPlanCuentas = (MascaraCodigoPlanCuentas)listaMascaras.get(0);

      Long mascaraId = mascaraCodigoPlanCuentas.getMascaraId();
      definirMascaraCuentasForm.setMascaraId(mascaraId);

      bloqueado = !strategosCuentasService.lockForUpdate(request.getSession().getId(), mascaraCodigoPlanCuentas.getMascaraId(), null);

      definirMascaraCuentasForm.setBloqueado(new Boolean(bloqueado));

      mascaraCodigoPlanCuentas = (MascaraCodigoPlanCuentas)strategosCuentasService.load(MascaraCodigoPlanCuentas.class, mascaraId);

      if (mascaraCodigoPlanCuentas != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        definirMascaraCuentasForm.setNiveles(mascaraCodigoPlanCuentas.getNiveles());
        definirMascaraCuentasForm.setMascara(mascaraCodigoPlanCuentas.getMascara());
        definirMascaraCuentasForm.setGruposMascaraCodigoPlanCuentas(mascaraCodigoPlanCuentas.getGruposMascara());
      }
      else
      {
        strategosCuentasService.unlockObject(request.getSession().getId(), mascaraId);

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
      forward = "noencontrado";
    }

    strategosCuentasService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}