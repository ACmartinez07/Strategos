	var objBotonNormal;			  
	var objBotonUp;
	var objBotonDown;
	var conteoContinuo = false;
	var cuadroNumericoActual;
	var intervaloActual = 350;
    var aceleraConteo = false;
    var limiteSuperior = 100;
    var limiteInferior = 0;
	
	// Manejo del aumento y decremento del cuadro num?rico 
		
	function aumentar(cuadro) 
	{
	    if (cuadro.value < limiteSuperior) 
	       cuadro.value++;
	}
	
	function decrementar(cuadro) 
	{   
	   if (cuadro.value > limiteInferior) 
	      cuadro.value--;
	}
		
	function aumentoConstante() 
	{     
		if (conteoContinuo) 
		{ 
			aumentar(cuadroNumericoActual);
			setTimeout("aumentoConstante()",intervaloActual);
			if (aceleraConteo) 
			{
				aceleraConteo = false;
				intervaloActual = 100;
			}  
			else 
				aceleraConteo = true;
		}
	}
	
	function decrementoConstante() 
	{
		if (conteoContinuo) 
		{ 
			decrementar(cuadroNumericoActual);
	        setTimeout("decrementoConstante()",intervaloActual);
	        if (aceleraConteo) 
	        {
		       aceleraConteo = false;
		       intervaloActual = 100;
		    }  
	        else 
	           aceleraConteo = true;
		}
	}
		
	function iniciarConteoContinuo(cuadro, limiteSup, limiteInf) 
	{   
		conteoContinuo = true;
		if (typeof cuadro === 'string' || cuadro instanceof String)
			cuadroNumericoActual = eval('document.forms[0].' + cuadro);
		else
			cuadroNumericoActual = cuadro;
	   
		if (limiteSup != null) 
			limiteSuperior = limiteSup;
		if (limiteInf != null) 
			limiteInferior = limiteInf;
	}
	
	function finalizarConteoContinuo() 
	{   
		conteoContinuo = false;
		aceleraConteo = false;
        intervaloActual = 350; 
	}
	
	
    // Manejo del swapping de Imagenes	
	function upAction(nombreBoton) 
	{
		if (document.images) 
			document[nombreBoton].src = objBotonUp.src;
	} 
	
	function downAction(nombreBoton) 
	{
		if (document.images) 
			document[nombreBoton].src = objBotonDown.src;
	} 
	
	function normalAction(nombreBoton) 
	{
		if (document.images) 
			document[nombreBoton].src = objBotonNormal.src;
	} 
		
	function inicializarBotonesCuadro(imagenNormal, imagenDown, imagenUp) 
	{
		if (document.images) 
		{ 
			objBotonNormal = new Image();
			objBotonNormal.src = imagenNormal;
			objBotonDown = new Image();
			objBotonDown.src = imagenDown;
			objBotonUp = new Image();
			objBotonUp.src = imagenUp;		  		  
		} 
	}
