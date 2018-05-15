package sgae.servidor.albumes;

import java.io.IOException;
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

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionPistas;
import sgae.nucleo.gruposMusicales.Pista;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.Link;
import sgae.util.generated.PistaInfoBreve;
import sgae.util.generated.PistasXML;
/**
 * Clase del recurso Pistas del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class PistasServerResource extends ServerResource {
	/** objeto del tipo String que contiene el CIF del grupo musical */
	private String CIF;
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	/** objeto del tipo String que contiene el identificador del album */
	private String idAlbum;
	
	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso Pistas. Se
	 * declaran las dos variants soportadas por este recurso
	 */
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
	/**
	 * Método que invoca la operación GET sobre el recurso Pistas.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * de las pistas de un album.
	 * @throws ResourceExcepcion con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical
	 * o el album y con error SERVER_ERROR_SERVICE_UNAVAILABLE en caso de que no se pueda producir el formato HTML.
	 */
	@Override
	public Representation get (Variant variant) throws ResourceException {
		Representation result = null;
		if (MediaType.TEXT_HTML.isCompatible(variant.getMediaType())) {
			PistasXML pistasXML = new PistasXML();
			Link link = new Link();
			try {
				for (Pista pista : this.controladorGruposMusicales.recuperarPistas(this.CIF, this.idAlbum)){
					link.setHref(pista.getIdPista() + "/");
					link.setTitle("Pista");
					link.setType("simple");
					PistaInfoBreve pistaInfoBreve = new PistaInfoBreve();
					pistaInfoBreve.setIdPista(pista.getIdPista());
					pistaInfoBreve.setNombre(pista.getNombre());
					pistaInfoBreve.setUri(link);
					pistasXML.getPistaInfoBreve().add(pistaInfoBreve);
				}
				Map<String, Object> dataModel = new HashMap<String, Object>();
				dataModel.put("pistasXML", pistasXML);
				//Velocity Template
				Representation pistasVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
						+"/PistasXML.vtl").get();
				result = new TemplateRepresentation(pistasVtl,dataModel,MediaType.TEXT_HTML);
			}catch (ExcepcionGruposMusicales | ExcepcionAlbumes e) {
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
				StringBuilder infoPistas = new StringBuilder();
				
				for(Pista pista : this.controladorGruposMusicales.recuperarPistas(this.CIF, this.idAlbum)){
					infoPistas.append((pista == null) ? "\n" : "Nombre: " + pista.getNombre() + "\tUri: " + pista.getIdPista()+"\n");
				}
				result = new StringRepresentation(infoPistas.toString());
			}catch (ExcepcionGruposMusicales | ExcepcionAlbumes e) {
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
			
		}else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}
		
		return result;
	}
	/**
	 * Método que invoca la operación POST sobre el recurso Pistas.
	 * @param representation que contiene el estado que debe alcanzar este recurso.
	 * @return objeto del tipo StringRepresentation que contiene la representación con el identificador
	 * de la pista.
	 * @throws ResourceExcepcion con error CLIENT_ERROR_BAD_REQUEST en el caso de que no exista el album, o no exista el grupo musical.
	 */	
	@Post("form")
	public StringRepresentation crearNuevaPista(Representation representacion) throws ResourceException {
		Form form = new Form(representacion);
		String titulo = form.getValues("titulo");			
		try{
			int duracion = Integer.parseInt(form.getValues("duracion"));
			String id = this.controladorGruposMusicales.anadirPista(this.CIF, this.idAlbum, titulo, duracion);
			//envia el id como texto y la referencia en la cabecera Location
			setStatus(Status.SUCCESS_CREATED);
			setLocationRef("http://localhost:8111/gruposMusicales/"+this.CIF+"/albumes/"+this.idAlbum+"/pistas/"+id);
			return new StringRepresentation(id);
		}catch (ExcepcionAlbumes e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales err) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}catch (ExcepcionPistas e){
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	
}
