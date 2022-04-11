package com.visiongc.app.strategos.web.struts.planes.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.forms.EditarPlanForm;
import com.visiongc.app.strategos.web.struts.util.ObjetosCopia;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class CopiarPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarPlanForm editarPlanForm = (EditarPlanForm)form;

		ActionMessages messages = getMessages(request);

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		if (!strategosIndicadoresService.checkLicencia(request))
		{
			strategosIndicadoresService.close();
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
			this.saveMessages(request, messages);
			
			return this.getForwardBack(request, 1, false);
		}
		strategosIndicadoresService.close();
		
		String planId = request.getParameter("planId");
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		editarPlanForm.clear();

		if ((planId != null) && (!planId.equals("")) && (!planId.equals("0")))
		{
			Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
			
			if (plan != null)
			{
				editarPlanForm.setPlanId(new Long(0L));
				editarPlanForm.setOrganizacionId(plan.getOrganizacionId());
				editarPlanForm.setPlanImpactaId(plan.getPlanImpactaId());
				if (plan.getPlanImpactaId() != null) 
					editarPlanForm.setPlanImpactaNombre(plan.getPlanImpacta().getOrganizacion().getNombre() + " / " + plan.getPlanImpacta().getNombre());
				editarPlanForm.setAnoInicial(plan.getAnoInicial());
				editarPlanForm.setAnoFinal(plan.getAnoFinal());
				editarPlanForm.setTipo(plan.getTipo());
				editarPlanForm.setActivo(new Boolean(false));
				editarPlanForm.setRevision(new Byte((byte) 0));
				editarPlanForm.setMetodologiaId(plan.getMetodologiaId());
				editarPlanForm.setMetodologiaNombre(plan.getMetodologia().getNombre());
				editarPlanForm.setNombre("Copia " + plan.getNombre());
				editarPlanForm.setOriginalPlanId(plan.getPlanId());
				editarPlanForm.setCopiar(true);
				editarPlanForm.setCrearClaseAutomaticamente(true);
				editarPlanForm.setOrganizacionDestinoId(plan.getOrganizacionId());
				editarPlanForm.setOrganizacionDestinoNombre(plan.getOrganizacion().getNombre());
			}
			else
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
			forward = "noencontrado";

		strategosPlanesService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
		
		return mapping.findForward(forward);
	}
	
	public int Copiar(Long organizacionOrigenId, Long organizacionDestinoId, HttpServletRequest request)
	{
		int respuesta = 10000;
		
	    StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

	    Map<String, String> filtros = new HashMap<String, String>();

	    filtros.put("organizacionId", organizacionOrigenId.toString());
	    PaginaLista planes = strategosPlanesService.getPlanes(1, 999, "nombre", "ASC", true, filtros);
	    
        for (Iterator<?> iter = planes.getLista().iterator(); iter.hasNext(); ) 
        {
        	Plan planOrigen = (Plan)iter.next();
        	Plan planDestino  = new Plan();
        	
        	planDestino.setPlanId(new Long(0L));
        	planDestino.setOrganizacionId(organizacionDestinoId);
        	planDestino.setPlanImpactaId(planOrigen.getPlanImpactaId());
			planDestino.setAnoInicial(planOrigen.getAnoInicial());
			planDestino.setAnoFinal(planOrigen.getAnoFinal());
			planDestino.setTipo(planOrigen.getTipo());
			planDestino.setActivo(new Boolean(false));
			planDestino.setRevision(new Byte((byte) 0));
			planDestino.setMetodologiaId(planOrigen.getMetodologiaId());
			planDestino.setNombre(planOrigen.getNombre());
			planDestino.setMetodologiaId(planOrigen.getMetodologiaId());
			
			respuesta = CopiarClase(planOrigen, planDestino, request);
			if (respuesta == 10000)
			{
				respuesta = strategosPlanesService.savePlan(planDestino, getUsuarioConectado(request));
				respuesta = new com.visiongc.app.strategos.web.struts.planes.perspectivas.actions.CopiarPerspectivaAction().CopiarPerspectiva(planOrigen, planDestino, request);
			}
			if (respuesta != 10000)
				break;
        }

	    strategosPlanesService.close();
        
		return respuesta;
	}
	
	public int CopiarClase(Plan planOrigen, Plan planDestino, HttpServletRequest request)
	{
		int respuesta = 10000;
		
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
	    ClaseIndicadores claseOrigen = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, planOrigen.getClaseId());
	    ClaseIndicadores claseDestino = new ClaseIndicadores();
	    
	    Map<String, Object> filtros = new HashMap<String, Object>();

	    filtros.put("organizacionId", planDestino.getOrganizacionId().toString());
	    filtros.put("nombre", claseOrigen.getNombre());
	    // Se quita la siguiente linea de codigo, porque las clases de los planes ya pudieran estar copiadas por la funcion de copiar indicadores
	    // Ya que la clase de los planes, puede ser tipo 0, porque puede ser una clase de los indicadores, seleccionada
	    //filtros.put("padreId", null);
	    List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
	    if (clases.size() > 0)
	    {
	        for (Iterator<?> iter = clases.iterator(); iter.hasNext(); ) 
	        {
	        	claseDestino = (ClaseIndicadores)iter.next();
	        	planDestino.setClaseId(claseDestino.getClaseId());
	        	respuesta = 10000;
	        	break;
	        }
	    }
	    else
	    {
			claseDestino = new ClaseIndicadores();
			ClaseIndicadores clasePadre = strategosClasesIndicadoresService.getClaseRaiz(planDestino.getOrganizacionId(), TipoClaseIndicadores.getTipoClaseIndicadores(), getUsuarioConectado(request));
			if (clasePadre != null)
				claseDestino.setPadreId(clasePadre.getClaseId());
			else
				respuesta = VgcReturnCode.DB_FK_VIOLATED;
	
			claseDestino.setClaseId(new Long(0L));
			claseDestino.setNombre(claseOrigen.getNombre());
			claseDestino.setOrganizacionId(planDestino.getOrganizacionId());
			claseDestino.setDescripcion(claseOrigen.getDescripcion());
			claseDestino.setEnlaceParcial(claseOrigen.getEnlaceParcial());
			claseDestino.setVisible(claseOrigen.getVisible());
			claseDestino.setTipo(claseOrigen.getTipo());
			
			if (respuesta == VgcReturnCode.DB_OK)
				respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseDestino, (Usuario)request.getSession().getAttribute("usuario"));
			if (respuesta == VgcReturnCode.DB_OK)
				planDestino.setClaseId(claseDestino.getClaseId());
	    }
	    
		if (respuesta == VgcReturnCode.DB_OK && planOrigen.getClaseIdIndicadoresTotales() != null)
		{
		    claseOrigen = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, planOrigen.getClaseIdIndicadoresTotales());

		    filtros = new HashMap<String, Object>();

		    filtros.put("organizacionId", planDestino.getOrganizacionId().toString());
		    filtros.put("nombre", claseOrigen.getNombre());
		    filtros.put("padreId", planDestino.getClaseId().toString());
		    clases = strategosClasesIndicadoresService.getClases(filtros);

		    if (clases.size() > 0)
		    {
		        for (Iterator<?> iter = clases.iterator(); iter.hasNext(); ) 
		        {
		        	claseDestino = (ClaseIndicadores)iter.next();
		        	planDestino.setClaseIdIndicadoresTotales(claseDestino.getClaseId());
		        	respuesta = VgcReturnCode.DB_OK;
		        	break;
		        }
		    }
		    else
		    {
				claseDestino = new ClaseIndicadores();
				claseDestino.setPadreId(planDestino.getClaseId());
		
				claseDestino.setClaseId(new Long(0L));
				claseDestino.setNombre(claseOrigen.getNombre());
				claseDestino.setOrganizacionId(planDestino.getOrganizacionId());
				claseDestino.setDescripcion(claseOrigen.getDescripcion());
				claseDestino.setEnlaceParcial(claseOrigen.getEnlaceParcial());
				claseDestino.setVisible(claseOrigen.getVisible());
				claseDestino.setTipo(claseOrigen.getTipo());
				
				respuesta = strategosClasesIndicadoresService.saveClaseIndicadores(claseDestino, (Usuario)request.getSession().getAttribute("usuario"));
				if (respuesta == VgcReturnCode.DB_OK)
					planDestino.setClaseIdIndicadoresTotales(claseDestino.getClaseId());
		    }
		}

		strategosClasesIndicadoresService.close();
		
		if (respuesta == VgcReturnCode.DB_OK)
		{
		    List<ObjetosCopia> clasesCopiadas = new ArrayList<ObjetosCopia>();
		    clasesCopiadas.add(new ObjetosCopia(planOrigen.getClaseId(), claseDestino.getClaseId(), claseDestino.getOrganizacionId()));

		    respuesta = new com.visiongc.app.strategos.web.struts.indicadores.actions.CopiarIndicadorAction().CopiarIndicador(planOrigen, planDestino, clasesCopiadas, request);
		}
		
		return respuesta;
	}
}