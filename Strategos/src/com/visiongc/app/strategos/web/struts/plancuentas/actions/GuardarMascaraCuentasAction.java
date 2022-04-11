package com.visiongc.app.strategos.web.struts.plancuentas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentasPK;
import com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.web.struts.plancuentas.forms.DefinirMascaraCuentasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarMascaraCuentasAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarMascaraCuentasAction";

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

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarMascaraCuentasAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosCuentasService mascaraCodigoPlanCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();

    if (cancelar)
    {
      mascaraCodigoPlanCuentasService.unlockObject(request.getSession().getId(), definirMascaraCuentasForm.getMascaraId());

      mascaraCodigoPlanCuentasService.close();

      return getForwardBack(request, 1, true);
    }

    MascaraCodigoPlanCuentas mascaraCodigoPlanCuentas = (MascaraCodigoPlanCuentas)mascaraCodigoPlanCuentasService.load(MascaraCodigoPlanCuentas.class, definirMascaraCuentasForm.getMascaraId());
    int respuesta = 10000;

    mascaraCodigoPlanCuentas.setMascaraId(definirMascaraCuentasForm.getMascaraId());
    mascaraCodigoPlanCuentas.setNiveles(definirMascaraCuentasForm.getNiveles());
    mascaraCodigoPlanCuentas.getGruposMascara().clear();

    String mascara = "";
    String patronNombre = "nombre_";
    String patronMascara = "mascara_";
    GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentas = null;
    for (int i = 0; i < definirMascaraCuentasForm.getNiveles().intValue(); i++) {
      grupoMascaraCodigoPlanCuentas = new GrupoMascaraCodigoPlanCuentas();
      String mascaraGrupo = request.getParameter(patronMascara + (i + 1));
      String nombreGrupo = request.getParameter(patronNombre + (i + 1));
      grupoMascaraCodigoPlanCuentas.setNombre(nombreGrupo);
      grupoMascaraCodigoPlanCuentas.setMascara(mascaraGrupo);
      grupoMascaraCodigoPlanCuentas.setPk(new GrupoMascaraCodigoPlanCuentasPK());
      grupoMascaraCodigoPlanCuentas.getPk().setMascaraId(definirMascaraCuentasForm.getMascaraId());
      grupoMascaraCodigoPlanCuentas.getPk().setNivel(new Integer(i + 1));
      mascara = mascara + "." + grupoMascaraCodigoPlanCuentas.getMascara();
      mascaraCodigoPlanCuentas.getGruposMascara().add(grupoMascaraCodigoPlanCuentas);
    }

    mascara = mascara.substring(1);
    mascaraCodigoPlanCuentas.setMascara(mascara);

    respuesta = mascaraCodigoPlanCuentasService.definirMascara(mascaraCodigoPlanCuentas, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      mascaraCodigoPlanCuentasService.unlockObject(request.getSession().getId(), null);

      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok", mascaraCodigoPlanCuentas.getMascara()));
    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado", mascaraCodigoPlanCuentas.getMascara()));
    }

    mascaraCodigoPlanCuentasService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}