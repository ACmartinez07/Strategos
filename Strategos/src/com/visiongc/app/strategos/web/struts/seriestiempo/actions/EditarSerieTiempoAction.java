package com.visiongc.app.strategos.web.struts.seriestiempo.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.seriestiempo.forms.EditarSerieTiempoForm;
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

public class EditarSerieTiempoAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		EditarSerieTiempoForm editarSerieTiempoForm = (EditarSerieTiempoForm)form;
		
		ActionMessages messages = getMessages(request);

		String serieId = request.getParameter("serieId");

		boolean bloqueado = false;

		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService();

		if ((serieId != null) && (!serieId.equals("")) && (!serieId.equals("0")))
		{
			bloqueado = !strategosSeriesTiempoService.lockForUpdate(request.getSession().getId(), serieId, null);
			
			editarSerieTiempoForm.setBloqueado(new Boolean(bloqueado));

			SerieTiempo serieTiempo = (SerieTiempo)strategosSeriesTiempoService.load(SerieTiempo.class, new Long(serieId));

			if (serieTiempo != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
				
				editarSerieTiempoForm.setSerieId(serieTiempo.getSerieId());
				editarSerieTiempoForm.setNombre(serieTiempo.getNombre());
				editarSerieTiempoForm.setIdentificador(serieTiempo.getIdentificador());
				editarSerieTiempoForm.setFija(serieTiempo.getFija());
				editarSerieTiempoForm.setPreseleccionada(serieTiempo.getPreseleccionada());
				editarSerieTiempoForm.setBloqueado(editarSerieTiempoForm.getFija());
			}
			else
			{
				strategosSeriesTiempoService.unlockObject(request.getSession().getId(), new Long(serieId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
			editarSerieTiempoForm.clear();

		strategosSeriesTiempoService.close();
		
		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}