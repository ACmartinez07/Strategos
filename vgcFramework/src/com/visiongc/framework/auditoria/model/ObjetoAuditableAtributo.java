package com.visiongc.framework.auditoria.model;

import com.visiongc.commons.util.TextEncoder;
import java.io.*;
import javax.xml.parsers.*;
import org.apache.commons.lang.builder.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// Referenced classes of package com.visiongc.framework.auditoria.model:
//            ObjetoAuditableAtributoPK

public class ObjetoAuditableAtributo
    implements Serializable
{

    public ObjetoAuditableAtributo()
    {
    }

    public ObjetoAuditableAtributoPK getPk()
    {
        return pk;
    }

    public void setPk(ObjetoAuditableAtributoPK pk)
    {
        this.pk = pk;
    }

    public Byte getTipo()
    {
        return tipo;
    }

    public void setTipo(Byte tipo)
    {
        this.tipo = tipo;
    }

    public String getConfiguracion()
    {
        return configuracion;
    }

    public void setConfiguracion(String configuracion)
    {
        this.configuracion = configuracion;
        if(this.configuracion != null)
            setXML();
    }

    public String getClaseRelacion()
    {
        return claseRelacion;
    }

    public void setClaseRelacion(String claseRelacion)
    {
        this.claseRelacion = claseRelacion;
    }

    public String getClaseImpl()
    {
        return claseImpl;
    }

    public void setClaseImpl(String claseImpl)
    {
        this.claseImpl = claseImpl;
    }

    public String getClaseSet()
    {
        return claseSet;
    }

    public void setClaseSet(String claseSet)
    {
        this.claseSet = claseSet;
    }

    public String getNombreRelacion()
    {
        return nombreRelacion;
    }

    public void setNombreRelacion(String nombreRelacion)
    {
        this.nombreRelacion = nombreRelacion;
    }

    public String getValorRelacion()
    {
        return valorRelacion;
    }

    public void setValorRelacion(String valorRelacion)
    {
        this.valorRelacion = valorRelacion;
    }

    public String getNombreImpl()
    {
        return nombreImpl;
    }

    public void setNombreImpl(String nombreImpl)
    {
        this.nombreImpl = nombreImpl;
    }

    public String getNombreSet()
    {
        return nombreSet;
    }

    public void setNombreSet(String nombreSet)
    {
        this.nombreSet = nombreSet;
    }

    public Boolean getHasValue()
    {
        return hasValue;
    }

    public void setHasValue(Boolean hasValue)
    {
        this.hasValue = hasValue;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof ObjetoAuditableAtributo))
        {
            return false;
        } else
        {
            ObjetoAuditableAtributo castOther = (ObjetoAuditableAtributo)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    private void setXML()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(configuracion))));
            NodeList lista = doc.getElementsByTagName("properties");
            for(int i = 0; i < lista.getLength(); i++)
            {
                Node node = lista.item(i);
                Element elemento = (Element)node;
                NodeList nodeLista = null;
                Node valor = null;
                if(elemento.getElementsByTagName("claseRelacion").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("claseRelacion").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        claseRelacion = valor.getNodeValue();
                }
                if(elemento.getElementsByTagName("claseImpl").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("claseImpl").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        claseImpl = valor.getNodeValue();
                }
                if(elemento.getElementsByTagName("claseSet").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("claseSet").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        claseSet = valor.getNodeValue();
                }
                if(elemento.getElementsByTagName("nombreRelacion").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("nombreRelacion").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        nombreRelacion = valor.getNodeValue();
                }
                if(elemento.getElementsByTagName("valorRelacion").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("valorRelacion").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        valorRelacion = valor.getNodeValue();
                }
                if(elemento.getElementsByTagName("nombreImpl").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("nombreImpl").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        nombreImpl = valor.getNodeValue();
                }
                if(elemento.getElementsByTagName("nombreSet").getLength() > 0)
                {
                    nodeLista = elemento.getElementsByTagName("nombreSet").item(0).getChildNodes();
                    valor = nodeLista.item(0);
                    if(valor != null)
                        nombreSet = valor.getNodeValue();
                }
            }

        }
        catch(ParserConfigurationException parserconfigurationexception) { }
        catch(SAXException saxexception) { }
        catch(IOException ioexception) { }
    }

    static final long serialVersionUID = 0L;
    private ObjetoAuditableAtributoPK pk;
    private Byte tipo;
    private String configuracion;
    private String claseRelacion;
    private String claseImpl;
    private String claseSet;
    private String nombreRelacion;
    private String valorRelacion;
    private String nombreImpl;
    private String nombreSet;
    private Boolean hasValue;
}