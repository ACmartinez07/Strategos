package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class SeleccionarVistaGraficoAction 

	 extends VgcAction
	{
		public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
		{
		}

		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			super.execute(mapping, form, request, response);

			String forward = mapping.getParameter();

			ReporteForm reporteForm = (ReporteForm)form;
			reporteForm.clear();
		  
			/* Parametros para el reporte */
			
					   
		    /*Asigna a la Forma que genera reportes, el nombre de la organizacion y plan seleccionados*/
		    reporteForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			

			return mapping.findForward(forward);
		}
	}


