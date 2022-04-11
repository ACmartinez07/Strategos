package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.responsables.forms.EditarResponsableForm;
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

public class GuardarResponsableAction extends VgcAction
{
	private static final String ACTION_KEY = "GuardarResponsableAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		EditarResponsableForm editarResponsableForm = (EditarResponsableForm)form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarResponsableAction.ultimoTs");
		
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

		if (cancelar)
		{
			strategosResponsablesService.unlockObject(request.getSession().getId(), editarResponsableForm.getResponsableId());
			strategosResponsablesService.close();
			return getForwardBack(request, 1, true);
		}

		Responsable responsable = new Responsable();
		boolean nuevo = false;
		int respuesta = 10000;

		if ((editarResponsableForm.getResponsableId() != null) && (!editarResponsableForm.getResponsableId().equals(Long.valueOf("0"))))
			responsable = (Responsable)strategosResponsablesService.load(Responsable.class, editarResponsableForm.getResponsableId());
		else
		{
			nuevo = true;
			responsable = new Responsable();
			responsable.setResponsableId(new Long(0L));
		}

		responsable.setCargo(editarResponsableForm.getCargo());
		responsable.setTipo(editarResponsableForm.getTipo());
		responsable.setOrganizacionId(new Long((String)request.getSession().getAttribute("organizacionId")));
		
		if ((editarResponsableForm.getNombre() != null) && (!editarResponsableForm.getNombre().equals("")))
			responsable.setNombre(editarResponsableForm.getNombre());
		else 
			responsable.setNombre(null);

		if ((editarResponsableForm.getUbicacion() != null) && (!editarResponsableForm.getUbicacion().equals("")))
			responsable.setUbicacion(editarResponsableForm.getUbicacion());
		else 
			responsable.setUbicacion(null);

		if ((editarResponsableForm.getEmail() != null) && (!editarResponsableForm.getEmail().equals("")))
			responsable.setEmail(editarResponsableForm.getEmail());
		else 
			responsable.setEmail(null);

		if ((editarResponsableForm.getNotas() != null) && (!editarResponsableForm.getNotas().equals("")))
			responsable.setNotas(editarResponsableForm.getNotas());
		else 
			responsable.setNotas(null);

		if ((editarResponsableForm.getUsuarioId() != null) && (editarResponsableForm.getUsuarioId().longValue() != 0L))
			responsable.setUsuarioId(editarResponsableForm.getUsuarioId());
		else 
			responsable.setUsuarioId(null);

		respuesta = strategosResponsablesService.saveResponsable(responsable, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosResponsablesService.unlockObject(request.getSession().getId(), null);
			forward = "exito";

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearResponsable";
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosResponsablesService.close();

		saveMessages(request, messages);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);
		return mapping.findForward(forward);
	}
}