/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class SalvarReporteAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
	    String ts = request.getParameter("ts");

		if (cancelar)
			return getForwardBack(request, 1, true);

	    if (!((ts == null) || (ts.equals(""))))
	    	forward = "finalizarForm";

	    if (forward.equals("finalizarForm")) 
	    	return getForwardBack(request, 1, true);
	    
	    return getForwardBack(request, 1, true);
	}
}