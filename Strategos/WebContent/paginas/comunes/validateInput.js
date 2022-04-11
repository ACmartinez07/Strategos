/*
 * tables.js 1.0.0
 * 30/01/2006
 *
 * Funciones de Javascript para la validacion de input
 *
 *
 */

	function esEnteroCeroCien(obtTexto) {
	    
		var valor = obtTexto.value;
	
	    var valido = valor.indexOf('.') == -1;
	
		if (valido) {
		   valido = valor.indexOf(',') == -1;
		}
		
		if (valido) {
		   valido = !isNaN(valor);
		}
	
		if (valido) {
		   valido = (valor >= 0) && (valor <= 100);
		}
		
	    return valido;
	}
	
	
	function verificarNumero(obtTexto) 
	{
	   var valido = false;
	   valido = esEnteroCeroCien(obtTexto);
	    
	   if (!valido) 
	      obtTexto.value = obtTexto.value.substring(0, obtTexto.value.length-1);   
	}

	function getkey(e) {        
		if (window.event)
		{
 			return window.event.keyCode;
		}
		else if (e)
		{
			return e.which;
		}
		else return null;
	 }

	function goodchars(e, goods) {
		var key, keychar;
		key = getkey(e);
		if (key == null) return true;

		// get character
		keychar = String.fromCharCode((96 <= key && key <= 105) ? key-48 : key);
		keychar = keychar.toLowerCase();
		goods = goods.toLowerCase();

		// check goodkeys
		if (goods.indexOf(keychar) != -1)
			return true;

		// control keys
        if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 )
   		return true;

		// else return false
		return false;
      }
        
	  function validarRango(desdeobj, hastaobj, desdeAntobj, hastaAntobj, errmsg){
  		desde = parseInt(desdeobj.value);
		hasta = parseInt(hastaobj.value);
		if (hasta<desde) {
			alert(errmsg);
			desdeobj.value = desdeAntobj.value;
			hastaobj.value = hastaAntobj.value;				
		} else {
			desdeAntobj.value = desde;
			hastaAntobj.value = hasta;				
		}
	  }

	  function validarDobleRango(desdeRg1obj, hastaRg1obj, desdeRg1Antobj, hastaRg1Antobj, errmsgRg1, desdeRg2obj, hastaRg2obj, desdeRg2Antobj, hastaRg2Antobj, errmsgRg2) {
  		desde = parseInt(desdeRg1obj.value);
		hasta = parseInt(hastaRg1obj.value);
		
		if (hasta == desde) {
			validarRango(desdeRg2obj, hastaRg2obj, desdeRg2Antobj, hastaRg2Antobj, errmsgRg2);
		} else {
		    validarRango(desdeRg1obj, hastaRg1obj, desdeRg1Antobj, hastaRg1Antobj, errmsgRg1);
		}
	  }



	var ns,ie;
	ns = (document.layers) ? 1:0;
	ie = (document.all) ? 1:0;
				
	if (!ie) document.captureEvents(Event.MOUSEMOVE);
	document.onmousemove = getMouseXY;
	var tempX = 0;
	var tempY = 0;
				
	function getMouseXY(e) {
	   if (ie) {
	      tempX = event.clientX + document.body.scrollLeft;
	      tempY = event.clientY + document.body.scrollTop;
	   }
	   else {
	      tempX = e.pageX;
	      tempY = e.pageY;
	   }
	
	   if (tempX < 0){tempX = 0;}
	   if (tempY < 0){tempY = 0;}
	}
				
	function Coords(nombreDiv) 
	{
		var theDiv;
		var d=document;
		if(d.all)
			theDiv=d.all[nombreDiv].style;
		else if(d.layers)
			theDiv=d.layers[nombreDiv];
		else
			theDiv=d.getElementById(nombreDiv).style;
		theDiv.left= parseInt(tempX) + 10;
		theDiv.top= parseInt(tempY) + 25;
	}
        
        