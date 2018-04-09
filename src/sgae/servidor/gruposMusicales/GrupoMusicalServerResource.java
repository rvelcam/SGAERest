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
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.GrupoMusicalXML;



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
	
	@Get("xml")
	public Representation representacionXML(){
		GrupoMusicalXML grupoXml = new GrupoMusicalXML();
		try {
			GrupoMusical grupoMusical = controladorGruposMusicales.recuperarGrupoMusical(this.CIF);			
			grupoXml.setCIF(grupoMusical.getCif()) ;
			grupoXml.setNombre(grupoMusical.getNombre());
			grupoXml.setFechaCreacion (grupoMusical.getFechaCreacion());			
			JaxbRepresentation<GrupoMusicalXML> result = new JaxbRepresentation<GrupoMusicalXML>(grupoXml);
			result.setFormattedOutput(true);
			return result;
		}catch (ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
	
	@Put("form")
	public void guardarModificarGrupoMusical(Representation representacion){
		Form form = new Form(representacion);
		String nombre = form.getValues("nombre");
		String fechaCreacion = form.getValues("fechaCreacion");
		String[] miembros = form.getValues("miembros").split(",");
		
		try{
			List<Persona> miembrosActuales = this.controladorGruposMusicales.recuperarMiembros(this.CIF);
			controladorGruposMusicales.crearGrupoMusical(CIF, nombre, fechaCreacion);			
			for (int i=0;i<miembros.length;i++) {
				this.controladorGruposMusicales.anadirMiembro(this.CIF, miembros[i]);
			}
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
		} catch (ExcepcionPersonas e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
}
