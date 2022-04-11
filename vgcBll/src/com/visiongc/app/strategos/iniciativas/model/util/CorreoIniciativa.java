package com.visiongc.app.strategos.iniciativas.model.util;



public class CorreoIniciativa
{
	
	  	private Boolean enviarResponsableFijarMeta;
	  
	  	private Boolean enviarResponsableLograrMeta;
	  
	  	private Boolean enviarResponsableSeguimiento;
	  
	  	private Boolean enviarResponsableCargarMeta;
	  
	  	private Boolean enviarResponsableCargarEjecutado;	
	  	
	  	private String dia;
	  	
	  	private String hora;
	  	
	  	private String correo;
	  
	  	private String titulo;
	
	  	private String texto;
	  
	  	public CorreoIniciativa() {}

		public Boolean getEnviarResponsableFijarMeta() {
			return enviarResponsableFijarMeta;
		}
	
		public void setEnviarResponsableFijarMeta(Boolean enviarResponsableFijarMeta) {
			this.enviarResponsableFijarMeta = enviarResponsableFijarMeta;
		}
	
		public Boolean getEnviarResponsableLograrMeta() {
			return enviarResponsableLograrMeta;
		}
	
		public void setEnviarResponsableLograrMeta(Boolean enviarResponsableLograrMeta) {
			this.enviarResponsableLograrMeta = enviarResponsableLograrMeta;
		}
	
		public Boolean getEnviarResponsableSeguimiento() {
			return enviarResponsableSeguimiento;
		}
	
		public void setEnviarResponsableSeguimiento(Boolean enviarResponsableSeguimiento) {
			this.enviarResponsableSeguimiento = enviarResponsableSeguimiento;
		}
	
		public Boolean getEnviarResponsableCargarMeta() {
			return enviarResponsableCargarMeta;
		}
	
		public void setEnviarResponsableCargarMeta(Boolean enviarResponsableCargarMeta) {
			this.enviarResponsableCargarMeta = enviarResponsableCargarMeta;
		}
	
		public Boolean getEnviarResponsableCargarEjecutado() {
			return enviarResponsableCargarEjecutado;
		}
	
		public void setEnviarResponsableCargarEjecutado(Boolean enviarResponsableCargarEjecutado) {
			this.enviarResponsableCargarEjecutado = enviarResponsableCargarEjecutado;
		}
		
		public String getDia() {
			return dia;
		}

		public void setDia(String dia) {
			this.dia = dia;
		}

		public String getHora() {
			return hora;
		}

		public void setHora(String hora) {
			this.hora = hora;
		}

		public String getCorreo() {
			return correo;
		}

		public void setCorreo(String correo) {
			this.correo = correo;
		}

		public String getTitulo() {
			return titulo;
		}
	
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
	
		public String getTexto() {
			return texto;
		}
	
		public void setTexto(String texto) {
			this.texto = texto;
		}

	  
}
