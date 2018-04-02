package sgae.servidor.gruposMusicales;

import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;

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
}
