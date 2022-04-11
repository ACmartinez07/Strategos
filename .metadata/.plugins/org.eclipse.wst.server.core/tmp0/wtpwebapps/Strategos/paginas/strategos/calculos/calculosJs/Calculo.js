function Calculo()
{
	// Propiedades
	
	// REgistrar Metodos
	this.ConfigurarCalculo = __ConfigurarCalculo;
	
    /// <summary>
    /// Configura el Calculo de los Indicadores
    /// </summary>
	/// <param name="url">parametros que necesita la forma.</param>
	function __ConfigurarCalculo(url, titulo)
	{
		abrirVentanaModal(url, titulo, '650', '440');
	}
}