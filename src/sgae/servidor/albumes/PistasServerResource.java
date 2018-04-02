package sgae.servidor.albumes;

import java.text.ParseException;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SgaeServerApplication;

public class PistasServerResource extends ServerResource {
	
	private String CIF;
	private ControladorGruposMusicales controladorGruposMusicales;
	private String idAlbum;
	
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.CIF = getAttribute("grupoMusicalCIF");
		this.idAlbum = getAttribute("idAlbum");
		//negociacion de contenido
		getVariants().add(new Variant(MediaType.TEXT_PLAIN));
		getVariants().add(new Variant(MediaType.TEXT_HTML));
	}
	
	@Override
	protected Representation get (Variant variant) throws ResourceException {
		Representation result = null;
		if (MediaType.TEXT_HTML.isCompatible(variant.getMediaType())) {
			
		}else if (MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())) {
			try {				
				StringBuilder infoPistas = new StringBuilder();
				for(String pista : this.controladorGruposMusicales.listarPistas(this.CIF, this.idAlbum)){
					infoPistas.append((pista == null) ? "\n" : pista);
				}
				result = new StringRepresentation(infoPistas.toString());
			}catch (ExcepcionGruposMusicales | ExcepcionAlbumes e) {
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
			
		}else {
			throw new ResourceException(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
		}
		
		return result;
	}
	
	@Post("form")
	public void crearNuevoAlbum(Representation representacion) throws ResourceException {
		Form form = new Form(representacion);
		String titulo = form.getValues("titulo");			
		try{
			int duracion = Integer.parseInt(form.getValues("duracion"));
			String id = this.controladorGruposMusicales.anadirPista(this.CIF, this.idAlbum, titulo, duracion);
			//No se si es necesario añadir alguna redireccion o algo
			setStatus(Status.SUCCESS_OK);
		}catch (ExcepcionAlbumes e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales err) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	
}
