/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.web.struts.reportes.forms.SeleccionarReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ReadListaReporteAction extends VgcAction
{
	  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	  {
	  }

	  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	  {
		  super.execute(mapping, form, request, response);

		  String forward = mapping.getParameter();

		  SeleccionarReporteForm seleccionarReporteForm = (SeleccionarReporteForm)form;

		  if (seleccionarReporteForm.getAtributoOrden() == null) 
			  seleccionarReporteForm.setAtributoOrden("nombre");
		    
		  if (seleccionarReporteForm.getTipoOrden() == null) 
			  seleccionarReporteForm.setTipoOrden("ASC");

		  Map<String, Object> filtros = new HashMap<String, Object>();
		  filtros.put("organizacionId", (new Long((String)request.getSession().getAttribute("organizacionId"))).toString());
		  
		  StrategosReportesService reportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
		  Usuario usuario = getUsuarioConectado(request);
		  PaginaLista paginaReportes = reportesService.getReportes(0, 0, seleccionarReporteForm.getAtributoOrden(), seleccionarReporteForm.getTipoOrden(), true, filtros, usuario.getUsuarioId());
		  reportesService.close();
		  
		  request.setAttribute("paginaReportes", paginaReportes);

		  if (paginaReportes.getLista() != null && paginaReportes.getLista().size() > 0) 
		  {
		      Reporte reporte = (Reporte)paginaReportes.getLista().get(0);
		      seleccionarReporteForm.setSeleccionados(reporte.getReporteId().toString());
		      seleccionarReporteForm.setValoresSeleccionados(reporte.getNombre());
		  } 
		  else 
			  seleccionarReporteForm.setSeleccionados(null);

		  StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		  OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long((String)request.getSession().getAttribute("organizacionId")));
		  seleccionarReporteForm.setRutaCompletaOrganizacion(organizacionStrategos.getNombre());
		  strategosOrganizacionesService.close();
		  
		  return mapping.findForward(forward);
	}
}