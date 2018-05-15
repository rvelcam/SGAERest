package sgae.servidor.personas;

import java.text.ParseException;

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

import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.PersonaXML;
/**
 * Clase del recurso persona del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class PersonaServerResource extends ServerResource{
	/** objeto controlador de personas */
	private ControladorPersonas controladorPersonas;
	/** objeto del tipo String que contiene el DNI de la persona */
	private String DNI;

	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso Persona.
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorPersonas = aplicacion.getControladorPersonas();
		this.DNI = getAttribute("DNI");
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Persona.
	 * @return objeto del tipo StringRepresentation que contiene la representación con la informacion
	 * completa de la persona
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista la persona.
	 */
	@Get("txt")
	public StringRepresentation representacionTXT() throws ResourceException {
		try{
			return new StringRepresentation(controladorPersonas.verPersona(DNI));
		}catch(ExcepcionPersonas e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Persona.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * completa de la persona en formato xml
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical.
	 */
	@Get("xml")
	public Representation representacionXML() throws ResourceException {
		PersonaXML personaXml = new PersonaXML();
		try{
			Persona persona = controladorPersonas.recuperarPersona(DNI);
			personaXml.setApellidos(persona.getApellidos());
			personaXml.setDni(DNI);
			personaXml.setNombre(persona.getNombre());
			personaXml.setFechaNacimiento(persona.getFechaNacimiento());
			JaxbRepresentation<PersonaXML> result = new JaxbRepresentation<PersonaXML>(personaXml);
			result.setFormattedOutput(true);
			return result;
		}catch(ExcepcionPersonas e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	/**
	 * Método que invoca la operación PUT sobre el recurso Persona. Permite almacenar o modificar 
	 * una persona.
	 * @param representacion que contiene el estado que debe alcanzar este recurso.
	 * @throws ResourceException con error CLIENT_ERROR_BAD_REQUEST en el caso de que los datos no se hayan introducido con el formato correcto.
	 */
	@Put("form")
	public void guardarModificarGrupoMusical(Representation representacion) throws ResourceException {
		Form form = new Form(representacion);
		String nombre = form.getValues("nombre");
		String apellidos = form.getValues("apellidos");
		String fechaNacimiento = form.getValues("fechaNacimiento");
		try{
			controladorPersonas.crearPersona(DNI, nombre, apellidos, fechaNacimiento);
			setStatus(Status.SUCCESS_CREATED);
		}catch(ExcepcionPersonas e){
			try{
				controladorPersonas.modificarPersona(DNI, nombre, apellidos, fechaNacimiento);
				setStatus(Status.SUCCESS_NO_CONTENT);
			}catch(ExcepcionPersonas er){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			} catch (ParseException e1) {
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}
		} catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	/**
	 * Método que invoca la operación DELETE sobre el recurso Persona. Permite eliminar una persona.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista la persona
	 */
	@Delete
	public void eliminarPersona() throws ResourceException {			
		try{			
			this.controladorPersonas.borrarPersona(this.DNI);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}catch (ExcepcionPersonas e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} 
	}
}
