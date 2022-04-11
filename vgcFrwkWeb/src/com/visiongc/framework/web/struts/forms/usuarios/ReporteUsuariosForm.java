package com.visiongc.framework.web.struts.forms.usuarios;


import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

import java.util.List;

import com.visiongc.framework.model.Grupo;

public class ReporteUsuariosForm extends EditarObjetoForm {
	
	
	private static final long serialVersionUID = 0L;
	
	private Long organizacionId;
	private String organizacionNombre;	
	private Integer estatus;
	private Byte tipoReporte;
	
	private Long grupoId;
	private List<Grupo> grupos;
	private Grupo grupo;
	
	
	
	public Long getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(Long grupoId) {
		this.grupoId = grupoId;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	
	public Long getOrganizacionId() {
		return this.organizacionId;
	}
	
	
	public void setOrganizacionNombre(String organizacionNombre) {
		this.organizacionNombre = organizacionNombre;
	}
	
	public String getOrganizacionNombre() {
		return this.organizacionNombre;
	}
	

	public void setEstatus(Integer estatus) {
		this.estatus = ReporteUsuariosForm.Estatus.getEstatus(estatus);
	}
	
	public Integer getEstatus() {
		return this.estatus;
	}
	

	public void setTipoReporte(Byte tipoReporte) {
		this.tipoReporte = ReporteUsuariosForm.TipoReportes.getTipoReportes(tipoReporte);
	}
		
	public Byte getTipoReporte() {
		return this.tipoReporte;
	}

	@Override
	public void clear() {
		organizacionId = null;
		organizacionNombre = null;
		estatus = null;
		this.tipoReporte = ReporteUsuariosForm.TipoReportes.TIPO_PDF;
	}
	
	public static class Estatus{
		private static final Integer ACTIVO = 1;
		private static final Integer INACTIVO = 0;
		
		public static Integer getEstatus(Integer estatus) {
			if (estatus == ACTIVO)
				return new Integer(ACTIVO);
			else if (estatus == INACTIVO)
				return new Integer(INACTIVO);
			else 
				return null;		
		}
	}
	
	public static class TipoReportes
	{
		private static final byte TIPO_PDF = 1;
		private static final byte TIPO_EXCEL = 2;
		
		public static Byte getTipoReportes(Byte tipoReporte)
		{
			if (tipoReporte == TIPO_PDF)
				return new Byte(TIPO_PDF);
			else if (tipoReporte == TIPO_EXCEL)
				return new Byte(TIPO_EXCEL);
			else
				return null;
		}
	}

}
