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

public class PersonasServerResource extends ServerResource{

	private ControladorPersonas controladorPersonas;
	
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorPersonas = aplicacion.getControladorPersonas();
	}
	@Get("txt")
	public StringRepresentation representacionTxt(){
		StringBuilder result = new StringBuilder();
		for(String persona : controladorPersonas.listarPersonas()){
			result.append((persona == null) ? " \n" : persona);
		}
		return new StringRepresentation(result.toString());		
	}
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