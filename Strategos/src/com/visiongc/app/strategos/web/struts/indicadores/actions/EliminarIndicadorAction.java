package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarIndicadorAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		
		Long[] indicadores = new Long[0];
		String indicadorId = null;
		String strIndicadorId = "";
		if (request.getQueryString().indexOf("indicadorId=") > -1)
		{
			strIndicadorId = request.getParameter("indicadorId");
			if (((strIndicadorId != null ? 1 : 0) & (strIndicadorId.equals("") ? 0 : 1)) != 0) 
			{
				String[] ids = strIndicadorId.split(",");
				indicadores = new Long[ids.length];
				for (int i = 0; i < ids.length; i++)
				{
					indicadores[i] = new Long(ids[i]);
					indicadorId = ids[i];
				}
			}
		}
		
		if ((indicadorId == null) || (indicadorId.equals("")))
		{
			indicadorId = request.getParameter("indicadorIniciativaId");
			indicadores = new Long[1];
			indicadores[0] = new Long(indicadorId);
		}

		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarIndicadorAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((indicadorId == null) || (indicadorId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(indicadorId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);
		
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		if (indicadores.length > 0)
		{
		    if (request.getParameter("funcion") != null) 
		    {
		    	String funcion = request.getParameter("funcion");
		    	if (funcion.equals("check")) 
		    	{
		    		boolean esInsumo = false;
					for (int i = indicadores.length - 1; i > -1; i--) 
					{
						esInsumo = strategosIndicadoresService.esInsumo(indicadores[i]); 
						if (esInsumo)
						{
				    		request.setAttribute("ajaxResponse", "true|" + strIndicadorId);
				    		return mapping.findForward("ajaxResponse");
						}
					}
					if (!esInsumo)
					{
			    		request.setAttribute("ajaxResponse", "false|" + strIndicadorId);
			    		return mapping.findForward("ajaxResponse");
					}
		    	}
		    }

			int res = 0;
			for (int i = indicadores.length - 1; i > -1; i--) 
			{
				Long id = indicadores[i];

				bloqueado = !strategosIndicadoresService.lockForDelete(request.getSession().getId(), id);
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(id));
	
				if (indicador != null) 
				{
					if ((indicador.getSoloLectura() != null) && (indicador.getSoloLectura().booleanValue()))
					{
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.sololectura", indicador.getNombre()));
						break;
					}
					else if (bloqueado)
					{
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", indicador.getNombre()));
						break;
					}
					else
					{
						StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
						Iniciativa iniciativa = strategosIniciativasService.getIniciativaByIndicador(indicador.getIndicadorId());
						strategosIniciativasService.close();
						if (iniciativa == null)
						{
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
							PryActividad actividad = strategosPryActividadesService.getActividadByIndicador(indicador.getIndicadorId());
							strategosPryActividadesService.close();
							
							if (actividad == null)
							{
								indicador.setIndicadorId(Long.valueOf(id));
								Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
								res = strategosIndicadoresService.deleteIndicador(indicador, usuario);
								
								strategosIndicadoresService.unlockObject(request.getSession().getId(), id);
			
								if (res == 10004)
								{
									messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", indicador.getNombre()));
									break;
								}
							}
							else
							{
								messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.editarindicador.mensaje.asociado", indicador.getNombre()));
								break;
							}
						}
						else
						{
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.editarindicador.mensaje.asociado", indicador.getNombre()));
							break;
						}
					}
					
				}
				else
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
			}			
			if (res == 10000)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionmultipleok"));
		}

		strategosIndicadoresService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarIndicadorAction.ultimoTs", indicadorId + "&" + ts);

		if (request.getSession().getAttribute("GuardarIndicador") == null)
			request.getSession().setAttribute("GuardarIndicador", "true");
		
		return getForwardBack(request, 1, true);
	}
}