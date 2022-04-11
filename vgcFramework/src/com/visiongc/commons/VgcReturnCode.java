package com.visiongc.commons;

/**
 * Clase que representa los valores de resultados posibles de llamadas a métodos
 * de Servicios de Negocios
 * 
 *  (14/05/2012)
 * 
 */
public class VgcReturnCode 
{
	public static final int S_OK = 0;
	
	public static final int DB_OK = 10000;
	public static final int DB_NOT_FOUND = 10001;
	public static final int DB_LOCKED = 10002;
	public static final int DB_PK_AK_VIOLATED = 10003;
	public static final int DB_FK_VIOLATED = 10004;
	public static final int DB_READ_ONLY = 10005;
	public static final int DB_RELATED = 10006;
	public static final int DB_CANCELED = 10007;

	public static final int MAIL_SEND_OK = 10000;
	public static final int MAIL_SEND_FAILED = 10001;
	public static final int MAIL_SEND_NOT_FROM = 10002;
	public static final int MAIL_SEND_FROM_INVALID = 10003;
	public static final int MAIL_SEND_NOT_TO = 10004;
	public static final int MAIL_SEND_INVALID_TO = 10005;
	public static final int MAIL_AUTHENTICATION_FAILED = 10006;
	public static final int MAIL_NOT_CONFIGURED = 10020;
	
	public static final int LICENCIA_SAVE_OK = 10000;
	public static final int LICENCIA_SAVE_ERROR = 10001;
	public static final int LICENCIA_SAVE_FOLDER_EMPTY = 10002;
	
	public static final int CONFIGURACION_READ_OK = 10000;
	public static final int CONFIGURACION_NO_CONNECT = 10001;
	public static final int CONFIGURACION_NO_SAVE = 10002;
	
	public static final Byte FORM_READY = 0;
	public static final Byte FORM_READY_ERROR = 1;
	public static final Byte FORM_SAVE = 2;
	public static final Byte FORM_SAVE_ERROR = 3;

	/**
	 * Método que devuelve una String que representa el código de retorno
	 * 
	 * @param code:
	 *            Númeral que identifica al Código de Retorno
	 * @return String que representa el Código de Retorno
	 */
	public static String getCode(int code) 
	{
		if (code == S_OK) 
			return "S_OK";

		if (code == DB_OK) 
			return "DB_OK";
		if (code == DB_NOT_FOUND) 
			return "DB_NOT_FOUND";
		if (code == DB_LOCKED) 
			return "DB_LOCKED";
		if (code == DB_PK_AK_VIOLATED) 
			return "DB_PK_AK_VIOLATED";
		if (code == DB_FK_VIOLATED) 
			return "DB_FK_VIOLATED";
		if (code == DB_READ_ONLY) 
			return "DB_READ_ONLY";
		if (code == DB_RELATED) 
			return "DB_RELATED";
		if (code == DB_CANCELED) 
			return "DB_CANCELED";

		return Integer.toString(code);
	}
}