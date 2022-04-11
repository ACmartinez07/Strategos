package com.visiongc.app.strategos.web.struts.causas.actions;

import com.visiongc.app.strategos.causas.StrategosCausasService;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.util.PermisologiaUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class GestionarCausasAction extends VgcAction
{
  public static final String ACTION_KEY = "GestionarCausasAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    navBar.agregarUrl(TreeviewWeb.getUrlTreeview(url), nombre);
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    ActionMessages messages = getMessages(request);

    String selectedCausaId = request.getParameter("selectedCausaId");
    String openCausaId = request.getParameter("openCausaId");
    String closeCausaId = request.getParameter("closeCausaId");

    boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("CAUSA_VIEWALL");

    ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();

    boolean bloqueada = false;

    if (request.getSession().getAttribute("causa") == null)
    {
      Causa causaRoot = new Causa();

      causaRoot = (Causa)nodosArbolService.getNodoArbolRaiz(causaRoot);

      if (causaRoot == null) {
        StrategosCausasService strategosCausasService = StrategosServiceFactory.getInstance().openStrategosCausasService();
        causaRoot = strategosCausasService.crearCausaRaiz(getUsuarioConectado(request));
        strategosCausasService.close();
      }

      TreeviewWeb.publishTree("arbolCausas", causaRoot.getCausaId().toString(), "session", request, true);
      nodosArbolService.refreshNodosArbol(causaRoot, (String)request.getSession().getAttribute("arbolCausas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
      request.getSession().setAttribute("causaRoot", causaRoot);

      bloqueada = !nodosArbolService.bloquearParaUso(request.getSession().getId(), null, causaRoot);

      request.getSession().setAttribute("causa", causaRoot);
      request.getSession().setAttribute("causaId", causaRoot.getCausaId().toString());
    }
    else
    {
      Causa causaSeleccionada = null;

      if (request.getAttribute("GestionarCausasAction.reloadId") != null) {
        causaSeleccionada = (Causa)nodosArbolService.load(Causa.class, (Long)request.getAttribute("GestionarCausasAction.reloadId"));
      } else if ((selectedCausaId != null) && (!selectedCausaId.equals(""))) {
        causaSeleccionada = (Causa)nodosArbolService.load(Causa.class, new Long(selectedCausaId));
      } else if ((openCausaId != null) && (!openCausaId.equals(""))) {
        TreeviewWeb.publishTreeOpen("arbolCausas", openCausaId, "session", request);
        causaSeleccionada = (Causa)nodosArbolService.load(Causa.class, new Long(openCausaId));
      } else if ((closeCausaId != null) && (!closeCausaId.equals(""))) {
        TreeviewWeb.publishTreeClose("arbolCausas", closeCausaId, "session", request);
        causaSeleccionada = (Causa)nodosArbolService.load(Causa.class, new Long(closeCausaId));
      } else {
        causaSeleccionada = (Causa)nodosArbolService.load(Causa.class, new Long((String)request.getSession().getAttribute("causaId")));
      }
      Long reloadId;
      if (causaSeleccionada == null) {
        causaSeleccionada = (Causa)request.getSession().getAttribute("causaRoot");
        reloadId = causaSeleccionada.getCausaId();
        TreeviewWeb.publishTree("arbolCausas", causaSeleccionada.getCausaId().toString(), "session", request, true);

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
      } else {
        reloadId = causaSeleccionada.getCausaId();
        if (closeCausaId == null) {
          TreeviewWeb.publishTreeOpen("arbolCausas", reloadId.toString(), "session", request);
        }
      }

      nodosArbolService.refreshNodosArbol((Causa)request.getSession().getAttribute("causaRoot"), (String)request.getSession().getAttribute("arbolCausas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);

      bloqueada = !nodosArbolService.bloquearParaUso(request.getSession().getId(), (NodoArbol)request.getSession().getAttribute("causa"), causaSeleccionada);

      request.getSession().setAttribute("causa", causaSeleccionada);
      request.getSession().setAttribute("causaId", causaSeleccionada.getCausaId().toString());
    }

    if (bloqueada) {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.causa.bloqueada"));
    }

    nodosArbolService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}