/*
 * tables.js 1.0.0
 * 30/01/2006
 *Validacion de input
 */

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


        
        