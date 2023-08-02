package sa.stc.app;

import java.util.Map;

public class util {
	public static String replaceAllWithMap(String base,Map<String,String> dict) {
		for (String key:dict.keySet()) {
			base = base.replaceAll(key,dict.get(key));
		}
		return base;
	}
}
