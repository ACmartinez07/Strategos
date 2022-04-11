<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

/** Funciones JavaScript para la naturaleza formula */

// Formato de string de categorias (Escala Cualitativa):
// [categoriaId:###][categoriaNombre:###]separadorCategorias ....
function setEscalaCualitativa() 
{
	var tablaListaCategorias = document.getElementById('tablaListaCategorias');

	// Se borra la lista de categorías
	while (tablaListaCategorias.getElementsByTagName("tr").length > 0) 
	{
		tablaListaCategorias.deleteRow(0);
	}

	var listaCategorias = document.editarIndicadorForm.escalaCualitativa.value.split(separadorCategorias);
	for (var i = 0; i < listaCategorias.length; i++) {
		if (listaCategorias[i].length > 0) {
			valoresCategoria = listaCategorias[i];
			indice = valoresCategoria.indexOf("][");
			categoriaId = valoresCategoria.substring('[categoriaId:'.length, indice);
			valoresCategoria = valoresCategoria.substring(indice + 1, valoresCategoria.length);
			nombreCategoria = valoresCategoria.substring('[nombreCategoria:'.length, valoresCategoria.length - 1);
			var numFilas = tablaListaCategorias.getElementsByTagName("tr").length;
			var tbody = tablaListaCategorias.getElementsByTagName("tbody")[0];
			var row = document.createElement("tr");
			row.style.height = "20px";
			var td1 = document.createElement("td");
			td1.appendChild(document.createTextNode(nombreCategoria));
			row.appendChild(td1);
			row.onclick = function() {
				selectRowColors(this.rowIndex, 
							document.getElementById('tablaListaCategorias'), 
							document.getElementById('categoriaSeleccionada'),
							"white", "blue", "#666666", "white");
			}
			tbody.appendChild(row);
			if (numFilas == 0) {
				selectRowColors(0,
							document.getElementById('tablaListaCategorias'), 
							document.getElementById('categoriaSeleccionada'),
							"white", "blue", "#666666", "white");
			}
		}
	}
}

function agregarCategoriaEscala() {

	if (document.editarIndicadorForm.categoriaId.selectedIndex == 0) {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.seleccionecategoria" />');
		return;
	}

	var escalaCualitativa = document.editarIndicadorForm.escalaCualitativa.value;

	var categoriaId = document.editarIndicadorForm.categoriaId.options[document.editarIndicadorForm.categoriaId.selectedIndex].value;
	var categoriaNombre = document.editarIndicadorForm.categoriaId.options[document.editarIndicadorForm.categoriaId.selectedIndex].text;

	var categoriaBuscada = '[categoriaId:' + categoriaId + ']';

	if (escalaCualitativa.indexOf(categoriaBuscada) < 0) {
		var separador = '';
		if (escalaCualitativa != '') {
			separador = separadorCategorias;
		}
		escalaCualitativa = escalaCualitativa
					+ separador
					+ categoriaBuscada
					+ '[categoriaNombre:' + categoriaNombre + ']';

		document.editarIndicadorForm.escalaCualitativa.value = escalaCualitativa;
		setEscalaCualitativa();

		var tablaListaCategorias = document.getElementById('tablaListaCategorias');
		selectRowColors(tablaListaCategorias.getElementsByTagName("tr").length - 1,
					tablaListaCategorias,
					document.getElementById('categoriaSeleccionada'),
					"white", "blue", "#666666", "white");

	} else {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.categoriaduplicada" />');
	}

}

function insertarCategoriaEscala() {

	if (document.editarIndicadorForm.categoriaId.selectedIndex == 0) {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.seleccionecategoria" />');
		return;
	}

	var escalaCualitativa = document.editarIndicadorForm.escalaCualitativa.value;

	var categoriaId = document.editarIndicadorForm.categoriaId.options[document.editarIndicadorForm.categoriaId.selectedIndex].value;
	var categoriaNombre = document.editarIndicadorForm.categoriaId.options[document.editarIndicadorForm.categoriaId.selectedIndex].text;

	var categoriaBuscada = '[categoriaId:' + categoriaId + ']';

	if (escalaCualitativa.indexOf(categoriaBuscada) < 0) {
		var separador = '';
		if (escalaCualitativa != '') {
			separador = separadorCategorias;
		}

		var posicionInsercion = parseInt(document.editarIndicadorForm.categoriaSeleccionada.value);

		if (posicionInsercion == 0) {
			escalaCualitativa =  categoriaBuscada
						+ '[categoriaNombre:' + categoriaNombre + ']'
						+ separador
						+ escalaCualitativa;
		} else {
			var index = 0;
			for (i = 0; i < posicionInsercion; i++) {
				index = index + escalaCualitativa.substring(index, escalaCualitativa.length).indexOf(separadorCategorias);
				if (index > -1) {
					index= index + separadorCategorias.length;
				}
			}
			parte1 = '';
			if (index > -1) {
				index = index - separadorCategorias.length;
				parte1 = escalaCualitativa.substring(0, index);
				parte2 = escalaCualitativa.substring(index, escalaCualitativa.length);
				alert(parte1);
				alert(parte2);
				escalaCualitativa = parte1 + separador
							+ categoriaBuscada
							+ '[categoriaNombre:' + categoriaNombre + ']'
							+ parte2;
			}
		}

		document.editarIndicadorForm.escalaCualitativa.value = escalaCualitativa;
		setEscalaCualitativa();

		var tablaListaCategorias = document.getElementById('tablaListaCategorias');
		selectRowColors(posicionInsercion,
					tablaListaCategorias,
					document.getElementById('categoriaSeleccionada'),
					"white", "blue", "#666666", "white");
	} else {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.categoriaduplicada" />');
	}
}

