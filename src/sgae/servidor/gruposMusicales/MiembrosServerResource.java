package sgae.servidor.gruposMusicales;

import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.personas.Persona;
import sgae.servidor.aplicacion.SgaeServerApplication;
import sgae.util.generated.Link;
import sgae.util.generated.MiembroInfoBreve;
import sgae.util.generated.MiembrosXML;

/**
 * Clase del recurso miembros del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class MiembrosServerResource extends ServerResource {

	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	/** objeto del tipo String que contiene el CIF del grupo musical */
	private String CIF;	
	
	/**
	 * Método utilizado para añadir tareas a la inicialización estándar del recurso Miembros.
	 */
	@Override
	protected void doInit() throws ResourceException{
		SgaeServerApplication aplicacion = (SgaeServerApplication)getApplication();
		this.controladorGruposMusicales = aplicacion.getControladorGruposMusicales();
		this.CIF = getAttribute("grupoMusicalCIF");
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Miembros.
	 * @return objeto del tipo StringRepresentation que contiene la representación con la informacion
	 * completa de los miembros del grupo musical clasificados en miembros actuales y miembros anteriores.
	 * @throws ResourceExcepcion con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical.
	 */
	@Get("txt")
	public StringRepresentation representacionTxt() throws ResourceException{
		StringBuilder result = new StringBuilder();
		try{			
			for(Persona miembro : this.controladorGruposMusicales.recuperarMiembros(this.CIF)){
				if (miembro != null) {
					result.append("DNI: "+miembro.getDni()+"\tNombre: "+miembro.getNombre() + "\tApellidos: " + miembro.getApellidos() + "\testado: miembro actual\turi: http://localhost:8111/personas/"+miembro.getDni()+"\n");
				}				
			}			
			for(Persona miembro : this.controladorGruposMusicales.recuperarMiembrosAnteriores(this.CIF)){
				if (miembro != null) {
					result.append("DNI: "+miembro.getDni()+"\tNombre: "+miembro.getNombre() + "\tApellidos: " + miembro.getApellidos() + "\testado: miembro actual\turi: http://localhost:8111/personas/"+miembro.getDni()+"\n");
				}				
			}
		}catch(ExcepcionGruposMusicales e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
		
		return new StringRepresentation(result.toString());		
	}
	/**
	 * Método que invoca la operación GET sobre el recurso Miembros.
	 * @return objeto del tipo Representation que contiene la representación con la informacion
	 * completa de los miembros del grupo musical clasificados en miembros actuales y miembros anteriores en formato xml.
	 * @throws ResourceExcepcion con error CLIENT_ERROR_NOT_FOUND en el caso de que no exista el grupo musical.
	 */
	@Get("xml")
	public Representation representacionXML() throws ResourceException{
		MiembrosXML miembrosXml = new MiembrosXML();
		try {
			for (Persona miembro: this.controladorGruposMusicales.recuperarMiembros(this.CIF)) {
				MiembroInfoBreve persona = new MiembroInfoBreve();
				Link link = new Link();
				link.setType("simple");
				link.setHref("http://localhost:8111/personas/"+miembro.getDni());
				link.setTitle("Miembro Actual");
				persona.setNombre(miembro.getNombre());
				persona.setApellidos(miembro.getApellidos());
				persona.setDni(miembro.getDni());
				persona.setUri(link);
				persona.setEstado("Miembro actual");
				miembrosXml.getMiembroInfoBreve().add(persona);
			}					
			for (Persona miembro: this.controladorGruposMusicales.recuperarMiembrosAnteriores(this.CIF)) {
				MiembroInfoBreve persona = new MiembroInfoBreve();
				Link link = new Link();
				link.setType("simple");
				link.setHref(persona.getDni());
				link.setTitle("Miembro Anterior");
				persona.setNombre(miembro.getNombre());
				persona.setApellidos(miembro.getApellidos());
				persona.setDni(miembro.getDni());
				persona.setEstado("Miembro anterior");
				persona.setUri(link);
				miembrosXml.getMiembroInfoBreve().add(persona);
			} 		
			JaxbRepresentation<MiembrosXML> result = new JaxbRepresentation<MiembrosXML>(miembrosXml);
			result.setFormattedOutput(true);
			return result;
		}catch (ExcepcionGruposMusicales e){
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
}
