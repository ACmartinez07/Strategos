/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.web.struts.portafolios.forms.GestionarPortafoliosForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class DesasociarIniciativaPortafolioAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);

		Long iniciativaId = (request.getParameter("iniciativaId") != null && request.getParameter("iniciativaId") != "") ? Long.parseLong(request.getParameter("iniciativaId")) : null;
		Long portafolioId = (request.getParameter("portafolioId") != null && request.getParameter("portafolioId") != "") ? Long.parseLong(request.getParameter("portafolioId")) : null;

		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

	    int respuesta = strategosPortafoliosService.desasociarIniciativa(portafolioId, iniciativaId, getUsuarioConectado(request));

	    if (respuesta == VgcReturnCode.DB_OK)
	    {
	    	GestionarPortafoliosForm gestionarPortafoliosForm = (GestionarPortafoliosForm)request.getSession().getAttribute("gestionarPortafoliosForm");
	    	if (gestionarPortafoliosForm != null)
	    		gestionarPortafoliosForm.setIniciativaId(null);
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.desasociariniciativa.portafolio.ok"));
	    }
	    else
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion"));

	    strategosPortafoliosService.close();

	    saveMessages(request, messages);

	    return getForwardBack(request, 1, true);
	}
}