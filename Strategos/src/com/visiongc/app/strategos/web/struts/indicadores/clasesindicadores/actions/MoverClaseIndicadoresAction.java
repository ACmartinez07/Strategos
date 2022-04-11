/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class MoverClaseIndicadoresAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		ActionMessages messages = getMessages(request);

		Long claseId = Long.parseLong(request.getParameter("claseId").toString());
		Long claseSeleccionId = Long.parseLong(request.getParameter("clasePadreId").toString());

		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(claseId));

		int respuesta = 10000;
		Byte status = StatusUtil.getStatusNoSuccess();
		if (claseSeleccionId.longValue() != clase.getPadreId().longValue())
		{
			if (claseSeleccionId.longValue() != clase.getClaseId())
			{
				if (!claseSeleccionEsHijo(claseSeleccionId, claseId, strategosClasesIndicadoresService))
				{
					clase.setPadreId(claseSeleccionId);
					respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(clase, getUsuarioConectado(request));
				    if (respuesta == 10000)
				    {
				    	strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), new Long(claseId));
				    	status = StatusUtil.getStatusSuccess();
				    }
				}
			}
		}
		
		strategosClasesIndicadoresService.close();

	    saveMessages(request, messages);

	    request.setAttribute("ajaxResponse", status.toString());
		return mapping.findForward("ajaxResponse");
	}
	
	private boolean claseSeleccionEsHijo(Long claseSeleccionId, Long claseId, StrategosClasesIndicadoresService strategosClasesIndicadoresService)
	{
		ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(claseSeleccionId));
		if (clase.getPadreId() != null && clase.getPadreId().longValue() == claseId.longValue())
			return true;
		else if (clase.getPadreId() != null)
			return claseSeleccionEsHijo(clase.getPadreId(), claseId, strategosClasesIndicadoresService);
		else
			return false;
	}
}