function removerCategoriaEscala() {
	var tablaListaCategorias = document.getElementById('tablaListaCategorias');
	numeroFilas = tablaListaCategorias.getElementsByTagName("tr").length;
	var escalaCualitativa = document.editarIndicadorForm.escalaCualitativa.value;

	if (numeroFilas == 0) {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.nocategoriasremover" />');
		return;
	}

	var posicionBuscada = parseInt(document.editarIndicadorForm.categoriaSeleccionada.value) + 1;
	if (posicionBuscada == 1) {
		var index = escalaCualitativa.indexOf(separadorCategorias);
		if (index < 0) {
			escalaCualitativa = '';
		} else {
			escalaCualitativa = escalaCualitativa.substring(index + separadorCategorias.length, escalaCualitativa.length);
		}
	} else if (posicionBuscada == numeroFilas) {
		var index = escalaCualitativa.lastIndexOf(separadorCategorias);
		escalaCualitativa = escalaCualitativa.substring(0, index);
	} else {
		var index = 0;
		for (i = 1; i < posicionBuscada; i++) {
			index = index + escalaCualitativa.substring(index, escalaCualitativa.length).indexOf(separadorCategorias);
			if (index > -1) {
				index= index + separadorCategorias.length;
			}
		}
		parte1 = '';
		if (index > -1) {
			parte1 = escalaCualitativa.substring(0, index - separadorCategorias.length);
		}
		index = index + escalaCualitativa.substring(index, escalaCualitativa.length).indexOf(separadorCategorias);
		parte2 = escalaCualitativa.substring(index, escalaCualitativa.length);
		escalaCualitativa = parte1 + parte2;
	}
	document.editarIndicadorForm.escalaCualitativa.value = escalaCualitativa;
	deleteRowColors(tablaListaCategorias,
					document.editarIndicadorForm.categoriaSeleccionada.value,
					document.getElementById('categoriaSeleccionada'),
					"white", "blue", "#666666", "white");
}

function moverCategoriaEscala(direccion) {
	var tablaListaCategorias = document.getElementById('tablaListaCategorias');
	numeroFilas = tablaListaCategorias.getElementsByTagName("tr").length;
	var escalaCualitativa = document.editarIndicadorForm.escalaCualitativa.value;

	if (numeroFilas == 0) {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.nocategorias" />');
		return;
	}
	var posicionActual = parseInt(document.editarIndicadorForm.categoriaSeleccionada.value) + 1;
	if (direccion == 'arriba') {
		if (posicionActual == 1) {
			alert('<vgcutil:message key="jsp.editarindicador.mensaje.categorianosube" />');
			return;
		}
	} else if (direccion == 'abajo') {
		if (posicionActual == numeroFilas) {
			alert('<vgcutil:message key="jsp.editarindicador.mensaje.categorianobaja" />');
			return;
		}
		posicionActual++;
	} else {
		alert('<vgcutil:message key="jsp.editarindicador.mensaje.opcionnovalida" />');
		return;
	}
	var index = 0;
	var parte1 = '';
	for (i = 1; i < posicionActual - 1; i++) {
		index = index + escalaCualitativa.substring(index, escalaCualitativa.length).indexOf(separadorCategorias);
		if (index > -1) {
			index = index + separadorCategorias.length;
		}
	}
	if (index > 0) {
		parte1 = escalaCualitativa.substring(0, index);
		escalaCualitativa = escalaCualitativa.substring(index, escalaCualitativa.length);
	}
	index = escalaCualitativa.indexOf(separadorCategorias);
	categoria1 = escalaCualitativa.substring(0, index);
	escalaCualitativa = escalaCualitativa.substring(index + separadorCategorias.length, escalaCualitativa.length);
	index = escalaCualitativa.indexOf(separadorCategorias);
	if (index < 0) {
		escalaCualitativa = parte1 + escalaCualitativa + separadorCategorias + categoria1;
	} else {
		categoria2 = escalaCualitativa.substring(0, index);
		escalaCualitativa = escalaCualitativa.substring(index, escalaCualitativa.length);
		escalaCualitativa = parte1 + categoria2 + separadorCategorias + categoria1 + escalaCualitativa;
	}
	document.editarIndicadorForm.escalaCualitativa.value = escalaCualitativa;
	setEscalaCualitativa();
	var categoriaSeleccionada;
	if (direccion == 'arriba') {
		categoriaSeleccionada = posicionActual - 2;
	} else if (direccion == 'abajo') {
		categoriaSeleccionada = posicionActual - 1;
	}
	selectRowColors(categoriaSeleccionada,
				tablaListaCategorias,
				document.getElementById('categoriaSeleccionada'),
				"white", "blue", "#666666", "white");
}