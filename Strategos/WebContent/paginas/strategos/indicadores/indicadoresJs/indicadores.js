/*
 * tables.js 1.0.0
 * 20/12/2005
 *
 * Funciones de Javascript para el manejo de tablas
 *
 *
 */
function moveRowOneDown(indexRow, oTable, fieldRowSelected) 
{
	var rows = oTable.rows;
   	var oRowSelected = rows[indexRow];
   	var rowSelected = fieldRowSelected.value/1;
   	var oRowDown;

   	// forzar casting a numero
   	indexRow = indexRow/1;

   	// mover solo si no es la ultima fila de la tabla
   	if (indexRow < rows.length-1) 
   	{
		oRowDown = rows[indexRow + 1];
		oRowSelected.id=indexRow + 1;
		oRowDown.id=indexRow;
		oTable.tBodies[0].insertBefore(oRowDown,oRowSelected);
		
		// actualizar la fila seleccionada	
		if (indexRow==rowSelected)
		{
			rowSelected++; 
			fieldRowSelected.value = rowSelected;
		}
   	}
}

function moveRowOneUp(indexRow, oTable, fieldRowSelected) 
{
	var rows = oTable.rows; 
	var oRowSelected = rows[indexRow];
	var rowSelected = fieldRowSelected.value/1;
   	
	var oRowUp;
   
	// forzar casting a numero
	indexRow = indexRow/1;
   
    // mover solo si no es la primera fila de la tabla
	if (indexRow > 0) 
	{
		oRowUp = rows[indexRow - 1];
		oRowSelected.id=indexRow - 1;
		oRowUp.id=indexRow;
		oTable.tBodies[0].insertBefore(oRowSelected,oRowUp);
	
		if (indexRow==rowSelected)
		{
			rowSelected=rowSelected-1; 
			fieldRowSelected.value = rowSelected;		
		}
	}
}

function selectRow(indexRow, oTable, fieldRowSelected) 
{
	var rowSelected = fieldRowSelected.value / 1;
	var numColumnas = 0;
	var i = 0;

	indexRow = indexRow / 1;
	numColumnas = oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length;

	oTable.getElementsByTagName("tr").item(rowSelected).style.backgroundColor = "";
	for (i = 0; i < numColumnas; i++) 
	{
		oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = "";
		oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = "";
	}

	oTable.getElementsByTagName("tr").item(indexRow).style.backgroundColor = "blue";
	for (i = 0; i < numColumnas; i++) 
	{
		oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = "blue";
		oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color="white";
	}
	fieldRowSelected.value = indexRow;		
}

function selectRowColors(indexRow, oTable, fieldRowSelected, 
						colorSelected, bgColorSelected,
						colorNoSelected, bgColorNoSelected) 
{

	var rowSelected = fieldRowSelected.value / 1;
	var numColumnas = 0;
	var i = 0;

	indexRow = indexRow / 1;
	numColumnas = oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length;

	for (i = 0; i < numColumnas; i++) 
	{
		oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = colorNoSelected;
		oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = bgColorNoSelected;
	}

	for (i = 0; i < numColumnas; i++) 
	{
		oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color = colorSelected;
		oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = bgColorSelected;
	}
	fieldRowSelected.value = indexRow;
}

function deleteRow(objTable, indexRow) 
{
	objTable.deleteRow(indexRow);
}

function deleteRowColors(objTable, indexRow, objIndexSelected,
						colorSelected, bgColorSelected,
						colorNoSelected, bgColorNoSelected) 
{
	var numFilas = objTable.getElementsByTagName("tr").length;
	objTable.deleteRow(indexRow);
	if (numFilas > 1) {
			if (objIndexSelected.value > 0) {
				objIndexSelected.value = objIndexSelected.value - 1;
				selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
								colorSelected, bgColorSelected,
								colorNoSelected, bgColorNoSelected);
			} else {
				selectRowColors(objIndexSelected.value, objTable, objIndexSelected,
								colorSelected, bgColorSelected,
								colorNoSelected, bgColorNoSelected);
			}
	}
}


/** Eventos JavaScript para la naturaleza Cualitativa (Nominal u Ordinal)*/

var categorias = "";
var sepCat = '<bean:write name="editarIndicadorForm" property="separadorCategorias" />';
var sepOrd = '<bean:write name="editarIndicadorForm" property="separadorOrden" />';

function inicializarEscalaCualitativa() {

	categorias = document.editarIndicadorForm.escalaCualitativa.value;

	var tabla = document.all ? document.all["listaCategorias"] : document.getElementById("listaCategorias");

	valores = categorias.split(sepCat);
	for (var i = 0; i < valores.length; i++) {
		if (valores[i].length > 0) {
			campos = valores[i].split(sepOrd);
			var numFilas = tabla.getElementsByTagName("tr").length;
			var tbody = document.getElementById("listaCategorias").getElementsByTagName("TBODY")[0];
			var row = document.createElement("TR");
			var td1 = document.createElement("TD");
			for (var j = 0; j < document.editarIndicadorForm.dummyCategoriaId.length; j++) {
				if (document.editarIndicadorForm.dummyCategoriaId[j].value == campos[0]) {
					var nombre = document.editarIndicadorForm.dummyCategoriaId[j].text;
					td1.appendChild(document.createTextNode(nombre));
					break;
				}
			}
			row.appendChild(td1);
			row.onclick = function() {
				selectRowColors(this.rowIndex, 
							document.getElementById('listaCategorias'), 
							document.getElementById('categoriaSeleccionada'),
							"white", "blue", "black", "white");
			};
			tbody.appendChild(row);
			if (numFilas == 0) {
				selectRowColors(0,
							document.getElementById('listaCategorias'), 
							document.getElementById('categoriaSeleccionada'),
							"white", "blue", "black", "white");
			}
		}
	}

}

