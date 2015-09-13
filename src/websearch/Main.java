package websearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import websearch.DecisionTree.DecisionTreeResult;
import static websearch.Adult.*;

public class Main {

	static final String AGE = ATT[0], WORKCLASS = ATT[1], FNLWGT = ATT[2],
			EDUCATION = ATT[3], EDUCATION_NUM = ATT[4],
			MARITAL_STATUS = ATT[5], OCCUPATION = ATT[6],
			RELATIONSHIP = ATT[7], RACE = ATT[8], SEX = ATT[9],
			CAPITAL_GAIN = ATT[10], CAPITAL_LOSS = ATT[11],
			HOURS_PER_WEEK = ATT[12], NATIVE_COUNTRY = ATT[13],
			OVER50K = ATT[14];

	static ArrayList<Adult> data;

	public static void main(String[] args) {
		File file = new File("adult.data");
		data = readLines(file);

		search();

		DecisionTreeResult result = new DecisionTree().estimate(data);
		System.out.println("***************************");
		System.out.println("Your decision tree scored:\n" + result.count
				+ " out of " + result.total + "! ("
				+ String.format("%.2f%%)", result.ratio * 100.0f));

	}

	private static void search() {
		Search search = new Search().from(data)
		// .filter().attribute(HOURS_PER_WEEK).greaterOrEqualTo(40)
		// .filter().attribute(EDUCATION).either("Bachelors","HS-grad")
		// .filter().attribute(EDUCATION_NUM).greaterOrEqualTo(10)
		// .filter().attribute(SEX).equalTo("Male")
		// .filter().attribute(OCCUPATION).either("Exec-managerial","Prof-specialty","Sales")
		// .filter().attribute(CAPITAL_LOSS).lessOrEqualTo(0)
		// .filter().attribute(CAPITAL_GAIN).greaterThan(0)
		// .filter().attribute(MARITAL_STATUS).equalTo("Never-married")
		// .filter().attribute(NATIVE_COUNTRY).equalTo("United-States")
		// .filter().attribute(RACE).equalTo("White")
		// .filter().attribute(AGE).greaterThan(60)
		// .filter().attribute(RELATIONSHIP).equalTo("Husband")
		 .filter().attribute(OVER50K).equalTo(true)
				//.filter().attribute(SEX).equalTo("Female")
				.search();

		Search search2 = new Search().from(data)
				.filter().attribute(OVER50K).equalTo(false)
				.search();

		String C = HOURS_PER_WEEK;
		
		System.out.println("Over50k");
		printResultsMeta(search);
		Histogram.drawCategorical(search, C);
		System.out.println("Under 50k");
		printResultsMeta(search2);
		Histogram.drawCategorical(search2, C);

		 //printResultsMeta(search);

		// printAttributeResults(search, OVER50K, 3);

		// printHighCorrelations(search, 99);
	}

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
				System.out.println(bs.toString());
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
				System.out.println(bs.toString());
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

		ArrayList<Adult> searchResults = search.toList();

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
		}

		scan.close();

		return adults;
	}

}
