package com.visiongc.app.strategos.web.struts.calculos.actions;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Correo {
	private final Properties properties = new Properties();
	
	private String password;
 
	private Session session;
 
	private void init(String host, String port, String user, String pass) {
 
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port",port);
		properties.setProperty("mail.smtp.mail.sender", user);
		properties.setProperty("mail.smtp.user", user);
		properties.setProperty("mail.smtp.auth", "true");
		
		
		session = Session.getDefaultInstance(properties);
                session.setDebug(true);
	}
 
	public void sendEmail(String host, String port, String user, String pass, String titulo, String texto, String correo){
 
		init(host, port, user, pass);
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
			message.setSubject(titulo);
			message.setText(texto);
			Transport t = session.getTransport("smtp");
			t.connect(host,(String)properties.get("mail.smtp.user"),pass);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		}catch (MessagingException me){
                        //Aqui se deberia o mostrar un mensaje de error o en lugar
                        //de no hacer nada con la excepcion, lanzarla para que el modulo
                        //superior la capture y avise al usuario con un popup, por ejemplo.
			return;
		}
		
	}
}
