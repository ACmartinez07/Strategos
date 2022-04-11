package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
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

public class EliminarClaseIndicadoresAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String claseId = request.getParameter("claseId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarClaseIndicadoresAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((claseId == null) || (claseId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(claseId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("check")) 
	    	{
	    		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
	    		Boolean esInsumo = false;
	    		esInsumo = CheckInsumoHijas(new Long(claseId), organizacionId, strategosClasesIndicadoresService, strategosIndicadoresService);
				if (esInsumo)
				{
		    		request.setAttribute("ajaxResponse", "true|" + claseId);
		    		return mapping.findForward("ajaxResponse");
				}
	    		
	    		Map<String, Object> filtros = new HashMap<String, Object>();

	    		filtros.put("organizacionId", organizacionId.toString());
	    		filtros.put("claseId", claseId);
	    		
	    		PaginaLista paginaIndicadores = strategosIndicadoresService.getIndicadores(1, 29, "nombre", "ASC", true, filtros, null, null, false);
	    		for (Iterator<?> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
				{
	    			Indicador indicador = (Indicador)i.next();
					esInsumo = strategosIndicadoresService.esInsumo(indicador.getIndicadorId()); 
					if (esInsumo)
					{
			    		request.setAttribute("ajaxResponse", "true|" + claseId);
			    		return mapping.findForward("ajaxResponse");
					}
				}
				if (!esInsumo)
				{
		    		request.setAttribute("ajaxResponse", "false|" + claseId);
		    		return mapping.findForward("ajaxResponse");
				}
				strategosIndicadoresService.close();
	    	}
	    }
		
		strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), claseId);

		bloqueado = !strategosClasesIndicadoresService.lockForDelete(request.getSession().getId(), claseId);

		ClaseIndicadores claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(claseId));
		if (claseIndicadores != null)
		{
			if (claseIndicadores.getPadreId() != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", claseIndicadores.getNombre()));
				else
				{
					int respuesta = 10000;
					Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
					strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), claseId);
					if (claseIndicadores.getHijos() != null && claseIndicadores.getHijos().size() >= 0)
						respuesta = EliminarHijas(Long.parseLong(claseId), usuario); 
					if (respuesta == 10000)
						respuesta = strategosClasesIndicadoresService.deleteClaseIndicadores(claseIndicadores, true, usuario);
					if (respuesta == 10004)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", claseIndicadores.getNombre()));
					else
					{
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", claseIndicadores.getNombre()));
						
						ClaseIndicadores padre = claseIndicadores.getPadre();
						request.setAttribute("GestionarClasesIndicadoresAction.reloadId", padre.getClaseId().toString());
					}
				}
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", claseIndicadores.getNombre()));
		}
		else 
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosClasesIndicadoresService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarClaseIndicadoresAction.ultimoTs", claseId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
	
	private int EliminarHijas(Long claseId, Usuario usuario)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		
		List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClasesHijas(claseId, null);
		
		for (Iterator<ClaseIndicadores> iter = clases.iterator(); iter.hasNext(); ) 
		{
			ClaseIndicadores claseHijo = (ClaseIndicadores)iter.next();
			if (claseHijo.getHijos() != null && claseHijo.getHijos().size() >= 0)
				respuesta = EliminarHijas(claseHijo.getClaseId(), usuario);
			if (respuesta == VgcReturnCode.DB_OK)
				respuesta = strategosClasesIndicadoresService.deleteClaseIndicadores(claseHijo, true, usuario);
			if (respuesta != VgcReturnCode.DB_OK)
				break;
		}
		
		strategosClasesIndicadoresService.close();
		
		return respuesta;
	}
	
	private Boolean CheckInsumoHijas(Long claseId, Long organizacionId, StrategosClasesIndicadoresService strategosClasesIndicadoresService, StrategosIndicadoresService strategosIndicadoresService)
	{
		Boolean esInsumo = false;

		List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClasesHijas(claseId, null);

		for (Iterator<ClaseIndicadores> iter = clases.iterator(); iter.hasNext(); ) 
		{
			ClaseIndicadores claseHijo = (ClaseIndicadores)iter.next();
			esInsumo = CheckInsumoHijas(claseHijo.getClaseId(), organizacionId, strategosClasesIndicadoresService, strategosIndicadoresService);
			if (esInsumo)
				break;
			
    		Map<String, Object> filtros = new HashMap<String, Object>();
    		filtros.put("organizacionId", organizacionId.toString());
    		filtros.put("claseId", claseHijo.getClaseId().toString());
    		
    		PaginaLista paginaIndicadores = strategosIndicadoresService.getIndicadores(1, 29, "nombre", "ASC", true, filtros, null, null, false);
    		for (Iterator<?> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
			{
    			Indicador indicador = (Indicador)i.next();
				esInsumo = strategosIndicadoresService.esInsumo(indicador.getIndicadorId()); 
				if (esInsumo)
		    		break;
			}
			if (esInsumo)
	    		break;
		}
		
		return esInsumo;
	}
}