package com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.indicadores.clasesindicadores.forms.EditarClaseIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarClaseIndicadoresAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarClaseIndicadoresForm editarClaseIndicadoresForm = (EditarClaseIndicadoresForm)form;
		editarClaseIndicadoresForm.clear();
		ActionMessages messages = getMessages(request);

		String claseId = request.getParameter("claseId");

		String funcion = request.getParameter("funcion");
    	if (funcion != null && funcion.equals("Copiar"))
    	{
			// Presentacion
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Clase.Copiar.ShowPresentacion", "ShowPresentacion");
			if (presentacion != null && presentacion.getData() != null)
				editarClaseIndicadoresForm.setShowPresentacion(presentacion.getData().equals("1") ? true : false);
			frameworkService.close();
    	}
		
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("CLASE_VIEWALL");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("CLASE_EDIT");
		boolean bloqueado = false;

		if ((claseId != null) && (!claseId.equals("")) && (!claseId.equals("0")))
		{
			bloqueado = !strategosClasesIndicadoresService.lockForUpdate(request.getSession().getId(), claseId, null);
			editarClaseIndicadoresForm.setBloqueado(new Boolean(bloqueado));
			ClaseIndicadores clase = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(claseId));

			if (clase != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
				
				ClaseIndicadores padre = clase.getPadre();
				long padreId = 0L;
				if (padre != null) 
				{
					padreId = padre.getClaseId().longValue();
					ClaseIndicadores clasePadre = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(padreId));
					if (clasePadre != null)
					{
						editarClaseIndicadoresForm.setPadre(clasePadre.getNombre());
						editarClaseIndicadoresForm.setClaseIdDestino(new Long(padreId));        	  
					}
				}

				editarClaseIndicadoresForm.setPadreId(new Long(padreId));
				editarClaseIndicadoresForm.setClaseId(clase.getClaseId());
				editarClaseIndicadoresForm.setNombre(clase.getNombre());
				editarClaseIndicadoresForm.setDescripcion(clase.getDescripcion());
				editarClaseIndicadoresForm.setEnlaceParcial(clase.getEnlaceParcial());
				editarClaseIndicadoresForm.setVisible(clase.getVisible());
			}
			else 
			{
				strategosClasesIndicadoresService.unlockObject(request.getSession().getId(), new Long(claseId));

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "notfound";
			}
		}
		else
		{
			editarClaseIndicadoresForm.clear();
			ClaseIndicadores padre = (ClaseIndicadores)request.getSession().getAttribute("claseIndicadores");
			editarClaseIndicadoresForm.setPadreId(padre.getClaseId());
		}

		strategosClasesIndicadoresService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarClaseIndicadoresForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
		
		saveMessages(request, messages);

		if (forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);
		return mapping.findForward(forward);
	}
}