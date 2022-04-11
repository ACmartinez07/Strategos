<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/10/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.asociarresponsables.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">

			function insertarFilaResponsableAsociable(elementoId,elementoNombre){		  			  
   			  objForma = document.asociarResponsablesForm;	
    	      insertarFila('Tabla1',objForma.listaTabla1,elementoId,elementoNombre,clickEventTabla1);		  	    		    			    	  
			}
			
			function insertarFilaResponsableAsociado(elementoId,elementoNombre){		  
				  objForma = document.asociarResponsablesForm;	
				  insertarFila('Tabla2',objForma.listaTabla2,elementoId,elementoNombre,clickEventTabla2);		  	    		    					  
			}

			function cancelar() {
				window.document.asociarResponsablesForm.action = '<html:rewrite action="/responsables/cancelarGuardarAsociarResponsable"/>';
				window.document.asociarResponsablesForm.submit();
			}

	        function asociar(responsableId) {
				window.location.href='<html:rewrite action="/responsables/asociarResponsables" />?responsableId=' + responsableId ;
			}

		    function guardar() {
				window.document.asociarResponsablesForm.action = '<html:rewrite action="/responsables/guardarResponsablesAsociados"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
				window.document.asociarResponsablesForm.submit();
			}

		</script>

		<%-- Inclusión de los JavaScript externos a esta página --%>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/responsables/asociarSelector/asociacion.js'/>"></script>

		<%-- Representación de la Forma --%>
		<html:form action="/responsables/guardarResponsablesAsociados">

			<%-- Atributos de la Forma --%>
			<html:hidden property="filaSelTabla1" value="" />
			<html:hidden property="filaSelTabla2" value="" />
			<html:hidden property="listaTabla1" value="|" />
			<html:hidden property="listaTabla2" value="|" />
			<html:hidden property="responsableId" />

			<vgcinterfaz:contenedorForma width="610px" height="400px">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
						<vgcutil:message key="jsp.asociarresponsables.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Ficha de los Asociados y Asociables --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0">

					<tr height="330px">

						<%-- Espacio vacio --%>
						<td width="10px">&nbsp;</td>

						<%-- Columna que contiene la Tabla de los Asociables --%>
						<td align="center" width="250px"><%-- Título de los Asociables --%>
						<table class="barraTituloGenerica" width="250">
							<tr height="17px">
								<td align="center"><vgcutil:message key="jsp.asociarresponsables.columna.responsables" /></td>
							</tr>
						</table>

						<%-- Elementos Asociables --%>
						<div style="overflow: auto; width: 250px; height: 270px;" class="contenedorForma">
						<table id="Tabla1" class="bordeFichaDatos">
							<tr>
								<td width="250px">&nbsp;</td>
							</tr>
						</table>
						</div>
						<logic:iterate id="responsable" scope="request" name="responsablesAsociables">
							<script>
									insertarFilaResponsableAsociable('<bean:write name="responsable" property="responsableId" />', '<bean:write name="responsable" property="cargo" />');
								</script>
						</logic:iterate></td>

						<%-- Columna que contiene los botones Asociar y Desasociar --%>
						<td align="center" width="100px">
						<table>
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/responsables/imagenes/asociarDesasociar1.gif'/>" border="0" title="<vgcutil:message key="boton.asociar" />" width="40" height="20"
									style="cursor: pointer;" onclick="javascript:moverElemento('>');"></td>
							</tr>
							<tr height="10px">
								<td></td>
							</tr>
							<tr>
								<td><img src="<html:rewrite page='/paginas/strategos/responsables/imagenes/asociarDesasociar2.gif'/>" border="0" title="<vgcutil:message key="boton.desasociar" />" width="40" height="20"
									style="cursor: pointer;" onclick="javascript:moverElemento('<');"></td>
							</tr>
						</table>
						</td>

						<%-- Columna que contiene la Tabla de los Asociados --%>
						<td align="center" width="250px"><%-- Título de los Asociados --%>
						<table class="barraTituloGenerica" width="250">
							<tr height="17px">
								<td align="center"><vgcutil:message key="jsp.asociarresponsables.columna.responsables.asociados" /></td>
							</tr>
						</table>

						<%-- Elementos Asociados --%>
						<div style="overflow: auto; width: 250px; height: 270px;" class="contenedorForma">
						<table id="Tabla2" class="bordeFichaDatos">
							<tr>
								<td width="250px">&nbsp;</td>
							</tr>
						</table>
						</div>
						<logic:iterate id="responsable" scope="request" name="responsablesAsociados">
							<script>
									insertarFilaResponsableAsociado('<bean:write name="responsable" property="responsableId" />','<bean:write name="responsable" property="cargo" />');
								</script>
						</logic:iterate></td>

						<%-- Espacio vacio --%>
						<td width="10px">&nbsp;</td>

					</tr>

				</table>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="asociarResponsablesForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<script>
			objForma = document.asociarResponsablesForm;
		</script>

	</tiles:put>
</tiles:insert>
