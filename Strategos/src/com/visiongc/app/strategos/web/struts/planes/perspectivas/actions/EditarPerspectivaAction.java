package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import java.util.Iterator;
import java.util.List;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.PerspectivaRelacion;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.GestionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarPerspectivaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarPerspectivaForm editarPerspectivaForm = (EditarPerspectivaForm)form;
		GestionarPerspectivasForm gestionarPerspectivasForm = (GestionarPerspectivasForm)request.getSession().getAttribute("gestionarPerspectivasForm");

		ActionMessages messages = getMessages(request);

    	String funcion = request.getParameter("accion");
    	if (funcion != null && funcion.equals("copiar"))
    	{
			// Presentacion
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Perspectivas.Copiar.ShowPresentacion", "ShowPresentacion");
			if (presentacion != null && presentacion.getData() != null)
				editarPerspectivaForm.setShowPresentacion(presentacion.getData().equals("1") ? true : false);
			frameworkService.close();

			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    		if (!strategosIndicadoresService.checkLicencia(request))
    		{
    			strategosIndicadoresService.close();
    			
    			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
    			this.saveMessages(request, messages);
    			
    			return this.getForwardBack(request, 1, false);
    		}
    		strategosIndicadoresService.close();
    	}
		
		String perspectivaId = request.getParameter("perspectivaId");

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("PLAN_PERSPECTIVA_VIEW");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("PLAN_PERSPECTIVA_EDIT");
		boolean bloqueado = false;

		if ((perspectivaId != null) && (!perspectivaId.equals("")) && (!perspectivaId.equals("0")))
		{
			bloqueado = !strategosPerspectivasService.lockForUpdate(request.getSession().getId(), perspectivaId, null);
			editarPerspectivaForm.setBloqueado(new Boolean(bloqueado));

			Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaId));

			if (perspectiva != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarPerspectivaForm.setPerspectivaId(perspectiva.getPerspectivaId());
				editarPerspectivaForm.setResponsableId(perspectiva.getResponsableId());
				editarPerspectivaForm.setNombre(perspectiva.getNombre());
				editarPerspectivaForm.setDescripcion(perspectiva.getDescripcion());
				editarPerspectivaForm.setFrecuencia(perspectiva.getFrecuencia());
				editarPerspectivaForm.setTipo(perspectiva.getTipo());
				editarPerspectivaForm.setTipoCalculo(perspectiva.getTipoCalculo());
				editarPerspectivaForm.setPadreId(perspectiva.getPadreId());

				if ((editarPerspectivaForm.getResponsableId() != null) && (!editarPerspectivaForm.getResponsableId().equals("")) && (editarPerspectivaForm.getResponsableId().byteValue() != 0)) 
				{
					StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
					Responsable responsable = (Responsable)strategosResponsablesService.load(Responsable.class, editarPerspectivaForm.getResponsableId());
					editarPerspectivaForm.setNombreResponsable(responsable.getNombreCargo());
					strategosResponsablesService.close();
				}
				
				setDefinicionAsociados(perspectiva, editarPerspectivaForm, strategosPerspectivasService);
			}
			else
			{
				strategosPerspectivasService.unlockObject(request.getSession().getId(), new Long(perspectivaId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			if (!strategosIndicadoresService.checkLicencia(request))
			{
				strategosIndicadoresService.close();
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
				this.saveMessages(request, messages);
				
				return this.getForwardBack(request, 1, false);
			}
			strategosIndicadoresService.close();

			editarPerspectivaForm.clear();
			Perspectiva padre = (Perspectiva)request.getSession().getAttribute("perspectiva");
			editarPerspectivaForm.setPadreId(padre.getPerspectivaId());

			if (gestionarPerspectivasForm.getNivelesPlantillaPlan().size() >= gestionarPerspectivasForm.getNivelSeleccionadoArbol().intValue()) 
			{
				ElementoPlantillaPlanes elementoPlantillaPlanes = (ElementoPlantillaPlanes)gestionarPerspectivasForm.getNivelesPlantillaPlan().get(gestionarPerspectivasForm.getNivelSeleccionadoArbol().intValue() - 1);
				gestionarPerspectivasForm.setElementoPlantillaPlanes(elementoPlantillaPlanes);
			}
		}
		
		request.getSession().setAttribute("frecuencias", Frecuencia.getFrecuencias());

		strategosPerspectivasService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarPerspectivaForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
	
	private void setDefinicionAsociados(Perspectiva perspectiva, EditarPerspectivaForm editarPerspectivaForm, StrategosPerspectivasService strategosPerspectivasService)
	{
		String insumosAsociados = "";
	    int indice = 1;

    	for (Iterator<PerspectivaRelacion> k = perspectiva.getRelacion().iterator(); k.hasNext(); ) 
    	{
    		PerspectivaRelacion perspectivaAsociada = (PerspectivaRelacion)k.next();
    		Perspectiva perspectivaInsumo = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(perspectivaAsociada.getPk().getRelacionId()));    		
    		
    		insumosAsociados = insumosAsociados + "[" + indice + "]" + "[perspectivaId:" + perspectivaInsumo.getPerspectivaId() + "]" + "[perspectivaNombre:" + perspectivaInsumo.getNombre() + "]" + "[rutaCompleta:" + strategosPerspectivasService.getRutaCompletaPerspectivaSinPorcentajes(perspectivaInsumo, editarPerspectivaForm.getSeparadorRuta()) + "]" + editarPerspectivaForm.getSeparadorObjetivos();

    		indice++;
    	}

    	if (!insumosAsociados.equals("")) 
    		editarPerspectivaForm.setInsumosAsociados(insumosAsociados.substring(0, insumosAsociados.length() - editarPerspectivaForm.getSeparadorObjetivos().length()));
	}
}