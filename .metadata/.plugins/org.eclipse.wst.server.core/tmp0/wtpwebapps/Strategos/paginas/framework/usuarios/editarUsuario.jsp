<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (18/08/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">
	<tiles:put name="title" type="String">
		<logic:equal name="editarUsuarioForm" property="usuarioId" value="0">
			<logic:equal name="editarUsuarioForm" property="copiar" value="false">
				<vgcutil:message key="jsp.framework.editarusuario.titulo.nuevo" />
			</logic:equal>
			<logic:notEqual name="editarUsuarioForm" property="copiar" value="false">
				<vgcutil:message key="jsp.framework.editarusuario.titulo.copiar" />
			</logic:notEqual>
		</logic:equal>
		<logic:notEqual name="editarUsuarioForm" property="usuarioId" value="0">
			<vgcutil:message key="jsp.framework.editarusuario.titulo.modificar" />
		</logic:notEqual>
	</tiles:put>

	<tiles:put name="body" type="String">
	
	<style type="text/css">
		.debil 
		{
		  background-color: Red; 
		  color:Black; 
		  font-weight: Bold;
		}
		
		.medio 
		{
		  background-color: Yellow; 
		  color:Black; 
		  font-weight: Bold;
		}
		
		.fuerte 
		{
		  background-color: Green; 
		  color:Black; 
		  font-weight: Bold;
		}
		
		.mouseEncimaCuerpoTreeView 
		{ /* Evento over del cuerpo del tree view */
			background-color:#F3F3F3;
			cursor:pointer;	
			font-family:Verdana;
			font-size:11px;
			color:#666666;
			text-decoration:none;	
		}
		
		.mouseFueraCuerpoTreeView 
		{ /* Evento out del cuerpo del tree view */
			background-color:#FFFFFF;
			color:#666666;
			font-family:Verdana;
			font-size:11px;
			text-decoration:none;	
		}
		
	</style>

	<script type="text/javascript" src="<html:rewrite page='/paginas/comunes/jQuery/timepicker/jquery.timepicker.js'/>"></script>
  	<link rel="stylesheet" type="text/css" href="<html:rewrite page='/paginas/comunes/jQuery/timepicker/css/jquery.timepicker.css'/>" />

	<script type="text/javascript">

	    var todas_org = false;
		var _loading = false;
		var organizacionSeleccionadaId = null;

		function Organizacion()
		{
			this.Id = null;
			this.Seleccionado = false;
		}
		
		function ltrim(str, filter) 
		{
			var i = str.length;
			filter || (filter = '');
			for (var k = 0; k < i && filtering(str.charAt(k), filter); k++);
			return str.substring(k, i);
		}
		
		function rtrim(str, filter) 
		{
			filter || (filter = '');
		  	for (var j = str.length - 1; j >= 0 && filtering(str.charAt(j), filter); j--);          
			return str.substring(0, j + 1);
		}
		
		function trim(str, filter) 
		{
			if (typeof(filter) == "undefined")
				filter = "";
				
			filter || (filter = '');
			return ltrim(rtrim(str, filter), filter);
		}
		
		function filtering(charToCheck, filter) 
		{
			filter || (filter = " \t\n\r\f");
			return (filter.indexOf(charToCheck) != -1);
		}		
		
		function excluirCaracter()
		{
			var patron = /[\"'-,#&]/;
			var patronUser = /[\"',#&]/;
			var respuesta = true;
			if (patronUser.test(document.editarUsuarioForm.UId.value))
				respuesta = false;
			if (patron.test(document.editarUsuarioForm.fullName.value) || document.editarUsuarioForm.fullName.value.indexOf("-") != -1)
				respuesta = false;
			<logic:notEmpty scope="session" name="cliente">
				<logic:equal scope="session" name="cliente" value="BDV">
					if (patron.test(document.editarUsuarioForm.pwd.value) || document.editarUsuarioForm.pwd.value.indexOf("-") != -1)
						respuesta = false;
				</logic:equal>
			</logic:notEmpty>
			if (!respuesta)
				alert("Existe caracteres no permitidos en esta ficha, tales como ' - , # & \"");
			else
			{
				document.editarUsuarioForm.UId.value = document.editarUsuarioForm.UId.value.trim();
				document.editarUsuarioForm.fullName.value = document.editarUsuarioForm.fullName.value.trim();
				var password = document.editarUsuarioForm.pwd.value.replace(" ", "").trim();
				if (password != document.editarUsuarioForm.pwd.value)
				{
					alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdconblanco" />');
					respuesta = false;
				}
			}
			
			return respuesta;
		}

		function validar(forma) 
		{
			<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesEditarUsuario" nombrePanel="panelDatosBasicos" />
			if (validateEditarUsuarioForm(forma) && excluirCaracter()) 
			{
				var password = document.editarUsuarioForm.pwd.value;
				if (password.length < document.editarUsuarioForm.minimoCaracteres.value)
				{
					var mensaje = '<vgcutil:message key="jsp.ingreso.minimocaracteres" />';
					mensaje = mensaje.replace("XXX", document.editarUsuarioForm.minimoCaracteres.value);
					alert(mensaje);
					return false;
				}
				
				if (document.editarUsuarioForm.pwd.value == document.editarUsuarioForm.UId.value)
			  	{
			  		alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.pwdigualuser" />');
			  		return false;
			  	}
				
				if (document.editarUsuarioForm.pwd.value != document.editarUsuarioForm.pwdConfirm.value) 
				{
					alert('<vgcutil:message key="jsp.framework.editarusuario.error.pwdconfirm" />');
					return false;
				}
				
				if (document.editarUsuarioForm.pwd.value.toLowerCase().indexOf("ñ") > -1)
				{
					alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.caracter.nopermitido" />');
					return false;
				}
				
				if (document.editarUsuarioForm.tipoContraIngresada.value < document.editarUsuarioForm.tipoContrasena.value)
			  	{
			  		if (document.editarUsuarioForm.tipoContrasena.value == 0)
						alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.tipocontrasena.debil" />');

			  		if (document.editarUsuarioForm.tipoContrasena.value == 1)
			  			alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.tipocontrasena.medio" />');
			  		
			  		if (document.editarUsuarioForm.tipoContrasena.value == 2)
			  			alert('<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.validation.tipocontrasena.fuerte" />');

			  		return false;
			  	}
				
				if ((document.editarUsuarioForm.grupos.value == '') && (!document.editarUsuarioForm.isAdmin.checked)) 
				{
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="panelesEditarUsuario" nombrePanel="panelPermisos" />
					alert('Debe asociar al menos un grupo de permisos');
					return false;
				}
				
				//rango de horas
				if ((document.editarUsuarioForm.txtRestriccionUsoHoraDesde.value == '') && (document.editarUsuarioForm.txtRestriccionUsoHoraHasta.value != '')) 
				{
					alert('<vgcutil:message key="jsp.framework.iniciosesion.restriccionuso.requerido" />');
					return;
				}
				
				if ((document.editarUsuarioForm.txtRestriccionUsoHoraDesde.value != '') && (document.editarUsuarioForm.txtRestriccionUsoHoraHasta.value == '')) 
				{
					alert('<vgcutil:message key="jsp.framework.iniciosesion.restriccionuso.requerido" />');
					return;
				}

				if (!document.editarUsuarioForm.isAdmin.checked && document.editarUsuarioForm.grupos.length > 0)
				{
					alert('<vgcutil:message key="jsp.framework.usuarios.grupo.validation.nohaygrupo" />');									
					return false;
				}
				
				document.editarUsuarioForm.restriccionUsoHoraDesde.value = document.editarUsuarioForm.txtRestriccionUsoHoraDesde.value;
				document.editarUsuarioForm.restriccionUsoHoraHasta.value = document.editarUsuarioForm.txtRestriccionUsoHoraHasta.value;
				window.document.editarUsuarioForm.action = '<sslext:rewrite  action="/framework/usuarios/guardarUsuario"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
				return true;
			} 
			else 
				return false;
		}
		
		let identificadorTiempoDeEspera;
		
		function cerrarModal(){				
			this.close();
			window.location.reload();
		}
		
		function guardar() 
		{
			if (validar(document.editarUsuarioForm))
			{
				activarBloqueoEspera();
				document.editarUsuarioForm.submit();				
			}
		}
		
		function cancelarGuardar() 
		{
			window.document.editarUsuarioForm.action = '<sslext:rewrite action="/framework/usuarios/cancelarGuardarUsuario"/>';
			window.document.editarUsuarioForm.submit();
		}

		function txtPasswordChanged()
		{
			if (document.editarUsuarioForm.pwd.value != "")
			{
				var passwordStrength = getPasswordStrength(document.editarUsuarioForm.pwd.value);
				if (passwordStrength == "Strong")
				{
					document.getElementById("lblPasswordType").innerHTML = "Fuerte";
					document.getElementById("tdPasswordType").className = "fuerte";
					document.editarUsuarioForm.tipoContraIngresada.value = "2";
				}
				else if (passwordStrength == "Medium")
				{
					document.getElementById("lblPasswordType").innerHTML = "Medio";
					document.getElementById("tdPasswordType").className = "medio";
					document.editarUsuarioForm.tipoContraIngresada.value = "1";
				}
				else if (passwordStrength == "Weak")
				{
					document.getElementById("lblPasswordType").innerHTML = "Débil";
					document.getElementById("tdPasswordType").className = "debil";
					document.editarUsuarioForm.tipoContraIngresada.value = "0";
				}
			}
			else
			{
				document.getElementById("lblPasswordType").innerHTML = "";
				document.getElementById("tdPasswordType").className = "";
				document.editarUsuarioForm.tipoContraIngresada.value = "";
			}
		}
		
		function getPasswordStrength(password)
		{
			var reDigits = /([0-9])/;
			var reLetters = /([A-Z]|[a-z])/;
			var reSymbols = /[^A-Za-z0-9]/;
			var OKDigits = reDigits.test(password);
			var OKLetters = reLetters.test(password);
			var OKSymbols = reSymbols.test(password);
			var Strength = "";

			if (OKDigits && OKLetters && OKSymbols)
				Strength = "Strong";
			else if (OKDigits && OKLetters && !OKSymbols)
				Strength = "Medium";
			else if (OKDigits && !OKLetters && OKSymbols)
				Strength = "Medium";
			else if (!OKDigits && OKLetters && OKSymbols)
				Strength = "Medium";
			else
			  	Strength = "Weak";
			return Strength;
		}
		
		function ejecutarPorDefecto(e, id) 
		{
			if(e.keyCode==13) 
				guardar();
			
			if (id == "pwd")
				txtPasswordChanged();
		}

		// Variables globales de Javascript de manejo de grupos por organización
		var indiceGrupos;
		var indiceOrganizaciones;
		var sepOrg = '<bean:write name="editarUsuarioForm" property="separadorCampos" scope="request"/>';
		var sepGrp = '<bean:write name="editarUsuarioForm" property="separadorFilas" scope="request"/>';
		var fullPathOrganizaciones = '<bean:write name="editarUsuarioFullPathOrganizaciones" scope="session"/>';
		var listaGrupoIds = '';
			
		function addGrupoIdLista(id) 
		{
			listaGrupoIds = listaGrupoIds + id + sepGrp;
		}
		
		function loadIndicesObjeto(myForm, nombreObjeto) 
		{
			var indices = new Array();
			var len = myForm.elements.length;
			for(var index = 0; index < len; index++) 
			{
				if(myForm.elements[index].name == nombreObjeto)
				{
					if (nombreObjeto == 'organizacion')
					{
						var objeto = new Organizacion();
						objeto.Id = myForm.elements[index].value;
						objeto.Seleccionado = myForm.elements[index].checked;
						
						indices.push(objeto);
					}
					else
						indices.push(index);
				}
			}
			
			return indices;
		}
		
		function marcarGrupoPorOrganizaciones(myForm, orgSeleccionadaId, grupoId, gruposSeleccionados) 
		{
			var existeGrupo = true;
			if (gruposSeleccionados.length > 0) 
			{
				var grupos = gruposSeleccionados.split(sepGrp);
				for (var i = 0; i < grupos.length; i++) 
				{
					if (grupos[i].length > 0)
					{
						var campos = grupos[i].split(sepOrg);
						if (campos.length > 0)
						{
							if (campos[0] == orgSeleccionadaId && campos[1] == grupoId)
							{
								existeGrupo = true;
								break;
							}
						}
					}
				}
			}

			len = indiceGrupos.length;
			for(var indexGrupo = 0; indexGrupo < len; indexGrupo++ ) 
			{
				if (myForm.elements[indiceGrupos[indexGrupo]].value == grupoId) 
				{
					if (!existeGrupo) 
					{
						myForm.elements[indiceGrupos[indexGrupo]].checked = false;
						changePropertyObjetoHtml('imgSelected' + grupoId, 'src', '<html:rewrite page="/paginas/framework/usuarios/noneSelected.jpg" />');
					} 
					else 
					{
						myForm.elements[indiceGrupos[indexGrupo]].checked = true;
						changePropertyObjetoHtml('imgSelected' + grupoId, 'src', '<html:rewrite page="/paginas/framework/usuarios/allSelected.jpg" />');
					} 
					break;
				}
			}
		}
		
		// Funcion que carga los grupos seleccionados por organización
		function loadGruposSeleccionados(myForm, fieldGrupos, checkbox, addOrganizacionId) 
		{
			if (checkbox != null) 
			{
				
				if(!checkbox.checked){
					todas_org = false;
				}
				
				if (checkbox.value == '0') 
				{
					checkbox.checked = false;
					return;
				}
			}
			
			if (myForm.isAdmin.checked) 
			{
				if (checkbox != null) 
					checkbox.checked = false;
				return;
			}
		
			// Buscar las organizaciones seleccionadas
			var len = indiceOrganizaciones.length;
			if (_loading)
			{
				var valores = fieldGrupos.value.split(sepGrp);
				for (var i = 0; i < valores.length; i++) 
				{
					if (valores[i].length > 0) 
					{
						var orgGrupo = valores[i].split(sepOrg);
						for (var j = 0; j < orgGrupo.length; j++)
						{
							if (orgGrupo[j].length > 0)
							{
								for(var index = 0; index < len; index++) 
								{
									if (indiceOrganizaciones[index].Id == orgGrupo[j])
									{
										indiceOrganizaciones[index].Seleccionado = true;
										var checkboxOrg = document.getElementById("checkbox_" + indiceOrganizaciones[index].Id); 
										if (checkboxOrg != null)
											checkboxOrg.checked = true;
										if (organizacionSeleccionadaId == null)
											organizacionSeleccionadaId = indiceOrganizaciones[index].Id;
										break;
									}
								}
								break;
							}
						}
					}
				}
				
				if (organizacionSeleccionadaId == null)
					organizacionSeleccionadaId = indiceOrganizaciones[0].Id;
			}
			else if (checkbox != null && !checkbox.checked)
			{
				if (addOrganizacionId)
					organizacionSeleccionadaId = checkbox.value;
				
				if (!addOrganizacionId)
					setGrupos(fieldGrupos, checkbox.value, false);
				else
					setGrupos(fieldGrupos, undefined, false);
				
				var len = indiceOrganizaciones.length;
				for(var index=0; index < len; index++ )
				{
					if (indiceOrganizaciones[index].Id == organizacionSeleccionadaId)
						indiceOrganizaciones[index].Seleccionado = checkbox.checked;
				}
			}
			else if (checkbox != null && addOrganizacionId)
			{
				organizacionSeleccionadaId = checkbox.value;
				var len = indiceOrganizaciones.length;
				for(var index=0; index < len; index++ )
				{
					if (indiceOrganizaciones[index].Id == organizacionSeleccionadaId)
						indiceOrganizaciones[index].Seleccionado = checkbox.checked;
				}
			}
			else if (checkbox != null && checkbox.checked)
			{
				if (addOrganizacionId)
					organizacionSeleccionadaId = checkbox.value;
				
				if (!addOrganizacionId)
					setGrupos(fieldGrupos, checkbox.value, true);
				else
					setGrupos(fieldGrupos, undefined, true);
			}
			
			// Buscar las organizaciones seleccionadas
			if (organizacionSeleccionadaId != null) // Si hay organizaciones seleccionadas 
			{
				if (fieldGrupos.value.length > 0)
				{
					var grupos = fieldGrupos.value.split(sepGrp);
					for (var i = 0; i < grupos.length; i++) 
					{
						if (grupos[i].length > 0)
						{
							var campos = grupos[i].split(sepOrg);
							if (campos.length > 0 && campos[0] == organizacionSeleccionadaId)
								marcarGrupoPorOrganizaciones(myForm, organizacionSeleccionadaId, campos[1], fieldGrupos.value);
						}
					}
				}
				else // Si no hay grupos seleccionados, se desmarcan todos los grupos
				{
					document.editarUsuarioForm.grupos.value = "";
					len = indiceGrupos.length;
					for(var indexGrupo = 0; indexGrupo < len; indexGrupo++ ) 
					{
						myForm.elements[indiceGrupos[indexGrupo]].checked = false;
						changePropertyObjetoHtml('imgSelected' + myForm.elements[indiceGrupos[indexGrupo]].value, 'src', '<html:rewrite page="/paginas/framework/usuarios/noneSelected.jpg" />');
					}
				}
			}
			else 
			{
				// Si no hay organizaciones seleccionadas, se desmarcan todos los grupos
				document.editarUsuarioForm.grupos.value = "";
				len = indiceGrupos.length;
				for(var indexGrupo = 0; indexGrupo < len; indexGrupo++ ) 
				{
					myForm.elements[indiceGrupos[indexGrupo]].checked = false;
					changePropertyObjetoHtml('imgSelected' + myForm.elements[indiceGrupos[indexGrupo]].value, 'src', '<html:rewrite page="/paginas/framework/usuarios/noneSelected.jpg" />');
				}
			}

			
				seleccionarNodo(organizacionSeleccionadaId);
			
		}
		
		// Funcion Asocia los permisos a todos los grupos seleccionados
		function asociarASeleccionados(fieldGrupos, grupo)
		{
			var grupoId = grupo.id;
			grupoId = grupoId.replace(new RegExp ("imgAsociar_", "gi"), "");
			if (!document.getElementById("grupo_" + grupoId).checked)
			{
				document.getElementById("grupo_" + grupoId).checked = true;
				changePropertyObjetoHtml('imgSelected' + grupoId, 'src', '<html:rewrite page="/paginas/framework/usuarios/allSelected.jpg" />');
			}
			var len = indiceOrganizaciones.length;
			for(var index = 0; index < len; index++) 
			{
				var grupos = fieldGrupos.value.split(sepGrp);
				var checkboxOrg = document.getElementById("checkbox_" + indiceOrganizaciones[index].Id); 
				if (checkboxOrg != null && checkboxOrg.checked)
				{
					var organizacionId = indiceOrganizaciones[index].Id;
					var grupoEncontrado = false;
					var valorBuscado = organizacionId + sepOrg + grupoId;
					for (var i = 0; i < grupos.length; i++) 
					{
						if (grupos[i].length > 0)
						{
							if (grupos[i] == valorBuscado)
							{
								grupoEncontrado = true;
								break;
							}
						}
					}

					if (!grupoEncontrado)
					{
						if (fieldGrupos.value.lenght != 0)
							fieldGrupos.value = fieldGrupos.value + sepGrp;
						fieldGrupos.value = fieldGrupos.value + valorBuscado;
					}
				}
			}
		}
		
		// Funcion que carga los grupos seleccionados por organización
		function establecerGrupoSeleccionado(myForm, fieldGrupos, checkbox) 
		{
			if(todas_org == true){
				asignarGruposTodasOrganizaciones(myForm, fieldGrupos, checkbox);
			}else{
			
			// Buscar las organizaciones seleccionadas
			if (organizacionSeleccionadaId == null)
				checkbox.checked = false;
			else
			{
				if (checkbox.checked) // Se ha seleccionado un grupo de permisos 
				{
					var checkboxOrg = document.getElementById("checkbox_" + organizacionSeleccionadaId); 
					if (checkboxOrg != null)
						checkboxOrg.checked = true;
				} 
			}
		
			loadGruposSeleccionados(myForm, fieldGrupos, checkbox, false);
			}
		}
		
		function asignarGruposTodasOrganizaciones(myForm, fieldGrupos, checkbox){
			
						
			var grupoId = checkbox.value;
			
			var organizacionesId = '<bean:write name="editarUsuarioForm" property="organizacionesId" scope="request"/>';
			
			var ids = organizacionesId.split(",");
			
			for (x=0;x<ids.length;x++){
								
				if(ids[x] != ""){
					
					organizacionSeleccionadaId = ids[x];
					
					// Buscar las organizaciones seleccionadas
					if (organizacionSeleccionadaId == null)
						checkbox.checked = false;
					else
					{
						if (checkbox.checked) // Se ha seleccionado un grupo de permisos 
						{
							var checkboxOrg = document.getElementById("checkbox_" + organizacionSeleccionadaId); 
							if (checkboxOrg != null)
								checkboxOrg.checked = true;
						} 
					}
				
					loadGruposSeleccionados(myForm, fieldGrupos, checkbox, false);
				}
			}
			
			
			
		}
		
		function establecerGrupoSeleccionadoImg(objeto)
		{
			var objetoId = objeto.id;
			objetoId = objetoId.replace(new RegExp ("imgSelected", "gi"), "");
			if (objeto.src.indexOf("allSelected.jpg") != -1)
				alert('<vgcutil:message key="jsp.framework.usuarios.grupo.seleccionado" />');
			else
			{
				if (!document.getElementById("grupo_" + objetoId).checked)
					document.getElementById("grupo_" + objetoId).checked = true;
				establecerGrupoSeleccionado(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, document.getElementById("grupo_" + objetoId));
			}
		}
		
		function clickIsAdmin(myForm, fieldGrupos, checkbox) 
		{
			if (checkbox.checked) 
			{
				fieldGrupos.value = "";
				// Buscar las organizaciones seleccionadas
				var len = indiceOrganizaciones.length;
				for(var index=0; index < len; index++ )
				{
					indiceOrganizaciones[index].Seleccionado = false;
					var checkboxOrg = document.getElementById("checkbox_" + indiceOrganizaciones[index].Id); 
					if (checkboxOrg != null)
						checkboxOrg.checked = false;
				}
		
				loadGruposSeleccionados(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, null, false);
			}
		}
		
		// Funcion que carga los grupos seleccionados por organización
		function marcarOrganizaciones(myForm, marcar) 
		{
			if (myForm.isAdmin.checked) 
				return;

			if (!marcar){
				document.editarUsuarioForm.grupos.value = "";
				todas_org = false;
			}else{
				todas_org = true;
			}
			// Buscar las organizaciones seleccionadas
			var len = indiceOrganizaciones.length;
			for(var indice = 0; indice < len; indice++)
			{
				indiceOrganizaciones[indice].Seleccionado = marcar;
				var checkboxOrg = document.getElementById("checkbox_" + indiceOrganizaciones[indice].Id); 
				if (checkboxOrg != null)
					checkboxOrg.checked = marcar;
			}
		
			if(todas_org == false){
				loadGruposSeleccionados(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, null, false);
			}
		}
		
		function limpiarHoraDesde() 
		{
			document.editarUsuarioForm.txtRestriccionUsoHoraDesde.value = '';
		}
		
		function limpiarHoraHasta() 
		{
			document.editarUsuarioForm.txtRestriccionUsoHoraHasta.value = '';
		}
		
		function seleccionarNodo(nodoId) 
		{
					
			
			var idArrow = document.getElementById("arrowNodoId");
			var selecionId = "";
			var selecionNombre = "";
			var tempLeft = "";
			var tempTop = "";
	    	var longIcono = "20";
			if (idArrow != null)
			{
				selecionId = idArrow.value;
				selecionNombre = document.getElementById("nombreNodo_" + selecionId).value;
				tempLeft = document.getElementById("tempLeftNodo_" + selecionId).value;
				tempTop = document.getElementById("tempTopNodo_" + selecionId).value;
				
				var div = document.getElementById("divNodo_" + selecionId);
				if (div != null)
				{
					var linkNombre = "javascript:seleccionarNodo(" + selecionId + ");"; 
					var iniLink = '<a onMouseOver="this.className=\'mouseEncimaCuerpoTreeView\'" onMouseOut="this.className=\'mouseFueraCuerpoTreeView\'" class="mouseFueraCuerpoTreeView" href="' + linkNombre + '">';
			    	var finLink = "</a>";
			    	
			    	var innerHTML = '<input type="hidden" value="' + selecionNombre + '" id="nombreNodo_' + selecionId + '" name="nombreNodo_' + selecionId + '">' + '\n';
			    	innerHTML = innerHTML + '<input type="hidden" value="' + tempLeft + '" id="tempLeftNodo_' + selecionId + '" name="tempLeftNodo_' + selecionId + '">' + '\n';
			    	innerHTML = innerHTML + '<input type="hidden" value="' + tempTop + '" id="tempTopNodo_' + selecionId + '" name="tempTopNodo_' + selecionId + '">' + '\n';
			    	innerHTML = innerHTML + '<div id="divNombre_' + selecionId + '" class="treeview" style="position:absolute; width:' + (parseInt(longIcono) +  parseInt(selecionNombre.length*8) ) + 'px; height:20px;  left: ' + tempLeft + 'px; top: ' + (parseInt(tempTop) + 3) + 'px; ">' + '\n'; 
			    	innerHTML = innerHTML + iniLink + '\n' + selecionNombre + '\n' + finLink;
			    	innerHTML = innerHTML + '\n' + '</div>';
			    	
					div.innerHTML = innerHTML;
				}
			}

			var divSeleccionado = document.getElementById("divNodo_" + nodoId);
			selecionNombre = document.getElementById("nombreNodo_" + nodoId).value;
			tempLeft = document.getElementById("tempLeftNodo_" + nodoId).value;
			tempTop = document.getElementById("tempTopNodo_" + nodoId).value;

	    	var innerHTML = '<input type="hidden" value="' + selecionNombre + '" id="nombreNodo_' + nodoId + '" name="nombreNodo_' + nodoId + '">' + '\n';
	    	innerHTML = innerHTML + '<input type="hidden" value="' + tempLeft + '" id="tempLeftNodo_' + nodoId + '" name="tempLeftNodo_' + nodoId + '">' + '\n';
	    	innerHTML = innerHTML + '<input type="hidden" value="' + tempTop + '" id="tempTopNodo_' + nodoId + '" name="tempTopNodo_' + nodoId + '">' + '\n';

	    	// Dibujar flecha de seleccion
			innerHTML = innerHTML + '<div id="divArrow" class="treeview" style="position:absolute; width: 6px; height: 11px; left: ' + tempLeft + 'px; top: ' + (parseInt(tempTop) + 6) + 'px;">' + '\n';
			innerHTML = innerHTML + '<a name="anclaNodo">' + '\n';
			innerHTML = innerHTML + '<img border="0" src="/Strategos/componentes/visorArbol/arrow_gris.gif" width="6" height="11">' + '\n';
			innerHTML = innerHTML + '</a>' + '\n';
			innerHTML = innerHTML + '<input type="hidden" value="' + nodoId + '" id="arrowNodoId" name="arrowNodoId">' + '\n';
			innerHTML = innerHTML + '</div>';
			tempLeft = parseInt(tempLeft) + 14;

			// Dibujar Seleccion
			innerHTML = innerHTML + '<div id="divNombre_' + nodoId + '" class="treeview" style="position:absolute; width:' + (parseInt(longIcono) +  parseInt(selecionNombre.length*8) ) + 'px; height:' + longIcono + 'px;  left: ' + tempLeft + 'px; top: ' + (parseInt(tempTop) + 3) + 'px; ">' + '\n'; 
			innerHTML = innerHTML + '<table class="treeview">' + '\n'; 
			innerHTML = innerHTML + '<tr>' + '\n';
			innerHTML = innerHTML + '<td class= "treeViewSeleccionado" bgcolor="#3399FF">' + selecionNombre + '</td>' + '\n';
			innerHTML = innerHTML + '</tr>' + '\n';
			innerHTML = innerHTML + '</table>' + '\n';
			innerHTML = innerHTML + '</div>' + '\n';
			
			divSeleccionado.innerHTML = innerHTML;
			
			organizacionSeleccionadaId = nodoId;
			if (listaGrupoIds.length > 0 && document.editarUsuarioForm.grupos.value.length > 0) 
			{
				len = indiceGrupos.length;
				for(var indexGrupo = 0; indexGrupo < len; indexGrupo++ ) 
				{
					document.editarUsuarioForm.elements[indiceGrupos[indexGrupo]].checked = false;					
					changePropertyObjetoHtml('imgSelected' + document.editarUsuarioForm.elements[indiceGrupos[indexGrupo]].value, 'src', '<html:rewrite page="/paginas/framework/usuarios/noneSelected.jpg" />');
				}
				
				var grupos = listaGrupoIds.split(sepGrp);
				var gruposValores = document.editarUsuarioForm.grupos.value.split(sepGrp);
				for (var j = 0; j < gruposValores.length; j++)
				{
					if (gruposValores[j].length > 0)
					{
						var campos = gruposValores[j].split(sepOrg);
						if (campos.length > 0)
						{
							if (organizacionSeleccionadaId == campos[0])
							{
								for (var i = 0; i < grupos.length; i++) 
								{
									if (grupos[i].length > 0)
									{
										if (grupos[i] == campos[1])
										{
											len = indiceGrupos.length;
											for(var indexGrupo = 0; indexGrupo < len; indexGrupo++ ) 
											{
												if (document.editarUsuarioForm.elements[indiceGrupos[indexGrupo]].value == grupos[i]) 
												{
													document.editarUsuarioForm.elements[indiceGrupos[indexGrupo]].checked = true;
													changePropertyObjetoHtml('imgSelected' + grupos[i], 'src', '/Strategos/paginas/framework/usuarios/allSelected.jpg');
													break;
												} 
											}
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		function setGrupos(fieldGrupos, grupoId, addGrupoId)
		{
			if (typeof(fieldGrupos) == "undefined")
				return;
			if (typeof(addGrupoId) == "undefined")
				return;
				
			if (addGrupoId && typeof(grupoId) != "undefined") // Se ha seleccionado un grupo de permisos 
			{
				var grupos = fieldGrupos.value.split(sepGrp);
				var grupoEncontrado = false;
				var valorBuscado = organizacionSeleccionadaId + sepOrg + grupoId;
				for (var i = 0; i < grupos.length; i++) 
				{
					if (grupos[i].length > 0)
					{
						if (grupos[i] == valorBuscado)
						{
							grupoEncontrado = true;
							break;
						}
					}
				}

				if (!grupoEncontrado)
				{
					fieldGrupos.value = fieldGrupos.value + valorBuscado + sepGrp;
					var checkboxOrg = document.getElementById("checkbox_" + organizacionSeleccionadaId); 
					if (checkboxOrg != null && !checkboxOrg.checked)
						checkboxOrg.checked = true;
				}
			} 
			else if (!addGrupoId) 
			{
				var grupos = fieldGrupos.value.split(sepGrp);
				var grupoEncontrado = false;
				var fieldGruposValue = "";
				if (typeof(grupoId) != "undefined")
				{
					var valorBuscado = organizacionSeleccionadaId + sepOrg + grupoId;
					for (var i = 0; i < grupos.length; i++) 
					{
						if (grupos[i].length > 0)
						{
							if (grupos[i] == valorBuscado)
								grupoEncontrado = true;
							else
								fieldGruposValue = fieldGruposValue + grupos[i] + sepGrp;
						}
					}
				}
				else
				{
					for (var i = 0; i < grupos.length; i++) 
					{
						if (grupos[i].length > 0)
						{
							var campos = grupos[i].split(sepOrg);
							if (campos.length > 0)
							{
								if (campos[0] == organizacionSeleccionadaId)
									grupoEncontrado = true;
								else
									fieldGruposValue = fieldGruposValue + grupos[i] + sepGrp;
							}
						}
					}
				}
				
				if (grupoEncontrado)
					fieldGrupos.value = fieldGruposValue; 
			}
		}
		
		$(function() {
			$('#txtRestriccionUsoHoraDesde').timepicker({'timeFormat': 'h:i A' });
		});
		
		$(function() {
			$('#txtRestriccionUsoHoraHasta').timepicker({'timeFormat': 'h:i A' });
		});

	</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarUsuarioForm.UId" />

		<sslext:form action="/framework/usuarios/guardarUsuario" focus="UId">

			<html:hidden property="usuarioId" />
			<html:hidden property="copiar" />
			<html:hidden property="grupos" />
			<html:hidden property="restriccionUsoHoraDesde" />
			<html:hidden property="restriccionUsoHoraHasta" />			
			<input type="hidden" name="minimoCaracteres" value="<bean:write name="com.visiongc.app.web.configiniciosesion" property="minimoCaracteres" />">
			<input type="hidden" name="tipoContrasena" value="<bean:write name="com.visiongc.app.web.configiniciosesion" property="tipoContrasena" />">
			<html:hidden property="reutilizacionContrasena" />
			<input type="hidden" name="tipoContraIngresada" value="" >

			<vgcinterfaz:contenedorForma width="750px" height="480px">

				<vgcinterfaz:contenedorFormaTitulo>
					<logic:equal name="editarUsuarioForm" property="usuarioId" value="0">
						<logic:equal name="editarUsuarioForm" property="copiar" value="false">
							<vgcutil:message key="jsp.framework.editarusuario.titulo.nuevo" />
						</logic:equal>
						<logic:notEqual name="editarUsuarioForm" property="copiar" value="false">
							<vgcutil:message key="jsp.framework.editarusuario.titulo.copiar" />
						</logic:notEqual>
					</logic:equal>
					<logic:notEqual name="editarUsuarioForm" property="usuarioId" value="0">
						<vgcutil:message key="jsp.framework.editarusuario.titulo.modificar" />
					</logic:notEqual>
				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="320px" width="735px" nombre="panelesEditarUsuario">

					<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="panelDatosBasicos">
						<vgcinterfaz:panelContenedorTitulo>Datos Básicos</vgcinterfaz:panelContenedorTitulo>

						<%-- Ficha de datos --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="8" align="center" height="100%" border="0">

							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.uid" /></td>
								<td><html:text styleClass="cuadroTexto" property="UId" size="75" maxlength="50" /></td>
							</tr>

							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.fullname" /></td>
								<td><html:text styleClass="cuadroTexto" property="fullName" size="75" maxlength="50" /></td>
							</tr>

							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.password" /></td>
								<td><html:password styleClass="cuadroTexto" property="pwd" onchange="ejecutarPorDefecto(event, this.name)" size="75" maxlength="20" /></td>
								<td id="tdPasswordType"><span id="lblPasswordType" name="lblPasswordType"></span></td>
							</tr>

							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.pwdconfirm" /></td>
								<td><html:password styleClass="cuadroTexto" property="pwdConfirm" size="75" maxlength="20" /></td>
							</tr>

							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.isadmin" /></td>
								<td><html:checkbox styleClass="botonSeleccionMultiple" property="isAdmin" onclick="clickIsAdmin(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, this)"></html:checkbox></td>
							</tr>
							
							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.estatus" /></td>
								<td width="25%">
									<html:select property="estatus" styleClass="cuadroTexto">
										<html:option value="0"><vgcutil:message key="jsp.framework.editarusuario.label.estatus.activo" /></html:option>
										<html:option value="1"><vgcutil:message key="jsp.framework.editarusuario.label.estatus.inactivo" /></html:option>
									</html:select>
								</td>
							</tr>
							
							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.blocked" /></td>
								<td width="25%"><html:checkbox styleClass="botonSeleccionMultiple" property="bloqueado"></html:checkbox></td>
							</tr>
							
							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.estatus.deshabilitado" /></td>
								<td width="25%"><html:checkbox styleClass="botonSeleccionMultiple" property="deshabilitado"></html:checkbox></td>
							</tr>

							<tr>
								<td align="right"><vgcutil:message key="jsp.framework.editarusuario.label.estatus.forzarcambioclave" /></td>
								<td width="25%"><html:checkbox styleClass="botonSeleccionMultiple" property="forzarCambiarpwd"></html:checkbox></td>
							</tr>

							<tr>
								<td colspan="2" style="padding-top: 10px;"><b><span id="lblPwdLen" name="lblPwdLen"></span></b></td>
							</tr>
							<tr>
								<td colspan="2"><b><span id="lblPwdTypeDesc" name="lblPwdTypeDesc"></span></b></td>
							</tr>
							<tr height="100%">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>
					<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="panelPermisos">
						<vgcinterfaz:panelContenedorTitulo>Permisos</vgcinterfaz:panelContenedorTitulo>

						<table class="bordeFichaDatos" width="100%" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
							<tr>
								<td width="50%" align="center"><a href="javascript:marcarOrganizaciones(self.document.editarUsuarioForm, true);"><vgcutil:message key="jsp.framework.editarusuario.label.selectall" /></a>&nbsp;&nbsp;&nbsp;<a href="javascript:marcarOrganizaciones(self.document.editarUsuarioForm, false);"><vgcutil:message key="jsp.framework.editarusuario.label.unselectall" /></a></td>
								<td width="50%">&nbsp;</td>
							</tr>
							<tr>
								<td align="center"><vgcutil:message key="jsp.framework.editarusuario.label.organizaciones" /></td>
								<td align="center"><vgcutil:message key="jsp.framework.editarusuario.label.grupospermisos" /></td>
							</tr>

							<tr height="270px">
								<td>
									<treeview:treeview 
										name="editarUsuarioArbolOrganizaciones" 
										scope="session" 
										baseObject="editarUsuarioOrganizacionRoot" 
										isRoot="true" 
										fieldName="nombre" 
										fieldId="organizacionId" 
										fieldChildren="hijos" 
										lblUrlObjectId="orgId" 
										styleClass="treeview" 
										checkbox="true" 
										checkboxName="organizacion" 
										pathImages="/componentes/visorArbol/" 
										urlJs="/componentes/visorArbol/visorArbol.js"
										nameSelectedId="organizacionId" 
										checkboxOnClick="loadGruposSeleccionados(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, this, true);"
										urlName="javascript:seleccionarNodo(orgId);" 
										lblUrlAnchor="marcadorAncla"
									/>
								</td>
								<td><vgcinterfaz:visorLista width="100%" useFrame="true" namePaginaLista="editarUsuarioPaginaGrupos" messageKeyNoElementos="jsp.framework.editarusuario.nogrupos" nombre="visorListaGrupos" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
									<vgcinterfaz:columnaVisorLista nombre="checkAsociar" width="20px">
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="check" width="20px">
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="seleccionados" width="20px">
									</vgcinterfaz:columnaVisorLista>
									<vgcinterfaz:columnaVisorLista nombre="nombre">
										<vgcutil:message key="jsp.framework.editarusuario.listagrupos.nombre" />
									</vgcinterfaz:columnaVisorLista>

									<vgcinterfaz:filasVisorLista nombreObjeto="grupo">
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="checkAsociar" align="center">
											<img id="imgAsociar_<bean:write name="grupo" property="grupoId" />" onclick="asociarASeleccionados(self.document.editarUsuarioForm.grupos, this)" src="<html:rewrite page="/paginas/framework/usuarios/asociar.gif" />" title="<vgcutil:message key='jsp.framework.editarusuario.listagrupos.asociar.title' />">
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="check" align="center">
											<script type="text/javascript">addGrupoIdLista('<bean:write name="grupo" property="grupoId" />');</script>
											<input type="checkbox" name="grupo" id="grupo_<bean:write name='grupo' property='grupoId' />" value="<bean:write name='grupo' property='grupoId' />" onclick="establecerGrupoSeleccionado(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, this)">
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionados" align="center">
											<img id="imgSelected<bean:write name="grupo" property="grupoId" />" onclick="establecerGrupoSeleccionadoImg(this)" src="<html:rewrite page="/paginas/framework/usuarios/noneSelected.jpg" />">
										</vgcinterfaz:valorFilaColumnaVisorLista>
										<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
											<bean:write name="grupo" property="grupo" />
										</vgcinterfaz:valorFilaColumnaVisorLista>

									</vgcinterfaz:filasVisorLista>

								</vgcinterfaz:visorLista></td>
							</tr>
						</table>

					</vgcinterfaz:panelContenedor>

					<vgcinterfaz:panelContenedor anchoPestana="150px" nombre="panelRestriccionUso">					
						<vgcinterfaz:panelContenedorTitulo>Uso del Sistema</vgcinterfaz:panelContenedorTitulo>

						<table class="bordeFichaDatos" width="100%" cellpadding="3" cellspacing="0" align="center" height="100%" border="0">
						<tr>
								<td align="right"><vgcutil:message key="jsp.framework.iniciosesion.restriccionusodias" /></td>
								<td width="80%">
									<html:checkbox property="restriccionUsoDiaLunes" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.lunes" />
								&nbsp;&nbsp;&nbsp;
								<html:checkbox property="restriccionUsoDiaMartes" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.martes" />
								&nbsp;&nbsp;&nbsp;
								<html:checkbox property="restriccionUsoDiaMiercoles" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.miercoles" />
								&nbsp;
								<html:checkbox property="restriccionUsoDiaJueves" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.jueves" />
								&nbsp;
								<html:checkbox property="restriccionUsoDiaViernes" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.viernes" />
								<br>
								<html:checkbox property="restriccionUsoDiaSabado" styleClass="botonSeleccionMultiple" /><vgcutil:message key="dia.sabado" />
								&nbsp;
								<html:checkbox property="restriccionUsoDiaDomingo" styleClass="botonSeleccionMultiple" ></html:checkbox><vgcutil:message key="dia.domingo" />
								</td>
							</tr>						
						<tr>						
							<td align="right"><vgcutil:message key="jsp.framework.iniciosesion.restriccionusohoras" /></td>
							<td>
							<input type="text" id="txtRestriccionUsoHoraDesde" value="<bean:write name="editarUsuarioForm" property="restriccionUsoHoraDesde" />" size="12" maxlength="15" class="cuadroTexto" readonly="true">
							<img style="cursor:pointer" onclick="limpiarHoraDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							&nbsp;&nbsp;a&nbsp;&nbsp;
							<input type="text" id="txtRestriccionUsoHoraHasta" value="<bean:write name="editarUsuarioForm" property="restriccionUsoHoraHasta" />" size="12" maxlength="15" class="cuadroTexto" readonly="true">
							<img style="cursor:pointer" onclick="limpiarHoraHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							</td>
						</tr>
						<tr height="100%">
							<td colspan="2">&nbsp;</td>
							</tr>
					</table>
					</vgcinterfaz:panelContenedor>
				</vgcinterfaz:contenedorPaneles>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelarGuardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

			<script type="text/javascript">
				// Inicializacion de objetos de Javascript que manejan los grupos por organización
				indiceGrupos = loadIndicesObjeto(self.document.editarUsuarioForm, 'grupo');
				indiceOrganizaciones = loadIndicesObjeto(self.document.editarUsuarioForm, 'organizacion');
				_loading = true;
				if (indiceOrganizaciones.length > 0) 
					loadGruposSeleccionados(self.document.editarUsuarioForm, self.document.editarUsuarioForm.grupos, window.document.editarUsuarioForm.elements[indiceOrganizaciones[0]], false);
				
				_loading = false;
			</script>

		</sslext:form>
		
		<script type="text/javascript">
			var mensaje = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.minimocaracteres" />';
			mensaje = mensaje.replace("XXX", document.editarUsuarioForm.minimoCaracteres.value);
			document.getElementById("lblPwdLen").innerHTML = mensaje;
			
			switch(document.editarUsuarioForm.tipoContrasena.value)
			{
				case "0":
					document.getElementById("lblPwdTypeDesc").innerHTML = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.tipocontrasena.debil" />';
					break;
				case "1":
					document.getElementById("lblPwdTypeDesc").innerHTML = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.tipocontrasena.media" />';
					break;
				case "2":
					document.getElementById("lblPwdTypeDesc").innerHTML = '<vgcutil:message key="jsp.framework.usuarios.cambiarclaveusuario.label.tipocontrasena.fuerte" />';
					break;
			}
			
			txtPasswordChanged();
		</script>
		
		<html:javascript formName="editarUsuarioForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>

	</tiles:put>

</tiles:insert>
