package com.visiongc.app.strategos.web.struts.presentaciones.paginas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.web.struts.presentaciones.paginas.forms.EditarPaginaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarPaginaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarPaginaForm editarPaginaForm = (EditarPaginaForm)form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarPaginaAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosPaginasService strategosPaginasService = StrategosServiceFactory.getInstance().openStrategosPaginasService();

		if (cancelar)
		{
			strategosPaginasService.unlockObject(request.getSession().getId(), editarPaginaForm.getPaginaId());
			
			strategosPaginasService.close();

			return getForwardBack(request, 1, true);
		}

		Pagina pagina = new Pagina();
		boolean nuevo = false;
		int respuesta = 10000;

		if ((editarPaginaForm.getPaginaId() != null) && (!editarPaginaForm.getPaginaId().equals(Long.valueOf("0"))))
		{
			pagina = (Pagina)strategosPaginasService.load(Pagina.class, editarPaginaForm.getPaginaId());
		}
		else
		{
			nuevo = true;
			pagina = new Pagina();
			pagina.setPaginaId(new Long(0L));
			pagina.setVistaId(editarPaginaForm.getVistaId());
		}

		pagina.setFilas(editarPaginaForm.getFilas());
		pagina.setColumnas(editarPaginaForm.getColumnas());
		pagina.setDescripcion(editarPaginaForm.getDescripcion());
		pagina.setAlto(editarPaginaForm.getAlto());
		pagina.setAncho(editarPaginaForm.getAncho());
		pagina.setNumero(editarPaginaForm.getNumero());

		respuesta = strategosPaginasService.savePagina(pagina, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosPaginasService.unlockObject(request.getSession().getId(), editarPaginaForm.getPaginaId());
			forward = "exito";

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearPagina";
			}
			else
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
			}
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosPaginasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarPaginaAction.ultimoTs", ts);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}