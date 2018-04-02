package sgae.util;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Clase auxiliar que envuelve una representaci�n XML y que permite ir 
 * a�adiendo elementos con o sin valores.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 */
public class GeneradorXml {
    /** Una representaci�n XML envuelta en esta clase */
    private DomRepresentation representacion;
    
    
    /**
     * Constructor que inicializa la representaci�n.
     * @throws IOException si se produce un error al crear la representaci�n
     */
    public GeneradorXml() throws IOException {
        super();
        representacion = new DomRepresentation(MediaType.TEXT_XML);
    }
    
    
    /**
     * M�todo que devuelve la representaci�n.
     * @return la representaci�n en formato XML
     * @throws IOException si se produce un error al manipular el documento XML
     */
    public DomRepresentation getRepresentacion() throws IOException {
        // Normaliza el documento antes de devolverlo
        representacion.getDocument().normalizeDocument();       
        return representacion;
    }    
    
    /**
     * M�todo para anexar a un elemento de un documento DOM un hijo que no toma 
     * valores.
     * @param elementoPadre nombre del elemento XML padre
     * @param nombreHijo nombre del elemento XML hijo
     * @return el elemento reci�n creado
     * @throws IOException si se produce un error al manipular el documento XML
     */
    public Element nuevoHijo(Element elementoPadre, String nombreHijo) 
        throws IOException {
        // Obtiene el documento DOM subyacente
        Document documento = representacion.getDocument();
        
        // Crea el elemento hijo
        Element elementoHijo = documento.createElement(nombreHijo);
        // Lo anexa al padre
        elementoPadre.appendChild(elementoHijo);  
        
        // Devuelve el elemento
        return elementoHijo;
    }    
    
    /**
     * M�todo para anexar a un elemento de un documento DOM un hijo d�ndole un 
     * valor textual.
     * @param elementoPadre nombre del elemento XML padre
     * @param nombreHijo nombre del elemento XML hijo
     * @param valorHijo contenido del elemento hijo
     * @return el elemento reci�n creado
     * @throws IOException si se produce un error al manipular el documento XML
     */
    public Element nuevoHijo(Element elementoPadre, String nombreHijo, 
                             String valorHijo) throws IOException {
        // Obtiene el documento DOM subyacente
        Document documento = representacion.getDocument();
        
        // Crea el elemento hijo
        Element elementoHijo = documento.createElement(nombreHijo);
        // Le da valor
        elementoHijo.appendChild(documento.createTextNode(valorHijo));
        // Lo anexa al padre
        elementoPadre.appendChild(elementoHijo);       
        
        // Devuelve el elemento
        return elementoHijo;
    }
    
    
    /**
     * M�todo para anexar a la ra�z de un documento DOM un hijo que no toma 
     * valores.
     * @param nombreHijo nombre del elemento XML hijo
     * @return el elemento reci�n creado
     * @throws IOException si se produce un error al manipular el documento XML
     */
    public Element nuevaRaiz(String nombreHijo) throws IOException {
        // Obtiene el documento DOM subyacente
        Document documento = representacion.getDocument();
        
        // Crea el elemento hijo
        Element elementoHijo = documento.createElement(nombreHijo);
        
        // A�ade los atributos con el espacio de nombres necesarios si usamos
        // referencias de tipo xlink (este espacio de nombres define atributos).
        elementoHijo.setAttribute("xmlns:xlink","http://www.w3.org/1999/xlink");
        // Lo anexa al documento
        documento.appendChild(elementoHijo);
        
        //Devuelve el elemento
        return elementoHijo;
    }    
    
    /**
     * M�todo para fijar un atributo del tipo xlink:href en un elemento del 
     * documento.
     * Fijar�, adem�s, el atributo xlink:type (tipo del enlace) a simple.
     * @param elemento elemento XML al que a�adir este atributo
     * @param uri URI que va a tomar el atributo
     */
    public void fijaHref(Element elemento, String uri) {
        // El atributo xlink:type que indica el tipo de enlace es obligatorio
        elemento.setAttribute("xlink:type", "simple");
        elemento.setAttribute("xlink:href", uri);
    }    
    
    /**
     * M�todo para anexar a un elemento de un documento DOM un hijo de tipo 
     * link que no toma valores.
     * @param elementoPadre nombre del elemento XML padre
     * @param nombreHijo nombre del elemento XML hijo
     * @param titulo t�tulo del enlace (vac�o o null si no se desea usar este 
     * campo)
     * @param uri URI del enlace
     * @return el elemento reci�n creado
     * @throws IOException si se produce un error al manipular el documento XML
     */
    public Element nuevoHijoLink(Element elementoPadre, String nombreHijo,
                                 String titulo, String uri) 
        throws IOException {
        // Obtiene el documento DOM subyacente
        Document documento = representacion.getDocument();
        
        // Crea el elemento hijo
        Element elementoHijo = documento.createElement(nombreHijo);
        // Fija la referencia (y el tipo del enlace)
        fijaHref(elementoHijo, uri);
        // Fija el t�tulo si existe
        if (titulo!=null && titulo.compareTo("") != 0)
            elementoHijo.setAttribute("xlink:title", titulo);
        // Lo anexa al padre
        elementoPadre.appendChild(elementoHijo);  
        
        // Devuelve el elemento
        return elementoHijo;
    }    
}
