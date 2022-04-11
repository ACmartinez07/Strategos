/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.portafolios.forms.GestionarPortafoliosForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.web.controles.SplitControl;
import com.visiongc.framework.web.struts.forms.FiltroForm;

/**
 * @author Kerwin
 *
 */
public class GestionarPortafoliosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		GestionarPortafoliosForm gestionarPortafoliosForm = (GestionarPortafoliosForm)form;
		
		Byte filtroCondicionType = gestionarPortafoliosForm != null && gestionarPortafoliosForm.getFiltro() != null ? gestionarPortafoliosForm.getFiltro().getCondicion() : null;
		String filtroNombre = gestionarPortafoliosForm != null && gestionarPortafoliosForm.getFiltro() != null ? gestionarPortafoliosForm.getFiltro().getNombre() : "";
		String portafolioSeleccionadoId = gestionarPortafoliosForm != null && gestionarPortafoliosForm.getSeleccionadoId() != null ? gestionarPortafoliosForm.getSeleccionadoId() : null;
		String iniciativaSeleccionadoId = gestionarPortafoliosForm != null && gestionarPortafoliosForm.getIniciativaId() != null ? gestionarPortafoliosForm.getIniciativaId().toString() : null;
		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		if (gestionarPortafoliosForm.getOrganizacionId() != null && organizacionId != null && organizacionId.longValue() != gestionarPortafoliosForm.getOrganizacionId().longValue())
		{
			filtroCondicionType = null; 
			filtroNombre = "";
			portafolioSeleccionadoId = null;
			iniciativaSeleccionadoId = null;
		}			
		gestionarPortafoliosForm.clear();

		String nombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : (filtroNombre != null ? filtroNombre : "");
		Byte selectCondicionType = (request.getParameter("selectCondicionType") != null && request.getParameter("selectCondicionType") != "") ? Byte.parseByte(request.getParameter("selectCondicionType")) : (filtroCondicionType != null ? filtroCondicionType : CondicionType.getFiltroCondicionActivo());
		Long iniciativaId = null; 
		gestionarPortafoliosForm.setOrganizacionId(organizacionId);
		if (request.getParameter("iniciativaSeleccionadaId") != null && request.getParameter("iniciativaSeleccionadaId") != "" && !request.getParameter("iniciativaSeleccionadaId").equals("0"))
			iniciativaId = Long.parseLong(request.getParameter("iniciativaSeleccionadaId"));
		else if (iniciativaSeleccionadoId != null && !iniciativaSeleccionadoId.equals(""))
			iniciativaId = Long.parseLong(iniciativaSeleccionadoId);
		Long portafolioId = null;
		if (request.getParameter("portafolioId") != null && request.getParameter("portafolioId") != "")
			portafolioId = Long.parseLong(request.getParameter("portafolioId"));
		else if (portafolioSeleccionadoId != null)
			portafolioId = Long.parseLong(portafolioSeleccionadoId);
		if (portafolioId != null)
			gestionarPortafoliosForm.setSeleccionadoId(portafolioId.toString());
		if (iniciativaId != null)
			gestionarPortafoliosForm.setIniciativaId(iniciativaId);

		FiltroForm filtro = new FiltroForm();
		filtro.setCondicion(selectCondicionType);
		if (nombre.equals(""))
			filtro.setNombre(null);
		else
			filtro.setNombre(nombre);
		gestionarPortafoliosForm.setFiltro(filtro);
		
	    StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

	    gestionarPortafoliosForm.setPortafolioId(portafolioId);
		
		Map<String, String> filtros = new HashMap<String, String>();
	    int pagina = 0;
	    String atributoOrden = null;
	    String tipoOrden = null;

	    if (atributoOrden == null) 
	    	atributoOrden = "nombre";
	    if (tipoOrden == null) 
	    	tipoOrden = "ASC";
	    if (pagina < 1) 
	    	pagina = 1;

	    filtros.put("organizacionId", organizacionId.toString());
		if (gestionarPortafoliosForm.getFiltro().getNombre() != null)
			filtros.put("nombre", gestionarPortafoliosForm.getFiltro().getNombre());
		if (gestionarPortafoliosForm.getFiltro() != null && 
				gestionarPortafoliosForm.getFiltro().getCondicion() != null && 
				(gestionarPortafoliosForm.getFiltro().getCondicion().byteValue() == CondicionType.getFiltroCondicionActivo() || gestionarPortafoliosForm.getFiltro().getCondicion().byteValue() == CondicionType.getFiltroCondicionInactivo()))
			filtros.put("activo", gestionarPortafoliosForm.getFiltro().getCondicion().toString());
	    
	    PaginaLista paginaPortafolios = strategosPortafoliosService.getPortafolios(pagina, 30, atributoOrden, tipoOrden, true, filtros);

	    paginaPortafolios.setTamanoSetPaginas(5);

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();

		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Portafolio", "Ancho");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Portafolio");
			pk.setObjeto("Ancho");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData("500");
			
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		}
		gestionarPortafoliosForm.setAnchoPorDefecto(configuracionUsuario.getData());
		
		configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Portafolio", "Alto");		
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Portafolio");
			pk.setObjeto("Alto");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData("350");
			
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		}
		gestionarPortafoliosForm.setAltoPorDefecto(configuracionUsuario.getData());
	    
		if (paginaPortafolios.getLista().size() > 0) 
		{
			for (Iterator<Portafolio> iter = paginaPortafolios.getLista().iterator(); iter.hasNext(); ) 
			{
				Portafolio portafolio = (Portafolio)iter.next();
				Indicador indicador = (Indicador)strategosPortafoliosService.load(Indicador.class, new Long(portafolio.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()))); 
				if (indicador != null)
				{
					portafolio.setFechaUltimaMedicion(indicador.getFechaUltimaMedicion());
					portafolio.setPorcentajeCompletado(indicador.getUltimaMedicion());
				}
			}
			
			configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Portafolio.Lista", "PortafolioId");
			Long portafolioIdFocus = null;
			boolean portafolioEnLaLista = false;
			if (configuracionUsuario != null)
			{
				portafolioIdFocus = new Long(configuracionUsuario.getData());
				for (Iterator<Portafolio> iter = paginaPortafolios.getLista().iterator(); iter.hasNext(); ) 
				{
					Portafolio portafolio = (Portafolio)iter.next();
					if (portafolio.getId().longValue() == portafolioIdFocus.longValue())
					{
						portafolioEnLaLista = true;
						break;
					}
				}
			}
			
			if ((gestionarPortafoliosForm.getSeleccionadoId() == null) || (gestionarPortafoliosForm.getSeleccionadoId().equals(""))) 
			{
				portafolioId = ((Portafolio)paginaPortafolios.getLista().get(0)).getId();
				if (!portafolioEnLaLista)
				{
					portafolioIdFocus = portafolioId;
					portafolioEnLaLista = true;
				}
				else
				{
					portafolioId = portafolioIdFocus;
					portafolioEnLaLista = false;
				}
				gestionarPortafoliosForm.setSeleccionadoId(portafolioId.toString());
			}
			else
			{
				for (Iterator<Portafolio> iter = paginaPortafolios.getLista().iterator(); iter.hasNext(); ) 
				{
					Portafolio portafolio = (Portafolio)iter.next();
					if (portafolio.getId().longValue() == new Long(gestionarPortafoliosForm.getSeleccionadoId()).longValue())
					{
						portafolioEnLaLista = true;
						break;
					}
				}
				
				if (portafolioEnLaLista)
					portafolioIdFocus = new Long(gestionarPortafoliosForm.getSeleccionadoId());
				else
				{
					portafolioId = ((Portafolio)paginaPortafolios.getLista().get(0)).getId();
					portafolioIdFocus = portafolioId;
					portafolioEnLaLista = true;
					gestionarPortafoliosForm.setSeleccionadoId(portafolioId.toString());
				}
			}

			if (portafolioEnLaLista)
			{
				if (configuracionUsuario == null)
				{
					configuracionUsuario = new ConfiguracionUsuario(); 
					ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
					pk.setConfiguracionBase("Strategos.Foco.Portafolio.Lista");
					pk.setObjeto("PortafolioId");
					pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
					configuracionUsuario.setPk(pk);
				}
				configuracionUsuario.setData(portafolioIdFocus.toString());
				frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
			}

			Portafolio portafolioSeleccionada = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(gestionarPortafoliosForm.getSeleccionadoId()));
			if (portafolioSeleccionada != null) 
				gestionarPortafoliosForm.setSeleccionadoNombre(portafolioSeleccionada.getNombre());
			else
			{
				portafolioId = ((Portafolio)paginaPortafolios.getLista().get(0)).getId();
				gestionarPortafoliosForm.setSeleccionadoId(portafolioId.toString());
				portafolioSeleccionada = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(gestionarPortafoliosForm.getSeleccionadoId()));
				if (portafolioSeleccionada != null) 
					gestionarPortafoliosForm.setSeleccionadoNombre(portafolioSeleccionada.getNombre());
				
				ActionMessages messages = getMessages(request);
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.reporteportafolio.portafolioeliminado"));
				saveMessages(request, messages);
			}
		}
		
		frameworkService.close();
		
	    request.setAttribute("paginaPortafolios", paginaPortafolios);

	    strategosPortafoliosService.close();
	    
	    SplitControl.setConfiguracion(request, "splitPortafolios");
	    
	    GestionarIniciativasForm gestionarIniciativasForm = new GestionarIniciativasForm(); 
	    gestionarIniciativasForm.setSource("Portafolio");
		
		return mapping.findForward(forward);
	}
}
