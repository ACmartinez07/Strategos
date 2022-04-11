package com.visiongc.app.strategos.web.struts.presentaciones.paginas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarPaginaAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarPaginaAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String paginaId = request.getParameter("paginaId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute(
      "EliminarPaginaAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((paginaId == null) || (paginaId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(paginaId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosPaginasService strategosPaginasService = 
      StrategosServiceFactory.getInstance().openStrategosPaginasService();

    strategosPaginasService.unlockObject(request.getSession().getId(), paginaId);

    bloqueado = !strategosPaginasService.lockForDelete(request.getSession()
      .getId(), paginaId);

    Pagina pagina = (Pagina)strategosPaginasService.load(Pagina.class, 
      new Long(paginaId));

    if (pagina != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", 
          new ActionMessage("action.eliminarregistro.bloqueado", pagina
          .getDescripcion()));
      }
      else
      {
        pagina.setPaginaId(Long.valueOf(paginaId));
        int res = strategosPaginasService.deletePagina(pagina, 
          getUsuarioConectado(request));

        strategosPaginasService.unlockObject(request.getSession()
          .getId(), paginaId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", 
            new ActionMessage(
            "action.eliminarregistro.relacion", pagina
            .getDescripcion()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", 
            new ActionMessage(
            "action.eliminarregistro.eliminacionok", 
            pagina.getDescripcion()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", 
        new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosPaginasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarPaginaAction.ultimoTs", 
      paginaId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}