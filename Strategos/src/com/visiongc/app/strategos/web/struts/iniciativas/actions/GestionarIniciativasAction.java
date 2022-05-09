package com.visiongc.app.strategos.web.struts.iniciativas.actions;

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
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarInstrumentosForm;
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
import com.visiongc.framework.web.controles.SplitControl;
import com.visiongc.framework.web.struts.forms.FiltroForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
    	if(forward.equals("gestionarInstrumentosIndicadoresIniciativasAction")) {
    		getBarraAreas(request, "strategos").setBotonSeleccionado("instrumentos");	
    	}else {
    		getBarraAreas(request, "strategos").setBotonSeleccionado("iniciativas");
    	}
    }    
    else {
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
    
    // portafolio
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
    
    //instrumentos
    Boolean instrumentos = false;
    Long instrumentoId = null;
    GestionarInstrumentosForm gestionarInstrumentoForm = (GestionarInstrumentosForm)request.getSession().getAttribute("gestionarInstrumentosForm");
    if((gestionarInstrumentoForm != null) && (forward.equals("gestionarInstrumentosIndicadoresIniciativasAction"))) {
    	instrumentos = true;
    	if(gestionarInstrumentoForm.getSeleccionados() != null) {
    		instrumentoId = Long.valueOf(Long.parseLong(gestionarInstrumentoForm.getSeleccionados()));
    	}else {
    		instrumentoId = Long.valueOf(0L);
    	}
    	gestionarIniciativasForm.setSource("instrumentos");
    }
    
    
    Byte filtroHistorycoType = (gestionarIniciativasForm != null) && (gestionarIniciativasForm.getFiltro() != null) ? gestionarIniciativasForm.getFiltro().getHistorico() : null;
    
    String filtroNombre = "";
    
    if(gestionarIniciativasForm != null && gestionarIniciativasForm.getFiltro() != null && gestionarIniciativasForm.getFiltroNombre() != null){
    	filtroNombre = gestionarIniciativasForm.getFiltroNombre();
    }
    
    
    /* obtener el a�o actual y setear en el filtro */
    Calendar cal= Calendar.getInstance();
    int year= cal.get(Calendar.YEAR);
    
    String filtroAnio = (request.getParameter("filtroAnio") != null) ? request.getParameter("filtroAnio") : "";
        
    
    gestionarIniciativasForm.clear();
    
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
    Long iniciativaId = null;
    if (request.getParameter("iniciativaId") != null) {
      iniciativaId = Long.valueOf(Long.parseLong(request.getParameter("iniciativaId")));
    } else if ((gestionarPortafoliosForm != null) && (gestionarPortafoliosForm.getIniciativaId() != null) && (gestionarIniciativasForm.getSource().equals("Portafolio"))) {
      iniciativaId = gestionarPortafoliosForm.getIniciativaId();
    }else if ((gestionarInstrumentoForm != null) && (gestionarInstrumentoForm.getIniciativaId() != null) && (gestionarIniciativasForm.getSource().equals("instrumentos"))) {
        iniciativaId = gestionarInstrumentoForm.getIniciativaId();
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
    Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();
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
    gestionarIniciativasForm.setInstrumentoId(instrumentoId);
    
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
    if(gestionarIniciativasForm.getSource().equals("instrumentos")) {
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
    if ((organizacionId != null) && (!organizacionId.equals("")) && (gestionarIniciativasForm.getSource() != null) && (!gestionarIniciativasForm.getSource().equals("Plan"))) {
      filtros.put("organizacionId", organizacionId);
    }
    if ((planId != null) && (planId.longValue() != 0L)) {
      filtros.put("planId", planId.toString());
    }
    if ((gestionarIniciativasForm.getIniciativaId() != null) && (gestionarIniciativasForm.getSource() != null) && (gestionarIniciativasForm.getSource().equals("Plan")) && (gestionarIniciativasForm.getPlanId() == null)) {
      filtros.put("iniciativaId", gestionarIniciativasForm.getIniciativaId().toString());
    }
    
    if (gestionarIniciativasForm.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
		filtros.put("historicoDate", "IS NULL");
	else
		filtros.put("historicoDate", "IS NOT NULL");
    
    if (gestionarIniciativasForm.getFiltro().getNombre() != null) {
      filtros.put("nombre", gestionarIniciativasForm.getFiltro().getNombre());
    }
    if (gestionarIniciativasForm.getFiltro().getAnio() != null) {
      filtros.put("anio", gestionarIniciativasForm.getFiltro().getAnio());
    }
    if (gestionarIniciativasForm.getEstatus() != null) {
      filtros.put("estatusId", gestionarIniciativasForm.getEstatus().toString());
    }
    if (gestionarIniciativasForm.getTipo() != null) {
        filtros.put("tipoId", gestionarIniciativasForm.getTipo().toString());
    }
    if (gestionarIniciativasForm.getPortafolioId() != null) {
      filtros.put("portafolioId", gestionarIniciativasForm.getPortafolioId().toString());
    }
    if (gestionarIniciativasForm.getInstrumentoId() != null) {
      filtros.put("instrumentoId", gestionarIniciativasForm.getInstrumentoId().toString());
    }
    PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(paginaIniciativa, 30, gestionarIniciativasForm.getAtributoOrden(), gestionarIniciativasForm.getTipoOrden(), true, filtros);
    paginaIniciativas.setTamanoSetPaginas(5);
    
    //porcentaje esperado
    
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	
	for (Iterator<Iniciativa> iter = paginaIniciativas.getLista().iterator(); iter.hasNext(); ) 
	{
		Iniciativa iniciativa = (Iniciativa)iter.next();
		OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosIniciativasService.load(OrganizacionStrategos.class, new Long(iniciativa.getOrganizacionId()));
		iniciativa.setOrganizacion(organizacion);
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
	}
	strategosIndicadoresService.close();
	strategosMedicionesService.close();
	
	
    
    
    ArrayList<Plan> listaPlanes = new ArrayList();
    PaginaLista paginaPlanes = new PaginaLista();
    
    
   
    
    paginaPlanes.setLista(listaPlanes);
    paginaPlanes.setNroPagina(1);
    paginaPlanes.setTamanoPagina(listaPlanes.size());
    paginaPlanes.setTamanoSetPaginas(1);
    paginaPlanes.setTotal(listaPlanes.size());
    if (paginaIniciativas.getLista().size() > 0)
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Iniciativa.Lista", "InicitivaId");
      Long iniciativaIdFocus = null;
      boolean iniciativaEnLaLista = false;
      if (configuracionUsuario != null)
      {
        iniciativaIdFocus = new Long(configuracionUsuario.getData());
        for (Iterator<Iniciativa> iter = paginaIniciativas.getLista().iterator(); iter.hasNext();)
        {
          Iniciativa iniciativa = (Iniciativa)iter.next();
          if (iniciativa.getIniciativaId().longValue() == iniciativaIdFocus.longValue())
          {
            iniciativaEnLaLista = true;
            break;
          }
        }
      }
      if ((gestionarIniciativasForm.getSeleccionadoId() == null) || (gestionarIniciativasForm.getSeleccionadoId().equals("")))
      {
        iniciativaId = ((Iniciativa)paginaIniciativas.getLista().get(0)).getIniciativaId();
        if (!iniciativaEnLaLista)
        {
          iniciativaIdFocus = iniciativaId;
          iniciativaEnLaLista = true;
        }
        else
        {
          iniciativaId = iniciativaIdFocus;
          iniciativaEnLaLista = false;
        }
        gestionarIniciativasForm.setSeleccionadoId(iniciativaId.toString());
      }
      else
      {
        iniciativaIdFocus = new Long(gestionarIniciativasForm.getSeleccionadoId());
        iniciativaEnLaLista = true;
      }
      if (iniciativaEnLaLista)
      {
        if (configuracionUsuario == null)
        {
          configuracionUsuario = new ConfiguracionUsuario();
          ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
          pk.setConfiguracionBase("Strategos.Foco.Iniciativa.Lista");
          pk.setObjeto("InicitivaId");
          pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
          configuracionUsuario.setPk(pk);
        }
        configuracionUsuario.setData(iniciativaIdFocus.toString());
        frameworkService.saveConfiguracionUsuario(configuracionUsuario, getUsuarioConectado(request));
      }
      frameworkService.close();
      
      Iniciativa iniciativaSeleccionada = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(gestionarIniciativasForm.getSeleccionadoId()));
      if (iniciativaSeleccionada != null)
      {
        gestionarIniciativasForm.setNombreIniciativaSeleccionada(iniciativaSeleccionada.getNombre());
        gestionarIniciativasForm.setTipoAlerta(iniciativaSeleccionada.getTipoAlerta());
        
        Set<IniciativaPlan> iniciativaPlanes = iniciativaSeleccionada.getIniciativaPlanes();
        StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
        for (Iterator<IniciativaPlan> iter = iniciativaPlanes.iterator(); iter.hasNext();)
        {
          IniciativaPlan iniciativaPlan = (IniciativaPlan)iter.next();
          
          
          if (!listaPlanes.contains(iniciativaPlan.getPlan()))
          {
            Plan plan = (Plan)strategosPlanesService.load(Plan.class, iniciativaPlan.getPk().getPlanId());
            OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPlanesService.load(OrganizacionStrategos.class, plan.getOrganizacionId());
            plan.setOrganizacion(organizacion);
            
            listaPlanes.add(plan);
          }
        }
        strategosPlanesService.close();
        paginaPlanes.setTamanoPagina(listaPlanes.size());
        paginaPlanes.setTotal(listaPlanes.size());
       
        if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
          forward = "mostrarGestionIniciativaAction";
        }else if(forward.equals("gestionarInstrumentosIndicadoresIniciativasAction")) {
          forward = "gestionarInstrumentosIndicadoresIniciativasAction";	
        }
      }
      else
      {
        iniciativaId = ((Iniciativa)paginaIniciativas.getLista().get(0)).getIniciativaId();
        gestionarIniciativasForm.setSeleccionadoId(iniciativaId.toString());
        iniciativaSeleccionada = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(gestionarIniciativasForm.getSeleccionadoId()));
        if (iniciativaSeleccionada != null)
        {
          gestionarIniciativasForm.setNombreIniciativaSeleccionada(iniciativaSeleccionada.getNombre());
          gestionarIniciativasForm.setTipoAlerta(iniciativaSeleccionada.getTipoAlerta());
          
          Set<IniciativaPlan> iniciativaPlanes = iniciativaSeleccionada.getIniciativaPlanes();
          StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
          for (Iterator<IniciativaPlan> iter = iniciativaPlanes.iterator(); iter.hasNext();)
          {
            IniciativaPlan iniciativaPlan = (IniciativaPlan)iter.next();
            
            if (!listaPlanes.contains(iniciativaPlan.getPlan()))
            {
              Plan plan = (Plan)strategosPlanesService.load(Plan.class, iniciativaPlan.getPk().getPlanId());
              OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosPlanesService.load(OrganizacionStrategos.class, plan.getOrganizacionId());
              plan.setOrganizacion(organizacion);
              
              listaPlanes.add(plan);
            }
          }
          strategosPlanesService.close();
          paginaPlanes.setTamanoPagina(listaPlanes.size());
          paginaPlanes.setTotal(listaPlanes.size());
          
        }
        if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
          	
          if ((gestionarIniciativasForm.getSource() != null) && (gestionarIniciativasForm.getSource().equals("Plan"))) {
            forward = "gestionarIniciativasAction";
          } else {
            forward = "mostrarGestionIniciativaAction";
          }
        }else if (!forward.equals("gestionarInstrumentosIndicadoresIniciativasAction")) {
          	
          if ((gestionarIniciativasForm.getSource() != null) && (gestionarIniciativasForm.getSource().equals("Plan"))) {
            forward = "gestionarIniciativasAction";
          } else {
            forward = "mostrarGestionIniciativaAction";
          }
        }
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.gestionariniciativas.iniciativaeliminada"));
        saveMessages(request, messages);
      }
    }
    else
    {
      if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
        forward = "mostrarGestionIniciativaAction";
      }
      if(forward.equals("gestionarInstrumentosIndicadoresIniciativasAction")) {
    	forward = "gestionarInstrumentosIndicadoresIniciativasAction"; 
      }
      gestionarIniciativasForm.setTipoAlerta(TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorActividades());
    }
    request.setAttribute("paginaIniciativas", paginaIniciativas);
    request.setAttribute("gestionarIniciativasPaginaPeriodos", paginaPlanes);
    
    if (listaPlanes.size() > 0)
    {
      Plan plan = (Plan)listaPlanes.get(0);
      request.setAttribute("gestionarIniciativas.seleccionadosIniciativaPeriodos", plan.getPlanId().toString());
      request.setAttribute("gestionarIniciativas.objetivo", obtenerObjetivo(iniciativaId, plan.getPlanId()));
    }
    strategosIniciativasService.close();
    if (!forward.equals("gestionarPortafoliosIndicadoresIniciativaAction")) {
      SplitControl.setConfiguracion(request, "splitIniciativas");
    }
    else if(forward.equals("gestionarInstrumentosIndicadoresIniciativasAction")) {
    	gestionarIniciativasForm.setSource("instrumentos");
    }
    else {
      gestionarIniciativasForm.setSource("portafolio");
    }
    
    if(instrumentos) {
    	forward = "gestionarInstrumentosIndicadoresIniciativasAction"; 
    }
    
    return mapping.findForward(forward);
  }
  
  public String obtenerObjetivo(Long iniciativaId, Long planId) throws SQLException{
	  
		String objetivo="";
		Long id=iniciativaId;
		
		if(id != null && planId != null){
		
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			
			Iniciativa ini = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(id));
			
			
			if((ini.getIniciativaPerspectivas() != null) && (ini.getIniciativaPerspectivas().size() > 0)){
				
			  IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)ini.getIniciativaPerspectivas().toArray()[0];
	          StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
	          Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
	          
	        	  objetivo= perspectiva.getNombre();
	         
			}
			strategosIniciativasService.close();
		}
		
		return objetivo;
  }
  
  
}
