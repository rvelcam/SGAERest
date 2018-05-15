package sgae.servidor.gruposMusicales;

import java.text.ParseException;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.StringUtils;
import sgae.util.generated.GrupoMusicalXML;
/**
 * Clase del recurso grupo musical del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class GrupoMusicalServerResource extends ServerResource{
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	/** objeto controlador de personas */
	private ControladorPersonas controladorPersonas;
	/** objeto del tipo String que contiene el CIF del grupo musical */
	private String CIF;
	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso GrupoMusical.
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		controladorPersonas = aplicacion.getControladorPersonas();
		this.CIF = getAttribute("grupoMusicalCIF");
	}
	/**
	 * Método que invoca la operación GET sobre el recurso GrupoMusical.
	 * @return objeto del tipo StringRepresentation que contiene la representación con la informacion
	 * del grupo musical.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical.
	 */
	@Get("txt")
	public StringRepresentation representacionTXT() throws ResourceException{
		try{
			return new StringRepresentation(controladorGruposMusicales.verGrupoMusical(CIF));
		}catch(ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	/**
	 * Método que invoca la operación GET sobre el recurso GrupoMusical.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * del grupo musical en formato xml.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical.
	 */
	@Get("xml")
	public Representation representacionXML() throws ResourceException{
		GrupoMusicalXML grupoXml = new GrupoMusicalXML();
		try {
			GrupoMusical grupoMusical = controladorGruposMusicales.recuperarGrupoMusical(this.CIF);			
			grupoXml.setCIF(grupoMusical.getCif()) ;
			grupoXml.setNombre(grupoMusical.getNombre());
			grupoXml.setFechaCreacion (grupoMusical.getFechaCreacion());			
			JaxbRepresentation<GrupoMusicalXML> result = new JaxbRepresentation<GrupoMusicalXML>(grupoXml);
			result.setFormattedOutput(true);
			return result;
		}catch (ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	/**
	 * Método que invoca la operación PUT sobre el recurso GrupoMusical. Permite almacenar o modificar 
	 * un grupo musical.
	 * @param representacion que contiene el estado que debe alcanzar este recurso.
	 * @throws ResourceException con error CLIENT_ERROR_BAD_REQUEST en el caso de que no exista la persona introducida
	 * o se haya introducido mal la fecha.
	 */
	@Put("form")
	public void guardarModificarGrupoMusical(Representation representacion) throws ResourceException{
		Form form = new Form(representacion);
		String nombre = form.getValues("nombre");
		String fechaCreacion = form.getValues("fechaCreacion");
		String miembros = form.getValues("miembros");
		String[] miembrosList=null;
		try{
			if (miembros!=null && !miembros.isEmpty()) {
				miembrosList = StringUtils.quitarEspacios(miembros.split(","));
				comprobarExistenciaMiembros(miembrosList);				
			}
			controladorGruposMusicales.crearGrupoMusical(CIF, nombre, fechaCreacion);
			if (miembrosList != null && miembrosList.length>0) {
				anadirMiembrosAGrupoMusical(miembrosList);
			}								
			setStatus(Status.SUCCESS_CREATED);
		}catch(ExcepcionGruposMusicales e){
			try{			
				if (miembrosList != null && miembrosList.length>0) {
					actualizarMiembrosGrupoMusical(miembrosList);
				}else {
					eliminarMiembrosGrupoMusical();
				}
				setStatus(Status.SUCCESS_NO_CONTENT);
			}catch(ExcepcionGruposMusicales er){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			} catch(ExcepcionPersonas e2){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}
		} catch (com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		} catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (ExcepcionPersonas e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	/**
	 * Método que invoca la operación DELETE sobre el recurso GrupoMusical. Permite eliminar grupo musical.
	 * @throws ResourceException con error CLIENT_ERROR_BAD_REQUEST en el caso de que no exista el grupo musical.
	 */
	@Delete
	public void eliminarGrupoMusical() throws ResourceException{			
		try{			
			this.controladorGruposMusicales.borrarGrupoMusical(this.CIF);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}catch (ExcepcionGruposMusicales e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} 
	}
	/**
	 * Método que actualiza los miembros de un grupo musical en función de la nueva lista de miembros.
	 * @param miembrosaActualizar array que contiene los nuevos miembros a incluir en el grupo musical.
	 * @throws ExcepcionPersonas, ExcepcionGruposMusicales si se produce un error en la actualización de los 
	 * componentes del grupo
	 */
	private void actualizarMiembrosGrupoMusical(String []miembrosaActualizar) throws ExcepcionPersonas, ExcepcionGruposMusicales{
		List<Persona> miembrosActuales = this.controladorGruposMusicales.recuperarMiembros(this.CIF);
		boolean encontrado = false;
		//Comparar la lista actual con la nueva recibida -> eliminar miembro en caso de no encontrarlo
		for (Persona miembro : miembrosActuales) {
			encontrado = false;
			for (int i=0;i<miembrosaActualizar.length;i++) {
				if (miembrosaActualizar[i].equals(miembro.getDni())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				this.controladorGruposMusicales.eliminarMiembro(this.CIF, miembro.getDni());
			}
		}
		//Comparar lista recibida con la actual -> añadir miembro en caso de que no se encuentre
		String dni=null;
		for (int i=0;i<miembrosaActualizar.length;i++) {
			encontrado = false;
			for (Persona miembro : miembrosActuales) {
				dni = miembro.getDni();
				if (miembrosaActualizar[i].equals(dni)) {
					encontrado = true;
					break;
				}				
			}
			if (!encontrado) {
				this.controladorGruposMusicales.anadirMiembro(this.CIF, miembrosaActualizar[i]);
			}
		}		
	}
	/**
	 * Método que añade los miembros de un conjunto de miembros a un grupo musical.
	 * @param miembrosAanadir array que contiene los nuevos miembros a incluir en el grupo musical.
	 * @throws ExcepcionPersonas, ExcepcionGruposMusicales en caso de que se produzca un error añadiendo los
	 * componentes al grupo.
	 */
	private void anadirMiembrosAGrupoMusical(String []miembrosAanadir) throws ExcepcionPersonas, ExcepcionGruposMusicales{
		for (int i=0;i<miembrosAanadir.length;i++) {
			this.controladorGruposMusicales.anadirMiembro(this.CIF, miembrosAanadir[i]);
		}
	}
	/**
	 * Método que añade los miembros de un conjunto de miembros a un grupo musical.
	 * @param miembrosAanadir array que contiene los nuevos miembros a eliminar en el grupo musical.
	 * @throws ExcepcionPersonas,ExcepcionGruposMusicales en caso de que se produzca un error al borrar
	 * los miembros de un grupo musical.
	 */
	private void eliminarMiembrosGrupoMusical () throws ExcepcionPersonas,ExcepcionGruposMusicales {
		for (Persona persona : this.controladorGruposMusicales.recuperarMiembros(this.CIF)) {
			this.controladorGruposMusicales.eliminarMiembro(this.CIF, persona.getDni());
		}
	}
	/**
	 * Método que comprueba la existencia de los miembros de una lista en un grupo musical.
	 * @param miembrosAComprobar array que contiene los  miembros a comprobar.
	 * @throws ExcepcionPersonas en caso de que no exista la persona en el sistema.
	 */
	private void comprobarExistenciaMiembros(String []miembrosAComprobar) throws ExcepcionPersonas {
		for(int i = 0; i<miembrosAComprobar.length; i++){
			controladorPersonas.recuperarPersona(miembrosAComprobar[i]);
		}
	}
}
