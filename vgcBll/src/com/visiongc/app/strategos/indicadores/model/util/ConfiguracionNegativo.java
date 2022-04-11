package com.visiongc.app.strategos.indicadores.model.util;



public class ConfiguracionNegativo
{
	
	  	private Boolean enviarResponsableFijarMeta;
	  
	  	private Boolean enviarResponsableLograrMeta;
	  
	  	private Boolean enviarResponsableSeguimiento;
	  
	  	private Boolean enviarResponsableCargarMeta;
	  
	  	private Boolean enviarResponsableCargarEjecutado;	
	  	
	  	private Boolean enviarResponsableNegativo;
	  
	  	private String titulo;
	
	  	private String texto;
	  
	  	public ConfiguracionNegativo() {}

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
		
		public Boolean getEnviarResponsableNegativo() {
			return enviarResponsableNegativo;
		}

		public void setEnviarResponsableNegativo(Boolean enviarResponsableNegativo) {
			this.enviarResponsableNegativo = enviarResponsableNegativo;
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
