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
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarInstrumentosForm;
import com.visiongc.app.strategos.web.struts.portafolios.forms.GestionarPortafoliosForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class DesasociarIniciativaInstrumentoAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);

	    Long iniciativaId = (request.getParameter("iniciativaId") != null && request.getParameter("iniciativaId") != "") ? Long.parseLong(request.getParameter("iniciativaId")) : null;
		Long instrumentoId = (request.getParameter("instrumentoId") != null && request.getParameter("instrumentoId") != "") ? Long.parseLong(request.getParameter("instrumentoId")) : null;

		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();

	    int respuesta = strategosInstrumentosService.desasociarInstrumento(instrumentoId, iniciativaId, getUsuarioConectado(request));

	    if (respuesta == VgcReturnCode.DB_OK)
	    {
	    	GestionarInstrumentosForm gestionarInstrumentosForm = (GestionarInstrumentosForm)request.getSession().getAttribute("gestionarInstrumentosForm");
	    	if (gestionarInstrumentosForm != null)
	    		gestionarInstrumentosForm.setIniciativaId(null);
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.desasociariniciativa.portafolio.ok"));
	    }
	    else
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion"));

	    strategosInstrumentosService.close();

	    saveMessages(request, messages);

	    return getForwardBack(request, 2, true);
	}
}