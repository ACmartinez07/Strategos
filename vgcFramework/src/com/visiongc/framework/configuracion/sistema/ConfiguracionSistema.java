package com.visiongc.framework.configuracion.sistema;

import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.util.xmldata.XmlUtil;

public class ConfiguracionSistema
{

    public ConfiguracionSistema()
    {
    }

    public String getXml()
    {
        XmlNodo xmlConfiguracion = new XmlNodo();
        return XmlUtil.buildXml(xmlConfiguracion);
    }

    public void readFromXml(String xml)
    {
        if(xml == null || xml.equals(""))
            return;
        else
            return;
    }
}