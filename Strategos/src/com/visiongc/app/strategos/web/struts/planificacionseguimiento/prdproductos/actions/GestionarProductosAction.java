package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.GestionarProductosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarProductosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		if (url.indexOf("Autonomo") > -1)
			navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarProductosForm gestionarProductosForm = (GestionarProductosForm)form;

		StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

		String atributoOrdenProductos = gestionarProductosForm.getAtributoOrdenProductos();
		String tipoOrdenProductos = gestionarProductosForm.getTipoOrdenProductos();
		int paginaProductos = gestionarProductosForm.getPaginaProductos();

		boolean inicializar = false;

		if (actionEjectuado(request, "/planificacionseguimiento/productos/gestionarProductosAutonomo")) 
		{
			gestionarProductosForm.setIniciativaId(new Long(request.getParameter("iniciativaId")));
			gestionarProductosForm.setLlamada(1);
			inicializar = true;
		} 
		else if (actionEjectuado(request, "/planificacionseguimiento/productos/gestionarProductosIniciativa")) 
		{
			GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)request.getSession().getAttribute("gestionarIniciativasForm");
			String iniciativaSeleccionadaId = gestionarIniciativasForm.getSeleccionadoId();
			gestionarProductosForm.setIniciativaId(new Long(iniciativaSeleccionadaId));
			gestionarProductosForm.setLlamada(2);
			inicializar = true;
		}
		else if (gestionarProductosForm.getLlamada() == 2) 
			return mapping.findForward("gestionarIniciativasAction");

		if (inicializar) 
		{
			Iniciativa iniciativa = (Iniciativa)strategosPrdProductosService.load(Iniciativa.class, gestionarProductosForm.getIniciativaId());

			if (iniciativa != null) 
			{
				gestionarProductosForm.setNombreIniciativa(iniciativa.getNombre());

				gestionarProductosForm.setNombrePlan(null);
			}
		}

		if ((atributoOrdenProductos == null) || (atributoOrdenProductos == "")) 
		{
			atributoOrdenProductos = "nombre";
			gestionarProductosForm.setAtributoOrdenProductos(atributoOrdenProductos);
		}
		
		if (tipoOrdenProductos == null) 
		{
			tipoOrdenProductos = "ASC";
			gestionarProductosForm.setTipoOrdenProductos(tipoOrdenProductos);
		}

		if (paginaProductos < 1) 
			paginaProductos = 1;

		Map<String, String> filtros = new HashMap<String, String>();

		filtros.put("iniciativaId", gestionarProductosForm.getIniciativaId().toString());

		if ((gestionarProductosForm.getFiltroNombre() != null) && (!gestionarProductosForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarProductosForm.getFiltroNombre());

		PaginaLista paginaListaProductos = strategosPrdProductosService.getProductos(paginaProductos, 30, atributoOrdenProductos, tipoOrdenProductos, true, filtros);

		for (Iterator<PrdProducto> iter = paginaListaProductos.getLista().iterator(); iter.hasNext(); ) 
		{
			PrdProducto producto = (PrdProducto)iter.next();
			
			if (producto.getSeguimientosProducto().size() > 0) 
			{
				PrdSeguimientoProducto seguimientoProducto = (PrdSeguimientoProducto)producto.getSeguimientosProducto().iterator().next();

				producto.setAlerta(seguimientoProducto.getAlerta());
				if (FechaUtil.compareFechasAnoMesDia(producto.getFechaInicio(), seguimientoProducto.getFechaFin()) != 0) 
					producto.setFechaFin(seguimientoProducto.getFechaFin());
			}			
		}

		paginaListaProductos.setTamanoSetPaginas(5);

		request.setAttribute("paginaProductos", paginaListaProductos);

		strategosPrdProductosService.close();

		if (paginaListaProductos.getLista().size() > 0) 
		{
			PrdProducto producto = (PrdProducto)paginaListaProductos.getLista().get(0);
			gestionarProductosForm.setSeleccionados(producto.getProductoId().toString());
			gestionarProductosForm.setValoresSeleccionados(producto.getNombre());
		} 
		else 
			gestionarProductosForm.setSeleccionados(null);

		if (actionEjectuado(request, "/planificacionseguimiento/productos/gestionarProductosIniciativa")) 
		{
			GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)request.getSession().getAttribute("gestionarIniciativasForm");
			forward = "gestionarIndicadoresIniciativaAction";
		}

		return mapping.findForward(forward);
	}
}