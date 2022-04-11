/**
 * 
 */
package com.visiongc.app.strategos.web.struts.servicio.forms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * @author Kerwin
 *
 */
public class ServicioForm 
{
	private static Properties defaultProps;
	private List<String> keys = new ArrayList<String>();

	static
	{
		try
	    {
			defaultProps = new Properties();
	    }
	    catch (Exception localException)
	    {
	    	try { } catch (Exception localException1) { } 
	    } 
	    finally 
	    { 
	    	try { } catch (Exception localException2) { }
	    }
	}
	
	public String getProperty(String key, String def)
	{
		return defaultProps.getProperty(key, def);
	}
	
	public void setProperty(String key, String def)
	{
		defaultProps.put(key, def);
		keys.add(key);
	}

	public String[][] Get()
	{
		String[][] configuracion = new String[keys.size()][2];
		String key;
		int k = 0;

		for (Iterator<?> iter = keys.iterator(); iter.hasNext(); )
		{
			key = (String)iter.next();
			configuracion[k][0] = key;
			configuracion[k][1] = defaultProps.getProperty(key, "");
			k ++;
		}

		return configuracion;
	}
}
