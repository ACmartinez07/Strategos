package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.calculos.model.util.VgcFormulaEvaluator;
import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicador;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoAsociadoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarIndicadorAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)form;
		
		if (request.getParameter("funcion") != null && request.getParameter("accion") != null)
		{
			String funcion = request.getParameter("funcion");
			String accion = request.getParameter("accion");
	    	if (funcion.equals("mover")) 
	    	{
	    		if (accion.equals("editar"))
	    		{
					editarIndicadorForm.setIndicadores(new ArrayList<Long>());
					if (request.getQueryString().indexOf("indicadorId=") > -1)
					{
						String strIndicadorId = request.getParameter("indicadorId");
						if (((strIndicadorId != null ? 1 : 0) & (strIndicadorId.equals("") ? 0 : 1)) != 0) 
						{
							String[] ids = strIndicadorId.split(",");
							for (int i = 0; i < ids.length; i++)
								editarIndicadorForm.getIndicadores().add(new Long(ids[i]));
						}
					}
		    		
					return mapping.findForward(forward);
	    		}
	    		else if (accion.equals("salvar"))
	    		{
	    			Byte status = StatusUtil.getStatusSuccess();
	    			Long claseId = request.getParameter("claseId") != null ? Long.parseLong(request.getParameter("claseId")) : 0L;
	    			if (claseId == 0L)
	    				status = StatusUtil.getStatusInvalido();
	    			else
	    			{
	    				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    				ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, new Long(claseId));
	    				List<Indicador> indicadores = new ArrayList<Indicador>();

	    				if (request.getQueryString().indexOf("indicadorId=") > -1)
						{
							String strIndicadores = request.getParameter("indicadorId");
							if (((strIndicadores != null ? 1 : 0) & (strIndicadores.equals("") ? 0 : 1)) != 0) 
							{
								String[] ids = strIndicadores.split(",");
								for (int i = 0; i < ids.length; i++)
								{
			    			    	Long indicadorId = new Long(ids[i]);
			    			    	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
			    			    	if (indicador != null)
			    			    	{
			    			    		indicador.setClaseId(claseId);
			    			    		indicador.setClase(clase);
			    			    		indicador.setOrganizacionId(clase.getOrganizacionId());
			    			    		indicador.setOrganizacion(clase.getOrganizacion());
			    			    		
			    			    		indicadores.add(indicador);
			    			    	}
								}
							}
						}
	    			    
	    			    Usuario usuario = getUsuarioConectado(request);
	    			    int respuesta = VgcReturnCode.DB_OK;
	    			    for (Iterator<Indicador> k = indicadores.iterator(); k.hasNext(); )
	    			    {
	    			    	Indicador indicador = (Indicador)k.next();
	    			    	respuesta = strategosIndicadoresService.saveIndicador(indicador, usuario);
	    			    	if (respuesta != VgcReturnCode.DB_OK)
	    			    	{
	    			    		status = StatusUtil.getStatusRegistroDuplicado();
	    			    		break;
	    			    	}
	    			    }
	    			    
	    			    strategosIndicadoresService.close();
	    			}
	    			
	    		    request.setAttribute("ajaxResponse", status.toString());
	    			return mapping.findForward("ajaxResponse");
	    		}
	    	}
		}
		
		forward = getData(editarIndicadorForm, forward, request);
		
		if (forward.equals("licencia"))
			return this.getForwardBack(request, 1, false);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
	
	public String getData(EditarIndicadorForm editarIndicadorForm, String forward, HttpServletRequest request)
	{
		ActionMessages messages = getMessages(request);

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService(strategosIndicadoresService);

		String indicadorId = request.getParameter("indicadorId");
		String planId = request.getParameter("planId");
		String iniciativaId = request.getParameter("iniciativaId");

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT");
		boolean bloqueado = false;
		boolean inicializar = request.getParameter("inicializar") != null && request.getParameter("inicializar") != "" ? Boolean.parseBoolean(request.getParameter("inicializar")) : false; 

		Map<Object, Object> filtros = new HashMap<Object, Object>();

		filtros.put("oculta", false);
		List<?> seriesTiempo = strategosSeriesTiempoService.getSeriesTiempo(0, 0, "serieId", null, false, filtros).getLista();

		inicializarPoolLocksUsoEdicion(request, strategosIndicadoresService);

		if ((indicadorId != null) && (!indicadorId.equals("")) && (!indicadorId.equals("0")))
		{
			editarIndicadorForm.clear();

			if ((iniciativaId != null) && (!iniciativaId.equals("")))
				editarIndicadorForm.setIniciativaId(new Long(iniciativaId));
			else 
				editarIndicadorForm.setIniciativaId(null);
			if ((planId != null) && (!planId.equals(""))) 
			{
				Plan plan = (Plan)strategosIndicadoresService.load(Plan.class, new Long(planId));
				if (plan != null) 
					editarIndicadorForm.setNombreIndicadorSingular(plan.getMetodologia().getNombreIndicadorSingular());
				editarIndicadorForm.setPlanId(new Long(planId));
			} 
			else 
				editarIndicadorForm.setPlanId(null);

			bloqueado = !strategosIndicadoresService.lockForUpdate(request.getSession().getId(), indicadorId, null);

			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
			OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(indicador.getOrganizacionId()));
			indicador.setOrganizacion(organizacion);
			editarIndicadorForm.setOrganizacionId(organizacion.getOrganizacionId());
			editarIndicadorForm.setOrganizacion(organizacion);
			
			if (indicador != null)
			{
				if (editarIndicadorForm.getSoloLectura().booleanValue())
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));

				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarIndicadorForm.setSoloLectura(indicador.getSoloLectura());

				if (indicador.getTipoFuncion().byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()) 
				{
					editarIndicadorForm.setBloquearIndicadorIniciativa(new Boolean(true));
					editarIndicadorForm.setDesdeIniciativasPlanes(new Boolean(true));
				}
				else
				{
					StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
					Iniciativa iniciativa = null;
					if ((iniciativaId != null) && (!iniciativaId.equals("")))
						iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(iniciativaId));
					if (iniciativa != null)
					{
						if (iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficacia()) != null && new Long(indicadorId).longValue() == iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficacia()).longValue())
						{
							editarIndicadorForm.setBloquearIndicadorIniciativa(iniciativa.getEstatus().getBloquearIndicadores());
							editarIndicadorForm.setDesdeIniciativasPlanes(iniciativa.getEstatus().getBloquearIndicadores());
						}
						else if (iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()) != null && new Long(indicadorId).longValue() == iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()).longValue())
						{
							editarIndicadorForm.setBloquearIndicadorIniciativa(iniciativa.getEstatus().getBloquearIndicadores());
							editarIndicadorForm.setDesdeIniciativasPlanes(iniciativa.getEstatus().getBloquearIndicadores());
						}
						else if (iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()) != null && new Long(indicadorId).longValue() == iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).longValue())
						{
							editarIndicadorForm.setBloquearIndicadorIniciativa(iniciativa.getEstatus().getBloquearIndicadores());
							editarIndicadorForm.setDesdeIniciativasPlanes(iniciativa.getEstatus().getBloquearIndicadores());
						}
					}
					strategosIniciativasService.close();
				}
        
				if (editarIndicadorForm.getIniciativaId() != null) 
					editarIndicadorForm.setDesdeIniciativasPlanes(new Boolean(true));

				editarIndicadorForm.setNaturalezaNombre(indicador.getNaturalezaNombre());
				editarIndicadorForm.setFrecuenciaNombre(indicador.getFrecuenciaNombre());
				if (indicador.getUnidad() != null) 
					editarIndicadorForm.setUnidadNombre(indicador.getUnidad().getNombre());
				editarIndicadorForm.setCaracteristicaNombre(indicador.getCaracteristicaNombre());
				editarIndicadorForm.setTipoCorteNombre(indicador.getCorteNombre());
				editarIndicadorForm.setTipoNombre(indicador.getGuiaNombre());
				editarIndicadorForm.setClaseNombre(indicador.getClase().getNombre());

				setDatosBasicos(indicador, editarIndicadorForm, request);

				SerieIndicador serieIndicadorReal = setSeriesIndicador(indicador, editarIndicadorForm);

				setDefinicion(request, indicador, editarIndicadorForm, seriesTiempo, strategosIndicadoresService, strategosClasesIndicadoresService, serieIndicadorReal);

				setAsociar(indicador, editarIndicadorForm, strategosIndicadoresService);

				setResponsables(indicador, editarIndicadorForm);

				setParametros(indicador, editarIndicadorForm, strategosIndicadoresService);

				setAlertas(indicador, editarIndicadorForm, strategosIndicadoresService);

				setRelaciones(indicador, editarIndicadorForm);

				setAdicionales(indicador, editarIndicadorForm, strategosIndicadoresService);
			}
			else
			{
				strategosIndicadoresService.unlockObject(request.getSession().getId(), new Long(indicadorId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			if (!strategosIndicadoresService.checkLicencia(request))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
				this.saveMessages(request, messages);
				forward = "licencia";
			}
			
			ClaseIndicadores claseIndicadores = null;
			Long claseId = null;

			Plan plan = null;
			Perspectiva perspectiva = null;
			if ((editarIndicadorForm.getPerspectivaId() != null) && (editarIndicadorForm.getPerspectivaId().longValue() != 0L))
			{
				perspectiva = (Perspectiva)strategosClasesIndicadoresService.load(Perspectiva.class, editarIndicadorForm.getPerspectivaId());
				if (perspectiva != null && perspectiva.getClaseId() != null)
					claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, perspectiva.getClaseId());
				else if ((editarIndicadorForm.getPlanId() != null) && (editarIndicadorForm.getPlanId().longValue() != 0L))
				{
					plan = (Plan)strategosClasesIndicadoresService.load(Plan.class, editarIndicadorForm.getPlanId());
					if (plan != null && plan.getClaseId() != null)
						claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, plan.getClaseId());
				}
			}
			else if ((editarIndicadorForm.getPlanId() != null) && (editarIndicadorForm.getPlanId().longValue() != 0L))
			{
				plan = (Plan)strategosClasesIndicadoresService.load(Plan.class, editarIndicadorForm.getPlanId());
				if (plan != null)
					claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, plan.getClaseId());
			} 
			else if ((request.getParameter("claseId") != null) && (!request.getParameter("claseId").equals("")))
			{
				claseId = new Long(request.getParameter("claseId"));
				claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, claseId);
			}
			else 
				claseIndicadores = (ClaseIndicadores)request.getSession().getAttribute("claseIndicadores");

			editarIndicadorForm.clear(!inicializar);

			if ((iniciativaId != null) && (!iniciativaId.equals("")))
				editarIndicadorForm.setIniciativaId(new Long(iniciativaId));
			else 
				editarIndicadorForm.setIniciativaId(null);
			if ((planId != null) && (!planId.equals(""))) 
			{
				editarIndicadorForm.setPlanId(new Long(planId));
				if (plan != null)
					editarIndicadorForm.setNombreIndicadorSingular(plan.getMetodologia().getNombreIndicadorSingular());
			}
			else 
				editarIndicadorForm.setPlanId(null);

			OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIndicadoresService.load(OrganizacionStrategos.class, new Long(claseIndicadores.getOrganizacionId()));
			editarIndicadorForm.setOrganizacionId(organizacion.getOrganizacionId());
			editarIndicadorForm.setOrganizacion(organizacion);
			
			editarIndicadorForm.setClaseIndicadores(claseIndicadores);
			editarIndicadorForm.setClaseId(claseIndicadores.getClaseId());

			String listaSeries = editarIndicadorForm.getSeparadorSeries() + SerieTiempo.getSerieReal().getSerieId() + editarIndicadorForm.getSeparadorSeries();
			editarIndicadorForm.setSeriesIndicador(listaSeries);
		}

		if (request.getParameter("funcion") != null && request.getParameter("funcion").toString().equals("Copiar"))
		{
			// Presentacion
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Copiar.ShowPresentacion", "ShowPresentacion");
			if (presentacion != null && presentacion.getData() != null)
				editarIndicadorForm.setShowPresentacion(presentacion.getData().equals("1") ? true : false);
			frameworkService.close();
		}
	    
		editarIndicadorForm.setFrecuencias(Frecuencia.getFrecuencias());
		editarIndicadorForm.setNaturalezas(Naturaleza.getNaturalezas());
		editarIndicadorForm.setTiposAsociado(TipoAsociadoIndicador.getTiposAsociado());
		editarIndicadorForm.setIndicadorAsociadoRevisiones(PeriodoUtil.getListaNumeros(new Integer(0), new Integer(99)));
		setUnidadesMedida(editarIndicadorForm, strategosIndicadoresService);
		setCategoriasMedicion(editarIndicadorForm, strategosIndicadoresService);
		setMesCierre(request, editarIndicadorForm);
		editarIndicadorForm.setCaracteristicas(Caracteristica.getCaracteristicas());
		editarIndicadorForm.setTipos(TipoIndicador.getTipos());
		editarIndicadorForm.setTiposCorte(TipoCorte.getTipoCortes());
		editarIndicadorForm.setTiposMedicion(TipoMedicion.getTipoMediciones());
		editarIndicadorForm.setSeriesTiempo(seriesTiempo);
		editarIndicadorForm.setFuncionesFormula(VgcFormulaEvaluator.getListaFunciones());
		
		strategosIndicadoresService.close();
		strategosSeriesTiempoService.close();
		strategosClasesIndicadoresService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarIndicadorForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
		
		saveMessages(request, messages);
		
		return forward;
	}

	private void setDatosBasicos(Indicador indicador, EditarIndicadorForm editarIndicadorForm, HttpServletRequest request)
	{
		editarIndicadorForm.setIndicadorId(indicador.getIndicadorId());

		ClaseIndicadores clase = indicador.getClase();
		clase.getNombre();
		if (clase != null) 
		{
			editarIndicadorForm.setClaseIndicadores(clase);
			editarIndicadorForm.setClaseId(clase.getClaseId());
		} 
		else 
		{
			ClaseIndicadores claseIndicadores = (ClaseIndicadores)request.getSession().getAttribute("claseIndicadores");
			editarIndicadorForm.setClaseIndicadores(claseIndicadores);
			editarIndicadorForm.setClaseId(claseIndicadores.getClaseId());
		}

		editarIndicadorForm.setUnidadId(indicador.getUnidadId());
		editarIndicadorForm.setNombreLargo(indicador.getNombreCorto());
		editarIndicadorForm.setNombre(indicador.getNombre());
		editarIndicadorForm.setDescripcion(indicador.getDescripcion());
		editarIndicadorForm.setComportamiento(indicador.getComportamiento());
		editarIndicadorForm.setFuente(indicador.getFuente());
		editarIndicadorForm.setNaturaleza(indicador.getNaturaleza());
		editarIndicadorForm.setNaturalezaOriginal(indicador.getNaturaleza());
		editarIndicadorForm.setFrecuencia(indicador.getFrecuencia());
		editarIndicadorForm.setFrecuenciaOriginal(indicador.getFrecuencia());
		editarIndicadorForm.setNumeroDecimales(indicador.getNumeroDecimales());
		editarIndicadorForm.setMostrarEnArbol(indicador.getMostrarEnArbol());
		editarIndicadorForm.setTipoSumaMedicion(indicador.getTipoSumaMedicion());
		editarIndicadorForm.setOrden(indicador.getOrden());
	}

  private void setDefinicionSimple(Indicador indicador, EditarIndicadorForm editarIndicadorForm) 
  {
	  editarIndicadorForm.setCodigoEnlace(indicador.getCodigoEnlace());
	  editarIndicadorForm.setEnlaceParcial(indicador.getEnlaceParcial());
  }

  private void setDefinicionCualitativo(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  {
    String escalaCualitativa = "";
    for (Iterator<?> k = indicador.getEscalaCualitativa().iterator(); k.hasNext(); ) {
      CategoriaIndicador categoriaIndicador = (CategoriaIndicador)k.next();
      escalaCualitativa = escalaCualitativa + "[categoriaId:" + categoriaIndicador.getPk().getCategoriaId() + "]" + "[categoriaNombre:" + categoriaIndicador.getCategoriaMedicion().getNombre() + "]" + editarIndicadorForm.getSeparadorIndicadores();
    }
    if (!escalaCualitativa.equals(""))
      editarIndicadorForm.setEscalaCualitativa(escalaCualitativa.substring(0, escalaCualitativa.length() - editarIndicadorForm.getSeparadorCategorias().length()));
  }

  private void setDefinicionSumatoria(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService, SerieIndicador serieReal)
  {
    Formula formulaIndicador = null;

    if (serieReal.getFormulas().size() > 0) {
      formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
    }

    if (formulaIndicador != null)
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext(); ) 
      {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());

        editarIndicadorForm.setIndicadorSumatoriaId("[indicadorId:" + indicadorInsumo.getIndicadorId().toString() + "][serieId:" + insumo.getPk().getSerieId().toString() + "]");
        editarIndicadorForm.setIndicadorSumatoria(indicadorInsumo.getNombre());
        editarIndicadorForm.setIndicadorSumatoriaFrecuenciaId(indicadorInsumo.getFrecuencia());
        editarIndicadorForm.setIndicadorSumatoriaFrecuencia(indicadorInsumo.getFrecuenciaNombre());
        if (indicadorInsumo.getUnidad() != null)
          editarIndicadorForm.setIndicadorSumatoriaUnidad(indicadorInsumo.getUnidad().getNombre());
      }
  }

  private void setDefinicionPromedio(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService, SerieIndicador serieReal)
  {
    Formula formulaIndicador = null;

    if (serieReal.getFormulas().size() > 0) {
      formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
    }

    if (formulaIndicador != null)
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext(); ) {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());

        editarIndicadorForm.setIndicadorPromedioId("[indicadorId:" + indicadorInsumo.getIndicadorId().toString() + "][serieId:" + insumo.getPk().getSerieId().toString() + "]");
        editarIndicadorForm.setIndicadorPromedio(indicadorInsumo.getNombre());
        editarIndicadorForm.setIndicadorPromedioFrecuenciaId(indicadorInsumo.getFrecuencia());
        editarIndicadorForm.setIndicadorPromedioFrecuencia(indicadorInsumo.getFrecuenciaNombre());
        if (indicadorInsumo.getUnidad() != null)
          editarIndicadorForm.setIndicadorPromedioUnidad(indicadorInsumo.getUnidad().getNombre());
      }
  }

  private void setDefinicionIndice(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService, SerieIndicador serieReal)
  {
    Formula formulaIndicador = null;

    if (serieReal.getFormulas().size() > 0) {
      formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];
      String expresion = formulaIndicador.getExpresion();
      int index = expresion.indexOf("\\");
      editarIndicadorForm.setIndicadorIndiceTipoVariacion(expresion.substring(0, index));
      editarIndicadorForm.setIndicadorIndiceTipoComparacion(expresion.substring(index + 1));
    }

    if (formulaIndicador != null)
      for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext(); ) {
        InsumoFormula insumo = (InsumoFormula)k.next();
        Indicador indicadorInsumo = (Indicador)strategosIndicadoresService.load(Indicador.class, insumo.getPk().getIndicadorId());

        editarIndicadorForm.setIndicadorIndiceId("[indicadorId:" + indicadorInsumo.getIndicadorId().toString() + "][serieId:" + insumo.getPk().getSerieId().toString() + "]");
        editarIndicadorForm.setIndicadorIndice(indicadorInsumo.getNombre());
        editarIndicadorForm.setIndicadorIndiceFrecuenciaId(indicadorInsumo.getFrecuencia());
        editarIndicadorForm.setIndicadorIndiceFrecuencia(indicadorInsumo.getFrecuenciaNombre());
        if (indicadorInsumo.getUnidad() != null)
          editarIndicadorForm.setIndicadorIndiceUnidad(indicadorInsumo.getUnidad().getNombre());
      }
  }

  	private void setDefinicionFomula(HttpServletRequest request, Indicador indicador, EditarIndicadorForm editarIndicadorForm, List<?> seriesTiempo, StrategosIndicadoresService strategosIndicadoresService, StrategosClasesIndicadoresService strategosClasesIndicadoresService, SerieIndicador serieReal)
  	{
  		editarIndicadorForm.setCodigoEnlaceFormula(indicador.getCodigoEnlace());
  		editarIndicadorForm.setEnlaceParcialFormula(indicador.getEnlaceParcial());
  		editarIndicadorForm.setAsignarInventario(indicador.getAsignarInventario());

  		String insumosFormula = "";
  		int indice = 1;

  		Formula formulaIndicador = null;

  		if (serieReal.getFormulas().size() > 0) 
  			formulaIndicador = (Formula)serieReal.getFormulas().toArray()[0];

  		if (formulaIndicador != null)
  		{
  			for (Iterator<?> k = formulaIndicador.getInsumos().iterator(); k.hasNext(); ) 
  			{
  				InsumoFormula insumo = (InsumoFormula)k.next();
  				Indicador indicadorInsumo = strategosIndicadoresService.getIndicadorBasico(insumo.getPk().getIndicadorId());

  				String nombreSerie = null;
  				for (Iterator<?> j = seriesTiempo.iterator(); j.hasNext(); ) 
  				{
  					SerieTiempo serie = (SerieTiempo)j.next();

  					if (serie.getSerieId().equals(insumo.getPk().getInsumoSerieId())) 
  					{
  						nombreSerie = serie.getNombre();
  						break;
  					}
  				}
  				
  				if (insumo != null && indicadorInsumo != null)
  					insumosFormula = insumosFormula + "[" + indice + "]" + "[indicadorId:" + insumo.getPk().getIndicadorId() + "]" + "[serieId:" + insumo.getPk().getInsumoSerieId() + "]" + "[indicadorNombre:" + indicadorInsumo.getNombre() + "][serieNombre:" + nombreSerie + "]" + "[rutaCompleta:" + strategosIndicadoresService.getRutaCompletaIndicador(insumo.getPk().getIndicadorId(), editarIndicadorForm.getSeparadorRuta()) + "]" + editarIndicadorForm.getSeparadorIndicadores();

  				indice++;
  			}

  			if (!insumosFormula.equals("")) 
  				editarIndicadorForm.setInsumosFormula(insumosFormula.substring(0, insumosFormula.length() - editarIndicadorForm.getSeparadorIndicadores().length()));
  			if (formulaIndicador.getExpresion() != null) 
  			{
  				String formula = IndicadorValidator.reemplazarIdsPorCorrelativosFormula(formulaIndicador.getExpresion(), insumosFormula);
  				editarIndicadorForm.setFormula(formula);
  			}
  		}
  	}

  private void setDefinicion(HttpServletRequest request, Indicador indicador, EditarIndicadorForm editarIndicadorForm, List<?> seriesTiempo, StrategosIndicadoresService strategosIndicadoresService, StrategosClasesIndicadoresService strategosClasesIndicadoresService, SerieIndicador serieReal)
  {
    if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaSimple()))
      setDefinicionSimple(indicador, editarIndicadorForm);
    else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
      setDefinicionFomula(request, indicador, editarIndicadorForm, seriesTiempo, strategosIndicadoresService, strategosClasesIndicadoresService, serieReal);
    else if ((indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal())) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())))
      setDefinicionCualitativo(indicador, editarIndicadorForm);
    else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaSumatoria()))
      setDefinicionSumatoria(indicador, editarIndicadorForm, strategosIndicadoresService, serieReal);
    else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaPromedio()))
      setDefinicionPromedio(indicador, editarIndicadorForm, strategosIndicadoresService, serieReal);
    else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaIndice()))
      setDefinicionIndice(indicador, editarIndicadorForm, strategosIndicadoresService, serieReal);
  }

  private void setAsociar(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService)
  {
    if (indicador.getIndicadorAsociadoTipo() != null) {
      editarIndicadorForm.setIndicadorAsociadoTipo(indicador.getIndicadorAsociadoTipo());

      if (indicador.getIndicadorAsociadoId() != null) {
        editarIndicadorForm.setIndicadorAsociadoId(indicador.getIndicadorAsociadoId());

        Indicador indicadorAsociado = (Indicador)strategosIndicadoresService.load(Indicador.class, indicador.getIndicadorAsociadoId());

        if (indicadorAsociado != null) {
          editarIndicadorForm.setIndicadorAsociadoNombre(indicadorAsociado.getNombre());
          editarIndicadorForm.setIndicadorAsociadoFrecuencia(indicadorAsociado.getFrecuenciaNombre());
          if (indicadorAsociado.getUnidad() != null) {
            editarIndicadorForm.setIndicadorAsociadoUnidad(indicadorAsociado.getUnidad().getNombre());
          }
        }
      }

      if (indicador.getIndicadorAsociadoRevision() != null)
        editarIndicadorForm.setIndicadorAsociadoRevision(indicador.getIndicadorAsociadoRevision());
    }
  }

  private void setResponsables(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  {
	editarIndicadorForm.setResponsableNotificacionId(indicador.getResponsableNotificacionId());  
    editarIndicadorForm.setResponsableFijarMetaId(indicador.getResponsableFijarMetaId());
    editarIndicadorForm.setResponsableLograrMetaId(indicador.getResponsableLograrMetaId());
    editarIndicadorForm.setResponsableSeguimientoId(indicador.getResponsableSeguimientoId());
    editarIndicadorForm.setResponsableCargarMetaId(indicador.getResponsableCargarMetaId());
    editarIndicadorForm.setResponsableCargarEjecutadoId(indicador.getResponsableCargarEjecutadoId());

    if (indicador.getResponsableNotificacion() != null)
        editarIndicadorForm.setResponsableNotificacion(indicador.getResponsableNotificacion().getNombreCargo());
    else {
        editarIndicadorForm.setResponsableNotificacion(null);
    }
        
    
    if (indicador.getResponsableFijarMeta() != null)
      editarIndicadorForm.setResponsableFijarMeta(indicador.getResponsableFijarMeta().getNombreCargo());
    else {
      editarIndicadorForm.setResponsableFijarMeta(null);
    }

    if (indicador.getResponsableLograrMeta() != null)
      editarIndicadorForm.setResponsableLograrMeta(indicador.getResponsableLograrMeta().getNombreCargo());
    else {
      editarIndicadorForm.setResponsableLograrMeta(null);
    }

    if (indicador.getResponsableSeguimiento() != null)
      editarIndicadorForm.setResponsableSeguimiento(indicador.getResponsableSeguimiento().getNombreCargo());
    else {
      editarIndicadorForm.setResponsableSeguimiento(null);
    }

    if (indicador.getResponsableCargarMeta() != null)
      editarIndicadorForm.setResponsableCargarMeta(indicador.getResponsableCargarMeta().getNombreCargo());
    else {
      editarIndicadorForm.setResponsableCargarMeta(null);
    }

    if (indicador.getResponsableCargarEjecutado() != null)
      editarIndicadorForm.setResponsableCargarEjecutado(indicador.getResponsableCargarEjecutado().getNombreCargo());
    else
      editarIndicadorForm.setResponsableCargarEjecutado(null);
  }

  private SerieIndicador setSeriesIndicador(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  {
    Set<?> seriesIndicador = indicador.getSeriesIndicador();

    String listaSeries = "";

    SerieIndicador serieIndicador = null;

    for (Iterator<?> i = seriesIndicador.iterator(); i.hasNext(); ) {
      SerieIndicador serie = (SerieIndicador)i.next();

      if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) {
        serieIndicador = serie;
      }
      listaSeries = listaSeries + editarIndicadorForm.getSeparadorSeries() + serie.getPk().getSerieId() + editarIndicadorForm.getSeparadorSeries();
    }

    editarIndicadorForm.setSeriesIndicador(listaSeries);

    return serieIndicador;
  }

  private void setParametros(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService) {
    editarIndicadorForm.setCaracteristica(indicador.getCaracteristica());

    if (indicador.getGuia().booleanValue())
      editarIndicadorForm.setTipoGuiaResultado(TipoIndicador.getTipoIndicadorGuia());
    else {
      editarIndicadorForm.setTipoGuiaResultado(TipoIndicador.getTipoIndicadorResultado());
    }

    editarIndicadorForm.setCorte(indicador.getCorte());

    editarIndicadorForm.setTipoCargaMedicion(indicador.getTipoCargaMedicion());

    editarIndicadorForm.setValorInicialCero(indicador.getValorInicialCero());

    if (indicador.getParametroInferiorValorFijo() != null) {
      editarIndicadorForm.setParametroTipoAcotamientoInferior(editarIndicadorForm.getTipoAcotamientoValorFijo());
      editarIndicadorForm.setParametroInferiorValorFijo(indicador.getParametroInferiorValorFijo());
    } else if (indicador.getParametroInferiorIndicadorId() != null) {
      editarIndicadorForm.setParametroTipoAcotamientoInferior(editarIndicadorForm.getTipoAcotamientoValorIndicador());
      editarIndicadorForm.setParametroInferiorIndicadorId(indicador.getParametroInferiorIndicadorId());

      Indicador indicadorParametroInferior = (Indicador)strategosIndicadoresService.load(Indicador.class, indicador.getParametroInferiorIndicadorId());

      if (indicadorParametroInferior != null) {
        editarIndicadorForm.setParametroInferiorIndicadorNombre(indicadorParametroInferior.getNombre());
      }

    }

    if (indicador.getParametroSuperiorValorFijo() != null) {
      editarIndicadorForm.setParametroTipoAcotamientoSuperior(editarIndicadorForm.getTipoAcotamientoValorFijo());
      editarIndicadorForm.setParametroSuperiorValorFijo(indicador.getParametroSuperiorValorFijo());
    } else if (indicador.getParametroSuperiorIndicadorId() != null) {
      editarIndicadorForm.setParametroTipoAcotamientoSuperior(editarIndicadorForm.getTipoAcotamientoValorIndicador());
      editarIndicadorForm.setParametroSuperiorIndicadorId(indicador.getParametroSuperiorIndicadorId());

      Indicador indicadorParametroSuperior = (Indicador)strategosIndicadoresService.load(Indicador.class, indicador.getParametroSuperiorIndicadorId());

      if (indicadorParametroSuperior != null)
        editarIndicadorForm.setParametroSuperiorIndicadorNombre(indicadorParametroSuperior.getNombre());
    }
  }

  private void setAlertas(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService)
  {
    if (indicador.getAlertaValorVariableZonaAmarilla().booleanValue())
      editarIndicadorForm.setAlertaValorVariableZonaAmarilla(new Byte("1"));
    else {
      editarIndicadorForm.setAlertaValorVariableZonaAmarilla(new Byte("0"));
    }
    if (indicador.getAlertaValorVariableZonaVerde().booleanValue())
      editarIndicadorForm.setAlertaValorVariableZonaVerde(new Byte("1"));
    else {
      editarIndicadorForm.setAlertaValorVariableZonaVerde(new Byte("0"));
    }

    if (indicador.getAlertaTipoZonaAmarilla() != null) {
      editarIndicadorForm.setAlertaTipoZonaAmarilla(indicador.getAlertaTipoZonaAmarilla());
    }
    if (indicador.getAlertaTipoZonaVerde() != null) {
      editarIndicadorForm.setAlertaTipoZonaVerde(indicador.getAlertaTipoZonaVerde());
    }

    editarIndicadorForm.setAlertaMetaZonaVerde(indicador.getAlertaMetaZonaVerde());

    editarIndicadorForm.setAlertaMetaZonaAmarilla(indicador.getAlertaMetaZonaAmarilla());

    editarIndicadorForm.setAlertaIndicadorIdZonaVerde(indicador.getAlertaIndicadorIdZonaVerde());
    Long indicadorIdZonaVerde = editarIndicadorForm.getAlertaIndicadorIdZonaVerde();

    if (indicadorIdZonaVerde != null) {
      Indicador indicadorZonaVerde = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorIdZonaVerde);

      String nombreIndicadorVerde = indicadorZonaVerde.getNombre();
      editarIndicadorForm.setNombreIndicadorZonaVerde(nombreIndicadorVerde);
    }

    editarIndicadorForm.setAlertaIndicadorIdZonaAmarilla(indicador.getAlertaIndicadorIdZonaAmarilla());

    Long indicadorIdZonaAmarilla = editarIndicadorForm.getAlertaIndicadorIdZonaAmarilla();

    if (indicadorIdZonaAmarilla != null) {
      Indicador indicadorZonaAmarilla = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorIdZonaAmarilla);

      String nombreIndicadorAmarilla = indicadorZonaAmarilla.getNombre();
      editarIndicadorForm.setNombreIndicadorZonaAmarilla(nombreIndicadorAmarilla);
    }
  }

  private void setRelaciones(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  {
    editarIndicadorForm.setUrl(indicador.getUrl());
  }

  private void setAdicionales(Indicador indicador, EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService) {
    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(strategosIndicadoresService);
    Long numeroMediciones = strategosMedicionesService.getNumeroMediciones(indicador.getIndicadorId());
    if ((numeroMediciones != null) && (numeroMediciones.intValue() > 0))
      editarIndicadorForm.setTieneMediciones(new Boolean(true));
    else {
      editarIndicadorForm.setTieneMediciones(new Boolean(false));
    }
    strategosMedicionesService.close();
  }

  private void setUnidadesMedida(EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService) {
    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosIndicadoresService);

    editarIndicadorForm.setUnidadesMedida(strategosUnidadesService.getUnidadesMedida(0, 0, "nombre", "asc", false, null).getLista());

    strategosUnidadesService.close();
  }

  private void setCategoriasMedicion(EditarIndicadorForm editarIndicadorForm, StrategosIndicadoresService strategosIndicadoresService) {
    StrategosCategoriasService strategosCategoriasService = StrategosServiceFactory.getInstance().openStrategosCategoriasService(strategosIndicadoresService);

    editarIndicadorForm.setCategorias(strategosCategoriasService.getCategoriasMedicion(0, 0, "nombre", "asc", false, null).getLista());

    strategosCategoriasService.close();
  }

  private void setMesCierre(HttpServletRequest request, EditarIndicadorForm editarIndicadorForm) {
    OrganizacionStrategos organizacion = (OrganizacionStrategos)request.getSession().getAttribute("organizacion");

    editarIndicadorForm.setMesCierreOrganizacion(PeriodoUtil.getMesNombre(organizacion.getMesCierre()));
  }
}