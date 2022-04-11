/**
 * 
 */
package com.visiongc.app.strategos.web.struts.configuracion.forms;

import java.sql.Time;

import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm.PeriodoStatus;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

/**
 * @author Kerwin
 *
 */
public class EditarConfiguracionSistemaForm extends EditarObjetoForm 
{
	static final long serialVersionUID = 0;
	
	// Indicador
	private Integer indicadorNivel;
	
	// Iniciativa
	private String iniciativaNombre;
	private String iniciativaIndicadorAvanceNombre;
	private Boolean iniciativaIndicadorAvanceMostrar;
	private String iniciativaIndicadorPresupuestoNombre;
	private Boolean iniciativaIndicadorPresupuestoMostrar;
	private String iniciativaIndicadorEficaciaNombre;
	private Boolean iniciativaIndicadorEficaciaMostrar;
	private String iniciativaIndicadorEficienciaNombre;
	private Boolean iniciativaIndicadorEficienciaMostrar;
	private Boolean iniciativaIndicadorAvanceAnteponer;
	
	// Plan
	private Boolean planObjetivoAlertaAnualMostrar;
	private Boolean planObjetivoAlertaParcialMostrar;
	private Boolean planObjetivoLogroAnualMostrar;
	private Boolean planObjetivoLogroParcialMostrar;
	
	// Responsable
  	private Boolean enviarResponsableFijarMeta;
  	private Boolean enviarResponsableLograrMeta;
  	private Boolean enviarResponsableSeguimiento;
  	private Boolean enviarResponsableCargarMeta;
  	private Boolean enviarResponsableCargarEjecutado;
 	private Boolean enviarResponsableNegativo;
  	
  	// Correo
  	private Byte tipoCorreoPorDefecto;
  	
  	
  	// inventario
  	private Integer dia;
  	private String titulo;
  	private String texto; 
  	private String hora;
  	private String correo;
  	private Boolean enviarResponsableFijarMetaInv;
  	private Boolean enviarResponsableLograrMetaInv;
  	private Boolean enviarResponsableSeguimientoInv;
  	private Boolean enviarResponsableCargarMetaInv;
  	private Boolean enviarResponsableCargarEjecutadoInv;
  	private Boolean enviarResponsableNegativoInv;
  	
  	// iniciativa - correo
  	private String dia1;
  	private String titulo1;
  	private String texto1; 
  	private String hora1;
  	private String correo1;
  	private Boolean enviarResponsableFijarMetaInv1;
  	private Boolean enviarResponsableLograrMetaInv1;
  	private Boolean enviarResponsableSeguimientoInv1;
  	private Boolean enviarResponsableCargarMetaInv1;
  	private Boolean enviarResponsableCargarEjecutadoInv1;
  	private Boolean enviarResponsableNegativoInv1;
  	
  	
	public Integer getIndicadorNivel() 
	{
		return this.indicadorNivel;
	}

	public void setIndicadorNivel(Integer indicadorNivel) 
	{
		this.indicadorNivel = indicadorNivel;
	}

	public String getIniciativaNombre() 
	{
		return this.iniciativaNombre;
	}

	public void setIniciativaNombre(String iniciativaNombre) 
	{
		this.iniciativaNombre = iniciativaNombre;
	}

	public String getIniciativaIndicadorAvanceNombre() 
	{
		return this.iniciativaIndicadorAvanceNombre;
	}

	public void setIniciativaIndicadorAvanceNombre(String iniciativaIndicadorAvanceNombre) 
	{
		this.iniciativaIndicadorAvanceNombre = iniciativaIndicadorAvanceNombre;
	}

	public Boolean getIniciativaIndicadorAvanceMostrar() 
	{
		return this.iniciativaIndicadorAvanceMostrar;
	}

	public void setIniciativaIndicadorAvanceMostrar(Boolean iniciativaIndicadorAvanceMostrar) 
	{
		this.iniciativaIndicadorAvanceMostrar = iniciativaIndicadorAvanceMostrar;
	}

	public String getIniciativaIndicadorPresupuestoNombre() 
	{
		return this.iniciativaIndicadorPresupuestoNombre;
	}

	public void setIniciativaIndicadorPresupuestoNombre(String iniciativaIndicadorPresupuestoNombre) 
	{
		this.iniciativaIndicadorPresupuestoNombre = iniciativaIndicadorPresupuestoNombre;
	}
	
