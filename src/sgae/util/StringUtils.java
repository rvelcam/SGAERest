package sgae.util;


public class StringUtils {	

	public static String[] quitarEspacios(String[] split) {		
		for(int i = 0; i < split.length; i++) {
			split[i] = split[i].trim();
		}
		return split;
	}
}
