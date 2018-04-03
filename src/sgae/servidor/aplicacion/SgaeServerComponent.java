package sgae.servidor.aplicacion;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.VirtualHost;
public class SgaeServerComponent extends Component{
	public static void main(String []args) throws Exception{
		new SgaeServerComponent().start();
	}
	public SgaeServerComponent(){
		setName("RESTfull Sgae Server Component");
		setOwner("ptpdx04");
		setAuthor("ptpdx04");
		setDescription("Class Component for Sgae App");
		Server server = new Server(new Context(), Protocol.HTTP, 8112);
		server.getContext().getParameters().set("tracing", "true");
		getServers().add(server);
		//protocolo CLAP para respuestas HTML
        getClients().add(Protocol.CLAP);
		VirtualHost host = getDefaultHost();
		host.attachDefault(new SgaeServerApplication());
	}
}
