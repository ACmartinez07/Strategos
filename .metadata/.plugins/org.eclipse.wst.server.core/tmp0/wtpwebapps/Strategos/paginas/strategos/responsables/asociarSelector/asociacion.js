// JavaScript Document
// Realizado por: Kerwin Arias(13/03/2012)

var onClickActivado = false;


var totalFilasTabla1 = 0;
var totalFilasTabla2 = 0;
var SEPARADOR_TOKENS = "|";
var listaElementos = SEPARADOR_TOKENS; 
var objForma;

function eliminarFila(objTabla) {
   
   var filaSeleccionada;
   var listaTabla;
   
   if (objTabla == 'Tabla1') {
      filaSeleccionada = objForma.filaSelTabla1;     
	  listaTabla = objForma.listaTabla1;
   } else {
      filaSeleccionada = objForma.filaSelTabla2;     
	  listaTabla = objForma.listaTabla2;
   }
   
   t = document.getElementById(objTabla);
   
   if (filaSeleccionada.value != "") {   
      deleteRow(t, filaSeleccionada.value);      
	  removerElemento(filaSeleccionada.value, listaTabla);
   }
      
   if (objTabla == 'Tabla1') {
      //totalFilasTabla1 = parseInt(totalFilasTabla1) - 1;
   } else {
      //totalFilasTabla2 = parseInt(totalFilasTabla2) - 1;
   }
   filaSeleccionada.value='';
   
}


function restaurarColorFondo(objetoTabla) {
 
  t = document.getElementById(objetoTabla);

  var fila;
  
  for (i = 0 ; i < t.rows.length ; i++) {
  	  fila = t.rows[i];
	  fondo(fila);	  
  } 	

}


function getNombreElemento(objetoTabla, elementoId) {
 
  t = document.getElementById(objetoTabla);

  var fila;
  var nombre;
  
  for (i = 0 ; i < t.rows.length ; i++) {
  	  fila = t.rows[i];
  	  if (fila.id == elementoId) {
  	     nombre = fila.cells[0].innerHTML; 	   
  	  }	
  } 	
  return nombre;
}


function moverElemento(tipoMovimiento) {
	 
	var filaSelTabla1 = objForma.filaSelTabla1; 
	var filaSelTabla2 = objForma.filaSelTabla2; 
	var listaTabla1 = objForma.listaTabla1; 
	var listaTabla2 = objForma.listaTabla2; 
	
    if (tipoMovimiento == '>') {
	   if (filaSelTabla1.value != '') {
	      insertarFila('Tabla2', listaTabla2, filaSelTabla1.value, getNombreElemento('Tabla1', filaSelTabla1.value), clickEventTabla2); 	      
	      eliminarFila('Tabla1');
	   } 	   
	} else {
	    if (filaSelTabla2.value != '') {
	      insertarFila('Tabla1', listaTabla1, filaSelTabla2.value, getNombreElemento('Tabla2', filaSelTabla2.value) , clickEventTabla1); 
	      eliminarFila('Tabla2');
	   } 	 
	}
	
	
	onClickActivado = false;

}

function insertarFila(objTabla, objLista, elementoId, elementoNombre, elemClickEvent) {
	
	var contenido = new Array(2);
	var url = new Array(2);
	var tipo = new Array(2);
	var ancho = new Array(2);
	var maxchar = new Array(2);
	var alinear = new Array(2);
	var anchocolumna = new Array(2);
	var nombre = new Array(2);
	var bloquearObj = new Array(2);
	var onBlurObj = new Array(2);
	var onMouseOutObj = new Array(2);
	var onKeyUpObj = new Array(2);
	var estiloObj = new Array(2);
	
	var onclickFila = "";
	var ondblclickFila = "";	
	var filaId = "";
	var objetoId = "";
	var nombreObjetoId = "";
	
	agregarElemento(elementoId, objLista);
	
	if (objTabla == 'Tabla1') {
	    totalFilasTabla1 =  totalFilasTabla1 + 1;
	} 
	if (objTabla == 'Tabla2') {
	    totalFilasTabla2 =  totalFilasTabla2 + 1;
	} 
	
	
	t = document.getElementById(objTabla);
	t.border = 0;
		
	contenido[0] = elementoNombre;
	url[0] = "";
	tipo[0] = 0;
	ancho[0] = "10";
	maxchar[0] = "20";
	alinear[0] = "left";
	anchocolumna[0] = "300";
	nombre[0] = "nombre" + elementoId + "_1";
	bloquearObj[0] = "0";
	onBlurObj[0] = null;
	onMouseOutObj[0] = null;
	onKeyUpObj[0] = null;
	estiloObj[0] = "entradaNumero";


	contenido[1] = elementoNombre;
	url[1] = "";
	tipo[1] = 4;
	ancho[1] = "0";
	maxchar[1] = "0";
	alinear[1] = "right";
	anchocolumna[1] = "0";
	nombre[1] = "nombre" + elementoId + "_2";
	bloquearObj[1] = "0";
	onBlurObj[1] = null;
	onMouseOutObj[1] = null;
	onKeyUpObj[1] = null;
	estiloObj[1] = "";

	
	filaId = elementoId;
	
	objetoId = elementoId;
	nombreObjetoId = "objId";
	
	t = document.getElementById(objTabla);
    
	addRow(t, filaId, nombreObjetoId, objetoId, elemClickEvent, dblclickEvent, onBlurObj, onKeyUpObj, onMouseOutObj, 
	contenido, tipo, anchocolumna, alinear, url, nombre, ancho, maxchar, bloquearObj, estiloObj, onmouseoverEventTr, onmouseoutEventTr, 'mouseFueraCuerpoListView');	
}

