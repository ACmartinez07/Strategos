/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;

/**
 * @author Kerwin
 *
 */
public class HistoricoIniciativaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)form;

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		String organizacionId = (String)request.getSession().getAttribute("organizacionId");
		Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();
		Long selectEstatusType = (request.getParameter("selectEstatusType") != null && request.getParameter("selectEstatusType") != "" && !request.getParameter("selectEstatusType").equals("0")) ? Long.parseLong(request.getParameter("selectEstatusType")) : null;
		Long planId = (request.getParameter("planId") != null && request.getParameter("planId") != "") ? Long.parseLong(request.getParameter("planId")) : null;
		if (planId == null && request.getSession().getAttribute("planActivoId") != null)
			planId = Long.parseLong((String) request.getSession().getAttribute("planActivoId"));
		
		String funcion = request.getParameter("funcion");
		if (funcion == null)
		{
			gestionarIniciativasForm.clear();

			String source = request.getParameter("source") != null ? request.getParameter("source") : null;
			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";

			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			if (filtroNombre.equals(""))
				filtro.setNombre(null);
			else
				filtro.setNombre(filtroNombre);
			filtro.setIncluirTodos(false);
			gestionarIniciativasForm.setFiltro(filtro);
			
			if (source != null)
				gestionarIniciativasForm.setSource(source);
			if (planId != null)
				gestionarIniciativasForm.setPlanId(planId);
			gestionarIniciativasForm.setOrganizacionId(new Long(organizacionId));
		}
		else if (funcion.equals("MarcarDesmarcar"))
		{
			planId = gestionarIniciativasForm.getPlanId();
			String seleccionados = request.getParameter("seleccionados");
			if (seleccionados != null && selectHitoricoType.byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				strategosIniciativasService.marcarHistorico(seleccionados);
			else
				strategosIniciativasService.desMarcarHistorico(seleccionados);
			
			ActionMessages messages = this.getMessages(request);
			
			gestionarIniciativasForm.setRespuesta(VgcReturnCode.FORM_SAVE.toString());
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
			saveMessages(request, messages);
		}
		gestionarIniciativasForm.setEstatus(selectEstatusType);
		
		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
		Map<String, String> filtros = new HashMap<String, String>();
		PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc", true, filtros);
		strategosIniciativaEstatusService.close();
		gestionarIniciativasForm.setTiposEstatus(paginaIniciativaEstatus.getLista());
		
		// tipos
	    
	    StrategosTipoProyectoService strategosTiposProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();
	    Map<String, String> filtrosTipo = new HashMap();
	    PaginaLista paginaTipos = strategosTiposProyectoService.getTiposProyecto(0, 0, "tipoProyectoId", "asc", true, filtrosTipo);
	    strategosTiposProyectoService.close();
	    gestionarIniciativasForm.setTipos(paginaTipos.getLista());
		
		filtros = new HashMap<String, String>();
		
		String atributoOrden = gestionarIniciativasForm.getAtributoOrden();
		String tipoOrden = gestionarIniciativasForm.getTipoOrden();
		int paginaIniciativa = gestionarIniciativasForm.getPagina();
		
		if (atributoOrden == null) 
			gestionarIniciativasForm.setAtributoOrden("nombre");
		if (tipoOrden == null) 
			gestionarIniciativasForm.setTipoOrden("ASC");
		if (paginaIniciativa < 1) 
			paginaIniciativa = 1;
		if (organizacionId != null && !organizacionId.equals("") && (gestionarIniciativasForm.getSource() != null && !gestionarIniciativasForm.getSource().equals("Plan"))) 
			filtros.put("organizacionId", organizacionId);
		if (planId != null && planId != 0L) 
			filtros.put("planId", planId.toString());
		if (gestionarIniciativasForm.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
			filtros.put("historicoDate", "IS NULL");
		else
			filtros.put("historicoDate", "IS NOT NULL");
		if (gestionarIniciativasForm.getFiltro().getNombre() != null)
			filtros.put("nombre", gestionarIniciativasForm.getFiltro().getNombre());
		if (gestionarIniciativasForm.getEstatus() != null)
			filtros.put("estatusId", gestionarIniciativasForm.getEstatus().toString());
		if (gestionarIniciativasForm.getEstatus() != null)
		      filtros.put("estatusId", gestionarIniciativasForm.getEstatus().toString());
		if (gestionarIniciativasForm.getTipo() != null)
		        filtros.put("tipoId", gestionarIniciativasForm.getTipo().toString());
		
		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(paginaIniciativa, 30, gestionarIniciativasForm.getAtributoOrden(), gestionarIniciativasForm.getTipoOrden(), true, filtros);
		paginaIniciativas.setTamanoSetPaginas(5);
		request.setAttribute("paginaIniciativas", paginaIniciativas);
		
		strategosIniciativasService.close();

		return mapping.findForward(forward);
	}
}
