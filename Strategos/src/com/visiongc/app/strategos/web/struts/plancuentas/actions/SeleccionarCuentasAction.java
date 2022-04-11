package com.visiongc.app.strategos.web.struts.plancuentas.actions;

import com.visiongc.app.strategos.plancuentas.model.Cuenta;
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

public final class SeleccionarCuentasAction extends VgcAction
{
  public static final String ACTION_KEY = "SeleccionarCuentasAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    ActionMessages messages = getMessages(request);

    String cuentaId = request.getParameter("cuentaId");

    SelectorArbolForm seleccionarCuentasForm = (SelectorArbolForm)form;

    boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("CUENTA_VIEWALL");

    seleccionarCuentasForm.setFuncionCierre(request.getParameter("funcionCierre"));

    if (seleccionarCuentasForm.getFuncionCierre() != null) {
      if (!seleccionarCuentasForm.getFuncionCierre().equals("")) {
        if (seleccionarCuentasForm.getFuncionCierre().indexOf(";") < 0)
          seleccionarCuentasForm.setFuncionCierre(seleccionarCuentasForm.getFuncionCierre() + ";");
      }
      else {
        seleccionarCuentasForm.setFuncionCierre(null);
      }
    }

    ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
    ArbolBean arbolBean = (ArbolBean)request.getSession().getAttribute("seleccionarCuentasArbolBean");

    if (arbolBean == null) {
      arbolBean = new ArbolBean();
      arbolBean.clear();
      request.getSession().setAttribute("seleccionarCuentasArbolBean", arbolBean);
    }

    if (arbolBean.getNodoSeleccionado() == null)
    {
      Cuenta cuentaRoot = new Cuenta();
      Cuenta cuenta = null;
      if ((cuentaId != null) && (!cuentaId.equals(""))) {
        cuenta = (Cuenta)arbolesService.load(Cuenta.class, new Long(cuentaId));
        if (cuenta != null) {
          List nodos = arbolesService.getRutaCompleta(cuenta);
          for (Iterator iter = nodos.iterator(); iter.hasNext(); ) {
            Cuenta c = (Cuenta)iter.next();
            TreeviewWeb.publishArbolAbrirNodo(arbolBean, c.getCuentaId().toString());
          }
          cuentaRoot = new Cuenta();
          cuentaRoot.setCuentaId(((Cuenta)nodos.get(0)).getCuentaId());
          cuentaRoot.setPadreId(((Cuenta)nodos.get(0)).getPadreId());
          cuentaRoot.setCodigo(((Cuenta)nodos.get(0)).getCodigo());
        } else {
          cuentaRoot = (Cuenta)arbolesService.getNodoArbolRaiz(cuentaRoot);
          TreeviewWeb.publishArbol(arbolBean, null, cuentaRoot.getCuentaId().toString(), null, null, true);
          cuenta = cuentaRoot;
        }
      } else {
        cuentaRoot = (Cuenta)arbolesService.getNodoArbolRaiz(cuentaRoot);
        TreeviewWeb.publishArbol(arbolBean, null, cuentaRoot.getCuentaId().toString(), null, null, true);
        cuenta = cuentaRoot;
      }

      arbolesService.bloquearParaUso(request.getSession().getId(), null, cuenta);

      arbolBean.setNodoRaiz(cuentaRoot);

      arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
      arbolBean.setNodoSeleccionado(cuenta);
      arbolBean.setSeleccionadoId(cuenta.getCuentaId().toString());
    }
    else
    {
      String seleccionarCuentaId = request.getParameter("seleccionarCuentaId");
      String abrirCuentaId = request.getParameter("abrirCuentaId");
      String cerrarCuentaId = request.getParameter("cerrarCuentaId");
      Cuenta cuentaSeleccionada = null;

      if (request.getAttribute("SeleccionarCuentasAction.reloadId") != null) {
        cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, (Long)request.getAttribute("SeleccionarCuentasAction.reloadId"));
      } else if ((seleccionarCuentaId != null) && (!seleccionarCuentaId.equals(""))) {
        cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(seleccionarCuentaId));
      } else if ((abrirCuentaId != null) && (!abrirCuentaId.equals(""))) {
        TreeviewWeb.publishArbolAbrirNodo(arbolBean, abrirCuentaId);
        cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(abrirCuentaId));
      } else if ((cerrarCuentaId != null) && (!cerrarCuentaId.equals(""))) {
        TreeviewWeb.publishArbolCerrarNodo(arbolBean, cerrarCuentaId);
        cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(cerrarCuentaId));
      } else {
        cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(arbolBean.getSeleccionadoId()));
      }
      Long reloadId;
      if (cuentaSeleccionada == null) {
        cuentaSeleccionada = (Cuenta)arbolBean.getNodoRaiz();
        reloadId = cuentaSeleccionada.getCuentaId();
        TreeviewWeb.publishArbol(arbolBean, cuentaSeleccionada.getCuentaId().toString(), true);

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.seleccionar.noencontrado"));
      } else {
        reloadId = cuentaSeleccionada.getCuentaId();
        if (cerrarCuentaId == null) {
          TreeviewWeb.publishArbolAbrirNodo(arbolBean, reloadId.toString());
        }
      }

      arbolesService.refreshNodosArbol(arbolBean.getNodoRaiz(), arbolBean.getListaNodosAbiertos(), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);

      arbolesService.bloquearParaUso(request.getSession().getId(), arbolBean.getNodoSeleccionado(), cuentaSeleccionada);

      arbolBean.setNodoSeleccionado(cuentaSeleccionada);
      arbolBean.setSeleccionadoId(cuentaSeleccionada.getCuentaId().toString());
    }

    arbolesService.close();

    saveMessages(request, messages);

    return mapping.findForward(forward);
  }
}