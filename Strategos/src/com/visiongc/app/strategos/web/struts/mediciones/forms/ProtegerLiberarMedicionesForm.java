/**
 * 
 */
package com.visiongc.app.strategos.web.struts.mediciones.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;

/**
 * @author Kerwin
 *
 */
public class ProtegerLiberarMedicionesForm extends ActionForm
{
	static final long serialVersionUID = 0L;
	private Byte accion;
	private Byte tipo;
	private Byte tipoSeleccion;
	private Byte origen;
	private Byte status;
	private Byte serieId;
	private String fechaFinal;
	private String respuesta;
	private Integer ano;
	private Integer mesInicial;
	private Integer mesFinal;
	private List<Long> indicadores;
	private Long claseId;
	private Long organizacionId;
	private Long indicadorId;
	private Long perspectivaId;
	private Long planId;
	private String altoForma;
	
	
	
	public Byte getSerieId() {
		return serieId;
	}

	public void setSerieId(Byte serieId) {
		this.serieId = serieId;
	}

	public Byte getAccion()
	{
		return this.accion;
	}

	public void setAccion(Byte accion) 
	{
	    this.accion = ProtegerLiberarAccion.getProtegerLiberarAccion(accion);
	}

	public Byte getTipo() 
	{
	    return this.tipo;
	}

	public void setTipo(Byte tipo) 
	{
	    this.tipo = ProtegerLiberarTipo.getProtegerLiberarTipo(tipo);
	}

	public Byte getTipoSeleccion() 
	{
	    return this.tipoSeleccion;
	}

	public void setTipoSeleccion(Byte tipoSeleccion) 
	{
	    this.tipoSeleccion = ProtegerLiberarSeleccion.getProtegerLiberarSeleccion(tipoSeleccion);
	}

	public Integer getAno() 
	{
	    return this.ano;
	}

	public void setAno(Integer ano) 
	{
	    this.ano = ano;
	}

	public Integer getMesInicial() 
	{
	    return this.mesInicial;
	}

	public void setMesInicial(Integer mesInicial) 
	{
	    this.mesInicial = mesInicial;
	}

	public Integer getMesFinal() 
	{
	    return this.mesFinal;
	}

	public void setMesFinal(Integer mesFinal) 
	{
	    this.mesFinal = mesFinal;
	}

	public String getFechaFinal() 
	{
	    return this.fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) 
	{
	    this.fechaFinal = fechaFinal;
	}

	public List<Long> getIndicadores() 
	{
	    return this.indicadores;
	}

	public void setIndicadores(List<Long> indicadores) 
	{
	    this.indicadores = indicadores;
	}

	public Long getClaseId() 
	{
	    return this.claseId;
	}

	public void setClaseId(Long claseId) 
	{
	    this.claseId = claseId;
	}

	public Long getOrganizacionId() 
	{
	    return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) 
	{
	    this.organizacionId = organizacionId;
	}

	public Long getIndicadorId() 
	{
	    return this.indicadorId;
	}

	public void setIndicadorId(Long indicadorId) 
	{
	    this.indicadorId = indicadorId;
	}

	public Long getPerspectivaId() 
	{
	    return this.perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) 
	{
	    this.perspectivaId = perspectivaId;
	}

	public Long getPlanId() 
	{
	    return this.planId;
	}

	public void setPlanId(Long planId) 
	{
	    this.planId = planId;
	}
	
