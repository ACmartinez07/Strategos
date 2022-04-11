/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.estatus.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.iniciativas.estatus.forms.EditarIniciativaEstatusForm;

import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.WebUtil;

/**
 * @author Kerwin
 *
 */
public class GuardarIniciativaEstatusAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIniciativaEstatusForm editarIniciativaEstatusForm = (EditarIniciativaEstatusForm)form;

		Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);
		Double porcentajeInicial = (request.getParameter("porcentajeInicial") != null && request.getParameter("porcentajeInicial") != "" ? Double.parseDouble(request.getParameter("porcentajeInicial")) : null);
		Double porcentajeFinal = (request.getParameter("porcentajeFinal") != null && request.getParameter("porcentajeFinal") != "" ? Double.parseDouble(request.getParameter("porcentajeFinal")) : null);
		
		editarIniciativaEstatusForm.setId(id);
		editarIniciativaEstatusForm.setPorcentajeInicial(porcentajeInicial);
		editarIniciativaEstatusForm.setPorcentajeFinal(porcentajeFinal);
		
		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
		boolean porcentajesIncorrectos = false;
		
		Map<String, String> filtros = new HashMap<String, String>();
		PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc", true, filtros);
		
		for (Iterator<IniciativaEstatus> iter = paginaIniciativaEstatus.getLista().iterator(); iter.hasNext(); ) 
		{
			IniciativaEstatus iniciativaEstatus = (IniciativaEstatus)iter.next();
			if (iniciativaEstatus.getId().longValue() != editarIniciativaEstatusForm.getId().longValue())
			{
				if (editarIniciativaEstatusForm.getPorcentajeInicial() != null && editarIniciativaEstatusForm.getPorcentajeFinal() != null && iniciativaEstatus.getPorcentajeInicial() != null && iniciativaEstatus.getPorcentajeFinal() != null)
				{
					if (editarIniciativaEstatusForm.getPorcentajeInicial().doubleValue() == iniciativaEstatus.getPorcentajeInicial().doubleValue() || editarIniciativaEstatusForm.getPorcentajeFinal().doubleValue() == iniciativaEstatus.getPorcentajeFinal().doubleValue())
						porcentajesIncorrectos = true;
					if (editarIniciativaEstatusForm.getPorcentajeInicial().doubleValue() <= iniciativaEstatus.getPorcentajeInicial().doubleValue() && editarIniciativaEstatusForm.getPorcentajeFinal().doubleValue() <= iniciativaEstatus.getPorcentajeFinal().doubleValue())
					{
						if (editarIniciativaEstatusForm.getPorcentajeFinal().doubleValue() >= iniciativaEstatus.getPorcentajeInicial().doubleValue())
							porcentajesIncorrectos = true;
					}
					else if (editarIniciativaEstatusForm.getPorcentajeInicial().doubleValue() >= iniciativaEstatus.getPorcentajeInicial().doubleValue() && editarIniciativaEstatusForm.getPorcentajeFinal().doubleValue() >= iniciativaEstatus.getPorcentajeFinal().doubleValue())
					{
						if (editarIniciativaEstatusForm.getPorcentajeInicial().doubleValue() <= iniciativaEstatus.getPorcentajeFinal().doubleValue())
							porcentajesIncorrectos = true;
					}
					else if (editarIniciativaEstatusForm.getPorcentajeInicial().doubleValue() >= iniciativaEstatus.getPorcentajeInicial().doubleValue() && editarIniciativaEstatusForm.getPorcentajeFinal().doubleValue() <= iniciativaEstatus.getPorcentajeFinal().doubleValue())
						porcentajesIncorrectos = true;
					else if (editarIniciativaEstatusForm.getPorcentajeInicial().doubleValue() <= iniciativaEstatus.getPorcentajeInicial().doubleValue() && editarIniciativaEstatusForm.getPorcentajeFinal().doubleValue() >= iniciativaEstatus.getPorcentajeFinal().doubleValue())
						porcentajesIncorrectos = true;
				}
				if (porcentajesIncorrectos)
					break;
			}
		}
		
		int respuesta = VgcReturnCode.DB_OK;
		boolean nuevo = false;
		if (!porcentajesIncorrectos)
		{
			IniciativaEstatus iniciativaEstatus = null;
			if ((editarIniciativaEstatusForm.getId() != null) && (!editarIniciativaEstatusForm.getId().equals(Long.valueOf("0"))))
			{
				iniciativaEstatus = (IniciativaEstatus)strategosIniciativaEstatusService.load(IniciativaEstatus.class, editarIniciativaEstatusForm.getId());
				iniciativaEstatus.setId(editarIniciativaEstatusForm.getId());
			}
			else
			{
				nuevo = true;
				iniciativaEstatus = new IniciativaEstatus();
				iniciativaEstatus.setId(new Long(0L));
			}

			iniciativaEstatus.setNombre(editarIniciativaEstatusForm.getNombre());
			iniciativaEstatus.setPorcentajeInicial(editarIniciativaEstatusForm.getPorcentajeInicial());
			iniciativaEstatus.setPorcentajeFinal(editarIniciativaEstatusForm.getPorcentajeFinal());
			iniciativaEstatus.setSistema(editarIniciativaEstatusForm.getSistema());
			iniciativaEstatus.setBloquearMedicion(WebUtil.getValorInputCheck(request, "bloquearMedicion"));
			iniciativaEstatus.setBloquearIndicadores(WebUtil.getValorInputCheck(request, "bloquearIndicadores"));

			respuesta = strategosIniciativaEstatusService.save(iniciativaEstatus, getUsuarioConectado(request));
		}
		strategosIniciativaEstatusService.close();

	    if (respuesta == VgcReturnCode.DB_OK)
	    {
			forward = "crearEstatus";
			if (!porcentajesIncorrectos)
			{
		    	editarIniciativaEstatusForm.setStatus(StatusUtil.getStatusSuccess());
		    	if (!nuevo)
		    		editarIniciativaEstatusForm.setStatus(StatusUtil.getStatusSuccessModify());
			}
			else
			{
				editarIniciativaEstatusForm.setStatus(StatusUtil.getStatusAlertaNotDefinida());
				VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
				editarIniciativaEstatusForm.setRespuesta(messageResources.getResource("jsp.editar.estatus.iniciativa.alerta.porcentaje.incorrectos"));
			}
	    }
	    else
	    	editarIniciativaEstatusForm.setStatus(StatusUtil.getStatusInvalido());

		return mapping.findForward(forward);
	}
}
