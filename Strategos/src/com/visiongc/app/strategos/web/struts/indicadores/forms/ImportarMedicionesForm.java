/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.forms;

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

import org.apache.struts.upload.FormFile;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.indicadores.model.util.TipoMedicionImportar;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class ImportarMedicionesForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;

	private Byte tipoFuente;
	private Byte tipoImportacion;
	private Byte tipoMedicion;
	private String planSeleccion;
	private Long planSeleccionId;
	private String fuenteSeleccion;
	private String fuenteSeleccionArchivo;
	private Boolean todosPlanes;
	private Boolean todosOrganizacion;
	private String separador;
	private Boolean logMediciones;
	private Boolean logErrores;
	private String respuesta;
	private FormFile fileForm;
	private Boolean calcularMediciones;
	private List<ImportacionType> tiposFuentes;
	private String plantillaSeleccion;
	private Byte asistenteEditar;
	private String nombreForma = null;
	private String nombreCampoValor = null;
	private String nombreCampoOculto = null;
	private String funcionCierre;
	private Byte excelTipo;
	private String bdNombre;
	private String bdUsuario;
	private String bdPassword;
	private String bdServidor;
	private String bdPuerto;
	private String bdTabla;
	private String nombre;
	private Long id;
	private String bdListaTablas;
	private String bdStatusTabla;
	
	public Byte getTipoMedicionEjecutadoTodos() 
	{
		return TipoMedicionImportar.getImportarEjecutadoTodos();
	}
	
	public Byte getTipoMedicionEjecutadoReales() 
	{
		return TipoMedicionImportar.getImportarEjecutadoReales();
	}

	public Byte getTipoMedicionEjecutadoMinimos() 
	{
		return TipoMedicionImportar.getImportarEjecutadoMinimos();
	}

	public Byte getTipoMedicionEjecutadoMaximos() 
	{
		return TipoMedicionImportar.getImportarEjecutadoMaximo();
	}

	public Byte getTipoMedicionEjecutadoProgramados() 
	{
		return TipoMedicionImportar.getImportarEjecutadoProgramado();
	}

	public Byte getTipoMedicionMetasParciales() 
	{
		return TipoMedicionImportar.getImportarMetaParciales();
	}

	public Byte getTipoMedicionMetasAnuales() 
	{
		return TipoMedicionImportar.getImportarMetaParciales();
	}
	
	public Byte getTipoMedicionImportarEjecutado() 
	{
		return 0;
	}

	public Byte getTipoMedicionImportarMetas() 
	{
		return 1;
	}
	
	public Byte getTipoFuente() 
	{
		return this.tipoFuente;
	}

  	public void setTipoFuente(Byte tipoFuente) 
  	{
  		this.tipoFuente = tipoFuente;
  	}

	public Byte getTipoImportacion() 
	{
		return this.tipoImportacion;
	}

  	public void setTipoImportacion(Byte tipoImportacion) 
  	{
  		this.tipoImportacion = tipoImportacion;
  	}

	public Byte getTipoMedicion() 
	{
		return this.tipoMedicion;
	}

  	public void setTipoMedicion(Byte tipoMedicion) 
  	{
  		this.tipoMedicion = tipoMedicion;
  	}

	public String getPlanSeleccion()
	{
		return this.planSeleccion;
	}

	public void setPlanSeleccion(String planSeleccion) 
	{
		this.planSeleccion = planSeleccion;
	}

	public Long getPlanSeleccionId()
	{
		return this.planSeleccionId;
	}

	public void setPlanSeleccionId(Long planSeleccionId) 
	{
		this.planSeleccionId = planSeleccionId;
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

	public void setFuenteSeleccionId(String fuenteSeleccionArchivo) 
	{
		this.fuenteSeleccionArchivo = fuenteSeleccionArchivo;
	}

	public Boolean getTodosPlanes()
	{
		return this.todosPlanes;
	}

	public void setTodosPlanes(Boolean todosPlanes) 
	{
		this.todosPlanes = todosPlanes;
	}

	public Boolean getTodosOrganizacion()
	{
		return this.todosOrganizacion;
	}

	public void setTodosOrganizacion(Boolean todosOrganizacion) 
	{
		this.todosOrganizacion = todosOrganizacion;
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
	
	public Boolean getCalcularMediciones()
	{
		return this.calcularMediciones;
	}

	public void setCalcularMediciones(Boolean calcularMediciones) 
	{
		this.calcularMediciones = calcularMediciones;
	}
	
	public List<ImportacionType> getTiposFuentes()
	{
		return this.tiposFuentes;
	}

	public void setTiposFuentes(List<ImportacionType> tiposFuentes) 
	{
		this.tiposFuentes = tiposFuentes;
	}

	public String getRespuesta()
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}

	public FormFile getFileForm()
	{
		return this.fileForm;
	}

	public void setFileForm(FormFile fileForm) 
	{
		this.fileForm = fileForm;
	}
	
	public Boolean getLogErrores()
	{
		return this.logErrores;
	}

	public void setLogErrores(Boolean logErrores) 
	{
		this.logErrores = logErrores;
	}
	
	public String getPlantillaSeleccion()
	{
		return this.plantillaSeleccion;
	}

	public void setPlantillaSeleccion(String plantillaSeleccion) 
	{
		this.plantillaSeleccion = plantillaSeleccion;
	}

	public Byte getAsistenteEditar() 
	{
		return this.asistenteEditar;
	}

	public void setAsistenteEditar(Byte asistenteEditar) 
	{
		this.asistenteEditar = asistenteEditar;
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

	public Byte getExcelTipo() 
	{
		return this.excelTipo;
	}

	public void setExcelTipo(Byte excelTipo) 
	{
		this.excelTipo = excelTipo;
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

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	public Long getId() 
	{
		return this.id;
	}

	public void setId(Long id) 
	{
		this.id = id;
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

	public void clear() 
	{
		setNombreForma(null);
		setNombreCampoValor(null);
		setNombreCampoOculto(null);

		this.tipoFuente = ImportacionType.getImportacionTypePlano();
		this.tipoImportacion = TipoMedicionImportar.getImportarEjecutadoReales();
		this.tipoMedicion = 0;
		this.planSeleccion = "";
		this.planSeleccionId = new Long(0L);
		this.todosOrganizacion = false;
		this.todosPlanes = false;
		this.separador = "|";
		this.logMediciones = true;
		this.logErrores = true;
		this.calcularMediciones = true;
		this.tiposFuentes = ImportacionType.getTipos();
		this.planSeleccion = "";
		this.plantillaSeleccion = "";
		this.asistenteEditar = 0;
		this.excelTipo = 0;
		this.id = new Long(0L);
		this.nombre = "";
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
		this.setShowPresentacion(false);
	}
	
	public static class ImportarStatus
	{
		private static final byte IMPORTARSTATUS_SUCCESS = 0;
		private static final byte IMPORTARSTATUS_NOSUCCESS = 1;
		private static final byte IMPORTARSTATUS_VALIDADO = 2;
		private static final byte IMPORTARSTATUS_SALVADO = 3;
		private static final byte IMPORTARSTATUS_IMPORTADO = 4;
		private static final byte IMPORTARSTATUS_NOCONFIGURADO = 5;
		private static final byte IMPORTARSTATUS_NOSALVADO = 6;
		private static final byte IMPORTARSTATUS_READSUCCESSFULL = 7;
		private static final byte IMPORTARSTATUS_FILEERROR = 8;
		
		private static Byte getImportarStatus(Byte status)
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
			else if (status == IMPORTARSTATUS_FILEERROR)
				return new Byte(IMPORTARSTATUS_FILEERROR);
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
		
		public static Byte getImportarStatusFileError() 
		{
			return new Byte(IMPORTARSTATUS_FILEERROR);
		}
	}
	
  	public String getXml() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
  	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "Importacion", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		Element elemento = document.createElement("id"); //creamos un nuevo elemento
		Text text = document.createTextNode(this.id.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("nombre"); //creamos un nuevo elemento
		text = document.createTextNode(this.nombre); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		elemento = document.createElement("tipoFuente"); //creamos un nuevo elemento
		text = document.createTextNode(this.tipoFuente.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("asistenteEditar"); //creamos un nuevo elemento
		text = document.createTextNode(this.asistenteEditar.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("excelTipo"); //creamos un nuevo elemento
		text = document.createTextNode(this.excelTipo.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("fuenteSeleccion"); //creamos un nuevo elemento
		text = document.createTextNode(this.fuenteSeleccion); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("fuenteSeleccionArchivo"); //creamos un nuevo elemento
		text = document.createTextNode(this.fuenteSeleccionArchivo); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("separador"); //creamos un nuevo elemento
		text = document.createTextNode(this.separador); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("bdNombre"); //creamos un nuevo elemento
		text = document.createTextNode(this.bdNombre); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("bdUsuario"); //creamos un nuevo elemento
		text = document.createTextNode(this.bdUsuario); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("bdPassword"); //creamos un nuevo elemento
		text = document.createTextNode(this.getPasswordConexionEncript()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("bdServidor"); //creamos un nuevo elemento
		text = document.createTextNode(this.bdServidor); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("bdPuerto"); //creamos un nuevo elemento
		text = document.createTextNode(this.bdPuerto); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("tipoImportacion"); //creamos un nuevo elemento
		text = document.createTextNode(this.tipoImportacion.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("tipoMedicion"); //creamos un nuevo elemento
		text = document.createTextNode(this.tipoMedicion.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		elemento = document.createElement("planSeleccion"); //creamos un nuevo elemento
		text = document.createTextNode(this.planSeleccion); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("planSeleccionId"); //creamos un nuevo elemento
		text = document.createTextNode(this.planSeleccionId.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);
				
		elemento = document.createElement("todosPlanes"); //creamos un nuevo elemento
		text = document.createTextNode(this.todosPlanes ? "1" : "0"); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("todosOrganizacion"); //creamos un nuevo elemento
		text = document.createTextNode(this.todosOrganizacion ? "1" : "0"); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("logMediciones"); //creamos un nuevo elemento
		text = document.createTextNode(this.logMediciones ? "1" : "0"); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("logErrores"); //creamos un nuevo elemento
		text = document.createTextNode(this.logErrores ? "1" : "0"); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("calcularMediciones"); //creamos un nuevo elemento
		text = document.createTextNode(this.calcularMediciones ? "1" : "0"); //Ingresamos la info
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
			
			if (elemento.getElementsByTagName("asistenteEditar").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("asistenteEditar").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.asistenteEditar = Byte.parseByte(valor.getNodeValue());
				else
					this.asistenteEditar = 0;
			}

			if (elemento.getElementsByTagName("excelTipo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("excelTipo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.excelTipo = Byte.parseByte(valor.getNodeValue());
				else
					this.excelTipo = 0;
			}

			if (elemento.getElementsByTagName("fuenteSeleccion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("fuenteSeleccion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.fuenteSeleccion = valor.getNodeValue();
				else
					this.fuenteSeleccion = "";
			}

			if (elemento.getElementsByTagName("fuenteSeleccionArchivo").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("fuenteSeleccionArchivo").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.fuenteSeleccionArchivo = valor.getNodeValue();
				else
					this.fuenteSeleccionArchivo = "";
			}

			if (elemento.getElementsByTagName("separador").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("separador").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.separador = valor.getNodeValue();
				else
					this.separador = "|";
			}

			if (elemento.getElementsByTagName("bdNombre").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("bdNombre").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.bdNombre = valor.getNodeValue();
				else
					this.bdNombre = "";
			}

			if (elemento.getElementsByTagName("bdUsuario").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("bdUsuario").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.bdUsuario = valor.getNodeValue();
				else
					this.bdUsuario = "";
			}

			if (elemento.getElementsByTagName("bdPassword").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("bdPassword").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.bdPassword = this.getPasswordConexionDecriptado(valor.getNodeValue());
				else
					this.bdPassword = "";
			}

			if (elemento.getElementsByTagName("bdServidor").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("bdServidor").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.bdServidor = valor.getNodeValue();
				else
					this.bdServidor = "";
			}

			if (elemento.getElementsByTagName("bdPuerto").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("bdPuerto").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.bdPuerto = valor.getNodeValue();
				else
					this.bdPuerto = "";
			}

			if (elemento.getElementsByTagName("tipoImportacion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tipoImportacion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.tipoImportacion = Byte.parseByte(valor.getNodeValue());
				else
					this.tipoImportacion = TipoMedicionImportar.getImportarEjecutadoReales();
			}

			if (elemento.getElementsByTagName("tipoMedicion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tipoMedicion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.tipoMedicion = Byte.parseByte(valor.getNodeValue());
				else
					this.tipoMedicion = 0;
			}

			if (elemento.getElementsByTagName("planSeleccion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("planSeleccion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.planSeleccion = valor.getNodeValue();
				else
					this.planSeleccion = "";
			}

			if (elemento.getElementsByTagName("planSeleccionId").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("planSeleccionId").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.planSeleccionId = Long.parseLong(valor.getNodeValue());
				else
					this.planSeleccionId = new Long(0L);
			}

			if (elemento.getElementsByTagName("todosPlanes").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("todosPlanes").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.todosPlanes = valor.getNodeValue() == "1" ? true: false;
				else
					this.todosPlanes = false;
			}

			if (elemento.getElementsByTagName("todosOrganizacion").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("todosOrganizacion").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.todosOrganizacion = valor.getNodeValue() == "1" ? true: false;
				else
					this.todosOrganizacion = false;
			}

			if (elemento.getElementsByTagName("logMediciones").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("logMediciones").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.logMediciones = valor.getNodeValue() == "1" ? true: false;
				else
					this.logMediciones = true;
			}

			if (elemento.getElementsByTagName("logErrores").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("logErrores").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.logErrores = valor.getNodeValue() == "1" ? true: false;
				else
					this.logErrores = true;
			}

			if (elemento.getElementsByTagName("calcularMediciones").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("calcularMediciones").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.calcularMediciones = valor.getNodeValue() == "1" ? true: false;
				else
					this.calcularMediciones = true;
			}
		}
  	}	
}
