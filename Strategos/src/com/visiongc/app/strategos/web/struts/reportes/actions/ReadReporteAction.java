/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class ReadReporteAction extends VgcAction
{
	  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	  {
	  }

	  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	  {
		  super.execute(mapping, form, request, response);

		  String forward = mapping.getParameter();
		  
		  ReporteForm reporteForm = (ReporteForm)form;
		  reporteForm.setFrecuencia((byte) 3);
		  reporteForm.setFrecuencias(Frecuencia.getFrecuencias());

		  return mapping.findForward(forward);
	  }
}