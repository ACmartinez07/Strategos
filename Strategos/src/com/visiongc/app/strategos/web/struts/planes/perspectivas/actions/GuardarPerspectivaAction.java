package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacion;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacionPK;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.GestionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarPerspectivaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarPerspectivaForm editarPerspectivaForm = (EditarPerspectivaForm)form;
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)request.getSession().getAttribute("gestionarPlanForm");
		GestionarPerspectivasForm gestionarPerspectivasForm = (GestionarPerspectivasForm)request.getSession().getAttribute("gestionarPerspectivasForm");

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarPerspectivaAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		if (cancelar)
		{
			strategosPerspectivasService.unlockObject(request.getSession().getId(), editarPerspectivaForm.getPerspectivaId());
			strategosPerspectivasService.close();

			return getForwardBack(request, 1, true);
		}

		Perspectiva perspectiva = new Perspectiva();
		boolean nuevo = false;
		int respuesta = 10000;

		perspectiva.setPerspectivaId(editarPerspectivaForm.getPerspectivaId());
		perspectiva.setNombre(editarPerspectivaForm.getNombre());

		if ((editarPerspectivaForm.getPerspectivaId() != null) && (!editarPerspectivaForm.getPerspectivaId().equals(Long.valueOf("0"))))
			perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, editarPerspectivaForm.getPerspectivaId());
		else
    	{
			nuevo = true;
			Perspectiva perspectivaPadre = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, editarPerspectivaForm.getPadreId());
			perspectiva = new Perspectiva();
			perspectiva.setPerspectivaId(new Long(0L));
			perspectiva.setPlanId(gestionarPlanForm.getPlanId());
			perspectiva.setPadreId(perspectivaPadre.getPerspectivaId());
			perspectiva.setTipo(gestionarPerspectivasForm.getElementoPlantillaPlanes().getTipo());
			perspectiva.setRelacion(new HashSet<PerspectivaRelacion>());
    	}

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		Plan plan = (Plan)strategosPlanesService.load(Plan.class, perspectiva.getPlanId());
		perspectiva.setPlan(plan);
		strategosPlanesService.close();

		perspectiva.setNombre(editarPerspectivaForm.getNombre());
		perspectiva.setFrecuencia(editarPerspectivaForm.getFrecuencia());
		perspectiva.setTipoCalculo(editarPerspectivaForm.getTipoCalculo());
		
		if (perspectiva.getTipoCalculo() == null) 
			perspectiva.setTipoCalculo(TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual());

		if ((editarPerspectivaForm.getResponsableId() != null) && (!editarPerspectivaForm.getResponsableId().equals("")) && (editarPerspectivaForm.getResponsableId().byteValue() != 0))
			perspectiva.setResponsableId(editarPerspectivaForm.getResponsableId());
		else 
			perspectiva.setResponsableId(null);

		if ((editarPerspectivaForm.getDescripcion() != null) && (!editarPerspectivaForm.getDescripcion().equals("")))
			perspectiva.setDescripcion(editarPerspectivaForm.getDescripcion());
		else 
			perspectiva.setDescripcion(null);
		
  		if ((editarPerspectivaForm.getInsumosAsociados() != null) && (!editarPerspectivaForm.getInsumosAsociados().equals(""))) 
  		{
  			String[] insumos = editarPerspectivaForm.getInsumosAsociados().split(editarPerspectivaForm.getSeparadorObjetivos());
  			String[] strInsumo = (String[])null;
  			boolean foundAsociado = false;
  			PerspectivaRelacion relacionCopia;
  			Set<PerspectivaRelacion> insumosRemover = new HashSet<PerspectivaRelacion>(); 
  			// Remover los Insumos que no existen
			for (Iterator<PerspectivaRelacion> k = perspectiva.getRelacion().iterator(); k.hasNext(); ) 
			{
				PerspectivaRelacion relacion = k.next();
  				foundAsociado = false;
	  			for (int i = 0; i < insumos.length; i++) 
	  			{
	  				strInsumo = insumos[i].split("\\]\\[");
	  				Long insumoAsociado = new Long(strInsumo[1].substring("perspectivaId:".length()));
    				if (relacion.getPk().getRelacionId() == insumoAsociado.longValue())
        			{
    					foundAsociado = true;
    					break;
        			}
	  			}

				if (!foundAsociado)
					insumosRemover.add(relacion);
			}

  			for (Iterator<PerspectivaRelacion> k = insumosRemover.iterator(); k.hasNext(); )
			{
				PerspectivaRelacion relacion = k.next();
				perspectiva.getRelacion().remove(relacion);
			}

			// Agregar los insumos que no existen
  			for (int i = 0; i < insumos.length; i++) 
  			{
  				foundAsociado = false;
  				strInsumo = insumos[i].split("\\]\\[");
  				Long insumoAsociado = new Long(strInsumo[1].substring("perspectivaId:".length()));
  				
  				for (Iterator<PerspectivaRelacion> k = perspectiva.getRelacion().iterator(); k.hasNext(); ) 
    			{
    				PerspectivaRelacion relacion = k.next();
    				if (relacion.getPk().getRelacionId() == insumoAsociado.longValue())
        			{
    					foundAsociado = true;
    					break;
        			}
    			}

				if (!foundAsociado)
    			{
					
					Perspectiva relacion = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, insumoAsociado);
					
					relacionCopia = new PerspectivaRelacion();
    				relacionCopia.setPk(new PerspectivaRelacionPK());
    				relacionCopia.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
    				relacionCopia.getPk().setRelacionId(relacion.getPerspectivaId());
    				relacionCopia.setPerspectiva(perspectiva);
    				relacionCopia.setRelacion(relacion);
    				
					perspectiva.getRelacion().add(relacionCopia);
    			}
  			}
  		}
  		else if (perspectiva.getRelacion() != null && perspectiva.getRelacion().size() > 0)
		{
  			Set<PerspectivaRelacion> insumosRemover = new HashSet<PerspectivaRelacion>(); 
  			// Remover los Insumos que no existen
			for (Iterator<PerspectivaRelacion> k = perspectiva.getRelacion().iterator(); k.hasNext(); ) 
			{
				PerspectivaRelacion relacion = k.next();
				insumosRemover.add(relacion);
			}
			for (Iterator<PerspectivaRelacion> k = insumosRemover.iterator(); k.hasNext(); )
			{
				PerspectivaRelacion relacion = k.next();
				perspectiva.getRelacion().remove(relacion);
			}
		}
		
		respuesta = strategosPerspectivasService.savePerspectiva(perspectiva, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			if (perspectiva.getPadreId() != null)
			{
			    StrategosPerspectivasService servicioPerspectivas = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
				Perspectiva padre = (Perspectiva)servicioPerspectivas.load(Perspectiva.class, perspectiva.getPadreId());
				if (padre.getPadreId() == null)
				{
				    Map<String, Object> filtros = new HashMap<String, Object>();

				    filtros.put("padreId", perspectiva.getPadreId());

				    String[] orden = new String[1];
				    String[] tipoOrden = new String[1];
				    orden[0] = "nombre";
				    tipoOrden[0] = "asc";
				
					List<Perspectiva> perspectivasHijas = servicioPerspectivas.getPerspectivas(orden, tipoOrden, filtros);
					Byte frecuencia = null;
			        for (Iterator<?> iter = perspectivasHijas.iterator(); iter.hasNext(); ) 
			        {
			        	Perspectiva pers = (Perspectiva)iter.next();
			        	if (frecuencia == null)
			        		frecuencia = pers.getFrecuencia();
			        	else if (frecuencia.byteValue() < pers.getFrecuencia().byteValue())
			        		frecuencia = pers.getFrecuencia();
			        }
			        
			        if (frecuencia != null && padre.getFrecuencia().byteValue() != frecuencia.byteValue())
			        {
			        	padre.setFrecuencia(frecuencia);
			        	respuesta = servicioPerspectivas.savePerspectiva(padre, getUsuarioConectado(request));
			        	
			        	if (respuesta == 10000)
			        	{
				        	StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				        	StrategosPlanesService servicioPlanes = StrategosServiceFactory.getInstance().openStrategosPlanesService();
				        	Indicador indicadorLogro = (Indicador)strategosIndicadoresService.load(Indicador.class, plan.getNlAnoIndicadorId());
				        	if (indicadorLogro != null)
				        	{
					        	indicadorLogro.setFrecuencia(frecuencia);
					        	respuesta = strategosIndicadoresService.saveIndicador(indicadorLogro, getUsuarioConectado(request));
					        	if (respuesta == 10000)
					        		respuesta = servicioPlanes.deleteIndicadorEstados(indicadorLogro.getIndicadorId(), plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null, null, null);
					        	if (respuesta == 10000)
					        		respuesta = servicioPlanes.deleteIndicadorEstados(padre.getNlAnoIndicadorId(), plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null, null, null);
					        	if (respuesta == 10000)
					        		respuesta = servicioPlanes.deletePlanEstados(plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), null, null, null, null);
					        	if (respuesta == 10000)
					        		respuesta = servicioPlanes.saveAlertaIndicadorPlan(indicadorLogro.getIndicadorId(), plan.getPlanId(), null, getUsuarioConectado(request));
				        	}
				        	
				        	if (respuesta == 10000)
				        	{
					        	indicadorLogro = (Indicador)strategosIndicadoresService.load(Indicador.class, plan.getNlParIndicadorId());
					        	if (indicadorLogro != null)
					        	{
						        	indicadorLogro.setFrecuencia(frecuencia);
						        	respuesta = strategosIndicadoresService.saveIndicador(indicadorLogro, getUsuarioConectado(request));			        	
						        	if (respuesta == 10000)
						        		respuesta = servicioPlanes.deleteIndicadorEstados(indicadorLogro.getIndicadorId(), plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null, null, null);
						        	if (respuesta == 10000)
						        		respuesta = servicioPlanes.deleteIndicadorEstados(padre.getNlParIndicadorId(), plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null, null, null);
						        	if (respuesta == 10000)
						        		respuesta = servicioPlanes.deletePlanEstados(plan.getPlanId(), TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), null, null, null, null);
						        	if (respuesta == 10000)
						        		respuesta = servicioPlanes.saveAlertaIndicadorPlan(indicadorLogro.getIndicadorId(), plan.getPlanId(), null, getUsuarioConectado(request));
					        	}
				        	}
				        	servicioPlanes.close();
				        	strategosIndicadoresService.close();
			        	}
			        }
			        servicioPerspectivas.close();
				}
			}
			
			strategosPerspectivasService.unlockObject(request.getSession().getId(), editarPerspectivaForm.getPerspectivaId());
			forward = "exito";
			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearPerspectiva";
			}
			else 
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		} 
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		strategosPerspectivasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarPerspectivaAction.ultimoTs", ts);
		
		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}