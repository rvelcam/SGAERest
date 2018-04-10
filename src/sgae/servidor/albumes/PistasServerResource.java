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
import sgae.nucleo.gruposMusicales.Pista;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.Link;
import sgae.util.generated.PistaInfoBreve;
import sgae.util.generated.PistasXML;

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
			PistasXML pistasXML = new PistasXML();
			Link link = new Link();
			try {
				for (Pista pista : this.controladorGruposMusicales.recuperarPistas(this.CIF, this.idAlbum)){
					link.setHref(pista.getIdPista());
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
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} catch (ParseErrorException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} catch (IOException e) {
				throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
			} 
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
			throw new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
		}
		
		return result;
	}
	
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
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (NumberFormatException errNum) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}catch(ExcepcionGruposMusicales err) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	
}
