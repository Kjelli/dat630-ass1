package websearch;

import java.util.Map;
import java.util.Map.Entry;

public class Histogram {
	static int maxWidth = 15;
	static double scale;
	public static void drawCategorical(Search search, String attributeName) {
		Map<Object,Double> map = search.valuesFor(attributeName, true);
		System.out.println("\nHistogram for " + attributeName);
		
		int max = -1;
		for(Double d : map.values()){
			if(d > max) max = (int)Math.round(d);
		}
		
		scale = maxWidth*1.0f/max*1.0f;
		
		for(Entry<Object,Double> entry : map.entrySet()){
			System.out.println(entry.getKey());
			for(int i = 0; i< entry.getValue()*scale ; i++){
				System.out.print("#");
			}
			for(int i = (int)(entry.getValue()*scale); i < maxWidth; i++ ){
				System.out.print("_");
			}
			System.out.println();
		}
		
		System.out.println();
	}
}
