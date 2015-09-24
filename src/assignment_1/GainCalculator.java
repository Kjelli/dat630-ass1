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

			Object median = mean(search, attrName);

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
			int underTotal = underPlus + underMinus;
			int overTotal = overPlus + overMinus;

			System.out.println("total: " + total);

			double underEntropy = -underPlus * 1.0f / underTotal
					* Math.log10(underPlus * 1.0f / underTotal) / Math.log10(2)
					- underMinus * 1.0f / underTotal
					* Math.log10(underMinus * 1.0f / underTotal)
					/ Math.log10(2);

			System.out.println("underEntropy: " + underEntropy);

			double overEntropy = -overPlus * 1.0f / overTotal
					* Math.log10(overPlus * 1.0f / overTotal) / Math.log10(2)
					- overMinus * 1.0f / overTotal
					* Math.log10(overMinus * 1.0f / overTotal) / Math.log10(2);

			System.out.println("overEntropy: " + overEntropy);

			double routeEntropy = -underTotal * 1.0f / total
					* Math.log10(underTotal * 1.0f / total) / Math.log10(2)
					- overTotal * 1.0f / total
					* Math.log10(overTotal * 1.0f / total) / Math.log10(2);

			System.out.println("routeEntropy: " + routeEntropy);

			double gain = routeEntropy
					- (underTotal * 1.0f / total * underEntropy + overTotal
							* 1.0f / total * overEntropy);
			return gain;
		} else {
			Main.printAttributeResults(search, attrName, 10000);
		}

		return 0.0f;
	}

	public Object mean(Search search, String attrName) {
		Map<Object, Double> map = search.valuesFor(attrName, true);
		Iterator<Object> keyIterator = map.keySet().iterator();
		Object median = null;

		int count = 0;
		int values = 0;

		for (int i = 0; i < map.size(); i++) {
			Object key = keyIterator.next();
			int value = (int) key;
			values += value * map.get(key);
			count += map.get(key);
		}
		return (int) Math.floor(values * 1.0f / count);
	}
}
