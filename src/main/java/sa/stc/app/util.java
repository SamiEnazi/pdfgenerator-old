package sa.stc.app;

import java.util.Map;

public class util {
	public static String replaceAllWithMap(String base, Map<String, String> dict) {
		for (String key : dict.keySet()) {
			base = base.replaceAll(key, dict.get(key));
		}
		return base;
	}

	public static String createRows(String[][] rows,boolean withIndex) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < rows.length; i++) {
			sb.append("<tr>");
			if (withIndex) {				
				sb.append(String.format("<td>%d</td>", i + 1));
			}
			for (int j = 0; j < rows[i].length; j++) {
				sb.append(String.format("<td>%s</td>", rows[i][j]));
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
}
