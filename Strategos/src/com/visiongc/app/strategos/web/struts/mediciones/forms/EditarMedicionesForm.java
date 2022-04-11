package com.visiongc.app.strategos.web.struts.mediciones.forms;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.util.Columna;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

public class EditarMedicionesForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Long claseId;
	private Long planId;
	private Long perspectivaId;
	private String perspectivaNombre;
	private String nombreObjetoPerspectiva;
	private Boolean soloSeleccionados;
	private List<Frecuencia> frecuencias;
	private List<Indicador> indicadores;
	private Boolean[] bloqueado;
	private Byte frecuencia;
	private String organizacion;
	private String clase;
	private List<ObjetoClaveValor> anos;
	private String anoDesde;
	private String anoHasta;
	private String anoInicial;
	private String anoFinal;
	private Integer numeroMaximoPeriodos;
	private List<ObjetoClaveValor> periodos;
	private Integer periodoDesde;
	private Integer periodoHasta;
	private String fechaDesde;
	private String fechaHasta;
	private PaginaLista paginaSeriesTiempo;
	private Boolean visualizarIndicadoresCompuestos;
	private String anchoMatriz;
	private Boolean desdeIndicador;
	private Boolean desdeIndicadorOrg;
	
	private Long iniciativaId;
	private Long organizacionId;
	private String iniciativa;
	private Boolean desdePlanificacion;
	private List<ObjetoClaveValor> anosD;
	private List<ObjetoClaveValor> periodosD;
  	private List<ObjetoClaveValor> anosH;
  	private List<ObjetoClaveValor> periodosH;
  	private String indicadoresSeries;
  	private Byte sourceScreen = 0;
  	private Boolean desdeClase;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;
	private String respuesta;
	private Byte status = 0;
	private String[] serieId;
	private Byte tipoCargaDesdePlanificacion = null;
	private List<Columna> columnas;
	
	private Boolean desdeReal;
	private Boolean esAdmin;
	
	public static final String SEPARADOR_SERIES = ":";
	public static final String SEPARADOR_MEDICIONES = ";";
	public static final String SEPARADOR_CAMPOS = "_";
	public static final String SEPARADOR_INDICADORES = "-";
	
	
	
	public Boolean getDesdeIndicadorOrg() {
		return desdeIndicadorOrg;
	}

	public void setDesdeIndicadorOrg(Boolean desdeIndicadorOrg) {
		this.desdeIndicadorOrg = desdeIndicadorOrg;
	}

	public String getIndicadoresSeries()
	{
		return this.indicadoresSeries;
	}

	public void setIndicadoresSeries(String indicadoresSeries) 
	{
		this.indicadoresSeries = indicadoresSeries;
	}

	public Boolean getDesdeClase()
	{
		return this.desdeClase;
	}

	public void setDesdeClase(Boolean desdeClase) 
	{
		this.desdeClase = desdeClase;
	}

	public Boolean getDesdePlanificacion()
	{
		return this.desdePlanificacion;
	}

	public void setDesdePlanificacion(Boolean desdePlanificacion) 
	{
		this.desdePlanificacion = desdePlanificacion;
	}
  
	public Long getIniciativaId()
	{
		return this.iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) 
	{
		this.iniciativaId = iniciativaId;
	}

	public Long getOrganizacionId()
	{
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
		this.organizacionId = organizacionId;
	}

	public String getIniciativa()
	{
		return this.iniciativa;
	}

	public void setIniciativa(String iniciativa) 
	{
		this.iniciativa = iniciativa;
	}
  
  	public List<Frecuencia> getFrecuencias() 
  	{
  		return this.frecuencias;
  	}

  	public void setFrecuencias(List<Frecuencia> frecuencias) 
  	{
  		this.frecuencias = frecuencias;
  	}

  	public Boolean getSoloSeleccionados() 
  	{
  		return this.soloSeleccionados;
  	}

  	public void setSoloSeleccionados(Boolean soloSeleccionados) 
  	{
  		this.soloSeleccionados = soloSeleccionados;
  	}

  	public List<Indicador> getIndicadores() 
  	{
  		return this.indicadores;
  	}

  	public void setIndicadores(List<Indicador> indicadores) 
  	{
  		this.indicadores = indicadores;
  	}

  	public Boolean[] getBloqueados() 
  	{
  		return this.bloqueado;
  	}

  	public void setBloqueados(Boolean[] bloqueado) 
  	{
  		this.bloqueado = bloqueado;
  	}
  	
  	public Byte getFrecuencia() 
  	{
  		return this.frecuencia;
  	}

  	public void setFrecuencia(Byte frecuencia) 
  	{
  		this.frecuencia = frecuencia;
  	}

  	public Long getClaseId() 
  	{
  		return this.claseId;
  	}

  	public void setClaseId(Long claseId) 
  	{
  		this.claseId = claseId;
  	}

  	public Long getPlanId() 
  	{
  		return this.planId;
  	}

  	public void setPlanId(Long planId) 
  	{
  		this.planId = planId;
  	}

  	public Long getPerspectivaId() 
  	{
  		return this.perspectivaId;
  	}

  	public void setPerspectivaId(Long perspectivaId) 
  	{
  		this.perspectivaId = perspectivaId;
  	}

  	public String getPerspectivaNombre() 
  	{
  		return this.perspectivaNombre;
  	}

  	public void setPerspectivaNombre(String perspectivaNombre) 
  	{
  		this.perspectivaNombre = perspectivaNombre;
  	}

  	public String getNombreObjetoPerspectiva() 
  	{
  		return this.nombreObjetoPerspectiva;
  	}

  	public void setNombreObjetoPerspectiva(String nombreObjetoPerspectiva) 
  	{
  		this.nombreObjetoPerspectiva = nombreObjetoPerspectiva;
  	}

  	public String getOrganizacion() 
  	{
  		return this.organizacion;
  	}

  	public void setOrganizacion(String organizacion) 
  	{
  		this.organizacion = organizacion;
  	}

  	public String getClase() 
  	{
  		return this.clase;
  	}

  	public void setClase(String clase) 
  	{
  		this.clase = clase;
  	}

  	public String getAnoDesde() 
  	{
  		return this.anoDesde;
  	}

  	public void setAnoDesde(String anoDesde) 
  	{
  		this.anoDesde = anoDesde;
  	}

  	public String getAnoHasta() 
  	{
  		return this.anoHasta;
  	}

  	public void setAnoHasta(String anoHasta) 
  	{
  		this.anoHasta = anoHasta;
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
  	
  	public List<ObjetoClaveValor> getAnos() 
  	{
  		return this.anos;
  	}

  	public void setAnos(List<ObjetoClaveValor> anos) 
  	{
  		this.anos = anos;
  	}

  	public Integer getNumeroMaximoPeriodos() 
  	{
  		return this.numeroMaximoPeriodos;
  	}

  	public void setNumeroMaximoPeriodos(Integer numeroMaximoPeriodos) 
  	{
  		this.numeroMaximoPeriodos = numeroMaximoPeriodos;
  	}

  	public List<ObjetoClaveValor> getPeriodos() 
  	{
  		return this.periodos;
  	}

  	public void setPeriodos(List<ObjetoClaveValor> periodos) 
  	{
  		this.periodos = periodos;
  	}

  	public Integer getPeriodoDesde() 
  	{
  		return this.periodoDesde;
  	}

  	public void setPeriodoDesde(Integer periodoDesde) 
  	{
  		this.periodoDesde = periodoDesde;
  	}

  	public Integer getPeriodoHasta() 
  	{
  		return this.periodoHasta;
  	}

  	public void setPeriodoHasta(Integer periodoHasta) 
  	{
  		this.periodoHasta = periodoHasta;
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

  	public PaginaLista getPaginaSeriesTiempo() 
  	{
  		return this.paginaSeriesTiempo;
  	}

  	public void setPaginaSeriesTiempo(PaginaLista paginaSeriesTiempo) 
  	{
  		this.paginaSeriesTiempo = paginaSeriesTiempo;
  	}

  	public Boolean getVisualizarIndicadoresCompuestos() 
  	{
  		return this.visualizarIndicadoresCompuestos;
  	}

  	public void setVisualizarIndicadoresCompuestos(Boolean visualizarIndicadoresCompuestos) 
  	{
  		this.visualizarIndicadoresCompuestos = visualizarIndicadoresCompuestos;
  	}

  	public String getAnchoMatriz() 
  	{
  		return this.anchoMatriz;
  	}

  	public void setAnchoMatriz(String anchoMatriz) 
  	{
  		this.anchoMatriz = anchoMatriz;
  	}

  	public String getSeparadorCampos() 
  	{
  		return "_";
  	}

  public String getSeparadorSeries() {
    return ":";
  }

  public String getSeparadorMediciones() {
    return ";";
  }

  public String getSeparadorIndicadores() {
    return "-";
  }

  public Byte getFrecuenciaDiaria() {
    return Frecuencia.getFrecuenciaDiaria();
  }

  public Byte getFrecuenciaQuincenal() {
    return Frecuencia.getFrecuenciaQuincenal();
  }

  public Byte getFrecuenciaMensual() {
    return Frecuencia.getFrecuenciaMensual();
  }

  public Byte getFrecuenciaBimestral() {
    return Frecuencia.getFrecuenciaBimensual();
  }

  public Byte getFrecuenciaTrimestral() {
    return Frecuencia.getFrecuenciaTrimestral();
  }

  public Byte getFrecuenciaCuatrimestral() {
    return Frecuencia.getFrecuenciaCuatrimestral();
  }

  public Byte getFrecuenciaSemestral() {
    return Frecuencia.getFrecuenciaSemestral();
  }

  public Byte getFrecuenciaAnual() {
    return Frecuencia.getFrecuenciaAnual();
  }

  public Byte getTodosInidicadoresClase() {
    return new Byte((byte) 0);
  }

  public Byte getSeleccionarInidicadoresClase() {
    return new Byte((byte) 1);
  }

  public Byte getNaturalezaSimple()
  {
    return Naturaleza.getNaturalezaSimple();
  }

  public Byte getNaturalezaFormula()
  {
    return Naturaleza.getNaturalezaFormula();
  }

  public Byte getNaturalezaCualitativoOrdinal()
  {
    return Naturaleza.getNaturalezaCualitativoOrdinal();
  }

  public Byte getNaturalezaCualitativoNominal()
  {
    return Naturaleza.getNaturalezaCualitativoNominal();
  }

  public Byte getNaturalezaSumatoria()
  {
    return Naturaleza.getNaturalezaSumatoria();
  }

  public Byte getNaturalezaPromedio() {
    return Naturaleza.getNaturalezaPromedio();
  }

  public Byte getNaturalezaIndice()
  {
    return Naturaleza.getNaturalezaIndice();
  }
  
  public List<ObjetoClaveValor> getAnosD() 
  {
	  return this.anosD;
  }

  public void setAnosD(List<ObjetoClaveValor> anosD) 
  {
      this.anosD = anosD;
  }
  
  public List<ObjetoClaveValor> getAnosH() 
  {
  	    return this.anosH;
  }

  public void setAnosH(List<ObjetoClaveValor> anosH) 
  {
      this.anosH = anosH;
  }

  public List<ObjetoClaveValor> getPeriodosD() 
  {
	  return this.periodosD;
  }

  public void setPeriodosD(List<ObjetoClaveValor> periodosD) 
  {
      this.periodosD = periodosD;
  }

  public List<ObjetoClaveValor> getPeriodosH() 
  {
	  return this.periodosH;
  }

	public void setPeriodosH(List<ObjetoClaveValor> periodosH) 
  	{
  		this.periodosH = periodosH;
	}

  	public Byte getSourceScreen() 
  	{
  		return this.sourceScreen;
  	}

  	public void setSourceScreen(Byte sourceScreen) 
  	{
  		this.sourceScreen = sourceScreen;
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

	public String getRespuesta()
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}
	
	public Byte getStatus()
	{
		return this.status;
	}

	public void setStatus(Byte status) 
	{
		this.status = EditarStatus.getEditarStatus(status);
	}
	
	public String[] getSerieId()
	{
		return this.serieId;
	}

	public void setSerieId(String[] serieId) 
	{
		this.serieId = serieId;
	}

	public Byte getTipoCargaDesdePlanificacion()
	{
		return this.tipoCargaDesdePlanificacion;
	}

	public void setTipoCargaDesdePlanificacion(Byte tipoCargaDesdePlanificacion) 
	{
		this.tipoCargaDesdePlanificacion = TipoCargaActividad.getTipoCargaActividad(tipoCargaDesdePlanificacion);
	}

	public List<Columna> getColumnas()
	{
		return this.columnas;
	}

	public void setColumnas(List<Columna> columnas) 
	{
		this.columnas = columnas;
	}
	
  	public Boolean getDesdeIndicador() 
  	{
  		return this.desdeIndicador;
  	}

  	public void setDesdeIndicador(Boolean desdeIndicador) 
  	{
  		this.desdeIndicador = desdeIndicador;
  	}
  
	public Boolean getDesdeReal() {
		return desdeReal;
	}

	public void setDesdeReal(Boolean desdeReal) {
		this.desdeReal = desdeReal;
	}

	public Boolean getEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(Boolean esAdmin) {
		this.esAdmin = esAdmin;
	}



	public static class TipoCargaActividad
	{
		private static final byte CARGA_REAL = 0;
		private static final byte CARGA_PROGRAMADO = 1;
		
		private static Byte getTipoCargaActividad(Byte status)
		{
			if (status == CARGA_REAL)
				return new Byte(CARGA_REAL);
			else if (status == CARGA_PROGRAMADO)
				return new Byte(CARGA_PROGRAMADO);
			else
				return null;
		}
		
		public static Byte getTipoCargaActividadReal() 
		{
			return new Byte(CARGA_REAL);
		}

		public static Byte getTipoCargaActividadProgramado() 
		{
			return new Byte(CARGA_PROGRAMADO);
		}
	}
	
	public class TipoSource
	{
		public static final byte SOURCE_CLASE = 0;
		public static final byte SOURCE_PLAN = 1;
		public static final byte SOURCE_INICIATIVA = 2;
		public static final byte SOURCE_ACTIVIDAD = 3;
		public static final byte SOURCE_INDICADOR = 4;
	}  
  
  	public void setConfiguracion(String xml) throws ParserConfigurationException, SAXException, IOException
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
			
			if (elemento.getElementsByTagName("frecuencia").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("frecuencia").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.frecuencia = Byte.parseByte(valor.getNodeValue());
			}

			if (elemento.getElementsByTagName("anoDesde").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoDesde").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.anoDesde = valor.getNodeValue();
			}
			
			if (elemento.getElementsByTagName("anoHasta").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoHasta").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.anoHasta = valor.getNodeValue();
			}
			
			if (elemento.getElementsByTagName("periodoDesde").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoDesde").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.periodoDesde = Integer.parseInt(valor.getNodeValue());
			}

			if (elemento.getElementsByTagName("periodoHasta").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoHasta").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.periodoHasta = Integer.parseInt(valor.getNodeValue());
			}

			if (elemento.getElementsByTagName("fechaDesde").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("fechaDesde").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.fechaDesde = valor.getNodeValue();
			}

			if (elemento.getElementsByTagName("fechaHasta").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("fechaHasta").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) this.fechaHasta = valor.getNodeValue();
			}
		}
		
		lista = doc.getElementsByTagName("serie");
		Long serieId = null;
		if (lista.getLength() > 0)
		{
			for (int i = 0; i < lista.getLength() ; i ++) 
			{
				Node node = lista.item(i);
				Element elemento = (Element) node;
				NodeList nodeLista = null;
				Node valor = null;
				
				if (elemento.getElementsByTagName("serieId").getLength() > 0)
				{
					nodeLista = elemento.getElementsByTagName("serieId").item(0).getChildNodes();
					valor = (Node) nodeLista.item(0);
					if (valor != null)
					{
						serieId = Long.parseLong(valor.getNodeValue());
						for (Iterator<SerieTiempo> j = this.paginaSeriesTiempo.getLista().iterator(); j.hasNext(); )
						{
							SerieTiempo serie = (SerieTiempo)j.next();
							if (serie.getSerieId().longValue() == serieId.longValue())
							{
								serie.setPreseleccionada(true);
								break;
							}
						}
					}
				}
			}
		}
  	}
  	
  	public String getXml(String[] serieId) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
  	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "EditarMediciones", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		Element elemento = document.createElement("frecuencia"); //creamos un nuevo elemento
		Text text = document.createTextNode(this.frecuencia.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("anoDesde");
		text = document.createTextNode(this.anoDesde.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("anoHasta");
		text = document.createTextNode(this.anoHasta.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("periodoDesde");
		text = document.createTextNode(this.periodoDesde.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("periodoHasta");
		text = document.createTextNode(this.periodoHasta.toString());
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("fechaDesde");
		text = document.createTextNode(this.fechaDesde);
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("fechaHasta");
		text = document.createTextNode(this.fechaHasta);
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		Element indicadores = document.createElement("series");  // creamos el elemento raiz
		raiz.appendChild(indicadores);

		for (int iSerie = 0; iSerie < serieId.length; iSerie++)
		{
			Element indicadorElement = document.createElement("serie");  // creamos el elemento raiz
			indicadores.appendChild(indicadorElement);
			
			elemento = document.createElement("serieId");
			text = document.createTextNode(serieId[iSerie]);
			elemento.appendChild(text);
			indicadorElement.appendChild(elemento);
		}
        
		Source source = new DOMSource(document);
		
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);

  		return writer.toString().trim();
  	}
  	
	public static class EditarStatus
	{
		private static final byte EDITARSTATUS_INICIO = 0;
		private static final byte EDITARSTATUS_SUCCESS = 1;
		private static final byte EDITARSTATUS_NOSUCCESS = 2;
		private static final byte EDITARSTATUS_NOMETA = 3;
		
		private static Byte getEditarStatus(Byte status)
		{
			if (status == EDITARSTATUS_INICIO)
				return new Byte(EDITARSTATUS_INICIO);
			else if (status == EDITARSTATUS_SUCCESS)
				return new Byte(EDITARSTATUS_SUCCESS);
			else if (status == EDITARSTATUS_NOSUCCESS)
				return new Byte(EDITARSTATUS_NOSUCCESS);
			else if (status == EDITARSTATUS_NOMETA)
				return new Byte(EDITARSTATUS_NOMETA);
			else
				return null;
		}
		
		public static Byte getEditarStatusInicio() 
		{
			return new Byte(EDITARSTATUS_INICIO);
		}

		public static Byte getEditarStatusSuccess() 
		{
			return new Byte(EDITARSTATUS_SUCCESS);
		}
		
		public static Byte getEditarStatusNoSuccess() 
		{
			return new Byte(EDITARSTATUS_NOSUCCESS);
		}

		public static Byte getEditarStatusNoMeta() 
		{
			return new Byte(EDITARSTATUS_NOMETA);
		}
	}
  
  	public void clear() 
  	{
  		this.anchoMatriz = null;
  		Calendar fecha = Calendar.getInstance();
  		this.anoDesde = new Integer(fecha.get(1)).toString();
  		this.anoHasta = new Integer(fecha.get(1)).toString();
  		this.anoInicial = null;
  		this.anoFinal = null;
  		this.anos = null;
  		this.bloqueado = null;
  		this.clase = null;
  		this.claseId = null;
  		this.planId = null;
  		this.perspectivaId = null;
  		this.perspectivaNombre = null;
  		this.nombreObjetoPerspectiva = null;
  		this.soloSeleccionados = new Boolean(false);
  		this.desdeIndicador = new Boolean(false);
  		
  		fecha.set(2, 0);
  		fecha.set(5, 1);
  		this.fechaDesde = VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta");
  		
  		fecha.set(2, 11);
  		fecha.set(5, 31);
  		this.fechaHasta = VgcFormatter.formatearFecha(fecha.getTime(), "formato.fecha.corta");
  		this.frecuencia = null;
  		this.frecuencias = null;
  		this.indicadores = null;
  		this.numeroMaximoPeriodos = null;
  		this.organizacion = null;
  		this.paginaSeriesTiempo = null;
  		this.periodoDesde = null;
  		this.periodoHasta = null;
  		this.periodos = null;
	
  		this.iniciativaId = null;
  		this.organizacionId = null;
  		this.desdePlanificacion = false;
  		this.anosD = null;
  		this.periodosD = null;
  		this.anosH = null;
  		this.periodosH = null;
  		this.indicadoresSeries = "";
  		this.status = EditarStatus.getEditarStatusInicio();
  		this.columnas = new ArrayList<Columna>();
  	}
  	
  	public String setColumnasDefault() throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException
  	{
  		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
  		
  		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "Forma", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		// Columna Nombre
		Element columna = document.createElement("columnas");  // creamos el elemento raiz
		raiz.appendChild(columna);
		
		Element elemento = document.createElement("titulo"); //creamos un nuevo elemento
		Text text = document.createTextNode(messageResources.getResource("jsp.editarmediciones.ficha.indicador")); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("orden"); //creamos un nuevo elemento
		text = document.createTextNode("1"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("visible"); //creamos un nuevo elemento
		text = document.createTextNode("true"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("ancho"); //creamos un nuevo elemento
		text = document.createTextNode("300"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		// Columna Unidad
		columna = document.createElement("columnas");  // creamos el elemento raiz
		raiz.appendChild(columna);
		
		elemento = document.createElement("titulo"); //creamos un nuevo elemento
		text = document.createTextNode(messageResources.getResource("jsp.editarmediciones.ficha.unidad")); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("orden"); //creamos un nuevo elemento
		text = document.createTextNode("2"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("visible"); //creamos un nuevo elemento
		text = document.createTextNode("true"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("ancho"); //creamos un nuevo elemento
		text = document.createTextNode("150"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);
		
		// Columna Serie
		columna = document.createElement("columnas");  // creamos el elemento raiz
		raiz.appendChild(columna);
		
		elemento = document.createElement("titulo"); //creamos un nuevo elemento
		text = document.createTextNode(messageResources.getResource("jsp.editarmediciones.ficha.serie")); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("orden"); //creamos un nuevo elemento
		text = document.createTextNode("3"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("visible"); //creamos un nuevo elemento
		text = document.createTextNode("true"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("ancho"); //creamos un nuevo elemento
		text = document.createTextNode("150"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		// Columna periodos
		columna = document.createElement("columnas");  // creamos el elemento raiz
		raiz.appendChild(columna);
		
		elemento = document.createElement("titulo"); //creamos un nuevo elemento
		text = document.createTextNode(messageResources.getResource("jsp.editarmediciones.ficha.periodo")); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("orden"); //creamos un nuevo elemento
		text = document.createTextNode("4"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("visible"); //creamos un nuevo elemento
		text = document.createTextNode("true"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);

		elemento = document.createElement("ancho"); //creamos un nuevo elemento
		text = document.createTextNode("150"); //Ingresamos la info
		elemento.appendChild(text);
		columna.appendChild(elemento);
		
		Source source = new DOMSource(document);
		
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);

  		return writer.toString().trim();
  	}
  	
  	public void setColumnas(String xml) throws ParserConfigurationException, SAXException, IOException
  	{
  		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
  		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();;
  		DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(xml))));
		NodeList lista = doc.getElementsByTagName("columnas");
		 
		this.columnas = new ArrayList<Columna>();
		Integer orden = 0;
		for (int i = 0; i < lista.getLength() ; i ++) 
		{
			Node node = lista.item(i);
			Element elemento = (Element) node;
			NodeList nodeLista = null;
			Node valor = null;

			Columna columna = new Columna();
			orden++;
			
			String atributo = "";
			if (elemento.getElementsByTagName("titulo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("titulo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) atributo = valor.getNodeValue(); else atributo = messageResources.getResource("jsp.editarmediciones.ficha.columna.nodefinida"); 
			}
			columna.setNombre(atributo);

			atributo = "";
			if (elemento.getElementsByTagName("orden").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("orden").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) atributo = valor.getNodeValue(); else atributo = orden.toString();
			}
			columna.setOrden(atributo);
			
			atributo = "";
			if (elemento.getElementsByTagName("visible").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("visible").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) atributo = valor.getNodeValue(); else atributo = "true";
			}
			columna.setMostrar(atributo);
			
			atributo = "";
			if (elemento.getElementsByTagName("ancho").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("ancho").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) atributo = valor.getNodeValue(); else atributo = "150";
			}
			columna.setTamano(atributo);
			
			this.columnas.add(columna);
		}
  	}
}