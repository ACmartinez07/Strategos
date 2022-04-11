package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.GestionarIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.Modulo.ModuloType;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.transaccion.TransaccionService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarIndicadoresAction extends VgcAction
{
	private PaginaLista paginaIndicadores = null;
	private PaginaLista paginaResponsable = null;
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIndicadoresForm gestionarIndicadoresForm = (GestionarIndicadoresForm)form;
		
		Long organizacionId = new Long((String)request.getSession().getAttribute("organizacionId"));
		Usuario user = getUsuarioConectado(request);
		Long usuarioId = user.getUsuarioId();
		Boolean actualizarForma = request.getSession().getAttribute("actualizarForma") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarForma")) : false;
		if (!actualizarForma)
		{
			actualizarForma = request.getSession().getAttribute("GuardarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("GuardarIndicador")) : false;
			if (request.getSession().getAttribute("GuardarIndicador") != null)
				request.getSession().removeAttribute("GuardarIndicador");
		}

		
		gestionarIndicadoresForm.setPagina(1); 
	
		
		String atributoOrden = gestionarIndicadoresForm.getAtributoOrden();
		String tipoOrden = gestionarIndicadoresForm.getTipoOrden();
		int pagina = gestionarIndicadoresForm.getPagina();
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL");
		
		gestionarIndicadoresForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL"));
		gestionarIndicadoresForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT"));

		if (atributoOrden == null) 
		{
			atributoOrden = "nombre";
			gestionarIndicadoresForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarIndicadoresForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

		Responsable responsable = null;
		
		
		
		Map<String, Object> filtroRes = new HashMap<String, Object>();
		filtroRes.put("usuarioId", usuarioId);
		
		List<Responsable> responsables = new ArrayList();
		
		paginaResponsable = strategosResponsablesService.getResponsables(pagina, 1, atributoOrden, tipoOrden, true, filtroRes);
		
		responsables = paginaResponsable.getLista();
				
		Long responsableId= new Long(0);
		for(Responsable res: responsables) {
										
			if(res.getUsuarioId() != null) {
				responsableId = res.getResponsableId();
			}
		}
	
		
		if(responsableId != 0) {
			
		
			Map<String, Object> filtros = new HashMap<String, Object>();
	
			
			if ((gestionarIndicadoresForm.getFiltroNombre() != null) && (!gestionarIndicadoresForm.getFiltroNombre().equals(""))) 
				filtros.put("nombre", gestionarIndicadoresForm.getFiltroNombre());
			if (!mostrarTodas) 
				filtros.put("visible", true);
			
			filtros.put("responsableCargarEjecutadoId", responsableId);
			filtros.put("responsableLograrMetaId", responsableId);
			filtros.put("responsableSeguimientoId", responsableId);
			
			Integer totalPaginas = 29;
			if (paginaIndicadores != null && paginaIndicadores.getFiltros() != null)
			{
				if (!paginaIndicadores.samePage(pagina, totalPaginas, atributoOrden, tipoOrden, filtros))
					paginaIndicadores = null;
			}
			
			paginaIndicadores = strategosIndicadoresService.getIndicadoresResponsables(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros, null, null, true);
			
			List<Indicador> indicadores = paginaIndicadores.getLista(); 
			
			for(Indicador ind: indicadores) {
				
				ind.setOrganizacionNombre(ind.getOrganizacion().getNombre());
				ind.setObservacion(debeRegistrar(ind.getIndicadorId()));
			}
			
			paginaIndicadores.setLista(indicadores);
			
			strategosIndicadoresService.close();
		
		}else {
			
			List indicadores = new ArrayList();
			   
			paginaIndicadores = new PaginaLista();
			    
			paginaIndicadores.setLista(indicadores);
			paginaIndicadores.setNroPagina(1);
			paginaIndicadores.setTamanoPagina(29);
			paginaIndicadores.setTotal(0);
			paginaIndicadores.setOrden(atributoOrden);
			paginaIndicadores.setTipoOrden(tipoOrden);
			
		}
		
		
		paginaIndicadores.setTamanoSetPaginas(5);
		request.setAttribute("paginaIndicadores", paginaIndicadores);
		
		
	    
	 
	    return mapping.findForward(forward);
		
		/*
		Integer totalPaginas = 29;
		if (paginaIndicadores != null && paginaIndicadores.getFiltros() != null)
		{
			if (!paginaIndicadores.samePage(pagina, totalPaginas, atributoOrden, tipoOrden, filtros))
				paginaIndicadores = null;
		}

		if (paginaIndicadores == null || actualizarForma)
		{
			paginaIndicadores = strategosIndicadoresService.getIndicadores(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros, null, null, true);
			paginaIndicadores.setFiltros(filtros);
			paginaIndicadores.setNroPagina(pagina);
			paginaIndicadores.setTamanoPagina(totalPaginas);
			paginaIndicadores.setOrden(atributoOrden);
			paginaIndicadores.setTipoOrden(tipoOrden);
			if (actualizarForma)
				request.getSession().removeAttribute("actualizarForma");
		}

		paginaIndicadores.setTamanoSetPaginas(5);
		request.setAttribute("paginaIndicadores", paginaIndicadores);
		
		Modulo modulo = new Modulo().getModuloActivo(ModuloType.Indicador.Reporte.ComiteEjecutivo);
		if (modulo != null)
		{
			gestionarIndicadoresForm.setReporteComiteEjecutivo(modulo.getActivo());
			if (gestionarIndicadoresForm.getReporteComiteEjecutivo() != null && gestionarIndicadoresForm.getReporteComiteEjecutivo())
				gestionarIndicadoresForm.setReporte(true);
		}
		strategosIndicadoresService.close();
		
		Modulo cliente = (Modulo) request.getSession().getAttribute("MIF");
		if (cliente != null && cliente.getActivo())
		{
			//Revisar si hay transacciones
			TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
			List<Transaccion> transacciones = transaccionService.getTransacciones(0, 0, "nombre", "ASC", true, null).getLista();
			gestionarIndicadoresForm.setHayTransacciones(transacciones.size() > 0);
			gestionarIndicadoresForm.setTransacciones(transacciones);
			transaccionService.close();
		}
		
		return mapping.findForward(forward);
		*/
	}
	
	public String debeRegistrar(Long indicadorId) {
  		
  		Calendar fecha = Calendar.getInstance();
        int ano = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
		int periodo =0;
  		
  		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    	StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
  		
  		
  			
  			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorId);
  			if(indicador != null) {
  					
  				periodo=obtenerPeriodoFrecuencia(mes, indicador.getFrecuencia());
  				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ano, ano, periodo, periodo);
  					
  				if(mediciones.size() == 0) {
  					return "Debe Registrar";
  				}
  			} 				
  				
  			
  			
  		
  		
  		return "";
  	}
  	
  	public int obtenerPeriodoFrecuencia(int mesAct, Byte frecuencia) {
  		int mes=0;
  		mes = mesAct;
  		
  		switch(frecuencia) {
	  		
		    case 3:
		    	//mensual
		    	mes = mesAct;
		    	break;
		    case 4:
		    	//bimestral
		    	if(mes <=2){
		    		 mes=1;
		    	}else if(mes >2 && mes <=4){
		    		 mes=2;
		    	}else if(mes >4 && mes <=6){
		    		 mes=3;
		    	}else if(mes >6 && mes <=8){
		    		 mes=4;
		    	}else if(mes >8 && mes <=10){
		    		 mes=5;
		    	}else if(mes >10){
		    		 mes=6;
		    	}
		    	break;
		    case 5:
		    	
		    	if(mes <=3){
		    		mes=1;
		    	}else if(mes >3 && mes <=6){
		    		mes=2;
		    	}else if(mes >6 && mes <=9){
		    		mes=3;
		    	}else if(mes >9){
		    		mes=4;
		    	}
		    	//trimestral
		    	break;
		    case 6:
		    	
		    	if(mes <=4){
		    		mes= 1;
		    	}else if(mes >4 && mes <=8){
		    		mes= 2;
		    	}else if(mes >8){
		    		mes= 3;
		    	}
		    	break;
		    	//cuatrimestral
	
		    case 7:
		    	if(mes<=6){
		    		mes= 1;
		    	}else if(mes>6){
		    		mes= 2;
		    	}
		    	//semestral
		    	break;
		    case 8:
		    	//anual
		      mes= 1;
		      break;
		}
  		
  		
  		return mes;
  	}
  	
}