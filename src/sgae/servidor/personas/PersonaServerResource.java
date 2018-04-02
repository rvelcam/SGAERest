package sgae.servidor.personas;

import java.text.ParseException;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.PersonaXML;

public class PersonaServerResource extends ServerResource{
	private ControladorPersonas controladorPersonas;
	private String DNI;

	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorPersonas = aplicacion.getControladorPersonas();
		this.DNI = getAttribute("DNI");
	}
	
	@Get("txt")
	public StringRepresentation representacionTXT(){
		try{
			return new StringRepresentation(controladorPersonas.verPersona(DNI));
		}catch(ExcepcionPersonas e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	@Get("xml")
	public Representation representacionXML(){
		PersonaXML personaXml = new PersonaXML();
		try{
			Persona persona = controladorPersonas.recuperarPersona(DNI);
			personaXml.setApellidos(persona.getApellidos());
			personaXml.setDni(DNI);
			personaXml.setNombre(persona.getNombre());
			JaxbRepresentation<PersonaXML> result = new JaxbRepresentation<PersonaXML>(personaXml);
			result.setFormattedOutput(true);
			return result;
		}catch(ExcepcionPersonas e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	@Put("form")
	public void guardarModificarGrupoMusical(Representation representacion){
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
		}
	}
}
