package com.visiongc.commons.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.VgcService;
import com.visiongc.commons.mail.VgcSMTPAuthenticator;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.configuracion.VgcConfiguraciones;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Enviroment;
import com.visiongc.framework.model.Licencia;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.Sistema.SistemaTipo;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.util.FrameworkConnection;

/**
 * Clase abstracta que implementa la interface VgcService y que deben extender
 * todas las implementaciones de Servicios de Negocios
 * 
 *  (27/01/2012)
 * 
 * @author Kerwin Arias
 * @version 1.0.1 -
 */
public abstract class VgcAbstractService implements VgcService 
{
	/** Instancia de Sesión de Persistencia */
	protected VgcPersistenceSession persistenceSession = null;
	protected VgcServiceFactory serviceFactory = null;
	protected VgcMessageResources messageResources = null;
	protected String archivoConfiguracion = "vision.sln";
	protected String folderConfiguracion = "conf";

	private boolean persistenceOwned = false;

	protected VgcAbstractService(VgcPersistenceSession inPersistenceSession, boolean persistenceOwned, VgcServiceFactory serviceFactory, VgcMessageResources messageResources) 
	{
		this.persistenceSession = inPersistenceSession;
		this.persistenceOwned = persistenceOwned;
		this.serviceFactory = serviceFactory;
		this.messageResources = messageResources;
	}

	public Object load(Class<?> clase, Long objetoId) 
	{
		return this.persistenceSession.load(clase, objetoId);
	}

	public Object load(Class<?> clase, Serializable objetoId) 
	{
		return this.persistenceSession.load(clase, objetoId);
	}

	public boolean lockForInsert(String instancia, Object idRelacionados[]) 
	{
		return this.persistenceSession.lockForInsert(instancia, idRelacionados);
	}

	public boolean lockForUse(String instancia, Object idsToUse[]) 
	{
		return this.persistenceSession.lockForUse(instancia, idsToUse);
	}

	public boolean lockForUse(String instancia, Object objetoId) 
	{
		Object[] idsToUse = new Object[1];
		idsToUse[0] = objetoId;
		return this.persistenceSession.lockForUse(instancia, idsToUse);
	}

	/**
	 * Creado por: Kerwin Arias (26/06/2012)
	 */
	public boolean lockForUse(String instancia, Object actualObjetoId, Object nuevoObjetoId) 
	{
		if (actualObjetoId != null) 
		{
			this.persistenceSession.unlockObject(instancia, actualObjetoId);
		}
		
		Object[] idsToUse = new Object[1];
		idsToUse[0] = nuevoObjetoId;
		return this.persistenceSession.lockForUse(instancia, idsToUse);
	}

	/**
	 *  (03/07/2012)
	 */
	public Object loadAndLockForUse(String instancia, Class<?> claseObjeto, String nombreCampoClave, Long objetoId) throws Exception 
	{
		Object objeto = null;
		Object[] idsToUse = new Object[1];
		idsToUse[0] = objetoId;
		if (this.persistenceSession.lockForUse(instancia, idsToUse)) 
		{
			objeto = this.persistenceSession.reload(claseObjeto, nombreCampoClave, objetoId);
			if (objeto == null) 
				this.persistenceSession.unlockObject(instancia, objetoId);
		}

		return objeto;
	}

	public boolean lockForUpdate(String instancia, Object objetoId) 
	{
		return this.persistenceSession.lockForUpdate(instancia, objetoId, null);
	}

	public boolean lockForUpdate(String instancia, Object objetoId, Object idRelacionados[]) 
	{
		return this.persistenceSession.lockForUpdate(instancia, objetoId, idRelacionados);
	}

	public boolean lockForDelete(String instancia, Object objetoId) 
	{
		return this.persistenceSession.lockForDelete(instancia, objetoId);
	}

	public boolean unlockObject(String instancia) 
	{
		return this.persistenceSession.unlockObject(instancia, null);
	}

	public boolean unlockObject(String instancia, Object objetoId) 
	{
		return this.persistenceSession.unlockObject(instancia, objetoId);
	}
	
	public int enviarCorreo(String to, String subject, String body, String adjunto, String file)
	{
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();

		String host = frameworkService.getConfiguracion("mail.smtp.host").getValor();
		String user = frameworkService.getConfiguracion("mail.smtp.user").getValor();
		String puerto = frameworkService.getConfiguracion("mail.smtp.port").getValor();
		String password =  TextEncoder.uRLDecode(frameworkService.getConfiguracion("mail.smtp.password").getValor());
		
		frameworkService.close();

		if (to == null) 
			return VgcReturnCode.MAIL_SEND_NOT_TO;
		if (host.equals("")) 
			return VgcReturnCode.MAIL_NOT_CONFIGURED;
		if (user.equals("")) 
			return VgcReturnCode.MAIL_NOT_CONFIGURED;
		if (puerto.equals("")) 
			return VgcReturnCode.MAIL_NOT_CONFIGURED;
		if (password.equals("")) 
			return VgcReturnCode.MAIL_NOT_CONFIGURED;

		password = Password.desencripta(password);
		
		try
		{
			Properties properties = new Properties();
			
	        properties.put("mail.smtp.user", user);
			properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", puerto);
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.debug", "true");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.socketFactory.port", puerto);
	        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        properties.put("mail.smtp.socketFactory.fallback", "false");	        
	        properties.put("mail.smtp.mail.sender", user);
	        properties.put("mail.smtp.password", password);
	        
	        Session session = Session.getDefaultInstance(properties);
	        session.setDebug(true);
	        
	        MimeMessage message = new MimeMessage(session);
	        
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(body, "text/plain");

	    	if (adjunto != null && !adjunto.equals("") && file != null && !file.equals(""))
	    	{
	            FileDataSource source = new FileDataSource(adjunto);
	            message.setDataHandler(new DataHandler(source));
	            message.setFileName(file);            
	    	}
            
            Transport transport = session.getTransport("smtp");
            if (!puerto.equals(""))
            	transport.connect((String) properties.get("mail.smtp.host"), Integer.parseInt((String) properties.get("mail.smtp.port")), (String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
            else
            	transport.connect((String) properties.get("mail.smtp.host"), (String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();              
		}
		catch (Throwable t) 
		{
			if (SendFailedException.class.isInstance(t)) 
			{
				SendFailedException excepcionEnvio = (SendFailedException) t;
				if (AuthenticationFailedException.class.isInstance(excepcionEnvio.getNextException())) 
					return VgcReturnCode.MAIL_AUTHENTICATION_FAILED;

				return VgcReturnCode.MAIL_SEND_FAILED;
			} 
			else if (AuthenticationFailedException.class.isInstance(t)) 
			{
				return VgcReturnCode.MAIL_AUTHENTICATION_FAILED;
			} 
			else 
			{
				throw new ChainedRuntimeException(t.getMessage(), t);
			}
		}

		return VgcReturnCode.MAIL_SEND_OK;
	}

	public int sendMail(String from, String to, String cc, String bcc, String subject, String body) 
	{
		try 
		{
			Properties propiedades = System.getProperties();

			propiedades.put("mail.transport.protocol", "smtp");
			String host = VgcConfiguraciones.getInstance().getValor("mail.smtp.host", "urlServidorCorreoSmtp", null);
			if (host.equals("urlServidorCorreoSmtp")) 
				return VgcReturnCode.MAIL_NOT_CONFIGURED;

			propiedades.put("mail.smtp.host", host);

			Authenticator auth = null;

			if (!VgcSMTPAuthenticator.getUser().equals(VgcSMTPAuthenticator.DEFAULT_USER)) 
			{
				auth = new VgcSMTPAuthenticator();
				propiedades.put("mail.smtp.auth", "true");
			}

			Session sesionMail = Session.getInstance(propiedades, auth);

			Message message = new MimeMessage(sesionMail);

			if (from == null) 
				return VgcReturnCode.MAIL_SEND_NOT_FROM;

			InternetAddress fromAddress = new InternetAddress(from);
			message.setFrom(fromAddress);

			if (to == null) 
				return VgcReturnCode.MAIL_SEND_NOT_TO;

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			if (cc != null) 
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));

			if (bcc != null) 
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));

			message.setSubject(subject);
			message.setContent(body, "text/plain");

			Transport.send(message);
		} 
		catch (Throwable t) 
		{
			if (SendFailedException.class.isInstance(t)) 
			{
				SendFailedException excepcionEnvio = (SendFailedException) t;
				if (AuthenticationFailedException.class.isInstance(excepcionEnvio.getNextException())) 
					return VgcReturnCode.MAIL_AUTHENTICATION_FAILED;

				return VgcReturnCode.MAIL_SEND_FAILED;
			} 
			else if (AuthenticationFailedException.class.isInstance(t)) 
			{
				return VgcReturnCode.MAIL_AUTHENTICATION_FAILED;
			} 
			else 
			{
				throw new ChainedRuntimeException(t.getMessage(), t);
			}
		}

