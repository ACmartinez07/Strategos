/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

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
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;


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
public class CalcularIndicadoresAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarInstrumentosForm editarInstrumentoForm = (EditarInstrumentosForm)form;
		editarInstrumentoForm.clear();
	  
		
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Usuario user = getUsuarioConectado(request);
		
		Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);
		
		boolean isAdmin=false;
		if(user.getIsAdmin()){
			
			isAdmin=true;
		}
		
		request.getSession().setAttribute("isAdmin", isAdmin); 
		
		/* Parametros para el reporte */
		
		Calendar ahora = Calendar.getInstance();
		editarInstrumentoForm.setAno(ahora.get(1));
		editarInstrumentoForm.setMesInicial(1);
		editarInstrumentoForm.setMesFinal(4);
		editarInstrumentoForm.setAlcance((byte) 1);
		editarInstrumentoForm.setInstrumentoId(id);
		
		Map<String, String> filtros = new HashMap<String, String>();
		
		return mapping.findForward(forward);
	}
}
