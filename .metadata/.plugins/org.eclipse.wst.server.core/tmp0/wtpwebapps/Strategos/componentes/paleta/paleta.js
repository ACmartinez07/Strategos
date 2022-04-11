function movePaleta(nombreDiv, coordX, coordY) 
{	
	var theDiv;
	var d=document;

	if(d.all) 
		theDiv=d.all[nombreDiv].style;
	else if (d.layers) 
		theDiv=d.layers[nombreDiv];
	else 
		theDiv=d.getElementById(nombreDiv).style;
	theDiv.left= parseInt(coordX) + 10;
	theDiv.top= parseInt(coordY) + 25;
}

function showPaleta(objDiv, txtColor, hideColor, strHideColor, intPath) 
{
	showPaletaFull(objDiv, txtColor, hideColor, strHideColor, intPath, -1, -1);
}

function showPaletaFull(objDiv, txtColor, hideColor, strHideColor, intPath, coordX, coordY, cerrar) 
{
	if ((coordX != -1) && (coordY != -1)) 
	{
		var theDiv;
		var d=document;

		if(d.all) 
			theDiv=d.all[objDiv.id].style;
		else if (d.layers) 
			theDiv=d.layers[objDiv.id];
		else 
			theDiv=d.getElementById(objDiv.id).style;
		theDiv.left= parseInt(coordX) + 10;
		theDiv.top= parseInt(coordY) + 25;
	}

    var strCerrar = "";
    
    if (cerrar == null) 
       strCerrar = "<img src=\"" + intPath + "bregresar.gif\" border=0 >";
    else 
       strCerrar = cerrar;

	var strBuffer = "<table cellspacing=\"0\" border=\"0\" cellspacing=\"0\" style=\"background-color:#FFFFFF\">\n"+
		"	<tr><td align=\"center\"><font face=\"Arial\">&nbsp;</font><br><br>\n"+
		"	</tr></td>\n"+
		"	<tr><td>\n"+
		"		<img src=\"" + intPath + "colorwheel.jpg\" border=\"0\" onclick=\"javascript:moved('" + objDiv.id + "', " + txtColor + ", " + hideColor + ", " + strHideColor + ");\">\n"+
		"	</td></tr>\n"+
		"	<tr><td align=\"center\" ><br><a  onMouseOver=\"this.className='mouseEncimaBarraAplicacionPrincipal'\""+
		"		onMouseOut=\"this.className='mouseFueraBarraAplicacionPrincipal'\""+
		"		class=\"mouseFueraBarraAplicacionPrincipal\" href=\"javascript:disappearColorWheel('" + objDiv.id + "')\" >\n"+
		"		" + strCerrar + "</a>\n"+
		"	</td></tr>\n"+
		"</table><br>\n";

	objDiv.innerHTML = strBuffer;
	objDiv.style.display='';
}


function carga() 
{
	if(navigator.userAgent.indexOf("MSIE")>=0) 
		navegador=0; // IE
	else 
		navegador=1; // Otros
}

window.onload=carga;
