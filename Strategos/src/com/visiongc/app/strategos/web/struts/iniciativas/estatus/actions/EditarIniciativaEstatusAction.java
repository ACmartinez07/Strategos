/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.estatus.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.iniciativas.estatus.forms.EditarIniciativaEstatusForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EditarIniciativaEstatusAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIniciativaEstatusForm editarIniciativaEstatusForm = (EditarIniciativaEstatusForm)form;
		if (editarIniciativaEstatusForm != null && editarIniciativaEstatusForm.getStatus() != null && editarIniciativaEstatusForm.getStatus().byteValue() == StatusUtil.getStatusAlertaNotDefinida().byteValue())
			return mapping.findForward(forward);
		
		editarIniciativaEstatusForm.clear();
		
		ActionMessages messages = getMessages(request);

		String id = request.getParameter("id");
		
		boolean verForm = getPermisologiaUsuario(request).tienePermiso("INICIATIVA_ESTATUS_VIEW");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("INICIATIVA_ESTATUS_EDIT");
		
		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();

		if ((id != null) && (!id.equals("")) && (!id.equals("0")))
		{
			IniciativaEstatus iniciativaEstatus = (IniciativaEstatus)strategosIniciativaEstatusService.load(IniciativaEstatus.class, new Long(id));

			if (iniciativaEstatus != null)
			{
				editarIniciativaEstatusForm.setId(iniciativaEstatus.getId());
				editarIniciativaEstatusForm.setNombre(iniciativaEstatus.getNombre());
				editarIniciativaEstatusForm.setPorcentajeInicial(iniciativaEstatus.getPorcentajeInicial());
				editarIniciativaEstatusForm.setPorcentajeFinal(iniciativaEstatus.getPorcentajeFinal());
				editarIniciativaEstatusForm.setSistema(iniciativaEstatus.getSistema());
				editarIniciativaEstatusForm.setBloquearMedicion(iniciativaEstatus.getBloquearMedicion());
				editarIniciativaEstatusForm.setBloquearIndicadores(iniciativaEstatus.getBloquearIndicadores());
			}
			else 
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}

		strategosIniciativaEstatusService.close();

		if (verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarIniciativaEstatusForm.setBloqueado(true);
		}
		else if (!verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
    
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}
