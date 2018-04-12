package sgae.servidor.gruposMusicales;

import java.text.ParseException;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.gruposMusicales.GrupoMusical;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.Persona;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.StringUtils;
import sgae.util.generated.GrupoMusicalXML;

public class GrupoMusicalServerResource extends ServerResource{
	
	private ControladorGruposMusicales controladorGruposMusicales;
	private ControladorPersonas controladorPersonas;
	private String CIF;

	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		controladorPersonas = aplicacion.getControladorPersonas();
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
		String miembros = form.getValues("miembros");
		String[] miembrosList=null;
		try{
			if (miembros!=null && !miembros.isEmpty()) {
				miembrosList = StringUtils.quitarEspacios(miembros.split(","));
				comprobarExistenciaMiembros(miembrosList);				
			}
			controladorGruposMusicales.crearGrupoMusical(CIF, nombre, fechaCreacion);
			if (miembrosList != null && miembrosList.length>0) {
				anadirMiembrosAGrupoMusical(miembrosList);
			}								
			setStatus(Status.SUCCESS_CREATED);
		}catch(ExcepcionGruposMusicales e){
			try{			
				if (miembrosList != null && miembrosList.length>0) {
					actualizarMiembrosGrupoMusical(miembrosList);
				}else {
					eliminarMiembrosGrupoMusical();
				}
				setStatus(Status.SUCCESS_NO_CONTENT);
			}catch(ExcepcionGruposMusicales er){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			} catch(ExcepcionPersonas e2){
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
			}
		} catch (ParseException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);	
		}catch (ExcepcionPersonas e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		}
	}
	
	@Delete
	public void eliminarGrupoMusical(){			
		try{			
			this.controladorGruposMusicales.borrarGrupoMusical(this.CIF);
			setStatus(Status.SUCCESS_NO_CONTENT);
		}catch (ExcepcionGruposMusicales e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
		} 
	}
	
	//DARLO UNA VUELTA
	private void actualizarMiembrosGrupoMusical(String []miembrosaActualizar) throws ExcepcionPersonas, ExcepcionGruposMusicales{
		List<Persona> miembrosActuales = this.controladorGruposMusicales.recuperarMiembros(this.CIF);
		boolean encontrado = false;
		//Comparar la lista actual con la nueva recibida -> eliminar miembro en caso de no encontrarlo
		for (Persona miembro : miembrosActuales) {
			encontrado = false;
			for (int i=0;i<miembrosaActualizar.length;i++) {
				if (miembrosaActualizar[i].equals(miembro.getDni())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				this.controladorGruposMusicales.eliminarMiembro(this.CIF, miembro.getDni());
			}
		}
		
		//Comparar lista recibida con la actual -> añadir miembro en caso de que no se encuentre
		String dni=null;
		for (int i=0;i<miembrosaActualizar.length;i++) {
			encontrado = false;
			for (Persona miembro : miembrosActuales) {
				dni = miembro.getDni();
				if (miembrosaActualizar[i].equals(dni)) {
					encontrado = true;
					break;
				}				
			}
			if (!encontrado) {
				this.controladorGruposMusicales.anadirMiembro(this.CIF, miembrosaActualizar[i]);
			}
		}		

	}
	private void anadirMiembrosAGrupoMusical(String []miembrosAanadir) throws ExcepcionPersonas, ExcepcionGruposMusicales{
		for (int i=0;i<miembrosAanadir.length;i++) {
			this.controladorGruposMusicales.anadirMiembro(this.CIF, miembrosAanadir[i]);
		}
	}
	private void eliminarMiembrosGrupoMusical () throws ExcepcionPersonas,ExcepcionGruposMusicales {
		for (Persona persona : this.controladorGruposMusicales.recuperarMiembros(this.CIF)) {
			this.controladorGruposMusicales.eliminarMiembro(this.CIF, persona.getDni());
		}
	}
	private void comprobarExistenciaMiembros(String []miembrosAComprobar) throws ExcepcionPersonas {
		for(int i = 0; i<miembrosAComprobar.length; i++){
			controladorPersonas.recuperarPersona(miembrosAComprobar[i]);
		}
	}
}
