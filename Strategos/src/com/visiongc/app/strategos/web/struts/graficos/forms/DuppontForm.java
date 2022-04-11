/**
 * 
 */
package com.visiongc.app.strategos.web.struts.graficos.forms;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicionImportar;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm.SourceType;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class DuppontForm extends EditarObjetoForm
{
	static final long serialVersionUID = 0L;
	
	private Indicador indicador;
	private Long planId;
	private String arbol;
	private String anoInicial;
	private String anoFinal;
	private Integer periodoInicial;
	private Integer periodoFinal;
	private Byte frecuencia;
	private String fechaDesde;
	private String fechaHasta;
	private Integer numerodeNiveles;
	private Integer tamanoLetra;
	private Byte source;
	
  	public DuppontForm()
  	{
  	}

  	public Indicador getIndicador() 
  	{
  		return this.indicador;
  	}

  	public void setIndicador(Indicador indicador) 
  	{
  		this.indicador = indicador;
  	}

  	public String getArbol() 
  	{
  		return this.arbol;
  	}

  	public void setArbol(String arbol) 
  	{
  		this.arbol = arbol;
  	}
  	
  	public Long getPlanId() 
  	{
  		return this.planId;
  	}

  	public void setPlanId(Long planId) 
  	{
  		this.planId = planId;
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

	public Byte getFrecuencia() 
	{
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) 
	{
		this.frecuencia = frecuencia;
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

	public Integer getNumerodeNiveles() 
	{
		return this.numerodeNiveles;
	}

	public void setNumerodeNiveles(Integer numerodeNiveles) 
	{
		this.numerodeNiveles = numerodeNiveles;
	}
	
	public Integer getTamanoLetra() 
	{
		return this.tamanoLetra;
	}

	public void setTamanoLetra(Integer tamanoLetra) 
	{
		this.tamanoLetra = tamanoLetra;
	}

 	public Byte getSource()
  	{
  		return this.source;
  	}

  	public void setSource(Byte source) 
  	{
  		this.source = SourceType.getTypeSource(source);
  	}
	
	public String getSeparadorSeries() 
	{
		return new com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm().getSeparadorSeries();
	}
	
	public void clear() 
	{
		this.indicador = null;
		this.arbol = null;
		this.planId = null;
		this.anoInicial = null;
		this.anoFinal = null;
		this.periodoInicial = null;
		this.periodoFinal = null;
		this.frecuencia = null;
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.numerodeNiveles = null;
		this.source = null;
	}
	
  	public String getXml() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
  	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "Duppont", null);
		document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

		Element raiz = document.createElement("properties");  // creamos el elemento raiz
		document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

		Element elemento = document.createElement("anoInicial"); //creamos un nuevo elemento
		Text text = document.createTextNode(this.anoInicial); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("anoFinal"); //creamos un nuevo elemento
		text = document.createTextNode(this.anoFinal); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);
		
		elemento = document.createElement("periodoInicial"); //creamos un nuevo elemento
		text = document.createTextNode(this.periodoInicial.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("periodoFinal"); //creamos un nuevo elemento
		text = document.createTextNode(this.periodoFinal.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("numerodeNiveles"); //creamos un nuevo elemento
		text = document.createTextNode(this.numerodeNiveles.toString()); //Ingresamos la info
		elemento.appendChild(text);
		raiz.appendChild(elemento);

		elemento = document.createElement("tamanoLetra"); //creamos un nuevo elemento
		text = document.createTextNode(this.tamanoLetra.toString()); //Ingresamos la info
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
			
			if (elemento.getElementsByTagName("anoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.anoInicial = valor.getNodeValue();
				else
					this.anoInicial = null;
			}

			if (elemento.getElementsByTagName("anoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("anoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.anoFinal = valor.getNodeValue();
				else
					this.anoFinal = null;
			}

			if (elemento.getElementsByTagName("periodoInicial").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoInicial").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.periodoInicial = Integer.parseInt(valor.getNodeValue());
				else
					this.periodoInicial = null;
			}

			if (elemento.getElementsByTagName("periodoFinal").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("periodoFinal").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.periodoFinal = Integer.parseInt(valor.getNodeValue());
				else
					this.periodoFinal = null;
			}

			if (elemento.getElementsByTagName("numerodeNiveles").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("numerodeNiveles").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.numerodeNiveles = Integer.parseInt(valor.getNodeValue());
				else
					this.numerodeNiveles = null;
			}

			if (elemento.getElementsByTagName("tamanoLetra").getLength() > 0)
			{
				nodeLista = elemento.getElementsByTagName("tamanoLetra").item(0).getChildNodes();
				valor = (Node) nodeLista.item(0);
				if (valor != null) 
					this.tamanoLetra = Integer.parseInt(valor.getNodeValue());
				else
					this.tamanoLetra = null;
			}
		}
  	}
  	
	public static class SourceType
	{
		private static final byte DUPPONT_SOURCE_GESTIONAR = 1;
		private static final byte DUPPONT_SOURCE_GRAFICO = 2;
		private static final byte DUPPONT_SOURCE_PRESENTACION = 3;

		private static Byte getTypeSource(Byte source)
		{
			if (source == DUPPONT_SOURCE_GESTIONAR)
				return new Byte(DUPPONT_SOURCE_GESTIONAR);
			else if (source == DUPPONT_SOURCE_GRAFICO)
				return new Byte(DUPPONT_SOURCE_GRAFICO);
			else if (source == DUPPONT_SOURCE_PRESENTACION)
				return new Byte(DUPPONT_SOURCE_PRESENTACION);
			else
				return null;
		}
		
		public static byte getSourceGestionar() 
		{
			return DUPPONT_SOURCE_GESTIONAR;
		}

		public static byte getSourceGrafico() 
		{
			return DUPPONT_SOURCE_GRAFICO;
		}
		
		public static byte getSourcePresentacion() 
		{
			return DUPPONT_SOURCE_PRESENTACION;
		}
	}
}
