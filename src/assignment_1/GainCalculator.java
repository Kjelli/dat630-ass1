package assignment_1;

import static assignment_1.Main.OVER50K;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GainCalculator {

	public Map computeGains(List<Adult> data, String[] attrNames) {
		Map<Object, Double> map = new HashMap<>();
		for (String s : attrNames) {
			map.put(s, computeGain(data, s));
		}
		
		map = MapSort.sort(map);
		
		return map;
	}

	public double computeGain(List<Adult> data, String attrName) {

		if (attrName.equals(OVER50K)) {
			return -1;
		}
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

		Map<Object, Double> map = search.valuesFor(attrName, true);

		double total = 0;

		for (double d : map.values()) {
			total += d;
		}

		if (numeric) {

			Object mean = mean(search, attrName);

			double underPlus = new Search().from(data).filter()
					.attribute(attrName).lessThan(mean).search().filter()
					.attribute(OVER50K).equalTo(true).search().toList().size();

			double overPlus = new Search().from(data).filter()
					.attribute(attrName).greaterThan(mean).search().filter()
					.attribute(OVER50K).equalTo(true).search().toList().size();

			double underMinus = new Search().from(data).filter()
					.attribute(attrName).lessThan(mean).search().filter()
					.attribute(OVER50K).equalTo(false).search().toList().size();

			double overMinus = new Search().from(data).filter()
					.attribute(attrName).greaterOrEqualTo(mean).search()
					.filter().attribute(OVER50K).equalTo(false).search()
					.toList().size();

			double under_before = new Search().from(data).filter()
					.attribute(OVER50K).equalTo(false).search().toList().size();
			double over_before = data.size() - under_before;

			double entropy_before = entropy(under_before, over_before);

			double entropy_after = (overPlus + overMinus) / total
					* entropy(overMinus, overPlus) + (underPlus + underMinus)
					/ total * entropy(underMinus, underPlus);

			double gain = entropy_before - entropy_after;

			return gain;
		} else {

			double under_before = new Search().from(data).filter()
					.attribute(OVER50K).equalTo(false).search().toList().size();
			double over_before = data.size() - under_before;

			double entropy_before = entropy(under_before, over_before);
			double entropy_after = 0;

			for (Entry<Object, Double> entry : map.entrySet()) {

				double over = new Search().from(data).filter()
						.attribute(attrName).equalTo(entry.getKey()).filter()
						.attribute(OVER50K).equalTo(true).search().toList()
						.size();

				double under = new Search().from(data).filter()
						.attribute(attrName).equalTo(entry.getKey()).filter()
						.attribute(OVER50K).equalTo(false).search().toList()
						.size();

				entropy_after += (over + under) / total * entropy(under, over);
			}

			return entropy_before - entropy_after;

		}
	}

	public double entropy(double under, double over) {
		double result = -over / (over + under)
				* Math.log10(over / (over + under)) / Math.log10(2) - under
				/ (over + under) * Math.log10(under / (over + under))
				/ Math.log10(2);
		return Double.isNaN(result) ? 1 : result;
	}

	public Object mean(Search search, String attrName) {
		Map<Object, Double> map = search.valuesFor(attrName, true);
		Iterator<Object> keyIterator = map.keySet().iterator();

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

	public Object median(Search search, String attrName) {
		Map<Object, Double> map = search.valuesFor(attrName, true);
		Iterator<Object> keyIterator = map.keySet().iterator();

		Object median = null;

		for (int i = 0; i < map.size() / 2 + 1; i++) {
			Object key = keyIterator.next();
			if (i == map.size() / 2) {
				median = key;
			}

		}
		return median;
	}
}
