package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

public class ReporteProyectosAsociadosAction extends VgcAction {

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre){}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ReporteForm reporteForm = (ReporteForm)form;
		reporteForm.clear();
	  
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Usuario user = getUsuarioConectado(request);
		String instrumentoId = (request.getParameter("instrumentoId"));
		
		String nombreCorto = request.getParameter("nombreCorto") != null ? request.getParameter("nombreCorto") : "";
		String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
		
		Long cooperanteId = (request.getParameter("cop") != null) && (request.getParameter("cop") != "") && (!request.getParameter("cop").equals("0")) ? Long.valueOf(Long.parseLong(request.getParameter("cop"))) : null;
		Long tiposConvenioId = (request.getParameter("con") != null) && (request.getParameter("con") != "") && (!request.getParameter("con").equals("0")) ? Long.valueOf(Long.parseLong(request.getParameter("con"))) : null;
		
		
		boolean isAdmin=false;
		if(user.getIsAdmin()){
			
			isAdmin=true;
		}
		
		request.getSession().setAttribute("isAdmin", isAdmin); 
		
		/* Parametros para el reporte */
		
		Calendar fecha = Calendar.getInstance();
        
		reporteForm.setId(new Long(instrumentoId));
		if(anio != "") {
			reporteForm.setAno(new Integer(anio));
		}else {
			reporteForm.setAno(null);
		}
		reporteForm.setNombre(nombreCorto);
		reporteForm.setCooperanteId(cooperanteId);
		reporteForm.setTipoCooperanteId(tiposConvenioId);
		
		Map<String, String> filtros = new HashMap<String, String>();
		

		return mapping.findForward(forward);
	}
}
