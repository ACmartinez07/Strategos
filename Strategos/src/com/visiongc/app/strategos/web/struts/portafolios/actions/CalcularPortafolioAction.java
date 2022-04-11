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
import com.visiongc.app.strategos.web.struts.portafolios.forms.EditarPortafolioForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class CalcularPortafolioAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);

	    ActionMessages messages = getMessages(request);

	    Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);

	    StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

	    Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(id));
	    if (portafolio != null)
	    {
	    	portafolio.setId(Long.valueOf(id));
	    	
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
				portafolio.setAlto(pagina.getAlto());
				portafolio.setAncho(pagina.getAncho());
				portafolio.setFilas(pagina.getFilas());
				portafolio.setColumnas(pagina.getColumnas());
			}

		    int respuesta = strategosPortafoliosService.calcular(portafolio, getUsuarioConectado(request));
	        if (respuesta == VgcReturnCode.DB_FK_VIOLATED)
	        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.calcularregistro.relacion", portafolio.getNombre()));
	        else
	        	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.calcularregistro.calcularok", portafolio.getNombre()));
	    }
	    else
	    {
	    	EditarPortafolioForm editarPortafolioForm = (EditarPortafolioForm)form;
	    	if (editarPortafolioForm != null)
	    		editarPortafolioForm.clear();
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.calcularregistro.noencontrado"));
	    }

	    strategosPortafoliosService.close();
	    saveMessages(request, messages);

	    return getForwardBack(request, 1, true);
	}
}