	public Boolean getIniciativaIndicadorPresupuestoMostrar() 
	{
		return this.iniciativaIndicadorPresupuestoMostrar;
	}

	public void setIniciativaIndicadorPresupuestoMostrar(Boolean iniciativaIndicadorPresupuestoMostrar) 
	{
		this.iniciativaIndicadorPresupuestoMostrar = iniciativaIndicadorPresupuestoMostrar;
	}

	public String getIniciativaIndicadorEficaciaNombre() 
	{
		return this.iniciativaIndicadorEficaciaNombre;
	}
	
	public void setIniciativaIndicadorEficaciaNombre(String iniciativaIndicadorEficaciaNombre) 
	{
		this.iniciativaIndicadorEficaciaNombre = iniciativaIndicadorEficaciaNombre;
	}

	public Boolean getIniciativaIndicadorEficaciaMostrar() 
	{
		return this.iniciativaIndicadorEficaciaMostrar;
	}

	public void setIniciativaIndicadorEficaciaMostrar(Boolean iniciativaIndicadorEficaciaMostrar) 
	{
		this.iniciativaIndicadorEficaciaMostrar = iniciativaIndicadorEficaciaMostrar;
	}
	
	public String getIniciativaIndicadorEficienciaNombre() 
	{
		return this.iniciativaIndicadorEficienciaNombre;
	}

	public void setIniciativaIndicadorEficienciaNombre(String iniciativaIndicadorEficienciaNombre) 
	{
		this.iniciativaIndicadorEficienciaNombre = iniciativaIndicadorEficienciaNombre;
	}

	public Boolean getIniciativaIndicadorEficienciaMostrar() 
	{
		return this.iniciativaIndicadorEficienciaMostrar;
	}

	public void setIniciativaIndicadorEficienciaMostrar(Boolean iniciativaIndicadorEficienciaMostrar) 
	{
		this.iniciativaIndicadorEficienciaMostrar = iniciativaIndicadorEficienciaMostrar;
	}

	public Boolean getIniciativaIndicadorAvanceAnteponer() 
	{
		return this.iniciativaIndicadorAvanceAnteponer;
	}

	public void setIniciativaIndicadorAvanceAnteponer(Boolean iniciativaIndicadorAvanceAnteponer) 
	{
		this.iniciativaIndicadorAvanceAnteponer = iniciativaIndicadorAvanceAnteponer;
	}

	public Boolean getPlanObjetivoLogroAnualMostrar() 
	{
		return this.planObjetivoLogroAnualMostrar;
	}

	public void setPlanObjetivoLogroAnualMostrar(Boolean planObjetivoLogroAnualMostrar) 
	{
		this.planObjetivoLogroAnualMostrar = planObjetivoLogroAnualMostrar;
	}

	public Boolean getPlanObjetivoLogroParcialMostrar() 
	{
		return this.planObjetivoLogroParcialMostrar;
	}

	public void setPlanObjetivoLogroParcialMostrar(Boolean planObjetivoLogroParcialMostrar) 
	{
		this.planObjetivoLogroParcialMostrar = planObjetivoLogroParcialMostrar;
	}

	public Boolean getPlanObjetivoAlertaAnualMostrar() 
	{
		return this.planObjetivoAlertaAnualMostrar;
	}

	public void setPlanObjetivoAlertaAnualMostrar(Boolean planObjetivoAlertaAnualMostrar) 
	{
		this.planObjetivoAlertaAnualMostrar = planObjetivoAlertaAnualMostrar;
	}

	public Boolean getPlanObjetivoAlertaParcialMostrar() 
	{
		return this.planObjetivoAlertaParcialMostrar;
	}

	public void setPlanObjetivoAlertaParcialMostrar(Boolean planObjetivoAlertaParcialMostrar) 
	{
		this.planObjetivoAlertaParcialMostrar = planObjetivoAlertaParcialMostrar;
	}
	
	public Boolean getEnviarResponsableFijarMeta() 
	{
		return this.enviarResponsableFijarMeta;
	}

	public void setEnviarResponsableFijarMeta(Boolean enviarResponsableFijarMeta) 
	{
		this.enviarResponsableFijarMeta = enviarResponsableFijarMeta;
	}

	public Boolean getEnviarResponsableLograrMeta() 
	{
		return this.enviarResponsableLograrMeta;
	}

	public void setEnviarResponsableLograrMeta(Boolean enviarResponsableLograrMeta) 
	{
		this.enviarResponsableLograrMeta = enviarResponsableLograrMeta;
	}

