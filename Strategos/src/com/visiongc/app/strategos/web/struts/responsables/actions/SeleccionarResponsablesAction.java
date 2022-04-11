package com.visiongc.app.strategos.web.struts.responsables.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.responsables.model.util.TipoResponsabilidad;
import com.visiongc.app.strategos.util.TipoObjeto;
import com.visiongc.app.strategos.web.struts.responsables.forms.SeleccionarResponsablesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

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

public final class SeleccionarResponsablesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
	  
		SeleccionarResponsablesForm seleccionarResponsablesForm = (SeleccionarResponsablesForm)form;

		String atributoOrden = seleccionarResponsablesForm.getAtributoOrden();
		String tipoOrden = seleccionarResponsablesForm.getTipoOrden();

		seleccionarResponsablesForm.setFuncionCierre(request.getParameter("funcionCierre"));
		seleccionarResponsablesForm.setSeleccionados(request.getParameter("seleccionados"));

		String[] arregloAtributoOrden = new String[2];
		String[] arregloTipoOrden = new String[2];
	  
		arregloAtributoOrden[0] = "tipo";
		arregloTipoOrden[0] = "ASC";
		int indiceOrden = 1;
		
		if (atributoOrden == null) 
		{
			arregloAtributoOrden[1] = "nombre";
			seleccionarResponsablesForm.setAtributoOrden("nombre");
		} 
		else if (atributoOrden.equals("tipo")) 
		{
			indiceOrden = 0;
			arregloAtributoOrden[1] = "nombre";
			arregloTipoOrden[1] = "ASC";
			seleccionarResponsablesForm.setAtributoOrden("tipo");
		} 
		else 
		{
			arregloAtributoOrden[1] = atributoOrden;
			seleccionarResponsablesForm.setAtributoOrden(arregloAtributoOrden[1]);
		}

		if (tipoOrden == null)
			arregloTipoOrden[indiceOrden] = "ASC";
		else 
			arregloTipoOrden[indiceOrden] = tipoOrden;
		seleccionarResponsablesForm.setTipoOrden(arregloTipoOrden[indiceOrden]);
		
		if (seleccionarResponsablesForm.getFuncionCierre() != null) 
		{
			if (!seleccionarResponsablesForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarResponsablesForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarResponsablesForm.setFuncionCierre(seleccionarResponsablesForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarResponsablesForm.setFuncionCierre(null);
		}
		
		StrategosResponsablesService responsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
	  
		if ((seleccionarResponsablesForm.getOrganizacionId() != null) && (!seleccionarResponsablesForm.getOrganizacionId().equals(""))) 
			filtros.put("organizacionId", seleccionarResponsablesForm.getOrganizacionId());
		if ((seleccionarResponsablesForm.getMostrarGrupos() != null) && (!seleccionarResponsablesForm.getMostrarGrupos().equals(""))) 
			filtros.put("esGrupo", Boolean.parseBoolean(seleccionarResponsablesForm.getMostrarGrupos()));
		if ((seleccionarResponsablesForm.getMostrarGlobales() != null) && (!seleccionarResponsablesForm.getMostrarGlobales().equals("")))
			filtros.put("tipo", Boolean.parseBoolean(seleccionarResponsablesForm.getMostrarGlobales()));
		else 
			filtros.put("tipo", true);
		
		PaginaLista paginaResponsables = responsablesService.getResponsables(0, 0, arregloAtributoOrden, arregloTipoOrden, true, filtros);

		request.setAttribute("paginaResponsables", paginaResponsables);
		
		responsablesService.close();
		
		return mapping.findForward(forward);
  	}
	
	public Boolean hayResposabilidad(Object objeto, Byte tipoResponsabilidad, Usuario usuario)
	{
		if (usuario.getIsAdmin())
			return false;
		
		Boolean hayResponsabilidad = false;
		Byte tipoObjeto = null;
		
		if (objeto.getClass().getName().equals("com.visiongc.app.strategos.indicadores.model.Indicador")) 
			tipoObjeto = TipoObjeto.getObjetoIndicador();
		else if (objeto.getClass().getName().equals("Iniciativa"))
			tipoObjeto = TipoObjeto.getObjetoIniciativa();
		else if (objeto.getClass().getName().equals("Actividad"))
			tipoObjeto = TipoObjeto.getObjetoActividad();
		else if (objeto.getClass().getName().equals("Perspectiva"))
			tipoObjeto = TipoObjeto.getObjetoPerspectiva();
		
		if (tipoObjeto.byteValue() == TipoObjeto.getObjetoIndicador().byteValue())
		{
			Indicador indicador = (Indicador) objeto;
			if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableFijarMeta().byteValue())
				hayResponsabilidad = indicador.getResponsableFijarMetaId() != null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableCargarMeta().byteValue())
				hayResponsabilidad = indicador.getResponsableCargarMetaId() != null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableSeguimiento().byteValue())
				hayResponsabilidad = indicador.getResponsableSeguimientoId() != null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableLograrMeta().byteValue())
				hayResponsabilidad = indicador.getResponsableLograrMetaId() != null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableCargarEjecutado().byteValue())
				hayResponsabilidad = indicador.getResponsableCargarEjecutadoId() != null;
		}
		
		return hayResponsabilidad;
	}
	
	public Boolean usuarioEsResponsable(Object objeto, Byte tipoResponsabilidad, Usuario usuario)
	{
		Boolean usuarioEsResponsable = false;
		Byte tipoObjeto = null;
		Long responsableId = null;
		
		if (objeto.getClass().getName().equals("com.visiongc.app.strategos.indicadores.model.Indicador")) 
			tipoObjeto = TipoObjeto.getObjetoIndicador();
		else if (objeto.getClass().getName().equals("Iniciativa"))
			tipoObjeto = TipoObjeto.getObjetoIniciativa();
		else if (objeto.getClass().getName().equals("Actividad"))
			tipoObjeto = TipoObjeto.getObjetoActividad();
		else if (objeto.getClass().getName().equals("Perspectiva"))
			tipoObjeto = TipoObjeto.getObjetoPerspectiva();
		
		if (tipoObjeto.byteValue() == TipoObjeto.getObjetoIndicador().byteValue())
		{
			Indicador indicador = (Indicador) objeto;
			if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableFijarMeta().byteValue())
				responsableId = indicador.getResponsableFijarMetaId() != null ? indicador.getResponsableFijarMetaId() : null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableCargarMeta().byteValue())
				responsableId = indicador.getResponsableCargarMetaId() != null ? indicador.getResponsableCargarMetaId() : null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableSeguimiento().byteValue())
				responsableId = indicador.getResponsableSeguimientoId() != null ? indicador.getResponsableSeguimientoId() : null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableLograrMeta().byteValue())
				responsableId = indicador.getResponsableLograrMetaId() != null ? indicador.getResponsableLograrMetaId() : null;
			else if (tipoResponsabilidad.byteValue() == TipoResponsabilidad.getTipoResponsableCargarEjecutado().byteValue())
				responsableId = indicador.getResponsableCargarEjecutadoId() != null ? indicador.getResponsableCargarEjecutadoId() : null;
		}

		if (responsableId != null && responsableId != 0L)
		{
			StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
			Responsable responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(responsableId));

			if (responsable != null)
			{
				usuarioEsResponsable = (responsable.getUsuarioId() != null && responsable.getUsuarioId().longValue() == usuario.getUsuarioId().longValue());
				
				if (!usuarioEsResponsable && responsable.getEsGrupo())
				{
			        for (Iterator<Responsable> i = responsable.getResponsables().iterator(); i.hasNext(); ) 
			        {
			            Responsable responsableAsociado = (Responsable)i.next();
			            if (responsableAsociado.getUsuarioId() != null && responsableAsociado.getUsuarioId().longValue() == usuario.getUsuarioId().longValue())
			            {
			            	usuarioEsResponsable = true;
			            	break;
			            }
			        }
				}
			}
			
			strategosResponsablesService.close();
		}
		
		return usuarioEsResponsable;
	}
}