function insertarCategoria() {
	if (document.editarIndicadorForm.dummyCategoriaId.selectedIndex != 0) {
		var valorBuscado = sepCat
				+ document.editarIndicadorForm.dummyCategoriaId[document.editarIndicadorForm.dummyCategoriaId.selectedIndex].value
				+ sepOrd;
		if (categorias.indexOf(valorBuscado) == -1) {
			var tabla = document.all ? document.all["listaCategorias"] : document.getElementById("listaCategorias");
			var numFilas = tabla.getElementsByTagName("tr").length;
			categorias = categorias + valorBuscado + (tabla.rows.length + 1) + sepCat;
			var tbody = document.getElementById("listaCategorias").getElementsByTagName("TBODY")[0];
			var row = document.createElement("TR");
			var td1 = document.createElement("TD");
			var nombre = document.editarIndicadorForm.dummyCategoriaId[document.editarIndicadorForm.dummyCategoriaId.selectedIndex].text;
			td1.appendChild(document.createTextNode(nombre));
			row.appendChild(td1);
			row.onclick = function() {
				selectRowColors(this.rowIndex, 
							document.getElementById('listaCategorias'), 
							document.getElementById('categoriaSeleccionada'),
							"white", "blue", "black", "white");
			};
			tbody.appendChild(row);
			if (numFilas == 0) {
				selectRowColors(0,
							document.getElementById('listaCategorias'), 
							document.getElementById('categoriaSeleccionada'),
							"white", "blue", "black", "white");
			}
		}
	}
}

function removerCategoria() {
	var tabla = document.getElementById('listaCategorias');
	numFilas = tabla.getElementsByTagName("tr").length;

	if (numFilas == 0) {
		alert('<bean:message key="editarindicador.nocategorias" />');
	} else {
		// Se modifica el string que contiene la escala seleccionada
		var posBuscada = parseInt(document.editarIndicadorForm.categoriaSeleccionada.value);
		posBuscada = posBuscada + 1;
		var strBuscada = sepOrd + posBuscada + sepCat;
		var indexStrBuscada = categorias.indexOf(strBuscada);
		str1 = categorias.substring(0, indexStrBuscada);
		index1 = str1.lastIndexOf(sepCat);
		if (index1 > -1) {
			str1 = str1.substring(0, index1);
		}
		str2 = categorias.substring(indexStrBuscada + strBuscada.length, categorias.length);
		categorias = str1 + str2;

		for (var i = posBuscada + 1; i <= numFilas; i++) {
			categorias = categorias.replace(sepOrd + i + sepCat, sepOrd + (i - 1) + sepCat);
		}
		deleteRowColors(tabla,
						document.editarIndicadorForm.categoriaSeleccionada.value,
						document.getElementById('categoriaSeleccionada'),
						"white", "blue", "black", "white");
	}
}

function moverCategoriaArriba() {
	var tabla = document.getElementById('listaCategorias');
	numFilas = tabla.getElementsByTagName("tr").length;

	if (numFilas == 0) {
		alert('<bean:message key="editarindicador.nocategorias" />');
	} else {
		// Se modifica el string que contiene la escala seleccionada
		var posActual = parseInt(document.editarIndicadorForm.categoriaSeleccionada.value) + 1;
		if (posActual > 1) {
			categorias = categorias.replace(sepOrd + posActual + sepCat, "remp");
			categorias = categorias.replace(sepOrd + (posActual - 1) + sepCat, sepOrd + posActual + sepCat);
			categorias = categorias.replace("remp", sepOrd + (posActual - 1) + sepCat);
			moveRowOneUp(document.editarIndicadorForm.categoriaSeleccionada.value, tabla,
							document.getElementById('categoriaSeleccionada'));
		}
	}
}

function moverCategoriaAbajo() {
	var tabla = document.getElementById('listaCategorias');
	numFilas = tabla.getElementsByTagName("tr").length;

	if (numFilas == 0) {
		alert('<bean:message key="editarindicador.nocategorias" />');
	} else {
		// Se modifica el string que contiene la escala seleccionada
		var posActual = parseInt(document.editarIndicadorForm.categoriaSeleccionada.value) + 1;
		if (posActual < numFilas) {
			categorias = categorias.replace(sepOrd + posActual + sepCat, "remp");
			categorias = categorias.replace(sepOrd + (posActual + 1) + sepCat, sepOrd + posActual + sepCat);
			categorias = categorias.replace("remp", sepOrd + (posActual + 1) + sepCat);
			moveRowOneDown(document.editarIndicadorForm.categoriaSeleccionada.value, tabla,
							document.getElementById('categoriaSeleccionada'));
		}
	}
}

function esEnteroCeroCien(obtTexto) 
{
	var valor = obtTexto.value;
    var valido = valor.indexOf('.') == -1;

	if (valido) 
	   valido = valor.indexOf(',') == -1;
	if (valido) 
	   valido = !isNaN(valor);
	if (valido) 
	   valido = (valor >= 0) && (valor <= 100);
	
    return valido;
}


function verificarNumero(obtTexto, porcentaje) 
{
   var valido = false;

   if (porcentaje) 
      valido = esEnteroCeroCien(obtTexto);
   else 
      valido = !isNaN(obtTexto.value);
    
   if (!valido) 
      obtTexto.value = obtTexto.value.substring(0, obtTexto.value.length-1);   
}
