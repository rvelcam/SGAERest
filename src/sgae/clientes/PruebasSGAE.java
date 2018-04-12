package sgae.clientes;
import java.text.ParseException;
import java.util.List;

import sgae.nucleo.gruposMusicales.ControladorGruposMusicales;
import sgae.nucleo.gruposMusicales.ExcepcionGruposMusicales;
import sgae.nucleo.discograficas.ControladorDiscograficas;
import sgae.nucleo.discograficas.ExcepcionContratos;
import sgae.nucleo.discograficas.ExcepcionDiscograficas;
import sgae.nucleo.discograficas.InterfazControladorDiscograficas;
import sgae.nucleo.personas.ControladorPersonas;
import sgae.nucleo.personas.ExcepcionPersonas;
import sgae.nucleo.personas.InterfazControladorPersonas;
import sgae.nucleo.personas.ControladorPersonas;
/**
 * Clase de prueba de la aplicaci�n.
 * @author Eduardo G�mez S�nchez y Manuel Rodr�guez Cayetano. ETSIT UVa.
 * @version 1.0
 *
 */
public class PruebasSGAE {
	/**
	 * M�todo principal para arrancar la aplicaci�n.
	 * @param args lista de argumentos de ejecuci�n del programa
	 */
	/**
	 * @param args argumentos de ejecuci�n del programa
	 */
	public static void main(String[] args) {
		// Lo primero es crear los controladores que sirven de punto de acceso a la capa de negocio
		InterfazControladorPersonas cp = new ControladorPersonas();
		ControladorGruposMusicales cgm = 
				new ControladorGruposMusicales(cp);
		InterfazControladorDiscograficas cd = new ControladorDiscograficas(cgm);
	
		
		//Variable auxiliar para almacenar listados
		List<String> listado;
		//Variable auxiliar para almacenar la ficha de un �tem
		String fichaCompleta;
		//Identificadores de persona, para asignarlos en la creaci�n y hacer referencias despu�s
		String pBart = "00000000A",
			   pLisa = "11111111B",
			   pMaggie = "22222222C",
			   pHomer = "33333333D",
			   pMarge = "44444444E",
			   pAbe = "55555555F",
			   pBurns = "66666666G",
			   pCarl = "77777777H",
			   pSkinner = "88888888I";

		///////////////////////////////////////////////////////////////////////
		// CASOS DE USO DE PERSONAS ///////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		try {
			// Creaci�n de personas
			System.out.println("Comenzando creaci�n de personas...");
			cp.crearPersona(pBart, "Bart", "Simpson", 
					null);
			cp.crearPersona(pLisa, "Lisa", "Simpson", 
					"02-02-2005");
			cp.crearPersona(pMaggie, "Maggie", "Simpson", 
					"03-03-2014");
			cp.crearPersona(pHomer, "Homer", "Simpson", 
					"01-08-1970");
			cp.crearPersona(pMarge, "Margaret", "Simpson", 
					"01-10-1975");			
			cp.crearPersona(pAbe, "Abe", "Simpson", 
					"01-03-1940");			
			cp.crearPersona(pBurns, "Monty", "Burns", 
					"01-06-1930");			
			cp.crearPersona(pCarl, "Carl", "Carlson", 
					"01-07-1965");			
			cp.crearPersona(pSkinner, "Seymor", "Skinner", 
					"01-08-1969");				
			System.out.println("Creaci�n de personas completada\n");

			// Listado de personas
			System.out.println("Comenzando listado de personas...");
			listado = cp.listarPersonas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de personas completado\n");

			// Visi�n detallada de personas
			System.out.println("Comenzando visi�n detallada de personas...");
			fichaCompleta = cp.verPersona(pMarge);
			System.out.println("Datos de Marge Simpson:\n" + fichaCompleta);
			fichaCompleta = cp.verPersona(pAbe);
			System.out.println("Datos de Abe Simpson:\n" + fichaCompleta);
			System.out.println("Visi�n detallada de personas completada\n");
			
			// Modificaci�n de personas
			System.out.println("Comenzando modificaci�n de personas...");
			cp.modificarPersona(pMarge, "Marge", "Simpson", 
					"01-10-1975");			
			cp.modificarPersona(pAbe, "Abe", "Simpson", 
					"01-03-1940");
			System.out.println("Modificaci�n de personas completada\n");

			// Visi�n detallada de personas
			System.out.println("Comenzando visi�n detallada de personas...");
			fichaCompleta = cp.verPersona(pMarge);
			System.out.println("Datos de Marge Simpson:\n" + fichaCompleta);
			fichaCompleta = cp.verPersona(pAbe);
			System.out.println("Datos de Abe Simpson:\n" + fichaCompleta);
			System.out.println("Visi�n detallada de personas completada\n");
			
			// Borrado de personas
			System.out.println("Comenzando borrado de personas...");
			cp.borrarPersona(pAbe); // Borramos a Abe Simpson
			System.out.println("Borrado completado\n");
			
			// Listado de personas
			System.out.println("Comenzando listado de personas...");
			listado = cp.listarPersonas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de personas completado\n");
			
		} catch (ParseException e) {
			System.err.println("Alguna de las fechas proporcionadas no es v�lida.");
		} catch (ExcepcionPersonas e) {
			System.err.println("Ha fallado una operaci�n para la persona con DNI " + 
					e.getDniPersona() + " por la siguiente raz�n: " + 
					e.getCausaFallo());
		}
		
		
		///////////////////////////////////////////////////////////////////////
		/// CASOS DE USO DE GRUPOS MUSICALES //////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		
		try {
			// Creaci�n de grupos musicales
			System.out.println("Comenzando creaci�n de grupos musicales...");
			cgm.crearGrupoMusical("D0123456D", "Jamiroquai", "02-04-1992");
			cgm.crearGrupoMusical("E0123456E", "Blur", "03-05-1988");
			System.out.println("Creaci�n de grupos musicales completada\n");

			// Listado de grupos musicales
			System.out.println("Comenzando listado de grupos musicales...");
			listado = cgm.listarGruposMusicales();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de grupos musicales completado\n");

			// Visi�n detallada de grupos musicales
			System.out.println("Comenzando visi�n detallada de grupos musicales...");
			String fichaDetallada = cgm.verGrupoMusical("D0123456D");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			fichaDetallada = cgm.verGrupoMusical("E0123456E");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			System.out.println("Visi�n detallada de grupos musicales completada\n");
			
			// Modificaci�n de grupos musicales
			System.out.println("Comenzando modificaci�n de compa��as discogr�ficas...");
			cgm.modificarGrupoMusical("D0123456D", "Jamiroquai", "01-06-1992");
			cgm.modificarGrupoMusical("E0123456E", "Blur", "01-05-1988");
			System.out.println("Modificaci�n de grupos musicales completada\n");

			// Visi�n detallada de grupos musicales
			System.out.println("Comenzando visi�n detallada de grupos musicales...");
			fichaDetallada = cgm.verGrupoMusical("D0123456D");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			fichaDetallada = cgm.verGrupoMusical("E0123456E");
			System.out.println("Datos del grupo musical:\n" + fichaDetallada);
			System.out.println("Visi�n detallada de grupos musicales completada\n");
			
			// Borrado de grupos musicales
			System.out.println("Comenzando borrado de grupos musicales...");
			cgm.borrarGrupoMusical("E0123456E");
			System.out.println("Borrado de grupos musicales completada\n");
			
			// Listado de grupos musicales
			System.out.println("Comenzando listado de grupos musicales...");
			listado = cgm.listarGruposMusicales();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de grupos musicales completado\n");

			} catch (ExcepcionGruposMusicales e) {
				System.err.println("Ha fallado una operaci�n para la discogr�fica con CIF " + 
						e.getCif() + " por la siguiente raz�n: " + 
						e.getCausaFallo());
			} catch (ParseException e) {
				System.err.println("Alguna de las fechas proporcionadas no es v�lida.");
		}
		
