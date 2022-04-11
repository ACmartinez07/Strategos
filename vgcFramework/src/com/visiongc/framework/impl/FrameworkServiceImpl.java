package com.visiongc.framework.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.*;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ComparatorNodoArbol;
import com.visiongc.framework.configuracion.VgcConfiguracionPorUsuario;
import com.visiongc.framework.configuracion.VgcConfiguracionesBase;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import com.visiongc.framework.model.*;
import com.visiongc.framework.model.Error;
import com.visiongc.framework.persistence.FrameworkPersistenceSession;
import java.util.*;

// Referenced classes of package com.visiongc.framework.impl:
//            FrameworkServiceFactory

public class FrameworkServiceImpl extends VgcAbstractService
    implements FrameworkService
{

    public FrameworkServiceImpl(FrameworkPersistenceSession persistenceSession, boolean persistenceOwned, FrameworkServiceFactory serviceFactory, VgcMessageResources messageResources)
    {
        super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
        this.persistenceSession = null;
        this.persistenceSession = persistenceSession;
    }

    public void registrarError(Error error) {
		boolean transActiva = false;
		try {
			if (!this.persistenceSession.isTransactionActive()) {
				this.persistenceSession.beginTransaction();
				transActiva = true;
			}
			if (error.getErrTimestamp() == null) {
				error.setErrTimestamp(new Date());
			}
			if (error.getErrNumber() == null) {
				error.setErrNumber(new Long(this.persistenceSession
						.getUniqueId()));
			}
			if (error.getErrUserId() == null) {
				error.setErrUserId("1");
			}
			this.persistenceSession.insert((Object) error, null);
			if (transActiva) {
				this.persistenceSession.commitTransaction();
				transActiva = false;
			}
		} catch (Throwable t) {
			if (transActiva) {
				this.persistenceSession.rollbackTransaction();
			}
			throw new ChainedRuntimeException(t.getMessage(), t);
		}
	}

    public PersonaBasica getPersonaPorCedula(String cedula)
    {
        return persistenceSession.getPersonaPorCedula(cedula);
    }

    public Usuario getUsuarioPorLogin(String login)
    {
        Usuario usuario = persistenceSession.getUsuarioPorLogin(login);
        return usuario;
    }

    public Usuario getUsuarioPorLoginPwd(String login, String pwd, String challenge, String hashFailed)
    {
        Usuario usuario = persistenceSession.getUsuarioPorLogin(login);
        if(usuario != null)
            try
            {
                if(pwd.isEmpty())
                {
                    usuario = null;
                } else
                {
                    String pwdDecriptado = Password.decriptPassWord(usuario.getPwd());
                    String hash = Md5.crearMD5((new StringBuilder(String.valueOf(challenge))).append(pwdDecriptado).toString());
                    String mensaje = null;
                    if(challenge.equals(Password.MD2) || challenge.equals(Password.MD5) || challenge.equals(Password.SHA1) || challenge.equals(Password.SHA256) || challenge.equals(Password.SHA384) || challenge.equals(Password.SHA512))
                        mensaje = Password.getStringMessageDigest(pwd, challenge);
                    if(mensaje == null && !hash.equals(pwd))
                    {
                        if(!pwd.equals(pwdDecriptado) && !Password.decriptJavaScript(hashFailed).equals(pwdDecriptado))
                            usuario = null;
                    } else
                    if(mensaje != null && !mensaje.equals(pwd))
                        usuario = null;
                }
            }
            catch(Throwable e)
            {
                throw new ChainedRuntimeException(e.getMessage(), e);
            }
        return usuario;
    }

    public boolean usuarioTienePermiso(long usuarioId, boolean isAdmin, long ObjetoId, String permisoId, String organizacionId)
    {
        if(isAdmin)
            return true;
        long orgId = 0L;
        if(organizacionId != null && !organizacionId.equals(""))
            orgId = Long.parseLong(organizacionId);
        if(permisoId.equals("true"))
        {
            return true;
        } else
        {
            boolean res = persistenceSession.verificarUsuarioPermiso(usuarioId, orgId, permisoId);
            return res;
        }
    }

    public List getGrupos()
    {
        List grupos = persistenceSession.getGrupos();
        return grupos;
    }

    public Set getPermisosRoot(boolean recursivo)
    {
        TreeSet conj = new TreeSet();
        conj.addAll(persistenceSession.getPermisosRoot());
        if(!recursivo)
        {
            Permiso root;
            for(Iterator i = conj.iterator(); i.hasNext(); root.setHijos(new TreeSet()))
            {
                root = (Permiso)i.next();
                conj.add(root);
            }

        } else
        {
            for(Iterator i = conj.iterator(); i.hasNext();)
            {
                Permiso root = (Permiso)i.next();
                if(root.getHijos().size() > 0)
                    getPermisosRootAux(root.getHijos());
            }

        }
        return conj;
    }

    private void getPermisosRootAux(Set conj)
    {
        for(Iterator i = conj.iterator(); i.hasNext();)
        {
            Permiso hijo = (Permiso)i.next();
            if(hijo.getHijos().size() > 0)
                getPermisosRootAux(hijo.getHijos());
        }

    }

    public Organizacion getOrganizacion(boolean recursivo, Long organizacionId)
    {
        Organizacion organizacionRoot;
        if(organizacionId == null)
            organizacionRoot = persistenceSession.getOrganizacionRoot();
        else
            organizacionRoot = (Organizacion)persistenceSession.load(Organizacion.class, organizacionId);
        if(organizacionRoot != null)
            if(!recursivo)
            {
                organizacionRoot.setHijosCargados(false);
                persistenceSession.clear();
            } else
            {
                organizacionRoot.setHijosCargados(true);
                for(Iterator i = organizacionRoot.getHijos().iterator(); i.hasNext();)
                {
                    Organizacion hijo = (Organizacion)i.next();
                    hijo.setHijosCargados(true);
                    if(hijo.getHijos().size() > 0)
                        getOrganizacionAux(hijo.getHijos());
                }

            }
        return organizacionRoot;
    }

    private void getOrganizacionAux(Set conj)
    {
        for(Iterator i = conj.iterator(); i.hasNext();)
        {
            Organizacion hijo = (Organizacion)i.next();
            if(hijo.getHijos().size() > 0)
                getOrganizacionAux(hijo.getHijos());
        }

    }

    public Organizacion getOrganizacionRoot(boolean recursivo)
    {
        return getOrganizacion(recursivo, null);
    }

    public List getOrganizacionesRoot(boolean recursivo)
    {
        List organizaciones = persistenceSession.getOrganizacionesRoot();
        List resultados = new ArrayList();
        if(recursivo)
        {
            Organizacion org;
            for(Iterator i = organizaciones.iterator(); i.hasNext(); resultados.add(org))
            {
                org = (Organizacion)i.next();
                org = getOrganizacion(true, org.getOrganizacionId());
            }

        } else
        {
            resultados = organizaciones;
        }
        return resultados;
    }

    public void refreshArbolOrganizaciones(Organizacion root, String abiertos, String separador)
    {
        refreshArbolOrganizaciones(root, abiertos, separador, null);
    }

    public void refreshArbolOrganizaciones(Organizacion root, String abiertos, String separador, Long reloadId)
    {
        boolean cargarHijosRoot = false;
        Collection hijosRoot;
        if(!root.getHijosCargados() || reloadId != null && reloadId.equals(root.getOrganizacionId()))
        {
            hijosRoot = persistenceSession.getOrganizacionesHijas(root.getOrganizacionId());
            persistenceSession.clear();
            root.setHijos(new TreeSet(new ComparatorNodoArbol("nombre")));
            cargarHijosRoot = true;
        } else
        {
            hijosRoot = root.getHijos();
        }
        if(reloadId != null && reloadId.equals(root.getOrganizacionId()))
        {
            Organizacion temp = (Organizacion)load(Organizacion.class, root.getOrganizacionId());
            root.setNombre(temp.getNombre());
        }
        Organizacion hijo;
        Collection hijos;
        for(Iterator i = hijosRoot.iterator(); i.hasNext(); refreshArbolOrganizacionesAux(hijo, hijos, abiertos, separador, reloadId))
        {
            hijo = (Organizacion)i.next();
            if(cargarHijosRoot)
                root.getHijos().add(hijo);
            if(!hijo.getHijosCargados() || reloadId != null && reloadId.equals(hijo.getOrganizacionId()))
            {
                hijos = persistenceSession.getOrganizacionesHijas(hijo.getOrganizacionId());
                persistenceSession.clear();
            } else
            {
                hijos = hijo.getHijos();
            }
        }

        root.setHijosCargados(true);
    }

    private void refreshArbolOrganizacionesAux(Organizacion org, Collection orgHijos, String abiertos, String separador, Long reloadId)
    {
        if(!org.getHijosCargados() || reloadId != null && reloadId.equals(org.getOrganizacionId()))
            org.setHijos(new TreeSet(new ComparatorNodoArbol("nombre")));
        if(reloadId != null && reloadId.equals(org.getOrganizacionId()))
        {
            Organizacion temp = (Organizacion)load(Organizacion.class, org.getOrganizacionId());
            org.setNombre(temp.getNombre());
        }
        boolean abrir = false;
        String idBuscado = (new StringBuilder(String.valueOf(separador))).append(org.getOrganizacionId().toString()).append(separador).toString();
        if(abiertos.indexOf(idBuscado) > -1)
            abrir = true;
        for(Iterator i = orgHijos.iterator(); i.hasNext();)
        {
            Organizacion hijo = (Organizacion)i.next();
            if(!org.getHijosCargados() || reloadId != null && reloadId.equals(org.getOrganizacionId()))
                org.getHijos().add(hijo);
            if(abrir)
            {
                Collection hijos;
                if(!hijo.getHijosCargados() || reloadId != null && reloadId.equals(hijo.getOrganizacionId()))
                {
                    hijos = persistenceSession.getOrganizacionesHijas(hijo.getOrganizacionId());
                    persistenceSession.clear();
                } else
                {
                    hijos = hijo.getHijos();
                }
                refreshArbolOrganizacionesAux(hijo, hijos, abiertos, separador, reloadId);
            }
        }

        if(!org.getHijosCargados())
            org.setHijosCargados(true);
    }

    public int saveConfiguracionUsuario(ConfiguracionUsuario configuracionUsuario, Usuario usuario)
    {
        boolean transActiva = false;
        int resDb = 10000;
        String fieldNames[] = new String[3];
        Object fieldValues[] = new Object[3];
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            fieldNames[0] = "pk.usuarioId";
            fieldValues[0] = configuracionUsuario.getPk().getUsuarioId();
            fieldNames[1] = "pk.configuracionBase";
            fieldValues[1] = configuracionUsuario.getPk().getConfiguracionBase();
            fieldNames[2] = "pk.objeto";
            fieldValues[2] = configuracionUsuario.getPk().getObjeto();
            boolean existe = false;
            if(persistenceSession.existsObject(configuracionUsuario, fieldNames, fieldValues))
                existe = true;
            if(existe)
                resDb = persistenceSession.update(configuracionUsuario, usuario);
            else
                resDb = persistenceSession.insert(configuracionUsuario, usuario);
            if(transActiva)
            {
                if(resDb == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return resDb;
    }

    public int deleteConfiguracionUsuario(ConfiguracionUsuario configuracionUsuario, Usuario usuario)
    {
        int resultado = 10000;
        boolean transActiva = false;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            resultado = persistenceSession.delete(configuracionUsuario, usuario);
            if(resultado == 10000 || resultado == 10001)
            {
                if(transActiva)
                {
                    persistenceSession.commitTransaction();
                    transActiva = false;
                }
            } else
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                throw new ChainedRuntimeException(t.getMessage(), t);
            }
        }
        return resultado;
    }

    public int saveObjetoBinario(ObjetoBinario objetoBinario, Usuario usuario)
    {
        boolean transActiva = false;
        int resultado = 10000;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            if(objetoBinario.getObjetoBinarioId() == null)
            {
                objetoBinario.setObjetoBinarioId(new Long(persistenceSession.getUniqueId()));
                resultado = persistenceSession.insert(objetoBinario, usuario);
            }
            if(resultado == 10000)
            {
                String fieldNames[] = new String[1];
                Object fieldValues[] = new Object[1];
                fieldNames[0] = "objeto_binario_id";
                fieldValues[0] = objetoBinario.getObjetoBinarioId();
                resultado = persistenceSession.saveBlob("afw_objeto_binario", "data", objetoBinario.getData(), fieldNames, fieldValues);
            }
            if(transActiva)
            {
                if(resultado == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return resultado;
    }

    public int deleteObjetoBinarioPorId(Long objetoBinarioId)
    {
        return persistenceSession.deleteObjetoBinarioPorId(objetoBinarioId);
    }

    public List getUsuariosPorOrganizacionGrupo(long organizacionId)
    {
        return persistenceSession.getUsuariosPorOrganizacionGrupo(organizacionId);
    }

    public int saveUserSession(UserSession userSession, Usuario usuario)
    {
        boolean transActiva = false;
        int res = 10000;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            persistenceSession.deleteUserSessionPorId(userSession.getSessionId());
            res = persistenceSession.insert(userSession, usuario);
            if(transActiva)
            {
                if(res == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return res;
    }

    public void clearUserSessions(String url)
    {
        persistenceSession.deleteUserSessions(url);
    }

    public void clearUserSessionPorId(String sessionId)
    {
        persistenceSession.deleteUserSessionPorId(sessionId);
    }

    public int savePlantilla(Plantilla plantilla, Usuario usuario)
    {
        boolean transActiva = false;
        int res = 10007;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            if(plantilla.getPlantillaId() == null || plantilla.getPlantillaId().longValue() == 0L)
            {
                plantilla.setPlantillaId(new Long(persistenceSession.getUniqueId()));
                res = persistenceSession.insert(plantilla, usuario);
            } else
            {
                res = persistenceSession.update(plantilla, usuario);
            }
            if(transActiva)
            {
                if(res == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return res;
    }

    public Plantilla loadPlantilla(String tipo, Long objetoId, Long usuarioId)
    {
        return persistenceSession.loadPlantilla(tipo, objetoId, usuarioId);
    }

    public List getPlantillas(Map filtros)
    {
        return persistenceSession.getPlantillas(filtros);
    }

    public List getUsuarios()
    {
        List usuarios = persistenceSession.getUsuarios();
        return usuarios;
    }

    public List getObjetosSistema()
    {
        return persistenceSession.getObjetosSistema();
    }

    public List getMessageResources()
    {
        return persistenceSession.getMessageResources();
    }

    public List getPermisosPorUsuario(long usuarioId)
    {
        return persistenceSession.getPermisosPorUsuario(usuarioId);
    }

    public PaginaLista getUserSessions(int pagina, int tamanoPagina, String atributoOrden, String tipoOrden)
    {
        PaginaLista paginaSesiones = persistenceSession.getUserSessions(pagina, tamanoPagina, atributoOrden, tipoOrden, true, null);
        UserSession us;
        for(Iterator i = paginaSesiones.getLista().iterator(); i.hasNext(); us.setBloqueos(getBloqueosPorUserSession(us.getSessionId())))
            us = (UserSession)i.next();

        return paginaSesiones;
    }

    public List getBloqueosPorUserSession(String sessionId)
    {
        return persistenceSession.getBloqueosPorUserSession(sessionId);
    }

    public PaginaLista getBloqueos(String atributoOrden, String tipoOrden)
    {
        return persistenceSession.getBloqueos(atributoOrden, tipoOrden);
    }

    public PaginaLista getBloqueosLectura(String atributoOrden, String tipoOrden)
    {
        return persistenceSession.getBloqueosLectura(atributoOrden, tipoOrden);
    }

    public boolean deleteBloqueo(String sessionId, String objetoId, String tipo)
    {
        boolean transActiva = false;
        int resDb = 10000;
        boolean borrado = false;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            Object bloqueo = null;
            if(tipo != null && (tipo.equalsIgnoreCase("D") || tipo.equalsIgnoreCase("U")))
                bloqueo = load(Lock.class, new LockPK(new Long(objetoId), tipo));
            else
                bloqueo = load(LockRead.class, new LockReadPK(new Long(objetoId), sessionId));
            resDb = persistenceSession.delete(bloqueo, null);
            if(resDb == 10000)
                borrado = true;
            if(transActiva)
                if(resDb == 10000)
                {
                    persistenceSession.commitTransaction();
                    transActiva = false;
                } else
                {
                    persistenceSession.rollbackTransaction();
                    transActiva = false;
                }
        }
        catch(Throwable t)
        {
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                throw new ChainedRuntimeException(t.getMessage(), t);
            }
        }
        return borrado;
    }

    public ConfiguracionUsuario getConfiguracionUsuario(Long usuarioId, String configuracionBase, String objeto)
    {
        ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
        pk.setUsuarioId(usuarioId);
        pk.setObjeto(objeto);
        pk.setConfiguracionBase(configuracionBase);
        return (ConfiguracionUsuario)persistenceSession.load(ConfiguracionUsuario.class, pk);
    }

    public int deleteDependenciasOrganizacionFramework(Long organizacionId, Usuario usuario)
    {
        boolean transActiva = false;
        int resultado = 10000;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            persistenceSession.deleteDependenciasOrganizacionFramework(organizacionId);
            if(resultado == 10000)
            {
                if(transActiva)
                {
                    persistenceSession.commitTransaction();
                    transActiva = false;
                }
            } else
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                throw new ChainedRuntimeException(t.getMessage(), t);
            }
        }
        return resultado;
    }

    public PaginaLista getErrores(int pagina, int tamanoPagina, String atributoOrden, String tipoOrden, Map filtros)
    {
        return persistenceSession.getErrores(pagina, tamanoPagina, atributoOrden, tipoOrden, filtros);
    }

    public List getConfiguraciones()
    {
        return persistenceSession.getConfiguraciones();
    }

    public Configuracion getConfiguracion(String parametro)
    {
        return persistenceSession.getConfiguracion(parametro);
    }

    public int saveConfiguracion(Configuracion configuracion, Usuario gestor)
    {
        boolean transActiva = false;
        int resDb = 10000;
        String fieldNames[] = new String[1];
        Object fieldValues[] = new Object[1];
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            fieldNames[0] = "parametro";
            fieldValues[0] = configuracion.getParametro();
            if(persistenceSession.existsObject(configuracion, fieldNames, fieldValues))
                resDb = persistenceSession.update(configuracion, gestor);
            else
                resDb = persistenceSession.insert(configuracion, gestor);
            if(transActiva)
            {
                if(resDb == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return resDb;
    }

    public int deleteConfiguracion(Configuracion configuracion, Usuario usuario)
    {
        int resultado = 10000;
        boolean transActiva = false;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            resultado = persistenceSession.delete(configuracion, usuario);
            if(resultado == 10000 || resultado == 10001)
            {
                if(transActiva)
                {
                    persistenceSession.commitTransaction();
                    transActiva = false;
                }
            } else
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
            {
                persistenceSession.rollbackTransaction();
                throw new ChainedRuntimeException(t.getMessage(), t);
            }
        }
        return resultado;
    }

    public ConfiguracionPagina getConfiguracionPagina(String nombreConfiguracionBase, Long usuarioId)
    {
        ConfiguracionUsuario configuracionUsuario = getConfiguracionUsuario(usuarioId, nombreConfiguracionBase, "configuracionPagina");
        VgcConfiguracionesBase configuracionBase = VgcConfiguracionPorUsuario.getInstance("com.visiongc.framework.configuracion.FrameworkConfiguracionesBase").getConfiguracionesBase();
        ConfiguracionPagina configuracionPagina = (ConfiguracionPagina)configuracionBase.getObjetoConfiguracionBase("configuracionPagina");
        configuracionPagina.setNombreConfiguracionBase(nombreConfiguracionBase);
        if(configuracionUsuario != null)
        {
            configuracionPagina.readFromXml(configuracionUsuario.getData());
            configuracionPagina.setConfiguracionUsuario(configuracionUsuario);
        }
        return configuracionPagina;
    }

    public int saveConfiguracionPagina(ConfiguracionPagina configuracionPagina, Usuario usuario)
    {
        boolean transActiva = false;
        int resDb = 10000;
        try
        {
            if(!persistenceSession.isTransactionActive())
            {
                persistenceSession.beginTransaction();
                transActiva = true;
            }
            Long imagenIdsViejos[] = new Long[6];
            ConfiguracionUsuario configuracionUsuario = configuracionPagina.getConfiguracionUsuario();
            if(configuracionUsuario == null)
            {
                configuracionUsuario = new ConfiguracionUsuario();
                ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
                pk.setConfiguracionBase(configuracionPagina.getNombreConfiguracionBase());
                pk.setObjeto("configuracionPagina");
                pk.setUsuarioId(usuario.getUsuarioId());
                configuracionUsuario.setPk(pk);
            }
            guardarImagenes(configuracionPagina, usuario, imagenIdsViejos);
            configuracionUsuario.setData(configuracionPagina.getXml());
            resDb = saveConfiguracionUsuario(configuracionUsuario, usuario);
            if(resDb == 10000)
                eliminarImagenesViejas(configuracionPagina, usuario, imagenIdsViejos);
            if(transActiva)
            {
                if(resDb == 10000)
                    persistenceSession.commitTransaction();
                else
                    persistenceSession.rollbackTransaction();
                transActiva = false;
            }
        }
        catch(Throwable t)
        {
            if(transActiva)
                persistenceSession.rollbackTransaction();
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
        return resDb;
    }

    private void guardarImagenes(ConfiguracionPagina configuracionPagina, Usuario usuario, Long idsViejos[])
        throws Exception
    {
        idsViejos[0] = configuracionPagina.getImagenSupIzqId();
        idsViejos[1] = configuracionPagina.getImagenSupCenId();
        idsViejos[2] = configuracionPagina.getImagenSupDerId();
        idsViejos[3] = configuracionPagina.getImagenInfIzqId();
        idsViejos[4] = configuracionPagina.getImagenInfCenId();
        idsViejos[5] = configuracionPagina.getImagenInfDerId();
        Long imagenId = guardarImagen(1, configuracionPagina, usuario);
        if(imagenId != null)
            configuracionPagina.setImagenSupIzqId(imagenId);
        else
        if(configuracionPagina.getImagenSupIzq().getNombre() == null || configuracionPagina.getImagenSupIzq().getNombre().equals(""))
            configuracionPagina.setImagenSupIzqId(null);
        imagenId = guardarImagen(2, configuracionPagina, usuario);
        if(imagenId != null)
            configuracionPagina.setImagenSupCenId(imagenId);
        else
        if(configuracionPagina.getImagenSupCen().getNombre() == null || configuracionPagina.getImagenSupCen().getNombre().equals(""))
            configuracionPagina.setImagenSupCenId(null);
        imagenId = guardarImagen(3, configuracionPagina, usuario);
        if(imagenId != null)
            configuracionPagina.setImagenSupDerId(imagenId);
        else
        if(configuracionPagina.getImagenSupDer().getNombre() == null || configuracionPagina.getImagenSupDer().getNombre().equals(""))
            configuracionPagina.setImagenSupDerId(null);
        imagenId = guardarImagen(4, configuracionPagina, usuario);
        if(imagenId != null)
            configuracionPagina.setImagenInfIzqId(imagenId);
        else
        if(configuracionPagina.getImagenInfIzq().getNombre() == null || configuracionPagina.getImagenInfIzq().getNombre().equals(""))
            configuracionPagina.setImagenInfIzqId(null);
        imagenId = guardarImagen(5, configuracionPagina, usuario);
        if(imagenId != null)
            configuracionPagina.setImagenInfCenId(imagenId);
        else
        if(configuracionPagina.getImagenInfCen().getNombre() == null || configuracionPagina.getImagenInfCen().getNombre().equals(""))
            configuracionPagina.setImagenInfCenId(null);
        imagenId = guardarImagen(6, configuracionPagina, usuario);
        if(imagenId != null)
            configuracionPagina.setImagenInfDerId(imagenId);
        else
        if(configuracionPagina.getImagenInfDer().getNombre() == null || configuracionPagina.getImagenInfDer().getNombre().equals(""))
            configuracionPagina.setImagenInfDerId(null);
    }

    private Long guardarImagen(int numero, ConfiguracionPagina configuracionPagina, Usuario usuario)
        throws Exception
    {
        Long imagenId = null;
        ObjetoBinario objetoBinario = null;
        if(numero == 1)
            objetoBinario = configuracionPagina.getImagenSupIzq();
        else
        if(numero == 2)
            objetoBinario = configuracionPagina.getImagenSupCen();
        else
        if(numero == 3)
            objetoBinario = configuracionPagina.getImagenSupDer();
        else
        if(numero == 4)
            objetoBinario = configuracionPagina.getImagenInfIzq();
        else
        if(numero == 5)
            objetoBinario = configuracionPagina.getImagenInfCen();
        else
        if(numero == 6)
            objetoBinario = configuracionPagina.getImagenInfDer();
        if(objetoBinario != null && objetoBinario.getData() != null && objetoBinario.getData().length > 0)
        {
            int resultadoBlob = saveObjetoBinario(objetoBinario, usuario);
            if(resultadoBlob == 10000)
                imagenId = objetoBinario.getObjetoBinarioId();
        }
        return imagenId;
    }

    private void eliminarImagenVieja(Usuario usuario, Long idViejo, Long idNuevo)
    {
        if(idViejo != null)
            if(idNuevo == null)
                deleteObjetoBinarioPorId(idViejo);
            else
            if(!idViejo.equals(idNuevo))
                deleteObjetoBinarioPorId(idViejo);
    }

    private void eliminarImagenesViejas(ConfiguracionPagina configuracionPagina, Usuario usuario, Long idsViejos[])
    {
        eliminarImagenVieja(usuario, idsViejos[0], configuracionPagina.getImagenSupIzqId());
        eliminarImagenVieja(usuario, idsViejos[1], configuracionPagina.getImagenSupCenId());
        eliminarImagenVieja(usuario, idsViejos[2], configuracionPagina.getImagenSupDerId());
        eliminarImagenVieja(usuario, idsViejos[3], configuracionPagina.getImagenInfIzqId());
        eliminarImagenVieja(usuario, idsViejos[4], configuracionPagina.getImagenInfCenId());
        eliminarImagenVieja(usuario, idsViejos[5], configuracionPagina.getImagenInfDerId());
    }

    public boolean getAuthenticateParticularUser(String queryValidacion)
    {
        return persistenceSession.getAuthenticateParticularUser(queryValidacion);
    }

    public PaginaLista getServicios(int pagina, int tamanoPagina, String atributoOrden, String tipoOrden, Map filtros)
    {
        return persistenceSession.getServicios(pagina, tamanoPagina, atributoOrden, tipoOrden, filtros);
    }

    public int setServicioVisto(Long usuarioId, Long fecha, String nombre, Byte estatus)
        throws Exception
    {
        return persistenceSession.setServicioVisto(usuarioId, fecha, nombre, estatus);
    }

    public StringBuffer getLog(Long usuarioId, Long fecha, String nombre, Byte tipo)
        throws Exception
    {
        return persistenceSession.getLog(usuarioId, fecha, nombre, tipo);
    }

    public int setAuditoria(Integer auditar)
    {
        return persistenceSession.setAuditoria(auditar);
    }

    public ObjetoBinario leerImagen(Long objetoBinarioId)
    {
        return persistenceSession.leerImagen(objetoBinarioId);
    }

    public int setVersion(Long sistemaId, Long fecha, Long build, String version)
        throws Exception
    {
        return persistenceSession.setVersion(sistemaId, fecha, build, version);
    }

    public Sistema getSistema(String producto)
    {
        return persistenceSession.getSistema(producto);
    }

    public int setConectado(Boolean isConnected, Long usuarioId)
        throws Exception
    {
        return persistenceSession.setConectado(isConnected, usuarioId);
    }

    public int setSerial(String cmaxc, String producto)
    {
        return persistenceSession.setSerial(cmaxc, producto);
    }

    private FrameworkPersistenceSession persistenceSession;
}