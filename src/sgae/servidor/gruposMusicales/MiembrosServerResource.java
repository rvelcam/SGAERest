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
import sgae.util.generated.MiembroInfoBreve;
import sgae.util.generated.MiembrosXML;

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
			for(Persona miembro : this.controladorGruposMusicales.recuperarMiembros(this.CIF)){
				if (miembro != null) {
					result.append("DNI: "+miembro.getDni()+"\tNombre: "+miembro.getNombre() + "\tApellidos: " + miembro.getApellidos() + "\testado: miembro actual\turi: http://localhost:8111/personas/"+miembro.getDni()+"\n");
				}				
			}			
			for(Persona miembro : this.controladorGruposMusicales.recuperarMiembrosAnteriores(this.CIF)){
				if (miembro != null) {
					result.append("DNI: "+miembro.getDni()+"\tNombre: "+miembro.getNombre() + "\tApellidos: " + miembro.getApellidos() + "\testado: miembro actual\turi: http://localhost:8111/personas/"+miembro.getDni()+"\n");
				}				
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
				MiembroInfoBreve persona = new MiembroInfoBreve();
				Link link = new Link();
				link.setType("simple");
				link.setHref("http://localhost:8111/personas/"+miembro.getDni());
				link.setTitle("Miembro Actual");
				persona.setNombre(miembro.getNombre());
				persona.setApellidos(miembro.getApellidos());
				persona.setDni(miembro.getDni());
				persona.setUri(link);
				persona.setEstado("Miembro actual");
				miembrosXml.getMiembroInfoBreve().add(persona);
			}					
			for (Persona miembro: this.controladorGruposMusicales.recuperarMiembrosAnteriores(this.CIF)) {
				MiembroInfoBreve persona = new MiembroInfoBreve();
				Link link = new Link();
				link.setType("simple");
				link.setHref(persona.getDni());
				link.setTitle("Miembro Anterior");
				persona.setNombre(miembro.getNombre());
				persona.setApellidos(miembro.getApellidos());
				persona.setDni(miembro.getDni());
				persona.setEstado("Miembro anterior");
				persona.setUri(link);
				miembrosXml.getMiembroInfoBreve().add(persona);
			} 		
			JaxbRepresentation<MiembrosXML> result = new JaxbRepresentation<MiembrosXML>(miembrosXml);
			result.setFormattedOutput(true);
			return result;
		}catch (ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
}
