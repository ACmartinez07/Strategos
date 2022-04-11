function Duppont()
{
	// Propiedades
	
	// Registrar Metodos
	this.ShowForm = __ShowForm;
	this.url = null;
	
    /// <summary>
    /// Llama la lista de Explicaciones
    /// </summary>
	/// <param name="defaultLoader">Set default loader.</param>
	/// <param name="indicadorId">indicador Id.</param>
	/// <param name="source">source.</param>
	/// <param name="planId">plan Id.</param>
	function __ShowForm(defaultLoader, indicadorId, source, planId)
	{
		if (typeof(defaultLoader) == "undefined")
			defaultLoader = false;
		if (typeof(indicadorId) == "undefined")
			return; 
		if (typeof(source) != "undefined")
			source = '&source=' + source;
		else
			source = '';
		if (typeof(planId) != "undefined")
			planId = '&planId=' + planId;
		else
			planId = '';
		
		window.location.href = this.url + "?defaultLoader=" + defaultLoader + "&indicadorId=" + indicadorId + source + planId;
	}
}