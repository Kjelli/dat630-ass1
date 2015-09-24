package assignment_1;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static assignment_1.Main.OVER50K;

public class GainCalculator {

	public double computeGain(List<Adult> data, String attrName) {
		Search search = new Search().from(data).search();
		boolean numeric = false;
		for (int i = 0; i < Adult.ATT.length; i++) {
			if (Adult.ATT[i].equals(attrName)) {
				switch (i) {
				case 0:
				case 2:
				case 4:
				case 10:
				case 11:
				case 12:
					numeric = true;
					break;
				default:
					break;
				}
			}
		}

		if (numeric) {
			
			Object median = median(search, attrName);
			
			System.out.println(median);
			
			int underPlus = new Search().from(data).filter()
					.attribute(attrName).lessThan(median).filter()
					.attribute(OVER50K).equalTo(true).search().toList().size();

			System.out.println("underPlus: " + underPlus);

			int overPlus = new Search().from(data).filter().attribute(attrName)
					.greaterThan(median).filter().attribute(OVER50K)
					.equalTo(true).search().toList().size();

			System.out.println("overPlus: " + overPlus);

			int underMinus = new Search().from(data).filter()
					.attribute(attrName).lessThan(median).filter()
					.attribute(OVER50K).equalTo(false).search().toList().size();

			System.out.println("underMinus: " + underMinus);

			int overMinus = new Search().from(data).filter()
					.attribute(attrName).greaterOrEqualTo(median).filter()
					.attribute(OVER50K).equalTo(false).search().toList().size();

			System.out.println("overMinus: " + overMinus);

			int total = data.size();

			System.out.println("total: " + total);

			double underEntropy = -underPlus / total
					* Math.log10(underPlus / total) / Math.log10(2)
					- underMinus / total * Math.log10(underMinus / total)
					/ Math.log10(2);

			System.out.println("underEntropy: " + underEntropy);

			double overEntropy = -overPlus / total
					* Math.log10(overPlus / total) / Math.log10(2) - overMinus
					/ total * Math.log10(overMinus / total) / Math.log10(2);

			System.out.println("overEntropy: " + overEntropy);

			double routeEntropy = -(underPlus + underMinus) / total
					* Math.log10((underPlus + underMinus) / total)
					/ Math.log10(2) - (overPlus + overMinus) / total
					* Math.log10((overPlus + overMinus) / total)
					/ Math.log10(2);

			System.out.println("routeEntropy: " + routeEntropy);

			double gain = routeEntropy - (underPlus + underMinus) / total
					* (underEntropy) - (overPlus + overMinus) / total
					* (overEntropy);
			return gain;
		} else {
			// TODO
		}

		return 0.0f;
	}

	public Object median(Search search, String attrName) {
		Map<Object, Double> map = search.valuesFor(attrName, true);
		Iterator<Object> keyIterator = map.keySet().iterator();
		Object median = null;
		for (int i = 0; i < map.size() / 2 + 1; i++) {
			keyIterator.next();
			if (i == map.size() / 2) {
				median = keyIterator.next();
			}
		}
		return map.get(median);
	}
}
