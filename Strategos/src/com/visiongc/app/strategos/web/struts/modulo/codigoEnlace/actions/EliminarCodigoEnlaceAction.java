/**
 * 
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EliminarCodigoEnlaceAction  extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);

	    String id = request.getParameter("id");

	    StrategosCodigoEnlaceService strategosCodigoEnlaceService = StrategosServiceFactory.getInstance().openStrategosCodigoEnlaceService();

	    CodigoEnlace codigoEnlace = (CodigoEnlace)strategosCodigoEnlaceService.load(CodigoEnlace.class, new Long(id));

	    if (codigoEnlace != null)
	    {
	        int res = strategosCodigoEnlaceService.deleteCodigoEnlace(codigoEnlace, getUsuarioConectado(request));
	        if (res == 10004)
	        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", codigoEnlace.getNombre()));
	        else
	        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", codigoEnlace.getNombre()));
	    }
	    else
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosCodigoEnlaceService.close();

	    saveMessages(request, messages);

	    return getForwardBack(request, 1, true);
	}
}