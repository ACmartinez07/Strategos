// JavaScript Document
// Revisado por: Kerwin Arias (21/10/2012)

var longIcono = 20;
var iniTop = 0;
var iniLeft = 0;
var tempTop = 0;
var pathImages = "";
var bgSelected = "#3399FF";
var fgSelected = "#FFFFFF";
/* Estilo definido para los nodos seleccionados */
var styleSelected = "treeViewSeleccionado";

var SEPARATOR_TOKENS = "?";
var openedLevels = SEPARATOR_TOKENS;

var CONNECTOR_T = pathImages + "empalme.gif";
var CONNECTOR_T_MAS = pathImages + "masempal.gif";
var CONNECTOR_T_MENOS = pathImages + "menosempal.gif";
var CONNECTOR_L = pathImages + "terminal.gif";
var CONNECTOR_L_MAS = pathImages + "masterm.gif";
var CONNECTOR_L_MENOS = pathImages + "menosterm.gif";

var NODE_NORMAL = pathImages + "nodo.gif";
var NODE_EXPAND = pathImages + "nodoexp.gif";
var ROOT_NORMAL = pathImages + "topeini.gif";
var ROOT_EXPAND = pathImages + "topeexp.gif";

var SELECTOR_NODE = pathImages + "arrow_blue.gif";
var VERTICAL_LINE = pathImages + "vert.gif";

function setImgNodeExpand(img_nodo_expand) 
{
	NODE_EXPAND = pathImages + img_nodo_expand;
}

function setImgNode(img_nodo) 
{
	NODE_NORMAL = pathImages + img_nodo;
}

function setFgSelected(fg_selected) 
{
	fgSelected = fg_selected;
}

function setBgSelected(bg_selected) 
{
	bgSelected = bg_selected;
}

function setPathImages(path_images) 
{
	pathImages = path_images;

	CONNECTOR_T = pathImages + "empalme.gif";
	CONNECTOR_T_MAS = pathImages + "masempal.gif";
	CONNECTOR_T_MENOS = pathImages + "menosempal.gif";
	CONNECTOR_L = pathImages + "terminal.gif";
	CONNECTOR_L_MAS = pathImages + "masterm.gif";
	CONNECTOR_L_MENOS = pathImages + "menosterm.gif";
	
	NODE_NORMAL = pathImages + "nodo.gif";
	NODE_EXPAND = pathImages + "nodoexp.gif";
	ROOT_NORMAL = pathImages + "topeini.gif";
	ROOT_EXPAND = pathImages + "topeexp.gif";
	
	SELECTOR_NODE = pathImages + "arrow_gris.gif";
	VERTICAL_LINE = pathImages + "vert.gif";
}

function setInicialTop(top) 
{
	iniTop = top;
}

function setInicialLeft(left) 
{
	iniLeft = left;
}

function selectNode(abierto, es_raiz, tieneHijos) 
{
	var nodeSelected = "";

	if (es_raiz) 
	{
		if (abierto) 
			nodeSelected = ROOT_EXPAND;
		else 
			nodeSelected = ROOT_NORMAL;
	} 
	else 
	{
		if (abierto) 
		{
			if (tieneHijos) 
				nodeSelected = NODE_EXPAND;
			else 
				nodeSelected = NODE_NORMAL;
		} 
		else 
			nodeSelected = NODE_NORMAL;
	}
	
	return nodeSelected;
}

function selectConnector(tiene_hijos, abierto, es_nodo_final, es_raiz) 
{
	var connectorSelected = "";

   	if (! es_raiz) 
   	{
		if (tiene_hijos) 
		{
			if (abierto) 
			{
				if (es_nodo_final) 
					connectorSelected = CONNECTOR_L_MENOS;
				else 
					connectorSelected = CONNECTOR_T_MENOS;
			} 
			else 
			{
				if (es_nodo_final) 
					connectorSelected = CONNECTOR_L_MAS;
				else 
					connectorSelected = CONNECTOR_T_MAS;
			}
		} 
		else 
		{
			if (es_nodo_final) 
				connectorSelected = CONNECTOR_L;
			else 
				connectorSelected = CONNECTOR_T;
		}
	}

	return connectorSelected;
}

