package sgae.util;

import java.text.ParseException;

public class Utils {
	// From
	// https://codereview.stackexchange.com/questions/69827/isnullorwhitespace-check-for-java
	/**
	 * Funci�n que comprueba si una cadena est� vac�a, contiene s�lo espacios o es un puntero null
	 * @param value cadena a comprobar
	 * @return true si la cadena est� vac�a, contiene s�lo espacios o es un puntero null
	 */
	public static boolean isStringNullOrEmptyOrWhitespace(CharSequence value) {
		if (value == null) {
			return true;
		}

		if (value.toString().equals("")) {
			return true;
		}

		/*
		 * From
		 * https://stackoverflow.com/questions/3247067/how-do-i-check-that-a-
		 * java-string-is-not-all-whitespaces trim() and other mentioned regular
		 * expression do not work for all types of whitespaces i.e: Unicode
		 * Character 'LINE SEPARATOR'
		 * http://www.fileformat.info/info/unicode/char/2028/index.htm Java
		 * functions Character.isWhitespace() covers all situations.
		 */
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isWhitespace(value.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Funci�n que comprueba si una cadena est� vac�a, contiene s�lo espacios o es un puntero null y lanza 
	 * una excepci�n ParseExeption en este caso (en caso contrario, devuelve elmismo valor de entrada)
	 * @param value cadena de texto a comprobar
	 * @param errorMsg cadena con el mensaje espec�fico de error que se asociar� a la excepci�n ParseException
	 * @return el valor de la cadena de entrada si contiene alg�n caracter distinto de espacio, tabulador o retorno de l�nea
	 * @throws ParseException si la cadena contenida en el par�metro <i>value</i>est� vac�a, contiene s�lo espacios o es un puntero null
	 */
	public static String testStringNullOrEmptyOrWhitespaceAndSet(String value, String errorMsg) throws ParseException {
		if (isStringNullOrEmptyOrWhitespace(value)) {
			throw new ParseException(errorMsg, 0);
		}
		return value;
	}
}
