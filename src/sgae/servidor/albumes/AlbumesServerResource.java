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
/**
 * Clase del recurso Albumes del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class AlbumesServerResource extends ServerResource {
	/** objeto del tipo String que contiene el CIF del grupo musical */
	private String CIF;
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	
	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso Pistas. Se
	 * declaran las dos variants soportadas por este recurso
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.CIF = getAttribute("grupoMusicalCIF");
		//negociacion de contenido
		getVariants().add(new Variant(MediaType.TEXT_PLAIN));
		getVariants().add(new Variant(MediaType.TEXT_HTML));
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Albumes.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * de las pistas de un album.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album y con error SERVER_ERROR_NOT_IMPLEMENTED en caso de que no se pueda producir el formato HTML.
	 */
	@Override
	public Representation get (Variant variant) throws ResourceException {
		Representation result = null;
		if (MediaType.TEXT_HTML.isCompatible(variant.getMediaType())) {
			AlbumesXML albumesXML = new AlbumesXML();			
			try {
				for (Album album : this.controladorGruposMusicales.recuperarAlbumes(this.CIF)){
					Link link = new Link();
					link.setHref(album.getId() + "/");
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
				throw new ResourceException(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
			} catch (ParseErrorException e) {
				throw new ResourceException(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
			} catch (IOException e) {
				throw new ResourceException(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
			}
			
		}else if (MediaType.TEXT_PLAIN.isCompatible(variant.getMediaType())) {
			try {
				StringBuilder infoAlbumes = new StringBuilder();
				for(Album album : this.controladorGruposMusicales.recuperarAlbumes(this.CIF)){
					infoAlbumes.append((album == null) ? "\n" : "Titulo: " + album.getTitulo() + "\tUri: " + album.getId() + "/\n");
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
	/**
	 * Método que invoca la operación POST sobre el recurso Albumes.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation que contiene el estado que debe alcanzar este recurso.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album y el error CLIENT_ERROR_BAD_REQUESTen caso de que los datos no tengan el formato correcto.
	 */
	@Post("form")
	public StringRepresentation crearNuevoAlbum(Representation representacion) throws ResourceException {
		Form form = new Form(representacion);
		String titulo = form.getValues("titulo");		
		String fechaPublicacion = form.getValues("fechaPublicacion");
		try{
			int ejemplaresVendidos = Integer.parseInt(form.getValues("ejemplaresVendidos"));
			String id = this.controladorGruposMusicales.crearAlbum(this.CIF, titulo, fechaPublicacion, ejemplaresVendidos);
			//envia el id como texto y la referencia en la cabecera Location
			setStatus(Status.SUCCESS_CREATED);
			setLocationRef("http://localhost:8111/gruposMusicales/"+this.CIF+"/albumes/"+id+"/");
			return new StringRepresentation(id);			
		}catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales err) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}catch(com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}

}