		///////////////////////////////////////////////////////////////////////
		/// CASOS DE USO DE DISCOGR�FICAS //////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		
		try {
			// Creaci�n de compa��as discogr�ficas
			System.out.println("Comenzando creaci�n de compa��as discogr�ficas...");
			cd.crearDiscografica("A0123456A", "CBS", "La Casa Blanca");
			cd.crearDiscografica("B0123456B", "Virgin Records", "Las Vegas");
			cd.crearDiscografica("C0123456C", "Dro", "Madrid");
			System.out.println("Creaci�n de compa��as discogr�ficas completada\n");

			// Listado de compa��as discogr�ficas
			System.out.println("Comenzando listado de compa��as discogr�ficas...");
			listado = cd.listarDiscograficas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de compa��as discogr�ficas completado\n");

			// Visi�n detallada de compa��as discogr�ficas
			System.out.println("Comenzando visi�n detallada de compa��as discogr�ficas...");
			String fichaDetallada = cd.verDiscografica("A0123456A");
			System.out.println("Datos de la compa��a:\n" + fichaDetallada);
			fichaDetallada = cd.verDiscografica("B0123456B");
			System.out.println("Datos de la compa��a:\n" + fichaDetallada);
			System.out.println("Visi�n detallada de compa��as discogr�ficas completada\n");
			
			// Modificaci�n de compa��as discogr�ficas
			System.out.println("Comenzando modificaci�n de compa��as discogr�ficas...");
			cd.modificarDiscografica("A0123456A", "CBS", "Springfield");
			cd.modificarDiscografica("B0123456B", "Virgin Records", "Londres");
			System.out.println("Modificaci�n de compa��as discogr�ficas completada\n");

			// Visi�n detallada de compa��as discogr�ficas
			System.out.println("Comenzando visi�n detallada de compa��as discogr�ficas...");
			fichaDetallada = cd.verDiscografica("A0123456A");
			System.out.println("Datos de la compa��a:\n" + fichaDetallada);
			fichaDetallada = cd.verDiscografica("B0123456B");
			System.out.println("Datos de la compa��a:\n" + fichaDetallada);
			System.out.println("Visi�n detallada de compa��as discogr�ficas completada\n");
			
			// Borrado de compa��as discogr�ficas
			System.out.println("Comenzando borrado de compa��as discogr�ficas...");
			cd.borrarDiscografica("B0123456B");
			System.out.println("Borrado de compa��as discogr�ficas completada\n");
			
			// Listado de compa��as discogr�ficas
			System.out.println("Comenzando listado de compa��as discogr�ficas...");
			listado = cd.listarDiscograficas();
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de compa��as discogr�ficas completado\n");

			} catch (ExcepcionDiscograficas e) {
				System.err.println("Ha fallado una operaci�n para la discogr�fica con CIF " + 
						e.getCif() + " por la siguiente raz�n: " + 
					e	.getCausaFallo());
		}

	
		///////////////////////////////////////////////////////////////////////
		// CASOS DE USO DE CONTRATOS //////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////
		try {
			// Creaci�n de contratos 
			System.out.println("Comenzando creaci�n de contratos sin partir "+ 
					"de una oferta...");
			cd.nuevoContrato("A0123456A", "D0123456D",  
					"01-01-2017", "01-01-2018", 3000); // A Jamiroquai en la CBS
			cd.nuevoContrato("C0123456C", "E0123456E",  
					"01-01-2016", "01-01-2018", 1000);	// A Blur en Dro
			System.out.println("Creaci�n de contratos completada\n");
			
			// Listado de contratos por compa��as discogr�ficas
			System.out.println("Comenzando listado de contratos por compa��as discogr�ficas...");
			listado = cd.verContratosVigentesDeDiscografica("A0123456A");
			System.out.println("Contratos de la compa��a discogr�fica");
			for(String s : listado) {
				System.out.println(s);
			}
			listado = cd.verContratosAnterioresDeDiscografica("C0123456C");
			System.out.println("Contratos del grupo musical");
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de contratos por compa��as discogr�ficas completado\n");
			
			// Finalizaci�n de contratos
			System.out.println("Comenzando finalizaci�n de contratos...");
			cd.terminarContrato("A0123456A", "00000000A"); 
			cd.terminarContrato("C0123456C", "11111111B"); 
			System.out.println("Finalizaci�n de contratos completada\n");

			// Listado de contratos por compa��as discogr�ficas
			System.out.println("Comenzando listado de contratos por compa��as discogr�ficas...");
			listado = cd.verContratosVigentesDeDiscografica("A0123456A");
			System.out.println("Contratos de la compa�ia discogr�fica");
			for(String s : listado) {
				System.out.println(s);
			}
			listado = cd.verContratosVigentesDeDiscografica("C0123456C");
			System.out.println("Contratos de la compa�ia discogr�fica");
			for(String s : listado) {
				System.out.println(s);
			}
			System.out.println("Listado de contratos por compa��as discogr�ficas completado\n");
	
			
		} catch (ExcepcionContratos e) {
			System.err.println("Ha fallado una operaci�n para el contrato con id " + 
					e.getIdContrato() + " por la siguiente raz�n: " + 
					e.getCausaFallo());
		} catch (ExcepcionDiscograficas e) {
			System.err.println("Ha fallado una operaci�n para la compa��a discogr�fica con CIF " + 
				e.getCif() + " por la siguiente raz�n: " + 
				e.getCausaFallo());
		}

		// // /////////////////////////////////////////////////////////////////
		// CASOS DE USO QUE OBTIENEN ESTAD�STICAS /////////////////////////////
		///////////////////////////////////////////////////////////////////////

		//Poblaci�n activa
		System.out.println("Comenzando recuperaci�n de estad�sticas...");
		int numeroPersonas = cp.verNumeroPersonas();
		System.out.println("Total personas: " + numeroPersonas);
		System.out.println("Recuperaci�n de estad�sticas completada\n");
	}
}
