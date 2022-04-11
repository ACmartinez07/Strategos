function verificarExpresionRegular(texto, mascara) {   
   var strExpresion = '';
   var expresion;
   var validado = false;
   var resultado = new Array(2);
   var i = 0;
   for (i = 0 ; i < texto.length ; i++) {
	  if (mascara.substring(i,i+1) == "X") {	  
		 strExpresion = strExpresion + '([0-9]|[a-z]|[A-Z])';
	  } 
	  expresion = new RegExp(strExpresion);
	  validado = expresion.test(texto.substring(0,i+1));
	  if (!validado) {
		 break;
	  }
   }
   resultado[0] = validado;
   resultado[1] = i;   
   return resultado;   
}

var mascaraActual = '';
function verificarMascara(texto) {   
	var mascara = mascaraActual;	
	var resultado = verificarExpresionRegular(texto.value, mascara);
	var validado = resultado[0];
	var posicion = parseInt(resultado[1]);	
	while ((!validado) && (texto.value != '')) {
		if (!validado) {
		   texto.value = texto.value.substring(0, posicion) + texto.value.substring(posicion + 1, texto.value.length);	      
		}
		resultado = verificarExpresionRegular(texto.value, mascara);
		validado = resultado[0];
		posicion = parseInt(resultado[1]);		
	}
	if (texto.value.length > mascara.length) {
	   texto.value = texto.value.substring(0, mascara.length);
	}
}

 