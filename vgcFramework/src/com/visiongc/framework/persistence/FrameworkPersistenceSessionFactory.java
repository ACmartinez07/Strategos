package com.visiongc.framework.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.framework.arboles.persistence.ArbolesPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriaPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;
import com.visiongc.framework.importacion.persistence.ImportacionPersistenceSession;
import com.visiongc.framework.message.persistence.MessagePersistenceSession;
import com.visiongc.framework.modulo.persistence.ModuloPersistenceSession;
import com.visiongc.framework.transaccion.persistence.TransaccionPersistenceSession;
import com.visiongc.framework.usuarios.persistence.UsuariosPersistenceSession;
import java.sql.Connection;

// Referenced classes of package com.visiongc.framework.persistence:
//            FrameworkPersistenceSession

public interface FrameworkPersistenceSessionFactory
{

    public abstract FrameworkPersistenceSession openPersistenceSession();

    public abstract FrameworkPersistenceSession openPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract UsuariosPersistenceSession openUsuariosPersistenceSession();

    public abstract UsuariosPersistenceSession openUsuariosPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract MessagePersistenceSession openMessagePersistenceSession();

    public abstract MessagePersistenceSession openMessagePersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract ArbolesPersistenceSession openArbolesPersistenceSession();

    public abstract ArbolesPersistenceSession openArbolesPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract AuditoriaPersistenceSession openAuditoriaPersistenceSession();

    public abstract AuditoriaPersistenceSession openAuditoriaPersistenceSession(VgcPersistenceSession vgcpersistencesession);
    
    public abstract AuditoriaMedicionPersistenceSession openAuditoriaMedicionPersistenceSession();

    public abstract AuditoriaMedicionPersistenceSession openAuditoriaMedicionPersistenceSession(VgcPersistenceSession vgcpersistencesession);
    
    public abstract AuditoriasPersistenceSession openAuditoriasPersistenceSession();

    public abstract AuditoriasPersistenceSession openAuditoriasPersistenceSession(VgcPersistenceSession vgcpersistencesession);
    
    public abstract ReporteServicioPersistenceSession openReporteServicioPersistenceSession();

    public abstract ReporteServicioPersistenceSession openReporteServicioPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract ModuloPersistenceSession openModuloPersistenceSession();

    public abstract ModuloPersistenceSession openModuloPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract ImportacionPersistenceSession openImportacionPersistenceSession();

    public abstract ImportacionPersistenceSession openImportacionPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract TransaccionPersistenceSession openTransaccionPersistenceSession();

    public abstract TransaccionPersistenceSession openTransaccionPersistenceSession(VgcPersistenceSession vgcpersistencesession);

    public abstract Connection getCurrentSession();
}