	public Boolean getEnviarResponsableSeguimiento() 
	{
		return this.enviarResponsableSeguimiento;
	}

	public void setEnviarResponsableSeguimiento(Boolean enviarResponsableSeguimiento) 
	{
		this.enviarResponsableSeguimiento = enviarResponsableSeguimiento;
	}

	public Boolean getEnviarResponsableCargarMeta() 
	{
		return this.enviarResponsableCargarMeta;
	}

	public void setEnviarResponsableCargarMeta(Boolean enviarResponsableCargarMeta) 
	{
		this.enviarResponsableCargarMeta = enviarResponsableCargarMeta;
	}

	public Boolean getEnviarResponsableCargarEjecutado() 
	{
		return this.enviarResponsableCargarEjecutado;
	}

	public void setEnviarResponsableCargarEjecutado(Boolean enviarResponsableCargarEjecutado) 
	{
		this.enviarResponsableCargarEjecutado = enviarResponsableCargarEjecutado;
	}

	public Byte getTipoCorreoPorDefecto() 
	{
		return this.tipoCorreoPorDefecto;
	}

	public void setTipoCorreoPorDefecto(Byte tipoCorreoPorDefecto) 
	{
		this.tipoCorreoPorDefecto = tipoCorreoPorDefecto;
	}
	
	public Byte getCorreoLocal() 
	{
		return new Byte(TipoCorreo.TIPO_LOCAL);
	}

	public Byte getCorreoGMail() 
	{
		return new Byte(TipoCorreo.TIPO_GMAIL);
	}
		
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
		
	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Boolean getEnviarResponsableFijarMetaInv() {
		return enviarResponsableFijarMetaInv;
	}

	public void setEnviarResponsableFijarMetaInv(Boolean enviarResponsableFijarMetaInv) {
		this.enviarResponsableFijarMetaInv = enviarResponsableFijarMetaInv;
	}

	public Boolean getEnviarResponsableLograrMetaInv() {
		return enviarResponsableLograrMetaInv;
	}

	public void setEnviarResponsableLograrMetaInv(Boolean enviarResponsableLograrMetaInv) {
		this.enviarResponsableLograrMetaInv = enviarResponsableLograrMetaInv;
	}

	public Boolean getEnviarResponsableSeguimientoInv() {
		return enviarResponsableSeguimientoInv;
	}

	public void setEnviarResponsableSeguimientoInv(Boolean enviarResponsableSeguimientoInv) {
		this.enviarResponsableSeguimientoInv = enviarResponsableSeguimientoInv;
	}

	public Boolean getEnviarResponsableCargarMetaInv() {
		return enviarResponsableCargarMetaInv;
	}

	public void setEnviarResponsableCargarMetaInv(Boolean enviarResponsableCargarMetaInv) {
		this.enviarResponsableCargarMetaInv = enviarResponsableCargarMetaInv;
	}

	public Boolean getEnviarResponsableCargarEjecutadoInv() {
		return enviarResponsableCargarEjecutadoInv;
	}

	public void setEnviarResponsableCargarEjecutadoInv(Boolean enviarResponsableCargarEjecutadoInv) {
		this.enviarResponsableCargarEjecutadoInv = enviarResponsableCargarEjecutadoInv;
	}
	
	public Boolean getEnviarResponsableNegativo() {
		return enviarResponsableNegativo;
	}

	public void setEnviarResponsableNegativo(Boolean enviarResponsableNegativo) {
		this.enviarResponsableNegativo = enviarResponsableNegativo;
	}

	public Boolean getEnviarResponsableNegativoInv() {
		return enviarResponsableNegativoInv;
	}

	public void setEnviarResponsableNegativoInv(Boolean enviarResponsableNegativoInv) {
		this.enviarResponsableNegativoInv = enviarResponsableNegativoInv;
	}
	
	public String getDia1() {
		return dia1;
	}

	public void setDia1(String dia1) {
		this.dia1 = dia1;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTexto1() {
		return texto1;
	}

	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}

	public String getHora1() {
		return hora1;
	}

	public void setHora1(String hora1) {
		this.hora1 = hora1;
	}

	public String getCorreo1() {
		return correo1;
	}

	public void setCorreo1(String correo1) {
		this.correo1 = correo1;
	}

	public Boolean getEnviarResponsableFijarMetaInv1() {
		return enviarResponsableFijarMetaInv1;
	}

