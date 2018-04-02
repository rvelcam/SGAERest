package sgae.servidor.aplicacion;

import java.io.IOException;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RootServerResource extends ServerResource{
	public RootServerResource(){
		setNegotiated(true);
	}
	
	
	@Override
	protected void doInit() throws ResourceException{
		System.out.print("The Root Resouce was inicialized");
	}
	
	@Override
	protected void doCatch(Throwable throwable) {
		System.out.print("An exception was thrown in the root resource");
	}
	
	@Override
	protected void doRelease() throws ResourceException{
		System.out.print("The Root Resouce was released.\n");
	}
	
	@Get("txt")
	public String getSubordinatedResourcesString(){
		return "Personas/\nDiscograficas/\nGruposMusicales/";
	}
	@Get("xml")
	public Representation getSubordinatedResourcesXml() throws IOException{
		DomRepresentation result = new DomRepresentation();
		result.setIndenting(true);
		Document doc = result.getDocument();
		Element recursos = doc.createElement("raiz");	
		doc.appendChild(recursos);
		
		Element personas = doc.createElement("link");
		personas.setAttribute("name", "personas");
		personas.setAttribute("type", "simple");
		personas.setAttribute("href", "personas/");
		recursos.appendChild(personas);
		
		Element discograficas = doc.createElement("link");
		discograficas.setAttribute("name", "discograficas");
		discograficas.setAttribute("type", "simple");
		discograficas.setAttribute("href", "discograficas/");
		recursos.appendChild(discograficas);
		
		Element gruposMusicales = doc.createElement("link");
		gruposMusicales.setAttribute("name", "gruposMusicales");
		gruposMusicales.setAttribute("type", "simple");
		gruposMusicales.setAttribute("href", "gruposMusicales/");
		recursos.appendChild(gruposMusicales);
		
		
		
		return result;
	}
	
}
