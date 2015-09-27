package assignment_1;

import static assignment_1.Main.*;

public class GainTree extends DecisionTree {

	public GainTree(boolean training, String filename) {
		super(training, filename);
	}

	@Override
	protected boolean predict(Adult a) {

		if ((int) a.get(CAPITAL_GAIN) > 7000) {
			return true;
		}

		if ((int) a.get(AGE) < 29) {

			return false;
		}
		if ((int) a.get(AGE) > 72) {
			return false;
		}
		if (a.get(RACE).equals("Other")
				|| a.get(RACE).equals("Amer-Indian-Eskimo")) {
			return false;
		}
		if (a.get(WORKCLASS).equals("Without-pay")) {
			return false;
		}

		if (a.get(OCCUPATION).equals("Farming-fishing")
				|| a.get(OCCUPATION).equals("Other-service")
				|| a.get(OCCUPATION).equals("Priv-house-serv")
				|| a.get(OCCUPATION).equals("Handlers-cleaners")) {
			return false;
		}

		if (a.get(NATIVE_COUNTRY).equals("Nicaragua")
				|| a.get(NATIVE_COUNTRY).equals("Outlying-US(Guam-USVI-etc)")
				|| a.get(NATIVE_COUNTRY).equals("South")
				|| a.get(NATIVE_COUNTRY).equals("Vietnam")
				|| a.get(NATIVE_COUNTRY).equals("Columbia")
				|| a.get(NATIVE_COUNTRY).equals("Dominican-Republic")
				|| a.get(NATIVE_COUNTRY).equals("Laos")) {
			return false;
		}

		if (a.get(SEX).equals("Male")) {
			
			if (a.get(MARITAL_STATUS).equals("Married-civ-spouse")
					|| a.get(MARITAL_STATUS).equals("Married-AF-spouse")) {
				if ((int) a.get(HOURS_PER_WEEK) < 32) {

					return false;
				}

				if ((int) a.get(EDUCATION_NUM) > 12) {
					
					return true;
				}

				if (a.is(WORKCLASS, "Federal-gov")) {
					return true;
				}

				if (a.is(WORKCLASS, "Self-emp-inc")) {
					return true;
				}

				if (a.is(WORKCLASS, "Self-emp-not-inc")) {
					return false;
				}

				if (a.get(OCCUPATION).equals("Tech-support")) {
					return true;
				}

				if ((int) a.get(EDUCATION_NUM) > 9) {

					if ((int) a.get(CAPITAL_LOSS) > 2000) {
						return false;
					}

					if (a.get(OCCUPATION).equals("Exec-managerial")) {
						return true;
					}

					if (a.get(OCCUPATION).equals("Sales")) {
						return true;
					}

					if (a.get(OCCUPATION).equals("Protective-serv")) {
						return true;
					}

					if (a.get(OCCUPATION).equals("Prof-specialty")) {
						return true;
					}

					if ((int) a.get(CAPITAL_GAIN) > 3000) {
						return true;
					}

				}

			}

		}

		if (a.get(SEX).equals("Female")) {
			if ((int) a.get(AGE) > 54) {

				return false;
			}
			if ((int) a.get(EDUCATION_NUM) < 10) {
				return false;
			}
			if (a.is(RELATIONSHIP, "Wife")) {
				return true;
			}

			if (a.get(OCCUPATION).equals("Prof-specialty")
					|| a.get(OCCUPATION).equals("Craft-repair")
					|| a.get(OCCUPATION).equals("Tech-support")) {

				return false;
			}

			if ((int) a.get(EDUCATION_NUM) > 15) {
				if (a.is(WORKCLASS, "Self-emp-not-inc")) {
					return false;
				}
				return true;
			}

			if (a.is(MARITAL_STATUS, "Widowed")) {

				return false;
			}

			if (a.is(MARITAL_STATUS, "Never-married")) {
				return false;
			}

		}

		return false;
	}

}
