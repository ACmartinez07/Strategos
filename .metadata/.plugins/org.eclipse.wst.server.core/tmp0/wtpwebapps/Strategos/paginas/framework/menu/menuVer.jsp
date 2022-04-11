<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (13/05/2012) -->

<vgcinterfaz:menuBotones id="menuVerUsuaios" key="menu.ver">
	<vgcinterfaz:botonMenu key="menu.ver.usuarios" onclick="gestionarUsuarios();" permisoId="USUARIO" />
	<vgcinterfaz:botonMenu key="menu.ver.grupos" onclick="gestionarGrupos();" permisoId="GRUPO" />
	<vgcinterfaz:botonMenu key="menu.ver.iniciosesion" onclick="gestionarInicioSesion();" permisoId="CONFIGURACION_SET" />
	<vgcinterfaz:botonMenu key="menu.ver.sesiones" onclick="gestionarSesionesUsuario();" permisoId="SESION_USUARIO" />
	<vgcinterfaz:botonMenu key="menu.ver.bloqueos" onclick="gestionarBloqueos();" permisoId="SESION_BLOQUEO" />
	<vgcinterfaz:botonMenu key="menu.ver.auditorias" onclick="gestionarAuditorias();" permisoId="AUDITORIA" />
	<vgcinterfaz:botonMenu key="menu.ver.errores" onclick="gestionarErrores();" permisoId="ERROR" />
	<vgcinterfaz:botonMenu key="menu.ver.servicios" onclick="gestionarServicios();" permisoId="SERVICIO" />
</vgcinterfaz:menuBotones>
