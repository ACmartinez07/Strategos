package com.visiongc.app.strategos.web.struts.planes.plantillas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.web.struts.planes.plantillas.forms.EditarPlantillaPlanesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarPlantillaPlanesAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarPlantillaPlanesAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarPlantillaPlanesForm editarPlantillaPlanesForm = (EditarPlantillaPlanesForm)form;

    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarPlantillaPlanesAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosPlantillasPlanesService strategosPlantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();

    if (cancelar)
    {
      strategosPlantillasPlanesService.unlockObject(request.getSession().getId(), editarPlantillaPlanesForm.getPlantillaPlanesId());

      strategosPlantillasPlanesService.close();

      return getForwardBack(request, 1, true);
    }

    PlantillaPlanes plantillaPlanes = new PlantillaPlanes();
    boolean nuevo = false;
    int respuesta = 10000;

    if ((editarPlantillaPlanesForm.getPlantillaPlanesId() != null) && (!editarPlantillaPlanesForm.getPlantillaPlanesId().equals(Long.valueOf("0"))))
    {
      plantillaPlanes = (PlantillaPlanes)strategosPlantillasPlanesService.load(PlantillaPlanes.class, editarPlantillaPlanesForm.getPlantillaPlanesId());
    }
    else
    {
      nuevo = true;
      plantillaPlanes = new PlantillaPlanes();
      plantillaPlanes.setPlantillaPlanesId(new Long(0L));

      plantillaPlanes.setElementos(new HashSet());
      if ((editarPlantillaPlanesForm.getTextoNiveles() != null) && (!editarPlantillaPlanesForm.getTextoNiveles().equals("")))
      {
        String[] niveles = editarPlantillaPlanesForm.getTextoNiveles().split(editarPlantillaPlanesForm.getSeparadorPerspectivas());
        for (int i = 0; i < niveles.length; i++) {
          String[] arregloTemporal = niveles[i].split(editarPlantillaPlanesForm.getSeparadorAtributosPerspectivas());
          ElementoPlantillaPlanes elementoPlantillaPlanes = new ElementoPlantillaPlanes();
          elementoPlantillaPlanes.setElementoId(new Long(0L));
          elementoPlantillaPlanes.setOrden(new Integer(arregloTemporal[0]));
          elementoPlantillaPlanes.setTipo(new Byte(arregloTemporal[1]));
          elementoPlantillaPlanes.setNombre(arregloTemporal[2].toString());
          plantillaPlanes.getElementos().add(elementoPlantillaPlanes);
        }

      }

    }

    plantillaPlanes.setNombre(editarPlantillaPlanesForm.getNombre());
    plantillaPlanes.setNombreIndicadorSingular(editarPlantillaPlanesForm.getNombreIndicadorSingular());
    plantillaPlanes.setNombreIniciativaSingular(editarPlantillaPlanesForm.getNombreIniciativaSingular());
    plantillaPlanes.setNombreActividadSingular(editarPlantillaPlanesForm.getNombreActividadSingular());

    if ((editarPlantillaPlanesForm.getDescripcion() != null) && (!editarPlantillaPlanesForm.getDescripcion().equals("")))
      plantillaPlanes.setDescripcion(editarPlantillaPlanesForm.getDescripcion());
    else {
      plantillaPlanes.setDescripcion(null);
    }

    respuesta = strategosPlantillasPlanesService.savePlantillaPlanes(plantillaPlanes, getUsuarioConectado(request));

    if (respuesta == 10000)
    {
      strategosPlantillasPlanesService.unlockObject(request.getSession().getId(), editarPlantillaPlanesForm.getPlantillaPlanesId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearPlantillaPlanes";
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }

    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosPlantillasPlanesService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarPlantillaPlanesAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}