package sgae.nucleo.discograficas;

import java.util.List;
import sgae.nucleo.personas.Persona;

/**
 * Interfaz para la clase controlador de discogr�ficas.
 * 
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public interface InterfazControladorDiscograficas {

	/**
	 * M�todo que crea una nueva discogr�fica y la a�ade a la colecci�n
	 * 
	 * @param cif
	 *            cif de la discogr�fica
	 * @param nombre
	 *            nombre de la discogr�fica
	 * @param direccion
	 *            direcci�n de la discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si ya existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	void crearDiscografica(String cif, String nombre, String direccion) throws ExcepcionDiscograficas;

	/**
	 * M�todo que permite modificar una discogr�fica, recibiendo todos los
	 * campos (el CIF no puede cambiar).
	 * 
	 * @param cif
	 *            cif de la discogr�fica
	 * @param nombre
	 *            nombre de la discogr�fica
	 * @param direccion
	 *            direcci�n de la discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	void modificarDiscografica(String cif, String nombre, String direccion) throws ExcepcionDiscograficas;

	/**
	 * M�todo que devuelve una lista de discogr�ficas, con su descripci�n breve
	 * 
	 * @return lista de cadenas de texto, donde cada una contiene la descripci�n
	 *         breve de una discogr�fica
	 */
	List<String> listarDiscograficas();

	/**
	 * M�todo que devuelve una lista de objetos que representan a las
	 * discogr�ficas
	 * 
	 * @return lista de objetos de tipo Discografica
	 */
	List<Discografica> recuperarDiscograficas();

	/**
	 * M�todo que permite ver los detalles de una discogr�fica.
	 * 
	 * @param cif
	 *            cif de la discogr�fica que se desea consultar
	 * @return cadena de texto con la descripci�n completa de la discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	String verDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * M�todo que permite recuperar el objeto que representa a una discogr�fica.
	 * 
	 * @param cif
	 *            cif de la discogr�fica que se desea consultar
	 * @return objeto de tipo Discografica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	Discografica recuperarDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * M�todo que permite borrar una discogr�fica.
	 * 
	 * @param cif
	 *            de la discogr�fica que se desea borrar
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	void borrarDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * M�todo que permite ver el n�mero de grupos contratados por una
	 * discogr�fica.
	 * 
	 * @param cif
	 *            cif de la discogr�fica
	 * @return el n�mero de grupos contratados de la discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	int verNumeroGruposDeDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * M�todo que permite ver la masa salarial de una discogr�fica.
	 * 
	 * @param cif
	 *            cif de la discogr�fica
	 * @return la masa salarial de la discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si no existe una discogr�fica con un CIF igual al valor del
	 *             par�metro <i>cif</i>
	 */
	float verMasaSalarialDeDiscografica(String cif) throws ExcepcionDiscograficas;

	/**
	 * M�todo que permite contratar a un grupo musical
	 * 
	 * @param cifDiscografica
	 *            CIF de la discogr�fica
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @param fechaInicio
	 *            fecha de inicio del contrato
	 * @param fechaFin
	 *            fecha de finalizaci�n del contrato
	 * @param sueldo
	 *            sueldo asociado al contrato
	 * @return identificador �nico del nuevo contrato
	 * @throws ExcepcionContratos
	 *             si el grupo musical cuyo CIF toma el valor del par�metro
	 *             <i>cifGrupoMusical</i> ya est� contratado o no existe, o si
	 *             la discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe
	 */
	String nuevoContrato(String cifDiscografica, String cifGrupoMusical, String fechaInicio, String fechaFin,
			float sueldo) throws ExcepcionContratos;

	/**
	 * M�todo que permite terminar un contrato de una discogr�fica
	 *  
	 * @param cifDiscografica CIF de la discogr�fica
	 * @param idContrato identificador  
	 * @throws ExcepcionContratos
	 *             si la discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe o no tiene asociado el contrato
	 *             con identificadro <i>idContrato</i>
	 */
	void terminarContrato(String cifDiscografica, String idContrato) throws ExcepcionContratos;

	/**
	 * M�todo que permite recuperar un contrato de una discogr�fica
	 *  
	 * @param cifDiscografica CIF de la discogr�fica
	 * @param idContrato identificador  del contrato
	 * @return un objeto de la clase Contrato
	 * @throws ExcepcionContratos
	 *             si la discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe o si el contrato
	 *             con identificador <i>idContrato</i> no existe
	 */
	Contrato recuperarContrato(String cifDiscografica, String idContrato) throws ExcepcionContratos;

	/**
	 * M�todo que permite recuperar una descripci�n textual de un contrato de una 
	 * discogr�fica.
	 *  
	 * @param cifDiscografica CIF de la discogr�fica
	 * @param idContrato identificador del contrato  
	 * @return una representaci�n textual de un Contrato
	 * @throws ExcepcionContratos
	 *             si la discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe o si el contrato
	 *             con identificador <i>idContrato</i> no existe
	 */
	String verContrato(String cifDiscografica, String idContrato) throws ExcepcionContratos;

	/**
	 * M�todo que devuelve una descripci�n del contrato actual de un grupo
	 * musical.
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return descripci�n textual del contrato
	 * @throws ExcepcionContratos
	 *             si no hay un contrato activo para el grupo musical con CIF
	 *             igual al valor del par�metro <i>cifGrupoMusical</i> o dicho
	 *             grupo no existe
	 */
	String verContratoActualDeGrupoMusical(String cifGrupoMusical) throws ExcepcionContratos;

	/**
	 * M�todo que devuelve el contrato actual de un grupo musical.
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return el contrato
	 * @throws ExcepcionContratos
	 *             si no hay un contrato activo para el grupo musical con CIF
	 *             igual al valor del par�metro <i>cifGrupoMusical</i> o dicho
	 *             grupo no existe
	 */
	Contrato recuperarContratoActualDeGrupoMusical(String cifGrupoMusical) throws ExcepcionContratos;

	/**
	 * M�todo que devuelve una descripci�n de los contratos anteriores de un
	 * grupo musical
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return lista de cadenas de texto, donde cada una contiene la descripci�n
	 *         de un contrato del grupo musical (puede estar vac�a)
	 */
	List<String> verContratosAnterioresDeGrupoMusical(String cifGrupoMusical);

	/**
	 * M�todo que devuelve una lista de los contratos anteriores de un
	 * grupo musical
	 * 
	 * @param cifGrupoMusical
	 *            CIF del grupo musical
	 * @return lista de contratos anteriores del grupo musical (puede estar vac�a)
	 */
	List<Contrato> recuperarContratosAnterioresDeGrupoMusical(String cifGrupoMusical);

	/**
	 * M�todo que devuelve una descripci�n de los contratos vigentes de una
	 * compa��a discogr�fica
	 * 
	 * @param cifDiscografica CIF de la compa��a discogr�fica
	 * @return lista de cadenas de texto, donde cada una contiene la descripci�n
	 *         de un contrato
	 * @throws ExcepcionDiscograficas
	 *             si la compa��a discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<String> verContratosVigentesDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * M�todo que devuelve una lista de los contratos vigentes de una
	 * cimpa��a discogr�fica
	 * 
	 * @param cifDiscografica CIF de la compa��a discogr�fica
	 * @return lista de contratos vigentes de la compa�ia discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si la compa�ia discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<Contrato> recuperarContratosVigentesDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * M�todo que devuelve una descripci�n de los contratos anteriores de una
	 * compa��a discogr�fica
	 * 
	 * @param cifDiscografica CIF de la compa��a discogr�fica
	 * @return lista de cadenas de texto, donde cada una contiene la descripci�n
	 *         de un contrato
	 * @throws ExcepcionDiscograficas
	 *             si la compa��a discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<String> verContratosAnterioresDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * M�todo que devuelve una lista de los contratos anteriores de una
	 * compa��a discogr�fica
	 * 
	 * @param cifDiscografica CIF de la compa��a discogr�fica
	 * @return lista de contratos anteriores de la compa�ia discogr�fica
	 * @throws ExcepcionDiscograficas
	 *             si la compa�ia discogr�fica con CIF igual al valor del par�metro
	 *             <i>cifDiscografica</i> no existe
	 */
	List<Contrato> recuperarContratosAnterioresDeDiscografica(String cifDiscografica) throws ExcepcionDiscograficas;

	/**
	 * M�todo que establece el director de una discogr�fica.
	 * @param cif cif de la discogr�fica
	 * @param director objeto de la clase Persona que ser� el nuevo director
	 * @throws ExcepcionDiscograficas si no existe una discogr�fica con un CIF igual al valor del par�metro <i>cif</i>
	 */	
	public void nuevoDirector(String cif, Persona director) throws ExcepcionDiscograficas;
	
	/**
	 * M�todo que permite recuperar la informaci�n del director de la discogr�fica.
	 * @param cif cif de la discogr�fica
	 * @return un objeto de la clase Persona con los datos del director
	 * @throws ExcepcionDiscograficas si no existe una discogr�fica con un CIF igual al valor del par�metro <i>cif</i>
	 */
	public Persona recuperarDirector(String cif) throws ExcepcionDiscograficas;
	
	/**
	 * M�todo para borrar el director de una discogr�fica.
	 * @param cif cif de la discogr�fica
	 * @throws ExcepcionDiscograficas si no existe una discogr�fica con un CIF igual al valor del par�metro <i>cif</i>
	 */
	public void borrarDirector(String cif) throws ExcepcionDiscograficas;
	
	/** 
	 * M�todo para listar todos los directores anteriores de una discogr�fica.
	 * @param cif cif de la discogr�fica
	 * @return lista de objetos de la clase @see Persona
	 * @throws ExcepcionDiscograficas si no existe una discogr�fica con un CIF igual al valor del par�metro <i>cif</i>
	 */
	public List<Persona> recuperarDirectoresAnteriores(String cif) throws ExcepcionDiscograficas;
}