	public String getRespuesta()
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) 
	{
		this.respuesta = respuesta;
	}

	public Byte getStatus()
	{
		return this.status;
	}

	public void setStatus(Byte status) 
	{
		this.status = ProtegerLiberarStatus.getProtegerLiberarStatus(status);
	}
	
	public Byte getOrigen()
	{
		return this.origen;
	}

	public void setOrigen(Byte origen) 
	{
		this.origen = ProtegerLiberarOrigen.getProtegerLiberarOrigen(origen);
	}

	public String getAltoForma()
	{
		return this.altoForma;
	}

	public void setAltoForma(String altoForma) 
	{
		this.altoForma = altoForma;
	}
	
	public Byte getOrigenOrganizaciones() 
	{
		return ProtegerLiberarOrigen.getOrigenOrganizaciones();
	}

	public Byte getOrigenIndicadores() 
	{
		return ProtegerLiberarOrigen.getOrigenIndicadores();
	}

	public Byte getOrigenPlanes() 
	{
		return ProtegerLiberarOrigen.getOrigenPlanes();
	}

	public Byte getOrigenIniciativas() 
	{
		return ProtegerLiberarOrigen.getOrigenIniciativas();
	}

	public Byte getOrigenActividades() 
	{
		return ProtegerLiberarOrigen.getOrigenActividades();
	}
	
	public Byte getSeleccionIndicador() 
	{
		return ProtegerLiberarSeleccion.getSeleccionIndicador();
	}

	public Byte getSeleccionIndicadoresSeleccionados() 
	{
		return ProtegerLiberarSeleccion.getSeleccionIndicadoresSeleccionados();
	}
	
	public Byte getSeleccionClase() 
	{
		return ProtegerLiberarSeleccion.getSeleccionClase();
	}

	public Byte getSeleccionSubClase() 
	{
		return ProtegerLiberarSeleccion.getSeleccionSubClase();
	}
	
	public Byte getSeleccionOrganizacion() 
	{
		return ProtegerLiberarSeleccion.getSeleccionOrganizacion();
	}

	public Byte getSeleccionSubOrganizacion() 
	{
		return ProtegerLiberarSeleccion.getSeleccionSubOrganizacion();
	}

	public Byte getSeleccionOrganizacionTodas() 
	{
		return ProtegerLiberarSeleccion.getSeleccionOrganizacionTodas();
	}

	public Byte getSeleccionPlan() 
	{
		return ProtegerLiberarSeleccion.getSeleccionPlan();
	}

	public Byte getSeleccionPerspectiva() 
	{
		return ProtegerLiberarSeleccion.getSeleccionPerspectiva();
	}

	public Byte getSeleccionIniciativa() 
	{
		return ProtegerLiberarSeleccion.getSeleccionIniciativa();
	}

	public Byte getAccionProteger() 
	{
		return ProtegerLiberarAccion.getAccionBloquear();
	}

	public Byte getAccionLiberar() 
	{
		return ProtegerLiberarAccion.getAccionLiberar();
	}
	
	public static class ProtegerLiberarAccion
	{
		private byte id;
		private String nombre;
		private static final byte ACCION_BLOQUEAR = 1;
		private static final byte ACCION_LIBERAR = 2;
		
		private static Byte getProtegerLiberarAccion(Byte protegerLiberarAccion)
		{
			if (protegerLiberarAccion == ACCION_BLOQUEAR)
				return new Byte(ACCION_BLOQUEAR);
			else if (protegerLiberarAccion == ACCION_LIBERAR)
				return new Byte(ACCION_LIBERAR);
			else
				return null;
		}
		
		public static Byte getAccionBloquear() 
		{
			return new Byte(ACCION_BLOQUEAR);
		}
		
		public static Byte getAccionLiberar() 
		{
			return new Byte(ACCION_LIBERAR);
		}
		
		public byte getId() 
		{
			return id;
		}

		public void setId(byte id) 
		{
			this.id = id;
		}

		public String getNombre() 
		{
			return nombre;
		}

		public void setNombre(String nombre) 
		{
			this.nombre = nombre;
		}
		
		public static List<ProtegerLiberarAccion> getTiposAcciones() 
		{
			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	
			List<ProtegerLiberarAccion> protegerLiberarAcciones = new ArrayList<ProtegerLiberarAccion>();
	
			ProtegerLiberarAccion protegerLiberarAccion = new ProtegerLiberarAccion();
			protegerLiberarAccion.setId(ACCION_BLOQUEAR);
			protegerLiberarAccion.nombre = messageResources.getResource("protegerliberar.accion.bloquear");
			protegerLiberarAcciones.add(protegerLiberarAccion);
	
			protegerLiberarAccion = new ProtegerLiberarAccion();
			protegerLiberarAccion.setId(ACCION_LIBERAR);
			protegerLiberarAccion.nombre = messageResources.getResource("protegerliberar.accion.liberar");
			protegerLiberarAcciones.add(protegerLiberarAccion);
	
			return protegerLiberarAcciones;
		}
		
		public static String getNombre(byte status) 
		{
			String nombre = "";

			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

			if (status == ACCION_BLOQUEAR) 
				nombre = messageResources.getResource("protegerliberar.accion.bloquear");
			else if (status == ACCION_LIBERAR) 
				nombre = messageResources.getResource("protegerliberar.accion.liberar");

			return nombre;
		}
	}

	public static class ProtegerLiberarOrigen
	{
		private byte id;
		private String nombre;
		private static final byte ORIGEN_ORGANIZACIONES = 1;
		private static final byte ORIGEN_INDICADORES = 2;
		private static final byte ORIGEN_PLANES = 3;
		private static final byte ORIGEN_INICIATIVAS = 4;
		private static final byte ORIGEN_ACTIVIDADES = 5;
		
		private static Byte getProtegerLiberarOrigen(Byte protegerLiberarOrigen)
		{
			if (protegerLiberarOrigen == ORIGEN_ORGANIZACIONES)
				return new Byte(ORIGEN_ORGANIZACIONES);
			else if (protegerLiberarOrigen == ORIGEN_INDICADORES)
				return new Byte(ORIGEN_INDICADORES);
			else if (protegerLiberarOrigen == ORIGEN_PLANES)
				return new Byte(ORIGEN_PLANES);
			else if (protegerLiberarOrigen == ORIGEN_INICIATIVAS)
				return new Byte(ORIGEN_INICIATIVAS);
			else if (protegerLiberarOrigen == ORIGEN_ACTIVIDADES)
				return new Byte(ORIGEN_ACTIVIDADES);
			else
				return null;
		}
		
		public static Byte getOrigenOrganizaciones() 
		{
			return new Byte(ORIGEN_ORGANIZACIONES);
		}
		
		public static Byte getOrigenIndicadores() 
		{
			return new Byte(ORIGEN_INDICADORES);
		}

		public static Byte getOrigenPlanes() 
		{
			return new Byte(ORIGEN_PLANES);
		}
		
		public static Byte getOrigenIniciativas() 
		{
			return new Byte(ORIGEN_INICIATIVAS);
		}

		public static Byte getOrigenActividades() 
		{
			return new Byte(ORIGEN_ACTIVIDADES);
		}
		
		public byte getId() 
		{
			return id;
		}

		public void setId(byte id) 
		{
			this.id = id;
		}

		public String getNombre() 
		{
			return nombre;
		}

		public void setNombre(String nombre) 
		{
			this.nombre = nombre;
		}
	}
	
	public static class ProtegerLiberarTipo
	{
		private byte id;
		private String nombre;
		private static final byte TIPO_POR_MEDICION = 1;
		private static final byte TIPO_POR_FECHA = 2;
		
		private static Byte getProtegerLiberarTipo(Byte protegerLiberarTipo)
		{
			if (protegerLiberarTipo == TIPO_POR_MEDICION)
				return new Byte(TIPO_POR_MEDICION);
			else if (protegerLiberarTipo == TIPO_POR_FECHA)
				return new Byte(TIPO_POR_FECHA);
			else
				return null;
		}
		
		public static Byte getTipoPorMedicion() 
		{
			return new Byte(TIPO_POR_MEDICION);
		}
		
		public static Byte getTipoPorFecha() 
		{
			return new Byte(TIPO_POR_FECHA);
		}
		
		public byte getId() 
		{
			return id;
		}

		public void setId(byte id) 
		{
			this.id = id;
		}

		public String getNombre() 
		{
			return nombre;
		}

		public void setNombre(String nombre) 
		{
			this.nombre = nombre;
		}
		
		public static List<ProtegerLiberarTipo> getTiposEstatus() 
		{
			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	
			List<ProtegerLiberarTipo> protegerLiberarTipos = new ArrayList<ProtegerLiberarTipo>();
	
			ProtegerLiberarTipo protegerLiberarTipo = new ProtegerLiberarTipo();
			protegerLiberarTipo.setId(TIPO_POR_MEDICION);
			protegerLiberarTipo.nombre = messageResources.getResource("protegerliberar.tipo.medicion");
			protegerLiberarTipos.add(protegerLiberarTipo);
	
			protegerLiberarTipo = new ProtegerLiberarTipo();
			protegerLiberarTipo.setId(TIPO_POR_FECHA);
			protegerLiberarTipo.nombre = messageResources.getResource("protegerliberar.tipo.fecha");
			protegerLiberarTipos.add(protegerLiberarTipo);
	
			return protegerLiberarTipos;
		}
		
		public static String getNombre(byte status) 
		{
			String nombre = "";

			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

			if (status == TIPO_POR_MEDICION) 
				nombre = messageResources.getResource("protegerliberar.tipo.medicion");
			else if (status == TIPO_POR_FECHA) 
				nombre = messageResources.getResource("protegerliberar.tipo.fecha");

			return nombre;
		}
	}
	
	public static class ProtegerLiberarSeleccion
	{
		private byte id;
		private String nombre;
		private static final byte SELECCION_INDICADOR = 1;
		private static final byte SELECCION_INDICADORES_SELECCIONADOS = 2;
		private static final byte SELECCION_CLASE = 3;
		private static final byte SELECCION_CLASE_SUBCLASES = 4;
		private static final byte SELECCION_ORGANIZACION = 5;
		private static final byte SELECCION_ORGANIZACION_SUBORGANIZACION = 6;
		private static final byte SELECCION_ORGANIZACION_TODAS = 7;
		private static final byte SELECCION_PLAN = 8;
		private static final byte SELECCION_PERSPECTIVA = 9;
		private static final byte SELECCION_INICIATIVA = 10;
		
		private static Byte getProtegerLiberarSeleccion(Byte protegerLiberarSeleccion)
		{
			if (protegerLiberarSeleccion == SELECCION_INDICADOR)
				return new Byte(SELECCION_INDICADOR);
			else if (protegerLiberarSeleccion == SELECCION_INDICADORES_SELECCIONADOS)
				return new Byte(SELECCION_INDICADORES_SELECCIONADOS);
			else if (protegerLiberarSeleccion == SELECCION_CLASE)
				return new Byte(SELECCION_CLASE);
			else if (protegerLiberarSeleccion == SELECCION_CLASE_SUBCLASES)
				return new Byte(SELECCION_CLASE_SUBCLASES);
			else if (protegerLiberarSeleccion == SELECCION_ORGANIZACION)
				return new Byte(SELECCION_ORGANIZACION);
			else if (protegerLiberarSeleccion == SELECCION_ORGANIZACION_SUBORGANIZACION)
				return new Byte(SELECCION_ORGANIZACION_SUBORGANIZACION);
			else if (protegerLiberarSeleccion == SELECCION_ORGANIZACION_TODAS)
				return new Byte(SELECCION_ORGANIZACION_TODAS);
			else if (protegerLiberarSeleccion == SELECCION_PLAN)
				return new Byte(SELECCION_PLAN);
			else if (protegerLiberarSeleccion == SELECCION_PERSPECTIVA)
				return new Byte(SELECCION_PERSPECTIVA);
			else if (protegerLiberarSeleccion == SELECCION_INICIATIVA)
				return new Byte(SELECCION_INICIATIVA);
			else
				return null;
		}
		
		public static Byte getSeleccionIndicador() 
		{
			return new Byte(SELECCION_INDICADOR);
		}
		
		public static Byte getSeleccionIndicadoresSeleccionados() 
		{
			return new Byte(SELECCION_INDICADORES_SELECCIONADOS);
		}

		public static Byte getSeleccionClase() 
		{
			return new Byte(SELECCION_CLASE);
		}

		public static Byte getSeleccionSubClase() 
		{
			return new Byte(SELECCION_CLASE_SUBCLASES);
		}
		
		public static Byte getSeleccionOrganizacion() 
		{
			return new Byte(SELECCION_ORGANIZACION);
		}

		public static Byte getSeleccionSubOrganizacion() 
		{
			return new Byte(SELECCION_ORGANIZACION_SUBORGANIZACION);
		}
		
		public static Byte getSeleccionOrganizacionTodas() 
		{
			return new Byte(SELECCION_ORGANIZACION_TODAS);
		}
		
		public static Byte getSeleccionPlan() 
		{
			return new Byte(SELECCION_PLAN);
		}

		public static Byte getSeleccionPerspectiva() 
		{
			return new Byte(SELECCION_PERSPECTIVA);
		}

		public static Byte getSeleccionIniciativa() 
		{
			return new Byte(SELECCION_INICIATIVA);
		}

		public byte getId() 
		{
			return id;
		}

		public void setId(byte id) 
		{
			this.id = id;
		}

		public String getNombre() 
		{
			return nombre;
		}

		public void setNombre(String nombre) 
		{
			this.nombre = nombre;
		}
		
		public static List<ProtegerLiberarSeleccion> getTiposEstatus() 
		{
			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	
			List<ProtegerLiberarSeleccion> protegerLiberarSelecciones = new ArrayList<ProtegerLiberarSeleccion>();
	
			ProtegerLiberarSeleccion protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_INDICADOR);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.indicador");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);
	
			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_INDICADORES_SELECCIONADOS);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.indicadoresseleccionados");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);

			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_CLASE);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.clase");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);
			
			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_CLASE_SUBCLASES);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.subclase");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);

			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_ORGANIZACION);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.organizacion");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);

			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_ORGANIZACION_SUBORGANIZACION);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.suborganizacion");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);
			
			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_ORGANIZACION_TODAS);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.todasorganizaciones");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);
			
			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_PLAN);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.plan");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);

			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_PERSPECTIVA);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.perspectiva");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);

			protegerLiberarSeleccion = new ProtegerLiberarSeleccion();
			protegerLiberarSeleccion.setId(SELECCION_INICIATIVA);
			protegerLiberarSeleccion.nombre = messageResources.getResource("protegerliberar.seleccion.iniciativa");
			protegerLiberarSelecciones.add(protegerLiberarSeleccion);

			return protegerLiberarSelecciones;
		}
		
		public static String getNombre(byte status) 
		{
			String nombre = "";

			VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

			if (status == SELECCION_INDICADOR) 
				nombre = messageResources.getResource("protegerliberar.seleccion.indicador");
			else if (status == SELECCION_INDICADORES_SELECCIONADOS) 
				nombre = messageResources.getResource("protegerliberar.seleccion.indicadoresseleccionados");
			else if (status == SELECCION_CLASE) 
				nombre = messageResources.getResource("protegerliberar.seleccion.clase");
			else if (status == SELECCION_CLASE_SUBCLASES) 
				nombre = messageResources.getResource("protegerliberar.seleccion.subclase");
			else if (status == SELECCION_ORGANIZACION) 
				nombre = messageResources.getResource("protegerliberar.seleccion.organizacion");
			else if (status == SELECCION_ORGANIZACION_SUBORGANIZACION) 
				nombre = messageResources.getResource("protegerliberar.seleccion.suborganizacion");
			else if (status == SELECCION_ORGANIZACION_TODAS) 
				nombre = messageResources.getResource("protegerliberar.seleccion.todasorganizaciones");
			else if (status == SELECCION_PLAN) 
				nombre = messageResources.getResource("protegerliberar.seleccion.plan");
			else if (status == SELECCION_PERSPECTIVA) 
				nombre = messageResources.getResource("protegerliberar.seleccion.perspectiva");
			else if (status == SELECCION_INICIATIVA) 
				nombre = messageResources.getResource("protegerliberar.seleccion.iniciativa");

			return nombre;
		}
	}
	
	public static class ProtegerLiberarStatus
	{
		private static final byte IMPORTARSTATUS_SUCCESS = 0;
		private static final byte IMPORTARSTATUS_NOSUCCESS = 1;
		private static final byte IMPORTARSTATUS_NOCONFIGURADO =2;
		
		private static Byte getProtegerLiberarStatus(Byte status)
		{
			if (status == IMPORTARSTATUS_SUCCESS)
				return new Byte(IMPORTARSTATUS_SUCCESS);
			else if (status == IMPORTARSTATUS_NOSUCCESS)
				return new Byte(IMPORTARSTATUS_NOSUCCESS);
			else if (status == IMPORTARSTATUS_NOCONFIGURADO)
				return new Byte(IMPORTARSTATUS_NOCONFIGURADO);
			else
				return null;
		}
		
		public static Byte getImportarStatusSuccess() 
		{
			return new Byte(IMPORTARSTATUS_SUCCESS);
		}
		
		public static Byte getImportarStatusNoSuccess() 
		{
			return new Byte(IMPORTARSTATUS_NOSUCCESS);
		}
		
		public static Byte getImportarStatusNoConfigurado() 
		{
			return new Byte(IMPORTARSTATUS_NOCONFIGURADO);
		}
		
	}
}