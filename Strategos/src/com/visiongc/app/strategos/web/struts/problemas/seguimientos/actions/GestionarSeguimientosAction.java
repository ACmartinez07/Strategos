package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.GestionarSeguimientosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarSeguimientosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarSeguimientosForm gestionarSeguimientosForm = (GestionarSeguimientosForm)form;

		String atributoOrden = gestionarSeguimientosForm.getAtributoOrden();
		String tipoOrden = gestionarSeguimientosForm.getTipoOrden();
		int pagina = gestionarSeguimientosForm.getPagina();
		Accion accionCorrectiva = (Accion)request.getSession().getAttribute("accionCorrectiva");

		if (atributoOrden == null) 
		{
			atributoOrden = "numeroReporte";
			gestionarSeguimientosForm.setAtributoOrden(atributoOrden);
		}
    
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarSeguimientosForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

		accionCorrectiva = (Accion)strategosSeguimientosService.load(Accion.class, accionCorrectiva.getAccionId());

		if (accionCorrectiva != null)
		{
			setResponsablesSeguimiento(accionCorrectiva, gestionarSeguimientosForm, strategosSeguimientosService, getUsuarioConectado(request));

			setSeguimientoCerrado(accionCorrectiva, gestionarSeguimientosForm, strategosSeguimientosService);

			setEsDiaSeguimiento(accionCorrectiva, gestionarSeguimientosForm, strategosSeguimientosService);

			setExisteSeguimiento(accionCorrectiva, gestionarSeguimientosForm, strategosSeguimientosService);

			Map<String, String> filtros = new HashMap<String, String>();

			filtros.put("accionId", accionCorrectiva.getAccionId().toString());

			PaginaLista paginaSeguimientos = strategosSeguimientosService.getSeguimientos(pagina, 30, atributoOrden, tipoOrden, true, filtros);
			
			paginaSeguimientos.setTamanoSetPaginas(5);

			request.setAttribute("paginaSeguimientos", paginaSeguimientos);
		}

		strategosSeguimientosService.close();

		return mapping.findForward(forward);
	}

	private void setResponsablesSeguimiento(Accion accionCorrectiva, GestionarSeguimientosForm gestionarSeguimientosForm, StrategosSeguimientosService servicio, Usuario usuario)
	{
		for (Iterator<?> i = accionCorrectiva.getResponsablesAccion().iterator(); i.hasNext(); ) 
		{
			ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
			if ((responsableAccion.getResponsable().getUsuarioId() == null) || (responsableAccion.getResponsable().getUsuarioId().toString().equals("0")) || 
					(!usuario.getUsuarioId().toString().equals(responsableAccion.getResponsable().getUsuarioId().toString()))) continue;
			gestionarSeguimientosForm.setEsResponsableAccionCorrectiva(new Boolean(true));
		}

		if (accionCorrectiva.getProblema() != null)
		{
			if ((accionCorrectiva.getProblema().getResponsable() != null) && accionCorrectiva.getProblema().getResponsable().getUsuarioId() != null && (usuario.getUsuarioId().toString().equals(accionCorrectiva.getProblema().getResponsable().getUsuarioId().toString()))) 
				gestionarSeguimientosForm.setEsResponsableProblema(new Boolean(true));

			if ((accionCorrectiva.getProblema().getAuxiliar() != null) && accionCorrectiva.getProblema().getAuxiliar().getUsuarioId() != null && (usuario.getUsuarioId().toString().equals(accionCorrectiva.getProblema().getAuxiliar().getUsuarioId().toString())))
				gestionarSeguimientosForm.setEsResponsableProblema(new Boolean(true));
		}
	}

	private void setSeguimientoCerrado(Accion accionCorrectiva, GestionarSeguimientosForm gestionarSeguimientosForm, StrategosSeguimientosService servicio)
	{
		gestionarSeguimientosForm.setSeguimientoCerrado(servicio.seguimientoCerrado(accionCorrectiva.getAccionId()));
	}

	private void setEsDiaSeguimiento(Accion accionCorrectiva, GestionarSeguimientosForm gestionarSeguimientosForm, StrategosSeguimientosService servicio)
	{
		gestionarSeguimientosForm.setEsDiaSeguimiento(servicio.esDiaSeguimiento(accionCorrectiva.getAccionId()));
	}

	private void setExisteSeguimiento(Accion accionCorrectiva, GestionarSeguimientosForm gestionarSeguimientosForm, StrategosSeguimientosService servicio)
	{
		gestionarSeguimientosForm.setExisteSeguimiento(servicio.existeSeguimiento(accionCorrectiva.getAccionId()));
	}
}