<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<bean:define toScope="page" id="hayEstilo" value="false"></bean:define>
<bean:define toScope="page" id="estiloSuperior" value=""></bean:define>
<%
	com.visiongc.framework.web.struts.forms.NavegadorForm estilos = new com.visiongc.framework.web.struts.actions.WelcomeAction().checkEstilo();
	if (estilos != null)
	{
		hayEstilo = estilos.getHayConfiguracion().toString().toLowerCase();
		estiloSuperior = estilos.getEstiloSuperiorForma();
	}
	else
		hayEstilo = "false";
%>
<bean:define toScope="page" id="hayEstilo" value="<%=hayEstilo%>"></bean:define>
<logic:equal scope="page" name="hayEstilo" value="true">
	<bean:define toScope="page" id="estiloSuperior" value="<%=estiloSuperior%>"></bean:define>
</logic:equal>
<script type="text/javascript">
	function mostrarCalendarioConFuncionCierre(nombreCampo, strValorFecha, formato, funcionCierre, verificarVentanaModal)
	{
		return mostrarCalendarioCompleto(nombreCampo, strValorFecha, formato, funcionCierre, verificarVentanaModal);
	}
	
	function mostrarCalendario(nombreCampo, strValorFecha, formato, verificarVentanaModal) 
	{
		return mostrarCalendarioCompleto(nombreCampo, strValorFecha, formato, null, verificarVentanaModal);
	}
	
	function mostrarCalendarioCompleto(nombreCampo, strValorFecha, formato, funcionCierre, verificarVentanaModal) 
	{
		var parametroFuncionCierre = "null";
		if (funcionCierre != null) 
		{
			parametroFuncionCierre = "'" + funcionCierre + "'";
			funcionCierre = "window.opener." + funcionCierre + "; ";
		} 
		else 
			funcionCierre = "";

		var _info = navigator.userAgent;
		var _ns;
		//var _ns6;
		var _ie;
	
		var arregloMeses = ['<vgcutil:message key="mes.enero" />', '<vgcutil:message key="mes.febrero" />', '<vgcutil:message key="mes.marzo" />', '<vgcutil:message key="mes.abril" />', '<vgcutil:message key="mes.mayo" />', '<vgcutil:message key="mes.junio" />', '<vgcutil:message key="mes.julio" />', '<vgcutil:message key="mes.agosto" />', '<vgcutil:message key="mes.septiembre" />', '<vgcutil:message key="mes.octubre" />', '<vgcutil:message key="mes.noviembre" />', '<vgcutil:message key="mes.diciembre" />'];
		var arreglosDiasSemana = ['<vgcutil:message key="dia.abreviado.domingo" />', '<vgcutil:message key="dia.abreviado.lunes" />', '<vgcutil:message key="dia.abreviado.martes" />', '<vgcutil:message key="dia.abreviado.miercoles" />', '<vgcutil:message key="dia.abreviado.jueves" />', '<vgcutil:message key="dia.abreviado.viernes" />', '<vgcutil:message key="dia.abreviado.sabado" />'];
		var comienzoSemana = 1; // day week starts from (normally 0 or 1)
	
		var dtFecha = (strValorFecha == null || strValorFecha == "" ? new Date() : stringToDate(strValorFecha, formato));
		if (dtFecha == null)
			return
	
		var dtMesPrevio = new Date(dtFecha);
		dtMesPrevio.setMonth(dtFecha.getMonth() - 1);
		
		var dtMesProximo = new Date(dtFecha);
		dtMesProximo.setMonth(dtFecha.getMonth() + 1);
		
		var dtPrimerDia = new Date(dtFecha);
		dtPrimerDia.setDate(1);
		dtPrimerDia.setDate(1 - (7 + dtPrimerDia.getDay() - comienzoSemana) % 7);
		
		var dtAnoPrevio = new Date(dtFecha);
		var dtAnoProximo = new Date(dtFecha);
	
		_ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
		_ns = (navigator.appName.indexOf("Netscape") >= 0 && ((_info.indexOf("Win") > 0 && _info.indexOf("Win16") < 0) || (_info.indexOf("Sun") > 0) || (_info.indexOf("Linux") > 0) || (_info.indexOf("AIX") > 0) || (_info.indexOf("OS/2") > 0)));
		//_ns6 = ((_ns == true) && (_info.indexOf("Mozilla/5") >= 0));
	
		if (_ie == true) 
		{
			if (dtFecha.getYear() < 1900) 
				dtAnoPrevio.setYear(dtFecha.getYear() - 1 + 1900);
			else 
				dtAnoPrevio.setYear(dtFecha.getYear() - 1);
		}
	
		if (_ie == true) 
		{
			if (dtFecha.getYear() < 1900) 
				dtAnoProximo.setYear(dtFecha.getYear() + 1 + 1900);
			else 
				dtAnoProximo.setYear(dtFecha.getYear() + 1);
		}
	
		if (_ns == true) 
			dtAnoPrevio.setYear(dtFecha.getYear() - 1 + 1900);
		
		if (_ns == true) 
			dtAnoProximo.setYear(dtFecha.getYear() + 1 + 1900);
	
		// Encabezado del calendario
		var strHtml = 	"<html>\n" +
						"	<head>\n" +
						"		<title><vgcutil:message key="jsp.calendario.titulo" /></title>\n" +
						"		<link rel=\"stylesheet\" type=\"text/css\" href=\"<html:rewrite page='/componentes/estilos/estilos.css'/>\">" +
						"	</head>\n" +
						"		<body leftmargin=\"0\" topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\">\n" +
						"			<table class=\"tablaCalendario\" cellspacing=\"0\" border=\"0\" width=\"100%\" height=\"100%\">\n" +
						"				<tr>\n" +
						"					<td>\n" +
						"    					<table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" width=\"100%\" height=\"100%\">\n" +
						"      						<tr>\n" +
						"        						<td class=\"encabezadoMesCalendario\" align=\"center\" valign=\"middle\" ><a class=\"encabezadoMesCalendario\" title=\"<vgcutil:message key='jsp.calendario.mesprevio' />\" href=\"javascript:window.opener.mostrarCalendarioCompleto('" + nombreCampo + "', '" + dateToStringDate(dtMesPrevio, formato) + "', '" + formato + "', " + parametroFuncionCierre + "," + verificarVentanaModal + ");\">&lt;</a></td>\n" +
						"        						<td class=\"encabezadoMesCalendario\" colspan=\"5\" align=\"center\" ><font color=\"white\">" + arregloMeses[dtFecha.getMonth()] + "</font></td>\n"+
						"        						<td class=\"encabezadoMesCalendario\" align=\"center\" valign=\"middle\"><a class=\"encabezadoMesCalendario\" title=\"<vgcutil:message key='jsp.calendario.mesproximo' />\" href=\"javascript:window.opener.mostrarCalendarioCompleto('" + nombreCampo + "', '" + dateToStringDate(dtMesProximo, formato) + "', '" + formato + "', " + parametroFuncionCierre + "," + verificarVentanaModal + ");\">&gt;</a></td>\n" +
						"      						</tr>\n" +
						"      						<tr>\n" +
						"        						<td class=\"encabezadoAnoCalendario\" align=\"center\" valign=\"middle\"><a class=\"encabezadoMesCalendario\" title=\"<vgcutil:message key='jsp.calendario.anoprevio' />\" href=\"javascript:window.opener.mostrarCalendarioCompleto('" + nombreCampo + "', '" + dateToStringDate(dtAnoPrevio, formato) + "', '" + formato + "', " + parametroFuncionCierre + "," + verificarVentanaModal + ");\">&lt;</a></td>\n" +
						"        						<td class=\"encabezadoAnoCalendario\" colspan=\"5\" align=\"center\" ><font color=\"white\">" + dtFecha.getFullYear() + "</font></td>\n" +
						"        						<td class=\"encabezadoAnoCalendario\" align=\"center\" valign=\"middle\"><a class=\"encabezadoMesCalendario\" title=\"<vgcutil:message key='jsp.calendario.anoproximo' />\" href=\"javascript:window.opener.mostrarCalendarioCompleto('" + nombreCampo + "', '" + dateToStringDate(dtAnoProximo, formato) + "', '" + formato + "', " + parametroFuncionCierre + "," + verificarVentanaModal + ");\">&gt;</a></td>\n" + 
						"							</tr>\n";
	
		var dtDiaMes = new Date(dtPrimerDia);
		// Días de una semana
		strHtml += "							<tr>\n";
		for (var n=0; n<7; n++)
			strHtml += "								<td class=\"encabezadoDiaSemanaCalendario\" align=\"center\">" + arreglosDiasSemana[(comienzoSemana+n)%7] + "</td>\n";
		
		// Tabla del calendario
		strHtml += "							</tr>\n";
		while (dtDiaMes.getMonth() == dtFecha.getMonth() || dtDiaMes.getMonth() == dtPrimerDia.getMonth()) 
		{
			// Encabezado de la fila
			strHtml += "							<tr>\n";
			for (var nDiaSemana = 0; nDiaSemana < 7; nDiaSemana++) 
			{
				if ((dtDiaMes.getDate() == dtFecha.getDate()) && (dtDiaMes.getMonth() == dtFecha.getMonth()))
					strHtml += "								<td class=\"diaSeleccionadoCalendario\" align=\"center\" valign=\"middle\" >\n"; // Día seleccionado o día actual
				else if ((dtDiaMes.getDay() == 0) || (dtDiaMes.getDay() == 6))
					strHtml += "								<td class=\"finSemanaCalendario\" align=\"center\" valign=\"middle\" >\n"; // Días de fin de semana
				else
					strHtml += "								<td class=\"diaLaborableCalendario\" align=\"center\" valign=\"middle\" >\n"; // Días laborables del mes seleccionado
				
				var fecha = "";
				if (typeof document.getElementById(nombreCampo) != "undefined" && document.getElementById(nombreCampo) != null)
					fecha = "window.opener.document.getElementById('" + nombreCampo + "')";
				else
					fecha = "window.opener." + nombreCampo;
				if (dtDiaMes.getMonth() == dtFecha.getMonth())
					strHtml += "									<a href=\"javascript:" + fecha + ".value='" + dateToStringDate(dtDiaMes, formato) + "'; " + funcionCierre + "window.close();\">" + "<font color=\"black\">"; // Días del mes seleccionado
				else
					strHtml += "									<a href=\"javascript:" + fecha + ".value='" + dateToStringDate(dtDiaMes, formato) + "'; " + funcionCierre + "window.close();\">" + "<font color=\"red\">"; // Días de otros meses

				var strDia = new String(dtDiaMes.getDate());
				if (strDia.length < 2) 
					strDia = '0' + strDia;
				
				strHtml += strDia  + "</font></a>\n";
				strHtml += "								</td>\n";
				dtDiaMes.setDate(dtDiaMes.getDate() + 1);
			}
			// Final de la fila
			strHtml += "							</tr>\n";
		}
		// Final del cuerpo del calendario
		strHtml +=  "        			</table>\n" +
					"    			</td>\n" +
					"  			</tr>\n" +
					"		</table>\n" +
					"	</body>\n" +
					"</html>\n";

		var vWinCal = abrirVentanaModal('', '<vgcutil:message key="jsp.calendario.titulo" />', '270', '215', null, null, verificarVentanaModal);
		vWinCal.opener = self;
		var calc_doc = vWinCal.document;
		calc_doc.write(strHtml);
		calc_doc.close();
		vWinCal.focus();
	
		if (typeof(window.opener._calendario) != "undefined")
			return vWinCal; 
	}
	
	// Funciones de parsing  y formateo de fechas. modify them if you wish other datetime format
	function stringToDate(strValorFecha, formato) 
	{
		var separador = "-";
		if (strValorFecha.indexOf(separador) < 0) 
			separador = "/";

		if (strValorFecha.indexOf(separador) < 0) 
		{
			alert('Separador de Fecha Inválido');
			return null;
		}
		var re_date = /^(\d+)\/(\d+)\/(\d+)\s+(\d+)\:(\d+)\:(\d+)$/;
		if (!re_date.exec(strValorFecha)) 
		{
			re_date = /^(\d+)\-(\d+)\-(\d+)\s+(\d+)\:(\d+)\:(\d+)$/;
			if (!re_date.exec(strValorFecha))
				strValorFecha=strValorFecha+' 00:00:00';
				if (!re_date.exec(strValorFecha)) 
				{
					re_date = /^(\d+)\/(\d+)\/(\d+)\s+(\d+)\:(\d+)\:(\d+)$/;
					if (!re_date.exec(strValorFecha)) 
						return alert("Invalid Datetime format: "+ strValorFecha);
				}
		}
		return (new Date (RegExp.$3, RegExp.$2-1, RegExp.$1, RegExp.$4, RegExp.$5, RegExp.$6));
	}
	
	function dateToStringDate(dtFecha, formato) 
	{
		
		var diaCompleto;
		var mesCompleto;
		var separador = "-";
		if (formato.indexOf(separador) < 0) 
			separador = "/";
	
		diaCompleto = dtFecha.getDate() + '';
		diaCompleto = (diaCompleto.length == 1 ? '0' + diaCompleto : diaCompleto);
		mesCompleto = (dtFecha.getMonth() + 1);
		mesCompleto = mesCompleto + '';
		mesCompleto = (mesCompleto.length == 1 ? '0' + mesCompleto : mesCompleto);
	
		return (new String (diaCompleto + separador + mesCompleto + separador + dtFecha.getFullYear()));
	}
	
	function dt2tmstr(dtFecha) 
	{
		return (new String (dtFecha.getHours() + ":"
				+ dtFecha.getMinutes() + ":"
				+ dtFecha.getSeconds()));
	}
	
	function fechaValida(fecha)
	{
		if (fecha != undefined && fecha.value != "" )
	    {
	        if (!/^\d{2}\/\d{2}\/\d{4}$/.test(fecha.value))
	        {
	            alert("formato de fecha no válido (dd/mm/aaaa)");
	            return false;
	        }
	        var dia  =  parseInt(fecha.value.substring(0,2),10);
	        var mes  =  parseInt(fecha.value.substring(3,5),10);
	        var anio =  parseInt(fecha.value.substring(6),10);
	 
		    switch(mes)
		    {
		        case 1:
		        case 3:
		        case 5:
		        case 7:
		        case 8:
		        case 10:
		        case 12:
		            numDias=31;
		            break;
		        case 4: case 6: case 9: case 11:
		            numDias=30;
		            break;
		        case 2:
		            if (comprobarBisiesto(anio))
		            	numDias=29; 
		            else
		            	numDias=28;
		            break;
		        default:
		            alert("Fecha introducida errónea");
		            return false;
		    }
		 
	        if (dia>numDias || dia==0)
	        {
	            alert("Fecha introducida errónea");
	            return false;
	        }
	        
	        return true;
		}
	}
	
	function fechasRangosValidos(fechaDesde, fechaHasta)
	{
		if ((typeof fechaDesde != "undefined") && (typeof fechaHasta != "undefined") && fechaDesde.value != "" && fechaHasta.value != "")
		{
			var fecha1 = fechaDesde.value.split("/");
			var fecha2 = fechaHasta.value.split("/");

			var diaDesde = fecha1[0];
			var diaHasta = fecha2[0];
			
			var mesDesde = fecha1[1];
			var mesHasta = fecha2[1];
			
			var anoDesde = fecha1[2];
			var anoHasta = fecha2[2];

	 		var desde = parseInt(anoDesde + "" + (mesDesde.length == 1 ? ("0" + mesDesde) : mesDesde) + "" + (diaDesde.length == 1 ? ("0" + diaDesde) : diaDesde));
			var hasta = parseInt(anoHasta + "" + (mesHasta.length == 1 ? ("0" + mesHasta) : mesHasta) + "" + (diaHasta.length == 1 ? ("0" + diaHasta) : diaHasta));

			if (hasta<desde) 
			{
				alert('<vgcutil:message key="jsp.general.alerta.rango.fechas.invalido" /> ');
				return false;
			}
		}
		
		return true;
	}
	
	function ultimoDiaDelMes(mes, ano)
	{
		var fecha = new Date(ano, (mes + 1), 1);
		fecha.setDate(fecha.getDate() - 1);

	    return fecha.getDate();
	}
	
	function comprobarBisiesto(ano)
	{
		if(ano % 4 == 0)
		{
			if ((ano % 100 == 0) && (ano % 400 != 0))
				return false;
			else 
				return true;
		}
		else 
			return false;
	}
	
	function getPeriodoDeFechaDiaria(fecha)
  	{
		var fechaSplit = fecha.split("/");
		var continuar = true;
		var numeroPeriodo = 0;

		var fechaInt = new Date(); 
		var fechaComp = new Date(); 
		var fechaFin = new Date();
		
		fechaInt.setDate(1); 
		fechaInt.setMonth(0); 
		fechaInt.setFullYear(fechaSplit[2]); 		

		fechaComp.setDate(fechaSplit[0]); 
		fechaComp.setMonth(fechaSplit[1]-1); 
		fechaComp.setFullYear(fechaSplit[2]); 		
		
		fechaFin.setDate(31); 
		fechaFin.setMonth(11); 
		fechaFin.setFullYear(fechaSplit[2]); 		

		var fechaControl;
		var periodo = 1;
		while (continuar)
		{
			numeroPeriodo = numeroPeriodo + 1;
			if (fechaInt.getDate() == fechaComp.getDate() && fechaInt.getMonth() == fechaComp.getMonth() && fechaInt.getFullYear() == fechaComp.getFullYear())
				return numeroPeriodo;
			if (fechaInt.getDate() == fechaFin.getDate() && fechaInt.getMonth() == fechaFin.getMonth() && fechaInt.getFullYear() == fechaFin.getFullYear())
				continuar = false;
			
			fechaControl = fechaInt.getTime();
			fechaControl = fechaControl + (1000*60*60*24*periodo); 
			fechaInt.setTime(fechaControl);
		}
		return 0;
	}
</script>
