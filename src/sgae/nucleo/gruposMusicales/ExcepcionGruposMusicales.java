package sgae.nucleo.gruposMusicales;

/**
 * Clase de excepci�n relacionada con los grupos musicales.
 * @author Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionGruposMusicales extends Exception {
	/** Serial ID que debe ponerse */
	private static final long serialVersionUID = 1L;
	/** El CIF de la Discografica sobre el que se ha hecho una operaci�n fallida */
	private String cif;
	/** El tipo de operaci�n que ha fallado */
	private String causaFallo;
	
	/**
	 * Constructor con par�metros.
	 * @param cif cif del grupo musical
	 * @param causaFallo descripci�n textual del fallo
	 */
	public ExcepcionGruposMusicales(String cif, String causaFallo) {
		super("Grupo musical: " + cif + ", error: " + causaFallo + "\n");
		this.cif = cif;
		this.causaFallo = causaFallo;
	}
	
	/**
	 * M�todo que devuelve el CIF del grupo musical.
	 * @return el CIF del grupo musical
	 */
	public String getCif() {
		return cif;
	}
	
	/**
	 * M�todo que devuelve la causa del fallo
	 * @return la causa del fallo
	 */
	public String getCausaFallo() {
		return causaFallo;
	}
}
