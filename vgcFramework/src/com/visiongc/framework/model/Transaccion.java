package com.visiongc.framework.model;

import com.visiongc.commons.util.TextEncoder;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.builder.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// Referenced classes of package com.visiongc.framework.model:
//            Tabla, Importacion, Columna

public class Transaccion
    implements Serializable
{

    public Transaccion(Long id, String nombre, Byte frecuencia, String configuracion, Long indicadorId, Tabla tabla)
    {
        this.id = id;
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.configuracion = configuracion;
        this.tabla = tabla;
        this.indicadorId = indicadorId;
    }

    public Transaccion()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Byte getFrecuencia()
    {
        return frecuencia;
    }

    public void setFrecuencia(Byte frecuencia)
    {
        this.frecuencia = frecuencia;
    }

    public String getConfiguracion()
    {
        return configuracion;
    }

    public void setConfiguracion(String configuracion)
    {
        this.configuracion = configuracion;
    }

    public Long getIndicadorId()
    {
        return indicadorId;
    }

    public void setIndicadorId(Long indicadorId)
    {
        this.indicadorId = indicadorId;
    }

    public Tabla getTabla()
    {
        if(tabla == null && !configuracion.equals(""))
        {
            setTabla(new Tabla(null, new ArrayList()));
            try
            {
                setXml(configuracion);
            }
            catch(ParserConfigurationException e)
            {
                e.printStackTrace();
            }
            catch(TransformerFactoryConfigurationError e)
            {
                e.printStackTrace();
            }
            catch(TransformerException e)
            {
                e.printStackTrace();
            }
            catch(SAXException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return tabla;
    }

    public void setTabla(Tabla tabla)
    {
        this.tabla = tabla;
    }

    public String getFechaInicial()
    {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial)
    {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal()
    {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal)
    {
        this.fechaFinal = fechaFinal;
    }

    public Integer getNumeroRegistros()
    {
        return numeroRegistros;
    }

    public void setNumeroRegistros(Integer numeroRegistros)
    {
        this.numeroRegistros = numeroRegistros;
    }

    public Long getTotalRegistros()
    {
        return totalRegistros;
    }

    public void setTotalRegistros(Long totalRegistros)
    {
        this.totalRegistros = totalRegistros;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("id", getId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Importacion))
        {
            return false;
        } else
        {
            Importacion castOther = (Importacion)other;
            return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getId()).toHashCode();
    }

    public String getXml()
        throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "transaccion", null);
        document.setXmlVersion("1.0");
        Element raiz = document.createElement("properties");
        document.getDocumentElement().appendChild(raiz);
        Element elemento = document.createElement("tabla");
        Text text = document.createTextNode(getTabla().getNombre());
        elemento.appendChild(text);
        raiz.appendChild(elemento);
        if(indicadorId != null && indicadorId.longValue() != 0L)
        {
            elemento = document.createElement("indicadorIdTransaccion");
            text = document.createTextNode(indicadorId.toString());
            elemento.appendChild(text);
            raiz.appendChild(elemento);
        }
        Element campos = document.createElement("campos");
        raiz.appendChild(campos);
        for(Iterator i = tabla.getColumnas().iterator(); i.hasNext();)
        {
            Columna columna = (Columna)i.next();
            Element campoElement = document.createElement("campo");
            campos.appendChild(campoElement);
            elemento = document.createElement("nombre");
            text = document.createTextNode(columna.getNombre());
            elemento.appendChild(text);
            campoElement.appendChild(elemento);
            elemento = document.createElement("tipo");
            text = document.createTextNode(columna.getTipo().toString());
            elemento.appendChild(text);
            campoElement.appendChild(elemento);
            elemento = document.createElement("tamano");
            text = document.createTextNode(columna.getTamano().toString());
            elemento.appendChild(text);
            campoElement.appendChild(elemento);
            elemento = document.createElement("alias");
            text = document.createTextNode(columna.getAlias());
            elemento.appendChild(text);
            campoElement.appendChild(elemento);
            elemento = document.createElement("formato");
            text = document.createTextNode(columna.getFormato());
            elemento.appendChild(text);
            campoElement.appendChild(elemento);
            elemento = document.createElement("clavePrimaria");
            text = document.createTextNode(columna.getClavePrimaria().booleanValue() ? "1" : "0");
            elemento.appendChild(text);
            campoElement.appendChild(elemento);
            if(columna.getIndicadorId() != null && columna.getIndicadorId().longValue() != 0L)
            {
                elemento = document.createElement("indicadorId");
                text = document.createTextNode(columna.getIndicadorId().toString());
                elemento.appendChild(text);
                campoElement.appendChild(elemento);
            }
        }

        javax.xml.transform.Source source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        javax.xml.transform.Result result = new StreamResult(writer);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
        return writer.toString().trim();
    }

    public void setXml(String xml)
        throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, SAXException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(xml))));
        NodeList lista = doc.getElementsByTagName("properties");
        for(int i = 0; i < lista.getLength(); i++)
        {
            Node node = lista.item(i);
            Element elemento = (Element)node;
            NodeList nodeLista = null;
            Node valor = null;
            if(elemento.getElementsByTagName("tabla").getLength() > 0)
            {
                if(getTabla() == null)
                    setTabla(new Tabla(null, new ArrayList()));
                nodeLista = elemento.getElementsByTagName("tabla").item(0).getChildNodes();
                valor = nodeLista.item(0);
                if(valor != null)
                    getTabla().setNombre(valor.getNodeValue());
                else
                    getTabla().setNombre(null);
                if(elemento.getElementsByTagName("indicadorIdTransaccion").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("indicadorIdTransaccion").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        setIndicadorId(Long.valueOf(Long.parseLong(valor.getNodeValue())));
                    else
                        setIndicadorId(null);
                }
                NodeList listaHijos = doc.getElementsByTagName("campo");
                if(listaHijos.getLength() > 0)
                {
                    int order = 0;
                    for(int hijos = 0; hijos < listaHijos.getLength(); hijos++)
                    {
                        node = listaHijos.item(hijos);
                        elemento = (Element)node;
                        nodeLista = null;
                        valor = null;
                        Columna columna = new Columna();
                        order++;
                        columna.setOrden(String.valueOf(order));
                        if(elemento.getElementsByTagName("nombre").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("nombre").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setNombre(valor.getNodeValue());
                            else
                                columna.setNombre(null);
                        }
                        if(elemento.getElementsByTagName("tipo").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("tipo").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setTipo(Byte.valueOf(Byte.parseByte(valor.getNodeValue())));
                            else
                                columna.setTipo(null);
                        }
                        if(elemento.getElementsByTagName("tamano").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("tamano").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setTamano(Integer.valueOf(Integer.parseInt(valor.getNodeValue())));
                            else
                                columna.setTamano(null);
                        }
                        if(elemento.getElementsByTagName("alias").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("alias").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setAlias(valor.getNodeValue());
                            else
                                columna.setAlias(null);
                        }
                        if(elemento.getElementsByTagName("formato").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("formato").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setFormato(valor.getNodeValue());
                            else
                                columna.setFormato(null);
                        }
                        if(elemento.getElementsByTagName("clavePrimaria").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("clavePrimaria").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setClavePrimaria(Boolean.valueOf(valor.getNodeValue().equals("1")));
                            else
                                columna.setClavePrimaria(null);
                        }
                        if(elemento.getElementsByTagName("indicadorId").getLength() > 0)
                        {
                            nodeLista = elemento.getElementsByTagName("indicadorId").item(0).getChildNodes();
                            valor = nodeLista.item(0);
                            if(valor != null)
                                columna.setIndicadorId(Long.valueOf(Long.parseLong(valor.getNodeValue())));
                            else
                                columna.setFormato(null);
                        }
                        getTabla().getColumnas().add(columna);
                    }

                }
            }
        }

    }

    static final long serialVersionUID = 0L;
    private Long id;
    private String nombre;
    private Byte frecuencia;
    private String configuracion;
    private Tabla tabla;
    private Long indicadorId;
    private Long totalRegistros;
    private String fechaInicial;
    private String fechaFinal;
    private Integer numeroRegistros;
}