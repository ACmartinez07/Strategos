package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoCalculoEstadoIniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.IniciativaPlanPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.portafolios.forms.GestionarPortafoliosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.PermisologiaUsuario;
import com.visiongc.framework.web.controles.SplitControl;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.taglib.interfaz.util.BarraAreaInfo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionarIniciativasAction
  extends VgcAction
{
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
    String titulo = null;
    if (url.toLowerCase().indexOf("portafolio") == -1)
    {
      titulo = "Iniciativas";
      Modulo modulo = new Modulo().getModuloActivo("C5DD8F17-963B-4175-A9A0-7D0D3754DFC0");
      if (modulo != null) {
        titulo = modulo.getModulo();
      }
      nombre = "Gestionar " + titulo;
      if (url.indexOf("/iniciativas/gestionarIniciativas.action") > -1) {
        navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
      } else {
        navBar.agregarUrlSinParametros(url, nombre);
      }
    }
  }
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    String forward = mapping.getParameter();
    if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
      getBarraAreas(request, "strategos").setBotonSeleccionado("iniciativas");
    } else {
      getBarraAreas(request, "strategos").setBotonSeleccionado("portafolios");
    }
    GestionarIniciativasForm gestionarIniciativasForm = (GestionarIniciativasForm)form;
    String source = request.getParameter("source") != null ? request.getParameter("source") : null;
    Boolean actualizarForma = Boolean.valueOf(request.getParameter("actualizarForma") != null ? Boolean.parseBoolean(request.getParameter("actualizarForma")) : false);
    if (actualizarForma.booleanValue()) {
      if (request.getSession().getAttribute("GuardarIndicador") == null) {
        request.getSession().setAttribute("GuardarIndicador", "true");
      }
    }
    Long portafolioId = null;
    GestionarPortafoliosForm gestionarPortafoliosForm = (GestionarPortafoliosForm)request.getSession().getAttribute("gestionarPortafoliosForm");
    if ((gestionarPortafoliosForm != null) && (forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")))
    {
      if (gestionarPortafoliosForm.getSeleccionadoId() != null) {
        portafolioId = Long.valueOf(Long.parseLong(gestionarPortafoliosForm.getSeleccionadoId()));
      } else {
        portafolioId = Long.valueOf(0L);
      }
      gestionarIniciativasForm.setSource("Portafolio");
    }
    Byte filtroHistorycoType = (gestionarIniciativasForm != null) && (gestionarIniciativasForm.getFiltro() != null) ? gestionarIniciativasForm.getFiltro().getHistorico() : null;
    
    String filtroNombre = "";
    
    if(gestionarIniciativasForm != null && gestionarIniciativasForm.getFiltro() != null && gestionarIniciativasForm.getFiltroNombre() != null){
    	filtroNombre = gestionarIniciativasForm.getFiltroNombre();
    }
    
    
    /* obtener el año actual y setear en el filtro */
    Calendar cal= Calendar.getInstance();
    int year= cal.get(Calendar.YEAR);
    
    String filtroAnio = (request.getParameter("filtroAnio") != null) ? request.getParameter("filtroAnio") : "";
        
    
    gestionarIniciativasForm.clear();
    
    Usuario user = getUsuarioConectado(request);
	Long usuarioId = user.getUsuarioId();
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
    Long iniciativaId = null;
    if (request.getParameter("iniciativaId") != null) {
      iniciativaId = Long.valueOf(Long.parseLong(request.getParameter("iniciativaId")));
    } else if ((gestionarPortafoliosForm != null) && (gestionarPortafoliosForm.getIniciativaId() != null) && (gestionarIniciativasForm.getSource().equals("Portafolio"))) {
      iniciativaId = gestionarPortafoliosForm.getIniciativaId();
    }
    Long planId = (request.getParameter("planId") != null) && (request.getParameter("planId") != "") ? Long.valueOf(Long.parseLong(request.getParameter("planId"))) : null;
    if ((planId == null) && (request.getSession().getAttribute("planActivoId") != null)) {
      planId = Long.valueOf(Long.parseLong((String)request.getSession().getAttribute("planActivoId")));
    }
    if ((gestionarPortafoliosForm != null) && (gestionarIniciativasForm.getSource().equals("Portafolio"))) {
      planId = null;
    }
    String nombre = filtroNombre != null ? filtroNombre : request.getParameter("filtroNombre") != null ? request.getParameter("filtroNombre") : "";
    String anio = filtroAnio != null ? filtroAnio : request.getParameter("filtroAnio") != null ? request.getParameter("filtroAnio") : "";
    Byte selectHitoricoType = Byte.valueOf(filtroHistorycoType != null ? filtroHistorycoType.byteValue() : (request.getParameter("selectHitoricoType") != null) && (request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado());
    Long selectEstatusType = (request.getParameter("selectEstatusType") != null) && (request.getParameter("selectEstatusType") != "") && (!request.getParameter("selectEstatusType").equals("0")) ? Long.valueOf(Long.parseLong(request.getParameter("selectEstatusType"))) : null;
    Long selectTipos = (request.getParameter("selectTipos") != null) && (request.getParameter("selectTipos") != "") && (!request.getParameter("selectTipos").equals("0")) ? Long.valueOf(Long.parseLong(request.getParameter("selectTipos"))) : null;

    
    FiltroForm filtro = new FiltroForm();
    filtro.setHistorico(selectHitoricoType);
    if (nombre.equals("")) {
      filtro.setNombre(null);
    } else {
      filtro.setNombre(nombre);
    }
    if (anio.equals("")) {
      filtro.setAnio(null);
    } else {
      filtro.setAnio(anio);
    }
    gestionarIniciativasForm.setFiltro(filtro);
    gestionarIniciativasForm.setEstatus(selectEstatusType);
    gestionarIniciativasForm.setTipo(selectTipos);
    gestionarIniciativasForm.setPortafolioId(portafolioId);
    
    // estatus
    
    StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
    Map<String, String> filtros = new HashMap();
    PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc", true, filtros);
    strategosIniciativaEstatusService.close();
    gestionarIniciativasForm.setTiposEstatus(paginaIniciativaEstatus.getLista());
    
    // tipos
    
    StrategosTipoProyectoService strategosTiposProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();
    Map<String, String> filtrosTipo = new HashMap();
    PaginaLista paginaTipos = strategosTiposProyectoService.getTiposProyecto(0, 0, "tipoProyectoId", "asc", true, filtrosTipo);
    strategosTiposProyectoService.close();
    gestionarIniciativasForm.setTipos(paginaTipos.getLista());
    
    ActionMessages messages = getMessages(request);
    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
    
    gestionarIniciativasForm.setVerForma(Boolean.valueOf(getPermisologiaUsuario(request).tienePermiso("INICIATIVA_VIEWALL")));
    gestionarIniciativasForm.setEditarForma(Boolean.valueOf(getPermisologiaUsuario(request).tienePermiso("INICIATIVA_EDIT")));
    if (source != null) {
      gestionarIniciativasForm.setSource(source);
    }
    if (planId != null) {
      gestionarIniciativasForm.setPlanId(planId);
    }
    if (iniciativaId != null)
    {
      gestionarIniciativasForm.setSeleccionadoId(iniciativaId.toString());
      Iniciativa iniciativaSeleccionada = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(gestionarIniciativasForm.getSeleccionadoId()));
      if (iniciativaSeleccionada != null) {
        organizacionId = iniciativaSeleccionada.getOrganizacionId().toString();
      }
    }
    gestionarIniciativasForm.setOrganizacionId(new Long(organizacionId));
    gestionarIniciativasForm.setIniciativaId(iniciativaId);
    
    filtros = new HashMap();
    
    String atributoOrden = gestionarIniciativasForm.getAtributoOrden();
    String tipoOrden = gestionarIniciativasForm.getTipoOrden();
    int paginaIniciativa = gestionarIniciativasForm.getPagina();
    if ((gestionarPortafoliosForm != null) && (gestionarIniciativasForm.getSource().equals("Portafolio"))) {
      organizacionId = null;
    }
    if (atributoOrden == null) {
      gestionarIniciativasForm.setAtributoOrden("nombre");
    }
    if (tipoOrden == null) {
      gestionarIniciativasForm.setTipoOrden("ASC");
    }
    if (paginaIniciativa < 1) {
      paginaIniciativa = 1;
    }
    
    if ((gestionarIniciativasForm.getFiltro().getHistorico() != null) && (gestionarIniciativasForm.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())) {
      filtros.put("historicoDate", "IS NULL");
    } else if ((gestionarIniciativasForm.getFiltro().getHistorico() != null) && (gestionarIniciativasForm.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())) {
      filtros.put("historicoDate", "IS NOT NULL");
    }
    
    if (gestionarIniciativasForm.getEstatus() != null) {
      filtros.put("estatusId", gestionarIniciativasForm.getEstatus().toString());
    }
    if (gestionarIniciativasForm.getTipo() != null) {
        filtros.put("tipoId", gestionarIniciativasForm.getTipo().toString());
    }
    
    
    // iniciativas filtro
    
    PaginaLista paginaResponsable = null;
    PaginaLista paginaIniciativas = null;
    
    StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

	Responsable responsable = null;
	
	
	
	Map<String, Object> filtroRes = new HashMap<String, Object>();
	filtroRes.put("usuarioId", usuarioId);
	
	List<Responsable> responsables = new ArrayList();
	
	paginaResponsable = strategosResponsablesService.getResponsables(paginaIniciativa, 1, atributoOrden, tipoOrden, true, filtroRes);
	
	responsables = paginaResponsable.getLista();
			
	Long responsableId= new Long(0);
	for(Responsable res: responsables) {
									
		if(res.getUsuarioId() != null) {
			responsableId = res.getResponsableId();
		}
	}

	
    
    //porcentaje esperado
    
    if(responsableId != 0) {
    	
    	
    	Map<String, Object> filtrosIni = new HashMap<String, Object>();
    	
    	filtrosIni.put("responsableCargarEjecutadoId", responsableId);
		filtrosIni.put("responsableLograrMetaId", responsableId);
		filtrosIni.put("responsableSeguimientoId", responsableId);
		
		 paginaIniciativas = strategosIniciativasService.getIniciativasResponsable(paginaIniciativa, 30, gestionarIniciativasForm.getAtributoOrden(), gestionarIniciativasForm.getTipoOrden(), true, filtrosIni);
		 paginaIniciativas.setTamanoSetPaginas(5);
		
		
    	StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    	StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
    	StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		
    	
    	for (Iterator<Iniciativa> iter = paginaIniciativas.getLista().iterator(); iter.hasNext(); ) 
    	{
    		Iniciativa iniciativa = (Iniciativa)iter.next();
    		if (iniciativa.getPorcentajeCompletado() != null)
    		{
    			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
    			if (indicador != null)
    			{
    				boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue());
    				
    				Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
    				if (medicionReal != null)
    				{
    					iniciativa.setUltimaMedicion(medicionReal.getValor());

    					List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null, medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
    					Double programado = null;
    					for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext(); ) 
    					{
    	            		Medicion medicion = (Medicion)iter2.next();
    	            		if (medicion.getValor() != null && programado == null)
    	            			programado = medicion.getValor();
    	            		else if (medicion.getValor() != null && programado != null && acumular)
    	            			programado = programado + medicion.getValor();
    	            		else if (medicion.getValor() != null && programado != null && !acumular)
    	            			programado = medicion.getValor();
    					}
    					
    					if (programado != null)
    						iniciativa.setPorcentajeEsperado(programado);
    				}
    			}
    		}
    		
    		//actividades
    		List<PryActividad> actividades = new ArrayList<PryActividad>();
			
			if(iniciativa.getProyectoId() != null){
				actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
			}
			
			
			 Date fechaAh = new Date();
			 Date fechaAc = new Date();
				
				
			 //fecha esperada
				
			 fechaAh = obtenerFechaFinal(actividades);
						   
		     //dias de atraso
				
			 if(fechaAh != null) {
				SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 	
				
				iniciativa.setFechaesperado(objSDF.format(fechaAh).toString()); 
				int milisecondsByDay = 86400000;
				int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
				int diasposi = dias * -1;
				
				iniciativa.setDias(diasposi);
					
			 }else {
				iniciativa.setDias(0);
			 }
			 
			 if(iniciativa.getEstatusId() == 2 && actividades.size() >0) {
				 iniciativa.setObservacion(debeRegistrar(actividades));
			 }
			 
			 
			 iniciativa.setOrganizacionNombre(iniciativa.getOrganizacion().getNombre());
						
    		
    	}
    	strategosIndicadoresService.close();
    	strategosMedicionesService.close();
    	
    	
	}else {
		
		List iniciativas = new ArrayList();
		   
		paginaIniciativas = new PaginaLista();
		    
		paginaIniciativas.setLista(iniciativas);
		paginaIniciativas.setNroPagina(1);
		paginaIniciativas.setTamanoPagina(29);
		paginaIniciativas.setTotal(0);
		paginaIniciativas.setOrden(atributoOrden);
		paginaIniciativas.setTipoOrden(tipoOrden);
				
	}

    
    ArrayList<Plan> listaPlanes = new ArrayList();
    PaginaLista paginaPlanes = new PaginaLista();
    
    paginaPlanes.setLista(listaPlanes);
    paginaPlanes.setNroPagina(1);
    paginaPlanes.setTamanoPagina(listaPlanes.size());
    paginaPlanes.setTamanoSetPaginas(1);
    paginaPlanes.setTotal(listaPlanes.size());
   
    /*
     if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
        forward = "mostrarGestionIniciativaAction";
     }
     */
     gestionarIniciativasForm.setTipoAlerta(TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorActividades());
    
    
    request.setAttribute("paginaIniciativas", paginaIniciativas);
    request.setAttribute("gestionarIniciativasPaginaPeriodos", paginaPlanes);
    
    
    strategosIniciativasService.close();
    if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
      SplitControl.setConfiguracion(request, "splitIniciativas");
    } else {
      gestionarIniciativasForm.setSource("portafolio");
    }
    return mapping.findForward(forward);
  }
  
  	public Date obtenerFechaFinal(List<PryActividad> actividades) {
		
		Date fecha = null;
		
		for(PryActividad act: actividades) {
			
			fecha = act.getFinPlan();
			
		}
		
		return fecha;
	}
  	
  	public String debeRegistrar(List<PryActividad> actividades) {
  		
  		Calendar fecha = Calendar.getInstance();
        int ano = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
		int periodo =0;
  		
  		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    	StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
  		
  		for(PryActividad act:actividades) {
  			
  			if(act.getIndicadorId() != null) {
  				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, act.getIndicadorId());
  				if(indicador != null) {
  					
  					periodo=obtenerPeriodoFrecuencia(mes, indicador.getFrecuencia());
  					List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ano, ano, periodo, periodo);
  					
  					if(mediciones.size() == 0) {
  						return "Debe Registrar";
  					}
  				} 				
  				
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
