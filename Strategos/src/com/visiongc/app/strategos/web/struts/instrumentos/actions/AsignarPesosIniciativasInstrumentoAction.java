/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

/**
 * @author andres_martinez
 *
 */
public class AsignarPesosIniciativasInstrumentoAction extends VgcAction{

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {	
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    EditarInstrumentosForm editarInstrumentosForm = (EditarInstrumentosForm)form;
		editarInstrumentosForm.clear();
	  
		
		
	    return mapping.findForward(forward);
	}
}
