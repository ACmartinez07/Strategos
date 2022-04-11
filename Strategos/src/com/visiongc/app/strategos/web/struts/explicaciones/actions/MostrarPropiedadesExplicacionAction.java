package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.util.TipoObjetoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class MostrarPropiedadesExplicacionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)form;

		String explicacionId = request.getParameter("explicacionId");

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		OrganizacionStrategos organizacion = (OrganizacionStrategos)request.getSession().getAttribute("organizacion");
		
		if ((explicacionId == null) || (explicacionId.equals(""))) 
			cancelar = true;

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

		if (cancelar)
		{
			strategosExplicacionesService.unlockObject(request.getSession().getId(), editarExplicacionForm.getExplicacionId());

			strategosExplicacionesService.close();

			return getForwardBack(request, 1, true);
		}

		Explicacion explicacion = (Explicacion)strategosExplicacionesService.load(Explicacion.class, new Long(explicacionId));

		if (explicacion != null)
		{
			editarExplicacionForm.clear();

			editarExplicacionForm.setTitulo(explicacion.getTitulo());
			editarExplicacionForm.setNombreUsuarioCreado(explicacion.getUsuarioCreado().getFullName());
			editarExplicacionForm.setFechaCreado(VgcFormatter.formatearFecha(explicacion.getCreado(), "formato.fecha.larga"));
			editarExplicacionForm.setNumeroAdjuntos(strategosExplicacionesService.getNumeroAdjuntos(explicacion.getExplicacionId()));
			editarExplicacionForm.setNombreTipoObjetoKey(TipoObjetoExplicacion.getTipoExplicacion(explicacion.getObjetoKey().byteValue()));
			editarExplicacionForm.setNombreOrganizacion(organizacion.getNombre());
			editarExplicacionForm.setTipo(explicacion.getTipo());
			
			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Indicador")) 
			{
				editarExplicacionForm.setNombreObjetoKey(((Indicador)request.getSession().getAttribute("indicador")).getNombre());

				organizacion = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, ((Indicador)request.getSession().getAttribute("indicador")).getOrganizacionId());

				editarExplicacionForm.setNombreOrganizacion(organizacion.getNombre());
			}

			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Celda"))
			{
				StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
				Celda celda = (Celda)strategosCeldasService.load(Celda.class, ((Celda)request.getSession().getAttribute("celda")).getCeldaId());
				
				String nombreObjetoKey = "";

				if (celda.getIndicadoresCelda() != null) 
				{
					if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) {
						nombreObjetoKey = celda.getTitulo();
					} 
					else if (celda.getIndicadoresCelda().size() == 1) 
					{
						IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
						StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
						Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
						nombreObjetoKey = indicador.getNombre();
					}
				}
				else 
					nombreObjetoKey = celda.getTitulo();

				editarExplicacionForm.setNombreObjetoKey(nombreObjetoKey);
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyCelda());
				editarExplicacionForm.setObjetoId(((Celda)request.getSession().getAttribute("celda")).getCeldaId());
			}
			
			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Organizacion"))
			{
				editarExplicacionForm.setNombreObjetoKey(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyOrganizacion());
				editarExplicacionForm.setObjetoId(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			}
		}

		strategosExplicacionesService.close();
		strategosOrganizacionesService.close();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}