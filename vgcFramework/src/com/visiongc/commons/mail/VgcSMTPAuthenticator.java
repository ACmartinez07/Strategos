package com.visiongc.commons.mail;

import com.visiongc.framework.configuracion.VgcConfiguraciones;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class VgcSMTPAuthenticator extends Authenticator
{

    public VgcSMTPAuthenticator()
    {
    }

    public static String getUser()
    {
        return VgcConfiguraciones.getInstance().getValor("mail.smtp.user", "usuarioServidorCorreoSmtp", null);
    }

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = getUser();
        String password = VgcConfiguraciones.getInstance().getValor("mail.smtp.password", "passwordServidorCorreoSmtp", null);
        return new PasswordAuthentication(username, password);
    }

    public static final String DEFAULT_USER = "usuarioServidorCorreoSmtp";
}