/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ParametroReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class ParametroReporteAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ParametroReporteForm parametroReporteForm = (ParametroReporteForm)form;
		parametroReporteForm.clear();
		
		//ActionMessages messages = getMessages(request);
		
	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	
	    	if (funcion.equals("parametros"))
	    	{
	    		if (request.getParameter("limitePeriodo") != null) 
	    			parametroReporteForm.setLimitePeriodo(Integer.parseInt(request.getParameter("limitePeriodo")));
	    		if (request.getParameter("frecuencia") != null) 
	    			parametroReporteForm.setFrecuencia(Byte.parseByte(request.getParameter("frecuencia")));
	    		else 
	    			parametroReporteForm.setFrecuencia(Frecuencia.getFrecuenciaMensual());
	    		
	    		Calendar ahora = Calendar.getInstance();
	    		
	    		parametroReporteForm.setAnoDesde(new Integer(ahora.get(1)).toString());
	    		parametroReporteForm.setAnoHasta(new Integer(ahora.get(1)).toString());
	    		int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(parametroReporteForm.getFrecuencia().byteValue(), Integer.parseInt(parametroReporteForm.getAnoHasta()));
	    		parametroReporteForm.setPeriodoDesde(new Integer(1));
	    		if (parametroReporteForm.getFrecuencia().byteValue() < Frecuencia.getFrecuenciaTrimestral().byteValue())
	    			parametroReporteForm.setPeriodoHasta(parametroReporteForm.getLimitePeriodo());
	    		else
	    			parametroReporteForm.setPeriodoHasta(new Integer(numeroMaximoPeriodos));
	    		
	    		ahora.set(1, Integer.parseInt(parametroReporteForm.getAnoDesde()));
	    		ahora.set(2, 0);
	    		ahora.set(5, 1);
	    		parametroReporteForm.setFechaDesde(VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta"));
	    		ahora.set(1, Integer.parseInt(parametroReporteForm.getAnoHasta()));
	    		ahora.set(2, 0);
	    		ahora.set(5, parametroReporteForm.getLimitePeriodo());
	    		parametroReporteForm.setFechaHasta(VgcFormatter.formatearFecha(ahora.getTime(), "formato.fecha.corta"));
	    		
	    		return mapping.findForward(forward);
	    	}
	    }
	    
	    return mapping.findForward(forward);
	}
}
