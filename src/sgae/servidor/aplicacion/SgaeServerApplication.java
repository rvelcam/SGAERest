package sgae.servidor.aplicacion;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.servidor.albumes.AlbumServerResource;
import sgae.servidor.albumes.AlbumesServerResource;
import sgae.servidor.albumes.PistaServerResource;
import sgae.servidor.albumes.PistasServerResource;
import sgae.servidor.gruposMusicales.GrupoMusicalServerResource;
import sgae.servidor.gruposMusicales.GruposMusicalesServerResource;
import sgae.servidor.gruposMusicales.MiembrosServerResource;
import sgae.servidor.personas.PersonaServerResource;
import sgae.servidor.personas.PersonasServerResource;

/**
 * Clase de aplicación del servidor.
 * 
 * @author Raúl Velasco Caminero y Héctor González Beltrán. ETSIT UVa.
 * @version 1.0
 */
public class SgaeServerApplication extends Application{
	/** objeto controlador de grupos musicales */
	private ControladorGruposMusicales controladorGruposMusicales;
	/** objeto controlador de personas */
	private ControladorPersonas controladorPersonas;
	
	/**
	 * Constructor que envía las propiedades de la aplicación e inicializa los controladores de personas y grupos musicales.
	 */
	public SgaeServerApplication(){
		setName("RESTfull Sgae Server Component");
		setOwner("ptpdx04");
		setAuthor("ptpdx04");
		setDescription("Class Application for Sgae App");
		this.controladorPersonas = new ControladorPersonas();
		this.controladorGruposMusicales = new ControladorGruposMusicales(this.controladorPersonas);
	}
	
	/**
	 * Método que contiene todas las reglas de encaminamiento de los patrones de URIs de las peticiones de los recursos
	 * que las gestionarán.
	 * @return el objeto router que contiene la vinculación de los diferentes recursos. 
	 */
	@Override
	public Restlet createInboundRoot(){
		Router router = new Router(getContext());
		router.attach("/", RootServerResource.class);
		router.attach("/gruposMusicales/", GruposMusicalesServerResource.class);		
		router.attach("/gruposMusicales/{grupoMusicalCIF}/", GrupoMusicalServerResource.class);	
		router.attach("/personas/", PersonasServerResource.class);	
		router.attach("/personas/{DNI}", PersonaServerResource.class);	
		router.attach("/gruposMusicales/{grupoMusicalCIF}/albumes/", AlbumesServerResource.class);
		router.attach("/gruposMusicales/{grupoMusicalCIF}/miembros", MiembrosServerResource.class);
		router.attach("/gruposMusicales/{grupoMusicalCIF}/albumes/{idAlbum}/", AlbumServerResource.class);
		router.attach("/gruposMusicales/{grupoMusicalCIF}/albumes/{idAlbum}/pistas/", PistasServerResource.class);
		router.attach("/gruposMusicales/{grupoMusicalCIF}/albumes/{idAlbum}/pistas/{idPista}", PistaServerResource.class);
		
		return router;		
	}
	
	/**
	 * Método que permite obtener una referencia al objeto ControladorGruposMusicales.
	 * 
	 * @return el controlador de grupos musicales.
	 */
	public ControladorGruposMusicales getControladorGruposMusicales(){
		return this.controladorGruposMusicales;
	}
	/**
	 * Método que permite obtener una referencia al objeto ControladorPersonas.
	 * 
	 * @return el controlador de personas.
	 */
	public ControladorPersonas getControladorPersonas(){
		return this.controladorPersonas;
	}
	
	
}
