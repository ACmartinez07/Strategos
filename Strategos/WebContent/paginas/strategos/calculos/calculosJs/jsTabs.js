/*
 * jsTabs.js 1.0.0
 * 08/12/2005
 *
 * Funciones de Javascript para el manejo de tabs en fichas de datos
 *
 *
 */


function selectTab(id,tabs,panel)
{
	var tab = document.getElementById(id);
	tabs = document.getElementById(tabs);//"tabs");
	for (var i=0;i<tabs.cells.length;i++)
	{
		tabs.cells[i].className="tab";
	}
	tab.className="currentTab";
	

	panel = document.getElementById(panel);//"tabPanel");
	var div =  document.getElementById("data_" + id);
	panel.innerHTML = div.innerHTML;
}

var panes = new Array();
	
function setupPanes(containerId){
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

function showPane(paneId, tabsId, tabId) {

	if (tabsId != null) {
		if (tabsId != '') {
			// marcando la pestana
			var tab = document.getElementById(tabId);
			var tabs = document.getElementById(tabsId);

			for (var i=0;i<tabs.cells.length;i++) {
				tabs.cells[i].className="tab";
			}
			tab.className="currentTab";
		}
	}

	// mostrando el panel seleccionado
	for (var con in panes) {
		if (panes[con][paneId] != null) {
			var pane = document.getElementById(paneId);
			pane.style.display = "block";
			for (var i in panes[con]) {
				var pane = panes[con][i];
				if (pane == undefined) continue;
				if (pane.id == paneId) continue;
				pane.style.display = "none";
			}
		}
	}
	return false;
}

