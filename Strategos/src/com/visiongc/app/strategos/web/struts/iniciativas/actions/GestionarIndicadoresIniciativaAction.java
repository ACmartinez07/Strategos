package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIndicadoresIniciativaForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarIndicadoresIniciativaAction extends VgcAction
{
	private PaginaLista paginaIndicadores = null;
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)request.getSession().getAttribute("gestionarIniciativasForm");
		GestionarIndicadoresIniciativaForm gestionarIndicadoresIniciativaForm = (GestionarIndicadoresIniciativaForm)form;
    
		String atributoOrden = gestionarIndicadoresIniciativaForm.getAtributoOrden();
		String tipoOrden = gestionarIndicadoresIniciativaForm.getTipoOrden();
		Boolean actualizarForma = request.getSession().getAttribute("actualizarForma") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarForma")) : false;
		if (!actualizarForma)
		{
			actualizarForma = request.getSession().getAttribute("GuardarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("GuardarIndicador")) : false;
			if (request.getSession().getAttribute("GuardarIndicador") != null)
				request.getSession().removeAttribute("GuardarIndicador");
		}
		
		int pagina = 0;
		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarIndicadoresIniciativaForm.setAtributoOrden(atributoOrden);
		}
    
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarIndicadoresIniciativaForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		gestionarIndicadoresIniciativaForm.setClaseId(null);
		gestionarIndicadoresIniciativaForm.setIndicadorId(null);

		if (gestionarIndicadoresIniciativaForm != null && (gestionarIndicadoresIniciativaForm.getIniciativaId() == null) || (gestionarIniciativasForm.getSeleccionadoId() != null && !gestionarIniciativasForm.getSeleccionadoId().equals(gestionarIndicadoresIniciativaForm.getIniciativaId().toString()))) 
		{
			Iniciativa iniciativa = null;
			if ((gestionarIniciativasForm.getSeleccionadoId() != null) && (!gestionarIniciativasForm.getSeleccionadoId().equals("")))
			{
				gestionarIndicadoresIniciativaForm.setIniciativaId(new Long(gestionarIniciativasForm.getSeleccionadoId()));
				iniciativa = (Iniciativa)strategosIndicadoresService.load(Iniciativa.class, gestionarIndicadoresIniciativaForm.getIniciativaId());
			}
			if (iniciativa != null) 
			{
				gestionarIndicadoresIniciativaForm.setClaseId(iniciativa.getClaseId());
				gestionarIndicadoresIniciativaForm.setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			} 
		}
		else if ((gestionarIndicadoresIniciativaForm.getIniciativaId() == null) || (gestionarIniciativasForm.getSeleccionadoId() != null && gestionarIniciativasForm.getSeleccionadoId().equals(gestionarIndicadoresIniciativaForm.getIniciativaId().toString()))) 
		{
			Iniciativa iniciativa = null;
			if ((gestionarIniciativasForm.getSeleccionadoId() != null) && (!gestionarIniciativasForm.getSeleccionadoId().equals("")))
			{
				gestionarIndicadoresIniciativaForm.setIniciativaId(new Long(gestionarIniciativasForm.getSeleccionadoId()));
				iniciativa = (Iniciativa)strategosIndicadoresService.load(Iniciativa.class, gestionarIndicadoresIniciativaForm.getIniciativaId());
			}
			if (iniciativa != null) 
			{
				gestionarIndicadoresIniciativaForm.setClaseId(iniciativa.getClaseId());
				gestionarIndicadoresIniciativaForm.setIndicadorId(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			} 
		}
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		
		if (gestionarIndicadoresIniciativaForm.getClaseId() != null)
			filtros.put("claseId", gestionarIndicadoresIniciativaForm.getClaseId().toString());
		else
			filtros.put("claseId", "0");
		
		Integer totalPaginas = 29;
		
		
				
		PaginaLista paginas =strategosIndicadoresService.getIndicadores(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros, null, null, true);
		
		List<Indicador> indicadores = paginas.getLista();
		List<Indicador> indicadoresFinal = new ArrayList();
		
		for(Indicador ind:indicadores){
			
			if(ind.getTipoFuncion() == 1){
				if(ind.getUltimaMedicion() != null){
					if(ind.getUltimaMedicion() >100){
						ind.setUltimaMedicion(100.00);						
					}
				}
			}
			indicadoresFinal.add(ind);
		}
		
				
		PaginaLista paginaIndicador= new PaginaLista(); 
		
		paginaIndicador.setLista(indicadoresFinal);
		
		if (paginaIndicadores != null && paginaIndicadores.getFiltros() != null)
		{
			if (!paginaIndicadores.samePage(pagina, totalPaginas, atributoOrden, tipoOrden, filtros))
				paginaIndicadores = null;
		}
		if (paginaIndicadores == null || actualizarForma)
		{
			
			paginaIndicadores = paginaIndicador;		
			paginaIndicadores.setFiltros(filtros);
			paginaIndicadores.setNroPagina(pagina);
			paginaIndicadores.setTamanoPagina(totalPaginas);
			paginaIndicadores.setOrden(atributoOrden);
			paginaIndicadores.setTipoOrden(tipoOrden);
			if (actualizarForma)
				request.getSession().removeAttribute("actualizarForma");
		}
		paginaIndicadores.setTamanoSetPaginas(5);
		
		request.setAttribute("gestionarIndicadoresIniciativaPaginaIndicadores", paginaIndicadores);
		
		strategosIndicadoresService.close();
		
		if (forward.equals("exploradorPortafolio"))
			gestionarIndicadoresIniciativaForm.setSource("portafolio");
		
		return mapping.findForward(forward);
	}
}