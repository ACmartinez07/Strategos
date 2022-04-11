package com.visiongc.app.strategos.web.struts.planes.plantillas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
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

public class EliminarPlantillaPlanesAction extends VgcAction
{
  private static final String ACTION_KEY = "EliminarPlantillaPlanesAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    ActionMessages messages = getMessages(request);

    String plantillaPlanesId = request.getParameter("plantillaPlanesId");
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("EliminarPlantillaPlanesAction.ultimoTs");
    boolean bloqueado = false;
    boolean cancelar = false;

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((plantillaPlanesId == null) || (plantillaPlanesId.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(plantillaPlanesId + "&" + ts))) {
      cancelar = true;
    }

    if (cancelar)
    {
      return getForwardBack(request, 1, true);
    }

    StrategosPlantillasPlanesService strategosPlantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();

    bloqueado = !strategosPlantillasPlanesService.lockForDelete(request.getSession().getId(), plantillaPlanesId);

    PlantillaPlanes plantillaPlanes = (PlantillaPlanes)strategosPlantillasPlanesService.load(PlantillaPlanes.class, new Long(plantillaPlanesId));

    if (plantillaPlanes != null)
    {
      if (bloqueado)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", plantillaPlanes.getNombre()));
      }
      else
      {
        plantillaPlanes.setPlantillaPlanesId(Long.valueOf(plantillaPlanesId));
        int res = strategosPlantillasPlanesService.deletePlantillaPlanes(plantillaPlanes, getUsuarioConectado(request));

        strategosPlantillasPlanesService.unlockObject(request.getSession().getId(), plantillaPlanesId);

        if (res == 10004)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", plantillaPlanes.getNombre()));
        }
        else
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", plantillaPlanes.getNombre()));
        }

      }

    }
    else
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
    }

    strategosPlantillasPlanesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("EliminarPlantillaPlanesAction.ultimoTs", plantillaPlanesId + "&" + ts);

    return getForwardBack(request, 1, true);
  }
}