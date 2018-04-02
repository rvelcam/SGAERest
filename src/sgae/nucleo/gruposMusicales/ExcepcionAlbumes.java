package sgae.nucleo.gruposMusicales;

/**
 * Clase de excepci�n relacionada con los �lbumes.
 * @author Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionAlbumes extends Exception {
	/** Serial ID que debe ponerse */
	private static final long serialVersionUID = 1L;
	/** El id del �lbum sobre el que se ha hecho una operaci�n fallida */
	private String id;
	/** El tipo de operaci�n que ha fallado */
	private String causaFallo;
	
	/**
	 * Constructor con par�metros.
	 * @param id id del �lbum
	 * @param causaFallo descripci�n textual del fallo
	 */
	public ExcepcionAlbumes(String id, String causaFallo) {
		super("�lbum: " + id + ", error: " + causaFallo + "\n");
		this.id = id;
		this.causaFallo = causaFallo;
	}
	
	/**
	 * M�todo que devuelve el id del �lbum.
	 * @return el id del �lbum
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * M�todo que devuelve la causa del fallo
	 * @return la causa del fallo
	 */
	public String getCausaFallo() {
		return causaFallo;
	}
}
