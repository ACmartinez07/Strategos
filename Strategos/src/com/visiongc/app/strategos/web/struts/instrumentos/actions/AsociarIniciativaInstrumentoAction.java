/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class AsociarIniciativaInstrumentoAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar arg0, String arg1, String arg2)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		Long iniciativaId = (request.getParameter("iniciativaId") != null && request.getParameter("iniciativaId") != "") ? Long.parseLong(request.getParameter("iniciativaId")) : null;
		Long instrumentoId = (request.getParameter("instrumentoId") != null && request.getParameter("instrumentoId") != "") ? Long.parseLong(request.getParameter("instrumentoId")) : null;

		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();

		if (iniciativaId != null && iniciativaId != 0L && instrumentoId != null && instrumentoId != 0L)
		{
			Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));
			if (instrumento != null)
				strategosInstrumentosService.asociarInstrumento(instrumentoId, iniciativaId, getUsuarioConectado(request));
			else 
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		}

		strategosInstrumentosService.close();

		saveMessages(request, messages);

		return getForwardBack(request, 2, true);
	}
}