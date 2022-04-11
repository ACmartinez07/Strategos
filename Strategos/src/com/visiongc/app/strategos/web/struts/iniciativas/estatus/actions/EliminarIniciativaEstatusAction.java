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
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EliminarIniciativaEstatusAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);

	    String id = request.getParameter("id");

	    StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();

	    IniciativaEstatus iniciativaEstatus = (IniciativaEstatus)strategosIniciativaEstatusService.load(IniciativaEstatus.class, new Long(id));
	    if (iniciativaEstatus != null)
	    {
	    	iniciativaEstatus.setId(Long.valueOf(id));
	    	if (!iniciativaEstatus.getSistema())
	    	{
		        int respuesta = strategosIniciativaEstatusService.delete(iniciativaEstatus, getUsuarioConectado(request));
		        if (respuesta == VgcReturnCode.DB_FK_VIOLATED)
		        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", iniciativaEstatus.getNombre()));
		        else
		        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", iniciativaEstatus.getNombre()));
	    	}
	    	else
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.fijo", iniciativaEstatus.getNombre()));
	    }
	    else
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosIniciativaEstatusService.close();
	    saveMessages(request, messages);

	    return getForwardBack(request, 1, true);
	}
}