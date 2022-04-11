/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

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
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EliminarVistaDatosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String reporteId = request.getParameter("reporteId");

		StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();

		if (reporteId != null)
		{
			reportesService.unlockObject(request.getSession().getId(), reporteId);
	
			boolean bloqueado = !reportesService.lockForDelete(request.getSession().getId(), reporteId);
	
			Reporte reporte = (Reporte)reportesService.load(Reporte.class, new Long(reporteId));
	
			if (reporte != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", reporte.getNombre()));
				else
				{
					reporte.setReporteId(Long.valueOf(reporteId));
					int res = reportesService.deleteReporte(reporte, getUsuarioConectado(request));
					
					if (res == 10004)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", reporte.getNombre()));
					else
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", reporte.getNombre()));
				}
			}
			else 
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
	
			reportesService.unlockObject(request.getSession().getId(), reporteId);
		}
		reportesService.close();

		saveMessages(request, messages);

		return getForwardBack(request, 1, true);
	}
}