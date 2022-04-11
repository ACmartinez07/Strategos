/**
------------------------------------------------
@about	Muestra el color seleccionado en un boton y en un textbox
@author	Carolina Casanova Garcia (aka quinqui)
@date		26/07/2011
@param	idObj		string		Recibe el ID de la capa de color, formado por "divColor_" + color
@return	void
*/
function capturarColor(idObj, inputObj, txtRGB, txtHex)
{
	var aux = new Array();
	arr = idObj.split("_");
	var value = "#" + arr[1];
	inputObj.style.backgroundColor = value;
	
	txtHex.value = value;
	txtRGB.value=hexToRgb(arr[1]);
	hideColorPicker();
}
/**
------------------------------------------------
@about	Crea un objeto DIV con el color seleccionado aplicado como fondo
@author	Carolina Casanova Garcia (aka quinqui)
@date		26/07/2011
@param	hColor		string		Codigo de Color Hexadecimal (sin #)
@return	object
*/
function divColor(hColor, inputObj, txtRGB, txtHex)
{
	var obj = document.createElement("div");
	obj.id	= "divColor_" + hColor;
	obj.style.backgroundColor = "#" + hColor; //"rgb(" + hRed + "," + hGreen + "," + hBlue + ")";
	obj.className	= "divColor";
	obj.title	= obj.style.backgroundColor;
	// se agrega una funcion al evento onclick para obtener el color
	// (si este manejador de eventos no les funciona, prueben con attachEvent()):
	obj.addEventListener("click", function() { capturarColor(this.id, inputObj, txtRGB, txtHex); }, false);
	// pueden agregar la accion a otros eventos, por ejemplo,
	// a cuando pasa el mouse por encima del objeto:
	//obj.addEventListener("mouseover", function() { capturarColor(this.id); }, false);
	return obj;
}

function colorPickerGetTopPos(inputObj)
{
	var returnValue = inputObj.offsetTop;
	while((inputObj = inputObj.offsetParent) != null)
	{
	  	returnValue += inputObj.offsetTop;
	}
	
	return (returnValue-120)-25;
}
	
function colorPickerGetLeftPos(inputObj)
{
	var returnValue = inputObj.offsetLeft;
	while((inputObj = inputObj.offsetParent) != null)returnValue += inputObj.offsetLeft;
	
	return (returnValue-420)+150;
}

/**
------------------------------------------------
@about	Crea una paleta de 216 colores (Web Safe)
@author	Carolina Casanova Garcia (aka quinqui)
@date		26/07/2011
@return	void
*/
function paleta(inputObj, txtRGB, txtHex)
{
	var color_picker_div = document.getElementById('The_colorPicker');
	color_picker_div.style.left = colorPickerGetLeftPos(inputObj) + 'px';
	color_picker_div.style.top = colorPickerGetTopPos(inputObj) + inputObj.offsetHeight + 2 + 'px';
	color_picker_div.style.display='block';
	
	var pale = document.getElementById("paletaColor");	// contenedor de los colores
	
	var color_picker_div_hijo = document.getElementById("The_colorPicker_div");	// contenedor de los colores
	if (color_picker_div_hijo != null)
		pale.removeChild(color_picker_div_hijo);
	color_picker_div_hijo = document.createElement('DIV');
	color_picker_div_hijo.id = 'The_colorPicker_div';
	pale.appendChild(color_picker_div_hijo);	
	
	var hexColor, hexRed, hexGreen, hexBlue;
	var nRed, nGreen, nBlue;
	
	for (nGreen = 0; nGreen < 256; nGreen = nGreen + 51)
	{
		for (nBlue = 0; nBlue < 256; nBlue = nBlue + 51)
		{
			for (nRed = 0; nRed < 256; nRed = nRed + 51)
			{
				hexRed		= nRed.toString(16);
				hexGreen	= nGreen.toString(16);
				hexBlue		= nBlue.toString(16);
				
				hexColor  = (nRed < 256 ? (parseInt(hexRed) < 10 ? "0": "") + hexRed + "": "FF");
				hexColor += (nGreen < 256 ? (parseInt(hexGreen) < 10 ? "0": "") + hexGreen + "": "FF");
				hexColor += (nBlue < 256 ? (parseInt(hexBlue) < 10 ? "0": "") + hexBlue: "FF");
				
				//debug.innerHTML += "<br/>[i] " + nIn + " [R] " + nRed + " [G] " + nGreen + " [B] " + nBlue + " [hex] " + hexColor;
				// agrega el color (div) a la paleta:
				var divCol = new divColor(hexColor, inputObj, txtRGB, txtHex);
				color_picker_div_hijo.appendChild(divCol);
			}
		}
	}
}

function hideColorPicker()
{
	var color_picker_div = document.getElementById('The_colorPicker');
	color_picker_div.style.display='none';
}
