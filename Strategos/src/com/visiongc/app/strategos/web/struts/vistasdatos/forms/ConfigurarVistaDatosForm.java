package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.vistasdatos.model.util.DatoCelda;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoAtributo;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.vistasdatos.model.util.VistaDatosUtil;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ConfigurarVistaDatosForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;

	private String textoMiembrosVariable;
	private String textoMiembrosTiempo;
	private String textoMiembrosIndicador;
	private String textoMiembrosOrganizacion;
	private String textoMiembrosPlan;
	private String textoMiembrosAtributo;
	private List<ObjetoValorNombre> miembrosVariable;
	private List<ObjetoValorNombre> miembrosTiempo;
	private List<ObjetoValorNombre> miembrosIndicador;
	private List<ObjetoValorNombre> miembrosOrganizacion;
	private List<ObjetoValorNombre> miembrosPlan;
	private List<ObjetoValorNombre> miembrosAtributo;
	private List<ObjetoValorNombre> dimensiones;
	private List<ObjetoValorNombre> selectores;
	private List<ObjetoValorNombre> filas;
	private String filasId;
	private String nombreFilas;
	private List<ObjetoValorNombre> columnas;
	private String columnasId;
	private String nombreColumnas;
	private List<Frecuencia> frecuencias;
	private Byte frecuencia;
	private String nombreFrecuencia;
	private List<ObjetoValorNombre> selector1;
	private List<ObjetoValorNombre> selector2;
	private List<ObjetoValorNombre> selector3;
	private List<ObjetoValorNombre> selector4;
	private List<ObjetoValorNombre> selectorTiempoDesde;
	private List<ObjetoValorNombre> selectorTiempoHasta;
	private Long selector1Id;
	private Long selector2Id;
	private Long selector3Id;
	private Long selector4Id;
	private String nombreSelector1;
	private String nombreSelector2;
	private String nombreSelector3;
	private String nombreSelector4;
	private String valorSelector1;
	private String valorSelector2;
	private String valorSelector3;
	private String valorSelector4;
	private String valorSelectorTiempoDesde;
	private String valorSelectorTiempoHasta;
	private List<List<DatoCelda>> matrizDatos;
	private int paginaMiembros = 0;
	private String atributoOrdenMiembros = null;
	private String tipoOrdenMiembros = null;
	private String seleccionadosMiembros = null;
	private String valoresSeleccionadosMiembros = null;
	private String textoMiembros;
	private String nombreMiembro;
	private Long miembroId;
	private int paginaDimensiones = 0;
	private String atributoOrdenDimensiones = null;
	private String tipoOrdenDimensiones = null;
	private String seleccionadosDimensiones = null;
	private String valoresSeleccionadosDimensiones = null;
	private String textoDimensiones;
	private String nombreDimension;
	private Byte dimensionId;
	private int paginaSelectores = 0;
	private String atributoOrdenSelectores = null;
	private String tipoOrdenSelectores = null;
	private String seleccionadosSelectores = null;
	private String valoresSeleccionadosSelectores = null;
	private String textoSelectores;
	private String nombreSelector;
	private Byte selectorId;
	private Integer anchoTablaDatos;
	private String nombre;
	private String descripcion;
	private Boolean publico;
	private Long reporteId;
	private String usuarioCreado; 
	private Byte source;
	private String configuracion;
	private Byte corte;
	private List<TipoAtributo> atributos;
	private Boolean showVariable = false;
	private Boolean showTablaParametro;
	private Boolean showTotalFilas;
	private Boolean showTotalColumnas;
	private Boolean acumularPeriodos;
	private Integer anoInicial;
	private Integer anoFinal;
	private Integer periodoInicial;
	private Integer periodoFinal;
	private String fechaInicial;
	private String fechaFinal;
  
	final String SEPARADOR_ATRIBUTOS = "|";
	final String SEPARADOR_ELEMENTOS = ",";
  
	public final String SEPARADOR_VARIABLES = "!@!";

	public String getTextoMiembrosAtributo() 
	{
		return this.textoMiembrosAtributo;
	}

	public void setTextoMiembrosAtributo(String textoMiembrosAtributo) 
	{
		this.textoMiembrosAtributo = textoMiembrosAtributo;
		this.miembrosAtributo = VistaDatosUtil.convertirCadenaLista(this.textoMiembrosAtributo, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTextoMiembrosIndicador() 
	{
		return this.textoMiembrosIndicador;
	}

	public void setTextoMiembrosIndicador(String textoMiembrosIndicador) 
	{
		this.textoMiembrosIndicador = textoMiembrosIndicador;
		this.miembrosIndicador = VistaDatosUtil.convertirCadenaLista(this.textoMiembrosIndicador, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTextoMiembrosOrganizacion() 
	{
		return this.textoMiembrosOrganizacion;
	}

	public void setTextoMiembrosOrganizacion(String textoMiembrosOrganizacion) 
	{
		this.textoMiembrosOrganizacion = textoMiembrosOrganizacion;
		this.miembrosOrganizacion = VistaDatosUtil.convertirCadenaLista(this.textoMiembrosOrganizacion, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTextoMiembrosPlan() 
	{
		return this.textoMiembrosPlan;
	}

	public void setTextoMiembrosPlan(String textoMiembrosPlan) 
	{
		this.textoMiembrosPlan = textoMiembrosPlan;
		this.miembrosPlan = VistaDatosUtil.convertirCadenaLista(this.textoMiembrosPlan, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTextoMiembrosTiempo() 
	{
		return this.textoMiembrosTiempo;
	}

	public void setTextoMiembrosTiempo(String textoMiembrosTiempo) 
	{
		this.textoMiembrosTiempo = textoMiembrosTiempo;
		this.miembrosTiempo = VistaDatosUtil.convertirCadenaLista(this.textoMiembrosTiempo, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTextoMiembrosVariable() 
	{
		return this.textoMiembrosVariable;
	}

	public void setTextoMiembrosVariable(String textoMiembrosVariable) 
	{
		this.textoMiembrosVariable = textoMiembrosVariable;
		this.miembrosVariable = VistaDatosUtil.convertirCadenaLista(textoMiembrosVariable, getSeparadorElementos(), getSeparadorAtributos());
	}

	public List<ObjetoValorNombre> getDimensiones() 
	{
		return this.dimensiones;
	}

	public void setDimensiones(List<ObjetoValorNombre> dimensiones) 
	{
		this.dimensiones = dimensiones;
		this.textoDimensiones = VistaDatosUtil.convertirListaCadena(dimensiones, getSeparadorElementos(), getSeparadorAtributos());
	}

	public List<ObjetoValorNombre> getMiembrosIndicador() 
	{
		return this.miembrosIndicador;
	}

	public void setMiembrosIndicador(List<ObjetoValorNombre> miembrosIndicador) 
	{
		this.miembrosIndicador = miembrosIndicador;
	}

	public List<ObjetoValorNombre> getMiembrosOrganizacion() 
	{
		return this.miembrosOrganizacion;
	}

	public void setMiembrosOrganizacion(List<ObjetoValorNombre> miembrosOrganizacion) 
	{
		this.miembrosOrganizacion = miembrosOrganizacion;
	}

	public List<ObjetoValorNombre> getMiembrosPlan() 
	{
		return this.miembrosPlan;
	}

	public void setMiembrosPlan(List<ObjetoValorNombre> miembrosPlan) 
	{
		this.miembrosPlan = miembrosPlan;
	}

	public List<ObjetoValorNombre> getMiembrosTiempo() 
	{
		return this.miembrosTiempo;
	}

	public void setMiembrosTiempo(List<ObjetoValorNombre> miembrosTiempo) 
	{
		this.miembrosTiempo = miembrosTiempo;
	}

	public List<ObjetoValorNombre> getMiembrosVariable() 
	{
		return this.miembrosVariable;
	}

	public void setMiembrosVariable(List<ObjetoValorNombre> miembrosVariable) 
	{
		this.miembrosVariable = miembrosVariable;
	}

	public List<ObjetoValorNombre> getSelectores() 
	{
		return this.selectores;
	}

	public void setSelectores(List<ObjetoValorNombre> selectores) 
	{
		this.selectores = selectores;
	}

	public String getAtributoOrdenDimensiones() 
	{
		return this.atributoOrdenDimensiones;
	}

	public void setAtributoOrdenDimensiones(String atributoOrdenDimensiones) 
	{
		this.atributoOrdenDimensiones = atributoOrdenDimensiones;
	}

	public String getAtributoOrdenMiembros() 
	{
		return this.atributoOrdenMiembros;
	}

	public void setAtributoOrdenMiembros(String atributoOrdenMiembros) 
	{
		this.atributoOrdenMiembros = atributoOrdenMiembros;
	}

	public String getAtributoOrdenSelectores() 
	{
		return this.atributoOrdenSelectores;
	}

	public void setAtributoOrdenSelectores(String atributoOrdenSelectores) 
	{
		this.atributoOrdenSelectores = atributoOrdenSelectores;
	}

	public Byte getDimensionId() 
	{
		return this.dimensionId;
	}

	public void setDimensionId(Byte dimensionId) 
	{
		this.dimensionId = dimensionId;
	}

	public Long getMiembroId() 
	{
		return this.miembroId;
	}

	public void setMiembroId(Long miembroId) 
	{
		this.miembroId = miembroId;
	}

	public String getNombreDimension() 
	{
		return this.nombreDimension;
	}

	public void setNombreDimension(String nombreDimension) 
	{
		this.nombreDimension = nombreDimension;
	}

	public String getNombreMiembro() 
	{
		return this.nombreMiembro;
	}

	public void setNombreMiembro(String nombreMiembro) 
	{
		this.nombreMiembro = nombreMiembro;
	}

	public String getNombreSelector() 
	{
		return this.nombreSelector;
	}

	public void setNombreSelector(String nombreSelector) 
	{
		this.nombreSelector = nombreSelector;
	}

	public int getPaginaDimensiones() 
	{
		return this.paginaDimensiones;
	}

	public void setPaginaDimensiones(int paginaDimensiones) 
	{
		this.paginaDimensiones = paginaDimensiones;
	}

	public int getPaginaMiembros() 
	{
		return this.paginaMiembros;
	}

	public void setPaginaMiembros(int paginaMiembros) 
	{
		this.paginaMiembros = paginaMiembros;
	}

	public List<ObjetoValorNombre> getMiembrosAtributo() 
	{
		return this.miembrosAtributo;
	}

	public void setMiembrosAtributo(List<ObjetoValorNombre> miembrosAtributo) 
	{
		this.miembrosAtributo = miembrosAtributo;
	}

	public int getPaginaSelectores() 
	{
		return this.paginaSelectores;
	}

	public void setPaginaSelectores(int paginaSelectores) 
	{
		this.paginaSelectores = paginaSelectores;
	}

	public String getSeleccionadosDimensiones() 
	{
		return this.seleccionadosDimensiones;
	}

	public void setSeleccionadosDimensiones(String seleccionadosDimensiones) 
	{
		this.seleccionadosDimensiones = seleccionadosDimensiones;
	}

	public String getSeleccionadosMiembros() 
	{
		return this.seleccionadosMiembros;
	}

	public void setSeleccionadosMiembros(String seleccionadosMiembros) 
	{
		this.seleccionadosMiembros = seleccionadosMiembros;
	}

	public String getSeleccionadosSelectores() 
	{
		return this.seleccionadosSelectores;
	}

	public void setSeleccionadosSelectores(String seleccionadosSelectores) 
	{
		this.seleccionadosSelectores = seleccionadosSelectores;
	}

	public Byte getSelectorId() 
	{
		return this.selectorId;
	}

	public void setSelectorId(Byte selectorId) 
	{
		this.selectorId = selectorId;
	}

	public String getTextoDimensiones() 
	{
		return this.textoDimensiones;
	}

	public void setTextoDimensiones(String textoDimensiones) 
	{
		this.textoDimensiones = textoDimensiones;
		this.dimensiones = VistaDatosUtil.convertirCadenaLista(textoDimensiones, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTextoMiembros() 
	{
		return this.textoMiembros;
	}

	public void setTextoMiembros(String textoMiembros) 
	{
		this.textoMiembros = textoMiembros;
	}

	public String getTextoSelectores() 
	{
		return this.textoSelectores;
	}

	public void setTextoSelectores(String textoSelectores) 
	{
		this.textoSelectores = textoSelectores;
		this.selectores = VistaDatosUtil.convertirCadenaLista(textoSelectores, getSeparadorElementos(), getSeparadorAtributos());
	}

	public String getTipoOrdenDimensiones() 
	{
		return this.tipoOrdenDimensiones;
	}

	public void setTipoOrdenDimensiones(String tipoOrdenDimensiones) 
	{
		this.tipoOrdenDimensiones = tipoOrdenDimensiones;
	}

	public String getTipoOrdenMiembros() 
	{
		return this.tipoOrdenMiembros;
	}

	public void setTipoOrdenMiembros(String tipoOrdenMiembros) 
	{
		this.tipoOrdenMiembros = tipoOrdenMiembros;
	}

	public String getTipoOrdenSelectores() 
	{
		return this.tipoOrdenSelectores;
	}

	public void setTipoOrdenSelectores(String tipoOrdenSelectores) 
	{
		this.tipoOrdenSelectores = tipoOrdenSelectores;
	}

	public String getValoresSeleccionadosDimensiones() 
	{
		return this.valoresSeleccionadosDimensiones;
	}

	public void setValoresSeleccionadosDimensiones(String valoresSeleccionadosDimensiones)
	{
		this.valoresSeleccionadosDimensiones = valoresSeleccionadosDimensiones;
	}

	public String getValoresSeleccionadosMiembros() 
	{
		return this.valoresSeleccionadosMiembros;
	}

	public void setValoresSeleccionadosMiembros(String valoresSeleccionadosMiembros)
	{
		this.valoresSeleccionadosMiembros = valoresSeleccionadosMiembros;
	}

	public String getValoresSeleccionadosSelectores() 
	{
		return this.valoresSeleccionadosSelectores;
	}

	public void setValoresSeleccionadosSelectores(String valoresSeleccionadosSelectores)
	{
		this.valoresSeleccionadosSelectores = valoresSeleccionadosSelectores;
	}

	public String getColumnasId() 
	{
		return this.columnasId;
	}

	public void setColumnasId(String columnasId) 
	{
		this.columnasId = columnasId;
	}

	public String getFilasId() 
	{
		return this.filasId;
	}

	public void setFilasId(String filasId) 
	{
		this.filasId = filasId;
	}

	public String getNombreColumnas() 
	{
		return this.nombreColumnas;
	}

	public void setNombreColumnas(String nombreColumnas) 
	{
		this.nombreColumnas = nombreColumnas;
	}

	public String getNombreFilas() 
	{
		return this.nombreFilas;
	}

	public void setNombreFilas(String nombreFilas) 
	{
		this.nombreFilas = nombreFilas;
	}

	public String getSeparadorElementos() 
	{
		return "%-%";
	}

	public String getSeparadorAtributos() 
	{
		return "|";
	}

	public Byte getTipoDimensionVariable() 
	{
		return TipoDimension.getTipoDimensionVariable();
	}

	public Byte getTipoDimensionAtributo() 
	{
		return TipoDimension.getTipoDimensionAtributo();
	}

	public Byte getTipoDimensionTiempo() 
	{
		return TipoDimension.getTipoDimensionTiempo();
	}

	public Byte getTipoDimensionIndicador() 
	{
		return TipoDimension.getTipoDimensionIndicador();
	}

	public Byte getTipoDimensionPlan() 
	{
		return TipoDimension.getTipoDimensionPlan();
	}

	public Byte getTipoDimensionOrganizacion() 
	{
		return TipoDimension.getTipoDimensionOrganizacion();
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

	public List<ObjetoValorNombre> getColumnas() 
	{
		return this.columnas;
	}

	public void setColumnas(List<ObjetoValorNombre> columnas) 
	{
		this.columnas = columnas;
	}

	public List<ObjetoValorNombre> getFilas() 
	{
		return this.filas;
	}

	public void setFilas(List<ObjetoValorNombre> filas) 
	{
		this.filas = filas;
	}

	public List<ObjetoValorNombre> getSelector1() 
	{
		return this.selector1;
	}

	public void setSelector1(List<ObjetoValorNombre> selector1) 
	{
		this.selector1 = selector1;
	}

	public Long getSelector1Id() 
	{
		return this.selector1Id;
	}

	public void setSelector1Id(Long selector1Id) 
	{
		this.selector1Id = selector1Id;
	}

	public List<ObjetoValorNombre> getSelector2() 
	{
		return this.selector2;
	}

	public void setSelector2(List<ObjetoValorNombre> selector2) 
	{
		this.selector2 = selector2;
	}

	public Long getSelector2Id() 
	{
		return this.selector2Id;
	}

	public void setSelector2Id(Long selector2Id) 
	{
		this.selector2Id = selector2Id;
	}

	public List<ObjetoValorNombre> getSelector3() 
	{
		return this.selector3;
	}

	public void setSelector3(List<ObjetoValorNombre> selector3) 
	{
		this.selector3 = selector3;
	}

	public Long getSelector3Id() 
	{
		return this.selector3Id;
	}

	public void setSelector3Id(Long selector3Id) 
	{
		this.selector3Id = selector3Id;
	}

	public List<ObjetoValorNombre> getSelector4() 
	{
		return this.selector4;
	}

	public void setSelector4(List<ObjetoValorNombre> selector4) 
	{
		this.selector4 = selector4;
	}

	public Long getSelector4Id() 
	{
		return this.selector4Id;
	}

	public void setSelector4Id(Long selector4Id) 
	{
		this.selector4Id = selector4Id;
	}

	public List<ObjetoValorNombre> getSelectorTiempoDesde() 
	{
		return this.selectorTiempoDesde;
	}

	public void setSelectorTiempoDesde(List<ObjetoValorNombre> selectorTiempoDesde) 
	{
		this.selectorTiempoDesde = selectorTiempoDesde;
	}

	public List<ObjetoValorNombre> getSelectorTiempoHasta() 
	{
		return this.selectorTiempoHasta;
	}

	public void setSelectorTiempoHasta(List<ObjetoValorNombre> selectorTiempoHasta) 
	{
		this.selectorTiempoHasta = selectorTiempoHasta;
	}
	
	public String getNombreSelector1() 
	{
		return this.nombreSelector1;
	}

	public void setNombreSelector1(String nombreSelector1) 
	{
		this.nombreSelector1 = nombreSelector1;
	}

	public String getNombreSelector2() 
	{
		return this.nombreSelector2;
	}

	public void setNombreSelector2(String nombreSelector2) 
	{
		this.nombreSelector2 = nombreSelector2;
	}

	public String getNombreSelector3() 
	{
		return this.nombreSelector3;
	}

	public void setNombreSelector3(String nombreSelector3) 
	{
		this.nombreSelector3 = nombreSelector3;
	}

	public String getNombreSelector4() 
	{
		return this.nombreSelector4;
	}

	public void setNombreSelector4(String nombreSelector4) 
	{
		this.nombreSelector4 = nombreSelector4;
	}

	public String getValorSelector1()
	{
		return this.valorSelector1;
	}

	public void setValorSelector1(String valorSelector1) 
	{
		this.valorSelector1 = valorSelector1;
	}

	public String getValorSelector2() 
	{
		return this.valorSelector2;
	}

	public void setValorSelector2(String valorSelector2) 
	{
		this.valorSelector2 = valorSelector2;
	}

	public String getValorSelector3() 
	{
		return this.valorSelector3;
	}	

	public void setValorSelector3(String valorSelector3) 
	{
		this.valorSelector3 = valorSelector3;
	}

	public String getValorSelector4() 
	{
		return this.valorSelector4;
	}

	public void setValorSelector4(String valorSelector4) 
	{
		this.valorSelector4 = valorSelector4;
	}

	public String getValorSelectorTiempoDesde() 
	{
		return this.valorSelectorTiempoDesde;
	}

	public void setValorSelectorTiempoDesde(String valorSelectorTiempoDesde) 
	{
		this.valorSelectorTiempoDesde = valorSelectorTiempoDesde;
	}

	public String getValorSelectorTiempoHasta() 
	{
		return this.valorSelectorTiempoHasta;
	}

	public void setValorSelectorTiempoHasta(String valorSelectorTiempoHasta) 
	{
		this.valorSelectorTiempoHasta = valorSelectorTiempoHasta;
	}
	
  	public List<List<DatoCelda>> getMatrizDatos()
  	{
  		return this.matrizDatos;
  	}

  	public void setMatrizDatos(List<List<DatoCelda>> matrizDatos) 
  	{
  		this.matrizDatos = matrizDatos;
  	}

  	public String getNombreFrecuencia()
  	{
  		return this.nombreFrecuencia;
  	}

  	public void setNombreFrecuencia(String nombreFrecuencia) 
  	{
  		this.nombreFrecuencia = nombreFrecuencia;
  	}

  	public Integer getAnchoTablaDatos()
  	{
  		return this.anchoTablaDatos;
  	}

  	public void setAnchoTablaDatos(Integer anchoTablaDatos) 
  	{
  		this.anchoTablaDatos = anchoTablaDatos;
  	}
  
  	public String getNombre()
  	{
  		return this.nombre;
  	}

  	public void setNombre(String nombre) 
  	{
  		this.nombre = nombre;
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
  	
  	public Long getReporteId()
  	{
  		return this.reporteId;
  	}

  	public void setReporteId(Long reporteId) 
  	{
  		this.reporteId = reporteId;
  	}

  	public String getUsuarioCreado()
  	{
  		return this.usuarioCreado;
  	}

  	public void setUsuarioCreado(String usuarioCreado) 
  	{
  		this.usuarioCreado = usuarioCreado;
  	}

 	public Byte getSource()
  	{
  		return this.source;
  	}

  	public void setSource(Byte source) 
  	{
  		this.source = SourceType.getTypeSource(source);
  	}

 	public Byte getCorte()
  	{
  		return this.corte;
  	}

  	public void setCorte(Byte corte) 
  	{
  		this.corte = Reporte.ReporteCorte.getCorte(corte);
  	}
  	
 	public String getConfiguracion()
  	{
  		return this.configuracion;
  	}

  	public void setConfiguracion(String configuracion) 
  	{
  		this.configuracion = configuracion;
  	}
  	
    public String getSeparadorIndicadores() 
    {
        return EditarIndicadorForm.SEPARADOR_INDICADORES;
    }
    
  	public String getSeparadorSeries() 
  	{
  		return EditarIndicadorForm.SEPARADOR_SERIES;
  	}
  	
  	public String getSeparadorVariables()
  	{
  		return SEPARADOR_VARIABLES;
  	}
  	
	public List<TipoAtributo> getAtributos() 
	{
		return this.atributos;
	}

	public void setAtributos(List<TipoAtributo> atributos) 
	{
		this.atributos = atributos;
	}

	public Boolean getShowVariable() 
	{
		return this.showVariable;
	}

	public void setShowVariable(Boolean showVariable) 
	{
		this.showVariable = showVariable;
	}

	public Boolean getShowTablaParametro() 
	{
		return this.showTablaParametro;
	}

	public void setShowTablaParametro(Boolean showTablaParametro) 
	{
		this.showTablaParametro = showTablaParametro;
	}
	
	public Boolean getShowTotalFilas() 
	{
		return this.showTotalFilas;
	}

	public void setShowTotalFilas(Boolean showTotalFilas) 
	{
		this.showTotalFilas = showTotalFilas;
	}

	public Boolean getShowTotalColumnas() 
	{
		return this.showTotalColumnas;
	}

	public void setShowTotalColumnas(Boolean showTotalColumnas) 
	{
		this.showTotalColumnas = showTotalColumnas;
	}

	public Boolean getAcumularPeriodos() 
	{
		return this.acumularPeriodos;
	}

	public void setAcumularPeriodos(Boolean acumularPeriodos) 
	{
		this.acumularPeriodos = acumularPeriodos;
	}
	
	public Integer getAnoInicial() 
	{
		return this.anoInicial;
	}

	public void setAnoInicial(Integer anoInicial) 
	{
		this.anoInicial = anoInicial;
	}

	public Integer getAnoFinal() 
	{
		return this.anoFinal;
	}

	public void setAnoFinal(Integer anoFinal) 
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
	
	public String getFechaInicial() 
	{
		return this.fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) 
	{
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinal() 
	{
		return this.fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) 
	{
		this.fechaFinal = fechaFinal;
	}
	
  	public void clear()
  	{
  		this.nombre = "";
  		this.descripcion = "";
  		this.publico = true;
  		this.reporteId = null;
 
  		this.textoMiembrosVariable = null;
  		this.textoMiembrosTiempo = null;
  		this.textoMiembrosIndicador = null;
  		this.textoMiembrosOrganizacion = null;
  		this.textoMiembrosPlan = null;
  		this.textoMiembrosAtributo = null;
  		this.miembrosVariable = null;
  		this.miembrosTiempo = null;
  		this.miembrosIndicador = null;
  		this.miembrosOrganizacion = null;
  		this.miembrosPlan = null;
  		this.miembrosAtributo = null;
  		this.dimensiones = null;
  		this.selectores = null;
  		this.filas = null;
  		this.filasId = null;
  		this.nombreFilas = null;
  		this.columnas = null;
  		this.columnasId = null;
  		this.nombreColumnas = null;
  		this.frecuencias = null;
  		this.frecuencia = null;
  		this.nombreFrecuencia = null;
  		this.selector1 = null;
  		this.selector2 = null;
  		this.selector3 = null;
  		this.selector4 = null;
  		this.selectorTiempoDesde = null;
  		this.selectorTiempoHasta = null;
  		this.selector1Id = null;
  		this.selector2Id = null;
  		this.selector3Id = null;
  		this.selector4Id = null;
  		this.nombreSelector1 = null;
  		this.nombreSelector2 = null;
  		this.nombreSelector3 = null;
  		this.nombreSelector4 = null;
  		this.valorSelector1 = null;
  		this.valorSelector2 = null;
  		this.valorSelector3 = null;
  		this.valorSelector4 = null;
  		this.matrizDatos = null;
  	  	this.atributoOrdenMiembros = null;
  	  	this.tipoOrdenMiembros = null;
  	  	this.seleccionadosMiembros = null;
  	  	this.valoresSeleccionadosMiembros = null;
  	  	this.textoMiembros = null;
  	  	this.nombreMiembro = null;
  	  	this.miembroId = null;
  	  	this.atributoOrdenDimensiones = null;
  	  	this.tipoOrdenDimensiones = null;
  	  	this.seleccionadosDimensiones = null;
  	  	this.valoresSeleccionadosDimensiones = null;
  	  	this.textoDimensiones = null;
  	  	this.nombreDimension = null;
  	  	this.dimensionId = null;
  	  	this.atributoOrdenSelectores = null;
  	  	this.tipoOrdenSelectores = null;
  	  	this.seleccionadosSelectores = null;
  	  	this.valoresSeleccionadosSelectores = null;
  	  	this.textoSelectores = null;
  	  	this.nombreSelector = null;
  	  	this.selectorId = null;
  	  	this.anchoTablaDatos = null;
  	  	this.paginaMiembros = 0;
  	  	this.paginaDimensiones = 0;
  	  	this.paginaSelectores = 0;
  	  	this.usuarioCreado = null;
  	  	this.source = null;
  	  	this.configuracion = "";
  	  	this.corte = null;
  	  	this.atributos = null;
  	  	this.showVariable = false;
  	  	this.showTablaParametro = null;
  	  	this.showTotalColumnas = null;
  	  	this.showTotalFilas = null;
  	  	this.acumularPeriodos = null;
  	  	this.anoInicial = null;
  	  	this.anoFinal = null;
  	  	this.periodoInicial = null;
  	  	this.periodoFinal = null;
  	  	this.fechaInicial = null;
  	  	this.fechaFinal = null;
  	}
  	
	public static class SourceType
	{
		private static final byte VISTA_SOURCE_GESTIONAR = 1;
		private static final byte VISTA_SOURCE_EDITAR = 2;
		private static final byte VISTA_SOURCE_REPORTE = 3;

		private static Byte getTypeSource(Byte source)
		{
			if (source == VISTA_SOURCE_GESTIONAR)
				return new Byte(VISTA_SOURCE_GESTIONAR);
			else if (source == VISTA_SOURCE_EDITAR)
				return new Byte(VISTA_SOURCE_EDITAR);
			else if (source == VISTA_SOURCE_REPORTE)
				return new Byte(VISTA_SOURCE_REPORTE);
			else
				return null;
		}
		
		public static byte getSourceGestionar() 
		{
			return VISTA_SOURCE_GESTIONAR;
		}

		public static byte getSourceEditar() 
		{
			return VISTA_SOURCE_EDITAR;
		}
		
		public static byte getSourceReporte() 
		{
			return VISTA_SOURCE_REPORTE;
		}
	}
	
  	public String getXml() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
  	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "Reporte", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		Element elemento = document.createElement("showTablaParametro"); //creamos un nuevo elemento
		Text text = document.createTextNode(this.showTablaParametro != null ? (this.showTablaParametro ? "1" : "0") : "1"); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		Source source = new DOMSource(document);
		
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);

  		return writer.toString().trim();
  	}
  	
  	public void setXml(String xml) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, SAXException, IOException
  	{
  		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
  		DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(xml))));
		NodeList lista = doc.getElementsByTagName("properties");
		 
		for (int i = 0; i < lista.getLength() ; i ++) 
		{
			Node node = lista.item(i);
			Element elemento = (Element) node;
			NodeList nodeLista = null;
			Node valor = null;
			
			if (elemento.getElementsByTagName("showTablaParametro").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("showTablaParametro").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.showTablaParametro = valor.getNodeValue().equals("1") ? true : false;
				else
					this.showTablaParametro = true;
			}
		}
  	}
}