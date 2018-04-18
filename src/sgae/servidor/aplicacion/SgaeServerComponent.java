package sgae.servidor.aplicacion;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.VirtualHost;

/**
 * Clase componente del servidor.
 * 
 * @author Ra�l Velasco Caminero y H�ctor Gonz�lez Beltr�n. ETSIT UVa.
 * @version 1.0
 */
public class SgaeServerComponent extends Component{
	/**
	 * M�todo principal que permite arrancar el programa java.
	 */
	public static void main(String []args) throws Exception{
		new SgaeServerComponent().start();
	}
	
	/**
	 * Constructor que env�a las propiedades del componente que crea el conector de tipo servidor y asocia un host virtual
	 * por defecto a la aplicaci�n Restlet que lo gestionar�n.
	 */
	public SgaeServerComponent(){
		setName("RESTfull Sgae Server Component");
		setOwner("ptpdx04");
		setAuthor("ptpdx04");
		setDescription("Class Component for Sgae App");
		Server server = new Server(new Context(), Protocol.HTTP, 8111);
		server.getContext().getParameters().set("tracing", "true");
		getServers().add(server);
		//protocolo CLAP para respuestas HTML
        getClients().add(Protocol.CLAP);
		VirtualHost host = getDefaultHost();
		host.attachDefault(new SgaeServerApplication());
	}
}
