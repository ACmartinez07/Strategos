package com.visiongc.framework.web.struts.actions.usuarios;

import java.util.ArrayList;
import java.util.Calendar;


import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.auditorias.forms.ReporteAuditoriaForm;
import com.visiongc.framework.web.struts.forms.usuarios.ReporteUsuariosForm;

public class ReporteUsuarioGrupoAction extends VgcAction{

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ReporteUsuariosForm reporteForm = (ReporteUsuariosForm)form;
		reporteForm.clear();
	  
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Usuario user = getUsuarioConectado(request);
		
		boolean isAdmin=false;
		if(user.getIsAdmin()){
			
			isAdmin=true;
		}
		
		request.getSession().setAttribute("isAdmin", isAdmin); 
		
		/* Parametros para el reporte */
		

		Date dateActual = new Date();
	    Calendar c = Calendar.getInstance();
	    c.setTime(dateActual);
	    String dia = (new StringBuilder()).append(c.get(5)).toString();
	    String mes = (new StringBuilder()).append(c.get(2) + 1).toString();
	    String ano = (new StringBuilder()).append(c.get(1)).toString();
	  
	    
	    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	    
	    PaginaLista paginaGrupos = usuariosService.getGrupos(1, "grupo", "ASC");
	    
	    if(paginaGrupos != null && paginaGrupos.getLista().size() > 0) {
	    	
	    	reporteForm.setGrupos(paginaGrupos.getLista());		
		    
	    }
	  
	    usuariosService.close();
    
	    reporteForm.setEstatus(2);
    	reporteForm.setTipoReporte((byte) 1);



		return mapping.findForward(forward);
	}
	
}
