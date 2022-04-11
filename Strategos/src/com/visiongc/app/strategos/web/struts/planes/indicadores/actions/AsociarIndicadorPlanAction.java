package com.visiongc.app.strategos.web.struts.planes.indicadores.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.MetaPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.indicadores.forms.SeleccionarIndicadoresForm;
import com.visiongc.app.strategos.web.struts.planes.indicadores.forms.ImportarProgramadoForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import com.visiongc.framework.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AsociarIndicadorPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		
		String indicadores = request.getParameter("indicadorId");
	    int respuesta = 10000;

		Long planId = new Long(request.getParameter("planId"));
		Long perspectivaId = new Long(request.getParameter("perspectivaId"));
		Boolean importarMetas = request.getParameter("importarMetas") != null ? Boolean.parseBoolean(request.getParameter("importarMetas")) : null;
		Boolean actualizarForma = request.getParameter("actualizarForma") != null ? Boolean.parseBoolean((String)request.getParameter("actualizarForma")) : false;
    	String funcion = request.getParameter("funcion");
		
		Integer anoInicial = new Integer(0000);
		Integer anoFinal = new Integer(9999);
		StrategosPerspectivasService strategosPerspectivaService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
		Perspectiva perspectiva = (Perspectiva)strategosPerspectivaService.load(Perspectiva.class, perspectivaId);

		String ajaxResponse = null;
		Integer anoIni = null;
		Integer anoFin = null;
		boolean respuestaBol = false;
		boolean continuar = true;
		
		if (indicadores != null)
		{
			String [] ind = indicadores.split(SeleccionarIndicadoresForm.SEPARADOR_INDICADORES);
			for (int index = 0; index < ind.length; index++)
			{
				Long indicadorId = new Long(ind[index]);
			    if (funcion != null) 
			    {
			    	if (funcion.equals("chequear")) 
			    	{
						IndicadorPerspectiva indicadorPerspectiva = new IndicadorPerspectiva();
						indicadorPerspectiva.setPk(new IndicadorPerspectivaPK());
						indicadorPerspectiva.getPk().setIndicadorId(indicadorId);
						indicadorPerspectiva.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
						IndicadorPerspectiva indicadorAsociadoPerspectiva = (IndicadorPerspectiva)strategosPerspectivaService.load(IndicadorPerspectiva.class, indicadorPerspectiva.getPk());

						boolean hayProgramado = false;
						boolean indicadorAsociado = true;
						if (indicadorAsociadoPerspectiva == null)
						{
							indicadorAsociado = false;
				    		hayProgramado = (getMediciones(indicadorId, anoInicial, anoFinal).size() > 0);
						}
						
						ajaxResponse = (hayProgramado ? "true" : "false") + "|" + indicadores + "|" + planId + "|" + perspectivaId + "|" + (indicadorAsociado ? "true" : "false");
						
						continuar = false;
						if (hayProgramado)
							break;
			    	}
			    	else if (funcion.equals("getAnos"))
			    	{
						List<MetaAnualParciales> metas = cargarMetas(indicadorId, planId, anoInicial, anoFinal);
			    		if (metas != null)
			    		{
			    			for (Iterator<?> i = metas.iterator(); i.hasNext(); ) 
			    			{
			    				MetaAnualParciales metasIndicador = (MetaAnualParciales)i.next();
			    				if (anoIni == null || anoIni.intValue() > metasIndicador.getMetaAnual().getMetaId().getAno().intValue())
			    					anoIni = metasIndicador.getMetaAnual().getMetaId().getAno();
			    				if (anoFin == null || anoFin.intValue() < metasIndicador.getMetaAnual().getMetaId().getAno().intValue())
			    					anoFin = metasIndicador.getMetaAnual().getMetaId().getAno();
			    			}
			    		}
						continuar = false;
			    		break;
			    	}
			    	else if (funcion.equals("chequearProgramado")) 
			    	{
			    		boolean hayProgramado = (getMediciones(indicadorId, anoInicial, anoFinal).size() > 0);
						
			    		ajaxResponse = (hayProgramado ? "true" : "false") + "|" + indicadorId + "|" + planId + "|" + perspectivaId + "|";
						continuar = false;
			    		break;
			    	}
			    	else if (funcion.equals("importarAno")) 
			    	{
			    		Integer ano = request.getParameter("ano") != null ? Integer.parseInt(request.getParameter("ano")) : null;
			    		if (ano != 0)
			    		{
			    			anoInicial = ano;
			    			anoFinal = ano;
			    		}

			    		respuesta = importarProgramadoXMetas(indicadorId, planId, anoInicial, anoFinal, getUsuarioConectado(request));
						continuar = false;
			    		if (respuesta != 10000)
			    			break;
			    	}
			    }
				if (continuar)
				{
					if (importarMetas)
						respuesta = importarProgramadoXMetas(indicadorId, planId, anoInicial, anoFinal, getUsuarioConectado(request));
					if (respuesta == 10000)
					{
						respuestaBol = strategosPerspectivaService.asociarIndicador(planId, perspectiva, indicadorId, new Boolean(true), getUsuarioConectado(request));
						if (!respuestaBol)
							break;
					}
				}
				
				if (respuesta != 10000)
					break;
			}			
		}

	    if (funcion != null) 
	    {
	    	if (funcion.equals("chequear") && respuesta == 10000) 
	    	{
	    		strategosPerspectivaService.close();
	    		request.setAttribute("ajaxResponse", ajaxResponse);
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	else if (funcion.equals("getAnos"))
	    	{
	    		strategosPerspectivaService.close();
	    		String forward = mapping.getParameter();

	    		ImportarProgramadoForm importarProgramadoForm = (ImportarProgramadoForm)form;
	    		importarProgramadoForm.setGrupoAnos(PeriodoUtil.getListaNumeros(anoIni, anoFin));
	    		if (importarProgramadoForm.getGrupoAnos().size() > 0)
	    			importarProgramadoForm.setAnoSeleccion(importarProgramadoForm.getGrupoAnos().get(0).getValor());
	    		
	    		return mapping.findForward(forward);
	    	}
	    	else if (funcion.equals("chequearProgramado")) 
	    	{
	    		strategosPerspectivaService.close();
	    		request.setAttribute("ajaxResponse", ajaxResponse);
	    		return mapping.findForward("ajaxResponse");
	    	}
	    	else if (funcion.equals("importarAno")) 
	    	{
	    		strategosPerspectivaService.close();
	    		if (respuesta == 10000)
    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.importarprogramado.mediciones.sucessful"));
	    		else if (respuesta == 10003)
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmetas.mensaje.guardarmetas.relacionados"));		
	    		saveMessages(request, messages);
	    	    
	    		return getForwardBack(request, 1, true);
	    	}
	    }

	    if (respuesta == 10000 && !respuestaBol)
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.asociarindicadorplan.error"));
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmetas.mensaje.guardarmetas.relacionados"));		
		else if (respuesta != 10000)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.importarprogramado.mediciones.error"));
		else if (respuesta == 10000 && respuestaBol)
		{
			if (request.getSession().getAttribute("AsociarIndicador") == null)
				request.getSession().setAttribute("AsociarIndicador", "true");
		}

		strategosPerspectivaService.close();
		
		saveMessages(request, messages);

		return getForwardBack(request, 1, true);
	}
	
	private int importarProgramadoXMetas(Long indicadorId, Long planId, Integer anoInicial, Integer anoFinal, Usuario usuario)
	{
		int respuesta = 10000;
		
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();

		List<MetaAnualParciales> metas = cargarMetas(indicadorId, planId, anoInicial, anoFinal);
		List<Meta> metasEditadas = new ArrayList<Meta>();
		Integer ano = null;
		Double valorMetaAnual = null;
		if (metas != null)
		{
			for (Iterator<?> i = metas.iterator(); i.hasNext(); ) 
			{
				MetaAnualParciales metasIndicador = (MetaAnualParciales)i.next();
				
				// Se Agrega la Meta Anual
				//metasEditadas.add(metasIndicador.getMetaAnual());
				for (Iterator<?> iter = metasIndicador.getMetasParciales().iterator(); iter.hasNext(); )
				{
					Meta meta = (Meta)iter.next();

					// Se Agrega la Meta Anual
					if (ano == null || ano.intValue() != meta.getMetaId().getAno().intValue())
					{
						if (ano != null)
						{
							Meta metaAnualEditada = new Meta(new MetaPK(meta.getMetaId().getPlanId(), meta.getMetaId().getIndicadorId(), SerieTiempo.getSerieMeta().getSerieId(), TipoMeta.getTipoMetaAnual(), ano, 0), valorMetaAnual, new Boolean(false));
							metasEditadas.add(metaAnualEditada);
						}
						ano = meta.getMetaId().getAno();
						valorMetaAnual = null;
					}
					
					if (meta.getTipoCargaMeta().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
					{
						if (meta.getValor() != null)
						{
							if (valorMetaAnual != null)
								valorMetaAnual = valorMetaAnual + meta.getValor();
							else
								valorMetaAnual = meta.getValor();
						}
					}
					else
						valorMetaAnual = meta.getValor();
						
					// Se Agrega la Meta Parcial
					Meta metaEditada = new Meta(new MetaPK(meta.getMetaId().getPlanId(), meta.getMetaId().getIndicadorId(), meta.getMetaId().getSerieId(), meta.getMetaId().getTipo(), meta.getMetaId().getAno(), meta.getMetaId().getPeriodo()), meta.getValor(), new Boolean(false));
					metaEditada.setTipoCargaMeta(meta.getTipoCargaMeta());
					metasEditadas.add(metaEditada);
				}
			}
		}
		
		if (ano != null)
		{
			Meta metaAnualEditada = new Meta(new MetaPK(planId, indicadorId, SerieTiempo.getSerieMeta().getSerieId(), TipoMeta.getTipoMetaAnual(), ano, 0), valorMetaAnual, new Boolean(false));
			metasEditadas.add(metaAnualEditada);
		}
		
		if (metasEditadas.size() > 0) 
			respuesta = strategosMetasService.saveMetas(metasEditadas, usuario);
		strategosMetasService.close();
		
		return respuesta;
	}
	
	private List<Medicion> getMediciones(Long indicadorId, Integer anoInicial, Integer anoFinal)
	{
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieProgramado().getSerieId(), anoInicial, anoFinal, new Integer(000), new Integer(999));
		strategosMedicionesService.close();
		
		return medicionesProgramada;
	}
	
	private List<MetaAnualParciales> cargarMetas(Long indicadorId, Long planId, Integer anoInicial, Integer anoFinal)
	{
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
		strategosIndicadoresService.close();

		List<Medicion> medicionesProgramada = getMediciones(indicadorId, anoInicial, anoFinal);
		Integer ano = null;
		Integer numeroMaximoPorPeriodo = null;

		List<MetaAnualParciales> metas = null;
		Meta metaAnual = null;
		Meta metaParcial = null;
		MetaPK metaPk = null;
		List<Meta> metasParciales = null;
		for (Iterator<Medicion> indexMedicion = medicionesProgramada.iterator(); indexMedicion.hasNext(); ) 
		{
			Medicion medicion = (Medicion)indexMedicion.next();
			if (ano == null || ano.intValue() != medicion.getMedicionId().getAno().intValue())
			{
				if (metasParciales != null && metasParciales.size() > 0)
				{
					if (metas == null)
						metas = new ArrayList<MetaAnualParciales>();
					
			  		metas.add(addMetas(indicador, metaAnual, metasParciales));
				}
				ano = medicion.getMedicionId().getAno();
				numeroMaximoPorPeriodo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
				metasParciales = new ArrayList<Meta>();
				metaAnual = null;
			}
			
			if (ano != null && numeroMaximoPorPeriodo != null)
			{
				if (metaAnual == null)
				{
					metaAnual = new Meta();
					metaPk = new MetaPK();
					metaPk.setIndicadorId(indicadorId);
					metaPk.setPlanId(planId);
					metaPk.setSerieId(SerieTiempo.getSerieMetaId());
					metaPk.setTipo(TipoMeta.getTipoMetaAnual());
					metaPk.setPeriodo(new Integer(0));
					metaPk.setAno(new Integer(ano));
					metaAnual.setMetaId(metaPk);
					metaAnual.setProtegido(new Boolean(false));
					metaAnual.setTipoCargaMeta(indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue() ? TipoMedicion.getTipoMedicionEnPeriodo() : TipoMedicion.getTipoMedicionAlPeriodo());
				}
				
  				metaParcial = new Meta();
  				metaPk = new MetaPK();
  				metaPk.setIndicadorId(indicadorId);
  				metaPk.setPlanId(planId);
  				metaPk.setSerieId(SerieTiempo.getSerieMetaId());
  				metaPk.setTipo(TipoMeta.getTipoMetaParcial());
  				metaPk.setAno(metaAnual.getMetaId().getAno());
  				metaPk.setPeriodo(new Integer(medicion.getMedicionId().getPeriodo()));
  				metaParcial.setMetaId(metaPk);
  				metaParcial.setProtegido(new Boolean(false));
  				metaParcial.setTipoCargaMeta(indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue() ? TipoMedicion.getTipoMedicionEnPeriodo() : TipoMedicion.getTipoMedicionAlPeriodo());
  				metaParcial.setValor(medicion.getValor());
  				
  				metasParciales.add(metaParcial);
			}
		}
		
		if (metasParciales != null && metasParciales.size() > 0)
		{
			if (metas == null)
				metas = new ArrayList<MetaAnualParciales>();
			metas.add(addMetas(indicador, metaAnual, metasParciales));
		}
		
		return metas;
	}
	
	private MetaAnualParciales addMetas(Indicador indicador, Meta metaAnual, List<Meta> metasParciales)
	{
		MetaAnualParciales metaAnualParciales = new MetaAnualParciales();
		
		Double valorAnual = null;
		if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
		{
			for (int indexMeta = metasParciales.size() - 1; indexMeta >= 0; indexMeta--)
			{
				Meta meta = (Meta)metasParciales.get(indexMeta);
				if (valorAnual == null)
					valorAnual = 0D;
				if (meta.getValor() != null)
					valorAnual = valorAnual + meta.getValor();
			}
		}
		else 
		{
			int numeroMaximoPorPeriodo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), metaAnual.getMetaId().getAno());
			for (int indexMeta = metasParciales.size() - 1; indexMeta >= 0; indexMeta--)
			{
				Meta meta = (Meta)metasParciales.get(indexMeta);
				if (meta.getMetaId().getPeriodo().intValue() == numeroMaximoPorPeriodo)
				{
					valorAnual = meta.getValor();
					break;
				}
			}
		}
		
		metaAnual.setValor(valorAnual);
		metaAnualParciales.setMetaAnual(metaAnual);
		metaAnualParciales.setMetasParciales(metasParciales);
  		metaAnualParciales.setNumeroPeriodos(Integer.valueOf(metasParciales.size()));
		
  		return metaAnualParciales;
	}
}