<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (29/10/2012) -->
<script type="text/javascript">
	var barraProgresoIntervaloActualizacion = 1;
	
	function setupBarraProgresoGrafica() 
	{
	    var paneles = new Array();
		paneles['barraProgresoGraf'] = new Array();
		var container = document.getElementById('barraProgresoGraf');
		var paneList = container.childNodes;
		for (var i=0; i < paneList.length; i++ ) 
		{
			var pane = paneList[i];
		    if (pane.nodeType != 1) continue;
		    paneles['barraProgresoGraf'][pane.id] = pane;
		    pane.style.display = "none";
		}
	}
</script>

<div id="panelBarraProgreso" style="position:absolute; left:0px; top:0px; width: 100%; height: 100%; display:none; z-index:50">
	<table style="width: 100%; height: 100%; background-image: url(<html:rewrite page='/componentes/mensajes/rejillaFondo.gif' />);" >
		<tr>
			<td style="width: 470px; height: 220px; text-align: center; vertical-align: middle;">
				<table id="tablaBarraProgreso" style="width: 470px; height: 220px;">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td style="width: 470px; height: 220px;">
							<div id="barraProgresoGraf" style="position:relative; width: 470px; height:40px">
								<div id="barra1" style="position:absolute; width:21px; height:21px; z-index:1; left: 1px; top: 10px">
									<img src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>">
								</div>
								<div id="barra2"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 22px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra3"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 43px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra4"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 64px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra5"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 85px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra6"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 106px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra7"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 127px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra8"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 148px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra9"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 169px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra10"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 190px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra11"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 211px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra12"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 232px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra13"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 253px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra14"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 274px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra15"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 295px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra16"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 216px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra17"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 237px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra18"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 258px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra19"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 279px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra20"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 300px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra21"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 321px; top: 10px"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra22"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 342px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra23"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 363px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra24"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 384px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra25"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 405px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra26"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 426px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra27"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 447px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra28"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 468px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra29"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 489px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra30"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 510px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra31"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 531px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra32"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 552px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barra33"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 573px; top: 10px;"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena1"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 1px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena2"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 22px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena3"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 43px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena4"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 64px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena5"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 85px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena6"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 106px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena7"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 127px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena8"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 148px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena9"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 169px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena10"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 190px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena11"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 211px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena12"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 232px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena13"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 253px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena14"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 274px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena15"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 295px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena16"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 216px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena17"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 237px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena18"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 258px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena19"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 279px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena20"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 300px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena21"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 321px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena22"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 342px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena23"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 363px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena24"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 384px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena25"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 405px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena26"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 426px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena27"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 447px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena28"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 468px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena29"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 489px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena30"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 510px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena31"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 531px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena32"
									style="position:absolute; width:21px; height:21px; z-index:1; left: 552px; top: 10px; display:none"><img
									src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>"></div>
								<div id="barrallena33" style="position:absolute; width:21px; height:21px; z-index:1; left: 573px; top: 10px; display:none">
									<img src="<html:rewrite page='/componentes/mensajes/progreso.gif'/>">
								</div>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	setupBarraProgresoGrafica();
</script>
