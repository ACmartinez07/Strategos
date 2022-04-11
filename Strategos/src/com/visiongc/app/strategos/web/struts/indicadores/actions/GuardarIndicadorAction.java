package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadorAsignarInventarioService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicador;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoAsociadoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.model.Usuario;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarIndicadorAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)form;

		ActionMessages messages = getMessages(request);
		
	

		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarIndicadorAction.ultimoTs");
		Boolean valorInicialCero = WebUtil.getValorInputCheck(request, "valorInicialCero");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosIndicadorAsignarInventarioService strategosIndicadoresInventarioService = StrategosServiceFactory.getInstance().openStrategosIndicadorAsignarInventarioService();
		
		if (cancelar)
		{
			strategosIndicadoresService.unlockObject(request.getSession().getId(), editarIndicadorForm.getIndicadorId());
			
			destruirPoolLocksUsoEdicion(request, strategosIndicadoresService);

			request.getSession().removeAttribute("editarIndicadorForm");

			strategosIndicadoresService.close();

			return getForwardBack(request, 1, true);
		}

		Usuario usuario = getUsuarioConectado(request);

		Indicador indicador = new Indicador();
		boolean nuevo = false;
		int respuesta = 10000;
		if (valorInicialCero != null)
			editarIndicadorForm.setValorInicialCero(valorInicialCero);

		if ((editarIndicadorForm.getIndicadorId() != null) && (!editarIndicadorForm.getIndicadorId().equals(Long.valueOf("0"))))
			indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, editarIndicadorForm.getIndicadorId());
		else
		{
			if (!strategosIndicadoresService.checkLicencia(request))
			{
				strategosIndicadoresService.close();
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
				this.saveMessages(request, messages);
				
				return this.getForwardBack(request, 1, false);
			}
			
			indicador = new Indicador();
			indicador.setIndicadorId(new Long(0L));
			Long claseId = editarIndicadorForm.getClaseIndicadores().getClaseId();
			indicador.setClaseId(claseId);
			indicador.setOrganizacionId(organizacionId);
			indicador.setEscalaCualitativa(new ArrayList<Object>());
			indicador.setSeriesIndicador(new HashSet<Object>());
			indicador.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
			nuevo = true;
		}

		setDatosBasicos(indicador, editarIndicadorForm, request);

		setAsociar(indicador, editarIndicadorForm, request);

		setResponsables(indicador, editarIndicadorForm);

		setParametros(indicador, editarIndicadorForm, request);

		setAlertas(indicador, editarIndicadorForm, request);

		SerieIndicador serieReal = setSeriesTiempo(indicador, editarIndicadorForm);

		setDefinicion(indicador, editarIndicadorForm, serieReal, request);

		setRelaciones(indicador, editarIndicadorForm);

		if ((editarIndicadorForm.getPlanId() != null) && (editarIndicadorForm.getPlanId().longValue() != 0L) && (editarIndicadorForm.getPerspectivaId() != null) && (editarIndicadorForm.getPerspectivaId().longValue() != 0L)) 
		{
			indicador.setPlanId(editarIndicadorForm.getPlanId());
			indicador.setPerspectivaId(editarIndicadorForm.getPerspectivaId());
		}
		
		if(indicador.getAsignarInventario() != null && indicador.getAsignarInventario() == true && indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula())){
			asignarInventario(indicador,strategosIndicadoresService, strategosIndicadoresInventarioService, usuario);
		}
		
		respuesta = strategosIndicadoresService.saveIndicador(indicador, usuario);

		if (respuesta == 10000) 
		{
			strategosIndicadoresService.unlockObject(request.getSession().getId(), editarIndicadorForm.getIndicadorId());

			request.getSession().removeAttribute("editarIndicadorForm");

			destruirPoolLocksUsoEdicion(request, strategosIndicadoresService);

			forward = "exito";
			
			if (request.getSession().getAttribute("GuardarIndicador") == null)
				request.getSession().setAttribute("GuardarIndicador", "true");

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearIndicador";
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
		else if (respuesta == 10007)
		{
			Indicador indicadorCircular = strategosIndicadoresService.getIndicadorReferenciaCircular(serieReal);
			if (indicadorCircular != null)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.circular", indicadorCircular.getNombre(), indicador.getNombre()));
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.circularEmpty"));
		}

		strategosIndicadoresService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarIndicadorAction.ultimoTs", ts);

		if (forward.equals("exito")) 
		{
			request.getSession().removeAttribute("editarIndicadorForm");
			return getForwardBack(request, 1, true);
		}
    
		return mapping.findForward(forward);
	}

  	private void setDatosBasicos(Indicador indicador, EditarIndicadorForm editarIndicadorForm, HttpServletRequest request)
  	{
  		indicador.setNombre(editarIndicadorForm.getNombreLargo());
  		indicador.setNombreCorto(editarIndicadorForm.getNombre());
  		indicador.setNaturaleza(editarIndicadorForm.getNaturaleza());
  		indicador.setFrecuencia(editarIndicadorForm.getFrecuencia());
  		indicador.setTipoSumaMedicion(editarIndicadorForm.getTipoSumaMedicion());

  		if ((indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal())))
  			editarIndicadorForm.setUnidadId(0L);
  		if ((editarIndicadorForm.getUnidadId() != null) && (editarIndicadorForm.getUnidadId().longValue() > 0L))
  			indicador.setUnidadId(editarIndicadorForm.getUnidadId());
  		else 
  			indicador.setUnidadId(null);

  		if (editarIndicadorForm.getPrioridad() != null)
  			indicador.setPrioridad(editarIndicadorForm.getPrioridad());
  		else 
  			indicador.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());

  		indicador.setMostrarEnArbol(WebUtil.getValorInputCheck(request, "mostrarEnArbol"));
  		indicador.setNumeroDecimales(editarIndicadorForm.getNumeroDecimales());

  		if ((editarIndicadorForm.getDescripcion() != null) && (!editarIndicadorForm.getDescripcion().equals("")))
  			indicador.setDescripcion(editarIndicadorForm.getDescripcion());
  		else 
  			indicador.setDescripcion(null);

  		if ((editarIndicadorForm.getComportamiento() != null) && (!editarIndicadorForm.getComportamiento().equals("")))
  			indicador.setComportamiento(editarIndicadorForm.getComportamiento());
  		else 
  			indicador.setComportamiento(null);

  		if ((editarIndicadorForm.getFuente() != null) && (!editarIndicadorForm.getFuente().equals("")))
  			indicador.setFuente(editarIndicadorForm.getFuente());
  		else
  			indicador.setFuente(null);

  		if ((editarIndicadorForm.getOrden() != null) && (!editarIndicadorForm.getOrden().equals("")))
  			indicador.setOrden(editarIndicadorForm.getOrden());
  		else
  			indicador.setOrden(null);
  	}

  	private void setAsociar(Indicador indicador, EditarIndicadorForm editarIndicadorForm, HttpServletRequest request)
  	{
  		if ((request.getParameter("indicadorAsociadoTipo") == null) || (request.getParameter("indicadorAsociadoTipo").equals(""))) 
  		{
  			indicador.setIndicadorAsociadoTipo(null);
  			indicador.setIndicadorAsociadoId(null);
  			indicador.setIndicadorAsociadoRevision(null);
  		} 
  		else 
  		{
  			indicador.setIndicadorAsociadoTipo(editarIndicadorForm.getIndicadorAsociadoTipo());
  			indicador.setIndicadorAsociadoId(editarIndicadorForm.getIndicadorAsociadoId());
  			if ((indicador.getIndicadorAsociadoTipo() != null) && (indicador.getIndicadorAsociadoTipo().equals(TipoAsociadoIndicador.getTipoAsociadoIndicadorProgramado())))
  				indicador.setIndicadorAsociadoRevision(editarIndicadorForm.getIndicadorAsociadoRevision());
  			else
  				indicador.setIndicadorAsociadoRevision(null);
  		}
  	}

  	private void setResponsables(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  	{
  		
  		if (editarIndicadorForm.getResponsableNotificacionId().equals(new Long(0L)))
  			indicador.setResponsableNotificacionId(null);
  		else 
  			indicador.setResponsableNotificacionId(editarIndicadorForm.getResponsableNotificacionId());
  		
  		
  		if (editarIndicadorForm.getResponsableFijarMetaId().equals(new Long(0L)))
  			indicador.setResponsableFijarMetaId(null);
  		else 
  			indicador.setResponsableFijarMetaId(editarIndicadorForm.getResponsableFijarMetaId());

  		if (editarIndicadorForm.getResponsableLograrMetaId().equals(new Long(0L)))
  			indicador.setResponsableLograrMetaId(null);
  		else 
  			indicador.setResponsableLograrMetaId(editarIndicadorForm.getResponsableLograrMetaId());

  		if (editarIndicadorForm.getResponsableSeguimientoId().equals(new Long(0L)))
  			indicador.setResponsableSeguimientoId(null);
  		else 
  			indicador.setResponsableSeguimientoId(editarIndicadorForm.getResponsableSeguimientoId());

  		if (editarIndicadorForm.getResponsableCargarMetaId().equals(new Long(0L)))
  			indicador.setResponsableCargarMetaId(null);
  		else 
  			indicador.setResponsableCargarMetaId(editarIndicadorForm.getResponsableCargarMetaId());

  		if (editarIndicadorForm.getResponsableCargarEjecutadoId().equals(new Long(0L)))
  			indicador.setResponsableCargarEjecutadoId(null);
  		else
  			indicador.setResponsableCargarEjecutadoId(editarIndicadorForm.getResponsableCargarEjecutadoId());
  	}

  	private void setParametros(Indicador indicador, EditarIndicadorForm editarIndicadorForm, HttpServletRequest request)
  	{
  		indicador.setCaracteristica(editarIndicadorForm.getCaracteristica());

  		indicador.setCorte(editarIndicadorForm.getCorte());

  		indicador.setTipoCargaMedicion(editarIndicadorForm.getTipoCargaMedicion());

  		if (editarIndicadorForm.getTipoGuiaResultado().byteValue() == TipoIndicador.getTipoIndicadorResultado().byteValue())
  			indicador.setGuia(new Boolean(false));
  		else 
  			indicador.setGuia(new Boolean(true));

  		indicador.setValorInicialCero(editarIndicadorForm.getValorInicialCero());

  		if ((request.getParameter("parametroSuperiorValorFijo") != null) && (!request.getParameter("parametroSuperiorValorFijo").equals("")))
  			indicador.setParametroSuperiorValorFijo(editarIndicadorForm.getParametroSuperiorValorFijo());
  		else 
  			indicador.setParametroSuperiorValorFijo(null);

  		if ((request.getParameter("parametroSuperiorIndicadorId") != null) && (!request.getParameter("parametroSuperiorIndicadorId").equals("")))
  			indicador.setParametroSuperiorIndicadorId(editarIndicadorForm.getParametroSuperiorIndicadorId());
  		else 
  			indicador.setParametroSuperiorIndicadorId(null);

  		if ((request.getParameter("parametroInferiorValorFijo") != null) && (!request.getParameter("parametroInferiorValorFijo").equals("")))
  			indicador.setParametroInferiorValorFijo(editarIndicadorForm.getParametroInferiorValorFijo());
  		else 
  			indicador.setParametroInferiorValorFijo(null);

  		if ((request.getParameter("parametroInferiorIndicadorId") != null) && (!request.getParameter("parametroInferiorIndicadorId").equals("")))
  			indicador.setParametroInferiorIndicadorId(editarIndicadorForm.getParametroInferiorIndicadorId());
  		else 
  			indicador.setParametroInferiorIndicadorId(null);

  		if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue()) 
  		{
  			indicador.setParametroSuperiorValorFijo(null);
  			indicador.setParametroSuperiorIndicadorId(null);
  		} 
  		else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue()) 
  		{
  			indicador.setParametroInferiorValorFijo(null);
  			indicador.setParametroInferiorIndicadorId(null);
  		} 
  		else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue()) 
  		{
  			indicador.setParametroInferiorValorFijo(null);
  			indicador.setParametroInferiorIndicadorId(null);
  		} 
  		else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()) 
  		{
  			indicador.setParametroSuperiorValorFijo(null);
  			indicador.setParametroSuperiorIndicadorId(null);
  		}
  	}

  	private void setAlertas(Indicador indicador, EditarIndicadorForm editarIndicadorForm, HttpServletRequest request)
  	{
  		Byte valorZonaAmarilla = editarIndicadorForm.getAlertaTipoZonaAmarilla();
  		indicador.setAlertaTipoZonaAmarilla(valorZonaAmarilla);

  		Byte valorZonaVerde = editarIndicadorForm.getAlertaTipoZonaVerde();
  		indicador.setAlertaTipoZonaVerde(valorZonaVerde);

  		if ((request.getParameter("alertaMetaZonaVerde") != null) && (!request.getParameter("alertaMetaZonaVerde").equals("")))
  			indicador.setAlertaMetaZonaVerde(editarIndicadorForm.getAlertaMetaZonaVerde());
  		else 
  			indicador.setAlertaMetaZonaVerde(null);

  		if ((request.getParameter("alertaMetaZonaAmarilla") != null) && (!request.getParameter("alertaMetaZonaAmarilla").equals("")))
  			indicador.setAlertaMetaZonaAmarilla(editarIndicadorForm.getAlertaMetaZonaAmarilla());
  		else 
  			indicador.setAlertaMetaZonaAmarilla(null);

  		Long alertaIndicadorIdZonaVerde = editarIndicadorForm.getAlertaIndicadorIdZonaVerde();
  		if (valorZonaVerde.equals(editarIndicadorForm.getTipoAlertaZonaValorAbsolutoIndicador())) 
  		{
  			if ((alertaIndicadorIdZonaVerde != null) && (alertaIndicadorIdZonaVerde.longValue() != 0L))
  				indicador.setAlertaIndicadorIdZonaVerde(alertaIndicadorIdZonaVerde);
  			else 
  				indicador.setAlertaIndicadorIdZonaVerde(null);
  		}

  		Long alertaIndicadorIdZonaAmarilla = editarIndicadorForm.getAlertaIndicadorIdZonaAmarilla();

  		if (valorZonaAmarilla.equals(editarIndicadorForm.getTipoAlertaZonaValorAbsolutoIndicador())) 
  		{
  			if ((alertaIndicadorIdZonaAmarilla != null) && (alertaIndicadorIdZonaAmarilla.longValue() != 0L))
  				indicador.setAlertaIndicadorIdZonaAmarilla(alertaIndicadorIdZonaAmarilla);
  			else 
  				indicador.setAlertaIndicadorIdZonaAmarilla(null);
  		}

  		Byte valorVariableAmarillo = editarIndicadorForm.getAlertaValorVariableZonaAmarilla();
  		if (valorVariableAmarillo.equals(new Byte("1")))
  			indicador.setAlertaValorVariableZonaAmarilla(new Boolean(true));
  		else 
  			indicador.setAlertaValorVariableZonaAmarilla(new Boolean(false));

		Byte valorVariableVerde = editarIndicadorForm.getAlertaValorVariableZonaVerde();
		if (valorVariableVerde.equals(new Byte("1")))
			indicador.setAlertaValorVariableZonaVerde(new Boolean(true));
		else
			indicador.setAlertaValorVariableZonaVerde(new Boolean(false));
  	}

  	private SerieIndicador setSeriesTiempo(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  	{
  		SerieIndicador serieReal = null;

  		String seriesIndicador = editarIndicadorForm.getSeriesIndicador();
  		indicador.getSeriesIndicador().clear();
  		if ((seriesIndicador != null) && (!seriesIndicador.equals("")))
  		{
  			String[] series = StringUtil.split(editarIndicadorForm.getSeriesIndicador(), editarIndicadorForm.getSeparadorSeries());

  			for (int i = 0; i < series.length; i++) 
  			{
  				String serie = series[i];
  				if ((serie != null) && (!serie.equals(""))) 
  				{
  					SerieIndicador serieIndicador = new SerieIndicador();
  					serieIndicador.setIndicador(indicador);
  					serieIndicador.setPk(new SerieIndicadorPK());
  					serieIndicador.getPk().setSerieId(new Long(serie));
  					serieIndicador.getPk().setIndicadorId(indicador.getIndicadorId());
  					serieIndicador.setFormulas(new HashSet<Object>());
  					if (serieIndicador.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) 
  					{
  						serieIndicador.setNaturaleza(editarIndicadorForm.getNaturaleza());
  						serieReal = serieIndicador;
  					} 
  					else 
  						serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());

  					indicador.getSeriesIndicador().add(serieIndicador);
  				}
  			}
  		}

  		return serieReal;
  	}

  	private void setRelaciones(Indicador indicador, EditarIndicadorForm editarIndicadorForm) 
  	{
  		indicador.setUrl(editarIndicadorForm.getUrl());
  	}

  	private void setDefinicion(Indicador indicador, EditarIndicadorForm editarIndicadorForm, SerieIndicador serieReal, HttpServletRequest request)
  	{
  		indicador.setCodigoEnlace(null);
  		indicador.setEnlaceParcial(null);

  		indicador.getEscalaCualitativa().clear();

  		serieReal.getFormulas().clear();

  		if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaSimple()))
  			setDefinicionSimple(indicador, editarIndicadorForm);
  		else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaFormula()))
  			setDefinicionFormula(indicador, editarIndicadorForm, serieReal, request);
  		else if ((indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal())))
  			setDefinicionCualitativo(indicador, editarIndicadorForm);
  		else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaSumatoria()))
  			setDefinicionSumatoria(indicador, editarIndicadorForm, serieReal);
  		else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaPromedio()))
  			setDefinicionPromedio(indicador, editarIndicadorForm, serieReal);
  		else if (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaIndice()))
  			setDefinicionIndice(indicador, editarIndicadorForm, serieReal);
  	}

  	private void setDefinicionSimple(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  	{
  		if ((editarIndicadorForm.getCodigoEnlace() != null) && (!editarIndicadorForm.getCodigoEnlace().equals(""))) 
  			indicador.setCodigoEnlace(editarIndicadorForm.getCodigoEnlace());
  		
  		if ((editarIndicadorForm.getEnlaceParcial() != null) && (!editarIndicadorForm.getEnlaceParcial().equals("")))
  			indicador.setEnlaceParcial(editarIndicadorForm.getEnlaceParcial());
  	}

  	private void setDefinicionCualitativo(Indicador indicador, EditarIndicadorForm editarIndicadorForm)
  	{
  		String[] categorias = editarIndicadorForm.getEscalaCualitativa().split(editarIndicadorForm.getSeparadorCategorias());
  		String[] valoresCategoria = (String[])null;

  		for (int i = 0; i < categorias.length; i++)
  		{
  			if (categorias[i].length() > 0) 
  			{
  				valoresCategoria = categorias[i].split("\\]\\[");
  				CategoriaIndicador categoriaIndicador = new CategoriaIndicador();
  				categoriaIndicador.setOrden(Integer.valueOf(i + 1));
  				categoriaIndicador.setPk(new CategoriaIndicadorPK());
  				categoriaIndicador.getPk().setIndicadorId(editarIndicadorForm.getIndicadorId());
  				categoriaIndicador.getPk().setCategoriaId(new Long(valoresCategoria[0].substring("categoriaId:".length() + 1)));
  				indicador.getEscalaCualitativa().add(categoriaIndicador);
  			}
  		}
  	}

  	private void setDefinicionFormula(Indicador indicador, EditarIndicadorForm editarIndicadorForm, SerieIndicador serieReal, HttpServletRequest request)
  	{
		if ((editarIndicadorForm.getCodigoEnlaceFormula() != null) && (!editarIndicadorForm.getCodigoEnlaceFormula().equals(""))) 
  			indicador.setCodigoEnlace(editarIndicadorForm.getCodigoEnlaceFormula());
  		
  		if ((editarIndicadorForm.getEnlaceParcialFormula() != null) && (!editarIndicadorForm.getEnlaceParcialFormula().equals("")))
  			indicador.setEnlaceParcial(editarIndicadorForm.getEnlaceParcialFormula());
		indicador.setAsignarInventario(WebUtil.getValorInputCheck(request, "asignarInventario"));
    
  		Formula formulaIndicador = new Formula();
  		formulaIndicador.setInsumos(new HashSet<Object>());

  		formulaIndicador.setExpresion(IndicadorValidator.reemplazarCorrelativosFormula(editarIndicadorForm.getFormula(), editarIndicadorForm.getInsumosFormula()));

  		if ((editarIndicadorForm.getInsumosFormula() != null) && (!editarIndicadorForm.getInsumosFormula().equals(""))) 
  		{
  			String[] insumos = editarIndicadorForm.getInsumosFormula().split(editarIndicadorForm.getSeparadorIndicadores());
  			String[] strInsumo = (String[])null;
  			for (int i = 0; i < insumos.length; i++) 
  			{
  				if (insumos[i].length() > 0) 
  				{
  					strInsumo = insumos[i].split("\\]\\[");
  					InsumoFormula insumoFormula = new InsumoFormula();
  					insumoFormula.setPk(new InsumoFormulaPK());
  					insumoFormula.getPk().setPadreId(editarIndicadorForm.getIndicadorId());
  					insumoFormula.getPk().setSerieId(new Long("0"));
  					insumoFormula.getPk().setIndicadorId(new Long(strInsumo[1].substring("indicadorId:".length())));
  					insumoFormula.getPk().setInsumoSerieId(new Long(strInsumo[2].substring("serieId:".length())));
  					formulaIndicador.getInsumos().add(insumoFormula);
  				}
  			}
  		}
	  
  		serieReal.getFormulas().add(formulaIndicador);
  	}

  	private void setDefinicionSumatoria(Indicador indicador, EditarIndicadorForm editarIndicadorForm, SerieIndicador serieReal) 
  	{
  		Formula formulaIndicador = new Formula();
  		formulaIndicador.setInsumos(new HashSet<Object>());

  		formulaIndicador.setExpresion("");

  		if ((editarIndicadorForm.getIndicadorSumatoriaId() != null) && (!editarIndicadorForm.getIndicadorSumatoriaId().equals(""))) 
  		{
  			String[] valoresInsumo = editarIndicadorForm.getIndicadorSumatoriaId().split("\\]\\[");
  			InsumoFormula insumoFormula = new InsumoFormula();
  			insumoFormula.setPk(new InsumoFormulaPK());
  			insumoFormula.getPk().setPadreId(editarIndicadorForm.getIndicadorId());
  			insumoFormula.getPk().setSerieId(new Long("0"));
  			insumoFormula.getPk().setIndicadorId(new Long(valoresInsumo[0].substring("[indicadorId:".length())));
  			insumoFormula.getPk().setInsumoSerieId(new Long(valoresInsumo[1].substring("serieId:".length(), valoresInsumo[1].length() - 1)));
  			formulaIndicador.getInsumos().add(insumoFormula);
  		}
  		
  		serieReal.getFormulas().add(formulaIndicador);
  	}

  	private void setDefinicionPromedio(Indicador indicador, EditarIndicadorForm editarIndicadorForm, SerieIndicador serieReal) 
  	{
  		Formula formulaIndicador = new Formula();
  		formulaIndicador.setInsumos(new HashSet<Object>());

  		formulaIndicador.setExpresion("");

  		if ((editarIndicadorForm.getIndicadorPromedioId() != null) && (!editarIndicadorForm.getIndicadorPromedioId().equals(""))) 
  		{
  			String[] valoresInsumo = editarIndicadorForm.getIndicadorPromedioId().split("\\]\\[");
  			InsumoFormula insumoFormula = new InsumoFormula();
  			insumoFormula.setPk(new InsumoFormulaPK());
  			insumoFormula.getPk().setPadreId(editarIndicadorForm.getIndicadorId());
  			insumoFormula.getPk().setSerieId(new Long("0"));
  			insumoFormula.getPk().setIndicadorId(new Long(valoresInsumo[0].substring("[indicadorId:".length())));
  			insumoFormula.getPk().setInsumoSerieId(new Long(valoresInsumo[1].substring("serieId:".length(), valoresInsumo[1].length() - 1)));
  			formulaIndicador.getInsumos().add(insumoFormula);
  		}
    
  		serieReal.getFormulas().add(formulaIndicador);
  	}

  	private void setDefinicionIndice(Indicador indicador, EditarIndicadorForm editarIndicadorForm, SerieIndicador serieReal) 
  	{
  		Formula formulaIndicador = new Formula();
  		formulaIndicador.setInsumos(new HashSet<Object>());

  		formulaIndicador.setExpresion(editarIndicadorForm.getIndicadorIndiceTipoVariacion() + "\\" + editarIndicadorForm.getIndicadorIndiceTipoComparacion());

  		if ((editarIndicadorForm.getIndicadorIndiceId() != null) && (!editarIndicadorForm.getIndicadorIndiceId().equals(""))) 
  		{
  			String[] valoresInsumo = editarIndicadorForm.getIndicadorIndiceId().split("\\]\\[");
  			
  			InsumoFormula insumoFormula = new InsumoFormula();
  			insumoFormula.setPk(new InsumoFormulaPK());
  			insumoFormula.getPk().setPadreId(editarIndicadorForm.getIndicadorId());
  			insumoFormula.getPk().setSerieId(new Long("0"));
  			insumoFormula.getPk().setIndicadorId(new Long(valoresInsumo[0].substring("[indicadorId:".length())));
  			insumoFormula.getPk().setInsumoSerieId(new Long(valoresInsumo[1].substring("serieId:".length(), valoresInsumo[1].length() - 1)));
  			
  			formulaIndicador.getInsumos().add(insumoFormula);
  		}
    
  		serieReal.getFormulas().add(formulaIndicador);
  	}
  	
  	public void asignarInventario(Indicador indicador, StrategosIndicadoresService strategosIndicadoresService, StrategosIndicadorAsignarInventarioService strategosIndicadoresInventarioService, Usuario usuario){
  		
  		List<InsumoFormula> insumos = strategosIndicadoresService.getInsumosFormula(indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
  		
  		for (Iterator iter = insumos.iterator(); iter.hasNext(); ) 
		{
  			InsumoFormula insumo = (InsumoFormula)iter.next();
  			IndicadorAsignarInventario indicadorInsumo = strategosIndicadoresInventarioService.getIndicadorInventario(indicador.getIndicadorId(), insumo.getPk().getIndicadorId());
  			
  			if(indicadorInsumo == null){
  			  IndicadorAsignarInventario indicadorInventario = new IndicadorAsignarInventario();
  		      indicadorInventario.setIndicadorId(indicador.getIndicadorId());
  		      indicadorInventario.setIndicadorInsumoId(insumo.getPk().getIndicadorId());
  		      strategosIndicadoresInventarioService.saveIndicadorInventario(indicadorInventario, usuario);
  			}
		}
  	}
}

