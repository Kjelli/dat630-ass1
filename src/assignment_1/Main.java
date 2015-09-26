package assignment_1;

import static assignment_1.Adult.ATT;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {

	public static final String AGE = ATT[0], WORKCLASS = ATT[1],
			FNLWGT = ATT[2], EDUCATION = ATT[3], EDUCATION_NUM = ATT[4],
			MARITAL_STATUS = ATT[5], OCCUPATION = ATT[6],
			RELATIONSHIP = ATT[7], RACE = ATT[8], SEX = ATT[9],
			CAPITAL_GAIN = ATT[10], CAPITAL_LOSS = ATT[11],
			HOURS_PER_WEEK = ATT[12], NATIVE_COUNTRY = ATT[13],
			OVER50K = ATT[14];

	static ArrayList<Adult> data;

	public static void main(String[] args) {
		File file = new File("adult.test");
		String outputFilename = "output/task1.out";
		boolean training = false;
		data = readLines(file);

		DecisionTree gt = new GainTree(training, outputFilename);

		DecisionTreeResult result = gt.estimate(data);
		System.out
				.println(result == null ? "Currently not in training set.. see output"
						: result);

		// System.out.println(new GainCalculator().computeGain(data,
		// OCCUPATION));

		// System.out.println(NATIVE_COUNTRY+": "+new
		// GainCalculator().computeGain(data, NATIVE_COUNTRY));

		Search s = new Search().from(data).runThrough(gt, true).filter()
				.attribute(SEX).equalTo("Female")
				// .filter().attribute(MARITAL_STATUS).equalTo("Married-civ-spouse")
				// .filter().attribute(RELATIONSHIP).equalTo("Wife")
				// .filter().attribute(WORKCLASS).equalTo("Without-pay")
				// .filter().attribute(CAPITAL_LOSS).greaterThan(0)
				// .filter().attribute(CAPITAL_GAIN).greaterThan(0)
				// .filter().attribute(CAPITAL_GAIN).lessThan(7000)
				// .filter().attribute(AGE).lessThan(30)
				// .filter().attribute(AGE).greaterThan(65)
				// .filter().attribute(NATIVE_COUNTRY).equalTo("El-Salvador")
				.filter().attribute(EDUCATION_NUM).greaterThan(15)
				// .filter().attribute(RELATIONSHIP).equalTo("Not-in-family")
				// .filter().attribute(HOURS_PER_WEEK).lessThan(32)
				// .filter().attribute(OCCUPATION).equalTo("Prof-specialty")
				// .filter().attribute(MARITAL_STATUS).equalTo("Divorced")
				.filter().attribute(OVER50K).equalTo(true).search();

		//printResultsMeta(s);
		//printHighCorrelations(s, 36000);
		// printAttributeResults(s,AGE, 99999);

		// System.out.println("Computing gains.");
		//
		// @SuppressWarnings("unchecked")
		// Map<Object, Double> gains = new GainCalculator().computeGains(
		// s.toList(), ATT);
		// for (Entry<Object, Double> entry : gains.entrySet()) {
		// System.out.println(entry.getKey() + ": " + entry.getValue());
		// }

	}

	@SuppressWarnings("unused")
	private static void printHighCorrelations(Search search, int take) {
		StringBuilder bs = new StringBuilder("");

		String divider = "#########################";

		bs.append(divider + "\n");
		bs.append("Highest correlations: \n\n");
		int count = 0;
		Map<Object, Double> map = search.highCorrelations();
		for (Entry<Object, Double> entry : map.entrySet()) {
			bs.append(entry.getKey() + "\n");
			if (++count > take) {
				bs.append("...\n\nOmitted " + (map.size() - count) + " values");
			}
		}

		System.out.println(bs.toString());
	}

	static void printAttributeResults(Search search, String attributeName,
			int take) {
		StringBuilder bs = new StringBuilder("");
		String divider = "=========================";

		bs.append(divider + "\n");
		bs.append("Displaying top " + take + " values of " + attributeName
				+ ":\n\n");
		Map<Object, Double> map = search.valuesFor(attributeName, true);
		int count = 0;
		for (Entry<Object, Double> entry : map.entrySet()) {
			if (++count > take) {
				bs.append("...\n\nOmitted " + (map.size() - count) + " values");
				break;
			}
			double ratio = entry.getValue() * 100.0f / data.size();
			double relratio = entry.getValue() * 100.0f
					/ search.toList().size();
			bs.append(entry.getKey()
					+ ": "
					+ (int) Math.round(entry.getValue())
					+ String.format(" \t(%.2f%% of total) [%.2f %% of hits]\n",
							ratio, relratio));
		}
		System.out.println(bs.toString());
	}

	static void printResultsMeta(Search search) {
		StringBuilder bs = new StringBuilder("");
		String divider = "=========================";
		String divider2 = "-------------------------";

		List<Adult> searchResults = search.toList();

		bs.append(divider + "\nFilters:\n\n");
		for (Filter filter : search.getFilters()) {
			bs.append(filter + "\n");
		}
		bs.append(divider2 + "\n");
		bs.append("Dataset Total: \t" + data.size() + "\n");
		bs.append("Hits total: \t" + searchResults.size() + "\n");
		bs.append("Dataset coverage: ");
		bs.append(String.format("%.2f",
				(searchResults.size() * 100.0f / data.size())));
		bs.append("%\n");

		System.out.println(bs.toString());

	}

	// Read the lines from the textfile.
	public static ArrayList<Adult> readLines(File file) {
		ArrayList<Adult> adults = new ArrayList<Adult>();
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (; scan.hasNextLine();) {
			String line = scan.nextLine();
			if (line.isEmpty())
				continue;
			adults.add(new Adult(line));

			// TODO: think of the ? (missing) attributes
		}

		scan.close();

		return adults;
	}

}
