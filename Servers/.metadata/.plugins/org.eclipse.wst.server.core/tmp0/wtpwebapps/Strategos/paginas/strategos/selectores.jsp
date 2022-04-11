<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
 
<%-- Modificado por: Kerwin Arias (27/01/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">Selectores de prueba</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Categorías de Medición
			// ********************************************************************************
			function limpiarCategoriaMedicion() 
			{				
				document.selectores.categoriaMedicionId.value = "";				
				document.selectores.nombreCategoriaMedicion.value = "";
			}
		
			function seleccionarCategoriaMedicion() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5  - funcionCierre: ----
				abrirSelectorCategoriasMedicion('selectores', 'nombreCategoriaMedicion', 'categoriaMedicionId', document.selectores.categoriaMedicionId.value, 'funcionCierreSelectorCategoriasMedicion()');		
			}			
			
			function funcionCierreSelectorCategoriasMedicion() 
			{				
				alert("Función de Cierre de: Categorías de Medición");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Categorías de Medición
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Unidades de Medida
			// ********************************************************************************
			function limpiarUnidadMedida() 
			{				
				document.selectores.unidadMedidaId.value = "";				
				document.selectores.nombreUnidadMedida.value = "";
			}
			
			function seleccionarUnidadMedida() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5  - funcionCierre: ----
				abrirSelectorUnidadesMedida('selectores', 'nombreUnidadMedida', 'unidadMedidaId', document.selectores.unidadMedidaId.value, 'funcionCierreSelectorUnidadesMedida()');		
			}			
			
			function funcionCierreSelectorUnidadesMedida() 
			{				
				alert("Función de Cierre de: Unidades de Medida");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Unidades de Medida
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Estados de Acciones
			// ********************************************************************************			
			function limpiarEstadoAcciones() 
			{				
				document.selectores.estadoAccionesId.value = "";				
				document.selectores.nombreEstadoAcciones.value = "";
			}
			
			function seleccionarEstadoAcciones() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5  - funcionCierre: ----											
				abrirSelectorEstadosAcciones('selectores', 'nombreEstadoAcciones', 'estadoAccionesId', document.selectores.estadoAccionesId.value, 'funcionCierreSelectorEstadosAcciones()');						
			}			
			
			function funcionCierreSelectorEstadosAcciones() 
			{				
				alert("Función de Cierre de: Estados de Acciones");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Estados de Acciones
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Causas
			// ********************************************************************************		
			function limpiarCausa() 
			{				
				document.selectores.causaId.value = "";				
				document.selectores.nombreCausa.value = "";
			}			
			
			function seleccionarCausa() 
			{
				abrirSelectorCausas('selectores', 'nombreCausa', 'causaId', 'funcionCierreSelectorCausas()');		
			}			
			
			function funcionCierreSelectorCausas() 
			{				
				alert("Función de Cierre de: Causas");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Causas
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Cuentas
			// ********************************************************************************
			function limpiarCuenta() 
			{
				document.selectores.cuentaId.value = "";
				document.selectores.nombreCuenta.value = "";
			}
			
			function seleccionarCuenta() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado
				// @param 4  - funcionCierre: ----
				abrirSelectorCuentas('selectores', 'nombreCuenta', 'cuentaId', 'funcionCierreSelectorCuentas()');
			}

			function funcionCierreSelectorCuentas() 
			{
				alert("Función de Cierre de: Cuentas");
			}
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Cuentas
			// ********************************************************************************
				
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Responsables
			// ********************************************************************************				
			function limpiarResponsable() 
			{				
				document.selectores.responsableId.value = "";				
				document.selectores.nombreResponsable.value = "";
			}
			
			function seleccionarResponsable() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado				 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5  - Organización: nombre de la organización por la que se dezsea filtrar los responsables
				// @param 6  - MostrarGrupos: indica si se quieren mostrar los grupos de la Tabla de responsables
						// 0 ó false: Muestra los responsables que no son grupo
						// 1 ó true: Muestra los responsables que son grupo
						// null: Muestra todos los responsables
				// @param 7  - MostrarGlobales: indica si se quieren mostrar los responsables Globales
						// 0 ó false: Muestra los responsables que no son globales
						// 1 o true: Muestra los responsables que son globales
						// null: Muestra todos los responsables
				// @param 8  - funcionCierre: ----				
				abrirSelectorResponsables('selectores', 'nombreResponsable', 'responsableId', document.selectores.responsableId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>', null, null, 'funcionCierreSelectorResponsables()');		
			}			
			
			function funcionCierreSelectorResponsables() 
			{				
				alert("Función de Cierre de: Responsables");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Responsables
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Series de Tiempo
			// ********************************************************************************
			function limpiarSerieTiempo() 
			{				
				document.selectores.serieTiempoId.value = "";				
				document.selectores.nombreSerieTiempo.value = "";
			}			
			
			function seleccionarSerieTiempo() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5  - funcionCierre: ----
				abrirSelectorSeriesTiempo('selectores', 'nombreSerieTiempo', 'serieTiempoId', document.selectores.serieTiempoId.value, 'funcionCierreSelectorSeriesTiempo()');		
			}			
			
			function funcionCierreSelectorSeriesTiempo() 
			{				
				alert("Función de Cierre de: Series de Tiempo");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Series de Tiempo
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Organizaciones
			// ********************************************************************************
			function limpiarOrganizacion() 
			{				
				document.selectores.organizacionId.value = "";				
				document.selectores.nombreOrganizacion.value = "";
			}			
			
			function seleccionarOrganizacion() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5: valor de sesión que indica el id de la organización
				// @param 6  - funcionCierre: ----
				abrirSelectorOrganizaciones('selectores', 'nombreOrganizacion', 'organizacionId', document.selectores.organizacionId.value, null, 'funcionCierreSelectorOrganizaciones()');
			}
			
			function funcionCierreSelectorOrganizaciones() 
			{				
				alert("Función de Cierre de: Organizaciones");
			}
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Organizaciones
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Clases de Indicadores
			// ********************************************************************************			
			function limpiarClaseIndicadores() 
			{				
				document.selectores.claseId.value = "";				
				document.selectores.nombreClaseIndicadores.value = "";
			}			
			
			function seleccionarClaseIndicadores() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5: valor de sesión que indica el id de la organización
				// @param 6  - funcionCierre: ----				
				// @param 7: Permite cambiar entre clases, true si permite, o si no false
				// @param 8: Permite Cambiar entre Organizaciones, true si permite, o si no false
				abrirSelectorClasesIndicadores('selectores', 'nombreClaseIndicadores', 'claseId', document.selectores.claseId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>');		
			}
			
			function funcionCierreSelectorClases() 
			{				
				alert("Función de Cierre de: Clases de Indicadores");
			}			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Clases de Indicadores
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Indicadores
			// ********************************************************************************
			function limpiarIndicador() 
			{				
				document.selectores.indicadorId.value = "";				
				document.selectores.nombreIndicador.value = "";
			}			
			
			function seleccionarIndicador() 
			{
				// @param 1  - nombreForma: nombre de la forma (FormBean) padre (que invoca al selector)
				// @param 2  - nombreCampoIndicador: nombre del cuadro de texto que contendrá el nombre del Indicador
				// @param 3  - nombreCampoIndicadorId: nombre del campo hidden que contendrá el valor (id) del Indicador
				// @param 4  - nombreCampoRutaCompleta: -----
				// @param 5  - funcionCierre: ----
				// @param 6  - seleccionados: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 7  - nombreCampoOrganizacion: nombre del campo que contendrá el nombre de la Organización devuelta
				// @param 8  - nombreCampoOrganizacionId: nombre del campo que contendrá el id de la Organización devuelta
				// @param 9  - nombreCampoClase: nombre del campo que contendrá el nombre de la Clase devuelta
				// @param 10 - nombreCampoClaseId: nombre del campo que contendrá el id de la Clase devuelta				
				// @param 11 - queryStringFiltros: valor que establece todos los parámetros adicionales. Puede ser null
					// @param 11a: (organizacionId) 				aplica a queryStringFiltros. Long - id de la Organización por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)			
					// @param 11b: (claseId)         				aplica a queryStringFiltros. Long - id de la Clase por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 11c: (frecuencia)     				aplica a queryStringFiltros. Byte - valor numérico que indica la Frecuencia por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 11d: (permitirCambiarOrganizacion)	aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar la Organización en el selector (Puede se null, vacio o no se referencia)
					// @param 11e: (permitirCambiarClase)			aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar la Clase en el selector (Puede se null, vacio o no se referencia)
					// @param 11f: (mostrarSeriesTiempo)			aplica a queryStringFiltros. Boolean - indica si se se muestran las series de tiempo en el selector (Puede se null, vacio o no se referencia)
					// @param 11g: (frecuenciasContenida)			aplica a queryStringFiltros. Byte - valor numérico que indica la Frecuencia que debe contener las frecuencias por las cuales se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 11h: (seleccionMultiple)				aplica a queryStringFiltros. Boolean - indica si se pueden seleccionar uno o varios indicadores en el selector (Puede se null, vacio o no se referencia)
					// @param 11i: (permitirCualitativos)			aplica a queryStringFiltros. Boolean - indica si se quieren mostrar los indicadores cualitativos en el selector (Puede se null, vacio o no se referencia)
					// @param 11j: (soloCompuestos)					aplica a queryStringFiltros. Boolean - indica si se quieren mostrar sólo los indicadores compuestos en el selector (Puede se null, vacio o no se referencia)
					// @param 11j: (permitirIniciativas)			aplica a queryStringFiltros. Boolean - indica si se quieren mostrar los indicadores por iniciativas
					// Ejemplos de queryStringFiltros
						// a) queryStringFiltros = "";
						// b) queryStringFiltros = '&organizacionId=1&claseId=3000&frecuencia=3&permitirCambiarOrganizacion=true&permitirCambiarClase=true';
						// c) queryStringFiltros = '&frecuencia=4&mostrarSeriesTiempo=true';
				var queryStringFiltros;
				queryStringFiltros = '&permitirCambiarOrganizacion=true&soloCompuestos=true&permitirCualitativos=false&seleccionMultiple=false';
				abrirSelectorIndicadores('selectores', 'nombreIndicador', 'indicadorId', 'indicadorRutasCompletas', 'funcionCierreSelectorIndicadores()', null, null, null, null, null, queryStringFiltros);				
			}	
			
			function funcionCierreSelectorIndicadores() 
			{				
				alert("Función de Cierre de: Indicadores");
			}
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Indicadores
			// ********************************************************************************			
						
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Usuarios
			// ********************************************************************************
			function limpiarUsuario() 
			{				
				document.selectores.usuarioId.value = "";				
				document.selectores.nombreUsuario.value = "";
			}			
			
			function seleccionarUsuario() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5: valor de sesión que indica el id de la organización
				// @param 6: indica si se muestran los administradores				
				// @param 7  - funcionCierre: ----
				abrirSelectorUsuarios('selectores', 'nombreUsuario', 'usuarioId', document.selectores.usuarioId.value, '<bean:write name="organizacion" scope="session" property="organizacionId"/>', 'false', 'funcionCierreSelectorUsuarios()');		
			}
			
			function funcionCierreSelectorUsuarios() 
			{				
				alert("Función de Cierre de: Usuarios");
			}
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Usuarios
			// ********************************************************************************					
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Plantillas de Planes
			// ********************************************************************************
			function limpiarPlantillaPlanes() 
			{				
				document.selectores.plantillaPlanesId.value = "";				
				document.selectores.nombrePlantillaPlanes.value = "";
			}			
			
			function seleccionarPlantillaPlanes() 
			{
				// @param 1: nombre de la forma padre (que invoca al selector)
				// @param 2: nombre del cuadro de texto que contendrá el valor (visible) devuelto
				// @param 3: nombre del campo hidden que contendrá el valor (id) del objeto seleccionado 
				// @param 4: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 5  - funcionCierre: ----
				abrirSelectorPlantillasPlanes('selectores', 'nombrePlantillaPlanes', 'plantillaPlanesId', document.selectores.plantillaPlanesId.value, 'funcionCierreSelectorPlantillas()');		
			}
			
			function funcionCierreSelectorPlantillas() 
			{				
				alert("Función de Cierre de: Plantillas de Planes ");
			}
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Plantillas de Planes
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Planes
			// ********************************************************************************			
			function limpiarPlan() 
			{				
				document.selectores.planId.value = "";				
				document.selectores.nombrePlan.value = "";
			}			
			
			function seleccionarPlan() 
			{							
				// @param 1 - nombreForma: nombre de la forma (FormBean) padre (que invoca al selector)
				// @param 2 - nombreCampoPlan: nombre del cuadro de texto que contendrá el nombre del Plan
				// @param 3 - nombreCampoPlanId: nombre del campo hidden que contendrá el valor (id) del Plan
				// @param 4 - nombreCampoRutaCompleta: -----
				// @param 5 - funcionCierre: ----
				// @param 6 - seleccionados: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección				
				// @param 7 - queryStringFiltros: valor que establece todos los parámetros adicionales. Puede ser null
					// @param 7a: (organizacionId) 				aplica a queryStringFiltros. Long - id de la Organización por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)					
					// @param 7b: (permitirCambiarOrganizacion)	aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar la Organización en el selector (Puede se null, vacio o no se referencia)
					// @param 7c: (mostrarSoloPlanesActivos) 	aplica a queryStringFiltros. Boolean - indica si solo se muestran aquellos planes activo o inactivos (Puede se null, vacio o no se referencia)					
					// Ejemplos de queryStringFiltros
						// a) queryStringFiltros = "";
						// b) queryStringFiltros = "&mostrarSoloPlanesActivos=true";
						// c) queryStringFiltros = '&organizacionId=1&permitirCambiarOrganizacion=true';						
				var queryStringFiltros;
				queryStringFiltros = '&permitirCambiarOrganizacion=true';				
				abrirSelectorPlanes('selectores', 'nombrePlan', 'planId', 'planesRutasCompletas', 'funcionCierreSelectorPlanes()', null, queryStringFiltros);
			}			
			
			function funcionCierreSelectorPlanes() 
			{								
				if ((document.selectores.planesRutasCompletas.value != null) && (document.selectores.planesRutasCompletas.value != "")) 
					alert("Función de Cierre de: Planes \n" + "Ruta Completa de la Organización del Plan: " + document.selectores.planesRutasCompletas.value);
			}
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Planes
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Perspectivas
			// ********************************************************************************
			function limpiarPerspectiva() 
			{
				document.selectores.perspectivaId.value = "";
				document.selectores.nombrePerspectiva.value = "";
			}
			
			function seleccionarPerspectiva() 
			{
				// @param 1 - nombreForma: nombre de la forma (FormBean) padre (que invoca al selector)
				// @param 2 - nombreCampoPerspectiva: nombre del cuadro de texto que contendrá el nombre de la Perspectiva
				// @param 3 - nombreCampoPerspectivaId: nombre del campo hidden que contendrá el valor (id) de la Perspectiva
				// @param 4 - nombreCampoRutaCompleta: -----
				// @param 5 - funcionCierre: ----
				// @param 6 - seleccionado: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección
				// @param 7 - queryStringFiltros: valor que establece todos los parámetros adicionales. Puede ser null
				// 	@param 7a: (organizacionId) 				aplica a queryStringFiltros. Long - id de la Organización por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
				// 	@param 7b: (planId)         				aplica a queryStringFiltros. Long - id del Plan por el cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
				// 	@param 7d: (permitirCambiarOrganizacion)	aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar la Organización en el selector (Puede se null, vacio o no se referencia)
				// 	@param 7e: (permitirCambiarPlan)			aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar el Plan en el selector (Puede se null, vacio o no se referencia)
				// 	Ejemplos de queryStringFiltros
				// 		a) queryStringFiltros = "";
				// 		b) queryStringFiltros = '&organizacionId=1&planId=8000&permitirCambiarOrganizacion=true&permitirCambiarPlan=true';
				// 		c) queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true';
				var queryStringFiltros;
				queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true';			
				abrirSelectorPerspectivas('selectores', 'nombrePerspectiva', 'perspectivaId', 'perspectivaRutaCompleta', 'funcionCierreSelectorPerspectivas()', null, queryStringFiltros);
			}
			
			function funcionCierreSelectorPerspectivas() 
			{
				if (document.selectores.perspectivaRutaCompleta.value != null) 
					alert("Ruta Completa de la Perspectiva: " + document.selectores.perspectivaRutaCompleta.value);
			}
			
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Perspectivas
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Iniciativas
			// ********************************************************************************			
			function limpiarIniciativa() 
			{				
				document.selectores.iniciativaId.value = "";				
				document.selectores.nombreIniciativa.value = "";
			}			
			
			function seleccionarIniciativa() 
			{							
				// @param 1 - nombreForma: nombre de la forma (FormBean) padre (que invoca al selector)
				// @param 2 - nombreCampoIniciativa: nombre del cuadro de texto que contendrá el nombre de la Iniciativa
				// @param 3 - nombreCampoIniciativaId: nombre del campo hidden que contendrá el valor (id) de la Iniciativa
				// @param 4 - nombreCampoRutaCompleta: -----
				// @param 5 - iniciativaPlanId: Plan al que está asociado la Iniciativa seleccionada (Puede se null o vacio)
				// @param 6 - funcionCierre: ----
				// @param 7 - seleccionados: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección				
				// @param 8 - queryStringFiltros: valor que establece todos los parámetros adicionales. Puede ser null
					// @param 8a: (organizacionId) 				aplica a queryStringFiltros. Long - id de la Organización por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)			
					// @param 8b: (planId)         				aplica a queryStringFiltros. Long - id del Plan por el cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 8c: (frecuencia)     				aplica a queryStringFiltros. Byte - valor númeríco que indica la Frecuencia por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 8d: (permitirCambiarOrganizacion)	aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar la Organización en el selector (Puede se null, vacio o no se referencia)
					// @param 8e: (permitirCambiarPlan)			aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar el Plan en el selector (Puede se null, vacio o no se referencia)
					// @param 8f: (seleccionMultiple)			aplica a queryStringFiltros. Boolean - indica si se pueden seleccionar uno o varias iniciativas en el selector (Puede se null, vacio o no se referencia)						
					// Ejemplos de queryStringFiltros
						// a) queryStringFiltros = "";
						// b) queryStringFiltros = '&organizacionId=1&planId=8000&frecuencia=3&permitirCambiarOrganizacion=true&permitirCambiarPlan=true';
						// c) queryStringFiltros = 'frecuencia=4';
				var queryStringFiltros;
				queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&seleccionMultiple=true';
				abrirSelectorIniciativas('selectores', 'nombreIniciativa', 'iniciativaId', 'iniciativaRutasCompletas', 'iniciativaPlanId', 'funcionCierreSelectorIniciativas()', null, queryStringFiltros);										
			}			
			
			function funcionCierreSelectorIniciativas() 
			{
				if (document.selectores.iniciativaRutasCompletas.value != null) 
					alert("Función de Cierre de: Iniciativas \n" + "Ruta Completa de la Organización de la Iniciativa" + document.selectores.iniciativaRutasCompletas.value);
				if ((document.selectores.iniciativaPlanId.value != null) && (document.selectores.iniciativaPlanId.value != "")) 
					alert("Plan ID de la Iniciativa: " + document.selectores.iniciativaPlanId.value);
			}		
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Iniciativas
			// ********************************************************************************
			
			// ********************************************************************************
			// INICIO: Funciones para implementar el Selector de Actividades
			// ********************************************************************************			
			function limpiarActividad() 
			{				
				document.selectores.actividadId.value = "";				
				document.selectores.nombreActividad.value = "";
			}
			
			function seleccionarActividad() 
			{							
				// @param 1 - nombreForma: nombre de la forma (FormBean) padre (que invoca al selector)
				// @param 2 - nombreCampoActividad: nombre del cuadro de texto que contendrá el nombre de la Actividad
				// @param 3 - nombreCampoActividadId: nombre del campo hidden que contendrá el valor (id) de la Actividad
				// @param 4 - nombreCampoRutaCompleta: -----
				// @param 5 - actividadPlanId: Plan al que está asociado la actividad seleccionada (Puede se null o vacio)
				// @param 6 - funcionCierre: ----
				// @param 7 - seleccionados: valor del id del objeto seleccionado. Puede ser "null" para no recordar la selección				
				// @param 8 - queryStringFiltros: valor que establece todos los parámetros adicionales. Puede ser null
					// @param 8a: (organizacionId) 				aplica a queryStringFiltros. Long - id de la Organización por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)			
					// @param 8b: (planId)         				aplica a queryStringFiltros. Long - id del Plan por el cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 8c: (frecuencia)     				aplica a queryStringFiltros. Byte - valor númeríco que indica la Frecuencia por la cual se desea pre-filtrar en el selector (Puede se null, vacio o no se referencia)
					// @param 8d: (permitirCambiarOrganizacion)	aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar la Organización en el selector (Puede se null, vacio o no se referencia)
					// @param 8e: (permitirCambiarPlan)			aplica a queryStringFiltros. Boolean - indica si se es permitido cambiar el Plan en el selector (Puede se null, vacio o no se referencia)
					// Ejemplos de queryStringFiltros
						// a) queryStringFiltros = "";
						// b) queryStringFiltros = '&organizacionId=1&planId=8000&frecuencia=3&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&permitirCambiarIniciativa=true';
						// c) queryStringFiltros = 'frecuencia=4';
				var queryStringFiltros;
				queryStringFiltros = '&permitirCambiarOrganizacion=true&permitirCambiarPlan=true&permitirCambiarIniciativa=true';
				abrirSelectorActividades('selectores', 'nombreActividad', 'actividadId', 'actividadRutasCompletas', 'actividadPlanId', 'onSeleccionarActividad()', null, queryStringFiltros);										
			}			
			
			function onSeleccionarActividad() 
			{
				if ((document.selectores.actividadId.value != null) && (document.selectores.actividadId.value != "")) 
					alert("Id de la Actividad: " + document.selectores.actividadId.value);
			}		
			// ********************************************************************************
			// FIN: Funciones para implementar el Selector de Actividades
			// ********************************************************************************
			
		</script>
			
		<%-- Funciones JavaScript externas --%>	
		<jsp:include page="/paginas/strategos/categoriasmedicion/categoriasMedicionJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/unidadesmedida/unidadesMedidaJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/estadosacciones/estadosAccionesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/causas/causasJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/plancuentas/cuentasJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/seriestiempo/seriesTiempoJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/clasesindicadores/clasesIndicadoresJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/indicadores/indicadoresJs.jsp"></jsp:include>
		<jsp:include page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planes/plantillas/plantillasPlanesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/iniciativas/iniciativasJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planes/planesJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planes/perspectivas/perspectivasJs.jsp"></jsp:include>
		<jsp:include page="/paginas/strategos/planificacionseguimiento/actividades/selector/actividadesJs.jsp"></jsp:include>
		
		<form name="selectores">	
		
			<%-- Campos ocultos --%>
			<input type="hidden" name="categoriaMedicionId">
			<input type="hidden" name="unidadMedidaId">
			<input type="hidden" name="estadoAccionesId">
			<input type="hidden" name="causaId">
			<input type="hidden" name="cuentaId">
			<input type="hidden" name="responsableId">
			<input type="hidden" name="serieTiempoId">		
			<input type="hidden" name="organizacionId">		
			<input type="hidden" name="claseId">
			<input type="hidden" name="indicadorId">
			<input type="hidden" name="indicadorRutasCompletas">			
			<input type="hidden" name="iniciativaRutasCompletas">
			<input type="hidden" name="iniciativaPlanId">
			<input type="hidden" name="planesRutasCompletas">
			<input type="hidden" name="perspectivaRutaCompleta">			
			<input type="hidden" name="usuarioId">
			<input type="hidden" name="plantillaPlanesId">
			<input type="hidden" name="iniciativaId">
			<input type="hidden" name="planId">
			<input type="hidden" name="perspectivaId">
			<input type="hidden" name="actividadId">
			<input type="hidden" name="actividadRutasCompletas">
			<input type="hidden" name="actividadPlanId">
			
			<%-- Este es el "Contenedor Secundario o Forma" --%>
			<table class="contenedorForma" cellspacing="2" cellpadding="2" width="400px">
	
				<%-- Barra Superior del "Contenedor Secundario o Forma" --%>
				<tr>
					<td class="barraSuperiorForma">..::Selectores de prueba</td>					
				</tr>					
	
				<%-- Cuerpo del "Contenedor Secundario o Forma" --%>
				<tr>
					
					<td colspan="2">						
					
						<%-- Este es la "Ficha de datos" --%>
						<table class="bordeFichaDatos" cellpadding="3" cellspacing="0">
													
							<%-- Este es el cuerpo de la "Ficha de Datos" --%>
							<tr>
								<td align="right">Categorías</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreCategoriaMedicion" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarCategoriaMedicion()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Categoría de Medición'>
									<img style="cursor:pointer" onclick="limpiarCategoriaMedicion()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>
							</tr>																					
							
							<tr>
								<td align="right">Unidades</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreUnidadMedida" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarUnidadMedida()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Unidad de Medida'>
									<img style="cursor:pointer" onclick="limpiarUnidadMedida()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>
							</tr>
							
							<tr>
								<td align="right">Estados</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreEstadoAcciones" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarEstadoAcciones()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Estado de Acciones'>
									<img style="cursor:pointer" onclick="limpiarEstadoAcciones()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>
							</tr>
							
							<tr>	
								<td align="right">Causas</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreCausa" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarCausa()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Causa'>
									<img style="cursor:pointer" onclick="limpiarCausa()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>
							</tr>					
					
							<tr>
								<td align="right">Cuentas</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreCuenta" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarCuenta()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Cuentas'>
									<img style="cursor:pointer" onclick="limpiarCuenta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>	
							</tr>
					
							<tr>	
								<td align="right">Responsables</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreResponsable" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarResponsable()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Responsable'>
									<img style="cursor:pointer" onclick="limpiarResponsable()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>	
							</tr>
							
							<tr>
								<td align="right">Series de Tiempo</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreSerieTiempo" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarSerieTiempo()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Serie de Tiempo'>
									<img style="cursor:pointer" onclick="limpiarSerieTiempo()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>
							</tr>
							
							<tr>
								<td colspan="2"><hr width="100%"></td>
							</tr>
					
							<tr>	
								<td align="right">Organizaciones</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreOrganizacion" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarOrganizacion()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Organización'>
									<img style="cursor:pointer" onclick="limpiarOrganizacion()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>	
							</tr>
							
							<tr>									
								<td align="right">Clases de Indicadores</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreClaseIndicadores" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarClaseIndicadores()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Clase'>
									<img style="cursor:pointer" onclick="limpiarClaseIndicadores()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>
							
							<tr>									
								<td align="right">Indicadores</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreIndicador" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarIndicador()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Indicador'>
									<img style="cursor:pointer" onclick="limpiarIndicador()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>
					
							<tr>	
								<td align="right">Usuarios</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreUsuario" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarUsuario()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Usuario'>
									<img style="cursor:pointer" onclick="limpiarUsuario()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>							
							
							<tr>	
								<td align="right">Plantillas de Planes</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombrePlantillaPlanes" readonly="true">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarPlantillaPlanes()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Plantilla de Planes'>
									<img style="cursor:pointer" onclick="limpiarPlantillaPlanes()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>
							
							<tr>	
								<td align="right">Iniciativas</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreIniciativa">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarIniciativa()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Iniciativa'>
									<img style="cursor:pointer" onclick="limpiarIniciativa()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>
							
							<tr>	
								<td align="right">Planes</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombrePlan">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarPlan()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Plan'>
									<img style="cursor:pointer" onclick="limpiarPlan()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>							
							
							<tr>	
								<td align="right">Perspectivas</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombrePerspectiva">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarPerspectiva()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Perspectiva'>
									<img style="cursor:pointer" onclick="limpiarPerspectiva()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>

							<tr>	
								<td align="right">Actividades</td>
								<td>
									<input class="cuadroTexto" type="text" name="nombreActividad">&nbsp;									
									<img style="cursor:pointer" onclick="seleccionarActividad()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Actividad'>
									<img style="cursor:pointer" onclick="limpiarActividad()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
								</td>										
							</tr>

					</table>
					
				</tr>
				
				<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
				<tr class="barraInferiorForma">
					<td colspan="2">
						
						<%-- Para el caso de Nuevo, Modificar y Propiedades --%>
						<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10"> <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:irAtras(1);" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
						
					</td>
				</tr>
				
			</table>		
		
		</form>
		
	</tiles:put>
	
</tiles:insert>