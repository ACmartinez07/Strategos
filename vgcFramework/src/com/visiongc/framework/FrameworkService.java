package com.visiongc.framework;

import com.visiongc.commons.VgcService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Error;
import com.visiongc.framework.model.ObjetoBinario;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.PersonaBasica;
import com.visiongc.framework.model.Plantilla;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.model.UserSession;
import com.visiongc.framework.model.Usuario;
import java.util.*;

public interface FrameworkService
    extends VgcService
{

    public abstract PersonaBasica getPersonaPorCedula(String s);

    public abstract Usuario getUsuarioPorLoginPwd(String s, String s1, String s2, String s3);

    public abstract Usuario getUsuarioPorLogin(String s);

    public abstract void registrarError(Error error);

    public abstract boolean usuarioTienePermiso(long l, boolean flag, long l1, String s, String s1);

    public abstract Set getPermisosRoot(boolean flag);

    public abstract List getGrupos();

    public abstract Organizacion getOrganizacionRoot(boolean flag);

    public abstract List getOrganizacionesRoot(boolean flag);

    public abstract Organizacion getOrganizacion(boolean flag, Long long1);

    public abstract void refreshArbolOrganizaciones(Organizacion organizacion, String s, String s1);

    public abstract void refreshArbolOrganizaciones(Organizacion organizacion, String s, String s1, Long long1);

    public abstract PaginaLista getUserSessions(int i, int j, String s, String s1);

    public abstract int saveUserSession(UserSession usersession, Usuario usuario);

    public abstract void clearUserSessions(String s);

    public abstract void clearUserSessionPorId(String s);

    public abstract List getBloqueosPorUserSession(String s);

    public abstract PaginaLista getBloqueos(String s, String s1);

    public abstract PaginaLista getBloqueosLectura(String s, String s1);

    public abstract boolean deleteBloqueo(String s, String s1, String s2);

    public abstract int deleteDependenciasOrganizacionFramework(Long long1, Usuario usuario);

    public abstract ConfiguracionUsuario getConfiguracionUsuario(Long long1, String s, String s1);

    public abstract int saveConfiguracionUsuario(ConfiguracionUsuario configuracionusuario, Usuario usuario);

    public abstract int deleteConfiguracionUsuario(ConfiguracionUsuario configuracionusuario, Usuario usuario);

    public abstract int saveObjetoBinario(ObjetoBinario objetobinario, Usuario usuario);

    public abstract int deleteObjetoBinarioPorId(Long long1);

    public abstract ConfiguracionPagina getConfiguracionPagina(String s, Long long1);

    public abstract int saveConfiguracionPagina(ConfiguracionPagina configuracionpagina, Usuario usuario);

    public abstract List getUsuariosPorOrganizacionGrupo(long l);

    public abstract int savePlantilla(Plantilla plantilla, Usuario usuario);

    public abstract Plantilla loadPlantilla(String s, Long long1, Long long2);

    public abstract List getPlantillas(Map map);

    public abstract List getUsuarios();

    public abstract List getObjetosSistema();

    public abstract List getMessageResources();

    public abstract List getConfiguraciones();

    public abstract Configuracion getConfiguracion(String s);

    public abstract int saveConfiguracion(Configuracion configuracion, Usuario usuario);

    public abstract int deleteConfiguracion(Configuracion configuracion, Usuario usuario);

    public abstract List getPermisosPorUsuario(long l);

    public abstract PaginaLista getErrores(int i, int j, String s, String s1, Map map);

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

    public abstract Sistema getSistema(String s);

    public abstract int setConectado(Boolean boolean1, Long long1)
        throws Exception;

    public abstract int setSerial(String s, String s1);
}