function Explicacion()
{
	// Propiedades
	
	// Registrar Metodos
	this.ShowList = __ShowList;
	this.url = null;
	
    /// <summary>
    /// Llama la lista de Explicaciones
    /// </summary>
	/// <param name="defaultLoader">Set default loader.</param>
	/// <param name="objetoId">objeto Id.</param>
	/// <param name="objetoKey">objeto Key.</param>
	/// <param name="tipo">Tipo.</param>
	function __ShowList(defaultLoader, objetoId, objetoKey, tipo)
	{
		if (typeof(objetoId) == "undefined")
			return;
		if (typeof(defaultLoader) == "undefined")
			defaultLoader = false;
		if (typeof(objetoKey) == "undefined")
			objetoKey = "Indicador"; 
		if (typeof(tipo) == "undefined")
			tipo = 0;
		
		window.location.href = this.url + "?defaultLoader=" + defaultLoader + "&objetoId=" + objetoId + "&objetoKey=" + objetoKey + "&tipo=" + tipo;
	}
}