package com.visiongc.app.strategos.web.struts.planes.plantillas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.web.struts.planes.plantillas.forms.EditarPlantillaPlanesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarPlantillaPlanesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		EditarPlantillaPlanesForm editarPlantillaPlanesForm = (EditarPlantillaPlanesForm)form;

		ActionMessages messages = getMessages(request);

		String plantillaPlanesId = request.getParameter("plantillaPlanesId");

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("METODOLOGIA_VIEW");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("METODOLOGIA_EDIT");
		boolean bloqueado = false;

		StrategosPlantillasPlanesService strategosPlantillasPlanesService = StrategosServiceFactory.getInstance().openStrategosPlantillasPlanesService();

		if ((plantillaPlanesId != null) && (!plantillaPlanesId.equals("")) && (!plantillaPlanesId.equals("0")))
		{
			bloqueado = !strategosPlantillasPlanesService.lockForUpdate(request.getSession().getId(), plantillaPlanesId, null);

			editarPlantillaPlanesForm.setBloqueado(new Boolean(bloqueado));

			PlantillaPlanes plantillaPlanes = (PlantillaPlanes)strategosPlantillasPlanesService.load(PlantillaPlanes.class, new Long(plantillaPlanesId));

			if (plantillaPlanes != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarPlantillaPlanesForm.setPlantillaPlanesId(plantillaPlanes.getPlantillaPlanesId());
				editarPlantillaPlanesForm.setNombre(plantillaPlanes.getNombre());
				editarPlantillaPlanesForm.setDescripcion(plantillaPlanes.getDescripcion());
				editarPlantillaPlanesForm.setNombreActividadSingular(plantillaPlanes.getNombreActividadSingular());
				editarPlantillaPlanesForm.setNombreIniciativaSingular(plantillaPlanes.getNombreIniciativaSingular());
				editarPlantillaPlanesForm.setNombreIndicadorSingular(plantillaPlanes.getNombreIndicadorSingular());

				String separadorPerspectivas = editarPlantillaPlanesForm.getSeparadorPerspectivas();
				String separadorAtributosPerspectivas = editarPlantillaPlanesForm.getSeparadorAtributosPerspectivas();
				String texto = "";
				
				if (plantillaPlanes.getElementos() != null) 
				{
					for (Iterator<?> k = plantillaPlanes.getElementos().iterator(); k.hasNext(); ) 
					{
						ElementoPlantillaPlanes elementoPlantillaPlanes = (ElementoPlantillaPlanes)k.next();
						if (texto.equals(""))
							texto = elementoPlantillaPlanes.getOrden().toString() + separadorAtributosPerspectivas + elementoPlantillaPlanes.getTipo().toString() + separadorAtributosPerspectivas + elementoPlantillaPlanes.getNombre().toString();
						else 
							texto = texto + separadorPerspectivas + elementoPlantillaPlanes.getOrden().toString() + separadorAtributosPerspectivas + elementoPlantillaPlanes.getTipo().toString() + separadorAtributosPerspectivas + elementoPlantillaPlanes.getNombre().toString();
					}
				}

				editarPlantillaPlanesForm.setTextoNiveles(texto);
			}
			else
			{
				strategosPlantillasPlanesService.unlockObject(request.getSession().getId(), new Long(plantillaPlanesId));

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
			editarPlantillaPlanesForm.clear();

		strategosPlantillasPlanesService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarPlantillaPlanesForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
		
		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}