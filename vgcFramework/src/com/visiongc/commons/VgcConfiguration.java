package com.visiongc.commons;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;

import javax.naming.ConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkConfiguration;

/**
 * Clase base que implementa el manejo de parámetros de configuración a nivel de
 * sistema mediante archivos .properties
 * 
 * @author Pablo
 * 
 */
public abstract class VgcConfiguration 
{
	private static Map configurations = new HashMap();

	protected Properties properties = null;
	protected Map abbreviatedClassNames = new HashMap();
	
	protected VgcConfiguration(Properties properties) 
	{
		/** Se obtienen los nombres de clases abreviados */
		this.abbreviatedClassNames = getAbbreviatedClassNames();

		/** Se obtienen primero las propiedades por defecto */
		this.properties = getDefaultProperties();

		/** Se agregan las propiedades nuevas que vienen del parámetro */
		this.properties.putAll(properties);

		/** Se obtiene la clase del componente de configuración */
		Class confClass = this.getClass();

		/**
		 * Se busca el componente de configuración en la lista de
		 * configuraciones, basado en la clase del componente
		 */
		VgcConfiguration conf = (VgcConfiguration) configurations.get(confClass);
		if (conf == null) {
			/** Si no existe el componente en la lista, se agrega */
			configurations.put(this.getClass(), this);
		}

		logConfigurations();

	}

	public VgcConfiguration(Class confClass) throws ChainedRuntimeException 
	{
		this(loadProperties(confClass));
	}

	public static final VgcConfiguration getConfiguration(Class confClass) 
	{
		VgcConfiguration conf = null;

		conf = (VgcConfiguration) configurations.get(confClass);

		return conf;
	}

	public static final Properties loadProperties(Class confClass) 
	{
		String resource = null;
		String className = confClass.getName();

		while (className.indexOf(".") > -1) {
			className = className.substring(className.indexOf(".") + 1);
		}

		resource = className.substring(0, className.indexOf("Configuration")) + ".properties";

		return loadProperties(confClass.getClassLoader(), resource);
	}

	public static final Properties loadProperties(String resource) throws ChainedRuntimeException 
	{
		return loadProperties(VgcConfiguration.class.getClassLoader(), resource);
	}

	public static final Properties loadProperties(ClassLoader classLoader, String resource) throws ChainedRuntimeException 
	{
		Properties properties = new Properties();

		try 
		{
			if (resource == null) 
				throw new ChainedRuntimeException("El resource de configuración de la aplicación '" + resource + "' no pudo ser encontrado por el classloader '" + classLoader + "'");

			InputStream is = classLoader.getResourceAsStream(resource);

			if (is == null) 
				throw new ChainedRuntimeException("El resource de configuración de la aplicación '" + resource + "' no pudo ser encontrado por el classloader '" + classLoader + "'");

			// then add (overwrite) with the given configuration properties
			properties.load(is);
		} 
		catch (IOException e) 
		{
			throw new ChainedRuntimeException("El resource de configuración de la aplicación '" + resource + "' cargado por el classloader '" + classLoader + "' tiene un formato inválido");
		}

		return properties;
	}

	/**
	 * provee acceso a todas las propiedades de configuración.
	 */
	public final Properties getProperties() 
	{
		return properties;
	}

	public abstract Properties getDefaultProperties();

	public abstract Map getAbbreviatedClassNames();

	/**
	 * chequea si una clave está presente.
	 */
	public final boolean containsKey(String key) 
	{
		return properties.containsKey(key);
	}

	/**
	 * obtiene una propiedad de configuración como string.
	 */
	public final String getString(String key) 
	{
		return properties.getProperty(key);
	}

	/**
	 * obtiene una propiedad de configuración como booleano. valores posibles
	 * son:
	 * <ul>
	 * <li>{true|yes|enable|on} ==> true</li>
	 * <li>{false|no|disable|off} ==> false</li>
	 * <li>ninguno de los anteriores ==> throws Exception</li>
	 * </ul>
	 * 
	 * @throws Exception
	 *             si el valor dado opr la clave no hace match con ninguno de
	 *             los valores esperados.
	 */
	public final boolean getBoolean(String key) 
	{
		return getBoolean(properties, key);
	}

	public static boolean getBoolean(Properties properties, String key) 
	{
		boolean result = false;

		String booleanText = properties.getProperty(key);
		if (booleanText == null) 
			throw new ChainedRuntimeException("clave '" + key + "' no está configurada o no tiene un valor {true|yes|enable|on|false|no|disable|off}");

		if (("true".equalsIgnoreCase(booleanText)) || ("yes".equalsIgnoreCase(booleanText)) || ("enable".equalsIgnoreCase(booleanText)) || ("on".equalsIgnoreCase(booleanText))) {
			result = true;

		} 
		else if ((!"false".equalsIgnoreCase(booleanText)) && (!"no".equalsIgnoreCase(booleanText)) && (!"disable".equalsIgnoreCase(booleanText)) && (!"off".equalsIgnoreCase(booleanText))) 
			throw new ChainedRuntimeException("clave '" + key + "' tiene un valor inválido de '" + booleanText + "', los valores permitidos son {true|yes|enable|on|false|no|disable|off}");

		return result;
	}

