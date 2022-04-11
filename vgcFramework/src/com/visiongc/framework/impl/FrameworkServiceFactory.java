package com.visiongc.framework.impl;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.impl.VgcDefaultServiceFactory;
import com.visiongc.commons.impl.VgcServiceFactory;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkConfiguration;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.ReporteServicioService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.impl.ArbolesServiceImpl;
import com.visiongc.framework.arboles.persistence.ArbolesPersistenceSession;
import com.visiongc.framework.auditoria.AuditoriaMedicionService;
import com.visiongc.framework.auditoria.AuditoriaService;
import com.visiongc.framework.auditoria.AuditoriasService;
import com.visiongc.framework.auditoria.impl.AuditoriaMedicionServiceImpl;
import com.visiongc.framework.auditoria.impl.AuditoriaServiceImpl;
import com.visiongc.framework.auditoria.impl.AuditoriasServiceImpl;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;
import com.visiongc.framework.importacion.ImportacionService;
import com.visiongc.framework.importacion.impl.ImportacionServiceImpl;
import com.visiongc.framework.importacion.persistence.ImportacionPersistenceSession;
import com.visiongc.framework.message.MessageService;
import com.visiongc.framework.message.impl.MessageServiceImpl;
import com.visiongc.framework.message.persistence.MessagePersistenceSession;
import com.visiongc.framework.modulo.ModuloService;
import com.visiongc.framework.modulo.impl.ModuloServiceImpl;
import com.visiongc.framework.modulo.persistence.ModuloPersistenceSession;
import com.visiongc.framework.persistence.FrameworkPersistenceSession;
import com.visiongc.framework.persistence.FrameworkPersistenceSessionFactory;
import com.visiongc.framework.persistence.ReporteServicioPersistenceSession;
import com.visiongc.framework.transaccion.TransaccionService;
import com.visiongc.framework.transaccion.impl.TransaccionServiceImpl;
import com.visiongc.framework.transaccion.persistence.TransaccionPersistenceSession;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.usuarios.impl.UsuariosServiceImpl;
import com.visiongc.framework.usuarios.persistence.UsuariosPersistenceSession;
import java.sql.*;
import java.util.Locale;

// Referenced classes of package com.visiongc.framework.impl:
//            FrameworkServiceImpl

