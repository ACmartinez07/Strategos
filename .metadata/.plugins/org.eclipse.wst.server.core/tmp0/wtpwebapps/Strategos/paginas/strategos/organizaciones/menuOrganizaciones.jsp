<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (22/10/2012) --%>

<script type="text/javascript" src="<html:rewrite  page='/paginas/strategos/graficos/Grafico.js'/>"></script>
<script type="text/javascript">

	function gestionarOrganizaciones(mostrarMisionVision) 
	{	
		window.location.href='<html:rewrite action="/organizaciones/gestionarOrganizaciones" />?mostrarMisionVision=' + mostrarMisionVision;
	}
	
	function gestionarIndicadores() 
	{
		window.location.href='<html:rewrite action="/indicadores/clasesindicadores/gestionarClasesIndicadores" />?defaultLoader=true';
	}
	
	function gestionarProblemas() 
	{
		window.location.href='<html:rewrite action="/problemas/clasesproblemas/gestionarClasesProblemas" />?defaultLoader=true&tipo=0';												    
	}
	
	function gestionarIniciativas() 
	{		
		window.location.href='<html:rewrite action="/iniciativas/gestionarIniciativas" />?defaultLoader=true&source=General&actualizarForma=true';
	}
	
	function gestionarPlantillasPlanes() 
	{
		window.location.href='<html:rewrite action="/planes/plantillas/gestionarPlantillasPlanes" />?defaultLoader=true';
	}
	
	function gestionarPlanesEstrategicos() 
	{
		window.location.href='<html:rewrite action="/planes/gestionarPlanesEstrategicos" />?defaultLoader=true';
	}
	
	function gestionarPlanesOperativos() 
	{
		window.location.href='<html:rewrite action="/planes/gestionarPlanesOperativos" />?defaultLoader=true';
	}
	
	function gestionarAccionesPendientes(tipo)
	{	
		window.location.href='<html:rewrite action="/problemas/acciones/gestionarAccionesPendientes" />?tipo=' + tipo;
	}
	
	function calcular() 
	{
		var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>';

		var _object = new Calculo();
		_object.ConfigurarCalculo('<html:rewrite action="/calculoindicadores/configurarCalculoIndicadores" />' + url, 'calcularMediciones');
	}

	function copiar()
	{
		window.location.href='<html:rewrite action="/organizaciones/copiarOrganizacion"/>?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&accion=copiar';
	}

	function generarCodigoEnlace()
	{
		abrirVentanaModal('<html:rewrite action="/organizaciones/editarCodigoEnlaceOrganizacion"/>?organizacionId=<bean:write name="organizacion" property="organizacionId"/>', 'codigoEnlace', 650, 360);
	}
	
	function move()
	{
		abrirVentanaModal('<html:rewrite action="/organizaciones/moverOrganizacion"/>?organizacionId=<bean:write name="organizacion" property="organizacionId"/>&accion=mover', 'moverOrganizacion', 500, 280);
	}
	
    function listaGrafico()
    {
		var nombreForma = '?nombreForma=' + 'gestionarOrganizacionesForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onListaGrafico()';

		abrirVentanaModal('<html:rewrite action="/graficos/listaGrafico" />' + nombreForma + nombreCampoOculto + funcionCierre, 'seleccionarGraficos', '500', '575');
    }
    
    function onListaGrafico()
    {
		var respuesta = document.gestionarOrganizacionesForm.graficoSeleccionadoId.value.split("|");
		if (respuesta.length > 0)
		{
			var campos = respuesta[0].split('!,!');
			document.gestionarOrganizacionesForm.graficoSeleccionadoId.value = campos[1];
			
			var grafico = new Grafico();
			grafico.url = '<html:rewrite action="/graficos/grafico"/>';
			grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarOrganizacionesForm.graficoSeleccionadoId.value);
		}
    }

    function asistenteGrafico()
    {
		var nombreForma = '?nombreForma=' + 'gestionarOrganizacionesForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'graficoSeleccionadoId';
		var funcionCierre = '&funcionCierre=' + 'onAsistenteGrafico()';
		var xml = nombreForma + nombreCampoOculto + funcionCierre + '&funcion=Asistente';
		abrirVentanaModal('<html:rewrite action="/graficos/asistenteGrafico" />' + xml, 'asistenteGraficos', '620', '440');
    }
    
    function onAsistenteGrafico()
    {
		var grafico = new Grafico();
		grafico.url = '<html:rewrite action="/graficos/grafico"/>';
		grafico.ShowForm(true, undefined, 'General', undefined, document.gestionarOrganizacionesForm.graficoSeleccionadoId.value);
    }

	function protegerLiberarMediciones(proteger)
	{
		if (proteger == undefined)
			proteger = true;
		
		var nombreForma = '?nombreForma=' + 'gestionarOrganizacionesForm';
		var nombreCampoOculto = '&nombreCampoOculto=' + 'respuesta';
		var funcionCierre = '&funcionCierre=' + 'onProtegerLiberarMediciones()';
		var parametros = '&proteger=' + proteger;
		parametros = parametros + '&origen=1';
		parametros = parametros + '&organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		
		abrirVentanaModal('<html:rewrite action="/mediciones/protegerLiberar" />' + nombreForma + nombreCampoOculto + funcionCierre + parametros, 'protegerLiberarMediciones', '610', '560');
	}
    
    function onProtegerLiberarMediciones()
    {
    }
    
    function calcularIniciativas()
    {
    	var url = '?organizacionId=<bean:write name="organizacion" property="organizacionId"/>';
		var _object = new Calculo();

		_object.ConfigurarCalculo('<html:rewrite action="/iniciativas/configurarCalculoIniciativas" />' + url, 'calcularMediciones');
    }
    
    function gestionarCodigosEnlaces()
    {
    	window.location.href="<html:rewrite action='/codigoenlace/gestionarCodigoEnlace'/>?defaultLoader=true";    	
    }
    
    function gestionarTiposProyecto()
    {
    	window.location.href="<html:rewrite action='/tiposproyecto/gestionarTiposProyecto'/>?defaultLoader=true";    	
    }
    
    function transformarInventario()
    {
    	var nombreForma = '?nombreForma=' + 'indicadorInventarioForm';
    	abrirVentanaModal('<html:rewrite action="/indicadores/transformarInventario" />' + nombreForma ,'transformarInventario', '620', '350');
    }
    
    function configurarServicio()
    {
    	var nombreForma = '?nombreForma=' + 'editarConfiguracionSistemaForm';
    	abrirVentanaModal('<html:rewrite action="/configuracion/configuracionServicio" />' + nombreForma ,'configuracionServicio', '620', '300');
    }
    
	function activarCorreo()
	{
		activarBloqueoEspera();
		var desbloquear = confirm('<vgcutil:message key="jsp.gestionariniciativas.activar.correos"/>');
		var proteger=false;
		
		if (desbloquear){
			proteger = true;
			window.location.href='<html:rewrite action="/configuracion/salvarCorreoIniciativa"/>?activar='+ proteger;
		}else{
			desactivarBloqueoEspera();
		}
					
	}

    
