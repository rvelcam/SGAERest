package sgae.nucleo.discograficas;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.util.Utils;

/**
 * Clase que recoge las caracter�sticas de un contrato y los m�todos para 
 * consultar o modificar dichas caracter�sticas.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class Contrato {
    /** Un identificador �nico */
    private String id;
    /** El grupo musica que es contratado */
    private GrupoMusical grupoMusical;
    /** Fecha de inicio */
    private String fechaInicio;
    /** Fecha de finalizaci�n */
    private String fechaFin;
    /** El sueldo que se percibe */
    private float sueldo;
    /** Si es false, �ste fue un contrato pasado pero est� rescindido */
    private boolean activo;

    /**
     * Constructor que recibe como par�metros la discogr�fica, el grupo musical, 
     * el sueldo, la fecha de inicio del contrato y la fecha de finalizaci�n.
     * Por defecto el contrato se crea como activo.
     * @param id un identificador �nico
     * @param grupoMusical el grupo musical que es contratado
     * @param fechaInicio fecha de inicio del contrato
     * @param fechaFin fecha de finalizaci�n del contrato
     * @param sueldo el sueldo que se percibe
     */
    public Contrato(String id, GrupoMusical grupoMusical,
		    String fechaInicio, String fechaFin, float sueldo) {
        super();
        // Inicializa seg�n los par�metros
        this.id = Utils.testStringNullOrEmptyOrWhitespaceAndSet(id, "Campo id vac�o");
        this.grupoMusical = grupoMusical;
        this.fechaInicio = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaInicio, "Campo fecha inicio vac�o");
        this.fechaFin = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaFin, "Campo fecha fin vac�o");
        this.sueldo = sueldo;
        // Por defecto un contrato se crea como activo
        this.activo = true;
        // Decimos al  grupo musical que est� contratado
        grupoMusical.contrata();
    }

    /**
     * M�todo que devuelve el id del contrato.
     * @return el identificador del contrato
     */
    public String getId() {
        return id;
    }

    /**
     * M�todo que devuelve el sueldo del contrato.
     * @return el sueldo del contrato
     */
    public float getSueldo() {
        return sueldo;
    }
    
    /**
     * M�todo que modifica el sueldo del contrato.
     * @param sueldo el nuevo sueldo del contrato
     */
    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

   /**
     * M�todo que devuelve la fecha de inicio del contrato.
     * @return texto con la fecha de inicio del contrato
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * M�todo que modifica la fecha de inicio del contrato.
     * @param fechaInicio la nueva fecha de inicio del contrato
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaInicio, "Campo fecha inicio vac�o");
    }

    /**
     * M�todo que devuelve la fecha de finalizaci�n del contrato.
     * @return texto con la fecha de finalizaci�n del contrato
     */
    public String getFechaFinalizacion() {
        return fechaFin;
    }

    /**
     * M�todo que modifica la fecha de inicio del contrato.
     * @param fechaFinalizacion la nueva fecha de finalizaci�n del contrato
     */
    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFin = Utils.testStringNullOrEmptyOrWhitespaceAndSet(fechaFinalizacion, "Campo fecha fin vac�o");
    }

   /** 
     * M�todo que informa de si el contrato est� activo.
     * @return true si el contrato est� activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * M�todo que termina el contrato.
     */
    public void terminar() {
        // Decimos al grupoMusical que est� parado
        grupoMusical.despide();
        // Cambiamos el estado del contrato
        activo = false;
    }
    
    /**
     * M�todo que devuelve en una �nica cadena toda la informaci�n de un 
     * contrato.
     * @return la cadena de texto con la informaci�n completa del contrato
     */
    public String verDetalles() {
        return "Contrato con grupo musical con CIF " + grupoMusical.getCif() 
            + " por " + String.format("%.2f", sueldo) + " euros."
            + " Situaci�n: " + (activo ? "EN VIGOR" : "TERMINADO")
	    + "Fecha de inicio: " + fechaInicio
	    + "Fecha de finalizaci�n " + fechaFin;
    }

    /**
     * M�todo que comprueba si un contrato tiene a un grupo musical determinado
     * @param cifGrupoMusical identificador del grupo musical
     * @return true si el grupo musical est� asociada al contrato
     */
    public boolean tieneGrupoMusical(String cifGrupoMusical) {
        return grupoMusical.getCif().equals(cifGrupoMusical);
    }
    
    /**
     * M�todo que devuelve el identificador del grupo musical que tiene este contrato
     * @return el identificador del grupo musical que est� asociado al contrato
     */
    public String getCifGrupoMusical() {
        // Le pide al grupo musical su id y lo devuelve
        return grupoMusical.getCif();
    }
}
