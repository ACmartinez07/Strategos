package com.visiongc.app.strategos.web.struts.causas.actions;

import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.struts.forms.SelectorArbolForm;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class SeleccionarCausasAction extends VgcAction
{
  public static final String ACTION_KEY = "SeleccionarCausasAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    ActionMessages messages = getMessages(request);

    String causaId = request.getParameter("causaId");

    SelectorArbolForm seleccionarCausasForm = (SelectorArbolForm)form;

    boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("CAUSA_VIEWALL");

    seleccionarCausasForm.setFuncionCierre(request.getParameter("funcionCierre"));

    if (seleccionarCausasForm.getFuncionCierre() != null) {
      if (!seleccionarCausasForm.getFuncionCierre().equals("")) {
        if (seleccionarCausasForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarCausasForm.setFuncionCierre(seleccionarCausasForm.getFuncionCierre() + ";");
      }
      else {
        seleccionarCausasForm.setFuncionCierre(null);
      }
    }

    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
    ArbolBean arbolBean = (ArbolBean)request.getSession().getAttribute("seleccionarCausasArbolBean");

    if (arbolBean == null) {
      arbolBean = new ArbolBean();
      arbolBean.clear();
      request.getSession().setAttribute("seleccionarCausasArbolBean", arbolBean);
    }

    if (arbolBean.getNodoSeleccionado() == null)
    {
      Causa causaRoot = new Causa();
      Causa causa = null;
      if ((causaId != null) && (!causaId.equals(""))) {
        causa = (Causa)arbolesService.load(Causa.class, new Long(causaId));
        if (causa != null) {
          List nodos = arbolesService.getRutaCompleta(causa);
          for (Iterator iter = nodos.iterator(); iter.hasNext(); ) {
            Causa c = (Causa)iter.next();
            TreeviewWeb.publishArbolAbrirNodo(arbolBean, c.getCausaId().toString());
          }
          causaRoot = new Causa();
          causaRoot.setCausaId(((Causa)nodos.get(0)).getCausaId());
          causaRoot.setPadreId(((Causa)nodos.get(0)).getPadreId());
          causaRoot.setNombre(((Causa)nodos.get(0)).getNombre());
        } else {
          causaRoot = (Causa)arbolesService.getNodoArbolRaiz(causaRoot);
          TreeviewWeb.publishArbol(arbolBean, null, causaRoot.getCausaId().toString(), null, null, true);
          causa = causaRoot;
        }
      } else {
        causaRoot = (Causa)arbolesService.getNodoArbolRaiz(causaRoot);
        TreeviewWeb.publishArbol(arbolBean, null, causaRoot.getCausaId().toString(), null, null, true);
        causa = causaRoot;
      }

      arbolesService.bloquearParaUso(request.getSession().getId(), null, causa);

      arbolBean.setNodoRaiz(causaRoot);

      arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
      arbolBean.setNodoSeleccionado(causa);
      arbolBean.setSeleccionadoId(causa.getCausaId().toString());
    }
    else
    {
      String seleccionarCausaId = request.getParameter("seleccionarCausaId");
      String abrirCausaId = request.getParameter("abrirCausaId");
      String cerrarCausaId = request.getParameter("cerrarCausaId");
      Causa causaSeleccionada = null;

      if (request.getAttribute("SeleccionarCausasAction.reloadId") != null) {
        causaSeleccionada = (Causa)arbolesService.load(Causa.class, (Long)request.getAttribute("SeleccionarCausasAction.reloadId"));
      } else if ((seleccionarCausaId != null) && (!seleccionarCausaId.equals(""))) {
        causaSeleccionada = (Causa)arbolesService.load(Causa.class, new Long(seleccionarCausaId));
      } else if ((abrirCausaId != null) && (!abrirCausaId.equals(""))) {
        TreeviewWeb.publishArbolAbrirNodo(arbolBean, abrirCausaId);
        causaSeleccionada = (Causa)arbolesService.load(Causa.class, new Long(abrirCausaId));
      } else if ((cerrarCausaId != null) && (!cerrarCausaId.equals(""))) {
        TreeviewWeb.publishArbolCerrarNodo(arbolBean, cerrarCausaId);
        causaSeleccionada = (Causa)arbolesService.load(Causa.class, new Long(cerrarCausaId));
      } else {
        causaSeleccionada = (Causa)arbolesService.load(Causa.class, new Long(arbolBean.getSeleccionadoId()));
      }
      Long reloadId;
      if (causaSeleccionada == null) {
        causaSeleccionada = (Causa)arbolBean.getNodoRaiz();
        reloadId = causaSeleccionada.getCausaId();
        TreeviewWeb.publishArbol(arbolBean, causaSeleccionada.getCausaId().toString(), true);

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.seleccionar.noencontrado"));
      } else {
        reloadId = causaSeleccionada.getCausaId();
        if (cerrarCausaId == null) {
          TreeviewWeb.publishArbolAbrirNodo(arbolBean, reloadId.toString());
        }
      }

      arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);

      arbolesService.bloquearParaUso(request.getSession().getId(), arbolBean.getNodoSeleccionado(), causaSeleccionada);

      arbolBean.setNodoSeleccionado(causaSeleccionada);
      arbolBean.setSeleccionadoId(causaSeleccionada.getCausaId().toString());
    }

    arbolesService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}