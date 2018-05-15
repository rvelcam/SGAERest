package sgae.servidor.aplicacion;

import java.io.IOException;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * Clase que representa a los recursos.
 * 
 * @author Ra�l Velasco Caminero y H�ctor Gonz�lez Beltr�n. ETSIT UVa.
 * @version 1.0
 */
public class RootServerResource extends ServerResource{
	
	/**
	 * Constructor en el que se habilita la negociaci�n de contenido.
	 */
	public RootServerResource(){
		setNegotiated(true);
	}
	
	/**
	 * M�todo utilizado para a�adir tareas a la inicializaci�n est�ndar del recurso.
	 */
	@Override
	protected void doInit() throws ResourceException{
		System.out.print("The Root Resouce was inicialized");
	}
	
	/**
	 * M�todo utilizado para a�adir tareas a la gestion est�ndar de excepciones.
	 */
	@Override
	protected void doCatch(Throwable throwable) {
		System.out.print("An exception was thrown in the root resource");
	}
	
	/**
	 * M�todo utilizado para a�adir tareas a la gestion est�ndar de excepciones.
	 */
	@Override
	protected void doRelease() throws ResourceException{
		System.out.print("The Root Resouce was released.\n");
	}
	/**
	 * M�todo que invoca la operaci�n GET sobre el recurso Root.
	 * @return objeto del tipo String que contiene los recursos que cuelgan de �ste
	 */
	@Get("txt")
	public String getSubordinatedResourcesString(){
		return "Personas/\nDiscograficas/\nGruposMusicales/";
	}
	/**
	 * M�todo que invoca la operaci�n GET sobre el recurso Root.
	 * @return objeto del tipo Representation que contiene la representaci�n con los recursos
	 * que cuelgan de �ste en formato xml
	 * @throws IOException error al generar el recurso raiz
	 */
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
