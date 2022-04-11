package com.visiongc.framework.persistence.hibernate;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.*;
import com.visiongc.framework.persistence.FrameworkPersistenceSession;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.hibernate.*;
import org.hibernate.criterion.*;

public class FrameworkHibernateSession extends VgcHibernateSession
    implements FrameworkPersistenceSession
{

    public FrameworkHibernateSession(Session session)
    {
        super(session);
    }

    public FrameworkHibernateSession(VgcHibernateSession parentSession)
    {
        super(parentSession);
    }

    public List getGrupos()
    {
        Session session = getHibernateSession();
        List grupos = session.createCriteria(Grupo.class).addOrder(Order.asc("grupo")).list();
        return grupos;
    }

    public List getUsuarios()
    {
        Session session = getHibernateSession();
        List usuarios = session.createCriteria(Usuario.class).addOrder(Order.asc("fullName")).list();
        return usuarios;
    }

    public PersonaBasica getPersonaPorCedula(String cedula)
    {
        Criteria consulta = session.createCriteria(PersonaBasica.class);
        consulta.add(Restrictions.ilike("cedula", cedula, MatchMode.EXACT));
        PersonaBasica persona = (PersonaBasica)consulta.uniqueResult();
        return persona;
    }

    public Usuario getUsuarioPorLogin(String login)
    {
        Criteria consulta = session.createCriteria(Usuario.class);
        consulta.add(Restrictions.ilike("UId", login, MatchMode.EXACT));
        Usuario usuario = (Usuario)consulta.uniqueResult();
        return usuario;
    }

    public Usuario getUsuarioPorId(Long usuarioId)
    {
        Criteria consulta = session.createCriteria(Usuario.class);
        consulta.add(Restrictions.eq("usuarioId", usuarioId));
        Usuario usuario = (Usuario)consulta.uniqueResult();
        return usuario;
    }

    public boolean verificarUsuarioPermiso(long usuarioId, long organizacionId, String permisoId)
    {
        if(permisoId == null)
            return false;
        Session session = getHibernateSession();
        String sql = (new StringBuilder("SELECT COUNT(AFW_PERMISO_GRUPO.PERMISO_ID) AS TOTAL FROM AFW_PERMISO_GRUPO, AFW_USUARIO_GRUPO WHERE AFW_PERMISO_GRUPO.GRUPO_ID=AFW_USUARIO_GRUPO.GRUPO_ID AND AFW_PERMISO_GRUPO.PERMISO_ID='")).append(permisoId).append("' AND ").append("AFW_USUARIO_GRUPO.USUARIO_ID=").append(usuarioId).toString();
        if(organizacionId != 0L)
            sql = (new StringBuilder(String.valueOf(sql))).append(" AND ").append("AFW_USUARIO_GRUPO.ORGANIZACION_ID=").append(organizacionId).toString();
        Integer nroPermisos = (Integer)session.createSQLQuery(sql).addScalar("TOTAL", Hibernate.INTEGER).uniqueResult();
        return nroPermisos.intValue() > 0;
    }

    public List getPermisosRoot()
    {
        Criteria consulta = session.createCriteria(Permiso.class);
        consulta.add(Restrictions.isNull("padreId"));
        consulta.addOrder(Order.asc("permisoId"));
        consulta.addOrder(Order.asc("grupo"));
        return consulta.list();
    }

    public Organizacion getOrganizacionRoot()
    {
        Criteria consulta = session.createCriteria(Organizacion.class);
        consulta.add(Restrictions.isNull("padreId"));
        Organizacion organizacionRoot = (Organizacion)consulta.uniqueResult();
        return organizacionRoot;
    }

    public List getOrganizacionesRoot()
    {
        Criteria consulta = session.createCriteria(Organizacion.class);
        consulta.add(Restrictions.isNull("padreId"));
        return consulta.list();
    }

    public List getOrganizacionesHijas(Long organizacionId)
    {
        Criteria consulta = session.createCriteria(Organizacion.class);
        consulta.add(Restrictions.eq("padreId", organizacionId));
        consulta.addOrder(Order.asc("nombre"));
        session.clear();
        return consulta.list();
    }

    public boolean existeConfiguracion(ConfiguracionUsuario configuracionUsuario)
    {
        Criteria consulta = session.createCriteria(ConfiguracionUsuario.class);
        consulta.add(Restrictions.eq("pk", configuracionUsuario.getPk()));
        return consulta.list().size() > 0;
    }

    public List getUsuariosPorOrganizacionGrupo(long organizacionId)
    {
        Query consulta = session.createQuery("select usuario from Usuario usuario, UsuarioGrupo usuGrp where usuGrp.pk.organizacionId = :organizacionId and usuGrp.pk.usuarioId = usuario.usuarioId");
        consulta.setLong("organizacionId", organizacionId);
        return consulta.list();
    }

    public int deleteUserSessions(String url)
    {
        return deleteUserSessions(url, null);
    }

    public int deleteUserSessionPorId(String sessionId)
    {
        return deleteUserSessions(null, sessionId);
    }

    private int deleteUserSessions(String url, String sessionId)
    {
        int borrados = 0;
        boolean transActiva = false;
        try
        {
            if(!isTransactionActive())
            {
                beginTransaction();
                transActiva = true;
            }
            if(url != null && !url.equals(""))
            {
                Query consulta = session.createQuery("from UserSession where url = :url");
                consulta.setString("url", url);
                List sesiones = consulta.list();
                Iterator i = sesiones.iterator();
                while(i.hasNext()) 
                {
                    UserSession u = (UserSession)i.next();
                    String hqlDelete = "delete Lock where instancia = :instancia";
                    Query sentencia = session.createQuery(hqlDelete).setString("instancia", u.getSessionId());
                    sentencia.executeUpdate();
                    hqlDelete = "delete LockRead where instancia = :instancia";
                    sentencia = session.createQuery(hqlDelete).setString("instancia", u.getSessionId());
                    sentencia.executeUpdate();
                    UserSession us = (UserSession)load(UserSession.class, u.getSessionId());
                    if(us == null)
                        continue;
                    Usuario usuario = new Usuario();
                    usuario.setUsuarioId(us.getUsuarioId());
                    int resultado = delete(us, usuario);
                    if(resultado != 10000)
                        break;
                    borrados = 1;
                }
            } else
            if(sessionId != null && !sessionId.equals(""))
            {
                String hqlDelete = "delete Lock where instancia = :instancia";
                Query sentencia = session.createQuery(hqlDelete).setString("instancia", sessionId);
                sentencia.executeUpdate();
                hqlDelete = "delete LockRead where instancia = :instancia";
                sentencia = session.createQuery(hqlDelete).setString("instancia", sessionId);
                sentencia.executeUpdate();
                UserSession us = (UserSession)load(UserSession.class, sessionId);
                if(us != null)
                {
                    Usuario usuario = new Usuario();
                    usuario.setUsuarioId(us.getUsuarioId());
                    int resultado = delete(us, usuario);
                    if(resultado == 10000)
                        borrados = 1;
                }
            }
            if(transActiva)
                commitTransaction();
        }
        catch(Exception e)
        {
            if(transActiva)
                rollbackTransaction();
        }
        return borrados;
    }

    public Plantilla loadPlantilla(String tipo, Long objetoId, Long usuarioId)
    {
        Plantilla plantilla = (Plantilla)session.createQuery("from Plantilla where objetoId = :objetoId and tipo = :tipo and usuarioId = :usuarioId").setLong("objetoId", objetoId.longValue()).setString("tipo", tipo).setLong("usuarioId", usuarioId.longValue()).uniqueResult();
        return plantilla;
    }

    public List getPlantillas(Map filtros)
    {
        session.clear();
        Criteria consulta = session.createCriteria(Plantilla.class);
        if(filtros != null)
        {
            Iterator iter = filtros.keySet().iterator();
            String fieldName = null;
            while(iter.hasNext()) 
            {
                fieldName = (String)iter.next();
                if(fieldName.equals("plantillaId"))
                    consulta.add(Restrictions.eq(fieldName, (Long)filtros.get(fieldName)));
                else
                if(fieldName.equals("usuarioId"))
                    consulta.add(Restrictions.eq(fieldName, (Long)filtros.get(fieldName)));
                else
                if(fieldName.equals("tipo"))
                    consulta.add(Restrictions.ilike(fieldName, (String)filtros.get(fieldName), MatchMode.EXACT));
                else
                if(fieldName.equals("publico"))
                    consulta.add(Restrictions.eq(fieldName, filtros.get(fieldName)));
                else
                if(fieldName.equals("orderBy"))
                {
                    ArrayList idList = (ArrayList)filtros.get(fieldName);
                    int j = 1;
                    boolean desc = false;
                    for(Iterator i = idList.listIterator(); i.hasNext();)
                    {
                        String param = (String)i.next();
                        if(j % 2 == 1)
                            if(param.equals("asc"))
                                desc = false;
                            else
                                desc = true;
                        if(j % 2 == 0)
                            if(desc)
                                consulta.addOrder(Order.desc(param));
                            else
                                consulta.addOrder(Order.asc(param));
                        j++;
                    }

                }
            }
        }
        return consulta.list();
    }

    public List getObjetosSistema()
    {
        List objetosSistema = session.createCriteria(ObjetoSistema.class).addOrder(Order.asc("objetoId")).list();
        return objetosSistema;
    }

    public List getMessageResources()
    {
        List messageResources = session.createCriteria(MessageResource.class).addOrder(Order.asc("clave")).list();
        return messageResources;
    }

    public List getPermisosPorUsuario(long usuarioId)
    {
        Query consulta = session.createQuery("select new com.visiongc.framework.model.PermisoUsuario(pg.pk.permisoId, ug.pk.organizacionId, ug.pk.usuarioId) from UsuarioGrupo ug, PermisoGrupo pg where ug.pk.usuarioId = :usuarioId and ug.pk.grupoId = pg.pk.grupoId order by pg.pk.permisoId asc, ug.pk.organizacionId asc");
        consulta.setLong("usuarioId", usuarioId);
        return consulta.list();
    }

    public PaginaLista getUserSessions(int pagina, int tamanoPagina, String atributoOrden, String tipoOrden, boolean getTotal, Map filtros)
    {
        Criteria consulta = prepareQuery(UserSession.class);
        return executeQuery(consulta, pagina, tamanoPagina, atributoOrden, tipoOrden, getTotal);
    }

    public List getBloqueosPorUserSession(String sessionId)
    {
        List bloqueos = new ArrayList();
        Criteria consulta = session.createCriteria(Lock.class);
        consulta.add(Restrictions.eq("instancia", sessionId));
        List locks = consulta.list();
        if(locks.size() > 0)
            bloqueos.addAll(locks);
        consulta = session.createCriteria(LockRead.class);
        consulta.add(Restrictions.eq("pk.instancia", sessionId));
        locks = consulta.list();
        if(locks.size() > 0)
            bloqueos.addAll(locks);
        return bloqueos;
    }

    public PaginaLista getBloqueos(String atributoOrden, String tipoOrden)
    {
        Criteria consulta = prepareQuery(Lock.class);
        return executeQuery(consulta, 0, 0, atributoOrden, tipoOrden, true);
    }

    public PaginaLista getBloqueosLectura(String atributoOrden, String tipoOrden)
    {
        Criteria consulta = prepareQuery(LockRead.class);
        return executeQuery(consulta, 0, 0, atributoOrden, tipoOrden, true);
    }

    public Sistema getSistema(String producto)
    {
        String sql = "select sistema from Sistema sistema";
        if(producto != null && !producto.equals(""))
            sql = (new StringBuilder(String.valueOf(sql))).append(" where lower(sistema.producto) like '").append(producto.toLowerCase()).append("'").toString();
        Query consulta = session.createQuery(sql);
        return (Sistema)consulta.uniqueResult();
    }

    public int deleteDependenciasOrganizacionFramework(Long organizacionId)
    {
        String hqlDelete = "delete UsuarioGrupo ug where ug.pk.organizacionId = :organizacionId";
        int borrados = session.createQuery(hqlDelete).setLong("organizacionId", organizacionId.longValue()).executeUpdate();
        return borrados;
    }

    public int deletePwdHistoria(Long usuarioId)
    {
        String hqlDelete = "delete PwdHistoria pwdHis where usuarioId = :usuarioId";
        int borrados = session.createQuery(hqlDelete).setLong("usuarioId", usuarioId.longValue()).executeUpdate();
        return borrados;
    }

    public PaginaLista getErrores(int pagina, int tamanoPagina, String atributoOrden, String tipoOrden, Map filtros)
    {
        int total = 0;
        String tablasConsulta = "";
        String condicionesConsulta = " where ";
        boolean hayCondicionesConsulta = false;
        Date fecha = null;
        boolean objetoCompleto = false;
        if(filtros != null)
        {
            Iterator iter = filtros.keySet().iterator();
            String fieldName = null;
            while(iter.hasNext()) 
            {
                fieldName = (String)iter.next();
                if(fieldName.equals("errTimestamp"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("error.errTimestamp = :fecha").append(" and ").toString();
                    fecha = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("objetoCompleto"))
                {
                    Object fieldValue = filtros.get(fieldName);
                    if(fieldValue != null)
                        if(fieldValue instanceof Boolean)
                        {
                            if(((Boolean)fieldValue).booleanValue())
                                objetoCompleto = true;
                        } else
                        if((fieldValue instanceof String) && ((String)fieldValue).equalsIgnoreCase("true"))
                            objetoCompleto = true;
                }
            }
        }
        String ordenConsulta = "";
        if(atributoOrden != null && !atributoOrden.equals(""))
            if(tipoOrden == null || tipoOrden.equals(""))
                ordenConsulta = (new StringBuilder(" order by error.")).append(atributoOrden).append(" asc").toString();
            else
            if(tipoOrden.equalsIgnoreCase("asc"))
                ordenConsulta = (new StringBuilder(" order by error.")).append(atributoOrden).append(" asc").toString();
            else
                ordenConsulta = (new StringBuilder(" order by error.")).append(atributoOrden).append(" desc").toString();
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        String objeto = "new Error(error.errTimestamp, error.errDescription)";
        if(objetoCompleto)
            objeto = "new  Error(error.errNumber, error.errSource, error.errDescription, error.errStackTrace, error.errCause, error.errTimestamp, error.errUserId, error.errVersion, error.errStep)";
        Query consulta = session.createQuery((new StringBuilder("select ")).append(objeto).append(" from Error error").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
        if(fecha != null)
            consulta.setTimestamp("fecha", fecha);
        total = consulta.list().size();
        if(tamanoPagina > 0 && pagina > 0)
            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
        List errores = consulta.list();
        PaginaLista paginaLista = new PaginaLista();
        paginaLista.setLista(errores);
        paginaLista.setNroPagina(pagina);
        paginaLista.setTamanoPagina(tamanoPagina);
        paginaLista.setTotal(total);
        paginaLista.setOrden(atributoOrden);
        paginaLista.setTipoOrden(tipoOrden);
        return paginaLista;
    }

    public Configuracion getConfiguracion(String parametro)
    {
        Criteria consulta = session.createCriteria(Configuracion.class);
        consulta.add(Restrictions.eq("parametro", parametro));
        consulta.addOrder(Order.asc("parametro"));
        Configuracion configuracion = (Configuracion)consulta.uniqueResult();
        session.clear();
        return configuracion;
    }

    public List getConfiguraciones()
    {
        List configuraciones = session.createCriteria(Configuracion.class).addOrder(Order.asc("parametro")).list();
        return configuraciones;
    }

    public int deleteObjetoBinarioPorId(Long objetoBinarioId)
    {
        int borrados = 0;
        boolean transActiva = false;
        try
        {
            if(!isTransactionActive())
            {
                beginTransaction();
                transActiva = true;
            }
            String hqlDelete = "delete ObjetoBinario ob where ob.objetoBinarioId = :objetoBinarioId";
            borrados = session.createQuery(hqlDelete).setLong("objetoBinarioId", objetoBinarioId.longValue()).executeUpdate();
            if(transActiva)
                commitTransaction();
        }
        catch(Exception e)
        {
            if(transActiva)
                rollbackTransaction();
        }
        return borrados != 1 ? 10001 : 10000;
    }

    public boolean getAuthenticateParticularUser(String queryValidacion)
    {
        Session session = getHibernateSession();
        String cedula = (String)session.createSQLQuery(queryValidacion).uniqueResult();
        return cedula != null && !cedula.equals("Sin Informacion");
    }

    public PaginaLista getServicios(int pagina, int tamanoPagina, String atributoOrden, String tipoOrden, Map filtros)
    {
        int total = 0;
        String tablasConsulta = "";
        String condicionesConsulta = " where ";
        boolean hayCondicionesConsulta = false;
        Date fecha = null;
        Date fechaDesde = null;
        Date fechaHasta = null;
        if(filtros != null)
        {
            Iterator iter = filtros.keySet().iterator();
            String fieldName = null;
            String fieldValue = null;
            while(iter.hasNext()) 
            {
                fieldName = (String)iter.next();
                if(filtros.get(fieldName) == null)
                    fieldValue = null;
                else
                if(filtros.get(fieldName) instanceof String)
                    fieldValue = (String)filtros.get(fieldName);
                else
                    fieldValue = getValorCondicionConsulta(filtros.get(fieldName));
                if(fieldName.equals("fecha"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.fecha = :fecha").append(" and ").toString();
                    fecha = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("nombre"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.").append(fieldName).append(" = '").append(fieldValue).append("' and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("usuarioId"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("estatus"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.").append(fieldName).append(" = ").append(fieldValue).append(" and ").toString();
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaDesde"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.fecha >= :fechaDesde").append(" and ").toString();
                    fechaDesde = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                } else
                if(fieldName.equals("fechaHasta"))
                {
                    condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.fecha <= :fechaHasta").append(" and ").toString();
                    fechaHasta = (Date)filtros.get(fieldName);
                    hayCondicionesConsulta = true;
                }
            }
        }
        String ordenConsulta = "";
        if(atributoOrden != null && !atributoOrden.equals(""))
            if(tipoOrden == null || tipoOrden.equals(""))
                ordenConsulta = (new StringBuilder(" order by servicio.")).append(atributoOrden).append(" asc").toString();
            else
            if(tipoOrden.equalsIgnoreCase("asc"))
                ordenConsulta = (new StringBuilder(" order by servicio.")).append(atributoOrden).append(" asc").toString();
            else
                ordenConsulta = (new StringBuilder(" order by servicio.")).append(atributoOrden).append(" desc").toString();
        if(hayCondicionesConsulta)
            condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
        else
            condicionesConsulta = "";
        String objeto = "servicio";
        Query consulta = session.createQuery((new StringBuilder("select ")).append(objeto).append(" from Servicio servicio").append(tablasConsulta).append(condicionesConsulta).append(ordenConsulta).toString());
        if(fecha != null)
            consulta.setTimestamp("fecha", fecha);
        if(fechaDesde != null)
        {
            FechaUtil.setHoraInicioDia(fechaDesde);
            consulta.setTimestamp("fechaDesde", fechaDesde);
        }
        if(fechaHasta != null)
        {
            FechaUtil.setHoraFinalDia(fechaHasta);
            consulta.setTimestamp("fechaHasta", fechaHasta);
        }
        total = consulta.list().size();
        if(tamanoPagina > 0 && pagina > 0)
            consulta.setFirstResult(tamanoPagina * pagina - tamanoPagina).setMaxResults(tamanoPagina);
        List servicios = consulta.list();
        Servicio servicio;
        for(Iterator iter = servicios.iterator(); iter.hasNext(); servicio.setUsuario(getUsuarioPorId(servicio.getUsuarioId())))
            servicio = (Servicio)iter.next();

        PaginaLista paginaLista = new PaginaLista();
        paginaLista.setLista(servicios);
        paginaLista.setNroPagina(pagina);
        paginaLista.setTamanoPagina(tamanoPagina);
        paginaLista.setTotal(total);
        paginaLista.setOrden(atributoOrden);
        paginaLista.setTipoOrden(tipoOrden);
        return paginaLista;
    }

    public int setServicioVisto(Long usuarioId, Long fecha, String nombre, Byte estatus)
        throws Exception
    {
        int respuesta = 10000;
        String sql = "update Servicio servicio";
        sql = (new StringBuilder(String.valueOf(sql))).append(" set servicio.estatus = :estatus").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" where servicio.usuarioId = :usuarioId").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" and servicio.fecha = :fecha").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" and servicio.nombre = :nombre").toString();
        Query update = session.createQuery(sql);
        update.setLong("usuarioId", usuarioId.longValue());
        update.setTimestamp("fecha", new Date(fecha.longValue()));
        update.setByte("estatus", estatus.byteValue());
        update.setString("nombre", nombre);
        int actualizaciones = update.executeUpdate();
        if(actualizaciones == 0)
            respuesta = 10001;
        return respuesta;
    }

    public int setAuditoria(Integer auditar)
    {
        boolean transActiva = false;
        try
        {
            if(!isTransactionActive())
            {
                beginTransaction();
                transActiva = true;
            }
            Query update = session.createQuery((new StringBuilder("update ObjetoAuditable objetoAuditable set objetoAuditable.auditoriaActiva = ")).append(auditar).toString());
            update.executeUpdate();
            if(transActiva)
                commitTransaction();
        }
        catch(Exception e)
        {
            if(transActiva)
                rollbackTransaction();
        }
        return 10000;
    }

    public StringBuffer getLog(Long usuarioId, Long fecha, String nombre, Byte tipo)
        throws Exception
    {
        StringBuffer log = new StringBuffer();
        String condicionesConsulta = " where ";
        if(tipo.byteValue() == 1)
        {
            condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.fecha = :fecha and ").toString();
            condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.nombre = '").append(nombre).append("' and ").toString();
            condicionesConsulta = (new StringBuilder(String.valueOf(condicionesConsulta))).append("servicio.usuarioId = ").append(usuarioId).toString();
            Query consulta = session.createQuery((new StringBuilder("select servicio from Servicio servicio")).append(condicionesConsulta).toString());
            consulta.setTimestamp("fecha", new Date(fecha.longValue()));
            List servicios = consulta.list();
            Servicio servicio;
            for(Iterator iter = servicios.iterator(); iter.hasNext(); log.append(servicio.getLog() == null ? "" : servicio.getLog()))
                servicio = (Servicio)iter.next();

        }
        return log;
    }

    public ObjetoBinario leerImagen(Long objetoBinarioId) {
		ObjetoBinario objetoBinario;
		Blob data;
		objetoBinario = null;
		ResultSet rs = null;
		Statement stm = null;
		boolean transActiva = false;
		Connection cn = null;
		data = null;
		try {
			try {
				cn = this.session.connection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
				transActiva = true;
				String sql = "SELECT ";
				sql = String.valueOf(sql) + "objeto_binario_id, ";
				sql = String.valueOf(sql) + "nombre, ";
				sql = String.valueOf(sql) + "mime_type, ";
				sql = String.valueOf(sql) + "data ";
				sql = String.valueOf(sql) + "FROM afw_objeto_binario ";
				sql = String.valueOf(sql) + "WHERE objeto_binario_id = "
						+ objetoBinarioId;
				rs = stm.executeQuery(sql);
				if (rs.next()) {
					objetoBinario = new ObjetoBinario();
					objetoBinario.setObjetoBinarioId(Long.valueOf(rs
							.getLong("objeto_binario_id")));
					objetoBinario.setNombre(rs.getString("nombre"));
					objetoBinario.setMimeType(rs.getString("mime_type"));
					data = rs.getBlob("data");
				}
				cn.commit();
			} catch (Exception e) {
				if (transActiva) {
					try {
						cn.rollback();
					} catch (SQLException sQLException) {
						// empty catch block
					}
				}
				throw new ChainedRuntimeException(e.getMessage(), (Throwable) e);
			}
		} finally {
			try {
				rs.close();
			} catch (Exception exception) {
			}
			try {
				stm.close();
			} catch (Exception exception) {
			}
		}
		if (objetoBinario != null) {
			try {
				objetoBinario.setDataBlob(data);
			} catch (Exception e) {
				// empty catch block
			}
		}
		return objetoBinario;
	}

    public int setVersion(Long sistemaId, Long fecha, Long build, String version)
        throws Exception
    {
        int respuesta = 10000;
        String sql = "update Sistema sistema";
        sql = (new StringBuilder(String.valueOf(sql))).append(" set sistema.fecha = :fecha, ").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" sistema.build = :build, ").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" sistema.version = :version ").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" where sistema.sistemaId = :sistemaId").toString();
        Query update = session.createQuery(sql);
        update.setTimestamp("fecha", new Date(fecha.longValue()));
        update.setLong("build", build.longValue());
        update.setString("version", version);
        update.setLong("sistemaId", sistemaId.longValue());
        int actualizaciones = update.executeUpdate();
        if(actualizaciones == 0)
        {
            respuesta = 10001;
        } else
        {
            DateFormat formatter = new SimpleDateFormat("yyMMdd");
            String fechaStr = formatter.format(new Date(fecha.longValue()));
            Query consulta = session.createQuery((new StringBuilder("select version from Version version where version.version = '")).append(version).append("' and version.build = '").append(build.toString()).append("' and version.dateBuild = '").append(fechaStr).append("'").toString());
            if(consulta.list().size() == 0)
            {
                sql = "insert into afw_version (version, build, dateBuild, createdAt)";
                sql = (new StringBuilder(String.valueOf(sql))).append(" values (?, ?, ?, ?)").toString();
                update = session.createSQLQuery(sql);
                update.setParameter(0, version);
                update.setParameter(1, build.toString());
                update.setParameter(2, fechaStr);
                update.setParameter(3, new Date(), Hibernate.TIMESTAMP);
                actualizaciones = update.executeUpdate();
            }
        }
        return respuesta;
    }

    public int setConectado(Boolean isConnected, Long usuarioId)
        throws Exception
    {
        int respuesta = 10000;
        String sql = "update Usuario usuario";
        sql = (new StringBuilder(String.valueOf(sql))).append(" set usuario.isConnected = :isConnected").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(" where usuario.usuarioId = :usuarioId").toString();
        Query update = session.createQuery(sql);
        update.setBoolean("isConnected", isConnected.booleanValue());
        update.setLong("usuarioId", usuarioId.longValue());
        int actualizaciones = update.executeUpdate();
        if(actualizaciones == 0)
            respuesta = 10001;
        return respuesta;
    }

    public int setSerial(String cmaxc, String producto)
    {
        int respuesta = 10000;
        String sql = "update Sistema sistema";
        sql = (new StringBuilder(String.valueOf(sql))).append(" set sistema.cmaxc = :cmaxc").toString();
        sql = (new StringBuilder(String.valueOf(sql))).append(", sistema.producto = :producto").toString();
        Query update = session.createQuery(sql);
        update.setString("cmaxc", cmaxc);
        update.setString("producto", producto);
        int actualizaciones = update.executeUpdate();
        if(actualizaciones == 0)
            respuesta = 10001;
        return respuesta;
    }
}