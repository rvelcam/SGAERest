package sgae.servidor.gruposMusicales;

import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.Link;
import sgae.util.generated.PersonaInfoBreve;
import sgae.util.generated.PersonasXML;

public class GruposMusicalesServerResource extends ServerResource{
	
	private ControladorGruposMusicales controladorGruposMusicales;
	
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
	}
	
	@Get("txt")
	public StringRepresentation representacionTxt(){
		StringBuilder result = new StringBuilder();
		for(String grupoMusical : this.controladorGruposMusicales.listarGruposMusicales()){
			result.append((grupoMusical == null) ? " \n" : grupoMusical);
		}
		return new StringRepresentation(result.toString());		
	}
	/*@Get("xml")
	public Representation representacionXML(){
		GruposMusicalesXML gruposXml = new GruposMusicalesXML();
		try{
			GrupoMusicalInfoBreve grupoInfoBreve = new GrupoMusicalInfoBreve();
			Link link = new Link();
			
			for(GrupoMusical grupo: controladorGruposMusicales.recuperarGruposMusicales()){
				link.setHref(grupo.getCif());
				link.setTitle("grupo musical");
				link.setType("simple");
				grupoInfoBreve.setUri(link);
				grupoInfoBreve.setCIF(grupo.getCif());
				grupoInfoBreve.setNombre(grupo.getNombre());				
				grupoInfoBreve.getGrupoMusicalInfoBreve().add(grupoInfoBreve);
			}
			JaxbRepresentation<GruposMusicalesXML> result = new JaxbRepresentation<PersonasXML>(GruposMusicalesXML);
			result.setFormattedOutput(true);
			return result;
		}catch(Exception e){
			System.out.println("EXCEPCION: "+e.toString());
			return null;
		}
	}*/
}
