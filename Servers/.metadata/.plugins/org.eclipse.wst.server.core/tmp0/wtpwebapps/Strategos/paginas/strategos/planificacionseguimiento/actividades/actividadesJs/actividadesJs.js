// Creado Por: Kerwin Arias (05/05/2012)
// Descripción: Javascript

// JavaScript para botones de duracionPlan

var objBotonNormal;
var objBotonUp;
var objBotonDown;
var conteoContinuo = false;
var cuadroNumericoActual;
var intervaloActual = 350;
var aceleraConteo = false;

// Manejo del aumento y decremento del cuadro num?rico 

function aumentarCuadroFecha() {
	var fechaInicio;
	var duracionReal;

	if (document.forms[0].finPlan.value == '') {
		document.forms[0].finPlan.value = document.forms[0].comienzoPlan.value;
	}

	if ((document.forms[0].finPlan.value != '') && (document.forms[0].comienzoPlan.value != '')) {
		       fechaInicio = document.forms[0].comienzoPlan.value; 
	   refrescarDuracion();   
	   duracionReal = parseInt(document.forms[0].duracionReal.value);	
       duracionReal = duracionReal + 1;   
	   document.forms[0].finPlan.value = sumarDiasFecha(fechaInicio, duracionReal);    
	   document.forms[0].duracionReal.value = duracionReal;	   
   }	   
   
   nombrePeriodoFecha();
}

function aumentoConstanteDuracionPlan() {
	if (conteoContinuo) {
		alert('33' + cuadroNumericoActual);
		aumentarCuadroFecha(cuadroNumericoActual);
		alert('331');
		setTimeout("aumentoConstanteDuracionPlan()", intervaloActual);
		alert('332');
		if (aceleraConteo) {
			aceleraConteo = false;
			intervaloActual = 100;
		} else {
			aceleraConteo = true;
		}
		alert('333');
	}
}

function decrementoConstanteDuracionPlan() {
	if (conteoContinuo) {
		disminuirCuadroFecha(cuadroNumericoActual);
		setTimeout("decrementoConstanteDuracionPlan()",intervaloActual);
		if (aceleraConteo) {
			aceleraConteo = false;
			intervaloActual = 100;
		} else {
			aceleraConteo = true;
		}
	}
}

function iniciarConteoContinuoDuracionPlan(cuadro) {
	conteoContinuo = true;
	cuadroNumericoActual = eval('document.forms[0].' + cuadro);
}

function finalizarConteoContinuoDuracionPlan() {
	conteoContinuo = false;
	aceleraConteo = false;
	intervaloActual = 350;
}

// Manejo del swapping de Imagenes	

function upActionDuracionPlan(nombreBoton) {
	if (document.images) {
		document[nombreBoton].src = objBotonUp.src;
	}
}

function downActionDuracionPlan(nombreBoton) {
	if (document.images) {
		document[nombreBoton].src = objBotonDown.src;
	}
}

function normalActionDuracionPlan(nombreBoton) {
	if (document.images) {
		document[nombreBoton].src = objBotonNormal.src;
	}
}

function inicializarBotonesCuadroDuracionPlan(imagenNormal, imagenDown, imagenUp) {

	if (document.images) {
		objBotonNormal = new Image();
		objBotonNormal.src = imagenNormal;
		objBotonDown = new Image();
		objBotonDown.src = imagenDown;
		objBotonUp = new Image();
		objBotonUp.src = imagenUp;
	}
}
