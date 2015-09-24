package assignment_1;

import static assignment_1.DecisionTree.*;

// Container class for the result
public class DecisionTreeResult {
	int count;
	int actual;
	int total;
	double ratio;
	int[][] confusion;

	DecisionTreeResult(int count, int total, int[][] confusion) {
		this.count = count;
		this.total = total;
		ratio = count * 1.0f / total;
		this.confusion = confusion;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("***************************\n");
		sb.append("Your decision tree scored:\n" + count + " out of "
				+ total + "! (" + String.format("%.2f%%)", ratio * 100.0f)
				+ "\n");
		sb.append("***************************\n");
		sb.append("CONFUSION CHART:\nPredicted vs actual\n \t\t"
				+ TRUE_LABEL + "\t\t" + FALSE_LABEL + "\n");
		sb.append("POSITIVE\t_" + confusion[TRUE][POSITIVE] + "_\t\t"
				+ confusion[FALSE][POSITIVE] + "\n");
		sb.append("NEGATIVE\t" + confusion[TRUE][NEGATIVE] + "\t\t_"
				+ confusion[FALSE][NEGATIVE] + "_\n");
		sb.append("===========================\n");
		sb.append("Correct "
				+ TRUE_LABEL
				+ " predictions: "
				+ confusion[TRUE][POSITIVE]
				+ String.format(
						" (%.2f%%)",
						confusion[TRUE][POSITIVE]
								* 100.0f
								/ (confusion[TRUE][POSITIVE] + confusion[FALSE][POSITIVE]))
				+ "\n");

		sb.append("Correct "
				+ FALSE_LABEL
				+ " predictions: "
				+ confusion[FALSE][NEGATIVE]
				+ String.format(
						" (%.2f%%)",
						confusion[FALSE][NEGATIVE]
								* 100.0f
								/ (confusion[TRUE][NEGATIVE] + confusion[FALSE][NEGATIVE]))
				+ "\n");

		return sb.toString();
	}

}