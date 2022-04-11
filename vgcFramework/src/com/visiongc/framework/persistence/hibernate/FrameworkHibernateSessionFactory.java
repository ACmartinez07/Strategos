package com.visiongc.framework.persistence.hibernate;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.persistence.hibernate.VgcHibernateSessionFactory;
import com.visiongc.framework.arboles.persistence.ArbolesPersistenceSession;
import com.visiongc.framework.arboles.persistence.hibernate.ArbolesHibernateSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;
import com.visiongc.framework.auditoria.persistence.hibernate.AuditoriaHibernateSession;
import com.visiongc.framework.auditoria.persistence.hibernate.AuditoriaMedicionHibernateSession;
import com.visiongc.framework.auditoria.persistence.hibernate.AuditoriasHibernateSession;
import com.visiongc.framework.importacion.persistence.ImportacionPersistenceSession;
import com.visiongc.framework.importacion.persistence.hibernate.ImportacionHibernateSession;
import com.visiongc.framework.message.persistence.MessagePersistenceSession;
import com.visiongc.framework.message.persistence.hibernate.MessageHibernateSession;
import com.visiongc.framework.modulo.persistence.ModuloPersistenceSession;
import com.visiongc.framework.modulo.persistence.hibernate.ModuloHibernateSession;
import com.visiongc.framework.persistence.FrameworkPersistenceSession;
import com.visiongc.framework.persistence.FrameworkPersistenceSessionFactory;
import com.visiongc.framework.persistence.ReporteServicioPersistenceSession;
import com.visiongc.framework.transaccion.persistence.TransaccionPersistenceSession;
import com.visiongc.framework.transaccion.persistence.hibernate.TransaccionHibernateSession;
import com.visiongc.framework.usuarios.persistence.UsuariosPersistenceSession;
import com.visiongc.framework.usuarios.persistence.hibernate.UsuariosHibernateSession;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Settings;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.impl.SessionFactoryImpl;

// Referenced classes of package com.visiongc.framework.persistence.hibernate:
//            FrameworkHibernateSession

public class FrameworkHibernateSessionFactory
    implements FrameworkPersistenceSessionFactory
{

    public FrameworkHibernateSessionFactory()
    {
        connection = null;
    }

    public FrameworkPersistenceSession openPersistenceSession()
    {
        FrameworkPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new FrameworkHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
            if(connection == null)
                connection = getCurrentSession();
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Framework", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public FrameworkPersistenceSession openPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        FrameworkPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new FrameworkHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Framework", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public UsuariosPersistenceSession openUsuariosPersistenceSession()
    {
        UsuariosPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new UsuariosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Usuarios", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public UsuariosPersistenceSession openUsuariosPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        UsuariosPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new UsuariosHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Usuarios", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public MessagePersistenceSession openMessagePersistenceSession()
    {
        MessagePersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new MessageHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Usuarios", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public MessagePersistenceSession openMessagePersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        MessagePersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new MessageHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Usuarios", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public ArbolesPersistenceSession openArbolesPersistenceSession()
    {
        ArbolesPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new ArbolesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Arboles", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public ArbolesPersistenceSession openArbolesPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        ArbolesPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new ArbolesHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Arboles", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public AuditoriaPersistenceSession openAuditoriaPersistenceSession()
    {
        AuditoriaPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new AuditoriaHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Auditor\355a", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public AuditoriaPersistenceSession openAuditoriaPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        AuditoriaPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new AuditoriaHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Auditor\355a", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }
    
    public AuditoriaMedicionPersistenceSession openAuditoriaMedicionPersistenceSession()
    {
        AuditoriaMedicionPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new AuditoriaMedicionHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesión de persistencia de hibernate de Auditoría", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public AuditoriaMedicionPersistenceSession openAuditoriaMedicionPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        AuditoriaMedicionPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new AuditoriaMedicionHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesión de persistencia de hibernate de Auditoría", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }
    
    
    public AuditoriasPersistenceSession openAuditoriasPersistenceSession()
    {
        AuditoriasPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new AuditoriasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesión de persistencia de hibernate de Auditoría", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public AuditoriasPersistenceSession openAuditoriasPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        AuditoriasPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new AuditoriasHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesión de persistencia de hibernate de Auditoría", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }
    

    public ModuloPersistenceSession openModuloPersistenceSession()
    {
        ModuloPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new ModuloHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Modulos", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public ModuloPersistenceSession openModuloPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        ModuloPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new ModuloHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Modulos", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public Connection getCurrentSession()
    {
        try
        {
            if(connection == null)
            {
                Settings settings = ((SessionFactoryImpl)VgcHibernateSessionFactory.getHibernateSessionFactory()).getSettings();
                connection = settings.getConnectionProvider().getConnection();
            }
        }
        catch(SQLException sqlexception) { }
        return connection;
    }

    public ImportacionPersistenceSession openImportacionPersistenceSession()
    {
        ImportacionPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new ImportacionHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Importaciones", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public ImportacionPersistenceSession openImportacionPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        ImportacionPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new ImportacionHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Importaciones", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public TransaccionPersistenceSession openTransaccionPersistenceSession()
    {
        TransaccionPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new TransaccionHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Transacciones", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public TransaccionPersistenceSession openTransaccionPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
        TransaccionPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession)vgcPersistenceSession;
        try
        {
            persistenceSession = new TransaccionHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesi\363n de persistencia de hibernate de Transacciones", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    
    public ReporteServicioPersistenceSession openReporteServicioPersistenceSession()
    {
    	ReporteServicioPersistenceSession persistenceSession = null;
        try
        {
            persistenceSession = new ReporteServicioHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesión de persistencia de hibernate de Auditoría", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }

    public ReporteServicioPersistenceSession openReporteServicioPersistenceSession(VgcPersistenceSession vgcPersistenceSession)
    {
    	ReporteServicioPersistenceSession persistenceSession = null;
        VgcHibernateSession hibernateSession = (VgcHibernateSession) vgcPersistenceSession;
        try
        {
            persistenceSession = new ReporteServicioHibernateSession(hibernateSession);
        }
        catch(Exception e)
        {
            try
            {
                throw new Exception("No se pudo crear una sesión de persistencia de hibernate de Auditoría", e);
            }
            catch(Exception exception) { }
        }
        return persistenceSession;
    }
    
    
    private Connection connection;

	
	
	
	
}