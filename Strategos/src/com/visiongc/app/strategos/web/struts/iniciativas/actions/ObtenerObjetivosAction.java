package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacionPK;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class ObtenerObjetivosAction extends VgcAction{
	 

		public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
		 {
		 }
		
		 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				    throws Exception
		 {
		    super.execute(mapping, form, request, response);
		    String forward = mapping.getParameter();
		     
			
			
			return mapping.findForward(forward);
		 }
}