function setTop() 
{
	if (tempTop == 0) 
		tempTop = iniTop;
	else 
		tempTop = parseInt(tempTop) + longIcono;
}

function addLevel(nivel) 
{
	if (! isOpenedLevel(nivel)) 
		openedLevels = openedLevels + nivel + SEPARATOR_TOKENS;
}

function removeLevel(nivel) 
{
	var tempStrIni, tempStrFin;
	var posToken = openedLevels.indexOf(SEPARATOR_TOKENS + nivel + SEPARATOR_TOKENS);

	if (posToken > -1) 
	{
		var lenToken = (SEPARATOR_TOKENS + nivel).length;

		tempStrIni = openedLevels.substring(0, posToken);
		tempStrFin = openedLevels.substring(posToken + lenToken);
		openedLevels = (tempStrIni + tempStrFin);
	}
}

function isOpenedLevel(nivel) 
{
	var posToken = openedLevels.indexOf(SEPARATOR_TOKENS + nivel + SEPARATOR_TOKENS);
	return (posToken > -1);
}

function drawOpenedNodeLines(nivel) 
{
	var i;
	var strBuffer = "";
	var VerticalLineLeft = 0;

	for (i = 1 ; i < nivel ; i++) 
	{
		if (isOpenedLevel(i)) 
		{
			VerticalLineLeft = parseInt(iniLeft) + ( ( parseInt(i) - 1 ) * longIcono);
			strBuffer = strBuffer + '<div  style="position:absolute; width:' + longIcono + 'px; height:' + longIcono + 'px;  left: ' + VerticalLineLeft + 'px; top: ' + tempTop + 'px;"><img border="0" src="' + VERTICAL_LINE + '" width="' + longIcono + '" height="' + longIcono + '"></div>';
		}
	}

	document.write(strBuffer);
}

function replaceStr(str, expr, reemp) 
{
	pos = str.indexOf(expr);
	if (pos > -1) 
	{
		len = expr.length;
		str = str.substring(0, pos) + reemp + str.substring(pos + len, str.length);
	}
	return str;
}

