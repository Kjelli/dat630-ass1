package assignment_1;

import static assignment_1.Main.AGE;
import static assignment_1.Main.CAPITAL_GAIN;
import static assignment_1.Main.EDUCATION;
import static assignment_1.Main.EDUCATION_NUM;
import static assignment_1.Main.HOURS_PER_WEEK;
import static assignment_1.Main.MARITAL_STATUS;
import static assignment_1.Main.NATIVE_COUNTRY;
import static assignment_1.Main.OCCUPATION;
import static assignment_1.Main.RACE;
import static assignment_1.Main.RELATIONSHIP;
import static assignment_1.Main.SEX;

public class ScoreTree extends DecisionTree {

	public ScoreTree(boolean training, String filename) {
		super(training, filename);
	}

	// This is the key method to assignment 1 and predicts wether or not an
	// adult makes over 50k or under
	protected boolean predict(Adult a) {
		float score = 0.0f;
		if (a.get(NATIVE_COUNTRY).equals("United-States")) {
			score += -1;
		}

		if ((int) a.get(AGE) < 32) {
			score += -2;
		}

		if (a.get(EDUCATION).equals("HS-grad")) {
			score += -3;
		}

		if ((int) a.get(HOURS_PER_WEEK) <= 40) {
			score += -1;
		}

		if ((int) a.get(CAPITAL_GAIN) > 0) {
			score += 2;
		}

		if ((int) a.get(EDUCATION_NUM) < 9) {
			score += -2;
		}
		if (a.get(MARITAL_STATUS).equals("Never-married")) {
			score += -2;
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

}
