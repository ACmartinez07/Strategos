package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.EditarClaseIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarClaseIndicadoresAction extends VgcAction
{
	private static final String ACTION_KEY = "GuardarClaseIndicadoresAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarClaseIndicadoresForm editarClaseIndicadoresForm = (EditarClaseIndicadoresForm)form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarClaseIndicadoresAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

		if (cancelar)
		{
			strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), editarClaseIndicadoresForm.getClaseId());
			strategosClasesIndicadoresService.close();

			return getForwardBack(request, 1, true);
		}

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		ClaseIndicadores claseIndicadores = new ClaseIndicadores();
		boolean nuevo = false;

		claseIndicadores.setClaseId(editarClaseIndicadoresForm.getClaseId());
		if ((editarClaseIndicadoresForm.getClaseId() != null) && (!editarClaseIndicadoresForm.getClaseId().equals(Long.valueOf("0"))))
			claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, editarClaseIndicadoresForm.getClaseId());
		else
		{
			nuevo = true;
			claseIndicadores = new ClaseIndicadores();
			claseIndicadores.setClaseId(new Long(0L));
			
			ClaseIndicadores claseIndicadoresPadre = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, editarClaseIndicadoresForm.getPadreId());
			claseIndicadores.setPadreId(claseIndicadoresPadre.getClaseId());
		}

		claseIndicadores.setNombre(editarClaseIndicadoresForm.getNombre());
		claseIndicadores.setTipo(TipoClaseIndicadores.getTipoClaseIndicadores());

		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		claseIndicadores.setOrganizacionId(organizacionId);

		if ((editarClaseIndicadoresForm.getDescripcion() != null) && (!editarClaseIndicadoresForm.getDescripcion().equals("")))
			claseIndicadores.setDescripcion(editarClaseIndicadoresForm.getDescripcion());
		else 
			claseIndicadores.setDescripcion(null);

		if ((editarClaseIndicadoresForm.getEnlaceParcial() != null) && (!editarClaseIndicadoresForm.getEnlaceParcial().equals("")))
			claseIndicadores.setEnlaceParcial(editarClaseIndicadoresForm.getEnlaceParcial());
		else 
			editarClaseIndicadoresForm.setEnlaceParcial(null);

		claseIndicadores.setVisible(editarClaseIndicadoresForm.getVisible());

		int respuesta = 10000;
		respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseIndicadores, usuario);

		if (respuesta == 10000) 
		{
			strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), editarClaseIndicadoresForm.getClaseId());
			forward = "exito";
			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearClaseIndicadores";
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		} 
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosClasesIndicadoresService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarClaseIndicadoresAction.ultimoTs", ts);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}