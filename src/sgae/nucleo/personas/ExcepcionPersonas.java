package sgae.nucleo.personas;

/**
 * Clase de excepci�n relativa a personas.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionPersonas extends Exception {
	/** Serial ID que debe ponerse */
	private static final long serialVersionUID = 1L;
	/** El DNI de la persona sobre el que se ha hecho una operaci�n 
	    fallida*/
	private String dniPersona;
	/** El tipo de operaci�n que ha fallado */
	private String causaFallo;
	
	
	/** 
	 * Constructor con par�metros.
	 * @param dniPersona DNI de la persona
	 * @param causaFallo texto que describe la causa del fallo
	 */
	public ExcepcionPersonas(String dniPersona, String causaFallo) {
		super("Persona: " + dniPersona + ", error: " + causaFallo + "\n");
		this.dniPersona = dniPersona;
		this.causaFallo = causaFallo;
	}
	
	/**
	 * M�todo que devuelve el DNI de la persona.
	 * @return la cadena de texto con el DNI de la persona
	 */
	public String getDniPersona() {
		return dniPersona;
	}
	
	/**
	 * M�todo que devuelve la causa del fallo.
	 * @return la cadena de texto con la causa del fallo
	 */
	public String getCausaFallo() {
		return causaFallo;
	}	
}
