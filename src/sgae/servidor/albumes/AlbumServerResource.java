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
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.Album;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.AlbumXML;

public class AlbumServerResource extends ServerResource {
	
	private String idAlbum;
	private ControladorGruposMusicales controladorGruposMusicales;
	private String CIF;
	
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.idAlbum = getAttribute("idAlbum");
		this.CIF = getAttribute("grupoMusicalCIF");
		getVariants().add(new Variant(MediaType.TEXT_PLAIN));
		getVariants().add(new Variant(MediaType.TEXT_HTML));
	}
	
	@Override
	protected Representation get (Variant variant) throws ResourceException {
		Representation result = null;
		if (MediaType.TEXT_HTML.isCompatible(variant.getMediaType())) {
			AlbumXML albumXML = new AlbumXML(); 
			try {
				Album album = this.controladorGruposMusicales.recuperarAlbum(this.CIF, this.idAlbum);
				albumXML.setIdAlbum(album.getId());
				albumXML.setTitulo(album.getTitulo());
				albumXML.setFechaPublicacion(album.getFechaPublicacion());
				albumXML.setEjemplaresVendidos(album.getEjemplaresVendidos());
				Map<String, Object> dataModel = new HashMap<String, Object>();
				dataModel.put("albumXML", albumXML);
				//Velocity Template				
				Representation albumVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
						+"/AlbumXML.vtl").get();
				result = new TemplateRepresentation(albumVtl,dataModel,MediaType.TEXT_HTML);
			}catch(ExcepcionAlbumes | ExcepcionGruposMusicales err) {
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
				result = new StringRepresentation(this.controladorGruposMusicales.verAlbum(this.CIF, this.idAlbum));
			}catch (ExcepcionGruposMusicales | ExcepcionAlbumes e) {
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
			
		}else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}
		
		return result;
	}
	
	@Put("form")
	public void modificarAlbum(Representation representacion){
		Form form = new Form(representacion);
		String titulo = form.getValues("titulo");		
		String fechaPublicacion = form.getValues("fechaPublicacion");
		try{
			int ejemplaresVendidos = Integer.parseInt(form.getValues("ejemplaresVendidos"));
			this.controladorGruposMusicales.modificarAlbum(this.CIF, this.idAlbum, titulo, fechaPublicacion, ejemplaresVendidos);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales | ExcepcionAlbumes err) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