function addNode(nombre, id, nivel, tieneHijos, abierto, esNodoFinal,
			esRaiz, seleccionado, customizedImgNode, toolTipImg1, customizedImg2Node, toolTipImg2, customizedPreviousImgNode, 
			viewCheckbox, checkboxChecked, checkboxName, checkboxOnClick, 
			linkConnectorOpen, linkConnectorClose, linkNombre, useAnchor, styleClass, lblUrlAnchor) 
{
	var strBuffer = "";
	var iniLink = "";
	var finLink = "";
	var imgConnector = "";
	var imgNode = "";
	var previousImgNode = "";
	var imgSelectorNode = "";
	var tempLeft = 0;
	var nombreAnclaSeleccionado = "anclaNodo";
	var iniAnclaSeleccionado = '<a name="' + nombreAnclaSeleccionado + '">';
	var finAnclaSeleccionado = '</a>';

	tempLeft = parseInt(iniLeft) + ( ( parseInt(nivel) - 1 ) * longIcono);
	setTop();

	if (abierto) 
		addLevel(nivel);
	if (esNodoFinal) 
		removeLevel(nivel);

	imgConnector = selectConnector(tieneHijos, abierto, esNodoFinal, esRaiz);

	if (customizedImgNode != '') 
	{
		imgNode = customizedImgNode;
		if (imgNode.indexOf('imgNodo') == 0) 
			imgNode = pathImages + imgNode; // Para los nodos que usan imagenes propias
	} 
	else 
		imgNode = selectNode(abierto, esRaiz, tieneHijos);

	if (customizedPreviousImgNode != '') 
		previousImgNode = customizedPreviousImgNode; 

	imgSelectorNode = SELECTOR_NODE;

    if (!esRaiz) 
    {
		if (linkConnectorOpen != "") 
		{
			linkConnector = '';
	        if (lblUrlAnchor != null) 
	        {
				if (abierto) 
				{
					if (linkConnectorClose == '') 
	              		linkConnector = replaceStr(linkConnectorOpen, lblUrlAnchor, "\'&oanclasel=#" + nombreAnclaSeleccionado + "\'" );
					else 
	              		linkConnector = replaceStr(linkConnectorClose, lblUrlAnchor, "\'&oanclasel=#" + nombreAnclaSeleccionado + "\'" );
	            } 
				else 
	              	linkConnector = replaceStr(linkConnectorOpen, lblUrlAnchor, "\'&canclasel=#" + nombreAnclaSeleccionado + "\'" );
	        }

			iniLink = '<a class="' + styleClass + '" href="' + linkConnector;
			iniLink = iniLink + '" >';
			finLink = '</a>'; 		
		}

		strBuffer = strBuffer + '<div class="' + styleClass + '" style="position:absolute; width:' + 
							longIcono + 'px; height:' + longIcono + 'px;  left: ' + tempLeft + 'px; top: ' + 
							tempTop + 'px; ">' + '\n' + iniLink + '\n' + '<img border="0" src="' + 
							imgConnector + '" width="' + longIcono + '" height="' + longIcono + '">' + '\n' + finLink + '\n' + '</div>' + '\n';
		tempLeft = parseInt(tempLeft) + parseInt(longIcono);
	}

	strBuffer = strBuffer + '<div class="' + styleClass + '"  style="position:absolute; width:' + 
						longIcono + 'px; height:' + longIcono + 'px;  left: ' + tempLeft + 
						'px; top: ' + tempTop + 'px;">' + '\n';

	if (imgNode != '')
	{
		strBuffer = strBuffer + '<img border="0" src="' + imgNode + 
							'" width="' + longIcono + '" height="' + longIcono + '"' + 
							(((typeof toolTipImg1 !== 'undefined') && toolTipImg1 != '') ? (' title="' + toolTipImg1 + '"') : '') + '>' + '\n';
	}
	
	strBuffer = strBuffer + '</div>' + '\n';

	if (customizedImg2Node != '') 
	{
		var imgNode2 = '';
		tempLeft = parseInt(tempLeft) + parseInt(longIcono) - 1;
		strBuffer = strBuffer + '<div class="' + styleClass + '"  style="position:absolute; width:' + 
					longIcono + 'px; height:' + longIcono + 'px;  left: ' + tempLeft + 
					'px; top: ' + tempTop + 'px;">' + '\n';

		imgNode2 = customizedImg2Node;
		if (imgNode2.indexOf('imgNodo') == 0) 
			imgNode2 = pathImages + imgNode2; // Para los nodos que usan imagenes propias

		strBuffer = strBuffer + '<img border="0" src="' + imgNode2 + 
							'" width="' + longIcono + '" height="' + longIcono + '"' + 
							(((typeof toolTipImg2 !== 'undefined') && toolTipImg2 != '') ? (' title="' + toolTipImg2 + '"') : '') + '>' + '\n';
		
		strBuffer = strBuffer + '</div>' + '\n';
	} 
	
	if (viewCheckbox) 
	{
		if (checkboxChecked) 
			checked = 'checked';
		else 
			checked = '';

		tempLeft = parseInt(tempLeft) + parseInt(longIcono) + 4;
		strBuffer = strBuffer + '<div class="' + styleClass + '" style="position:absolute; width:' + longIcono + 'px; height:' + longIcono + 
								'px;  left: ' + tempLeft + 'px; top: ' + tempTop + 'px;">' +
								'<input type="checkbox" name="' + checkboxName + '" id="checkbox_' + id + '" value="' + id + '" onClick="' + checkboxOnClick + '" ' + checked + ' ></div>';
	}

	if (previousImgNode != '') 
	{
		tempLeft = parseInt(tempLeft) + parseInt(longIcono) + 4; 
		strBuffer = strBuffer + '<div class="' + styleClass + '" style="position:absolute; width:' + 
								longIcono + 'px; height:' + longIcono + 'px;  left: ' + tempLeft + 
								'px; top: ' + tempTop + 'px;">' + '\n' + '<img border="0" src="' + 
								previousImgNode + '" width="' + longIcono + '" height="' + longIcono + '">' + '\n' + '</div>';
	}

	iniLink = "";
	finLink = "";

	if (linkNombre != "") 
    {
		if (lblUrlAnchor != null) 
            linkNombre = replaceStr(linkNombre, lblUrlAnchor, "\'&sanclasel=#" + nombreAnclaSeleccionado + "\'" );
    	 
    	iniLink = '<a onMouseOver="this.className=\'mouseEncimaCuerpoTreeView\'" onMouseOut="this.className=\'mouseFueraCuerpoTreeView\'" class="mouseFueraCuerpoTreeView" href="' + linkNombre;
    	iniLink = iniLink + '">';
    	finLink = '</a>';
    }

	tempLeft = parseInt(tempLeft) + parseInt(longIcono) + 6;
	strBuffer = strBuffer + '<div id="divNodo_' + id + '">' + '\n';
	if (!isHtml(nombre))
		strBuffer = strBuffer + '<input type="hidden" value="' + nombre + '" id="nombreNodo_' + id + '" name="nombreNodo_' + id + '">' + '\n';
	else
		strBuffer = strBuffer + '<input type="hidden" value="undefined" id="nombreNodo_' + id + '" name="nombreNodo_' + id + '">' + '\n';
	strBuffer = strBuffer + '<input type="hidden" value="' + tempLeft + '" id="tempLeftNodo_' + id + '" name="tempLeftNodo_' + id + '">' + '\n';
	strBuffer = strBuffer + '<input type="hidden" value="' + tempTop + '" id="tempTopNodo_' + id + '" name="tempTopNodo_' + id + '">' + '\n';
	if (seleccionado) 
	{
		strBuffer = strBuffer + '<div id="divArrow" class="' + styleClass + '" style="position:absolute; width: 6px; height: 11px; left: ' + tempLeft + 'px; top: ' + 
								(parseInt(tempTop) + 6) + 'px;">' + '\n' + iniAnclaSeleccionado + '\n' + 
								'<img border="0" src="' + imgSelectorNode + '" width="6" height="11">' + '\n' + finAnclaSeleccionado + '\n';
		strBuffer = strBuffer + '<input type="hidden" value="' + id + '" id="arrowNodoId" name="arrowNodoId">' + '\n';
		strBuffer = strBuffer + '</div>';
		tempLeft = parseInt(tempLeft) + 14;
	}

	strBuffer = strBuffer + '<div id="divNombre_' + id + '" class="' + styleClass + '" style="position:absolute; width:' + (parseInt(longIcono) +  parseInt(nombre.length*8) ) + 
				'px; height:' + longIcono + 'px; left: ' + tempLeft + 'px; top: ' + (parseInt(tempTop) + 3) + 'px; ">' + '\n'; 
	if (seleccionado) 
		strBuffer = strBuffer + '<table class="' + styleClass + '">' + '\n' + '<tr>' + '\n' + '<td class= "' + styleSelected + '" bgcolor="' + bgSelected + '">' + 
							nombre + '</td>' + '\n' + '</tr>' + '\n' + '</table>';
	else 
		strBuffer = strBuffer + iniLink + '\n' + nombre + '\n' + finLink;
	strBuffer = strBuffer + '\n' + '</div>';
	strBuffer = strBuffer + '\n' + '</div>';

	drawOpenedNodeLines(nivel);
	document.write(strBuffer);
}
