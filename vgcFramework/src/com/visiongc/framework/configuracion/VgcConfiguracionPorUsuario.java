package com.visiongc.framework.configuracion;

import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.commons.util.xmldata.XmlUtil;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.visiongc.framework.configuracion:
//            VgcConfiguracionesBase

public class VgcConfiguracionPorUsuario
{

    public VgcConfiguracionPorUsuario()
    {
        configuracionesBase = null;
        nombreConfiguracionBase = null;
    }

    public static VgcConfiguracionPorUsuario getInstance(
			String nombreConfiguracionBase) {
		VgcConfiguracionPorUsuario configuracionPorUsuario = (VgcConfiguracionPorUsuario) configuracionesPorUsuario
				.get(nombreConfiguracionBase);
		if (configuracionPorUsuario == null) {
			configuracionPorUsuario = new VgcConfiguracionPorUsuario();
			try {
				Class<?> clase = VgcConfiguracionPorUsuario.class
						.getClassLoader().loadClass(nombreConfiguracionBase);
				Constructor<?> constructor = clase.getConstructor(null);
				configuracionPorUsuario.configuracionesBase = (VgcConfiguracionesBase) constructor
						.newInstance(null);
			} catch (NoSuchMethodException e) {
				throw new ChainedRuntimeException(
						"No pudo instanciarse la clase de Configuraciones Base "
								+ nombreConfiguracionBase
								+ " (La clase no tiene un constructor apropiado)",
						(Throwable) e);
			} catch (NullPointerException e) {
				throw new ChainedRuntimeException(
						"No existe la clase de Configuraciones Base "
								+ nombreConfiguracionBase, (Throwable) e);
			} catch (ClassNotFoundException e) {
				throw new ChainedRuntimeException("No pudo encontrarse "
						+ nombreConfiguracionBase, (Throwable) e);
			} catch (Throwable t) {
				throw new ChainedRuntimeException(
						"No pudo instanciarse la clase de Configuraciones Base "
								+ nombreConfiguracionBase
								+ " (véase el stacktrace para más detalles)", t);
			}
			configuracionPorUsuario.nombreConfiguracionBase = nombreConfiguracionBase;
			configuracionesPorUsuario.put(nombreConfiguracionBase,
					configuracionPorUsuario);
		}
		return configuracionPorUsuario;
	}

    public XmlNodo getConfiguracion(String nombreObjeto, Long usuarioId)
    {
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        XmlNodo configuracion = null;
        ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(usuarioId, nombreConfiguracionBase, nombreObjeto);
        String stringXml = null;
        if(configuracionUsuario != null)
            stringXml = configuracionUsuario.getData();
        if(stringXml != null)
            configuracion = XmlUtil.readXml(stringXml);
        return configuracion;
    }

    public VgcConfiguracionesBase getConfiguracionesBase()
    {
        return configuracionesBase;
    }

    private VgcConfiguracionesBase configuracionesBase;
    private String nombreConfiguracionBase;
    private static Map configuracionesPorUsuario = null;

    static 
    {
        configuracionesPorUsuario = new HashMap();
    }
}