	/**
	 * obtiene una propiedad de configuración tipo long.
	 */
	public final long getLong(String key) 
	{
		return getLong(properties, key);
	}

	public static long getLong(Properties properties, String key) 
	{
		long value = 0;

		String valueText = properties.getProperty(key);
		if (valueText != null) 
		{
			try 
			{
				value = Long.parseLong(valueText);
			} 
			catch (NumberFormatException e) 
			{
				throw new ChainedRuntimeException("propiedad de configuración '" + key + "' no es parseable a un long: '" + valueText + "'");
			}
		}

		return value;
	}

	protected String getClassName(String key) 
	{
		String className = null;

		String value = properties.getProperty(key);
		Map propertyClassNames = (Map) abbreviatedClassNames.get(key);
		if ((propertyClassNames != null) && (propertyClassNames.containsKey(value))) {
			className = (String) propertyClassNames.get(value);
		} else {
			className = value;
		}

		return className;
	}

	/**
	 * instantiates an implementation of which the class-name is configured in
	 * the configuration. First, this method searches for the constructor that
	 * takes a Configuration as a parameter. If that is found, the configured
	 * properties are provided in the constructor. Otherwise the default
	 * constructor is used. The classloader of this class is used to load the
	 * class. Instantiated objects are cached.
	 * 
	 * @param key
	 *            is the key under which the class-name can be looked up in the
	 *            configuration.
	 * @throws ConfigurationException
	 *             if for some reason or another, the instantiator cannot
	 *             produce an object of the expectedClass.
	 */
	public final Object instantiate(String key, Class expectedClass) 
	{
		Object newInstance = null;

		/** Primero se chequea si el objeto solicitado está en el cache */
		String className = getClassName(key);

		if (className == null) 
			throw new ChainedRuntimeException("No hay un class-name configurado para la clave '" + key + "'");

		try 
		{
			Class clase = FrameworkConfiguration.class.getClassLoader().loadClass(className);
			Constructor constructor = null;
			try 
			{
				constructor = clase.getConstructor(new Class[] { FrameworkConfiguration.class });
				newInstance = constructor.newInstance(new Object[] { this });
			} 
			catch (NoSuchMethodException e) 
			{
				/** Como alternativa se utiliza el constructor por defecto */
				constructor = clase.getConstructor((Class[]) null);
				newInstance = constructor.newInstance((Object[]) null);
			}

		} 
		catch (NoSuchMethodException e) 
		{
			throw new ChainedRuntimeException("No pudo instanciarse la clase " + className + " (como está configurada en la clave " + key + ") : La clase no tiene un constructor apropiado: default-constructor o un constructor que toma un Properties como argumento", e);
		} 
		catch (NullPointerException e) 
		{
			throw new ChainedRuntimeException("No hay clase especificada para la clave " + key + " (se esperaba el classname de una implementación de " + expectedClass.getName() + ")", e);
		} 
		catch (ClassNotFoundException e) 
		{
			throw new ChainedRuntimeException("No pudo encontrarse " + className + " (como está configurada en la clave " + key + ")", e);
		} 
		catch (Throwable t) 
		{
			throw new ChainedRuntimeException("No pudo instanciarse la clase " + className + " (como está configurada en la clave " + key + ") : véase el stacktrace para más detalles", t);
		}

		/** Se chequea si la clase instanciada es del tipo esperado */
		if (!expectedClass.isInstance(newInstance)) 
			throw new ChainedRuntimeException("La clase configurada in la clave " + key + " (" + className + ") no es castable a " + expectedClass.getName());

		return newInstance;
	}

	/**
	 * muestra cuales configuraciones están siendo usadas para detectar
	 * problemas
	 */
	protected void logConfigurations() 
	{
		Logger log = LoggerFactory.getLogger(VgcConfiguration.class);
		if (log.isDebugEnabled()) 
		{
			log.debug("Configuración de la Aplicación: ");
			Map sortedProps = new TreeMap(properties);
			for (Iterator iter = sortedProps.entrySet().iterator(); iter.hasNext();) 
			{
				Map.Entry entry = (Map.Entry) iter.next();
				log.debug("  [" + entry.getKey() + "] " + entry.getValue());
			}
		}
	}
}
