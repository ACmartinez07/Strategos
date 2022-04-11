/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planes.modelos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.ModeloPK;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EliminarModeloAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		Long id = Long.parseLong(request.getParameter("Id"));
	    		
		request.setAttribute("ajaxResponse", Eliminar(id, request));
		
		return mapping.findForward("ajaxResponse");
	}
	
	private int Eliminar(Long id, HttpServletRequest request)
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
		StrategosModelosService strategosModelosService = StrategosServiceFactory.getInstance().openStrategosModelosService();

		strategosModelosService.unlockObject(request.getSession().getId(), id);

	    boolean bloqueado = !strategosModelosService.lockForDelete(request.getSession().getId(), id);

		Long planId = request.getParameter("planId") != null ? new Long(request.getParameter("planId")) : null;
	    
		ModeloPK pk = new ModeloPK();
		pk.setPlanId(planId);
		pk.setModeloId(id);
		Modelo modelo = (Modelo)strategosModelosService.load(Modelo.class, pk);
	    
	    if (modelo != null)
	    {
	    	if (bloqueado)
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", modelo.getNombre()));
	    	else
	    	{
	    		result = strategosModelosService.deleteModelo(modelo, getUsuarioConectado(request));

	    		if (result == 10004)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", modelo.getNombre()));
	    		else
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", modelo.getNombre()));
	    	}
	    }
	    else
	    {
	    	result = 9999;
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
	    }

	    strategosModelosService.unlockObject(request.getSession().getId(), id);

	    strategosModelosService.close();

	    saveMessages(request, messages);

		return result; 
	}
}