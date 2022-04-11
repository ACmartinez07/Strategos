package com.visiongc.framework.configuracion;

import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.util.xmldata.XmlUtil;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;

// Referenced classes of package com.visiongc.framework.configuracion:
//            VgcConfiguracionesBase

public class FrameworkConfiguracionesBase
    implements VgcConfiguracionesBase
{

    public FrameworkConfiguracionesBase()
    {
    }

    public XmlNodo getConfiguracionBase(String nombreObjeto)
    {
        XmlNodo configuracion = null;
        if(nombreObjeto != null && nombreObjeto.equals("configuracionPagina"))
            configuracion = XmlUtil.readXml((new ConfiguracionPagina()).getXml());
        return configuracion;
    }

    public Object getObjetoConfiguracionBase(String nombreObjeto)
    {
        Object objeto = null;
        if(nombreObjeto != null && nombreObjeto.equals("configuracionPagina"))
            objeto = new ConfiguracionPagina();
        return objeto;
    }
}