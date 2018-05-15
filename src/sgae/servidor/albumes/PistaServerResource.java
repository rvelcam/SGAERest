package sgae.servidor.albumes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
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

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionPistas;
import sgae.nucleo.gruposMusicales.Pista;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.PistaXML;
/**
 * Clase del recurso Pista del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class PistaServerResource extends ServerResource {
	/** objeto del tipo String que contiene el identificador del album */
	private String idAlbum;
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	/** objeto del tipo String que contiene el CIF del grupo musical */
	private String CIF;
	/** objeto del tipo String que contiene el identificador de la pista */
	private String idPista;
	
	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso Pistas. Se
	 * declaran las dos variants soportadas por este recurso
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.idAlbum = getAttribute("idAlbum");
		this.idPista = getAttribute("idPista");
		this.CIF = getAttribute("grupoMusicalCIF");
		getVariants().add(new Variant(MediaType.TEXT_PLAIN));
		getVariants().add(new Variant(MediaType.TEXT_HTML));		
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Pista.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * de la pista
	 * @throws ResourceException con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album o la pista y con error SERVER_ERROR_SERVICE_UNAVAILABLE en caso de que no se pueda generar el formato HTML.
	 */
	@Override
	public Representation get (Variant variant) throws ResourceException {
		Representation result = null;
		if (MediaType.TEXT_HTML.isCompatible(variant.getMediaType())) {
			PistaXML pistaXML = new PistaXML(); 
			try {
				Pista pista = this.controladorGruposMusicales.recuperarPista(this.CIF, this.idAlbum, this.idPista);
				pistaXML.setIdPista(pista.getIdPista());
				pistaXML.setNombre(pista.getNombre());
				pistaXML.setDuracion(pista.getDuracion());				
				Map<String, Object> dataModel = new HashMap<String, Object>();
				dataModel.put("pistaXML", pistaXML);
				//Velocity Template				
				Representation pistaVtl = new ClientResource(LocalReference.createClapReference(getClass().getPackage())
						+"/PistaXML.vtl").get();
				result = new TemplateRepresentation(pistaVtl,dataModel,MediaType.TEXT_HTML);
			}catch(ExcepcionAlbumes | ExcepcionGruposMusicales | ExcepcionPistas err) {
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
				result = new StringRepresentation(this.controladorGruposMusicales.verPista(this.CIF, this.idAlbum, this.idPista));
			}catch (ExcepcionGruposMusicales | ExcepcionAlbumes | ExcepcionPistas e) {
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
			}
			
		}else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}
		
		return result;
	}
	
	/**
	 * Método que invoca la operación DELETE sobre el recurso Pista.
	 * @param variant que declara el tipo de contenido permitido.
	 * @return objeto del tipo Representation vacío.
	 * @throws ResourceExcepcion con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical, 
	 * el album o la pista
	 */
	@Override
	public Representation delete (Variant variant) throws ResourceException {
		try{			
			this.controladorGruposMusicales.eliminarPista(this.CIF, this.idAlbum, this.idPista);
			setStatus(Status.SUCCESS_NO_CONTENT);
			return new StringRepresentation("");
		}catch (ExcepcionGruposMusicales e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} catch (ExcepcionAlbumes e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		} catch (ExcepcionPistas e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
}
