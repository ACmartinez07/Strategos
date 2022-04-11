package com.visiongc.app.strategos.web.struts.planes.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.iniciativas.forms.GestionarIniciativasPlanForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarIniciativasPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIniciativasPlanForm gestionarIniciativasPlanForm = (GestionarIniciativasPlanForm)form;
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)request.getSession().getAttribute("gestionarPlanForm");

		String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
		String filtroAnio = (request.getParameter("filtroAnio") != null) ? request.getParameter("filtroAnio") : "";
		Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();
		Long selectEstatusType = (request.getParameter("selectEstatusType") != null && request.getParameter("selectEstatusType") != "" && !request.getParameter("selectEstatusType").equals("0")) ? Long.parseLong(request.getParameter("selectEstatusType")) : null;
        Long selectTipos = (request.getParameter("selectTipos") != null) && (request.getParameter("selectTipos") != "") && (!request.getParameter("selectTipos").equals("0")) ? Long.valueOf(Long.parseLong(request.getParameter("selectTipos"))) : null;

        StrategosTipoProyectoService strategosTiposProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();
        Map<String, String> filtrosTipo = new HashMap();
        PaginaLista paginaTipos = strategosTiposProyectoService.getTiposProyecto(0, 0, "tipoProyectoId", "asc", true, filtrosTipo);
        strategosTiposProyectoService.close();
        gestionarIniciativasPlanForm.setTipos(paginaTipos.getLista());
		
		FiltroForm filtro = new FiltroForm();
		filtro.setHistorico(selectHitoricoType);
		if (filtroNombre.equals(""))
			filtro.setNombre(null);
		else
			filtro.setNombre(filtroAnio);
		if (filtroAnio.equals(""))
			filtro.setAnio(null);
		else
			filtro.setAnio(filtroAnio);
		gestionarIniciativasPlanForm.setFiltro(filtro);
		gestionarIniciativasPlanForm.setEstatus(selectEstatusType);
		gestionarIniciativasPlanForm.setTipo(selectTipos);
		
		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
		Map<String, String> filtros = new HashMap<String, String>();
		PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc", true, filtros);
		strategosIniciativaEstatusService.close();
		gestionarIniciativasPlanForm.setTiposEstatus(paginaIniciativaEstatus.getLista());
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		Perspectiva perspectiva = (Perspectiva)strategosIniciativasService.load(Perspectiva.class, gestionarPlanForm.getPerspectivaId());

		if (perspectiva != null) 
		{
			gestionarIniciativasPlanForm.setNombreIniciativaPlural(perspectiva.getPlan().getMetodologia().getNombreIniciativaPlural());
			gestionarIniciativasPlanForm.setNombreIniciativaSingular(perspectiva.getPlan().getMetodologia().getNombreIniciativaSingular());
		}

		gestionarIniciativasPlanForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("INICIATIVA_VIEWALL"));
		gestionarIniciativasPlanForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("INICIATIVA_EDIT"));
		String atributoOrden = gestionarIniciativasPlanForm.getAtributoOrden();
		String tipoOrden = gestionarIniciativasPlanForm.getTipoOrden();
		int pagina = gestionarIniciativasPlanForm.getPagina();
		
		if (atributoOrden == null || atributoOrden.equals("orden")) 
		{
			atributoOrden = "nombre";
			gestionarIniciativasPlanForm.setAtributoOrden(atributoOrden);
		}

		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarIniciativasPlanForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		filtros = new HashMap<String, String>();

		filtros.put("perspectivaId", gestionarPlanForm.getPerspectivaId().toString());
		if (gestionarIniciativasPlanForm.getFiltro().getHistorico() != null && gestionarIniciativasPlanForm.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
			filtros.put("historicoDate", "IS NULL");
		else if (gestionarIniciativasPlanForm.getFiltro().getHistorico() != null && gestionarIniciativasPlanForm.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
			filtros.put("historicoDate", "IS NOT NULL");
		if (gestionarIniciativasPlanForm.getFiltro().getNombre() != null && !gestionarIniciativasPlanForm.getFiltro().getNombre().isEmpty())
			filtros.put("nombre", gestionarIniciativasPlanForm.getFiltro().getNombre());
		if (gestionarIniciativasPlanForm.getFiltro().getAnio() != null && !gestionarIniciativasPlanForm.getFiltro().getAnio().isEmpty())
			filtros.put("anio", gestionarIniciativasPlanForm.getFiltro().getAnio());
		if (gestionarIniciativasPlanForm.getEstatus() != null)
			filtros.put("estatusId", gestionarIniciativasPlanForm.getEstatus().toString());
		if (gestionarIniciativasPlanForm.getTipo() != null) {
	        filtros.put("tipoId", gestionarIniciativasPlanForm.getTipo().toString());
	    }
		
		PaginaLista paginaIniciativasPlan = strategosIniciativasService.getIniciativas(0, 0, atributoOrden, tipoOrden, true, filtros);
		paginaIniciativasPlan.setTamanoSetPaginas(5);

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		for (Iterator<Iniciativa> iter = paginaIniciativasPlan.getLista().iterator(); iter.hasNext(); ) 
		{
			Iniciativa iniciativa = (Iniciativa)iter.next();
			if (iniciativa.getPorcentajeCompletado() != null)
			{
				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				if (indicador != null)
				{
					boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
					
					Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
					if (medicionReal != null)
					{
						iniciativa.setUltimaMedicion(medicionReal.getValor());

						List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null, medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
						Double programado = null;
						for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext(); ) 
						{
		            		Medicion medicion = (Medicion)iter2.next();
		            		if (medicion.getValor() != null && programado == null)
		            			programado = medicion.getValor();
		            		else if (medicion.getValor() != null && programado != null && acumular)
		            			programado = programado + medicion.getValor();
		            		else if (medicion.getValor() != null && programado != null && !acumular)
		            			programado = medicion.getValor();
						}
						
						if (programado != null)
							iniciativa.setPorcentajeEsperado(programado);
					}
				}
			}
		}
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		
		request.setAttribute("paginaIniciativasPlan", paginaIniciativasPlan);

		strategosIniciativasService.close();

		return mapping.findForward(forward);
	}
}