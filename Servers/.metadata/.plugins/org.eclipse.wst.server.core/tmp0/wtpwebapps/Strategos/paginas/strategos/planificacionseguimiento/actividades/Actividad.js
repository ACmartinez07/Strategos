function Actividad()
{
	// Propiedades
	
	// Registrar Metodos
	this.ShowList = __ShowList;
	this.url = null;
	
    /// <summary>
    /// Llama la lista de Planificacion y Seguimiento
    /// </summary>
	/// <param name="defaultLoader">Set default loader.</param>
	/// <param name="iniciativaId">iniciativa Id.</param>
	/// <param name="funcion">funcion.</param>
	/// <param name="planId">plan Id.</param>
	function __ShowList(defaultLoader, iniciativaId, funcion, planId)
	{
		if (typeof(iniciativaId) == "undefined")
			return;
		if (typeof(defaultLoader) == "undefined")
			defaultLoader = false;
		if (typeof(funcion) == "undefined")
			funcion = "";
		else
			funcion = "&funcion=" + funcion;
		if (typeof(planId) == "undefined")
			planId = "";
		else
			planId = "&planId=" + planId;
	
		window.location.href = this.url + "?defaultLoader=" + defaultLoader + "&iniciativaId=" + iniciativaId + funcion + planId;
	}
}