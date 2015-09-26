package assignment_1;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapSort {
	// Sort a map by the values.
	public static Map<Object, Double> sort(Map<Object, Double> map) {

		List<Entry<Object, Double>> entries = new LinkedList<Entry<Object, Double>>(
				map.entrySet());
		Collections.sort(entries, new Comparator<Entry<Object, Double>>() {
			@Override
			public int compare(Entry<Object, Double> entry1,
					Entry<Object, Double> entry2) {
				if (entry1.getKey() instanceof String
						&& ((String) entry1.getKey()).equals("undefined"))
					return 1;
				return Double.compare(entry2.getValue(), entry1.getValue());
			}
		});

		Map<Object, Double> sorted = new LinkedHashMap<Object, Double>();
		for (Entry<Object, Double> entry : entries) {
			sorted.put(entry.getKey(), entry.getValue());
		}
		return sorted;
	}
}
