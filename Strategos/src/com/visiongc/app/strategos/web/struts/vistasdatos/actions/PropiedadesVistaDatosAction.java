/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;

/**
 * @author Kerwin
 *
 */
public class PropiedadesVistaDatosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    ConfigurarVistaDatosForm configurarVistaDatosForm = (ConfigurarVistaDatosForm)form;
	    configurarVistaDatosForm.clear();
	    
	    Long reporteId = (request.getParameter("reporteId") != null ? new Long(request.getParameter("reporteId")) : null);
	    StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
	    
		if (reporteId != null)
		{
			Reporte reporte = (Reporte) reportesService.load(Reporte.class, reporteId);
			if (reporte!= null)
			{
				configurarVistaDatosForm.setNombre(reporte.getNombre());
				configurarVistaDatosForm.setDescripcion(reporte.getDescripcion());
				configurarVistaDatosForm.setPublico(reporte.getPublico());
				
				UsuariosService strategosUsuarioService = FrameworkServiceFactory.getInstance().openUsuariosService();
				Usuario usuario = null;
				if (reporte.getUsuarioId() != null) 
				{
					usuario = (Usuario)strategosUsuarioService.load(Usuario.class, reporte.getUsuarioId());
					if (usuario != null) 
						configurarVistaDatosForm.setCreadoUsuarioNombre(usuario.getFullName());
				}
				strategosUsuarioService.close();
			}
		}

		reportesService.close();

	    return mapping.findForward(forward);
	}
}