</script>

<%-- Inicio del Menú --%>
<vgcinterfaz:contenedorMenuHorizontal>

	<%-- Menú: Archivo --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
			<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
			<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

	<%-- Menú: Edición --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
			<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" permisoId="ORGANIZACION_ADD" aplicaOrganizacion="true" />
			<logic:equal name="gestionarOrganizacionesForm" property="editarForma" value="true">
				<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" aplicaOrganizacion="true" />
			</logic:equal>
			<logic:notEqual name="gestionarOrganizacionesForm" property="editarForma" value="true">
				<logic:equal name="gestionarOrganizacionesForm" property="verForma" value="true">
					<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" aplicaOrganizacion="true" />
				</logic:equal>
			</logic:notEqual>
			<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" permisoId="ORGANIZACION_DELETE" aplicaOrganizacion="true" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.edicion.copiar" onclick="copiar();" permisoId="ORGANIZACION_COPY" aplicaOrganizacion="true" />
			<vgcinterfaz:botonMenu key="menu.edicion.move" onclick="move();" permisoId="ORGANIZACION_MOVE" aplicaOrganizacion="true" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedadesOrganizacion();" permisoId="" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.edicion.codigosenlace" permisoId="ORGANIZACION_CODIGO_ENLACE" aplicaOrganizacion="true" onclick="generarCodigoEnlace();" agregarSeparador="true" />
			<vgcinterfaz:menuAnidado key="menu.mediciones.proteccion" agregarSeparador="true" >
				<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.liberar" onclick="protegerLiberarMediciones(false);" permisoId="INDICADOR_MEDICION_DESPROTECCION" aplicaOrganizacion="true" />
				<vgcinterfaz:botonMenu key="menu.mediciones.proteccion.bloquear" onclick="protegerLiberarMediciones(true)" permisoId="INDICADOR_MEDICION_PROTECCION" aplicaOrganizacion="true" />
			</vgcinterfaz:menuAnidado>
			<vgcinterfaz:menuAnidado key="menu.edicion.calcular.organizacion">
				<vgcinterfaz:botonMenu key="menu.edicion.calcular.organizacion.indicadores" onclick="calcular();" permisoId="ORGANIZACION_CALCULAR_INDICADOR" aplicaOrganizacion="true" />
				<vgcinterfaz:botonMenu key="menu.edicion.calcular.organizacion.iniciativas" onclick="calcularIniciativas();" permisoId="ORGANIZACION_CALCULAR_INICIATIVA" aplicaOrganizacion="true" />			
			</vgcinterfaz:menuAnidado>
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

	<%-- Menú: Ver --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuVer" key="menu.ver">
			<vgcinterfaz:botonMenu key="menu.ver.indicadores" onclick="gestionarIndicadores();" permisoId="INDICADOR_RAIZ" />
			<vgcinterfaz:botonMenu key="menu.ver.problemas" onclick="gestionarProblemas();" permisoId="PROBLEMA" />
			<%-- 
			<vgcinterfaz:menuAnidado key="menu.ver.acciones">
				<vgcinterfaz:botonMenu key="menu.ver.acciones.pendientes.seguimiento" onclick="gestionarAccionesPendientes(1);" permisoId="SEGUIMIENTO" />
				<vgcinterfaz:botonMenu key="menu.ver.acciones.pendientes.responder" onclick="gestionarAccionesPendientes(2);" permisoId="SEGUIMIENTO" />
				<vgcinterfaz:botonMenu key="menu.ver.acciones.pendientes.aprobar" onclick="gestionarAccionesPendientes(3);" permisoId="SEGUIMIENTO" />
			</vgcinterfaz:menuAnidado>
			 --%>
			<bean:define id="menuItemIniciativa">
				<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
			</bean:define>
			<vgcinterfaz:botonMenu key="<%= menuItemIniciativa %>" onclick="gestionarIniciativas();" permisoId="INICIATIVA" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.ver.unidadesmedida" onclick="gestionarUnidadesMedida();" permisoId="UNIDAD" />
			<vgcinterfaz:botonMenu key="menu.ver.categoriasmedicion" onclick="gestionarCategoriasMedicion();" permisoId="CATEGORIA" />
			<vgcinterfaz:botonMenu key="menu.ver.responsables" onclick="gestionarResponsables();" permisoId="RESPONSABLE" aplicaOrganizacion="true" />
			<vgcinterfaz:botonMenu key="menu.ver.causas" onclick="gestionarCausas();" permisoId="CAUSA" />
			<vgcinterfaz:botonMenu key="menu.ver.estadosacciones" onclick="gestionarEstadosAcciones();" permisoId="ESTATUS" />
			<vgcinterfaz:botonMenu key="menu.ver.estatusiniciativas" onclick="gestionarEstatusIniciativas();" permisoId="INICIATIVA_ESTATUS" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.ver.seriestiempo" onclick="gestionarSeriesTiempo();" permisoId="SERIES_TIEMPO" />
			<vgcinterfaz:botonMenu key="menu.ver.tipo.proyecto" onclick="gestionarTiposProyecto();" permisoId="TIPOS" />
			<vgcinterfaz:botonMenu key="menu.ver.plancuentas" onclick="gestionarCuentas();" permisoId="IMPUTACION" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.ver.alertas" onclick="gestionarAlertas();" permisoId="ALERTA" agregarSeparador="true" />
			<logic:equal scope="session" name="cliente" value="PGN">
				<vgcinterfaz:botonMenu key="menu.ver.codigo.enlace" onclick="gestionarCodigosEnlaces();" permisoId="CODIGO_ENLACE" agregarSeparador="true" />
			</logic:equal>
			<logic:equal name="gestionarOrganizacionesForm" property="mostrarMisionVision" value="true">
				<vgcinterfaz:botonMenu key="menu.ver.vision.mision.estrategia" icon="/componentes/menu/activo.gif" onclick="gestionarOrganizaciones(false);" permisoId="ORGANIZACION" />
			</logic:equal>
			<logic:notEqual name="gestionarOrganizacionesForm" property="mostrarMisionVision" value="true">
				<vgcinterfaz:botonMenu key="menu.ver.vision.mision.estrategia" onclick="gestionarOrganizaciones(true);" permisoId="ORGANIZACION" />
			</logic:notEqual>
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

	<%-- Menú: Evaluación --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuEvaluacion" key="menu.evaluacion">
			<vgcinterfaz:menuAnidado key="menu.evaluacion.graficos">
				<vgcinterfaz:botonMenu permisoId="INDICADOR_EVALUAR_GRAFICO_PLANTILLA" key="menu.evaluacion.graficos.plantillas" onclick="listaGrafico();" />
				<vgcinterfaz:botonMenu permisoId="INDICADOR_EVALUAR_GRAFICO_ASISTENTE" key="menu.evaluacion.graficos.asistente" onclick="asistenteGrafico();" />
			</vgcinterfaz:menuAnidado>
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

	<%-- Menú: Planes --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuPlanes" key="menu.planes">
			<vgcinterfaz:botonMenu key="menu.planes.plantillas" onclick="gestionarPlantillasPlanes();" permisoId="METODOLOGIA" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.planes.planesestrategicos" onclick="gestionarPlanesEstrategicos();" permisoId="PLAN" />
			<vgcinterfaz:botonMenu key="menu.planes.planesoperativos" onclick="gestionarPlanesOperativos();" permisoId="PLAN" />
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

	<%-- Menú: Herramientas --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
			<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" permisoId="" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.herramientas.configurar.email" onclick="configurarEmail();" />
			<vgcinterfaz:botonMenu key="menu.herramientas.indicador.inventario" onclick="transformarInventario();" />
			<vgcinterfaz:botonMenu key="menu.herramientas.configurar.servicios.ind" onclick="configurarServicio();" />
			<vgcinterfaz:botonMenu key="menu.herramientas.configurar.correo.iniciativa" onclick="activarCorreo();" />
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

	<%-- Menú: Ayuda --%>
	<vgcinterfaz:contenedorMenuHorizontalItem>
		<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
			<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
			<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
		</vgcinterfaz:menuBotones>
	</vgcinterfaz:contenedorMenuHorizontalItem>

</vgcinterfaz:contenedorMenuHorizontal>
