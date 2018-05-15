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
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.Album;
import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.AlbumXML;
/**
 * Clase del recurso Album del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class AlbumServerResource extends ServerResource {
	/** objeto del tipo String que contiene el identificador del album */
	private String idAlbum;
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	/** objeto del tipo String que contiene el CIF del grupo musical */
	private String CIF;
	
	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso Pistas. Se
	 * declaran las tres variants soportadas por este recurso
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.idAlbum = getAttribute("idAlbum");
		this.CIF = getAttribute("grupoMusicalCIF");
		getVariants().add(new Variant(MediaType.TEXT_PLAIN));
		getVariants().add(new Variant(MediaType.TEXT_HTML));
		getVariants().add(new Variant(MediaType.APPLICATION_WWW_FORM));
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Album.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * de las pistas de un album.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album y con error SERVER_ERROR_NOT_IMPLEMENTED en el caso de que no se encuentre el recurso, los datos
	 * introducidos no tengan el formato correcto o haya un error en la comunicación.
	 */
	@Override
	public Representation get (Variant variant) throws ResourceException {
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
				throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
			} catch (ParseErrorException e) {
				throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
			} catch (IOException e) {
				throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
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
	/**
	 * Método que invoca la operación PUT sobre el recurso Album.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation que contiene el estado que debe alcanzar este recurso.
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album o el error CLIENT_ERROR_BAD_REQUEST en caso de que los datos no tengan el formato correcto.
	 */
	@Override
	public Representation put (Representation representacion, Variant variant) throws ResourceException{
		if (MediaType.APPLICATION_WWW_FORM.isCompatible(variant.getMediaType())) {
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
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
		}else {
			setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}	
		return new StringRepresentation("");
	}
	/**
	 * Método que invoca la operación DELETE sobre el recurso Album.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation vacío.
	 * @throws ResourceExcepcion con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album
	 */
	@Override
	public Representation delete (Variant variant) throws ResourceException {
		try{			
			this.controladorGruposMusicales.borrarAlbum(this.CIF, this.idAlbum);
			setStatus(Status.SUCCESS_NO_CONTENT);
			return new StringRepresentation("");
		}catch (ExcepcionGruposMusicales e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} catch (ExcepcionAlbumes e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} 
	}

}
