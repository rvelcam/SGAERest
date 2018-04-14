package sgae.clientes;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class ClienteSGAE {

	public static void main(String[] args) {		
		
		//datos necesarios para crear todos los recursos
		String rootURL = "http://localhost:8111/";
		//datos persona
		String DNI = "12345678Z";
		String nombre = "Juan";
		String apellidos = "Rodriguez";
		String fechaNacimiento = "10-08-1985";
		//datos grupo musical
		String CIF = "1A";
		String nombreGrupoMusical = "ptpdx04";
		String fechaCreacion = "09-01-2018";
		//datos de un album
		String idAlbum="";
		String tituloAlbum = "Nueva asignatura";
		String fechaPublicacion = "09-04-2018";
		int ejemplaresVendidos=45;
		//datos de una pista
		String idPista="";
		String tituloPista = "Nuevo tema";
		int duracion = 45;
		Representation representation;
		Representation result;
		//crear una persona
		try {
			ClientResource nuevaPersona = new ClientResource (rootURL + "personas/"+DNI);			
			String data = "nombre="+nombre+"&apellidos="+apellidos+"&fechaNacimiento="+fechaNacimiento;			
			representation = new StringRepresentation(data, MediaType.APPLICATION_WWW_FORM);
			result = nuevaPersona.put(representation);
			System.out.println("Creacion de una persona\n--------------------------------------------");
			System.out.println("Status : " + nuevaPersona.getResponse());			
			System.out.println("Resultado : " + result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//listar todas las personas
		try {
			ClientResource listaPersonas = new ClientResource (rootURL + "personas/");
			result = listaPersonas.get();
			System.out.println("Listado de todas las personas\n--------------------------------------------");
			System.out.println("Status : " + listaPersonas.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//crear un grupo musical
		try {
			ClientResource nuevoGrupoMusical = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/");
			String data = "nombre="+nombreGrupoMusical+"&fechaCreacion="+fechaCreacion;
			representation = new StringRepresentation(data, MediaType.APPLICATION_WWW_FORM);
			result = nuevoGrupoMusical.put(representation);
			System.out.println("Creacion de un grupo musical\n--------------------------------------------");
			System.out.println("Status : " + nuevoGrupoMusical.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
	        System.out.println(e.getResponse().getEntityAsText());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//listar todos los grupos musicales
		try {
			ClientResource listaGruposMusicales = new ClientResource (rootURL + "gruposMusicales/");
			result = listaGruposMusicales.get();
			System.out.println("Listado de todos los grupos Musicales\n--------------------------------------------");
			System.out.println("Status : " + listaGruposMusicales.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//añadir una persona a un grupo musical
		try {
			ClientResource nuevoMiembroGrupoMusical = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/");
			String data = "nombre="+nombreGrupoMusical+"&fechaCreacion="+fechaCreacion+"&miembros="+DNI;
			representation = new StringRepresentation(data, MediaType.APPLICATION_WWW_FORM);
			result = nuevoMiembroGrupoMusical.put(representation);
			System.out.println("Añadir un miembro a un grupo musical\n--------------------------------------------");
			System.out.println("Status : " + nuevoMiembroGrupoMusical.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
	        System.out.println(e.getResponse().getEntityAsText());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//listar todos los miembros de un grupo musical
		try {
			ClientResource listaMiembrosGruposMusicales = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/miembros");
			result = listaMiembrosGruposMusicales.get();
			System.out.println("Listado de todos los miembros de un grupo Musicales\n--------------------------------------------");
			System.out.println("Status : " + listaMiembrosGruposMusicales.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*eliminar un miembro de un grupo musical -> para eliminar un miembro se envia la lista completa de miembros menos el
		  que se quiere eliminar*/
		try {
			ClientResource eliminarMiembroGrupoMusical = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/");
			String data = "nombre="+nombreGrupoMusical+"&fechaCreacion="+fechaCreacion+"&miembros=";
			representation = new StringRepresentation(data, MediaType.APPLICATION_WWW_FORM);
			result = eliminarMiembroGrupoMusical.put(representation);
			System.out.println("Eliminar un miembro de un grupo musical\n--------------------------------------------");
			System.out.println("Status : " + eliminarMiembroGrupoMusical.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
		    System.out.println(e.getResponse().getEntityAsText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Comprobacion de que se ha eliminado correctamente ese miembro
		try {
			ClientResource listaMiembrosGruposMusicales = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/miembros");
			result = listaMiembrosGruposMusicales.get();
			System.out.println("Listado de todos los miembros de un grupo Musicales\n--------------------------------------------");
			System.out.println("Status : " + listaMiembrosGruposMusicales.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//añadir un album a un grupo musical
		try {
			ClientResource nuevoAlbum = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/albumes/");
			String data = "titulo="+tituloAlbum+"&fechaPublicacion="+fechaPublicacion+"&ejemplaresVendidos="+ejemplaresVendidos;
			representation = new StringRepresentation(data, MediaType.APPLICATION_WWW_FORM);
			result = nuevoAlbum.post(representation);
			idAlbum = result.getText();
			System.out.println("Creacion de un album\n--------------------------------------------");
			System.out.println("Status : " + nuevoAlbum.getResponse());
			System.out.println("Body : " + idAlbum);			
		}catch (ResourceException e) {
	        System.out.println(e.getResponse().getEntityAsText());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//listar todos los albumes de un grupo musical
		try {
			ClientResource listaAlbumesGruposMusicales = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/albumes/");
			result = listaAlbumesGruposMusicales.get();
			System.out.println("Listado de todos los albumes de un grupo Musicales\n--------------------------------------------");
			System.out.println("Status : " + listaAlbumesGruposMusicales.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//añadir una pista a un album
		try {
			ClientResource nuevaPista = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/albumes/"+idAlbum+"/pistas/");
			String data = "titulo="+tituloPista+"&duracion="+duracion;
			representation = new StringRepresentation(data, MediaType.APPLICATION_WWW_FORM);
			result = nuevaPista.post(representation);
			idPista = result.getText();
			System.out.println("Creacion de una pista\n--------------------------------------------");
			System.out.println("Status : " + nuevaPista.getResponse());
			System.out.println("Body : " + idPista);
		}catch (ResourceException e) {
	        System.out.println(e.getResponse().getEntityAsText());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//listar todas las pistas de un album
		try {
			ClientResource listaPistasAlbum = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/albumes/"+idAlbum+"/pistas/");
			result = listaPistasAlbum.get();
			System.out.println("Listado de todas las pistas de un album\n--------------------------------------------");
			System.out.println("Status : " + listaPistasAlbum.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//borrar una pista
		try {
			ClientResource eliminarPistaAlbum = new ClientResource (rootURL + "gruposMusicales/"+CIF+"/albumes/"+idAlbum+"/pistas/"+idPista);
			result = eliminarPistaAlbum.delete();
			System.out.println("Eliminacion de una pista de un album\n--------------------------------------------");
			System.out.println("Status : " + eliminarPistaAlbum.getResponse());
			System.out.println(result.getText());
		}catch (ResourceException e) {
            System.out.println(e.getResponse().getEntityAsText());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
