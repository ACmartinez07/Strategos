package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaDatosBasicosXls extends VgcAction {
	
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
	super.execute(mapping, form, request, response);
		
			String forward = mapping.getParameter();	
		    
			MessageResources mensajes = getResources(request);
			ReporteForm reporte = new ReporteForm();
			reporte.clear();
			String alcance = (request.getParameter("alcance"));
			String orgId = (request.getParameter("organizacionId"));
			String tipo = (request.getParameter("tipo"));
			String estatus = (request.getParameter("estatus"));	
			String ano = (request.getParameter("ano"));		
			int estatusId = Integer.parseInt(estatus);
			String todos = (request.getParameter("todos"));	
			
			Calendar fecha = Calendar.getInstance();
	        int anoTemp = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;
			
			Map<String, Object> filtros = new HashMap<String, Object>();
			Paragraph texto;
			
			StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
			
			List<OrganizacionStrategos> organizaciones = organizacionservice.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();
			
			
			//
			if(request.getParameter("alcance").equals("1")){
				
				int x=1;
				int numeroCelda=3;
				
				
				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtro = new FiltroForm();
				filtro.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtro.setNombre(null);
				else
					filtro.setNombre(filtroNombre);
				reporte.setFiltro(filtro);
				filtro.setAnio(""+ano);
			    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
			    	filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
					filtros.put("historicoDate", "IS NULL");
				else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
					filtros.put("historicoDate", "IS NOT NULL");
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if(!tipo.equals("0")) {
					filtros.put("tipoId", tipo);
				}
				if(todos.equals("false")) {
					filtros.put("anio", ano);
				}
				
				List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
		    	
		        MessageResources messageResources = getResources(request);
		        
		        Object[][] data = new Object[24][5];
			    
			    
			
		    	data[0][0]=messageResources.getMessage("Formato Formulacion de proyectos Estrategicos");
		    	data[1][0]=messageResources.getMessage("Reg.asdasd");
		    	data[2][0]=messageResources.getMessage("Datos Basicos");
		    	data[3][0]=messageResources.getMessage("Dependencia");
		    	data[3][2]=messageResources.getMessage("Responsable del proyecto");
		    	data[3][4]=messageResources.getMessage("Cargo");
		    	data[4][0]=messageResources.getMessage("Nombre del proyecto");
		    	data[4][4]=messageResources.getMessage("Vigencia");
		    	data[5][0]=messageResources.getMessage("Objetivo estrategico");
		    	data[5][3]=messageResources.getMessage("Iniciativa estrtegica");
		    	data[6][0]=messageResources.getMessage("Lider iniciativa");
		    	data[6][3]=messageResources.getMessage("Tipo iniciativa");
		    	
		
		    	
		    	if (iniciativas.size() > 0)
				{
					for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
					{
						Iniciativa iniciativa = (Iniciativa)iter.next();
						
						StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
						StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
						StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
						
						Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
						
						List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
						List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
						
						List<PryActividad> actividades = new ArrayList<PryActividad>();
						
						
						if(iniciativa.getProyectoId() != null){
							actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
						}
						
						if(estatusId == 8) {
							
							data[x][0]=iniciativa.getNombre();
							
							if(iniciativa.getFrecuenciaNombre() != null) {
								data[x][1]=iniciativa.getFrecuenciaNombre();
							}else {
								data[x][1]=("");
							}
							
							if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
								data[x][2]=iniciativa.getPorcentajeCompletadoFormateado();
								data[x][3]=iniciativa.getPorcentajeCompletadoFormateado();
							}else {
								data[x][2]=("");
								data[x][3]=("");
							}
							
							if(iniciativa.getFechaUltimaMedicion() != null){
								data[x][4]=iniciativa.getFechaUltimaMedicion();
							}
							else{
								data[x][4]=("");
							}
							
							
							Date fechaAh = new Date();
							Date fechaAc = new Date();
								
							
							fechaAh = obtenerFechaFinal(actividades);
							
							if(fechaAh != null) {
								
								SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
								
								data[x][5]=objSDF.format(fechaAh);
														
								int milisecondsByDay = 86400000;
								
								int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
								
								int diasposi = dias * -1;
										
								data[x][6]=""+diasposi;
								
							}else {
								data[x][5]="";
								data[x][6]="";
							}
							
							
													
							//estatus
							if (medicionesProgramadas.size() == 0) {
								//EstatusIniciar
								data[x][7]=messageResources.getMessage("estado.sin.iniciar");
							}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
								//EstatusIniciardesfasado
								data[x][7]=messageResources.getMessage("estado.sin.iniciar.desfasada");
							}					
							else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
								//EnEjecucionSinRetrasos
								data[x][7]=messageResources.getMessage("estado.en.ejecucion.sin.retrasos");
							}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
								//EnEjecucionConRetrasosSuperables
								data[x][7]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables");
							}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
								//EnEjecucionConRetrasosSignificativos
								data[x][7]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos");
							}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
								//EstatusConcluidas
								data[x][7]=messageResources.getMessage("estado.concluidas");
							}
							else if(iniciativa.getEstatusId() == 3) {
								//EstatusCancelado
								data[x][7]="Cancelado";
							}
							else if(iniciativa.getEstatusId() == 4) {
								//EstatusSuspendido
								data[x][7]="Suspendido";
							}else {
								//EstatusIniciar
								data[x][7]=messageResources.getMessage("estado.sin.iniciar");
							}
							
							if(iniciativa.getTipoProyecto()==null){
								data[x][8]="";
							}else {
								data[x][8]=iniciativa.getTipoProyecto().getNombre();
							}
							
							if(iniciativa.getAnioFormulacion() == null) {
								data[x][9]="";
							}else {
								data[x][9]=iniciativa.getAnioFormulacion();
							}
							
						
							//responsable ejecutar
							
							if(iniciativa.getResponsableCargarEjecutado() ==null){
								data[x][10]="";
							}
							else{
								data[x][10]=iniciativa.getResponsableCargarEjecutado().getNombre();
							}
							
							//responsable lograr meta
							
							if(iniciativa.getResponsableLograrMeta() ==null){
								data[x][11]="";
							}
							else{
								data[x][11]=iniciativa.getResponsableLograrMeta().getNombre();
							}
																		
							x=x+1;
						}else {
							Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
							
							if(est) {
								
								data[x][0]=iniciativa.getNombre();
								
								if(iniciativa.getFrecuenciaNombre() != null) {
									data[x][1]=iniciativa.getFrecuenciaNombre();
								}else {
									data[x][1]=("");
								}
								
								if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
									data[x][2]=iniciativa.getPorcentajeCompletadoFormateado();
									data[x][3]=iniciativa.getPorcentajeCompletadoFormateado();
								}else {
									data[x][2]=("");
									data[x][3]=("");
								}
								
								if(iniciativa.getFechaUltimaMedicion() != null){
									data[x][4]=iniciativa.getFechaUltimaMedicion();
								}
								else{
									data[x][4]=("");
								}
								
								
								Date fechaAh = new Date();
								Date fechaAc = new Date();
									
								
								fechaAh = obtenerFechaFinal(actividades);
								
								if(fechaAh != null) {
									
									SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
									
									data[x][5]=objSDF.format(fechaAh);
															
									int milisecondsByDay = 86400000;
									
									int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
									
									int diasposi = dias * -1;
											
									data[x][6]=""+diasposi;
									
								}else {
									data[x][5]="";
									data[x][6]="";
								}
								
								
														
								//estatus
								if (medicionesProgramadas.size() == 0) {
									//EstatusIniciar
									data[x][7]=messageResources.getMessage("estado.sin.iniciar");
								}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
									//EstatusIniciardesfasado
									data[x][7]=messageResources.getMessage("estado.sin.iniciar.desfasada");
								}					
								else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
									//EnEjecucionSinRetrasos
									data[x][7]=messageResources.getMessage("estado.en.ejecucion.sin.retrasos");
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
									//EnEjecucionConRetrasosSuperables
									data[x][7]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables");
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									//EnEjecucionConRetrasosSignificativos
									data[x][7]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos");
								}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
									//EstatusConcluidas
									data[x][7]=messageResources.getMessage("estado.concluidas");
								}
								else if(iniciativa.getEstatusId() == 3) {
									//EstatusCancelado
									data[x][7]="Cancelado";
								}
								else if(iniciativa.getEstatusId() == 4) {
									//EstatusSuspendido
									data[x][7]="Suspendido";
								}else {
									//EstatusIniciar
									data[x][7]=messageResources.getMessage("estado.sin.iniciar");
								}
								
								if(iniciativa.getTipoProyecto()==null){
									data[x][8]="";
								}else {
									data[x][8]=iniciativa.getTipoProyecto().getNombre();
								}
								
								if(iniciativa.getAnioFormulacion() == null) {
									data[x][9]="";
								}else {
									data[x][9]=iniciativa.getAnioFormulacion();
								}
								
							
								//responsable ejecutar
								
								if(iniciativa.getResponsableCargarEjecutado() ==null){
									data[x][10]="";
								}
								else{
									data[x][10]=iniciativa.getResponsableCargarEjecutado().getNombre();
								}
								
								//responsable lograr meta
								
								if(iniciativa.getResponsableLograrMeta() ==null){
									data[x][11]="";
								}
								else{
									data[x][11]=iniciativa.getResponsableLograrMeta().getNombre();
								}
																			
								x=x+1;
							}
						}
						
						
						
					}
				}
		    	
		    	HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");

				
				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = sheet.createRow(0);
		        
		        String header = "Reporte Iniciativas Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		        		        
		        HSSFRow OrgRow = sheet.createRow(2);
		        
		        
		        OrganizacionStrategos org = (OrganizacionStrategos)organizacionservice.load(OrganizacionStrategos.class,  ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				
				if(org != null) {
					 String nombre = "Organizaci�n: "+org.getNombre();
				     HSSFCell cellOrg = OrgRow.createCell(0);
				     cellOrg.setCellStyle(headerStyle);
				     cellOrg.setCellValue(nombre);
				}
		        		       
		        
		        for (int i = 0; i < data.length; ++i) {
		            HSSFRow dataRow = sheet.createRow(numeroCelda +1);

		            Object[] d = data[i];
		            String iniciativaName = (String) d[0];
		            String frecuencia = (String) d[1];
		            String ejecutado=(String)d[2];
		            String programado = (String) d[3];
		            String actualizacion = (String) d[4];
		            String actesperada =(String) d[5];
		            String dias = (String) d[6];
		            String estatusIn = (String) d[7];
		            String tipoIn = (String) d[8];
		            String anio = (String) d[9];
		            String responsable = (String) d[10];
		            String resplograr = (String) d[11];

		           
		            
		            dataRow.createCell(0).setCellValue(iniciativaName);
		            dataRow.createCell(1).setCellValue(frecuencia);
		            dataRow.createCell(2).setCellValue(ejecutado);
		            dataRow.createCell(3).setCellValue(programado);
		            dataRow.createCell(4).setCellValue(actualizacion);
		            dataRow.createCell(5).setCellValue(actesperada);
		            dataRow.createCell(6).setCellValue(dias);
		            dataRow.createCell(7).setCellValue(estatusIn);
		            dataRow.createCell(8).setCellValue(tipoIn);
		            dataRow.createCell(9).setCellValue(anio);
		            dataRow.createCell(10).setCellValue(responsable);
		            dataRow.createCell(11).setCellValue(resplograr);
		            
		            numeroCelda=numeroCelda+1;
		        }
		        
		        HSSFRow dataRow = sheet.createRow(numeroCelda+1);   
		        
		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="IniciativasResumido_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
		        
			}
			
			else if(request.getParameter("alcance").equals("4")) {
				
				Map<String, Object> filtro = new HashMap<String, Object>();
				
				filtro.put("padreId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				
				List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId(), true);
				
				
				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtrou = new FiltroForm();
				filtrou.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtrou.setNombre(null);
				else
					filtrou.setNombre(filtroNombre);
				reporte.setFiltro(filtrou);
				
			
				
				
				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");

				
				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = sheet.createRow(0);
		        
		        String header = "Reporte Iniciativas Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		       
		        
		        filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
					filtros.put("historicoDate", "IS NULL");
				else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
					filtros.put("historicoDate", "IS NOT NULL");
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if(!tipo.equals("0")) {
					filtros.put("tipoId", tipo);
				}
				if(todos.equals("false")) {
					filtros.put("anio", ano);
				}
				 
		        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
		               
		        int index = iniciativas.size();       
		        
		        MessageResources messageResources = getResources(request);
		        
		        HSSFRow dataRow = sheet.createRow(1);
				dataRow.createCell(0).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
	            dataRow.createCell(1).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre"));
	            dataRow.createCell(2).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia"));
	            dataRow.createCell(3).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado"));
	            dataRow.createCell(4).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado"));
	            dataRow.createCell(5).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion"));
	            dataRow.createCell(6).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada"));
	            dataRow.createCell(7).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.dias"));
	            dataRow.createCell(8).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.estatus"));
	            dataRow.createCell(9).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.tipoproyecto"));
	            dataRow.createCell(10).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
	            dataRow.createCell(11).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable"));
	            dataRow.createCell(12).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.lograr"));
	            int x=1;		        
		        
			        if (iniciativas.size() > 0)
					{
						for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
						{
							Iniciativa iniciativa = (Iniciativa)iter.next();
							
							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
							
							Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
							
							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
							
							List<PryActividad> actividades = new ArrayList<PryActividad>();
							
							
							if(iniciativa.getProyectoId() != null){
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}
							
									
							if(estatusId == 8) {
								
				
								HSSFRow dataRow1 = sheet.createRow(x+1);
															
								
								dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
					            dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
					            
					            if(iniciativa.getFrecuenciaNombre() != null) {
					            	dataRow1.createCell(2).setCellValue(iniciativa.getFrecuenciaNombre());
								}else {
									dataRow1.createCell(2).setCellValue("");
								}
								
								if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
									dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
									dataRow1.createCell(4).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
								}else {
									dataRow1.createCell(3).setCellValue("");
									dataRow1.createCell(4).setCellValue("");
								}
								
								
																			
								
								if(iniciativa.getFechaUltimaMedicion() != null){
									dataRow1.createCell(5).setCellValue(iniciativa.getFechaUltimaMedicion());
								}
								else{
									dataRow1.createCell(5).setCellValue("");
								}
								
								
								Date fechaAh = new Date();
								Date fechaAc = new Date();
								
								fechaAh = obtenerFechaFinal(actividades);
								
								if(fechaAh != null) {
									
									SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
									
									String fechaTemp =objSDF.format(fechaAh);
									
																			
									dataRow1.createCell(6).setCellValue(fechaTemp);
						            
									int milisecondsByDay = 86400000;
									
									int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
									
									int diasposi = dias * -1;
									
									dataRow1.createCell(7).setCellValue(""+diasposi);
								}else {
									dataRow1.createCell(6).setCellValue("");
									dataRow1.createCell(7).setCellValue("");
								}
								
								
								
								//estatus
								if (medicionesProgramadas.size() == 0) {
									//EstatusIniciar
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
								}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
									//EstatusIniciardesfasado
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
								}					
								else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
									//EnEjecucionSinRetrasos
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
									//EnEjecucionConRetrasosSuperables
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									//EnEjecucionConRetrasosSignificativos
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
								}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
									//EstatusConcluidas
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.concluidas"));
								}
								else if(iniciativa.getEstatusId() == 3) {
									//EstatusCancelado
									dataRow1.createCell(8).setCellValue("Cancelado");
								}
								else if(iniciativa.getEstatusId() == 4) {
									//EstatusSuspendido
									dataRow1.createCell(8).setCellValue("Suspendido");
								}else {
									//EstatusIniciar
									dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
								}
								
								if(iniciativa.getTipoProyecto()==null){
									dataRow1.createCell(9).setCellValue("");
								}else {
									dataRow1.createCell(9).setCellValue(iniciativa.getTipoProyecto().getNombre());
								}
								
								if(iniciativa.getAnioFormulacion() == null) {
									dataRow1.createCell(10).setCellValue("");
								}else {
									dataRow1.createCell(10).setCellValue(iniciativa.getAnioFormulacion());
								}
							
								//responsable ejecutar
								
								if(iniciativa.getResponsableCargarEjecutado() ==null){
									dataRow1.createCell(11).setCellValue("");
								}
								else{
									dataRow1.createCell(11).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
								}
								
								//responsable lograr meta
								
								if(iniciativa.getResponsableLograrMeta() ==null){
									dataRow1.createCell(12).setCellValue("");
								}
								else{
									dataRow1.createCell(12).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
								}
					            
					            
								x=x+1;
								
							}else {
								
								Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
								
								if(est) {
									
									String ruta=null;
									HSSFRow dataRow1 = sheet.createRow(x+1);
																	
									
									dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
						            dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
						            
						            if(iniciativa.getFrecuenciaNombre() != null) {
						            	dataRow1.createCell(2).setCellValue(iniciativa.getFrecuenciaNombre());
									}else {
										dataRow1.createCell(2).setCellValue("");
									}
									
									if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
										dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
										dataRow1.createCell(4).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
									}else {
										dataRow1.createCell(3).setCellValue("");
										dataRow1.createCell(4).setCellValue("");
									}
									
									
																				
									
									if(iniciativa.getFechaUltimaMedicion() != null){
										dataRow1.createCell(5).setCellValue(iniciativa.getFechaUltimaMedicion());
									}
									else{
										dataRow1.createCell(5).setCellValue("");
									}
									
									
									Date fechaAh = new Date();
									Date fechaAc = new Date();
									
									fechaAh = obtenerFechaFinal(actividades);
									
									if(fechaAh != null) {
										
										SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
										
										String fechaTemp =objSDF.format(fechaAh);
										
																				
										dataRow1.createCell(6).setCellValue(fechaTemp);
							            
										int milisecondsByDay = 86400000;
										
										int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
										
										int diasposi = dias * -1;
										
										dataRow1.createCell(7).setCellValue(""+diasposi);
									}else {
										dataRow1.createCell(6).setCellValue("");
										dataRow1.createCell(7).setCellValue("");
									}
									
									
									
									//estatus
									if (medicionesProgramadas.size() == 0) {
										//EstatusIniciar
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
									}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
										//EstatusIniciardesfasado
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
									}					
									else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
										//EnEjecucionSinRetrasos
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
									}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
										//EnEjecucionConRetrasosSuperables
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
									}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
										//EnEjecucionConRetrasosSignificativos
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
									}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
										//EstatusConcluidas
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.concluidas"));
									}
									else if(iniciativa.getEstatusId() == 3) {
										//EstatusCancelado
										dataRow1.createCell(8).setCellValue("Cancelado");
									}
									else if(iniciativa.getEstatusId() == 4) {
										//EstatusSuspendido
										dataRow1.createCell(8).setCellValue("Suspendido");
									}else {
										//EstatusIniciar
										dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
									}
									
									if(iniciativa.getTipoProyecto()==null){
										dataRow1.createCell(9).setCellValue("");
									}else {
										dataRow1.createCell(9).setCellValue(iniciativa.getTipoProyecto().getNombre());
									}
									
									if(iniciativa.getAnioFormulacion() == null) {
										dataRow1.createCell(10).setCellValue("");
									}else {
										dataRow1.createCell(10).setCellValue(iniciativa.getAnioFormulacion());
									}
								
									//responsable ejecutar
									
									if(iniciativa.getResponsableCargarEjecutado() ==null){
										dataRow1.createCell(11).setCellValue("");
									}
									else{
										dataRow1.createCell(11).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
									}
									
									//responsable lograr meta
									
									if(iniciativa.getResponsableLograrMeta() ==null){
										dataRow1.createCell(12).setCellValue("");
									}
									else{
										dataRow1.createCell(12).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
									}
						            
						            
									x=x+1;
									
								}
								
								
							}
							
							
						}
					}
		      
		        
		        if(organizacionesSub.size() > 0 || organizacionesSub != null) {
					       
			            
			    	 for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();)
						{
							
							OrganizacionStrategos organizacion = (OrganizacionStrategos)iter.next();
						
						    filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
							if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
								filtros.put("historicoDate", "IS NULL");
							else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
								filtros.put("historicoDate", "IS NOT NULL");
							if (reporte.getFiltro().getNombre() != null)
								filtros.put("nombre", reporte.getFiltro().getNombre());
							if(!tipo.equals("0")) {
								filtros.put("tipoId", tipo);
							}
							if(todos.equals("false")) {
								filtros.put("anio", ano);
							}
						
							
					        List<Iniciativa> iniciativasSub = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
				            
					         
					    	
					    	
					    	 if (iniciativasSub.size() > 0)
								{
					    		 
					    		 
									for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();)
									{
										Iniciativa iniciativa = (Iniciativa)iter1.next();
										
										StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
										StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
										StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
										
										Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
											
										List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
										List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
										List<PryActividad> actividades = new ArrayList<PryActividad>();
										
										
										if(iniciativa.getProyectoId() != null){
											actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
										}
										
										if(estatusId == 8) {
											
											String ruta=null;
											HSSFRow dataRow1 = sheet.createRow(x+1);
											OrganizacionStrategos org= new OrganizacionStrategos();
											ruta=organizacion.getNombre()+"/";
											org=organizacion.getPadre();
											while(org !=null){
												ruta=org.getNombre()+"/"+ruta;
												if(org.getPadre()==null){
													org = null;
												}
												else{
													org=org.getPadre();
												}
											}
											
											
											dataRow1.createCell(0).setCellValue(ruta);
								            dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
								            
								            if(iniciativa.getFrecuenciaNombre() != null) {
								            	dataRow1.createCell(2).setCellValue(iniciativa.getFrecuenciaNombre());
											}else {
												dataRow1.createCell(2).setCellValue("");
											}
											
											if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
												dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
												dataRow1.createCell(4).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
											}else {
												dataRow1.createCell(3).setCellValue("");
												dataRow1.createCell(4).setCellValue("");
											}
											
											
																						
											
											if(iniciativa.getFechaUltimaMedicion() != null){
												dataRow1.createCell(5).setCellValue(iniciativa.getFechaUltimaMedicion());
											}
											else{
												dataRow1.createCell(5).setCellValue("");
											}
											
											
											Date fechaAh = new Date();
											Date fechaAc = new Date();
											
											fechaAh = obtenerFechaFinal(actividades);
											
											if(fechaAh != null) {
												
												SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
												
												String fechaTemp =objSDF.format(fechaAh);
												
																						
												dataRow1.createCell(6).setCellValue(fechaTemp);
									            
												int milisecondsByDay = 86400000;
												
												int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
												
												int diasposi = dias * -1;
												
												dataRow1.createCell(7).setCellValue(""+diasposi);
											}else {
												dataRow1.createCell(6).setCellValue("");
												dataRow1.createCell(7).setCellValue("");
											}
											
											
											
											//estatus
											if (medicionesProgramadas.size() == 0) {
												//EstatusIniciar
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
												//EstatusIniciardesfasado
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
											}					
											else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
												//EnEjecucionSinRetrasos
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
												//EnEjecucionConRetrasosSuperables
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
												//EnEjecucionConRetrasosSignificativos
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
											}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
												//EstatusConcluidas
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.concluidas"));
											}
											else if(iniciativa.getEstatusId() == 3) {
												//EstatusCancelado
												dataRow1.createCell(8).setCellValue("Cancelado");
											}
											else if(iniciativa.getEstatusId() == 4) {
												//EstatusSuspendido
												dataRow1.createCell(8).setCellValue("Suspendido");
											}else {
												//EstatusIniciar
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}
											
											if(iniciativa.getTipoProyecto()==null){
												dataRow1.createCell(9).setCellValue("");
											}else {
												dataRow1.createCell(9).setCellValue(iniciativa.getTipoProyecto().getNombre());
											}
											
											if(iniciativa.getAnioFormulacion() == null) {
												dataRow1.createCell(10).setCellValue("");
											}else {
												dataRow1.createCell(10).setCellValue(iniciativa.getAnioFormulacion());
											}
										
											//responsable ejecutar
											
											if(iniciativa.getResponsableCargarEjecutado() ==null){
												dataRow1.createCell(11).setCellValue("");
											}
											else{
												dataRow1.createCell(11).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
											}
											
											//responsable lograr meta
											
											if(iniciativa.getResponsableLograrMeta() ==null){
												dataRow1.createCell(12).setCellValue("");
											}
											else{
												dataRow1.createCell(12).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
											}
								            
								            
											x=x+1;
											
										}else {
											Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
											
											if(est) {
												
												String ruta=null;
												HSSFRow dataRow1 = sheet.createRow(x+1);
												OrganizacionStrategos org= new OrganizacionStrategos();
												ruta=organizacion.getNombre()+"/";
												org=organizacion.getPadre();
												while(org !=null){
													ruta=org.getNombre()+"/"+ruta;
													if(org.getPadre()==null){
														org = null;
													}
													else{
														org=org.getPadre();
													}
												}
												
												
												dataRow1.createCell(0).setCellValue(ruta);
									            dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
									            
									            if(iniciativa.getFrecuenciaNombre() != null) {
									            	dataRow1.createCell(2).setCellValue(iniciativa.getFrecuenciaNombre());
												}else {
													dataRow1.createCell(2).setCellValue("");
												}
												
												if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
													dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
													dataRow1.createCell(4).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
												}else {
													dataRow1.createCell(3).setCellValue("");
													dataRow1.createCell(4).setCellValue("");
												}
												
												
																							
												
												if(iniciativa.getFechaUltimaMedicion() != null){
													dataRow1.createCell(5).setCellValue(iniciativa.getFechaUltimaMedicion());
												}
												else{
													dataRow1.createCell(5).setCellValue("");
												}
												
												
												Date fechaAh = new Date();
												Date fechaAc = new Date();
												
												fechaAh = obtenerFechaFinal(actividades);
												
												if(fechaAh != null) {
													
													SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
													
													String fechaTemp =objSDF.format(fechaAh);
													
																							
													dataRow1.createCell(6).setCellValue(fechaTemp);
										            
													int milisecondsByDay = 86400000;
													
													int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
													
													int diasposi = dias * -1;
													
													dataRow1.createCell(7).setCellValue(""+diasposi);
												}else {
													dataRow1.createCell(6).setCellValue("");
													dataRow1.createCell(7).setCellValue("");
												}
												
												
												
												//estatus
												if (medicionesProgramadas.size() == 0) {
													//EstatusIniciar
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
													//EstatusIniciardesfasado
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
												}					
												else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
													//EnEjecucionSinRetrasos
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
													//EnEjecucionConRetrasosSuperables
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
													//EnEjecucionConRetrasosSignificativos
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
												}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
													//EstatusConcluidas
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.concluidas"));
												}
												else if(iniciativa.getEstatusId() == 3) {
													//EstatusCancelado
													dataRow1.createCell(8).setCellValue("Cancelado");
												}
												else if(iniciativa.getEstatusId() == 4) {
													//EstatusSuspendido
													dataRow1.createCell(8).setCellValue("Suspendido");
												}else {
													//EstatusIniciar
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}
												
												if(iniciativa.getTipoProyecto()==null){
													dataRow1.createCell(9).setCellValue("");
												}else {
													dataRow1.createCell(9).setCellValue(iniciativa.getTipoProyecto().getNombre());
												}
												
												if(iniciativa.getAnioFormulacion() == null) {
													dataRow1.createCell(10).setCellValue("");
												}else {
													dataRow1.createCell(10).setCellValue(iniciativa.getAnioFormulacion());
												}
											
												//responsable ejecutar
												
												if(iniciativa.getResponsableCargarEjecutado() ==null){
													dataRow1.createCell(11).setCellValue("");
												}
												else{
													dataRow1.createCell(11).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
												}
												
												//responsable lograr meta
												
												if(iniciativa.getResponsableLograrMeta() ==null){
													dataRow1.createCell(12).setCellValue("");
												}
												else{
													dataRow1.createCell(12).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
												}
									            
									            
												x=x+1;
												
											}
										}
										
										
									}
							 
										        
								}
			            
						}
				}
			
			
			    Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="IniciativasResumido_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
				
				
				
				
			}			
			
			// organizacion seleccionada
			else{
				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtro = new FiltroForm();
				filtro.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtro.setNombre(null);
				else
					filtro.setNombre(filtroNombre);
				reporte.setFiltro(filtro);
				
			
				
				
				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");

				
				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = sheet.createRow(0);
		        
		        String header = "Reporte Iniciativas Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);
		        
		            
		        
				if (organizaciones.size() > 0)
				{
					
					 MessageResources messageResources = getResources(request);
					 
					 					        
				        HSSFRow dataRow = sheet.createRow(1);
						dataRow.createCell(0).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
			            dataRow.createCell(1).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre"));
			            dataRow.createCell(2).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia"));
			            dataRow.createCell(3).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado"));
			            dataRow.createCell(4).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado"));
			            dataRow.createCell(5).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion"));
			            dataRow.createCell(6).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada"));
			            dataRow.createCell(7).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.dias"));
			            dataRow.createCell(8).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.estatus"));
			            dataRow.createCell(9).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.tipoproyecto"));
			            dataRow.createCell(10).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
			            dataRow.createCell(11).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable"));
			            dataRow.createCell(12).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.lograr"));
			            int x=1;
			            
			    	 for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();)
						{
							
							OrganizacionStrategos organizacion = (OrganizacionStrategos)iter.next();
						
						    filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
							if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
								filtros.put("historicoDate", "IS NULL");
							else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
								filtros.put("historicoDate", "IS NOT NULL");
							if (reporte.getFiltro().getNombre() != null)
								filtros.put("nombre", reporte.getFiltro().getNombre());
							if(!tipo.equals("0")) {
								filtros.put("tipoId", tipo);
							}
							if(todos.equals("false")) {
								filtros.put("anio", ano);
							}
						
							
					        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();
				            
					         
					    	
					    	
					    	 if (iniciativas.size() > 0)
								{
					    		 
					    		 
									for (Iterator<Iniciativa> iter1 = iniciativas.iterator(); iter1.hasNext();)
									{
										Iniciativa iniciativa = (Iniciativa)iter1.next();
										
										StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
										StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
										StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
										
										Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
											
										List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
										List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
										List<PryActividad> actividades = new ArrayList<PryActividad>();
										
										
										if(iniciativa.getProyectoId() != null){
											actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
										}
										
										if(estatusId == 8) {
											
											String ruta=null;
											HSSFRow dataRow1 = sheet.createRow(x+1);
											OrganizacionStrategos org= new OrganizacionStrategos();
											ruta=organizacion.getNombre()+"/";
											org=organizacion.getPadre();
											while(org !=null){
												ruta=org.getNombre()+"/"+ruta;
												if(org.getPadre()==null){
													org = null;
												}
												else{
													org=org.getPadre();
												}
											}
											
											
											dataRow1.createCell(0).setCellValue(ruta);
								            dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
								            
								            if(iniciativa.getFrecuenciaNombre() != null) {
								            	dataRow1.createCell(2).setCellValue(iniciativa.getFrecuenciaNombre());
											}else {
												dataRow1.createCell(2).setCellValue("");
											}
											
											if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
												dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
												dataRow1.createCell(4).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
											}else {
												dataRow1.createCell(3).setCellValue("");
												dataRow1.createCell(4).setCellValue("");
											}
											
											
																						
											
											if(iniciativa.getFechaUltimaMedicion() != null){
												dataRow1.createCell(5).setCellValue(iniciativa.getFechaUltimaMedicion());
											}
											else{
												dataRow1.createCell(5).setCellValue("");
											}
											
											
											Date fechaAh = new Date();
											Date fechaAc = new Date();
											
											fechaAh = obtenerFechaFinal(actividades);
											
											if(fechaAh != null) {
												
												SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
												
												String fechaTemp =objSDF.format(fechaAh);
												
																						
												dataRow1.createCell(6).setCellValue(fechaTemp);
									            
												int milisecondsByDay = 86400000;
												
												int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
												
												int diasposi = dias * -1;
												
												dataRow1.createCell(7).setCellValue(""+diasposi);
											}else {
												dataRow1.createCell(6).setCellValue("");
												dataRow1.createCell(7).setCellValue("");
											}
											
											
											
											//estatus
											if (medicionesProgramadas.size() == 0) {
												//EstatusIniciar
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
												//EstatusIniciardesfasado
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
											}					
											else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
												//EnEjecucionSinRetrasos
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
												//EnEjecucionConRetrasosSuperables
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
												//EnEjecucionConRetrasosSignificativos
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
											}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
												//EstatusConcluidas
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.concluidas"));
											}
											else if(iniciativa.getEstatusId() == 3) {
												//EstatusCancelado
												dataRow1.createCell(8).setCellValue("Cancelado");
											}
											else if(iniciativa.getEstatusId() == 4) {
												//EstatusSuspendido
												dataRow1.createCell(8).setCellValue("Suspendido");
											}else {
												//EstatusIniciar
												dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}
											
											if(iniciativa.getTipoProyecto()==null){
												dataRow1.createCell(9).setCellValue("");
											}else {
												dataRow1.createCell(9).setCellValue(iniciativa.getTipoProyecto().getNombre());
											}
											
											if(iniciativa.getAnioFormulacion() == null) {
												dataRow1.createCell(10).setCellValue("");
											}else {
												dataRow1.createCell(10).setCellValue(iniciativa.getAnioFormulacion());
											}
										
											//responsable ejecutar
											
											if(iniciativa.getResponsableCargarEjecutado() ==null){
												dataRow1.createCell(11).setCellValue("");
											}
											else{
												dataRow1.createCell(11).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
											}
											
											//responsable lograr meta
											
											if(iniciativa.getResponsableLograrMeta() ==null){
												dataRow1.createCell(12).setCellValue("");
											}
											else{
												dataRow1.createCell(12).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
											}
								            
								            
											x=x+1;
											
										}else {
											Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);								
											
											if(est) {
												
												String ruta=null;
												HSSFRow dataRow1 = sheet.createRow(x+1);
												OrganizacionStrategos org= new OrganizacionStrategos();
												ruta=organizacion.getNombre()+"/";
												org=organizacion.getPadre();
												while(org !=null){
													ruta=org.getNombre()+"/"+ruta;
													if(org.getPadre()==null){
														org = null;
													}
													else{
														org=org.getPadre();
													}
												}
												
												
												dataRow1.createCell(0).setCellValue(ruta);
									            dataRow1.createCell(1).setCellValue(iniciativa.getNombre());
									            
									            if(iniciativa.getFrecuenciaNombre() != null) {
									            	dataRow1.createCell(2).setCellValue(iniciativa.getFrecuenciaNombre());
												}else {
													dataRow1.createCell(2).setCellValue("");
												}
												
												if(iniciativa.getPorcentajeCompletadoFormateado() != null) {
													dataRow1.createCell(3).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
													dataRow1.createCell(4).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
												}else {
													dataRow1.createCell(3).setCellValue("");
													dataRow1.createCell(4).setCellValue("");
												}
												
												
																							
												
												if(iniciativa.getFechaUltimaMedicion() != null){
													dataRow1.createCell(5).setCellValue(iniciativa.getFechaUltimaMedicion());
												}
												else{
													dataRow1.createCell(5).setCellValue("");
												}
												
												
												Date fechaAh = new Date();
												Date fechaAc = new Date();
												
												fechaAh = obtenerFechaFinal(actividades);
												
												if(fechaAh != null) {
													
													SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy"); 
													
													String fechaTemp =objSDF.format(fechaAh);
													
																							
													dataRow1.createCell(6).setCellValue(fechaTemp);
										            
													int milisecondsByDay = 86400000;
													
													int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);
													
													int diasposi = dias * -1;
													
													dataRow1.createCell(7).setCellValue(""+diasposi);
												}else {
													dataRow1.createCell(6).setCellValue("");
													dataRow1.createCell(7).setCellValue("");
												}
												
												
												
												//estatus
												if (medicionesProgramadas.size() == 0) {
													//EstatusIniciar
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
													//EstatusIniciardesfasado
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
												}					
												else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
													//EnEjecucionSinRetrasos
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
													//EnEjecucionConRetrasosSuperables
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
													//EnEjecucionConRetrasosSignificativos
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
												}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
													//EstatusConcluidas
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.concluidas"));
												}
												else if(iniciativa.getEstatusId() == 3) {
													//EstatusCancelado
													dataRow1.createCell(8).setCellValue("Cancelado");
												}
												else if(iniciativa.getEstatusId() == 4) {
													//EstatusSuspendido
													dataRow1.createCell(8).setCellValue("Suspendido");
												}else {
													//EstatusIniciar
													dataRow1.createCell(8).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}
												
												if(iniciativa.getTipoProyecto()==null){
													dataRow1.createCell(9).setCellValue("");
												}else {
													dataRow1.createCell(9).setCellValue(iniciativa.getTipoProyecto().getNombre());
												}
												
												if(iniciativa.getAnioFormulacion() == null) {
													dataRow1.createCell(10).setCellValue("");
												}else {
													dataRow1.createCell(10).setCellValue(iniciativa.getAnioFormulacion());
												}
											
												//responsable ejecutar
												
												if(iniciativa.getResponsableCargarEjecutado() ==null){
													dataRow1.createCell(11).setCellValue("");
												}
												else{
													dataRow1.createCell(11).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
												}
												
												//responsable lograr meta
												
												if(iniciativa.getResponsableLograrMeta() ==null){
													dataRow1.createCell(12).setCellValue("");
												}
												else{
													dataRow1.createCell(12).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
												}
									            
									            
												x=x+1;
												
											}
										}
										
										
									}
							 
										        
								}
			            
						}
				}
			
			
			    Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		       
		        
		        String archivo="IniciativasResumido_"+hourdateFormat.format(date)+".xls"; 
		        
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
		       
		        ServletOutputStream file  = response.getOutputStream();
		        
		        workbook.write(file);
		        file.close();
			}
			forward="exito";
	        organizacionservice.close();
	        iniciativaservice.close(); 
	        /** C�digo de l�gica de Negocio del action	*/

			/** Otherwise, return "success" */
			return mapping.findForward(forward);  
	        
	}
	
	public String obtenerObjetivo(Iniciativa iniciativa) throws SQLException{
		String objetivo=null;
		Long id=iniciativa.getIniciativaId();
		
		Map<String, Object> filtros = new HashMap<String, Object>();
		
		if((iniciativa.getIniciativaPerspectivas() != null) && (iniciativa.getIniciativaPerspectivas().size() > 0)){
			
			IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iniciativa.getIniciativaPerspectivas().toArray()[0];
            StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
            Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());
            
            objetivo= perspectiva.getNombre();
        
		}
		return objetivo;
	}
	
	public Date obtenerFechaFinal(List<PryActividad> actividades) {
		
		Date fecha = null;
		
		for(PryActividad act: actividades) {
			
			fecha = act.getFinPlan();
			
		}
		
		return fecha;
	}
	
	public Boolean tieneEstatus(Iniciativa iniciativa, List<Medicion> medicionesEjecutadas, List<Medicion> medicionesProgramadas, int estatus){
		
		Boolean tiene=false;
		//estatus
		if (medicionesProgramadas.size() == 0 && estatus == 0) {
			//EstatusIniciar
			tiene = true;
		}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0 && estatus == 1) {
			//EstatusIniciardesfasado
			tiene = true;
		}					
		else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D && estatus == 2) {
			//EnEjecucionSinRetrasos
			tiene = true;
		}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue() && estatus == 3) {
			//EnEjecucionConRetrasosSuperables
			tiene = true;
		}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue() && estatus == 4) {
			//EnEjecucionConRetrasosSignificativos
			tiene = true;
		}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D && estatus == 5) {
			//EstatusConcluidas
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 3 && estatus == 6) {
			//EstatusCancelado
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 4 && estatus == 7) {
			//EstatusSuspendido
			tiene = true;
		}else if(iniciativa.getEstatusId() == 1  && estatus == 0) {
			//EstatusSuspendido
			tiene = true;
		}
		
		return tiene;
	}	

}
