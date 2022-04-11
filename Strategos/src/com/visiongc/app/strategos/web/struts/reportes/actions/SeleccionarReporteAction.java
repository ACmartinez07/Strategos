/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.web.struts.reportes.forms.SeleccionarReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class SeleccionarReporteAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("eliminar")) 
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		Eliminar(id, request);
	    		
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	
	    	if (funcion.equals("read")) 
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		
	    		Read(id, request);
	    		
	    		return mapping.findForward("ajaxResponse");
	    	}

	    	if (funcion.equals("corte"))
	    	{
	    		SeleccionarReporteForm seleccionarReporteForm = new SeleccionarReporteForm();
	    		seleccionarReporteForm.clear();
	    		
	    		return mapping.findForward(forward);
	    	}
	    }
		
		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
	    String ts = request.getParameter("ts");

		if (cancelar)
			return getForwardBack(request, 1, true);

		SeleccionarReporteForm seleccionarReporteForm = (SeleccionarReporteForm)form;
		
		if (request.getParameter("nombreForma") != null) 
			seleccionarReporteForm.setNombreForma(request.getParameter("nombreForma"));
		if (request.getParameter("nombreCampoOculto") != null) 
			seleccionarReporteForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
		if (request.getParameter("funcionCierre") != null) 
			seleccionarReporteForm.setFuncionCierre(request.getParameter("funcionCierre"));
		
	    if (!((ts == null) || (ts.equals(""))))
	    	forward = "finalizarForm";

	    if (forward.equals("finalizarForm")) 
	    	return getForwardBack(request, 1, true);
	    
	    return getForwardBack(request, 1, true);
	}
	
	private int Eliminar(Long id, HttpServletRequest request)
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
	    StrategosReportesService strategosReportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();

	    strategosReportesService.unlockObject(request.getSession().getId(), id);

	    boolean bloqueado = !strategosReportesService.lockForDelete(request.getSession().getId(), id);

	    Reporte reporte = (Reporte)strategosReportesService.load(Reporte.class, new Long(id));
		
	    if (reporte != null)
	    {
	    	if (bloqueado)
	    	{
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", reporte.getNombre()));
	    	}
	    	else
	    	{
	    		reporte.setReporteId(Long.valueOf(id));
	    		int res = strategosReportesService.deleteReporte(reporte, getUsuarioConectado(request));

	    		if (res == 10004)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", reporte.getNombre()));
	    		else
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", reporte.getNombre()));
	    	}
	    }
	    else 
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosReportesService.unlockObject(request.getSession().getId(), id);

	    strategosReportesService.close();

	    saveMessages(request, messages);

		return result; 
	}
	
	private int Read(Long id, HttpServletRequest request)
	{
		int result = 10000;
		
		ActionMessages messages = getMessages(request);
		
	    StrategosReportesService strategosReportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();

	    Reporte reporte = (Reporte)strategosReportesService.load(Reporte.class, new Long(id));
		
	    if (reporte != null)
	    	request.setAttribute("ajaxResponse", reporte.getNombre());
	    else 
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

	    strategosReportesService.close();

	    saveMessages(request, messages);

		return result; 
	}
}