<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (31/10/2012) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.editarnivelesplantillaplanes.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">			
			
			var separadorPerspectivas = <%=request.getParameter("separadorPerspectivas")%>;			 
			var valoresExistentes = <%=request.getParameter("valoresExistentes")%>;						
			
			function validarNombres() 
			{
				var arreglo = new Array();
				var validacion = true;					
				if ((valoresExistentes != null) && (valoresExistentes != "")) 
				{
					arreglo = valoresExistentes.split(separadorPerspectivas);
					for (var i = 0; i < arreglo.length; i++) 
					{						
						if (document.editarNivelesPlantillaPlanesForm.nombre.value == arreglo[i]) 
						{
							alert('<vgcutil:message key="jsp.editarnivelesplantillaplanes.validacion.nombreduplicado" />');
							document.editarNivelesPlantillaPlanesForm.nombre.focus();
							validacion = false;
						}
					}
				}
				return validacion;
			}
			
			function validar() 
			{
				if (document.editarNivelesPlantillaPlanesForm.nombre.value == "") 
				{
					alert('<vgcutil:message key="jsp.editarnivelesplantillaplanes.validacion.nombrerequerido" />');
					document.editarNivelesPlantillaPlanesForm.nombre.focus();
					return false;
				} 
				else 
					return validarNombres();					
			}

			function guardar() 
			{			
				if (validar()) 
				{				
					this.opener.document.editarPlantillaPlanesForm.nombreNivel.value = document.editarNivelesPlantillaPlanesForm.nombre.value;					
					if ((accionNiveles != "") && (accionNiveles != "1")) 
					{
						if (document.editarNivelesPlantillaPlanesForm.nivel[0].checked == true) 
							this.opener.document.editarPlantillaPlanesForm.tipoNivel.value = 0;
						else 
							this.opener.document.editarPlantillaPlanesForm.tipoNivel.value = 1;
					}										
					this.opener.agregarNivel();
					this.close();
				}
			}

			function cancelar() 
			{
				this.close();
			}

			function ejecutarPorDefecto(e) 
			{
				if(e.keyCode==13) 
					guardar();
			}
			
		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<form name="editarNivelesPlantillaPlanesForm">
			<vgcinterfaz:contenedorForma idContenedor="editarNivelesPlantillaPlanes" width="400px">
	
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					    ..::<vgcutil:message key="jsp.editarnivelesplantillaplanes.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
	
				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" id="tablaNiveles">
	
					<%-- Campos de la Ficha de Datos --%>
					<tr id="nombreNivel">
						<td align="right">
							<vgcutil:message key="jsp.editarnivelesplantillaplanes.ficha.nombre" />
						</td>
						<td style="width: 270px;">
							<input type="text" name="nombre" value="<vgcutil:message key="jsp.editarnivelesplantillaplanes.ficha.nombre" />" onkeypress="ejecutarPorDefecto(event)" maxlength="50" size="37" class="cuadroTexto">
						</td>
					</tr>					
					<tr id="nivelSuperior">
						<td align="right"><vgcutil:message key="jsp.editarnivelesplantillaplanes.ficha.nivel.superior" /></td>
						<td><input type="radio" name="nivel" checked="true" class="botonSeleccionSimple"></td>
					</tr>					
					<tr id="nivelInferior">
						<td align="right"><vgcutil:message key="jsp.editarnivelesplantillaplanes.ficha.nivel.inferior" /></td>
						<td><input type="radio" name="nivel" class="botonSeleccionSimple"></td>
					</tr>
	
				</table>				
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> <vgcutil:message
						key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;					
							<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</vgcinterfaz:contenedorFormaBarraInferior>
	
			</vgcinterfaz:contenedorForma>
		
		</form>	
		<script type="text/javascript">		
			var tabla = document.getElementById("tablaNiveles");
			var accionNiveles = <%=request.getParameter("accionNiveles")%>;						
			if (accionNiveles == "") 
			{
				tabla.deleteRow(document.getElementById("nivelSuperior").rowIndex);
				tabla.deleteRow(document.getElementById("nivelInferior").rowIndex);
				this.opener.document.editarPlantillaPlanesForm.tipoNivel.value = 0;
			}			
			if (accionNiveles == "1") 
			{
				tabla.deleteRow(document.getElementById("nivelSuperior").rowIndex);
				tabla.deleteRow(document.getElementById("nivelInferior").rowIndex);
				this.opener.document.editarPlantillaPlanesForm.tipoNivel.value = 1;
			}
			
			var editarNivelesPlantillaPlanes = document.getElementById("editarNivelesPlantillaPlanes");
			if (editarNivelesPlantillaPlanes != null)
			{
				var altoForma = "68px";
				if (accionNiveles == "0")
					altoForma = "88px";
				editarNivelesPlantillaPlanes.style.height = altoForma;
			}
			document.editarNivelesPlantillaPlanesForm.nombre.focus();
		</script>

	</tiles:put>
</tiles:insert>
