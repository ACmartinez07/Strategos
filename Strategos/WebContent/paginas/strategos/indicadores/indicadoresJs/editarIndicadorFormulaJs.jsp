<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

/** Funciones JavaScript para la naturaleza formula */
function setListaInsumosFormula(separadorIndicadores, separadorRuta) 
{
	if (typeof separadorIndicadores == 'undefined')
		separadorIndicadores = '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>';
	if (typeof separadorRuta == 'undefined')
		separadorRuta = '<bean:write name="editarIndicadorForm" property="separadorRuta" ignore="true"/>';
	
	var tablaListaInsumos = document.getElementById('tablaListaInsumosFormula');

	// Se borra la lista de insumos
	while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
		tablaListaInsumos.deleteRow(0);

	// Se recorre la lista de insumos
	var insumos = document.editarIndicadorForm.insumosFormula.value.split(separadorIndicadores);
	for (i = 0; i < insumos.length; i++) {
		if (insumos[i].length > 0) {
			// correlativo
			strTemp = insumos[i];
			indice = strTemp.indexOf("][") + 1;
			correlativo = strTemp.substring(0, indice);
			// indicadorId
			strTemp = strTemp.substring(indice, strTemp.length);
			indice = strTemp.indexOf("][");
			// serieId
			strTemp = strTemp.substring(indice + 1, strTemp.length);
			indice = strTemp.indexOf("][");
			// nombreIndicador
			strTemp = strTemp.substring(indice + 1, strTemp.length);
			indice = strTemp.indexOf("][");
			nombreIndicador = '[' + strTemp.substring('indicadorNombre:'.length + 1, indice + 1);
			// nombreSerie
			strTemp = strTemp.substring(indice + 1, strTemp.length);
			indice = strTemp.indexOf("][");
			serie = '[' + strTemp.substring('serieNombre:'.length + 1, indice + 1);
			//serie = '[' + strTemp.substring(indice + 'serieNombre:'.length + 2, strTemp.length);
			var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
			var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
			var row = document.createElement("TR");
			var td1 = document.createElement("TD");
			var td2 = document.createElement("TD");
			var td3 = document.createElement("TD");
			td1.appendChild(document.createTextNode(correlativo));
			td2.appendChild(document.createTextNode(serie));
			td2.style.color = "blue";
			td3.appendChild(document.createTextNode(nombreIndicador));
			row.appendChild(td1);
			row.appendChild(td2);
			row.appendChild(td3);
			row.onclick = function() {
				var selAnterior = document.getElementById('insumoSeleccionado').value;
				selectRowColors(this.rowIndex, 
							document.getElementById('tablaListaInsumosFormula'), 
							document.getElementById('insumoSeleccionado'),
							"white", "blue", "black", "white");
				paintListaInsumosFormulaColumnaSerie(selAnterior, document.getElementById('tablaListaInsumosFormula'), "blue");
				mostrarRutaCompletaInsumoFormula(separadorIndicadores, separadorRuta);
			}
			tbody.appendChild(row);
			if (numFilas == 0) {
				selectRowColors(0,
							document.getElementById('tablaListaInsumosFormula'), 
							document.getElementById('insumoSeleccionado'),
							"white", "blue", "black", "white");
			}
		}
	}
	mostrarRutaCompletaInsumoFormula(separadorIndicadores, separadorRuta);
}

function paintListaInsumosFormulaColumnaSerie(indexRow, oTable, color) 
{
	indexRow = indexRow / 1;
	oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(1).style.color = color;			
}

function seleccionarIndicadoresInsumoFormula(forma, funcionRetorno, url) 
{
	if (forma == undefined)
		forma = 'editarIndicadorForm';
	if (funcionRetorno == undefined)
		funcionRetorno = 'agregarIndicadoresInsumoFormula()';
	if (url == undefined)
		url = '&mostrarSeriesTiempo=true&permitirCambiarOrganizacion=true&permitirCambiarClase=true&seleccionMultiple=true&frecuencia=' + document.editarIndicadorForm.frecuencia.value + '&excluirIds=' + document.editarIndicadorForm.indicadorId.value + '&permitirIniciativas=true';

	abrirSelectorIndicadores(forma, 'indicadorInsumoSeleccionadoNombres', 'indicadorInsumoSeleccionadoIds', 'indicadorInsumoSeleccionadoRutasCompletas', funcionRetorno, null, null, null, null, null, url);
}

