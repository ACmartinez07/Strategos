package com.visiongc.app.strategos.web.struts.estadosacciones.actions;

import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.estadosacciones.forms.EditarEstadoAccionesForm;
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

public class GuardarEstadoAccionesAction extends VgcAction
{
	private static final String ACTION_KEY = "GuardarEstadoAccionesAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarEstadoAccionesForm editarEstadoAccionesForm = (EditarEstadoAccionesForm)form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarEstadoAccionesAction.ultimoTs");
		Boolean estadoAccionesEstaEnUso = new Boolean(false);

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosEstadosService strategosEstadosService = StrategosServiceFactory.getInstance().openStrategosEstadosService();

		if (cancelar)
		{
			strategosEstadosService.unlockObject(request.getSession().getId(), editarEstadoAccionesForm.getEstadoId());
			
			strategosEstadosService.close();

			return getForwardBack(request, 1, true);
		}

		EstadoAcciones estadoAcciones = new EstadoAcciones();
		boolean nuevo = false;
		int respuesta = 10000;

		estadoAcciones.setEstadoId(editarEstadoAccionesForm.getEstadoId());
		estadoAcciones.setNombre(editarEstadoAccionesForm.getNombre());

		if ((editarEstadoAccionesForm.getEstadoId() != null) && (!editarEstadoAccionesForm.getEstadoId().equals(Long.valueOf("0"))))
			estadoAcciones = (EstadoAcciones)strategosEstadosService.load(EstadoAcciones.class, editarEstadoAccionesForm.getEstadoId());
		else
		{
			nuevo = true;
			estadoAcciones = new EstadoAcciones();
			estadoAcciones.setEstadoId(new Long(0L));
		}

		estadoAcciones.setNombre(editarEstadoAccionesForm.getNombre());
		estadoAcciones.setAplicaSeguimiento(editarEstadoAccionesForm.getAplicaSeguimiento());

		EstadoAcciones estadoAccionesIndicaFinalizacion = strategosEstadosService.estadoAccionesIndicaFinalizacion();

		if (estadoAccionesIndicaFinalizacion != null) 
			estadoAccionesEstaEnUso = strategosEstadosService.estadoAccionesEstaEnUso(estadoAccionesIndicaFinalizacion.getEstadoId());

		if (estadoAccionesEstaEnUso.booleanValue()) 
			estadoAcciones.setCondicion(new Boolean(false));
		else 
		{
			if ((estadoAccionesIndicaFinalizacion != null) && (editarEstadoAccionesForm.getIndicaFinalizacion() != null) && (editarEstadoAccionesForm.getIndicaFinalizacion().booleanValue())) 
			{
				estadoAccionesIndicaFinalizacion.setCondicion(new Boolean(false));
				strategosEstadosService.saveEstadoAcciones(estadoAccionesIndicaFinalizacion, getUsuarioConectado(request));
			}
			estadoAcciones.setCondicion(editarEstadoAccionesForm.getIndicaFinalizacion());
		}

		respuesta = strategosEstadosService.saveEstadoAcciones(estadoAcciones, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosEstadosService.unlockObject(request.getSession().getId(), null);
			forward = "exito";

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearEstadoAcciones";
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosEstadosService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarEstadoAccionesAction.ultimoTs", ts);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}