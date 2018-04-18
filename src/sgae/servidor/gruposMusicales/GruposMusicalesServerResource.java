package sgae.servidor.gruposMusicales;


import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.GrupoMusicalInfoBreve;
import sgae.util.generated.GruposMusicalesXML;
import sgae.util.generated.Link;


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
		for(GrupoMusical grupoMusical : this.controladorGruposMusicales.recuperarGruposMusicales()){
			result.append((grupoMusical == null) ? " \n" : "CIF: " + grupoMusical.getCif() + "\tNombre: " + grupoMusical.getNombre() + "\tUri: " + grupoMusical.getCif() + "/");
		}
		return new StringRepresentation(result.toString());		
	}
	@Get("xml")
	public Representation representacionXML(){
		GruposMusicalesXML gruposXml = new GruposMusicalesXML();
		try{
					
			for(GrupoMusical grupo: controladorGruposMusicales.recuperarGruposMusicales()){
				GrupoMusicalInfoBreve grupoInfoBreve = new GrupoMusicalInfoBreve();
				Link link = new Link();
				link.setHref(grupo.getCif() + "/");
				link.setTitle("grupo musical");
				link.setType("simple");
				grupoInfoBreve.setUri(link);
				grupoInfoBreve.setCIF(grupo.getCif());
				grupoInfoBreve.setNombre(grupo.getNombre());				
				gruposXml.getGrupoMusicalInfoBreve().add(grupoInfoBreve);
			}
			JaxbRepresentation<GruposMusicalesXML> result = new JaxbRepresentation<GruposMusicalesXML>(gruposXml);
			result.setFormattedOutput(true);
			return result;
		}catch(Exception e){
			System.out.println("EXCEPCION: "+e.toString());
			return null;
		}
	}
}
