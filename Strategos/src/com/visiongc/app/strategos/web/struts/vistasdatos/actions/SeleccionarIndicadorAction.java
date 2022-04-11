package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.SeleccionarIndicadorForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarIndicadorAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		SeleccionarIndicadorForm seleccionarIndicadorForm = (SeleccionarIndicadorForm)form;
		
		if (seleccionarIndicadorForm.getPrimeraVez() == null) 
			seleccionarIndicadorForm.clear();

		String atributoOrden = seleccionarIndicadorForm.getAtributoOrden();
		String tipoOrden = seleccionarIndicadorForm.getTipoOrden();
		
		seleccionarIndicadorForm.setFuncionCierre(request.getParameter("funcionCierre"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			seleccionarIndicadorForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			seleccionarIndicadorForm.setTipoOrden(tipoOrden);
		}

		if (seleccionarIndicadorForm.getFuncionCierre() != null) 
		{
			if (!seleccionarIndicadorForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarIndicadorForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarIndicadorForm.setFuncionCierre(seleccionarIndicadorForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarIndicadorForm.setFuncionCierre(null);
		}

		if (request.getParameter("frecuencia") != null) 
		{
			byte frecuencia = request.getParameter("frecuencia") == null ? Frecuencia.getFrecuenciaMensual().byteValue() : new Byte(request.getParameter("frecuencia")).byteValue();
			seleccionarIndicadorForm.setFrecuencia(new Byte(frecuencia));
		}

		seleccionarIndicadorForm.setNombreFrecuencia(Frecuencia.getNombre(seleccionarIndicadorForm.getFrecuencia().byteValue()));

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		List listaIndicadores = new ArrayList();

		Map filtros = new HashMap();

		filtros.put("frecuencia", seleccionarIndicadorForm.getFrecuencia());
		if ((seleccionarIndicadorForm.getFiltroNombre() != null) && (!seleccionarIndicadorForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", seleccionarIndicadorForm.getFiltroNombre());
		if ((seleccionarIndicadorForm.getFiltroOrganizacionId() != null) && (!seleccionarIndicadorForm.getFiltroOrganizacionId().equals("")) && (seleccionarIndicadorForm.getFiltroOrganizacionId().longValue() != 0L)) 
			filtros.put("organizacionId", seleccionarIndicadorForm.getFiltroOrganizacionId());
		if ((seleccionarIndicadorForm.getFiltroPlanId() != null) && (!seleccionarIndicadorForm.getFiltroPlanId().equals("")) && (seleccionarIndicadorForm.getFiltroPlanId().longValue() != 0L)) 
			filtros.put("planId", seleccionarIndicadorForm.getFiltroPlanId());
		if ((seleccionarIndicadorForm.getFiltroUnidadId() != null) && (!seleccionarIndicadorForm.getFiltroUnidadId().equals("")) && (seleccionarIndicadorForm.getFiltroUnidadId().longValue() != 0L)) 
			filtros.put("unidadId", seleccionarIndicadorForm.getFiltroUnidadId());
		if ((seleccionarIndicadorForm.getFiltroResponsableFijarMetaId() != null) && (!seleccionarIndicadorForm.getFiltroResponsableFijarMetaId().equals("")) && (seleccionarIndicadorForm.getFiltroResponsableFijarMetaId().longValue() != 0L)) 
			filtros.put("responsableFijarMetaId", seleccionarIndicadorForm.getFiltroResponsableFijarMetaId());
		if ((seleccionarIndicadorForm.getFiltroResponsableLograrMetaId() != null) && (!seleccionarIndicadorForm.getFiltroResponsableLograrMetaId().equals("")) && (seleccionarIndicadorForm.getFiltroResponsableLograrMetaId().longValue() != 0L)) 
			filtros.put("responsableLograrMetaId", seleccionarIndicadorForm.getFiltroResponsableLograrMetaId());
		if ((seleccionarIndicadorForm.getFiltroResponsableSeguimientoId() != null) && (!seleccionarIndicadorForm.getFiltroResponsableSeguimientoId().equals("")) && (seleccionarIndicadorForm.getFiltroResponsableSeguimientoId().longValue() != 0L)) 
			filtros.put("responsableSeguimientoId", seleccionarIndicadorForm.getFiltroResponsableSeguimientoId());
		if ((seleccionarIndicadorForm.getFiltroNaturaleza() != null) && (!seleccionarIndicadorForm.getFiltroNaturaleza().equals(""))) 
			filtros.put("naturaleza", new Byte(seleccionarIndicadorForm.getFiltroNaturaleza()));
		if ((seleccionarIndicadorForm.getFiltroCaracteristica() != null) && (!seleccionarIndicadorForm.getFiltroCaracteristica().equals(""))) 
			filtros.put("caracteristica", new Byte(seleccionarIndicadorForm.getFiltroCaracteristica()));
		if ((seleccionarIndicadorForm.getFiltroTipoIndicador() != null) && (!seleccionarIndicadorForm.getFiltroTipoIndicador().equals(""))) 
		{
			if (seleccionarIndicadorForm.getFiltroTipoIndicador().equals("1"))
				filtros.put("guia", new Boolean(true));
			else 
				filtros.put("guia", new Boolean(false));
		}

		PaginaLista paginaIndicadores = strategosIndicadoresService.getIndicadores(0, 0, atributoOrden, tipoOrden, true, filtros, null, null, true);

		request.setAttribute("paginaIndicadores", paginaIndicadores);

		setListas(seleccionarIndicadorForm, request);

		if (seleccionarIndicadorForm.getListaIndicadores().size() > 0) 
		{
			seleccionarIndicadorForm.setSeleccionados(((Indicador)seleccionarIndicadorForm.getListaIndicadores().get(0)).getIndicadorId().toString());
			seleccionarIndicadorForm.setValoresSeleccionados(((Indicador)seleccionarIndicadorForm.getListaIndicadores().get(0)).getNombre());
		} 
		else 
		{
			seleccionarIndicadorForm.setSeleccionados(null);
			seleccionarIndicadorForm.setValoresSeleccionados(null);
		}

		strategosIndicadoresService.close();

		return mapping.findForward(forward);
	}

	private void setListas(SeleccionarIndicadorForm seleccionarIndicadorForm, HttpServletRequest request)
	{
		seleccionarIndicadorForm.setListaNaturalezas(Naturaleza.getNaturalezas());
		seleccionarIndicadorForm.setListaCaracteristicas(Caracteristica.getCaracteristicas());
		seleccionarIndicadorForm.setListaTiposIndicadores(TipoIndicador.getTipos());
	}
}