// Creado Por: Kerwin Arias (18/04/2012)

var totalFilas = 0;

function obtenerExpresionRegular(mascara, posicion) {
   var strExpresion = '';
   
   for (i = 0 ; i <= posicion ; i++) {
      
      if (mascara.substring(i,i+1) == "X") {	  
	     strExpresion = strExpresion + '[0-9]';
	  } else {
	     strExpresion = strExpresion + '\\.';
	  }	  	   
   }
   
   return new RegExp(strExpresion);   
}



function verificarExpresion(texto) {
	
   var caracter = '';
   var caracterAnterior = '';
   var validado = false;
   var resultado = new Array(2);
   var i = 0;

   for (i = 0 ; i < texto.length ; i++) {

	  caracter = texto.substring(i,i+1).toUpperCase(); 
	  
      if (texto.length == 1) {	  
	    if (caracter == 'X') {
		   validado = true;
		} else {		   
		   validado = false;
		   break;
		}
	  } else {
	    
	    if (caracter == 'X') {		   
		   validado = true;
	    } else {
		   validado = false;
		   break;
		}
	  }   
   }
   
   resultado[0] = validado;
   resultado[1] = i;
   
   return resultado;
}

function verificarMascara(texto) {

    var ultimoCaracter = texto.value.substring(texto.value.length-1,texto.value.length); 
	var	resultado = verificarExpresion(texto.value);
	var	validado = resultado[0];
	var	posicion = parseInt(resultado[1]);

    while ((!validado) && (texto.value != '')) {
        		
		if (!validado) {
		   if (posicion == texto.value.length) {
			  texto.value = texto.value.substring(0, texto.value.length);
		   } else {
			  texto.value = texto.value.substring(0, posicion) + texto.value.substring(posicion + 1, texto.value.length);	      
		   }	   
		} 	
        
		resultado = verificarExpresion(texto.value);    
		validado = resultado[0];   
		posicion = parseInt(resultado[1]);		       		
	}
	
	if (validado) {
	   texto.value = texto.value.toUpperCase();
	}

	
}


function eliminarTodaFila(objTabla) {   
   var tabla = document.getElementById(objTabla);
   
   var numeroFilas = tabla.rows.length; 
   
   for (i = 0 ; i < numeroFilas ; i++) {   
      deleteRow(tabla, 0);
   }
}



function insertarFila(objTabla) {
	
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
	
	totalFilas = parseInt(totalFilas) + 1;
		
	contenido[0] = "";
	url[0] = "";
	tipo[0] = 3;
	ancho[0] = "10";
	maxchar[0] = "10";
	alinear[0] = "left";
	anchocolumna[0] = "70";
	nombre[0] = "mascara_" + totalFilas;	
	bloquearObj[0] = "0";
	onBlurObj[0] = null;
	onMouseOutObj[0] = null;
	onKeyUpObj[0] = keyupObjEvent;
	estiloObj[0] = "cuadroTexto";	
	
	
	contenido[1] = "";
	url[1] = "";
	tipo[1] = 3;
	ancho[1] = "37";
	maxchar[1] = "250";
	alinear[1] = "left";
	anchocolumna[1] = "310";
	nombre[1] = "nombre_" + totalFilas;
	bloquearObj[1] = "0";
	onBlurObj[1] = null;
	onMouseOutObj[1] = null;
	onKeyUpObj[1] = null;
	estiloObj[1] = "cuadroTexto";	
	
	filaId = "fila" + totalFilas;
	
	objetoId = "0";
	nombreObjetoId = 'objId' + totalFilas;
	
	t = document.getElementById(objTabla);

	addRow(t, filaId, nombreObjetoId, objetoId, clickEvent, dblclickEvent, onBlurObj, onKeyUpObj, onMouseOutObj,  contenido, tipo, anchocolumna, alinear, url, nombre, ancho, maxchar, bloquearObj, estiloObj, '');	
}

function getkey(e) { 
if (window.event)  { return window.event.keyCode; }
else if (e) {return e.which; }
else {return null; } 
}



