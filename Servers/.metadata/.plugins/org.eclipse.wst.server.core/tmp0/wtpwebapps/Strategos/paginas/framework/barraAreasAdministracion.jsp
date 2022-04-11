<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (09/09/2012) -->

<%-- Funciones JavaScript locales de la página Jsp --%>
<script type="text/javascript">	

	function gestionarUsuarios() 
	{
		window.location.href='<html:rewrite action="/framework/usuarios/gestionarUsuarios" />?defaultLoader=true';
	}

	function gestionarGrupos() 
	{
		window.location.href='<html:rewrite action="/framework/grupos/gestionarGrupos" />?defaultLoader=true';
	}
	
	function gestionarInicioSesion()
	{
		window.location.href='<html:rewrite action="/framework/iniciosesion/configurarInicioSesion" />?defaultLoader=true';
	}

	function gestionarSesionesUsuario() 
	{
		window.location.href='<html:rewrite action="/framework/sesionesusuario/gestionarSesionesUsuario" />?defaultLoader=true';
	}

	function gestionarBloqueos() 
	{
		window.location.href='<html:rewrite action="/framework/bloqueos/gestionarBloqueos" />?defaultLoader=true';
	}

	function gestionarAuditorias() 
	{
		window.location.href='<html:rewrite action="/framework/auditorias/gestionarAuditorias" />?defaultLoader=true';
	}
	
	function gestionarAuditoriasMedicion() 
	{
		window.location.href='<html:rewrite action="/framework/auditorias/gestionarAuditoriasMedicion" />?defaultLoader=true';
	}

	function gestionarErrores() 
	{
		window.location.href='<html:rewrite action="/framework/errores/gestionarErrores" />?defaultLoader=true';
	}

	function gestionarServicios() 
	{
		window.location.href='<html:rewrite action="/framework/servicio/gestionarServicios" />?defaultLoader=true';
	}
	
	function gestionarAlertas()
	{
		window.location.href='<html:rewrite action="/alertas/gestionarAlertas" />?defaultLoader=true';
	}
</script>

<vgcinterfaz:barraAreas width="100px" nombre="administracion">

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="usuarios" permisoId="USUARIO" onclick="gestionarUsuarios();" urlImage="/paginas/framework/imagenes/usuarios.gif">
		<vgcutil:message key="barraareas.administracion.usuarios" />
	</vgcinterfaz:botonBarraAreas>

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="grupos" permisoId="GRUPO" onclick="gestionarGrupos();" urlImage="/paginas/framework/imagenes/grupos.gif">
		<vgcutil:message key="barraareas.administracion.grupos" />
	</vgcinterfaz:botonBarraAreas>
	
	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="inicioSesion" permisoId="CONFIGURACION_SET" onclick="gestionarInicioSesion();" urlImage="/paginas/framework/imagenes/configlogin.gif">
		<vgcutil:message key="barraareas.administracion.iniciosesion" />
	</vgcinterfaz:botonBarraAreas>

	<!-- 
	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="sesionesUsuario" permisoId="ADMIN_USUARIO" onclick="gestionarSesionesUsuario();" urlImage="/paginas/framework/imagenes/sesiones.gif">
		<vgcutil:message key="barraareas.administracion.sesionesusuario" />
	</vgcinterfaz:botonBarraAreas>

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="bloqueos" permisoId="ADMIN_USUARIO" onclick="gestionarBloqueos();" urlImage="/paginas/framework/imagenes/bloqueos.gif">
		<vgcutil:message key="barraareas.administracion.bloqueos" />
	</vgcinterfaz:botonBarraAreas>
	 -->

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="errores" permisoId="ERROR" onclick="gestionarErrores();" urlImage="/paginas/framework/imagenes/errores.gif">
		<vgcutil:message key="barraareas.administracion.errores" />
	</vgcinterfaz:botonBarraAreas>

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="auditorias" permisoId="AUDITORIA" onclick="gestionarAuditorias();" urlImage="/paginas/framework/imagenes/auditorias.gif">
		<vgcutil:message key="barraareas.administracion.auditorias" />
	</vgcinterfaz:botonBarraAreas>
	
	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="auditoriasMedicion" permisoId="AUDITORIA" onclick="gestionarAuditoriasMedicion();" urlImage="/paginas/framework/imagenes/auditorias.gif">
		<vgcutil:message key="barraareas.administracion.auditorias.medicion" />
	</vgcinterfaz:botonBarraAreas>

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" nombre="servicios" permisoId="SERVICIO" onclick="gestionarServicios();" urlImage="/paginas/framework/imagenes/service.png" agregarSeparador="true">
		<vgcutil:message key="barraareas.administracion.servicios" />
	</vgcinterfaz:botonBarraAreas>

	<vgcinterfaz:botonBarraAreas aplicaOrganizacion="false" permisoId="ORGANIZACION" onclick="inicio();" nombre="regresar" urlImage="/paginas/framework/imagenes/inicio.gif">
		<vgcutil:message key="barraareas.administracion.regresar" />
	</vgcinterfaz:botonBarraAreas>

</vgcinterfaz:barraAreas>