// Formato de string de insumos:
// [correlativo:##][indicadorId:###][serieId:###][indicadorNombre:###][serieNombre:###][rutaCompleta:###]; ....
function agregarIndicadoresInsumoFormula() 
{
	seleccionadoIds = document.editarIndicadorForm.indicadorInsumoSeleccionadoIds.value.split('<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>');
	seleccionadoNombres = document.editarIndicadorForm.indicadorInsumoSeleccionadoNombres.value.split('<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>');
	seleccionadoRutasCompletas = document.editarIndicadorForm.indicadorInsumoSeleccionadoRutasCompletas.value.split('<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>');

	var listaInsumos = document.editarIndicadorForm.insumosFormula.value;
	var correlativo = 0;

	if (listaInsumos != null) {
		if (listaInsumos == '') {
			// no hay insumos en la lista
			correlativo = 1;
		} else {
			strBuscada = ']' + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>' + '[';
			indice1 = listaInsumos.lastIndexOf(strBuscada);
			if (indice1 > -1) {
				// hay dos o más insumos en la lista
				indice1 = indice1 + strBuscada.length;
				indice2 = listaInsumos.substring(indice1, listaInsumos.length).indexOf(']');
				indice2 = indice1 + indice2;
				correlativo = parseInt(listaInsumos.substring(indice1 , indice2)) + 1;
			} else {
				// hay un solo insumo en la lista
				indice = listaInsumos.indexOf(']');
				correlativo = parseInt(listaInsumos.substring(1, indice)) + 1;
			}
		}
	} else {
		listaInsumos = '';
		correlativo = 1;
	}

	for (i = 0; i < seleccionadoIds.length; i++) {
		partesId = seleccionadoIds[i].split('<bean:write name="editarIndicadorForm" property="separadorSeries" ignore="true"/>');
		nombresIndicador = seleccionadoNombres[i].split('<bean:write name="editarIndicadorForm" property="separadorSeries" ignore="true"/>');

		insumoBuscado = '[indicadorId:' + partesId[0] + '][serieId:' + partesId[1] + ']';

		if (listaInsumos.indexOf(insumoBuscado) < 0) {
			var separadorIndicadores = '';
			if (listaInsumos != '') {
				separadorIndicadores = '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>';
			}
			if (seleccionadoRutasCompletas[i] != '<bean:write name="editarIndicadorForm" property="codigoIndicadorEliminado" ignore="true"/>') {
				listaInsumos = listaInsumos 
							+ separadorIndicadores
							+ '[' + correlativo + ']' 
							+ insumoBuscado 
							+ '[indicadorNombre:' + nombresIndicador[0]  + ']'
							+ '[serieNombre:' + nombresIndicador[1] + ']'
							+ '[rutaCompleta:' + seleccionadoRutasCompletas[i] + ']';
				correlativo++;
			}
		}
	}
	document.editarIndicadorForm.insumosFormula.value = listaInsumos;
	setListaInsumosFormula();
}

function insertarInsumoEnFormula() 
{
	var tabla = document.getElementById('tablaListaInsumosFormula');
	numeroFilas = tabla.getElementsByTagName("tr").length;
	var insumosFormula = document.editarIndicadorForm.insumosFormula.value;

	if (numeroFilas == 0) {
		alert('<vgcutil:message key="jsp.editarindicador.noinsumosformula" />');
	} else {
		var posicionBuscada = parseInt(document.editarIndicadorForm.insumoSeleccionado.value);
		var valorInsertableFormula = '';
		if (posicionBuscada == 0) {
			var index = insumosFormula.indexOf(']');
			valorInsertableFormula = insumosFormula.substring(0, index + 1);
		} else if ((posicionBuscada + 1) == numFilas) {
			var strBuscada = ']' + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>' + '[';
			var index = insumosFormula.lastIndexOf(strBuscada) + 1 + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>'.length;
			index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
			valorInsertableFormula = insumosFormula.substring(index, index2 + 1);
		} else {
			var strBuscada = ']' + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>' + '[';
			var index = 0;
			for (i = 0; i < posicionBuscada; i++) {
				index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
				if (index != -1) {
					index= index + 1 + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>'.length;
				}
			}
			if (index != -1) {
				index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
				valorInsertableFormula = insumosFormula.substring(index, index2 + 1);
			}
		}
		insertAtCursorPosition(document.editarIndicadorForm.formula, valorInsertableFormula);
	}
}

function removerInsumoFormula() 
{
	var tabla = document.getElementById('tablaListaInsumosFormula');
	numeroFilas = tabla.getElementsByTagName("tr").length;
	var insumosFormula = document.editarIndicadorForm.insumosFormula.value;

	if (numeroFilas == 0) 
		alert('<vgcutil:message key="jsp.editarindicador.noinsumosformula" />');
	else 
	{
		// Se modifica el string que contiene los insumos seleccionados
		var posicionBuscada = parseInt(document.editarIndicadorForm.insumoSeleccionado.value);
		valorEliminableFormula = '';
		if (posicionBuscada == 0) 
		{
			var index = insumosFormula.indexOf(']');
			valorEliminableFormula = insumosFormula.substring(0, index + 1);
			// Se busca el último valor de cada indicador seleccionado
			index = insumosFormula.indexOf('[rutaCompleta:');
			insumosFormula = insumosFormula.substring(index + 2, insumosFormula.length);
			index = insumosFormula.indexOf(']');
			if (insumosFormula.length > (index + 1)) 
				index++;
			insumosFormula = insumosFormula.substring(index + 1, insumosFormula.length);
			index = insumosFormula.indexOf('[');
			if (index != -1 && index != 0)
				insumosFormula = insumosFormula.substring(index, insumosFormula.length);
		} 
		else if ((posicionBuscada + 1) == numeroFilas) 
		{
			var strBuscada = ']' + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>' + '[';
			var index = insumosFormula.lastIndexOf(strBuscada) + 2;
			index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
			valorEliminableFormula = insumosFormula.substring(index, index2 + 1);
			insumosFormula = insumosFormula.substring(0, index - 1);
		} 
		else 
		{
			var strBuscada = ']' + '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>' + '[';
			var index = 0;
			for (i = 0; i < posicionBuscada; i++) 
			{
				index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
				if (index != -1) 
					index= index + 2;
			}
			str1 = '';
			if (index != -1) 
			{
				str1 = insumosFormula.substring(0, index);
				index2 = index + insumosFormula.substring(index, insumosFormula.length).indexOf(']');
				valorEliminableFormula = insumosFormula.substring(index, index2 + 1);
			}
			index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
			str2 = insumosFormula.substring(index + 2, insumosFormula.length);
			insumosFormula = str1 + str2;
		}
		formula = document.editarIndicadorForm.formula.value;
		index = formula.indexOf(valorEliminableFormula);
		while (index > -1) 
		{
			formula = formula.substring(0, index) + formula.substring(index + valorEliminableFormula.length, formula.length)
			index = formula.indexOf(valorEliminableFormula);
		}
		document.editarIndicadorForm.formula.value = formula;
		document.editarIndicadorForm.insumosFormula.value = insumosFormula;
		deleteRowColors(tabla,
						document.editarIndicadorForm.insumoSeleccionado.value,
						document.getElementById('insumoSeleccionado'),
						"white", "blue", "black", "white");
	}
}

// Modificado por: Kerwin Arias (01/07/2012)
function mostrarRutaCompletaInsumoFormula(separadorIndicadores, separadorRuta) 
{
	if (typeof separadorIndicadores == 'undefined')
		separadorIndicadores = '<bean:write name="editarIndicadorForm" property="separadorIndicadores" ignore="true"/>';
	if (typeof separadorRuta == 'undefined')
		separadorRuta = '<bean:write name="editarIndicadorForm" property="separadorRuta" ignore="true"/>';
	
	var tabla = document.getElementById('tablaListaInsumosFormula');
	numFilas = tabla.getElementsByTagName("tr").length;
	var rutaCompletaInsumo = '';

	if (numFilas > 0) {
		// Si existen indicadores fórmula
		// Se modifica el string que contiene los insumos seleccionados
		var posBuscada = parseInt(document.editarIndicadorForm.insumoSeleccionado.value);
		valorCorrelativoFormula = '';
		var insumosFormula = document.editarIndicadorForm.insumosFormula.value;
		if (posBuscada == 0) {
			var strBuscada = '[rutaCompleta:';
			var index = insumosFormula.indexOf(strBuscada);
			insumosFormula = insumosFormula.substring(index + strBuscada.length, insumosFormula.length);
			var index2 = insumosFormula.indexOf(']' + separadorIndicadores);
			if (index2 < 0) {
				// solo hay 1 indicador insumo
				rutaCompletaInsumo = insumosFormula.substring(0, insumosFormula.length - 1);
			} else {
				rutaCompletaInsumo = insumosFormula.substring(0, index2);
			}
		} else if ((posBuscada + 1) == numFilas) {
			var strBuscada = '[rutaCompleta:';
			var index = insumosFormula.lastIndexOf(strBuscada);
			rutaCompletaInsumo = insumosFormula.substring(index + strBuscada.length, insumosFormula.length - 1);
		} else {
			var strBuscada = ']' + separadorIndicadores + '[';
			var index = 0;
			for (i = 0; i < posBuscada; i++) {
				index = index + insumosFormula.substring(index, insumosFormula.length).indexOf(strBuscada);
				if (index > -1) {
					index= index + strBuscada.length;
				}
			}
			if (index > -1) {
				insumosFormula = insumosFormula.substring(index, insumosFormula.length);
				strBuscada = '[rutaCompleta:'
				index = insumosFormula.indexOf(strBuscada) + strBuscada.length;
				strBuscada = ']' + separadorIndicadores + '[';
				index2 = insumosFormula.indexOf(strBuscada);
				rutaCompletaInsumo = insumosFormula.substring(index, index2);
			}
		}
		setTablaRutaCompletaInsumoFormula(rutaCompletaInsumo, separadorRuta);
	}

}

// Creado por: Kerwin Arias (01/07/2012)
function setTablaRutaCompletaInsumoFormula(rutaCompletaInsumo, separadorRuta) 
{
	if (typeof separadorRuta == 'undefined')
		separadorRuta = '<bean:write name="editarIndicadorForm" property="separadorRuta" ignore="true"/>';

	var tablaRutaCompletaInsumo = document.getElementById('tablaRutaCompletaInsumoFormula');

	// Se borra la lista de insumos
	while (tablaRutaCompletaInsumo.getElementsByTagName("tr").length > 0) {
		tablaRutaCompletaInsumo.deleteRow(0);
	}

	if (rutaCompletaInsumo == '') {
		return;
	}

	// Se recorre la lista de insumos
	var partesRuta = rutaCompletaInsumo.split(separadorRuta);
	nroPartes = partesRuta.length;
	for (i = 0; i < partesRuta.length; i++) {
		var numFilas = tablaRutaCompletaInsumo.getElementsByTagName("tr").length;
		var tbody = tablaRutaCompletaInsumo.getElementsByTagName("TBODY")[0];
		var row = document.createElement("TR");
		for (j = 0; j < i; j++) {
			var tdIdent = document.createElement("TD");
			tdIdent.appendChild(document.createTextNode('-'));
			tdIdent.width = '5px';
			row.appendChild(tdIdent);
		}
		var tdValor = document.createElement("TD");
		tdValor.appendChild(document.createTextNode(partesRuta[i]));
		if (nroPartes > 1) {
			tdValor.colSpan=nroPartes;
		}
		row.appendChild(tdValor);
		tbody.appendChild(row);
		nroPartes--;
	}

}

function insertAtCursorPosition(myField, myValue) 
{
	//IE support
	if (document.selection) {
		insertStringExplorer(myValue, myField);
		//myField.focus();
		//sel = document.selection.createRange();
		//sel.text = myValue;
		//sel.moveStart(?character?, -myValue.length);
		//sel.select();
	} //MOZILLA/NETSCAPE support
	else if (myField.selectionStart || myField.selectionStart == '0') {
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		myField.value = myField.value.substring(0, startPos) + myValue
			+ myField.value.substring(endPos, myField.value.length);
		myField.selectionStart = startPos + myValue.length;
		myField.selectionEnd = startPos + myValue.length;
	} else {
		myField.value += myValue;
		myField.selectionStart = startPos + myValue.length;
		myField.selectionEnd = startPos + myValue.length;
	}
}

// global variabe to keep track of where the cursor was
var globalPosicionCursor = 0;

//sets the global variable to keep track of the cursor position
function setPosicionCursor(campoText) {
	globalPosicionCursor = getPosicionCursorFormula(campoText);
}

//This function returns the index of the cursor location in
//the value of the input text element
//It is important to make sure that the sWeirdString variable contains
//a set of characters that will not be encountered normally in your
//text
function getPosicionCursorFormula(textElement) 
{
	//save off the current value to restore it later,
	var sOldText = textElement.value;

	//create a range object and save off it's text
	var objRange = document.selection.createRange();
	var sOldRange = objRange.text;

	//set this string to a small string that will not normally be encountered
	var sWeirdString = '#%~';

	//insert the weirdstring where the cursor is at
	objRange.text = sOldRange + sWeirdString; objRange.moveStart('character', (0 - sOldRange.length - sWeirdString.length));

	//save off the new string with the weirdstring in it
	var sNewText = textElement.value;

	//set the actual text value back to how it was
	objRange.text = sOldRange;

	//look through the new string we saved off and find the location of
	//the weirdstring that was inserted and return that value
	for (i=0; i <= sNewText.length; i++) {
		var sTemp = sNewText.substring(i, i + sWeirdString.length);
		if (sTemp == sWeirdString) {
			var cursorPos = (i - sOldRange.length);
			return cursorPos;
		}
	}
}

//this function inserts the input string into the textarea
//where the cursor was at
function insertStringExplorer(stringToInsert, campoTexto) {
	var firstPart = campoTexto.value.substring(0, globalPosicionCursor);
	var secondPart = campoTexto.value.substring(globalPosicionCursor, campoTexto.value.length);
	campoTexto.value = firstPart + stringToInsert + secondPart;
	globalPosicionCursor = globalPosicionCursor + stringToInsert.length;
}

/*
function validarTextoFormula() 
{
	if (document.editarIndicadorForm.formula.value.indexOf(',') > -1) 
		document.editarIndicadorForm.formula.value = document.editarIndicadorForm.formula.value.replace(',', '.');
}
*/

function selectRowColors(indexRow, oTable, fieldRowSelected, 
						colorSelected, bgColorSelected,
						colorNoSelected, bgColorNoSelected) {

	var rowSelected = fieldRowSelected.value / 1;
	var numColumnas = 0;
	var i = 0;

	indexRow = indexRow / 1;
	var numColumnas = 1;
	if (oTable.getElementsByTagName("tr").item(rowSelected) != null) {
		numColumnas = oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length;

		for (i = 0; i < numColumnas; i++) {
			oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = colorNoSelected;
			oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = bgColorNoSelected;
		}
	}

	numColumnas = oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").length;

	for (i = 0; i < numColumnas; i++) {
		oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color = colorSelected;
		oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = bgColorSelected;
	}

	fieldRowSelected.value = indexRow;
}

function deleteRowColors(objTable, indexRow, objIndexSelected,
						colorSelected, bgColorSelected,
						colorNoSelected, bgColorNoSelected) {
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
	