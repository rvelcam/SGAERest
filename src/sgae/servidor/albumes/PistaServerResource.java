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
import org.restlet.resource.Delete;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionAlbumes;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionPistas;
import sgae.nucleo.gruposMusicales.Pista;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.PistaXML;

public class PistaServerResource extends ServerResource {
	
	private String idAlbum;
	private ControladorGruposMusicales controladorGruposMusicales;
	private String CIF;
	private String idPista;
	
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
	
	@Override
	protected Representation get (Variant variant) throws ResourceException {
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
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} catch (ParseErrorException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} catch (IOException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
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
	
	@Delete
	public void eliminarPista(){			
		try{			
			this.controladorGruposMusicales.eliminarPista(this.CIF, this.idAlbum, this.idPista);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}catch (ExcepcionPistas e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch(ExcepcionGruposMusicales | ExcepcionAlbumes err) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		} 
	}
}
