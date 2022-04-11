package com.visiongc.app.strategos.web.struts.categoriasmedicion.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
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

public class EliminarCategoriaMedicionAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarCategoriaMedicionAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String categoriaId = request.getParameter("categoriaId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarCategoriaMedicionAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((categoriaId == null) || (categoriaId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(categoriaId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService();

    bloqueado = !strategosCategoriasService.lockForDelete(request.getSession().getId(), categoriaId);

    CategoriaMedicion categoriaMedicion = (CategoriaMedicion)strategosCategoriasService.load(CategoriaMedicion.class, new Long(categoriaId));

    if (categoriaMedicion != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", categoriaMedicion.getNombre()));
      }
      else
      {
        categoriaMedicion.setCategoriaId(Long.valueOf(categoriaId));
        int res = strategosCategoriasService.deleteCategoriaMedicion(categoriaMedicion, getUsuarioConectado(request));

        strategosCategoriasService.unlockObject(request.getSession().getId(), categoriaId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", categoriaMedicion.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", categoriaMedicion.getNombre()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosCategoriasService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarCategoriaMedicionAction.ultimoTs", categoriaId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}