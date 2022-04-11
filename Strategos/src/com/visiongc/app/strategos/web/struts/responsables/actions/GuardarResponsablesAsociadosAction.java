package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.web.struts.responsables.forms.AsociarResponsablesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarResponsablesAsociadosAction extends VgcAction
{
	private static final String ACTION_KEY = "GuardarResponsablesAsociadosAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		AsociarResponsablesForm asociarResponsablesForm = (AsociarResponsablesForm)form;
		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarResponsablesAsociadosAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

		if (cancelar)
		{
			strategosResponsablesService.unlockObject(request.getSession().getId(), asociarResponsablesForm.getResponsableId());
			strategosResponsablesService.close();
			return getForwardBack(request, 1, true);
		}

		int respuesta = 10000;
		String responsableId = request.getParameter("responsableId");
		long[] asociadoId = (long[])null;
		String listaStrResponsables = request.getParameter("listaTabla2");
		String[] listArrResponsables = (String[])null;

		if ((listaStrResponsables != null) && (listaStrResponsables != "")) 
		{
			listaStrResponsables = listaStrResponsables.substring(1);
			listArrResponsables = StringUtil.split(listaStrResponsables, "|");
			int cantidadReponsables = 0;
			for (int i = 0; i < listArrResponsables.length; i++)
				if (!listArrResponsables[i].equals(""))
					cantidadReponsables++;

			asociadoId = new long[cantidadReponsables];
			for (int i = 0; i < listArrResponsables.length; i++)
				if (!listArrResponsables[i].equals(""))
					asociadoId[i] = new Long(listArrResponsables[i]).longValue();
		}

		respuesta = strategosResponsablesService.guardarGrupoResponsables(Long.parseLong(responsableId), asociadoId, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosResponsablesService.unlockObject(request.getSession().getId(), null);
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarresgistro.asociado.ok"));
			forward = "exito";
		}

		strategosResponsablesService.close();

		saveMessages(request, messages);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);
		return mapping.findForward(forward);
	}
}