package sgae.nucleo.personas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que recoge las caracter�sticas de una persona y los m�todos para 
 * consultar o modificar dichas caracter�sticas.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT. UVa
 * @version 1.0
 */
public class Persona {
    /** El DNI de la persona */
    private String dni;
    /** El nombre de la persona */
    private String nombre;
    /** Los apellidos de la persona */
    private String apellidos;
    /** La fecha de nacimiento de la persona */
    private Date fechaNacimiento;
    
    /** 
     * Constructor con los atributos que se pueden inicializar de partida. 
     * Nota: la fecha de nacimiento se pasa como una cadena con el formato 
     * dd-MM-yyyy .
     *
     * @param dni El DNI de la persona
     * @param nombre El nombre de la persona
     * @param apellidos Los apellidos de la persona
     * @param fechaNacimiento La fecha de nacimiento de la persona, se 
     * pasa como una cadena dd-MM-yyyy
     * @throws ParseException si el par�metro <i>fechaNacimiento</i> no tiene 
     * el formato dd-MM-yyyy
     */
    public Persona(String dni, String nombre, String apellidos,	
		   String fechaNacimiento) 
	throws ParseException {
	super();
	// Inicializa con valores pasados como par�metros
	this.dni = dni;
	this.nombre = nombre;
	this.apellidos = apellidos;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	dateFormat.setLenient(false);
	this.fechaNacimiento = dateFormat.parse(fechaNacimiento);
    }
    
    /**
     * M�todo que lee el DNI.
     * NOTA: El DNI s�lo se puede leer, no escribir.
     * @return el valor del DNI
     */
    public String getDni() {
	return dni;
    }
    
    /**
     * M�todo que lee el nombre.
     * @return el nombre de la persona
     */
    public String getNombre() {
	return nombre;
    }
    
    /**
     * M�todo que modifica el nombre.
     * @param nuevoNombre el nuevo nombre para la persona
     */
    public void setNombre(String nuevoNombre) {
	nombre = nuevoNombre;
    }
    
    /**
     * M�todo que lee los apellidos.
     * @return los apellidos de la persona
     */
    public String getApellidos() {
	return apellidos;
    }
    
    /**
     * M�todo que modifica los apellidos.
     * @param nuevosApellidos los nuevos apellidos de la persona
     */
    public void setApellidos(String nuevosApellidos) {
	apellidos = nuevosApellidos;
    }
    
    /** 
     * M�todo que devuelve la fecha de nacimiento como una cadena.
     * @return la fecha de nacimiento en formato dd-MM-yyyy
     */
    public String getFechaNacimiento() {
	return new SimpleDateFormat("dd-MM-yyyy").format(fechaNacimiento);
    }
    
    /**
     * M�todo que cambia la fecha a partir de una cadena.
     * @param nuevaFechaNacimiento la nueva fecha de nacimiento de la persona
     * @throws ParseException si el par�metro <i>fechaNacimiento</i> no tiene 
     * el formato dd-MM-yyyy
     */
    public void setFechaNacimiento(String nuevaFechaNacimiento) 
	throws ParseException {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	dateFormat.setLenient(false);
	this.fechaNacimiento = dateFormat.parse(nuevaFechaNacimiento);
    }
    
    /**
     * M�todo que devuelve en una �nica cadena la informaci�n b�sica de la 
     * persona.
     * @return la descripci�n breve de la persona
     */
    public String verDescripcionBreve() {
	return "DNI: " + dni + "\tNombre: " + nombre + "\tApellidos: " + apellidos + "\n";
    }
    
    /**
     * M�todo que devuelve en una �nica cadena la informaci�n completa de 
     * la persona.
     * @return la descripci�n completa de la persona
     */
    public String verDescripcionCompleta() {
	return "DNI: " + dni + "\t" +
	    "Nombre: " + nombre + "\t" +
	    "Apellidos: " + apellidos + "\t" +
	    "fecha de nacimiento: " + getFechaNacimiento() + "\n";
    }
}
