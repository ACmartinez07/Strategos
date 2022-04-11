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
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EliminarPortafolioAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);

	    String id = request.getParameter("id");

	    StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

	    Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(id));
	    if (portafolio != null)
	    {
	    	portafolio.setId(Long.valueOf(id));
		    int respuesta = strategosPortafoliosService.delete(portafolio, getUsuarioConectado(request));
	        if (respuesta == VgcReturnCode.DB_FK_VIOLATED)
	        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", portafolio.getNombre()));
	        else
	        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", portafolio.getNombre()));
	    }
	    else
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosPortafoliosService.close();
	    saveMessages(request, messages);

	    return getForwardBack(request, 1, true);
	}
}