public class FrameworkServiceFactory extends VgcDefaultServiceFactory
    implements VgcServiceFactory
{

    public static FrameworkServiceFactory getInstance()
    {
        if(instance == null)
            instance = new FrameworkServiceFactory();
        return instance;
    }

    public FrameworkServiceFactory()
    {
        persistenceSessionFactory = null;
        databaseType = null;
        connection = null;
        instance = this;
        try
        {
            persistenceSessionFactory = FrameworkConfiguration.getInstance().getFrameworkPersistenceSessionFactory();
        }
        catch(Throwable ex)
        {
            throw new ChainedRuntimeException("No se pudo inicializar la factor\355a de Servicios de Framework", ex);
        }
    }

    public Byte setDatabaseType()
    {
        if(databaseType == null)
            databaseType = getDatabaseType();
        return databaseType;
    }

    private Byte getDatabaseType()
    {
        Byte databaseType = null;
        try
        {
            String driverName = null;
            if(databaseType == null && connection == null)
            {
                Connection conn = getCurrentSession();
                driverName = conn.getMetaData().getDriverName();
                conn.close();
                conn = null;
            } else
            {
                driverName = connection.getMetaData().getDriverName();
            }
            if(driverName != null)
                if(driverName.toLowerCase().indexOf("sqlserver") > -1)
                    databaseType = Byte.valueOf((byte)2);
                else
                if(driverName.toLowerCase().indexOf("postgresql") > -1)
                    databaseType = Byte.valueOf((byte)1);
                else
                if(driverName.toLowerCase().indexOf("oracle") > -1)
                    databaseType = Byte.valueOf((byte)3);
        }
        catch(SecurityException securityexception) { }
        catch(IllegalArgumentException illegalargumentexception) { }
        catch(SQLException sqlexception) { }
        return databaseType;
    }

    public static Byte getDatabasePostgresql()
    {
        return Byte.valueOf((byte)1);
    }

    public static Byte getDatabaseSqlServer()
    {
        return Byte.valueOf((byte)2);
    }

    public static Byte getDatabaseOracle()
    {
        return Byte.valueOf((byte)3);
    }

    public FrameworkService openFrameworkService()
    {
        return openFrameworkService(null, null);
    }

    public FrameworkService openFrameworkService(Locale locale)
    {
        return openFrameworkService(null, locale);
    }

    public FrameworkService openFrameworkService(VgcService vgcService)
    {
        return openFrameworkService(vgcService, null);
    }

    public FrameworkService openFrameworkService(VgcService vgcService, Locale locale)
    {
        FrameworkPersistenceSession frameworkPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            frameworkPersistenceSession = persistenceSessionFactory.openPersistenceSession();
            persistenceOwned = true;
        } else
        {
            frameworkPersistenceSession = persistenceSessionFactory.openPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new FrameworkServiceImpl(frameworkPersistenceSession, persistenceOwned, this, messageResources);
    }

    public UsuariosService openUsuariosService()
    {
        return openUsuariosService(null, null);
    }

    public UsuariosService openUsuariosService(Locale locale)
    {
        return openUsuariosService(null, locale);
    }

    public UsuariosService openUsuariosService(VgcService vgcService)
    {
        return openUsuariosService(vgcService, null);
    }

    public UsuariosService openUsuariosService(VgcService vgcService, Locale locale)
    {
        UsuariosPersistenceSession usuariosPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            usuariosPersistenceSession = persistenceSessionFactory.openUsuariosPersistenceSession();
            persistenceOwned = true;
        } else
        {
            usuariosPersistenceSession = persistenceSessionFactory.openUsuariosPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new UsuariosServiceImpl(usuariosPersistenceSession, persistenceOwned, this, messageResources);
    }

    public MessageService openMessageService()
    {
        return openMessageService(null, null);
    }

    public MessageService openMessageService(Locale locale)
    {
        return openMessageService(null, locale);
    }

    public MessageService openMessageService(VgcService vgcService)
    {
        return openMessageService(vgcService, null);
    }

    public MessageService openMessageService(VgcService vgcService, Locale locale)
    {
        MessagePersistenceSession messagePersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            messagePersistenceSession = persistenceSessionFactory.openMessagePersistenceSession();
            persistenceOwned = true;
        } else
        {
            messagePersistenceSession = persistenceSessionFactory.openMessagePersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new MessageServiceImpl(messagePersistenceSession, persistenceOwned, this, messageResources);
    }

    public ArbolesService openArbolesService()
    {
        return openArbolesService(null, null);
    }

    public ArbolesService openArbolesService(Locale locale)
    {
        return openArbolesService(null, locale);
    }

    public ArbolesService openArbolesService(VgcService vgcService)
    {
        return openArbolesService(vgcService, null);
    }

    public ArbolesService openArbolesService(VgcService vgcService, Locale locale)
    {
        ArbolesPersistenceSession arbolesPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            arbolesPersistenceSession = persistenceSessionFactory.openArbolesPersistenceSession();
            persistenceOwned = true;
        } else
        {
            arbolesPersistenceSession = persistenceSessionFactory.openArbolesPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new ArbolesServiceImpl(arbolesPersistenceSession, persistenceOwned, this, messageResources);
    }

    public AuditoriaService openAuditoriaService()
    {
        return openAuditoriaService(null, null);
    }

    public AuditoriaService openAuditoriaService(VgcService vgcService)
    {
        return openAuditoriaService(vgcService, null);
    }

    public AuditoriaService openAuditoriaService(VgcService vgcService, Locale locale)
    {
        AuditoriaPersistenceSession auditoriaPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            auditoriaPersistenceSession = persistenceSessionFactory.openAuditoriaPersistenceSession();
            persistenceOwned = true;
        } else
        {
            auditoriaPersistenceSession = persistenceSessionFactory.openAuditoriaPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new AuditoriaServiceImpl(auditoriaPersistenceSession, persistenceOwned, this, messageResources);
    }

    public AuditoriaService openAuditoriaService(VgcPersistenceSession vgcPersistenceSession)
    {
        AuditoriaPersistenceSession auditoriaPersistenceSession = persistenceSessionFactory.openAuditoriaPersistenceSession(vgcPersistenceSession);
        boolean persistenceOwned = false;
        return new AuditoriaServiceImpl(auditoriaPersistenceSession, persistenceOwned, this, null);
    }
    
    public AuditoriaMedicionService openAuditoriaMedicionService()
    {
        return openAuditoriaMedicionService(null, null);
    }

    public AuditoriaMedicionService openAuditoriaMedicionService(VgcService vgcService)
    {
        return openAuditoriaMedicionService(vgcService, null);
    }

    public AuditoriaMedicionService openAuditoriaMedicionService(VgcService vgcService, Locale locale)
    {
        AuditoriaMedicionPersistenceSession auditoriaMedicionPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            auditoriaMedicionPersistenceSession = persistenceSessionFactory.openAuditoriaMedicionPersistenceSession();
            persistenceOwned = true;
        } else
        {
            auditoriaMedicionPersistenceSession = persistenceSessionFactory.openAuditoriaMedicionPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new AuditoriaMedicionServiceImpl(auditoriaMedicionPersistenceSession, persistenceOwned, this, messageResources);
    }

    public AuditoriaMedicionService openAuditoriaMedicionService(VgcPersistenceSession vgcPersistenceSession)
    {
        AuditoriaMedicionPersistenceSession auditoriaMedicionPersistenceSession = persistenceSessionFactory.openAuditoriaMedicionPersistenceSession(vgcPersistenceSession);
        boolean persistenceOwned = false;
        return new AuditoriaMedicionServiceImpl(auditoriaMedicionPersistenceSession, persistenceOwned, this, null);
    }
    
    
    ////////////////////////////////////////////////
    
    public AuditoriasService openAuditoriasService()
    {
        return openAuditoriasService(null, null);
    }

    public AuditoriasService openAuditoriasService(VgcService vgcService)
    {
        return openAuditoriasService(vgcService, null);
    }

    public AuditoriasService openAuditoriasService(VgcService vgcService, Locale locale)
    {
        AuditoriasPersistenceSession auditoriasPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            auditoriasPersistenceSession = persistenceSessionFactory.openAuditoriasPersistenceSession();
            persistenceOwned = true;
        } else
        {
            auditoriasPersistenceSession = persistenceSessionFactory.openAuditoriasPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new AuditoriasServiceImpl(auditoriasPersistenceSession, persistenceOwned, this, messageResources);
    }

    public AuditoriasService openAuditoriasService(VgcPersistenceSession vgcPersistenceSession)
    {
        AuditoriasPersistenceSession auditoriasPersistenceSession = persistenceSessionFactory.openAuditoriasPersistenceSession(vgcPersistenceSession);
        boolean persistenceOwned = false;
        return new AuditoriasServiceImpl(auditoriasPersistenceSession, persistenceOwned, this, null);
    }
    
    //////////////////////////////////////
    
    public ReporteServicioService openReporteServicioService()
    {
        return openReporteServicioService(null, null);
    }

    public ReporteServicioService openReporteServicioService(VgcService vgcService)
    {
        return openReporteServicioService(vgcService, null);
    }

    public ReporteServicioService openReporteServicioService(VgcService vgcService, Locale locale)
    {
        ReporteServicioPersistenceSession reporteServicioPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
        	reporteServicioPersistenceSession = persistenceSessionFactory.openReporteServicioPersistenceSession();
            persistenceOwned = true;
        } else
        {
        	reporteServicioPersistenceSession = persistenceSessionFactory.openReporteServicioPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new ReporteServicioServiceImpl(reporteServicioPersistenceSession, persistenceOwned, this, messageResources);
    }

    public ReporteServicioService openReporteServicio(VgcPersistenceSession vgcPersistenceSession)
    {
    	ReporteServicioPersistenceSession reporteServicioPersistenceSession = persistenceSessionFactory.openReporteServicioPersistenceSession(vgcPersistenceSession);
        boolean persistenceOwned = false;
        return new ReporteServicioServiceImpl(reporteServicioPersistenceSession, persistenceOwned, this, null);
    }
    
    
    ////////////////////////////////////////

    public Connection getCurrentSession()
    {
        if(connection == null)
            connection = persistenceSessionFactory.getCurrentSession();
        return connection;
    }

    public ModuloService openModuloService()
    {
        return openModuloService(null, null);
    }

    public ModuloService openModuloService(Locale locale)
    {
        return openModuloService(null, locale);
    }

    public ModuloService openModuloService(VgcService vgcService)
    {
        return openModuloService(vgcService, null);
    }

    public ModuloService openModuloService(VgcService vgcService, Locale locale)
    {
        ModuloPersistenceSession moduloPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            moduloPersistenceSession = persistenceSessionFactory.openModuloPersistenceSession();
            persistenceOwned = true;
        } else
        {
            moduloPersistenceSession = persistenceSessionFactory.openModuloPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new ModuloServiceImpl(moduloPersistenceSession, persistenceOwned, this, messageResources);
    }

    public ImportacionService openImportacionService()
    {
        return openImportacionService(null, null);
    }

    public ImportacionService openImportacionService(Locale locale)
    {
        return openImportacionService(null, locale);
    }

    public ImportacionService openImportacionService(VgcService vgcService)
    {
        return openImportacionService(vgcService, null);
    }

    public ImportacionService openImportacionService(VgcService vgcService, Locale locale)
    {
        ImportacionPersistenceSession importacionPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            importacionPersistenceSession = persistenceSessionFactory.openImportacionPersistenceSession();
            persistenceOwned = true;
        } else
        {
            importacionPersistenceSession = persistenceSessionFactory.openImportacionPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new ImportacionServiceImpl(importacionPersistenceSession, persistenceOwned, this, messageResources);
    }

    public TransaccionService openTransaccionService()
    {
        return openTransaccionService(null, null);
    }

    public TransaccionService openTransaccionService(Locale locale)
    {
        return openTransaccionService(null, locale);
    }

    public TransaccionService openTransaccionService(VgcService vgcService)
    {
        return openTransaccionService(vgcService, null);
    }

    public TransaccionService openTransaccionService(VgcService vgcService, Locale locale)
    {
        TransaccionPersistenceSession transaccionPersistenceSession;
        boolean persistenceOwned;
        if(vgcService == null)
        {
            transaccionPersistenceSession = persistenceSessionFactory.openTransaccionPersistenceSession();
            persistenceOwned = true;
        } else
        {
            transaccionPersistenceSession = persistenceSessionFactory.openTransaccionPersistenceSession(vgcService.getPersistenceSession());
            persistenceOwned = false;
        }
        VgcMessageResources messageResources;
        if(locale == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        else
            messageResources = VgcResourceManager.getMessageResources(locale, "Framework");
        return new TransaccionServiceImpl(transaccionPersistenceSession, persistenceOwned, this, messageResources);
    }

    private FrameworkPersistenceSessionFactory persistenceSessionFactory;
    private Byte databaseType;
    private Connection connection;
    private static FrameworkServiceFactory instance = null;
    private static final byte DATABASE_POSTGRESQL = 1;
    private static final byte DATABASE_SQLSERVER = 2;
    private static final byte DATABASE_ORACLE = 3;

}