package com.visiongc.app.strategos.web.struts.planes.valoresiniciales.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.planes.model.util.ValorInicialIndicador;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.GestionarPerspectivasForm;
import com.visiongc.app.strategos.web.struts.planes.valoresiniciales.forms.EditarValoresInicialesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.Calendar;
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

public final class EditarValoresInicialesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarValoresInicialesForm editarValoresInicialesForm = (EditarValoresInicialesForm)form;

		ActionMessages messages = getMessages(request);

		String perspectivaId = (String)request.getSession().getAttribute("perspectivaId");
		boolean mostrarUnidadMedida = mapping.getPath().toLowerCase().indexOf("mostrarUnidadMedida") > -1;
		boolean mostrarCodigoEnlace = mapping.getPath().toLowerCase().indexOf("mostrarCodigoEnlace") > -1;
		boolean editarValoresIniciales = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VALOR_INICIAL_CARGAR");

		if (mostrarUnidadMedida) 
			editarValoresInicialesForm.setMostrarUnidadMedida(new Boolean(request.getParameter("mostrarUnidadMedida")).booleanValue());
		if (mostrarCodigoEnlace) 
			editarValoresInicialesForm.setMostrarCodigoEnlace(new Boolean(request.getParameter("mostrarCodigoEnlace")).booleanValue());

		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();

		Perspectiva perspectiva = (Perspectiva)strategosMetasService.load(Perspectiva.class, new Long(perspectivaId));

		if (!editarValoresInicialesForm.isEstablecerMetasSoloIndicadoresSeleccionados())
		{
			List<?> listaIndicadores = new ArrayList<Object>();

			editarValoresInicialesForm.getValoresInicialesIndicadores().clear();

			listaIndicadores = getIndicadores(perspectiva, request);

			for (Iterator<?> iterador = listaIndicadores.iterator(); iterador.hasNext(); )
			{
				Indicador indicador = (Indicador)iterador.next();
				
				ValorInicialIndicador valorInicialIndicador = new ValorInicialIndicador();
				valorInicialIndicador.setIndicador(indicador);
				
				setPeriodosIndicador(valorInicialIndicador);

				valorInicialIndicador.setValorInicial(strategosMetasService.getValorInicial(valorInicialIndicador.getIndicador().getIndicadorId(), editarValoresInicialesForm.getPlanId()));
				valorInicialIndicador.setProteger(!editarValoresIniciales);
				
				setValidacionPeriodoAno(valorInicialIndicador);

				if (!editarValoresInicialesForm.getVisualizarIndicadoresCompuestos().booleanValue()) 
				{
					if (valorInicialIndicador.getIndicador().getNaturaleza().byteValue() != Naturaleza.getNaturalezaSimple().byteValue())
						continue;
					editarValoresInicialesForm.getValoresInicialesIndicadores().add(valorInicialIndicador);
				}
				else
					editarValoresInicialesForm.getValoresInicialesIndicadores().add(valorInicialIndicador);
			}
		}

		if (editarValoresInicialesForm.isEstablecerMetasSoloIndicadoresSeleccionados())
		{
			for (Iterator<ValorInicialIndicador> iterador = editarValoresInicialesForm.getValoresInicialesIndicadores().iterator(); iterador.hasNext(); )
			{
				ValorInicialIndicador valorInicialIndicador = (ValorInicialIndicador)iterador.next();
				
				setPeriodosIndicador(valorInicialIndicador);

				valorInicialIndicador.setValorInicial(strategosMetasService.getValorInicial(valorInicialIndicador.getIndicador().getIndicadorId(), editarValoresInicialesForm.getPlanId()));
				valorInicialIndicador.setProteger(!editarValoresIniciales);
				
				setValidacionPeriodoAno(valorInicialIndicador);
			}
		}

		if (editarValoresInicialesForm.getValoresInicialesIndicadores().size() == 0)
		{
			request.getSession().removeAttribute("editarValoresInicialesForm");

			forward = "noencontrado";

			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarvaloresiniciales.noindicadores"));
		}

		editarValoresInicialesForm.setBloquear(!editarValoresIniciales);
		
		strategosMetasService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}

	private void setPeriodosIndicador(ValorInicialIndicador valorInicialIndicador)
	{
		int anoActual = Calendar.getInstance().get(1);
		int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(valorInicialIndicador.getIndicador().getFrecuencia().byteValue(), anoActual);
		valorInicialIndicador.setNumeroPeriodos(new Integer(PeriodoUtil.getListaNumeros(new Integer(1), new Integer(numeroMaximoPeriodos)).size()));
	}

	private void setValidacionPeriodoAno(ValorInicialIndicador valorInicialIndicador)
	{
		if ((valorInicialIndicador.getValorInicial().getMetaId().getPeriodo() != null) && (valorInicialIndicador.getValorInicial().getMetaId().getPeriodo().byteValue() == 0)) 
			valorInicialIndicador.getValorInicial().getMetaId().setPeriodo(null);
		if ((valorInicialIndicador.getValorInicial().getMetaId().getAno() != null) && (valorInicialIndicador.getValorInicial().getMetaId().getAno().byteValue() == 0))
			valorInicialIndicador.getValorInicial().getMetaId().setAno(null);
	}

	private List<?> getIndicadores(Perspectiva perspectiva, HttpServletRequest request)
	{
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)request.getSession().getAttribute("gestionarPlanForm");
		GestionarPerspectivasForm gestionarPerspectivasForm = (GestionarPerspectivasForm)request.getSession().getAttribute("gestionarPerspectivasForm");

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Map<String, String> filtros = new HashMap<String, String>();

		if (perspectiva.getPadreId() != null)
			filtros.put("perspectivaId", gestionarPlanForm.getPerspectivaId().toString());
		else if (perspectiva.getPadreId() == null)
		{
			if (gestionarPerspectivasForm.getVerIndicadoresLogroPlan())
				filtros.put("indicadoresLogroPlanId", gestionarPlanForm.getPlanId().toString());
			else 
				filtros.put("planId", gestionarPlanForm.getPlanId().toString());
		}

		PaginaLista paginaIndicadores = null;

		if (gestionarPerspectivasForm.getVerIndicadoresLogroPlan())
			paginaIndicadores = strategosIndicadoresService.getIndicadoresLogroPlan(0, 0, null, null, true, filtros);
		else 
			paginaIndicadores = strategosIndicadoresService.getIndicadores(0, 0, null, null, true, filtros, null, null, false);

		if (perspectiva.getPadreId() != null)
		{
			if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
			{
				int numeroVeces = paginaIndicadores.getLista().size();
				for (int k = 1; k <= numeroVeces; k++) 
				{
					for (Iterator<?> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
					{
						boolean eliminarIndicador = true;
						Indicador indicador = (Indicador)i.next();
						for (Iterator<?> p = perspectiva.getHijos().iterator(); p.hasNext(); ) 
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
			else
			{
				int numeroVeces = paginaIndicadores.getLista().size();
				for (int k = 1; k <= numeroVeces; k++) 
				{
					for (Iterator<?> i = paginaIndicadores.getLista().iterator(); i.hasNext(); ) 
					{
						boolean eliminarIndicador = false;
						Indicador indicador = (Indicador)i.next();
						for (Iterator<?> p = perspectiva.getHijos().iterator(); p.hasNext(); ) 
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
		else if (!gestionarPerspectivasForm.getVerIndicadoresLogroPlan()) 
			paginaIndicadores.getLista().clear();

		return paginaIndicadores.getLista();
	}
}