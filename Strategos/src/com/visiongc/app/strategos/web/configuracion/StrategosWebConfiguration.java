package com.visiongc.app.strategos.web.configuracion;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSessionFactory;
import com.visiongc.commons.VgcConfiguration;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class StrategosWebConfiguration extends VgcConfiguration
{
	public Map<String, Map<String, String>> getAbbreviatedClassNames()
	{
		Map<String, Map<String, String>> abbreviatedClassNames = new HashMap<String, Map<String, String>>();
		Map<String, String> propertyClassNames = new HashMap<String, String>();

		propertyClassNames.put("hibernateStrategos", "com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSessionFactory");
		abbreviatedClassNames.put("com.visiongc.app.strategos.persistence.session.factory", propertyClassNames);

		return abbreviatedClassNames;
	}

	public Properties getDefaultProperties()
	{
		Properties properties = new Properties();
		
		properties.put("com.visiongc.app.strategos.persistence.session.factory", "hibernateStrategos");

		return properties;
	}

	public StrategosWebConfiguration() throws ChainedRuntimeException
	{
		super(StrategosWebConfiguration.class);
	}

	public static StrategosWebConfiguration getInstance()
	{
		StrategosWebConfiguration conf = (StrategosWebConfiguration)VgcConfiguration.getConfiguration(StrategosWebConfiguration.class);

		if (conf == null)
		{
			try
			{
				new StrategosWebConfiguration();
				conf = (StrategosWebConfiguration)VgcConfiguration.getConfiguration(StrategosWebConfiguration.class);
			}
			catch (Exception e)
			{
				throw new ChainedRuntimeException("La aplicación no está¡ configurada correctamente.", e);
			}
		}

		return conf;
	}

	public StrategosPersistenceSessionFactory getStrategosPersistenceSessionFactory()
	{
		return (StrategosPersistenceSessionFactory)instantiate("com.visiongc.app.strategos.persistence.session.factory", StrategosPersistenceSessionFactory.class);
	}
}