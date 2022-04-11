/**
 * Objeto de Impresion
 */
function Imprimir()
{
	// Propiedades
	this.url = null;
	
	// Registrar Metodos
	this.Print = __Print;
	
    /// <summary>
    /// Imprimir Objetos
    /// </summary>
	/// <param name="objeto">objeto a Imprimir.</param>
	function __Print(objeto)
	{
		if (typeof(objeto) != "undefined")
		{
			var formImp = window.open('', 'PRINT', 'height=400,width=600');
			//formImp.document.write('<html><head><title>' + document.title  + '</title>');
			formImp.document.write('<html><head><title>Test</title>');
			formImp.document.write('</head><body >');
			//formImp.document.write('<h1>' + document.title  + '</h1>');
			formImp.document.write('<h1>Test</h1>');
			formImp.document.write(objeto.innerHTML);
			formImp.document.write('</body></html>');

			formImp.document.close(); // necessary for IE >= 10
			formImp.focus(); // necessary for IE >= 10*/

			formImp.print();
			formImp.close();

	        return true;			
		}
	}	
}	