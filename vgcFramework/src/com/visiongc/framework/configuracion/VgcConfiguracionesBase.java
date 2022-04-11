package com.visiongc.framework.configuracion;

import com.visiongc.commons.util.xmldata.XmlNodo;

public interface VgcConfiguracionesBase
{

    public abstract XmlNodo getConfiguracionBase(String s);

    public abstract Object getObjetoConfiguracionBase(String s);
}