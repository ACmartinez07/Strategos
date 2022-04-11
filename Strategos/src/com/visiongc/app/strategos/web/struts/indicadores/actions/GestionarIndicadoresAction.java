package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.web.struts.indicadores.forms.GestionarIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.Modulo.ModuloType;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.transaccion.TransaccionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarIndicadoresAction extends VgcAction
{
	private PaginaLista paginaIndicadores = null;
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIndicadoresForm gestionarIndicadoresForm = (GestionarIndicadoresForm)form;
		Long claseId = new Long((String)request.getSession().getAttribute("claseId"));
		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		Boolean actualizarForma = request.getSession().getAttribute("actualizarForma") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarForma")) : false;
		if (!actualizarForma)
		{
			actualizarForma = request.getSession().getAttribute("GuardarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("GuardarIndicador")) : false;
			if (request.getSession().getAttribute("GuardarIndicador") != null)
				request.getSession().removeAttribute("GuardarIndicador");
		}

		if (claseId != null && claseId != 0L && gestionarIndicadoresForm != null && gestionarIndicadoresForm.getClaseId() != null && claseId.longValue() != gestionarIndicadoresForm.getClaseId().longValue())
			gestionarIndicadoresForm.setPagina(1); 
		gestionarIndicadoresForm.setClaseId(claseId);
		
		String atributoOrden = gestionarIndicadoresForm.getAtributoOrden();
		String tipoOrden = gestionarIndicadoresForm.getTipoOrden();
		int pagina = gestionarIndicadoresForm.getPagina();
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL");
		
		gestionarIndicadoresForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL"));
		gestionarIndicadoresForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarIndicadoresForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarIndicadoresForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("organizacionId", organizacionId.toString());
		filtros.put("claseId", claseId.toString());
		if ((gestionarIndicadoresForm.getFiltroNombre() != null) && (!gestionarIndicadoresForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarIndicadoresForm.getFiltroNombre());
		if (!mostrarTodas) 
			filtros.put("visible", true);
		
		Integer totalPaginas = 29;
		if (paginaIndicadores != null && paginaIndicadores.getFiltros() != null)
		{
			if (!paginaIndicadores.samePage(pagina, totalPaginas, atributoOrden, tipoOrden, filtros))
				paginaIndicadores = null;
		}

		if (paginaIndicadores == null || actualizarForma)
		{
			paginaIndicadores = strategosIndicadoresService.getIndicadores(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros, null, null, true);
			paginaIndicadores.setFiltros(filtros);
			paginaIndicadores.setNroPagina(pagina);
			paginaIndicadores.setTamanoPagina(totalPaginas);
			paginaIndicadores.setOrden(atributoOrden);
			paginaIndicadores.setTipoOrden(tipoOrden);
			if (actualizarForma)
				request.getSession().removeAttribute("actualizarForma");
		}

		paginaIndicadores.setTamanoSetPaginas(5);
		request.setAttribute("paginaIndicadores", paginaIndicadores);
		
		Modulo modulo = new Modulo().getModuloActivo(ModuloType.Indicador.Reporte.ComiteEjecutivo);
		if (modulo != null)
		{
			gestionarIndicadoresForm.setReporteComiteEjecutivo(modulo.getActivo());
			if (gestionarIndicadoresForm.getReporteComiteEjecutivo() != null && gestionarIndicadoresForm.getReporteComiteEjecutivo())
				gestionarIndicadoresForm.setReporte(true);
		}
		strategosIndicadoresService.close();
		
		Modulo cliente = (Modulo) request.getSession().getAttribute("MIF");
		if (cliente != null && cliente.getActivo())
		{
			//Revisar si hay transacciones
			TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
			List<Transaccion> transacciones = transaccionService.getTransacciones(0, 0, "nombre", "ASC", true, null).getLista();
			gestionarIndicadoresForm.setHayTransacciones(transacciones.size() > 0);
			gestionarIndicadoresForm.setTransacciones(transacciones);
			transaccionService.close();
		}
		
		return mapping.findForward(forward);
	}
}