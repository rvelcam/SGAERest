package sgae.servidor.gruposMusicales;

import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.Link;
import sgae.util.generated.MiembrosXML;
import sgae.util.generated.PersonaInfoBreve;

public class MiembrosServerResource extends ServerResource {

	private ControladorGruposMusicales controladorGruposMusicales;
	private String CIF;	
	
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.CIF = getAttribute("grupoMusicalCIF");
	}
	
	@Get("txt")
	public StringRepresentation representacionTxt(){
		StringBuilder result = new StringBuilder();
		try{
			result.append("Miembros actuales del grupo\n");
			for(Persona miembro : this.controladorGruposMusicales.recuperarMiembros(this.CIF)){
				result.append((miembro == null) ? " \n" : miembro.verDescripcionBreve());
			}
			result.append("Miembros anteriores del grupo\n");
			for(Persona miembro : this.controladorGruposMusicales.recuperarMiembrosAnteriores(this.CIF)){
				result.append((miembro == null) ? " \n" : miembro.verDescripcionBreve());
			}
		}catch(ExcepcionGruposMusicales e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
		
		return new StringRepresentation(result.toString());		
	}
	
	@Get("xml")
	public Representation representacionXML(){
		MiembrosXML miembrosXml = new MiembrosXML();
		try {
			for (Persona miembro: this.controladorGruposMusicales.recuperarMiembros(this.CIF)) {
				PersonaInfoBreve persona = new PersonaInfoBreve();
				Link link = new Link();
				link.setType("simple");
				link.setHref("http://localhost:8111/personas/"+miembro.getDni());
				link.setTitle("Miembro Actual");
				persona.setNombre(miembro.getNombre());
				persona.setApellidos(miembro.getApellidos());
				persona.setDni(miembro.getDni());
				persona.setUri(link);
				miembrosXml.getPersonaInfoBreve().add(persona);
			}					
			for (Persona miembro: this.controladorGruposMusicales.recuperarMiembrosAnteriores(this.CIF)) {
				PersonaInfoBreve persona = new PersonaInfoBreve();
				Link link = new Link();
				link.setType("simple");
				link.setHref(persona.getDni());
				link.setTitle("Miembro Anterior");
				persona.setNombre(miembro.getNombre());
				persona.setApellidos(miembro.getApellidos());
				persona.setDni(miembro.getDni());
				persona.setUri(link);
				miembrosXml.getPersonaInfoBreve().add(persona);
			} 		
			JaxbRepresentation<MiembrosXML> result = new JaxbRepresentation<MiembrosXML>(miembrosXml);
			result.setFormattedOutput(true);
			return result;
		}catch (ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
}
