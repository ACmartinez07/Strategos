/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;

/**
 * @author Andres
 *
 */
public class ReporteIniciativaEjecucionAction extends VgcAction
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
	  
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Usuario user = getUsuarioConectado(request);
		
		boolean isAdmin=false;
		if(user.getIsAdmin()){
			
			isAdmin=true;
		}
		
		request.getSession().setAttribute("isAdmin", isAdmin); 
		
		/* Parametros para el reporte */
		
		Calendar fecha = Calendar.getInstance();
        int ano = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int anoIn = ano -20;
        int anoFin = ano +20;
        
        
	    /*Asigna a la Forma que genera reportes, el nombre de la organizacion y plan seleccionados*/
	    reporteForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		reporteForm.setAnoFinal(""+anoFin);
		reporteForm.setMesFinal(""+mes);
		reporteForm.setAno(ano);
		reporteForm.setAnoInicial(""+anoIn);
		
		Map<String, String> filtros = new HashMap<String, String>();
		
		StrategosTipoProyectoService strategosTiposProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();
		Map<String, String> filtrosTipo = new HashMap();
		PaginaLista paginaTipos = strategosTiposProyectoService.getTiposProyecto(0, 0, "tipoProyectoId", "asc", true, filtros);
		strategosTiposProyectoService.close();
		
		List<TipoProyecto> tipos = new ArrayList<TipoProyecto>();
		
		
		for (Iterator<TipoProyecto> iter = paginaTipos.getLista().iterator(); iter.hasNext(); ) 
		{
			TipoProyecto tipoProyecto = (TipoProyecto)iter.next();
			tipos.add(tipoProyecto);
		}
		
		reporteForm.setTipos(tipos);

		return mapping.findForward(forward);
	}
}
