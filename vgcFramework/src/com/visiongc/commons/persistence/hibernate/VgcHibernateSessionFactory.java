package com.visiongc.commons.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.util.VgcHibernateDtdEntityResolver;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaEntero;
import com.visiongc.framework.auditoria.model.AuditoriaEvento;
import com.visiongc.framework.auditoria.model.AuditoriaFecha;
import com.visiongc.framework.auditoria.model.AuditoriaFlotante;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMemo;
import com.visiongc.framework.auditoria.model.AuditoriaString;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.ObjetoAuditableAtributo;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Error;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Importacion;
import com.visiongc.framework.model.Lock;
import com.visiongc.framework.model.LockRead;
import com.visiongc.framework.model.Message;
import com.visiongc.framework.model.MessageResource;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.ObjetoBinario;
import com.visiongc.framework.model.ObjetoSistema;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.OrganizacionBasica;
import com.visiongc.framework.model.Permiso;
import com.visiongc.framework.model.PermisoGrupo;
import com.visiongc.framework.model.PersonaBasica;
import com.visiongc.framework.model.Plantilla;
import com.visiongc.framework.model.PwdHistoria;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Servicio;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.UserSession;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.UsuarioGrupo;
import com.visiongc.framework.model.Version;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.EntityResolver;

public class VgcHibernateSessionFactory {
	private static SessionFactory hibernateSessionFactory = null;

	static {
		try {
			Configuration configuration = new Configuration();
			VgcHibernateDtdEntityResolver resolver = new VgcHibernateDtdEntityResolver();
			configuration.setEntityResolver((EntityResolver) resolver);
			boolean reintentar = false;
			try {
				VgcHibernateSessionFactory.mapFrameworkObjects(configuration);
				VgcHibernateSessionFactory.setProperties(configuration);
				configuration = configuration.configure();
			} catch (Throwable t) {
				reintentar = true;
			}
			if (reintentar) {
				configuration = new Configuration();
				resolver = new VgcHibernateDtdEntityResolver();
				configuration.setEntityResolver((EntityResolver) resolver);
				VgcHibernateSessionFactory.mapFrameworkObjects(configuration);
				VgcHibernateSessionFactory.setProperties(configuration);
				configuration = configuration.configure();
			}
			hibernateSessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ChainedRuntimeException(
					"No se pudo inicializar la fábrica de Servicios de persistencia",
					ex);
		}
	}

	public static SessionFactory getHibernateSessionFactory() {
		try {
			return hibernateSessionFactory;
		} catch (Throwable ex) {
			throw new ChainedRuntimeException(
					"No se pudo inicializar la fábrica de Servicios de hibernate session factory",
					ex);
		}
	}

	private static void setProperties(Configuration configuration) {
		configuration.setProperty("hibernate.connection.autocommit", "false");
		configuration.setProperty("hibernate.query.substitutions",
				"true 1, false 0, yes 'Y', no 'N'");
		configuration.setProperty("hibernate.connection.isolation",
				String.valueOf(1));
	}

	private static void mapFrameworkObjects(Configuration configuration) {
		configuration.addClass(Error.class);
		configuration.addClass(Permiso.class);
		configuration.addClass(Lock.class);
		configuration.addClass(LockRead.class);
		configuration.addClass(Grupo.class);
		configuration.addClass(OrganizacionBasica.class);
		configuration.addClass(Organizacion.class);
		configuration.addClass(Usuario.class);
		configuration.addClass(UserSession.class);
		configuration.addClass(UsuarioGrupo.class);
		configuration.addClass(Configuracion.class);
		configuration.addClass(ConfiguracionUsuario.class);
		configuration.addClass(Plantilla.class);
		configuration.addClass(ObjetoSistema.class);
		configuration.addClass(MessageResource.class);
		configuration.addClass(PermisoGrupo.class);
		configuration.addClass(PersonaBasica.class);
		configuration.addClass(Sistema.class);
		configuration.addClass(ObjetoAuditable.class);
		configuration.addClass(ObjetoAuditableAtributo.class);
		configuration.addClass(AuditoriaEvento.class);
		configuration.addClass(AuditoriaString.class);
		configuration.addClass(AuditoriaMemo.class);
		configuration.addClass(AuditoriaFecha.class);
		configuration.addClass(AuditoriaEntero.class);
		configuration.addClass(AuditoriaFlotante.class);
		configuration.addClass(AuditoriaMedicion.class);
		configuration.addClass(Auditoria.class);
		configuration.addClass(ObjetoBinario.class);
		configuration.addClass(PwdHistoria.class);
		configuration.addClass(Servicio.class);
		configuration.addClass(Message.class);
		configuration.addClass(Modulo.class);
		configuration.addClass(Version.class);
		configuration.addClass(Importacion.class);
		configuration.addClass(Transaccion.class);
		configuration.addClass(ReporteServicio.class);		
	}
}