package sgae.servidor.gruposMusicales;

import java.text.ParseException;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.PersonaInfoBreve;
import sgae.util.generated.PersonaXML;


public class GrupoMusicalServerResource extends ServerResource{
	
	private ControladorGruposMusicales controladorGruposMusicales;
	private String CIF;

	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.CIF = getAttribute("grupoMusicalCIF");
	}
	
	@Get("txt")
	public StringRepresentation representacionTXT(){
		try{
			return new StringRepresentation(controladorGruposMusicales.verGrupoMusical(CIF));
		}catch(ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	
	/*@Get("xml")
	public Representation representacionXML(){
		GrupoMusicalXML grupoXml = new GrupoMusicalXML();
		try {
			GrupoMusical grupoMusical = controladorGruposMusicales.recuperarGrupoMusical(this.CIF);
			List<Persona> miembrosActuales = grupoMusical.recuperarMiembros();
			List<Persona> miembrosAnteriores = grupoMusical.recuperarMiembrosAnteriores();
			grupoXml.setCIF(grupoMusical.getCif()) ;
			grupoXml.setNombre(grupoMusical.getNombre());
			grupoXml.setFechaCreacion (grupoMusical.getFechaCreacion());
			for (Persona miembro:miembrosActuales) {
				MiembroXML miembroXml = new miembroXml();
				PersonaInfoBreve personaInfo = new PersonaInfoBreve();
				personaInfo.setApellidos(miembro.getApellidos());
				miembroXml.setEstado("Miembro actual");
				miembroXml.setDatos (personaInfo);
				grupoXml.getMiembroXML().add(miembroXML);
			}
			JaxbRepresentation<GrupoMusicalXML> result = new JaxbRepresentation<GrupoMusicalXML>(grupoXml);
			result.setFormattedOutput(true);
			return result;
		}catch (ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}*/
	
	@Put("form")
	public void guardarModificarGrupoMusical(Representation representacion){
		Form form = new Form(representacion);
		String nombre = form.getValues("nombre");
		String fechaCreacion = form.getValues("fechaCreacion");
		try{
			controladorGruposMusicales.crearGrupoMusical(CIF, nombre, fechaCreacion);
			setStatus(Status.SUCCESS_CREATED);
		}catch(ExcepcionGruposMusicales e){
			try{
				controladorGruposMusicales.modificarGrupoMusical(CIF, nombre, fechaCreacion);
				setStatus(Status.SUCCESS_NO_CONTENT);
			}catch(ExcepcionGruposMusicales er){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			} catch (ParseException e1) {
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}
		} catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}
	}
	
	
	
}
