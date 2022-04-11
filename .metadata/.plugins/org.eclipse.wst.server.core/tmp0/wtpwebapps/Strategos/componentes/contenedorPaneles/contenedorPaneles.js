/*
 * paneles.js 1.0.0
 * 
 * Creado por: Kerwin Arias (29/05/2012)
 */


function seleccionarPanel(id, tabs, panel) {
	var tab = document.getElementById(id);
	var tabs = document.getElementById(tabs);
	for (i=0;i<tabs.cells.length;i++) {
		tabs.cells[i].className="tabPestana";
	}
	tab.className="tabPestanaSeleccionada";

	var panel = document.getElementById(panel);
	var div =  document.getElementById("data_" + id);
	panel.innerHTML = div.innerHTML;
}


function configurarContenedorPaneles(panes, containerId) {
	panes[containerId] = new Array();
	var container = document.getElementById(containerId);
	var paneList = container.childNodes;
	for (var i=0; i < paneList.length; i++ ) {
		var pane = paneList[i];
	    if (pane.nodeType != 1) continue;
	    panes[containerId][pane.id] = pane;
	    pane.style.display = "none";
	}
}

function mostrarPanelContenedorPaneles(panes, paneId, tabsId, tabId) {
	if (tabsId != null) {
	    // Se marca el selector de panel del panel seleccionado
		if (tabsId != '') {
			var tab = document.getElementById(tabId);
			var tabs = document.getElementById(tabsId);
			if (tabs != null) {
				// Solo cuando el contenedor de paneles muestra los selectores de paneles
				for (i = 0; i < tabs.cells.length; i++) {
					if (tabs.cells[i].id.indexOf("contenedorPanelesEspacioBlanco") >= 0) {
						tabs.cells[i].className="tabPestanaBlanco";
					} else {
						tabs.cells[i].className="tabPestana";
					}
				}
			} else {
				var indice = 1;
				var tabs = document.getElementById(tabsId + indice);
				while (tabs != null) {
					// Solo cuando el contenedor de paneles muestra los selectores de paneles
					for (i = 0; i < tabs.cells.length; i++) {
						if (tabs.cells[i].id.indexOf("contenedorPanelesEspacioBlanco") >= 0) {
							tabs.cells[i].className="tabPestanaBlanco";
						} else {
							tabs.cells[i].className="tabPestana";
						}
					}
					indice++;
					tabs = document.getElementById(tabsId + indice);
				}
			}
			if (tab != null) {
				// Solo cuando el contenedor de paneles muestra los selectores de paneles
				tab.className="tabPestanaSeleccionada";
			}
		}
	}
	// Se muestra el panel seleccionado
	for (var con in panes) {
		if (panes[con][paneId] != null) {
			var pane = document.getElementById(paneId);

			pane.style.display = "block";
			for (var i in panes[con]) {
				var pane = panes[con][i];
				if (pane == undefined) continue;
				if (pane.id == paneId) continue;
				pane.style.display = "none"
			}
		}
	}
	return false;
}
