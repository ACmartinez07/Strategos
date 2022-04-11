/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EliminarReporteGraficoAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String reporteId = request.getParameter("reporteId");

		
		StrategosReportesGraficoService reportesGraficoService = StrategosServiceFactory.getInstance().openStrategosReportesGraficoService();
		

		if (reporteId != null)
		{
			reportesGraficoService.unlockObject(request.getSession().getId(), reporteId);
	
			boolean bloqueado = !reportesGraficoService.lockForDelete(request.getSession().getId(), reporteId);
			
			
			ReporteGrafico reporte= reportesGraficoService.obtenerReporte(new Long(reporteId));
			//ReporteGrafico reporte= (ReporteGrafico)reportesGraficoService.load(ReporteGrafico.class, reporteId);
	
			if (reporte != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", reporte.getNombre()));
				else
				{
					reporte.setReporteId(Long.valueOf(reporteId));
					int res = reportesGraficoService.deleteReporte(reporte, getUsuarioConectado(request));
					
					if (res == 10004)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", reporte.getNombre()));
					else
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", reporte.getNombre()));
				}
			}
			else 
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
	
			reportesGraficoService.unlockObject(request.getSession().getId(), reporteId);
		}
		reportesGraficoService.close();

		saveMessages(request, messages);

		return getForwardBack(request, 1, true);
	}
}