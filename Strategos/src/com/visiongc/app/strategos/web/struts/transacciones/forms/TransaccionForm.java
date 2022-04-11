/**
 * 
 */
package com.visiongc.app.strategos.web.struts.transacciones.forms;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
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

import org.apache.struts.upload.FormFile;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class TransaccionForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;

	private Transaccion transaccion;
	private Byte tipoFuente;
	private List<ImportacionType> tiposFuentes;
	private Byte excelTipo;
	private String respuesta;
	private Byte status;
	private String fuenteSeleccion;
	private String fuenteSeleccionArchivo;
	private String bdNombre;
	private String bdUsuario;
	private String bdPassword;
	private String bdServidor;
	private String bdPuerto;
	private String bdTabla;
	private String bdListaTablas;
	private String bdStatusTabla;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;
	private String separador;
	private Boolean logMediciones;
	private Boolean logErrores;
	private Boolean hayColumnaFecha;
	private Boolean hayColumnaMonto;
	private Long indicadorTransaccionesId;
	private Long indicadorMontoId;
	private String indicadorTransaccionesNombre;
	private String indicadorMontoNombre;
	private Byte frecuencia;
	private FormFile fileForm;
	private Long transaccionId;
	
	// Campos para el Reporte
	private String fechaInicial;
	private String fechaFinal;
	private Integer numeroRegistros;
	private List<List<Columna>> datos;
	private Boolean showTablaParametro;
    private String nombre;
    private String nombreFrecuencia;
    private List<Columna> columnas;
    private Integer anchoTablaDatos;
    private Integer totalColumnas;
    private String totalConsolidado;
    private String totalOperacion;

	public Transaccion getTransaccion()
	{
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) 
	{
	    this.transaccion = transaccion;
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
		this.status = TransaccionStatus.getTransaccionStatus(status);
	}

	public Byte getTipoFuente() 
	{
		return this.tipoFuente;
	}

  	public void setTipoFuente(Byte tipoFuente) 
  	{
  		this.tipoFuente = ImportacionType.getType(tipoFuente);
  	}

	public List<ImportacionType> getTiposFuentes()
	{
		return this.tiposFuentes;
	}

	public void setTiposFuentes(List<ImportacionType> tiposFuentes) 
	{
		this.tiposFuentes = tiposFuentes;
	}
  	
	public Byte getExcelTipo() 
	{
		return this.excelTipo;
	}

	public void setExcelTipo(Byte excelTipo) 
	{
		this.excelTipo = excelTipo;
	}

	public String getFuenteSeleccion()
	{
		return this.fuenteSeleccion;
	}

	public void setFuenteSeleccion(String fuenteSeleccion) 
	{
		this.fuenteSeleccion = fuenteSeleccion;
	}

	public String getFuenteSeleccionArchivo()
	{
		return this.fuenteSeleccionArchivo;
	}

	public void setFuenteSeleccionArchivo(String fuenteSeleccionArchivo) 
	{
		this.fuenteSeleccionArchivo = fuenteSeleccionArchivo;
	}

	public String getBdNombre() 
	{
		return this.bdNombre;
	}

	public void setBdNombre(String bdNombre) 
	{
		this.bdNombre = bdNombre;
	}

	public String getBdUsuario() 
	{
		return this.bdUsuario;
	}

	public void setBdUsuario(String bdUsuario) 
	{
		this.bdUsuario = bdUsuario;
	}
	
	public String getBdPassword() 
	{
		return this.bdPassword;
	}

	public void setBdPasswrod(String bdPassword) 
	{
		this.bdPassword = bdPassword;
	}

	public String getBdServidor() 
	{
		return this.bdServidor;
	}

	public void setBdServidor(String bdServidor) 
	{
		this.bdServidor = bdServidor;
	}
	
	public String getBdPuerto() 
	{
		return this.bdPuerto;
	}

	public void setBdPuerto(String bdPuerto) 
	{
		this.bdPuerto = bdPuerto;
	}

	public String getBdTabla() 
	{
		return this.bdTabla;
	}

	public void setBdTabla(String bdTabla) 
	{
		this.bdTabla = bdTabla;
	}
	
	public String getBdListaTablas() 
	{
		return this.bdListaTablas;
	}

	public void setBdListaTablas(String bdListaTablas) 
	{
		this.bdListaTablas = bdListaTablas;
	}

	public String getBdStatusTabla() 
	{
		return this.bdStatusTabla;
	}

	public void setBdStatusTabla(String bdStatusTabla) 
	{
		this.bdStatusTabla = bdStatusTabla;
	}

	public String getPasswordConexionEncript() 
	{
		return Password.encriptPassWord(this.bdPassword);
	}

	public String getPasswordConexionDecriptado(String passwordConexion)
	{
		return Password.decriptPassWord(passwordConexion);
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
	
	public String getSeparador()
	{
		return this.separador;
	}

	public void setSeparador(String separador) 
	{
		this.separador = separador;
	}

	public Boolean getLogMediciones()
	{
		return this.logMediciones;
	}

	public void setLogMediciones(Boolean logMediciones) 
	{
		this.logMediciones = logMediciones;
	}

	public Boolean getLogErrores()
	{
		return this.logErrores;
	}

	public void setLogErrores(Boolean logErrores) 
	{
		this.logErrores = logErrores;
	}

	public Boolean getHayColumnaFecha()
	{
		return this.hayColumnaFecha;
	}

	public void setHayColumnaFecha(Boolean hayColumnaFecha) 
	{
		this.hayColumnaFecha = hayColumnaFecha;
	}

	public Boolean getHayColumnaMonto()
	{
		return this.hayColumnaMonto;
	}

	public void setHayColumnaMonto(Boolean hayColumnaMonto) 
	{
		this.hayColumnaMonto = hayColumnaMonto;
	}
	
	public Long getIndicadorTransaccionesId()
	{
		return this.indicadorTransaccionesId;
	}

	public void setIndicadorTransaccionesId(Long indicadorTransaccionesId) 
	{
		this.indicadorTransaccionesId = indicadorTransaccionesId;
	}

	public String getIndicadorTransaccionesNombre()
	{
		return this.indicadorTransaccionesNombre;
	}

	public void setIndicadorTransaccionesNombre(String indicadorTransaccionesNombre) 
	{
		this.indicadorTransaccionesNombre = indicadorTransaccionesNombre;
	}

	public Long getIndicadorMontoId()
	{
		return this.indicadorMontoId;
	}

	public void setIndicadorMontoId(Long indicadorMontoId) 
	{
		this.indicadorMontoId = indicadorMontoId;
	}

	public String getIndicadorMontoNombre()
	{
		return this.indicadorMontoNombre;
	}

	public void setIndicadorMontoNombre(String indicadorMontoNombre) 
	{
		this.indicadorMontoNombre = indicadorMontoNombre;
	}
	
	public Byte getFrecuencia() 
	{
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
		this.frecuencia = frecuencia;
	}
	
	public FormFile getFileForm()
	{
		return this.fileForm;
	}

	public void setFileForm(FormFile fileForm) 
	{
		this.fileForm = fileForm;
	}

	public Long getTransaccionId()
	{
		return this.transaccionId;
	}

	public void setTransaccionId(Long transaccionId) 
	{
		this.transaccionId = transaccionId;
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

	public Integer getNumeroRegistros()
	{
		return this.numeroRegistros;
	}

	public void setNumeroRegistros(Integer numeroRegistros) 
	{
		this.numeroRegistros = numeroRegistros;
	}

	public List<List<Columna>> getDatos()
	{
		return this.datos;
	}

	public void setDatos(List<List<Columna>> datos) 
	{
		this.datos = datos;
	}
	
	public Boolean getShowTablaParametro() 
	{
		return this.showTablaParametro;
	}

	public void setShowTablaParametro(Boolean showTablaParametro) 
	{
		this.showTablaParametro = showTablaParametro;
	}
	
    public String getNombre() 
    {
        return this.nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }
    
  	public String getNombreFrecuencia()
  	{
  		return this.nombreFrecuencia;
  	}

  	public void setNombreFrecuencia(String nombreFrecuencia) 
  	{
  		this.nombreFrecuencia = nombreFrecuencia;
  	}

  	public List<Columna> getColumnas()
  	{
  		return this.columnas;
  	}

  	public void setColumnas(List<Columna> columnas) 
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

  	public Integer getTotalColumnas()
  	{
  		return this.totalColumnas;
  	}

  	public void setTotalColumnas(Integer totalColumnas) 
  	{
  		this.totalColumnas = totalColumnas;
  	}

  	public String getTotalConsolidado()
  	{
  		return this.totalConsolidado;
  	}

  	public void setTotalConsolidado(String totalConsolidado) 
  	{
  		this.totalConsolidado = totalConsolidado;
  	}

  	public String getTotalOperacion()
  	{
  		return this.totalOperacion;
  	}

  	public void setTotalOperacion(String totalOperacion) 
  	{
  		this.totalOperacion = totalOperacion;
  	}
  	
	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);

		this.transaccion = new Transaccion();
	    this.tipoFuente = ImportacionType.getImportacionTypePlano();
	    this.excelTipo = 0;
		this.fuenteSeleccion = "";
		this.fuenteSeleccionArchivo = "";
		this.bdNombre = "";
		this.bdUsuario = "";
		this.bdPassword = "";
		this.bdServidor = "";
		this.bdPuerto = "";
		this.bdTabla = "";
		this.bdListaTablas = "";
		this.bdStatusTabla = "";
		this.separador = "|";
		this.logMediciones = true;
		this.logErrores = true;
		this.hayColumnaFecha = false;
		this.hayColumnaMonto = false;
		this.indicadorMontoId = null;
		this.indicadorMontoNombre = "";
		this.indicadorTransaccionesId = null;
		this.indicadorTransaccionesNombre = "";
		this.status = TransaccionStatus.getImportarStatusSuccess();
		this.frecuencia = null;
		this.fileForm = null;
		this.transaccionId = null;
		this.numeroRegistros= null;
		this.datos = new ArrayList<List<Columna>>();
		this.showTablaParametro = null;
		this.nombre = "";
		this.nombreFrecuencia = null;
		this.columnas = new ArrayList<Columna>();
		this.anchoTablaDatos = null;
		this.totalColumnas = 0;
		
		//Setear solo Excel
		List<Byte> tipos = new ArrayList<Byte>();
		tipos.add(ImportacionType.getImportacionTypeExcel());
		this.tiposFuentes = ImportacionType.getTipos(tipos);
	}
	
	public static class TransaccionStatus
	{
		private static final byte IMPORTARSTATUS_SUCCESS = 0;
		private static final byte IMPORTARSTATUS_NOSUCCESS = 1;
		private static final byte IMPORTARSTATUS_VALIDADO = 2;
		private static final byte IMPORTARSTATUS_SALVADO = 3;
		private static final byte IMPORTARSTATUS_IMPORTADO = 4;
		private static final byte IMPORTARSTATUS_NOCONFIGURADO = 5;
		private static final byte IMPORTARSTATUS_NOSALVADO = 6;
		private static final byte IMPORTARSTATUS_READSUCCESSFULL = 7;
		
		private static Byte getTransaccionStatus(Byte status)
		{
			if (status == IMPORTARSTATUS_SUCCESS)
				return new Byte(IMPORTARSTATUS_SUCCESS);
			else if (status == IMPORTARSTATUS_NOSUCCESS)
				return new Byte(IMPORTARSTATUS_NOSUCCESS);
			else if (status == IMPORTARSTATUS_VALIDADO)
				return new Byte(IMPORTARSTATUS_VALIDADO);
			else if (status == IMPORTARSTATUS_SALVADO)
				return new Byte(IMPORTARSTATUS_SALVADO);
			else if (status == IMPORTARSTATUS_IMPORTADO)
				return new Byte(IMPORTARSTATUS_IMPORTADO);
			else if (status == IMPORTARSTATUS_NOCONFIGURADO)
				return new Byte(IMPORTARSTATUS_NOCONFIGURADO);
			else if (status == IMPORTARSTATUS_NOSALVADO)
				return new Byte(IMPORTARSTATUS_NOSALVADO);
			else if (status == IMPORTARSTATUS_READSUCCESSFULL)
				return new Byte(IMPORTARSTATUS_READSUCCESSFULL);
			else
				return null;
		}
		
		public static Byte getImportarStatusSuccess() 
		{
			return new Byte(IMPORTARSTATUS_SUCCESS);
		}
		
		public static Byte getImportarStatusNoSuccess() 
		{
			return new Byte(IMPORTARSTATUS_NOSUCCESS);
		}

		public static Byte getImportarStatusValidado() 
		{
			return new Byte(IMPORTARSTATUS_VALIDADO);
		}

		public static Byte getImportarStatusSalvado() 
		{
			return new Byte(IMPORTARSTATUS_SALVADO);
		}

		public static Byte getImportarStatusImportado() 
		{
			return new Byte(IMPORTARSTATUS_IMPORTADO);
		}

		public static Byte getImportarStatusNoConfigurado() 
		{
			return new Byte(IMPORTARSTATUS_NOCONFIGURADO);
		}

		public static Byte getImportarStatusNoSalvado() 
		{
			return new Byte(IMPORTARSTATUS_NOSALVADO);
		}

		public static Byte getImportarStatusReadSuccess() 
		{
			return new Byte(IMPORTARSTATUS_READSUCCESSFULL);
		}
	}
	
  	public String getXmlParametros(String xmlAtributos) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
  	{
  		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "transaccion", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		Element elemento = document.createElement("fechaInicial"); //creamos un nuevo elemento
		Text text = document.createTextNode(this.fechaInicial); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("fechaFinal"); //creamos un nuevo elemento
		text = document.createTextNode(this.fechaFinal); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("numeroRegistros"); //creamos un nuevo elemento
		text = document.createTextNode(this.numeroRegistros.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("showTablaParametro"); //creamos un nuevo elemento
		text = document.createTextNode(this.showTablaParametro ? "1" : "0"); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("xmlAtributos"); //creamos un nuevo elemento
		text = document.createTextNode(xmlAtributos); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		Source source = new DOMSource(document);
		
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);

  		return writer.toString().trim();
  	}
  	
  	public String setParametros(String xml) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, SAXException, IOException
  	{
  		String atributos = null;
  		
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
			
			if (elemento.getElementsByTagName("fechaInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("fechaInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && this.fechaInicial == null) 
					this.fechaInicial = valor.getNodeValue();
			}

			if (elemento.getElementsByTagName("fechaFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("fechaFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && this.fechaFinal == null) 
					this.fechaFinal = valor.getNodeValue();
			}

			if (elemento.getElementsByTagName("numeroRegistros").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("numeroRegistros").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && this.numeroRegistros == null) 
					this.numeroRegistros = Integer.parseInt(valor.getNodeValue());
			}

			if (elemento.getElementsByTagName("showTablaParametro").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("showTablaParametro").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null && this.showTablaParametro == null) 
					this.showTablaParametro = valor.getNodeValue().equals("1") ? true : false;
			}

			if (elemento.getElementsByTagName("xmlAtributos").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("xmlAtributos").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					atributos = valor.getNodeValue();
			}
		}
		
		return atributos;
  	}	
}