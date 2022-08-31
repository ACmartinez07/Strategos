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
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import com.visiongc.framework.model.Usuario;

public class AsignarPesosInstrumentosParametrosAction extends VgcAction {
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarInstrumentosForm editarInstrumentosForm = (EditarInstrumentosForm)form;
		editarInstrumentosForm.clear();
	 		
		Usuario user = getUsuarioConectado(request);
		String anio = (request.getParameter("anio"));
		
		boolean isAdmin=false;
		if(user.getIsAdmin()){
			
			isAdmin=true;
		}
		
		request.getSession().setAttribute("isAdmin", isAdmin); 
		
		/* Parametros para el reporte */		
		editarInstrumentosForm.setAnio(anio);
		
		Map<String, String> filtros = new HashMap<String, String>();
		

		return mapping.findForward(forward);
	}
}
