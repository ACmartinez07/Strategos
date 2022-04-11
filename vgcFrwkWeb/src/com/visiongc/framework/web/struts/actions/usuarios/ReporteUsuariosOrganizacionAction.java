package com.visiongc.framework.web.struts.actions.usuarios;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.usuarios.ReporteUsuariosForm;



/**
 * @author andresmartinez
 *
 */
public class ReporteUsuariosOrganizacionAction extends VgcAction{

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {	
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
	   super.execute(mapping, form, request, response);
	   String forward = mapping.getParameter();	
	   
	   ActionMessages messages = getMessages(request);
	   ReporteUsuariosForm reporteUsuariosForm = (ReporteUsuariosForm)form;
	   reporteUsuariosForm.clear();
	   
	   FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	   Usuario user = getUsuarioConectado(request);
	   
	   boolean isAdmin=false;
		if(user.getIsAdmin()){
			
			isAdmin=true;
		}
		
		request.getSession().setAttribute("isAdmin", isAdmin); 
		
		reporteUsuariosForm.setEstatus(2);
		reporteUsuariosForm.setTipoReporte((byte) 1);
	   
	   
	   
	   return mapping.findForward(forward);
	}
	
	

}
