package com.visiongc.app.strategos.web.struts.planes.metas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.MetasIndicador;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.responsables.model.util.TipoResponsabilidad;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class EditarMetasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarMetasForm editarMetasForm = (EditarMetasForm)form;

		ActionMessages messages = getMessages(request);

		boolean mostrarUnidadMedida = mapping.getPath().toLowerCase().indexOf("mostrarUnidadMedida") > -1;
		boolean mostrarCodigoEnlace = mapping.getPath().toLowerCase().indexOf("mostrarCodigoEnlace") > -1;
    
		if (mostrarUnidadMedida) 
			editarMetasForm.setMostrarUnidadMedida(new Boolean(request.getParameter("mostrarUnidadMedida")).booleanValue());
		if (mostrarCodigoEnlace) 
			editarMetasForm.setMostrarCodigoEnlace(new Boolean(request.getParameter("mostrarCodigoEnlace")).booleanValue());
		boolean editarValores = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VALOR_META_CARGAR");
		boolean hayResponsabilidad = false;
		boolean usuarioEsResponsable = false;
		boolean mensajeResponsableAgregado = false;

		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();

		Perspectiva perspectiva = (Perspectiva)strategosMetasService.load(Perspectiva.class, editarMetasForm.getPerspectivaId());

		if (!editarMetasForm.isEstablecerMetasSoloIndicadoresSeleccionados())
		{
			List<Indicador> listaIndicadores = new ArrayList<Indicador>();
			
			editarMetasForm.getMetasIndicadores().clear();

			listaIndicadores = getIndicadores(editarMetasForm, perspectiva, request);

			for (Iterator<?> iterador = listaIndicadores.iterator(); iterador.hasNext(); )
			{
				Indicador indicador = (Indicador)iterador.next();

				hayResponsabilidad = false;
				if (editarValores)
				{
					if (new com.visiongc.app.strategos.web.struts.responsables.actions.SeleccionarResponsablesAction().hayResposabilidad(indicador, TipoResponsabilidad.getTipoResponsableCargarMeta(), getUsuarioConectado(request)))
					{
						hayResponsabilidad = true;
						usuarioEsResponsable = new com.visiongc.app.strategos.web.struts.responsables.actions.SeleccionarResponsablesAction().usuarioEsResponsable(indicador, TipoResponsabilidad.getTipoResponsableCargarMeta(), getUsuarioConectado(request));
						if (!usuarioEsResponsable && !mensajeResponsableAgregado)
						{
							mensajeResponsableAgregado = true;
				    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarmetas.mensaje.indicadores.noresponsable"));
				    		saveMessages(request, messages);
						}
					}
				}
				
				MetasIndicador metasIndicador = new MetasIndicador();
				metasIndicador.setIndicador(indicador);

				metasIndicador.getIndicador().setTipoCargaMeta(getTipoCargaMeta(strategosMetasService, editarMetasForm.getPlanId(), metasIndicador.getIndicador().getIndicadorId()));

				metasIndicador.getIndicador().setEstaBloqueado(isBloqueado(metasIndicador, perspectiva, request, strategosMetasService));
				if (!editarValores || hayResponsabilidad)
					metasIndicador.getIndicador().setEstaBloqueado(true);

				metasIndicador.setMetasAnualesParciales(strategosMetasService.getMetasAnualesParciales(metasIndicador.getIndicador().getIndicadorId(), editarMetasForm.getPlanId(), metasIndicador.getIndicador().getFrecuencia(), editarMetasForm.getAnoDesde(), editarMetasForm.getAnoHasta(), false));

				editarMetasForm.getMetasIndicadores().add(metasIndicador);
			}
		}
		else if (editarMetasForm.isEstablecerMetasSoloIndicadoresSeleccionados())
		{
			for (Iterator<MetasIndicador> iterador = editarMetasForm.getMetasIndicadores().iterator(); iterador.hasNext(); )
			{
				MetasIndicador metasIndicador = (MetasIndicador)iterador.next();

				hayResponsabilidad = false;
				if (editarValores)
				{
					if (new com.visiongc.app.strategos.web.struts.responsables.actions.SeleccionarResponsablesAction().hayResposabilidad(metasIndicador.getIndicador(), TipoResponsabilidad.getTipoResponsableCargarMeta(), getUsuarioConectado(request)))
					{
						hayResponsabilidad = true;
						usuarioEsResponsable = new com.visiongc.app.strategos.web.struts.responsables.actions.SeleccionarResponsablesAction().usuarioEsResponsable(metasIndicador.getIndicador(), TipoResponsabilidad.getTipoResponsableCargarMeta(), getUsuarioConectado(request));
						if (!usuarioEsResponsable && !mensajeResponsableAgregado)
						{
							mensajeResponsableAgregado = true;
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarmetas.mensaje.indicadores.noresponsable"));
				    		saveMessages(request, messages);
						}
					}
				}
				
				metasIndicador.getIndicador().setTipoCargaMeta(getTipoCargaMeta(strategosMetasService, editarMetasForm.getPlanId(), metasIndicador.getIndicador().getIndicadorId()));

				metasIndicador.getIndicador().setEstaBloqueado(isBloqueado(metasIndicador, perspectiva, request, strategosMetasService));
				if (!editarValores || hayResponsabilidad)
					metasIndicador.getIndicador().setEstaBloqueado(true);
				
				metasIndicador.setMetasAnualesParciales(strategosMetasService.getMetasAnualesParciales(metasIndicador.getIndicador().getIndicadorId(), editarMetasForm.getPlanId(), metasIndicador.getIndicador().getFrecuencia(), editarMetasForm.getAnoDesde(), editarMetasForm.getAnoHasta(), false));
			}
		}

		if (editarMetasForm.getMetasIndicadores().size() == 0)
		{
			request.getSession().removeAttribute("editarMetasForm");
			
			forward = "noencontrado";

			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarmetas.noindicadores"));
		}

		setListaAnos(editarMetasForm);

		setAnchoMatriz(editarMetasForm);
		
		editarMetasForm.setBloquear(!editarValores);

		strategosMetasService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
		return mapping.findForward(forward);
	}

	private Byte getTipoCargaMeta(StrategosMetasService strategosMetasService, Long planId, Long indicadorId)
	{
		Byte tipoCargaMeta = null;

		IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
		indicadorPlanPk.setIndicadorId(indicadorId);
		indicadorPlanPk.setPlanId(planId);
		IndicadorPlan indicadorPlan = (IndicadorPlan)strategosMetasService.load(IndicadorPlan.class, indicadorPlanPk);
		if (indicadorPlan != null) 
			tipoCargaMeta = indicadorPlan.getTipoMedicion();
		
		return tipoCargaMeta;
	}

	private void setListaAnos(EditarMetasForm editarMetasForm)
	{
		int anoDesde = editarMetasForm.getAnoDesde().intValue();
		int anoHasta = editarMetasForm.getAnoHasta().intValue() + 1;

		editarMetasForm.getListaAnos().clear();

		for (int i = anoDesde; i < anoHasta; i++)
			editarMetasForm.getListaAnos().add(new Integer(i));
	}

	private void setAnchoMatriz(EditarMetasForm editarMetasForm)
	{
		int anchoMatriz = 700;
		
		anchoMatriz += 150 * editarMetasForm.getListaAnos().size();
		editarMetasForm.setAnchoMatriz(Integer.toString(anchoMatriz) + "px");
	}

	private List<Indicador> getIndicadores(EditarMetasForm editarMetasForm, Perspectiva perspectiva, HttpServletRequest request)
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Map<String, String> filtros = new HashMap<String, String>();

		if (perspectiva.getPadreId() != null)
			filtros.put("perspectivaId", editarMetasForm.getPerspectivaId().toString());
		else if (perspectiva.getPadreId() == null)
		{
			if (editarMetasForm.getVerIndicadoresLogroPlan().booleanValue())
				filtros.put("indicadoresLogroPlanId", editarMetasForm.getPlanId().toString());
			else 
				filtros.put("planId", editarMetasForm.getPlanId().toString());
		}

		PaginaLista paginaIndicadores = null;

		if (editarMetasForm.getVerIndicadoresLogroPlan().booleanValue())
			paginaIndicadores = strategosIndicadoresService.getIndicadoresLogroPlan(0, 0, null, null, true, filtros);
		else 
			paginaIndicadores = strategosIndicadoresService.getIndicadores(0, 0, null, null, true, filtros, null, null, true);

		if (perspectiva.getPadreId() != null)
		{
			if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
			{
				if (perspectiva.getHijos().size() > 0)
				{
					int numeroVeces = paginaIndicadores.getLista().size();
					for (int k = 1; k <= numeroVeces; k++) 
					{
						for (Iterator<Indicador> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
						{
							boolean eliminarIndicador = true;
							Indicador indicador = (Indicador)i.next();
							for (Iterator<Perspectiva> p = perspectiva.getHijos().iterator(); p.hasNext(); ) 
							{
								Perspectiva perspectivaHija = (Perspectiva)p.next();
								if ((indicador.getIndicadorId().longValue() == perspectivaHija.getNlAnoIndicadorId().longValue()) || (indicador.getIndicadorId().longValue() == perspectivaHija.getNlParIndicadorId().longValue())) 
								{
									eliminarIndicador = false;
									break;
								}
							}
							if (eliminarIndicador) 
							{
								paginaIndicadores.getLista().remove(indicador);
								break;
							}
						}
					}
				}
			}
			else
			{
				if (perspectiva.getHijos().size() > 0)
				{
					int numeroVeces = paginaIndicadores.getLista().size();
					for (int k = 1; k <= numeroVeces; k++) 
					{
						for (Iterator<Indicador> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
						{
							boolean eliminarIndicador = false;
							Indicador indicador = (Indicador)i.next();
							for (Iterator<Perspectiva> p = perspectiva.getHijos().iterator(); p.hasNext(); ) 
							{
								Perspectiva perspectivaHija = (Perspectiva)p.next();
								if ((indicador.getIndicadorId().longValue() == perspectivaHija.getNlAnoIndicadorId().longValue()) || (indicador.getIndicadorId().longValue() == perspectivaHija.getNlParIndicadorId().longValue())) 
								{
									eliminarIndicador = true;
									break;
								}
							}
							if (eliminarIndicador) 
							{
								paginaIndicadores.getLista().remove(indicador);
								break;
							}
						}					
					}
				}
			}
		}
		else if (!editarMetasForm.getVerIndicadoresLogroPlan().booleanValue()) 
			paginaIndicadores.getLista().clear();

		return paginaIndicadores.getLista();
	}

	public Boolean isBloqueado(MetasIndicador metasIndicador, Perspectiva perspectiva, HttpServletRequest request, StrategosMetasService strategosMetasService)
	{
		Boolean indicadorBloqueado = new Boolean(false);

		indicadorBloqueado = new Boolean(!strategosMetasService.lockForUpdate(request.getSession().getId(), metasIndicador.getIndicador().getIndicadorId()));
		if (!indicadorBloqueado.booleanValue())
			agregarLockPoolLocksUsoEdicion(request, null, metasIndicador.getIndicador().getIndicadorId());
		else 
			return new Boolean(true);

		if (metasIndicador.getIndicador().getTipoFuncion().byteValue() == 3) 
			return new Boolean(true);

		if (perspectiva.getPadreId() == null) 
			return new Boolean(true);

		if (perspectiva.getPadreId() != null)
		{
			if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico())) 
				return new Boolean(true);
		}

		return indicadorBloqueado;
	}
}