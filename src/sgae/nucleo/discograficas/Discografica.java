package sgae.nucleo.discograficas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.Persona;

/**
 * Clase que recoge las caracter�sticas de una discogr�fica y los m�todos para
 * consultar o modificar dichas caracter�sticas.
 * 
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class Discografica {
	/** El identificador de la discogr�fica */
	private String cif;
	/** El nombre comercial */
	private String nombre;
	/** La direcci�n de la discogr�fica */
	private String direccion;
	/** La persona que la dirige */
	private Persona director = null;
	/** La lista de directores anteriores */
	private Map<String, Persona> listaDirectoresAnteriores;
	/** El total de gruposContratados de esta discogr�fica */
	private int numeroGruposContratados;
	/** El total de sueldos pagados por esta discogr�fica a todos sus grupos */
	private float masaSalarial;
	// Adem�s, mantiene dos listas de contratos
	// NOTA: la separaci�n en dos listas se debe solamente a criterios
	// de eficiencia, ya que se har�n m�s b�squedas sobre los contratos
	// activos, que por otra parte ser�n los menos
	/**
	 * Una colecci�n de los contratos activos, indexada por un identificador
	 * �nico
	 */
	private Map<String, Contrato> listaContratosActivos;
	/**
	 * Una colecci�n de los contratos terminados, indexada por un identificador
	 * �nico
	 */
	private Map<String, Contrato> listaContratosTerminados;
	/**
	 * Un contador de los contratos realizados, utilizado para generar
	 * identificadores �nicos
	 */
	private int ultimoContrato;

	/**
	 * Constructor con los campos b�sicos
	 * 
	 * @param cif
	 *            el identificador de la discografica
	 * @param nombre
	 *            el nombre comercial
	 * @param direccion
	 *            la direcci�n de la discografica
	 */
	public Discografica(String cif, String nombre, String direccion) {
		super();
		// Asigna campos b�sicos
		this.cif = cif;
		this.nombre = nombre;
		this.direccion = direccion;
		// Inicializa las colecciones de contratos
		listaContratosActivos = new HashMap<String, Contrato>();
		listaContratosTerminados = new HashMap<String, Contrato>();
		// Inicializa el contador de contratos
		ultimoContrato = 0;
		listaDirectoresAnteriores = new HashMap<String,Persona>();
	}

	/**
	 * M�todo que permite leer el CIF. NOTA: el CIF no se puede cambiar.
	 * 
	 * @return el valor del CIF de la discografica
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * M�todo que permite leer el nombre.
	 * 
	 * @return el nombre de la discografica
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * M�todo que permite cambiar el nombre.
	 * 
	 * @param nuevoNombre
	 *            el nuevo nombre de la discografica
	 */
	public void setNombre(String nuevoNombre) {
		nombre = nuevoNombre;
	}

	/**
	 * M�todo que permite leer la direcci�n.
	 * 
	 * @return la direcci�n de la discografica
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * M�todo que permite cambiar la direcci�n.
	 * 
	 * @param nuevaDireccion
	 *            la nueva direcci�n de la discografica
	 */
	public void setDireccion(String nuevaDireccion) {
		direccion = nuevaDireccion;
	}

	/**
	 * M�todo que permite leer el numero de grupos contratados.
	 * 
	 * @return el n�mero de grupos contratados por la discografica
	 */
	public int getNumeroGruposContratados() {
		return numeroGruposContratados;
	}

	/**
	 * M�todo que permite leer la masa salarial.
	 * 
	 * @return la masa salarial de la discografica
	 */
	public float getMasaSalarial() {
		return masaSalarial;
	}

	/**
	 * M�todo que permite recuperar una descripci�n breve de la discografica.
	 * 
	 * @return descripci�n textual breve de la discografica
	 */
	public String verDescripcionBreve() {
		return nombre + ", con CIF " + cif;
	}

	/**
	 * M�todo que devuelve en una �nica cadena la informaci�n completa de la
	 * discografica.
	 * 
	 * @return la descripci�n textual completa de la discografica
	 */
	public String verDescripcionCompleta() {
		return "CIF: " + cif + "\n" + "Nombre: " + nombre + "\n" + "Direcci�n: " + direccion + "\nDirector: "
				+ ((director == null) ? "sin asignar" : director.verDescripcionBreve())
				+ "\nN�mero de grupos contratados: " + numeroGruposContratados + "\nMasa salarial: " + masaSalarial
				+ "\n";
	}

	public String nuevoContrato(GrupoMusical grupoMusical, String fechaInicicio, String fechaFin, float sueldo)
			throws ExcepcionContratos {
		// Genera un identificador �nico de contrato
		String idContrato = "c" + ultimoContrato;
		// Crea la instancia
		Contrato c = new Contrato(idContrato, grupoMusical, fechaInicicio, fechaFin, sueldo);
		// Y la colecciona
		listaContratosActivos.put(idContrato, c);
		// Incrementa el identificador de contrato
		ultimoContrato++;
		// Incrementa el n�mero de empleados
		numeroGruposContratados++;
		// Incrementa la masa salarial
		masaSalarial += sueldo;
		// Devuelve el identificador del contrato reci�n creado
		return idContrato;
	}

    /**
     * M�todo que devuelve una descripci�n de los contratos vigentes de 
     * una discogr�fica.
     * @return lista  de cadenas de texto, donde cada una contiene la 
     * descripci�n de un contrato de la discogr�fica
     */
    public List<String> verContratosVigentes() {
        // Se inicializa una lista
        List<String> listado = new ArrayList<String>();

	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosActivos.values()) {
		    // A�ade al listado los detalles del contrato
		    listado.add(c.verDetalles());
	    }
        
        // Al terminar se devuelve el listado (que podr�a estar vac�o)
        return listado;
    }
    
    /**
     * M�todo que devuelve los objetos de tipo Contrato que representan
     * los contratos vigentes de la discogr�fica.
     * @return lista objetos de tipo Contrato
     */
    public List<Contrato> recuperarContratosVigentes() {
        // Se inicializa una lista
        List<Contrato> listado = new ArrayList<Contrato>();
	
	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosActivos.values()) {
		    // A�ade al listado el objeto
		    listado.add(c);
		}
	    
	    // Al terminar se devuelve el listado (que podr�a estar vac�o)
	    return listado;
    }
    
	/**
	 * M�todo que devuelve una cadena con toda la informaci�n de un contrato 
	 * vigente a partir de su identificador
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return cadena de texto con la descripci�n textual del contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del par�metro
	 *             <i>idContrato</i> no existe
	 */
	public String verContrato(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosActivos.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Delega en �l para que genere una cadena con sus detalles
			return c.verDetalles();
		} else {
			// Si el contrato no exist�a, lanzamos una excepci�n
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

	/**
	 * M�todo que devuelve el objeto que representa a un contrato vigente a partir de su
	 * identificador.
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return un objeto de tipo contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del par�metro
	 *             <i>idContrato</i> no existe
	 */
	public Contrato recuperarContrato(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosActivos.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Devuelve el objeto
			return c;
		} else {
			// Si el contrato no exist�a, lanzamos una excepci�n
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

    /**
     * M�todo que devuelve una descripci�n de los contratos terminados de 
     * una discogr�fica.
     * @return lista  de cadenas de texto, donde cada una contiene la 
     * descripci�n de un contrato de la discogr�fica
     */
    public List<String> verContratosTerminados() {
        // Se inicializa una lista
        List<String> listado = new ArrayList<String>();

	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosTerminados.values()) {
		    // A�ade al listado los detalles del contrato
		    listado.add(c.verDetalles());
	    }
        
        // Al terminar se devuelve el listado (que podr�a estar vac�o)
        return listado;
    }
    
    /**
     * M�todo que devuelve los objetos de tipo Contrato que representan
     * los contratos terminados de la discogr�fica.
     * @return lista objetos de tipo Contrato
     */
    public List<Contrato> recuperarContratosTerminados() {
        // Se inicializa una lista
        List<Contrato> listado = new ArrayList<Contrato>();
	
	    // Recorre la lista de contratos terminados
	    for(Contrato c : listaContratosTerminados.values()) {
		    // A�ade al listado el objeto
		    listado.add(c);
		}
	    
	    // Al terminar se devuelve el listado (que podr�a estar vac�o)
	    return listado;
    }
    
	/**
	 * M�todo que devuelve una cadena con toda la informaci�n de un contrato 
	 * terminado a partir de su identificador
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return cadena de texto con la descripci�n textual del contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del par�metro
	 *             <i>idContrato</i> no existe
	 */
	public String verContratoTerminado(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosTerminados.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Delega en �l para que genere una cadena con sus detalles
			return c.verDetalles();
		} else {
			// Si el contrato no exist�a, lanzamos una excepci�n
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

	/**
	 * M�todo que devuelve el objeto que representa a un contrato terminado a partir de su
	 * identificador.
	 * 
	 * @param idContrato
	 *            identificador del contrato
	 * @return un objeto de tipo contrato
	 * @throws ExcepcionContratos
	 *             si el contrato con identificador igual al valor del par�metro
	 *             <i>idContrato</i> no existe
	 */
	public Contrato recuperarContratoTerminado(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosTerminados.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Devuelve el objeto
			return c;
		} else {
			// Si el contrato no exist�a, lanzamos una excepci�n
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea ver.");
		}
	}

	/**
	 * M�todo que permite terminar un contrato cuyo identificador se pasa como
	 * par�metro.
	 * 
	 * @param idContrato
	 *            identificador del contrado
	 * @throws ExcepcionContratos
	 *             si el contrato no existe
	 */
	public void terminarContrato(String idContrato) throws ExcepcionContratos {
		// Recupera la instancia de contrato
		Contrato c = listaContratosActivos.get(idContrato);
		// Si existe ese contrato
		if (c != null) {
			// Delega en �l las tareas de que se termine
			c.terminar();
			// Lo saca del listado de contratos activos
			listaContratosActivos.remove(idContrato);
			// Y lo a�ade a la de los terminados
			listaContratosTerminados.put(idContrato, c);
			// Decrementa el n�mero de empleados
			numeroGruposContratados--;
			// Decrementa la masa salarial
			masaSalarial -= c.getSueldo();
		} else {
			// Si el contrato no exist�a, lanzamos una excepci�n
			throw new ExcepcionContratos(idContrato, "No se ha podido encontrar el contrato que se desea terminar.");
		}
	}

	/**
	 * M�todo que establece el director de una discogr�fica.
	 * @param director objeto de la clase Persona que ser� el nuevo director
	 */	
	public void nuevoDirector(Persona director) {
		if (this.director != null) {
			// Si el �ltimo director ya est� en la lista de directores anteriores, no se a�ade a ella
			if (!listaDirectoresAnteriores.containsKey(this.director.getDni())) {
				if (director != null) {
					// S�lo se a�ade un director a la lista de directores anteriores si 
					// el nuevo director es distinto del director actual
					if (director.getDni().compareTo(this.director.getDni()) != 0) {
						listaDirectoresAnteriores.put(this.director.getDni(), this.director)
;
					}
				} else {
					// Nuevo director vac�o
					listaDirectoresAnteriores.put(this.director.getDni(), this.director);
				}
			}
		}
		// Se fija el director: si el nuevo y el actual es el mismo esta asignaci�n 
		// no tiene efecto; si el nuevo director es null, el efecto es dejar a la 
		// discogr�fica sin director
		this.director = director;
	}

	/**
	 * M�todo que permite recuperar la informaci�n del director de la discogr�fica.
	 * @return un objeto de la clase Persona con los datos del director
	 */
	public Persona recuperarDirector() {
		return director;
	}

	/**
	 * M�todo para borrar el director de una discogr�fica.
	 */
	public void borrarDirector() {
		listaDirectoresAnteriores.put(director.getDni(), director);
		this.director = null;
	}

	/** 
	 * M�todo para listar todos los directores anteriores de una discogr�fica.
	 * @return lista de objetos de la clase @see Persona
	 */
	public List<Persona> recuperarDirectoresAnteriores() {
		// Se inicializa una lista
	    List<Persona> listado = new ArrayList<Persona>();
		
	    // Recorre la lista de contratos terminados
	    for(Persona p : listaDirectoresAnteriores.values()) {
		    // A�ade al listado el objeto
		    listado.add(p);
		}
	    // Al terminar se devuelve el listado (que podr�a estar vac�o)
	    return listado;
	}
}
