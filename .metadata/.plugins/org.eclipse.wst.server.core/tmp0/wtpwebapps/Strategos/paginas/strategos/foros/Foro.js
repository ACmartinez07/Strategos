function Foro()
{
	// Propiedades
	
	// Registrar Metodos
	this.ShowList = __ShowList;
	this.url = null;
	
    /// <summary>
    /// Llama la lista de Foros
    /// </summary>
	/// <param name="defaultLoader">Set default loader.</param>
	/// <param name="objetoId">objeto Id.</param>
	/// <param name="objetoKey">objeto Key.</param>
	function __ShowList(defaultLoader, objetoId, objetoKey)
	{
		if (typeof(objetoId) == "undefined")
			return;
		if (typeof(defaultLoader) == "undefined")
			defaultLoader = false;
		if (typeof(objetoKey) == "undefined")
			objetoKey = "Indicador"; 
		
		window.location.href = this.url + "?defaultLoader=" + defaultLoader + "&objetoId=" + objetoId + "&objetoKey=" + objetoKey;
	}
}