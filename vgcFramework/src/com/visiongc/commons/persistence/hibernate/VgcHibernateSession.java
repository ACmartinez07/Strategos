package com.visiongc.commons.persistence.hibernate;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;
import javax.tools.JavaFileObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.Work;
import org.json.JSONException;
import org.json.JSONObject;

import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.graficos.model.Grafico;
import com.visiongc.app.strategos.indicadores.model.CategoriaIndicador;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.reportes.model.Reporte;
import com.visiongc.app.strategos.responsables.model.GrupoResponsable;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.VgcConfiguration;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.persistence.lob.BlobImpl;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkConfiguration;
import com.visiongc.framework.auditoria.ObjetosAuditables;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaAtributo;
import com.visiongc.framework.auditoria.model.AuditoriaEntero;
import com.visiongc.framework.auditoria.model.AuditoriaEvento;
import com.visiongc.framework.auditoria.model.AuditoriaFecha;
import com.visiongc.framework.auditoria.model.AuditoriaFlotante;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.model.AuditoriaMemo;
import com.visiongc.framework.auditoria.model.AuditoriaString;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoAtributo;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.Grupo;
import com.visiongc.framework.model.Lock;
import com.visiongc.framework.model.LockPK;
import com.visiongc.framework.model.LockRead;
import com.visiongc.framework.model.LockReadPK;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.UserSession;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.persistence.FrameworkPersistenceSession;
import com.visiongc.framework.persistence.FrameworkPersistenceSessionFactory;
import com.visiongc.framework.persistence.hibernate.FrameworkHibernateSession;
import com.visiongc.servicio.strategos.calculos.impl.CalculoManager;
import com.visiongc.servicio.strategos.servicio.model.Servicio;

/**
 * 
 *  (03/03/2012)
 * 
 * @author Kerwin
 */
public class VgcHibernateSession implements VgcPersistenceSession 
{
	protected Session session = null;

	/** Transaccion activa (Solo se utiliza en mainSession) */
	/** Solo hay una transaction por mainSession */
	protected Transaction transaction = null;

	/** Sesion de Persistencia Principal (No tiene padre) */
	protected VgcHibernateSession mainSession = null;

	public void clear() 
	{
		this.session.clear();
	}

	public void evictFromSession(Object objeto) 
	{
		this.session.evict(objeto);
	}

	/**
	 * 
	 *  (03/03/2012)
	 * 
	 * @param session
	 *            : objeto session de hibernate
	 */
	public VgcHibernateSession(Session session) 
	{
		this.session = session;
		this.mainSession = this;
		/**
		 * Solo se instancia la lista de sesiones hijas para el servicio padre
		 * de todos
		 */
		this.session.setFlushMode(FlushMode.COMMIT);
	}

	/**
	 * 
	 *  (19/02/2012)
	 * 
	 * @param session
	 * @param transaction
	 */
	public VgcHibernateSession(VgcHibernateSession parentSession) 
	{
		this.session = parentSession.session;
		this.session.setFlushMode(FlushMode.COMMIT);
		this.session.setCacheMode(CacheMode.IGNORE);
		this.mainSession = parentSession.mainSession;
	}

	public Session getHibernateSession() 
	{
		return session;
	}

	/**
	 * 
	 * @return Creado por: Kerwin Arias (03/03/2012)
	 */
	public Transaction getHibernateTransaction() 
	{
		return mainSession.transaction;
	}

	public void beginTransaction() 
	{
		try 
		{
			if (!this.isTransactionActive()) 
				mainSession.transaction = session.beginTransaction();
		} 
		catch (HibernateException e) 
		{
			/** Se anula la transacción por el error */
			mainSession.transaction = null;
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no se pudo iniciar la transacción", he);
		}
	}

