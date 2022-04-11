package com.visiongc.framework.configuracion;

import com.visiongc.commons.util.xmldata.XmlNodo;

public interface VgcObjetoConfigurable
{

    public abstract XmlNodo getConfiguracion();

    public abstract XmlNodo getConfiguracionBase();

    public abstract boolean configuracionesSonIguales(XmlNodo xmlnodo, XmlNodo xmlnodo1);
}