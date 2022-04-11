/**
 * 
 */
package com.visiongc.app.strategos.web.struts.reportes.forms;

import java.util.Calendar;
import java.util.List;

import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.web.struts.util.ObjetoReporte;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;

/**
 * @author Kerwin
 *
 */
public class ReporteForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private String reporteSeleccion;
	private String reporteNombre;
	private Long reporteSeleccionId;
	private Byte frecuencia;
	private List<Frecuencia> frecuencias;
	private String insumosFormula;
	private String fechaDesde;
	private String fechaHasta;
	private String respuesta;
	private String className;
	private Integer numeroMaximoPeriodos;
	private String anoInicial;
	private String anoFinal;
	private String mesInicial;
	private String mesFinal;
	private Integer periodoInicial;
	private Integer periodoFinal;
	private String descripcion;
	private Boolean publico;
	private Integer filas;
	private Integer columnas;
	private Integer anchoTablaDatos;
	private Boolean virtual;
	private Long id;
	private String configuracion;
	private Long planId;
	private Long objetoSeleccionadoId;
	private String nombrePlan;
	private String nombreOrganizacion;
	private String objetoStatus;
	private List<ObjetoClaveValor> grupoAnos;
	private List<ObjetoClaveValor> grupoMeses;
	private List<ObjetoClaveValor> grupoStatus;
	private Byte asistenteEditar;
	private Byte estatusSave;
	private Byte corte;
	private Byte alcance;
	private Byte tipoPeriodo;
	private Boolean visualizarObjetivo;
	private Boolean visualizarObjetivoEstadoParcial;
	private Boolean visualizarObjetivoEstadoAnual;
	private Boolean visualizarObjetivoAlerta;
	private Boolean visualizarIndicadores;
	private Boolean visualizarIndicadoresEjecutado;
	private Boolean visualizarIndicadoresMeta;
	private Boolean visualizarIndicadoresEstadoParcial;
	private Boolean visualizarIndicadoresEstadoParcialSuavisado;
	private Boolean visualizarIndicadoresEstadoAnual;
	private Boolean visualizarIndicadoresEstadoAnualSuavisado;
	private Boolean visualizarIndicadoresAlerta;
	private Boolean visualizarIniciativas;
	private Boolean visualizarIniciativasEjecutado;
	private Boolean visualizarIniciativasMeta;
	private Boolean visualizarIniciativasAlerta;
	private Boolean visualizarActividad;
	private Boolean visualizarActividadEjecutado;
	private Boolean visualizarActividadMeta;
	private Boolean visualizarActividadAlerta;
	private PlantillaPlanes plantillaPlanes;
	private String source;
	private Boolean acumular;
	private Integer ano;
	private Byte tipoReporte;
	private String filtroNombre;
	private FiltroForm filtro = new FiltroForm();
	private List<List<DatoCelda>> matrizDatos;
	
	//Campos para comunicacion
	private String valoresSeleccionados = null;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;
	
	// Campos para JavaScript
	private Byte orientacion;
	private String titulo;
	private List<ObjetoReporte> objetos;
	
	//tipos 
	private Long tipo;
	private List<TipoProyecto> tipos;
	private String estatus;
	private Boolean todosAno;
	
	//portafolio
	private Long portafolioId;
	
	//instrumento
	private Long cooperanteId;
	private Long tipoCooperanteId;
	private String nombre;
	
	public String getNombreOrganizacion() 
	{
	    return this.nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion) 
	{
	    this.nombreOrganizacion = nombreOrganizacion;
	}

	public List<ObjetoClaveValor> getGrupoAnos() 
	{
	    return this.grupoAnos;
	}

	public void setGrupoAnos(List<ObjetoClaveValor> grupoAnos) 
	{
	    this.grupoAnos = grupoAnos;
	}

	public List<ObjetoClaveValor> getGrupoMeses() 
	{
	    return this.grupoMeses;
	}

	public void setGrupoMeses(List<ObjetoClaveValor> grupoMeses) 
	{
	    this.grupoMeses = grupoMeses;
	}

	public List<ObjetoClaveValor> getGrupoStatus() 
	{
	    return this.grupoStatus;
	}

	public void setGrupoStatus(List<ObjetoClaveValor> grupoStatus) 
	{
	    this.grupoStatus = grupoStatus;
	}
	
	public String getObjetoStatus() 
	{
	    return this.objetoStatus;
	}
	  
	public void setObjetoStatus(String objetoStatus) 
	{
	    this.objetoStatus = objetoStatus;
	}
	
	public String getNombrePlan() 
	{
	    return this.nombrePlan;
	}
	  
	public void setNombrePlan(String nombrePlan) 
	{
	    this.nombrePlan = nombrePlan;
	}

	public Long getPlanId()
	{
	    return this.planId;
	}

	public void setPlanId(Long planId) 
	{
	    this.planId = planId;
	}	    

	public Long getObjetoSeleccionadoId()
	{
	    return this.objetoSeleccionadoId;
	}

	public void setObjetoSeleccionadoId(Long objetoSeleccionadoId) 
	{
	    this.objetoSeleccionadoId = objetoSeleccionadoId;
	}	    
	
	public String getMesInicial() 
	{
		return this.mesInicial;
	}
	  
	public void setMesInicial(String mesInicial) 
	{
		this.mesInicial = mesInicial;
	}

	public String getMesFinal() 
	{
		return this.mesFinal;
	}
	  
	public void setMesFinal(String mesFinal) 
	{
		this.mesFinal = mesFinal;
	}
	
	public String getReporteSeleccion()
	{
		return this.reporteSeleccion;
	}

	public void setReporteSeleccion(String reporteSeleccion) 
	{
		this.reporteSeleccion = reporteSeleccion;
	}

	public String getReporteNombre()
	{
		return this.reporteNombre;
	}

	public void setReporteNombre(String reporteNombre) 
	{
		this.reporteNombre = reporteNombre;
	}
	
	public Long getReporteSeleccionId()
	{
		return this.reporteSeleccionId;
	}

	public void setReporteSeleccionId(Long reporteSeleccionId) 
	{
		this.reporteSeleccionId = reporteSeleccionId;
	}
	
	public Byte getAsistenteEditar() 
	{
		return this.asistenteEditar;
	}

	public void setAsistenteEditar(Byte asistenteEditar) 
	{
		this.asistenteEditar = asistenteEditar;
	}
	
	public Byte getFrecuencia() 
	{
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
		this.frecuencia = frecuencia;
	}
	
	public List<Frecuencia> getFrecuencias() 
	{
		return this.frecuencias;
	}

	public void setFrecuencias(List<Frecuencia> frecuencias) 
	{
		this.frecuencias = frecuencias;
	}
	
	public String getFechaDesde() 
	{
	    return this.fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) 
	{
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() 
	{
		return this.fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) 
	{
		this.fechaHasta = fechaHasta;
	}
	
	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}

	public String getClassName() 
	{
	    return this.className;
	}

	public void setClassName(String className) 
	{
	    this.className = className;
	}

	public String getAnoInicial() 
	{
		return this.anoInicial;
	}

	public void setAnoInicial(String anoInicial) 
	{
		this.anoInicial = anoInicial;
	}

	public String getAnoFinal() 
	{
		return this.anoFinal;
	}

	public void setAnoFinal(String anoFinal) 
	{
		this.anoFinal = anoFinal;
	}
	
	public Integer getPeriodoInicial() 
	{
		return this.periodoInicial;
	}

	public void setPeriodoInicial(Integer periodoInicial) 
	{
		this.periodoInicial = periodoInicial;
	}
	
	public Integer getPeriodoFinal() 
	{
		return this.periodoFinal;
	}

	public void setPeriodoFinal(Integer periodoFinal) 
	{
		this.periodoFinal = periodoFinal;
	}
	
	public Integer getNumeroMaximoPeriodos() 
	{
	    return this.numeroMaximoPeriodos;
	}

	public void setNumeroMaximoPeriodos(Integer numeroMaximoPeriodos) 
	{
		this.numeroMaximoPeriodos = numeroMaximoPeriodos;
	}
	
	public String getInsumosFormula() 
	{
		return this.insumosFormula;
	}

	public void setInsumosFormula(String insumosFormula) 
	{
		this.insumosFormula = insumosFormula;
	}
	
	public String getSeparadorIndicadores() 
	{
		return "!;!";
	}
	
	public String getSeparadorSeries() 
	{
		return "!@!";
	}
	
	public String getCodigoIndicadorEliminado() 
	{
		return "!ELIMINADO!";
	}
	
	public String getNombreForma() 
	{
		return nombreForma;
	}

	public void setNombreForma(String nombreForma) 
	{
		this.nombreForma = nombreForma;
	}

	public String getNombreCampoOculto() 
	{
		return nombreCampoOculto;
	}

	public void setNombreCampoOculto(String nombreCampoOculto) 
	{
		this.nombreCampoOculto = nombreCampoOculto;
	}

	public String getNombreCampoValor() 
	{
		return nombreCampoValor;
	}

	public void setNombreCampoValor(String nombreCampoValor) 
	{
		this.nombreCampoValor = nombreCampoValor;
	}

	public String getFuncionCierre() 
	{
		return funcionCierre;
	}

	public void setFuncionCierre(String funcionCierre) 
	{
		this.funcionCierre = funcionCierre;
	}
	
	public String getValoresSeleccionados() 
	{
		return valoresSeleccionados;
	}

	public void setValoresSeleccionados(String valoresSeleccionados) 
	{
		this.valoresSeleccionados = valoresSeleccionados;
	}
	
	public String getDescripcion() 
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public Boolean getPublico() 
	{
		return this.publico;
	}

	public void setPublico(Boolean publico) 
	{
		this.publico = publico;
	}

	public Integer getFilas() 
	{
		return this.filas;
	}

	public void setFilas(Integer filas) 
	{
		this.filas = filas;
	}
	
	public Integer getColumnas() 
	{
		return this.columnas;
	}

	public void setColumnas(Integer columnas) 
	{
		this.columnas = columnas;
	}

  	public Integer getAnchoTablaDatos()
  	{
  		return this.anchoTablaDatos;
  	}

  	public void setAnchoTablaDatos(Integer anchoTablaDatos) 
  	{
  		this.anchoTablaDatos = anchoTablaDatos;
  	}

  	public List<List<DatoCelda>> getMatrizDatos()
  	{
  		return this.matrizDatos;
  	}

  	public void setMatrizDatos(List<List<DatoCelda>> matrizDatos) 
  	{
  		this.matrizDatos = matrizDatos;
  	}

	public Boolean getVirtual() 
	{
		return this.virtual;
	}

	public void setVirtual(Boolean virtual) 
	{
		this.virtual = virtual;
	}

	public Long getId() 
	{
		return this.id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getConfiguracion() 
	{
		return this.configuracion;
	}

	public void setConfiguracion(String configuracion) 
	{
		this.configuracion = configuracion;
	}
	
	public Byte getEstatusSave() 
	{
		return this.estatusSave;
	}

	public void setEstatusSave(Byte estatusSave) 
	{
		this.estatusSave = estatusSave;
	}

 	public Byte getCorte()
  	{
  		return this.corte;
  	}

  	public void setCorte(Byte corte) 
  	{
  		this.corte = Reporte.ReporteCorte.getCorte(corte);
  	}

 	public Byte getAlcance()
  	{
  		return this.alcance;
  	}

  	public void setAlcance(Byte alcance) 
  	{
  		this.alcance = ReporteForm.AlcanceStatus.getAlcanceStatus(alcance);
  	}

 	public Byte getTipoPeriodo()
  	{
  		return this.tipoPeriodo;
  	}

  	public void setTipoPeriodo(Byte tipoPeriodo) 
  	{
  		this.tipoPeriodo = ReporteForm.PeriodoStatus.getPeriodoStatus(tipoPeriodo);
  	}
  	
	public String getFrecuenciaNombre() 
	{
		return Frecuencia.getNombre(this.frecuencia);
	}

 	public Boolean getVisualizarObjetivo()
  	{
  		return this.visualizarObjetivo;
  	}

  	public void setVisualizarObjetivo(Boolean visualizarObjetivo) 
  	{
  		this.visualizarObjetivo = visualizarObjetivo;
  	}
	
 	public Boolean getVisualizarObjetivoEstadoParcial()
  	{
  		return this.visualizarObjetivoEstadoParcial;
  	}

  	public void setVisualizarObjetivoEstadoParcial(Boolean visualizarObjetivoEstadoParcial) 
  	{
  		this.visualizarObjetivoEstadoParcial = visualizarObjetivoEstadoParcial;
  	}
	
 	public Boolean getVisualizarObjetivoEstadoAnual()
  	{
  		return this.visualizarObjetivoEstadoAnual;
  	}

  	public void setVisualizarObjetivoEstadoAnual(Boolean visualizarObjetivoEstadoAnual) 
  	{
  		this.visualizarObjetivoEstadoAnual = visualizarObjetivoEstadoAnual;
  	}
	
 	public Boolean getVisualizarObjetivoAlerta()
  	{
  		return this.visualizarObjetivoAlerta;
  	}

  	public void setVisualizarObjetivoAlerta(Boolean visualizarObjetivoAlerta) 
  	{
  		this.visualizarObjetivoAlerta = visualizarObjetivoAlerta;
  	}

 	public Boolean getVisualizarIndicadores()
  	{
  		return this.visualizarIndicadores;
  	}

  	public void setVisualizarIndicadores(Boolean visualizarIndicadores) 
  	{
  		this.visualizarIndicadores = visualizarIndicadores;
  	}

 	public Boolean getVisualizarIndicadoresEjecutado()
  	{
  		return this.visualizarIndicadoresEjecutado;
  	}

  	public void setVisualizarIndicadoresEjecutado(Boolean visualizarIndicadoresEjecutado) 
  	{
  		this.visualizarIndicadoresEjecutado = visualizarIndicadoresEjecutado;
  	}

 	public Boolean getVisualizarIndicadoresMeta()
  	{
  		return this.visualizarIndicadoresMeta;
  	}

  	public void setVisualizarIndicadoresMeta(Boolean visualizarIndicadoresMeta) 
  	{
  		this.visualizarIndicadoresMeta = visualizarIndicadoresMeta;
  	}
  	
 	public Boolean getVisualizarIndicadoresEstadoParcial()
  	{
  		return this.visualizarIndicadoresEstadoParcial;
  	}

  	public void setVisualizarIndicadoresEstadoParcial(Boolean visualizarIndicadoresEstadoParcial) 
  	{
  		this.visualizarIndicadoresEstadoParcial = visualizarIndicadoresEstadoParcial;
  	}

 	public Boolean getVisualizarIndicadoresEstadoParcialSuavisado()
  	{
  		return this.visualizarIndicadoresEstadoParcialSuavisado;
  	}

  	public void setVisualizarIndicadoresEstadoParcialSuavisado(Boolean visualizarIndicadoresEstadoParcialSuavisado) 
  	{
  		this.visualizarIndicadoresEstadoParcialSuavisado = visualizarIndicadoresEstadoParcialSuavisado;
  	}
  	
 	public Boolean getVisualizarIndicadoresEstadoAnual()
  	{
  		return this.visualizarIndicadoresEstadoAnual;
  	}

  	public void setVisualizarIndicadoresEstadoAnual(Boolean visualizarIndicadoresEstadoAnual) 
  	{
  		this.visualizarIndicadoresEstadoAnual = visualizarIndicadoresEstadoAnual;
  	}

 	public Boolean getVisualizarIndicadoresEstadoAnualSuavisado()
  	{
  		return this.visualizarIndicadoresEstadoAnualSuavisado;
  	}

  	public void setVisualizarIndicadoresEstadoAnualSuavisado(Boolean visualizarIndicadoresEstadoAnualSuavisado) 
  	{
  		this.visualizarIndicadoresEstadoAnualSuavisado = visualizarIndicadoresEstadoAnualSuavisado;
  	}
  	
 	public Boolean getVisualizarIndicadoresAlerta()
  	{
  		return this.visualizarIndicadoresAlerta;
  	}

  	public void setVisualizarIndicadoresAlerta(Boolean visualizarIndicadoresAlerta) 
  	{
  		this.visualizarIndicadoresAlerta = visualizarIndicadoresAlerta;
  	}

 	public Boolean getVisualizarIniciativas()
  	{
  		return this.visualizarIniciativas;
  	}

  	public void setVisualizarIniciativas(Boolean visualizarIniciativas) 
  	{
  		this.visualizarIniciativas = visualizarIniciativas;
  	}
  	
 	public Boolean getVisualizarIniciativasEjecutado()
  	{
  		return this.visualizarIniciativasEjecutado;
  	}

  	public void setVisualizarIniciativasEjecutado(Boolean visualizarIniciativasEjecutado) 
  	{
  		this.visualizarIniciativasEjecutado = visualizarIniciativasEjecutado;
  	}
  	
 	public Boolean getVisualizarIniciativasMeta()
  	{
  		return this.visualizarIniciativasMeta;
  	}

  	public void setVisualizarIniciativasMeta(Boolean visualizarIniciativasMeta) 
  	{
  		this.visualizarIniciativasMeta = visualizarIniciativasMeta;
  	}
	
 	public Boolean getVisualizarIniciativasAlerta()
  	{
  		return this.visualizarIniciativasAlerta;
  	}

  	public void setVisualizarIniciativasAlerta(Boolean visualizarIniciativasAlerta) 
  	{
  		this.visualizarIniciativasAlerta = visualizarIniciativasAlerta;
  	}
	
 	public Boolean getVisualizarActividad()
  	{
  		return this.visualizarActividad;
  	}

  	public void setVisualizarActividad(Boolean visualizarActividad) 
  	{
  		this.visualizarActividad = visualizarActividad;
  	}
	
 	public Boolean getVisualizarActividadEjecutado()
  	{
  		return this.visualizarActividadEjecutado;
  	}

  	public void setVisualizarActividadEjecutado(Boolean visualizarActividadEjecutado) 
  	{
  		this.visualizarActividadEjecutado = visualizarActividadEjecutado;
  	}
	
 	public Boolean getVisualizarActividadMeta()
  	{
  		return this.visualizarActividadMeta;
  	}

  	public void setVisualizarActividadMeta(Boolean visualizarActividadMeta) 
  	{
  		this.visualizarActividadMeta = visualizarActividadMeta;
  	}
	
 	public Boolean getVisualizarActividadAlerta()
  	{
  		return this.visualizarActividadAlerta;
  	}

  	public void setVisualizarActividadAlerta(Boolean visualizarActividadAlerta) 
  	{
  		this.visualizarActividadAlerta = visualizarActividadAlerta;
  	}
  	
	public Byte getAlcanceObjetivo() 
	{
		return new Byte(AlcanceStatus.ALCANCE_OBJETIVO);
	}

	public Byte getAlcancePlan() 
	{
		return new Byte(AlcanceStatus.ALCANCE_PLAN);
	}

	public Byte getAlcanceOrganizacion() 
	{
		return new Byte(AlcanceStatus.ALCANCE_ORGANIZACION);
	}
	
	public Byte getAlcanceSubOrganizacion() 
	{
		return new Byte(AlcanceStatus.ALCANCE_SUB_ORGANIZACION);
	}


	public Byte getPeriodoAlCorte() 
	{
		return new Byte(PeriodoStatus.PERIODO_ALCORTE);
	}

	public Byte getPeriodoPorPeriodo() 
	{
		return new Byte(PeriodoStatus.PERIODO_PORPERIODO);
	}
	
	public PlantillaPlanes getPlantillaPlanes() 
	{
		return this.plantillaPlanes;
	}

	public void setPlantillaPlanes(PlantillaPlanes plantillaPlanes) 
	{
		this.plantillaPlanes = plantillaPlanes;
	}

	public String getSource() 
	{
		return this.source;
	}

	public void setSource(String source) 
	{
		this.source = source;
	}
	
	public Boolean getAcumular() 
	{
		return this.acumular;
	}

	public void setAcumular(Boolean acumular) 
	{
		this.acumular = acumular;
	}

	public Integer getAno() 
	{
		return this.ano;
	}

	public void setAno(Integer ano) 
	{
		this.ano = ano;
	}

	public Byte getTipoReporte() 
	{
		return this.tipoReporte;
	}

	public void setTipoReporte(Byte tipoReporte) 
	{
		this.tipoReporte = ReporteForm.TipoReportes.getTipoReportes(tipoReporte);
	}
	
  	public String getFiltroNombre()
  	{
  		return this.filtroNombre;
  	}

  	public void setFiltroNombre(String filtroNombre) 
  	{
  		this.filtroNombre = filtroNombre;
  	}

	public FiltroForm getFiltro() 
	{
		return this.filtro;
	}

	public void setFiltro(FiltroForm filtro) 
	{
		this.filtro = filtro;
	}
  	
	public Byte getOrientacion() 
	{
		return this.orientacion;
	}

	public void setOrientacion(Byte orientacion) 
	{
		this.orientacion = ReporteForm.OrientacionTipo.getOrientacionTipo(orientacion);
	}
	
	public String getTitulo() 
	{
		return this.titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}

	public List<ObjetoReporte> getObjetos() 
	{
		return this.objetos;
	}

	public void setObjetos(List<ObjetoReporte> objetos) 
	{
		this.objetos = objetos;
	}
	
	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public List<TipoProyecto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoProyecto> tipos) {
		this.tipos = tipos;
	}
			
  	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	public Boolean getTodosAno() {
		return todosAno;
	}

	public void setTodosAno(Boolean todosAno) {
		this.todosAno = todosAno;
	}
	
	public Long getPortafolioId() {
		return portafolioId;
	}

	public void setPortafolioId(Long portafolioId) {
		this.portafolioId = portafolioId;
	}
		
	public Long getCooperanteId() {
		return cooperanteId;
	}

	public void setCooperanteId(Long cooperanteId) {
		this.cooperanteId = cooperanteId;
	}

	public Long getTipoCooperanteId() {
		return tipoCooperanteId;
	}

	public void setTipoCooperanteId(Long tipoCooperanteId) {
		this.tipoCooperanteId = tipoCooperanteId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setValoresSeleccionados(null);

		this.reporteSeleccion = "";
		this.reporteNombre = "";
		this.reporteSeleccionId = new Long(0L);
		this.asistenteEditar = 0;
		this.frecuencia = Frecuencia.getFrecuenciaMensual();
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.respuesta = null;
		this.className = null;
		this.numeroMaximoPeriodos = null;
		this.anoInicial = null;
		this.anoFinal = null;
		this.periodoInicial = null;
		this.periodoFinal = null;
		this.descripcion = null;
		this.publico = null;
		this.filas = null;
		this.columnas = null;
		this.anchoTablaDatos = null;
		this.matrizDatos = null;
		this.virtual = null;
		this.id = null;
		this.configuracion = null;
		this.estatusSave = null;
		this.corte = null;
		this.planId = null;
		this.objetoSeleccionadoId = null;
		this.grupoAnos = null;
		this.grupoMeses = null;
		this.grupoStatus = null;
		this.estatusSave = null;
		this.insumosFormula = null;
		this.mesInicial = null;
		this.mesFinal = null;
		this.nombrePlan = null;
		this.nombreOrganizacion = null;
		this.alcance = AlcanceStatus.ALCANCE_OBJETIVO;
		this.visualizarObjetivo = true;
		this.visualizarObjetivoEstadoParcial = true;
		this.visualizarObjetivoEstadoAnual = true;
		this.visualizarObjetivoAlerta = true;
		this.visualizarIndicadores = false;
		this.visualizarIndicadoresEjecutado = false;
		this.visualizarIndicadoresMeta = false;
		this.visualizarIndicadoresEstadoParcial = false;
		this.visualizarIndicadoresEstadoAnual = false;
		this.visualizarIndicadoresAlerta = false;
		this.visualizarIniciativas = false;
		this.visualizarIniciativasEjecutado = false;
		this.visualizarIniciativasMeta = false;
		this.visualizarIniciativasAlerta = false;
		this.visualizarActividad = false;
		this.visualizarActividadEjecutado = false;
		this.visualizarActividadMeta = false;
		this.visualizarActividadAlerta = false;
		this.plantillaPlanes = null;
		this.source = null;
		this.acumular = new Boolean(false);
		this.tipoReporte = ReporteForm.TipoReportes.TIPO_PDF;
		this.tipoPeriodo = ReporteForm.PeriodoStatus.PERIODO_PORPERIODO;
		this.filtroNombre = null;
		
		// Campos para JavaScript 
		this.orientacion = ReporteForm.OrientacionTipo.ORIENTACION_PORTRAIT;
		this.titulo = null;
		this.objetos = null;
		this.tipo = null;
		this.tipos = null;
		this.todosAno = false;
  		Calendar fecha = Calendar.getInstance();
		this.ano = fecha.get(1);

		FiltroForm filtro = new FiltroForm();
		filtro.setHistorico(HistoricoType.getFiltroHistoricoNoMarcado());
		filtro.setIncluirTodos(true);
		this.setFiltro(filtro);
	}
	
	public static class ReporteStatus
	{
		private static final byte SAVE_EXITO = 1;
		private static final byte SAVE_NOEXITO = 2;
		
		public static Byte getStatus(Byte corte)
		{
			if (corte == SAVE_EXITO)
				return new Byte(SAVE_EXITO);
			else if (corte == SAVE_NOEXITO)
				return new Byte(SAVE_NOEXITO);
			else
				return null;
		}
		
		public static Byte getStatusExito() 
		{
			return new Byte(SAVE_EXITO);
		}

		public static Byte getStatusNoExito() 
		{
			return new Byte(SAVE_NOEXITO);
		}
	}

	public static class OrientacionTipo
	{
		public static final byte ORIENTACION_PORTRAIT = 1;
		public static final byte ORIENTACION_LANDSCAPE = 2;
		
		public static Byte getOrientacionTipo(Byte orientacion)
		{
			if (orientacion == ORIENTACION_PORTRAIT)
				return new Byte(ORIENTACION_PORTRAIT);
			else if (orientacion == ORIENTACION_LANDSCAPE)
				return new Byte(ORIENTACION_LANDSCAPE);
			else
				return null;
		}
	}
	
	public static class AlcanceStatus
	{
		private static final byte ALCANCE_OBJETIVO = 1;
		private static final byte ALCANCE_PLAN = 2;
		private static final byte ALCANCE_ORGANIZACION = 3;
		private static final byte ALCANCE_SUB_ORGANIZACION = 4;
		
		public static Byte getAlcanceStatus(Byte alcance)
		{
			if (alcance == ALCANCE_OBJETIVO)
				return new Byte(ALCANCE_OBJETIVO);
			else if (alcance == ALCANCE_PLAN)
				return new Byte(ALCANCE_PLAN);
			else if (alcance == ALCANCE_ORGANIZACION)
				return new Byte(ALCANCE_ORGANIZACION);
			else if (alcance == ALCANCE_SUB_ORGANIZACION)
				return new Byte(ALCANCE_SUB_ORGANIZACION);
			else
				return null;
		}
	}

	public static class PeriodoStatus
	{
		private static final byte PERIODO_ALCORTE = 1;
		private static final byte PERIODO_PORPERIODO = 2;
		
		public static Byte getPeriodoStatus(Byte tipoPeriodo)
		{
			if (tipoPeriodo == PERIODO_ALCORTE)
				return new Byte(PERIODO_ALCORTE);
			else if (tipoPeriodo == PERIODO_PORPERIODO)
				return new Byte(PERIODO_PORPERIODO);
			else
				return null;
		}
	}
	
	public static class TipoReportes
	{
		private static final byte TIPO_PDF = 1;
		private static final byte TIPO_EXCEL = 2;
		
		public static Byte getTipoReportes(Byte tipoReporte)
		{
			if (tipoReporte == TIPO_PDF)
				return new Byte(TIPO_PDF);
			else if (tipoReporte == TIPO_EXCEL)
				return new Byte(TIPO_EXCEL);
			else
				return null;
		}
	}
	
	public static class TipoObjeto
	{
		public static final byte TIPO_STRING = 1;
		public static final byte TIPO_TABLA = 2;
		
		public static Byte getTipoObjeto(Byte tipoObjeto)
		{
			if (tipoObjeto == TIPO_STRING)
				return new Byte(TIPO_STRING);
			else if (tipoObjeto == TIPO_TABLA)
				return new Byte(TIPO_TABLA);
			else
				return null;
		}
	}
}
