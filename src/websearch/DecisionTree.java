package websearch;

import java.util.ArrayList;

import static websearch.Main.*;

public class DecisionTree {
	// Run this with the data interpreted from the textfiles.
	public DecisionTreeResult estimate(ArrayList<Adult> data) {
		int count = 0;
		for (Adult a : data) {
			boolean estimate = predict(a);
			boolean actual = (boolean) a.get(OVER50K);
			if (estimate == actual) {
				count++;
			}
		}

		return new DecisionTreeResult(count, data.size());
	}

	// This is the key method to assignment 1 and predicts wether or not an
	// adult makes over 50k or under
	private boolean predict(Adult a) {
		float score = 0.0f;
		if (a.get(NATIVE_COUNTRY) == "United-States") {
			score += -67.56 + 22.02;
		}

		if (a.get(EDUCATION).equals("HS-grad")) {
			score += -27.10 + 5.14;
		}

		if ((int) a.get(HOURS_PER_WEEK) <= 40) {
			score -= 58.33f + 12.24;
		}
		
		if((int) a.get(CAPITAL_GAIN) > 0){
			score += -3.18 + 5.15; 
		}

		if ((int) a.get(EDUCATION_NUM) < 9) {
			score -= 39.42f;
		}
		if (a.get(MARITAL_STATUS).equals("Never-married")) {
			score += -31.3 + 1.5;
		}
		if (!a.get(RELATIONSHIP).equals("Not-in-family")) {
			score += -22.87 + 2.63;
		}

		if (a.get(SEX).equals("Female")) {
			score += -29.46 + 3.62;
		}
		if (!a.get(RACE).equals("White")) {
			score += -12.35 + 2.22;
		}
		return score >= 0;
	}

	// Container class for the result
	public class DecisionTreeResult {
		int count;
		int total;
		double ratio;

		DecisionTreeResult(int count, int total) {
			this.count = count;
			this.total = total;
			ratio = count * 1.0f / total;
		}

	}
}