function backspacekey(e)
{

var key, keychar;
key = getkey(e);
if (key == null) return true;

if (key==8)
   return true;

// else return false
return false;
}


function clickEvent(event) {
}

function mouseoutObjEvent(event) {

}

function keyupObjEvent(event) {

  if (!backspacekey(event)) {
     verificarMascara(this);
  } else {
     if (this.value.substring(this.value.length-1, this.value.length) == '.') {
	    this.value = this.value.substring(0, this.value.length-1);
	 }
  } 
  
  

}

function blurObjEvent(event) {

}



function dblclickEvent(event) { 
}


function resaltar(elem) {  
  elem.style.backgroundColor='#ACD7F7';   
}

function fondo(elem) {  
  elem.style.backgroundColor='white';  
}


function highlight(event) {  
  this.style.backgroundColor='black';  
  this.style.color='white';
}

function normal(event) {  
  this.style.backgroundColor='yellow';  
  this.style.color='';
}

function addRow(objTable, rowId, nameObjId, objId, onclickRow, ondblclickRow, onblurObj, onkeyupObj, onmouseoutObj, contentCol, typeCol, widthCol, alignCol, linkCol, nameObj, widthObj, maxcharObj, blockObj, styleObj, strMascara) {      
        
	// types:
	// 0 - texto
	// 1 - hyperlink texto
	// 2 - hyperlink imagen
    // 3 - input text

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

    currentObjId = document.createElement('input');
    currentObjId.type = 'hidden';
    currentObjId.id = nameObjId;
	currentObjId.name = nameObjId;
	currentObjId.value = strMascara;
	
	newRow.appendChild(currentObjId);
	newRow.onclick = onclickRow;
	newRow.ondblclick = ondblclickRow;

	for (i=0; i < contentCol.length; i++) {
	
	  currentCell = document.createElement("td");

	  currentCell.align = alignCol[j]; 	
      currentCell.width = widthCol[j]; 	
	  
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
	  }
	  
	  j = j + 1;
	  if (j == typeCol.length) {
		 j = 0;
	  }
	  
	  newRow.appendChild(currentCell);		
	} 
}




function deleteRow(objTable, indexRow) {
	objTable.deleteRow(indexRow);
}


function keyPressTest(e) { 

	  var key; 
	  var ctrlKey; 
	  var shiftKey;
	  if(window.event) { 
		   key = window.event.keyCode; 
		   shiftKey = window.event.shiftKey; 
		   ctrlKey = window.event.ctrlKey; 
	  } else if(e.which) { 
		   key = e.which; 
		   shiftKey = e.shiftKey; 
		   ctrlKey = e.ctrlKey; 
	  } 

	  if (ctrlKey) {
	     return "ctrl";
	  } else if (shiftKey) {
	     return "shift";
	  } else if (key == 13) {
	     return "enter";
	  } else {
	     return String.fromCharCode(key);
	  }	 
} 



function validarNiveles(texto) {
   
   if ((texto.value != '') && (parseInt(texto.value) > 0) && (parseInt(texto.value) < 7)) {
      return true;
   } else {      
      return false;
   }
}

function revisarNiveles(texto) {
   if (!validarNiveles(texto)) {
      texto.value = texto.value.substring(0, texto.value.length-1);
   } 
}

function insertarNiveles() {    
   totalFilas = 0;
   
   var nivelesValidos = validarNiveles(document.forms[0].niveles);  

     
   if (nivelesValidos) {
     
	  eliminarTodaFila('tablaNiveles');

      switch (parseInt(document.forms[0].niveles.value))  {
	  
	  case 1:
            insertarFila('tablaNiveles');	  
	  break;
	  case 2:
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
	  break;
	  case 3:
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
	  break;	  
	  case 4:
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
	  break;
	  case 5:
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  													  
	  break;
	  case 6:
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  
            insertarFila('tablaNiveles');	  												
            insertarFila('tablaNiveles');	  			
	  break;	  	  
	  }
   }
   
   return nivelesValidos;
    
}






