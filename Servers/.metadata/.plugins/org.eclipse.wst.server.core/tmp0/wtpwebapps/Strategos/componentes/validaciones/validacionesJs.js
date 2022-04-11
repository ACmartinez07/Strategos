// <script type="text/javascript">


//-- Modificado por: Kerwin Arias (27/08/2012) -->

function validarNumero(evento, objInput, sepDecimal, numDecimales, permitirValoresNegativos) {
	var valorCampo;
	var evento_key = (window.event) ? event.keyCode : evento.which;
	var numPosSepDecimal = 0;
	var numPosSigNegativo = 0;
	var strParteEntera = "";
	var strParteDecimal = "";
	var keyCodeValido = false;
	switch (evento_key) {
		case 0:
			keyCodeValido = true;
			break;
		case 8:
			keyCodeValido = true;
			break;
		case 45:
			if (!permitirValoresNegativos) {
				if (window.event) {
					window.event.keyCode = 0;
				}
				return false;
			}
			break;
		case 48:
			break;
		case 49:
			break;
		case 50:
			break;
		case 51:
			break;
		case 52:
			break;
		case 53:
			break;
		case 54:
			break;
		case 55:
			break;
		case 56:
			break;
		case 57:
			break;
		case 46:
			if (numDecimales == 0) {
				if (window.event) {
					window.event.keyCode = 0;
				}
				return false;
			}
			break;
		case 44:
			if (numDecimales == 0) {
				if (window.event) {
					window.event.keyCode = 0;
				}
				return false;
			}
			break;
		default:
			if (window.event) {
				window.event.keyCode = 0;
			}
			return false;
	}
	valorCampo = objInput.value;
	if ((evento_key == 46) || (evento_key == 44)) {
		if (valorCampo.indexOf(sepDecimal) != -1) {
			if (window.event) {
				window.event.keyCode = 0;
			}
			return false;
		}
		if (sepDecimal == ',') {
			if (window.event) {
				window.event.keyCode = 44;
			} else {
				if (evento_key != 44) {
					var newEvent = document.createEvent("KeyEvents");
					newEvent.initKeyEvent("keypress", true, true, document.defaultView, evento.ctrlKey, evento.altKey, evento.shiftKey, evento.metaKey, 0, ",".charCodeAt(0));
					evento.preventDefault();
					evento.target.dispatchEvent(newEvent);
				}
			}
		}
		if (sepDecimal == '.') {
			if (window.event) {
				window.event.keyCode = 46;
			} else {
				if (evento_key != 46) {
					var newEvent = document.createEvent("KeyEvents");
					newEvent.initKeyEvent("keypress", true, true, document.defaultView, evento.ctrlKey, evento.altKey, evento.shiftKey, evento.metaKey, 0, ".".charCodeAt(0));
					evento.preventDefault();
					evento.target.dispatchEvent(newEvent);
				}
			}
		}
	}
	if (permitirValoresNegativos) {
		if (window.event.keyCode == 45) {
			if (valorCampo.length  > 0) {
				if (window.event) {
					window.event.keyCode = 0;
				}
				return false;
			}
		}
	}
	if ((numPosSepDecimal = valorCampo.indexOf(sepDecimal)) != -1) {
		strParteEntera = valorCampo.substr(0,(numPosSepDecimal - 1));
		strParteDecimal = valorCampo.substr((numPosSepDecimal + 1), valorCampo.length)
		if (strParteDecimal.length > (numDecimales - 1)){
			if (valorCampo.length  > 0) {
				if (window.event) {
					window.event.keyCode = 0;
				}
			}
			if (keyCodeValido) {
				return true;
			} else {
				return false;
			}
		}
	}
	return true;
}

//----------------------------------------------------------------------------------------------------------//
//------ FIN: Funciones para manejo de números (Enteros, Decimales) ----------------------------------------//
//----------------------------------------------------------------------------------------------------------//

// </script>
