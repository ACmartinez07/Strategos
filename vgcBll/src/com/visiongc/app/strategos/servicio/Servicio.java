package com.visiongc.app.strategos.servicio;

import com.visiongc.commons.util.Password;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.servicio.strategos.calculos.CalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;









public class Servicio
{
  private static Properties defaultProps;
  private List<String> keys = new ArrayList();
  
  static
  {
    try
    {
      defaultProps = new Properties();
    }
    catch (Exception localException) {}finally {}
  }
  



  public Servicio() {}
  


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
    
    int k = 0;
    
    for (Iterator<?> iter = keys.iterator(); iter.hasNext();)
    {
      String key = (String)iter.next();
      configuracion[k][0] = key;
      configuracion[k][1] = defaultProps.getProperty(key, "");
      k++;
    }
    
    return configuracion;
  }
  
  public int calcular(byte tipo, List<Object> indicadores, Usuario usuario) throws Exception
  {
    int respuesta = 10000;
    
    if (tipo == 1)
    {
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
      Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
      frameworkService.close();
      
      if (configuracion != null)
      {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("properties");
        Element eElement = (Element)nList.item(0);
        
        String url = getTagValue("url", eElement);
        String driver = getTagValue("driver", eElement);
        String user = getTagValue("user", eElement);
        String password = getTagValue("password", eElement);
        password = Password.decriptPassWord(password);
        
        if (new FrameworkConnection().testConnection(url, driver, user, password))
        {
          setProperty("url", url);
          setProperty("driver", driver);
          setProperty("user", user);
          setProperty("password", password);
          
          setProperty("logMediciones", Boolean.valueOf(false).toString());
          setProperty("logErrores", Boolean.valueOf(false).toString());
          setProperty("tomarPeriodosSinMedicionConValorCero", Boolean.valueOf(false).toString());
          setProperty("planId", "");
          setProperty("organizacionId", "");
          setProperty("perspectivaId", "");
          setProperty("claseId", "");
          
          setProperty("ano", "");
          setProperty("mesInicial", "");
          setProperty("mesFinal", "");
          setProperty("arbolCompletoOrganizacion", Boolean.valueOf(false).toString());
          setProperty("todasOrganizaciones", Boolean.valueOf(false).toString());
          setProperty("indicadorId", "");
          setProperty("porNombre", "");
          setProperty("nombreIndicador", "");
          
          setProperty("logConsolaMetodos", Boolean.valueOf(false).toString());
          setProperty("logConsolaDetallado", Boolean.valueOf(false).toString());
          setProperty("usuarioId", usuario.getUsuarioId().toString());
          
          setProperty("activarSheduler", Boolean.valueOf(false).toString());
          setProperty("unidadTiempo", Integer.valueOf(0).toString());
          setProperty("numeroEjecucion", Integer.valueOf(0).toString());
          
          StringBuffer log = new StringBuffer();
          
          respuesta = new CalcularManager(Get(), log, VgcMessageResources.getVgcMessageResources("StrategosWeb")).Ejecutar(indicadores, tipo);
        }
      }
    }
    
    return respuesta;
  }
  
  public Byte calcularAlertaXPeriodos(Byte tipo, Byte caracteristica, Double zonaVerde, Double zonaAmarilla, Double ejecutado, Double meta, Double metaInferior, Double ejecutadoAnterior)
  {
    Byte alerta = null;
    
    if (tipo.byteValue() == 2) {
      alerta = new CalcularManager().Ejecutar(caracteristica, zonaVerde, zonaAmarilla, ejecutado, meta, metaInferior, ejecutadoAnterior);
    }
    return alerta;
  }
  
  public static String getTagValue(String sTag, Element eElement)
  {
    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
    Node nValue = nlList.item(0);
    
    if (nValue != null) {
      return nValue.getNodeValue();
    }
    return "";
  }
  
  public static class EjecutarTipo {
    private static final byte EJECUCIONALERTA = 1;
    private static final byte EJECUCIONALERTAXPERIODO = 2;
    
    public EjecutarTipo() {}
    
    public static Byte getEjecucionAlerta() {
      return new Byte((byte)1);
    }
    
    public static Byte getEjecucionAlertaXPeriodos()
    {
      return new Byte((byte)2);
    }
  }
}
