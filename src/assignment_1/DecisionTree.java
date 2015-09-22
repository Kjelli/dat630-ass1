package assignment_1;

import static assignment_1.Main.CAPITAL_GAIN;
import static assignment_1.Main.EDUCATION;
import static assignment_1.Main.EDUCATION_NUM;
import static assignment_1.Main.HOURS_PER_WEEK;
import static assignment_1.Main.MARITAL_STATUS;
import static assignment_1.Main.NATIVE_COUNTRY;
import static assignment_1.Main.OCCUPATION;
import static assignment_1.Main.OVER50K;
import static assignment_1.Main.RACE;
import static assignment_1.Main.RELATIONSHIP;
import static assignment_1.Main.SEX;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DecisionTree {
	static final int POSITIVE = 0, NEGATIVE = 1;
	static final int FALSE = 0, TRUE = 1;

	static final String TRUE_LABEL = ">50k", FALSE_LABEL = "<=50k";

	File output;
	boolean fileError = false;
	boolean training = false;

	// Run this with the data interpreted from the textfiles.
	public DecisionTreeResult estimate(ArrayList<Adult> data) {
		int correctCount = 0;
		int[][] confusion = new int[2][2];

		output = new File("task1.out");
		if (output.exists()) {
			output.delete();
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fileError = true;
		}

		for (Adult a : data) {
			boolean estimate = predict(a);
			boolean actual = false;
			if (training) {
				actual = (boolean) a.get(OVER50K);
			}
			if (!fileError) {
				try {
					fw.write((estimate ? ">50k\n" : "<=50k\n"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			if (training) {
				if (estimate) {
					if (actual) {
						correctCount++;
						confusion[TRUE][POSITIVE]++;
					} else {
						confusion[TRUE][NEGATIVE]++;
					}
				} else {
					if (actual) {
						confusion[FALSE][POSITIVE]++;
					} else {
						correctCount++;
						confusion[FALSE][NEGATIVE]++;
					}
				}
			}
		}
		if (!fileError) {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (training) {
			return new DecisionTreeResult(correctCount, data.size(), confusion);
		} else {
			return null;
		}
	}

	// This is the key method to assignment 1 and predicts wether or not an
	// adult makes over 50k or under
	private boolean predict(Adult a) {
		float score = 0.0f;
		if (a.get(NATIVE_COUNTRY).equals("United-States")) {
			score += -1;
		}

		if (a.get(EDUCATION).equals("HS-grad")) {
			score += -1;
		}

		if ((int) a.get(HOURS_PER_WEEK) <= 40) {
			score += -1;
		}

		if ((int) a.get(CAPITAL_GAIN) > 0) {
			score += 1;
		}

		if ((int) a.get(EDUCATION_NUM) < 9) {
			score += -2;
		}
		if (a.get(MARITAL_STATUS).equals("Never-married")) {
			score += -1;
		}

		if (a.get(MARITAL_STATUS).equals("Married-civ-spouse")) {
			score += 2;
		}

		if (!a.get(RELATIONSHIP).equals("Not-in-family")) {
			score += -1;
		}

		if (a.get(OCCUPATION).equals("Exec-managerial")
				|| a.get(OCCUPATION).equals("Sales")) {
			score += 2;
		}

		if (a.get(SEX).equals("Female")) {
			score += -1;
		}
		if (!a.get(RACE).equals("White")) {
			score += -1;
		}
		return score >= 0;
	}

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
}
