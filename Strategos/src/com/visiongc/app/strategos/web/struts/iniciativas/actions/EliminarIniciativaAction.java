package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarInstrumentosForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EliminarIniciativaAction extends VgcAction
{
	private static final String ACTION_KEY = "EliminarIniciativaAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		
		Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null && request.getParameter("desdeInstrumento") != "") ? Boolean.valueOf(request.getParameter("desdeInstrumento")) : null;
		
		String iniciativaId = request.getParameter("iniciativaId");
		Long instrumentoId = (request.getParameter("instrumentoId") != null && request.getParameter("instrumentoId") != "") ? Long.parseLong(request.getParameter("instrumentoId")) : null;
		
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("EliminarIniciativaAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;
		
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((iniciativaId == null) || (iniciativaId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(iniciativaId + "&" + ts))) 
			cancelar = true;

		if (cancelar) {
			if(desdeInstrumento) {
				return getForwardBack(request, 2, true);
			}else {
				return getForwardBack(request, 1, true);
			}
		}
			
			

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		strategosIniciativasService.unlockObject(request.getSession().getId(), iniciativaId);

		bloqueado = !strategosIniciativasService.lockForDelete(request.getSession().getId(), iniciativaId);
		
		if(desdeInstrumento != null && desdeInstrumento) {
			
			if(instrumentoId != null) {
				
				StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();

			    int respuesta = strategosInstrumentosService.desasociarInstrumento(instrumentoId, new Long(iniciativaId), getUsuarioConectado(request));

			    if (respuesta == VgcReturnCode.DB_OK)
			    {
			    	GestionarInstrumentosForm gestionarInstrumentosForm = (GestionarInstrumentosForm)request.getSession().getAttribute("gestionarInstrumentosForm");
			    	if (gestionarInstrumentosForm != null)
			    		gestionarInstrumentosForm.setIniciativaId(null);
			    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.desasociariniciativa.portafolio.ok"));
			    }
			    else
			    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion"));

			    strategosInstrumentosService.close();
				
			}
			
			
		}
		

		Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(iniciativaId));

		if (iniciativa != null)
		{
			if ((iniciativa.getSoloLectura() != null) && (iniciativa.getSoloLectura().booleanValue()))
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.sololectura", iniciativa.getNombre()));
			else if (bloqueado)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", iniciativa.getNombre()));
			else
			{
				iniciativa.setIniciativaId(Long.valueOf(iniciativaId));
				int res = strategosIniciativasService.deleteIniciativa(iniciativa, getUsuarioConectado(request));
				
				if (res == 10004)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", iniciativa.getNombre()));
				else
				{
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", iniciativa.getNombre()));
					GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)request.getSession().getAttribute("gestionarIniciativasForm");
					if (request.getSession().getAttribute("actualizarForma") == null)
						request.getSession().setAttribute("actualizarForma", "true");
				}
			}
		}
		else 
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

		strategosIniciativasService.unlockObject(request.getSession().getId(), iniciativaId);

		strategosIniciativasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("EliminarIniciativaAction.ultimoTs", iniciativaId + "&" + ts);
		
		if(desdeInstrumento != null && desdeInstrumento) {
			return getForwardBack(request, 2, true);
		}else {
			return getForwardBack(request, 1, true);
		}
		
	}
}