		return VgcReturnCode.MAIL_SEND_OK;
	}

	public VgcPersistenceSession getPersistenceSession() 
	{
		return persistenceSession;
	}

	public VgcPersistenceSession getVgcPersistenceSession() 
	{
		return persistenceSession;
	}

	public VgcServiceFactory getVgcServiceFactory() 
	{
		return serviceFactory;
	}

	public boolean getPersistenceOwned() 
	{
		return this.persistenceOwned;
	}

	public void close() 
	{
		serviceFactory.closeService(this);
	}

	protected void finalize() 
	{
		this.close();
	}

	public boolean saveSoloLectura(Object objeto, boolean soloLectura, String[] fieldNames, Object[] fieldValues) 
	{
		return this.persistenceSession.saveSoloLectura(objeto, soloLectura, fieldNames, fieldValues);
	}
	
	private String claveMaquina(String macAplicacion, String macServidorBd, String algoritmo)
	{
		String codigoMaquinaActual = "";
		
	    if (algoritmo.equals("1"))
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.MD2);
	    else if (algoritmo.equals("2"))
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.MD5);
	    else if (algoritmo.equals("3"))
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.SHA1);
	    else if (algoritmo.equals("4"))
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.SHA256);
	    else if (algoritmo.equals("5"))
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.SHA384);
	    else if (algoritmo.equals("6"))
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.SHA512);
	    else 
	    	codigoMaquinaActual = Password.getStringMessageDigest((macAplicacion + macServidorBd), Password.SHA512);
		
		return codigoMaquinaActual;
	}
	
	public int setProducto(Sistema sistema, HttpServletRequest request) throws Exception
	{
		String separador = ",";
		int respuesta = VgcReturnCode.LICENCIA_SAVE_OK;

		Licencia licencia = new Licencia();
	    
	    String clave = "";
	    String[] campos = null;
		String codigoMaquina = "";
	    String fechaInstalacion = "";
	    String numeroUsuarios = "";
	    String numeroIndicadores = "";
	    String diasVencimiento = "";
	    String tipoLicencia = "";
	    String algoritmo = "";
	    String serial = "";
	    String fechaClave = "";
	    String chequearLicencia = "";
	    String licenciaVencida = "0";
	    String companyName = "";
	    String productoId = "";
	    String fileCheck = "";
	    
		if (sistema.getCmaxc() != null)
		{
		    try
		    {
				clave = Password.decriptPassWord(sistema.getCmaxc());
			    campos = clave.split(separador);

				codigoMaquina = campos[0];
			    fechaInstalacion = campos[1];
			    numeroUsuarios = campos[2];
			    numeroIndicadores = campos[3];
			    diasVencimiento = campos[4];
			    tipoLicencia = campos[5];
			    algoritmo = campos[6];
			    fechaClave = campos[7];
			    if (campos.length >= 11)
			    {
			    	chequearLicencia = campos[8];
				    licenciaVencida = campos[9];
				    serial = campos[10];
			    }
			    else
			    {
			    	if (!chequearLicencia.equals("0"))
			    		chequearLicencia = "1";
				    licenciaVencida = campos[8];
				    serial = campos[9];
			    }
			    if (campos.length >= 12 && !campos[11].equals("")) 
			    	companyName = campos[11];
			    else
			    {
			    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			    	Organizacion organizcion = frameworkService.getOrganizacionRoot(false);
			    	frameworkService.close();
			    	
			    	if (organizcion != null)
			    		companyName = organizcion.getNombre();
			    	else
			    		companyName = "Demo";
			    }
			    if (campos.length >= 13 && !campos[12].equals(""))
			    	productoId = campos[12];
			    else
			    	productoId = "UA-57790239-1";
			    if (campos.length >= 14 && !campos[13].equals(""))
			    	fileCheck = campos[13];
			    else
			    	fileCheck = "0";

			    clave = codigoMaquina + separador;
			    clave = clave + fechaInstalacion + separador;
			    clave = clave + numeroUsuarios + separador;
			    clave = clave + numeroIndicadores + separador;
			    clave = clave + diasVencimiento + separador;
			    clave = clave + tipoLicencia + separador;
			    clave = clave + algoritmo + separador;
			    clave = clave + fechaClave + separador;
			    clave = clave + chequearLicencia + separador;
			    clave = clave + licenciaVencida + separador;
			    clave = clave + serial + separador;
			    clave = clave + companyName + separador;
			    clave = clave + productoId + separador;
			    clave = clave + fileCheck;
			    
			    sistema.setCmaxc(Password.encriptPassWord(clave));

			    licencia = new Licencia();
			    licencia.setTipo(tipoLicencia);
					    
			    respuesta = setLicencia(request, sistema.getCmaxc(), licencia.getTipo(), null);
		    }
		    catch (Exception e)
		    {
		    	respuesta = VgcReturnCode.LICENCIA_SAVE_ERROR;
		    	System.out.println("Error salvando el archivo de lincencia");
		    	e.printStackTrace();
		    }
	    }
		
		return respuesta;
	}
	
	public Licencia readProducto(Sistema sistema) throws Exception
	{
		Licencia licencia = new Licencia();
	    
		String separador = ",";
		int respuesta = VgcReturnCode.LICENCIA_SAVE_OK; 
				
	    String clave = "";
	    String[] campos = null;
		String codigoMaquina = "";
	    String fechaInstalacion = "";
	    String numeroUsuarios = "";
	    String numeroIndicadores = "";
	    String diasVencimiento = "";
	    String tipoLicencia = "";
	    String algoritmo = "";
	    String serial = "";
	    String fechaClave = "";
	    String licenciaVencida = "0";
	    String expiracionDias = null;
	    String chequearLicencia = "";
	    boolean maquinaDiferentes = false;
	    String companyName = "";
	    String productoId = "";
	    String fileCheck = "";
	    
		if (respuesta == VgcReturnCode.LICENCIA_SAVE_OK && sistema.getCmaxc() != null)
		{
		    clave = Password.decriptPassWord(sistema.getCmaxc());
		    campos = clave.split(separador);

			codigoMaquina = campos[0];
		    fechaInstalacion = campos[1];
		    numeroUsuarios = campos[2];
		    numeroIndicadores = campos[3];
		    diasVencimiento = campos[4];
		    tipoLicencia = campos[5];
		    algoritmo = campos[6];
		    fechaClave = campos[7];
		    if (campos.length >= 11)
		    {
		    	chequearLicencia = campos[8];
			    licenciaVencida = campos[9];
			    serial = campos[10];
		    }
		    else
		    {
		    	if (!chequearLicencia.equals("0"))
		    		chequearLicencia = "1";
			    licenciaVencida = campos[8];
			    serial = campos[9];
		    }
		    if (campos.length >= 12 && !campos[11].equals("")) 
		    	companyName = campos[11];
		    else
		    {
		    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		    	Organizacion organizcion = frameworkService.getOrganizacionRoot(false);
		    	frameworkService.close();
		    	
		    	if (organizcion != null)
		    		companyName = organizcion.getNombre();
		    	else
		    		companyName = "Demo";
		    }
		    if (campos.length >= 13 && !campos[12].equals(""))
		    	productoId = campos[12];
		    else
		    	productoId = "UA-57790239-1";
		    if (campos.length >= 14 && !campos[13].equals(""))
		    	fileCheck = campos[13];
		    else
		    	fileCheck = "0";
			
			String expiracion = null;
			String macAplicacionActual = getMACApplication(null);
			String macServidorBdActual = getMACApplication(obtenerServidorPuerto()[0]);
			if (chequearLicencia.equals("0"))
				tipoLicencia = SistemaTipo.getSistemaTipoDemo().toString();
		
			String codigoMaquinaActual = claveMaquina(macAplicacionActual, macServidorBdActual, algoritmo);
			if (licenciaVencida.equals("1") && !chequearLicencia.equals("0"))
				expiracion = null;
			else if (!codigoMaquina.equals(codigoMaquinaActual) && !chequearLicencia.equals("0"))
			{
				expiracion = null;
				maquinaDiferentes = true;
			}
			else if (tipoLicencia.equals(SistemaTipo.getSistemaTipoDemo().toString()) && !chequearLicencia.equals("0"))
			{
	      		Date fechaVencimiento = FechaUtil.convertirStringToDate(fechaInstalacion, VgcResourceManager.getResourceApp("formato.fecha.corta")); 
				Calendar c = Calendar.getInstance();
	      		c.setTime(fechaVencimiento);
	      		
	      		Calendar fecha = Calendar.getInstance();
		        int day = c.get(5);
		        int month = c.get(2);
		        int year = c.get(1);

		        fecha.set(5, day);
		        fecha.set(2, month);
		        fecha.set(1, year);

		        fecha.add(5, Integer.parseInt(diasVencimiento));
		        fechaVencimiento.setTime(fecha.getTimeInMillis());
		        Date fechaActual = new Date();
				if (fechaVencimiento.getTime() < fechaActual.getTime())
					expiracion = null;
				else
				{
					expiracion = VgcFormatter.formatearFecha(fecha.getTime(), "dd/MM/yyyy");
					
					//Calcular la diferencia en Dias
					Calendar fActual = Calendar.getInstance();
					fActual.setTime(fechaActual);
					
					// conseguir la representacion de la fecha en milisegundos
			        Long milis1 = fActual.getTimeInMillis();
			        Long milis2 = fecha.getTimeInMillis();
			        Long diff = milis2 - milis1;
			        Long diffDays = diff / (24 * 60 * 60 * 1000);
					
					expiracionDias = diffDays.toString();
				}
			}
		    
			Integer fueraLimiteUsuarios = Integer.parseInt(numeroUsuarios);
			if (!tipoLicencia.equals(SistemaTipo.getSistemaTipoFull().toString()))
			{
				UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
				Map<String, Object> filtros = new HashMap<String, Object>();
				filtros.put("isConnected", true);
	
				PaginaLista paginaUsuarios = usuariosService.getUsuarios(0, "fullName", "ASC", false, filtros);
				if (paginaUsuarios.getLista() != null && paginaUsuarios.getLista().size() > 0)
				{
					Integer usuariosConectados = paginaUsuarios.getLista().size();
					if (usuariosConectados > Integer.parseInt(numeroUsuarios))
						fueraLimiteUsuarios = null;
				}
	
				usuariosService.close();
			}
			
			licencia = new Licencia();
			licencia.setExpiracion(expiracion);
		    licencia.setSerial(serial);
		    licencia.setTipo(tipoLicencia);
		    licencia.setNumeroIndicadores(Integer.parseInt(numeroIndicadores));
		    licencia.setNumeroUsuarios(fueraLimiteUsuarios);
		    licencia.setCmaxc(sistema.getCmaxc());
		    licencia.setCodigoMaquina(codigoMaquina);
		    licencia.setFechaInstalacion(fechaInstalacion);
		    licencia.setDiasVencimiento(diasVencimiento);
		    licencia.setAlgoritmo(algoritmo);
		    licencia.setFechaLicencia(fechaClave);
		    licencia.setExpiracionDias(expiracionDias);
		    licencia.setInstalacionMaquinaDiferentes(maquinaDiferentes);
		    licencia.setChequearLicencia((chequearLicencia.equals("0") ? false : true));
		    licencia.setCompanyName(companyName);
		    licencia.setProductoId(productoId);
		    licencia.setFileCheck((fileCheck.equals("1") ? true: false));
		    
		    if (expiracion == null && licenciaVencida.equals("1") && (tipoLicencia.equals(SistemaTipo.getSistemaTipoDemo().toString()) || tipoLicencia.equals(SistemaTipo.getSistemaTipoPrototipo().toString())))
		    {
		    	licenciaVencida = "1";

			    clave = codigoMaquina + separador;
			    clave = clave + fechaInstalacion + separador;
			    clave = clave + numeroUsuarios + separador;
			    clave = clave + numeroIndicadores + separador;
			    clave = clave + diasVencimiento + separador;
			    clave = clave + tipoLicencia + separador;
			    clave = clave + algoritmo + separador;
			    clave = clave + fechaClave + separador;
			    clave = clave + chequearLicencia + separador;
			    clave = clave + licenciaVencida + separador;
			    clave = clave + serial + separador;
			    clave = clave + companyName + separador;
			    clave = clave + productoId + separador;
			    clave = clave + fileCheck;
			    
			    sistema.setCmaxc(Password.encriptPassWord(clave));

			    licencia = new Licencia();
			    licencia.setTipo(tipoLicencia);
		    }
		}
		else
		{
			licencia = new Licencia();
			licencia.setExpiracion(null);
			licencia.setRespuesta(respuesta);
			licencia.setTipo(SistemaTipo.getSistemaTipoName(SistemaTipo.getSistemaTipoDemo()));
		}
		
		return licencia;
	}
	
	public Licencia getCheckProducto(Sistema sistema, HttpServletRequest request) throws Exception
	{
		Licencia licencia = new Licencia();
	    
		String separador = ",";
		String licenciaArchivo = getLicencia(request);
		String[] camposArchivo = null;
		int respuesta = VgcReturnCode.LICENCIA_SAVE_OK; 
				
		if (licenciaArchivo != null && !licenciaArchivo.equals(""))
		{
			String claveArchivo = Password.decriptPassWord(licenciaArchivo);
			camposArchivo = claveArchivo.split(separador);
		}
	    
	    String clave = "";
	    String[] campos = null;
		String codigoMaquina = "";
	    String fechaInstalacion = "";
	    String numeroUsuarios = "";
	    String numeroIndicadores = "";
	    String diasVencimiento = "";
	    String tipoLicencia = "";
	    String algoritmo = "";
	    String serial = "";
	    String fechaClave = "";
	    String licenciaVencida = "0";
	    String expiracionDias = null;
	    String chequearLicencia = "";
	    boolean maquinaDiferentes = false;
	    String companyName = "";
	    String productoId = "";
	    String fileCheck = "";
	    
		if (sistema.getCmaxc() == null && sistema.getProducto().equals("PROTOTIPO"))
		{
			if (licenciaArchivo != null && !licenciaArchivo.equals(""))
			{
				codigoMaquina = camposArchivo[0];
			    fechaInstalacion = camposArchivo[1];
			    numeroUsuarios = camposArchivo[2];
			    numeroIndicadores = camposArchivo[3];
			    diasVencimiento = camposArchivo[4];
			    tipoLicencia = camposArchivo[5];
			    algoritmo = camposArchivo[6];
			    fechaClave = camposArchivo[7];
			    if (camposArchivo.length >= 11)
			    {
			    	chequearLicencia = camposArchivo[8];
				    licenciaVencida = camposArchivo[9];
			    	serial = camposArchivo[10];
			    }
			    else
			    {
			    	chequearLicencia = "1";
				    licenciaVencida = camposArchivo[8];
			    	serial = camposArchivo[9];
			    }
			    if (camposArchivo.length >= 12 && !camposArchivo[11].equals("")) 
			    	companyName = camposArchivo[11];
			    else
			    {
			    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			    	Organizacion organizcion = frameworkService.getOrganizacionRoot(false);
			    	frameworkService.close();
			    	
			    	if (organizcion != null)
			    		companyName = organizcion.getNombre();
			    	else
			    		companyName = "Demo";
			    }
			    if (camposArchivo.length >= 13 && !camposArchivo[12].equals(""))
			    	productoId = camposArchivo[12];
			    else
			    	productoId = "UA-57790239-1";
			    if (camposArchivo.length >= 14 && !camposArchivo[13].equals(""))
			    	fileCheck = camposArchivo[13];
			    else
			    	fileCheck = "0";
			    
			    if (fechaInstalacion.equals("00/00/0000"))
			    	fechaInstalacion = VgcFormatter.formatearFecha(Calendar.getInstance().getTime(), "dd/MM/yyyy");
			    
				String macAplicacion = "00-00-00-00";
				String macServidorBd = "00-00-00-00";
			    if (codigoMaquina.equals(claveMaquina(macAplicacion, macServidorBd, algoritmo)))
			    {
			    	//Se generar una nueva clave unica para este cliente
			    	Integer tipoAlgorigmo = (int) ((Math.random() * 6) + 1);
			    	algoritmo = tipoAlgorigmo.toString();
			    	
					macAplicacion = getMACApplication(null);
					if (!macAplicacion.equals("SIN-MAC-address"))
					{
						macServidorBd = getMACApplication(obtenerServidorPuerto()[0]);
				    	codigoMaquina = claveMaquina(macAplicacion, macServidorBd, algoritmo);
					}
					else if (chequearLicencia.equals("1"))
					{
						macServidorBd = getMACApplication(obtenerServidorPuerto()[0]);
				    	codigoMaquina = claveMaquina(macAplicacion, macServidorBd, algoritmo);
					}
			    }
			    
			    clave = codigoMaquina + separador;
			    clave = clave + fechaInstalacion + separador;
			    clave = clave + numeroUsuarios + separador;
			    clave = clave + numeroIndicadores + separador;
			    clave = clave + diasVencimiento + separador;
			    clave = clave + tipoLicencia + separador;
			    clave = clave + algoritmo + separador;
			    clave = clave + fechaClave + separador;
			    clave = clave + chequearLicencia + separador;
			    clave = clave + licenciaVencida + separador;
			    clave = clave + serial + separador;
			    clave = clave + companyName + separador;
			    clave = clave + productoId + separador;
			    clave = clave + fileCheck;
			    
			    sistema.setCmaxc(Password.encriptPassWord(clave));

			    licencia = new Licencia();
			    if (chequearLicencia.equals("0"))
			    	tipoLicencia = SistemaTipo.getSistemaTipoDemo().toString();
			    licencia.setTipo(tipoLicencia);
			    
			    respuesta = setLicencia(request, sistema.getCmaxc(), licencia.getTipo(), null);
			}
		}
		
		if (respuesta == VgcReturnCode.LICENCIA_SAVE_OK && sistema.getCmaxc() != null)
		{
		    clave = Password.decriptPassWord(sistema.getCmaxc());
		    campos = clave.split(separador);

		    if (licenciaArchivo != null && !licenciaArchivo.equals(""))
			{
			    Date fechaArchivo = FechaUtil.convertirStringToDate(camposArchivo[7], VgcResourceManager.getResourceApp("formato.fecha.corta"));
			    Date fechaServidor = FechaUtil.convertirStringToDate(campos[7], VgcResourceManager.getResourceApp("formato.fecha.corta"));
			    if (fechaArchivo.getTime() > fechaServidor.getTime())
			    {
					codigoMaquina = campos[0];
				    fechaInstalacion = camposArchivo[1];
				    numeroUsuarios = camposArchivo[2];
				    numeroIndicadores = camposArchivo[3];
				    diasVencimiento = camposArchivo[4];
				    tipoLicencia = camposArchivo[5];
				    algoritmo = campos[6];
				    fechaClave = camposArchivo[7];
				    if (camposArchivo.length >= 11)
				    {
				    	chequearLicencia = camposArchivo[8];
					    licenciaVencida = camposArchivo[9];
				    	serial = camposArchivo[10];
				    }
				    else
				    {
				    	if (!chequearLicencia.equals("0"))
				    		chequearLicencia = "1";
					    licenciaVencida = camposArchivo[8];
					    serial = camposArchivo[9];
				    }
				    if (camposArchivo.length >= 12) 
				    	companyName = camposArchivo[11];
				    else
				    {
				    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
				    	Organizacion organizcion = frameworkService.getOrganizacionRoot(false);
				    	frameworkService.close();
				    	
				    	if (organizcion != null)
				    		companyName = organizcion.getNombre();
				    	else
				    		companyName = "Demo";
				    }
				    if (camposArchivo.length >= 13)
				    	productoId = camposArchivo[12];
				    else
				    	productoId = "UA-57790239-1";

				    clave = codigoMaquina + separador;
				    clave = clave + fechaInstalacion + separador;
				    clave = clave + numeroUsuarios + separador;
				    clave = clave + numeroIndicadores + separador;
				    clave = clave + diasVencimiento + separador;
				    clave = clave + tipoLicencia + separador;
				    clave = clave + algoritmo + separador;
				    clave = clave + fechaClave + separador;
				    clave = clave + chequearLicencia + separador;
				    clave = clave + licenciaVencida + separador;
				    clave = clave + serial + separador;
				    clave = clave + companyName + separador;
				    clave = clave + productoId + separador;
				    clave = clave + fileCheck;
				    
				    sistema.setCmaxc(Password.encriptPassWord(clave));

				    licencia = new Licencia();
				    if (chequearLicencia.equals("0"))
				    	tipoLicencia = SistemaTipo.getSistemaTipoDemo().toString();
				    licencia.setTipo(tipoLicencia);
				    
				    respuesta = setLicencia(request, sistema.getCmaxc(), licencia.getTipo(), null);
			    }
			    else
			    {
					codigoMaquina = campos[0];
				    fechaInstalacion = campos[1];
				    numeroUsuarios = campos[2];
				    numeroIndicadores = campos[3];
				    diasVencimiento = campos[4];
				    tipoLicencia = campos[5];
				    algoritmo = campos[6];
				    fechaClave = campos[7];
				    if (campos.length >= 11)
				    {
				    	chequearLicencia = campos[8];
					    licenciaVencida = campos[9];
					    serial = campos[10];
				    }
				    else
				    {
				    	if (!chequearLicencia.equals("0"))
				    		chequearLicencia = "1";
					    licenciaVencida = campos[8];
					    serial = campos[9];
				    }
				    if (campos.length >= 12 && !campos[11].equals("")) 
				    	companyName = campos[11];
				    else
				    {
				    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
				    	Organizacion organizcion = frameworkService.getOrganizacionRoot(false);
				    	frameworkService.close();
				    	
				    	if (organizcion != null)
				    		companyName = organizcion.getNombre();
				    	else
				    		companyName = "Demo";
				    }
				    if (campos.length >= 13 && !campos[12].equals(""))
				    	productoId = campos[12];
				    else
				    	productoId = "UA-57790239-1";
				    if (campos.length >= 14 && !campos[13].equals(""))
				    	fileCheck = campos[13];
				    else
				    	fileCheck = "0";
			    }
			}
		    else
		    {
				codigoMaquina = campos[0];
			    fechaInstalacion = campos[1];
			    numeroUsuarios = campos[2];
			    numeroIndicadores = campos[3];
			    diasVencimiento = campos[4];
			    tipoLicencia = campos[5];
			    algoritmo = campos[6];
			    fechaClave = campos[7];
			    if (campos.length >= 11)
			    {
			    	chequearLicencia = campos[8];
				    licenciaVencida = campos[9];
				    serial = campos[10];
			    }
			    else
			    {
			    	if (!chequearLicencia.equals("0"))
			    		chequearLicencia = "1";
				    licenciaVencida = campos[8];
				    serial = campos[9];
			    }
			    if (campos.length >= 12 && !campos[11].equals("")) 
			    	companyName = campos[11];
			    else
			    {
			    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			    	Organizacion organizcion = frameworkService.getOrganizacionRoot(false);
			    	frameworkService.close();
			    	
			    	if (organizcion != null)
			    		companyName = organizcion.getNombre();
			    	else
			    		companyName = "Demo";
			    }
			    if (campos.length >= 13 && !campos[12].equals(""))
			    	productoId = campos[12];
			    else
			    	productoId = "UA-57790239-1";
			    if (campos.length >= 14 && !campos[13].equals(""))
			    	fileCheck = campos[13];
			    else
			    	fileCheck = "0";
		    }
			
			String expiracion = null;
			String macAplicacionActual = getMACApplication(null);
			String macServidorBdActual = getMACApplication(obtenerServidorPuerto()[0]);
			if (chequearLicencia.equals("0"))
				tipoLicencia = SistemaTipo.getSistemaTipoDemo().toString();
		
			String codigoMaquinaActual = claveMaquina(macAplicacionActual, macServidorBdActual, algoritmo);
			if (licenciaVencida.equals("1") && !chequearLicencia.equals("0"))
				expiracion = null;
			else if (!codigoMaquina.equals(codigoMaquinaActual) && !chequearLicencia.equals("0"))
			{
				boolean hayConindidencia = false;
				List<String> macs = BuscarMatrizAplicacion();
				boolean esLocal = (obtenerServidorPuerto()[0]).equalsIgnoreCase("localhost");
				for (Iterator<String> i = macs.iterator(); i.hasNext(); )
				{
					String mac = (String)i.next();
					codigoMaquinaActual = claveMaquina(mac, esLocal ? mac : "", algoritmo);
					if (codigoMaquina.indexOf(codigoMaquinaActual) != -1)
					{
						hayConindidencia = true;
						break;
					}
				}
				
				if (!hayConindidencia)
				{
					expiracion = null;
					maquinaDiferentes = true;
				}
			}
			else if (tipoLicencia.equals(SistemaTipo.getSistemaTipoDemo().toString()) && !chequearLicencia.equals("0"))
			{
	      		Date fechaVencimiento = FechaUtil.convertirStringToDate(fechaInstalacion, VgcResourceManager.getResourceApp("formato.fecha.corta")); 
				Calendar c = Calendar.getInstance();
	      		c.setTime(fechaVencimiento);
	      		
	      		Calendar fecha = Calendar.getInstance();
		        int day = c.get(5);
		        int month = c.get(2);
		        int year = c.get(1);

		        fecha.set(5, day);
		        fecha.set(2, month);
		        fecha.set(1, year);

		        fecha.add(5, Integer.parseInt(diasVencimiento));
		        fechaVencimiento.setTime(fecha.getTimeInMillis());
		        Date fechaActual = new Date();
				if (fechaVencimiento.getTime() < fechaActual.getTime())
					expiracion = null;
				else
				{
					expiracion = VgcFormatter.formatearFecha(fecha.getTime(), "dd/MM/yyyy");
					
					//Calcular la diferencia en Dias
					Calendar fActual = Calendar.getInstance();
					fActual.setTime(fechaActual);
					
					// conseguir la representacion de la fecha en milisegundos
			        Long milis1 = fActual.getTimeInMillis();
			        Long milis2 = fecha.getTimeInMillis();
			        Long diff = milis2 - milis1;
			        Long diffDays = diff / (24 * 60 * 60 * 1000);
					
					expiracionDias = diffDays.toString();
				}
			}
		    
			Integer fueraLimiteUsuarios = Integer.parseInt(numeroUsuarios);
			if (!tipoLicencia.equals(SistemaTipo.getSistemaTipoFull().toString()))
			{
				UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
				Map<String, Object> filtros = new HashMap<String, Object>();
				filtros.put("isConnected", true);
	
				PaginaLista paginaUsuarios = usuariosService.getUsuarios(0, "fullName", "ASC", false, filtros);
				if (paginaUsuarios.getLista() != null && paginaUsuarios.getLista().size() > 0)
				{
					Integer usuariosConectados = paginaUsuarios.getLista().size();
					if (usuariosConectados > Integer.parseInt(numeroUsuarios))
						fueraLimiteUsuarios = null;
				}
	
				usuariosService.close();
			}
			
			licencia = new Licencia();
			licencia.setExpiracion(expiracion);
		    licencia.setSerial(serial);
		    licencia.setTipo(tipoLicencia);
		    licencia.setNumeroIndicadores(Integer.parseInt(numeroIndicadores));
		    licencia.setNumeroUsuarios(fueraLimiteUsuarios);
		    licencia.setCmaxc(sistema.getCmaxc());
		    licencia.setCodigoMaquina(codigoMaquina);
		    licencia.setFechaInstalacion(fechaInstalacion);
		    licencia.setDiasVencimiento(diasVencimiento);
		    licencia.setAlgoritmo(algoritmo);
		    licencia.setFechaLicencia(fechaClave);
		    licencia.setExpiracionDias(expiracionDias);
		    licencia.setInstalacionMaquinaDiferentes(maquinaDiferentes);
		    licencia.setChequearLicencia((chequearLicencia.equals("0") ? false : true));
		    licencia.setCompanyName(companyName);
		    licencia.setProductoId(productoId);
		    licencia.setFileCheck((fileCheck.equals("1") ? true: false));
		    
		    if (expiracion == null && licenciaVencida.equals("1") && (tipoLicencia.equals(SistemaTipo.getSistemaTipoDemo().toString()) || tipoLicencia.equals(SistemaTipo.getSistemaTipoPrototipo().toString())))
		    {
		    	licenciaVencida = "1";

			    clave = codigoMaquina + separador;
			    clave = clave + fechaInstalacion + separador;
			    clave = clave + numeroUsuarios + separador;
			    clave = clave + numeroIndicadores + separador;
			    clave = clave + diasVencimiento + separador;
			    clave = clave + tipoLicencia + separador;
			    clave = clave + algoritmo + separador;
			    clave = clave + fechaClave + separador;
			    clave = clave + chequearLicencia + separador;
			    clave = clave + licenciaVencida + separador;
			    clave = clave + serial + separador;
			    clave = clave + companyName + separador;
			    clave = clave + productoId + separador;
			    clave = clave + fileCheck;
			    
			    sistema.setCmaxc(Password.encriptPassWord(clave));

			    licencia = new Licencia();
			    licencia.setTipo(tipoLicencia);
			    
			    setLicencia(request, sistema.getCmaxc(), licencia.getTipo(), null);
			    
		    }
		    
		    if (expiracion == null && fileCheck != null && fileCheck.equals("1") && maquinaDiferentes)
	    	{
	    		String file = codigoMaquina + separador;
	    		file = file + codigoMaquinaActual + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		file = file + separador;
	    		
	    		file = Password.encriptPassWord(file);
	    		
	    		setLicencia(request, file, licencia.getTipo(), "verificar.sln");
	    	}
		}
		else
		{
			licencia = new Licencia();
			licencia.setExpiracion(null);
			licencia.setRespuesta(respuesta);
			licencia.setTipo(SistemaTipo.getSistemaTipoName(SistemaTipo.getSistemaTipoDemo()));
		}
		
		return licencia;
	}
	
    public static String obtainMacAddress() throws IOException, InterruptedException 
    {
    	boolean isWin = System.getProperty("os.name").toLowerCase().indexOf("win") != -1;
        Process aProc = Runtime.getRuntime().exec(isWin ? "ipconfig /all" : "/sbin/ifconfig -a");
        BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(aProc.getInputStream())));
        Pattern macAddressPattern = Pattern.compile("((\\p{XDigit}\\p{XDigit}" + (isWin ? "-" : ":") + "){5}\\p{XDigit}\\p{XDigit})");
        for(String outputLine = ""; outputLine != null; outputLine = br.readLine())
        {
        	Matcher macAddressMatcher = macAddressPattern.matcher(outputLine);
            if (macAddressMatcher.find()) 
            	return macAddressMatcher.group(0);
        }

        aProc.destroy();
        aProc.waitFor();
        return null;
    }        

	private String getMACApplication(String hostRemoto)
	{
		StringBuilder sb = new StringBuilder();
        try 
        {
        	InetAddress ip;
        	if (hostRemoto == null || (hostRemoto != null && hostRemoto.equals("")) || (hostRemoto != null && hostRemoto.toLowerCase().equals("localhost")))
    			ip = InetAddress.getLocalHost();
    		else
    			ip = InetAddress.getByName(hostRemoto);
        	NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        	byte[] mac = null;
        	if (network != null)
    		{
	            mac = network.getHardwareAddress();
	            if (mac != null && mac.length > 0)
	            {
	                for (int i = 0; i < mac.length; i++) 
	                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
	            }
	            else
	            {
	            	if (hostRemoto == null || (hostRemoto != null && hostRemoto.equals("")) || (hostRemoto != null && hostRemoto.toLowerCase().equals("localhost")))
	            	{
	                	Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
	                    while(networks.hasMoreElements()) 
	                    {
	                    	network = networks.nextElement();
	                    	mac = network.getHardwareAddress();
	
	                    	if (mac != null && mac.length > 0) 
	                    	{
	                            for (int i = 0; i < mac.length; i++) 
	                                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	                            break;
	                        }
	                    }
	            	}
	            	else
	            		sb.append(getMacAddress());
	            }
    		}
        	else
        	{
            	if (hostRemoto == null || (hostRemoto != null && hostRemoto.equals("")) || (hostRemoto != null && hostRemoto.toLowerCase().equals("localhost")))
            	{
                	Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
                    while(networks.hasMoreElements()) 
                    {
                    	network = networks.nextElement();
                    	mac = network.getHardwareAddress();

                    	if (mac != null && mac.length > 0) 
                    	{
                            for (int i = 0; i < mac.length; i++) 
                                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                            break;
                        }
                    }
            	}
            	else
            		sb.append(getMacAddress());
        	}
        	
        	if (sb.toString().equals(""))
        		sb.append(getMacAddress());
        } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (SocketException e)
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
		}		
        catch (Exception e)
        {
        	e.printStackTrace();
        }

    	if (sb.toString().equals(""))
        	sb.append("SIN-MAC-address");
        
        return sb.toString();
	}
	
	private List<String> BuscarMatrizAplicacion()
	{
		StringBuilder sb = new StringBuilder();
		List<String> macs = new ArrayList<String>();
        try 
        {
        	Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while(networks.hasMoreElements()) 
            {
            	NetworkInterface network = networks.nextElement();
            	byte[] mac = network.getHardwareAddress();

            	if (mac != null && mac.length > 0) 
            	{
            		sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) 
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    if (!sb.toString().equals(""))
                    	macs.add(sb.toString());
                }
            }
        	
        	if (macs.size() == 0)
        	{
        		String os = System.getProperty("os.name");
        		String ipConfigResponse = null;
        		
    			if (os.startsWith("Linux")) 
    				ipConfigResponse = linuxRunIfConfigCommand();
    			else if (os.toLowerCase().startsWith("Solaris".toLowerCase()) || os.toLowerCase().startsWith("SunOS".toLowerCase()))
    				ipConfigResponse = solarisRunArpCommand();
    			else if (ipConfigResponse == null)
    			{
    				boolean isWin = System.getProperty("os.name").toLowerCase().indexOf("win") != -1;
                    Process aProc = Runtime.getRuntime().exec(isWin ? "ipconfig /all" : "/sbin/ifconfig -a");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(aProc.getInputStream())));
                    Pattern macAddressPattern = Pattern.compile("((\\p{XDigit}\\p{XDigit}" + (isWin ? "-" : ":") + "){5}\\p{XDigit}\\p{XDigit})");
                    
                    for(String outputLine = ""; outputLine != null; outputLine = br.readLine())
                    {
                    	Matcher macAddressMatcher = macAddressPattern.matcher(outputLine);
                        if (macAddressMatcher.find()) 
                        	macs.add(macAddressMatcher.group(0));
                    }
                    aProc.destroy();
                    aProc.waitFor();
    			}
    			
    			if (ipConfigResponse != null)
    			{
        			StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
        			String lastMacAddress = null;

        			while (tokenizer.hasMoreTokens()) 
        			{
        				String line = tokenizer.nextToken().trim();
        				if (lastMacAddress != null) 
        					macs.add(lastMacAddress);
        		
        				int macAddressPosition = line.indexOf("HWaddr");
        				if (macAddressPosition <= 0)
        					continue;
        		
        				String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
        				if (linuxIsMacAddress(macAddressCandidate)) 
        				{
        					lastMacAddress = macAddressCandidate;
        					continue;
        				}
        			}
    			}
        	}
        	
        	if (macs.size() == 0)
        		macs.add(getMacAddress());
        } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (SocketException e)
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
		}		
        catch (Exception e)
        {
        	e.printStackTrace();
        }

        return macs;
	}
	
	private final static String getMacAddress() throws IOException 
	{
		String os = System.getProperty("os.name");
		try 
		{
			if (os.startsWith("Linux")) 
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			else if (os.toLowerCase().startsWith("Solaris".toLowerCase()) || os.toLowerCase().startsWith("SunOS".toLowerCase()))
				return linuxParseMacAddress(solarisRunArpCommand());
			else
			{
				boolean isWin = System.getProperty("os.name").toLowerCase().indexOf("win") != -1;
                Process aProc = Runtime.getRuntime().exec(isWin ? "ipconfig /all" : "/sbin/ifconfig -a");
                BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(aProc.getInputStream())));
                Pattern macAddressPattern = Pattern.compile("((\\p{XDigit}\\p{XDigit}" + (isWin ? "-" : ":") + "){5}\\p{XDigit}\\p{XDigit})");
                
                for(String outputLine = ""; outputLine != null; outputLine = br.readLine())
                {
                	Matcher macAddressMatcher = macAddressPattern.matcher(outputLine);
                    if (macAddressMatcher.find()) 
                    {
                        aProc.destroy();
                        aProc.waitFor();
                    	return macAddressMatcher.group(0);
                    }
                }
                
                throw new IOException("Sistema operativo desconocido: " + os);
			}
		} 
		catch (ParseException ex) 
		{
			ex.printStackTrace();
			System.out.println("Sistema operativo desconocido: " + os);
			throw new IOException(ex.getMessage());
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
			System.out.println("Sistema operativo desconocido: " + os);
			throw new IOException(e.getMessage());
		}
	}
	
	private final static String linuxParseMacAddress(String ipConfigResponse) throws ParseException 
	{
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;

		while (tokenizer.hasMoreTokens()) 
		{
			String line = tokenizer.nextToken().trim();
			if (lastMacAddress != null) 
				return lastMacAddress;
	
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0)
				continue;
	
			String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
			if (linuxIsMacAddress(macAddressCandidate)) 
			{
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		
		return lastMacAddress;
	}

	private final static boolean linuxIsMacAddress(String macAddressCandidate) 
	{
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}
			
	private final static String linuxRunIfConfigCommand() throws IOException 
	{
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

		StringBuffer buffer = new StringBuffer();
		for (;;) 
		{
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();

		return outputText;
	}

	private final static String solarisRunArpCommand() 
	{
		String outputText = "";
		
		try 
		{
			Process p = Runtime.getRuntime().exec("arp –a");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	
			String s = null;
	        while ((s = stdInput.readLine()) != null) 
	        {
	            s = s.trim();
	            if (s.startsWith("e") && s.length() > 0)
	            {
	            	String[] result = s.split(" ");
	            	if (result.length > 0 && result[(result.length -1)].length() == 17)
	            	{
		            	outputText = result[(result.length -1)];
		            	break;
	            	}
	            }
	        }
	        stdInput.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			outputText = "Comando imposible de ejecutar";
		}
        
		return outputText;
	}

	public String[] obtenerServidorPuerto()
	{
		String[] conexion = new String[2];
		
		try 
		{
			Connection connection = FrameworkServiceFactory.getInstance().getCurrentSession();
			String url = connection.getMetaData().getURL();
			String driverName = connection.getMetaData().getDriverName();
			
			if (driverName.toLowerCase().indexOf("postgresql") > -1)
			{
				//url="jdbc:postgresql://localhost:5432/PEMEX?compatible=7.1"
				url = url.replace("jdbc:postgresql://", ""); 
				url = url.substring(0, url.indexOf("/"));
				String[] campos = url.split(":");
				
				conexion[0] = campos[0];
				conexion[1] = campos[1];
			}
			else if (driverName.toLowerCase().indexOf("oracle") > -1)
			{
				//url="jdbc:oracle:thin:@localhost:1521:STRATEGOS"
				url = url.replace("jdbc:oracle:thin:@", ""); 
				String[] campos = url.split(":");

				conexion[0] = campos[0];
				conexion[1] = campos[1];
			}
			else if ((driverName.toLowerCase().indexOf("sqlserver") > -1) || driverName.toLowerCase().indexOf("sql server") > -1)
			{
				//url="jdbc:sqlserver://localhost:1433;databaseName=Personal;user=sa;password=gmed;"
				url = url.replace("jdbc:sqlserver://", ""); 
				url = url.substring(0, url.indexOf(";"));
				String[] campos = url.split(":");

				conexion[0] = campos[0];
				conexion[1] = campos[1];
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return conexion;
	}
	
	public Enviroment getEnvironment()
	{
		Enviroment enviroment = null;
	    try 
	    {
		    // ********
		    // Obtener un parametro desde el context.xml
		    // ********
			Context ctx = new InitialContext();
		    ctx = (Context) ctx.lookup("java:comp/env");
		    
		    enviroment = new Enviroment();
		    try 
		    {
		    	enviroment.setTipo((String) ctx.lookup("tipoEnvironment"));
		    } 
		    catch (Exception e) 
		    {
			}
		    try 
		    {
		    	enviroment.setPageLoad((String) ctx.lookup("pageLoad"));
		    }
		    catch (Exception e) 
		    {
			}
	    } 
	    catch (Exception e) 
	    {
		}
	    
	    return enviroment;
	}
	
	public String getFolderMasterConfiguration()
	{
		String folderMasterConfiguration = "";
	    try 
	    {
		    // ********
		    // Obtener un parametro desde el context.xml
		    // ********
			Context ctx = new InitialContext();
		    ctx = (Context) ctx.lookup("java:comp/env");
		    folderMasterConfiguration = (String) ctx.lookup("folderMasterConfiguration");			
	    } 
	    catch (Exception e) 
	    {
	    	try
	    	{
		    	if (e.toString().indexOf("javax.naming.NameNotFoundException") != -1)
	    		{
				    File folder = new File("/Vision/Strategos");
				    
				    if (!folder.exists() || !folder.isDirectory())
				    {
				    	folder = new File("/Vision");
				    	if (!folder.exists() || !folder.isDirectory())
					    	folder.mkdirs();
				    	folder  = new File(folder.getAbsolutePath() + "/Strategos");
				    	if (!folder.exists() || !folder.isDirectory())
				    		folder.mkdirs();
				    }
				    
				    folderMasterConfiguration = folder.getAbsolutePath();
	    		}
		    	else
		    	{
			    	System.out.println("Folder No configurado");
					e.printStackTrace();
		    	}
	    	}
		    catch (Exception ex) 
		    {
		    	System.out.println("El usuario No tiene permiso para crear Folder");
		    	ex.printStackTrace();
		    }
		}
	    
	    if (!folderMasterConfiguration.equals(""))
	    	setFoldersConfiguracion(folderMasterConfiguration);
		
	    return folderMasterConfiguration;
	}
	
	private int setFoldersConfiguracion(String folderMasterConfiguration)
	{
		int resultado = VgcReturnCode.LICENCIA_SAVE_OK;
	    try 
	    {
		    File folder = new File(folderMasterConfiguration + "/" + folderConfiguracion);
		    
		    if (!folder.exists() || !folder.isDirectory())
		    	folder.mkdirs();
	    }
	    catch (Exception e) 
	    {
	    	resultado = VgcReturnCode.LICENCIA_SAVE_FOLDER_EMPTY;
	    	System.out.println("No tiene permiso para Crear Folders");
			e.printStackTrace();
	    }
		
		return resultado;
	}
	
	private String getLicencia(HttpServletRequest request)
	{
		String licencia = "";
		BufferedReader entrada;	
    	String linea = "";
    	
    	final byte STATUS_INICIAL = 0;
    	final byte STATUS_FOLDER = 2;
    	final byte STATUS_INSTALATION = 3;
    	
    	byte status = STATUS_INICIAL;
	    
	    try 
	    {
		    String folder = getFolderMasterConfiguration();			
		    if (!folder.equals(""))
		    {
			    File Ffichero = new File(folder + "/" + folderConfiguracion + "/" + archivoConfiguracion);
			    status = STATUS_FOLDER;
			    if(Ffichero.exists())
			    	entrada = new BufferedReader(new InputStreamReader(new FileInputStream(Ffichero), "utf-8")); // lector con buffer		    }
			    else
			    {
			    	status = STATUS_INSTALATION;
			    	String result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/";
					result = request.getScheme() + "://" + result.replaceAll("//", "/");

					URL url = new URL(result + archivoConfiguracion); 
					URLConnection urlConn = url.openConnection();
				
					entrada = new BufferedReader(new InputStreamReader(urlConn.getInputStream())); // lector con buffer
			    }
		    }
		    else
		    {
		    	status = STATUS_INSTALATION;
		    	String result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/";
				result = request.getScheme() + "://" + result.replaceAll("//", "/");

				URL url = new URL(result + archivoConfiguracion); 
				URLConnection urlConn = url.openConnection();
			
				entrada = new BufferedReader(new InputStreamReader(urlConn.getInputStream())); // lector con buffer
		    }
			
	    	if (entrada != null)
	    	{
		    	while((linea = entrada.readLine()) != null)
		    		licencia = linea;
	    	}
	    	
	    	entrada.close();    	
	    }
	    catch (IOException e)
	    {
	    	if (status == STATUS_FOLDER)
	    		System.out.println("Error leyendo licencia desde la carpeta de configuración");
	    	else if (status == STATUS_INSTALATION)
	    		System.out.println("Error leyendo licencia desde el directorio de instalación");
	    	else
	    		System.out.println("Error leyendo licencia");
	    	e.printStackTrace();
	    } 
	    catch (Exception e) 
	    {
	    	if (status == STATUS_FOLDER)
	    		System.out.println("Error leyendo licencia desde la carpeta de configuración");
	    	else if (status == STATUS_INSTALATION)
	    		System.out.println("Error leyendo licencia desde el directorio de instalación");
	    	else
	    		System.out.println("Error leyendo licencia");
			e.printStackTrace();
		}
		
		return licencia;
	}

	private int setLicencia(HttpServletRequest request, String licencia, String tipo, String file)
	{
		int respuesta = VgcReturnCode.LICENCIA_SAVE_OK;
	    try 
	    {
	    	// ********
	    	// Salvar Configuracion en un Archivo
	    	// ********
		    String folder = getFolderMasterConfiguration();
		    if (!folder.equals(""))
		    {
			    File Ffichero = new File(folder + "/" + folderConfiguracion + "/" + (file == null ? archivoConfiguracion : file));
			    if(Ffichero.exists())
			    	Ffichero.delete();
			    
			    Ffichero.createNewFile();
			    BufferedWriter entrada = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero,true), "utf-8")); // lector con buffer
			    entrada.write(licencia);
			    entrada.flush();
			    entrada.close();
		    }
		    else
		    	respuesta = VgcReturnCode.LICENCIA_SAVE_FOLDER_EMPTY;

	    	// ********
	    	// Despues que se grabe en el archivo de 
		    // configuracion se graba en la Base de datos
	    	// ********
		    if (respuesta == VgcReturnCode.LICENCIA_SAVE_OK && file == null)
		    {
		    	FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		    	respuesta = frameworkService.setSerial(licencia, tipo);
			    frameworkService.close();
			    if (respuesta != VgcReturnCode.LICENCIA_SAVE_OK)
			    {
			    	System.out.println("Error salvando la licencia");
			    	respuesta = VgcReturnCode.LICENCIA_SAVE_ERROR; 
			    }
		    }
		    
		    // ********
		    // Leer archivo con FileOutputStream
		    // ********
		    //FileOutputStream fos = new FileOutputStream(Ffichero, true);
		    //fos.write(licencia.getBytes());
		    //fos.flush();
		    //fos.close();
		    
		    // ********
		    // Obtener el path real por medio de un URL
		    // ********
			//String path = this.getClass().getClassLoader().getResource("").getPath();
			//String fullPath = URLDecoder.decode(path, "UTF-8");
			//String pathArr[] = fullPath.split("/WEB-INF/");
			//System.out.println(fullPath);
			//System.out.println(pathArr[0]);
			//fullPath = pathArr[0];
			//String reponsePath = new File(fullPath).getPath() + File.separatorChar + archivo;
			
			// ********
			// Obtener un parametro desde el Web.XML
			// ********
			// * Web.Xml
			//<context-param>
	    	//<param-name>AdministratorEmail</param-name>
	    	//<param-value>mkyong2002@yahoo.com</param-value>
			//</context-param>
			// * Desde Java
			//ServletContext sc = request.getSession().getServletContext();  
			//String testNameValue = sc.getInitParameter("AdministratorEmail");  		
			
		    // ********
		    // ********
		    //Ffichero.createNewFile();
		    //BufferedWriter entrada = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero,true), "utf-8")); // lector con buffer
		    //entrada.write(licencia);
		    //entrada.flush();
		    //entrada.close();
		    
		    // ********
		    // Leer archivo con OutputStream
		    // ********
		    //OutputStream out = urlConn.getOutputStream();
		    //byte [] array = licencia.getBytes();
		    //InputStream is = new ByteArrayInputStream(licencia.getBytes());
		    //int leido = is.read(array);
		    //while (leido > 0) 
		    //{
		    //	out.write(array,0,leido);
		     //  leido=is.read(array);
		    //}
		    //out.write(licencia);
		    //out.flush();
		    //out.close();
		    
		    // ********
		    // Leer archivo con BufferedWriter 
		    // ********
		    //BufferedWriter entrada = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
	    	//entrada.write(licencia);
		    //entrada.flush();
		    //entrada.close();
		    
		    // ********
		    // Obtener la direccion por medio de la aplicacion
		    // ********
	    	//String result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/conf/";
			//result = request.getScheme() + "://" + result.replaceAll("//", "/");
			//URL url = new URL(result + archivo); 
			//URLConnection urlConn = url.openConnection();
			//urlConn.setDoOutput(true);
			//urlConn.connect();
	    	
		    // ********
		    // Leer archivo con Writer
		    // ********
		    //Writer output = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
		    //output.write(licencia + "\r\n");
		    //output.flush();
		    //output.close();
	    	
		    // ********
		    // Leer archivo con PrintWriter 
		    // ********
		    //PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(urlConn.getOutputStream()), true);
		    //printwriter.println(licencia + "\r\n"); //escribe en el archivo
		    //printwriter.write(licencia + "\r\n"); //escribe en el archivo
		    //printwriter.flush();
		    //printwriter.close(); //cierra el archivo
		    
		    System.out.println("Lincencia salvada con exito");
	    }
	    catch (IOException e)
	    {
	    	respuesta = VgcReturnCode.LICENCIA_SAVE_ERROR;
	    	System.out.println("Error salvando el archivo de lincencia");
	    	e.printStackTrace();
		} 
	    catch (Exception e) 
	    {
	    	respuesta = VgcReturnCode.LICENCIA_SAVE_ERROR;
	    	System.out.println("Error salvando el archivo de lincencia");
			e.printStackTrace();
		}
	    
	    return respuesta;
	}
	
	public Blob readBlob(String tabla, String campo, String where, String condicion) throws Exception 
	{
		return this.persistenceSession.readBlob(tabla, campo, where, condicion);
	}
	
	public int getMasterConfiguracion(HttpServletRequest request)
	{
		int resultado = VgcReturnCode.CONFIGURACION_READ_OK;
	    try 
	    {
	    	// ********
		    // Obtener un parametro desde el context.xml
		    // ********
	    	//Context ctx = new InitialContext();
	    	//Context envContext = (Context) ctx.lookup("java:comp/env");
	    	//DataSource ds = (DataSource)envContext.lookup("jdbc/Strategos");

		    //Connection c = ds.getConnection();
		    //c.close();

	    	// ********
		    // Obtener un parametro desde el context.xml
		    // ********
			String esquema = "";
			String linea = "";
			InputStream schema = request.getSession().getServletContext().getResourceAsStream("META-INF/context.xml");
			if (schema != null)
			{
				BufferedReader entrada = new BufferedReader(new InputStreamReader(schema)); // lector con buffer
		    	if (entrada != null)
		    	{
			    	while((linea = entrada.readLine()) != null)
			    		esquema = esquema + linea;
		    	}
			}
	    	
	    	if (!esquema.equals(""))
	    	{
	    		linea = esquema;
	    		linea = linea.substring(linea.indexOf("username=\""), linea.length());
	    		linea = linea.substring(linea.indexOf("\"") + 1, linea.length());
	    		String user = linea.substring(0, linea.indexOf("\""));
	    		linea = esquema;
	    		linea = linea.substring(linea.indexOf("password=\""), linea.length());
	    		linea = linea.substring(linea.indexOf("\"") + 1, linea.length());
	    		String pwd = linea.substring(0, linea.indexOf("\""));
	    		linea = esquema;
	    		linea = linea.substring(linea.indexOf("driverClassName=\""), linea.length());
	    		linea = linea.substring(linea.indexOf("\"") + 1, linea.length());
	    		String driver = linea.substring(0, linea.indexOf("\""));
	    		linea = esquema;
	    		linea = linea.substring(linea.indexOf("url=\""), linea.length());
	    		linea = linea.substring(linea.indexOf("\"") + 1, linea.length());
	    		String url = linea.substring(0, linea.indexOf("\""));
	    		
	    		if (!user.equals("") && !pwd.equals("") && !driver.equals("") && !url.equals(""))
	    		{
	    			boolean saveConfiguracion = false;
	    			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	    			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
	    			
	    			if (configuracion != null)
	    			{
	    				//XML
	    				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
	    		        DocumentBuilder db = dbf.newDocumentBuilder(); 
	    		        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8"))); 
	    		        doc.getDocumentElement().normalize();
	    				NodeList nList = doc.getElementsByTagName("properties");
	    				Element eElement = (Element) nList.item(0);
	    				
	    				/** Se obtiene el FormBean haciendo el casting respectivo */
	    				if (!url.equals(getTagValue("url", eElement)))
	    					saveConfiguracion = true;
	    				else if (!driver.equals(getTagValue("driver", eElement)))
	    					saveConfiguracion = true;
	    				else if (!user.equals(getTagValue("user", eElement)))
	    					saveConfiguracion = true;
	    				
	    				//Desencript Password
	    				String pwdDabase = getTagValue("password", eElement);
	    				if (pwdDabase != null && !pwdDabase.equals(""))
	    					pwdDabase = Password.decriptPassWord(pwdDabase);
	    				if (!saveConfiguracion && !pwd.equals(pwdDabase))
	    					saveConfiguracion = true;
	    			}
	    			else
	    				saveConfiguracion = true;

	    			if (saveConfiguracion)
	    			{
	    				pwd = Password.encriptPassWord(pwd);
	    				if (!new FrameworkConnection().testConnection(url, driver, user, Password.decriptPassWord(pwd)))
	    					resultado = VgcReturnCode.CONFIGURACION_NO_CONNECT;
	    				else
	    				{
	    					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    					DocumentBuilder builder = factory.newDocumentBuilder();
	    					DOMImplementation implementation = builder.getDOMImplementation();
	    					Document document = implementation.createDocument(null, "servicios", null);
	    					document.setXmlVersion("1.0"); // asignamos la version de nuestro XML

	    					Element raiz = document.createElement("properties");  // creamos el elemento raiz
	    					document.getDocumentElement().appendChild(raiz);  //pegamos la raiz al documento

	    					Element elemento = document.createElement("url"); //creamos un nuevo elemento
	    					Text text = document.createTextNode(url); //Ingresamos la info
	    					elemento.appendChild(text);
	    					raiz.appendChild(elemento);

	    					elemento = document.createElement("driver");
	    					text = document.createTextNode(driver);
	    					elemento.appendChild(text);
	    					raiz.appendChild(elemento);
	    					
	    					elemento = document.createElement("user");
	    					text = document.createTextNode(user);
	    					elemento.appendChild(text);
	    					raiz.appendChild(elemento);

	    					elemento = document.createElement("password");
	    					text = document.createTextNode(pwd);
	    					elemento.appendChild(text);
	    					raiz.appendChild(elemento);
	    					
	    					Source source = new DOMSource(document);
	    					
	    					StringWriter writer = new StringWriter();
	    					Result result = new StreamResult(writer);

	    					Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    					transformer.transform(source, result);

	    					configuracion = new Configuracion(); 
	    					configuracion.setParametro("Strategos.Servicios.Configuracion");
	    					configuracion.setValor(writer.toString().trim());
	    					Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
	    					if (frameworkService.saveConfiguracion(configuracion, usuario) == VgcReturnCode.DB_OK)
	    						resultado = VgcReturnCode.CONFIGURACION_READ_OK;
	    					else
	    						resultado = VgcReturnCode.CONFIGURACION_NO_SAVE;
	    				}
	    			}
	    			frameworkService.close();
	    		}
	    	}
	    	else
				resultado = VgcReturnCode.CONFIGURACION_NO_SAVE;

	    } 
	    catch (IOException e)
	    {
	    	e.printStackTrace();
	    	resultado = VgcReturnCode.CONFIGURACION_NO_SAVE;
	    } 
	    catch (Exception e) 
	    {
			e.printStackTrace();
			resultado = VgcReturnCode.CONFIGURACION_NO_SAVE;
		}
		
	    return resultado;
	}
	
	public static String getTagValue(String sTag, Element eElement) 
	{
		if (eElement.getElementsByTagName(sTag).getLength() > 0 && eElement.getElementsByTagName(sTag).item(0) != null)
		{
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
			Node nValue = (Node) nlList.item(0);
			
			if (nValue != null)
				return nValue.getNodeValue();
			else
				return "";
		}
		else
			return "";
	}
	
	public void registrarAuditoriaEvento(Object objeto, Usuario usuario, byte tipoEvento)
	{
		this.persistenceSession.registrarAuditoriaEvento(objeto, usuario, tipoEvento);
	}
}
