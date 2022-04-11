function Grafico()
{
	// Propiedades
	this.url = null;
	
	// Registrar Metodos
	this.ShowForm = __ShowForm;
	
    /// <summary>
    /// Llama la la Forma del Grafico
    /// </summary>
	/// <param name="defaultLoader">Set default loader.</param>
	/// <param name="objetoId">objeto Id.</param>
	/// <param name="source">source.</param>
	/// <param name="claseId">clase Id.</param>
	/// <param name="graficoId">grafico Id.</param>
	/// <param name="planId">plan Id.</param>
	/// <param name="vistaId">vista Id.</param>
	/// <param name="paginaId">pagina Id.</param>
	/// <param name="xml">parametros adicionales en xml.</param>
	function __ShowForm(defaultLoader, objetoId, source, claseId, graficoId, planId, vistaId, paginaId, xml)
	{
		if (typeof(objetoId) != "undefined")
			objetoId = '&objetoId=' + objetoId;
		else
			objetoId = '';
		if (typeof(defaultLoader) == "undefined")
			defaultLoader = false;
		if (typeof(source) == "undefined")
			source = "Indicador"; 
		if (typeof(claseId) != "undefined")
			claseId = '&claseId=' + claseId;
		else
			claseId = '';
		if (typeof(graficoId) != "undefined")
			graficoId = '&graficoId=' + graficoId;
		else
			graficoId = '';
		if (typeof(planId) != "undefined")
			planId = '&planId=' + planId;
		else
			planId = '';
		if (typeof(vistaId) != "undefined")
			vistaId = '&vistaId=' + vistaId;
		else
			vistaId = '';
		if (typeof(paginaId) != "undefined")
			paginaId = '&paginaId=' + paginaId;
		else
			paginaId = '';
		if (typeof(xml) == "undefined")
			xml = '';
		
		window.location.href = this.url + "?defaultLoader=" + defaultLoader + objetoId + "&source=" + source + claseId + graficoId + planId + vistaId + paginaId + xml;
	}
}