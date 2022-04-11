/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.portafolios.forms.EditarPortafolioForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EditarPortafolioAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarPortafolioForm editarPortafolioForm = (EditarPortafolioForm)form;
		if (editarPortafolioForm != null && editarPortafolioForm.getStatus() != null && editarPortafolioForm.getStatus().byteValue() == StatusUtil.getStatusAlertaNotDefinida().byteValue())
			return mapping.findForward(forward);
		
		editarPortafolioForm.clear();
		
		ActionMessages messages = getMessages(request);

		Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : null);
		
		boolean verForm = getPermisologiaUsuario(request).tienePermiso("PORTAFOLIO_VIEW");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("PORTAFOLIO_EDIT");
		
		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

		if (id != null && id != 0)
		{
			Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(id));

			if (portafolio != null)
			{
				editarPortafolioForm.setId(portafolio.getId());
				editarPortafolioForm.setNombre(portafolio.getNombre());
				editarPortafolioForm.setActivo(portafolio.getActivo());
				editarPortafolioForm.setPorcentajeCompletado(portafolio.getPorcentajeCompletado());
				editarPortafolioForm.setEstatusId(portafolio.getEstatusId());
				editarPortafolioForm.setEstatus(portafolio.getEstatus());
				editarPortafolioForm.setOrganizacion(portafolio.getOrganizacion());
				
				Map<String, String> filtros = new HashMap<String, String>();
				StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();
			    filtros = new HashMap<String, String>();
			    filtros.put("portafolioId", portafolio.getId().toString());
			    PaginaLista paginaPaginas = strategosPaginasService.getPaginas(0, 0, "numero", "ASC", true, filtros);
			    strategosPaginasService.close();
			    List<Pagina> paginas = paginaPaginas.getLista();
				if (paginas.size() > 0)
				{
					Pagina pagina = null;
					pagina = (Pagina)paginas.toArray()[0];
					editarPortafolioForm.setAlto(pagina.getAlto());
					editarPortafolioForm.setAncho(pagina.getAncho());
				}
				strategosPaginasService.close();
			}
			else 
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}

		strategosPortafoliosService.close();

		if (verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarPortafolioForm.setBloqueado(true);
		}
		else if (!verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
    
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}