	public void setEnviarResponsableFijarMetaInv1(Boolean enviarResponsableFijarMetaInv1) {
		this.enviarResponsableFijarMetaInv1 = enviarResponsableFijarMetaInv1;
	}

	public Boolean getEnviarResponsableLograrMetaInv1() {
		return enviarResponsableLograrMetaInv1;
	}

	public void setEnviarResponsableLograrMetaInv1(Boolean enviarResponsableLograrMetaInv1) {
		this.enviarResponsableLograrMetaInv1 = enviarResponsableLograrMetaInv1;
	}

	public Boolean getEnviarResponsableSeguimientoInv1() {
		return enviarResponsableSeguimientoInv1;
	}

	public void setEnviarResponsableSeguimientoInv1(Boolean enviarResponsableSeguimientoInv1) {
		this.enviarResponsableSeguimientoInv1 = enviarResponsableSeguimientoInv1;
	}

	public Boolean getEnviarResponsableCargarMetaInv1() {
		return enviarResponsableCargarMetaInv1;
	}

	public void setEnviarResponsableCargarMetaInv1(Boolean enviarResponsableCargarMetaInv1) {
		this.enviarResponsableCargarMetaInv1 = enviarResponsableCargarMetaInv1;
	}

	public Boolean getEnviarResponsableCargarEjecutadoInv1() {
		return enviarResponsableCargarEjecutadoInv1;
	}

	public void setEnviarResponsableCargarEjecutadoInv1(Boolean enviarResponsableCargarEjecutadoInv1) {
		this.enviarResponsableCargarEjecutadoInv1 = enviarResponsableCargarEjecutadoInv1;
	}

	public Boolean getEnviarResponsableNegativoInv1() {
		return enviarResponsableNegativoInv1;
	}

	public void setEnviarResponsableNegativoInv1(Boolean enviarResponsableNegativoInv1) {
		this.enviarResponsableNegativoInv1 = enviarResponsableNegativoInv1;
	}

	public void clear() 
	{
		this.setRespuesta(null);
		this.setStatus(VgcReturnCode.FORM_READY);
		this.iniciativaNombre = null;
		this.iniciativaIndicadorAvanceNombre = null;
		this.iniciativaIndicadorAvanceMostrar = false;
		this.iniciativaIndicadorPresupuestoNombre = null;
		this.iniciativaIndicadorPresupuestoMostrar = false;
		this.iniciativaIndicadorEficaciaNombre = null;
		this.iniciativaIndicadorEficaciaMostrar = false;
		this.iniciativaIndicadorEficienciaNombre = null;
		this.iniciativaIndicadorEficienciaMostrar = false;
		this.iniciativaIndicadorAvanceAnteponer = false;
		
		this.planObjetivoLogroAnualMostrar = true;
		this.planObjetivoLogroParcialMostrar = true;
		this.planObjetivoAlertaAnualMostrar = true;
		this.planObjetivoAlertaParcialMostrar = true;
		
		this.enviarResponsableCargarEjecutado = true;
		this.enviarResponsableCargarMeta = true;
		this.enviarResponsableFijarMeta = true;
		this.enviarResponsableLograrMeta = true;
		this.enviarResponsableSeguimiento = true;
		this.enviarResponsableNegativo = true;
		
		this.enviarResponsableCargarEjecutadoInv = true;
		this.enviarResponsableCargarMetaInv = true;
		this.enviarResponsableFijarMetaInv = true;
		this.enviarResponsableLograrMetaInv = true;
		this.enviarResponsableSeguimientoInv = true;
		this.enviarResponsableNegativoInv = true;
		
		this.enviarResponsableCargarEjecutadoInv1 = true;
		this.enviarResponsableCargarMetaInv1 = true;
		this.enviarResponsableFijarMetaInv1 = true;
		this.enviarResponsableLograrMetaInv1 = true;
		this.enviarResponsableSeguimientoInv1 = true;
		
		this.indicadorNivel = 2;
		
		this.tipoCorreoPorDefecto = TipoCorreo.TIPO_LOCAL;
	
		
		
	}
	
	public static class TipoCorreo
	{
		private static final byte TIPO_LOCAL = 1;
		private static final byte TIPO_GMAIL = 2;
		
		public static Byte getTipoCorreo(Byte tipoCorreo)
		{
			if (tipoCorreo == TIPO_LOCAL)
				return new Byte(TIPO_LOCAL);
			else if (tipoCorreo == TIPO_GMAIL)
				return new Byte(TIPO_GMAIL);
			else
				return null;
		}
	}
}
