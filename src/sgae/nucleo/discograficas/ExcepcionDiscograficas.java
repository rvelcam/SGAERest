package sgae.nucleo.discograficas;

public class ExcepcionDiscograficas extends Exception {
    /** Serial ID que debe ponerse */
    private static final long serialVersionUID = 1L;
    /** El cif de la discogr�fica sobre el que se ha hecho una operaci�n fallida */
    private String cif;
    /** El tipo de operaci�n que ha fallado */
    private String causaFallo;

    /**
     * Constructor con par�metros.
     * @param cif identificador de la discogr�fica
     * @param causaFallo descripci�n textual del fallo
     */
    public ExcepcionDiscograficas(String cif, String causaFallo) {
        super();
        this.cif = cif;
        this.causaFallo = causaFallo;
    }

    /**
     * M�todo que devuelve el identificador del contrato.
     * @return el identificador del contrato
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
