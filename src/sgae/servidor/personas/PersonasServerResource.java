package sgae.servidor.personas;


import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.Link;
import sgae.util.generated.PersonaInfoBreve;
import sgae.util.generated.PersonasXML;
/**
 * Clase del recurso personas del servidor.
 * 
 * @author Ra�l Velasco Caminero y H�ctor Gonz�lez Beltr�n. ETSIT UVa.
 * @version 1.0
 */
public class PersonasServerResource extends ServerResource{
	/** objeto controlador de personas */
	private ControladorPersonas controladorPersonas;
	
	/**
	 * M�todo utilizado para a�adir tareas a la inicializaci�n est�ndar del recurso Personas.
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorPersonas = aplicacion.getControladorPersonas();
	}
	/**
	 * M�todo que invoca la operaci�n GET sobre el recurso Personas.
	 * @return objeto del tipo StringRepresentation que contiene la representaci�n con la informacion
	 * completa de las personas almacenadas en la aplicaci�n 
	 */
	@Get("txt")
	public StringRepresentation representacionTxt(){
		StringBuilder result = new StringBuilder();
		for(Persona persona : controladorPersonas.recuperarPersonas()){
			result.append((persona == null) ? " \n" : "DNI: " + persona.getDni() 
					+ "\tNombre: " + persona.getNombre() + "\tApellidos: " 
					+ persona.getApellidos() + "\tUri: " 
					+ getRequest().getResourceRef().getIdentifier()+persona.getDni() + "\t\n");
		}
		return new StringRepresentation(result.toString());		
	}
	/**
	 * M�todo que invoca la operaci�n GET sobre el recurso Personas.
	 * @return objeto del tipo Representation que contiene la representaci�n con la informacion
	 * completa de las personas almacenadas en la aplicaci�n en formato xml
	 */
	@Get("xml")
	public Representation representacionXML(){
		PersonasXML personasXml = new PersonasXML();
		try{		
			
			for(Persona persona: controladorPersonas.recuperarPersonas()){
				PersonaInfoBreve personaInfoBreve = new PersonaInfoBreve();
				Link link = new Link();
				link.setHref(persona.getDni());
				link.setTitle("persona");
				link.setType("simple");
				personaInfoBreve.setUri(link);
				personaInfoBreve.setNombre(persona.getNombre());
				personaInfoBreve.setApellidos(persona.getApellidos());
				personaInfoBreve.setDni(persona.getDni());
				personasXml.getPersonaInfoBreve().add(personaInfoBreve);
			}
			JaxbRepresentation<PersonasXML> result = new JaxbRepresentation<PersonasXML>(personasXml);
			result.setFormattedOutput(true);
			return result;
		}catch(Exception e){
			System.out.println("EXCEPCION: "+e.toString());
			return null;
		}
	}
	
}