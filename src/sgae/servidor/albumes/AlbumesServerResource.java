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
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SgaeServerApplication;

public class AlbumesServerResource extends ServerResource {
	
	private String CIF;
	private ControladorGruposMusicales controladorGruposMusicales;
	
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.CIF = getAttribute("grupoMusicalCIF");
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
				StringBuilder infoAlbumes = new StringBuilder();
				for(String album : this.controladorGruposMusicales.listarAlbumes(this.CIF)){
					infoAlbumes.append((album == null) ? "\n" : album);
				}
				result = new StringRepresentation(infoAlbumes.toString());
			}catch (ExcepcionGruposMusicales e) {
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
		String fechaPublicacion = form.getValues("fechaPublicacion");
		try{
			int ejemplaresVendidos = Integer.parseInt(form.getValues("ejemplaresVendidos"));
			String id = this.controladorGruposMusicales.crearAlbum(this.CIF, titulo, fechaPublicacion, ejemplaresVendidos);
			//No se si es necesario añadir alguna redireccion o algo
			setStatus(Status.SUCCESS_OK);
		}catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales err) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
