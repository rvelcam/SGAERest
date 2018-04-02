package sgae.nucleo.discograficas;

/**
 * Clase de excepci�n relacionada con los contratos.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class ExcepcionContratos extends Exception {
    /** Serial ID que debe ponerse */
    private static final long serialVersionUID = 1L;
    /** El id del contrato sobre el que se ha hecho una operaci�n fallida */
    private String idContrato;
    /** El tipo de operaci�n que ha fallado */
    private String causaFallo;

    /**
     * Constructor con par�metros.
     * @param idContrato identificador del contrato
     * @param causaFallo descripci�n textual del fallo
     */
    public ExcepcionContratos(String idContrato, String causaFallo) {
        super();
        this.idContrato = idContrato;
        this.causaFallo = causaFallo;
    }

    /**
     * M�todo que devuelve el identificador del contrato.
     * @return el identificador del contrato
     */
    public String getIdContrato() {
        return idContrato;
    }
    
    /**
     * M�todo que devuelve la causa del fallo
     * @return la causa del fallo
     */
    public String getCausaFallo() {
        return causaFallo;
    }
}
