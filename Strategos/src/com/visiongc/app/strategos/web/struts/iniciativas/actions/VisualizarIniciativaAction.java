package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.web.struts.iniciativas.forms.EditarIniciativaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VisualizarIniciativaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIniciativaForm editarIniciativaForm = (EditarIniciativaForm)form;
    
		forward = new com.visiongc.app.strategos.web.struts.iniciativas.actions.EditarIniciativaAction().getData(editarIniciativaForm, forward, request);

		if (forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}