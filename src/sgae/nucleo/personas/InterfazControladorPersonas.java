package sgae.nucleo.personas;

import java.text.ParseException;
import java.util.List;

/**
 * Interfaz para la clase controlador para las personas.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 *
 */
public interface InterfazControladorPersonas {

	/**
	 * M�todo que crea una nueva persona y la a�ade a la colecci�n.
	 * @param dni DNI de la persona
	 * @param nombre nombre de la persona
	 * @param apellidos apellidos de la persona 
	 * @param fechaNacimiento fecha de nacimiento de la persona en formato
	 * dd-MM-yyyy
	 * @throws ParseException si el par�metro <i>fechaNacimiento</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionPersonas si ya existe una persona con un DNI igual 
	 * al valor del campo <i>dni</i>
	 */
	void crearPersona(String dni, String nombre, String apellidos, String fechaNacimiento)
			throws ParseException, ExcepcionPersonas;

	/**
	 * M�todo que permite modificar una persona, recibiendo todos los 
	 * campos (el DNI no puede cambiar).
	 * @param dni DNI de la persona
	 * @param nombre nombre de la persona
	 * @param apellidos apellidos de la persona 
	 * @param fechaNacimiento fecha de nacimiento de la persona en formato
	 * dd-MM-yyyy
	 * @throws ParseException si el par�metro <i>fechaNacimiento</i> no tiene 
	 * el formato dd-MM-yyyy
	 * @throws ExcepcionPersonas si no existe una persona con un DNI igual 
	 * al valor del campo <i>dni</i>
	 */
	void modificarPersona(String dni, String nombre, String apellidos, String fechaNacimiento)
			throws ParseException, ExcepcionPersonas;

	/**
	 * M�todo que devuelve una lista de personas, con su descripci�n breve.
	 * @return una lista donde cada elemento es una cadena de texto con la 
	 * descripci�n breve de una persona
	 */
	List<String> listarPersonas();

	/**
	 * M�todo que permite obtener una colecci�n de objetos que 
	 * representan a todas las personas existentes en este controlador.
	 * @return una lista donde cada elemento es un objeto de la clase 
	 * Persona
	 */
	List<Persona> recuperarPersonas();

	/**
	 * M�todo que permite ver los detalles de una persona en una cadena
	 * de texto.
	 * @param dni DNI de la persona
	 * @return cadena de texto con la descripci�n completa de la persona
	 * @throws ExcepcionPersonas si no existe una persona con un valor
	 * de DNI igual al par�metro <i>dni</i>
	 */
	String verPersona(String dni) throws ExcepcionPersonas;

	/**
	 * M�todo que permite recuperar el objeto que representa a una persona.
	 * @param dni DNI de la persona
	 * @return un objeto de tipo Persona
	 * @throws ExcepcionPersonas si no existe una persona con un valor
	 * de DNI igual al par�metro <i>dni</i>
	 */
	Persona recuperarPersona(String dni) throws ExcepcionPersonas;

	/**
	 * M�todo que permite borrar una persona.
	 * @param dni DNI de la persona
	 * @throws ExcepcionPersonas si no existe una persona con un valor
	 * de DNI igual al par�metro <i>dni</i>
	 */
	void borrarPersona(String dni) throws ExcepcionPersonas;

	/**
	 * M�todo que devuelve el n�mero total de personas mantenido por este 
	 * controlador.
	 * @return el n�mero total de personas
	 */
	int verNumeroPersonas();

}