package com.visiongc.app.strategos.web.struts;

import com.visiongc.commons.MapStrutsActionRight;
import com.visiongc.commons.struts.StrutsActionRight;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;

public class StrategosMapStrutsActionRight implements MapStrutsActionRight
{
	private static StrategosMapStrutsActionRight instance = null;
  	private HashMap<String, StrutsActionRight> mapa = null;

  	public StrategosMapStrutsActionRight()
  	{
	    instance = this;
	    this.mapa = new HashMap<String, StrutsActionRight>();
	    StrutsActionRight actionRight = null;
	    
	    // ---------------------------------------------
	    // Organizacion
	    // ---------------------------------------------
	    actionRight = new StrutsActionRight("organizaciones.gestionarorganizaciones", false, false, null);
	    this.mapa.put("organizaciones.gestionarorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.crearorganizacion", false, false, "ORGANIZACION_ADD");
	    this.mapa.put("organizaciones.crearorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.modificarorganizacion", false, false, "ORGANIZACION_EDIT");
	    this.mapa.put("organizaciones.modificarorganizacion", actionRight);
	    this.mapa.put("organizaciones.cancelarcopiarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.verorganizacion", false, false, "ORGANIZACION_VIEWALL");
	    this.mapa.put("organizaciones.verorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.eliminarorganizacion", false, false, "ORGANIZACION_DELETE");
	    this.mapa.put("organizaciones.eliminarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.guardarorganizacion", false, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.guardarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.cancelarguardarorganizacion", false, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.cancelarguardarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.generarreporteorganizaciones", false, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.generarreporteorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.seleccionarorganizaciones", true, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.seleccionarorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.seleccionarorganizacionesinterno", true, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.seleccionarorganizacionesinterno", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.propiedadesorganizacion", true, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.propiedadesorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.guardarorganizacionsololectura", false, false, "ORGANIZACION_READONLY");
	    this.mapa.put("organizaciones.guardarorganizacionsololectura", actionRight);
	    // Copiar Organizacion
	    actionRight = new StrutsActionRight("organizaciones.copiarorganizacion", false, true, "ORGANIZACION_COPY");
	    this.mapa.put("organizaciones.copiarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.guardarcopiarorganizacion", false, false, "ORGANIZACION_COPY");
	    this.mapa.put("organizaciones.guardarcopiarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.cancelarcopiarorganizacion", false, false, "ORGANIZACION_COPY");
	    // Codigo de Enlace
	    actionRight = new StrutsActionRight("organizaciones.editarcodigoenlaceorganizacion", false, false, "ORGANIZACION_CODIGO_ENLACE");
	    this.mapa.put("organizaciones.editarcodigoenlaceorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.codigoenlaceorganizacion", false, false, "ORGANIZACION_CODIGO_ENLACE");
	    this.mapa.put("organizaciones.codigoenlaceorganizacion", actionRight);
	    // Mover Organizacion
	    actionRight = new StrutsActionRight("organizaciones.moverorganizacion", false, false, "ORGANIZACION_MOVE");
	    this.mapa.put("organizaciones.moverorganizacion", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.guardarmoverorganizacion", false, false, "ORGANIZACION_MOVE");
	    this.mapa.put("organizaciones.guardarmoverorganizacion", actionRight);
	    
	    actionRight = new StrutsActionRight("organizaciones.gestionarindicadores", true, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.gestionarindicadores", actionRight);
	    actionRight = new StrutsActionRight("organizaciones.gestionariniciativas", true, false, "ORGANIZACION");
	    this.mapa.put("organizaciones.gestionariniciativas", actionRight);
	    
	    // Calcular Iniciativas
	    actionRight = new StrutsActionRight("iniciativas.configurarcalculoiniciativas", false, false, "ORGANIZACION_CALCULAR_INICIATIVA");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.calculariniciativas", false, false, "ORGANIZACION_CALCULAR_INICIATIVA");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    actionRight = new StrutsActionRight("configuracion.validarconfiguracionpagina", true, false, "CONFIGURACION_PAGINA");
	    this.mapa.put("configuracion.validarconfiguracionpagina", actionRight);
	    
	    actionRight = new StrutsActionRight("configuracion.salvarconfiguracionservicio", true, false, "CONFIGURACION_PAGINA");
	    this.mapa.put("configuracion.salvarconfiguracionservicio", actionRight);
	    actionRight = new StrutsActionRight("configuracion.configuracionservicio", true, false, "CONFIGURACION_PAGINA");
	    this.mapa.put("configuracion.configuracionservicio", actionRight);
	    
	    actionRight = new StrutsActionRight("configuracion.salvarcorreoiniciativa", true, false, "CONFIGURACION_PAGINA");
	    this.mapa.put("configuracion.salvarcorreoiniciativa", actionRight);
	    
	    
	    
	    actionRight = new StrutsActionRight("unidadesmedida.gestionarunidadesmedida", false, false, "UNIDAD");
	    this.mapa.put("unidadesmedida.gestionarunidadesmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.crearunidadmedida", false, false, "UNIDAD_ADD");
	    this.mapa.put("unidadesmedida.crearunidadmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.modificarunidadmedida", false, false, "UNIDAD_EDIT");
	    this.mapa.put("unidadesmedida.modificarunidadmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.eliminarunidadmedida", false, false, "UNIDAD_DELETE");
	    this.mapa.put("unidadesmedida.eliminarunidadmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.guardarunidadmedida", false, false, "UNIDAD");
	    this.mapa.put("unidadesmedida.guardarunidadmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.cancelarguardarunidadmedida", true, false, "UNIDAD");
	    this.mapa.put("unidadesmedida.cancelarguardarunidadmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.generarreporteunidadesmedida", false, false, "UNIDAD_PRINT");
	    this.mapa.put("unidadesmedida.generarreporteunidadesmedida", actionRight);
	    actionRight = new StrutsActionRight("unidadesmedida.seleccionarunidadesmedida", true, false, "UNIDAD");
	    this.mapa.put("unidadesmedida.seleccionarunidadesmedida", actionRight);
	
	    actionRight = new StrutsActionRight("causas.gestionarcausas", false, false, "CAUSA");
	    this.mapa.put("causas.gestionarcausas", actionRight);
	    actionRight = new StrutsActionRight("causas.crearcausa", false, false, "CAUSA_ADD");
	    this.mapa.put("causas.crearcausa", actionRight);
	    actionRight = new StrutsActionRight("causas.modificarcausa", false, false, "CAUSA_EDIT");
	    this.mapa.put("causas.modificarcausa", actionRight);
	    actionRight = new StrutsActionRight("causas.eliminarcausa", false, false, "CAUSA_DELETE");
	    this.mapa.put("causas.eliminarcausa", actionRight);
	    actionRight = new StrutsActionRight("causas.guardarcausa", false, false, "CAUSA");
	    this.mapa.put("causas.guardarcausa", actionRight);
	    actionRight = new StrutsActionRight("causas.cancelarguardarcausa", true, false, "CAUSA");
	    this.mapa.put("causas.cancelarguardarcausa", actionRight);
	    actionRight = new StrutsActionRight("causas.generarreportecausas", false, false, "CAUSA_PRINT");
	    this.mapa.put("causas.generarreportecausas", actionRight);
	    actionRight = new StrutsActionRight("causas.seleccionarcausas", true, false, "CAUSA");
	    this.mapa.put("causas.seleccionarcausas", actionRight);
	
	    actionRight = new StrutsActionRight("categoriasmedicion.gestionarcategoriasmedicion", false, false, "CATEGORIA");
	    this.mapa.put("categoriasmedicion.gestionarcategoriasmedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.crearcategoriamedicion", false, false, "CATEGORIA_ADD");
	    this.mapa.put("categoriasmedicion.crearcategoriamedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.modificarcategoriamedicion", false, false, "CATEGORIA_EDIT");
	    this.mapa.put("categoriasmedicion.modificarcategoriamedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.eliminarcategoriamedicion", false, false, "CATEGORIA_DELETE");
	    this.mapa.put("categoriasmedicion.eliminarcategoriamedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.guardarcategoriamedicion", false, false, "CATEGORIA");
	    this.mapa.put("categoriasmedicion.guardarcategoriamedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.cancelarguardarcategoriamedicion", true, false, "CATEGORIA");
	    this.mapa.put("categoriasmedicion.cancelarguardarcategoriamedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.generarreportecategoriasmedicion", false, false, "CATEGORIA_PRINT");
	    this.mapa.put("categoriasmedicion.generarreportecategoriasmedicion", actionRight);
	    actionRight = new StrutsActionRight("categoriasmedicion.seleccionarcategoriasmedicion", true, false, "CATEGORIA");
	    this.mapa.put("categoriasmedicion.seleccionarcategoriasmedicion", actionRight);
	
	    actionRight = new StrutsActionRight("estadosacciones.gestionarestadosacciones", false, false, "ESTATUS");
	    this.mapa.put("estadosacciones.gestionarestadosacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.crearestadoacciones", false, false, "ESTATUS_ADD");
	    this.mapa.put("estadosacciones.crearestadoacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.modificarestadoacciones", false, false, "ESTATUS_EDIT");
	    this.mapa.put("estadosacciones.modificarestadoacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.eliminarestadoacciones", false, false, "ESTATUS_DELETE");
	    this.mapa.put("estadosacciones.eliminarestadoacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.guardarestadoacciones", false, false, "ESTATUS");
	    this.mapa.put("estadosacciones.guardarestadoacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.cancelarguardarestadoacciones", true, false, "ESTATUS");
	    this.mapa.put("estadosacciones.cancelarguardarestadoacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.cambiarordenestadoacciones", false, false, "ESTATUS_EDIT");
	    this.mapa.put("estadosacciones.cambiarordenestadoacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.seleccionarestadosacciones", true, false, "ESTATUS");
	    this.mapa.put("estadosacciones.seleccionarestadosacciones", actionRight);
	    actionRight = new StrutsActionRight("estadosacciones.generarreporteestadosacciones", true, false, "ESTATUS");
	    this.mapa.put("estadosacciones.generarreporteestadosacciones", actionRight);
	
	    actionRight = new StrutsActionRight("responsables.gestionarresponsables", false, true, "RESPONSABLE");
	    this.mapa.put("responsables.gestionarresponsables", actionRight);
	    actionRight = new StrutsActionRight("responsables.crearresponsable", false, true, "RESPONSABLE_ADD");
	    this.mapa.put("responsables.crearresponsable", actionRight);
	    actionRight = new StrutsActionRight("responsables.modificarresponsable", false, true, "RESPONSABLE_EDIT");
	    this.mapa.put("responsables.modificarresponsable", actionRight);
	    actionRight = new StrutsActionRight("responsables.guardarresponsable", false, true, "RESPONSABLE");
	    this.mapa.put("responsables.guardarresponsable", actionRight);
	    actionRight = new StrutsActionRight("responsables.cancelarguardarresponsable", false, true, "RESPONSABLE");
	    this.mapa.put("responsables.cancelarguardarresponsable", actionRight);
	    actionRight = new StrutsActionRight("responsables.eliminarresponsable", false, true, "RESPONSABLE_DELETE");
	    this.mapa.put("responsables.eliminarresponsable", actionRight);
	    actionRight = new StrutsActionRight("responsables.asociarresponsables", false, true, "RESPONSABLE_ADD");
	    this.mapa.put("responsables.asociarresponsables", actionRight);
	    actionRight = new StrutsActionRight("responsables.guardarresponsablesasociados", false, true, "RESPONSABLE");
	    this.mapa.put("responsables.guardarresponsablesasociados", actionRight);
	    actionRight = new StrutsActionRight("responsables.cancelarguardarasociarresponsable", false, true, "RESPONSABLE");
	    this.mapa.put("responsables.cancelarguardarasociarresponsable", actionRight);
	    actionRight = new StrutsActionRight("responsables.generarreporteresponsables", false, true, "RESPONSABLE_PRINT");
	    this.mapa.put("responsables.generarreporteresponsables", actionRight);
	    actionRight = new StrutsActionRight("responsables.seleccionarresponsables", true, false, "RESPONSABLE");
	    this.mapa.put("responsables.seleccionarresponsables", actionRight);
	    actionRight = new StrutsActionRight("responsables.propiedadesresponsable", false, false, "RESPONSABLE");
	    this.mapa.put("responsables.propiedadesresponsable", actionRight);
	
	    actionRight = new StrutsActionRight("plancuentas.gestionarcuentas", false, false, "IMPUTACION");
	    this.mapa.put("plancuentas.gestionarcuentas", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.crearcuenta", false, false, "IMPUTACION_ADD");
	    this.mapa.put("plancuentas.crearcuenta", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.modificarcuenta", false, false, "IMPUTACION_EDIT");
	    this.mapa.put("plancuentas.modificarcuenta", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.eliminarcuenta", false, false, "IMPUTACION_DELETE");
	    this.mapa.put("plancuentas.eliminarcuenta", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.guardarcuenta", false, false, "IMPUTACION");
	    this.mapa.put("plancuentas.guardarcuenta", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.cancelarguardarcuenta", false, false, "IMPUTACION");
	    this.mapa.put("plancuentas.cancelarguardarcuenta", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.definirmascaracuentas", false, false, "IMPUTACION_MASK");
	    this.mapa.put("plancuentas.definirmascaracuentas", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.guardarmascaracuentas", false, false, "IMPUTACION_MASK");
	    this.mapa.put("plancuentas.guardarmascaracuentas", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.cancelarguardarmascaracuentas", false, false, "IMPUTACION_MASK");
	    this.mapa.put("plancuentas.cancelarguardarmascaracuentas", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.generarreportecuentas", false, false, "IMPUTACION");
	    this.mapa.put("plancuentas.generarreportecuentas", actionRight);
	    actionRight = new StrutsActionRight("plancuentas.seleccionarcuentas", true, false, "IMPUTACION");
	    this.mapa.put("plancuentas.seleccionarcuentas", actionRight);
	
	    actionRight = new StrutsActionRight("seriestiempo.gestionarseriestiempo", false, false, "SERIE_TIEMPO");
	    this.mapa.put("seriestiempo.gestionarseriestiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.crearserietiempo", false, false, "SERIE_TIEMPO_ADD");
	    this.mapa.put("seriestiempo.crearserietiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.modificarserietiempo", false, false, "SERIE_TIEMPO_EDIT");
	    this.mapa.put("seriestiempo.modificarserietiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.eliminarserietiempo", false, false, "SERIE_TIEMPO_DELETE");
	    this.mapa.put("seriestiempo.eliminarserietiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.guardarserietiempo", false, false, "SERIE_TIEMPO");
	    this.mapa.put("seriestiempo.guardarserietiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.cancelarguardarserietiempo", true, false, "SERIE_TIEMPO");
	    this.mapa.put("seriestiempo.cancelarguardarserietiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.generarreporteseriestiempo", false, false, "SERIE_TIEMPO_PRINT");
	    this.mapa.put("seriestiempo.generarreporteseriestiempo", actionRight);
	    actionRight = new StrutsActionRight("seriestiempo.seleccionarseriestiempo", true, false, "SERIE_TIEMPO");
	    this.mapa.put("seriestiempo.seleccionarseriestiempo", actionRight);
	
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.gestionarclasesindicadores", false, false, "INDICADOR_RAIZ");
	    this.mapa.put("indicadores.clasesindicadores.gestionarclasesindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.gestionarclasesindicadores", false, false, "CLASE_VISTA");
	    this.mapa.put("indicadores.clasesindicadores.gestionarclasesindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.crearclaseindicadores", false, false, "CLASE_ADD");
	    this.mapa.put("indicadores.clasesindicadores.crearclaseindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.modificarclaseindicadores", false, false, "CLASE_EDIT");
	    this.mapa.put("indicadores.clasesindicadores.modificarclaseindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.verclaseindicadores", false, false, "CLASE_VIEWALL");
	    this.mapa.put("indicadores.clasesindicadores.verclaseindicadores", actionRight);
	    
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.propiedadesclaseindicadores", false, false, "CLASE");
	    this.mapa.put("indicadores.clasesindicadores.propiedadesclaseindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.guardarclaseindicadores", false, false, "CLASE");
	    this.mapa.put("indicadores.clasesindicadores.guardarclaseindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.cancelarclaseindicadores", true, false, "CLASE");
	    this.mapa.put("indicadores.clasesindicadores.cancelarclaseindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.eliminarclaseindicadores", false, false, "CLASE_DELETE");
	    this.mapa.put("indicadores.clasesindicadores.eliminarclaseindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.generarreporteclasesindicadores", false, false, "CLASE_PRINT");
	    this.mapa.put("indicadores.clasesindicadores.generarreporteclasesindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.seleccionarclasesindicadores", true, false, "CLASE");
	    this.mapa.put("indicadores.clasesindicadores.seleccionarclasesindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.seleccionarclasesindicadoresinterno", true, false, "CLASE");
	    this.mapa.put("indicadores.clasesindicadores.seleccionarclasesindicadoresinterno", actionRight);
	    // Copiar Clase
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.copiarclase", false, true, "CLASE_COPY");
	    this.mapa.put("indicadores.clasesindicadores.copiarclase", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.guardarcopiarclase", false, false, "CLASE_COPY");
	    this.mapa.put("indicadores.clasesindicadores.guardarcopiarclase", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.cancelarcopiarclase", false, false, "CLASE_COPY");
	    this.mapa.put("indicadores.clasesindicadores.cancelarcopiarclase", actionRight);
	    // Mover Clase
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.moverclase", false, false, "CLASE_MOVE");
	    this.mapa.put("indicadores.clasesindicadores.moverclase", actionRight);
	    actionRight = new StrutsActionRight("indicadores.clasesindicadores.guardarmoverclase", false, false, "CLASE_MOVE");
	    this.mapa.put("indicadores.clasesindicadores.guardarmoverclase", actionRight);
	    
	    actionRight = new StrutsActionRight("indicadores.gestionarindicadores", false, false, "INDICADOR_RAIZ");
	    this.mapa.put("indicadores.gestionarindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.crearindicador", false, false, "INDICADOR_ADD");
	    this.mapa.put("indicadores.crearindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.modificarindicador", false, false, "INDICADOR_EDIT");
	    this.mapa.put("indicadores.modificarindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.verindicador", false, false, "INDICADOR_VIEWALL");
	    this.mapa.put("indicadores.verindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.editarindicadorfuncion", true, false, "INDICADOR");
	    this.mapa.put("indicadores.editarindicadorfuncion", actionRight);
	    actionRight = new StrutsActionRight("indicadores.guardarindicador", false, false, "INDICADOR");
	    this.mapa.put("indicadores.guardarindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.cancelarguardarindicador", true, false, "INDICADOR");
	    this.mapa.put("indicadores.cancelarguardarindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.eliminarindicador", false, false, "INDICADOR_DELETE");
	    this.mapa.put("indicadores.eliminarindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.generarreporteindicadores", false, false, "INDICADOR_PRINT");
	    this.mapa.put("indicadores.generarreporteindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarindicadores", true, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarindicadoresorganizaciones", true, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarindicadoresorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarindicadoresclasesindicadores", true, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarindicadoresclasesindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarindicadoresiniciativas", true, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarindicadoresiniciativas", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarindicadoresplanes", true, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarindicadoresplanes", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarindicadoresindicadores", true, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarindicadoresindicadores", actionRight);
	    actionRight = new StrutsActionRight("indicadores.propiedadesindicador", true, false, "INDICADOR");
	    this.mapa.put("indicadores.propiedadesindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.visualizarindicador", true, false, "INDICADOR");
	    this.mapa.put("indicadores.visualizarindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.guardarindicadorsololectura", false, false, "INDICADOR_READONLY");
	    this.mapa.put("indicadores.guardarindicadorsololectura", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarclasesorganizaciones", false, false, "INDICADOR");
	    this.mapa.put("indicadores.seleccionarclasesorganizaciones", actionRight);
	    // Copiar Indicadores
	    actionRight = new StrutsActionRight("indicadores.copiarindicador", false, true, "INDICADOR_EDIT");
	    this.mapa.put("indicadores.copiarindicador", actionRight);
	    // Mover Indicadores
	    actionRight = new StrutsActionRight("indicadores.moverindicador", false, true, "INDICADOR_EDIT");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("indicadores.guardarmoverindicador", false, true, "INDICADOR_EDIT");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("indicadores.guardarcopiarindicador", false, false, "INDICADOR_COPY");
	    this.mapa.put("indicadores.guardarcopiarindicador", actionRight);
	    actionRight = new StrutsActionRight("indicadores.cancelarcopiarindicador", false, false, "INDICADOR");
	    this.mapa.put("indicadores.cancelarcopiarindicador", actionRight);
	    // Indicador Consolidado
	    actionRight = new StrutsActionRight("indicadores.indicadorconsolidado", false, false, "INDICADOR_CONSOLIDADO");
	    this.mapa.put("indicadores.indicadorconsolidado", actionRight);
	    actionRight = new StrutsActionRight("indicadores.seleccionarmultiplesclases", false, false, "INDICADOR_CONSOLIDADO");
	    this.mapa.put("indicadores.seleccionarmultiplesclases", actionRight);
	
	    actionRight = new StrutsActionRight("indicadores.leerindicadorconsolidado", true, false, "INDICADOR");
	    this.mapa.put("indicadores.leerindicadorconsolidado", actionRight);
	    actionRight = new StrutsActionRight("indicadores.cancelarindicadorconsolidado", false, false, "INDICADOR");
	    this.mapa.put("indicadores.cancelarindicadorconsolidado", actionRight);

	    actionRight = new StrutsActionRight("indicadores.enviaremailindicador", false, false, "INDICADOR_EMAIL");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    //instrumentos
	        
	       
	    actionRight = new StrutsActionRight("instrumentos.gestionarinstrumentosiniciativas", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.gestionarinstrumentosiniciativas", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.gestionarinstrumentosindicadoresiniciativas", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.gestionarinstrumentosindicadoresiniciativas", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.asociariniciativa", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.asociariniciativa", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.desasociariniciativa", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.desasociariniciativa", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.gestionarinstrumentos", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.gestionarinstrumentos", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.gestionarcooperantes", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.gestionarcooperantes", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.gestionarconvenios", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.gestionarconvenios", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.resumidaejecucion", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.resumidaejecucion", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.reporteinstrumentopdf", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.reporteinstrumentopdf", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.reporteinstrumentoxls", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.reporteinstrumentoxls", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.resumidaejecuciondetalle", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.resumidaejecuciondetalle", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.reporteinstrumentodetallepdf", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.reporteinstrumentodetallepdf", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.reporteinstrumentodetallexls", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.reporteinstrumentodetallexls", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.proyectosasociados", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.reporteproyectosasociados", actionRight);
	    
	
	    
	    actionRight = new StrutsActionRight("instrumentos.crearconvenio", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.crearconvenio", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.modificarconvenio", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.modificarconvenio", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.guardarconvenio", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.guardarconvenio", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.cancelarguardarconvenio", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.cancelarguardarconvenio", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.seleccionarconvenio", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.seleccionarconvenio", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.eliminarconvenio", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.eliminarconvenio", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.crearcooperante", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.crearcooperante", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.modificarcooperante", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.modificarcooperante", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.guardarcooperante", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.guardarcooperante", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.cancelarguardarcooperante", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.cancelarguardarcooperante", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.seleccionarcooperante", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.seleccionarcooperante", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.eliminarcooperante", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.eliminarcooperante", actionRight);
	    
	    actionRight = new StrutsActionRight("instrumentos.crearinstrumento", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.crearinstrumento", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.modificarinstrumento", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.modificarinstrumento", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.guardarinstrumento", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.guardarinstrumento", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.cancelarguardarinstrumento", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.cancelarguardarinstrumento", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.seleccionarinstrumento", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.seleccionarinstrumento", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.eliminarinstrumento", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.eliminarinstrumento", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.reportebasicopdf", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.reportebasicopdf", actionRight);
	    actionRight = new StrutsActionRight("instrumentos.seleccionarorganizacion", false, false, "INSTRUMENTOS");
	    this.mapa.put("instrumentos.seleccionarorganizacion", actionRight);
	   	    
	    // mediciones
	    actionRight = new StrutsActionRight("mediciones.configuraredicionmediciones", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.configuraredicionmediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.cancelarconfiguraredicionmediciones", true, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.cancelarconfiguraredicionmediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.guardarmediciones", false, true, "INDICADOR_MEDICION_CARGAR");
	    this.mapa.put("mediciones.guardarmediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.cancelarguardarmediciones", true, true, "INDICADOR_MEDICION_CARGAR");
	    this.mapa.put("mediciones.cancelarguardarmediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.seleccionarperiodomediciones", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.seleccionarperiodomediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.seleccionarperiodomedicionesclase", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.seleccionarperiodomedicionesclase", actionRight);
	    actionRight = new StrutsActionRight("mediciones.protegermediciones", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.protegermediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.editarmediciones", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.editarmediciones", actionRight);
	    actionRight = new StrutsActionRight("mediciones.guardarmedicion", false, true, "INDICADOR_MEDICION_CARGAR");
	    this.mapa.put("mediciones.guardarmedicion", actionRight);
	    actionRight = new StrutsActionRight("mediciones.cancelarguardarmedicion", false, true, "INDICADOR_MEDICION_CARGAR");
	    this.mapa.put("mediciones.cancelarguardarmedicion", actionRight);
	    actionRight = new StrutsActionRight("mediciones.protegerliberar", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.protegerliberar", actionRight);
	    actionRight = new StrutsActionRight("mediciones.protegerliberarsalvar", false, true, "INDICADOR_MEDICION");
	    this.mapa.put("mediciones.protegerliberarsalvar", actionRight);
	    actionRight = new StrutsActionRight("mediciones.imprimirmediciones", false, true, "INDICADOR_MEDICION");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("mediciones.exportarxlsmediciones", false, true, "INDICADOR_MEDICION");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    actionRight = new StrutsActionRight("calculoindicadores.configurarcalculoindicadores", false, true, "INDICADOR_MEDICION_CALCULAR");
	    this.mapa.put("calculoindicadores.configurarcalculoindicadores", actionRight);
	    actionRight = new StrutsActionRight("calculoindicadores.calcularindicadores", false, true, "INDICADOR_MEDICION_CALCULAR");
	    this.mapa.put("calculoindicadores.calcularindicadores", actionRight);
	    actionRight = new StrutsActionRight("calculoindicadores.cancelarcalcularindicadores", false, true, "INDICADOR_MEDICION_CALCULAR");
	    this.mapa.put("calculoindicadores.cancelarcalcularindicadores", actionRight);
	    actionRight = new StrutsActionRight("graficos.dupontindicador", false, false, "INDICADOR");
	    this.mapa.put("graficos.dupontindicador", actionRight);

	    // Explicaciones
	    actionRight = new StrutsActionRight("anexos.gestionaranexos", false, false, "EXPLICACION");
	    this.mapa.put("anexos.gestionaranexos", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.gestionarexplicaciones", false, false, "EXPLICACION");
	    this.mapa.put("explicaciones.gestionarexplicaciones", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.crearexplicacion", false, false, "EXPLICACION_ADD");
	    this.mapa.put("explicaciones.crearexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.modificarexplicacion", false, false, "EXPLICACION_EDIT");
	    this.mapa.put("explicaciones.modificarexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.verexplicacion", false, false, "EXPLICACION_VIEW");
	    this.mapa.put("explicaciones.verexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.eliminarexplicacion", false, false, "EXPLICACION_DELETE");
	    this.mapa.put("explicaciones.eliminarexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.guardarexplicacion", false, false, "EXPLICACION_ADD");
	    this.mapa.put("explicaciones.guardarexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.cancelarguardarexplicacion", true, false, "EXPLICACION_ADD");
	    this.mapa.put("explicaciones.cancelarguardarexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.propiedadesexplicacion", true, false, "EXPLICACION");
	    this.mapa.put("explicaciones.propiedadesexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.subiradjuntoexplicacion", true, false, "EXPLICACION");
	    this.mapa.put("explicaciones.subiradjuntoexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.eliminaradjuntoexplicacion", true, false, "EXPLICACION_DELETE");
	    this.mapa.put("explicaciones.eliminaradjuntoexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.descargaradjuntoexplicacion", true, false, "EXPLICACION");
	    this.mapa.put("explicaciones.descargaradjuntoexplicacion", actionRight);
	    actionRight = new StrutsActionRight("explicaciones.generarreporteexplicacionespdf", true, false, "EXPLICACION");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("explicaciones.generarreporteexplicacionesxls", true, false, "EXPLICACION");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    actionRight = new StrutsActionRight("foros.gestionarforos", false, false, "FORO");
	    this.mapa.put("foros.gestionarforos", actionRight);
	    actionRight = new StrutsActionRight("foros.crearforo", false, false, "FORO_ADD");
	    this.mapa.put("foros.crearforo", actionRight);
	    actionRight = new StrutsActionRight("foros.guardarforo", false, false, "FORO");
	    this.mapa.put("foros.guardarforo", actionRight);
	    actionRight = new StrutsActionRight("foros.cancelarguardarforo", true, false, "FORO");
	    this.mapa.put("foros.cancelarguardarforo", actionRight);
	
	    actionRight = new StrutsActionRight("presentaciones.vistas.gestionarvistas", false, false, "VISTA");
	    this.mapa.put("presentaciones.vistas.gestionarvistas", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.crearvista", false, false, "VISTA_ADD");
	    this.mapa.put("presentaciones.vistas.crearvista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.modificarvista", false, false, "VISTA_EDIT");
	    this.mapa.put("presentaciones.vistas.modificarvista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.vervista", false, false, "VISTA_VIEWALL");
	    this.mapa.put("presentaciones.vistas.vervista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.guardarvista", false, false, "VISTA");
	    this.mapa.put("presentaciones.vistas.guardarvista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.cancelarguardarvista", false, false, "VISTA");
	    this.mapa.put("presentaciones.vistas.cancelarguardarvista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.eliminarvista", false, false, "VISTA_DELETE");
	    this.mapa.put("presentaciones.vistas.eliminarvista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.mostrarvista", false, false, "VISTA");
	    this.mapa.put("presentaciones.vistas.mostrarvista", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.vistas.generarreportevistas", false, false, "VISTA");
	    this.mapa.put("presentaciones.vistas.generarreportevistas", actionRight);
	
	    actionRight = new StrutsActionRight("presentaciones.paginas.gestionarpaginas", false, false, "VISTA");
	    this.mapa.put("presentaciones.paginas.gestionarpaginas", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.crearpagina", false, false, "PAGINA_ADD");
	    this.mapa.put("presentaciones.paginas.crearpagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.modificarpagina", false, false, "PAGINA_EDIT");
	    this.mapa.put("presentaciones.paginas.modificarpagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.verpagina", false, false, "VISTA_VIEWALL");
	    this.mapa.put("presentaciones.paginas.verpagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.eliminarpagina", false, false, "PAGINA_DELETE");
	    this.mapa.put("presentaciones.paginas.eliminarpagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.guardarpagina", false, false, "PAGINA");
	    this.mapa.put("presentaciones.paginas.guardarpagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.cancelarguardarpagina", true, false, "PAGINA");
	    this.mapa.put("presentaciones.paginas.cancelarguardarpagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.generarreportepagina", false, false, "PAGINA_PRINT");
	    this.mapa.put("presentaciones.paginas.generarreportepagina", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.generarreportepaginas", false, false, "VISTA");
	    this.mapa.put("presentaciones.paginas.generarreportepaginas", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.paginas.cambiarordenpagina", true, false, "PAGINA");
	    this.mapa.put("presentaciones.paginas.cambiarordenpagina", actionRight);
	
	    actionRight = new StrutsActionRight("presentaciones.celdas.gestionarceldas", false, false, "CELDA");
	    this.mapa.put("presentaciones.celdas.gestionarceldas", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.celdas.crearcelda", false, false, "CELDA_ADD");
	    this.mapa.put("presentaciones.celdas.crearcelda", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.celdas.modificarcelda", false, false, "CELDA_EDIT");
	    this.mapa.put("presentaciones.celdas.modificarcelda", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.celdas.eliminarcelda", false, false, "CELDA_DELETE");
	    this.mapa.put("presentaciones.celdas.eliminarcelda", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.celdas.guardarcelda", false, false, "CELDA");
	    this.mapa.put("presentaciones.celdas.guardarcelda", actionRight);
	    actionRight = new StrutsActionRight("presentaciones.celdas.cancelarguardarcelda", true, false, "CELDA");
	    this.mapa.put("presentaciones.celdas.cancelarguardarcelda", actionRight);
	    actionRight = new StrutsActionRight("graficos.graficocelda", false, false, "CELDA");
	    this.mapa.put("graficos.graficocelda", actionRight);
	    actionRight = new StrutsActionRight("graficos.guardarconfiguraciongraficocelda", false, false, "CELDA");
	    this.mapa.put("graficos.guardarconfiguraciongraficocelda", actionRight);
	
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.gestionarclasesproblemas", false, false, "CLASE_PROBLEMA");
	    this.mapa.put("problemas.clasesproblemas.gestionarclasesproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.crearclaseproblemas", false, false, "CLASE_PROBLEMA_ADD");
	    this.mapa.put("problemas.clasesproblemas.crearclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.modificarclaseproblemas", false, false, "CLASE_PROBLEMA_EDIT");
	    this.mapa.put("problemas.clasesproblemas.modificarclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.verclaseproblemas", false, false, "CLASE_PROBLEMA_VIEWALL");
	    this.mapa.put("problemas.clasesproblemas.verclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.copiarclaseproblemas", false, false, "CLASE_PROBLEMA_EDIT");
	    this.mapa.put("problemas.clasesproblemas.copiarclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.propiedadesclaseproblemas", false, false, "CLASE_PROBLEMA");
	    this.mapa.put("problemas.clasesproblemas.propiedadesclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.guardarclaseproblemas", false, false, "CLASE_PROBLEMA");
	    this.mapa.put("problemas.clasesproblemas.guardarclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.cancelarclaseproblemas", false, false, "CLASE_PROBLEMA");
	    this.mapa.put("problemas.clasesproblemas.cancelarclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.guardarcopiaclaseproblemas", false, false, "CLASE_PROBLEMA");
	    this.mapa.put("problemas.clasesproblemas.guardarcopiaclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.eliminarclaseproblemas", false, false, "CLASE_PROBLEMA_DELETE");
	    this.mapa.put("problemas.clasesproblemas.eliminarclaseproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.clasesproblemas.generarreporteclasesproblemas", false, false, "CLASE_PROBLEMA_PRINT");
	    this.mapa.put("problemas.clasesproblemas.generarreporteclasesproblemas", actionRight);
	
	    actionRight = new StrutsActionRight("problemas.gestionarproblemas", false, false, "PROBLEMA");
	    this.mapa.put("problemas.gestionarproblemas", actionRight);
	    actionRight = new StrutsActionRight("problemas.crearproblema", false, false, "PROBLEMA_ADD");
	    this.mapa.put("problemas.crearproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.modificarproblema", false, false, "PROBLEMA_EDIT");
	    this.mapa.put("problemas.modificarproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.verproblema", false, false, "PROBLEMA_VIEWALL");
	    this.mapa.put("problemas.verproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.copiarproblema", false, false, "PROBLEMA_EDIT");
	    this.mapa.put("problemas.copiarproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.eliminarproblema", false, false, "PROBLEMA_DELETE");
	    this.mapa.put("problemas.eliminarproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.guardarproblema", false, false, "PROBLEMA");
	    this.mapa.put("problemas.guardarproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.guardarproblemasololectura", false, false, "PROBLEMA");
	    this.mapa.put("problemas.guardarproblemasololectura", actionRight);
	    actionRight = new StrutsActionRight("problemas.guardarcopiaproblema", false, false, "PROBLEMA");
	    this.mapa.put("problemas.guardarcopiaproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.cancelarguardarproblema", true, false, "PROBLEMA");
	    this.mapa.put("problemas.cancelarguardarproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.propiedadesproblema", true, false, "PROBLEMA");
	    this.mapa.put("problemas.propiedadesproblema", actionRight);
	    actionRight = new StrutsActionRight("problemas.generarreporteproblemas", false, false, "PROBLEMAS_PRINT");
	    this.mapa.put("problemas.generarreporteproblemas", actionRight);
	
	    actionRight = new StrutsActionRight("problemas.acciones.gestionaracciones", false, false, "ACCION");
	    this.mapa.put("problemas.acciones.gestionaracciones", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.crearaccion", false, false, "ACCION_ADD");
	    this.mapa.put("problemas.acciones.crearaccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.guardaraccion", false, false, "ACCION");
	    this.mapa.put("problemas.acciones.guardaraccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.modificaraccion", false, false, "ACCION_EDIT");
	    this.mapa.put("problemas.acciones.modificaraccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.veraccion", false, false, "ACCION_VIEWALL");
	    this.mapa.put("problemas.acciones.veraccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.cancelarguardaraccion", false, false, "ACCION");
	    this.mapa.put("problemas.acciones.cancelarguardaraccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.eliminaraccion", false, false, "ACCION_DELETE");
	    this.mapa.put("problemas.acciones.eliminaraccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.propiedadesaccion", false, false, "ACCION");
	    this.mapa.put("problemas.acciones.propiedadesaccion", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.guardaraccionsololectura", false, false, "ACCION");
	    this.mapa.put("problemas.acciones.guardaraccionsololectura", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.generarreporteacciones", false, false, "ACCIONES_PRINT");
	    this.mapa.put("problemas.acciones.generarreporteacciones", actionRight);
	    actionRight = new StrutsActionRight("problemas.acciones.gestionaraccionespendientes", true, false, "ACCION");
	    this.mapa.put("problemas.acciones.gestionaraccionespendientes", actionRight);
	
	    actionRight = new StrutsActionRight("problemas.seguimientos.gestionarseguimientos", false, false, "ACCION");
	    this.mapa.put("problemas.seguimientos.gestionarseguimientos", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.enviarseguimiento", false, false, "SEGUIMIENTO_ADD");
	    this.mapa.put("problemas.seguimientos.enviarseguimiento", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.modificarseguimiento", false, false, "SEGUIMIENTO_EDIT");
	    this.mapa.put("problemas.seguimientos.modificarseguimiento", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.eliminarseguimiento", false, false, "SEGUIMIENTO_DELETE");
	    this.mapa.put("problemas.seguimientos.eliminarseguimiento", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.guardarseguimiento", false, false, "SEGUIMIENTO");
	    this.mapa.put("problemas.seguimientos.guardarseguimiento", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.guardarseguimientosololectura", false, false, "SEGUIMIENTO");
	    this.mapa.put("problemas.seguimientos.guardarseguimientosololectura", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.cancelarguardarseguimiento", true, false, "SEGUIMIENTO");
	    this.mapa.put("problemas.seguimientos.cancelarguardarseguimiento", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.propiedadesseguimiento", true, false, "SEGUIMIENTO");
	    this.mapa.put("problemas.seguimientos.propiedadesseguimiento", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.generarreporteseguimientos", true, false, "SEGUIMIENTOS_PRINT");
	    this.mapa.put("problemas.seguimientos.generarreporteseguimientos", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.modificarconfiguracionmensajeemailseguimientos", false, false, "SEGUIMIENTO_MENSAJE");
	    this.mapa.put("problemas.seguimientos.modificarconfiguracionmensajeemailseguimientos", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.guardarconfiguracionmensajeemailseguimientos", false, false, "SEGUIMIENTO_MENSAJE");
	    this.mapa.put("problemas.seguimientos.guardarconfiguracionmensajeemailseguimientos", actionRight);
	    actionRight = new StrutsActionRight("problemas.seguimientos.cancelarguardarconfiguracionmensajeemailseguimientos", true, false, "SEGUIMIENTO_MENSAJE");
	    this.mapa.put("problemas.seguimientos.cancelarguardarconfiguracionmensajeemailseguimientos", actionRight);
	    // =================
	    // Plan
	    // =================
	    actionRight = new StrutsActionRight("planes.gestionarplanes", false, false, "PLAN");
	    this.mapa.put("planes.gestionarplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.gestionarplanesestrategicos", false, false, "PLAN");
	    this.mapa.put("planes.gestionarplanesestrategicos", actionRight);
	    actionRight = new StrutsActionRight("planes.gestionarplanesoperativos", false, false, "PLAN");
	    this.mapa.put("planes.gestionarplanesoperativos", actionRight);
	    actionRight = new StrutsActionRight("planes.crearplan", false, false, "PLAN_ADD");
	    this.mapa.put("planes.crearplan", actionRight);
	    actionRight = new StrutsActionRight("planes.modificarplan", false, false, "PLAN_EDIT");
	    this.mapa.put("planes.modificarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.verplan", false, false, "PLAN_VIEW");
	    this.mapa.put("planes.verplan", actionRight);
	    actionRight = new StrutsActionRight("planes.guardarplan", false, false, "PLAN");
	    this.mapa.put("planes.guardarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.cancelarguardarplan", true, false, "PLAN");
	    this.mapa.put("planes.cancelarguardarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.eliminarplan", false, false, "PLAN_DELETE");
	    this.mapa.put("planes.eliminarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.activardesactivarplan", false, false, "PLAN_ACTIVO");
	    this.mapa.put("planes.activardesactivarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.gestionarplan", true, false, "PLAN");
	    this.mapa.put("planes.gestionarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.visualizarplan", true, false, "PLAN");
	    this.mapa.put("planes.visualizarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.copiarplan", false, false, "PLAN_COPY");
	    this.mapa.put("planes.copiarplan", actionRight);
	    actionRight = new StrutsActionRight("planes.guardarcopiaplan", false, false, "PLAN_COPY");
	    this.mapa.put("planes.guardarcopiaplan", actionRight);
	    actionRight = new StrutsActionRight("planes.seleccionarplanes", true, false, "PLAN");
	    this.mapa.put("planes.seleccionarplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.seleccionarplanesorganizaciones", true, false, "PLAN");
	    this.mapa.put("planes.seleccionarplanesorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("planes.seleccionarplanesplanes", true, false, "PLAN");
	    this.mapa.put("planes.seleccionarplanesplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.generarreporteplanes", false, false, "PLAN");
	    this.mapa.put("planes.generarreporteplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.setpanel", false, false, "PLAN");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    actionRight = new StrutsActionRight("planes.perspectivas.visualizarperspectiva", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.visualizarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.visualizarperspectiva", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.visualizarperspectiva", actionRight);
	    
	    
	    actionRight = new StrutsActionRight("planes.visualizarestrategico", true, false, "PLAN");
	    this.mapa.put("planes.visualizarestrategico", actionRight);
	    actionRight = new StrutsActionRight("planes.visualizaroperativo", true, false, "PLAN");
	    this.mapa.put("planes.visualizaroperativo", actionRight);
	    
	    
	    
	    // Metas
	    actionRight = new StrutsActionRight("planes.metas.configuraredicionmetas", false, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.configuraredicionmetas", actionRight);
	    actionRight = new StrutsActionRight("planes.metas.cancelarconfiguraredicionmetas", true, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.cancelarconfiguraredicionmetas", actionRight);
	    actionRight = new StrutsActionRight("planes.metas.editarmetas", true, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.editarmetas", actionRight);
	    actionRight = new StrutsActionRight("planes.metas.editarmetasparciales", true, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.editarmetasparciales", actionRight);
	    actionRight = new StrutsActionRight("planes.metas.guardarmetas", true, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.guardarmetas", actionRight);
	    actionRight = new StrutsActionRight("planes.metas.guardarmetasparciales", true, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.guardarmetasparciales", actionRight);
	    actionRight = new StrutsActionRight("planes.metas.cancelarguardarmetas", true, false, "INDICADOR_VALOR_META");
	    this.mapa.put("planes.metas.cancelarguardarmetas", actionRight);
	    actionRight = new StrutsActionRight("planes.protegerliberar", true, false, "PROTEGER_LIBERAR_META");
	    this.mapa.put("planes.protegerliberar", actionRight);
	    actionRight = new StrutsActionRight("planes.protegerliberarsalvar", false, true, "PROTEGER_LIBERAR_META");
	    this.mapa.put("planes.protegerliberarsalvar", actionRight);
	    
	    actionRight = new StrutsActionRight("planes.perspectivas.trasladarmetas", true, false, "TRASLADAR_META");
	    this.mapa.put("planes.perspectivas.trasladarmetas", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.trasladarmetassalvar", false, true, "TRASLADAR_META");
	    this.mapa.put("planes.perspectivas.trasladarmetassalvar", actionRight);
	    
	    
	    
	    // Valor Inicial
	    actionRight = new StrutsActionRight("planes.valoresiniciales.configuraredicionvaloresiniciales", false, false, "INDICADOR_VALOR_INICIAL");
	    this.mapa.put("planes.valoresiniciales.configuraredicionvaloresiniciales", actionRight);
	    actionRight = new StrutsActionRight("planes.valoresiniciales.cancelarconfiguraredicionvaloresiniciales", true, false, "INDICADOR_VALOR_INICIAL");
	    this.mapa.put("planes.valoresiniciales.cancelarconfiguraredicionvaloresiniciales", actionRight);
	    actionRight = new StrutsActionRight("planes.valoresiniciales.editarvaloresiniciales", true, false, "INDICADOR_VALOR_INICIAL");
	    this.mapa.put("planes.valoresiniciales.editarvaloresiniciales", actionRight);
	    actionRight = new StrutsActionRight("planes.valoresiniciales.guardarvaloresiniciales", true, false, "INDICADOR_VALOR_INICIAL");
	    this.mapa.put("planes.valoresiniciales.guardarvaloresiniciales", actionRight);
	    actionRight = new StrutsActionRight("planes.valoresiniciales.cancelarguardarvaloresiniciales", true, false, "INDICADOR_VALOR_INICIAL");
	    this.mapa.put("planes.valoresiniciales.cancelarguardarvaloresiniciales", actionRight);
	    // Modelo Causa Efecto
	    actionRight = new StrutsActionRight("planes.modelos.listamodelo", false, false, "PLAN_MODELO_VER");
	    this.mapa.put("planes.modelos.listamodelo", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.gestionarmodelos", false, false, "PLAN_MODELO_DISENO");
	    this.mapa.put("planes.modelos.gestionarmodelos", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.imprimirmodelos", false, false, "PLAN_MODELO_DISENO_PRINT");
	    this.mapa.put("planes.modelos.imprimirmodelos", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.crearmodelo", false, false, "PLAN_MODELO_DISENO_ADD");
	    this.mapa.put("planes.modelos.crearmodelo", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.editarmodelo", false, false, "PLAN_MODELO_DISENO_EDIT");
	    this.mapa.put("planes.modelos.editarmodelo", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.eliminarmodelo", false, false, "PLAN_MODELO_DISENO_DELETE");
	    this.mapa.put("planes.modelos.eliminarmodelo", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.guardarmodelo", false, false, "PLAN_MODELO_DISENO");
	    this.mapa.put("planes.modelos.guardarmodelo", actionRight);
	    actionRight = new StrutsActionRight("planes.modelos.cancelarguardarmodelo", false, false, "PLAN_MODELO_DISENO");
	    this.mapa.put("planes.modelos.cancelarguardarmodelo", actionRight);
	    // Metodologia
	    actionRight = new StrutsActionRight("planes.plantillas.gestionarplantillasplanes", false, false, "METODOLOGIA");
	    this.mapa.put("planes.plantillas.gestionarplantillasplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.crearplantillaplanes", false, false, "METODOLOGIA_ADD");
	    this.mapa.put("planes.plantillas.crearplantillaplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.modificarplantillaplanes", false, false, "METODOLOGIA_EDIT");
	    this.mapa.put("planes.plantillas.modificarplantillaplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.verplantillaplanes", false, false, "METODOLOGIA_VIEW");
	    this.mapa.put("planes.plantillas.verplantillaplanes", actionRight);   
	    actionRight = new StrutsActionRight("planes.plantillas.eliminarplantillaplanes", false, false, "METODOLOGIA_DELETE");
	    this.mapa.put("planes.plantillas.eliminarplantillaplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.guardarplantillaplanes", false, false, "METODOLOGIA");
	    this.mapa.put("planes.plantillas.guardarplantillaplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.cancelarguardarplantillaplanes", false, false, "METODOLOGIA");
	    this.mapa.put("planes.plantillas.cancelarguardarplantillaplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.generarreporteplantillasplanes", false, false, "METODOLOGIA_PRINT");
	    this.mapa.put("planes.plantillas.generarreporteplantillasplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.plantillas.seleccionarplantillasplanes", true, false, "METODOLOGIA");
	    this.mapa.put("planes.plantillas.seleccionarplantillasplanes", actionRight);

	    // =================
	    // Perspectiva
	    // =================
	    actionRight = new StrutsActionRight("planes.perspectivas.gestionarperspectivas", false, false, "PLAN");
	    this.mapa.put("planes.perspectivas.gestionarperspectivas", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.crearperspectiva", false, false, "PLAN_PERSPECTIVA_ADD");
	    this.mapa.put("planes.perspectivas.crearperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.modificarperspectiva", false, false, "PLAN_PERSPECTIVA_EDIT");
	    this.mapa.put("planes.perspectivas.modificarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.verperspectiva", false, false, "PLAN_PERSPECTIVA_VIEW");
	    this.mapa.put("planes.perspectivas.verperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.eliminarperspectiva", false, false, "PLAN_PERSPECTIVA_DELETE");
	    this.mapa.put("planes.perspectivas.eliminarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.guardarperspectiva", false, false, "PLAN_PERSPECTIVA");
	    this.mapa.put("planes.perspectivas.guardarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.cancelarguardarperspectiva", false, false, "PLAN");
	    this.mapa.put("planes.perspectivas.cancelarguardarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.generarreporteperspectivas", false, false, "PLAN");
	    this.mapa.put("planes.perspectivas.generarreporteperspectivas", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.propiedadesperspectiva", false, false, "PLAN");
	    this.mapa.put("planes.perspectivas.propiedadesperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.seleccionarperspectivas", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.seleccionarperspectivas", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.seleccionarperspectivasorganizaciones", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.seleccionarperspectivasorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.seleccionarperspectivasplanes", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.seleccionarperspectivasplanes", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.seleccionarperspectivasperspectivas", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.seleccionarperspectivasperspectivas", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.visualizarperspectivas", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.visualizarperspectivas", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.visualizarperspectiva", true, false, "PLAN");
	    this.mapa.put("planes.perspectivas.visualizarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.visualizarperspectivasrelacionadas", true, false, "PLAN_PERSPECTIVA_RELACION");
	    this.mapa.put("planes.perspectivas.visualizarperspectivasrelacionadas", actionRight);
	    // Copiar Perspectiva
	    actionRight = new StrutsActionRight("planes.perspectivas.copiarleerperspectiva", false, true, "PLAN_PERSPECTIVA_COPY");
	    this.mapa.put("planes.perspectivas.copiarleerperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.copiarguardarperspectiva", false, false, "PLAN_PERSPECTIVA_COPY");
	    this.mapa.put("planes.perspectivas.copiarguardarperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.copiarcancelarperspectiva", false, false, "PLAN_PERSPECTIVA_COPY");
	    this.mapa.put("planes.perspectivas.copiarcancelarperspectiva", actionRight);  
	    actionRight = new StrutsActionRight("planes.indicadores.asignarpesosindicadoresplanperspectiva", false, false, "PLAN_PESO");
	    this.mapa.put("planes.indicadores.asignarpesosindicadoresplanperspectiva", actionRight);
	    actionRight = new StrutsActionRight("planes.indicadores.asignarpesosindicadoresplanobjetivo", false, false, "PLAN_PESO");
	    this.mapa.put("planes.indicadores.asignarpesosindicadoresplanobjetivo", actionRight);
	    actionRight = new StrutsActionRight("planes.indicadores.asignarpesosindicadoresplan", true, false, "PLAN_PESO");
	    this.mapa.put("planes.indicadores.asignarpesosindicadoresplan", actionRight);
	    actionRight = new StrutsActionRight("planes.perspectivas.enviaremail", true, false, "PLAN_PERSPECTIVA_EMAIL");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    // Plan Indicadores
	    actionRight = new StrutsActionRight("planes.indicadores.gestionarindicadoresplan", false, false, "PLAN");
	    this.mapa.put("planes.indicadores.gestionarindicadoresplan", actionRight);
	    actionRight = new StrutsActionRight("planes.indicadores.asociarindicadorplan", false, false, "INDICADOR");
	    this.mapa.put("planes.indicadores.asociarindicadorplan", actionRight);
	    actionRight = new StrutsActionRight("planes.indicadores.desasociarindicadorplan", false, false, "INDICADOR");
	    this.mapa.put("planes.indicadores.desasociarindicadorplan", actionRight);
	    actionRight = new StrutsActionRight("planes.indicadores.importarprogramado", false, false, "INDICADOR_VALOR_META");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    // =================
	    // Plan Iniciativas
	    // =================
	    actionRight = new StrutsActionRight("planes.iniciativas.gestionariniciativasplan", false, false, "PLAN");
	    this.mapa.put("planes.iniciativas.gestionariniciativasplan", actionRight);
	    actionRight = new StrutsActionRight("planes.iniciativas.asociariniciativaplan", true, false, "PLAN_PERSPECTIVA_EDIT");
	    this.mapa.put("planes.iniciativas.asociariniciativaplan", actionRight);
	    actionRight = new StrutsActionRight("planes.iniciativas.desasociariniciativaplan", true, false, "PLAN_PERSPECTIVA_EDIT");
	    this.mapa.put("planes.iniciativas.desasociariniciativaplan", actionRight);
	    actionRight = new StrutsActionRight("planes.iniciativas.vinculariniciativaplan", true, false, "INICIATIVA");
	    this.mapa.put("planes.iniciativas.vinculariniciativaplan", actionRight);
	    
	    // =================
	    // Iniciativas
	    // =================
	    actionRight = new StrutsActionRight("iniciativas.gestionariniciativas", false, false, "INICIATIVA");
	    this.mapa.put("iniciativas.gestionariniciativas", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.gestionariniciativasdeplan", false, false, "INICIATIVA");
	    this.mapa.put("iniciativas.gestionariniciativasdeplan", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.gestionarindicadoresiniciativa", false, false, "INICIATIVA");
	    this.mapa.put("iniciativas.gestionarindicadoresiniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.creariniciativa", false, false, "INICIATIVA_ADD");
	    this.mapa.put("iniciativas.creariniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.modificariniciativa", false, false, "INICIATIVA_EDIT");
	    this.mapa.put("iniciativas.modificariniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.veriniciativa", false, false, "INICIATIVA_VIEWALL");
	    this.mapa.put("iniciativas.veriniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.eliminariniciativa", false, false, "INICIATIVA_DELETE");
	    this.mapa.put("iniciativas.eliminariniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.guardariniciativa", false, false, "INICIATIVA");
	    this.mapa.put("iniciativas.guardariniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.cancelarguardariniciativa", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.cancelarguardariniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.propiedadesiniciativa", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.propiedadesiniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.visualizariniciativa", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.visualizariniciativa", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.guardariniciativasololectura", false, false, "INICIATIVA_READONLY");
	    this.mapa.put("iniciativas.guardariniciativasololectura", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.mostrargestioniniciativa", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.mostrargestioniniciativa", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.gestionarplanificacionseguimientos", true, false, "INICIATIVA");
	    this.mapa.put("planificacionseguimiento.gestionarplanificacionseguimientos", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.seleccionariniciativas", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.seleccionariniciativas", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.seleccionariniciativasorganizaciones", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.seleccionariniciativasorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.seleccionariniciativasplanes", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.seleccionariniciativasplanes", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.seleccionariniciativasiniciativas", true, false, "INICIATIVA");
	    this.mapa.put("iniciativas.seleccionariniciativasiniciativas", actionRight);
	    actionRight = new StrutsActionRight("iniciativas.enviaremail", true, true, "INICIATIVA_EMAIL");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.visualizariniciativasrelacionadas", true, true, "INICIATIVA_RELACION");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.historicoiniciativa", true, true, "INICIATIVA_HISTORICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
    	actionRight = new StrutsActionRight("iniciativas.desbloquearculminadosalvar", true, false, "INICIATIVA_CULMINADO");   	  	
    	this.mapa.put("iniciativas.desbloquearculminadosalvar", actionRight);    
  
    	actionRight = new StrutsActionRight("iniciativas.salvarcorreoiniciativas", true, false, "INICIATIVA_CDRREO");   	  	
    	this.mapa.put("iniciativas.salvarcorreoiniciativas", actionRight);
    	
    	
    	actionRight = new StrutsActionRight("iniciativas.protegerliberar", true, false, "PROTEGER_LIBERAR_INICIATIVA");
    	this.mapa.put(actionRight.getActionName(), actionRight);
 	    actionRight = new StrutsActionRight("iniciativas.protegerliberarsalvar", false, true, "PROTEGER_LIBERAR_INICIATIVA");
 	    this.mapa.put("iniciativas.protegerliberarsalvar", actionRight);
	    
	    
	    // Graficos Iniciativas
	    actionRight = new StrutsActionRight("iniciativa.grafico.configurar", false, true, "INICIATIVA_EVALUAR_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativa.grafico.creargrafico", false, true, "INICIATIVA_EVALUAR_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativa.grafico.preimprimir", false, true, "INICIATIVA_EVALUAR_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativa.grafico.imprimirgrafico", false, true, "INICIATIVA_EVALUAR_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativa.grafico.salvarimagen", false, true, "INICIATIVA_EVALUAR_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    // =================
	    // Estutus de las Iniciativas
	    // =================
	    actionRight = new StrutsActionRight("iniciativas.estatus.gestionarestatus", false, true, "INICIATIVA_ESTATUS");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.estatus.editar.crear", false, true, "INICIATIVA_ESTATUS_ADD");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.estatus.editar.modificar", false, true, "INICIATIVA_ESTATUS_EDIT");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.estatus.guardar", false, true, "INICIATIVA_ESTATUS");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.estatus.eliminar", false, true, "INICIATIVA_ESTATUS_DELETE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("iniciativas.estatus.generarreporte", false, true, "INICIATIVA_ESTATUS_PRINT");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    // =================
	    // Actividades
	    // =================
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.gestionaractividades", false, false, "ACTIVIDAD");
	    this.mapa.put("planificacionseguimiento.actividades.gestionaractividades", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.crearactividad", false, false, "ACTIVIDAD_ADD");
	    this.mapa.put("planificacionseguimiento.actividades.crearactividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.modificaractividad", false, false, "ACTIVIDAD_EDIT");
	    this.mapa.put("planificacionseguimiento.actividades.modificaractividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.veractividad", false, false, "ACTIVIDAD_VIEWALL");
	    this.mapa.put("planificacionseguimiento.actividades.veractividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.editaractividadfuncion", true, false, "ACTIVIDAD");
	    this.mapa.put("planificacionseguimiento.actividades.editaractividadfuncion", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.guardaractividad", false, false, "ACTIVIDAD");
	    this.mapa.put("planificacionseguimiento.actividades.guardaractividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.cancelarguardaractividad", false, false, "ACTIVIDAD");
	    this.mapa.put("planificacionseguimiento.actividades.cancelarguardaractividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.eliminaractividad", false, false, "ACTIVIDAD_DELETE");
	    this.mapa.put("planificacionseguimiento.actividades.eliminaractividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.generarreporteactividades", false, false, "ACTIVIDAD_PRINT");
	    this.mapa.put("planificacionseguimiento.actividades.generarreporteactividades", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.propiedadesactividad", false, false, "ACTIVIDAD");
	    this.mapa.put("planificacionseguimiento.actividades.propiedadesactividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.gestionargantt", false, false, "ACTIVIDAD");
	    this.mapa.put("planificacionseguimiento.actividades.gestionargantt", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.asignarpesosactividad", true, false, "ACTIVIDAD_PESO");
	    this.mapa.put("planificacionseguimiento.actividades.asignarpesosactividad", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.asociariniciativa", true, false, "ACTIVIDAD_ASOCIAR");
	    this.mapa.put("planificacionseguimiento.actividades.asociariniciativa", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.actividades.enviaremail", true, false, "ACTIVIDAD_EMAIL");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.gestionarproductosiniciativa", false, true, "INICIATIVA");
	    this.mapa.put("planificacionseguimiento.productos.gestionarproductosiniciativa", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.gestionarproductosautonomo", false, true, "PRODUCTO");
	    this.mapa.put("planificacionseguimiento.productos.gestionarproductosautonomo", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.gestionarproductos", false, true, "PRODUCTO");
	    this.mapa.put("planificacionseguimiento.productos.gestionarproductos", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.crearproducto", false, true, "PRODUCTO_ADD");
	    this.mapa.put("planificacionseguimiento.productos.crearproducto", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.modificarproducto", false, true, "PRODUCTO_EDIT");
	    this.mapa.put("planificacionseguimiento.productos.modificarproducto", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.eliminar.producto", false, true, "PRODUCTO_DELETE");
	    this.mapa.put("planificacionseguimiento.productos.eliminarproducto", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.guardarproducto", false, true, "PRODUCTO");
	    this.mapa.put("planificacionseguimiento.productos.guardarproducto", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.cancelarguardarproducto", true, true, "PRODUCTO");
	    this.mapa.put("planificacionseguimiento.productos.cancelarguardarproducto", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.iniciarregistroseguimiento", false, true, "INICIATIVA_SEGUIMIENTO");
	    this.mapa.put("planificacionseguimiento.productos.iniciarregistroseguimiento", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.actualizarregistroseguimiento", false, true, "INICIATIVA_SEGUIMIENTO");
	    this.mapa.put("planificacionseguimiento.productos.actualizarregistroseguimiento", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.guardarregistroseguimiento", false, true, "INICIATIVA_SEGUIMIENTO");
	    this.mapa.put("planificacionseguimiento.productos.guardarregistroseguimiento", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.cancelarguardarregistroseguimiento", true, true, "INICIATIVA_SEGUIMIENTO");
	    this.mapa.put("planificacionseguimiento.productos.cancelarguardarregistroseguimiento", actionRight);
	    actionRight = new StrutsActionRight("planificacionseguimiento.productos.generarreportedetalladoiniciativaporproductos", true, true, "INICIATIVA_SEGUIMIENTO");
	    this.mapa.put("planificacionseguimiento.productos.generarreportedetalladoiniciativaporproductos", actionRight);
	    // Selector
	    actionRight = new StrutsActionRight("actividades.selector.seleccionaractividad", true, false, "ACTIVIDAD_ADD");
	    this.mapa.put("actividades.selector.seleccionaractividad", actionRight);   
	    actionRight = new StrutsActionRight("actividades.selector.seleccionaractividadorganizaciones", true, false, "ACTIVIDAD_ADD");
	    this.mapa.put("actividades.selector.seleccionaractividadorganizaciones", actionRight);
	    actionRight = new StrutsActionRight("actividades.selector.seleccionaractividadplanes", true, false, "ACTIVIDAD_ADD");
	    this.mapa.put("actividades.selector.seleccionaractividadplanes", actionRight);
	    actionRight = new StrutsActionRight("actividades.selector.seleccionaractividadiniciativas", true, false, "ACTIVIDAD_ADD");
	    this.mapa.put("actividades.selector.seleccionaractividadiniciativas", actionRight);
	    actionRight = new StrutsActionRight("actividades.selector.seleccionaractividadactividades", true, false, "ACTIVIDAD_ADD");
	    this.mapa.put("actividades.selector.seleccionaractividadactividades", actionRight);

	    // Reportes
	    actionRight = new StrutsActionRight("reportes.cancelarasistentereporte", false, true, "REPORTES_EDIT");
	    this.mapa.put("reportes.cancelarasistentereporte", actionRight);
	    actionRight = new StrutsActionRight("reportes.guardarasistentereporte", false, true, "REPORTES_EDIT");
	    this.mapa.put("reportes.guardarasistentereporte", actionRight);
	    actionRight = new StrutsActionRight("reportes.asistentereporte", false, true, "REPORTES_EDIT");
	    this.mapa.put("reportes.asistentereporte", actionRight);
	
	    actionRight = new StrutsActionRight("reportes.cancelarlistareporte", false, true, "REPORTES_EDIT");
	    this.mapa.put("reportes.cancelarlistareporte", actionRight);
	    actionRight = new StrutsActionRight("reportes.seleccionarreporte", false, true, "REPORTES_EDIT");
	    this.mapa.put("reportes.seleccionarreporte", actionRight);
	    actionRight = new StrutsActionRight("reportes.listareporte", false, true, "REPORTES_EDIT");
	    this.mapa.put("reportes.listareporte", actionRight);
	    actionRight = new StrutsActionRight("reportes.eliminarreporte", true, false, "REPORTES_EDIT");
	    this.mapa.put("reportes.eliminarreporte", actionRight);
	    actionRight = new StrutsActionRight("reportes.crearformato", true, false, "REPORTES_EDIT");
	    this.mapa.put("reportes.crearformato", actionRight);
	    
	    actionRight = new StrutsActionRight("reportes.parametrosreporte", false, true, "ORGANIZACION"); //Permiso General
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	   
	    actionRight = new StrutsActionRight("reportes.indicadores.reportecomiteejecutivo", true, false, "INDICADOR_EVALUAR_REPORTE_COMITE");
	    this.mapa.put("reportes.indicadores.reportecomiteejecutivo", actionRight);
	    actionRight = new StrutsActionRight("reportes.indicadores.reportecomiteejecutivopdf", true, false, "INDICADOR_EVALUAR_REPORTE_COMITE");
	    this.mapa.put("reportes.indicadores.reportecomiteejecutivopdf", actionRight);
	    
	    // =================
	    // Vista de Datos
	    // =================
	    actionRight = new StrutsActionRight("vistasdatos.configurarvistadatos", false, true, "VISTA_DATOS_ADD");
	    this.mapa.put("vistasdatos.configurarvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.modificarconfigurarvistadatos", false, true, "VISTA_DATOS_EDIT");
	    this.mapa.put("vistasdatos.modificarconfigurarvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.verconfigurarvistadatos", false, true, "VISTA_DATOS_VIEW");
	    this.mapa.put("vistasdatos.verconfigurarvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.eliminar", false, true, "VISTA_DATOS_DELETE");
	    this.mapa.put("vistasdatos.eliminar", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.mostrarvistadatos", false, true, "VISTA_DATOS_REPORTE_");
	    this.mapa.put("vistasdatos.mostrarvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionarvista", false, true, "VISTA_DATOS_REPORTE_VIEW");
	    this.mapa.put("vistasdatos.seleccionarvista", actionRight);
	    
	    
	    actionRight = new StrutsActionRight("vistasdatos.imprimir", false, true, "VISTA_DATOS_REPORTE_PRINT");
	    this.mapa.put("vistasdatos.imprimir", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.exportarxls", false, true, "VISTA_DATOS_REPORTE_EXPORT");
	    this.mapa.put("vistasdatos.exportarxls", actionRight);
	    actionRight = new StrutsActionRight("reportes.cortereporte", false, true, "VISTA_DATOS_ADD");
	    this.mapa.put("reportes.cortereporte", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.editarreportetranversal", false, true, "VISTA_DATOS_ADD");
	    this.mapa.put("vistasdatos.editarreportetranversal", actionRight);
	    actionRight = new StrutsActionRight("reportes.mostrarreporte", false, true, "VISTA_DATOS_REPORTE_VIEW");
	    this.mapa.put("reportes.mostrarreporte", actionRight);
	    // =================
	    actionRight = new StrutsActionRight("vistasdatos.listardimensiones", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.listardimensiones", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.visualizardatos", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.visualizardatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.listarmiembros", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.listarmiembros", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionartiempo", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.seleccionartiempo", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionaratributo", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.seleccionaratributo", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionarvariable", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.seleccionarvariable", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionarindicador", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.seleccionarindicador", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionarorganizacion", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.seleccionarorganizacion", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.seleccionarplan", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.seleccionarplan", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.guardarconfiguracionvistadatos", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.guardarconfiguracionvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.guardarmostrarvistadatos", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.guardarmostrarvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.gestionarvistadatos", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.gestionarvistadatos", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.generarreportevistas", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.generarreportevistas", actionRight);
	    actionRight = new StrutsActionRight("vistasdatos.propiedades", false, true, "VISTA_DATOS");
	    this.mapa.put("vistasdatos.propiedades", actionRight);

	    // =================
	    // Reportes Grafico
	    // =================
	    actionRight = new StrutsActionRight("reportesgrafico.configurarreportegrafico", false, true, "VISTA_DATOS_ADD");
	    this.mapa.put("reportesgrafico.configurarreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.modificarreportegrafico", false, true, "VISTA_DATOS_EDIT");
	    this.mapa.put("reportesgrafico.modificarreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.verconfiguracionreportegrafico", false, true, "VISTA_DATOS_VIEW");
	    this.mapa.put("reportesgrafico.verconfiguracionreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.eliminarreportegrafico", false, true, "VISTA_DATOS_DELETE");
	    this.mapa.put("reportesgrafico.eliminarreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.mostrarreportegrafico", false, true, "VISTA_DATOS_REPORTE_");
	    this.mapa.put("reportesgrafico.mostrarreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionarvistagrafico", false, true, "VISTA_DATOS_REPORTE_VIEW");
	    this.mapa.put("reportesgrafico.seleccionarvistagrafico", actionRight);
	    
	    actionRight = new StrutsActionRight("reportesgrafico.graficarreporte", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.graficarreporte", actionRight);
	    
	    
	    actionRight = new StrutsActionRight("reportesgrafico.imprimirgrafico", false, true, "VISTA_DATOS_REPORTE_PRINT");
	    this.mapa.put("reportesgrafico.imprimirgrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.exportarxlsgrafico", false, true, "VISTA_DATOS_REPORTE_EXPORT");
	    this.mapa.put("reportesgrafico.exportarxlsgrafico", actionRight);
	
	    actionRight = new StrutsActionRight("reportes.mostrarreportegrafico", false, true, "VISTA_DATOS_REPORTE_VIEW");
	    this.mapa.put("reportes.mostrarreportegrafico", actionRight);
	    // =================
	    actionRight = new StrutsActionRight("reportesgrafico.listardimensionesgrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.listardimensionesgrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.visualizarreportegrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.visualizarreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.listarmiembrosgrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.listarmiembrosgrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionartiempografico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.seleccionartiempografico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionaratributografico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.seleccionaratributografico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionarvariablegrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.seleccionarvariablegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionarindicadorgrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.seleccionarindicadorgrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionarorganizaciongrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.seleccionarorganizaciongrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionarplangrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.seleccionarplangrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.guardarconfiguracionreportegrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.guardarconfiguracionreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.guardarmostrarreportegrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.guardarmostrarreportegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.gestionarreportegrafico", false, true, "VISTA_DATOS");
	    this.mapa.put("reportesgrafico.gestionarreportegrafico", actionRight);
	  
	    actionRight = new StrutsActionRight("reportesgrafico.cancelarasistentegrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.cancelarasistentegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.guardarasistentegrafico", true, false, "INDICADOR_EVALUAR_GRAFICO_ASISTENTE");
	    this.mapa.put("reportesgrafico.guardarasistentegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.asistentegrafico", false, true, "INDICADOR_EVALUAR_GRAFICO_ASISTENTE");
	    this.mapa.put("reportesgrafico.asistentegrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.cancelarlistagrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.cancelarlistagrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.seleccionargrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.seleccionargrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.listagrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.listagrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.eliminargrafico", true, false, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.eliminargrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.imprimirgrafico", true, false, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.imprimirgrafico", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.salvarimagen", true, false, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("reportesgrafico.salvarimagen", actionRight);
	    actionRight = new StrutsActionRight("reportesgrafico.grafico", true, false, "INDICADOR_EVALUAR_GRAFICO_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    
	    
	    // =================
	    // Portafolio
	    // =================
	    actionRight = new StrutsActionRight("portafolios.gestionarportafolios", false, false, "PORTAFOLIO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.gestionarportafoliosiniciativas", false, false, "PORTAFOLIO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.gestionarportafoliosindicadoresiniciativa", false, false, "PORTAFOLIO");
	    this.mapa.put(actionRight.getActionName(), actionRight);    
	    actionRight = new StrutsActionRight("portafolios.crearportafolio", false, false, "PORTAFOLIO_ADD");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.modificarportafolio", false, false, "PORTAFOLIO_EDIT");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.eliminarportafolio", false, false, "PORTAFOLIO_DELETE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.guardarportafolio", false, false, "PORTAFOLIO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.cancelarguardarportafolio", true, false, "PORTAFOLIO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.generarreporteportafolios", false, false, "PORTAFOLIO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.asociariniciativa", false, false, "PORTAFOLIO_INICIATIVA_ADD");
	    this.mapa.put(actionRight.getActionName(), actionRight);    
	    actionRight = new StrutsActionRight("portafolios.desasociariniciativa", false, false, "PORTAFOLIO_INICIATIVA_DELETE");
	    this.mapa.put(actionRight.getActionName(), actionRight);    
	    actionRight = new StrutsActionRight("portafolios.mostrarvista", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.calcular", false, false, "PORTAFOLIO_CALCULAR");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("portafolios.asignarpesos", false, false, "PORTAFOLIO_ASIGNARPESOS");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    // Reportes del Plan
	    actionRight = new StrutsActionRight("reportes.planes.ejecucion", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.ejecucionreporte", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.ejecucionreportexls", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.meta", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.metareportepdf", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.metareportexls", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.explicaciones", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.explicacionreporte", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.consolidado", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.consolidadoreportepdf", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.planes.consolidadoreportexls", false, false, "PLAN_REPORTE");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
	    // Reportes de la Iniciativa
	    actionRight = new StrutsActionRight("reportes.iniciativas.ejecucion", false, false, "INICIATIVA_EVALUAR_REPORTE_DETALLADO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.iniciativas.ejecucionresumida", false, false, "INICIATIVA_EVALUAR_REPORTE_RESUMIDA");
	    this.mapa.put("reportes.iniciativas.ejecucionresumida", actionRight);
	    actionRight = new StrutsActionRight("reportes.iniciativas.ejecucionreporte", false, false, "INICIATIVA_EVALUAR_REPORTE_DETALLADO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.iniciativas.ejecucioniniciativapdf", false, false, "INICIATIVA_EVALUAR_REPORTE_RESUMIDA");
	    this.mapa.put("reportes.iniciativas.ejecucioniniciativapdf", actionRight);
	    actionRight = new StrutsActionRight("reportes.iniciativas.ejecucioniniciativaxls", false, false, "INICIATIVA_EVALUAR_REPORTE_RESUMIDA");
	    this.mapa.put("reportes.iniciativas.ejecucioniniciativaxls", actionRight);
	    
	    
	    actionRight = new StrutsActionRight("reportes.iniciativas.resumidaejecucion", false, false, "INICIATIVA_EVALUAR_REPORTE_RESUMIDA");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.iniciativas.reporteiniciativapdf", false, false, "INICIATIVA_EVALUAR_REPORTE_RESUMIDA");
	    this.mapa.put("reportes.iniciativas.reporteiniciativapdf", actionRight);
	    actionRight = new StrutsActionRight("reportes.iniciativas.reporteiniciativaxls", false, false, "INICIATIVA_EVALUAR_REPORTE_RESUMIDA");
	    this.mapa.put("reportes.iniciativas.reporteiniciativaxls", actionRight);	    
	    
	    
	    actionRight = new StrutsActionRight("reportes.portafolios.detalle", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.portafolios.detalleejecucionpdf", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put("reportes.portafolios.detalleejecucionpdf", actionRight);
	    actionRight = new StrutsActionRight("reportes.portafolios.detalleejecucionxls", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put("reportes.portafolios.detalleejecucionxls", actionRight);	
	    actionRight = new StrutsActionRight("reportes.portafolios.resumida", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    actionRight = new StrutsActionRight("reportes.portafolios.resumidaejecucionpdf", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put("reportes.portafolios.resumidaejecucionpdf", actionRight);
	    actionRight = new StrutsActionRight("reportes.portafolios.resumidaejecucionxls", false, false, "PORTAFOLIO_VISTA");
	    this.mapa.put("reportes.portafolios.resumidaejecucionxls", actionRight);	
	    
	    actionRight = new StrutsActionRight("reportes.explicaciones.instrumentos.ejecucionpdf", false, false, "EXPLICACIONES_VISTA");
	    this.mapa.put("reportes.explicaciones.instrumentos.ejecucionpdf", actionRight);
	    actionRight = new StrutsActionRight("reportes.explicaciones.instrumentos.ejecucionxls", false, false, "EXPLICACIONES_VISTA");
	    this.mapa.put("reportes.explicaciones.instrumentos.ejecucionxls", actionRight);	
	    
	    // Grafico
	    actionRight = new StrutsActionRight("graficos.cancelarasistentegrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.cancelarasistentegrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.guardarasistentegrafico", true, false, "INDICADOR_EVALUAR_GRAFICO_ASISTENTE");
	    this.mapa.put("graficos.guardarasistentegrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.asistentegrafico", false, true, "INDICADOR_EVALUAR_GRAFICO_ASISTENTE");
	    this.mapa.put("graficos.asistentegrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.cancelarlistagrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.cancelarlistagrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.seleccionargrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.seleccionargrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.listagrafico", false, true, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.listagrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.eliminargrafico", true, false, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.eliminargrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.imprimirgrafico", true, false, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.imprimirgrafico", actionRight);
	    actionRight = new StrutsActionRight("graficos.salvarimagen", true, false, "INDICADOR_EVALUAR_GRAFICO");
	    this.mapa.put("graficos.salvarimagen", actionRight);
	    actionRight = new StrutsActionRight("graficos.grafico", true, false, "INDICADOR_EVALUAR_GRAFICO_GRAFICO");
	    this.mapa.put(actionRight.getActionName(), actionRight);
	    
    	// Importar
    	actionRight = new StrutsActionRight("indicadores.importar", false, true, "INDICADOR_MEDICION_IMPORTAR");
    	this.mapa.put("indicadores.importar", actionRight);
    	actionRight = new StrutsActionRight("indicadores.importarsalvar", true, false, "INDICADOR_MEDICION_IMPORTAR");
    	this.mapa.put("indicadores.importarsalvar", actionRight);
    	actionRight = new StrutsActionRight("indicadores.verarchivolog", true, false, "INDICADOR_MEDICION_IMPORTAR");
    	this.mapa.put("indicadores.verarchivolog", actionRight);
    	actionRight = new StrutsActionRight("indicadores.listaimportacion", true, false, "INDICADOR_MEDICION_IMPORTAR");
    	this.mapa.put("indicadores.listaimportacion", actionRight);
    	actionRight = new StrutsActionRight("indicadores.eliminarimportacion", true, false, "INDICADOR_MEDICION_IMPORTAR");
    	this.mapa.put("indicadores.eliminarimportacion", actionRight);
    	
    	// Inventario
    	
    	actionRight = new StrutsActionRight("indicadores.transformarinventario", false, true, "INDICADOR_TRANSFORMAR_INVENTARIO");
    	this.mapa.put("indicadores.transformarinventario", actionRight);
    	actionRight = new StrutsActionRight("indicadores.transformarinventariosalvar", true, false, "INDICADOR_TRANSFORMAR_INVENTARIO");
    	this.mapa.put("indicadores.transformarinventariosalvar", actionRight);
    	
    	// Transacciones
    	actionRight = new StrutsActionRight("transacciones.importar", true, false, "INDICADOR_MEDICION_TRANSACCION_IMPORTAR");
    	this.mapa.put(actionRight.getActionName(), actionRight);
    	actionRight = new StrutsActionRight("transacciones.reportetransaccion", true, false, "INDICADOR_MEDICION_TRANSACCION_REPORTE");
    	this.mapa.put(actionRight.getActionName(), actionRight);
    	actionRight = new StrutsActionRight("transacciones.reporteejecuciontransaccion", true, false, "INDICADOR_MEDICION_TRANSACCION_REPORTE");
    	this.mapa.put(actionRight.getActionName(), actionRight);
    	actionRight = new StrutsActionRight("transacciones.imprimirtransaccion", true, false, "INDICADOR_MEDICION_TRANSACCION_REPORTE");
    	this.mapa.put(actionRight.getActionName(), actionRight);
    	actionRight = new StrutsActionRight("transacciones.imprimirexceltransaccion", true, false, "INDICADOR_MEDICION_TRANSACCION_REPORTE");
    	this.mapa.put(actionRight.getActionName(), actionRight);
    	
    	// Tipos Proyecto
    	actionRight = new StrutsActionRight("tiposproyecto.gestionartiposproyecto", false, false, "TIPOS");
	    this.mapa.put("tiposproyecto.gestionartiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.creartiposproyecto", false, false, "TIPOS_ADD");
	    this.mapa.put("tiposproyecto.creartiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.modificartiposproyecto", false, false, "TIPOS_EDIT");
	    this.mapa.put("tiposproyecto.modificartiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.eliminartiposproyecto", false, false, "TIPOS_DELETE");
	    this.mapa.put("tiposproyecto.eliminartiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.guardartiposproyecto", false, false, "TIPOS");
	    this.mapa.put("tiposproyecto.guardartiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.cancelarguardartiposproyecto", true, false, "TIPOS");
	    this.mapa.put("tiposproyecto.cancelarguardartiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.generarreportetiposproyecto", false, false, "TIPOS_PRINT");
	    this.mapa.put("tiposproyecto.generarreportetiposproyecto", actionRight);
	    actionRight = new StrutsActionRight("tiposproyecto.seleccionartiposproyecto", true, false, "TIPOS");
	    this.mapa.put("tiposproyecto.seleccionartiposproyecto", actionRight);

    	
    	//Configuracion
		actionRight = new StrutsActionRight("configuracion.sistema.editar", true, false, "CONFIGURACION_SISTEMA");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("configuracion.sistema.email", false, false, null);
		this.mapa.put(actionRight.getActionName(), actionRight);
		
		
	    // =================
	    // Codigo de Enlace PGN
	    // =================
		actionRight = new StrutsActionRight("codigoenlace.gestionarcodigoenlace", true, false, "CODIGO_ENLACE");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("codigoenlace.crearcodigoenlace", true, false, "CODIGO_ENLACE_ADD");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("codigoenlace.modificarcodigoenlace", true, false, "CODIGO_ENLACE_EDIT");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("codigoenlace.eliminarcodigoenlace", true, false, "CODIGO_ENLACE_DELETE");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("codigoenlace.generarreportecodigoenlace", true, false, "CODIGO_ENLACE_PRINT");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("codigoenlace.guardarcodigoenlace", true, false, "CODIGO_ENLACE");
		this.mapa.put(actionRight.getActionName(), actionRight);
		actionRight = new StrutsActionRight("codigoenlace.seleccionarcodigoenlace", true, false, "CODIGO_ENLACE");
		this.mapa.put(actionRight.getActionName(), actionRight);
  	}

  	public static MapStrutsActionRight getInstance()
  	{
  		if (instance == null) 
  		{
  			try 
  			{
  				new StrategosMapStrutsActionRight();
  			} 
  			catch (Exception e) 
  			{
  				throw new ChainedRuntimeException("El mapeo de acciones a permisos no est� configurado correctamente.", e);
  			}
  		}

  		return instance;
  	}

  	public Map<String, StrutsActionRight> getMapa()
  	{
  		return this.mapa;
  	}
}