function clickEventTabla1(event) {    
  restaurarColorFondo('Tabla1');   
  if (objForma.filaSelTabla1.value != this.id) {
     resaltar(this);  
	 objForma.filaSelTabla1.value = this.id;	 
  } else {
     objForma.filaSelTabla1.value = '';
  }
}

// Función que establece el estilo
function onmouseoverEventTr(event) {
	this.className='mouseEncimaCuerpoListView';
}

// Función que establece el estilo
function onmouseoutEventTr(event) {
	this.className='mouseFueraCuerpoListView';
}

function clickEventTabla2(event) {  
  restaurarColorFondo('Tabla2');   
  if (objForma.filaSelTabla2.value != this.id) {
     resaltar(this);  
	 objForma.filaSelTabla2.value = this.id;
  } else {
     objForma.filaSelTabla2.value = '';
  }

}


function mouseoutObjEvent(event) {
}

function keyupObjEvent(event) {

}

function blurObjEvent(event) {
}

function dblclickEvent(event) {
}

// Función que establece el estilo
function onmouseoverEventTr(event) {
	if (!onClickActivado) {
		this.className='mouseEncimaCuerpoListView';
	}
}

// Función que establece el estilo
function onmouseoutEventTr(event) {
	if (!onClickActivado) {
		this.className='mouseFueraCuerpoListView';
	}
}

function resaltar(elem) {  
  //elem.style.backgroundColor='#FFFFCC';
  onClickActivado = true;
  elem.className='cuerpoListViewFilaSeleccionada';   
}

function fondo(elem) {  
  //elem.style.backgroundColor='white';
  elem.className='mouseFueraCuerpoListView';  
}

function highlight(event) {  
  this.style.backgroundColor='black';  
  this.style.color='white';
}

function normal(event) {  
  this.style.backgroundColor='yellow';  
  this.style.color='';
}

