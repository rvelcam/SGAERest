package sgae.servidor.albumes;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.Album;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.AlbumInfoBreve;
import sgae.util.generated.AlbumesXML;
import sgae.util.generated.Link;

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
			AlbumesXML albumesXML = new AlbumesXML();
			Link link = new Link();
			try {
				for (Album album : this.controladorGruposMusicales.recuperarAlbumes(this.CIF)){
					link.setHref(album.getId());
					link.setTitle("album");
					link.setType("simple");
					AlbumInfoBreve albumInfoBreve = new AlbumInfoBreve();
					albumInfoBreve.setIdAlbum(album.getId());
					albumInfoBreve.setTitulo(album.getTitulo());
					albumInfoBreve.setUri(link);
					albumesXML.getAlbumInfoBreve().add(albumInfoBreve);
				}
				Map<String, Object> dataModel = new HashMap<String, Object>();
				dataModel.put("albumesXML", albumesXML);
				//Velocity Template
				Representation albumesVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
						+"/AlbumesXML.vtl").get();
				result = new TemplateRepresentation(albumesVtl,dataModel,MediaType.TEXT_HTML);
			}catch (ExcepcionGruposMusicales e) {
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			} catch (ResourceNotFoundException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} catch (ParseErrorException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} catch (IOException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			}
			
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
			throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}
		
		return result;
	}
	
	@Post("form")
	public Representation crearNuevoAlbum(Representation representacion) throws ResourceException {
		Form form = new Form(representacion);
		String titulo = form.getValues("titulo");		
		String fechaPublicacion = form.getValues("fechaPublicacion");
		try{
			int ejemplaresVendidos = Integer.parseInt(form.getValues("ejemplaresVendidos"));
			String id = this.controladorGruposMusicales.crearAlbum(this.CIF, titulo, fechaPublicacion, ejemplaresVendidos);
			//No se si es necesario añadir alguna redireccion o algo
			setStatus(Status.SUCCESS_OK);
			return new StringRepresentation(id);
		}catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales err) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
