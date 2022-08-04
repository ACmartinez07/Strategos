/**
 * 
 */
package com.visiongc.app.strategos.web.struts.graficos.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.graficos.util.AnoPeriodo;
import com.visiongc.app.strategos.indicadores.graficos.util.DatosSerie;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.util.ValoresSerie;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.Pagina;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class GraficoForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;

	private String graficoSeleccion;
	private String graficoNombre;
	private Long graficoSeleccionId;
	private Byte asistenteEditar;
	private Byte frecuencia;
	private String frecuenciaNombre;
	private List<Frecuencia> frecuencias;
	private Byte frecuenciaAgrupada;
	private String insumosFormula;
	private String insumosSerie;
	private String valoresSeleccionados = null;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;
	private String respuesta;
	private String anoInicial;
	private String anoFinal;
	private Integer periodoInicial;
	private Integer periodoFinal;
	private Boolean acumular = false;
	private Boolean compararVsRangoAnterior;
	private Byte tipo;
	private String titulo;
	private String tituloEjeY;
	private String tituloEjeZ;
	private String tituloEjeX;
	private Long id;
	private List<DatosSerie> series = new ArrayList<DatosSerie>();
	private Boolean virtual;
	private Integer numeroMaximoPeriodos;
	private List<AnoPeriodo> anosPeriodos;
	private Boolean mostrarLeyendas = true;
	private Boolean mostrarTooltips = true;
	private Integer tamanoLeyenda = 11;
	private String xmlAnterior;
	private Double valorMinimo;
	private Double valorMaximo;
	private List<?> tiposSerie;
	private Boolean condicion;
	private Boolean mostrarCondicion;
	private String source;
	private Long objetoId;
	private String className;
	private Long claseId;
	private Boolean showDuppont;
	private Long indicadorId;
	private String ejeX;
	private String serieName;
	private String serieData;
	private String serieColor;
	private String serieTipo;
	private String rangoAlertas;
	private Boolean isAscendente;
	private Boolean showImage;
	private Long planId;
	private String url;
	private String ultimoPeriodo;
	private List<Indicador> indicadores;
	private Boolean esReporteGrafico = false;
	private Long reporteId;
	private Boolean desdeInstrumento;
	
	// Campos para presentacion Ejecutiva
	private Long vistaId;
	private Long paginaId;
	private Long previaCeldaId;
	private Long siguienteCeldaId;
	private Vista vista;
	private Pagina pagina;
	private Celda celda;
	private String fechaDesde;
	private String fechaHasta;
	private Boolean verTituloImprimir;
	private Boolean ajustarEscala;
	private Boolean showPath;
	private String ubicacionOrganizacion;
	private String ubicacionClase;
	private Boolean impVsAnoAnterior;
	private String objetosIds;
	
	// Configuracion Portafolio
	private Portafolio portafolio;
	private Long portafolioId;
	private Long organizacionId;
	private String organizacionNombre;
	private String objetoNombre;
	private Byte tipoObjeto;
	private Integer ano;
	private Integer periodo;
	private Byte tipoGrafico;
	private String fecha;
	private List<ValoresSerie> valores;
	private List<Frecuencia> frecuenciasCompatibles;
	
	
	public String getGraficoSeleccion()
	{
		return this.graficoSeleccion;
	}

	public void setGraficoSeleccion(String graficoSeleccion) 
	{
		this.graficoSeleccion = graficoSeleccion;
	}

	public String getGraficoNombre()
	{
		return this.graficoNombre;
	}

	public void setGraficoNombre(String graficoNombre) 
	{
		this.graficoNombre = graficoNombre;
	}
	
	public Long getGraficoSeleccionId()
	{
		return this.graficoSeleccionId;
	}

	public void setGraficoSeleccionId(Long graficoSeleccionId) 
	{
		this.graficoSeleccionId = graficoSeleccionId;
	}
	
	public Byte getAsistenteEditar() 
	{
		return this.asistenteEditar;
	}

	public void setAsistenteEditar(Byte asistenteEditar) 
	{
		this.asistenteEditar = asistenteEditar;
	}
	
	public String getFrecuenciaNombre() 
	{
		return this.frecuenciaNombre;
	}

	public void setFrecuenciaNombre(String frecuenciaNombre) 
	{
		this.frecuenciaNombre = frecuenciaNombre;
	}

	public Byte getFrecuencia() 
	{
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
		this.frecuencia = frecuencia;
	}
	
	public Byte getFrecuenciaAgrupada() 
	{
		return this.frecuenciaAgrupada;
	}

	public void setFrecuenciaAgrupada(Byte frecuenciaAgrupada) 
	{
		this.frecuenciaAgrupada = frecuenciaAgrupada;
	}
	
	public List<Frecuencia> getFrecuencias() 
	{
		return this.frecuencias;
	}

	public void setFrecuencias(List<Frecuencia> frecuencias) 
	{
		this.frecuencias = frecuencias;
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
	
	public String getValoresSeleccionados() 
	{
		return valoresSeleccionados;
	}

	public void setValoresSeleccionados(String valoresSeleccionados) 
	{
		this.valoresSeleccionados = valoresSeleccionados;
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
	
	public Boolean getAcumular() 
	{
		return this.acumular;
	}

	public void setAcumular(Boolean acumular) 
	{
		this.acumular = acumular;
	}

	public Boolean getCompararVsRangoAnterior() 
	{
		return this.compararVsRangoAnterior;
	}

	public void setCompararVsRangoAnterior(Boolean compararVsRangoAnterior) 
	{
		this.compararVsRangoAnterior = compararVsRangoAnterior;
	}

	public Byte getTipo() 
	{
		return this.tipo;
	}

	public void setTipo(Byte tipo) 
	{
		this.tipo = GraficoTipo.getTipo(tipo);
	}
	
	public String getTitulo() 
	{
		return this.titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}

	public String getTituloEjeY() 
	{
		return this.tituloEjeY;
	}

	public void setTituloEjeY(String tituloEjeY) 
	{
		this.tituloEjeY = tituloEjeY;
	}

	public String getTituloEjeZ() 
	{
		return this.tituloEjeZ;
	}

	public void setTituloEjeZ(String tituloEjeZ) 
	{
		this.tituloEjeZ = tituloEjeZ;
	}

	public String getTituloEjeX() 
	{
		return this.tituloEjeX;
	}

	public void setTituloEjeX(String tituloEjeX) 
	{
		this.tituloEjeX = tituloEjeX;
	}

	public Long getId() 
	{
		return this.id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getRespuesta() 
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}

	public List<DatosSerie> getSeries() 
	{
		return this.series;
	}

	public void setSeries(List<DatosSerie> series) 
	{
		this.series = series;
	}
	
	public Boolean getVirtual() 
	{
		return this.virtual;
	}

	public void setVirtual(Boolean virtual) 
	{
		this.virtual = virtual;
	}
	
	public List<AnoPeriodo> getAnosPeriodos() 
	{
	    return this.anosPeriodos;
	}

	public void setAnosPeriodos(List<AnoPeriodo> anosPeriodos) 
	{
	    this.anosPeriodos = anosPeriodos;
	}
	
	public Boolean getMostrarLeyendas() 
	{
		return this.mostrarLeyendas;
	}

	public void setMostrarLeyendas(Boolean mostrarLeyendas) 
	{
		this.mostrarLeyendas = mostrarLeyendas;
	}
	
	public Boolean getMostrarTooltips() 
	{
		return this.mostrarTooltips;
	}

	public void setMostrarTooltips(Boolean mostrarTooltips) 
	{
		this.mostrarTooltips = mostrarTooltips;
	}

	public void setTamanoLeyenda(Integer tamanoLeyenda) 
	{
		this.tamanoLeyenda = tamanoLeyenda;
	}
	
	public Integer getTamanoLeyenda() 
	{
		return this.tamanoLeyenda;
	}
	
	public Long getPaginaId() 
	{
		return this.paginaId;
	}

	public void setPaginaId(Long paginaId) 
	{
		this.paginaId = paginaId;
	}

	public Long getVistaId() 
	{
		return this.vistaId;
	}

	public void setVistaId(Long vistaId) 
	{
		this.vistaId = vistaId;
	}

	public Long getPreviaCeldaId() 
	{
		return this.previaCeldaId;
	}

	public void setPreviaCeldaId(Long previaCeldaId) 
	{
		this.previaCeldaId = previaCeldaId;
	}

	public Long getSiguienteCeldaId() 
	{
		return this.siguienteCeldaId;
	}

	public void setSiguienteCeldaId(Long siguienteCeldaId) 
	{
		this.siguienteCeldaId = siguienteCeldaId;
	}

	public Celda getCelda() 
	{
		return this.celda;
	}

	public void setCelda(Celda celda) 
	{
		this.celda = celda;
	}

	public Pagina getPagina() 
	{
		return this.pagina;
	}

	public void setPagina(Pagina pagina) 
	{
		this.pagina = pagina;
	}

	public Vista getVista() 
	{
		return this.vista;
	}

	public void setVista(Vista vista) 
	{
		this.vista = vista;
	}

	public String getXmlAnterior() 
	{
		return this.xmlAnterior;
	}

	public void setXmlAnterior(String xmlAnterior) 
	{
		this.xmlAnterior = xmlAnterior;
	}

	public Double getValorMaximo() 
	{
		return this.valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) 
	{
		this.valorMaximo = valorMaximo;
	}

	public Double getValorMinimo() 
	{
		return this.valorMinimo;
	}

	public void setValorMinimo(Double valorMinimo) 
	{
		this.valorMinimo = valorMinimo;
	}
	
	public String getInsumosSerie() 
	{
		return this.insumosSerie;
	}

	public void setInsumosSerie(String insumosSerie) 
	{
		this.insumosSerie = insumosSerie;
	}

	public List<?> getTiposSerie() 
	{
	    return this.tiposSerie;
	}

	public void setTiposSerie(List<?> tiposSerie) 
	{
	    this.tiposSerie = tiposSerie;
	}

	public List<Indicador> getIndicadores() 
	{
	    return this.indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) 
	{
	    this.indicadores = indicadores;
	}
	
	public Boolean getCondicion() 
	{
	    return this.condicion;
	}

	public void setCondicion(Boolean condicion) 
	{
	    this.condicion = condicion;
	}

	public Boolean getMostrarCondicion() 
	{
	    return this.mostrarCondicion;
	}

	public void setMostrarCondicion(Boolean mostrarCondicion) 
	{
	    this.mostrarCondicion = mostrarCondicion;
	}
	
	public String getSource() 
	{
	    return this.source;
	}

	public void setSource(String source) 
	{
	    this.source = source;
	}

	public Long getObjetoId() 
	{
	    return this.objetoId;
	}

	public void setObjetoId(Long objetoId) 
	{
	    this.objetoId = objetoId;
	}

	public String getClassName() 
	{
	    return this.className;
	}

	public void setClassName(String className) 
	{
	    this.className = className;
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

	public Boolean getVerTituloImprimir() 
	{
		return this.verTituloImprimir;
	}

	public void setVerTituloImprimir(Boolean verTituloImprimir) 
	{
		this.verTituloImprimir = verTituloImprimir;
	}

	public Boolean getAjustarEscala() 
	{
		return this.ajustarEscala;
	}

	public void setAjustarEscala(Boolean ajustarEscala) 
	{
		this.ajustarEscala = ajustarEscala;
	}

	public Long getClaseId() 
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
		this.claseId = claseId;
	}
	
	public String getUbicacionOrganizacion() 
	{
		return this.ubicacionOrganizacion;
	}

	public void setUbicacionOrganizacion(String ubicacionOrganizacion) 
	{
		this.ubicacionOrganizacion = ubicacionOrganizacion;
	}

	public String getUbicacionClase() 
	{
		return this.ubicacionClase;
	}

	public void setUbicacionClase(String ubicacionClase) 
	{
		this.ubicacionClase = ubicacionClase;
	}
	
	public Boolean getShowPath() 
	{
		return this.showPath;
	}

	public void setShowPath(Boolean showPath) 
	{
		this.showPath = showPath;
	}
	
	public Boolean getImpVsAnoAnterior() 
	{
		return this.impVsAnoAnterior;
	}

	public void setImpVsAnoAnterior(Boolean impVsAnoAnterior) 
	{
		this.impVsAnoAnterior = impVsAnoAnterior;
	}

	public String getObjetosIds() 
	{
		return this.objetosIds;
	}

	public void setObjetosIds(String objetosIds) 
	{
		this.objetosIds = objetosIds;
	}
	
	// ==============
	// Portafolio
	// ==============
	public Long getOrganizacionId() 
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public String getOrganizacionNombre() 
	{
		return this.organizacionNombre;
	}

	public void setOrganizacionNombre(String organizacionNombre) 
	{
		this.organizacionNombre = organizacionNombre;
	}

	public String getObjetoNombre() 
	{
		return this.objetoNombre;
	}

	public void setObjetoNombre(String objetoNombre) 
	{
		this.objetoNombre = objetoNombre;
	}
	
	public Byte getTipoObjeto() 
	{
		return this.tipoObjeto;
	}

	public void setTipoObjeto(Byte tipoObjeto) 
	{
		this.tipoObjeto = tipoObjeto;
	}

	public Integer getAno() 
	{
		return this.ano;
	}

	public void setAno(Integer ano) 
	{
		this.ano = ano;
	}

	public Integer getPeriodo() 
	{
		return this.periodo;
	}

	public void setPeriodo(Integer periodo) 
	{
		this.periodo = periodo;
	}
	
	public Byte getTipoGrafico() 
	{
		return this.tipoGrafico;
	}

	public void setTipoGrafico(Byte tipoGrafico) 
	{
		this.tipoGrafico = GraficoTipoIniciativa.getTipoGraficoIniciativa(tipoGrafico);
	}

	public String getFecha() 
	{
		return this.fecha;
	}

	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}

	public List<ValoresSerie> getValores() 
	{
		return this.valores;
	}

	public void setValores(List<ValoresSerie> valores) 
	{
		this.valores = valores;
	}
	
	public List<Frecuencia> getFrecuenciasCompatibles() 
	{
		return this.frecuenciasCompatibles;
	}

	public void setFrecuenciasCompatibles(List<Frecuencia> frecuenciasCompatibles) 
	{
		this.frecuenciasCompatibles = frecuenciasCompatibles;
	}
	
	public Boolean getShowDuppont() 
	{
	    return this.showDuppont;
	}

	public void setShowDuppont(Boolean showDuppont) 
	{
	    this.showDuppont = showDuppont;
	}
	 
	public Long getIndicadorId() 
	{
	    return this.indicadorId;
	}

	public void setIndicadorId(Long indicadorId) 
	{
	    this.indicadorId = indicadorId;
	}

	public String getEjeX() 
	{
	    return this.ejeX;
	}

	public void setEjeX(String ejeX) 
	{
	    this.ejeX = ejeX;
	}

	public String getSerieData() 
	{
	    return this.serieData;
	}

	public void setSerieData(String serieData) 
	{
	    this.serieData = serieData;
	}

	public String getSerieName() 
	{
	    return this.serieName;
	}

	public void setSerieName(String serieName) 
	{
	    this.serieName = serieName;
	}
	
	public String getSerieColor() 
	{
	    return this.serieColor;
	}

	public void setSerieColor(String serieColor) 
	{
	    this.serieColor = serieColor;
	}

	public String getSerieTipo() 
	{
	    return this.serieTipo;
	}

	public void setSerieTipo(String serieTipo) 
	{
	    this.serieTipo = serieTipo;
	}

	public String getRangoAlertas() 
	{
	    return this.rangoAlertas;
	}

	public void setRangoAlertas(String rangoAlertas) 
	{
	    this.rangoAlertas = rangoAlertas;
	}
	
	public Boolean getShowImage() 
	{
	    return this.showImage;
	}

	public void setShowImage(Boolean showImage) 
	{
	    this.showImage = showImage;
	}

	public Boolean getIsAscendente() 
	{
	    return this.isAscendente;
	}

	public void setIsAscendente(Boolean isAscendente) 
	{
	    this.isAscendente = isAscendente;
	}

	public Long getPlanId() 
	{
	    return this.planId;
	}

	public void setPlanId(Long planId) 
	{
	    this.planId = planId;
	}
	
	public String getUrl() 
	{
	    return this.url;
	}

	public void setUrl(String url) 
	{
	    this.url = url;
	}

	public String getUltimoPeriodo() 
	{
	    return this.ultimoPeriodo;
	}

	public void setUltimoPeriodo(String ultimoPeriodo) 
	{
	    this.ultimoPeriodo = ultimoPeriodo;
	}

	public Portafolio getPortafolio() 
	{
	    return this.portafolio;
	}

	public void setPortafolio(Portafolio portafolio) 
	{
	    this.portafolio = portafolio;
	}

	public Long getPortafolioId() 
	{
	    return this.portafolioId;
	}

	public void setPortafolioId(Long portafolioId) 
	{
	    this.portafolioId = portafolioId;
	}
	
	public Boolean getEsReporteGrafico() {
		return esReporteGrafico;
	}

	public void setEsReporteGrafico(Boolean esReporteGrafico) {
		this.esReporteGrafico = esReporteGrafico;
	}

	public Long getReporteId() {
		return reporteId;
	}

	public void setReporteId(Long reporteId) {
		this.reporteId = reporteId;
	}

	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);
		setValoresSeleccionados(null);

		this.graficoSeleccion = "";
		this.graficoNombre = "";
		this.graficoSeleccionId = new Long(0L);
		this.id = new Long(0L);
		this.asistenteEditar = 0;
		this.frecuencia = Frecuencia.getFrecuenciaMensual();
		this.tipo = GraficoTipo.getTipoLinea();
		this.respuesta = "";
		this.series = new ArrayList<DatosSerie>();
		this.virtual = false;
		this.numeroMaximoPeriodos = new Integer(0);
		this.vistaId = new Long(0L);
		this.paginaId = new Long(0L);
		this.portafolioId = new Long(0L);
		this.series = new ArrayList<DatosSerie>();
		this.tiposSerie = new ArrayList<Object>();
		this.indicadores = new ArrayList<Indicador>();
		this.condicion = false;
		this.mostrarCondicion = true;
		this.verTituloImprimir = true;
		this.ajustarEscala = true;
		this.claseId = null;
		this.showPath = false;
		this.ubicacionOrganizacion = "";
		this.ubicacionClase = "";
		this.impVsAnoAnterior = false;
		this.objetosIds = null;
		this.showDuppont = false;
		this.indicadorId = null;
		this.frecuenciaNombre = null;
		this.ejeX = null;
		this.serieName = null;
		this.serieData = null;
		this.serieColor = null;
		this.serieTipo = null;
		this.showImage = null;
		this.rangoAlertas = null;
		this.isAscendente = null;
		this.planId = null;
		this.url = null;
		this.ultimoPeriodo = null;
		this.esReporteGrafico = false;
		this.desdeInstrumento = false;
		
		// Configuracion
		this.objetoId = null;
		this.objetoNombre = null;
		this.tipoObjeto = null;
		this.ano = null;
		this.periodo = null;
		this.frecuencias = Frecuencia.getFrecuencias();
		this.tipoGrafico = null;
		this.organizacionId = null;
		this.organizacionNombre = null;
		this.fecha = null;
		this.source = null;
		
		// Grafico Portafolio
		this.valores = new ArrayList<ValoresSerie>();
		this.frecuenciasCompatibles = new ArrayList<Frecuencia>();
	}
	
	public Boolean getDesdeInstrumento() {
		return desdeInstrumento;
	}

	public void setDesdeInstrumento(Boolean desdeInstrumento) {
		this.desdeInstrumento = desdeInstrumento;
	}

	public static class GraficoTipo
	{
		private static final byte TIPO_LINEA = 1;
		private static final byte TIPO_COLUMNA = 2;
		private static final byte TIPO_BARRA = 3;
		private static final byte TIPO_AREA = 4;
		private static final byte TIPO_TORTA = 5;
		
		private static final byte TIPO_PARETO = 6;
		private static final byte TIPO_COMBINADO = 8;

		private static final byte TIPO_LINEA3D = 13;
		private static final byte TIPO_MULTIPLETORTA = 7;
		
		private static final byte TIPO_CASCADA = 9;
		private static final byte TIPO_TORTA3D = 10;
		private static final byte TIPO_BARRA3D = 11;
		private static final byte TIPO_BARRAAPILADA3D = 12;
		
		private static final byte TIPO_GAUGE = 14;

		public static Byte getTipo(Byte tipo)
		{
			if (tipo == TIPO_LINEA)
				return new Byte(TIPO_LINEA);
			else if (tipo == TIPO_COLUMNA)
				return new Byte(TIPO_COLUMNA);
			else if (tipo == TIPO_BARRA)
				return new Byte(TIPO_BARRA);
			else if (tipo == TIPO_AREA)
				return new Byte(TIPO_AREA);
			else if (tipo == TIPO_TORTA)
				return new Byte(TIPO_TORTA);
			else if (tipo == TIPO_PARETO)
				return new Byte(TIPO_PARETO);
			else if (tipo == TIPO_MULTIPLETORTA)
				return new Byte(TIPO_MULTIPLETORTA);
			else if (tipo == TIPO_COMBINADO)
				return new Byte(TIPO_COMBINADO);
			else if (tipo == TIPO_CASCADA)
				return new Byte(TIPO_CASCADA);
			else if (tipo == TIPO_TORTA3D)
				return new Byte(TIPO_TORTA3D);
			else if (tipo == TIPO_BARRA3D)
				return new Byte(TIPO_BARRA3D);
			else if (tipo == TIPO_BARRAAPILADA3D)
				return new Byte(TIPO_BARRAAPILADA3D);
			else if (tipo == TIPO_LINEA3D)
				return new Byte(TIPO_LINEA3D);
			else if (tipo == TIPO_GAUGE)
				return new Byte(TIPO_GAUGE);
			else
				return null;
		}
		
		public static Byte getTipoLinea() 
		{
			return new Byte(TIPO_LINEA);
		}

		public static Byte getTipoColumna() 
		{
			return new Byte(TIPO_COLUMNA);
		}
		
		public static Byte getTipoBarra() 
		{
			return new Byte(TIPO_BARRA);
		}		

		public static Byte getTipoArea() 
		{
			return new Byte(TIPO_AREA);
		}		

		public static Byte getTipoTorta() 
		{
			return new Byte(TIPO_TORTA);
		}		

		public static Byte getTipoPareto() 
		{
			return new Byte(TIPO_PARETO);
		}		

		public static Byte getTipoMultipleTorta() 
		{
			return new Byte(TIPO_MULTIPLETORTA);
		}		

		public static Byte getTipoCombinado() 
		{
			return new Byte(TIPO_COMBINADO);
		}		

		public static Byte getTipoCascada() 
		{
			return new Byte(TIPO_CASCADA);
		}		

		public static Byte getTipoTorta3d() 
		{
			return new Byte(TIPO_TORTA3D);
		}		

		public static Byte getTipoBarra3d() 
		{
			return new Byte(TIPO_BARRA3D);
		}		

		public static Byte getTipoBarraApilada3d() 
		{
			return new Byte(TIPO_BARRAAPILADA3D);
		}		

		public static Byte getTipoLina3d() 
		{
			return new Byte(TIPO_LINEA3D);
		}
		
		public static Byte getTipoGauge() 
		{
			return new Byte(TIPO_GAUGE);
		}		
	}
	
	public static class GraficoTipoIniciativa
	{
		private static final byte GRAFICO_ESTATUS = 0;
		private static final byte GRAFICO_PORCENTAJES = 1;
		private static final byte GRAFICO_TIPOS_ESTATUS = 2;
		private static final byte GRAFICO_PORCENTAJE_PORTAFOLIO = 3;
		
		private static Byte getTipoGraficoIniciativa(Byte tipo)
		{
			if (tipo == GRAFICO_ESTATUS)
				return getGraficoTipoEstatus();
			else if (tipo == GRAFICO_PORCENTAJES)
				return new Byte(GRAFICO_PORCENTAJES);
			else if (tipo == GRAFICO_TIPOS_ESTATUS)
				return new Byte(GRAFICO_TIPOS_ESTATUS);
			else if (tipo == GRAFICO_PORCENTAJE_PORTAFOLIO)
				return new Byte(GRAFICO_PORCENTAJE_PORTAFOLIO);
			else
				return null;
		}
		
		public static Byte getGraficoTipoEstatus() 
		{
			return new Byte(GRAFICO_ESTATUS);
		}

		public static Byte getGraficoTipoPorcentajes() 
		{
			return new Byte(GRAFICO_PORCENTAJES);
		}

		public static Byte getGraficoTipoTiposEstatus() 
		{
			return new Byte(GRAFICO_TIPOS_ESTATUS);
		}

		public static Byte getGraficoPorcentajePortafolio() 
		{
			return new Byte(GRAFICO_PORCENTAJE_PORTAFOLIO);
		}
		
		public static String getNombre(byte tipo) 
		{
			String nombre = "";

			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		    if (tipo == GRAFICO_ESTATUS) 
		    	nombre = messageResources.getResource("grafico.estatus");
		    else if (tipo == GRAFICO_PORCENTAJES) 
		    	nombre = messageResources.getResource("grafico.porcentajes");
		    else if (tipo == GRAFICO_TIPOS_ESTATUS) 
		    	nombre = messageResources.getResource("grafico.tipos.estatus");
		    else if (tipo == GRAFICO_PORCENTAJE_PORTAFOLIO) 
		    	nombre = messageResources.getResource("grafico.porcentaje.portafolio");

		    return nombre;
		}		
	}
	
	public static class GraficoEstatus
	{
		private static final Long ESTATUS_SIN_INICIAR = 0L;
		private static final Long ESTATUS_SIN_INICIAR_DESFASADA = 1L;
		private static final Long ESTATUS_EN_EJECUCION_SIN_RETRASOS = 2L;
		private static final Long ESTATUS_EN_EJECUCION_CON_RETRASOS_SUPERABLES = 3L;
		private static final Long ESTATUS_EN_EJECUCION_CON_RETRASOS_SIGNIFICATIVOS = 4L;
		private static final Long ESTATUS_CONCLUIDAS = 5L;
		private static final Long ESTATUS_0Y25 = 6L;
		private static final Long ESTATUS_25Y50 = 7L;
		private static final Long ESTATUS_50Y75 = 8L;
		private static final Long ESTATUS_75Y100 = 9L;
		
		private Long id;
		private String nombre;

		public Long getId()
		{
			return this.id;
		}

		public void setId(Long id) 
		{
			this.id = id;
		}

		public String getNombre() 
		{
			return this.nombre;
		}

		public void setNombre(String nombre) 
		{
			this.nombre = nombre;
		}

		public static Long getEstatus(Long estatus)
		{
			if (estatus == ESTATUS_SIN_INICIAR)
				return getEstatusSinIniciar();
			else if (estatus == ESTATUS_SIN_INICIAR_DESFASADA)
				return getEstatusSinIniciarDesfasada();
			else if (estatus == ESTATUS_EN_EJECUCION_SIN_RETRASOS)
				return getEstatusEnEjecucionSinRetrasos();
			else if (estatus == ESTATUS_EN_EJECUCION_CON_RETRASOS_SUPERABLES)
				return getEstatusEnEjecucionConRetrasosSuperables();
			else if (estatus == ESTATUS_EN_EJECUCION_CON_RETRASOS_SIGNIFICATIVOS)
				return getEstatusEnEjecucionConRetrasosSignificativos();
			else if (estatus == ESTATUS_0Y25)
				return getEstatus0y25();
			else if (estatus == ESTATUS_25Y50)
				return getEstatus25y50();
			else if (estatus == ESTATUS_50Y75)
				return getEstatus50y75();
			else if (estatus == ESTATUS_75Y100)
				return getEstatus75y100();
			else if (estatus == ESTATUS_CONCLUIDAS)
				return getEstatusConcluidas();
			else
				return null;
		}
		
		public static Long getEstatusSinIniciar() 
		{
			return ESTATUS_SIN_INICIAR;
		}
		
		public static Long getEstatusSinIniciarDesfasada() 
		{
			return ESTATUS_SIN_INICIAR_DESFASADA;
		}		

		public static Long getEstatusEnEjecucionSinRetrasos() 
		{
			return ESTATUS_EN_EJECUCION_SIN_RETRASOS;
		}		

		public static Long getEstatusEnEjecucionConRetrasosSuperables() 
		{
			return ESTATUS_EN_EJECUCION_CON_RETRASOS_SUPERABLES;
		}		
		
		public static Long getEstatusEnEjecucionConRetrasosSignificativos() 
		{
			return ESTATUS_EN_EJECUCION_CON_RETRASOS_SIGNIFICATIVOS;
		}
		
		public static Long getEstatusConcluidas() 
		{
			return ESTATUS_CONCLUIDAS;
		}

		public static Long getEstatus0y25() 
		{
			return ESTATUS_0Y25;
		}

		public static Long getEstatus25y50() 
		{
			return ESTATUS_25Y50;
		}
		
		public static Long getEstatus50y75() 
		{
			return ESTATUS_50Y75;
		}
		
		public static Long getEstatus75y100() 
		{
			return ESTATUS_75Y100;
		}
		
		public static List<GraficoEstatus> getListEstatus(byte tipo) 
		{
		    return getListEstatus(null, tipo);
		}

		public static List<GraficoEstatus> getListEstatus(VgcMessageResources messageResources, byte tipo)
		{
			if (messageResources == null) 
				messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

			List<GraficoEstatus> estatus = new ArrayList<GraficoEstatus>();

			GraficoEstatus estado = new GraficoEstatus();

			if (tipo == GraficoTipoIniciativa.getGraficoTipoTiposEstatus())
			{
				StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
				Map<String, String> filtros = new HashMap<String, String>();
				PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc", true, filtros);
				strategosIniciativaEstatusService.close();
				
				for (Iterator<IniciativaEstatus> iter = paginaIniciativaEstatus.getLista().iterator(); iter.hasNext(); ) 
				{
					IniciativaEstatus iniciativaEstatus = (IniciativaEstatus)iter.next();

					estado = new GraficoEstatus();
				    estado.id = iniciativaEstatus.getId();
				    estado.nombre = iniciativaEstatus.getNombre();
				    estatus.add(estado);
				}
			}
			else
			{
				estado = new GraficoEstatus();
			    estado.id = ESTATUS_SIN_INICIAR;
			    estado.nombre = messageResources.getResource("estado.sin.iniciar");
			    estatus.add(estado);

				estado = new GraficoEstatus();
			    estado.id = ESTATUS_SIN_INICIAR_DESFASADA;
			    estado.nombre = messageResources.getResource("estado.sin.iniciar.desfasada");
			    estatus.add(estado);

			    if (tipo == GraficoTipoIniciativa.getGraficoTipoEstatus())
			    {
					estado = new GraficoEstatus();
				    estado.id = ESTATUS_EN_EJECUCION_SIN_RETRASOS;
				    estado.nombre = messageResources.getResource("estado.en.ejecucion.sin.retrasos");
				    estatus.add(estado);

					estado = new GraficoEstatus();
				    estado.id = ESTATUS_EN_EJECUCION_CON_RETRASOS_SUPERABLES;
				    estado.nombre = messageResources.getResource("estado.en.ejecucion.con.retrasos.superables");
				    estatus.add(estado);

					estado = new GraficoEstatus();
				    estado.id = ESTATUS_EN_EJECUCION_CON_RETRASOS_SIGNIFICATIVOS;
				    estado.nombre = messageResources.getResource("estado.en.ejecucion.con.retrasos.significativos");
				    estatus.add(estado);
			    }
			    else if (tipo == GraficoTipoIniciativa.getGraficoTipoPorcentajes())
			    {
					estado = new GraficoEstatus();
				    estado.id = ESTATUS_0Y25;
				    estado.nombre = messageResources.getResource("estado.mayor.0.menor.25");
				    estatus.add(estado);

					estado = new GraficoEstatus();
				    estado.id = ESTATUS_25Y50;
				    estado.nombre = messageResources.getResource("estado.mayor.25.menor.50");
				    estatus.add(estado);

					estado = new GraficoEstatus();
				    estado.id = ESTATUS_50Y75;
				    estado.nombre = messageResources.getResource("estado.mayor.50.menor.75");
				    estatus.add(estado);

					estado = new GraficoEstatus();
				    estado.id = ESTATUS_75Y100;
				    estado.nombre = messageResources.getResource("estado.mayor.75.menor.100");
				    estatus.add(estado);
			    }

				estado = new GraficoEstatus();
			    estado.id = ESTATUS_CONCLUIDAS;
			    estado.nombre = messageResources.getResource("estado.concluidas");
			    estatus.add(estado);
			}
		    
		    return estatus;
		}

		public static String getNombre(byte estado) 
		{
			String nombre = "";

			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		    if (estado == ESTATUS_SIN_INICIAR) 
		    	nombre = messageResources.getResource("estado.sin.iniciar");
		    if (estado == ESTATUS_SIN_INICIAR_DESFASADA) 
		    	nombre = messageResources.getResource("estado.sin.iniciar.desfasada");
		    if (estado == ESTATUS_EN_EJECUCION_SIN_RETRASOS) 
		    	nombre = messageResources.getResource("estado.en.ejecucion.sin.retrasos");
		    if (estado == ESTATUS_EN_EJECUCION_CON_RETRASOS_SUPERABLES) 
		    	nombre = messageResources.getResource("estado.en.ejecucion.con.retrasos.superables");
		    if (estado == ESTATUS_EN_EJECUCION_CON_RETRASOS_SIGNIFICATIVOS) 
		    	nombre = messageResources.getResource("estado.en.ejecucion.con.retrasos.significativos");
		    if (estado == ESTATUS_0Y25) 
		    	nombre = messageResources.getResource("estado.mayor.0.menor.25");
		    if (estado == ESTATUS_25Y50) 
		    	nombre = messageResources.getResource("estado.mayor.25.menor.50");
		    if (estado == ESTATUS_50Y75) 
		    	nombre = messageResources.getResource("estado.mayor.50.menor.75");
		    if (estado == ESTATUS_75Y100) 
		    	nombre = messageResources.getResource("estado.mayor.75.menor.100");
		    if (estado == ESTATUS_CONCLUIDAS) 
		    	nombre = messageResources.getResource("estado.concluidas");

		    return nombre;
		}		
	}
}