	public void commitTransaction() 
	{
		try 
		{
			session.flush();
			if (this.isTransactionActive()) 
				mainSession.transaction.commit();
		} 
		catch (ConstraintViolationException e) 
		{
			if (this.isTransactionActive()) 
				this.rollbackTransaction();
			throw e;
		} 
		catch (HibernateException e) 
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no se pudo aplicar la transacción", he);
		} 
		finally 
		{
			mainSession.transaction = null;
		}
	}

	public boolean isTransactionActive() 
	{
		return mainSession.transaction != null;
	}

	public void rollbackTransaction() 
	{
		try 
		{
			if (this.isTransactionActive()) 
			{
				mainSession.transaction.rollback();
			}
		} 
		catch (HibernateException e) 
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no se pudo deshacer la transacción", he);
		} 
		finally 
		{
			mainSession.transaction = null;
		}
	}

	public void close() 
	{
		try 
		{
			if (session != null) 
				session.close();
			session = null;
		} 
		catch (HibernateException e) 
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			e.printStackTrace();
			throw new ChainedRuntimeException("no pudo cerrarse la sesión de persistencia hibernate", he);
		}
	}

	/**
	 *  (01/04/2012)
	 */
	public Object load(Class clazz, Long id) 
	{
		Object o = session.get(clazz, id);
		return o;
	}

	/**
	 * Creado por: Kerwin Arias (30/06/2012)
	 */
	public Object reload(Class claseObjeto, String nombreCampoClave, Long objetoId) throws Exception 
	{
		Query q = session.createQuery("from " + claseObjeto.getName() + " where " + nombreCampoClave + "=?");
		q.setLong(0, objetoId.longValue());
		q.setCacheMode(CacheMode.IGNORE);
		List resultados = q.list();
		Object objeto = null;
		if (resultados.size() > 0) 
			objeto = resultados.get(0);

		return objeto;
	}

	/**
	 *  (01/04/2012)
	 */
	public Object load(Class clazz, Serializable id) 
	{
		Object o = null;
		
		try
		{
			o = session.get(clazz, id);
		}
		catch (HibernateException e) 
		{
			o = null;
		}
		
		return o;
	}

	public void lock(Object o) 
	{
		try 
		{
			session.lock(o, LockMode.UPGRADE);
		} 
		catch (HibernateException e) 
		{
			throw new ChainedRuntimeException("no pudo aplicarse un bloqueo sobre '" + o + "' en la base de datos", e);
		}
	}

	public int insert(Object objeto, Usuario usuario) 
	{
		
		System.out.print("\n\nEntra al insert \n\n");
		try 
		{
			if (objeto instanceof Collection) 
			{
				Iterator iter = ((Collection) objeto).iterator();
				while (iter.hasNext()) 
				{
					insert(iter.next(), usuario);
				}
			} 
			else 
			{
				session.save(objeto);
			}
			session.flush();
		} 
		catch (ConstraintViolationException e) 
		{
			String mensaje = e.getMessage();
			if (((mensaje.toUpperCase().lastIndexOf("DUPLIC") > 0) || (mensaje.toUpperCase().lastIndexOf("UNIQUE") > 0)) && (mensaje.toUpperCase().lastIndexOf("KEY") > 0)) 
				return VgcReturnCode.DB_PK_AK_VIOLATED;
			ConstraintViolationException ce = new ConstraintViolationException(mensaje, e.getSQLException(), e.getSQL(), e.getConstraintName());
			throw new ChainedRuntimeException("no pudo guardarse '" + objeto + "' en la base de datos", ce);
		} 
		catch (HibernateException e) 
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no pudo guardarse '" + objeto + "' en la base de datos", he);
		}
		
		byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoInsert();
		if (objeto instanceof com.visiongc.framework.model.UserSession)
			tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoLogin();
		
		if (usuario != null)
			registrarAuditoriaEvento(objeto, usuario, tipoEvento);
		
		return VgcReturnCode.DB_OK;
	}

	public int update(Object objeto, Usuario usuario) 
	{
		try 
		{
			if (objeto instanceof Collection) 
			{
				Iterator iter = ((Collection) objeto).iterator();
				while (iter.hasNext()) 
					update(iter.next(), usuario);
			} 
			else 
			{
				if (session.contains(objeto)) 
					session.merge(objeto);
				else 
					session.update(objeto);
			}
			session.flush();
		} 
		catch (ConstraintViolationException e) 
		{
			String mensaje = e.getMessage();
			if (((mensaje.toUpperCase().lastIndexOf("DUPLIC") > 0) || (mensaje.toUpperCase().lastIndexOf("UNIQUE") > 0)) && (mensaje.toUpperCase().lastIndexOf("KEY") > 0)) 
				return VgcReturnCode.DB_PK_AK_VIOLATED;
			ConstraintViolationException ce = new ConstraintViolationException(mensaje, e.getSQLException(), e.getSQL(), e.getConstraintName());
			throw new ChainedRuntimeException("no pudo guardarse '" + objeto + "' en la base de datos", ce);
		} 
		catch (HibernateException e) 
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no pudo guardarse '" + objeto + "' en la base de datos", he);
		}
		
		if (usuario != null)
			registrarAuditoriaEvento(objeto, usuario, AuditoriaTipoEvento.getAuditoriaTipoEventoUpdate());
		
		return VgcReturnCode.DB_OK;
	}

	public int delete(Object objeto, Usuario usuario) 
	{
		try 
		{
			session.delete(objeto);
			session.flush();
		} 
		catch (ConstraintViolationException e) 
		{
			session.clear();
			return VgcReturnCode.DB_FK_VIOLATED;
		} 
		catch (HibernateException e) 
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no pudo eliminarse '" + objeto + "' de la base de datos", he);
		}
		
		byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoDelete();
		if (objeto instanceof com.visiongc.framework.model.UserSession)
			tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoLogout();

		if (usuario != null)
			registrarAuditoriaEvento(objeto, usuario, tipoEvento);
		
		return VgcReturnCode.DB_OK;
	}

	/***************************************************************************
	 * Método que genera números identificadores únicos para objetos
	 * persistentes Kerwin Arias (05/09/2012)
	 */
	public long getUniqueId()
	  {
	    int MAX_TIME = 5;
	    
	    int id = 0;
	    long timeStart = 0L;
	    long timeFinish = 0L;
	    int recordsAffected = 0;
	    String rdbms = null;
	    ResultSet rs = null;
	    Statement stm = null;
	    try
	    {
	      Connection cn = this.session.connection();
	      stm = cn.createStatement();
	      
	      String sql = "SELECT RDBMS_ID FROM AFW_SISTEMA";
	      rs = stm.executeQuery(sql);
	      if (rs.next()) {
	        rdbms = rs.getString("RDBMS_ID");
	      } else {
	        throw new Exception("no est� configurada la tabla AFW_SISTEMA.");
	      }
	      long l1;
	      if (rdbms.compareTo("ORACLE") == 0)
	      {
	        sql = "SELECT VISION_UNIQUE_ID.NEXTVAL FROM DUAL";
	        rs = stm.executeQuery(sql);
	        if (rs.next()) {
	          return rs.getInt("NEXTVAL");
	        }
	        throw new Exception("la base de datos '" + rdbms + "' no tiene inicializado el generador de identificadores �nicos.");
	      }
	      if ((rdbms.compareTo("ACCESS") == 0) || (rdbms.compareTo("DB2") == 0))
	      {
	        sql = "SELECT ID FROM AFW_IDGEN";
	        rs = stm.executeQuery(sql);
	        if (rs.next())
	        {
	          id = rs.getInt("ID");
	          
	          Date now = new Date();
	          timeStart = now.getTime();
	          timeFinish = now.getTime();
	          while (timeFinish - timeStart < 5L)
	          {
	            stm.executeUpdate("UPDATE AFW_IDGEN SET ID=" + Integer.toString(id + 1) + " WHERE ID=" + Integer.toString(id));
	            recordsAffected = stm.getUpdateCount();
	            if (recordsAffected > 0) {
	              return id + 1;
	            }
	            now = new Date();
	            timeFinish = now.getTime();
	          }
	          throw new Exception("imposible obtener un identificador �nico en " + rdbms);
	        }
	        throw new Exception("la base de datos '" + rdbms + "' no tiene inicializado el generador de identificadores �nicos.");
	      }
	      if (rdbms.compareTo("SYBASE_ANY") == 0)
	      {
	        sql = "INSERT INTO AFW_IDGEN (IDGEN) VALUES ('A')";
	        
	        stm.executeUpdate(sql);
	        
	        sql = "SELECT @@IDENTITY AS NEW_ID";
	        rs = stm.executeQuery(sql);
	        if (rs.next()) {
	          return rs.getInt("NEW_ID");
	        }
	        throw new Exception("imposible obtener un identificador �nico en " + rdbms);
	      }
	      if (rdbms.compareTo("SQL_SERVER") == 0)
	      {
	        sql = "INSERT INTO AFW_IDGEN (IDGEN) VALUES ('A')";
	        stm.executeUpdate(sql);
	        
	        sql = "SELECT @@IDENTITY AS NEW_ID";
	        rs = stm.executeQuery(sql);
	        if (rs.next()) {
	          return rs.getInt("NEW_ID");
	        }
	        throw new Exception("imposible obtener un identificador �nico en " + rdbms);
	      }
	      if (rdbms.compareTo("INFORMIX") == 0)
	      {
	        sql = "INSERT INTO AFW_IDGEN (idgen) VALUES ('A')";
	        stm.executeUpdate(sql);
	        
	        sql = "SELECT DISTINCT DBINFO('SQLCA.SQLERRD1') AS NEW_ID FROM SYSTABLES";
	        rs = stm.executeQuery(sql);
	        if (rs.next()) {
	          return rs.getInt("NEW_ID");
	        }
	        throw new Exception("imposible obtener un identificador �nico en " + rdbms);
	      }
	      if (rdbms.compareTo("MY_SQL") == 0)
	      {
	        stm.execute("UPDATE sequence SET id=LAST_INSERT_ID(id+1);");
	        
	        sql = "SELECT LAST_INSERT_ID()";
	        rs = stm.executeQuery(sql);
	        if (rs.next()) {
	          return rs.getInt("last_insert_id()");
	        }
	        throw new Exception("la base de datos '" + rdbms + "' no tiene inicializado el generador de identificadores �nicos.");
	      }
	      if (rdbms.compareTo("POSTGRESQL") == 0)
	      {
	        sql = "SELECT nextval('AFW_IDGEN')";
	        
	        rs = stm.executeQuery(sql);
	        if (rs.next()) {
	          return rs.getInt("NEXTVAL");
	        }
	        throw new Exception("la base de datos '" + rdbms + "' no tiene inicializado el generador de identificadores �nicos.");
	      }
	      throw new Exception("la base de datos '" + rdbms + "' no tiene inicializado el generador de identificadores �nicos.");
	    }
	    catch (Exception e)
	    {
	      throw new ChainedRuntimeException(e.getMessage(), e);
	    }
	    finally
	    {
	      try
	      {
	        rs.close();
	      }
	      catch (Exception localException15) {}
	      try
	      {
	        stm.close();
	      }
	      catch (Exception localException16) {}
	    }
	  }

	/**
	 * 
	 *  (28/04/2012)
	 * 
	 * @param instancia
	 * @param parentIds
	 * @return
	 */
	private boolean lockRelative(String instancia, Object parentIds[]) 
	{
		boolean resultado = true;
		int bloqueosExitosos = 0;
		int resDb = VgcReturnCode.DB_OK;
		boolean hayTransaccionPadre = false;

		try 
		{
			if (this.isTransactionActive()) 
				hayTransaccionPadre = true;

			if (parentIds != null) 
			{
				for (int index = 0; index < parentIds.length; index++) 
				{
					resDb = lockRelative(instancia, (Long) parentIds[index]);
					if (resDb == VgcReturnCode.DB_OK) 
					{
						bloqueosExitosos++;
					} 
					else if (resDb == VgcReturnCode.DB_PK_AK_VIOLATED) 
					{
						bloqueosExitosos++;
						if (hayTransaccionPadre) 
							break;
					} 
					else if (resDb == VgcReturnCode.DB_LOCKED) 
					{
						resultado = false;
						break;
					}
				}
				if (bloqueosExitosos == parentIds.length) 
					resultado = true;
				else 
					resultado = true;
			}
		} 
		catch (Throwable t) 
		{
			throw new ChainedRuntimeException("no se pudo bloquear el objeto relacionado", t);
		}
		
		return resultado;
	}

	private int lockRelative(String instancia, Long objetoId) 
	{
		boolean transActiva = false;

		try 
		{
			if (!this.isTransactionActive()) 
			{
				this.beginTransaction();
				transActiva = true;
			}
			session.setFlushMode(FlushMode.AUTO);

			LockRead lockr = new LockRead(new LockReadPK(objetoId, instancia));

			session.save(lockr);
			session.flush();
			session.evict(lockr);

			/** Se ha insertado el bloqueo de uso por primera vez */
			Criteria consulta = session.createCriteria(Lock.class);

			consulta.add(Restrictions.eq("pk.objetoId", objetoId));
			consulta.add(Restrictions.ilike("pk.tipo", "D"));

			if (consulta.list().size() > 0) 
			{
				/**
				 * Hay un bloqueo para eliminar el objeto que impide el bloqueo
				 * para uso
				 */
				if (transActiva) 
					this.rollbackTransaction();

				return VgcReturnCode.DB_LOCKED;
			}

			if (transActiva) 
				this.commitTransaction();
		} 
		catch (ConstraintViolationException e) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			return VgcReturnCode.DB_PK_AK_VIOLATED;
		} 
		catch (NonUniqueObjectException e) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			return VgcReturnCode.DB_PK_AK_VIOLATED;
		} 
		catch (Throwable t) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			throw new ChainedRuntimeException("no se pudo bloquear el objeto relacionado", t);
		}
		
		return VgcReturnCode.DB_OK;
	}

	/**
	 *  (15/04/2012)
	 */
	public boolean lockForInsert(String instancia, Object parentIds[]) 
	{
		boolean transActiva = false;

		try 
		{

			if (!this.isTransactionActive()) 
			{
				this.beginTransaction();
				transActiva = true;
			}
			session.setFlushMode(FlushMode.AUTO);

			// Se intenta el bloqueo de los objetos relacionados
			if (parentIds != null) 
			{
				if (!lockRelative(instancia, parentIds)) 
				{
					if (transActiva) 
						this.rollbackTransaction();

					session.setFlushMode(FlushMode.COMMIT);
					return false;
				}
			}

			if (transActiva) 
				this.commitTransaction();

		} 
		catch (ConstraintViolationException e) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			return false;
		} 
		catch (NonUniqueObjectException e) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			return false;
		} 
		catch (Throwable t) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			throw new ChainedRuntimeException("no se pudo bloquear alguno de los objetos relacionados para inserción", t);
		}

		session.setFlushMode(FlushMode.COMMIT);
		return true;
	}

	/**
	 *  (02/07/2012)
	 */
	public boolean lockForUse(String instancia, Object idsToUse[]) 
	{
		return lockRelative(instancia, idsToUse);
	}

	/**
	 *  (15/04/2012)
	 */
	public boolean lockForUpdate(String instancia, Object objectId, Object parentIds[]) 
	{
		boolean transActiva = false;

		String id = "";
		if (objectId.getClass().getName().equals("java.lang.String")) 
			id = (String) objectId;
		else if (objectId.getClass().getName().equals("java.lang.Long")) 
			id = ((Long) objectId).toString();
		else 
			id = objectId.toString();

		// Se crea el objeto lock (candado)
		Lock lock = new Lock(new LockPK(new Long(id), "U"), instancia, new Date());

		try 
		{
			if (!this.isTransactionActive()) 
			{
				this.beginTransaction();
				transActiva = true;
			}
			session.setFlushMode(FlushMode.AUTO);

			// Se intenta el bloqueo de los objetos relacionados
			if (parentIds != null) 
			{
				if (!lockRelative(instancia, parentIds)) 
				{
					if (transActiva) 
						this.rollbackTransaction();

					session.setFlushMode(FlushMode.COMMIT);
					return false;
				}
			}

			List bloqueos = session.createQuery("from Lock lock where lock.instancia=? and lock.pk.objetoId=? and lock.pk.tipo=?").setString(0, instancia).setLong(1, (new Long(id)).longValue()).setString(2, "U").list();

			if (bloqueos.size() == 0) 
			{
				// Se intenta guardar el lock
				session.save(lock);
			}

			if (transActiva) 
			{
				this.commitTransaction();
			}

		} 
		catch (ConstraintViolationException e) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			return false;
		} 
		catch (NonUniqueObjectException e) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			return false;
		} 
		catch (Throwable t) 
		{
			if (transActiva) 
				this.rollbackTransaction();

			session.setFlushMode(FlushMode.COMMIT);
			throw new ChainedRuntimeException("no se pudo bloquear el objeto para modificación", t);
		}

		session.setFlushMode(FlushMode.COMMIT);
		return true;
	}
	
	/**
	 *  (15/04/2012)
	 */
	public boolean lockForDelete(String instancia, Object objectId) {

		String id = "";
		if (objectId.getClass().getName().equals("java.lang.String")) {
			id = (String) objectId;
		} else if (objectId.getClass().getName().equals("java.lang.Long")) {
			id = ((Long) objectId).toString();
		} else {
			id = objectId.toString();
		}

		// Se crea el objeto lock (candado)
		Lock lock = new Lock(new LockPK(new Long(id), "D"), instancia, new Date());

		boolean transActiva = false;
		boolean desbloquear = false;

		try {

			if (!this.isTransactionActive()) {
				this.beginTransaction();
				transActiva = true;
			}
			session.setFlushMode(FlushMode.AUTO);

			List bloqueos = session.createQuery("from Lock lock " + "where lock.instancia=? " + "and lock.pk.objetoId=?" + "and lock.pk.tipo=?").setString(0, instancia).setLong(1, (new Long(id)).longValue()).setString(2, "D").list();

			if (bloqueos.size() == 0) {
				// Se intenta guardar el lock
				session.save(lock);
			}

			// Verificar que no esté bloqueado en LockRead
			Criteria consulta = session.createCriteria(LockRead.class);

			consulta.add(Restrictions.eq("pk.objetoId", new Long(id)));

			if (consulta.list().size() == 0) {
				consulta = session.createCriteria(Lock.class);

				consulta.add(Restrictions.eq("pk.objetoId", new Long(id)));

				if (consulta.list().size() == 1) {
					desbloquear = false;
				} else {
					desbloquear = true;
				}
			} else {
				desbloquear = true;
			}
			if (desbloquear) {
				this.delete(lock, null);
				this.session.flush();

				if (transActiva) {
					this.commitTransaction();
				}
				session.setFlushMode(FlushMode.COMMIT);
				return false;
			}

			if (transActiva) {
				this.commitTransaction();
			}

		} catch (ConstraintViolationException e) {
			if (transActiva) {
				this.rollbackTransaction();
			}
			session.setFlushMode(FlushMode.COMMIT);
			return false;
		} catch (NonUniqueObjectException e) {
			if (transActiva) {
				this.rollbackTransaction();
			}
			session.setFlushMode(FlushMode.COMMIT);
			return false;
		} catch (Throwable t) {
			if (transActiva) {
				this.rollbackTransaction();
			}
			session.setFlushMode(FlushMode.COMMIT);
			throw new ChainedRuntimeException("no se pudo bloquear el objeto para eliminación", t);
		}

		session.setFlushMode(FlushMode.COMMIT);
		return true;
	}

	/**
	 *  (10/01/2012)
	 */
	public boolean unlockObject(String instancia, Object objectId) {

		boolean transActiva = false;

		try {
			String id = "";
			if (objectId != null) {
				if (objectId.getClass().getName().equals("java.lang.String")) {
					id = (String) objectId;
				} else if (objectId.getClass().getName().equals("java.lang.Long")) {
					id = ((Long) objectId).toString();
				} else {
					id = objectId.toString();
				}
			}

			if (!this.isTransactionActive()) {
				this.beginTransaction();
				transActiva = true;
			}

			String hqlDelete = "delete LockRead where instancia = :instancia";
			if (!id.equals("")) {
				hqlDelete = hqlDelete + " and pk.objetoId = :objetoId";
			}

			Query sentencia = session.createQuery(hqlDelete).setString("instancia", instancia);

			if (!id.equals("")) {
				sentencia.setLong("objetoId", Long.parseLong(id));
			}

			sentencia.executeUpdate();

			hqlDelete = "delete Lock where instancia = :instancia";
			if (!id.equals("")) {
				hqlDelete = hqlDelete + " and pk.objetoId = :objetoId";
			}

			sentencia = session.createQuery(hqlDelete).setString("instancia", instancia);

			if (!id.equals("")) {
				sentencia.setLong("objetoId", Long.parseLong(id));
			}

			sentencia.executeUpdate();

			if (transActiva) {
				this.commitTransaction();
			}

		} catch (Throwable t) {
			if (transActiva) {
				this.rollbackTransaction();
			}
			return false;
		}

		return true;
	}
	
	public Blob readBlob(String tabla, String campo, String where, String condicion) throws Exception
	{
		Blob resultado = null;
		
		Connection con = this.session.connection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	    Statement stm = null;
	    ResultSet rs = null;
		
		String sql = "SELECT " + campo + " FROM " + tabla + " WHERE " + where + " = " + condicion;
		
	    try
	    {
	    	con.setAutoCommit(false);
	    	stm = con.createStatement();

	    	rs = stm.executeQuery(sql);

	    	if (rs != null) 
	    	{
	    		if (rs.next()) 
	    		{
	    			resultado = rs.getBlob(1);
	    		}
	    		rs.close();
	    	}
	    	con.setAutoCommit(true);
	    }
	    catch (Exception e) 
	    {
	    	throw new Exception(e.getMessage()); 
	    } 
	    finally 
	    {
    		try 
    		{
    			rs.close(); 
    		} 
    		catch (Exception localException4) 
    		{
    		}
    		
    		try 
    		{ 
    			stm.close(); 
    		} 
    		catch (Exception localException5) 
    		{
    		}
    	}
		
		return resultado;
	}

	/**
	 * 
	 * Creado por: Kerwin Arias (14/05/2012)
	 * 
	 *  (12/05/2012)
	 */
	public int saveBlob(String tableName, String fieldName, byte[] data, String[] keyNames, Object[] idValues) throws Exception 
	{
		int res = VgcReturnCode.DB_OK;

		Connection con = this.session.connection();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

		String sql = "UPDATE ";

		if ((tableName != null) && (!tableName.equals(""))) {
			sql = sql + tableName;
		} else {
			res = VgcReturnCode.DB_CANCELED;
		}

		sql = sql + " SET ";

		if ((fieldName != null) && (!fieldName.equals(""))) 
			sql = sql + fieldName;
		else 
			res = VgcReturnCode.DB_CANCELED;

		if (con.getMetaData().getDatabaseProductName().equalsIgnoreCase("postgresql")) 
			sql = sql + " = lo(?)";
		else 
			sql = sql + " = ?";

		sql = sql + " WHERE ";

		boolean hayCamposClave = false;
		for (int i = 0; i < keyNames.length; i++) 
		{
			hayCamposClave = true;
			sql = sql + keyNames[i] + " = ";
			Object idValue = idValues[i];
			if (idValue.getClass().getName().equals("java.lang.Long")) 
				sql = sql + idValue.toString();
			else if (idValue.getClass().getName().equals("java.lang.String")) 
				sql = sql + "" + replaceQuotes((String) idValue) + "'";
			sql = sql + " AND ";
		}

		if (hayCamposClave) 
			sql = sql.substring(0, sql.length() - 5);

		if (res == VgcReturnCode.DB_OK) 
		{
			PreparedStatement pstmt = con.prepareStatement(sql);

			String bd = con.getMetaData().getDatabaseProductName();
			if (bd.equals("Oracle"))
				pstmt.setBlob(1, createBlob(data, session));
			else
				pstmt.setBlob(1, new BlobImpl(data));

			if (pstmt.executeUpdate() != 1) 
				res = VgcReturnCode.DB_CANCELED;

			pstmt.close();
		}

		return res;
	}

	/**
     * Using Hibernate.createBlob : Cause raise Error for  Access DB
     * @param bytes
     * @return
	 * @throws SQLException 
	 * @throws SerialException 
     */
    public static Blob createBlob(byte[] bytes, Session ses) throws SerialException, SQLException 
    {
    	return Hibernate.createBlob(bytes, ses);
    }	

	private String replaceQuotes(String str) 
	{
		return str.replaceAll("'", "''");
	}

	/**
	 * Función que define la paginación de una Consulta Criteria de Hibernate
	 * Creado por: Kerwin Arias (02/11/2012)
	 * 
	 * @param consulta
	 * @param pagina
	 * @param tamanoPagina
	 */
	public void setCriteriaPagination(Criteria consulta, int pagina, int tamanoPagina) {
		if ((tamanoPagina > 0) && (pagina > 0)) {
			consulta.setFirstResult((tamanoPagina * pagina) - tamanoPagina).setMaxResults(tamanoPagina);
		}
	}

	/**
	 * Creado por: Kerwin Arias (31/03/2012)
	 * 
	 *  (10/04/2012)
	 */
	public boolean existsObject(Object objeto, String fieldNames[], Object fieldValues[]) {
		return existsObject(objeto, fieldNames, fieldValues, null, null);

	}

	/**
	 * Creado por: Kerwin Arias (08/04/2012)
	 * 
	 *  (10/04/2012)
	 */
	public boolean existsObject(Object objeto, String fieldNames[], Object fieldValues[], String idFieldNames[], Object idFieldValues[]) {

		String sql = "select count(*) from " + objeto.getClass().getName();
		boolean caseSensitive = FrameworkConfiguration.getInstance().getBoolean("com.visiongc.framework.persistence.save.casesensitive");

		sql = sql + " where ";
		for (int i = 0; i < fieldNames.length; i++) {
			if (fieldValues[i] == null) {
				sql = sql + fieldNames[i] + " is null and ";
			} else if (fieldValues[i].getClass().getName().equals("java.lang.String")) {
				if (caseSensitive) {
					sql = sql + fieldNames[i] + "=? and ";
				} else {
					sql = sql + "lower(" + fieldNames[i] + ")=? and ";
				}
			} else {
				sql = sql + fieldNames[i] + "=? and ";
			}
		}
		if (idFieldNames == null) {
			sql = sql.substring(0, sql.length() - 5);
		} else {
			sql = sql + "not(";
			for (int i = 0; i < idFieldNames.length; i++) {
				if (idFieldValues[i] == null) {
					sql = sql + idFieldNames[i] + " is null and ";
				} else if (idFieldValues[i].getClass().getName().equals("java.lang.String")) {
					if (caseSensitive) {
						sql = sql + idFieldNames[i] + "=? and ";
					} else {
						sql = sql + "lower(" + idFieldNames[i] + ")=? and ";
					}
				} else {
					sql = sql + idFieldNames[i] + "=? and ";
				}
			}
			sql = sql.substring(0, sql.length() - 5) + ")";
		}

		Query consulta = session.createQuery(sql);
		int ajuste = 0;
		for (int i = 0; i < fieldValues.length; i++) {
			if (fieldValues[i] != null) {
				if (fieldValues[i].getClass().getName().equals("java.lang.String")) {
					if (caseSensitive) {
						consulta.setString(i + ajuste, ((String) fieldValues[i]));
					} else {
						consulta.setString(i + ajuste, ((String) fieldValues[i]).toLowerCase());
					}
				} else if (fieldValues[i].getClass().getName().equals("java.lang.Long")) {
					consulta.setLong(i + ajuste, ((Long) fieldValues[i]).longValue());
				} else if (fieldValues[i].getClass().getName().equals("java.lang.Byte")) {
					consulta.setByte(i + ajuste, ((Byte) fieldValues[i]).byteValue());
				} else if (fieldValues[i].getClass().getName().equals("java.util.Date")) {
					consulta.setDate(i + ajuste, (Date) fieldValues[i]);
				} else if (fieldValues[i].getClass().getName().equals("java.lang.Double")) {
					consulta.setDouble(i + ajuste, ((Double) fieldValues[i]).doubleValue());
				} else if (fieldValues[i].getClass().getName().equals("java.lang.Integer")) {
					consulta.setInteger(i + ajuste, ((Integer) fieldValues[i]).intValue());
				}
			} else {
				ajuste--;
			}
		}
		if (idFieldNames != null) {
			ajuste = ajuste + fieldValues.length;
			for (int i = 0; i < idFieldValues.length; i++) {
				if (idFieldValues[i] != null) {
					if (idFieldValues[i].getClass().getName().equals("java.lang.String")) {
						if (caseSensitive) {
							consulta.setString(i + ajuste, ((String) idFieldValues[i]));
						} else {
							consulta.setString(i + ajuste, ((String) idFieldValues[i]).toLowerCase());
						}
					} else if (idFieldValues[i].getClass().getName().equals("java.lang.Long")) {
						consulta.setLong(i + ajuste, ((Long) idFieldValues[i]).longValue());
					} else if (idFieldValues[i].getClass().getName().equals("java.lang.Byte")) {
						consulta.setByte(i + ajuste, ((Byte) idFieldValues[i]).byteValue());
					} else if (idFieldValues[i].getClass().getName().equals("java.util.Date")) {
						consulta.setDate(i + ajuste, (Date) idFieldValues[i]);
					} else if (idFieldValues[i].getClass().getName().equals("java.lang.Double")) {
						consulta.setDouble(i + ajuste, ((Double) idFieldValues[i]).doubleValue());
					} else if (idFieldValues[i].getClass().getName().equals("java.lang.Integer")) {
						consulta.setInteger(i + ajuste, ((Integer) idFieldValues[i]).intValue());
					}
				} else {
					ajuste--;
				}
			}

		}

		Object total = consulta.list().get(0);

		if (total.hashCode() == new Integer(0)) 
			return false;
		else 
			return true;
	}

	/**
	 * 
	 * Creado por: Kerwin Arias (10/04/2012)
	 * 
	 *  (08/07/2012)
	 * 
	 * @param clase
	 * @param orden
	 * @param tipoOrden
	 * @return
	 */
	protected Criteria prepareQuery(Class clase) 
	{
		Criteria consulta = session.createCriteria(clase);

		return consulta;
	}

	protected PaginaLista executeQuery(Criteria consulta, int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal) 
	{
		String arregloOrden[] = null;
		String arregloTipoOrden[] = null;

		if ((orden != null) && (tipoOrden != null)) 
		{
			arregloOrden = new String[1];
			arregloTipoOrden = new String[1];

			arregloOrden[0] = orden;
			arregloTipoOrden[0] = tipoOrden;

		}

		return executeQuery(consulta, pagina, tamanoPagina, arregloOrden, arregloTipoOrden, getTotal);
	}

	/**
	 * 
	 * Creado por: Kerwin Arias (25/11/2012)
	 * 
	 * @param consulta
	 * @param pagina
	 * @param tamanoPagina
	 * @param getTotal
	 * @return
	 */
	protected PaginaLista executeQuery(Criteria consulta, int pagina, int tamanoPagina, String orden[], String tipoOrden[], boolean getTotal) 
	{
		/** Declaración de las variables u objetos locales */
		int total = 0;
		boolean continuar = true;

		/** Configurar la consulta de acuerdo a los parámetros de orden */
		if ((orden != null) && (tipoOrden != null)) 
		{
			for (int i = 0; (i < orden.length) && (i < tipoOrden.length); i++) 
			{
				if ((orden[i] != null) && (!orden.equals(""))) 
				{
					if ((tipoOrden[i] == null) || (tipoOrden[i].equals(""))) 
						consulta.addOrder(Order.asc(orden[i]));
					else 
					{
						if (tipoOrden[i].equalsIgnoreCase("asc")) 
							consulta.addOrder(Order.asc(orden[i]));
						else 
							consulta.addOrder(Order.desc(orden[i]));
					}
				}
			}
		}

		List lista = null;
		while (continuar) 
		{
			/** Se obtiene el total de registros que retorna la consulta */
			if (getTotal) 
				total = consulta.list().size();

			/** Configurar la consulta de acuerdo a los parámetros de páginación */
			if ((tamanoPagina > 0) && (pagina > 0)) 
			{
				int primerResultado = (tamanoPagina * pagina) - tamanoPagina;
				if ((getTotal) && ((primerResultado + 1) > total)) 
				{
					pagina = 1;
					primerResultado = (tamanoPagina * pagina) - tamanoPagina;
				}
				consulta.setFirstResult(primerResultado).setMaxResults(tamanoPagina);
			}

			/** Se obtiene la lista de registros */
			lista = (List) consulta.list();

			if (!getTotal) 
				total = lista.size();
			if (lista.size() > 0) 
				continuar = false;
			else if (pagina < 2) 
				continuar = false;
			else 
				pagina--;
		}

		/** Declaración de las variables u objetos locales */
		PaginaLista paginaLista = new PaginaLista();

		/** Establece el valor de los atributos */
		paginaLista.setLista(lista);
		paginaLista.setNroPagina(pagina);
		paginaLista.setTamanoPagina(tamanoPagina);
		paginaLista.setTotal(total);
		if ((orden != null) && (orden.length > 0)) 
		{
			paginaLista.setOrden(orden[0]);
			paginaLista.setTipoOrden(tipoOrden[0]);
		}

		/** Valor de retorno de la función */
		return paginaLista;

	}

	public boolean saveSoloLectura(Object objeto, boolean soloLectura, String[] fieldNames, Object[] fieldValues) 
	{
		boolean transActiva = false;
		int actualizados = 0;

		if (fieldNames.length == 0) 
			return false;

		try 
		{
			if (!this.isTransactionActive()) 
			{
				this.beginTransaction();
				transActiva = true;
			}

			String hqlUpdate = "update " + ObjetoAbstracto.getClassSimpleName(objeto) + " o set o.soloLectura = :soloLectura where ";
			for (int i = 0; i < fieldNames.length; i++) 
			{
				String fieldName = fieldNames[i];
				hqlUpdate = hqlUpdate + fieldName + " = :" + fieldName + " and ";
			}
			hqlUpdate = hqlUpdate.substring(0, hqlUpdate.length() - 5);
			byte valor = 0;
			if (soloLectura) 
				valor = 1;
			Query sentencia = session.createQuery(hqlUpdate).setByte("soloLectura", valor);
			for (int i = 0; i < fieldNames.length; i++) 
			{
				String fieldName = fieldNames[i];
				sentencia.setLong(fieldName, ((Long) fieldValues[i]).longValue());
			}

			actualizados = sentencia.executeUpdate();

			if (transActiva) 
				this.commitTransaction();
		} 
		catch (Throwable t) 
		{
			if (transActiva) 
				this.rollbackTransaction();
			throw new ChainedRuntimeException("Error actualizando el atributo SoloLectura", t);
		}

		return actualizados == 1;

	}

	protected String getCondicionConsulta(Object fieldValue, String operador) 
	{
		String valor = getValorCondicionConsulta(fieldValue);
		String condicion = null;
		if (operador.equals("like")) 
			condicion = " " + operador + " '" + valor.toLowerCase() + "'";
		else if (operador.equals("=")) 
		{
			if (fieldValue instanceof List) 
				operador = " in ";
			condicion = " " + operador + " " + (valor.isEmpty() ? "NULL" : valor) ;
		} 
		else if (operador.equals("!=")) 
		{
			if (fieldValue instanceof List) 
				operador = " not in ";
			condicion = " " + operador + " " + valor;
		}
		return condicion;
	}

	protected String getCondicionConsulta(String campo, Object fieldValue, String operador) 
	{
		String valor = getValorCondicionConsulta(fieldValue);
		String condicion = null;
		if (operador.equals("like")) 
			condicion = campo + " " + operador + " '" + valor.toLowerCase() + "'";
		else if (operador.equals("=")) 
		{
			if (fieldValue instanceof List) 
				operador = " in ";
			condicion = campo + " " + operador + " " + valor;
			if (fieldValue instanceof Boolean) 
			{
				if (!((Boolean) fieldValue).booleanValue()) 
					condicion = "(" + condicion + " or " + campo + " is null)";
			}
		} 
		else if (operador.equals("!=")) 
		{
			if (fieldValue instanceof List) 
				operador = " not in ";
			condicion = campo + " " + operador + " " + valor;
			if (fieldValue instanceof Boolean) 
			{
				if (!((Boolean) fieldValue).booleanValue()) 
					condicion = "(" + condicion + " or " + campo + " not is null)";
			}
		}
		return condicion;
	}

	protected String getValorCondicionConsulta(Object fieldValue) 
	{
		return new StringUtil().getValorCondicionConsulta(fieldValue);
	}

	public void registrarAuditoriaEvento(Object objeto, Usuario usuario, byte tipoEvento) 
	{
		
		String signo="l.";
		int index=0;
		
		String cadena=objeto.getClass().getName().toString();
		
		index=cadena.indexOf(signo);
		
		if(objeto instanceof Medicion || objeto instanceof AuditoriaMedicion || objeto instanceof ConfiguracionUsuario
				|| objeto instanceof Causa || objeto instanceof IniciativaEstatus || objeto instanceof EstadoAcciones || objeto instanceof PryCalendario) {
			
		}else {
		
			Auditoria auditoria = new Auditoria();
			auditoria.setAuditoriaId(new Long(this.getUniqueId()));
			auditoria.setFechaEjecucion(new Date());
			auditoria.setClaseEntidad(objeto.toString());
			auditoria.setUsuario(usuario.getFullName());
			auditoria.setTipoEvento(obtenerEvento(tipoEvento));
			auditoria.setEntidad(cadena.substring(index+2, cadena.length()));
			
			String detalle = obtenerValores(objeto);
			
			if(!detalle.equals("")) {
				
				auditoria.setDetalle(obtenerValores(objeto));
				this.insert(auditoria, null);
			
			}
			
		}
	}
	
	public String obtenerEvento(byte tipoEvento) {
		String evento="";
		
		switch(tipoEvento) {
		
		case 1:
			evento = "inserci�n";
			break;
		case 2:
			evento = "actualizaci�n";
			break;
		case 3:
			evento = "eliminaci�n";
			break;
		case 4:
			evento = "login";
			break;
		case 5:
			evento = "logout";
			break;
		case 6:
			evento = "calculo";
			break;
		case 7:
			evento = "importar";
			break;
		case 8:
			evento = "proteger/liberar";
			break;
			
		}
		
		return evento;
		
	}
	
	public String obtenerValores(Object objeto) {
		
		
		String cadena = "";
		
		
		if(objeto instanceof Indicador){
			
			Indicador indicador = (Indicador) objeto; 
			
			cadena+="{";
			cadena+="Indicador Id"+ ":"+ indicador.getIndicadorId()+",";
			cadena+="Nombre"+ ":"+ indicador.getNombre()+",";
			cadena+="Descripcion"+ ":"+ indicador.getDescripcion()+",";
			cadena+="Actividad Id"+ ":"+ indicador.getActividadId()+",";
			cadena+="Alerta"+ ":"+ indicador.getAlerta()+",";
			cadena+="Alerta Meta Zona Amarilla"+ ":"+ indicador.getAlertaMetaZonaAmarilla()+",";
			cadena+="Alerta Meta Zona Verde"+ ":"+ indicador.getAlertaMetaZonaVerde()+",";
			cadena+="Alerta Min Max"+ ":"+ indicador.getAlertaMinMax()+",";
			cadena+="Alerta Tipo Zona Amarilla"+ ":"+ indicador.getAlertaTipoZonaAmarilla()+",";
			cadena+="Alerta Tipo Zona Verde"+ ":"+ indicador.getAlertaMetaZonaVerde()+",";
			cadena+="Asignar Inventario"+ ":"+ indicador.getAsignarInventario()+",";
			cadena+="Caracteristica"+ ":"+ indicador.getCaracteristica()+",";
			cadena+="Caracteristica Nombre"+ ":"+ indicador.getCaracteristicaNombre()+",";
			cadena+="Clase"+ ":"+ indicador.getClaseId()+",";
			cadena+="Codigo Enlace"+ ":"+ indicador.getCodigoEnlace()+",";
			cadena+="Comportamiento"+ ":"+ indicador.getComportamiento()+",";
			cadena+="Corte"+ ":"+ indicador.getCorte()+",";
			cadena+="Corte Nombre"+ ":"+ indicador.getCorteNombre()+",";
			cadena+="Enlace Parcial"+ ":"+ indicador.getEnlaceParcial()+",";
			cadena+="Esta Bloqueado"+ ":"+ indicador.getEstaBloqueado()+",";
			cadena+="Estado Anual"+ ":"+ indicador.getEstadoAnual()+",";
			cadena+="Estado Parcial"+ ":"+ indicador.getEstadoParcial()+",";
			cadena+="Fecha Bloqueado Ejecutado" +":"+ indicador.getFechaBloqueoEjecutado()+",";
			cadena+="Fecha Bloqueo Meta"+ ":"+ indicador.getFechaBloqueoMeta()+",";
			cadena+="Fecha Inicial"+ ":"+ indicador.getFechaInicial()+",";
			cadena+="Fecha Final"+ ":"+ indicador.getFechaFinal()+",";
			cadena+="Fecha Ultima Medicion"+ ":"+ indicador.getFechaUltimaMedicion()+",";
			cadena+="Fecha Ultima Medicion Ano"+ ":"+ indicador.getFechaUltimaMedicionAno()+",";
			cadena+="Fecha Ultima Medicion Periodo"+ ":"+ indicador.getFechaUltimaMedicionPeriodo()+",";
			cadena+="Formula Serie Real"+ ":"+ indicador.getFormulaSerieReal()+",";
			cadena+="Frecuencia"+ ":"+ indicador.getFrecuencia()+",";
			cadena+="Frecuencia Nombre"+ ":"+ indicador.getFrecuenciaNombre()+",";
			cadena+="Fuente"+ ":"+ indicador.getFuente()+",";
			cadena+="Guia"+ ":"+ indicador.getGuia()+",";
			cadena+="Guia Nombre"+ ":"+ indicador.getGuiaNombre()+",";
			cadena+="Indicador Asociado Id"+ ":"+ indicador.getIndicadorAsociadoId()+",";
			cadena+="Es Porcentaje"+ ":"+ indicador.getIsPocentaje()+",";
			cadena+="Mes inicio"+ ":"+ indicador.getMesInicio()+",";
			cadena+="Mes Cierre"+ ":"+ indicador.getMesCierre()+",";
			cadena+="Meta Anual"+ ":"+ indicador.getMetaAnual()+",";
			cadena+="Meta Parcial"+ ":"+ indicador.getMetaParcial()+",";
			cadena+="Multidimensional"+ ":"+ indicador.getMultidimensional()+",";
			cadena+="Naturaleza Nombre"+ ":"+ indicador.getNaturalezaNombre()+",";
			cadena+="Orden"+ ":"+ indicador.getOrden()+",";
			cadena+="Organizacion Id"+ ":"+ indicador.getOrganizacionId()+",";
			cadena+="Perspectiva Id"+ ":"+ indicador.getPerspectivaId()+",";
			cadena+="Peso"+ ":"+ indicador.getPeso()+",";
			cadena+="Plan Id"+ ":"+ indicador.getPlanId()+",";
			cadena+="Prioridad Nombre"+ ":"+ indicador.getPrioridadNombre()+",";
			cadena+="Solo Lectura"+ ":"+ indicador.getSoloLectura()+",";
			cadena+="Tipo Carga Medicion"+ ":"+ indicador.getTipoCargaMedicion()+",";
			cadena+="Tipo Suma Medicion"+ ":"+ indicador.getTipoSumaMedicion()+",";
			cadena+="Ultima Medicion"+ ":"+ indicador.getUltimaMedicion()+",";
			cadena+="Ultimo Programado"+ ":"+ indicador.getUltimoProgramado()+",";
			cadena+="Ultima Medicion Ano Anterior"+ ":"+ indicador.getUltimaMedicionAnoAnterior()+",";
			cadena+="Ultimo Programado Inferior"+ ":"+ indicador.getUltimoProgramadoInferior()+",";
			cadena+="Unidad Id"+ ":"+ indicador.getUnidadId()+",";
			cadena+="URL"+ ":"+ indicador.getUrl()+",";
			cadena+="Valor Inicial Cero"+ ":"+ indicador.getValorInicialCero()+",";
			cadena+="Responsable Cargar Ejecutado"+ ":"+ indicador.getResponsableCargarEjecutadoId()+",";
			cadena+="Repsonsable Cargar Meta"+ ":"+ indicador.getResponsableCargarMetaId()+",";
			cadena+="Responsable Fijar Meta"+ ":"+ indicador.getResponsableFijarMetaId()+",";
			cadena+="Responsable Lograr Meta"+ ":"+ indicador.getResponsableLograrMetaId()+",";
			cadena+="Responsable Seguimiento"+ ":"+ indicador.getResponsableSeguimientoId();
			
			cadena+="}";
			
		}else if(objeto instanceof PryActividad) {
			
			PryActividad pryActividad = (PryActividad) objeto; 
			
			cadena+="{";
			cadena+="Actividad Id"+ ":"+ pryActividad.getActividadId()+",";
			cadena+="Proyecto Id"+ ":"+ pryActividad.getProyectoId()+",";
			cadena+="Nombre"+ ":"+ pryActividad.getNombre()+",";
			cadena+="Descripcion"+ ":"+ pryActividad.getDescripcion()+",";
			cadena+="Alerta"+ ":"+ pryActividad.getAlerta()+",";
			cadena+="Clase"+ ":"+ pryActividad.getClaseId()+",";
			cadena+="Comienzo Real"+ ":"+ pryActividad.getComienzoReal()+",";
			cadena+="Comienzo Paln"+ ":"+ pryActividad.getComienzoPlan()+",";
			cadena+="Compuesta"+ ":"+ pryActividad.getCompuesta()+",";
			cadena+="Duracion Plan"+ ":"+ pryActividad.getDuracionPlan()+",";
			cadena+="Duracion Real"+ ":"+ pryActividad.getDuracionReal()+",";
			cadena+="Fecha Ultima Medicion"+ ":"+ pryActividad.getFechaUltimaMedicion()+",";
			cadena+="Fila"+ ":"+ pryActividad.getFila()+",";
			cadena+="Fin Real"+ ":"+ pryActividad.getFinReal()+",";
			cadena+="Fin Plan"+ ":"+ pryActividad.getFinPlan()+",";
			cadena+="Identacion"+ ":"+ pryActividad.getIdentacion()+",";
			cadena+="Indicador Id"+ ":"+ pryActividad.getIndicadorId()+",";
			cadena+="Naturaleza"+ ":"+ pryActividad.getNaturaleza()+",";
			cadena+="Nivel"+ ":"+ pryActividad.getNivel()+",";
			cadena+="Peso"+ ":"+ pryActividad.getPeso()+",";
			cadena+="Porcentaje Completado"+ ":"+ pryActividad.getPorcentajeCompletado()+",";
			cadena+="Porcentaje Ejecutado"+ ":"+ pryActividad.getPorcentajeEjecutado()+",";
			cadena+="Porcentaje Esperado"+ ":"+ pryActividad.getPorcentajeEsperado()+",";
			cadena+="Tipo Medicion"+ ":"+ pryActividad.getTipoMedicion()+",";
			cadena+="Unidad"+ ":"+ pryActividad.getUnidadId()+",";
			cadena+="Recursos Humanos"+ ":"+ pryActividad.getRecursosHumanos()+",";
			cadena+="Recursos Materiales"+ ":"+ pryActividad.getRecursosMateriales()+",";
			cadena+="Responsable Cargar Ejecutado"+ ":"+ pryActividad.getResponsableCargarEjecutado()+",";
			cadena+="Responsable Cargar Meta"+ ":"+ pryActividad.getResponsableCargarMeta()+",";
			cadena+="Responsable Fijar Meta"+ ":"+ pryActividad.getResponsableFijarMeta()+",";
			cadena+="Responsable Lograr Meta"+ ":"+ pryActividad.getResponsableLograrMeta()+",";
			cadena+="Responsable Seguimiento"+ ":"+ pryActividad.getResponsableSeguimiento()+",";
			cadena+="Fecha Creado"+ ":"+ pryActividad.getCreado()+",";
			cadena+="Fecha Modificado"+ ":"+ pryActividad.getModificado();
			
			cadena+="}";
			
			
		}else if(objeto instanceof Responsable) {
			
			Responsable responsable = (Responsable) objeto; 
			
			cadena+="{";
			cadena+="Responsable Id"+ ":"+ responsable.getResponsableId() +",";
			cadena+="Nombre"+ ":"+ responsable.getNombre() +",";
			cadena+="Cargo"+ ":"+ responsable.getCargo() +",";
			cadena+="Email"+ ":"+ responsable.getEmail() +",";
			cadena+="Tipo"+ ":"+ responsable.getTipo() +",";
			cadena+="Es Grupo"+ ":"+ responsable.getEsGrupo() +",";
			cadena+="Ubicacion"+ ":"+ responsable.getUbicacion() +",";
			cadena+="Organizacion Id"+ ":"+ responsable.getOrganizacionId() +",";
			cadena+="Notas"+ ":"+ responsable.getNotas();
			
			cadena+="}";
			
		}else if(objeto instanceof OrganizacionStrategos) {
			
			OrganizacionStrategos organizacion = (OrganizacionStrategos) objeto; 
			
			cadena+="{";
			cadena+="Organizacion Id"+ ":"+ organizacion.getOrganizacionId()+",";
			cadena+="Nombre"+ ":"+ organizacion.getNombre()+",";
			cadena+="Enlace Parcial"+ ":"+ organizacion.getEnlaceParcial()+",";
			cadena+="Rif"+ ":"+ organizacion.getRif()+",";
			cadena+="Fax"+ ":"+ organizacion.getFax()+",";
			cadena+="Mes Cierre"+ ":"+ organizacion.getMesCierre()+",";
			cadena+="Telefono"+ ":"+ organizacion.getTelefono()+",";
			cadena+="Ruta Completa"+ ":"+ organizacion.getRutaCompleta()+",";
			cadena+="Direccion"+ ":"+ organizacion.getDireccion()+",";
			cadena+="Visible"+ ":"+ organizacion.getVisible()+",";
			cadena+="% Zona Amarilla Iniciativas"+ ":"+ organizacion.getPorcentajeZonaAmarillaIniciativas()+",";
			cadena+="% Zona Amarilla Meta Indicadores"+ ":"+ organizacion.getPorcentajeZonaAmarillaMetaIndicadores()+",";
			cadena+="% Zona Amarilla Min Max Indicadores"+ ":"+ organizacion.getPorcentajeZonaAmarillaMinMaxIndicadores()+",";
			cadena+="% Zona Verde Iniciativas"+ ":"+ organizacion.getPorcentajeZonaVerdeIniciativas()+",";
			cadena+="% Zona Verde Meta Indicadores"+ ":"+ organizacion.getPorcentajeZonaVerdeMetaIndicadores()+",";
			cadena+="Padre Id"+ ":"+ organizacion.getPadreId()+",";
			cadena+="Fecha Creado"+ ":"+ organizacion.getCreado()+",";
			cadena+="Fecha Modificado"+ ":"+ organizacion.getModificado();
			
			cadena+="}";
			
			
		}else if(objeto instanceof Formula) {
			
			Formula formula = (Formula) objeto; 
			
			cadena+="{";
			cadena+="Indicador Id"+ ":"+ formula.getPk().getIndicadorId()+",";
			cadena+="Serie Id"+ ":"+ formula.getPk().getSerieId()+",";
			cadena+="Expresion"+ ":"+ formula.getExpresion();
			
			cadena+="}";
			
		}else if(objeto instanceof UnidadMedida) {
			
			UnidadMedida unidad = (UnidadMedida) objeto; 
			
			cadena+="{";
			cadena+="Unidad Id"+ ":"+  unidad.getUnidadId()+",";
			cadena+="Nombre Id"+ ":"+  unidad.getNombre()+",";
			cadena+="Tipo"+ ":"+  unidad.getTipo();
			
			cadena+="}";
			
		}else if(objeto instanceof SerieTiempo) {
			
			SerieTiempo serieTie = (SerieTiempo) objeto; 
			
			cadena+="{";
			cadena+="Serie Id"+ ":"+ serieTie.getSerieId()+",";
			cadena+="Nombre"+ ":"+ serieTie.getNombre()+",";
			cadena+="Identificador"+ ":"+ serieTie.getIdentificador()+",";
			cadena+="Fija"+ ":"+ serieTie.getFija()+",";
			cadena+="Preseleccionada"+ ":"+ serieTie.getPreseleccionada();
			
			cadena+="}";
				
			
		}else if(objeto instanceof SerieIndicador) {
			
			SerieIndicador serieInd = (SerieIndicador) objeto; 
			
			cadena+="{";
			cadena+="Serie Id"+ ":"+ serieInd.getPk().getSerieId()+",";
			cadena+="Indicador Id"+ ":"+ serieInd.getPk().getIndicadorId()+",";
			cadena+="Nombre"+ ":"+ serieInd.getNombre()+",";
			cadena+="Naturaleza"+ ":"+ serieInd.getNaturaleza()+",";
			cadena+="Fecha Bloqueo"+ ":"+ serieInd.getFechaBloqueo();
			
			cadena+="}";
			
			
		}else if(objeto instanceof Iniciativa) {
			
			Iniciativa iniciativa = (Iniciativa) objeto; 
			
			cadena+="{";
			cadena+="Iniciativa Id"+ ":"+ iniciativa.getIniciativaId()+",";
			cadena+="Nombre"+ ":"+ iniciativa.getNombre()+",";
			cadena+="Descripcion"+ ":"+ iniciativa.getDescripcion()+",";
			cadena+="A�o Formulacion"+ ":"+ iniciativa.getAnioFormulacion()+",";
			cadena+="Alerta"+ ":"+ iniciativa.getAlerta()+",";
			cadena+="Alerta Zona Amarilla"+ ":"+ iniciativa.getAlertaZonaAmarilla()+",";
			cadena+="Alerta Zona Verde"+ ":"+ iniciativa.getAlertaZonaVerde()+",";
			cadena+="Clase Id"+ ":"+ iniciativa.getClaseId()+",";
			cadena+="Condicion"+ ":"+ iniciativa.getCondicion()+",";
			cadena+="Ente Ejecutor"+ ":"+ iniciativa.getEnteEjecutor()+",";
			cadena+="Estatus Id"+ ":"+ iniciativa.getEstatusId()+",";
			cadena+="Fecha Ultima Medicion"+ ":"+ iniciativa.getFechaUltimaMedicion()+",";
			cadena+="Frecuencia"+ ":"+ iniciativa.getFrecuencia()+",";
			cadena+="Frecuencia Nombre"+ ":"+ iniciativa.getFrecuenciaNombre()+",";
			cadena+="Fecha Historico"+ ":"+ iniciativa.getHistoricoDate()+",";
			cadena+="Naturaleza"+ ":"+ iniciativa.getNaturaleza()+",";
			cadena+="Naturaleza Nombre"+ ":"+ iniciativa.getNaturalezaNombre()+",";
			cadena+="Organizacion Id"+ ":"+ iniciativa.getOrganizacionId()+",";
			cadena+="Proyecto Id"+ ":"+ iniciativa.getProyectoId()+",";
			cadena+="Porcentaje Esperado"+ ":"+ iniciativa.getPorcentajeEsperado()+",";
			cadena+="Porcentaje Completado"+ ":"+ iniciativa.getPorcentajeCompletado()+",";
			cadena+="Tipo Medicion"+ ":"+ iniciativa.getTipoMedicion()+",";
			cadena+="Ultima Medicion"+ ":"+ iniciativa.getUltimaMedicion()+",";
			cadena+="Responsable Cargar Ejecutado"+ ":"+ iniciativa.getResponsableCargarEjecutadoId()+",";
			cadena+="Responsable Cargar Meta"+ ":"+ iniciativa.getResponsableCargarMetaId()+",";
			cadena+="Responsable Fijar Meta"+ ":"+ iniciativa.getResponsableFijarMetaId()+",";
			cadena+="Responsable Lograr Meta"+ ":"+ iniciativa.getResponsableLograrMetaId()+",";
			cadena+="Responsable Seguimiento"+ ":"+ iniciativa.getResponsableSeguimientoId();
			
			cadena+="}";
			
			
		}else if(objeto instanceof ClaseIndicadores) {
			
			ClaseIndicadores claseInd = (ClaseIndicadores) objeto; 
			
			cadena+="{";
			cadena+="Clase Id"+ ":"+ claseInd.getClaseId()+",";
			cadena+="Nombre"+ ":"+ claseInd.getNombre()+",";
			cadena+="Descripci�n"+ ":"+ claseInd.getDescripcion()+",";
			cadena+="Enlace Parcial"+ ":"+ claseInd.getEnlaceParcial()+",";
			cadena+="Nivel"+ ":"+ claseInd.getNivel()+",";
			cadena+="Tipo"+ ":"+ claseInd.getTipo()+",";
			cadena+="Padre Id"+ ":"+ claseInd.getPadreId()+",";
			cadena+="Organizacion Id"+ ":"+ claseInd.getOrganizacionId()+",";
			cadena+="Visible"+ ":"+ claseInd.getVisible()+",";
			cadena+="Fecha Creado"+ ":"+ claseInd.getCreado()+",";
			cadena+="Fecha Modificado"+ ":"+ claseInd.getModificado();
			
			
			cadena+="}";
			
						
		}else if(objeto instanceof CategoriaIndicador) {
			
			CategoriaIndicador categoriaInd = (CategoriaIndicador) objeto; 
			
			cadena+="{";
			cadena+="Categoria Id"+ ":"+ categoriaInd.getPk().getCategoriaId()+",";			
			cadena+="Indicador Id"+ ":"+ categoriaInd.getPk().getIndicadorId();
			cadena+="Orden"+ ":"+ categoriaInd.getOrden();
			
			cadena+="}";
			
					
			
		}else if(objeto instanceof Meta) {
			
			Meta meta = (Meta) objeto; 
			
			cadena+="{";
			cadena+="A�o"+ ":"+ meta.getMetaId().getAno()+",";
			cadena+="Periodo"+ ":"+ meta.getMetaId().getPeriodo()+",";
			cadena+="Indicador Id"+ ":"+ meta.getMetaId().getIndicadorId()+",";
			cadena+="Plan Id"+ ":"+ meta.getMetaId().getPlanId()+",";
			cadena+="Serie Id"+ ":"+ meta.getMetaId().getSerieId()+",";
			cadena+="Tipo"+ ":"+ meta.getMetaId().getTipo()+",";
			cadena+="Tipo Carga Meta"+ ":"+ meta.getTipoCargaMeta()+",";
			cadena+="Protegido"+ ":"+ meta.getProtegido()+",";
			cadena+="Valor"+ ":"+ meta.getValor();
			
			cadena+="}";
			
			
		}else if(objeto instanceof Perspectiva) {
			
			Perspectiva perspectiva = (Perspectiva) objeto; 
			
			cadena+="{";
			cadena+="Perspectiva Id"+ ":"+ perspectiva.getPerspectivaId()+",";
			cadena+="Nombre"+ ":"+ perspectiva.getNombre()+",";
			cadena+="Titulo"+ ":"+ perspectiva.getTitulo()+",";
			cadena+="Descripcion"+ ":"+ perspectiva.getDescripcion()+",";
			cadena+="A�o"+ ":"+ perspectiva.getAno()+",";
			cadena+="Alerta Anual"+ ":"+ perspectiva.getAlertaAnual()+",";
			cadena+="Alerta Parcial"+ ":"+ perspectiva.getAlertaParcial()+",";
			cadena+="Clase Id"+ ":"+ perspectiva.getClaseId()+",";
			cadena+="Frecuencia"+ ":"+ perspectiva.getFrecuencia()+",";
			cadena+="Nivel"+ ":"+ perspectiva.getNivel()+",";
			cadena+="Orden"+ ":"+ perspectiva.getOrden()+",";
			cadena+="Padre Id"+ ":"+ perspectiva.getPadreId()+",";
			cadena+="Plan Id"+ ":"+ perspectiva.getPlanId()+",";
			cadena+="Nombre"+ ":"+ perspectiva.getNombre()+",";
			cadena+="Responsable Id"+ ":"+ perspectiva.getResponsableId()+",";
			cadena+="Tipo"+ ":"+ perspectiva.getTipo()+",";
			cadena+="Tipo Calculo"+ ":"+ perspectiva.getTipoCalculo()+",";
			cadena+="Fecha Ultima Medicion"+ ":"+ perspectiva.getFechaUltimaMedicion()+",";
			cadena+="Frecuencia"+ ":"+ perspectiva.getFrecuencia()+",";
			cadena+="Ultima Medicion Anual"+ ":"+ perspectiva.getUltimaMedicionAnual()+",";
			cadena+="Ultima Medicion Parcial"+ ":"+ perspectiva.getUltimaMedicionParcial()+",";
			cadena+="Fecha Creado"+ ":"+ perspectiva.getCreado()+",";
			cadena+="Fecha Modificado"+ ":"+ perspectiva.getModificado();
			
			cadena+="}";
			
			
		}else if(objeto instanceof Plan) {
			
			Plan plan = (Plan) objeto; 
			
			cadena+="{";
			cadena+="Plan Id"+ ":"+ plan.getPlanId()+",";
			cadena+="Nombre"+ ":"+ plan.getNombre()+",";
			cadena+="Activo"+ ":"+ plan.getActivo()+",";
			cadena+="Alerta"+ ":"+ plan.getAlerta()+",";
			cadena+="A�o Inicial"+ ":"+ plan.getAnoInicial()+",";
			cadena+="A�o Final"+ ":"+ plan.getAnoFinal()+",";
			if(plan.getClase() != null) {
				cadena+="Clase Nombre"+ ":"+ plan.getClase().getNombre()+",";
			}
			cadena+="Clase Id"+ ":"+ plan.getClaseId()+",";
			cadena+="Esquema"+ ":"+ plan.getEsquema()+",";
			cadena+="Metodologia Id"+ ":"+ plan.getMetodologiaId()+",";
			cadena+="Fecha Ultima Medicion"+ ":"+ plan.getFechaUltimaMedicion()+",";
			
			cadena+="Organizaci�n Id"+ ":"+ plan.getOrganizacionId()+",";
			
			if(plan.getOrganizacion() != null) {
				cadena+="Nombre Organizaci�n"+ ":"+ plan.getOrganizacion().getNombre()+",";
			}
			
			cadena+="Padre Id"+ ":"+ plan.getPadreId()+",";
			cadena+="Plan Impacta Id"+ ":"+ plan.getPlanImpactaId()+",";
			cadena+="Tipo"+ ":"+ plan.getTipo()+",";
			cadena+="Revision"+ ":"+ plan.getRevision()+",";
			cadena+="Ultima Medicion Anual"+ ":"+ plan.getUltimaMedicionAnual()+",";
			cadena+="Ultima Medicion Parcial"+ ":"+ plan.getUltimaMedicionParcial();
			cadena+="}";
			
		}else if(objeto instanceof Servicio) {
			
			Servicio servicio = (Servicio) objeto; 
			
			cadena+="{";
			cadena+="Usuario Id"+ ":"+ servicio.getUsuarioId()+",";
			cadena+="Nombre"+ ":"+ servicio.getNombre()+",";
			cadena+="Fecha"+ ":"+ servicio.getFecha()+",";
			cadena+="Fecha Exacta"+ ":"+ servicio.getFechaExacta()+",";
			cadena+="Mensaje"+ ":"+ servicio.getMensaje()+",";
			cadena+="Estatus"+ ":"+ servicio.getEstatus()+",";
			cadena+="Log"+ ":"+ servicio.getLog();
			
			cadena+="}";
			
			
		}else if(objeto instanceof CategoriaMedicion) {
			
			CategoriaMedicion categoriaMed = (CategoriaMedicion) objeto; 
			
			cadena+="{";
			cadena+="Categoria Id"+ ":"+ categoriaMed.getCategoriaId()+",";
			cadena+="Nombre"+ ":"+ categoriaMed.getNombre(); 
			cadena+="}";
			
		}else if(objeto instanceof Explicacion) {
			
			Explicacion explicacion = (Explicacion) objeto; 
			
			cadena+="{";
			cadena+="Explicaci�n Id"+ ":"+ explicacion.getExplicacionId()+",";
			cadena+="Titulo"+ ":"+ explicacion.getTitulo()+",";
			cadena+="Tipo"+ ":"+ explicacion.getTipo()+",";
			cadena+="Numero Adjuntos"+ ":"+ explicacion.getNumeroAdjuntos()+",";
			cadena+="Fecha"+ ":"+ explicacion.getFecha()+",";
			cadena+="Fecha Compromiso"+ ":"+ explicacion.getFechaCompromiso()+",";
			cadena+="Fecha Real"+ ":"+ explicacion.getFechaReal()+",";
			cadena+="Publico"+ ":"+ explicacion.getPublico()+",";
			cadena+="Fecha Creado"+ ":"+ explicacion.getCreado();
			
			if(explicacion.getUsuarioCreado() != null) {
				cadena+=","; 
				cadena+="Usuario Creaci�n"+ ":"+ explicacion.getUsuarioCreado();
			}
			
			cadena+="}";
			
		}else if(objeto instanceof AdjuntoExplicacion) {
			
			AdjuntoExplicacion adjunto = (AdjuntoExplicacion) objeto; 
			
			
			cadena+="{";
			cadena+="Adjunto Id"+ ":"+ adjunto.getPk().getAdjuntoId()+",";
			cadena+="Explicaci�n Id"+ ":"+ adjunto.getPk().getExplicacionId()+","; 
			if(adjunto.getExplicacion() != null) {
				cadena+="Titulo Explicaci�n"+ ":"+ adjunto.getExplicacion().getTitulo()+","; 
			}
			
			cadena+="Titulo Archivo"+ ":"+ adjunto.getRuta()+","; 
			cadena+="Ruta"+ ":"+ adjunto.getRuta(); 
			cadena+="}";
			
			
		}else if(objeto instanceof MemoExplicacion) {
			
			MemoExplicacion memo = (MemoExplicacion) objeto; 
			
			cadena+="{";
			cadena+="Explicaci�n Id"+ ":"+ memo.getPk().getExplicacionId()+",";
			
			if(memo.getExplicacion() != null) {
				cadena+="Titulo"+ ":"+ memo.getExplicacion().getTitulo()+",";
			}
				
			cadena+="Tipo"+ ":"+ memo.getPk().getTipo()+",";
			cadena+="Memo"+ ":"+ memo.getMemo();
			
			cadena+="}";
			
			
		}else if(objeto instanceof InsumoFormula) {
			
			InsumoFormula insumos = (InsumoFormula) objeto; 
			
			cadena+="{";
			cadena+="Indicador Id"+ ":"+insumos.getPk().getIndicadorId()+",";
			
			cadena+="Insumo Serie Id"+ ":"+insumos.getPk().getInsumoSerieId()+",";
			if(insumos.getInsumo() != null) {
				cadena+="Insumo"+ ":"+insumos.getInsumo().getNombre()+",";
			}
			cadena+="Padre Id"+ ":"+insumos.getPk().getPadreId()+",";
			if(insumos.getPadre() != null) {
				cadena+="Nombre Padre"+ ":"+insumos.getPadre().getNombre()+",";
			}
			cadena+="Serie Id"+ ":"+insumos.getPk().getSerieId()+",";
			if(insumos.getSerieTiempo() != null) {
				cadena+="Serie tiempo"+ ":"+insumos.getSerieTiempo().getNombre()+",";
			}
			if(insumos.getFormula() != null) {
				cadena+="Formula"+ ":"+insumos.getFormula().getExpresion()+",";
			}
			
			cadena+="Valor"+ ":"+insumos.getValor();
			
			cadena+="}";
			
			
		}else if(objeto instanceof GrupoResponsable) {
			
			GrupoResponsable grupoRes = (GrupoResponsable) objeto; 
			
			cadena+="{";
			cadena+="Padre Id"+ ":"+ grupoRes.getPk().getPadreId()+",";
			cadena+="Responsable Id"+ ":"+ grupoRes.getPk().getResponsableId();
			cadena+="}";
			
		}else if(objeto instanceof Usuario) {
			
			
			Usuario usuario = (Usuario) objeto; 
			
			
			cadena+="{";
			cadena+="Usuario Id"+ ":"+ usuario.getUsuarioId()+",";
			cadena+="Nombre"+ ":"+ usuario.getFullName()+",";
			cadena+="Conectado"+ ":"+ usuario.getIsConnected()+",";
			cadena+="Bloqueado"+ ":"+ usuario.getBloqueado()+",";
			cadena+="Deshabilitado"+ ":"+ usuario.getDeshabilitado()+",";
			cadena+="Estatus"+ ":"+ usuario.getEstatus()+",";
			cadena+="Forzar Cambiar Contrase�a"+ ":"+ usuario.getForzarCambiarpwd()+",";
			cadena+="Administrador"+ ":"+ usuario.getIsAdmin()+",";
			cadena+="Instancia"+ ":"+ usuario.getInstancia()+",";
			cadena+="Password"+ ":"+ usuario.getPwd()+",";
			cadena+="Timestamp"+ ":"+ usuario.getTimeStamp()+",";
			cadena+="Sistema"+ ":"+ usuario.getIsSystem()+",";
			cadena+="Fecha Creado"+ ":"+ usuario.getCreado()+",";
			cadena+="Fecha Modificado"+ ":"+ usuario.getModificado()+",";
			cadena+="Fecha Modificaci�n Password"+ ":"+ usuario.getUltimaModifPwd();
			
			cadena+="}";
			
		}else if(objeto instanceof UserSession) {
			
			UserSession session = (UserSession) objeto; 
			
			
			cadena+="{";
			cadena+="Session Id"+ ":"+ session.getSessionId() +",";
			cadena+="Remote User"+ ":"+ session.getRemoteUser() +",";
			cadena+="Remote Host"+ ":"+ session.getRemoteHost() +",";
			cadena+="Address"+ ":"+ session.getRemoteAddress() +",";
			cadena+="URL"+ ":"+ session.getUrl();
			
			
			cadena+="}";
				
		}else if(objeto instanceof IndicadorPerspectiva) {
			
			IndicadorPerspectiva indicadorPers = (IndicadorPerspectiva) objeto; 
			
			cadena+="{";
			cadena+="Indicador Id"+ ":"+ indicadorPers.getPk().getIndicadorId()+",";
			cadena+="Perspectiva Id"+ ":"+ indicadorPers.getPk().getPerspectivaId()+",";
			cadena+="Peso"+ ":"+ indicadorPers.getPeso();
			
			if(indicadorPers.getIndicador() != null) {
				cadena+=",";
				cadena+="Nombre Indicador"+ ":"+ indicadorPers.getIndicador().getNombre();
			}
			if(indicadorPers.getPerspectiva() != null) {
				cadena+=",";
				cadena+="Nombre Perspectiva"+ ":"+ indicadorPers.getPerspectiva().getNombre();
			}
			
			cadena+="}";
			
		}else if(objeto instanceof IndicadorEstado) {
			
			IndicadorEstado indicadorEst = (IndicadorEstado) objeto; 
			
			
			cadena+="{";
			cadena+="Indicador Id"+ ":"+ indicadorEst.getPk().getIndicadorId()+",";
			cadena+="Plan Id"+ ":"+ indicadorEst.getPk().getPlanId()+",";
			cadena+="A�o"+ ":"+ indicadorEst.getPk().getAno()+",";
			cadena+="Periodo"+ ":"+ indicadorEst.getPk().getPeriodo()+",";
			cadena+="Tipo"+ ":"+ indicadorEst.getPk().getTipo()+",";
			cadena+="Estado"+ ":"+ indicadorEst.getEstado();
			
			cadena+="}";
			
			
		}else if(objeto instanceof IndicadorPlan) {
			
			IndicadorPlan indicadorPlan = (IndicadorPlan) objeto; 
			
			
			cadena+="{";
			cadena+="Indicador Id"+ ":"+ indicadorPlan.getPk().getIndicadorId()+",";
			cadena+="Plan Id"+ ":"+ indicadorPlan.getPk().getPlanId()+",";
			cadena+="Crecimiento"+ ":"+ indicadorPlan.getCrecimiento()+",";
			cadena+="Peso"+ ":"+ indicadorPlan.getPeso()+",";
			cadena+="Tipo Medici�n"+ ":"+ indicadorPlan.getTipoMedicion();
			if(indicadorPlan.getIndicador() != null) {
				cadena+=",";
				cadena+="Nombre Indicador"+ ":"+ indicadorPlan.getIndicador().getNombre();
			}
			if(indicadorPlan.getPlan() != null) {
				cadena+=",";
				cadena+="Nombre Plan"+ ":"+ indicadorPlan.getPlan().getNombre();
			}
			
			cadena+="}";
			
						
		}else if(objeto instanceof Grafico) {
			
			Grafico grafico= (Grafico) objeto; 
			
			
			cadena+="{";
			cadena+="Grafico Id"+ ":"+ grafico.getGraficoId()+","; 
			cadena+="Nombre"+ ":"+ grafico.getGraficoId();
			
			if(grafico.getOrganizacion() != null) {
				cadena+=",";
				cadena+="Nombre Organizaci�n"+ ":"+ grafico.getOrganizacion().getNombre();
			}
			if(grafico.getUsuario() != null) {
				cadena+=",";
				cadena+="Nombre Usuario"+ ":"+ grafico.getUsuario().getFullName();
			}
			
			cadena+="}";
			
			
		}else if(objeto instanceof Reporte) {
			
			Reporte reporte = (Reporte) objeto; 
			
			
			cadena+="{";
			cadena+="Reporte Id"+ ":"+ reporte.getReporteId()+","; 
			cadena+="Nombre"+ ":"+ reporte.getNombre()+",";
			cadena+="Descripci�n"+ ":"+ reporte.getDescripcion()+",";
			cadena+="Corte"+ ":"+ reporte.getCorte()+",";
			cadena+="Publico"+ ":"+ reporte.getPublico()+",";
			cadena+="Tipo"+ ":"+ reporte.getTipo();
			
			if(reporte.getOrganizacion() != null) {
				cadena+=",";
				cadena+="Nombre Organizaci�n"+ ":"+ reporte.getOrganizacion().getNombre();
			}
			if(reporte.getUsuario() != null) {
				cadena+=",";
				cadena+="Nombre Usuario"+ ":"+ reporte.getUsuario().getFullName();
			}
			
			cadena+="}";
			
			
		}else if(objeto instanceof PlantillaPlanes) {
			
			PlantillaPlanes plantillaPlanes = (PlantillaPlanes) objeto; 
			
			
			cadena+="{";
			cadena+="Plantilla Planes Id"+ ":"+ plantillaPlanes.getPlantillaPlanesId()+","; 
			cadena+="Nombre"+ ":"+ plantillaPlanes.getNombre()+","; 
			cadena+="Descripci�n"+ ":"+ plantillaPlanes.getDescripcion()+","; 
			cadena+="Tipo"+ ":"+ plantillaPlanes.getTipo()+","; 
			cadena+="Nombre Iniciativas Singular"+ ":"+ plantillaPlanes.getNombreIniciativaSingular()+","; 
			cadena+="Nombre Actividad Singular"+ ":"+ plantillaPlanes.getNombreActividadSingular()+","; 
			cadena+="Nombre Inidicador Singular"+ ":"+ plantillaPlanes.getNombreIndicadorSingular()+","; 
			cadena+="Fecha Creado"+ ":"+ plantillaPlanes.getCreado()+","; 
			cadena+="Fecha Modificado"+ ":"+ plantillaPlanes.getModificado(); 			
			cadena+="}";
			
			
		}else if(objeto instanceof PryProyecto) {
			
			PryProyecto proyecto = (PryProyecto) objeto; 
			
			
			cadena+="{";
			cadena+="Proyecto Id"+ ":"+ proyecto.getProyectoId() +","; 
			cadena+="Nombre"+ ":"+ proyecto.getNombre() +",";
			cadena+="Comienzo"+ ":"+ proyecto.getComienzo() +",";
			cadena+="Comienzo Plan"+ ":"+ proyecto.getComienzoPlan() +",";
			cadena+="Comienzo Real"+ ":"+ proyecto.getComienzoReal() +",";
			cadena+="Duraci�n Plan"+ ":"+ proyecto.getDuracionPlan() +",";
			cadena+="Duraci�n Real"+ ":"+ proyecto.getDuracionReal() +",";
			cadena+="Fin Plan"+ ":"+ proyecto.getFinPlan() +",";
			cadena+="Fin Real"+ ":"+ proyecto.getFinReal() +",";
			cadena+="Variaci�n Comienzo"+ ":"+ proyecto.getVariacionComienzo() +",";
			cadena+="Variaci�n Duraci�n"+ ":"+ proyecto.getVariacionDuracion() +",";
			cadena+="Variaci�n Fin"+ ":"+ proyecto.getVariacionFin() +",";
			cadena+="Fecha Creado"+ ":"+ proyecto.getCreado() +",";
			cadena+="Fecha Modificado"+ ":"+ proyecto.getModificado();
			cadena+="}";
			
			
		}else if(objeto instanceof TipoProyecto) {
			
			
			TipoProyecto tipoProyecto = (TipoProyecto) objeto; 
			
			cadena+="{";
			cadena+="Tipo Proyecto Id"+ ":"+ tipoProyecto.getTipoProyectoId()+",";
			cadena+="Nombre"+ ":"+ tipoProyecto.getNombre(); 
			cadena+="}";
			
		}else if(objeto instanceof Grupo) {
			
			
			Grupo grupo = (Grupo) objeto; 
			
			cadena+="{";
			cadena+="Grupo Id"+ ":"+ grupo.getGrupoId() +",";
			cadena+="Grupo"+ ":"+ grupo.getGrupo() +",";
			cadena+="Fecha Creado"+ ":"+ grupo.getCreado()+",";
			cadena+="Fecha Modificado"+ ":"+ grupo.getModificado();
			cadena+="}";
					
		}
		
		
		return cadena;
	}
	
	
	

	public Object getUltimaAuditoriaAtributo(String instanciaId, String nombreAtributo, byte tipoAtributo) 
	{
		Criteria consulta = null;
		Query consultaMemo = null;

		if (tipoAtributo == AuditoriaTipoAtributo.getAuditoriaTipoAtributoString()) 
			consulta = this.prepareQuery(AuditoriaString.class);
		else if (tipoAtributo == AuditoriaTipoAtributo.getAuditoriaTipoAtributoMemo()) 
		    consultaMemo = this.session.createQuery("select auditoriaMemo.valor from AuditoriaMemo auditoriaMemo where auditoriaMemo.pk.instanciaId = '" + instanciaId + "' and auditoriaMemo.pk.nombreAtributo = '" + nombreAtributo + "' Order by auditoriaMemo.pk.fecha desc");
		else if (tipoAtributo == AuditoriaTipoAtributo.getAuditoriaTipoAtributoFecha()) 
			consulta = this.prepareQuery(AuditoriaFecha.class);
		else if (tipoAtributo == AuditoriaTipoAtributo.getAuditoriaTipoAtributoEntero()) 
			consulta = this.prepareQuery(AuditoriaEntero.class);
		else if (tipoAtributo == AuditoriaTipoAtributo.getAuditoriaTipoAtributoFlotante()) 
			consulta = this.prepareQuery(AuditoriaFlotante.class);
		
		if (tipoAtributo != AuditoriaTipoAtributo.getAuditoriaTipoAtributoMemo())
		{
			consulta.add(Restrictions.eq("pk.instanciaId", instanciaId)).add(Restrictions.eq("pk.nombreAtributo", nombreAtributo));
			consulta.addOrder(Order.desc("pk.fecha"));
		}

		Object ultimaAuditoria = null;
		try
		{
			List<?> auditorias = new ArrayList<Object>();
			if (tipoAtributo != AuditoriaTipoAtributo.getAuditoriaTipoAtributoMemo())
				auditorias = consulta.list();
			else
				auditorias = consultaMemo.list();

			if (auditorias.size() > 0)
			{
				if (tipoAtributo != AuditoriaTipoAtributo.getAuditoriaTipoAtributoMemo())
					ultimaAuditoria = auditorias.get(0);
				else
					ultimaAuditoria = (String)auditorias.get(0);
			}
		}
		catch (HibernateException e)
		{
			String mensaje = e.getMessage();
			HibernateException he = new HibernateException(mensaje);
			throw new ChainedRuntimeException("no pudo leer en la base de datos", he);
		}

		return ultimaAuditoria;
	}

	public PaginaLista getPaginaLista(Query consulta, int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal) 
	{
		int total = 0;

		if (getTotal) 
			total = consulta.list().size();

		if ((tamanoPagina > 0) && (pagina > 0)) 
		{
			int primerResultado = (tamanoPagina * pagina) - tamanoPagina;
			if ((getTotal) && ((primerResultado + 1) > total)) 
			{
				pagina = 1;
				primerResultado = (tamanoPagina * pagina) - tamanoPagina;
			}
			consulta.setFirstResult(primerResultado).setMaxResults(tamanoPagina);
		}

		List lista = consulta.list();
		if (!getTotal) 
			total = lista.size();

		PaginaLista paginaLista = new PaginaLista();

		paginaLista.setLista(lista);
		paginaLista.setNroPagina(pagina);
		paginaLista.setTamanoPagina(tamanoPagina);
		paginaLista.setTotal(total);
		paginaLista.setOrden(orden);
		paginaLista.setTipoOrden(tipoOrden);

		return paginaLista;
	}
	
	public String getGuid()
	{
		UUID uuid = UUID.randomUUID();

		return uuid.toString();
	}
	
	/// nueva auditoria
	
	
	
	
	
}