function addRow(objTable, rowId, nameObjId, objId, onclickRow, ondblclickRow, onblurObj, onkeyupObj, onmouseoutObj, contentCol, typeCol, widthCol, alignCol, linkCol, nameObj, widthObj, maxcharObj, blockObj, styleObj, onmouseoverTr, onmouseoutTr, classTr) {      
        
	// types:
	// 0 - texto
	// 1 - hyperlink texto
	// 2 - hyperlink imagen
    // 3 - input text
	// 4 - hidden text

	var newRow = document.createElement("tr");
	
	var currentCell;
	var currentText;
	var currentImage;
	var currentText;
	var currentObjId;
	var currentLink;
	var j = 0;
	
	objTable.insertRow(objTable.rows.length);
	
	newRow = objTable.rows[objTable.rows.length-1];
	
	newRow.id = rowId;
	
	// Establece el estilo de la fila 
	newRow.onmouseout = onmouseoutTr; 
	newRow.onmouseover = onmouseoverTr;
	newRow.className = classTr;
	newRow.height = 20;

    currentObjId = document.createElement('input');
    currentObjId.type = 'hidden';
    currentObjId.id = nameObjId +  rowId;
	currentObjId.name = nameObjId +  rowId;
	currentObjId.value = objId;
	
	newRow.appendChild(currentObjId);
	newRow.onclick = onclickRow;
	newRow.ondblclick = ondblclickRow;

	for (i=0; i < contentCol.length; i++) {
	
	  if (typeCol[j] != 4) {
	     currentCell = document.createElement("td");
	  }
	  
       if (typeCol[j] != 4) {
	     currentCell.align = alignCol[j]; 	
	  }
	  
	  switch (typeCol[j]) {
		  case 0: 
			 currentText = document.createTextNode( contentCol[i] );
			 currentCell.appendChild(currentText);
			 break;
		  
		  case 1: 
			 currentText = document.createTextNode( contentCol[i] );

			 currentLink = document.createElement('a');
			 currentLink.setAttribute('href', linkCol[j] );
			 currentLink.appendChild(currentText);
			 currentCell.appendChild(currentLink);
			 
			 break;
		  
		  case 2: 
			 currentImage = document.createElement('img');
			 currentImage.src = contentCol[i];
			 
			 currentLink = document.createElement('a');
			 currentLink.setAttribute('href', linkCol[j] );
			 currentLink.appendChild(currentImage);
			 currentCell.appendChild(currentLink);
		  
			 break;

		  case 3: 
			 currentText = document.createElement('input');			 
             currentText.type = 'text';
             currentText.size = widthObj[j];
			 currentText.onmouseout = onmouseoutObj[j];
			 currentText.onkeyup = onkeyupObj[j];			 
			 currentText.onblur = onblurObj[j];		
			 currentText.className = styleObj[j];	 
	  		 
             if (maxcharObj[j] > 0) {
                currentText.maxLength = maxcharObj[j]; 
             } 	

             if (contentCol[j] != "") {
                currentText.value = contentCol[j]; 
             } 	 

             if ((blockObj[j] != "") && (blockObj[j] == "1")) {
                currentText.readOnly = true; 
             } 	 
 
			 
			 currentText.id = nameObj[j];	 	
			 currentText.name = nameObj[j];	 	

			 currentCell.appendChild(currentText);

			 break;		
			 
	  case 4: 
			 currentText = document.createElement('input');			 
             currentText.type = 'hidden';
      			 
			 currentText.id = nameObj[j];	 	
			 currentText.name = nameObj[j];	 	

			// currentCell.appendChild(currentText);

			 break;					   
	  }
	  
	  j = j + 1;
	  if (j == typeCol.length) {
		 j = 0;
	  }
	  
	  newRow.appendChild(currentCell);		
	} 
}




function deleteRow(objTable, elemId) {
	
	for (i=0; i < objTable.rows.length; i++) {
	   if (objTable.rows[i].id == elemId) {
	      objTable.deleteRow(i);
	   }	
	}
}


function keyPressTest(e) { 

	  var key; 
	  var ctrlKey; 
	  var shiftKey;
	  if(window.event) { 
		   key = window.event.keyCode; 
		   shiftKey = window.event.shiftKey; 
		   ctrlKey = window.event.ctrlKey; 
	  } else if (e.which) { 
		   key = e.which; 
		   shiftKey = e.shiftKey; 
		   ctrlKey = e.ctrlKey; 
	  } 

	  if (ctrlKey) {
	     return "ctrl +";
	  } else if (shiftKey) {
	     return "shift +";
	  } else if (key == 17) {
	     return "ctrl";
	  } else if (key == 16) {
	     return "shift";
	  } else if (key == 13) {
	     return "enter";
	  } else {
	     return String.fromCharCode(key);
	  }	 
} 

function agregarElemento(elemento, lista)
{    
	if (! existeElemento(elemento, lista))
    {
	   lista.value = lista.value + elemento + SEPARADOR_TOKENS;
    }
}

function removerElemento(elemento, lista)
{
	var tempStrIni, tempStrFin;
	var posToken = lista.value.indexOf(SEPARADOR_TOKENS + elemento + SEPARADOR_TOKENS); 

	if (posToken > -1)
	{
		var lenToken = (SEPARADOR_TOKENS + elemento).length;
		
		tempStrIni = lista.value.substring(0, posToken); 		
		tempStrFin = lista.value.substring(posToken + lenToken); 		
		lista.value = (tempStrIni + tempStrFin);
	}
}

function existeElemento(elemento, lista)
{	
	var posToken = lista.value.indexOf(SEPARADOR_TOKENS + elemento + SEPARADOR_TOKENS); 
	
	return (posToken > -1);
}			