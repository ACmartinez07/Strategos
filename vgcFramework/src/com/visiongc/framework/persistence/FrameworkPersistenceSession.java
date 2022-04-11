package com.visiongc.framework.persistence;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.*;
import java.util.List;
import java.util.Map;

public interface FrameworkPersistenceSession
    extends VgcPersistenceSession
{

    public abstract PersonaBasica getPersonaPorCedula(String s);

    public abstract Usuario getUsuarioPorLogin(String s);

    public abstract boolean verificarUsuarioPermiso(long l, long l1, String s);

    public abstract List getGrupos();

    public abstract List getPermisosRoot();

    public abstract Organizacion getOrganizacionRoot();

    public abstract List getOrganizacionesRoot();

    public abstract List getOrganizacionesHijas(Long long1);

    public abstract boolean existeConfiguracion(ConfiguracionUsuario configuracionusuario);

    public abstract List getUsuariosPorOrganizacionGrupo(long l);

    public abstract int deleteUserSessions(String s);

    public abstract int deleteUserSessionPorId(String s);

    public abstract Plantilla loadPlantilla(String s, Long long1, Long long2);

    public abstract List getPlantillas(Map map);

    public abstract List getUsuarios();

    public abstract List getObjetosSistema();

    public abstract List getMessageResources();

    public abstract List getPermisosPorUsuario(long l);

    public abstract PaginaLista getUserSessions(int i, int j, String s, String s1, boolean flag, Map map);

    public abstract List getBloqueosPorUserSession(String s);

    public abstract PaginaLista getBloqueos(String s, String s1);

    public abstract PaginaLista getBloqueosLectura(String s, String s1);

    public abstract Sistema getSistema(String s);

    public abstract int deleteDependenciasOrganizacionFramework(Long long1);

    public abstract PaginaLista getErrores(int i, int j, String s, String s1, Map map);

    public abstract List getConfiguraciones();

    public abstract Configuracion getConfiguracion(String s);

    public abstract int deleteObjetoBinarioPorId(Long long1);

    public abstract boolean getAuthenticateParticularUser(String s);

    public abstract PaginaLista getServicios(int i, int j, String s, String s1, Map map);

    public abstract int setServicioVisto(Long long1, Long long2, String s, Byte byte1)
        throws Exception;

    public abstract StringBuffer getLog(Long long1, Long long2, String s, Byte byte1)
        throws Exception;

    public abstract int setAuditoria(Integer integer);

    public abstract ObjetoBinario leerImagen(Long long1);

    public abstract int setVersion(Long long1, Long long2, Long long3, String s)
        throws Exception;

    public abstract int setConectado(Boolean boolean1, Long long1)
        throws Exception;

    public abstract int setSerial(String s, String s1);
}