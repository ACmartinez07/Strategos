package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesIndicadorAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)form;

		String indicadorId = request.getParameter("indicadorId");

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		
		if ((indicadorId == null) || (indicadorId.equals(""))) 
			cancelar = true;

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		if (cancelar)
		{
			strategosIndicadoresService.unlockObject(request.getSession().getId(), editarIndicadorForm.getIndicadorId());
			
			strategosIndicadoresService.close();

			return getForwardBack(request, 1, true);
		}

		Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));

		if (indicador != null)
		{
			editarIndicadorForm.setNombre(indicador.getNombre());
			editarIndicadorForm.setOrganizacionId(indicador.getOrganizacionId());
			editarIndicadorForm.setClaseId(indicador.getClaseId());
			editarIndicadorForm.setNaturalezaNombre(indicador.getNaturalezaNombre());
			editarIndicadorForm.setNumeroUsosComoIndicadorInsumo(new Integer(strategosIndicadoresService.getNumeroUsosComoIndicadorInsumo(new Long(indicadorId))));
			editarIndicadorForm.setSoloLectura(indicador.getSoloLectura());
		}

		strategosIndicadoresService.close();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}
