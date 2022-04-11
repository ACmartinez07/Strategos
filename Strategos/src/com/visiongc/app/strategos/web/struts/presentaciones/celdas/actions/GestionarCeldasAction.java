package com.visiongc.app.strategos.web.struts.presentaciones.celdas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.web.struts.presentaciones.celdas.forms.GestionarCeldasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarCeldasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarCeldasForm gestionarCeldasForm = (GestionarCeldasForm)form;

		String atributoOrden = gestionarCeldasForm.getAtributoOrden();
		String tipoOrden = gestionarCeldasForm.getTipoOrden();
		int pagina = gestionarCeldasForm.getPagina();
		String seleccionados = gestionarCeldasForm.getSeleccionados();

		StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();

		Vista vista = (Vista)request.getSession().getAttribute("vista");

		Pagina paginaSeleccionada = (Pagina)request.getSession().getAttribute("pagina");
		String paginaId = paginaSeleccionada.getPaginaId().toString();

		if (paginaSeleccionada.getPaginaId() != null) 
		{
			gestionarCeldasForm.setNumeroPagina(paginaSeleccionada.getNumero());
			gestionarCeldasForm.setFilasPagina(paginaSeleccionada.getFilas());
			gestionarCeldasForm.setColumnasPagina(paginaSeleccionada.getColumnas());
			gestionarCeldasForm.setNombreVista(vista.getNombre());
			gestionarCeldasForm.setPaginaId(paginaSeleccionada.getPaginaId());
		}

		Map filtros = new HashMap();

		if (atributoOrden == null) 
		{
			atributoOrden = "celdaId";
			gestionarCeldasForm.setAtributoOrden(atributoOrden);
		}

		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarCeldasForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		if ((paginaId == null) && (paginaId.equals(""))) 
			paginaId = "0";

		filtros.put("paginaId", paginaId);

		PaginaLista paginaCeldas = strategosCeldasService.getCeldas(pagina, 30, atributoOrden, tipoOrden, true, filtros, null);

		Celda celdaSeleccionada = null;
		int indiceCelda = 0;
		int totalCeldas = 0;
		boolean interrumpirCiclo = false;
		if ((paginaCeldas != null) && (paginaCeldas.getLista() != null) && (paginaCeldas.getLista().size() > 0)) 
		{
			totalCeldas = paginaCeldas.getLista().size();
			interrumpirCiclo = totalCeldas == 0;
		} 
		else 
		{
			seleccionados = null;
			gestionarCeldasForm.setSeleccionados(seleccionados);
		}

		while (!interrumpirCiclo) 
		{
			if ((seleccionados == null) || (seleccionados.equals(""))) 
			{
				Long celdaId = null;
				if ((paginaCeldas != null) && (paginaCeldas.getLista() != null) && (paginaCeldas.getLista().size() > indiceCelda))
				{
					celdaId = ((Celda)paginaCeldas.getLista().get(indiceCelda)).getCeldaId();
					
					if (celdaId != null) 
					{
						seleccionados = celdaId.toString();
						gestionarCeldasForm.setSeleccionados(seleccionados);
						indiceCelda++;
					} 
					else 
						interrumpirCiclo = true;
				}
			}

			if ((seleccionados != null) && (!seleccionados.equals(""))) 
			{
				celdaSeleccionada = (Celda)strategosCeldasService.load(Celda.class, new Long(gestionarCeldasForm.getSeleccionados()));

				if ((celdaSeleccionada != null) && ((paginaId == null) || (celdaSeleccionada.getPagina().getPaginaId().longValue() != new Long(paginaId).longValue()))) 
					celdaSeleccionada = null;

				if (celdaSeleccionada == null) 
					seleccionados = null;
					gestionarCeldasForm.setSeleccionados(seleccionados);
			}

			if (!interrumpirCiclo) 
				interrumpirCiclo = seleccionados != null;

			if (!interrumpirCiclo) 
				interrumpirCiclo = indiceCelda >= totalCeldas;
		}

		paginaCeldas.setTamanoSetPaginas(5);

		request.setAttribute("paginaCeldas", paginaCeldas);

		strategosCeldasService.close();

		return mapping.findForward(forward);
	}
}