package sgae.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {	

	public static String[] quitarEspacios(String[] split) {
		List<String>resultado= new ArrayList<String>(); 
		for(int i = 0; i < split.length; i++) {
			resultado.add(split[i].trim());
		}
		return (String[]) resultado.toArray();
	}
}
